package modelo.dao;

import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.usuario.Analista;
import utils.ConverterDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalistaDAOImpl implements AnalistaDAO {

    @Override
    public void cadastrarAnalista(Connection conexao, Analista analista) throws SQLException {
        String sqlEndereco = "INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuario (email, senha, cargo, endereco_id) VALUES (?, ?, ?, ?)";
        String sqlAnalista = "INSERT INTO usuario_analista (usuario_id, nome, sobrenome, cpf, sexo) VALUES (?, ?, ?, ?, ?)";

        Long enderecoId = null;
        Long usuarioId = null;

        /*
         *No cadastro, a operação no banco de dados será dividida em:
         * conexao.setAutoCommit(false) inicia uma transação, desabilitando o commit automático
         * 1º - Inserir endereço e recuperar id do endereço gerado
         * 2º - Inserir usuário (utilizando o endereco_id) e recuperar id do usuário gerado
         * 3º - Inserir a analista (utilizando o usuario_id)
         * conexao.commit() salva no banco de dados | Se der erro em qualquer das etapas acima, o commit não é executado (é feito rollback)
         */

        try {
            conexao.setAutoCommit(false);

            // 1. Inserir endereço e recuperar id gerado
            try (PreparedStatement psEndereco = conexao.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS)) {
                psEndereco.setString(1, analista.getEndereco().getLogradouro());
                psEndereco.setString(2, analista.getEndereco().getNumero());
                psEndereco.setString(3, analista.getEndereco().getComplemento());
                psEndereco.setString(4, analista.getEndereco().getBairro());
                psEndereco.setString(5, analista.getEndereco().getCidade());
                psEndereco.setString(6, analista.getEndereco().getEstado());
                psEndereco.setString(7, analista.getEndereco().getCep());
                psEndereco.setString(8, analista.getEndereco().getPais());

                psEndereco.executeUpdate();

                try (ResultSet idGerado = psEndereco.getGeneratedKeys()) {
                    if (!idGerado.next())
                        throw new SQLException("Falha ao obter o ID do endereço.");

                    enderecoId = idGerado.getLong(1);
                }
            }

            // 2. Inserir usuário (utilizando o endereco_id) e recuperar id gerado
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, analista.getEmail());
                psUsuario.setString(2, analista.getSenha());
                psUsuario.setString(3, Cargo.ANALISTA.toString());
                psUsuario.setLong(4, enderecoId);

                psUsuario.executeUpdate();

                try (ResultSet idGerado = psUsuario.getGeneratedKeys()) {
                    if (!idGerado.next())
                        throw new SQLException("Falha ao obter o ID do usuário.");

                    usuarioId = idGerado.getLong(1);
                }
            }

            // 3. Inserir analista (utilizando usuario_id)
            try (PreparedStatement psAnalista = conexao.prepareStatement(sqlAnalista)) {
                psAnalista.setLong(1, usuarioId);
                psAnalista.setString(2, analista.getNome());
                psAnalista.setString(3, analista.getSobrenome());
                psAnalista.setString(4, analista.getCpf());
                psAnalista.setString(5, analista.getSexo().toString());

                psAnalista.executeUpdate();
            }

            conexao.commit();

        } catch (SQLException e) {
            conexao.rollback();
            throw e;

        } finally {
            if (conexao != null)
                conexao.setAutoCommit(true);
        }
    }

    @Override
    public void atualizarAnalista(Connection conexao, Analista analista) throws SQLException {
        String sqlEndereco = "UPDATE endereco SET logradouro=?, numero=?, complemento=?, bairro=?, cidade=?, estado=?, cep=?, pais=? WHERE id = ?";
        String sqlUsuario = "UPDATE usuario SET email=?, senha=?, cargo=? WHERE id = ?";
        String sqlAnalista = "UPDATE usuario_analista SET nome=?, sobrenome=?, cpf=?, sexo=? WHERE usuario_id = ?";

        /*
         *Na atualização, a operação no banco de dados será dividida em:
         * conexao.setAutoCommit(false) inicia uma transação, desabilitando o commit automático
         * 1º - Atualizar endereço
         * 2º - Atualizar usuário
         * 3º - Atualizar analista
         * conexao.commit() salva no banco de dados | Se der erro em qualquer das etapas acima, o commit não é executado (é feito rollback)
         */

        try {
            conexao.setAutoCommit(false);

            // 1. Atualizar endereco
            try (PreparedStatement psEndereco = conexao.prepareStatement(sqlEndereco)) {
                psEndereco.setString(1, analista.getEndereco().getLogradouro());
                psEndereco.setString(2, analista.getEndereco().getNumero());
                psEndereco.setString(3, analista.getEndereco().getComplemento());
                psEndereco.setString(4, analista.getEndereco().getBairro());
                psEndereco.setString(5, analista.getEndereco().getCidade());
                psEndereco.setString(6, analista.getEndereco().getEstado());
                psEndereco.setString(7, analista.getEndereco().getCep());
                psEndereco.setString(8, analista.getEndereco().getPais());
                psEndereco.setLong(9, analista.getEndereco().getId());

                int linhasAfetadas = psEndereco.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o endereço com id: " + analista.getEndereco().getId());
            }

            // 2) Atualizar usuario
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario)) {
                psUsuario.setString(1, analista.getEmail());
                psUsuario.setString(2, analista.getSenha()); // idealmente já com hash
                psUsuario.setString(3, Cargo.ANALISTA.name());
                psUsuario.setLong(4, analista.getId());

                int linhasAfetadas = psUsuario.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o usuário com id: " + analista.getId());
            }

            // 3) Atualizar analista
            try (PreparedStatement psAnalista = conexao.prepareStatement(sqlAnalista)) {
                psAnalista.setString(1, analista.getNome());
                psAnalista.setString(2, analista.getSobrenome());
                psAnalista.setString(3, analista.getCpf());
                psAnalista.setString(4, analista.getSexo().toString());
                psAnalista.setLong(5, analista.getId());

                int linhasAfetadas = psAnalista.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o usuário com id: " + analista.getId());
            }

            conexao.commit();

        } catch (SQLException e) {
            conexao.rollback();
            throw e;

        } finally {
            if (conexao != null)
                conexao.setAutoCommit(true);
        }
    }

    @Override
    public void deletarAnalistaPeloId(Connection conexao, Long id) throws SQLException {
        String sqlUsuario = "DELETE FROM usuario WHERE id = ?";

        /*
         * Na deleção, a operação no banco de dados é bastante simples:
         * Deleta apenas o usuário. O endereço e analista vinculados são deletados automaticamente, em castaca.
         */

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sqlUsuario)) {
            preparedStatement.setLong(1, id);

            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas == 0)
                throw new SQLException("Nenhum usuário encontrado com o ID fornecido: " + id);
        }
    }

    @Override
    public Analista recuperarAnalistaPeloId(Connection conexao, Long id) throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais,"
                + "ue.nome, ue.sobrenome, ue.cpf, ue.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_analista ue ON u.id = ue.usuario_id "
                + "INNER JOIN endereco e ON u.endereco_id = e.id "
                + "WHERE u.id = ?";

        Analista analista = null;

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(resultSet.getLong("endereco_id"));
                    endereco.setLogradouro(resultSet.getString("logradouro"));
                    endereco.setNumero(resultSet.getString("numero"));
                    endereco.setComplemento(resultSet.getString("complemento"));
                    endereco.setBairro(resultSet.getString("bairro"));
                    endereco.setCidade(resultSet.getString("cidade"));
                    endereco.setEstado(resultSet.getString("estado"));
                    endereco.setCep(resultSet.getString("cep"));
                    endereco.setPais(resultSet.getString("pais"));

                    analista = new Analista();
                    analista.setId(resultSet.getLong("id"));
                    analista.setEmail(resultSet.getString("email"));
                    analista.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    analista.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    analista.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    analista.setEndereco(endereco);

                    analista.setNome(resultSet.getString("nome"));
                    analista.setSobrenome(resultSet.getString("sobrenome"));
                    analista.setCpf(resultSet.getString("cpf"));
                    analista.setSexo(Sexo.valueOf(resultSet.getString("sexo")));
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar analista com ID: " + id, e);
            throw e;
        }

        return analista;
    }

    @Override
    public List<Analista> recuperarAnalistasPeloCurso(Connection conexao, Long cursoId) throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais, "
                + "a.nome, a.sobrenome, a.cpf, a.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_analista a ON u.id = a.usuario_id "
                + "INNER JOIN endereco e ON e.id = u.endereco_id "
                + "INNER JOIN curso c ON c.analista_id = u.id "
                + "WHERE c.id = ?";

        List<Analista> analistas = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setLong(1, cursoId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(resultSet.getLong("endereco_id"));
                    endereco.setLogradouro(resultSet.getString("logradouro"));
                    endereco.setNumero(resultSet.getString("numero"));
                    endereco.setComplemento(resultSet.getString("complemento"));
                    endereco.setBairro(resultSet.getString("bairro"));
                    endereco.setCidade(resultSet.getString("cidade"));
                    endereco.setEstado(resultSet.getString("estado"));
                    endereco.setCep(resultSet.getString("cep"));
                    endereco.setPais(resultSet.getString("pais"));

                    Analista analista = new Analista();
                    analista.setId(resultSet.getLong("id"));
                    analista.setEmail(resultSet.getString("email"));
                    analista.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    analista.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    analista.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    analista.setEndereco(endereco);

                    analista.setNome(resultSet.getString("nome"));
                    analista.setSobrenome(resultSet.getString("sobrenome"));
                    analista.setCpf(resultSet.getString("cpf"));
                    analista.setSexo(Sexo.valueOf(resultSet.getString("sexo")));

                    analistas.add(analista);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de analistas", e);
            throw e;
        }

        return analistas;
    }
}

package modelo.dao;

import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.usuario.Pedagogo;
import utils.ConverterDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedagogoDAOImpl implements PedagogoDAO {

    @Override
    public void cadastrarPedagogo(Connection conexao, Pedagogo pedagogo) throws SQLException {
        String sqlEndereco = "INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuario (email, senha, cargo, endereco_id) VALUES (?, ?, ?, ?)";
        String sqlPedagogo = "INSERT INTO usuario_pedagogo (usuario_id, nome, sobrenome, cpf, sexo) VALUES (?, ?, ?, ?, ?)";

        Long enderecoId = null;
        Long usuarioId = null;

        /*
         *No cadastro, a operação no banco de dados será dividida em:
         * conexao.setAutoCommit(false) inicia uma transação, desabilitando o commit automático
         * 1º - Inserir endereço e recuperar id do endereço gerado
         * 2º - Inserir usuário (utilizando o endereco_id) e recuperar id do usuário gerado
         * 3º - Inserir o pedagogo (utilizando o usuario_id)
         * conexao.commit() salva no banco de dados | Se der erro em qualquer das etapas acima, o commit não é executado (é feito rollback)
         */

        try {
            conexao.setAutoCommit(false);

            // 1. Inserir endereço e recuperar id gerado
            try (PreparedStatement psEndereco = conexao.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS)) {
                psEndereco.setString(1, pedagogo.getEndereco().getLogradouro());
                psEndereco.setString(2, pedagogo.getEndereco().getNumero());
                psEndereco.setString(3, pedagogo.getEndereco().getComplemento());
                psEndereco.setString(4, pedagogo.getEndereco().getBairro());
                psEndereco.setString(5, pedagogo.getEndereco().getCidade());
                psEndereco.setString(6, pedagogo.getEndereco().getEstado());
                psEndereco.setString(7, pedagogo.getEndereco().getCep());
                psEndereco.setString(8, pedagogo.getEndereco().getPais());

                psEndereco.executeUpdate();

                try (ResultSet idGerado = psEndereco.getGeneratedKeys()) {
                    if (!idGerado.next())
                        throw new SQLException("Falha ao obter o ID do endereço.");

                    enderecoId = idGerado.getLong(1);
                }
            }

            // 2. Inserir usuário (utilizando o endereco_id) e recuperar id gerado
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, pedagogo.getEmail());
                psUsuario.setString(2, pedagogo.getSenha());
                psUsuario.setString(3, Cargo.PEDAGOGO.toString());
                psUsuario.setLong(4, enderecoId);

                psUsuario.executeUpdate();

                try (ResultSet idGerado = psUsuario.getGeneratedKeys()) {
                    if (!idGerado.next())
                        throw new SQLException("Falha ao obter o ID do usuário.");

                    usuarioId = idGerado.getLong(1);
                }
            }

            // 3. Inserir pedagogo (utilizando usuario_id)
            try (PreparedStatement psPedagogo = conexao.prepareStatement(sqlPedagogo)) {
                psPedagogo.setLong(1, usuarioId);
                psPedagogo.setString(2, pedagogo.getNome());
                psPedagogo.setString(3, pedagogo.getSobrenome());
                psPedagogo.setString(4, pedagogo.getCpf());
                psPedagogo.setString(5, pedagogo.getSexo().toString());

                psPedagogo.executeUpdate();
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
    public void atualizarPedagogo(Connection conexao, Pedagogo pedagogo) throws SQLException {
        String sqlEndereco = "UPDATE endereco SET logradouro=?, numero=?, complemento=?, bairro=?, cidade=?, estado=?, cep=?, pais=? WHERE id = ?";
        String sqlUsuario = "UPDATE usuario SET email=?, senha=?, cargo=? WHERE id = ?";
        String sqlPedagogo = "UPDATE usuario_pedagogo SET nome=?, sobrenome=?, cpf=?, sexo=? WHERE usuario_id = ?";

        /*
         *Na atualização, a operação no banco de dados será dividida em:
         * conexao.setAutoCommit(false) inicia uma transação, desabilitando o commit automático
         * 1º - Atualizar endereço
         * 2º - Atualizar usuário
         * 3º - Atualizar pedagogo
         * conexao.commit() salva no banco de dados | Se der erro em qualquer das etapas acima, o commit não é executado (é feito rollback)
         */

        try {
            conexao.setAutoCommit(false);

            // 1. Atualizar endereco
            try (PreparedStatement psEndereco = conexao.prepareStatement(sqlEndereco)) {
                psEndereco.setString(1, pedagogo.getEndereco().getLogradouro());
                psEndereco.setString(2, pedagogo.getEndereco().getNumero());
                psEndereco.setString(3, pedagogo.getEndereco().getComplemento());
                psEndereco.setString(4, pedagogo.getEndereco().getBairro());
                psEndereco.setString(5, pedagogo.getEndereco().getCidade());
                psEndereco.setString(6, pedagogo.getEndereco().getEstado());
                psEndereco.setString(7, pedagogo.getEndereco().getCep());
                psEndereco.setString(8, pedagogo.getEndereco().getPais());
                psEndereco.setLong(9, pedagogo.getEndereco().getId());

                int linhasAfetadas = psEndereco.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o endereço com id: " + pedagogo.getEndereco().getId());
            }

            // 2) Atualizar usuario
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario)) {
                psUsuario.setString(1, pedagogo.getEmail());
                psUsuario.setString(2, pedagogo.getSenha()); // idealmente já com hash
                psUsuario.setString(3, Cargo.PEDAGOGO.name());
                psUsuario.setLong(4, pedagogo.getId());

                int linhasAfetadas = psUsuario.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o usuário com id: " + pedagogo.getId());
            }

            // 3) Atualizar pedagogo
            try (PreparedStatement psPedagogo = conexao.prepareStatement(sqlPedagogo)) {
                psPedagogo.setString(1, pedagogo.getNome());
                psPedagogo.setString(2, pedagogo.getSobrenome());
                psPedagogo.setString(3, pedagogo.getCpf());
                psPedagogo.setString(4, pedagogo.getSexo().toString());
                psPedagogo.setLong(5, pedagogo.getId());

                int linhasAfetadas = psPedagogo.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o usuário com id: " + pedagogo.getId());
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
    public void deletarPedagogoPeloId(Connection conexao, Long id) throws SQLException {
        String sqlUsuario = "DELETE FROM usuario WHERE id = ?";

        /*
         * Na deleção, a operação no banco de dados é bastante simples:
         * Deleta apenas o usuário. O endereço e pedagogo vinculados são deletados automaticamente, em castaca.
         */

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sqlUsuario)) {
            preparedStatement.setLong(1, id);

            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas == 0)
                throw new SQLException("Nenhum usuário encontrado com o ID fornecido: " + id);
        }
    }

    @Override
    public Pedagogo recuperarPedagogoPeloId(Connection conexao, Long id) throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais,"
                + "p.nome, p.sobrenome, p.cpf, p.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_pedagogo p ON u.id = p.usuario_id "
                + "INNER JOIN endereco e ON u.endereco_id = e.id "
                + "WHERE u.id = ?";

        Pedagogo pedagogo = null;

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

                    pedagogo = new Pedagogo();
                    pedagogo.setId(resultSet.getLong("id"));
                    pedagogo.setEmail(resultSet.getString("email"));
                    pedagogo.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    pedagogo.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    pedagogo.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    pedagogo.setEndereco(endereco);

                    pedagogo.setNome(resultSet.getString("nome"));
                    pedagogo.setSobrenome(resultSet.getString("sobrenome"));
                    pedagogo.setCpf(resultSet.getString("cpf"));
                    pedagogo.setSexo(Sexo.valueOf(resultSet.getString("sexo")));
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar pedagogo com ID: " + id, e);
            throw e;
        }

        return pedagogo;
    }

    @Override
    public List<Pedagogo> recuperarPedagogosPelaTurma(Connection conexao, Long cursoId) throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais, "
                + "p.nome, p.sobrenome, p.cpf, p.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_pedagogo p ON u.id = p.usuario_id "
                + "INNER JOIN endereco e ON e.id = u.endereco_id "
                + "INNER JOIN turma t ON t.pedagogo_id = u.id "
                + "WHERE t.id = ?";

        List<Pedagogo> pedagogos = new ArrayList<>();

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

                    Pedagogo pedagogo = new Pedagogo();
                    pedagogo.setId(resultSet.getLong("id"));
                    pedagogo.setEmail(resultSet.getString("email"));
                    pedagogo.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    pedagogo.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    pedagogo.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    pedagogo.setEndereco(endereco);

                    pedagogo.setNome(resultSet.getString("nome"));
                    pedagogo.setSobrenome(resultSet.getString("sobrenome"));
                    pedagogo.setCpf(resultSet.getString("cpf"));
                    pedagogo.setSexo(Sexo.valueOf(resultSet.getString("sexo")));

                    pedagogos.add(pedagogo);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de pedagogos", e);
            throw e;
        }

        return pedagogos;
    }
}

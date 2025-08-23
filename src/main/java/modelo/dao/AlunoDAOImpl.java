package modelo.dao;

import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.usuario.Aluno;
import utils.ConverterDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlunoDAOImpl implements AlunoDAO {

    @Override
    public void cadastrarAluno(Connection conexao, Aluno aluno) throws SQLException {
        String sqlEndereco = "INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuario (email, senha, cargo, endereco_id) VALUES (?, ?, ?, ?)";
        String sqlAluno = "INSERT INTO usuario_aluno (usuario_id, nome, sobrenome, cpf, sexo) VALUES (?, ?, ?, ?, ?)";

        Long enderecoId = null;
        Long usuarioId = null;

        /*
         *No cadastro, a operação no banco de dados será dividida em:
         * conexao.setAutoCommit(false) inicia uma transação, desabilitando o commit automático
         * 1º - Inserir endereço e recuperar id do endereço gerado
         * 2º - Inserir usuário (utilizando o endereco_id) e recuperar id do usuário gerado
         * 3º - Inserir o aluno (utilizando o usuario_id)
         * conexao.commit() salva no banco de dados | Se der erro em qualquer das etapas acima, o commit não é executado (é feito rollback)
         */

        try {
            conexao.setAutoCommit(false);

            // 1. Inserir endereço e recuperar id gerado
            try (PreparedStatement psEndereco = conexao.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS)) {
                psEndereco.setString(1, aluno.getEndereco().getLogradouro());
                psEndereco.setString(2, aluno.getEndereco().getNumero());
                psEndereco.setString(3, aluno.getEndereco().getComplemento());
                psEndereco.setString(4, aluno.getEndereco().getBairro());
                psEndereco.setString(5, aluno.getEndereco().getCidade());
                psEndereco.setString(6, aluno.getEndereco().getEstado());
                psEndereco.setString(7, aluno.getEndereco().getCep());
                psEndereco.setString(8, aluno.getEndereco().getPais());

                psEndereco.executeUpdate();

                try (ResultSet idGerado = psEndereco.getGeneratedKeys()) {
                    if (!idGerado.next())
                        throw new SQLException("Falha ao obter o ID do endereço.");

                    enderecoId = idGerado.getLong(1);
                }
            }

            // 2. Inserir usuário (utilizando o endereco_id) e recuperar id gerado
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, aluno.getEmail());
                psUsuario.setString(2, aluno.getSenha());
                psUsuario.setString(3, Cargo.ALUNO.toString());
                psUsuario.setLong(4, enderecoId);

                psUsuario.executeUpdate();

                try (ResultSet idGerado = psUsuario.getGeneratedKeys()) {
                    if (!idGerado.next())
                        throw new SQLException("Falha ao obter o ID do usuário.");

                    usuarioId = idGerado.getLong(1);
                }
            }

            // 3. Inserir aluno (utilizando usuario_id)
            try (PreparedStatement psAluno = conexao.prepareStatement(sqlAluno)) {
                psAluno.setLong(1, usuarioId);
                psAluno.setString(2, aluno.getNome());
                psAluno.setString(3, aluno.getSobrenome());
                psAluno.setString(4, aluno.getCpf());
                psAluno.setString(5, aluno.getSexo().toString());

                psAluno.executeUpdate();
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
    public void atualizarAluno(Connection conexao, Aluno aluno) throws SQLException {
        String sqlEndereco = "UPDATE endereco SET logradouro=?, numero=?, complemento=?, bairro=?, cidade=?, estado=?, cep=?, pais=? WHERE id = ?";
        String sqlUsuario = "UPDATE usuario SET email=?, senha=?, cargo=? WHERE id = ?";
        String sqlAluno = "UPDATE usuario_aluno SET nome=?, sobrenome=?, cpf=?, sexo=? WHERE usuario_id = ?";

        /*
         *Na atualização, a operação no banco de dados será dividida em:
         * conexao.setAutoCommit(false) inicia uma transação, desabilitando o commit automático
         * 1º - Atualizar endereço
         * 2º - Atualizar usuário
         * 3º - Atualizar aluno
         * conexao.commit() salva no banco de dados | Se der erro em qualquer das etapas acima, o commit não é executado (é feito rollback)
         */

        try {
            conexao.setAutoCommit(false);

            // 1. Atualizar endereco
            try (PreparedStatement psEndereco = conexao.prepareStatement(sqlEndereco)) {
                psEndereco.setString(1, aluno.getEndereco().getLogradouro());
                psEndereco.setString(2, aluno.getEndereco().getNumero());
                psEndereco.setString(3, aluno.getEndereco().getComplemento());
                psEndereco.setString(4, aluno.getEndereco().getBairro());
                psEndereco.setString(5, aluno.getEndereco().getCidade());
                psEndereco.setString(6, aluno.getEndereco().getEstado());
                psEndereco.setString(7, aluno.getEndereco().getCep());
                psEndereco.setString(8, aluno.getEndereco().getPais());
                psEndereco.setLong(9, aluno.getEndereco().getId());

                int linhasAfetadas = psEndereco.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o endereço com id: " + aluno.getEndereco().getId());
            }

            // 2) Atualizar usuario
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario)) {
                psUsuario.setString(1, aluno.getEmail());
                psUsuario.setString(2, aluno.getSenha()); // idealmente já com hash
                psUsuario.setString(3, Cargo.ALUNO.name());
                psUsuario.setLong(4, aluno.getId());

                int linhasAfetadas = psUsuario.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o usuário com id: " + aluno.getId());
            }

            // 3) Atualizar aluno
            try (PreparedStatement psAluno = conexao.prepareStatement(sqlAluno)) {
                psAluno.setString(1, aluno.getNome());
                psAluno.setString(2, aluno.getSobrenome());
                psAluno.setString(3, aluno.getCpf());
                psAluno.setString(4, aluno.getSexo().toString());
                psAluno.setLong(5, aluno.getId());

                int linhasAfetadas = psAluno.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o usuário com id: " + aluno.getId());
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
    public void deletarAlunoPeloId(Connection conexao, Long id) throws SQLException {
        String sqlUsuario = "DELETE FROM usuario WHERE id = ?";

        /*
         * Na deleção, a operação no banco de dados é bastante simples:
         * Deleta apenas o usuário. O endereço e aluno vinculados são deletados automaticamente, em castaca.
         */

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sqlUsuario)) {
            preparedStatement.setLong(1, id);

            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas == 0)
                throw new SQLException("Nenhum usuário encontrado com o ID fornecido: " + id);
        }
    }

    @Override
    public Aluno recuperarAlunoPeloId(Connection conexao, Long id) throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais,"
                + "ua.nome, ua.sobrenome, ua.cpf, ua.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_aluno ua ON u.id = ua.usuario_id "
                + "INNER JOIN endereco e ON u.endereco_id = e.id "
                + "WHERE u.id = ?";

        Aluno aluno = null;

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

                    aluno = new Aluno();
                    aluno.setId(resultSet.getLong("id"));
                    aluno.setEmail(resultSet.getString("email"));
                    aluno.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    aluno.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    aluno.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    aluno.setEndereco(endereco);

                    aluno.setNome(resultSet.getString("nome"));
                    aluno.setSobrenome(resultSet.getString("sobrenome"));
                    aluno.setCpf(resultSet.getString("cpf"));
                    aluno.setSexo(Sexo.valueOf(resultSet.getString("sexo")));
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar aluno com ID: " + id, e);
            throw e;
        }

        return aluno;
    }

    @Override
    public List<Aluno> recuperarAlunosPelaTurma(Connection conexao, Long turmaId) throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais, "
                + "ua.nome, ua.sobrenome, ua.cpf, ua.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_aluno ua ON u.id = ua.usuario_id "
                + "INNER JOIN endereco e ON e.id = u.endereco_id "
                + "WHERE ua.turma_id = ?";

        List<Aluno> alunos = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            preparedStatement.setLong(1, turmaId);

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

                    Aluno aluno = new Aluno();
                    aluno.setId(resultSet.getLong("id"));
                    aluno.setEmail(resultSet.getString("email"));
                    aluno.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    aluno.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    aluno.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    aluno.setEndereco(endereco);

                    aluno.setNome(resultSet.getString("nome"));
                    aluno.setSobrenome(resultSet.getString("sobrenome"));
                    aluno.setCpf(resultSet.getString("cpf"));
                    aluno.setSexo(Sexo.valueOf(resultSet.getString("sexo")));

                    alunos.add(aluno);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de alunos", e);
            throw e;
        }

        return alunos;
    }
}

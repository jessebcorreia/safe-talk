package modelo.dao;

import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.usuario.UnidadeEnsino;
import utils.ConverterDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnidadeEnsinoDAOImpl implements UnidadeEnsinoDAO {

    @Override
    public void cadastrarUnidadeEnsino(Connection conexao, UnidadeEnsino unidadeEnsino) throws SQLException {
        String sqlEndereco = "INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuario (email, senha, cargo, endereco_id) VALUES (?, ?, ?, ?)";
        String sqlUnidadeEnsino = "INSERT INTO usuario_unidade_ensino (usuario_id, nome_fantasia, razao_social, cnpj, descricao) VALUES (?, ?, ?, ?, ?)";

        Long enderecoId = null;
        Long usuarioId = null;

        /*
         *No cadastro, a operação no banco de dados será dividida em:
         * conexao.setAutoCommit(false) inicia uma transação, desabilitando o commit automático
         * 1º - Inserir endereço e recuperar id do endereço gerado
         * 2º - Inserir usuário (utilizando o endereco_id) e recuperar id do usuário gerado
         * 3º - Inserir a unidade de ensino (utilizando o usuario_id)
         * conexao.commit() salva no banco de dados | Se der erro em qualquer das etapas acima, o commit não é executado (é feito rollback)
         */

        try {
            conexao.setAutoCommit(false);

            // 1. Inserir endereço e recuperar id gerado
            try (PreparedStatement psEndereco = conexao.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS)) {
                psEndereco.setString(1, unidadeEnsino.getEndereco().getLogradouro());
                psEndereco.setString(2, unidadeEnsino.getEndereco().getNumero());
                psEndereco.setString(3, unidadeEnsino.getEndereco().getComplemento());
                psEndereco.setString(4, unidadeEnsino.getEndereco().getBairro());
                psEndereco.setString(5, unidadeEnsino.getEndereco().getCidade());
                psEndereco.setString(6, unidadeEnsino.getEndereco().getEstado());
                psEndereco.setString(7, unidadeEnsino.getEndereco().getCep());
                psEndereco.setString(8, unidadeEnsino.getEndereco().getPais());

                psEndereco.executeUpdate();

                try (ResultSet idGerado = psEndereco.getGeneratedKeys()) {
                    if (!idGerado.next())
                        throw new SQLException("Falha ao obter o ID do endereço.");

                    enderecoId = idGerado.getLong(1);
                }
            }

            // 2. Inserir usuário (utilizando o endereco_id) e recuperar id gerado
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, unidadeEnsino.getEmail());
                psUsuario.setString(2, unidadeEnsino.getSenha());
                psUsuario.setString(3, Cargo.UNIDADE_ENSINO.toString());
                psUsuario.setLong(4, enderecoId);

                psUsuario.executeUpdate();

                try (ResultSet idGerado = psUsuario.getGeneratedKeys()) {
                    if (!idGerado.next())
                        throw new SQLException("Falha ao obter o ID do usuário.");

                    usuarioId = idGerado.getLong(1);
                }
            }

            // 3. Inserir unidade de ensino (utilizando usuario_id)
            try (PreparedStatement psUnidadeEnsino = conexao.prepareStatement(sqlUnidadeEnsino)) {
                psUnidadeEnsino.setLong(1, usuarioId);
                psUnidadeEnsino.setString(2, unidadeEnsino.getNomeFantasia());
                psUnidadeEnsino.setString(3, unidadeEnsino.getRazaoSocial());
                psUnidadeEnsino.setString(4, unidadeEnsino.getCnpj());
                psUnidadeEnsino.setString(5, unidadeEnsino.getDescricao());

                psUnidadeEnsino.executeUpdate();
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
    public void atualizarUnidadeEnsino(Connection conexao, UnidadeEnsino unidadeEnsino) throws SQLException {
        String sqlEndereco = "UPDATE endereco SET logradouro=?, numero=?, complemento=?, bairro=?, cidade=?, estado=?, cep=?, pais=? WHERE id = ?";
        String sqlUsuario = "UPDATE usuario SET email=?, senha=?, cargo=? WHERE id = ?";
        String sqlUnidadeEnsino = "UPDATE usuario_unidade_ensino SET nome_fantasia=?, razao_social=?, cnpj=?, descricao=? WHERE usuario_id = ?";

        /*
         *Na atualização, a operação no banco de dados será dividida em:
         * conexao.setAutoCommit(false) inicia uma transação, desabilitando o commit automático
         * 1º - Atualizar endereço
         * 2º - Atualizar usuário
         * 3º - Atualizar unidade de ensino
         * conexao.commit() salva no banco de dados | Se der erro em qualquer das etapas acima, o commit não é executado (é feito rollback)
         */

        try {
            conexao.setAutoCommit(false);

            // 1. Atualizar endereco
            try (PreparedStatement psEndereco = conexao.prepareStatement(sqlEndereco)) {
                psEndereco.setString(1, unidadeEnsino.getEndereco().getLogradouro());
                psEndereco.setString(2, unidadeEnsino.getEndereco().getNumero());
                psEndereco.setString(3, unidadeEnsino.getEndereco().getComplemento());
                psEndereco.setString(4, unidadeEnsino.getEndereco().getBairro());
                psEndereco.setString(5, unidadeEnsino.getEndereco().getCidade());
                psEndereco.setString(6, unidadeEnsino.getEndereco().getEstado());
                psEndereco.setString(7, unidadeEnsino.getEndereco().getCep());
                psEndereco.setString(8, unidadeEnsino.getEndereco().getPais());
                psEndereco.setLong(9, unidadeEnsino.getEndereco().getId());

                int linhasAfetadas = psEndereco.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o endereço com id: " + unidadeEnsino.getEndereco().getId());
            }

            // 2) Atualizar usuario
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario)) {
                psUsuario.setString(1, unidadeEnsino.getEmail());
                psUsuario.setString(2, unidadeEnsino.getSenha()); // idealmente já com hash
                psUsuario.setString(3, Cargo.UNIDADE_ENSINO.name());
                psUsuario.setLong(4, unidadeEnsino.getId());

                int linhasAfetadas = psUsuario.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o usuário com id: " + unidadeEnsino.getId());
            }

            // 3) Atualizar unidade de ensino
            try (PreparedStatement psUnidadeEnsino = conexao.prepareStatement(sqlUnidadeEnsino)) {
                psUnidadeEnsino.setString(1, unidadeEnsino.getNomeFantasia());
                psUnidadeEnsino.setString(2, unidadeEnsino.getRazaoSocial());
                psUnidadeEnsino.setString(3, unidadeEnsino.getCnpj());
                psUnidadeEnsino.setString(4, unidadeEnsino.getDescricao());
                psUnidadeEnsino.setLong(5, unidadeEnsino.getId());

                int linhasAfetadas = psUnidadeEnsino.executeUpdate();
                if (linhasAfetadas == 0)
                    throw new SQLException("Não foi possível atualizar o usuário com id: " + unidadeEnsino.getId());
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
    public void deletarUnidadeEnsinoPeloId(Connection conexao, Long id) throws SQLException {
        String sqlUsuario = "DELETE FROM usuario WHERE id = ?";

        /*
         * Na deleção, a operação no banco de dados é bastante simples:
         * Deleta apenas o usuário. O endereço e unidade de ensino vinculados são deletados automaticamente, em castaca.
         */

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sqlUsuario)) {
            preparedStatement.setLong(1, id);

            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas == 0)
                throw new SQLException("Nenhum usuário encontrado com o ID fornecido: " + id);
        }
    }

    @Override
    public UnidadeEnsino recuperarUnidadeEnsinoPeloId(Connection conexao, Long id) throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais,"
                + "ue.nome_fantasia, ue.razao_social, ue.cnpj, ue.descricao "
                + "FROM usuario u "
                + "INNER JOIN usuario_unidade_ensino ue ON u.id = ue.usuario_id "
                + "INNER JOIN endereco e ON u.endereco_id = e.id "
                + "WHERE u.id = ?";

        UnidadeEnsino unidadeEnsino = null;

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

                    unidadeEnsino = new UnidadeEnsino();
                    unidadeEnsino.setId(resultSet.getLong("id"));
                    unidadeEnsino.setEmail(resultSet.getString("email"));
                    unidadeEnsino.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                    unidadeEnsino.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                    unidadeEnsino.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                    unidadeEnsino.setEndereco(endereco);

                    unidadeEnsino.setNomeFantasia(resultSet.getString("nome_fantasia"));
                    unidadeEnsino.setRazaoSocial(resultSet.getString("razao_social"));
                    unidadeEnsino.setCnpj(resultSet.getString("cnpj"));
                    unidadeEnsino.setDescricao(resultSet.getString("descricao"));
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar unidade de ensino com ID: " + id, e);
            throw e;
        }

        return unidadeEnsino;
    }

    @Override
    public List<UnidadeEnsino> recuperarUnidadesDeEnsino(Connection conexao) throws SQLException {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais,"
                + "ue.nome_fantasia, ue.razao_social, ue.cnpj, ue.descricao "
                + "FROM usuario u "
                + "INNER JOIN usuario_unidade_ensino ue ON u.id = ue.usuario_id "
                + "INNER JOIN endereco e ON u.endereco_id = e.id";

        List<UnidadeEnsino> unidadesDeEnsino = new ArrayList<>();

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

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

                UnidadeEnsino unidadeEnsino = new UnidadeEnsino();
                unidadeEnsino.setId(resultSet.getLong("id"));
                unidadeEnsino.setEmail(resultSet.getString("email"));
                unidadeEnsino.setCriadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em")));
                unidadeEnsino.setAtualizadoEm(ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em")));
                unidadeEnsino.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
                unidadeEnsino.setEndereco(endereco);

                unidadeEnsino.setNomeFantasia(resultSet.getString("nome_fantasia"));
                unidadeEnsino.setRazaoSocial(resultSet.getString("razao_social"));
                unidadeEnsino.setCnpj(resultSet.getString("cnpj"));
                unidadeEnsino.setDescricao(resultSet.getString("descricao"));

                unidadesDeEnsino.add(unidadeEnsino);
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de unidades de ensino", e);
            throw e;
        }

        return unidadesDeEnsino;
    }
}

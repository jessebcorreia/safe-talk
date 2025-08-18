package modelo.dao;

import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.usuario.UnidadeEnsino;
import utils.ConverterDados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnidadeEnsinoDAOImpl implements UnidadeEnsinoDAO {

    @Override
    public boolean cadastrarUnidadeEnsino(Connection conexao, UnidadeEnsino unidadeEnsino) {
        String sql = "INSERT INTO usuario_unidade_ensino (usuario_id, nome_fantasia, razao_social, cnpj, descricao) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, unidadeEnsino.getId());
            preparedStatement.setString(2, unidadeEnsino.getNomeFantasia());
            preparedStatement.setString(3, unidadeEnsino.getRazaoSocial());
            preparedStatement.setString(4, unidadeEnsino.getCnpj());
            preparedStatement.setString(5, unidadeEnsino.getDescricao());

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            Logger.getLogger(UnidadeEnsinoDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean atualizarUnidadeEnsino(Connection conexao, UnidadeEnsino unidadeEnsino) {
        String sql = "UPDATE usuario_unidade_ensino SET nome_fantasia=?, razao_social=?, cnpj=?, descricao=? WHERE usuario_id = ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, unidadeEnsino.getNomeFantasia());
            preparedStatement.setString(2, unidadeEnsino.getRazaoSocial());
            preparedStatement.setString(3, unidadeEnsino.getCnpj());
            preparedStatement.setString(4, unidadeEnsino.getDescricao());
            preparedStatement.setLong(5, unidadeEnsino.getId());

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            Logger.getLogger(UnidadeEnsinoDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public UnidadeEnsino recuperarUnidadeEnsinoPeloId(Connection conexao, Long id) {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais,"
                + "ue.nome_fantasia, ue.razao_social, ue.cnpj, ue.descricao "
                + "FROM usuario u "
                + "INNER JOIN usuario_unidade_ensino ue ON u.id = ue.usuario_id "
                + "INNER JOIN endereco e ON u.endereco_id = e.id "
                + "WHERE u.id = ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Long enderecoId = resultSet.getLong("endereco_id");
                String logradouro = resultSet.getString("logradouro");
                String numero = resultSet.getString("numero");
                String complemento = resultSet.getString("complemento");
                String bairro = resultSet.getString("bairro");
                String cidade = resultSet.getString("cidade");
                String estado = resultSet.getString("estado");
                String cep = resultSet.getString("cep");
                String pais = resultSet.getString("pais");

                Endereco endereco = new Endereco(enderecoId, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);
                Cargo cargo = Cargo.valueOf(resultSet.getString("cargo"));

                Long usuarioId = resultSet.getLong("id");
                String email = resultSet.getString("email");
                LocalDateTime criadoEm = ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("criado_em"));
                LocalDateTime atualizadoEm = ConverterDados.timestampParaLocalDateTime(resultSet.getTimestamp("atualizado_em"));
                String nomeFantasia = resultSet.getString("nome_fantasia");
                String razaoSocial = resultSet.getString("razao_social");
                String cnpj = resultSet.getString("cnpj");
                String descricao = resultSet.getString("descricao");

                return new UnidadeEnsino(usuarioId, email, null, criadoEm, atualizadoEm, cargo, endereco, nomeFantasia, razaoSocial, cnpj, descricao);
            }
        } catch (SQLException e) {
            Logger.getLogger(UnidadeEnsinoDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return null;
    }
}

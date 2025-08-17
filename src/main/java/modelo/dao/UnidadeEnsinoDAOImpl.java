package modelo.dao;

import modelo.entidade.usuario.UnidadeEnsino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
}

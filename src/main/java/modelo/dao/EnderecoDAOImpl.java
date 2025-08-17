package modelo.dao;

import modelo.entidade.geral.Endereco;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnderecoDAOImpl implements EnderecoDAO {

    @Override
    public Long cadastrarEndereco(Connection conexao, Endereco endereco) {
        String sql = "INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, cep, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Long idGerado = null;

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, endereco.getLogradouro());
            preparedStatement.setString(2, endereco.getNumero());
            preparedStatement.setString(3, endereco.getComplemento());
            preparedStatement.setString(4, endereco.getBairro());
            preparedStatement.setString(5, endereco.getCidade());
            preparedStatement.setString(6, endereco.getEstado());
            preparedStatement.setString(7, endereco.getCep());
            preparedStatement.setString(8, endereco.getPais());

            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas == 0)
                throw new SQLException("Falha ao inserir endereço: nenhuma linha afetada.");

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (!resultSet.next())
                    throw new SQLException("Falha ao inserir endereço: não retornou ID.");
                idGerado = resultSet.getLong(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(EnderecoDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

        return idGerado;
    }
}

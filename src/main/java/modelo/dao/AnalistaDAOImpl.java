package modelo.dao;

import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.usuario.Analista;
import utils.ConverterDados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalistaDAOImpl implements AnalistaDAO {

    @Override
    public boolean cadastrarAnalista(Connection conexao, Analista analista) {
        String sql = "INSERT INTO usuario_analista (usuario_id, nome, sobrenome, cpf, sexo) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, analista.getId());
            preparedStatement.setString(2, analista.getNome());
            preparedStatement.setString(3, analista.getSobrenome());
            preparedStatement.setString(4, analista.getCpf());
            preparedStatement.setString(5, analista.getSexo().toString());

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            Logger.getLogger(AnalistaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean atualizarAnalista(Connection conexao, Analista analista) {
        String sql = "UPDATE usuario_analista SET nome=?, sobrenome=?, cpf=?, sexo=? WHERE usuario_id = ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, analista.getNome());
            preparedStatement.setString(2, analista.getSobrenome());
            preparedStatement.setString(3, analista.getCpf());
            preparedStatement.setString(4, analista.getSexo().toString());
            preparedStatement.setLong(5, analista.getId());

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            Logger.getLogger(AnalistaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public Analista recuperarAnalistaPeloId(Connection conexao, Long id) {
        String sql = "SELECT u.id, u.email, u.criado_em, u.atualizado_em, u.cargo, "
                + "e.id AS endereco_id, e.logradouro, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep, e.pais,"
                + "a.nome, a.sobrenome, a.cpf, a.sexo "
                + "FROM usuario u "
                + "INNER JOIN usuario_analista a ON u.id = a.usuario_id "
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
                String nome = resultSet.getString("nome");
                String sobrenome = resultSet.getString("sobrenome");
                String cpf = resultSet.getString("cpf");
                Sexo sexo = Sexo.valueOf(resultSet.getString("sexo"));

                return new Analista(usuarioId, email, null, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo, null);
            }
        } catch (SQLException e) {
            Logger.getLogger(AnalistaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return null;
    }
}

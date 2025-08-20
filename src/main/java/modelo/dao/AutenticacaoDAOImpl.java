package modelo.dao;

import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.usuario.*;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutenticacaoDAOImpl implements AutenticacaoDAO {

    @Override
    public Usuario recuperarUsuarioPeloEmail(Connection conexao, String email) throws SQLException {
        String sql = "SELECT id, email, senha, cargo FROM usuario WHERE email = ?";
        Usuario usuario = null;

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, email);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String senha = resultSet.getString("senha");
                    String cargoStr = resultSet.getString("cargo");
                    Cargo cargo = Cargo.valueOf(cargoStr);

                    usuario = instanciarUsuarioDeACordoComCargo(id, email, senha, cargo);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar usuário pelo email: " + email, e);
            throw e;
        }

        return usuario;
    }

    private Usuario instanciarUsuarioDeACordoComCargo(Long id, String email, String senha, Cargo cargo) {
        switch (cargo) {
            case ALUNO:
                return new Aluno(id, email, senha, cargo);
            case PEDAGOGO:
                return new Pedagogo(id, email, senha, cargo);
            case ANALISTA:
                return new Analista(id, email, senha, cargo);
            case UNIDADE_ENSINO:
                return new UnidadeEnsino(id, email, senha, cargo);
            default:
                return null;
        }
    }
}

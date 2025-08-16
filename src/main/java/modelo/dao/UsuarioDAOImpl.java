package modelo.dao;

import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.usuario.*;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario recuperarUsuarioPeloEmailESenha(String email, String senha) {
        String sql = "SELECT id, cargo FROM usuario WHERE email = ? AND senha = ?";
        Usuario usuario = null;

        try(Connection conexao = FabricaConexao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String cargoStr = resultSet.getString("cargo");
                    Cargo cargo = Cargo.valueOf(cargoStr);

                    switch (cargo) {
                        case ALUNO:
                            usuario = new Aluno(id, cargo);
                            break;
                        case PEDAGOGO:
                            usuario = new Pedagogo(id, cargo);
                            break;
                        case ANALISTA:
                            usuario = new Analista(id, cargo);
                            break;
                        case UNIDADE_ENSINO:
                            usuario = new UnidadeEnsino(id, cargo);
                            break;
                        default:
                            return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

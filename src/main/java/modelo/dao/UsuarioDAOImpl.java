package modelo.dao;

import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.usuario.*;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Long cadastrarUsuario(Connection conexao, Usuario usuario) {
        String sql = "INSERT INTO usuario (email, senha, cargo, endereco_id) VALUES (?, ?, ?, ?)";
        Long idGerado = null;

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(3, usuario.getCargo().toString());
            preparedStatement.setLong(4, usuario.getEndereco().getId());

            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas == 0)
                throw new SQLException("Falha ao inserir usuário: nenhuma linha afetada.");

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (!resultSet.next())
                    throw new SQLException("Falha ao inserir usuário: não retornou ID.");
                idGerado = resultSet.getLong(1);
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

        return idGerado;
    }

    @Override
    public boolean atualizarUsuario(Connection conexao, Usuario usuario) {
        String sql = "UPDATE usuario SET email=?, senha=?, cargo=? WHERE id = ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(3, usuario.getCargo().toString());
            preparedStatement.setLong(4, usuario.getId());

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean deletarUsuarioPeloId(Connection conexao, Long id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public Usuario recuperarUsuarioPeloEmailESenha(Connection conexao, String email, String senha) {
        String sql = "SELECT id, cargo FROM usuario WHERE email = ? AND senha = ?";
        Usuario usuario = null;

        try(PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String cargoStr = resultSet.getString("cargo");
                    // TODO: Tratar possível exceção ao instanciar um cargo
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
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, e);;
            return null;
        }
        return usuario;
    }
}

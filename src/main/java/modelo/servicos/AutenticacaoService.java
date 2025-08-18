package modelo.servicos;

import modelo.dao.UsuarioDAO;
import modelo.entidade.usuario.Usuario;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutenticacaoService {
    private final UsuarioDAO usuarioDAO;

    public AutenticacaoService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario autenticar(String email, String senha) {
        try(Connection conexao = FabricaConexao.conectar()) {
            Usuario usuario = usuarioDAO.recuperarUsuarioPeloEmail(conexao, email);

            if (usuario == null)
                return null;

            return usuario.getSenha().equals(senha) ? usuario : null;
        } catch (SQLException e) {
            Logger.getLogger(AutenticacaoService.class.getName()).log(Level.SEVERE, "Usuário ou senha incorretos", e);
            return null;
        }
    }
}

package modelo.servicos;

import modelo.dao.AutenticacaoDAO;
import modelo.entidade.usuario.Usuario;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutenticacaoServiceImpl implements AutenticacaoService {
    private final AutenticacaoDAO autenticacaoDAO;
    private final FabricaConexao fabricaConexao;

    public AutenticacaoServiceImpl(AutenticacaoDAO autenticacaoDAO, FabricaConexao fabricaConexao) {
        this.autenticacaoDAO = autenticacaoDAO;
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public Usuario autenticarUsuario(String email, String senha) {
        try(Connection conexao = fabricaConexao.conectar()) {
            Usuario usuario = autenticacaoDAO.recuperarUsuarioPeloEmail(conexao, email);

            if (usuario == null)
                return null;

            boolean credenciaisCorretas = usuario.getSenha().equals(senha);

            if (!credenciaisCorretas)
                return null;

            return usuario;

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro de banco de dados durante o login para o usuário: " + email, e);
            return null;
        }
    }
}

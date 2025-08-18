package modelo.servicos;

import modelo.dao.PedagogoDAO;
import modelo.dao.EnderecoDAO;
import modelo.dao.UsuarioDAO;
import modelo.entidade.usuario.Pedagogo;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedagogoService {
    private final EnderecoDAO enderecoDAO;
    private final UsuarioDAO usuarioDAO;
    private final PedagogoDAO pedagogoDAO;

    public PedagogoService(EnderecoDAO enderecoDAO, UsuarioDAO usuarioDAO, PedagogoDAO pedagogoDAO) {
        this.enderecoDAO = enderecoDAO;
        this.usuarioDAO = usuarioDAO;
        this.pedagogoDAO = pedagogoDAO;
    }

    public boolean cadastrarPedagogo(Pedagogo pedagogo) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            Long enderecoId = enderecoDAO.cadastrarEndereco(conexao, pedagogo.getEndereco());
            if (enderecoId == null) {
                conexao.rollback();
                return false;
            }
            pedagogo.getEndereco().setId(enderecoId);

            Long usuarioId = usuarioDAO.cadastrarUsuario(conexao, pedagogo);
            if (usuarioId == null) {
                conexao.rollback();
                return false;
            }
            pedagogo.setId(usuarioId);

            boolean sucesso = pedagogoDAO.cadastrarPedagogo(conexao, pedagogo);
            if (!sucesso) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(PedagogoService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean atualizarPedagogo(Pedagogo pedagogo) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            boolean usuarioAtualizado = usuarioDAO.atualizarUsuario(conexao, pedagogo);
            boolean enderecoAtualizado = enderecoDAO.atualizarEndereco(conexao, pedagogo.getEndereco());
            boolean pedagogoAtualizada = pedagogoDAO.atualizarPedagogo(conexao, pedagogo);

            if (!usuarioAtualizado || !enderecoAtualizado || !pedagogoAtualizada) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(PedagogoService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deletarPedagogoPeloId(Long id) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            boolean pedagogoDeletada = usuarioDAO.deletarUsuarioPeloId(conexao, id);

            if (!pedagogoDeletada) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(PedagogoService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public Pedagogo recuperarPedagogoPeloId(Long id) {
        try (Connection conexao = FabricaConexao.conectar()) {
            return pedagogoDAO.recuperarPedagogoPeloId(conexao, id);
        } catch (SQLException e) {
            Logger.getLogger(PedagogoService.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    };
}

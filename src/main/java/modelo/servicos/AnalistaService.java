package modelo.servicos;

import modelo.dao.EnderecoDAO;
import modelo.dao.AnalistaDAO;
import modelo.dao.UsuarioDAO;
import modelo.entidade.usuario.Analista;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalistaService {
    private final EnderecoDAO enderecoDAO;
    private final UsuarioDAO usuarioDAO;
    private final AnalistaDAO analistaDAO;

    public AnalistaService(EnderecoDAO enderecoDAO, UsuarioDAO usuarioDAO, AnalistaDAO analistaDAO) {
        this.enderecoDAO = enderecoDAO;
        this.usuarioDAO = usuarioDAO;
        this.analistaDAO = analistaDAO;
    }

    public boolean cadastrarAnalista(Analista analista) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            Long enderecoId = enderecoDAO.cadastrarEndereco(conexao, analista.getEndereco());
            if (enderecoId == null) {
                conexao.rollback();
                return false;
            }
            analista.getEndereco().setId(enderecoId);

            Long usuarioId = usuarioDAO.cadastrarUsuario(conexao, analista);
            if (usuarioId == null) {
                conexao.rollback();
                return false;
            }
            analista.setId(usuarioId);

            boolean sucesso = analistaDAO.cadastrarAnalista(conexao, analista);
            if (!sucesso) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AnalistaService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean atualizarAnalista(Analista analista) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            boolean usuarioAtualizado = usuarioDAO.atualizarUsuario(conexao, analista);
            boolean enderecoAtualizado = enderecoDAO.atualizarEndereco(conexao, analista.getEndereco());
            boolean analistaAtualizada = analistaDAO.atualizarAnalista(conexao, analista);

            if (!usuarioAtualizado || !enderecoAtualizado || !analistaAtualizada) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AnalistaService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deletarAnalistaPeloId(Long id) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            boolean analistaDeletada = usuarioDAO.deletarUsuarioPeloId(conexao, id);

            if (!analistaDeletada) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AnalistaService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public Analista recuperarAnalistaPeloId(Long id) {
        try (Connection conexao = FabricaConexao.conectar()) {
            return analistaDAO.recuperarAnalistaPeloId(conexao, id);
        } catch (SQLException e) {
            Logger.getLogger(AnalistaService.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    };
}

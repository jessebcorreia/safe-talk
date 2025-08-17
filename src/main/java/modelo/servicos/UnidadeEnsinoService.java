package modelo.servicos;

import modelo.dao.EnderecoDAO;
import modelo.dao.UnidadeEnsinoDAO;
import modelo.dao.UsuarioDAO;
import modelo.entidade.usuario.UnidadeEnsino;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnidadeEnsinoService {
    private final EnderecoDAO enderecoDAO;
    private final UsuarioDAO usuarioDAO;
    private final UnidadeEnsinoDAO unidadeEnsinoDAO;

    public UnidadeEnsinoService(EnderecoDAO enderecoDAO, UsuarioDAO usuarioDAO, UnidadeEnsinoDAO unidadeEnsinoDAO) {
        this.enderecoDAO = enderecoDAO;
        this.usuarioDAO = usuarioDAO;
        this.unidadeEnsinoDAO = unidadeEnsinoDAO;
    }

    public boolean cadastrarUnidadeEnsino(UnidadeEnsino unidadeEnsino) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            // 1) Insere na tabela 'endereco' e atribui o valor de enderecoId ao atributo 'id' da classe Endereco
            Long enderecoId = enderecoDAO.cadastrarEndereco(conexao, unidadeEnsino.getEndereco());
            if (enderecoId == null) {
                conexao.rollback();
                return false;
            }
            unidadeEnsino.getEndereco().setId(enderecoId);

            // 2) Insere na tabela 'usuario' e atribui o valor de usuarioId ao atributo 'id' da classe Usuario
            Long usuarioId = usuarioDAO.cadastrarUsuario(conexao, unidadeEnsino);
            if (usuarioId == null) {
                conexao.rollback();
                return false;
            }
            unidadeEnsino.setId(usuarioId);

            // 3) Insere na tabela 'usuario_unidade_ensino'
            boolean sucesso = unidadeEnsinoDAO.cadastrarUnidadeEnsino(conexao, unidadeEnsino);
            if (!sucesso) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(UnidadeEnsinoService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean atualizarUnidadeEnsino(UnidadeEnsino unidadeEnsino) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            boolean usuarioAtualizado = usuarioDAO.atualizarUsuario(conexao, unidadeEnsino);
            boolean enderecoAtualizado = enderecoDAO.atualizarEndereco(conexao, unidadeEnsino.getEndereco());
            boolean unidadeEnsinoAtualizada = unidadeEnsinoDAO.atualizarUnidadeEnsino(conexao, unidadeEnsino);

            if (!usuarioAtualizado || !enderecoAtualizado || !unidadeEnsinoAtualizada) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(UnidadeEnsinoService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deletarUnidadeEnsinoPeloId(Long id) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            boolean unidadeEnsinoDeletada = usuarioDAO.deletarUsuarioPeloId(conexao, id);

            if (!unidadeEnsinoDeletada) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(UnidadeEnsinoService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}

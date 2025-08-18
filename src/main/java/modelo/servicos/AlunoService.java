package modelo.servicos;

import modelo.dao.AlunoDAO;
import modelo.dao.EnderecoDAO;
import modelo.dao.UsuarioDAO;
import modelo.entidade.usuario.Aluno;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlunoService {
    private final EnderecoDAO enderecoDAO;
    private final UsuarioDAO usuarioDAO;
    private final AlunoDAO alunoDAO;

    public AlunoService(EnderecoDAO enderecoDAO, UsuarioDAO usuarioDAO, AlunoDAO alunoDAO) {
        this.enderecoDAO = enderecoDAO;
        this.usuarioDAO = usuarioDAO;
        this.alunoDAO = alunoDAO;
    }

    public boolean cadastrarAluno(Aluno aluno) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            Long enderecoId = enderecoDAO.cadastrarEndereco(conexao, aluno.getEndereco());
            if (enderecoId == null) {
                conexao.rollback();
                return false;
            }
            aluno.getEndereco().setId(enderecoId);

            Long usuarioId = usuarioDAO.cadastrarUsuario(conexao, aluno);
            if (usuarioId == null) {
                conexao.rollback();
                return false;
            }
            aluno.setId(usuarioId);

            boolean sucesso = alunoDAO.cadastrarAluno(conexao, aluno);
            if (!sucesso) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AlunoService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean atualizarAluno(Aluno aluno) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            boolean usuarioAtualizado = usuarioDAO.atualizarUsuario(conexao, aluno);
            boolean enderecoAtualizado = enderecoDAO.atualizarEndereco(conexao, aluno.getEndereco());
            boolean alunoAtualizada = alunoDAO.atualizarAluno(conexao, aluno);

            if (!usuarioAtualizado || !enderecoAtualizado || !alunoAtualizada) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AlunoService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deletarAlunoPeloId(Long id) {
        try (Connection conexao = FabricaConexao.conectar()) {
            conexao.setAutoCommit(false);

            boolean alunoDeletada = usuarioDAO.deletarUsuarioPeloId(conexao, id);

            if (!alunoDeletada) {
                conexao.rollback();
                return false;
            }

            conexao.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(AlunoService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public Aluno recuperarAlunoPeloId(Long id) {
        try (Connection conexao = FabricaConexao.conectar()) {
            return alunoDAO.recuperarAlunoPeloId(conexao, id);
        } catch (SQLException e) {
            Logger.getLogger(AlunoService.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    };
}

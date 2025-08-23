package modelo.servicos;

import modelo.dao.AlunoDAO;
import modelo.entidade.usuario.Aluno;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlunoServiceImpl implements AlunoService {

    private final AlunoDAO alunoDAO;
    private final FabricaConexao fabricaConexao;

    public AlunoServiceImpl(AlunoDAO alunoDAO, FabricaConexao fabricaConexao) {
        this.alunoDAO = alunoDAO;
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public void cadastrarAluno(Aluno aluno) {
        try (Connection conexao = fabricaConexao.conectar()) {
            alunoDAO.cadastrarAluno(conexao, aluno);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao cadastrar aluno.", e);
            throw new RuntimeException("Não foi possível cadastrar o aluno.", e);
        }
    }

    @Override
    public void atualizarAluno(Aluno aluno) {
        try (Connection conexao = fabricaConexao.conectar()) {
            alunoDAO.atualizarAluno(conexao, aluno);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao atualizar aluno com ID: " + aluno.getId(), e);
            throw new RuntimeException("Não foi possível atualizar o aluno.", e);
        }
    }

    @Override
    public void deletarAlunoPeloId(Long id) {
        try (Connection conexao = fabricaConexao.conectar()) {
            alunoDAO.deletarAlunoPeloId(conexao, id);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao deletar aluno com ID: " + id, e);
            throw new RuntimeException("Não foi possível deletar o aluno.", e);
        }
    }

    @Override
    public Aluno recuperarAlunoPeloId(Long id) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return alunoDAO.recuperarAlunoPeloId(conexao, id);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar aluno com ID: " + id, e);
            return null;
        }
    }

    @Override
    public List<Aluno> recuperarAlunosPelaTurma(Long turmaId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return alunoDAO.recuperarAlunosPelaTurma(conexao, turmaId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de alunos.", e);
            return new ArrayList<>();
        }
    }

}

package modelo.dao;

import modelo.entidade.usuario.Aluno;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AlunoDAO {
    void cadastrarAluno(Connection conexao, Aluno aluno) throws SQLException;
    void atualizarAluno(Connection conexao, Aluno aluno) throws SQLException;
    void deletarAlunoPeloId(Connection conexao, Long id) throws SQLException;
    Aluno recuperarAlunoPeloId(Connection conexao, Long id) throws SQLException;
    List<Aluno> recuperarAlunosPelaTurma(Connection conexao, Long turmaId) throws SQLException;
}

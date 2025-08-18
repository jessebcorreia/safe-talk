package modelo.dao;

import modelo.entidade.usuario.Aluno;

import java.sql.Connection;

public interface AlunoDAO {
    boolean cadastrarAluno(Connection conexao, Aluno aluno);
    boolean atualizarAluno(Connection conexao, Aluno aluno);
    Aluno recuperarAlunoPeloId(Connection conexao, Long id);
}

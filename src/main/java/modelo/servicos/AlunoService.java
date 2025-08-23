package modelo.servicos;

import modelo.entidade.usuario.Aluno;

import java.util.List;

public interface AlunoService {
    void cadastrarAluno(Aluno aluno);
    void atualizarAluno(Aluno aluno);
    void deletarAlunoPeloId(Long id);
    Aluno recuperarAlunoPeloId(Long id);
    List<Aluno> recuperarAlunosPelaTurma(Long turmaId);
}

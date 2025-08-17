package modelo.entidade.usuario;

import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.instituicao.Curso;

import java.time.LocalDateTime;
import java.util.List;

public class Analista extends UsuarioPessoaFisica {
    private List<Curso> cursos;

    // No args constructor
    public Analista() {}

    // All args constructor
    public Analista(Long id, String email, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Cargo cargo, Endereco endereco, String nome, String sobrenome, String cpf, Sexo sexo, List<Curso> cursos) {
        super(id, email, senha, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo);
        setCursos(cursos);
    }

    // Login constructor
    public Analista(Long id, String email, String senha, Cargo cargo) {
        super(id, email, senha, cargo);
    }

    // Métodos de acesso
    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}

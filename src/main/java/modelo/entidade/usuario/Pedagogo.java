package modelo.entidade.usuario;

import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.instituicao.Turma;

import java.time.LocalDateTime;
import java.util.List;

public class Pedagogo extends UsuarioPessoaFisica {
    private List<Turma> turmas;

    // No args constructor
    public Pedagogo() {}

    // All args constructor
    public Pedagogo(Long id, String email, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Cargo cargo, Endereco endereco, String nome, String sobrenome, String cpf, Sexo sexo, List<Turma> turmas) {
        super(id, email, senha, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo);
        setTurmas(turmas);
    }

    // Login constructor
    public Pedagogo(Long id, String email, String senha, Cargo cargo) {
        super(id, email, senha, cargo);
    }

    // Métodos de acesso
    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }
}

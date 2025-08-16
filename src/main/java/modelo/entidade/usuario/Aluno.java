package modelo.entidade.usuario;

import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.Responsavel;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.instituicao.Turma;

import java.time.LocalDateTime;
import java.util.List;

public class Aluno extends UsuarioPessoaFisica {
    private List<Turma> turmas;
    private List<Responsavel> responsaveis;

    // No args constructor
    public Aluno() {}

    // All args constructor
    public Aluno(Long id, String email, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Cargo cargo, Endereco endereco, String nome, String sobrenome, String cpf, Sexo sexo, List<Turma> turmas, List<Responsavel> responsaveis) {
        super(id, email, senha, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo);
        setTurmas(turmas);
        setResponsaveis(responsaveis);
    }

    // Métodos de acesso
    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public List<Responsavel> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(List<Responsavel> responsaveis) {
        this.responsaveis = responsaveis;
    }
}

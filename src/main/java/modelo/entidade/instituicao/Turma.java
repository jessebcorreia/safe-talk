package modelo.entidade.instituicao;

import modelo.entidade.geral.enumeracoes.Periodo;
import modelo.entidade.usuario.Pedagogo;

import java.time.LocalDateTime;

public class Turma {
    private Long id;
    private String descricao;
    private Periodo periodo;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    private Curso curso;
    private Pedagogo pedagogo;

    // No args constructor
    public Turma() {}

    // All args constructor
    public Turma(Long id, String descricao, Periodo periodo, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Curso curso, Pedagogo pedagogo) {
        setId(id);
        setDescricao(descricao);
        setPeriodo(periodo);
        setCriadoEm(criadoEm);
        setAtualizadoEm(atualizadoEm);
        setCurso(curso);
        setPedagogo(pedagogo);
    }

    // Métodos de acesso
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Pedagogo getPedagogo() {
        return pedagogo;
    }

    public void setPedagogo(Pedagogo pedagogo) {
        this.pedagogo = pedagogo;
    }
}

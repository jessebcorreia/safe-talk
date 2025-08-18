package modelo.entidade.instituicao;

import modelo.entidade.usuario.Analista;
import modelo.entidade.usuario.UnidadeEnsino;

import java.time.LocalDateTime;

public class Curso {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    private UnidadeEnsino unidadeEnsino;
    private Analista analista;

    // No args constructor
    public Curso() {}

    // All args constructor
    public Curso(Long id, String nome, String descricao, LocalDateTime criadoEm, LocalDateTime atualizadoEm, UnidadeEnsino unidadeEnsino, Analista analista) {
        setId(id);
        setNome(nome);
        setDescricao(descricao);
        setCriadoEm(criadoEm);
        setAtualizadoEm(atualizadoEm);
        setUnidadeEnsino(unidadeEnsino);
        setAnalista(analista);
    }

    // Métodos de acesso
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public UnidadeEnsino getUnidadeEnsino() {
        return unidadeEnsino;
    }

    public void setUnidadeEnsino(UnidadeEnsino unidadeEnsino) {
        this.unidadeEnsino = unidadeEnsino;
    }

    public Analista getAnalista() {
        return analista;
    }

    public void setAnalista(Analista analista) {
        this.analista = analista;
    }

    // Outros métodos
    @Override
    public String toString() {
        Long id = getId();
        String nome = getNome();
        String descricao = getDescricao();
        LocalDateTime criadoEm = getCriadoEm();
        LocalDateTime atualizadoEm = getAtualizadoEm();
        UnidadeEnsino unidadeEnsino = getUnidadeEnsino();
        Analista analista = getAnalista();

        Long unidadeEnsinoId = unidadeEnsino != null ? unidadeEnsino.getId() : null;
        Long analistaId = analista != null ? analista.getId() : null;

        return "{\n" +
                "  id: " + (id != null ? id : "null") + ",\n" +
                "  nome: " + (nome != null ? "\"" + nome + "\"" : "null") + ",\n" +
                "  descricao: " + (descricao != null ? "\"" + descricao + "\"" : "null") + ",\n" +
                "  criadoEm: " + (criadoEm != null ? "\"" + criadoEm + "\"" : "null") + ",\n" +
                "  atualizadoEm: " + (atualizadoEm != null ? "\"" + atualizadoEm + "\"" : "null") + ",\n" +
                "  unidadeEnsinoId: " + (unidadeEnsinoId != null ? unidadeEnsinoId : "null") + ",\n" +
                "  analistaId: " + (analistaId != null ? analistaId : "null") + "\n" +
                "}";
    }
}

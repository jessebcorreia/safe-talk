package modelo.entidade.denuncia;

import modelo.entidade.denuncia.andamento.Andamento;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.TipoDenuncia;
import modelo.entidade.usuario.Aluno;
import modelo.entidade.usuario.Analista;
import modelo.entidade.usuario.Pedagogo;
import modelo.entidade.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Denuncia {
    private long id;
    private String titulo;
    private String conteudo;
    private TipoDenuncia tipoDenuncia;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private LocalDateTime encerradoEm;

    private Endereco localFato;
    private Usuario autor;
    private Analista analista;
    private List<Aluno> vitimas = new ArrayList<>();
    private List<Usuario> agressores = new ArrayList<>();
    private List<Pedagogo> pedagogos = new ArrayList<>();
    private List<Andamento> andamentos = new ArrayList<>();

    // No args constructor
    public Denuncia() {}

    // All args constructor
    public Denuncia(long id, String titulo, String conteudo, TipoDenuncia tipoDenuncia, LocalDateTime criadoEm, LocalDateTime atualizadoEm, LocalDateTime encerradoEm, Endereco localFato, Usuario autor, Analista analista, List<Aluno> vitimas, List<Usuario> agressores, List<Pedagogo> pedagogos, List<Andamento> andamentos) {
        setId(id);
        setTitulo(titulo);
        setConteudo(conteudo);
        setTipoDenuncia(tipoDenuncia);
        setCriadoEm(criadoEm);
        setAtualizadoEm(atualizadoEm);
        setEncerradoEm(encerradoEm);
        setLocalFato(localFato);
        setAutor(autor);
        setAnalista(analista);
        setVitimas(vitimas);
        setAgressores(agressores);
        setPedagogos(pedagogos);
        setAndamentos(andamentos);
    }

    // Métodos de acesso
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public TipoDenuncia getTipoDenuncia() {
        return tipoDenuncia;
    }

    public void setTipoDenuncia(TipoDenuncia tipoDenuncia) {
        this.tipoDenuncia = tipoDenuncia;
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

    public LocalDateTime getEncerradoEm() {
        return encerradoEm;
    }

    public void setEncerradoEm(LocalDateTime encerradoEm) {
        this.encerradoEm = encerradoEm;
    }

    public Endereco getLocalFato() {
        return localFato;
    }

    public void setLocalFato(Endereco localFato) {
        this.localFato = localFato;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Analista getAnalista() {
        return analista;
    }

    public void setAnalista(Analista analista) {
        this.analista = analista;
    }

    public List<Aluno> getVitimas() {
        return vitimas;
    }

    public void setVitimas(List<Aluno> vitimas) {
        this.vitimas = vitimas;
    }

    public List<Usuario> getAgressores() {
        return agressores;
    }

    public void setAgressores(List<Usuario> agressores) {
        this.agressores = agressores;
    }

    public List<Pedagogo> getPedagogos() {
        return pedagogos;
    }

    public void setPedagogos(List<Pedagogo> pedagogos) {
        this.pedagogos = pedagogos;
    }

    public List<Andamento> getAndamentos() {
        return andamentos;
    }

    public void setAndamentos(List<Andamento> andamentos) {
        this.andamentos = andamentos;
    }

    // Outros métodos
    @Override
    public String toString() {
        long id = getId();
        String titulo = getTitulo();
        String conteudo = getConteudo();
        TipoDenuncia tipoDenuncia = getTipoDenuncia();
        LocalDateTime criadoEm = getCriadoEm();
        LocalDateTime atualizadoEm = getAtualizadoEm();
        LocalDateTime encerradoEm = getEncerradoEm();

        Endereco localFato = getLocalFato();
        Usuario autor = getAutor();
        Analista analista = getAnalista();

        Long localFatoId = localFato != null ? localFato.getId() : null;
        Long autorId = autor != null ? autor.getId() : null;
        Long analistaId = analista != null ? analista.getId() : null;

        return "{\n" +
                "  id: " + id + ",\n" +
                "  titulo: " + (titulo != null ? "\"" + titulo + "\"" : "null") + ",\n" +
                "  conteudo: " + (conteudo != null ? "\"" + conteudo + "\"" : "null") + ",\n" +
                "  tipoDenuncia: " + (tipoDenuncia != null ? "\"" + tipoDenuncia + "\"" : "null") + ",\n" +
                "  criadoEm: " + (criadoEm != null ? "\"" + criadoEm + "\"" : "null") + ",\n" +
                "  atualizadoEm: " + (atualizadoEm != null ? "\"" + atualizadoEm + "\"" : "null") + ",\n" +
                "  encerradoEm: " + (encerradoEm != null ? "\"" + encerradoEm + "\"" : "null") + ",\n" +
                "  localFatoId: " + (localFatoId != null ? localFatoId : "null") + ",\n" +
                "  autorId: " + (autorId != null ? autorId : "null") + ",\n" +
                "  analistaId: " + (analistaId != null ? analistaId : "null") + "\n" +
                "}";
    }

}

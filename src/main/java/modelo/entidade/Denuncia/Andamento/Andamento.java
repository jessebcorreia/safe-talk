package modelo.entidade.Denuncia.Andamento;

import modelo.entidade.geral.enumeracoes.TipoAndamento;
import modelo.entidade.usuario.Usuario;

import java.time.LocalDateTime;

public abstract class Andamento {
    private Long id;
    private String conteudo;
    private LocalDateTime criadoEm;
    private TipoAndamento tipoAndamento;

    private Usuario autor;

    // No args constructor
    public Andamento() {}

    // All args constructor
    public Andamento(Long id, String conteudo, LocalDateTime criadoEm, TipoAndamento tipoAndamento, Usuario autor) {
        setId(id);
        setConteudo(conteudo);
        setCriadoEm(criadoEm);
        setTipoAndamento(tipoAndamento);
        setAutor(autor);
    }

    // Métodos de acesso
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public TipoAndamento getTipoAndamento() {
        return tipoAndamento;
    }

    public void setTipoAndamento(TipoAndamento tipoAndamento) {
        this.tipoAndamento = tipoAndamento;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
}

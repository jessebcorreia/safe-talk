package modelo.entidade.denuncia.Andamento;

import modelo.entidade.geral.enumeracoes.TipoAndamento;
import modelo.entidade.usuario.Usuario;

import java.time.LocalDateTime;

public class Analise extends Andamento {
    private LocalDateTime atualizadoEm;

    // No args constructor
    public Analise() {}

    // All args constructor
    public Analise(Long id, String conteudo, LocalDateTime criadoEm, TipoAndamento tipoAndamento, Usuario autor, LocalDateTime atualizadoEm) {
        super(id, conteudo, criadoEm, tipoAndamento, autor);
        setAtualizadoEm(atualizadoEm);
    }

    // Métodos de acesso
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}

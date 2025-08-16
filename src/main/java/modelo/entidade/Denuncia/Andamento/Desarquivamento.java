package modelo.entidade.Denuncia.Andamento;

import modelo.entidade.geral.enumeracoes.MotivoDesarquivamento;
import modelo.entidade.geral.enumeracoes.TipoAndamento;
import modelo.entidade.usuario.Usuario;

import java.time.LocalDateTime;

public class Desarquivamento extends Andamento {
    private MotivoDesarquivamento motivoDesarquivamento;

    // No args constructor
    public Desarquivamento() {}

    // All args constructor
    public Desarquivamento(Long id, String conteudo, LocalDateTime criadoEm, TipoAndamento tipoAndamento, Usuario autor, MotivoDesarquivamento motivoDesarquivamento) {
        super(id, conteudo, criadoEm, tipoAndamento, autor);
        setMotivoDesarquivamento(motivoDesarquivamento);
    }

    // Métodos de acesso
    public MotivoDesarquivamento getMotivoDesarquivamento() {
        return motivoDesarquivamento;
    }

    public void setMotivoDesarquivamento(MotivoDesarquivamento motivoDesarquivamento) {
        this.motivoDesarquivamento = motivoDesarquivamento;
    }
}

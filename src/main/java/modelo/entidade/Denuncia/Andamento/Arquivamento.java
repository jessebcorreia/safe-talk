package modelo.entidade.denuncia.Andamento;

import modelo.entidade.geral.enumeracoes.MotivoArquivamento;
import modelo.entidade.geral.enumeracoes.TipoAndamento;
import modelo.entidade.usuario.Usuario;

import java.time.LocalDateTime;

public class Arquivamento extends Andamento {
    private MotivoArquivamento motivoArquivamento;

    // No args constructor
    public Arquivamento() {}

    // All args constructor
    public Arquivamento(Long id, String conteudo, LocalDateTime criadoEm, TipoAndamento tipoAndamento, Usuario autor, MotivoArquivamento motivoArquivamento) {
        super(id, conteudo, criadoEm, tipoAndamento, autor);
        setMotivoArquivamento(motivoArquivamento);
    }

    // Métodos de acesso
    public MotivoArquivamento getMotivoArquivamento() {
        return motivoArquivamento;
    }

    public void setMotivoArquivamento(MotivoArquivamento motivoArquivamento) {
        this.motivoArquivamento = motivoArquivamento;
    }
}

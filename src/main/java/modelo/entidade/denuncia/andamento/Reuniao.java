package modelo.entidade.denuncia.andamento;

import modelo.entidade.geral.enumeracoes.TipoAndamento;
import modelo.entidade.usuario.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reuniao extends Andamento {
    private LocalDate dataReuniao;
    private LocalTime horaReuniao;
    private LocalDateTime atualizadoEm;

    // No args constructor
    public Reuniao() {}

    // All args constructor
    public Reuniao(Long id, String conteudo, LocalDateTime criadoEm, TipoAndamento tipoAndamento, Usuario autor, LocalDate dataReuniao, LocalTime horaReuniao, LocalDateTime atualizadoEm) {
        super(id, conteudo, criadoEm, tipoAndamento, autor);
        setDataReuniao(dataReuniao);
        setHoraReuniao(horaReuniao);
        setAtualizadoEm(atualizadoEm);
    }

    // Métodos de acesso
    public LocalDate getDataReuniao() {
        return dataReuniao;
    }

    public void setDataReuniao(LocalDate dataReuniao) {
        this.dataReuniao = dataReuniao;
    }

    public LocalTime getHoraReuniao() {
        return horaReuniao;
    }

    public void setHoraReuniao(LocalTime horaReuniao) {
        this.horaReuniao = horaReuniao;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}

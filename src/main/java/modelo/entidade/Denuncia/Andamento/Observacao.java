package modelo.entidade.denuncia.Andamento;

import modelo.entidade.geral.enumeracoes.TipoAndamento;
import modelo.entidade.usuario.Usuario;

import java.time.LocalDateTime;

public class Observacao extends Andamento {

    // No args constructor
    public Observacao() {}

    // All args constructor
    public Observacao(Long id, String conteudo, LocalDateTime criadoEm, TipoAndamento tipoAndamento, Usuario autor) {
        super(id, conteudo, criadoEm, tipoAndamento, autor);
    }
}

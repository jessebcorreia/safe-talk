package modelo.entidade.Denuncia.Andamento;

import modelo.entidade.geral.enumeracoes.TipoAndamento;
import modelo.entidade.usuario.Usuario;

import java.time.LocalDateTime;

public class InformacaoComplementar extends Andamento {

    // No args constructor
    public InformacaoComplementar() {}

    // All args constructor

    public InformacaoComplementar(Long id, String conteudo, LocalDateTime criadoEm, TipoAndamento tipoAndamento, Usuario autor) {
        super(id, conteudo, criadoEm, tipoAndamento, autor);
    }
}

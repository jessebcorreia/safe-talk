package modelo.entidade.Denuncia.Andamento;

import modelo.entidade.geral.enumeracoes.ResultadoParecerFinal;
import modelo.entidade.geral.enumeracoes.TipoAndamento;
import modelo.entidade.usuario.Usuario;

import java.time.LocalDateTime;

public class ParecerFinal extends Andamento {
    private ResultadoParecerFinal resultadoParecerFinal;

    // No args constructor
    public ParecerFinal() {}

    // All args constructor
    public ParecerFinal(Long id, String conteudo, LocalDateTime criadoEm, TipoAndamento tipoAndamento, Usuario autor, ResultadoParecerFinal resultadoParecerFinal) {
        super(id, conteudo, criadoEm, tipoAndamento, autor);
        setResultadoParecerFinal(resultadoParecerFinal);
    }

    // Métodos de acesso
    public ResultadoParecerFinal getResultadoParecerFinal() {
        return resultadoParecerFinal;
    }

    public void setResultadoParecerFinal(ResultadoParecerFinal resultadoParecerFinal) {
        this.resultadoParecerFinal = resultadoParecerFinal;
    }
}

package modelo.servicos;

import modelo.entidade.usuario.UnidadeEnsino;

import java.util.List;

public interface UnidadeEnsinoService {
    void cadastrarUnidadeEnsino(UnidadeEnsino unidadeEnsino);
    void atualizarUnidadeEnsino(UnidadeEnsino unidadeEnsino);
    void deletarUnidadeEnsinoPeloId(Long id);
    UnidadeEnsino recuperarUnidadeEnsinoPeloId(Long id);
    List<UnidadeEnsino> recuperarUnidadesDeEnsino();
}

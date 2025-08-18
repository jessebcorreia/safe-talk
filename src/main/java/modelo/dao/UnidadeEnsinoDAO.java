package modelo.dao;

import modelo.entidade.usuario.UnidadeEnsino;

import java.sql.Connection;

public interface UnidadeEnsinoDAO {
    boolean cadastrarUnidadeEnsino(Connection conexao, UnidadeEnsino unidadeEnsino);
    boolean atualizarUnidadeEnsino(Connection conexao, UnidadeEnsino unidadeEnsino);
    UnidadeEnsino recuperarUnidadeEnsinoPeloId(Connection conexao, Long id);
}

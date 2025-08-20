package modelo.dao;

import modelo.entidade.usuario.UnidadeEnsino;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UnidadeEnsinoDAO {
    void cadastrarUnidadeEnsino(Connection conexao, UnidadeEnsino unidadeEnsino) throws SQLException;
    void atualizarUnidadeEnsino(Connection conexao, UnidadeEnsino unidadeEnsino) throws SQLException;
    void deletarUnidadeEnsinoPeloId(Connection conexao, Long id) throws SQLException;
    UnidadeEnsino recuperarUnidadeEnsinoPeloId(Connection conexao, Long id) throws SQLException;
    List<UnidadeEnsino> recuperarUnidadesDeEnsino(Connection conexao) throws SQLException;
}

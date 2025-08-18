package modelo.dao;

import modelo.entidade.usuario.Analista;

import java.sql.Connection;

public interface AnalistaDAO {
    boolean cadastrarAnalista(Connection conexao, Analista unidadeEnsino);
    boolean atualizarAnalista(Connection conexao, Analista unidadeEnsino);
    Analista recuperarAnalistaPeloId(Connection conexao, Long id);
}

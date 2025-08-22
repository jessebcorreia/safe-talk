package modelo.dao;

import modelo.entidade.usuario.Analista;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AnalistaDAO {
    void cadastrarAnalista(Connection conexao, Analista analista) throws SQLException;
    void atualizarAnalista(Connection conexao, Analista analista) throws SQLException;
    void deletarAnalistaPeloId(Connection conexao, Long id) throws SQLException;
    Analista recuperarAnalistaPeloId(Connection conexao, Long id) throws SQLException;
    List<Analista> recuperarAnalistasPeloCurso(Connection conexao, Long cursoId) throws SQLException;
}

package modelo.dao;

import modelo.entidade.usuario.Pedagogo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PedagogoDAO {
    void cadastrarPedagogo(Connection conexao, Pedagogo pedagogo) throws SQLException;
    void atualizarPedagogo(Connection conexao, Pedagogo pedagogo) throws SQLException;
    void deletarPedagogoPeloId(Connection conexao, Long id) throws SQLException;
    Pedagogo recuperarPedagogoPeloId(Connection conexao, Long id) throws SQLException;
    List<Pedagogo> recuperarPedagogosPelaTurma(Connection conexao, Long turmaId) throws SQLException;
}

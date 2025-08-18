package modelo.dao;

import modelo.entidade.usuario.Pedagogo;

import java.sql.Connection;

public interface PedagogoDAO {
    boolean cadastrarPedagogo(Connection conexao, Pedagogo pedagogo);
    boolean atualizarPedagogo(Connection conexao, Pedagogo pedagogo);
    Pedagogo recuperarPedagogoPeloId(Connection conexao, Long id);
}

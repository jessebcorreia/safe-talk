package modelo.fabrica.conexao;

import java.sql.Connection;
import java.sql.SQLException;

public interface FabricaConexao {
    Connection conectar() throws SQLException;
}

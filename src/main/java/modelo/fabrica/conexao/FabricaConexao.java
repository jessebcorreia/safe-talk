package modelo.fabrica.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String DATABASE = "safe_talk";
    public static final String USER = "root";
    public static final String PASSWORD = "root";

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado.", e);
        }

        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE +
                "?useSSL=false" +
                "&allowPublicKeyRetrieval=true" +
                "&serverTimezone=America/Sao_Paulo" +
                "&useUnicode=true&characterEncoding=UTF-8";
        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}

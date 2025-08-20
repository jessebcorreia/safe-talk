package modelo.fabrica.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexaoImpl implements FabricaConexao {

    private final String host;
    private final String port;
    private final String database;
    private final String user;
    private final String password;

    public FabricaConexaoImpl(String host, String port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public FabricaConexaoImpl() {
        this("localhost", "3306", "safe_talk", "root", "root");
    }

    @Override
    public Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado.", e);
        }

        String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database +
                "?useSSL=false" +
                "&allowPublicKeyRetrieval=true" +
                "&serverTimezone=America/Sao_Paulo" +
                "&useUnicode=true&characterEncoding=UTF-8";

        boolean usuarioESenhaInformados = this.user != null && !this.user.isEmpty() && this.password != null && !this.password.isEmpty();
        if (usuarioESenhaInformados)
            return DriverManager.getConnection(url, this.user, this.password);

        return DriverManager.getConnection(url);
    }
}

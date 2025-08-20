package modelo.dao;

import modelo.entidade.usuario.Usuario;

import java.sql.Connection;
import java.sql.SQLException;

public interface AutenticacaoDAO {
    Usuario recuperarUsuarioPeloEmail(Connection conexao, String email) throws SQLException;
}

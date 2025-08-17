package modelo.dao;

import modelo.entidade.usuario.Usuario;

import java.sql.Connection;

public interface UsuarioDAO {
    Long cadastrarUsuario(Connection conexao, Usuario usuario);
    Usuario recuperarUsuarioPeloEmailESenha(Connection conexao, String email, String senha);
}

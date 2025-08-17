package modelo.dao;

import modelo.entidade.usuario.Usuario;

import java.sql.Connection;

public interface UsuarioDAO {
    Long cadastrarUsuario(Connection conexao, Usuario usuario);
    boolean atualizarUsuario(Connection conexao, Usuario usuario);
    boolean deletarUsuarioPeloId(Connection conexao, Long id);
    Usuario recuperarUsuarioPeloEmailESenha(Connection conexao, String email, String senha);
}

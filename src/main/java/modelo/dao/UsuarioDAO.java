package modelo.dao;

import modelo.entidade.usuario.Usuario;

public interface UsuarioDAO {
    Usuario recuperarUsuarioPeloEmailESenha(String email, String senha);
}

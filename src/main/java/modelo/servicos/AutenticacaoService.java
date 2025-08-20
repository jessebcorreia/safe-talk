package modelo.servicos;

import modelo.entidade.usuario.Usuario;

public interface AutenticacaoService {
    Usuario autenticarUsuario(String email, String senha);
}

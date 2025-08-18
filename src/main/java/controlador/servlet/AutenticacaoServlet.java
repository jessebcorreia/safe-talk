package controlador.servlet;

import modelo.dao.UsuarioDAO;
import modelo.dao.UsuarioDAOImpl;
import modelo.entidade.usuario.Usuario;
import modelo.servicos.AutenticacaoService;
import utils.AtributosSessao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login/*")
public class AutenticacaoServlet extends HttpServlet {
    private AutenticacaoService autenticacaoService;

    @Override
    public void init() throws ServletException {
        super.init();

        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        this.autenticacaoService = new AutenticacaoService(usuarioDAO);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String endpoint = request.getPathInfo();
        if (endpoint == null) endpoint = "/";

        // Remove a barra "/" do final do endpoint, se existir
        if (endpoint.length() > 1 && endpoint.endsWith("/")) {
            endpoint = endpoint.substring(0, endpoint.length() - 1);
        }

        switch (endpoint) {
            case "/iniciar-sessao":
                iniciarSessao(request, response);
                break;
            case "/encerrar-sessao":
                encerrarSessao(request, response);
                break;
            default:
                break;
        }
    }

    private void iniciarSessao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (email == null || senha == null)
            return;

        Usuario usuario = autenticacaoService.autenticar(email, senha);
        if (usuario == null)
            return;

        HttpSession sessaoAntiga = request.getSession(false);
        if (sessaoAntiga != null)
            sessaoAntiga.invalidate();

        HttpSession sessao = request.getSession(true);
        sessao.setAttribute(AtributosSessao.USUARIO_ID, usuario.getId());
        sessao.setAttribute(AtributosSessao.USUARIO_EMAIL, usuario.getEmail());
        sessao.setAttribute(AtributosSessao.USUARIO_CARGO, usuario.getCargo());

        sessao.setMaxInactiveInterval(30 * 60); // 30 * 60 = 1800 segundos | 30 minutos
    }

    private void encerrarSessao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession(false);
        if (sessao != null)
            sessao.invalidate();
    }
}

package controlador.servlet;

import modelo.dao.AutenticacaoDAO;
import modelo.dao.AutenticacaoDAOImpl;
import modelo.entidade.usuario.Usuario;
import modelo.fabrica.conexao.FabricaConexao;
import modelo.fabrica.conexao.FabricaConexaoImpl;
import modelo.servicos.AutenticacaoService;
import modelo.servicos.AutenticacaoServiceImpl;
import utils.AtributosSessao;

import javax.servlet.RequestDispatcher;
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

        AutenticacaoDAO autenticacaoDAO = new AutenticacaoDAOImpl();
        FabricaConexao fabricaConexao = new FabricaConexaoImpl();

        this.autenticacaoService = new AutenticacaoServiceImpl(autenticacaoDAO, fabricaConexao);
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
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String endpoint = request.getPathInfo();
        if (endpoint == null) endpoint = "/";

        // Remove a barra "/" do final do endpoint, se existir
        if (endpoint.length() > 1 && endpoint.endsWith("/")) {
            endpoint = endpoint.substring(0, endpoint.length() - 1);
        }

        switch (endpoint) {
            case "/":
                mostrarTelaLogin(request, response);
                break;
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

    private void mostrarTelaLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/visualizacao/home/login.html");
        dispatcher.forward(request, response);
    }

    private void iniciarSessao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = autenticacaoService.autenticarUsuario(email, senha);

        if (usuario == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.setAttribute("erroAutenticacao", "E-mail ou senha incorretos.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/visualizacao/home/login.html");
            dispatcher.forward(request, response);
            return;
        }

        HttpSession sessaoAntiga = request.getSession(false);
        if (sessaoAntiga != null)
            sessaoAntiga.invalidate();

        HttpSession sessao = request.getSession(true);
        sessao.setAttribute(AtributosSessao.USUARIO_ID, usuario.getId());
        sessao.setAttribute(AtributosSessao.USUARIO_EMAIL, usuario.getEmail());
        sessao.setAttribute(AtributosSessao.USUARIO_CARGO, usuario.getCargo());

        sessao.setMaxInactiveInterval(30 * 60); // 30 * 60 = 1800 segundos | 30 minutos

        response.sendRedirect(request.getContextPath() + "/usuario/unidade-ensino/formulario-cadastro");

        System.out.println("Usuário logado");
    }

    private void encerrarSessao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession(false);

        if (sessao != null)
            sessao.invalidate();

        response.sendRedirect(request.getContextPath() + "/login");
    }
}

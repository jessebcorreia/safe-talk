package controlador.servlet;

import modelo.dao.*;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.usuario.Pedagogo;
import modelo.servicos.PedagogoService;
import utils.ConverterDados;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/usuario/pedagogo/*")
public class PedagogoServlet extends HttpServlet {
    private PedagogoService pedagogoService;

    @Override
    public void init() throws ServletException {
        super.init();

        EnderecoDAO enderecoDAO = new EnderecoDAOImpl();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        PedagogoDAOImpl pedagogoDAO = new PedagogoDAOImpl();

        this.pedagogoService = new PedagogoService(enderecoDAO, usuarioDAO, pedagogoDAO);
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
            case "/":
                System.out.println("/");
                break;
            case "/cadastrar":
                cadastrarPedagogo(request, response);
                break;
            case "/atualizar":
                atualizarPedagogo(request, response);
                break;
            case "/deletar":
                deletarPedagogo(request, response);
                break;
            case "/recuperar":
                recuperarPedagogo(request, response);
                break;
            case "/listar":
                System.out.println("/listar");
                break;
            default:
                System.out.println("Rota não mapeada");
                // Se o endpoint for nulo ou não estiver mapeado, cai aqui
        }
    }

    private void cadastrarPedagogo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Pedagogo pedagogo = criarPedagogoMapeandoRequisicao(request);
        boolean sucesso = pedagogoService.cadastrarPedagogo(pedagogo);
        if (!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao cadastrar pedagogo");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/login");
    }

    private void atualizarPedagogo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Pedagogo pedagogo = criarPedagogoMapeandoRequisicao(request);

        boolean sucesso = pedagogoService.atualizarPedagogo(pedagogo);
        if(!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao atualizar pedagogo");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/usuario/pedagogo");
    }

    private void deletarPedagogo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long idUsuario = ConverterDados.stringParaLong(request.getParameter("id_usuario"));

        boolean sucesso = pedagogoService.deletarPedagogoPeloId(idUsuario);
        if (!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao deletar usuário");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/usuario");
    }

    private void recuperarPedagogo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idUsuario = ConverterDados.stringParaLong(request.getParameter("id_usuario"));
        if (idUsuario == null){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "O id não foi informado");
            return;
        }

        Pedagogo pedagogo = pedagogoService.recuperarPedagogoPeloId(idUsuario);
        if (pedagogo == null){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "O pedagogo não foi encontrado");
            return;
        }

        System.out.println(pedagogo);
        request.setAttribute("pedagogo", pedagogo);
        request.getRequestDispatcher("/usuario/pedagogo-detalhe.jsp").forward(request, response);
    }

    private Pedagogo criarPedagogoMapeandoRequisicao(HttpServletRequest request) {
        String logradouro = request.getParameter("logradouro");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String cep = request.getParameter("cep");
        String pais = request.getParameter("pais");

        Long idEndereco = ConverterDados.stringParaLong(request.getParameter("id_endereco"));

        Endereco endereco = new Endereco(idEndereco, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");

        String sexoStr = request.getParameter("sexo");
        Sexo sexo = Sexo.valueOf(sexoStr);

        String cargoStr = request.getParameter("cargo");
        Cargo cargo = Cargo.valueOf(cargoStr);

        Long idUsuario = ConverterDados.stringParaLong(request.getParameter("id_usuario"));
        LocalDateTime criadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("criado_em"));
        LocalDateTime atualizadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("atualizado_em"));

        return new Pedagogo(idUsuario, email, senha, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo, null);
    }
}

package controlador.servlet;

import modelo.dao.*;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.usuario.Analista;
import modelo.servicos.AnalistaService;
import utils.ConverterDados;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/usuario/analista/*")
public class AnalistaServlet extends HttpServlet {
    private AnalistaService analistaService;

    @Override
    public void init() throws ServletException {
        super.init();

        EnderecoDAO enderecoDAO = new EnderecoDAOImpl();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        AnalistaDAOImpl analistaDAO = new AnalistaDAOImpl();

        this.analistaService = new AnalistaService(enderecoDAO, usuarioDAO, analistaDAO);
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
                cadastrarAnalista(request, response);
                break;
            case "/atualizar":
                atualizarAnalista(request, response);
                break;
            case "/deletar":
                deletarAnalista(request, response);
                break;
            case "/recuperar":
                recuperarAnalista(request, response);
                break;
            case "/listar":
                System.out.println("/listar");
                break;
            default:
                System.out.println("Rota não mapeada");
                // Se o endpoint for nulo ou não estiver mapeado, cai aqui
        }
    }

    private void cadastrarAnalista(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Analista analista = criarAnalistaMapeandoRequisicao(request);
        boolean sucesso = analistaService.cadastrarAnalista(analista);
        if (!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao cadastrar analista");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/login");
    }

    private void atualizarAnalista(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Analista analista = criarAnalistaMapeandoRequisicao(request);

        boolean sucesso = analistaService.atualizarAnalista(analista);
        if(!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao atualizar analista");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/usuario/analista");
    }

    private void deletarAnalista(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long idUsuario = ConverterDados.stringParaLong(request.getParameter("id_usuario"));

        boolean sucesso = analistaService.deletarAnalistaPeloId(idUsuario);
        if (!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao deletar usuário");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/usuario");
    }

    private void recuperarAnalista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idUsuario = ConverterDados.stringParaLong(request.getParameter("id_usuario"));
        if (idUsuario == null){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "O id não foi informado");
            return;
        }

        Analista analista = analistaService.recuperarAnalistaPeloId(idUsuario);
        if (analista == null){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "O analista não foi encontrado");
            return;
        }

        System.out.println(analista);
        request.setAttribute("analista", analista);
        request.getRequestDispatcher("/usuario/analista-detalhe.jsp").forward(request, response);
    }

    private Analista criarAnalistaMapeandoRequisicao(HttpServletRequest request) {
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

        return new Analista(idUsuario, email, senha, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo, null);
    }
}

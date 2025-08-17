package controlador.servlet;

import utils.ConverterDados;
import modelo.dao.*;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.usuario.UnidadeEnsino;
import modelo.servicos.UnidadeEnsinoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/usuario/unidade-ensino/*")
public class UnidadeEnsinoServlet extends HttpServlet {
    private UnidadeEnsinoService unidadeEnsinoService;

    @Override
    public void init() throws ServletException {
        super.init();

        EnderecoDAO enderecoDAO = new EnderecoDAOImpl();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        UnidadeEnsinoDAOImpl unidadeEnsinoDAO = new UnidadeEnsinoDAOImpl();

        this.unidadeEnsinoService = new UnidadeEnsinoService(enderecoDAO, usuarioDAO, unidadeEnsinoDAO);
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
                cadastrarUnidadeEnsino(request, response);
                break;
            case "/atualizar":
                atualizarUnidadeEnsino(request, response);
                break;
            case "/deletar":
                deletarUnidadeEnsino(request, response);
                break;
            case "/recuperar":
                recuperarUnidadeEnsino(request, response);
                break;
            case "/listar":
                System.out.println("/listar");
                break;
            default:
                System.out.println("Rota não mapeada");
                // Se o endpoint for nulo ou não estiver mapeado, cai aqui
        }
    }

    private void cadastrarUnidadeEnsino(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        UnidadeEnsino unidadeEnsino = criarUnidadeEnsinoMapeandoRequisicao(request);
        boolean sucesso = unidadeEnsinoService.cadastrarUnidadeEnsino(unidadeEnsino);
        if (!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao cadastrar unidade");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/login");
    }

    private void atualizarUnidadeEnsino(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        UnidadeEnsino unidadeEnsino = criarUnidadeEnsinoMapeandoRequisicao(request);

        boolean sucesso = unidadeEnsinoService.atualizarUnidadeEnsino(unidadeEnsino);
        if(!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao atualizar unidade");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/usuario/unidade-ensino");
    }

    private void deletarUnidadeEnsino(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long idUsuario = ConverterDados.stringParaLong(request.getParameter("id_usuario"));

        boolean sucesso = unidadeEnsinoService.deletarUnidadeEnsinoPeloId(idUsuario);
        if (!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao deletar usuário");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/usuario");
    }

    private void recuperarUnidadeEnsino(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idUsuario = ConverterDados.stringParaLong(request.getParameter("id_usuario"));
        if (idUsuario == null){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "O id não foi informado");
            return;
        }

        UnidadeEnsino unidadeEnsino = unidadeEnsinoService.recuperarUnidadeEnsinoPeloId(idUsuario);
        if (unidadeEnsino == null){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "A unidade de ensino não foi encontrada");
            return;
        }

        request.setAttribute("unidade-ensino", unidadeEnsino);
        request.getRequestDispatcher("/usuario/unidade-ensino-detalhe.jsp").forward(request, response);
    }

    private UnidadeEnsino criarUnidadeEnsinoMapeandoRequisicao(HttpServletRequest request) {
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
        String nomeFantasia = request.getParameter("nome_fantasia");
        String razaoSocial = request.getParameter("razao-social");
        String cnpj = request.getParameter("cnpj");
        String descricao = request.getParameter("descricao");

        String cargoStr = request.getParameter("cargo");
        Cargo cargo = Cargo.valueOf(cargoStr);

        Long idUsuario = ConverterDados.stringParaLong(request.getParameter("id_usuario"));
        LocalDateTime criadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("criado_em"));
        LocalDateTime atualizadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("atualizado_em"));

        return new UnidadeEnsino(idUsuario, email, senha, criadoEm, atualizadoEm, cargo, endereco, nomeFantasia, razaoSocial, cnpj, descricao);
    }
}

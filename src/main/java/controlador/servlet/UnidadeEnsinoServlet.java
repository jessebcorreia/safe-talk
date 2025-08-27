package controlador.servlet;

import modelo.fabrica.conexao.FabricaConexao;
import modelo.fabrica.conexao.FabricaConexaoImpl;
import utils.ConverterDados;
import modelo.dao.*;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.usuario.UnidadeEnsino;
import modelo.servicos.UnidadeEnsinoServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/usuario/unidade-ensino/*")
public class UnidadeEnsinoServlet extends HttpServlet {
    private UnidadeEnsinoServiceImpl unidadeEnsinoService;

    @Override
    public void init() throws ServletException {
        super.init();

        UnidadeEnsinoDAO unidadeEnsinoDAO = new UnidadeEnsinoDAOImpl();
        FabricaConexao fabricaConexao = new FabricaConexaoImpl();

        this.unidadeEnsinoService = new UnidadeEnsinoServiceImpl(unidadeEnsinoDAO, fabricaConexao);
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
        if (endpoint == null)
            endpoint = "/";

        // Remove a barra "/" do final do endpoint, se existir
        if (endpoint.length() > 1 && endpoint.endsWith("/")) {
            endpoint = endpoint.substring(0, endpoint.length() - 1);
        }

        switch (endpoint) {
            // Rotas para exibir as telas (exemplo: usuario/unidade-ensino/cadastrar - vai
            // mostrar a tela de cadastro)
            case "/":
                mostrarTelaInicial(request, response);
                break;
            case "/cadastrar":
                mostrarTelaCadastrar(request, response);
                break;
            case "/exibir":
                mostrarTelaExibir(request, response);
                break;
            case "/listar":
                listarUnidadesEnsino(request, response);
                return;

            // Rotas para executar ações (exemplo: o formulário tem a action
            // /usuario/unidade-ensino/exec-cadastrar - isso vai enviar o formulário para
            // essa rota)
            case "/exec-cadastrar":
                cadastrarUnidadeEnsino(request, response);
                break;
            case "/exec-atualizar":
                atualizarUnidadeEnsino(request, response);
                break;
            case "/exec-deletar":
                deletarUnidadeEnsino(request, response);
                break;
            case "/exec-recuperar":
                recuperarUnidadeEnsino(request, response);
                break;
            case "/exec-listar":
                listarUnidadesEnsino(request, response);
                break;
            default:
                mostrarTelaErro(request, response);
        }
    }

    // Métodos para exibir as telas
    private void mostrarTelaInicial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/unidade-ensino/index.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaCadastrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request
                .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/cadastrar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaExibir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/unidade-ensino/exibir.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaListar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/unidade-ensino/listar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaErro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/inicio/erro-404.jsp");
        dispatcher.forward(request, response);
    }

    // Métodos para executar ações
    private void cadastrarUnidadeEnsino(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Tabela de endereço
        String logradouro = request.getParameter("logradouro");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String cep = request.getParameter("cep");
        String pais = request.getParameter("pais");

        // Cria uma instância da classe Endereco (endereco vira um objeto que vai ser
        // passado dentro do construtor da UnidadeEnsino)
        Endereco endereco = new Endereco(null, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

        // Tabela de usuário
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Cargo cargo = Cargo.UNIDADE_ENSINO;

        // Tabela de Unidade de Ensino
        String nomeFantasia = request.getParameter("nome_fantasia");
        String razaoSocial = request.getParameter("razao_social");
        String cnpj = request.getParameter("cnpj");
        String descricao = request.getParameter("descricao");

        // Cria uma instância da classe UnidadeEnsino
        UnidadeEnsino unidadeEnsino = new UnidadeEnsino(null, email, senha, null, null, cargo, endereco, nomeFantasia,
                razaoSocial, cnpj, descricao);

        try {
            // Tenta cadastrar a unidade de ensino, com as informações que são passadas
            // acima (como é um cadastro, os campos de "id" e "criado_em" são preenchidos
            // automaticamente (pode lançar exceção)
            unidadeEnsinoService.cadastrarUnidadeEnsino(unidadeEnsino);
            response.sendRedirect(request.getContextPath() + "/login");

        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar unidade de ensino: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível realizar o cadastro.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/inicio/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void atualizarUnidadeEnsino(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID da unidade de ensino não informado ou inválido.");
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Tabela de endereço:
        Long enderecoId = ConverterDados.stringParaLong(request.getParameter("endereco_id")); // Recupera o id para
                                                                                              // localizar a linha na
                                                                                              // tabela
        String logradouro = request.getParameter("logradouro");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String cep = request.getParameter("cep");
        String pais = request.getParameter("pais");

        // Cria uma instância da classe Endereco (endereco vira um objeto que vai ser
        // passado dentro do construtor da UnidadeEnsino)
        Endereco endereco = new Endereco(enderecoId, logradouro, numero, complemento, bairro, cidade, estado, cep,
                pais);

        // Tabela de usuário (id do usuário recuperado no início do código)
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Cargo cargo = Cargo.valueOf(request.getParameter("cargo"));
        LocalDateTime criadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("criado_em"));
        LocalDateTime atualizadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("atualizado_em"));

        // Tabela de Unidade de Ensino (usa o usuario_id para localizar a linha na
        // tabela):
        String nomeFantasia = request.getParameter("nome_fantasia");
        String razaoSocial = request.getParameter("razao_social");
        String cnpj = request.getParameter("cnpj");
        String descricao = request.getParameter("descricao");

        // Cria uma instância da classe UnidadeEnsino
        UnidadeEnsino unidadeEnsino = new UnidadeEnsino(usuarioId, email, senha, criadoEm, atualizadoEm, cargo,
                endereco, nomeFantasia, razaoSocial, cnpj, descricao);

        try {
            // Tenta atualizar a unidade de ensino, com as informações que são passadas
            // acima (como é uma atualização, todos os campos foram informados)
            unidadeEnsinoService.atualizarUnidadeEnsino(unidadeEnsino);
            response.sendRedirect(request.getContextPath() + "/usuario/unidade-ensino");

        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar unidade de ensino: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível atualizar a unidade de ensino.");

            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deletarUnidadeEnsino(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID da unidade de ensino não fornecido para exclusão.");
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Tenta atualizar a unidade de ensino pelo id do usuário (deleta os registros
            // nas outras tabelas em cascata)
            unidadeEnsinoService.deletarUnidadeEnsinoPeloId(usuarioId);
            response.sendRedirect(request.getContextPath() + "/usuario/unidade-ensino");

        } catch (RuntimeException e) {
            System.err.println("Erro ao deletar unidade de ensino: " + e.getMessage());
            request.setAttribute("mensagemErro",
                    "Não foi possível deletar a unidade de ensino. Tente novamente mais tarde.");
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void recuperarUnidadeEnsino(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID da unidade de ensino não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Tenta buscar a unidade de ensino pelo ID
            UnidadeEnsino unidadeEnsino = unidadeEnsinoService.recuperarUnidadeEnsinoPeloId(usuarioId);
            System.out.println(unidadeEnsino);

            if (unidadeEnsino == null) {
                request.setAttribute("mensagemErro", "Unidade de ensino não encontrada para o ID: " + usuarioId);
                RequestDispatcher dispatcher = request
                        .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/index.jsp");
                dispatcher.forward(request, response);
                return;
            }

            request.setAttribute("unidadeEnsino", unidadeEnsino);
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao recuperar unidade de ensino: " + e.getMessage());
            request.setAttribute("mensagemErro", "Erro ao tentar recuperar a unidade de ensino.");
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listarUnidadesEnsino(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            // Tenta listar todas as unidades de ensino
            List<UnidadeEnsino> unidadesEnsino = unidadeEnsinoService.recuperarUnidadesDeEnsino();
            System.out.println(unidadesEnsino);

            request.setAttribute("unidadesEnsino", unidadesEnsino);
            System.out.println("DEBUG size=" + (unidadesEnsino == null ? "null" : unidadesEnsino.size()));
            request.setAttribute("unidades", unidadesEnsino);
            request.setAttribute("debugSize", (unidadesEnsino == null ? -1 : unidadesEnsino.size()));
            request.setAttribute("debugAttrNames", request.getAttributeNames());
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/listar.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao listar unidades de ensino: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível listar as unidades de ensino.");
            RequestDispatcher dispatcher = request
                    .getRequestDispatcher("/assets/pages/usuario/unidade-ensino/index.jsp");
            dispatcher.forward(request, response);
        }
    }

}

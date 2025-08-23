package controlador.servlet;

import modelo.dao.AnalistaDAO;
import modelo.dao.AnalistaDAOImpl;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.usuario.Analista;
import modelo.fabrica.conexao.FabricaConexao;
import modelo.fabrica.conexao.FabricaConexaoImpl;
import modelo.servicos.AnalistaServiceImpl;
import utils.ConverterDados;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/usuario/analista/*")
public class AnalistaServlet extends HttpServlet {
    private AnalistaServiceImpl analistaService;

    @Override
    public void init() throws ServletException {
        super.init();

        AnalistaDAO analistaDAO = new AnalistaDAOImpl();
        FabricaConexao fabricaConexao = new FabricaConexaoImpl();

        this.analistaService = new AnalistaServiceImpl(analistaDAO, fabricaConexao);
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
            // Rotas para exibir as telas (exemplo: usuario/analista/cadastrar - vai mostrar a tela de cadastro)
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
                mostrarTelaListar(request, response);
                break;

            // Rotas para executar ações (exemplo: o formulário tem a action /usuario/analista/exec-cadastrar - isso vai enviar o formulário para essa rota)
            case "/exec-cadastrar":
                cadastrarAnalista(request, response);
                break;
            case "/exec-atualizar":
                atualizarAnalista(request, response);
                break;
            case "/exec-deletar":
                deletarAnalista(request, response);
                break;
            case "/exec-recuperar":
                recuperarAnalista(request, response);
                break;
            case "/exec-listar":
                listarAnalistas(request, response);
                break;
            default:
                mostrarTelaErro(request, response);
        }
    }

    // Métodos para exibir as telas
    private void mostrarTelaInicial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaCadastrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/cadastrar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaExibir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/exibir.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaListar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/listar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaErro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/inicio/erro-404.jsp");
        dispatcher.forward(request, response);
    }


    // Métodos para executar ações
    private void cadastrarAnalista(HttpServletRequest request, HttpServletResponse response)
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

        // Cria uma instância da classe Endereco (endereco vira um objeto que vai ser passado dentro do construtor da Analista)
        Endereco endereco = new Endereco(null, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

        // Tabela de usuário
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Cargo cargo = Cargo.UNIDADE_ENSINO;

        // Tabela de Unidade de Ensino
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");
        Sexo sexo = Sexo.valueOf(request.getParameter("sexo"));

        // Cria uma instância da classe Analista
        Analista analista = new Analista(null, email, senha, null, null, cargo, endereco, nome, sobrenome, cpf, sexo, null);

        try {
            // Tenta cadastrar a unidade de ensino, com as informações que são passadas acima (como é um cadastro, os campos de "id" e "criado_em" são preenchidos automaticamente (pode lançar exceção)
            analistaService.cadastrarAnalista(analista);
            response.sendRedirect(request.getContextPath() + "/usuario/analista");

        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar unidade de ensino: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível realizar o cadastro.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void atualizarAnalista(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID da unidade de ensino não informado ou inválido.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Tabela de endereço:
        Long enderecoId = ConverterDados.stringParaLong(request.getParameter("endereco_id")); // Recupera o id para localizar a linha na tabela
        String logradouro = request.getParameter("logradouro");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String cep = request.getParameter("cep");
        String pais = request.getParameter("pais");

        // Cria uma instância da classe Endereco (endereco vira um objeto que vai ser passado dentro do construtor da Analista)
        Endereco endereco = new Endereco(enderecoId, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

        // Tabela de usuário (id do usuário recuperado no início do código)
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Cargo cargo = Cargo.valueOf(request.getParameter("cargo"));
        LocalDateTime criadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("criado_em"));
        LocalDateTime atualizadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("atualizado_em"));

        // Tabela de Unidade de Ensino (usa o usuario_id para localizar a linha na tabela):
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");
        Sexo sexo = Sexo.valueOf(request.getParameter("sexo"));

        // Cria uma instância da classe Analista
        Analista analista = new Analista(usuarioId, email, senha, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo, null);

        try {
            // Tenta atualizar a unidade de ensino, com as informações que são passadas acima (como é uma atualização, todos os campos foram informados)
            analistaService.atualizarAnalista(analista);
            response.sendRedirect(request.getContextPath() + "/usuario/analista");

        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar unidade de ensino: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível atualizar a unidade de ensino.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deletarAnalista(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID da unidade de ensino não fornecido para exclusão.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Tenta atualizar a unidade de ensino pelo id do usuário (deleta os registros nas outras tabelas em cascata)
            analistaService.deletarAnalistaPeloId(usuarioId);
            response.sendRedirect(request.getContextPath() + "/usuario/analista");

        } catch (RuntimeException e) {
            System.err.println("Erro ao deletar unidade de ensino: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível deletar a unidade de ensino. Tente novamente mais tarde.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void recuperarAnalista(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID da unidade de ensino não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Tenta buscar a unidade de ensino pelo ID
            Analista analista = analistaService.recuperarAnalistaPeloId(usuarioId);
            System.out.println(analista);

            if (analista == null) {
                request.setAttribute("mensagemErro", "Unidade de ensino não encontrada para o ID: " + usuarioId);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
                dispatcher.forward(request, response);
                return;
            }

            request.setAttribute("analista", analista);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao recuperar unidade de ensino: " + e.getMessage());
            request.setAttribute("mensagemErro", "Erro ao tentar recuperar a unidade de ensino.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listarAnalistas(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long cursoId = ConverterDados.stringParaLong(request.getParameter("curso_id"));

        if (cursoId == null) {
            request.setAttribute("mensagemErro", "ID da unidade de ensino não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            List<Analista> analistas = analistaService.recuperarAnalistasPeloCurso(cursoId);
            System.out.println(analistas);

            request.setAttribute("analistas", analistas);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao listar unidades de ensino: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível listar as unidades de ensino.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/usuario/analista/index.jsp");
            dispatcher.forward(request, response);
        }
    }

}

package controlador.servlet;

import modelo.dao.PedagogoDAO;
import modelo.dao.PedagogoDAOImpl;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.usuario.Pedagogo;
import modelo.fabrica.conexao.FabricaConexao;
import modelo.fabrica.conexao.FabricaConexaoImpl;
import modelo.servicos.PedagogoServiceImpl;
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

@WebServlet("/usuario/pedagogo/*")
public class PedagogoServlet extends HttpServlet {
    private PedagogoServiceImpl pedagogoService;

    @Override
    public void init() throws ServletException {
        super.init();

        PedagogoDAO pedagogoDAO = new PedagogoDAOImpl();
        FabricaConexao fabricaConexao = new FabricaConexaoImpl();

        this.pedagogoService = new PedagogoServiceImpl(pedagogoDAO, fabricaConexao);
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
            // Rotas para exibir as telas (exemplo: usuario/pedagogo/cadastrar - vai mostrar a tela de cadastro)
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

            // Rotas para executar ações (exemplo: o formulário tem a action /usuario/pedagogo/exec-cadastrar - isso vai enviar o formulário para essa rota)
            case "/exec-cadastrar":
                cadastrarPedagogo(request, response);
                break;
            case "/exec-atualizar":
                atualizarPedagogo(request, response);
                break;
            case "/exec-deletar":
                deletarPedagogo(request, response);
                break;
            case "/exec-recuperar":
                recuperarPedagogo(request, response);
                break;
            case "/exec-listar":
                listarPedagogos(request, response);
                break;
            default:
                mostrarTelaErro(request, response);
        }
    }

    // Métodos para exibir as telas
    private void mostrarTelaInicial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaCadastrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/cadastrar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaExibir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/exibir.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaListar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/listar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaErro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/inicio/erro-404.jsp");
        dispatcher.forward(request, response);
    }


    // Métodos para executar ações
    private void cadastrarPedagogo(HttpServletRequest request, HttpServletResponse response)
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

        // Cria uma instância da classe Endereco (endereco vira um objeto que vai ser passado dentro do construtor da Pedagogo)
        Endereco endereco = new Endereco(null, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

        // Tabela de usuário
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Cargo cargo = Cargo.PEDAGOGO;

        // Tabela de Pedagogo
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");
        Sexo sexo = Sexo.valueOf(request.getParameter("sexo"));

        // Cria uma instância da classe Pedagogo
        Pedagogo pedagogo = new Pedagogo(null, email, senha, null, null, cargo, endereco, nome, sobrenome, cpf, sexo, null);

        try {
            // Tenta cadastrar o pedagogo, com as informações que são passadas acima (como é um cadastro, os campos de "id" e "criado_em" são preenchidos automaticamente (pode lançar exceção)
            pedagogoService.cadastrarPedagogo(pedagogo);
            response.sendRedirect(request.getContextPath() + "/usuario/pedagogo");

        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar pedagogo: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível realizar o cadastro.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void atualizarPedagogo(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID do pedagogo não informado ou inválido.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
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

        // Cria uma instância da classe Endereco (endereco vira um objeto que vai ser passado dentro do construtor da Pedagogo)
        Endereco endereco = new Endereco(enderecoId, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

        // Tabela de usuário (id do usuário recuperado no início do código)
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Cargo cargo = Cargo.valueOf(request.getParameter("cargo"));
        LocalDateTime criadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("criado_em"));
        LocalDateTime atualizadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("atualizado_em"));

        // Tabela de Pedagogo (usa o usuario_id para localizar a linha na tabela):
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");
        Sexo sexo = Sexo.valueOf(request.getParameter("sexo"));

        // Cria uma instância da classe Pedagogo
        Pedagogo pedagogo = new Pedagogo(usuarioId, email, senha, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo, null);

        try {
            // Tenta atualizar o pedagogo, com as informações que são passadas acima (como é uma atualização, todos os campos foram informados)
            pedagogoService.atualizarPedagogo(pedagogo);
            response.sendRedirect(request.getContextPath() + "/usuario/pedagogo");

        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar pedagogo: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível atualizar o pedagogo.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deletarPedagogo(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID do pedagogo não fornecido para exclusão.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Tenta atualizar o pedagogo pelo id do usuário (deleta os registros nas outras tabelas em cascata)
            pedagogoService.deletarPedagogoPeloId(usuarioId);
            response.sendRedirect(request.getContextPath() + "/usuario/pedagogo");

        } catch (RuntimeException e) {
            System.err.println("Erro ao deletar pedagogo: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível deletar o pedagogo. Tente novamente mais tarde.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void recuperarPedagogo(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID do pedagogo não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Tenta buscar o pedagogo pelo ID
            Pedagogo pedagogo = pedagogoService.recuperarPedagogoPeloId(usuarioId);
            System.out.println(pedagogo);

            if (pedagogo == null) {
                request.setAttribute("mensagemErro", "Pedagogo não encontrado para o ID: " + usuarioId);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
                dispatcher.forward(request, response);
                return;
            }

            request.setAttribute("pedagogo", pedagogo);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao recuperar pedagogo: " + e.getMessage());
            request.setAttribute("mensagemErro", "Erro ao tentar recuperar o pedagogo.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listarPedagogos(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long turmaId = ConverterDados.stringParaLong(request.getParameter("turma_id"));

        if (turmaId == null) {
            request.setAttribute("mensagemErro", "ID do pedagogo não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            List<Pedagogo> pedagogos = pedagogoService.recuperarPedagogosPelaTurma(turmaId);
            System.out.println(pedagogos);

            request.setAttribute("pedagogos", pedagogos);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao listar pedagogos: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível listar os pedagogos.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/pedagogo/index.jsp");
            dispatcher.forward(request, response);
        }
    }

}

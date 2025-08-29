package controlador.servlet;

import modelo.dao.AlunoDAO;
import modelo.dao.AlunoDAOImpl;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.instituicao.Turma;
import modelo.entidade.usuario.Aluno;
import modelo.fabrica.conexao.FabricaConexao;
import modelo.fabrica.conexao.FabricaConexaoImpl;
import modelo.servicos.AlunoServiceImpl;
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

@WebServlet("/usuario/aluno/*")
public class AlunoServlet extends HttpServlet {
    private AlunoServiceImpl alunoService;

    @Override
    public void init() throws ServletException {
        super.init();

        AlunoDAO alunoDAO = new AlunoDAOImpl();
        FabricaConexao fabricaConexao = new FabricaConexaoImpl();

        this.alunoService = new AlunoServiceImpl(alunoDAO, fabricaConexao);
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
            // Rotas para exibir as telas (exemplo: usuario/aluno/cadastrar - vai mostrar a tela de cadastro)
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

            // Rotas para executar ações (exemplo: o formulário tem a action /usuario/aluno/exec-cadastrar - isso vai enviar o formulário para essa rota)
            case "/exec-cadastrar":
                cadastrarAluno(request, response);
                break;
            case "/exec-atualizar":
                atualizarAluno(request, response);
                break;
            case "/exec-deletar":
                deletarAluno(request, response);
                break;
            case "/exec-recuperar":
                recuperarAluno(request, response);
                break;
            case "/exec-listar":
                listarAlunos(request, response);
                break;
            default:
                mostrarTelaErro(request, response);
        }
    }

    // Métodos para exibir as telas
    private void mostrarTelaInicial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaCadastrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/cadastrar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaExibir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/exibir.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaListar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/listar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaErro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/inicio/erro-404.jsp");
        dispatcher.forward(request, response);
    }


    // Métodos para executar ações
    private void cadastrarAluno(HttpServletRequest request, HttpServletResponse response)
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

        // Cria uma instância da classe Endereco (endereco vira um objeto que vai ser passado dentro do construtor da Aluno)
        Endereco endereco = new Endereco(null, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

        // Tabela de usuário
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Cargo cargo = Cargo.ALUNO;

        // Tabela de Aluno
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");
        Sexo sexo = Sexo.valueOf(request.getParameter("sexo"));
        Long turmaId = ConverterDados.stringParaLong(request.getParameter("turma_id"));
        // TODO: Utilizar TurmaService para instanciar um objeto do tipo Turma, e depois passa para o construtor

        // Cria uma instância da classe Aluno
        Aluno aluno = new Aluno(null, email, senha, null, null, cargo, endereco, nome, sobrenome, cpf, sexo, null, null);

        try {
            // Tenta cadastrar o aluno, com as informações passadas acima (como é um cadastro, os campos de "id" e "criado_em" são preenchidos automaticamente (pode lançar exceção)
            alunoService.cadastrarAluno(aluno);
            response.sendRedirect(request.getContextPath() + "/usuario/aluno");

        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar aluno: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível realizar o cadastro.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void atualizarAluno(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID do aluno não informado ou inválido.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
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

        // Cria uma instância da classe Endereco (endereco vira um objeto que vai ser passado dentro do construtor da Aluno)
        Endereco endereco = new Endereco(enderecoId, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

        // Tabela de usuário (id do usuário recuperado no início do código)
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Cargo cargo = Cargo.valueOf(request.getParameter("cargo"));
        LocalDateTime criadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("criado_em"));
        LocalDateTime atualizadoEm = ConverterDados.stringParaLocalDateTime(request.getParameter("atualizado_em"));

        // Tabela de Aluno (usa o usuario_id para localizar a linha na tabela):
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String cpf = request.getParameter("cpf");
        Sexo sexo = Sexo.valueOf(request.getParameter("sexo"));
        Long turmaId = ConverterDados.stringParaLong(request.getParameter("turma_id"));
        // TODO: Utilizar TurmaService para instanciar um objeto do tipo Turma, e depois passa para o construtor

        // Cria uma instância da classe Aluno
        Aluno aluno = new Aluno(usuarioId, email, senha, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo, null, null);

        try {
            // Tenta atualizar o aluno, com as informações que são passadas acima (como é uma atualização, todos os campos foram informados)
            alunoService.atualizarAluno(aluno);
            response.sendRedirect(request.getContextPath() + "/usuario/aluno");

        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar aluno: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível atualizar o aluno.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deletarAluno(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID do aluno não fornecido para exclusão.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Tenta atualizar o aluno pelo id do usuário (deleta os registros nas outras tabelas em cascata)
            alunoService.deletarAlunoPeloId(usuarioId);
            response.sendRedirect(request.getContextPath() + "/usuario/aluno");

        } catch (RuntimeException e) {
            System.err.println("Erro ao deletar aluno: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível deletar o aluno. Tente novamente mais tarde.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void recuperarAluno(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long usuarioId = ConverterDados.stringParaLong(request.getParameter("usuario_id"));

        if (usuarioId == null) {
            request.setAttribute("mensagemErro", "ID do aluno não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Tenta buscar o aluno pelo ID
            Aluno aluno = alunoService.recuperarAlunoPeloId(usuarioId);
            System.out.println(aluno);

            if (aluno == null) {
                request.setAttribute("mensagemErro", "Aluno não encontrado para o ID: " + usuarioId);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
                dispatcher.forward(request, response);
                return;
            }

            request.setAttribute("aluno", aluno);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao recuperar aluno: " + e.getMessage());
            request.setAttribute("mensagemErro", "Erro ao tentar recuperar o aluno.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listarAlunos(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long turmaId = ConverterDados.stringParaLong(request.getParameter("turma_id"));
        System.out.println(turmaId);

        if (turmaId == null) {
            request.setAttribute("mensagemErro", "ID do aluno não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            List<Aluno> alunos = alunoService.recuperarAlunosPelaTurma(turmaId);
            System.out.println(alunos);

            request.setAttribute("alunos", alunos);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível listar os alunos.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/paginas/usuario/aluno/index.jsp");
            dispatcher.forward(request, response);
        }
    }

}

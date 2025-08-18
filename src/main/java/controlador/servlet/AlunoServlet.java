package controlador.servlet;

import modelo.dao.*;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.usuario.Aluno;
import modelo.servicos.AlunoService;
import utils.ConverterDados;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/usuario/aluno/*")
public class AlunoServlet extends HttpServlet {
    private AlunoService alunoService;

    @Override
    public void init() throws ServletException {
        super.init();

        EnderecoDAO enderecoDAO = new EnderecoDAOImpl();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        AlunoDAOImpl alunoDAO = new AlunoDAOImpl();

        this.alunoService = new AlunoService(enderecoDAO, usuarioDAO, alunoDAO);
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
                cadastrarAluno(request, response);
                break;
            case "/atualizar":
                atualizarAluno(request, response);
                break;
            case "/deletar":
                deletarAluno(request, response);
                break;
            case "/recuperar":
                recuperarAluno(request, response);
                break;
            case "/listar":
                System.out.println("/listar");
                break;
            default:
                System.out.println("Rota não mapeada");
                // Se o endpoint for nulo ou não estiver mapeado, cai aqui
        }
    }

    private void cadastrarAluno(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Aluno aluno = criarAlunoMapeandoRequisicao(request);
        boolean sucesso = alunoService.cadastrarAluno(aluno);
        if (!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao cadastrar aluno");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/login");
    }

    private void atualizarAluno(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Aluno aluno = criarAlunoMapeandoRequisicao(request);

        boolean sucesso = alunoService.atualizarAluno(aluno);
        if(!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao atualizar aluno");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/usuario/aluno");
    }

    private void deletarAluno(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long idUsuario = ConverterDados.stringParaLong(request.getParameter("id_usuario"));

        boolean sucesso = alunoService.deletarAlunoPeloId(idUsuario);
        if (!sucesso) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao deletar usuário");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/usuario");
    }

    private void recuperarAluno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idUsuario = ConverterDados.stringParaLong(request.getParameter("id_usuario"));
        if (idUsuario == null){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "O id não foi informado");
            return;
        }

        Aluno aluno = alunoService.recuperarAlunoPeloId(idUsuario);
        if (aluno == null){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "O aluno não foi encontrado");
            return;
        }

        System.out.println(aluno);
        request.setAttribute("aluno", aluno);
        request.getRequestDispatcher("/usuario/aluno-detalhe.jsp").forward(request, response);
    }

    private Aluno criarAlunoMapeandoRequisicao(HttpServletRequest request) {
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

        return new Aluno(idUsuario, email, senha, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo, null, null);
    }
}

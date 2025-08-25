package controlador.servlet;

import modelo.dao.*;
import modelo.dao.DenunciaServiceImpl;
import modelo.entidade.denuncia.Denuncia;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.geral.enumeracoes.SituacaoAnaliseDenuncia;
import modelo.entidade.geral.enumeracoes.TipoDenuncia;
import modelo.entidade.usuario.Aluno;
import modelo.entidade.usuario.Analista;
import modelo.entidade.usuario.Pedagogo;
import modelo.fabrica.conexao.FabricaConexao;
import modelo.fabrica.conexao.FabricaConexaoImpl;
import modelo.servicos.*;
import utils.ConverterDados;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/denuncia/*")
public class DenunciaServlet extends HttpServlet {
    private DenunciaService denunciaService;
    private AlunoService alunoService;
    private PedagogoService pedagogoService;
    private AnalistaService analistaService;

    @Override
    public void init() throws ServletException {
        super.init();
        FabricaConexao fabricaConexao = new FabricaConexaoImpl();

        DenunciaDAO denunciaDAO = new DenunciaDAOImpl();
        this.denunciaService = new DenunciaServiceImpl(denunciaDAO, fabricaConexao);

        AlunoDAO alunoDAO = new AlunoDAOImpl();
        this.alunoService = new AlunoServiceImpl(alunoDAO, fabricaConexao);

        PedagogoDAO pedagogoDAO = new PedagogoDAOImpl();
        this.pedagogoService = new PedagogoServiceImpl(pedagogoDAO, fabricaConexao);

        AnalistaDAO analistaDAO = new AnalistaDAOImpl();
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
            // Rotas para exibir as telas
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

            // Rotas para executar ações
            case "/exec-cadastrar":
                cadastrarDenuncia(request, response);
                break;
            case "/exec-atualizar":
                atualizarDenuncia(request, response);
                break;
            case "/exec-deletar":
                deletarDenuncia(request, response);
                break;
            case "/exec-recuperar":
                recuperarDenuncia(request, response);
                break;
            case "/exec-listar-denuncias-autor":
                listarDenunciasPeloAutor(request, response);
                break;
            case "/exec-listar-denuncias-pedagogo":
                listarDenunciasPeloPedagogo(request, response);
                break;
            case "/exec-listar-denuncias-analista":
                listarDenunciasPeloAnalista(request, response);
                break;
            case "/exec-listar-denuncias-vitima":
                listarDenunciasPelaVitima(request, response);
                break;
            case "/exec-listar-denuncias-agressor":
                listarDenunciasPeloAgressor(request, response);
                break;
            default:
                mostrarTelaErro(request, response);
        }
    }


    // Métodos para exibir as telas
    private void mostrarTelaInicial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaCadastrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/cadastrar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaExibir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/exibir.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaListar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/listar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarTelaErro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/inicio/erro-404.jsp");
        dispatcher.forward(request, response);
    }


    // Métodos para executar ações
    private void cadastrarDenuncia(HttpServletRequest request, HttpServletResponse response)
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

        // Cria uma instância da classe Endereco (endereco vira um objeto que vai ser passado dentro do construtor da denúncia)
        Endereco localFato = new Endereco(null, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

        // Tabela de denúncia
        String titulo = request.getParameter("titulo");
        String conteudo = request.getParameter("conteudo");
        TipoDenuncia tipoDenuncia = TipoDenuncia.valueOf(request.getParameter("tipo-denuncia"));
        SituacaoAnaliseDenuncia situacaoAnaliseDenuncia = SituacaoAnaliseDenuncia.NAO_INICIADA;
        Long autorId = ConverterDados.stringParaLong(request.getParameter("autor_id"));
        Long pedagogoId = ConverterDados.stringParaLong(request.getParameter("pedagogo_id"));
        Long analistaId = ConverterDados.stringParaLong(request.getParameter("analista_id"));
        String[] vitimasId = request.getParameterValues("vitimas[]");
        String[] agressoresId = request.getParameterValues("agressores[]");

        Aluno autor = alunoService.recuperarAlunoPeloId(autorId);
        Pedagogo pedagogo = pedagogoService.recuperarPedagogoPeloId(pedagogoId);
        Analista analista = analistaService.recuperarAnalistaPeloId(analistaId);
        List<Aluno> vitimas = new ArrayList<>();
        List<Aluno> agressores = new ArrayList<>();

        if (vitimasId != null) {
            for (String vitimaId : vitimasId) {
                Long id = ConverterDados.stringParaLong(vitimaId);
                Aluno vitima = alunoService.recuperarAlunoPeloId(id);
                vitimas.add(vitima);
            }
        }

        if (agressoresId != null) {
            for (String agressorId : agressoresId) {
                Long id = ConverterDados.stringParaLong(agressorId);
                Aluno agressor = alunoService.recuperarAlunoPeloId(id);
                agressores.add(agressor);
            }
        }

        // Cria uma instância da classe Denuncia
        Denuncia denuncia = new Denuncia(null, titulo, conteudo, tipoDenuncia, situacaoAnaliseDenuncia, null, null, null, localFato, autor, analista, pedagogo, vitimas, agressores, null);

        try {
            // Tenta cadastrar o aluno, com as informações passadas acima (como é um cadastro, os campos de "id" e "criado_em" são preenchidos automaticamente (pode lançar exceção)
            denunciaService.cadastrarDenuncia(denuncia);
            response.sendRedirect(request.getContextPath() + "/denuncia");

        } catch (RuntimeException e) {
            System.err.println("Erro ao cadastrar denúncia: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível realizar o cadastro.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void atualizarDenuncia(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long denunciaId = ConverterDados.stringParaLong(request.getParameter("denuncia_id"));

        if (denunciaId == null) {
            request.setAttribute("mensagemErro", "ID da denúncia não informado ou inválido.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Long enderecoId = ConverterDados.stringParaLong(request.getParameter("local_fato_id")); // Recupera o id para localizar a linha na tabela
        String logradouro = request.getParameter("logradouro");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String cep = request.getParameter("cep");
        String pais = request.getParameter("pais");

        // Cria uma instância da classe Endereco
        Endereco localFato = new Endereco(enderecoId, logradouro, numero, complemento, bairro, cidade, estado, cep, pais);

        // Tabela de denúncia
        String titulo = request.getParameter("titulo");
        String conteudo = request.getParameter("conteudo");
        TipoDenuncia tipoDenuncia = TipoDenuncia.valueOf(request.getParameter("tipo-denuncia"));
        SituacaoAnaliseDenuncia situacaoAnaliseDenuncia = SituacaoAnaliseDenuncia.valueOf(request.getParameter("situacao-analise-denuncia"));
        Long autorId = ConverterDados.stringParaLong(request.getParameter("autor_id"));
        Long pedagogoId = ConverterDados.stringParaLong(request.getParameter("pedagogo_id"));
        Long analistaId = ConverterDados.stringParaLong(request.getParameter("analista_id"));
        String[] vitimasId = request.getParameterValues("vitimas[]");
        String[] agressoresId = request.getParameterValues("agressores[]");

        Aluno autor = alunoService.recuperarAlunoPeloId(autorId);
        Pedagogo pedagogo = pedagogoService.recuperarPedagogoPeloId(pedagogoId);
        Analista analista = analistaService.recuperarAnalistaPeloId(analistaId);
        List<Aluno> vitimas = new ArrayList<>();
        List<Aluno> agressores = new ArrayList<>();

        if (vitimasId != null) {
            for (String vitimaId : vitimasId) {
                Long id = ConverterDados.stringParaLong(vitimaId);
                Aluno vitima = alunoService.recuperarAlunoPeloId(id);
                vitimas.add(vitima);
            }
        }

        if (agressoresId != null) {
            for (String agressorId : agressoresId) {
                Long id = ConverterDados.stringParaLong(agressorId);
                Aluno agressor = alunoService.recuperarAlunoPeloId(id);
                agressores.add(agressor);
            }
        }

        // Cria uma instância da classe Denuncia
        Denuncia denuncia = new Denuncia(denunciaId, titulo, conteudo, tipoDenuncia, situacaoAnaliseDenuncia, null, null, null, localFato, autor, analista, pedagogo, vitimas, agressores, null);

        try {
            // Tenta cadastrar o aluno, com as informações passadas acima (como é um cadastro, os campos de "id" e "criado_em" são preenchidos automaticamente (pode lançar exceção)
            denunciaService.atualizarDenuncia(denuncia);
            response.sendRedirect(request.getContextPath() + "/denuncia");

        } catch (RuntimeException e) {
            System.err.println("Erro ao atualizar denúncia: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível atualizar.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void deletarDenuncia(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long denunciaId = ConverterDados.stringParaLong(request.getParameter("denuncia_id"));

        if (denunciaId == null) {
            request.setAttribute("mensagemErro", "ID da denúncia não fornecido para exclusão.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            denunciaService.deletarDenunciaPeloId(denunciaId);
            response.sendRedirect(request.getContextPath() + "/denuncia");

        } catch (RuntimeException e) {
            System.err.println("Erro ao deletar denúncia: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível deletar a denúncia.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void recuperarDenuncia(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long denunciaId = ConverterDados.stringParaLong(request.getParameter("denuncia_id"));

        if (denunciaId == null) {
            request.setAttribute("mensagemErro", "ID da denúncia não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            Denuncia denuncia = denunciaService.recuperarDenunciaPeloId(denunciaId);
            System.out.println(denuncia);

            if (denuncia == null) {
                request.setAttribute("mensagemErro", "Denúncia não encontrada para o ID: " + denunciaId);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
                dispatcher.forward(request, response);
                return;
            }

            request.setAttribute("denuncia", denuncia);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao recuperar aluno: " + e.getMessage());
            request.setAttribute("mensagemErro", "Erro ao tentar recuperar o aluno.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listarDenunciasPeloAutor(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long autorId = ConverterDados.stringParaLong(request.getParameter("autor_id"));

        if (autorId == null) {
            request.setAttribute("mensagemErro", "ID do autor não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            List<Denuncia> denuncias = denunciaService.recuperarDenunciasPeloAutor(autorId);
            System.out.println(denuncias);

            request.setAttribute("denuncias", denuncias);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao listar denuncias: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível listar as denúncias.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listarDenunciasPeloPedagogo(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long pedagogoId = ConverterDados.stringParaLong(request.getParameter("pedagogo_id"));

        if (pedagogoId == null) {
            request.setAttribute("mensagemErro", "ID do pedagogo não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            List<Denuncia> denuncias = denunciaService.recuperarDenunciasPeloPedagogo(pedagogoId);
            System.out.println(denuncias);

            request.setAttribute("denuncias", denuncias);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao listar denuncias: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível listar as denúncias do pedagogo.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listarDenunciasPeloAnalista(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long analistaId = ConverterDados.stringParaLong(request.getParameter("analista_id"));

        if (analistaId == null) {
            request.setAttribute("mensagemErro", "ID do analista não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            List<Denuncia> denuncias = denunciaService.recuperarDenunciasPeloAnalista(analistaId);
            System.out.println(denuncias);

            request.setAttribute("denuncias", denuncias);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao listar denuncias: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível listar as denúncias do analista.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listarDenunciasPelaVitima(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long vitimaId = ConverterDados.stringParaLong(request.getParameter("vitima_id"));

        if (vitimaId == null) {
            request.setAttribute("mensagemErro", "ID da vítima não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            List<Denuncia> denuncias = denunciaService.recuperarDenunciasPelaVitima(vitimaId);
            System.out.println(denuncias);

            request.setAttribute("denuncias", denuncias);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao listar denuncias: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível listar as denúncias da vítima.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listarDenunciasPeloAgressor(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Long agressorId = ConverterDados.stringParaLong(request.getParameter("agressor_id"));

        if (agressorId == null) {
            request.setAttribute("mensagemErro", "ID da vítima não fornecido ou inválido para recuperação.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            List<Denuncia> denuncias = denunciaService.recuperarDenunciasPeloAgressor(agressorId);
            System.out.println(denuncias);

            request.setAttribute("denuncias", denuncias);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);

        } catch (RuntimeException e) {
            System.err.println("Erro ao listar denuncias: " + e.getMessage());
            request.setAttribute("mensagemErro", "Não foi possível listar as denúncias da vítima.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/assets/pages/denuncia/index.jsp");
            dispatcher.forward(request, response);
        }
    }

}

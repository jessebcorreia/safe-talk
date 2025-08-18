package controlador.filtro;

import utils.AtributosSessao;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter(
        urlPatterns = { "/*" },
        initParams = {
                @WebInitParam(name = "whitelist", value = "/login/*,/usuario/unidade-ensino/cadastrar,/")
        }
)
public class AutenticacaoFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AutenticacaoFilter.class.getName());

    private Set<String> whitelistExatas;
    private List<String> whitelistPrefixos;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.log(Level.FINE, "AutenticacaoFilter inicializado");

        whitelistExatas = new HashSet<>();
        whitelistPrefixos = new ArrayList<>();

        String whitelistBruta = filterConfig.getInitParameter("whitelist");
        if (whitelistBruta == null)
            whitelistBruta = "";

        String[] rotas = whitelistBruta.split(",");
        for (String rota : rotas) {
            rota = rota.trim();

            if (rota.isEmpty())
                continue;

            if (rota.endsWith("/*")) {
                whitelistPrefixos.add(rota.substring(0, rota.length() - 2));
                continue;
            }
            whitelistExatas.add(rota);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req  = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        String endpointBruto = req.getRequestURI();
        int qtdeLetrasContextPath = req.getContextPath().length();
        String endpoint = endpointBruto.substring(qtdeLetrasContextPath);

        if (rotaEstaDentroDaWhitelist(endpoint)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession sessao = req.getSession(false);
        boolean autenticado = sessao != null && sessao.getAttribute(AtributosSessao.USUARIO_ID) != null;

        if (!autenticado) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autenticado");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean rotaEstaDentroDaWhitelist(String endpoint) {
        if (endpoint == null)
            return false;

        if (whitelistExatas.contains(endpoint))
            return true;

        for (String prefixo : whitelistPrefixos) {
            if (endpoint.startsWith(prefixo))
                return true;
        }

        return false;
    }

    @Override
    public void destroy() {
        LOGGER.log(Level.FINE, "AutenticacaoFilter destruído");
    }
}

package modelo.servicos;

import modelo.dao.AnalistaDAO;
import modelo.entidade.usuario.Analista;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalistaServiceImpl implements AnalistaService {

    private final AnalistaDAO analistaDAO;
    private final FabricaConexao fabricaConexao;

    public AnalistaServiceImpl(AnalistaDAO analistaDAO, FabricaConexao fabricaConexao) {
        this.analistaDAO = analistaDAO;
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public void cadastrarAnalista(Analista analista) {
        try (Connection conexao = fabricaConexao.conectar()) {
            analistaDAO.cadastrarAnalista(conexao, analista);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao cadastrar analista.", e);
            throw new RuntimeException("Não foi possível cadastrar o analista.", e);
        }
    }

    @Override
    public void atualizarAnalista(Analista analista) {
        try (Connection conexao = fabricaConexao.conectar()) {
            analistaDAO.atualizarAnalista(conexao, analista);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao atualizar analista com ID: " + analista.getId(), e);
            throw new RuntimeException("Não foi possível atualizar o analista.", e);
        }
    }

    @Override
    public void deletarAnalistaPeloId(Long id) {
        try (Connection conexao = fabricaConexao.conectar()) {
            analistaDAO.deletarAnalistaPeloId(conexao, id);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao deletar analista com ID: " + id, e);
            throw new RuntimeException("Não foi possível deletar o analista.", e);
        }
    }

    @Override
    public Analista recuperarAnalistaPeloId(Long id) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return analistaDAO.recuperarAnalistaPeloId(conexao, id);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar analista com ID: " + id, e);
            return null;
        }
    }

    @Override
    public List<Analista> recuperarAnalistasPeloCurso(Long cursoId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return analistaDAO.recuperarAnalistasPeloCurso(conexao, cursoId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de analistas.", e);
            return new ArrayList<>();
        }
    }
}

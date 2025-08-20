package modelo.servicos;

import modelo.dao.UnidadeEnsinoDAO;
import modelo.entidade.usuario.UnidadeEnsino;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnidadeEnsinoServiceImpl implements UnidadeEnsinoService {

    private final UnidadeEnsinoDAO unidadeEnsinoDAO;
    private final FabricaConexao fabricaConexao;

    public UnidadeEnsinoServiceImpl(UnidadeEnsinoDAO unidadeEnsinoDAO, FabricaConexao fabricaConexao) {
        this.unidadeEnsinoDAO = unidadeEnsinoDAO;
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public void cadastrarUnidadeEnsino(UnidadeEnsino unidadeEnsino) {
        try (Connection conexao = fabricaConexao.conectar()) {
            unidadeEnsinoDAO.cadastrarUnidadeEnsino(conexao, unidadeEnsino);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao cadastrar unidade de ensino.", e);
            throw new RuntimeException("Não foi possível cadastrar a unidade de ensino.", e);
        }
    }

    @Override
    public void atualizarUnidadeEnsino(UnidadeEnsino unidadeEnsino) {
        try (Connection conexao = fabricaConexao.conectar()) {
            unidadeEnsinoDAO.atualizarUnidadeEnsino(conexao, unidadeEnsino);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao atualizar unidade de ensino com ID: " + unidadeEnsino.getId(), e);
            throw new RuntimeException("Não foi possível atualizar a unidade de ensino.", e);
        }
    }

    @Override
    public void deletarUnidadeEnsinoPeloId(Long id) {
        try (Connection conexao = fabricaConexao.conectar()) {
            unidadeEnsinoDAO.deletarUnidadeEnsinoPeloId(conexao, id);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao deletar unidade de ensino com ID: " + id, e);
            throw new RuntimeException("Não foi possível deletar a unidade de ensino.", e);
        }
    }

    @Override
    public UnidadeEnsino recuperarUnidadeEnsinoPeloId(Long id) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return unidadeEnsinoDAO.recuperarUnidadeEnsinoPeloId(conexao, id);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar unidade de ensino com ID: " + id, e);
            return null;
        }
    }

    @Override
    public List<UnidadeEnsino> recuperarUnidadesDeEnsino() {
        try (Connection conexao = fabricaConexao.conectar()) {
            return unidadeEnsinoDAO.recuperarUnidadesDeEnsino(conexao);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de unidades de ensino.", e);
            return new ArrayList<>();
        }
    }
}

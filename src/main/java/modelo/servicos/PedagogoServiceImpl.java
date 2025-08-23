package modelo.servicos;

import modelo.dao.PedagogoDAO;
import modelo.entidade.usuario.Pedagogo;
import modelo.fabrica.conexao.FabricaConexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedagogoServiceImpl implements PedagogoService {

    private final PedagogoDAO pedagogoDAO;
    private final FabricaConexao fabricaConexao;

    public PedagogoServiceImpl(PedagogoDAO pedagogoDAO, FabricaConexao fabricaConexao) {
        this.pedagogoDAO = pedagogoDAO;
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public void cadastrarPedagogo(Pedagogo pedagogo) {
        try (Connection conexao = fabricaConexao.conectar()) {
            pedagogoDAO.cadastrarPedagogo(conexao, pedagogo);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao cadastrar pedagogo.", e);
            throw new RuntimeException("Não foi possível cadastrar o pedagogo.", e);
        }
    }

    @Override
    public void atualizarPedagogo(Pedagogo pedagogo) {
        try (Connection conexao = fabricaConexao.conectar()) {
            pedagogoDAO.atualizarPedagogo(conexao, pedagogo);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao atualizar pedagogo com ID: " + pedagogo.getId(), e);
            throw new RuntimeException("Não foi possível atualizar o pedagogo.", e);
        }
    }

    @Override
    public void deletarPedagogoPeloId(Long id) {
        try (Connection conexao = fabricaConexao.conectar()) {
            pedagogoDAO.deletarPedagogoPeloId(conexao, id);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao deletar pedagogo com ID: " + id, e);
            throw new RuntimeException("Não foi possível deletar o pedagogo.", e);
        }
    }

    @Override
    public Pedagogo recuperarPedagogoPeloId(Long id) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return pedagogoDAO.recuperarPedagogoPeloId(conexao, id);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar pedagogo com ID: " + id, e);
            return null;
        }
    }

    @Override
    public List<Pedagogo> recuperarPedagogosPelaTurma(Long turmaId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return pedagogoDAO.recuperarPedagogosPelaTurma(conexao, turmaId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de pedagogos.", e);
            return new ArrayList<>();
        }
    }
}

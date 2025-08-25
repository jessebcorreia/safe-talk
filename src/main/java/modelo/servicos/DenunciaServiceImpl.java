package modelo.dao;


import modelo.entidade.denuncia.Denuncia;
import modelo.entidade.usuario.Aluno;
import modelo.entidade.usuario.Analista;
import modelo.entidade.usuario.Pedagogo;
import modelo.fabrica.conexao.FabricaConexao;
import modelo.servicos.DenunciaService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DenunciaServiceImpl implements DenunciaService {

    private final DenunciaDAO denunciaDAO;
    private final FabricaConexao fabricaConexao;

    public DenunciaServiceImpl(DenunciaDAO denunciaDAO, FabricaConexao fabricaConexao) {
        this.denunciaDAO = denunciaDAO;
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public void cadastrarDenuncia(Denuncia denuncia) {
        try (Connection conexao = fabricaConexao.conectar()) {
            denunciaDAO.cadastrarDenuncia(conexao, denuncia);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao cadastrar denúncia.", e);
            throw new RuntimeException("Não foi possível cadastrar a denúncia.", e);
        }
    }

    @Override
    public void atualizarDenuncia(Denuncia denuncia) {
        try (Connection conexao = fabricaConexao.conectar()) {
            denunciaDAO.atualizarDenuncia(conexao, denuncia);
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao atualizar denúncia com ID: " + denuncia.getId(), e);
            throw new RuntimeException("Não foi possível atualizar a denúncia.", e);
        }
    }

    @Override
    public void deletarDenunciaPeloId(Long id) {
        try (Connection conexao = fabricaConexao.conectar()) {
            denunciaDAO.deletarDenunciaPeloId(conexao, id);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao deletar denúncia com ID: " + id, e);
            throw new RuntimeException("Não foi possível deletar a denúncia.", e);
        }
    }

    @Override
    public Denuncia recuperarDenunciaPeloId(Long denunciaId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarDenunciaPeloId(conexao, denunciaId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar denúncia com ID: " + denunciaId, e);
            return null;
        }
    }

    @Override
    public List<Denuncia> recuperarDenunciasPeloAutor(Long autorId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarDenunciasPeloAutor(conexao, autorId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de denúncias pelo autor com ID: " + autorId, e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Denuncia> recuperarDenunciasPeloPedagogo(Long pedagogoId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarDenunciasPeloPedagogo(conexao, pedagogoId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de denúncias pelo pedagogo com ID: " + pedagogoId, e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Denuncia> recuperarDenunciasPeloAnalista(Long analistaId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarDenunciasPeloAnalista(conexao, analistaId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de denúncias pelo analista com ID: " + analistaId, e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Denuncia> recuperarDenunciasPelaVitima(Long vitimaId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarDenunciasPelaVitima(conexao, vitimaId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de denúncias pela vítima com ID: " + vitimaId, e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Denuncia> recuperarDenunciasPeloAgressor(Long agressorId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarDenunciasPeloAgressor(conexao, agressorId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar lista de denúncias pelo agressor com ID: " + agressorId, e);
            return new ArrayList<>();
        }
    }

    @Override
    public Aluno recuperarAutorDenuncia(Long denunciaId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarAutorDenuncia(conexao, denunciaId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar o autor da denúncia com ID: " + denunciaId, e);
            return null;
        }
    }

    @Override
    public Pedagogo recuperarPedagogoDenuncia(Long denunciaId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarPedagogoDenuncia(conexao, denunciaId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar o pedagogo da denúncia com ID: " + denunciaId, e);
            return null;
        }
    }

    @Override
    public Analista recuperarAnalistaDenuncia(Long denunciaId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarAnalistaDenuncia(conexao, denunciaId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar o analista da denúncia com ID: " + denunciaId, e);
            return null;
        }
    }

    @Override
    public List<Aluno> recuperarVitimasPelaDenuncia(Long denunciaId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarVitimasPelaDenuncia(conexao, denunciaId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar as vítimas na denúncia com ID: " + denunciaId, e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Aluno> recuperarAgressoresPelaDenuncia(Long denunciaId) {
        try (Connection conexao = fabricaConexao.conectar()) {
            return denunciaDAO.recuperarAgressoresPelaDenuncia(conexao, denunciaId);

        } catch (SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Erro ao recuperar os agressores na denúncia com ID: " + denunciaId, e);
            return new ArrayList<>();
        }
    }
}

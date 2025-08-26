package modelo.dao;

import modelo.entidade.denuncia.Denuncia;
import modelo.entidade.usuario.Aluno;
import modelo.entidade.usuario.Analista;
import modelo.entidade.usuario.Pedagogo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DenunciaDAO {
    void cadastrarDenuncia(Connection conexao, Denuncia denuncia) throws SQLException;
    void atualizarDenuncia(Connection conexao, Denuncia denuncia) throws SQLException;
    void deletarDenunciaPeloId(Connection conexao, Long id) throws SQLException;
    Denuncia recuperarDenunciaPeloId(Connection conexao, Long denunciaId) throws SQLException;

    List<Denuncia> recuperarDenunciasPeloAutor(Connection conexao, Long autorId) throws SQLException;
    List<Denuncia> recuperarDenunciasPeloPedagogo(Connection conexao, Long pedagogoId) throws SQLException;
    List<Denuncia> recuperarDenunciasPeloAnalista(Connection conexao, Long analistaId) throws SQLException;
    List<Denuncia> recuperarDenunciasPelaVitima(Connection conexao, Long vitimaId) throws SQLException;
    List<Denuncia> recuperarDenunciasPeloAgressor(Connection conexao, Long agressorId) throws SQLException;

    // TODO: Verificar se esses métodos devem pertencer à DenunciaDAO, ou devem ser migrados para Aluno/Pedagogo/Analista, conforme o caso
    Aluno recuperarAutorDenuncia(Connection conexao, Long denunciaId) throws SQLException;
    Pedagogo recuperarPedagogoDenuncia(Connection conexao, Long denunciaId) throws SQLException;
    Analista recuperarAnalistaDenuncia(Connection conexao, Long denunciaId) throws SQLException;
    List<Aluno> recuperarVitimasPelaDenuncia(Connection conexao, Long denunciaId) throws SQLException;
    List<Aluno> recuperarAgressoresPelaDenuncia(Connection conexao, Long denunciaId) throws SQLException;
}

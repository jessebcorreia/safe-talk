package modelo.servicos;


import modelo.entidade.denuncia.Denuncia;
import modelo.entidade.usuario.Aluno;
import modelo.entidade.usuario.Analista;
import modelo.entidade.usuario.Pedagogo;

import java.util.List;

public interface DenunciaService {
    void cadastrarDenuncia(Denuncia denuncia);
    void atualizarDenuncia(Denuncia denuncia);
    void deletarDenunciaPeloId(Long id);
    Denuncia recuperarDenunciaPeloId(Long denunciaId);

    List<Denuncia> recuperarDenunciasPeloAutor(Long autorId);
    List<Denuncia> recuperarDenunciasPeloPedagogo(Long pedagogoId);
    List<Denuncia> recuperarDenunciasPeloAnalista(Long analistaId);
    List<Denuncia> recuperarDenunciasPelaVitima(Long vitimaId);
    List<Denuncia> recuperarDenunciasPeloAgressor(Long agressorId);

    Aluno recuperarAutorDenuncia(Long denunciaId);
    Pedagogo recuperarPedagogoDenuncia(Long denunciaId);
    Analista recuperarAnalistaDenuncia(Long denunciaId);
    List<Aluno> recuperarVitimasPelaDenuncia(Long denunciaId);
    List<Aluno> recuperarAgressoresPelaDenuncia(Long denunciaId);
}

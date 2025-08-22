package modelo.servicos;

import modelo.entidade.usuario.Analista;

import java.util.List;

public interface AnalistaService {
    void cadastrarAnalista(Analista analista);
    void atualizarAnalista(Analista analista);
    void deletarAnalistaPeloId(Long id);
    Analista recuperarAnalistaPeloId(Long id);
    List<Analista> recuperarAnalistasPeloCurso(Long cursoId);
}

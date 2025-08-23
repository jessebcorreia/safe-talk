package modelo.servicos;

import modelo.entidade.usuario.Pedagogo;

import java.util.List;

public interface PedagogoService {
    void cadastrarPedagogo(Pedagogo pedagogo);
    void atualizarPedagogo(Pedagogo pedagogo);
    void deletarPedagogoPeloId(Long id);
    Pedagogo recuperarPedagogoPeloId(Long id);
    List<Pedagogo> recuperarPedagogosPelaTurma(Long turmaId);
}

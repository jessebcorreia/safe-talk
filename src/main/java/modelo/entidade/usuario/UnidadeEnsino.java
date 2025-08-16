package modelo.entidade.usuario;

import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.Endereco;

import java.time.LocalDateTime;

public class UnidadeEnsino extends UsuarioPessoaJuridica {
    private String descricao;

    // No args constructor
    public UnidadeEnsino() {}

    // All args constructor
    public UnidadeEnsino(Long id, String email, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Cargo cargo, Endereco endereco, String nomeFantasia, String razaoSocial, String cnpj, String descricao) {
        super(id, email, senha, criadoEm, atualizadoEm, cargo, endereco, nomeFantasia, razaoSocial, cnpj);
        setDescricao(descricao);
    }

    // Métodos de acesso
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

package modelo.entidade.usuario;

import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.Endereco;

import java.time.LocalDateTime;

public abstract class UsuarioPessoaJuridica extends Usuario {
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;

    // No args constructor
    public UsuarioPessoaJuridica() {}

    // All args constructor
    public UsuarioPessoaJuridica(Long id, String email, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Cargo cargo, Endereco endereco, String nomeFantasia, String razaoSocial, String cnpj) {
        super(id, email, senha, criadoEm, atualizadoEm, cargo, endereco);
        setNomeFantasia(nomeFantasia);
        setRazaoSocial(razaoSocial);
        setCnpj(cnpj);
    }

    // Métodos de acesso
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}

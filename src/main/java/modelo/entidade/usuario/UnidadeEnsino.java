package modelo.entidade.usuario;

import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.Endereco;

import java.time.LocalDateTime;

public class UnidadeEnsino extends Usuario {
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String descricao;

    // No args constructor
    public UnidadeEnsino() {}

    // All args constructor
    public UnidadeEnsino(Long id, String email, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Cargo cargo, Endereco endereco, String nomeFantasia, String razaoSocial, String cnpj, String descricao) {
        super(id, email, senha, criadoEm, atualizadoEm, cargo, endereco);
        setNomeFantasia(nomeFantasia);
        setRazaoSocial(razaoSocial);
        setCnpj(cnpj);
        setDescricao(descricao);
    }

    // Login constructor
    public UnidadeEnsino(Long id, String email, String senha, Cargo cargo) {
        super(id, email, senha, cargo);
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Outros métodos
    @Override
    public String toString() {
        Long id = getId();
        String email = getEmail();
        LocalDateTime criadoEm = getCriadoEm();
        LocalDateTime atualizadoEm = getAtualizadoEm();
        Cargo cargo = getCargo();
        String nomeFantasia = getNomeFantasia();
        String razaoSocial = getRazaoSocial();
        String cnpj = getCnpj();
        String descricao = getDescricao();
        Endereco endereco = getEndereco();

        // ajusta a string do endereço para ter indentação extra
        String enderecoStr = endereco != null
                ? endereco.toString().replaceAll("(?m)^", "    ") // adiciona 4 espaços no início de cada linha
                : "null";

        return "{\n" +
                "  id: " + id + ",\n" +
                "  email: " + (email != null ? "\"" + email + "\"" : "null") + ",\n" +
                "  criadoEm: " + (criadoEm != null ? "\"" + criadoEm + "\"" : "null") + ",\n" +
                "  atualizadoEm: " + (atualizadoEm != null ? "\"" + atualizadoEm + "\"" : "null") + ",\n" +
                "  cargo: " + (cargo != null ? "\"" + cargo + "\"" : "null") + ",\n" +
                "  nomeFantasia: " + (nomeFantasia != null ? "\"" + nomeFantasia + "\"" : "null") + ",\n" +
                "  razaoSocial: " + (razaoSocial != null ? "\"" + razaoSocial + "\"" : "null") + ",\n" +
                "  cnpj: " + (cnpj != null ? "\"" + cnpj + "\"" : "null") + ",\n" +
                "  descricao: " + (descricao != null ? "\"" + descricao + "\"" : "null") + ",\n" +
                "  endereco: \n" + enderecoStr + "\n" +
                "}";
    }
}

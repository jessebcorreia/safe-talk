package modelo.entidade.geral;

import modelo.entidade.geral.enumeracoes.Parentesco;

public class Responsavel {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private Parentesco parentesco;
    private Endereco endereco;

    // No args constructor
    public Responsavel() {}

    // All args constructor
    public Responsavel(Long id, String nome, String sobrenome, String cpf, String telefone, Parentesco parentesco, Endereco endereco) {
        setId(id);
        setNome(nome);
        setSobrenome(sobrenome);
        setCpf(cpf);
        setTelefone(telefone);
        setParentesco(parentesco);
        setEndereco(endereco);
    }

    // Métodos de acesso
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}

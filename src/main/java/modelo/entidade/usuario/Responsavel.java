package modelo.entidade.usuario;

import modelo.entidade.geral.Endereco;
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

    // Outros métodos
    @Override
    public String toString() {
        Long id = getId();
        String nome = getNome();
        String sobrenome = getSobrenome();
        String cpf = getCpf();
        String telefone = getTelefone();
        Parentesco parentesco = getParentesco();
        Endereco endereco = getEndereco();

        String enderecoStr = endereco != null
                ? endereco.toString().replaceAll("(?m)^", "    ")
                : "null";

        return "{\n" +
                "  id: " + (id != null ? id : "null") + ",\n" +
                "  nome: " + (nome != null ? "\"" + nome + "\"" : "null") + ",\n" +
                "  sobrenome: " + (sobrenome != null ? "\"" + sobrenome + "\"" : "null") + ",\n" +
                "  cpf: " + (cpf != null ? "\"" + cpf + "\"" : "null") + ",\n" +
                "  telefone: " + (telefone != null ? "\"" + telefone + "\"" : "null") + ",\n" +
                "  parentesco: " + (parentesco != null ? "\"" + parentesco + "\"" : "null") + ",\n" +
                "  endereco: \n" + enderecoStr + "\n" +
                "}";
    }
}

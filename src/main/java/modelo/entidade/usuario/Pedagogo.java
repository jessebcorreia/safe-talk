package modelo.entidade.usuario;

import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.instituicao.Turma;

import java.time.LocalDateTime;
import java.util.List;

public class Pedagogo extends Usuario {
    private String nome;
    private String sobrenome;
    private String cpf;
    private Sexo sexo;
    private List<Turma> turmas;

    // No args constructor
    public Pedagogo() {}

    // All args constructor
    public Pedagogo(Long id, String email, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Cargo cargo, Endereco endereco, String nome, String sobrenome, String cpf, Sexo sexo, List<Turma> turmas) {
        super(id, email, senha, criadoEm, atualizadoEm, cargo, endereco);
        setNome(nome);
        setSobrenome(sobrenome);
        setCpf(cpf);
        setSexo(sexo);
        setTurmas(turmas);
    }

    // Login constructor
    public Pedagogo(Long id, String email, String senha, Cargo cargo) {
        super(id, email, senha, cargo);
    }

    // Métodos de acesso
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    // Outros métodos
    @Override
    public String toString() {
        Long id = getId();
        String email = getEmail();
        String senha = getSenha();
        LocalDateTime criadoEm = getCriadoEm();
        LocalDateTime atualizadoEm = getAtualizadoEm();
        Cargo cargo = getCargo();
        Endereco endereco = getEndereco();
        String nome = getNome();
        String sobrenome = getSobrenome();
        String cpf = getCpf();
        Sexo sexo = getSexo();

        String enderecoStr = endereco != null
                ? endereco.toString().replaceAll("(?m)^", "    ")
                : "null";

        return "{\n" +
                "  id: " + (id != null ? id : "null") + ",\n" +
                "  email: " + (email != null ? "\"" + email + "\"" : "null") + ",\n" +
                "  senha: " + (senha != null ? "\"" + senha + "\"" : "null") + ",\n" +
                "  criadoEm: " + (criadoEm != null ? "\"" + criadoEm + "\"" : "null") + ",\n" +
                "  atualizadoEm: " + (atualizadoEm != null ? "\"" + atualizadoEm + "\"" : "null") + ",\n" +
                "  cargo: " + (cargo != null ? "\"" + cargo + "\"" : "null") + ",\n" +
                "  nome: " + (nome != null ? "\"" + nome + "\"" : "null") + ",\n" +
                "  sobrenome: " + (sobrenome != null ? "\"" + sobrenome + "\"" : "null") + ",\n" +
                "  cpf: " + (cpf != null ? "\"" + cpf + "\"" : "null") + ",\n" +
                "  sexo: " + (sexo != null ? "\"" + sexo + "\"" : "null") + ",\n" +
                "  endereco: \n" + enderecoStr + "\n" +
                "}";
    }
}

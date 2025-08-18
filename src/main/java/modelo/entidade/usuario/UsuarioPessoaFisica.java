package modelo.entidade.usuario;

import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Sexo;

import java.time.LocalDateTime;

public abstract class UsuarioPessoaFisica extends Usuario {
    private String nome;
    private String sobrenome;
    private String cpf;
    private Sexo sexo;

    // No args constructor
    public UsuarioPessoaFisica() {}

    // All args constructor
    public UsuarioPessoaFisica(Long id, String email, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Cargo cargo, Endereco endereco, String nome, String sobrenome, String cpf, Sexo sexo) {
        super(id, email, senha, criadoEm, atualizadoEm, cargo, endereco);
        setNome(nome);
        setSobrenome(sobrenome);
        setCpf(cpf);
        setSexo(sexo);
    }

    // Login constructor
    public UsuarioPessoaFisica(Long id, String email, String senha, Cargo cargo) {
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
}

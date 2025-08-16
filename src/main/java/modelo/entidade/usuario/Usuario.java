package modelo.entidade.usuario;

import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.Endereco;

import java.time.LocalDateTime;

public abstract class Usuario {
    private Long id;
    private String email;
    private String senha;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private Cargo cargo;

    private Endereco endereco;

    // No args constructor
    public Usuario() {}

    // All args constructor
    public Usuario(Long id, String email, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Cargo cargo, Endereco endereco) {
        setId(id);
        setEmail(email);
        setSenha(senha);
        setCriadoEm(criadoEm);
        setAtualizadoEm(atualizadoEm);
        setCargo(cargo);
        setEndereco(endereco);
    }

    // Login constructor
    public Usuario(Long id, Cargo cargo) {
        setId(id);
        setCargo(cargo);
    }

    // Métodos de acesso
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}

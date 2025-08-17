package modelo.entidade.usuario;

import modelo.entidade.geral.Endereco;
import modelo.entidade.geral.enumeracoes.Cargo;
import modelo.entidade.geral.enumeracoes.Sexo;
import modelo.entidade.instituicao.Curso;

import java.time.LocalDateTime;
import java.util.List;

public class Analista extends UsuarioPessoaFisica {
    private List<Curso> cursos;

    // No args constructor
    public Analista() {}

    // All args constructor
    public Analista(Long id, String email, String senha, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Cargo cargo, Endereco endereco, String nome, String sobrenome, String cpf, Sexo sexo, List<Curso> cursos) {
        super(id, email, senha, criadoEm, atualizadoEm, cargo, endereco, nome, sobrenome, cpf, sexo);
        setCursos(cursos);
    }

    // Login constructor
    public Analista(Long id, Cargo cargo) {
        super(id, cargo);
    }

    // Métodos de acesso
    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
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

package modelo.dao;

import modelo.entidade.geral.Endereco;

import java.sql.Connection;

public interface EnderecoDAO {
    Long cadastrarEndereco(Connection conexao, Endereco endereco);
    boolean atualizarEndereco(Connection conexao, Endereco endereco);
}

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Cadastrar Aluno</title>

    <link rel="stylesheet" href="<c:url value='/assets/css/global.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/header-deslogado.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/aluno-cadastrar.css'/>" />
</head>

<body>
    <%@ include file="/assets/components/header-deslogado.jsp" %>

    <h1>Cadastrar Aluno</h1>

    <form action="<c:url value='/usuario/aluno/exec-cadastrar'/>" method="post">
        <div>
        <!--Endereço-->
            <label for="logradouro">Logradouro</label>
            <input type="text" id="logradouro" name="logradouro" placeholder="Digite aqui o logradouro" required />

            <label for="numero">Número</label>
            <input type="text" id="numero" name="numero" placeholder="Digite aqui o número" required />

            <label for="complemento">Complemento</label>
            <input type="text" id="complemento" name="complemento" placeholder="Digite aqui o complemento" required />

            <label for="bairro">Bairro</label>
            <input type="text" id="bairro" name="bairro" placeholder="Digite aqui o bairro" required />

            <label for="cidade">Cidade</label>
            <input
            type="text" id="cidade" name="cidade" placeholder="Digite aqui a cidade" required />

            <label for="estado">Estado</label>
            <input type="text" id="estado" name="estado" placeholder="Digite aqui o estado" required />

            <label for="cep">CEP</label>
            <input type="text" id="cep" name="cep" placeholder="Digite aqui o cep" required />

            <label for="pais">País</label>
            <input type="text" id="pais" name="pais" placeholder="Digite aqui o pais" required />
           </div>

    <!--email e senha usuario-->
    <div>
        <label for="email">E-mail</label>
        <input type="email" id="email" name="email" placeholder="Digite aqui o e-mail" required />

        <label for="senha">Senha</label>
        <input type="password" id="senha" name="senha" placeholder="Digite aqui a senha" required />
    </div>


    <!--Dados Aluno-->
    <div>
        <label classfor="nome">Nome </label>
        <input type="text" id="nome" name="nome" placeholder="Digite aqui o nome " required />

        <label for="sobrenome">Sobrenome</label>
        <input type="text" id="sobrenome" name="sobrenome" placeholder="Digite aqui a sobrenome" required />

        <label for="cpf">CPF</label>
        <input type="text" id="cpf" name="cpf" placeholder="Digite aqui o cpf no formato: 000.000.000-00" required />

        <label for="sexo"> Sexo </label>
        <select name="sexo" id="sexo "required>
            <option value="MASCULINO">Masculino</option>
            <option value="FEMININO">Feminino</option>
        </select>

        <label for="turma_id">Truma</label>
        <input type="text" id="turma_id" name="turma_id" placeholder="Digite aqui a turma do aluno" required />

        <button type="submit">Cadastrar</button>
    </div>
    </form>
</body>

</html>

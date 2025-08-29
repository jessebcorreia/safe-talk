<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Cadastrar Unidade de Ensino</title>

    <link rel="stylesheet" href="<c:url value='/assets/css/global.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/componentes/header-deslogado.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/unidade-ensino-cadastrar.css'/>" />
</head>

<body>
    <%@ include file="/assets/componentes/header-deslogado.jsp" %>

    <h1>Cadastrar Unidade de Ensino</h1>

    <form action="<c:url value='/usuario/unidade-ensino/exec-cadastrar'/>" method="post">
        <label for="email">E-mail</label>
        <input type="email" id="email" name="email" placeholder="Digite aqui o e-mail" required />

        <label for="senha">Senha</label>
        <input type="password" id="senha" name="senha" placeholder="Digite aqui a senha" required />

        <label for="nome_fantasia">Nome Fantasia</label>
        <input type="text" id="nome_fantasia" name="nome_fantasia" placeholder="Digite aqui o nome fantasia" required />

        <label for="razao_social">Razão Social</label>
        <input type="text" id="razao_social" name="razao_social" placeholder="Digite aqui a razão social" required />

        <label for="cnpj">CNPJ</label>
        <input type="text" id="cnpj" name="cnpj" placeholder="Digite aqui o cnpj no formato: 00.000.000/0000-00" required />

        <label for="descricao">Descrição da Unidade</label>
        <input type="text" id="descricao" name="descricao" placeholder="Digite aqui a descrição" required />

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

        <button type="submit">Cadastrar</button>
    </form>
</body>

</html>

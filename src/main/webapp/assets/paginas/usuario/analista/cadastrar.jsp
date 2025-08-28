<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Safe Talk</title>

    <link rel="stylesheet" href="<c:url value='/assets/css/global.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/header-deslogado.css'/>" />
</head>

<body>
    <%@ include file="/assets/componentes/header-deslogado.jsp" %>

    <h1>Cadastrar Analista</h1>

    <form action="<c:url value='/usuario/analista/exec-cadastrar' />" method="post">
        <fieldset class="endereco-wrapper">
            <legend>Informações de Endereço</legend>

            <label for="logradouro">Lagradouro</label>
            <input type="text" id="logradouro" name="logradouro">

            <label for="numero">Número</label>
            <input type="text" id="numero" name="numero">

            <label for="complemento">Complemento</label>
            <input type="text" id="complemento" name="complemento">

            <label for="bairro">Bairro</label>
            <input type="text" id="bairro" name="bairro">

            <label for="cidade">Cidade</label>
            <input type="text" id="cidade" name="cidade">

            <label for="estado">Estado</label>
            <input type="text" id="estado" name="estado">

            <label for="cep">CEP</label>
            <input type="text" id="cep" name="cep">

            <label for="pais">Pais</label>
            <input type="text" id="pais" name="pais">
        </fieldset>

        <fieldset class="credenciais-wrapper">
            <legend>Email e Senha</legend>

            <label for="email">Email</label>
            <input type="email" id="email" name="email">

            <label for="senha">Senha</label>
            <input type="password" id="senha" name="senha">
        </fieldset>

        <fieldset class="dados-wrapper">
            <legend>Informações do Analista</legend>

            <label for="nome">Nome</label>
            <input type="text" id="nome" name="nome">

            <label for="sobrenome">Sobrenome</label>
            <input type="text" id="sobrenome" name="sobrenome">

            <label for="cpf">CPF</label>
            <input type="text" id="cpf" name="cpf">

            <label for="sexo">sexo</label>
            <input type="text" id="sexo" name="sexo">
        </fieldset>

        <button type="submit">Cadastrar</button>
    </form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>

    <link rel="stylesheet" href="<c:url value='/assets/css/global.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/header-deslogado.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/unidade-ensino-cadastrar.css'/>" />
</head>

<body>
    <%@ include file="/assets/componentes/header-deslogado.jsp" %>

    <h1>Iniciar sessão</h1>

    <form action="<c:url value='/login/exec-iniciar-sessao'/>" method="post">
        <label for="email">E-mail</label>
        <input type="email" id="email" name="email" placeholder="Digite aqui o e-mail" required />

        <label for="senha">Senha</label>
        <input type="password" id="senha" name="senha" placeholder="Digite aqui a senha" required />

        <button type="submit">Cadastrar</button>
    </form>
</body>

</html>

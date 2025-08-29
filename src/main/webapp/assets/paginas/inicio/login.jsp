<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Safe Talk</title>

    <link
        rel="stylesheet"
        type="text/css"
        href="https://cdn.jsdelivr.net/npm/@phosphor-icons/web@2.1.1/src/regular/style.css"
    />
    <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=League+Spartan:wght@100..900&display=swap"
    />
    <link rel="stylesheet" href="<c:url value='/assets/css/global.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/componentes/header-deslogado.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/inicio/login.css'/>" />
</head>

<body>
    <%@ include file="/assets/componentes/header-deslogado.jsp" %>

    <main class="login">
        <h1>Acesso ao sistema</h1>
        <form action="<c:url value='/login/exec-iniciar-sessao'/>" method="post">
            <div class="input-wrapper">
                <label for="email">E-mail</label>
                <div>
                    <input type="email" id="email" name="email" placeholder="Digite aqui o e-mail" required />
                    <i class="ph ph-envelope"></i>
                </div>
            </div>

            <div class="input-wrapper">
                <label for="senha">Senha</label>
                <div>
                    <input type="password" id="senha" name="senha" placeholder="Digite aqui a senha" required />
                    <i class="ph ph-lock-key"></i>
                </div>
            </div>

            <button type="submit">Acessar</button>
        </form>
    </main>
</body>

</html>

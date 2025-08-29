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

    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=League+Spartan:wght@100..900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/assets/css/global.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/componentes/header-deslogado.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/index.css'/>" />
</head>

<body>
    <%@ include file="/assets/componentes/header-deslogado.jsp" %>

    <main class="container">
        <section>
            <div>
                <p>
                    Bem-vindo ao <strong>Safe Talk</strong> de escuta e apoio contra o <strong>bullying</strong>.
                </p>
                <p>
                    Este site foi criado para ajudar a identificar, denunciar e combater o <strong>bullying</strong> nas escolas de forma segura e confidencial. Aqui, alunos, professores e responsáveis podem registrar ocorrências e acompanhar o andamento das ações.
                </p>
                <p>
                    Nosso objetivo é promover um ambiente escolar mais respeitoso, acolhedor e livre de violência.
                </p>
            </div>
            <img
                class="alunos"
                src="<c:url value='/assets/img/alunos.svg'/>"
                alt="Imagem ilustrativa com 3 alunos"
            />
        </section>

        <section>
            <img
                class="escola"
                src="<c:url value='/assets/img/escola.svg'/>"
                alt="Imagem ilustrativa com 3 alunos"
            />
            <div>
                <p>
                    Nosso sistema contra o bullying permite que qualquer aluno denuncie situações de forma segura, rápida.
                </p>
                <p>
                    As denúncias são analisadas por profissionais preparados para agir com responsabilidade e cuidado.
                </p>
                <p>
                    Sabemos que é difícil, mas denunciar é um ato de coragem que pode proteger você e outras pessoas. Falar é o primeiro passo para transformar a realidade e construir um ambiente mais justo e acolhedor.
                </p>
            </div>
        </section>
    </main>
</body>

</html>

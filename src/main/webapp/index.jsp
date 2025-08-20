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
    <link rel="stylesheet" href="<c:url value='/assets/css/pagina-inicial.css'/>" />
</head>

<body>
    <%@ include file="/assets/components/header-deslogado.jsp" %>

    <main>
        <section>
            <div>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Sequi, neque ipsum. Ullam nihil pariatur harum minus! Ipsa reiciendis, tenetur blanditiis doloribus nemo odio, nihil fugiat inventore quod expedita laudantium officiis!</p>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corporis cupiditate sunt tempore inventore atque autem dolores, laboriosam sapiente, nobis ipsum, dolorum iusto dolore! Deleniti repellendus maiores error iure culpa enim?</p>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Officia necessitatibus ipsum asperiores fugit harum impedit amet alias quisquam nulla recusandae, maxime sint vitae dignissimos placeat! Dolor placeat nemo quaerat autem!</p>
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
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Sequi, neque ipsum. Ullam nihil pariatur harum minus! Ipsa reiciendis, tenetur blanditiis doloribus nemo odio, nihil fugiat inventore quod expedita laudantium officiis!</p>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Corporis cupiditate sunt tempore inventore atque autem dolores, laboriosam sapiente, nobis ipsum, dolorum iusto dolore! Deleniti repellendus maiores error iure culpa enim?</p>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Officia necessitatibus ipsum asperiores fugit harum impedit amet alias quisquam nulla recusandae, maxime sint vitae dignissimos placeat! Dolor placeat nemo quaerat autem!</p>
            </div>
        </section>
    </main>
</body>

</html>

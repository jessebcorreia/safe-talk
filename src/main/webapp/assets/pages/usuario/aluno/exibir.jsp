<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Exibir Aluno</title>

    <link rel="stylesheet" href="<c:url value='/assets/css/global.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/header-deslogado.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css/aluno-exibir.css'/>" />
</head>

<body>

    <%@ include file="/assets/components/header-deslogado.jsp" %>


        <div class="card">
            <h2>Dados do Aluno/</h2>
            <p> Nome: ${aluno.nome}</p>
            <p> Sobrenome: ${aluno.sobrenome}</p>
            <p> CPF: ${aluno.cpf}</p>
            <p> Turma: ${aluno.turma.id}</p>

            <p> email: ${aluno.email}</p>

            <p> Logradouro: ${aluno.endereco.logradouro}</p>
            <p> Numero: ${aluno.endereco.numero}</p>
            <p> Complemento: ${aluno.endereco.complemento}</p>
            <p> Bairro: ${aluno.endereco.bairro}</p>
            <p> Cidade: ${aluno.endereco.cidade}</p>
            <p> Estado: ${aluno.endereco.estado}</p>
            <p> CEP: ${aluno.endereco.cep}</p>
            <p> Pais: ${aluno.endereco.pais}</p>


            <button>Editar</button>
            <button>Excluir</button>
            <button>Voltar</button>
        </div>
</body>

</html>
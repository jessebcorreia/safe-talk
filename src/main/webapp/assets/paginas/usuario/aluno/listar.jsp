<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Listar Alunos</title>

    <link rel="stylesheet" href="<c:url value='/assets/css-js/global.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css-js/componentes/header-deslogado.css'/>" />
    <link rel="stylesheet" href="<c:url value='/assets/css-js/css-js/usuario/aluno/listar.css'/>" />
</head>

<body>
    <%@ include file="/assets/componentes/header-deslogado.jsp" %>
<div>
		<div>
			<h1>Alunos</h1>

	    <c:if test="${empty alunos}">
            <p>Nenhum aluno encontrada.</p>
        </c:if>

	    <c:if test="${not empty alunos}">
			<table id="tabela-alunos">
				<thead>
					<tr>
						<th>Nome</th>
						<th>Sobrenome</th>
						<th>CPF</th>
						<th>Email</th>
						<th>Gênero</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="aluno" items="${alunos}">
						<tr>
							<td><c:out value="${aluno.nome}" /></td>
							<td><c:out value="${aluno.sobrenome}" /></td>
							<td><c:out value="${aluno.sobrenome}" /></td>
							<td><c:out value="${aluno.email}" /></td>
                            <td><c:out value="${aluno.sexo}" /></td>
								<td>
								<a href="editar?id=<c:out value='${aluno.id}'/>">Editar</a>
								<a href="deletar?id=<c:out value='${aluno.id}'/>">Deletar</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		</div>
	</div>

</body>

</html>

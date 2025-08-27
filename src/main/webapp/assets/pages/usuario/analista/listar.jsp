<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

        <!DOCTYPE html>
        <html lang="pt-br">

        <head>
            <meta charset="UTF-8">
            <title>Listar Analistas</title>
        </head>

        <body>
            <h2>Lista de Analistas</h2>
            <c:choose>
                <c:when test="${empty analistas}">
                    <p>Nenhum analista encontrado.</p>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Sobrenome</th>
                                <th>Email</th>
                                <th>Cargo</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="analista" items="${analistas}">
                                <tr>
                                    <td>
                                        <c:out value="${a.id}" />
                                    </td>
                                    <td>
                                        <c:out value="${a.nome}" />
                                    </td>
                                    <td>
                                        <c:out value="${a.sobrenome}" />
                                    </td>
                                    <td>
                                        <c:out value="${a.email}" />
                                    </td>
                                    <td>
                                        <c:out value="${a.cargo}" />
                                    </td>
                                    <td class="links">
                                        <a href="<c:url value='/usuario/analista/exec-recuperar?usuario_id=${a.id}'/>">Exibir</a>
                                        <a  href="<c:url value='/usuario/analista/exec-atualizar?usuario_id=${a.id}'/>">Editar</a>
                                        <a  href="<c:url value='/usuario/analista/exec-deletar?usuario_id=${a.id}'/>">Excluir</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
            <p>
             <a class="link" href="<c:url value='/usuario/analista/cadastrar'/>">Novo Analista</a>
            </p>

        </body>

        </html>
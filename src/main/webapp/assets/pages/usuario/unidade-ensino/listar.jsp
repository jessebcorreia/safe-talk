<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
            <!DOCTYPE html>
            <html lang="pt-br">

            <head>
                <meta charset="UTF-8">
                <title>Unidades</title>
            </head>

            <body>
                <h2>Unidades de Ensino</h2>


                <c:if test="${empty unidades}">
                    <p>Nenhuma unidade encontrada.</p>
                </c:if>

                <c:if test="${not empty unidadesEnsino}">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Razão Social</th>
                                <th>Nome Fantasia</th>
                                <th>CNPJ</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${unidadesEnsino}">
                                <tr>
                                    <td>${u.id}</td>
                                    <td>${u.razaoSocial}</td>
                                    <td>${u.nomeFantasia}</td>
                                    <td>${u.cnpj}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </body>

            </html>
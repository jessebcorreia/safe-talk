<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exibir Analista</title>
</head>
<body>
    <h1>Analista</h1>

    <c:choose>
        <c:when test="${not empty analista}">
            <div class="card">
            <div><span class="id">ID:</span><c:out value="${analista.id}"/></div>
            <div><span class="nome">Nome:</span><c:out value="${analista.nome}"/></div>
            <div><span class="cpf">CPF:</span><c:out value="${analista.cpf}"/></div>
            <div><span class="sexo">Gênero:</span><c:out value="${analista.sexo}"/></div>
            <div><span class="email">Email:</span><c:out value="${analista.email}"/></div>
            <div><span class="cargo">Cargo:</span><c:out value="${analista.cargo}"/></div>
            <div><span class="criadoEm">Criado em:</span><c:out value="${analista.criadoEm}"/></div>
            <span></span>
            <h3>Endereço</h3>
            <div class="endereco">
                <div><span class="lagradouro">lagradouro:</span><c:out value="${analista.endereco.lagradouro}"/></div>
                <div><span class="numero">Número</span><c:out value="${analista.endereco.numero}"/></div>
                <div><span class="complemento">Complemento:</span><c:out value="${analista.endereco.complemento}"/></div>
                <div><span class="bairro">Bairro:</span><c:out value="${analista.endereco.bairro}"/></div>
                <div><span class="cidade">Cidade:</span><c:out value="${analista.endereco.cidade}"/></div>
                <div><span class="estado">Estado:</span><c:out value="${analista.endereco.estado}"/></div>
                <div><span class="cep">CEP:</span><c:out value="${analista.endereco.cep}"/></div>
            </div>
        </c:when>
        </div>
        <c:otherwise>
            <p>Nenhum analista encontrado.</p>
        </c:otherwise>
    </c:choose>
    
</body>
</html>
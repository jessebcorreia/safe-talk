<html lang="br">
<head>
    <meta charset="UTF-8">
    <title>Cadastrar</title>
</head>
<body>
<h1>Cadastrar Analista</h1>
    <form action="<c:url value='/usuario/analista/exec-cadastrar'" method="post"></form>
    <fieldset>

        <legend>Informações de Endereço</legend>
    <div class="enderoco">
        <label for="lagradouro">Lagradouro</label>
        <input type="text" id="lagradouro" name="lagradouro">
        <label for="numero">Número</label>
        <input type="number" id="numero" name="numero">
        <label for="complemento">Complemento</label>
        <input type="text" id="complemento" name="complemento">
        <label for="bairro">Bairro</label>
        <input type="text" id="bairro" name="bairro">
        <label for="cidade">Cidade</label>
        <input type="text" id="cidade" name="cidade">
        <label for="estado">Estado</label>
        <input type="text" id="estado" name="estado">
        <label for="cep">CEP</label>
        <input type="number" id="cep" name="cep">
        <label for="pais">Pais</label>
        <input type="text" id="pais" name="pais">
    </div>
    </fieldset>
    <fieldset>
        <legend>Email e Senha</legend>
    <div class="usuario">
        <label for="email">Email</label>
        <input type="email" id="email" name="email">
        <label for="senha">Senha</label>
        <input type="password" id="senha" name="senha">
    </div>
    </fieldset>
    <fieldset>
        <legend>Informações do Analista</legend>
    <div class="analista">
        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome">
        <label for="sobrenome">Sobrenome</label>
        <input type="text" id="sobrenome" name="sobrenome">
        <label for="cpf">CPF</label>
        <input type="text" id="cpf" name="cpf">
        <label for="sexo">sexo</label>
        <input type="text" id="sexo" name="sexo">
    </div>
    </fieldset>
    <input type="submit" value="Cadastrar">

</body>
</html>
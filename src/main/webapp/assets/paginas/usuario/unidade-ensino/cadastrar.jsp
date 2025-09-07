<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Safe Talk - Cadastrar Unidade de Ensino</title>

    <link rel="icon" href="<c:url value='/assets/img/favicon.svg'/>" type="image/svg+xml">
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
    <link rel="stylesheet" href="<c:url value='/assets/css/usuario/unidade-ensino/cadastrar.css'/>" />
</head>

<body>
    <%@ include file="/assets/componentes/header-deslogado.jsp" %>

    <div class="container">
        <h1>Cadastrar Unidade de Ensino</h1>

        <div class="progresso">
            <div class="circulo ativo">1</div>

            <div class="conector"></div>

            <div class="circulo">2</div>

            <div class="conector"></div>

            <div class="circulo">3</div>
        </div>

        <form action="<c:url value='/usuario/unidade-ensino/exec-cadastrar'/>" method="post">

            <div class="painel ativo">

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

                <div class="input-wrapper">
                    <label for="razao_social">Razão Social</label>
                    <div>
                        <input type="text" id="razao_social" name="razao_social" placeholder="Digite aqui a razão social da unidade de ensino" required />
                        <i class="ph ph-identification-card"></i>
                    </div>
                </div>

                <div class="input-wrapper">
                    <label for="nome_fantasia">Nome Fantasia</label>
                    <div>
                        <input type="text" id="nome_fantasia" name="nome_fantasia" placeholder="Digite aqui o nome fantasia da unidade de ensino" required />
                        <i class="ph ph-identification-card"></i>
                    </div>
                </div>

                <div class="input-wrapper">
                    <label for="cnpj">CNPJ</label>
                    <div>
                        <input type="text" id="cnpj" name="cnpj" placeholder="00.000.000/0000-00" required />
                        <i class="ph ph-identification-card"></i>
                    </div>
                </div>

                <div class="input-wrapper">
                    <label for="descricao">Descrição</label>
                    <div>
                        <input type="text" id="descricao" name="descricao" placeholder="Digite uma breve descrição da unidade de ensino" required />
                        <i class="ph ph-identification-card"></i>
                    </div>
                </div>

            </div>

            <div class="painel">
                <div class="input-wrapper">
                    <label for="logradouro">Logradouro</label>
                    <div>
                        <input type="text" id="logradouro" name="logradouro" placeholder="Digite aqui o logradouro" required />
                        <i class="ph ph-building-office"></i>
                    </div>
                </div>

                <div class="input-wrapper">
                    <label for="numero">Número</label>
                    <div>
                        <input type="text" id="numero" name="numero" placeholder="Digite aqui o número" required />
                        <i class="ph ph-building-office"></i>
                    </div>
                </div>

                <div class="input-wrapper">
                    <label for="complemento">Complemento</label>
                    <div>
                        <input type="text" id="complemento" name="complemento" placeholder="Digite aqui " required />
                        <i class="ph ph-building-office"></i>
                    </div>
                </div>

                <div class="input-wrapper">
                    <label for="bairro">Bairro</label>
                    <div>
                        <input type="text" id="bairro" name="bairro" placeholder="Digite aqui " required />
                        <i class="ph ph-building-office"></i>
                    </div>
                </div>

                <div class="input-wrapper">
                    <label for="cidade">Cidade</label>
                    <div>
                        <input type="text" id="cidade" name="cidade" placeholder="Digite aqui " required />
                        <i class="ph ph-building-office"></i>
                    </div>
                </div>

                <div class="input-wrapper">
                    <label for="estado">Estado</label>
                    <div>
                        <input type="text" id="estado" name="estado" placeholder="Digite aqui " required />
                        <i class="ph ph-building-office"></i>
                    </div>
                </div>

                <div class="input-wrapper">
                    <label for="cep">CEP</label>
                    <div>
                        <input type="text" id="cep" name="cep" placeholder="Digite aqui " required />
                        <i class="ph ph-building-office"></i>
                    </div>
                </div>

                <div class="input-wrapper">
                    <label for="pais">País</label>
                    <div>
                        <input type="text" id="pais" name="pais" placeholder="Digite aqui " required />
                        <i class="ph ph-building-office"></i>
                    </div>
                </div>
            </div>

            <div class="botoes-acao">
                <button type="button" class="voltar">Voltar</button>
                <button type="button" class="proximo">Próximo</button>
                <button type="submit" class="cadastrar">Cadastrar</button>
            </div>
        </form>
    </div>

    <div class="cadastro-concluido">
        <span>Unidade de Ensino cadastrada com sucesso!</span>
        <img
            src="<c:url value='/assets/img/cadastro-concluido.svg'/>"
            alt="Logo SafeTalk"
        />
    </div>

    <script src="<c:url value='/assets/js/usuario/unidade-ensino/cadastrar.js'/>"></script>

</body>

</html>

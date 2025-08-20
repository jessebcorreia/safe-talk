<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Área Logada</title>
  </head>
  <body>
    <h1>Parabéns! Tu tá logado.</h1>

    <br />
    <br />

    <form action="/safe-talk/login/encerrar-sessao" method="post">
      <button type="submit">Sair</button>
    </form>
  </body>
</html>

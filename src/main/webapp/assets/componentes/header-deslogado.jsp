<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <div class="logo-wrapper">
        <div class="nome-sistema-wrapper">
            <span class="nome">Safe</span>
            <span class="nome">Talk</span>
        </div>
        <img
            class="logo"
            src="<c:url value='/assets/img/logo.svg'/>"
            alt="Logo SafeTalk"
        />
    </div>

    <nav>
        <ul>
            <li>
                <a href="<c:url value='/como-denunciar'/>">Como denunciar?</a>
            </li>

            <li>
                <a href="<c:url value='/relatorios'/>">Relatórios</a>
            </li>

            <li>
                <a href="<c:url value='/contato'/>">Contato</a>
            </li>

            <li>
                <a href="<c:url value='/planos'/>">Planos</a>
            </li>

            <li>
                <a class="acesso" href="<c:url value='/usuario/unidade-ensino/cadastrar'/>">Cadastrar</a>
            </li>

            <li>
                <a class="acesso" href="<c:url value='/login'/>">Entrar</a>
            </li>
        </ul>
    </nav>
</header>

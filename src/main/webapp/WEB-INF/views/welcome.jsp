<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Добро пожаловать</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
    <a href="/admin"><button type="button" class="btn btn-primary pull-right">Админ панель</button></a>
    <br>
</c:if>

<c:if test="${currentUserAuthorities == 'ROLE_PUPIL'}">
    <a href="/pupil"><button type="button" class="btn btn-primary pull-right">Дневник</button></a>
    <br>
</c:if>

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h4 class="text-right">
            Вы вошли как ${currentUser.username}(${currentUserAuthorities}) | <a onclick="document.forms['logoutForm'].submit()">Выйти</a>
        </h4>

    </c:if>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
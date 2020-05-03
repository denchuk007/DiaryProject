<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Журнал</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<a href="${contextPath}/"><button class="btn btn-primary pull-right">Поставить оценку</button></a>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <h4 class="text-right">
        Вы вошли как ${currentUser.username}(${currentUserAuthorities}) | <a onclick="document.forms['logoutForm'].submit()">Выйти</a>
    </h4>
</c:if>

<c:if test="${!empty pupils}">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Имя, Фамилия</th>
            <th>Поставить оценку</th>
        </tr>
        </thead>
        <c:forEach items="${pupils}" var="pupil">
            <tbody>
            <tr>
                <th>${pupil.id}</th>
                <td>${pupil.name} ${pupil.surname}</td>
                <td><a href="/new-mark/${pupil.id}"><button class="btn btn-primary">+</button></a></td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</c:if>
<script>
    $(document).ready(function(){
        $(".table").css("display", "block !important");
    });
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
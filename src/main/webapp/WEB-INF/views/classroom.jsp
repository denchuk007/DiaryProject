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

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</c:if>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <ul class="nav navbar-nav">
                <li><a href="/welcome">Главная</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Администрирование <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/new-subject">Создать предмет</a></li>
                        <li><a href="/new-classroom">Создать класс</a></li>
                        <li><a href="/registration">Создать пользователя</a></li>
                    </ul>
                </li>
                <li><a href="/classrooms">Журнал</a></li>
            </ul>
        </div>
        <div class="collapse navbar-collapse" id="navbar-main">
            <a class="navbar-brand navbar-right" href="#" onclick="document.forms['logoutForm'].submit()">Вы вошли как ${currentUser.username} | Выход</a>
        </div>
    </div>
</nav>

<div class="container-fluid">
<h4 class="text-left">
        ${classroom.digit}${classroom.word} класс
    </h4>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Имя Фамилия</th>
            <th>Поставить оценку</th>
        </tr>
        </thead>
        <c:forEach items="${pupils}" var="pupil">
            <c:forEach items="${pupil.roles}" var="role">
            <c:if test="${role.name == 'ROLE_PUPIL'}">
            <tbody>
            <tr>
                <th>${pupil.id}</th>
                <td><a href="/teacher/${pupil.id}">${pupil.name} ${pupil.surname}</a></td>
                <td><a href="/new-mark/${pupil.id}"><button class="btn btn-primary">+</button></a></td>
            </tr>
            </tbody>
            </c:if>
            </c:forEach>
        </c:forEach>
    </table>

</div>
<script>
    $(document).ready(function(){
        $(".table").css("display", "block !important");
    });
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
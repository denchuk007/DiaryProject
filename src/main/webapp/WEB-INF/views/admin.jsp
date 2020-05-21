<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Admin</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</head>

<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <form id="logoutForm" method="post" action="${contextPath}/logout">
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
                        <li><a href="/admin">Пользователи</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="collapse navbar-collapse" id="navbar-main">
            <a class="navbar-brand navbar-right" href="#" onclick="document.forms['logoutForm'].submit()">Вы вошли как ${pageContext.request.userPrincipal.name} | Выход</a>
        </div>
    </div>
</nav>

<br>

<h2 class="form-signin-heading text-center">Пользователи</h2>

<div class="container-fluid">

<c:if test="${!empty listUsers}">
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Логин</th>

            <th>Роль</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Дата рождения</th>
            <th>Класс</th>
            <th>Дети</th>
            <th>Ред.</th>
            <th>Удалить</th>
        </tr>
        </thead>
        <c:forEach items="${listUsers}" var="user">
        <tbody>
            <tr>
                <th>${user.id}</th>
                <td>${user.username}</td>

                <td>
                    <c:if test="${!empty user.roles}">
                        <c:forEach items="${user.roles}" var="role">
                            <c:if test="${role.name == 'ROLE_PUPIL'}">
                                Ученик
                            </c:if>
                            <c:if test="${role.name == 'ROLE_PARENT'}">
                                Родитель
                            </c:if>
                            <c:if test="${role.name == 'ROLE_TEACHER'}">
                                Учитель
                            </c:if>
                            <c:if test="${role.name == 'ROLE_ADMIN'}">
                                <span style="color: red;">Администратор</span>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.birthday}</td>
                <td>${user.classroom.digit}${user.classroom.word}</td>
                <td>
                    <c:forEach items="${user.pupils}" var="pupil">
                        ${pupil.name} ${pupil.surname} (id: ${pupil.id})<br>
                    </c:forEach>
                </td>
                <td><a href="/edit/${user.id}"><button class="btn btn-primary">Ред.</button></a></td>
                <td>
                    <form action="${contextPath}/admin" method="post">
                        <input id="userId" type="hidden" name="userId" value="${user.id}"/>
                        <input id="action" type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-primary" onclick="return confirm('Удалить пользователя ${user.username}?')">Удалить</button>
                    </form>
                </td>
            </tr>
        </tbody>
        </c:forEach>
    </table>
</c:if>
</div>

<script>
    $(document).ready(function(){
        $(".table").css("display", "block !important");
    });
</script>
</body>
</html>
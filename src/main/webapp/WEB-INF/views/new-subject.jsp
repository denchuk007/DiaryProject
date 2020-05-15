<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Создание предмета</title>

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

                            <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
                                <li><a href="/admin">Пользователи</a></li>
                            </c:if>
                        </ul>
                    </li>

                    <c:if test="${currentUserAuthorities == 'ROLE_TEACHER'}">
                    <li><a href="/classrooms">Журнал</a></li>
                    </c:if>

                </ul>
            </div>
            <div class="collapse navbar-collapse" id="navbar-main">
                <a class="navbar-brand navbar-right" href="#" onclick="document.forms['logoutForm'].submit()">Вы вошли как ${currentUser.username} | Выход</a>
            </div>
        </div>
    </nav>

<div class="container" style="width: 400px">
    <form:form method="POST" modelAttribute="subjectForm" class="form-signin">
        <h2 class="form-signin-heading text-center">Создание предмета</h2>
        <spring:bind path="title">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="hidden" path="id" class="form-control"></form:input>
                <form:input type="text" path="title" class="form-control" placeholder="Название"
                            autofocus="true"></form:input>
                <form:errors path="title"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Принять</button>
    </form:form>
</div>

<div class="container-fluid">
<br><br>
<table class="table table-blue">
    <thead>
    <td>ID</td>
    <td>Название</td>
    <td>Редактировать</td>
    <td>Удалить</td>
    </thead>

    <tbody>
    <c:forEach items="${subjects}" var="subject">
        <tr>
            <td>${subject.id}</td>
            <td>${subject.title}</td>
            <td><a href="/edit/subject/${subject.id}"><button class="btn btn-primary">Ред.</button></a></td>
            <td><a href="/remove/subject/${subject.id}" onclick="return confirm('Удалить предмет ${subject.title}?')"><button class="btn btn-primary">Удалить</button></a> </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
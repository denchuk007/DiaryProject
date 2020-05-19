<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Поставить оценку</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

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

<div class="container" style="width: 25%">

    <form:form method="POST" modelAttribute="homeworkForm" class="form-signin">

        <h2 class="form-signin-heading text-center">Домашнее задание для ${currentClassroom.digit}${currentClassroom.word} класса</h2>

        <spring:bind path="text">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="hidden" path="id" class="form-control"></form:input>
                <form:textarea type="text" path="text" class="form-control" placeholder="Задание"></form:textarea>
                <form:errors path="text"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="date">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="date" class="form-control" placeholder="Дата"></form:input>
                <form:errors path="date"></form:errors>
            </div>
        </spring:bind>

        <select id="subjects" onchange="document.getElementById('subjectId').value= this.value;">
            <br>
            <br>
            <br>
            <c:if test="${subjects != null}">
                <option selected>Выберите предмет</option>
                <c:forEach items="${subjects}" var="subject">
                    <option value="${subject.id}">${subject.title}</option>
                </c:forEach>
            </c:if>
        </select>
        <br>
        <br>

        <input id="subjectId" type="hidden" name="subjectId" value="0"/>

        <button class="btn btn-lg btn-primary btn-block" onclick="checkForN()" type="submit">Принять</button>
    </form:form>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<!-- Selectpicker -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/i18n/defaults-*.min.js"></script>

<script>
    $(document).ready(function(){
        $('#subjects').selectpicker({
            liveSearch: true,
            width: "100%",
            maxOptions: 1
        });
    });
</script>
</body>
</html>
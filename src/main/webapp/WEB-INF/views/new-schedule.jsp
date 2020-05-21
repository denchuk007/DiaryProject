<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Создание расписания</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
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
                        <li><a href="/new-schedule">Создание расписания</a></li>
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

<div class="container" style="width: 25%">

    <form:form method="POST" modelAttribute="scheduleForm" class="form-signin">

        <h2 class="form-signin-heading text-center">Создание расписания</h2>

        <spring:bind path="interval">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="hidden" path="id" class="form-control"></form:input>
                <form:input type="text" path="interval" class="form-control" id="value"  placeholder="Интервал времени"></form:input>
                <form:errors path="interval"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="cabinet">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="cabinet" class="form-control" placeholder="Кабинет"></form:input>
                <form:errors path="cabinet"></form:errors>
            </div>
        </spring:bind>

        <select id="subjects" class="select" onchange="document.getElementById('subjectId').value= this.value;">


            <c:if test="${subjects != null}">
                <option selected>Выберите предмет</option>
                <c:forEach items="${subjects}" var="subject">
                    <option value="${subject.id}">${subject.title}</option>
                </c:forEach>
            </c:if>
        </select>
        <br>

        <select id="classrooms" class="select" onchange="document.getElementById('classroomId').value= this.value;">
            <br>
            <br>
            <br>
            <c:if test="${classrooms != null}">
                <option selected>Выберите класс</option>
                <c:forEach items="${classrooms}" var="classroom">
                    <option value="${classroom.id}">${classroom.digit}${classroom.word}</option>
                </c:forEach>
            </c:if>
        </select>
        <br>


        <select id="weeks" class="select" onchange="document.getElementById('week').value= this.value;">
            <br>
            <br>
            <br>
                <option selected>Выберите неделю</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
        </select>
        <br>


        <select id="daysOfWeek" class="select" onchange="document.getElementById('dayOfWeek').value= this.value;">
            <br>
            <br>
            <br>
            <option selected>Выберите день недели</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select>
        <br>


        <input id="subjectId" type="hidden" name="subjectId" value="0"/>
        <input id="classroomId" type="hidden" name="classroomId" value="0"/>
        <input id="week" type="hidden" name="week" value="0"/>
        <input id="dayOfWeek" type="hidden" name="dayOfWeek" value="0"/>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Принять</button>
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
        $(".select").selectpicker({
            liveSearch: true,
            width: "100%",
            maxOptions: 1
        });
    });
</script>
</body>
</html>
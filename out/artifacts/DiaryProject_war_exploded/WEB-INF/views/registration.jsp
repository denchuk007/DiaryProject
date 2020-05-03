<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Создание аккаунта</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>

<a href="/create-classroom"><button class="btn btn-primary pull-right">Создать школьный класс</button></a>

<div class="container">

    <form:form method="POST" modelAttribute="userForm" class="form-signin" name="form1">
        <h2 class="form-signin-heading">Создание аккаунта</h2>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="username" class="form-control" placeholder="Логин"
                            autofocus="true"></form:input>
                <form:errors path="username"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" class="form-control" placeholder="Пароль"></form:input>
                <form:errors path="password"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="confirmPassword">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="confirmPassword" class="form-control"
                            placeholder="Подтверждение пароля"></form:input>
                <form:errors path="confirmPassword"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="name" class="form-control"
                            placeholder="Имя"></form:input>
                <form:errors path="name"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="surname">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="surname" class="form-control"
                            placeholder="Фамлиия"></form:input>
                <form:errors path="surname"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="birthday">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="birthday" class="form-control"
                            placeholder="Дата рождения"></form:input>
                <form:errors path="birthday"></form:errors>
            </div>
        </spring:bind>

        <select id="roles"  onchange="document.getElementById('roleId').value= this.value; ">
            <c:if test="${roles != null}">
                <option selected>Выберите роль</option>
                <c:forEach items="${roles}" var="role">
                    <option value="${role.id}">${role.name}</option>
                </c:forEach>
            </c:if>
        </select>

        <select id="classrooms" onchange="document.getElementById('classroomId').value= this.value;">
            <br>
            <br>
            <c:if test="${classrooms != null}">
                <option selected>Выберите школьный класс</option>
                <c:forEach items="${classrooms}" var="classroom">
                    <option value="${classroom.id}">${classroom.digit}${classroom.word}</option>
                </c:forEach>
            </c:if>
        </select>

        <select id="pupils" onchange="document.getElementById('pupilId').value= this.value;">
            <br>
            <br>
            <br>
            <c:if test="${pupils != null}">
                <option selected>Выберите ученика</option>
                <c:forEach items="${pupils}" var="pupil">
                    <option value="${pupil.id}">${pupil.username}</option>
                </c:forEach>
            </c:if>
        </select>

        <input id="roleId" type="hidden" name="roleId" value="0"/>
        <input id="classroomId" type="hidden" name="classroomId" value="0"/>
        <input id="pupilId" type="hidden" name="pupilId" value="0"/>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Зарегистрировать</button>
    </form:form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>


<script>
    $(document).ready(function(){
        $("#classrooms").hide();
        $("#pupils").hide();
    });

    $("#roles").change(function(){
        $("#classrooms").hide();
        $("#pupils").hide();
        if ($("#roles").val() == 1 || $("#roles").val() == 3) {
            $("#classrooms").css("display","");
        }
        else if ($("#roles").val() == 2) {
            $("#pupils").css("display","");
        }
    });
</script>
</body>
</html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>

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

<div class="container">

    <form:form method="POST" modelAttribute="userForm" class="form-signin" name="form1">
        <c:if test="${userForm.name == null}">
        <h2 class="form-signin-heading">Создание аккаунта</h2>
        </c:if>
        <c:if test="${userForm.name != null}">
            <h2 class="form-signin-heading">Редактирование акканута</h2>
        </c:if>
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

        <select id="roles" class="selectpicker" title="Выберите роль">
            <c:if test="${roles != null}">
                <c:forEach items="${roles}" var="role">
                    <option value="${role.id}">
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
                            Администратор
                        </c:if>
                    </option>
                </c:forEach>
            </c:if>
        </select>

        <select id="classrooms" multiple title="Выберите школьный класс">
            <br>
            <br>
            <c:if test="${classrooms != null}">
                <c:forEach items="${classrooms}" var="classroom">
                    <option value="${classroom.id}">${classroom.digit}${classroom.word}</option>
                </c:forEach>
            </c:if>
        </select>

        <select id="pupils" multiple title="Выберите ученика">
            <br>
            <br>
            <br>
            <c:if test="${pupils != null}">
                <c:forEach items="${pupils}" var="pupil">
                    <option value="${pupil.id}">${pupil.name} ${pupil.surname} (${pupil.classroom.digit}${pupil.classroom.word})</option>
                </c:forEach>
            </c:if>
        </select>

        <input id="roleId" type="hidden" name="roleId" value="0"/>
        <input id="classroomId" type="hidden" name="classroomId" value="0"/>
        <input id="pupilsId" type="hidden" name="pupilsId" value="0"/>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Принять</button>

    </form:form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<!-- Selectpicker -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>

<script>
    $(document).ready(function(){
        $("#classrooms").hide();
        $("#pupils").hide();

        $('#roles').selectpicker({
            width: "100%",
            maxOptions: 1
        });
    });

    $("#roles").change(function(){
        $("#roleId").val($("#roles").val());

        $("#classrooms").selectpicker('destroy');
        $("#pupils").selectpicker('destroy');
        if ($("#roles").val() == 1 || $("#roles").val() == 3) {
            $('#classrooms').selectpicker({
                liveSearch: true,
                width: "100%",
                maxOptions: 1
            });
        } else if ($("#roles").val() == 2) {
            $('#pupils').selectpicker({
                liveSearch: true,
                width: "100%"
            });
        }
    });

    $("#classrooms").change(function () {
        $("#classroomId").val($("#classrooms").val());
    })

    $("#pupils").change(function () {
        $("#pupilsId").val($("#pupils").val());
    })

</script>
</body>
</html>
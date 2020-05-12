<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8;">

    <title>Создание предмета</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>

<div class="container">

    <form:form method="POST" modelAttribute="subjectForm" class="form-signin">
        <h2 class="form-signin-heading">Создание предмета</h2>
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
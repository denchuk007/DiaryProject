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

<div class="container">

    <form:form method="POST" modelAttribute="mark" class="form-signin">
        <h2 class="form-signin-heading">Оценка для ученика ${pupil.name} ${pupil.surname}</h2>

        <spring:bind path="value">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="value" class="form-control" placeholder="Оценка"></form:input>
                <form:errors path="value"></form:errors>
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

        <input id="subjectId" type="hidden" name="subjectId" value="0"/>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Принять</button>
    </form:form>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
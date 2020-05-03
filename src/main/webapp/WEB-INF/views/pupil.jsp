<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Дневник ученика</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <h4 class="text-right">
        Вы вошли как ${currentUser.username}(${currentUserAuthorities}) | <a onclick="document.forms['logoutForm'].submit()">Выйти</a>
    </h4>

    <select class="text-left" id="subjectsSelect">
        <option selected>Все предметы</option>
        <c:forEach items="${subjects}" var="subject">
            <option value="${subject.id}">${subject.title}</option>
        </c:forEach>
    </select>

            <table class="table table-bordered" id="table">
                <thead>
                <tr>
                    <th>Предмет</th>
                    <th>Оценка</th>
                    <th>Дата</th>
                    <th>Учитель</th>
                </tr>
                </thead>
                <c:forEach items="${currentUser.marks}" var="mark">
                    <c:if test="${mark.subject.id == 1}">
                    <tbody>
                    <tr>
                        <td>${mark.subject.title}</td>
                        <th>${mark.value}</th>
                        <td>${mark.date}</td>
                        <td>${mark.teacher.name} ${mark.teacher.surname}</td>
                    </tr>
                    </tbody>
                    </c:if>
                </c:forEach>
            </table>
</c:if>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<script>
    $("#subjectsSelect").change(function(){
        alert(1);
        $('#table tr:last').after('<tr>...</tr><tr>...</tr>');
    });
</script>

</body>
</html>
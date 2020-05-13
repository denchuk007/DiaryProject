<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Школьные классы</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <h4 class="text-right">
        Вы вошли как ${currentUser.username}(${currentUserAuthorities}) | <a onclick="document.forms['logoutForm'].submit()">Выйти</a>
    </h4>
</c:if>

<c:if test="${!empty classrooms}">
    <table class="table_price">
        <thead>
            <th>ID</th>
            <th>Школьные классы</th>
        </thead>
        <c:forEach items="${classrooms}" var="classroom">
            <tbody>
            <tr>
                <th>${classroom.id}</th>
                <td><a href="/classroom/${classroom.digit}${classroom.word}">${classroom.digit}${classroom.word}</a></td>
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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
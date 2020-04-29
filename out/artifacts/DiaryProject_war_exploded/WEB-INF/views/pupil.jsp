<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Дневник ученика</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <h4 class="text-right">
        Вы вошли как ${currentUser.username}(${currentUserAuthorities}) | <a onclick="document.forms['logoutForm'].submit()">Выйти</a>
    </h4>

    <table class="table table-bordered">
        <c:if test="${!empty marks}">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Value</th>
                    <th>Date</th>

                </tr>
                </thead>
                <c:forEach items="${marks}" var="mark">
                    <tbody>
                    <tr>
                        <th>${mark.value}</th>
                        <td>${mark.date}</td>

                    </tr>
                    </tbody>
                </c:forEach>
            </table>
        </c:if>
    </table>

</c:if>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
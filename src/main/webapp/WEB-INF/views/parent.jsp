<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Ученики</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

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

    <table class="table table-blue">

        <thead>
        <td>ID</td>
        <td>Имя Фамилия</td>
        <td>Класс</td>
        </thead>

        <tbody>
        <c:if test="${currentUser.pupils.size() > 0}">
            <c:forEach begin="0" end="${currentUser.pupils.size() - 1}" step="1" var="i">
            <tr>
                <td>${currentUserPupils[i].id}</td>
                <td><a class="pupils" href="/parent/${i + 1}">${currentUserPupils[i].name} ${currentUserPupils[i].surname}</a></td>
                <td>${currentUserPupils[i].classroom.digit}${currentUserPupils[i].classroom.word}</td>
            </tr>
            </c:forEach>
        </c:if>
        </tbody>

    </table>
</c:if>

</div>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<script>

    // $(".pupils").click(function () {
    //     location.href = "/parent/" + $('.pupils').eq(0).attr('id');
    // });

</script>
</html>
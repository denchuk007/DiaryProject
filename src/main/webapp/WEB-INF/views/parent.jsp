<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Ученики</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-default">

    <div class="container-fluid">
        <div class="navbar-header">

            <ul class="nav navbar-nav">

                <li><a href="/welcome">Главная</a></li>
                <li><a href="/parent">Ученики</a></li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Уведомления [${currentUser.notifications.size()}] <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:if test="${currentUser.notifications.size() == 0}">
                            <li><a href="#">Уведомлений нет</a></li>
                        </c:if>
                        <c:forEach items="${currentUser.notifications}" var="notification">
                            <li><a href="/remove/notification/${notification.id}">${notification.text}</a></li>
                        </c:forEach>
                        <li role="separator" class="divider"></li>
                        <li><a href="/remove/notifications/${currentUser.id}" onclick="return confirm('Удалить все уведомления?')">Удалить уведомления</a></li>

                    </ul>
                </li>
            </ul>
        </div>
        <div class="collapse navbar-collapse" id="navbar-main">
            <a class="navbar-brand navbar-right" href="#" onclick="document.forms['logoutForm'].submit()">Вы вошли как ${currentUser.username} | Выход</a>
        </div>
    </div>
</nav>

<div class="container-fluid">
<c:if test="${pageContext.request.userPrincipal.name != null}">

    <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <table class="table table-blue">

        <thead>
        <td>№</td>
        <td>Имя Фамилия</td>
        <td>Класс</td>
        </thead>

        <tbody>
        <c:if test="${currentUser.pupils.size() > 0}">
            <c:forEach begin="0" end="${currentUser.pupils.size() - 1}" step="1" var="i">
            <tr>
                <td>${i + 1}</td>
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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Средний балл</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</c:if>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">

            <ul class="nav navbar-nav">
                <li><a href="/welcome">Главная</a></li>
                <li><a href="/schedule">Расписание</a></li>
                <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Администрирование <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/new-subject">Создать предмет</a></li>
                            <li><a href="/new-classroom">Создать класс</a></li>
                            <li><a href="/registration">Создать пользователя</a></li>
                            <li><a href="/admin">Пользователи</a></li>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${currentUserAuthorities == 'ROLE_PUPIL'}">
                    <li><a href="/pupil">Дневник</a></li>
                </c:if>

                <c:if test="${currentUserAuthorities == 'ROLE_TEACHER'}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Администрирование <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/new-subject">Создать предмет</a></li>
                            <li><a href="/new-classroom">Создать класс</a></li>
                            <li><a href="/registration">Создать пользователя</a></li>
                        </ul>
                    </li>
                    <li><a href="/classrooms">Журнал</a></li>
                </c:if>

                <c:if test="${currentUserAuthorities == 'ROLE_PARENT'}">
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
                </c:if>
            </ul>
        </div>
        <div class="collapse navbar-collapse" id="navbar-main">
            <a class="navbar-brand navbar-right" href="#" onclick="document.forms['logoutForm'].submit()">Вы вошли как ${currentUser.username} | Выход</a>
        </div>
    </div>
</nav>

<div class="container-fluid">
<h3>Средний балл за период</h3>

    <input type="date" class="selectpicker" id="firstDate"/>
    —
    <input type="date" class="selectpicker" id="secondDate"/>

    <button class="btn btn-primary" id="okButton">Принять</button>

    <br><br>

    <c:if test="${iLength != null && jLength != null}">
    <table class="table">
        <thead>
        <tr>
        <td>Предмет</td>
        <td>Средний балл</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach begin="0" end="${iLength - 1}" step="1" var="i">
        <tr>
            <c:forEach begin="0" end="${jLength - 1}" step="1" var="j">
            <td>${totalMarks[i][j]}</td>
            </c:forEach>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>

</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script>
    $("#okButton").click(function () {
        location.href = "/total-mark/" + ${pupilId} + "/" + $("#firstDate").val() + "/" + $("#secondDate").val();
    })
</script>
</body>
</html>
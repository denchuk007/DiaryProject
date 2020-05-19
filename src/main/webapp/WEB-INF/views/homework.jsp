<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Домашнее задание</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">

            <ul class="nav navbar-nav">
                <li><a href="/welcome">Главная</a></li>

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

<div class="container" style="width: 25%">

    <h2 class="form-signin-heading text-center">    Просмотр домашнего задания
    </h2>

    <div class="form-signin">
    <input class="form-control" type="date" id="date" placeholder="Дата"/>
    <button class="btn btn-lg btn-primary btn-block" id="okButton">Принять</button>
    </div>
</div>

<c:if test="${currentClassroomHomework != null && currentClassroomHomework.size() != 0}">
    <br>
<div class="container-fluid">
    <h4>Домашнее задание за ${date}</h4>
    <table class="table table-blue">
        <thead>
        <tr>
            <th>Предмет</th>
            <th>Задание</th>
            <c:if test="${currentUserAuthorities == 'ROLE_TEACHER'}">
            <th>Ред.</th>
                <th>Удалить</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${currentClassroomHomework}" var="homework">
            <tr>
            <td>
                ${homework.subject.title}
            </td>
            <td>
                ${homework.text}
            </td>
                <c:if test="${currentUserAuthorities == 'ROLE_TEACHER'}">
                <td>
                    <a href="/edit/homework/${currentClassroom.id}/${homework.id}"><button class="btn btn-primary">Ред.</button></a>
                </td>
                <td>
                    <a href="/delete/homework/${homework.id}"><button class="btn btn-primary">Удалить</button></a>
                </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</c:if>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<!-- Selectpicker -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/i18n/defaults-*.min.js"></script>

<script>
    $(document).ready(function(){
        $('#subjects').selectpicker({
            liveSearch: true,
            width: "100%",
            maxOptions: 1
        });
    })

    $("#okButton").click(function () {
        location.href = "/homework/" + ${currentClassroom.id} + "/" + $("#date").val();
    })

</script>
</body>
</html>
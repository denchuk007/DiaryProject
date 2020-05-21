<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Расписание</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/i18n/defaults-*.min.js"></script>
</head>
<body>
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

<div class="container" style="width: 25%">

    <h2 class="form-signin-heading text-center">
        Расписание
    </h2>

    <div class="form-signin">
        <select id="select">
            <option selected>Выберите класс</option>
            <c:forEach items="${classrooms}" var="classroom">
                <option value="${classroom.digit}${classroom.word}">${classroom.digit}${classroom.word}</option>
            </c:forEach>
        </select>
        <button class="btn btn-lg btn-primary btn-block" id="okButton">Принять</button>
        <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
        <a href="/new-schedule"><button class="btn btn-lg btn-primary btn-block">Заполнить расписание</button></a>
        </c:if>
    </div>
</div>

<br>
<div class="container-fluid">
    <table class="table">
        <thead>
        <tr>
            <td></td>
            <c:if test="${currentUserAuthorities != 'ROLE_ADMIN'}">
            <td colspan="3"  style="border-top: 1px #000000">Первая неделя</td>
            <td colspan="3"  style="border-top: 1px #000000">Вторая неделя</td>
            </c:if>
            <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
                <td colspan="4"  style="border-top: 1px #000000">Первая неделя</td>
                <td colspan="4"  style="border-top: 1px #000000">Вторая неделя</td>
            </c:if>
        </tr>
        <tr>
            <td>День недели</td>
            <td>Время</td>
            <td>Предмет</td>
            <td>Кабинет</td>
            <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
                <td></td>
            </c:if>
            <td>Время</td>
            <td>Предмет</td>
            <td>Кабинет</td>
            <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
                <td></td>
            </c:if>
        </tr>
        </thead>
        <tbody>
            <c:forEach begin="0" end="29" step="1" var="i">
                <tr>
                    <c:if test="${i == 0}"><td rowspan="6">ПН</td></c:if>
                    <c:if test="${i == 6}"><td rowspan="6" >ВТ</td></c:if>
                    <c:if test="${i == 12}"><td rowspan="6">СР</td></c:if>
                    <c:if test="${i == 18}"><td rowspan="6">ЧТ</td></c:if>
                    <c:if test="${i == 24}"><td rowspan="6">ПТ</td></c:if>


                    <c:forEach begin="0" end="2" step="1" var="j">
                    <td>${firstWeek[i][j]}</td>
                </c:forEach>

                    <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
                        <td>
                            <a href="/edit/schedule/${firstWeek[i][3]}"><button class="btn btn-primary">Ред.</button></a>
                            <a href="/remove/schedule/${firstWeek[i][3]}"><button class="btn btn-primary">Уд.</button></a>
                        </td>
                    </c:if>

                    <c:forEach begin="0" end="2" step="1" var="j">
                        <td>${secondWeek[i][j]}</td>
                    </c:forEach>

                    <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
                        <td>
                            <a href="/edit/schedule/${secondWeek[i][3]}"><button class="btn btn-primary">Ред.</button></a>
                            <a href="/remove/schedule/${secondWeek[i][3]}"><button class="btn btn-primary">Уд.</button></a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script>
    $(document).ready(function(){
        $('select').selectpicker({
            liveSearch: true,
            width: "100%",
            maxOptions: 1
        });
    })

    $("#okButton").click(function () {
        location.href = "/schedule/" + $("#select").val();
    })

</script>
</body>
</html>
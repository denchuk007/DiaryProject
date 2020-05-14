<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Дневник ученика</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/sunny/jquery-ui.css">

</head>
<body style="background: url(https://w-dog.ru/wallpapers/1/80/451075820004097.jpg) no-repeat; background-size: 100%">

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

                    <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
                        <li><a href="/new-subject">Создать предмет</a></li>
                        <li><a href="/new-classroom">Создать класс</a></li>
                        <li><a href="/admin">Пользователи</a></li>
                    </c:if>

                    <c:if test="${currentUserAuthorities == 'ROLE_PUPIL'}">
                        <li><a href="/pupil">Дневник</a></li>
                    </c:if>

                    <c:if test="${currentUserAuthorities == 'ROLE_TEACHER'}">
                        <li><a href="/new-subject">Создать предмет</a></li>
                        <li><a href="/new-classroom">Создать класс</a></li>
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

<c:if test="${currentUser.roles.iterator().next().name == 'ROLE_PARENT'}">
    <h4 class="text-left">
        Оценки ученика ${pupil.name} ${pupil.surname} (${pupil.classroom.digit}${pupil.classroom.word})
    </h4>
</c:if>

<c:if test="${currentUser.roles.iterator().next().name == 'ROLE_TEACHER'}">
    <h4 class="text-left">
            Оценки ученика ${pupil.name} ${pupil.surname}
        <a href="/classroom/${pupil.classroom.digit}${pupil.classroom.word}">
            (${pupil.classroom.digit}${pupil.classroom.word})
        </a>
    </h4>
</c:if>

<div class="container-fluid">
    <select class="text-left selectpicker" id="monthSelect">
        <option selected>Выберите месяц</option>
        <option value="1">Январь</option>
        <option value="2">Февраль</option>
        <option value="3">Март</option>
        <option value="4">Апрель</option>
        <option value="5">Май</option>
        <option value="6">Июнь</option>
        <option value="7">Июль</option>
        <option value="8">Август</option>
        <option value="9">Сентябрь</option>
        <option value="10">Октябрь</option>
        <option value="11">Ноябрь</option>
        <option value="12">Декабрь</option>
    </select>

    <select class="text-left selectpicker" id="yearSelect">
        <option selected>Выберите год</option>
    </select>

    <button class="btn btn-primary" id="okButton">Перейти</button>

    <br>
    <br>

        <table class="table table-striped table-bordered" id="table">
                <thead>
                <tr>
                    <th>Предмет</th>
                    <c:forEach begin="1" end="${lengthOfMonth}" step="1" var="value">
                        <th>${value}</th>
                    </c:forEach>

                </tr>
                </thead>
                    <tbody>
                    <c:if test="${subjectsCount != null && subjectsCount != 0}">
                    <c:forEach begin="0" end="${subjectsCount - 1}" step="1" var="i">
                    <tr>
                        <td>${subjectsTitle[i]}</td>
                        <c:forEach begin="1" end="${lengthOfMonth}" step="1" var="j">
                            <td>
                                <p title="Оценка поставлена учителем: ${marksTable[i][j].teacher.name} ${marksTable[i][j].teacher.surname}">
                                    <c:if test="${currentUserAuthorities != 'ROLE_TEACHER'}">
                                    ${marksTable[i][j].value}
                                    </c:if>

                                    <c:if test="${currentUserAuthorities == 'ROLE_TEACHER'}">
                                        <a id="mark" onclick="dialogOpen(${marksTable[i][j].pupil.id}, ${marksTable[i][j].id})" href="#">${marksTable[i][j].value}</a>
                                    </c:if>
                                </p>
                            </td>
                        </c:forEach>
                    </tr>
                    </c:forEach>
                    </c:if>
                    </tbody>
        </table>

</div>

<div id="dialog" title="Диалоговое окно">
    Выберите действие
</div>

</body>

<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<!-- Selectpicker -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/i18n/defaults-*.min.js"></script>

<script>

    let pupilId;
    let markId;

    $(document).ready(function() {
        let date = new Date();

        for (let i = date.getFullYear() - 10; i < date.getFullYear() + 1; i++) {
            let option = new Option(i, i);
            $("#yearSelect").append(option);
        }

        if (location.pathname == "/pupil" || location.pathname == "/parent/${pupilNumber}" || location.pathname == "/teacher/${pupilId}") {
            $("#monthSelect").val(date.getMonth() + 1);
            $("#yearSelect").val(date.getFullYear());
            $("#okButton").click();
        } else {
            $("#monthSelect").val(${selectedMonth});
            $("#yearSelect").val(${selectedYear});
        }
    });

    $("#okButton").click(function () {
        if ($("#yearSelect").val() != null || $("#monthSelect").val() != null) {
            if(location.pathname.includes("/parent")) {
                location.href = "/parent/" + ${pupilNumber} + "/" + $("#monthSelect").val() + "/" + $("#yearSelect").val();
            } else if (location.pathname.includes("/teacher")) {
                location.href = "/teacher/" + ${pupilId} + "/" + $("#monthSelect").val() + "/" + $("#yearSelect").val();
            } else {
                location.href = "/pupil/" + $("#monthSelect").val() + "/" + $("#yearSelect").val();
            }
        }
    });

    $('#dialog').dialog({
        title: '',
        autoOpen: false,
        resizable: false,
        buttons: [
            {
                text: "Изменить", click: function() {
                    location.href = "/edit/mark/" + getPupilId() + "/" + getMarkId();
                }},
            {
                text: "Удалить", click: function() {
                    location.href = "/remove/mark/" + getPupilId() + "/" + getMarkId();
                }},
            {
                text: "Отмена", click: function() {
                    $(this).dialog("close")
                }}
            ]
    });

    function dialogOpen(pupilId, markId) {
        this.pupilId = pupilId;
        this.markId = markId;
        $('#dialog').dialog("open");
    }

    function getPupilId() {
        return this.pupilId;
    }

    function getMarkId() {
        return this.markId;
    }

</script>

</html>
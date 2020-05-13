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
<body style="background: url(https://w-dog.ru/wallpapers/1/80/451075820004097.jpg) no-repeat; background-size: 100%">

<div class="container-fluid">

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <h4 class="text-right">
        Вы вошли как ${currentUser.username}(${currentUserAuthorities}) | <a onclick="document.forms['logoutForm'].submit()" href="#">Выйти</a>
    </h4>

<c:if test="${currentUser.roles.iterator().next().name != 'ROLE_PUPIL'}">
    <h4 class="text-left">
        Оценки ученика ${pupil.name} ${pupil.surname} (${pupil.classroom.digit}${pupil.classroom.word})
    </h4>
</c:if>

</c:if>

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
                                    ${marksTable[i][j].value}
                                </p>
                            </td>
                        </c:forEach>
                    </tr>
                    </c:forEach>
                    </c:if>
                    </tbody>
        </table>

</div>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<!-- Selectpicker -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/i18n/defaults-*.min.js"></script>

<script>

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

</script>

</html>
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

    <select class="text-left" id="monthSelect">
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

    <select class="text-left" id="yearSelect">
        <option selected>Выберите год</option>
    </select>

    <button id="okButton">Перейти</button>

    <br>
    <br>

            <table class="table table-bordered" id="table">
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
                        <c:forEach begin="0" end="${lengthOfMonth}" step="1" var="j">
                             <td>${marksTable[i][j]}</td>
                        </c:forEach>
                    </tr>
                    </c:forEach>
                    </c:if>
                    </tbody>
            </table>
</c:if>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<script>

    $(document).ready(function() {
        let date = new Date();

        for (let i = date.getFullYear() - 10; i < date.getFullYear() + 1; i++) {
            let option = new Option(i, i);
            $("#yearSelect").append(option);
        }

        if (location.pathname == "/pupil") {
            $("#monthSelect").val(date.getMonth() + 1);
            $("#yearSelect").val(date.getFullYear());
            $("#okButton").click();
        } else {
            $("#monthSelect").val(${selectedMonth});
            $("#yearSelect").val(${selectedYear});
        }
    });

    $("#okButton").click(function () {
        if($("#yearSelect").val() != null || $("#monthSelect").val() != null) {
            location.href = "/pupil/" + $("#monthSelect").val() + "/" + $("#yearSelect").val();
        }
    })

</script>
</html>
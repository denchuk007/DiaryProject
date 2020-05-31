<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Анализ</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/sunny/jquery-ui.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages':['line']});
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {

            var data = new google.visualization.DataTable();
            data.addColumn('number', 'Номер месяца');

            <c:forEach begin="0" end="${subjectsCount - 1}" step="1" var="i">
            data.addColumn('number', '${subjectsTitle[i]}');
            </c:forEach>

            let mas = [];
            <c:forEach begin="0" end="11" step="1" var="i">
            <c:if test="${arrayToChart[i][1] != 0}">
                mas[${i}] = [];
                    <c:forEach begin="0" end="${subjectsCount}" step="1" var="j">
                    mas[${i}][${j}] = ${arrayToChart[i][j]};
                    </c:forEach>
            </c:if>
                </c:forEach>

            data.addRows(mas);

            var options = {
                chart: {
                    title: 'График прогресса оценок в течение года',
                    //subtitle: 'in millions of dollars (USD)'
                },
                width: 900,
                height: 500
            };

            var chart = new google.charts.Line(document.getElementById('linechart_material'));

            chart.draw(data, google.charts.Line.convertOptions(options));
        }
    </script>

    <script type="text/javascript">
        google.charts.load('current', {'packages':['line']});
        google.charts.setOnLoadCallback(drawChart2);

        function drawChart2() {

            var data = new google.visualization.DataTable();
            data.addColumn('number', 'Номер месяца');

            <c:forEach begin="0" end="${subjectsCount - 1}" step="1" var="i">
            data.addColumn('number', '${subjectsTitle[i]}');
            </c:forEach>

            let mas = [];
            <c:forEach begin="0" end="11" step="1" var="i">
            <c:if test="${arrayToChartTotal[i][1] != 0}">
            mas[${i}] = [];
            <c:forEach begin="0" end="${subjectsCount}" step="1" var="j">
            mas[${i}][${j}] = ${arrayToChartTotal[i][j]};
            </c:forEach>
            </c:if>
            </c:forEach>

            data.addRows(mas);

            var options = {
                chart: {
                    title: 'График прогресса оценок в течение года (общий)',
                    //subtitle: 'in millions of dollars (USD)'
                },
                width: 900,
                height: 500
            };

            var chart = new google.charts.Line(document.getElementById('linechart_material2'));

            chart.draw(data, google.charts.Line.convertOptions(options));
        }
    </script>
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

                <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
                    <li><a href="/new-subject">Создать предмет</a></li>
                    <li><a href="/new-classroom">Создать класс</a></li>
                    <li><a href="/admin">Пользователи</a></li>
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
            <a class="navbar-brand navbar-right" href="#" onclick="document.forms['logoutForm'].submit()">Вы вошли как ${pageContext.request.userPrincipal.name} | Выход</a>
        </div>
    </div>
</nav>

<div class="container-fluid">

    <h3>Анализ оценок за ${year} год</h3>

    <h4>Лучшие предметы (по оценкам): </h4>
    <c:forEach begin="0" end="2" step="1" var="i">
        ${top3SubjectsTitle[i]}: ${top3SubjectsMarks[i]}
        <br>
    </c:forEach>

    <table class="table table-blue">
        <c:if test="${subjectsCount != 0}">
        <thead class="text-center">
        <tr>
            <th></th>

            <th style="text-align: center">Январь</th>
            <th style="text-align: center">Февраль</th>
            <th style="text-align: center">Март</th>
            <th style="text-align: center">Апрель</th>
            <th style="text-align: center">Май</th>
            <th style="text-align: center">Июнь</th>
            <th style="text-align: center">Июль</th>
            <th style="text-align: center">Август</th>
            <th style="text-align: center">Сентябрь</th>
            <th style="text-align: center">Октябрь</th>
            <th style="text-align: center">Ноябрь</th>
            <th style="text-align: center">Декабрь</th>

        </tr>
        </thead>

        <tbody>
        <c:forEach begin="0" end="${subjectsCount - 1}" step="1" var="i">
            <tr>
                <td style="text-align: left">
                    ${subjectsTitle[i]}
                </td>
            <c:forEach begin="0" end="11" step="1" var="j">
                <td>
                    ${resultTable[i][j]}
                    (${totalResultTable[i][j]})
                </td>
            </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
        </c:if>
    </table>

    <div id="linechart_material" style="width: 900px; height: 500px"></div>
    <div id="linechart_material2" style="width: 900px; height: 500px"></div>


</div>

</body>

<script>
    let date = new Date();

    for (let i = date.getFullYear() - 10; i < date.getFullYear() + 1; i++) {
        let option = new Option(i, i);
        $("#yearSelect").append(option);
    }

</script>

<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<!-- Selectpicker -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/i18n/defaults-*.min.js"></script>


</html>
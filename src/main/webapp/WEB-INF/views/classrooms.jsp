<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Школьные классы</title>
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
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Администрирование <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/new-subject">Создать предмет</a></li>
                            <li><a href="/new-classroom">Создать класс</a></li>
                            <li><a href="/registration">Создать пользователя</a></li>
                        </ul>
                    </li>
                    <li><a href="/classrooms">Журнал</a></li>
                </ul>
            </div>
            <div class="collapse navbar-collapse" id="navbar-main">
                <a class="navbar-brand navbar-right" href="#" onclick="document.forms['logoutForm'].submit()">Вы вошли как ${currentUser.username} | Выход</a>
            </div>
        </div>
    </nav>

<div class="container-fluid">
<c:if test="${!empty classrooms}">
    <table class="table table-blue">
        <thead>
            <th>№</th>
            <th>Школьные классы</th>
            <th>Домашнее задание</th>
        </thead>
        <c:forEach items="${classrooms}" var="classroom">
            <tbody>
            <tr>
                <th>${classrooms.indexOf(classroom) + 1}</th>
                <td>
                    <a href="/classroom/${classroom.digit}${classroom.word}">
                            ${classroom.digit}${classroom.word}
                    </a>
                    <c:if test="${currentUser.classroom.id == classroom.id}">
                        (Ваш класс)
                    </c:if>
                </td>
                <td>
                    <a href="/homework/${classroom.id}">
                        <button class="btn btn-primary">Просмотр</button>
                    </a>
                    <a href="/new-homework/${classroom.id}">
                        <button class="btn btn-primary">+</button>
                    </a>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</c:if>
</div>

<script>
    $(document).ready(function(){
        $(".table").css("display", "block !important");
    });
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
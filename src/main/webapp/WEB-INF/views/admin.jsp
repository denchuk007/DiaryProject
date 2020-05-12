<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Admin</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<a href="${contextPath}/welcome"><button class="btn btn-primary pull-left"> <= Назад </button></a>
<a href="${contextPath}/registration"><button class="btn btn-primary pull-right">Создать аккаунт</button></a>
<br>
<br>

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="post" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <h4 class="text-right">Вы вошли как ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()" href="#">Выйти</a>
        </h4>
    </c:if>

<c:if test="${!empty listUsers}">
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Логин</th>

            <th>Роль</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Дата рождения</th>
            <th>Класс</th>
            <th>Дети</th>
            <th>Ред.</th>
            <th>Удалить</th>
        </tr>
        </thead>
        <c:forEach items="${listUsers}" var="user">
        <tbody>
            <tr>
                <th>${user.id}</th>
                <td>${user.username}</td>

                <td>
                    <c:if test="${!empty user.roles}">
                        <c:forEach items="${user.roles}" var="role">
                            <c:if test="${role.name == 'ROLE_PUPIL'}">
                                Ученик
                            </c:if>
                            <c:if test="${role.name == 'ROLE_PARENT'}">
                                Родитель
                            </c:if>
                            <c:if test="${role.name == 'ROLE_TEACHER'}">
                                Учитель
                            </c:if>
                            <c:if test="${role.name == 'ROLE_ADMIN'}">
                                <span style="color: red;">Администратор</span>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.birthday}</td>
                <td>${user.classroom.digit}${user.classroom.word}</td>
                <td>
                    <c:forEach items="${user.pupils}" var="pupil">
                        ${pupil.name} ${pupil.surname}
                    </c:forEach>
                </td>
                <td><a href="/edit/${user.id}"><button class="btn btn-primary">Ред.</button></a></td>
                <td>
                    <form action="${contextPath}/admin" method="post">
                        <input id="userId" type="hidden" name="userId" value="${user.id}"/>
                        <input id="action" type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-primary" onclick="return confirm('Удалить пользователя ${user.username}?')">Удалить</button>
                    </form>
                </td>
            </tr>
        </tbody>
        </c:forEach>
    </table>
</c:if>
<script>
    $(document).ready(function(){
        $(".table").css("display", "block !important");
    });
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
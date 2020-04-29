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

<a href="${contextPath}/registration"><button class="btn btn-primary pull-right">Создать аккаунт</button></a>
<br>

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="post" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <h3 class="text-right">Админ панель ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Выйти</a>
        </h3>
    </c:if>

<span><c:forEach items="${loggedUser.authorities}" var="authorities"> ${authorities.authority} </c:forEach></span>

<c:if test="${!empty listUsers}">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Password</th>
            <th>Roles</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <c:forEach items="${listUsers}" var="user">
        <tbody>
            <tr>
                <th>${user.id}</th>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>
                    <c:if test="${!empty user.roles}">
                        <c:forEach items="${user.roles}" var="role">
                            ${role.name}
                        </c:forEach>
                    </c:if>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin" method="post">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <input type="hidden" name="action" value="edit"/>
                        <button type="submit">Edit</button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin" method="post">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit">Delete</button>
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
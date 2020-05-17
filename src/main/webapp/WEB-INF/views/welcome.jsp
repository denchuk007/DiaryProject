<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Добро пожаловать</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <style>

        #container {
            width:810px;
            height:300px;
            line-height:15px;
        }
        .img {
            width:300px;
            height:150px;
            border:1px solid #CCC;
            padding:10px;
            float:left;
        }
        .content {
            width: 80%;
            color:#333;
            margin-left:10px;
            padding:10px;
            float:left;
        }
        .date{
            width:250px;
            height:15px;
            color:#999;
        }
        .title{
            width:300px;
            margin-top:5px;
            font-weight:bold;
            clear:both;
            font-size:16px;
        }
        .text{
            width:100%;
            margin-top:5px;
            font-size:14px;
            overflow:hidden;
        }
        .next{
            width:120px;
            clear:both;
            font-weight:bold;}
        .next b{
            color:#F93;
        }

        * {
            box-sizing: border-box;
        }

        .col-1 {
            width: 33.333%;
            margin: .5rem;
        }

        .col-2 {
            width: 66.666%;
            margin: .5rem;
        }

        .row {
            display: flex;
            flex-flow: row nowrap;

            justify-content: space-between;
        }
        * {
            margin: 0;
            box-sizing: border-box;
        }
        .post-wrap {
            width: 20%;
            display: flex;
            flex-wrap: wrap;
        }
        .post-item {
            padding: 10px;
        }
        .post-item-wrap {
            background: #F7F7F2;
            position: relative;
        }
        .post-item-wrap:after {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: -1;
            background: #C9F2C7;
            transform: rotate(1deg);
            transform-origin: left bottom;
            transition: .3s ease-in-out;
        }

        .post-link {
            text-decoration: none;
            display: block;
            padding: 30px;
        }
        .post-title {
            color: #33261D;
            margin-bottom: 15px;
            transition: .3s ease-in-out;
        }

        .post-content {
            color: #A69888;
            font-size: 14px;
        }
        @media (min-width: 768px) {
            .post-item {
                flex-basis: 50%;
                flex-shrink: 0;
            }
        }
        @media (min-width: 960px) {
            .post-item {
                flex-basis: 33.333333333%;
            }
        }
    </style>
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
<!--
<div style="margin-left: 15px; width: 15%; background-color: darkgray; border-color: black; border-radius: 10px;">
    -->



<div class="row">
    <div class="col-1">
        <div class="post-wrap" style="margin-left: 15px">
            <div class="post-item">
                <div class="post-item-wrap">
            <span class="post-link">
                <h3 class="post-title">Именинники</h3>
                <p class="post-content" >
                    <c:forEach items="${birthdays}" var="birthday">
                        <span>${birthday}</span>
                        <br><br>
                    </c:forEach>
                </p>
            </span>
                </div>
            </div>
        </div>
    </div>
    <div class="col-2">

        <c:forEach items="${news}" var="news">
            <div class="content">

                <div class="date">
                        ${news.date}
                            <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">
                            |
                    <a href="/edit/post/${news.id}"> Редактировать</a>
                            |
                    <a  href="/remove/post/${news.id}"> Удалить</a>
                            </c:if>
                </div>

                <div class="title">${news.title}</div>
                <div class="text">
                        ${news.text}
                </div>

            </div>

        </c:forEach>

        <c:if test="${currentUserAuthorities == 'ROLE_ADMIN'}">

        <div class="content">
            <form:form method="POST" modelAttribute="newsForm" class="form-signin">
                <spring:bind path="text">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="hidden" path="id" class="form-control"></form:input>
                        <form:input type="hidden" path="date" class="form-control"></form:input>
                        <div style="width: 600px">
                            <form:input type="text" path="title" class="form-control" placeholder="Название"></form:input>
                            <br>
                        <form:textarea type="text" path="text" class="form-control" placeholder="Текст"
                                    autofocus="true"></form:textarea>
                        <form:errors path="text"></form:errors>
                        </div>
                    </div>
                </spring:bind>

                <button class="btn btn-lg btn-primary btn-block" style="width: 20%" type="submit">Принять</button>
            </form:form>
        </div>

        </c:if>
</div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
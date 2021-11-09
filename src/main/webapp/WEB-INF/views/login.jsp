<%@ page contentType="text/html;charset=UTF-8" language="java" buffer="8192kb" autoFlush="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fer" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <fer:head jspName="login"/>
</head>
<body>
<div class="modal fade" id="login" data-bs-backdrop="static" data-ba-keyboarf="false" tabindex="-1"
     aria-labelledby="siteInfoLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content" id="login-content">
            <div class="modal-header" id="login-header">
                <div class="login-registration rb active-header" id="reg-button" type="button">
                    Регистрация
                </div>
                <div class="login-registration lb" id="login-button" type="button">
                    Вход в аккаунт
                </div>
            </div>
            <div class="modal-body login-scrolling" id="login-body">
                <div class="semi-header" id="reg-semi-header">Зарегистрировать новый аккаунт</div>
                <div class="semi-body" id="reg-semi-body">
                    <form method="post" action="">
                        <input id="nickname" name="nickname" class="login-input" placeholder="Введите никнейм"
                               type="text" value="${cuberLogin.nickname}"
                               autocomplete="off" required>
                        <c:if test="${exception=='nicknameMatch'}">
                            <div class="exception">Пользователь с таким никнеймом уже существует</div>
                        </c:if>
                        <input id="mail" name="mail" class="login-input" placeholder="*Введите электронную почту"
                               type="email" value="${cuberLogin.email}"
                               autocomplete="off">
                        <c:if test="${exception=='emailMatch'}">
                            <div class="exception">Эта почта уже привязана к аккаунту другого пользователя</div>
                        </c:if>
                        <c:if test="${exception=='incorrectEmail'}">
                            <div class="exception">Некорректный адрес почты</div>
                        </c:if>
                        <c:if test="${exception=='sendEmailProblem'}">
                            <div class="exception">Проблема с отправкой письма, попробуйте привязать почту позднее</div>
                        </c:if>
                        <input id="password-1" name="password-1" class="login-input incorrect-password"
                               placeholder="Введите пароль" type="password" value="${cuberLogin.password}"
                               autocomplete="off" required>

                        <div id="weak-password" class="exception">Пароль должен быть не менее 8 сиволов в длину и
                            содержать строчную,
                            заглавную букву и цифру
                        </div>

                        <input id="password-2" name="password-2" class="login-input" placeholder="Повторите пароль"
                               type="password" value="${confirmPassword}"
                               autocomplete="off" required>

                        <div id="password-match" class="exception">Пароли не совпадают</div>

                        <button type="submit" class="login-button">Зарегистрировать</button>
                        <c:if test="${exception=='dataBaseProblem'}">
                            <div class="exception">Проблема с базой данных, попробуйте зарегистрироваться позднее</div>
                        </c:if>
                    </form>
                    <p>*Необязательное поле, но даст возможность обратной связи и восстановления никнейма/пароля. Можно
                        указать позднее</p>
                </div>

                <div class="semi-header" id="login-semi-header">Войти в существующий аккаунт</div>
                <div class="semi-body" id="login-semi-body">
                    <form method="post" action="">
                        <c:if test="${registrationDone==true}">
                            <div class="good">Регистрация прошла успешно. Зайдите в созданный аккаунт</div>
                        </c:if>
                        <input id="nickname-login" name="nickname-login" class="login-input"
                               placeholder="Введите никнейм" type="text" value="${cuber.nickname}"
                               autocomplete="off" required>
                        <c:if test="${exception=='noSuchUser'}">
                            <div class="exception">Пользователя с таким никнеймом не существует</div>
                        </c:if>
                        <input id="password-login" name="password-login" class="login-input"
                               placeholder="Введите пароль" type="password" value="${cuber.password}"
                               autocomplete="off" required>
                        <c:if test="${exception=='incorrectPassword'}">
                            <div class="exception">Неправильный пароль</div>
                        </c:if>
                        <div><label for="remember">Запомнить меня</label><input type="checkbox"
                                                                                id="remember" name="remember"></div>
                        <button type="submit" class="login-button">Войти</button>
                        <button type="button" id="forgot-password" class="login-button">Забыли никнейм/пароль</button>
                        <c:if test="${exception=='dataBaseProblem'}">
                            <div class="exception">Проблема с базой данных, попробуйте зарегистрироваться позднее</div>
                        </c:if>
                    </form>
                </div>

                <div class="semi-header" id="forgot-semi-header">Восстановление никнейма/пароля</div>
                <div class="semi-body" id="forgot-semi-body">
                    <form method="post" action="">
                        <input id="forgot-mail" name="forgot-mail" class="login-input"
                               placeholder="Введите электронную почту" type="email"
                               autocomplete="off" required>
                        <c:if test="${exception=='emailMatch-r'}">
                            <div class="exception">Эта почта не привязана ни к одному аккаунту</div>
                        </c:if>
                        <c:if test="${exception=='incorrectEmail-r'}">
                            <div class="exception">Некорректный адрес почты</div>
                        </c:if>
                        <c:if test="${exception=='sendEmailProblem-r'}">
                            <div class="exception">Проблема с отправкой письма, попробуйте восстановить аккаунт
                                позднее
                            </div>
                        </c:if>
                        <button type="submit" class="login-button">Отправить письмо</button>
                        <c:if test="${exception=='dataBaseProblem-r'}">
                            <div class="exception">Проблема с базой данных, попробуйте восстановить аккаунт позднее
                            </div>
                        </c:if>
                    </form>
                </div>

                <div class="semi-header" id="mail-semi-header">На указанную почту было отправлено письмо с кодом
                    подтверждения
                </div>
                <div class="semi-body" id="mail-semi-body">
                    <form action="" method="post">
                        <input id="confirm-mail" name="confirm-mail" class="login-input"
                               placeholder="Введите код из письма" type="text"
                               autocomplete="off" required>
                        <c:if test="${exception=='incorrectCode'}">
                            <div class="exception">Неправильный код</div>
                        </c:if>
                        <button type="submit" class="login-button">Отправить</button>
                        <c:if test="${exception=='dataBaseProblem'}">
                            <div class="exception">Проблема с базой данных, попробуйте зарегистрироваться позднее</div>
                        </c:if>
                    </form>
                </div>

                <div class="semi-header" id="mail2-semi-header">На указанную почту было отправлено письмо с кодом
                    для входа в аккаунт
                </div>
                <div class="semi-body" id="mail2-semi-body">
                    <form action="" method="post">
                        <input id="confirm-mail2" name="confirm-mail2" class="login-input"
                               placeholder="Введите код из письма" type="text"
                               autocomplete="off" required>
                        <c:if test="${exception=='incorrectCode-r'}">
                            <div class="exception">Неправильный код</div>
                        </c:if>
                        <button type="submit" class="login-button">Войти</button>
                        <c:if test="${exception=='dataBaseProblem'}">
                            <div class="exception">Проблема с базой данных, попробуйте зарегистрироваться позднее</div>
                        </c:if>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<div class="wrapper container">
    <fer:navigationRow currentName="algorithms"/>

    <div class="row" id="icons">
        <div class="col-12">
            <form id="cubeIconsForm" action="" method="get">
                <label aria-label="Показать все">
                    <input class="cubeRadio" type="radio" id="all" name="cube" value="all"
                           <c:if test="${param.cube=='all' || param.cube==null}">checked</c:if>/>
                    <img class="cubeIcons" src="${pageContext.request.contextPath}/static/img/cubeIcons/All.png">
                </label>
                <c:forEach items="${cubes}" var="cube">
                    <label aria-label="${cube.description}">
                        <input class="cubeRadio" type="radio" id="${cube.name}" name="cube" value="${cube.name}"
                               <c:if test="${param.cube==cube.name}">checked</c:if>/>
                        <img class="cubeIcons" src="${pageContext.request.contextPath}${cube.image}">
                    </label>
                </c:forEach>
            </form>
        </div>
    </div>

    <div class="row scrolling" id="methods">

        <c:forEach items="${methods}" var="method">
            <div class="col-6 col-md-4">
                <a href="${pageContext.request.contextPath}/algorithms/method?name=${method.name}">
                    <div class="card">
                        <div class="card-header">
                            <span>${method.name}</span>
                        </div>
                        <img class="card-img-top"
                             src="${pageContext.request.contextPath}${method.image}">
                    </div>
                </a>
            </div>
        </c:forEach>

    </div>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        let modal = new bootstrap.Modal(document.getElementById('login'), {keyboard: false});
        modal.show();
    })

    function hideElements() {
        document.getElementById('reg-semi-header').style.display = "none";
        document.getElementById('reg-semi-body').style.display = "none";
        document.getElementById('mail-semi-header').style.display = "none";
        document.getElementById('mail-semi-body').style.display = "none";
        document.getElementById('mail2-semi-header').style.display = "none";
        document.getElementById('mail2-semi-body').style.display = "none";
    }

    <c:if test="${intoLogin==true}">
    <fer:loginHide login="block" forgot="none"/>
    </c:if>
    <c:if test="${intoRecovery==true}">
    <fer:loginHide login="none" forgot="block"/>
    </c:if>
</script>
</body>
</html>
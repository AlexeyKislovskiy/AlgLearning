<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fer" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <fer:head jspName="account"/>
</head>
<body>
<fer:moderatorButton description="К странице модерации"/>
<div class="wrapper container">
    <fer:navigationRow currentName="account"/>

    <div class="row row-x" id="account-header">
        <div class="col-1"></div>
        <div class="col-10 account-header">
            <form action="" method="post">
                <input class="hidden-form" name="delete" value="delete">
                <div class="img-wrapper form-sender" id="delete" aria-label="Удалить аккаунт"><img class="account-img"
                                                                                                   type="button"
                                                                                                   src="${pageContext.request.contextPath}/static/img/icons/delete_grey.png">
                </div>
            </form>
            ${cuber.nickname}
            <form action="" method="post">
                <input class="hidden-form" name="exit" value="exit">
                <div class="img-wrapper form-sender" id="exit" aria-label="Выйти из аккаунта"><img class="account-img"
                                                                                                   type="button"
                                                                                                   src="${pageContext.request.contextPath}/static/img/icons/exit.png">
                </div>
            </form>
        </div>
        <div class="col-1"></div>
    </div>

    <div class="row row-x">
        <div class="col-1"></div>
        <div class="col-md-3 col-10 acc-card">
            <div class="acc-header" id="acc-header">
                Информация
            </div>
            <div class="acc-body" id="acc-info">
                <div>Дата регистрации: ${cuber.registrationDate}</div>
                <div>Заходили дней: ${cuber.visitedDays}</div>
                <div>Заходили подряд дней: ${cuber.visitedDaysRow}</div>
                <div><span class="learning">Ситуаций в изучаемых:</span> ${numOfLearning}</div>
                <div><span class="learned">Ситуаций в изученных:</span> ${numOfLearned}</div>
            </div>
        </div>
        <div class="col-1"></div>
        <div class="col-1" id="col-1-insert"></div>
        <div class="col-md-6 col-10 acc-card">
            <div class="acc-header" id="stat-header">
                Статистика
            </div>
            <div class="header-2-wrap" id="header-3">
                <div class="acc-body-header rb">
                    Изучение
                    <img class="acc-img" src="${pageContext.request.contextPath}/static/img/icons/learning.png">
                </div>
                <div class="acc-body-header lb">
                    Тренировка
                    <img class="acc-img" src="${pageContext.request.contextPath}/static/img/icons/training.png">
                </div>
            </div>

            <div class="acc-body scrolling" id="stat-info">
                <div class="header-2" id="header-2">

                    <fer:statistics name="За последний день" n1="${day.get(2)}" n2="${day.get(0)}" n3="${day.get(1)}"
                                    n4="${day.get(3)}"/>

                    <fer:statistics name="За последнюю неделю" n1="${week.get(2)}" n2="${week.get(0)}"
                                    n3="${week.get(1)}"
                                    n4="${week.get(3)}"/>

                    <fer:statistics name="За последний месяц" n1="${month.get(2)}" n2="${month.get(0)}"
                                    n3="${month.get(1)}"
                                    n4="${month.get(3)}"/>

                    <fer:statistics name="За последний год" n1="${year.get(2)}" n2="${year.get(0)}" n3="${year.get(1)}"
                                    n4="${year.get(3)}"/>

                    <fer:statistics name="За все время" n1="${all.get(2)}" n2="${all.get(0)}" n3="${all.get(1)}"
                                    n4="${all.get(3)}"/>

                </div>

            </div>
        </div>
    </div>

</div>
<fer:moderatorButtonAction into="/moderator"/>
</body>
</html>
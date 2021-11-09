<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fer" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <fer:head jspName="settings"/>
</head>
<body>
<fer:moderatorButton description="К странице модерации"/>
<div class="wrapper container">
    <fer:navigationRow currentName="settings"/>


    <div class="row scrolling" id="setting-scrolling">
        <div class="col-1"></div>
        <div class="col-10 setting-header">
            <div type="button" class="reset-all" aria-label="Сбросить все настройки"><img class="setting-img"
                                                                                          src="${pageContext.request.contextPath}/static/img/icons/settings.png">
            </div>
            Изучение
            <div type="button" id="reset-learning" class="img-r" aria-label="Сбросить все настройки в блоке"><img
                    class="setting-img"
                    src="${pageContext.request.contextPath}/static/img/icons/learning.png">
            </div>
        </div>
        <div class="col-1"></div>

        <div class="col-1"></div>
        <div class="col-10 setting-body">
            <div class="col-12 setting-body-header">Показывать информацию о ситуации по умолчанию</div>
            <form class="col-12 setting-body-content" action="" method="post">
                <div class="form-sender"><label for="setting1-1">Нет</label><input type="radio"
                                                                                   name="setting1"
                                                                                   value="no"
                                                                                   id="setting1-1"
                                                                                   <c:if test="${setting1==null || setting1.value=='no'}">checked</c:if>>
                </div>
                <div class="form-sender"><label for="setting1-2">Да</label><input type="radio" name="setting1"
                                                                                  value="yes"
                                                                                  id="setting1-2"
                                                                                  <c:if test="${setting1.value=='yes'}">checked</c:if>>
                </div>
            </form>

            <div class="col-12 setting-body-header">Скрывать количество алгоритмов до открытия</div>
            <form class="col-12 setting-body-content" action="" method="post">
                <div class="form-sender"><label for="setting2-1">Нет</label><input type="radio" name="setting2"
                                                                                   value="no" id="setting2-1"
                                                                                   <c:if test="${setting2==null || setting2.value=='no'}">checked</c:if>>
                </div>
                <div class="form-sender"><label for="setting2-2">Да</label><input type="radio" name="setting2"
                                                                                  value="yes" id="setting2-2"
                                                                                  <c:if test="${setting2.value=='yes'}">checked</c:if>>
                </div>
            </form>

            <div class="col-12 setting-body-header">Время до следующего повторения относительно последнего промежутка
            </div>
            <div class="col-12 setting-body-content">

                <div>Трудно: X
                    <input type="text" class="time-multi" name="hard" id="hard-text" value="1" autocomplete="off"
                    >
                </div>

                <div>Средне: X
                    <input type="text" class="time-multi" name="medium" id="medium-text" value="1.5" autocomplete="off"
                    >
                </div>

                <div>Легко: X
                    <input type="text" class="time-multi" name="easy" id="easy-text" value="2" autocomplete="off"
                    >
                </div>

            </div>

            <div class="col-12 setting-body-header">Полностью сбрасывать при забывании
            </div>
            <div class="col-12 setting-body-content">


                <div><label for="forgot-1">Нет</label><input type="radio" name="forgot" id="forgot-1">
                    X
                    <input type="text" class="time-multi" name="hard" id="forgot-text" value="0.1" autocomplete="off"
                           disabled
                    >
                </div>
                <div><label for="forgot-2">Да</label><input type="radio" name="forgot" id="forgot-2"
                                                            checked></div>

            </div>

            <div class="col-12 setting-body-header">Базовое время первого повторения(в днях)
            </div>
            <div class="col-12 setting-body-content">

                <div>
                    <input type="text" class="time-multi int-multi" name="first-learning" id="first-learning-text"
                           value="1" autocomplete="off"
                    >
                </div>

            </div>


        </div>
        <div class="col-1"></div>


        <div class="col-1"></div>
        <div class="col-10 setting-header">
            <div type="button" class="reset-all" aria-label="Сбросить все настройки"><img class="setting-img"
                                                                                          src="${pageContext.request.contextPath}/static/img/icons/settings.png">
            </div>
            Тренировка
            <div type="button" id="reset-training" class="img-r" aria-label="Сбросить все настройки в блоке"><img
                    class="setting-img"
                    src="${pageContext.request.contextPath}/static/img/icons/training.png">
            </div>
        </div>
        <div class="col-1"></div>

        <div class="col-1"></div>
        <div class="col-10 setting-body">
            <div class="col-12 setting-body-header">Показывать информацию о ситуации по умолчанию</div>
            <form class="col-12 setting-body-content" action="" method="post">
                <div class="form-sender"><label for="setting6-1">Нет</label><input type="radio"
                                                                                   name="setting6"
                                                                                   value="no"
                                                                                   id="setting6-1"
                                                                                   <c:if test="${setting6==null || setting6.value=='no'}">checked</c:if>>
                </div>
                <div class="form-sender"><label for="setting6-2">Да</label><input type="radio" name="setting6"
                                                                                  value="yes"
                                                                                  id="setting6-2"
                                                                                  <c:if test="${setting6.value=='yes'}">checked</c:if>>
                </div>
            </form>


            <div class="col-12 setting-body-header">Скрывать количество алгоритмов до открытия</div>
            <form class="col-12 setting-body-content" action="" method="post">
                <div class="form-sender"><label for="setting7-1">Нет</label><input type="radio" name="setting7"
                                                                                   value="no" id="setting7-1"
                                                                                   <c:if test="${setting7==null || setting7.value=='no'}">checked</c:if>>
                </div>
                <div class="form-sender"><label for="setting7-2">Да</label><input type="radio" name="setting7"
                                                                                  value="yes" id="setting7-2"
                                                                                  <c:if test="${setting7.value=='yes'}">checked</c:if>>
                </div>
            </form>

            <div class="col-12 setting-body-header">Включать по умолчанию</div>
            <div class="col-12 setting-body-content">
                <div><label for="stand-check-unlearned">Неизученные</label><input type="checkbox"
                                                                                  id="stand-check-unlearned"></div>
                <div><label for="stand-check-learning">Изучаемые</label><input type="checkbox" id="stand-check-learning"
                                                                               checked></div>
                <div><label for="stand-check-learned">Изученные</label><input type="checkbox" id="stand-check-learned"
                                                                              checked></div>
            </div>


        </div>
        <div class="col-1"></div>


        <div class="col-1"></div>
        <div class="col-10 setting-header">
            <div type="button" class="reset-all" aria-label="Сбросить все настройки"><img class="setting-img"
                                                                                          src="${pageContext.request.contextPath}/static/img/icons/settings.png">
            </div>
            Алгоритмы
            <div type="button" id="reset-algorithms" class="img-r" aria-label="Сбросить все настройки в блоке"><img
                    class="setting-img"
                    src="${pageContext.request.contextPath}/static/img/icons/algorithms.png">
            </div>
        </div>
        <div class="col-1"></div>

        <div class="col-1"></div>
        <div class="col-10 setting-body">

            <div class="col-12 setting-body-header">Сортировка по умолчанию</div>
            <form class="col-12 setting-body-content" action="" method="post">
                <div class="form-sender"><label for="setting9-1">По популярности</label><input type="radio"
                                                                                               name="setting9"
                                                                                               id="setting9-1"
                                                                                               value="popularity"
                                                                                               <c:if test="${setting9==null || setting9.value=='popularity'}">checked</c:if>>
                </div>
                <div class="form-sender"><label for="setting9-2">По длине</label><input type="radio" name="setting9"
                                                                                        id="setting9-2"
                                                                                        value="length"
                                                                                        <c:if test="${setting9.value=='length'}">checked</c:if>>
                </div>
            </form>

            <div class="col-12 setting-body-header">Показывать сначала используемые алгоритмы</div>
            <div class="col-12 setting-body-content">
                <div><label for="used-top1">Нет</label><input type="radio" name="used-top" id="used-top1" checked
                ></div>
                <div><label for="used-top2">Да</label><input type="radio" name="used-top" id="used-top2">
                </div>
            </div>

            <div class="col-12 setting-body-header">Учитывать перехваты при расчете длины</div>
            <form class="col-12 setting-body-content" action="" method="post">
                <div class="form-sender"><label for="setting11-1">Нет</label><input type="radio" name="setting11"
                                                                                    id="setting11-1" value="no"
                                                                                    <c:if test="${setting11==null || setting11.value=='no'}">checked</c:if>>
                </div>
                <div class="form-sender"><label for="setting11-2">Да</label><input type="radio" name="setting11"
                                                                                   id="setting11-2" value="yes"
                                                                                   <c:if test="${setting11.value=='yes'}">checked</c:if>>
                </div>
            </form>

            <div class="col-12 setting-body-header">Учитывать двойные движения как два хода</div>
            <form class="col-12 setting-body-content" action="" method="post">
                <div class="form-sender"><label for="setting12-1">Нет</label><input type="radio" name="setting12"
                                                                                    id="setting12-1" value="no"
                                                                                    <c:if test="${setting12==null || setting12.value=='no'}">checked</c:if>>
                </div>
                <div class="form-sender"><label for="setting12-2">Да</label><input type="radio" name="setting12"
                                                                                   id="setting12-2" value="yes"
                                                                                   <c:if test="${setting12.value=='yes'}">checked</c:if>>
                </div>
            </form>

            <div class="col-12 setting-body-header">Добавить все ситуации метода в изученные</div>
            <div class="col-12 setting-body-content">
                <div><label for="into-learned1">Все</label><input type="radio" name="into-learned" id="into-learned1"
                                                                  checked></div>
                <div><label for="into-learned2">Только изучаемые</label><input type="radio" name="into-learned"
                                                                               id="into-learned2">
                </div>
                <div><label for="into-learned3">Только неизученные</label><input type="radio" name="into-learned"
                                                                                 id="into-learned3">
                </div>
            </div>

            <div class="col-12 setting-body-header">Добавить все ситуации метода в изучаемые</div>
            <div class="col-12 setting-body-content">
                <div><label for="into-learning1">Все</label><input type="radio" name="into-learning" id="into-learning1"
                                                                   checked
                ></div>
                <div><label for="into-learning2">Только изученные</label><input type="radio" name="into-learning"
                                                                                id="into-learning2">
                </div>
                <div><label for="into-learning3">Только неизученные</label><input type="radio" name="into-learning"
                                                                                  id="into-learning3">
                </div>
            </div>

            <div class="col-12 setting-body-header">Удалить все ситуации метода</div>
            <div class="col-12 setting-body-content">
                <div><label for="into-unlearned1">Все</label><input type="radio" name="into-unlearned"
                                                                    id="into-unlearned1"
                                                                    checked
                ></div>
                <div><label for="into-unlearned2">Только изученные</label><input type="radio" name="into-unlearned"
                                                                                 id="into-unlearned2">
                </div>
                <div><label for="into-unlearned3">Только изучаемые</label><input type="radio" name="into-unlearned"
                                                                                 id="into-unlearned3">
                </div>
            </div>

            <div class="col-12 setting-body-header">Отображать неподтвержденные пользовательские алгоритмы</div>
            <div class="col-12 setting-body-content">
                <div><label for="unchecked-alg1">Нет</label><input type="radio" name="unchecked-alg" id="unchecked-alg1"
                                                                   checked></div>
                <div><label for="unchecked-alg2">Да</label><input type="radio" name="unchecked-alg" id="unchecked-alg2">
                </div>
            </div>

            <div class="col-12 setting-body-header">Спрашивать подтверждение действий добавления</div>
            <div class="col-12 setting-body-content">
                <div><label for="add-ask1">Нет</label><input type="radio" name="add-ask" id="add-ask1"
                                                             checked></div>
                <div><label for="add-ask2">Да</label><input type="radio" name="add-ask" id="add-ask2">
                </div>
            </div>

            <div class="col-12 setting-body-header">Спрашивать подтверждение действий удаления</div>
            <div class="col-12 setting-body-content">
                <div><label for="delete-ask1">Нет</label><input type="radio" name="delete-ask" id="delete-ask1" checked
                ></div>
                <div><label for="delete-ask2">Да</label><input type="radio" name="delete-ask" id="delete-ask2"
                >
                </div>
            </div>

            <div class="col-12 setting-body-header">Отмечать добавленный алгоритм как используемый</div>
            <div class="col-12 setting-body-content">
                <div><label for="auto-add1">Нет</label><input type="radio" name="auto-add" id="auto-add1" checked
                ></div>
                <div><label for="auto-add2">Да</label><input type="radio" name="auto-add" id="auto-add2">
                </div>
            </div>
        </div>
        <div class="col-1"></div>


        <div class="col-1"></div>
        <div class="col-10 setting-header">
            <div type="button" class="reset-all" aria-label="Сбросить все настройки"><img class="setting-img"
                                                                                          src="${pageContext.request.contextPath}/static/img/icons/settings.png">
            </div>
            Аккаунт
            <div type="button" id="reset-account" class="img-r" aria-label="Сбросить все настройки в блоке"><img
                    class="setting-img"
                    src="${pageContext.request.contextPath}/static/img/icons/account.png">
            </div>
        </div>
        <div class="col-1"></div>

        <div class="col-1"></div>
        <div class="col-10 setting-body">
            <div class="col-12 setting-body-header">Установить фоновое изображение</div>
            <form class="col-12 setting-body-content" action="${pageContext.request.contextPath}/upload" method="post"
                  enctype="multipart/form-data">
                <input type="file" name="background-img" accept="image/*">
                <input type="submit" class="sub-button">
            </form>

            <div class="col-12 setting-body-header">Поменять никнейм</div>
            <div class="col-12 setting-body-content">

                <div>
                    <input type="text" class="login-change" name="login-change" id="login-change"
                           placeholder="Введите новый никнейм" autocomplete="off"
                    >
                </div>

            </div>

            <div class="col-12 setting-body-header">Поменять пароль</div>
            <div class="col-12 setting-body-content">

                <div>
                    <input type="password" class="password-change" name="old-password" id="old-password"
                           placeholder="Введите старый пароль" autocomplete="off"
                    >
                </div>

                <div>
                    <input type="password" class="password-change" name="new-password" id="new-password"
                           placeholder="Введите новый пароль" autocomplete="off" disabled
                    >
                </div>

            </div>

            <div class="col-12 setting-body-header">Добавить/сменить электронную почту</div>
            <div class="col-12 setting-body-content">

                <div>
                    <input type="email" name="email" id="email"
                           placeholder="Введите адрес электронной почты" autocomplete="off"
                    >
                </div>

            </div>

        </div>
        <div class="col-1"></div>


    </div>
</div>
<fer:moderatorButtonAction into="/moderator"/>
</body>
</html>
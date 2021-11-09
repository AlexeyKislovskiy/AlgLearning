<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fer" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <fer:head jspName="method"/>
</head>
<body>
<fer:moderatorButton description="К странице модерации"/>
<div class="wrapper container">
    <fer:navigationRow currentName="algorithms"/>

    <div class="row" id="row-1">
        <div class="col-6">
            <a id="arrow-a" type="button" aria-label="Назад" onclick="javascript:history.back(); return false;">
                <img id="back-arrow" src="${pageContext.request.contextPath}/static/img/icons/backArrow.png">
            </a>

            <div id="info-div" aria-label="Информация о методе" type="button" class="wrap" data-bs-toggle="modal"
                 data-bs-target="#infoModal">
                <img id="info" src="${pageContext.request.contextPath}/static/img/icons/info.png">
            </div>

            <div class="modal fade" id="infoModal" tabindex="-1" aria-labelledby="infoModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header" id="infoModalHeader">
                            <div class="alg-card">
                                <div id="alg-card-text">${method.name}</div>
                                <img id="alg-card-img"
                                     src="${pageContext.request.contextPath}${method.image}">
                            </div>

                            <div type="button" class="img-alg-div-1" data-bs-dismiss="modal" aria-label="Закрыть">
                                <img id="closeInfo" class="img-alg"
                                     src="${pageContext.request.contextPath}/static/img/icons/close.png">
                            </div>
                        </div>
                        <div class="modal-body">
                            <p>${method.description}</p>
                            <p>Количество ситуаций: ${method.numberOfSituation}</p>
                            <p><span class="learning-info">Изучающих:</span> ${method.learning}</p>
                            <p><span class="learned-info">Изучивших:</span> ${method.learned}</p>
                        </div>
                    </div>
                </div>
            </div>

            <fer:methodAllAction name="check" description="Добавить все ситуации в изученные"/>

            <fer:methodAllAction name="plus" description="Добавить все ситуации в изучаемые"/>

            <fer:methodAllAction name="delete" description="Удалить все ситуации"/>


        </div>

        <div class="col-6 input">
            <div class="card card-stat">
                <div><span aria-label="Неизученных" class="unlearned">${num.get(0)}</span>/<span aria-label="Изучаемых"
                                                                                                 class="learning">${num.get(1)}</span>/<span
                        aria-label="Изученных" class="learned">${num.get(2)}</span></div>
            </div>
            <form action="" method="get">
                <input class="hidden-form" name="name" value="${param.name}">
                <div id="search-div" aria-label="Выражения для поиска" type="button" data-bs-toggle="modal"
                     data-bs-target="#searchModal"><img id="search-info"
                                                        src="${pageContext.request.contextPath}/static/img/icons/info_white.png">
                </div>
                <input name="search" placeholder="Поиск по названию" type="search" autocomplete="off">
                <button type="submit"></button>
            </form>

            <div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="searchModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-content">
                        <div class="modal-header" id="searchModalHeader">
                            <div id="search-info-text">Выражения для поиска</div>
                            <div type="button" class="img-alg-div-1" data-bs-dismiss="modal" aria-label="Закрыть">
                                <img class="img-alg"
                                     src="${pageContext.request.contextPath}/static/img/icons/close.png">
                            </div>
                        </div>
                        <div class="modal-body alg-scrolling">
                            <p>Все примеры указаны для Pll</p>
                            <p>Отдельная строка: название содержит указанную строку, не учитывая регистра. Запрос: 'а',
                                результат: Aa, Ab, Ga, Ja, Na, Ra, Ua</p>
                            <p>Символ '^' перед началом строки: название начинается с указанной строки. Запрос: '^a',
                                результат: Aa, Ab</p>
                            <p>Символ '$' после конца строки: название заканчивается на указанную строку. Запрос: 'a$',
                                результат: Aa, Ga, Ja, Na, Ra, Ua</p>
                            <p>Символ '*': любое название. Запрос: '*', результат: все ситуации</p>
                            <p>Символ '|': разделитель между частями запроса. Запрос: '^a|b$', результат: Aa, Ab, Gb,
                                Jb, Nb, Rb, Ub</p>
                            <p>Символ '~': исключение строк, соответствующих данному запросу. Запрос: 'a|g|j|~b$',
                                результат: Aa, Ga, Gc, Gd, Ja, Na, Ra, Ua</p>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>


    <div class="row scrolling" id="methods">

        <c:forEach items="${situations}" var="situation">
            <div class="col-4 col-md-3">
                <form action="" method="get">
                    <input class="hidden-form" name="name" value="${param.name}">
                    <c:if test="${param.search!=null}"><input class="hidden-form" name="search"
                                                              value="${param.search}"></c:if>
                    <input class="hidden-form" name="situation" value="${situation.name}">
                    <div class="card form-sender <c:if test="${situation.state==learning}">learning</c:if>  <c:if test=
                        "${situation.state==learned}">learned</c:if>" type="button">
                        <div class="card-header">
                            <span>${situation.name}</span>
                        </div>
                        <img class="card-img-top" src="${pageContext.request.contextPath}${situation.image}">

                    </div>
                </form>
            </div>
        </c:forEach>


    </div>
</div>

<c:if test="${situation!=null}">
    <div class="modal fade" id="algModal" tabindex="-1" aria-labelledby="algModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content learned-x">
                <div class="modal-header alg-modal-body">
                    <div class="modal-title col-6 alg-h2 algModalLabel" id="algModalLabel">
                        <div class="card <c:if test="${situation.state==learning}">learning</c:if>  <c:if test=
                        "${situation.state==learned}">learned</c:if>">
                            <div class="card-header">
                                <span>${situation.name}</span>
                            </div>
                            <img class="card-img-top" src="${pageContext.request.contextPath}${situation.image}">

                        </div>
                    </div>

                    <fer:methodAction name="check" description="Добавить ситуацию в изученные"/>

                    <fer:methodAction name="plus" description="Добавить ситуацию в изучаемые"/>

                    <fer:methodAction name="delete" description="Удалить ситуацию"/>


                    <div type="button" class="img-alg-div close-div" data-bs-dismiss="modal" aria-label="Закрыть">
                        <img id="close" class="img-alg"
                             src="${pageContext.request.contextPath}/static/img/icons/close.png">
                    </div>
                </div>
                <div class="modal-body alg-scrolling ">
                    <div class="alg-modal-body">
                        <div class="col-4 an-alg-h">Зеркальная ситуация</div>
                        <div class="col-4 an-alg-h">Обратная ситуация</div>
                        <div class="col-4 an-alg-h">Зеркальная и обратная ситуация</div>
                    </div>
                    <div class="alg-modal-body an-alg">

                        <fer:anotherSituation aname="${mirrorSituation.name}" aid="${mirrorSituation.id}"
                                              astate="${mirrorSituation.state}" aimage="${mirrorSituation.image}"/>

                        <fer:anotherSituation aname="${reverseSituation.name}" aid="${reverseSituation.id}"
                                              astate="${reverseSituation.state}" aimage="${reverseSituation.image}"/>

                        <fer:anotherSituation aname="${mirrorReverseSituation.name}" aid="${mirrorReverseSituation.id}"
                                              astate="${mirrorReverseSituation.state}"
                                              aimage="${mirrorReverseSituation.image}"/>

                    </div>

                    <div class="radioButton alg-modal-body">
                        <form class="row" action="" method="post">
                            <input class="hidden-form" name="name" value="${param.name}">
                            <c:if test="${param.search!=null}"><input class="hidden-form" name="search"
                                                                      value="${param.search}"></c:if>
                            <input class="hidden-form" name="situation" value="${param.situation}">
                            <div class="col-7 form-sender"><label id="l1" for="r1">По популярности</label><input id="r1"
                                                                                                                 class="radio"
                                                                                                                 type="radio"
                                                                                                                 name="sort"
                                                                                                                 value="popularity"
                                                                                                                 <c:if test="${param.sort=='popularity' || param.sort==null && setting9=='popularity'}">checked</c:if>/>
                            </div>
                            <div class="col-4 form-sender"><label id="l2" for="r2">По длине</label><input id="r2"
                                                                                                          class="radio"
                                                                                                          type="radio"
                                                                                                          name="sort"
                                                                                                          value="length"
                                                                                                          <c:if test="${param.sort=='length' || param.sort==null && setting9=='length'}">checked</c:if>/>
                            </div>
                        </form>
                    </div>


                    <div class="alg-modal-body">
                        <div class="col-8">
                            <span class="alg-h">Алгоритм</span>
                        </div>
                        <div class="col-3">
                            <span class="alg-h">Используют</span>
                        </div>
                    </div>

                    <c:forEach items="${algorithms}" var="algorithm">
                        <div class="alg-modal-body alg_border <c:if test="${algorithm.verified==false}">unchecked</c:if>">
                            <div class="col-8">
                                <label for="${algorithm.id}">${algorithm.text}</label>
                            </div>
                            <div class="col-3">
                                <label for="${algorithm.id}">${algorithm.numberOfUses}</label>
                            </div>

                            <div class="col-1 alg-check just_end"
                                 aria-label="<c:if test="${algorithm.state==notUsing}">Отметить как используемый</c:if>
                                <c:if test="${algorithm.state==using}">Снять отметку использования</c:if>">
                                <form action="" method="post" class="just_end">
                                    <input class="hidden-form" name="use-flag" value="${algorithm.id}">
                                    <input class="form-sender" type="checkbox" name="use"
                                           value="${algorithm.id}"
                                           id="${algorithm.id}"
                                           <c:if test="${algorithm.state==using}">checked</c:if>>
                                </form>
                            </div>
                        </div>
                    </c:forEach>


                    <form class="alg-modal-body" name="new-alg" action="" method="post">
                        <input type="text" class="input3x3 new-alg-text" name="new-alg" id="new-alg-text-1"
                               placeholder="Введите алгоритм"
                               autocomplete="off">
                        <div type="submit"
                             class="img-alg-div new-alg-div form-sender"
                             id="new-alg-div-1" aria-label="Добавить новый алгоритм">
                            <img id="plus-new-alg-1" class="img-alg plus-new-alg"
                                 src="${pageContext.request.contextPath}/static/img/icons/plus_grey.png">
                        </div>
                    </form>


                </div>
            </div>
        </div>
    </div>
</c:if>


<c:if test="${situation!=null}">
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            let modal = new bootstrap.Modal(document.getElementById('algModal'), {keyboard: false});
            modal.show();
            document.getElementById('algModal').addEventListener('hidden.bs.modal', ev => {
                let form = document.createElement('form');
                form.action = '';
                form.method = 'GET';

                form.innerHTML = '<input class="hidden-form" name="name" value="${param.name}">; <c:if test="${param.search!=null}"><input class="hidden-form" name="search" value="${param.search}"></c:if>';

                document.body.append(form);

                form.submit();
            })
        })
    </script>
</c:if>
<fer:moderatorButtonAction into="/moderator"/>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fer" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <fer:head jspName="method-training"/>
</head>
<body>
<div class="wrapper container">
    <fer:moderatorButton description="К странице модерации"/>
    <fer:navigationRow currentName="training"/>

    <div class="row row-x" id="top-row">
        <div class=col-3>
            <a id="arrow-a" type="button" aria-label="Назад" onclick="javascript:history.back(); return false;">
                <img id="back-arrow" src="${pageContext.request.contextPath}/static/img/icons/backArrow.png">
            </a>

            <a href="${pageContext.request.contextPath}/algorithms/method?name=${param.name}" id="a-page"
               aria-label="Посмотреть метод в алгоритмах">
                <img id="page" src="${pageContext.request.contextPath}/static/img/icons/page.png">
            </a>

        </div>

        <div class="col-6 scramble-div">
            <div class="scramble">
                ${scramble}
            </div>
        </div>

        <div class="col-3">
            <div id="choose-div" aria-label="Выбрать показываемые ситуации" type="button" class="wrap"
                 data-bs-toggle="modal"
                 data-bs-target="#chooseModal">
                <img id="choose" src="${pageContext.request.contextPath}/static/img/icons/choose.png">
            </div>

            <div class="modal fade" id="chooseModal" tabindex="-1" aria-labelledby="chooseModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-content">
                        <div class="modal-header" id="alg-modal-header">
                            <div class="alg-card <fer:methodLearning/>
                            <fer:methodLearned/>"
                                 id="alg-card">
                                <div id="alg-card-text">${method.name}</div>
                                <img id="alg-card-img"
                                     src="${pageContext.request.contextPath}${method.image}">
                            </div>
                            <div class="header-wrap">
                                <div class="inner-header">

                                    <fer:icons name="clearChoose" description="Отметить по умолчанию"/>

                                    <fer:icons name="chooseAll" description="Отметить все ситуации"/>

                                    <fer:icons name="unchooseAll" description="Снять все отметки"/>

                                    <fer:icons name="close" description="Закрыть"/>

                                </div>
                                <div class="input">
                                    <form action="" method="get">
                                        <input class="hidden-form" name="name" value="${param.name}">
                                        <input name="search" placeholder="Поиск по названию" type="search"
                                               autocomplete="off">
                                        <button type="submit"></button>
                                    </form>
                                </div>

                            </div>


                        </div>
                        <div class="modal-body alg-scrolling">
                            <div class="row">
                                <form class="row" id="using-form" action="" method="post">
                                    <c:forEach items="${allSituations}" var="situation">
                                        <div class="col-4 checkbox-wrap">
                                            <label for="${situation.id}">
                                                <div class="sit-card <fer:situationLearning/>
                                                    <fer:situationLearned/>">
                                                    <div class="sit-card-text">${situation.name}</div>
                                                    <img class="sit-card-img"
                                                         src="${pageContext.request.contextPath}${situation.image}">
                                                </div>
                                            </label>
                                            <input type="checkbox"
                                                   <c:if test="${situation.trainingState==situationTraining}">checked</c:if>
                                                   class="using-check" id="${situation.id}" name="${situation.id}"
                                                   value="${situation.id}">
                                        </div>
                                    </c:forEach>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <div class="row row-x" id="row-1">
        <div class="col-md-1 col-3"></div>
        <div class="col-md-3 col-6 cc">
            <div class="card" aria-label="Показать информацию" id="hidden-card">
                <div class="card-header <fer:situationLearning/>
                            <fer:situationLearned/>" id="hidden-card-text">
                    ?
                </div>
                <img class="card-img-top" id="hidden-card-img"
                     src="${pageContext.request.contextPath}/static/img/icons/unknown.png">
            </div>
        </div>
        <div class="col-md-1 col-3"></div>
        <div class="col-md-1 col-3"></div>
        <div class="col-md-5 col-6 cc">
            <div class="card" id="card-alg" aria-label="Показать информацию">
                <div class="card-header <fer:situationLearning/>
                                                    <fer:situationLearned/>">
                    <span>Алгоритмы</span>
                </div>

                <div class="alg-body" id="alg-body-1">
                    <c:if test="${setting7=='no'}">
                        <c:forEach items="${algorithms}" var="algorithm">
                            <div class="alg-border">
                                ? ? ? ? ?
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${setting7=='yes'}">
                        <c:forEach items="${algorithms}" var="algorithm">
                            <c:if test="${algorithm==algorithms.get(0)}">
                                <div class="alg-border">
                                    ? ? ? ? ?
                                </div>
                            </c:if>
                            <c:if test="${algorithm!=algorithms.get(0)}">
                                <div class="alg-border" style="display: none">
                                    ? ? ? ? ?
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${algorithms.size()==0}">
                        <div class="alg-border">
                            ? ? ? ? ?
                        </div>
                    </c:if>
                </div>

            </div>
        </div>
    </div>

    <div class="row row-x" id="row-2">
        <div class="col-3"></div>
        <div class="col-6 level">
            <div class="next" type="button" id="next">
                Дальше
            </div>
        </div>
    </div>

</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        function showHiddenCard() {
            document.getElementById('hidden-card').classList.add('removed-aria-label');
            document.getElementById('hidden-card-text').textContent = "${situation.name}";
            document.getElementById('hidden-card-img').src = "${pageContext.request.contextPath}${situation.image}";
        }

        <c:if test="${setting6=='no'}">
        document.getElementById('hidden-card').onclick = function () {
            showHiddenCard();
        }
        </c:if>
        <c:if test="${setting6=='yes'}">
        showHiddenCard();
        </c:if>
        document.getElementById('next').addEventListener('click', ev => {
            window.location = '${pageContext.request.contextPath}/training/method-training?name=${param.name}';
        })
        document.addEventListener('keypress', (event) => {
            let keyName = event.key;
            if (keyName === 'Enter' || keyName === ' ') window.location = '${pageContext.request.contextPath}/training/method-training?name=${param.name}';
        });
        document.getElementById('card-alg').onclick = function () {
            let elements = document.getElementsByClassName('alg-border');
            let list = [];
            <c:forEach items="${algorithms}" var="algorithm">
            list.push("${algorithm.text}");
            </c:forEach>
            <c:if test="${algorithms.size()==0}">
            list.push("Для этой ситуации не выбрано ни одного алгоритма в качестве используемого");
            elements[0].style.textDecoration = "none";
            </c:if>
            for (let i = 0; i < elements.length; i++) {
                elements[i].textContent = list[i];
                elements[i].style.display = "block";
            }
            document.getElementById('card-alg').classList.add('removed-aria-label');
        }

        document.getElementById('chooseModal').addEventListener('hidden.bs.modal', ev => {
            let form = document.getElementById('using-form');
            let newURL = location.href.split("?")[0] + '?name=${param.name}';
            window.history.pushState('object', document.title, newURL);
            form.submit();
        })

        document.getElementById('clearChoose-div').addEventListener('click', ev => {
            let el = document.getElementsByClassName('using-check');
            let cards = document.getElementsByClassName('sit-card');
            for (let i = 0; i < el.length; i++) {
                if (cards[i].classList.contains("learning") || cards[i].classList.contains("learned")) {
                    el[i].checked = "true";
                } else el[i].checked = null;
            }
        })
        <c:if test="${param.search!=null}">
        let modal = new bootstrap.Modal(document.getElementById('chooseModal'), {keyboard: false});
        modal.show();
        </c:if>

    })
</script>
<fer:moderatorButtonAction into="/moderator"/>
</body>
</html>
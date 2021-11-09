<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fer" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <fer:head jspName="method-learning"/>
</head>
<body>
<fer:moderatorButton description="К странице модерации"/>
<div class="wrapper container">
    <fer:navigationRow currentName="learning"/>

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

        <div class="col-3 num-3-div">
            <div class="num-3">
                <span aria-label="Новых"
                      class="new <c:if test="${situation.learningState==newS}">current-learn</c:if>">${method.numOfNew}</span>/<span
                    aria-label="Забытых"
                    class="forgotten <c:if test="${situation.learningState==forgot}">current-learn</c:if>">${method.numOfForgot}</span>/<span
                    aria-label="Для повторения"
                    class="repeat <c:if test="${situation.learningState==repeat}">current-learn</c:if>">${method.numOfRepeat}</span>
            </div>
        </div>
    </div>

    <div class="row row-x" id="row-1">
        <div class="col-md-1 col-3"></div>
        <div class="col-md-3 col-6 cc">
            <div class="card" aria-label="Показать информацию" id="hidden-card">
                <div class="card-header" id="hidden-card-text">
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
                <div class="card-header">
                    Алгоритмы
                </div>

                <div class="alg-body" id="alg-body-1">
                    <c:if test="${setting2=='no'}">
                        <c:forEach items="${algorithms}" var="algorithm">
                            <div class="alg-border">
                                ? ? ? ? ?
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${setting2=='yes'}">
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
            <fer:learningButton name="again" description="Снова"/>
            <fer:learningButton name="hard" description="Трудно"/>
            <fer:learningButton name="medium" description="Средне"/>
            <fer:learningButton name="easy" description="Легко"/>
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

        <c:if test="${setting1=='no'}">
        document.getElementById('hidden-card').onclick = function () {
            showHiddenCard();
        }
        </c:if>
        <c:if test="${setting1=='yes'}">
        showHiddenCard();
        </c:if>
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
    })
</script>
<fer:moderatorButtonAction into="/moderator"/>
</body>
</html>
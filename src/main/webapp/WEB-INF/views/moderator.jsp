<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fer" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <fer:head jspName="moderator"/>
</head>
<body>
<fer:moderatorButton description="К странице алгоритмов"/>

<div class="wrapper container">
    <div class="row" id="header-row">
        <div class="col-2 head-mod l-head">
            Добавил
        </div>
        <div class="col-2 head-mod c-head">
            Метод
        </div>
        <div class="col-3 head-mod c-head">
            Ситуация
        </div>
        <div class="col-5 head-mod r-head">
            Алгоритм
        </div>
    </div>
    <div class="row" id="content-row">
        <c:forEach items="${algorithms}" var="algorithm">
            <div class="col-2 content-mod">
                    ${algorithm.addCuberId}
            </div>
            <div class="col-2 content-mod">
                    ${algorithm.methodName}
            </div>
            <div class="col-3 content-mod">
                    ${algorithm.situationName}
                <img class="situation-img" src="${pageContext.request.contextPath}${algorithm.situationImage}">
            </div>
            <div class="col-4 content-mod alg-text">
                <input type="text" form="${algorithm.id}" class="input3x3 new-alg-text" name="plus-text"
                       id="new-alg-text-1"
                       value="${algorithm.text}" autocomplete="off">
            </div>
            <div class="col-1 content-mod">
                <form id="${algorithm.id}" action="" method="post" class="plus form-submit" aria-label="Добавить">
                    <input class="hidden-form" name="plus" value="${algorithm.id}">
                    <img type="button" class="button-img"
                         src="${pageContext.request.contextPath}/static/img/icons/plus.png">
                </form>
                <form action="" method="post" class="delete form-submit" aria-label="Удалить">
                    <input class="hidden-form" name="delete" value="${algorithm.id}">
                    <img type="button" class="button-img"
                         src="${pageContext.request.contextPath}/static/img/icons/delete.png">
                </form>
            </div>
        </c:forEach>


    </div>
</div>
<fer:moderatorButtonAction into="/algorithms"/>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        formSubmit();

        function formSubmit() {
            let el = document.getElementsByClassName('form-submit');
            for (let i = 0; i < el.length; i++) {
                el[i].addEventListener('click', evt => {
                    el[i].submit();
                })
            }
        }
    })
</script>
</body>
</html>
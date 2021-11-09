<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fer" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <fer:head jspName="learning"/>
</head>
<body>
<fer:moderatorButton description="К странице модерации"/>
<div class="wrapper container">
    <fer:navigationRow currentName="learning"/>

    <fer:cubeIcons/>

    <div class="row row-x scrolling" id="methods">
        <c:forEach items="${methods}" var="method">
            <div class="col-6 col-md-4">
                <a href="${pageContext.request.contextPath}/learning/method-learning?name=${method.name}">
                    <div class="card">
                        <div class="card-header">
                            <div class="row">
                                <div class="col-6">
                                    <span>${method.name}</span>
                                </div>
                                <div class="col-6 info-3-num-div">
                                <span><span aria-label="Новых" class="new">${method.numOfNew}</span>/<span
                                        aria-label="Забытых"
                                        class="forgotten">${method.numOfForgot}</span>/<span
                                        aria-label="Для повторения" class="repeat">${method.numOfRepeat}</span></span>
                                </div>
                            </div>
                        </div>
                        <img class="card-img-top" src="${pageContext.request.contextPath}${method.image}">
                    </div>
                </a>
            </div>
        </c:forEach>

    </div>
</div>

<fer:moderatorButtonAction into="/moderator"/>
</body>
</html>
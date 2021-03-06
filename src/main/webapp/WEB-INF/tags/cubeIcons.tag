<%@tag description="cube icons row" pageEncoding="UTF-16" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row" id="icons">
    <div class="col-12">
        <form id="cubeIconsForm" action="" method="get">
            <label aria-label="Показать все">
                <input class="cubeRadio" type="radio" id="all" name="cube" value="all"
                       <c:if test="${param.cube=='all' || param.cube==null}">checked</c:if>/>
                <img class="cubeIcons" src="${pageContext.request.contextPath}/static/img/cubeIcons/All.png" alt="all">
            </label>
            <c:forEach items="${cubes}" var="cube">
                <label aria-label="${cube.description}">
                    <input class="cubeRadio" type="radio" id="${cube.name}" name="cube" value="${cube.name}"
                           <c:if test="${param.cube==cube.name}">checked</c:if>/>
                    <img class="cubeIcons" src="${pageContext.request.contextPath}${cube.image}" alt="${cube.name}">
                </label>
            </c:forEach>
        </form>
    </div>
</div>
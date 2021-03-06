<%@tag description="methods row" pageEncoding="UTF-16" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="link" required="true" %>
<div class="row scrolling" id="methods">

    <c:forEach items="${methods}" var="method">
        <div class="col-6 col-md-4">
            <a href="${pageContext.request.contextPath}${link}${method.name}">
                <div class="card <c:if test="${method.state==learning}">learning</c:if>
                    <c:if test="${method.state==learned}">learned</c:if>">
                    <div class="card-header">
                        <span>${method.name}</span>
                    </div>
                    <img class="card-img-top"
                         src="${pageContext.request.contextPath}${method.image}" alt="${method.name}">
                </div>
            </a>
        </div>
    </c:forEach>

</div>
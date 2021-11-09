<%@tag description="icons" pageEncoding="UTF-16" %>
<%@attribute name="name" required="true" %>
<%@attribute name="description" required="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="${name}-div" type="button" class="img-alg-div"
     aria-label="${description}">
    <img id="clearChoose" class="img-alg ia" <c:if test="${name=='close'}">data-bs-dismiss="modal"</c:if>
         src="${pageContext.request.contextPath}/static/img/icons/${name}.png">
</div>
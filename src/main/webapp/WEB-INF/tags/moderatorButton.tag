<%@tag description="moderator button" pageEncoding="UTF-16" %>
<%@attribute name="description" required="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${cuber.status!=userStatus}">
    <div id="moderator-div" aria-label="${description}"><img
            id="moderator-img" type="button"
            src="${pageContext.request.contextPath}/static/img/icons/moderator.png"></div>
</c:if>
<%@tag description="another situation" pageEncoding="UTF-16" %>
<%@attribute name="aname" required="true" %>
<%@attribute name="aid" required="true" %>
<%@attribute name="astate" required="true" %>
<%@attribute name="aimage" required="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-4">
    <form action="" method="get">
        <input class="hidden-form" name="name" value="${param.name}">
        <c:if test="${param.search!=null}"><input class="hidden-form" name="search"
                                                  value="${param.search}"></c:if>
        <input class="hidden-form" name="situation" value="${aname}">
        <div class="card <c:if test="${aid!=situation.id && aid!=-1}">
                                form-sender</c:if> <c:if test="${astate==learning}">learning</c:if>  <c:if test=
                        "${astate==learned}">learned</c:if>"
             <c:if test="${aid!=situation.id && aid!=-1}">type="button"</c:if>>
            <div class="card-header">
                <span>${aname}</span>
            </div>
            <img class="card-img-top"
                 src="${pageContext.request.contextPath}${aimage}">

        </div>
    </form>
</div>
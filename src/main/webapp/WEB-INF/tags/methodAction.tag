<%@tag description="method action" pageEncoding="UTF-16" %>
<%@attribute name="name" required="true" %>
<%@attribute name="description" required="true" %>
<form class="wrap" action="" method="post">
    <input class="hidden-form" name="${name}" value="${name}">
    <div type="button" class="img-alg-div form-sender" aria-label="${description}">
        <img id="${name}" class="img-alg"
             src="${pageContext.request.contextPath}/static/img/icons/${name}_grey.png">
    </div>
</form>
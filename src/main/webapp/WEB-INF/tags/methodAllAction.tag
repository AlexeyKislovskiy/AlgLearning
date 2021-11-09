<%@tag description="method all action" pageEncoding="UTF-16" %>
<%@attribute name="name" required="true" %>
<%@attribute name="description" required="true" %>
<form class="wrap" id="${name}-wrap" action="" method="post">
    <input class="hidden-form" name="${name}-all" value="${name}-all">
    <div type="button" class="form-sender" id="${name}-div" aria-label="${description}">
        <img id="${name}-all" src="${pageContext.request.contextPath}/static/img/icons/${name}.png">
    </div>
</form>
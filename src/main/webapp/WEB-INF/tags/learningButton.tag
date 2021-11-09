<%@tag description="learning button" pageEncoding="UTF-16" %>
<%@attribute name="name" required="true" %>
<%@attribute name="description" required="true" %>
<form class="${name} col-3" action="" method="post">
    <input class="hidden-form" name="${name}" value="${name}">
    <div class="form-sender" id="${name}" type="button">
        ${description}
    </div>
</form>
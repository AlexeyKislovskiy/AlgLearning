<%@tag description="statistics" pageEncoding="UTF-16" %>
<%@attribute name="name" required="true" %>
<%@attribute name="n1" required="true" %>
<%@attribute name="n2" required="true" %>
<%@attribute name="n3" required="true" %>
<%@attribute name="n4" required="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="semi-header">${name}</div>
<div class="acc-content rb">
    <div><span class="new">Новых:</span> ${n1}</div>
    <div><span class="forgot">Забытых:</span> ${n2}</div>
    <div><span class="repeat">Повторенных:</span> ${n3}</div>
</div>
<div class="acc-content lb">
    <div><span class="solved">Собранных:</span> ${n4}</div>
</div>
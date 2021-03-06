<%@tag description="navigation" pageEncoding="UTF-16" %>
<%@attribute name="currentName" required="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="nav nav-pills nav-fill">
    <li class="nav-item">
        <a class="nav-link <c:if test="${currentName=='learning'}">current</c:if>"
           href="${pageContext.request.contextPath}/learning">Изучение</a>
    </li>
    <li class="nav-item">
        <a class="nav-link <c:if test="${currentName=='training'}">current</c:if>"
           href="${pageContext.request.contextPath}/training">Тренировка</a>
    </li>
    <li class="nav-item">
        <a class="nav-link <c:if test="${currentName=='algorithms'}">current</c:if>"
           href="${pageContext.request.contextPath}/algorithms">Алгоритмы</a>
    </li>
    <li class="nav-item">
        <a class="nav-link <c:if test="${currentName=='settings'}">current</c:if>"
           href="${pageContext.request.contextPath}/settings">Настройки</a>
    </li>
    <li class="nav-item">
        <a class="nav-link <c:if test="${currentName=='account'}">current</c:if>"
           href="${pageContext.request.contextPath}/account">Аккаунт</a>
    </li>
</ul>
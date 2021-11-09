<%@tag description="moderator button action" pageEncoding="UTF-16" %>
<%@attribute name="into" required="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${cuber.status!=userStatus}">
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            intoModeratorPage();

            function intoModeratorPage() {
                document.getElementById('moderator-div').addEventListener('click', ev => {
                    window.location = '${pageContext.request.contextPath}${into}'
                })
            }
        })
    </script>
</c:if>
<%@tag description="navigation row" pageEncoding="UTF-16" %>
<%@attribute name="currentName" required="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fer" tagdir="/WEB-INF/tags" %>
<div class="row row-x" id="navigation">
    <div class="col-3">
        <fer:siteLogo/>

        <fer:siteInfo/>

    </div>
    <div class="col-9">
        <fer:navigation currentName="${currentName}"/>
    </div>
</div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fer" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <fer:head jspName="training"/>
</head>
<body>
<fer:moderatorButton description="К странице модерации"/>
<div class="wrapper container">
    <fer:navigationRow currentName="training"/>

    <fer:cubeIcons/>

    <fer:methodsRow link="/training/method-training?name="/>
</div>
<fer:moderatorButtonAction into="/moderator"/>
</body>
</html>
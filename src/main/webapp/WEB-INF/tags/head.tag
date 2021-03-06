<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="jspName" required="true" %>
<title>AlgLearning</title>
<meta charset="utf-8">
<meta name="viewport"
      content="width-device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="icon" href="${pageContext.request.contextPath}/static/img/icons/Icon180.png" type="image/png">
<link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<c:if test="${jspName=='account'}">
    <link href="${pageContext.request.contextPath}/static/css/account.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/account.js"></script>
</c:if>
<c:if test="${jspName=='algorithms' || jspName=='training'}">
    <link href="${pageContext.request.contextPath}/static/css/algorithms.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/algorithms.js"></script>
</c:if>
<c:if test="${jspName=='learning'}">
    <link href="${pageContext.request.contextPath}/static/css/learning.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/algorithms.js"></script>
</c:if>
<c:if test="${jspName=='method'}">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link href="${pageContext.request.contextPath}/static/css/method.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/method.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/algorithm-form-validation.js"></script>
</c:if>
<c:if test="${jspName=='method-learning'}">
    <link href="${pageContext.request.contextPath}/static/css/method-learning.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/method-learning.js"></script>
</c:if>
<c:if test="${jspName=='method-training'}">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link href="${pageContext.request.contextPath}/static/css/method-training.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/method-training.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/method-learning.js"></script>
</c:if>
<c:if test="${jspName=='settings'}">
    <link href="${pageContext.request.contextPath}/static/css/settings.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/settings.js"></script>
</c:if>
<c:if test="${jspName=='moderator'}">
    <link href="${pageContext.request.contextPath}/static/css/moderator.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/moderator.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/algorithm-form-validation.js"></script>
</c:if>
<c:if test="${jspName=='login'}">
    <link href="${pageContext.request.contextPath}/static/css/algorithms.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/login.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/algorithms.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/login.js"></script>
    <style>
        body {
            color: #fff;
            background: #000 url(${pageContext.request.contextPath}/static/img/background.jpg) fixed;
            min-height: 100%;
            background-size: cover;
            overflow: hidden;
        }

        <c:if test="${emailConfirmation==true}">
        #reg-semi-header, #reg-semi-body {
            display: none;
        }

        #mail-semi-header, #mail-semi-body {
            display: block;
        }

        </c:if>

        <c:if test="${recoveryCode==true}">
        #forgot-semi-header, #forgot-semi-body, #reg-semi-header, #reg-semi-body {
            display: none;
        }

        #mail2-semi-header, #mail2-semi-body {
            display: block;
        }

        </c:if>
    </style>
</c:if>
<c:if test="${jspName!='login'}">
    <style>
        body {
            color: #fff;
        <c:if test="${upload!=null}"> background: #000 url(${pageContext.request.contextPath}/static/img/backgrounds/${upload}) fixed;
        </c:if><c:if test="${upload==null}"> background: #000 url(${pageContext.request.contextPath}/static/img/background.jpg) fixed;
        </c:if> min-height: 100%;
            background-size: cover;
            overflow: hidden;
        }
    </style>
</c:if>
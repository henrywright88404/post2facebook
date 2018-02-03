<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>

</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<h2>Post2Facebook - Gmail to facebook page</h2>

Next message to be posted: 

<form:form action="Posted" modelAtribute="EmailMessage">
	<textarea type="text" cols="50" rows="10" >${email.message} </textarea>

	<input type="submit" value="Post a Gmail Message to facebook"> </input>
</form:form>





<hr>

<a href="${contextPath}/main-menu">Home</a>

<br><br>

<a href="${contextPath}/charttofacebook/">post report</a>

<br><br>

</body>
</html>
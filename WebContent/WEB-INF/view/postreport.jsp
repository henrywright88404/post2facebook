<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>

</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<h2>Post2Facebook - Report to Facebook page - Upload File</h2>


	<form:form method="post" modelAttribute="reportFile" enctype="multipart/form-data" >
		Upload the report please:
		<input type="file" name="reportFile"/>
		<input type="submit" value="Upload"/>
		<form:errors path="reportFile" cssStyle="color: #ff0000;"/>
	
	</form:form>
<hr>

<a href="${contextPath}/main-menu">Home</a>

<br><br>

<a href="${contextPath}/gmailtofacebook/">post email</a>

<br><br>

</body>
</html>
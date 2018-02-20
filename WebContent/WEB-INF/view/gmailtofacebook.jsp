<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<head>

</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
	<div class="container">
		<div class="jumbotron">
			<h2 class="col-sm-12"> Gmail to facebook page</h2>
		</div>
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Post2Facebook</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="${contextPath}/main-menu">Home</a></li>
					<li class="active"><a href="${contextPath}/gmailtofacebook/">Post Email</a></li>
					<li><a href="${contextPath}/charttofacebook/">Post report</a></li>
					<li><a href="${contextPath}/gmailtofacebook/auto-post">Auto Post</a></li>
					
				</ul>
			</div>
		</nav>
	
			

			<form:form class="form-inline" action="Posted" modelAtribute="EmailMessage" >
			
			<div class="row">
				<label class="col-sm-12">Next message to be posted:</label>
				<textarea readonly class="form-control" style="min-width: 100%" rows="25">${email.message} </textarea>
			</div>
			<br>
				<input type="submit" class="btn" value="Post a Gmail Message to facebook"></input>
			</form:form>

	</div>
</body>
</html>
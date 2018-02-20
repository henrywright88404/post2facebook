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
<body>
	<div class="container">
		<div class="jumbotron">
			<h2>Post2Facebook - Publish report</h2>
		</div>
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Post2Facebook</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath }/main-menu/">Home</a></li>
					<li><a href="${pageContext.request.contextPath }/gmailtofacebook/">Post Email</a></li>
					<li class="active"><a href="${pageContext.request.contextPath }/charttofacebook/">Post report</a></li>
					<li><a href="${pageContext.request.contextPath }gmailtofacebook/auto-post">Auto Post</a></li>
					</ul>
			</div>
		</nav>
	<form method="post" modelAttribute="faultChart" action="posted">
		<input type="text" name="message"
			placeholder="Type your message to go with this report here"><br>
		<input type="hidden" name="faultChart" value=${chart }></input> <img
			src="data:image/jpeg;base64,${chart}" name="faultChart"> <br>

		<input type="submit" value="Post this chart to facebook"></input>

	</form>
</div>
</body>
</html>
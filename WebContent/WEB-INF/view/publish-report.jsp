<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>

	<h2>Post2Facebook - Publish report</h2>

	<hr>


	<form method="post" action="posted" >
	<input type="text" modelAttribute="message" placeholder="Type your message to go with this report here"><br>
	
		<img src="data:image/jpeg;base64,${chart}" modelAttribute="FaultChart">
		<br>
		
		 <input	type="submit" value="Post this chart to facebook"></input>

	</form>
	

	<br>
	<br>

	<a href="gmailtofacebook/">post Email</a>

	<br>
	<br>

	<a href="charttofacebook/">post Report</a>

	<br>
	<br>

</body>
</html>
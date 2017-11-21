<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="user" class="ua.nure.kn155.omelchenko.User" scope="session" />
<html>
<head>
<title>Details</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/details" method= "post">
		First Name: ${user.firstName } <br/>
		Last Name ${user.lastName}<br/>
		Date of Birth <fmt:formatDate value="${user.dateOfBirthd}" type="date" dateStyle="medium"/><br/>
		<input type="submit" name="okButton" value="Ok">
	</form>
</body>
</html>
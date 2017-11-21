<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="user" class="ua.nure.kn155.omelchenko.User" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/delete" method="post">
		First Name: ${user.firstName } <br /> Last Name ${user.lastName}<br />
		Date of Birth
		<fmt:formatDate value="${user.dateOfBirthd}" type="date"
			dateStyle="medium" />
		<br /> <input type="submit" name="okButton" value="Ok"> <input
			type="submit" name="cancelButton" value="Cancel">
	</form>
</body>
</html>
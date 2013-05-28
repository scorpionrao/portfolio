<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="homeMenuBar" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Una Creations - Error Page</title>
<link type="text/css" rel="stylesheet" href="MenuBar.css">
</head>
<body>
 <!--  align title to center -->
 <h1 class="pageTitle">UNA CREATIONS --- Error Page</h1>
 <br><br>
 <table class="TopMenuBar">
      <tr>
      	<homeMenuBar:forEach items="${menuBarList}" var="menuItem">
        	<td class="TopMenuCell">
        		<p>An error occurred: ${errorMessage}</p>
        	</td>
        	<<td class="TopMenuCell">
				<a href="Portfolio.jsp" class="TopMenuCellText">Back to main page</a>
			</td>
		</homeMenuBar:forEach>
      </tr>
    </table>
</body>
</html>
        
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>登录</title>
</head>
<body>
<div id="div-body" style="display:none">
	<form action="${pageContext.request.contextPath}/login.sec" method="post">
		<table>
			<tr>
		        <td><label for="userNameText">Name:</label></td>
		        <td><input id="userNameText" /></td>
	        </tr>
	        <tr>
		        <td> <label for="passwordTextbox">Password:</label></td>
		        <td> <input id="passwordTextbox" type="password" /></td>
	        </tr>
	       	<tr><td colspan="2"> <input type="submit" value="登录"/></td></tr>
		</table>
   </form>
 </div>
 
 <c:if test="${not empty errorMsg}">
 	<script>alert("${errorMsg}");</script>
 </c:if>
 
<script type="text/javascript"
src="${pageContext.request.contextPath}/static/app/auth/login.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>a simple grid</title>
<!-- TODO  GRID 的样式统一管理 -->
<style type="text/css">
.heading {
	font-weight: bold;
	padding-bottom: 0.25em;
}

/* styles for autoheight */
#userDiv {
	width: 400px;
	height: auto;
}

#userDiv .dgrid-scroller {
	position: relative;
	overflow-y: hidden;
}
.has-ie-6 #grid .dgrid-scroller {
	/* IE6 doesn't react properly to hidden on this page for some reason */
	overflow-y: visible;
}
#userDiv .dgrid-header-scroll {
	display: none;
}
#userDiv .dgrid-header {
	right: 0;
}
.field-userid {
    width: 80px;
    font-weight: bold;
}


</style>
</head>
<body >
<h1>a simple grid</h1>
<hr>
<input type="button" id="adduserbtn"/>
<input type="button" id="refreshlist"/>
<div id="userDiv" ></div> 
<div id="adduserdlg" style="display:none">
	<form data-dojo-type="dijit/form/Form" id="adduserform" action="${pageContext.request.contextPath}/module1/adduser.do" encType="multipart/form-data" >
		<table>
			<tr>
				<td><label for="userNameText">userNameText:</label></td>
				<td><input type="text" id="userNameText"/></td>
			</tr>
			<tr>
				<td><label for="password">password:</label></td>
				<td><input type="password" id="password"/></td>
			</tr>
			<tr>
				<td><label for="accountText">accountText:</label></td>
				<td><input type="text" id="accountText"/></td>
			</tr>
			<tr>
				<td><label for="sexSelect">sex:</label></td>
				<td><input type="text" id="sexSelect"/></td>
			</tr>
			<tr>
				<td colspan='2'><input type="submit" id="saveuserbtn"/></td>
			</tr>
		</table>
	</form>
</div>
<div id="response"></div>

	<script type="text/javascript"
<%-- 		src="${pageContext.request.contextPath}/resource/app/module1/showgrid.js"></script> --%>
		src="${pageContext.request.contextPath}/static/app/demo/usergrid.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/static/app/demo/adduser.js"></script>
</body>
</html>
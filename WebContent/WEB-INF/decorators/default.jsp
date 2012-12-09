<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>FT－<decorator:title default="模块名"/></title>
    <script>
		var contextPath = "${pageContext.request.contextPath}";
    </script>
    		<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/dojo/1.8.0/dojo/resources/dojo.css" />
		<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/dojo/1.8.0/dijit/themes/claro/claro.css" />
    <!-- Configure Dojo first -->
    <script src="${pageContext.request.contextPath}/static/js/dojoConfig.js"></script>
    <!-- load Dojo -->
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/dojo/1.8.0/dojo/dojo.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css"></link>
<decorator:head/>
<script>
require(
[
	"dojo/dom",
	"dojo/_base/fx",
	 "dijit/ProgressBar",
	"dojo/ready"
], function(dom, fx,ProgressBar ,ready){
	ready(function(){
	     dojo.subscribe("/dojo/io/done", null, function(){
	    	 dom.byId("processbar").style.display='none';
	     });
	     var myProgressBar = new ProgressBar({
	         style: "width: 300px;position: absolute; top: 50%; left: 40%;z-index:999;",
	         label:'loading....'
	     });
	     dojo.subscribe("/dojo/io/start", null, function(){
	    	 dom.byId("processbar").style.display='block';
	    	 myProgressBar.placeAt("processbar");
	    	 myProgressBar.set('value',Infinity);
	     });
		setTimeout(function(){
			var loader = dom.byId("loader");
			fx.fadeOut({ node: loader, duration: 500, onEnd: function(){ loader.style.display = "none"; }}).play();
		}, 500);
	});
});
</script>
</head>
<body  class="claro">
<div id="goHome"><a href="${pageContext.request.contextPath}">Home</a></div>
<div id="loader"><div id="loaderInner"></div></div>
<div id="processbar"></div>
<decorator:body/>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>团队管理</title>

    <link href="${ContextPath}/Hplus/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${ContextPath}/Hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">

    <link href="${ContextPath}/Hplus/css/animate.min.css" rel="stylesheet">
    <link href="${ContextPath}/Hplus/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
     <script src="${ContextPath}/Hplus/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ContextPath}/Hplus/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${ContextPath}/Hplus/js/content.min.js?v=1.0.0"></script>
    <script src="teams.js"></script>

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row" id = "teams">
        </div>
    </div>
    
   
     
</body>

</html>
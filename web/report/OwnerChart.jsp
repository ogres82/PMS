<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser" %> 
<%@ page import="com.bstek.bdf2.core.context.ContextHolder" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
    DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
    request.setAttribute("loginUser",user);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>业主信息</title> 
<link href="${ContextPath}/Hplus/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
<link href="${ContextPath}/Hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="${ContextPath}/Hplus/css/animate.min.css" rel="stylesheet">
<link href="${ContextPath}/Hplus/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
<script src="${ContextPath}/Hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="${ContextPath}/Hplus/js/bootstrap.min.js?v=3.3.5"></script>
<script src="${ContextPath}/Hplus/js/content.min.js?v=1.0.0"></script>
<script src="${ContextPath}/Hplus/js/plugins/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${ContextPath}/report/OwnerChart.js"></script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
            <div class="col-sm-5">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>房间类型入住情况</h5>
                    </div>
                    <div class="ibox-content">
                    	<div id="roomType" style="height:400px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-7">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>小区入住情况</h5>
                    </div>
                    <div class="ibox-content">
                    	<div id="roomCommunity" style="height:400px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>月入住情况</h5>
                    </div>
                    <div class="ibox-content">
                    	<div id="monthIn" style="height:400px;"></div>
                    </div>
                </div>
            </div>
           
        </div>      
    </div>
</body>

</html>
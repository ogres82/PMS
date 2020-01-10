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
<title>物管人员信息</title> 
<link href="${ContextPath}/Hplus/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
<link href="${ContextPath}/Hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="${ContextPath}/Hplus/css/animate.min.css" rel="stylesheet">
<link href="${ContextPath}/Hplus/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
<script src="${ContextPath}/Hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="${ContextPath}/Hplus/js/bootstrap.min.js?v=3.3.5"></script>
<script src="${ContextPath}/Hplus/js/content.min.js?v=1.0.0"></script>
<script src="${ContextPath}/Hplus/js/plugins/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${ContextPath}/mainFrame/js/index.js"></script>
<script type="text/javascript" src="${ContextPath}/report/EmpDistribution.js"></script>
<style>
.col-lg-1, .col-lg-10, .col-lg-11, .col-lg-12, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-md-1, .col-md-10, .col-md-11, .col-md-12, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-sm-1, .col-sm-10, .col-sm-11, .col-sm-12, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-xs-1, .col-xs-10, .col-xs-11, .col-xs-12, .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9{
	padding-right:0px;
}
</style>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
    	<div class="row">
             <div class="col-md-3">
                 <div class="ibox float-e-margins">
                     <div class="ibox-title">
                         <span class="label label-primary pull-right">天</span>
                         <h5>保安人员</h5>
                     </div>
                     <div class="ibox-content">
                         <br>
                         <div class="row">
                             <div class="col-md-6">
                                 <h2 class="no-margins">
                                 	<span class="no-margins timer count-title" id="count-number" data-to="7" data-speed="1500"></span>.8
                                 	<span style="font-size:14px;">km</span>
                                 </h2>
                                 <br>
                                 <div class="font-bold text-navy"><i class="fa fa-user"></i> <small> 人均巡逻</small>
                                 </div>
                             </div>
                             <div class="col-md-6">
                                 <h2 class="no-margins">
                                 	<span class="no-margins timer count-title" id="count-number" data-to="2" data-speed="1500"></span>
                                 	<span style="font-size:14px;">件</span>
                                 </h2>
                                 <br>
                                 <div class="font-bold text-navy"><i class="fa fa-file-text"></i> <small> 处理事件</small>
                                 </div>
                             </div>
                         </div>
                         <br>
                     </div>
                 </div>
             </div>
             <div class="col-md-3">
                 <div class="ibox float-e-margins">
                     <div class="ibox-title">
                         <span class="label label-primary pull-right">天</span>
                         <h5>保洁人员</h5>
                     </div>
                     <div class="ibox-content">
                         <br>
                         <div class="row">
                             <div class="col-md-6">
                                 <h2 class="no-margins">
                                 	<span class="no-margins timer count-title" id="count-number" data-to="1240" data-speed="1500"></span>
                                 	<span style="font-size:14px;">㎡</span>
                                 </h2>
                                 <br>
                                 <div class="font-bold text-navy"><i class="fa fa-map"></i> <small> 人均保洁面积</small>
                                 </div>
                             </div>
                             <div class="col-md-6">
                                 <h2 class="no-margins">
                                 	<span class="no-margins timer count-title" id="count-number" data-to="150" data-speed="1500"></span>
                                 	<span style="font-size:14px;">户</span>
                                 </h2>
                                 <br>
                                 <div class="font-bold text-navy"> <i class="fa fa-home"></i> <small> 户数</small>
                                 </div>
                             </div>
                         </div>
                         <br>
                     </div>
                 </div>
             </div>
             <div class="col-md-3">
                 <div class="ibox float-e-margins">
                     <div class="ibox-title">
                         <span class="label label-primary pull-right">天</span>
                         <h5>维修人员</h5>
                     </div>
                     <div class="ibox-content">
                         <br>
                         <div class="row">
                             <div class="col-md-6">
                                 <h2 class="no-margins">
                                 	<span class="no-margins timer count-title" id="count-number" data-to="4" data-speed="1500"></span>
                                 	<span style="font-size:14px;">单</span>
                                 </h2>
                                 <br>
                                 <div class="font-bold text-navy"><i class="fa fa-clone"></i> <small> 维修单数</small>
                                 </div>
                             </div>
                             <div class="col-md-6">
                                 <h2 class="no-margins">
                                 	<span class="no-margins timer count-title" id="count-number" data-to="3" data-speed="1500"></span>
                                 	<span style="font-size:14px;">小时</span>
                                 </h2>
                                 <br>
                                 <div class="font-bold text-navy"><i class="fa fa-tachometer"></i> <small> 服务时长</small>
                                 </div>
                             </div>
                         </div>
                         <br>
                     </div>
                 </div>
             </div>
             <div class="col-md-3">
                 <div class="ibox float-e-margins">
                     <div class="ibox-title">
                         <span class="label label-primary pull-right">天</span>
                         <h5>绿化人员</h5>
                     </div>
                     <div class="ibox-content">
                     	<br>
                         <div class="row">
                             <div class="col-md-6">
                                 <h2 class="no-margins">
                                 	<span class="no-margins timer count-title" id="count-number" data-to="800" data-speed="1500"></span>
                                 	<span style="font-size:14px;">㎡</span>
                                 </h2>
                                 <br>
                                 <div class="font-bold text-navy"><i class="fa fa-map"></i> <small> 人均绿化面积</small>
                                 </div>
                             </div>
                             <div class="col-md-6">
                                 <h2 class="no-margins">
                                 	<span class="no-margins timer count-title" id="count-number" data-to="82" data-speed="1500"></span>
                                 	<span style="font-size:14px;">株</span>
                                 </h2>
                                 <br>
                                 <div class="font-bold text-navy"><i class="fa fa-tree"></i> <small> 植物</small>
                                 </div>
                             </div>
                         </div>
                         <br>
                     </div>
                 </div>
             </div>
        </div>
        <!-- 头部结束 -->
		<div class="row">
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>性别</h5>
                    </div>
                    <div class="ibox-content">
                    	<div id="empSex" style="height:200px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>年龄</h5>
                    </div>
                    <div class="ibox-content">
                    	<div id="empAge" style="height:200px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>部门</h5>
                    </div>
                    <div class="ibox-content">
                    	<div id="empDept" style="height:200px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>学历</h5>
                    </div>
                    <div class="ibox-content">
                    	<div id="empDegree" style="height:200px;"></div>
                    </div>
                </div>
            </div>
        </div>      
    </div>
</body>

</html>
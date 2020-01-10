<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设备档案</title> 
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/Hplus/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<script src="${ContextPath}/Hplus/js/plugins/staps/jquery.steps.min.js"></script>
<script src="${ContextPath}/Hplus/js/plugins/echarts/echarts-all.js"></script>
<script type="text/javascript" src="${ContextPath}/report/chargeReportByHis.js"></script>
</head>
<body class="gray-bg" style="height:100%">
    <div class="wrapper wrapper-content" style="height:100%">
    
	    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div style="width:100%;height:45px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row" style="">
		   		<div class="col-md-6" style="float:left;margin-top:10px;" >
		   			 <button id="btn1" onclick="openButtonWindow(1)" class="btn btn-primary " type="button" style="display:none"><i class="fa fa-bar-chart"></i> 图表展示</button>
		   			 <button id="btn2" onclick="openButtonWindow(2)" class="btn btn-primary " type="button"><i class="fa fa-list-alt"></i> 报表展示</button>
		   	    </div>
		   	</div>
	   	</div>
	   	<!-- 占位div -->
   		<div class="" style="width:100%;height:40px;"></div>
   		<!-- 占位div -->
   	
	    
		   
   		<!-- 数据表格区域  -->  
   		<div class="row" style="height:100%">
	            <div class="col-sm-12" style="height:100%">
	                <div class="ibox float-e-margins" style="height:100%">
	                    <div class="ibox-title">
	                        <h5>区域收费情况<small></small></h5>
	                        <div class="ibox-tools">
	                            <a class="collapse-link">
	                                <i class="fa fa-chevron-up"></i>
	                            </a>
	                            <a class="dropdown-toggle" data-toggle="dropdown" href="table_data_tables.html#">
	                                <i class="fa fa-wrench"></i>
	                            </a>
	                            <a class="close-link">
	                                <i class="fa fa-times"></i>
	                            </a>
	                        </div>
	                    </div>
	                    <div class="ibox-content" id="tempTable" style="height:100%">
	                    	<div id="tableArea" style="display:none" class="animated">
		                    	<div id="toolbar"></div>
								<table class="table-no-bordered" id="chargeInfo"></table>	
	                    	</div>
	                    	<div id="chartsArea" class="animated" style="height:100%">
	                    		<div class="echarts" id="charts_area" style="height:100%"></div>
	                    	</div>
	                    </div>
	                    
	                </div>
	            </div>
	        </div>
    </div>
</body>

</html>
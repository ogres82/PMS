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
<title>服务统计</title> 
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/Hplus/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<link href="${ContextPath}/Hplus/css/plugins/datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="${ContextPath}/Hplus/js/plugins/staps/jquery.steps.min.js"></script>
<script src="${ContextPath}/Hplus/js/plugins/echarts/echarts.min.js"></script>
<script src="${ContextPath}/Hplus/js/plugins/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${ContextPath}/Hplus/js/plugins/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${ContextPath}/report/serviceReport.js"></script>
</head>
<body class="gray-bg" style="height:">
    <div class="wrapper wrapper-content" style="height:">
    	  <div class="row">
          	<div class="col-sm-6">   
              <div class="ibox float-e-margins" style="height:100%">
                  <div class="ibox-title">
                      <h5>事件发生时间分析<small></small></h5>
                  </div>
                  <div class="ibox-content" id="tempTable" style="height:450px">
                  	<div id="chartsArea2" class="animated" style="height:400px">
                  		<div class="echarts" id="charts_area2" style="height:400px"></div>
                  	</div>
                  </div>
              </div>
             </div>
             <div class="col-sm-6">   
              <div class="ibox float-e-margins" style="height:100%">
                  <div class="ibox-title">
                      <h5>事件响应时长分析<small></small></h5>
                  </div>
                  <div class="ibox-content" id="tempTable" style="height:450px">
                  	<div id="chartsArea3" class="animated" style="height:400px">
                  		<div class="echarts" id="charts_area3" style="height:400px"></div>
                  	</div>
                  </div>
              </div>
             </div>
          </div> 
          <div class="row">
          	<div class="col-sm-6">   
              <div class="ibox float-e-margins" style="height:100%">
                  <div class="ibox-title">
                      <h5>业主满意度<small></small></h5>
                  </div>
                  <div class="ibox-content" id="tempTable" style="height:450px">
                  	<div id="chartsArea4" class="animated" style="height:400px">
                  		<div class="echarts" id="charts_area4" style="height:400px"></div>
                  	</div>
                  </div>
              </div>
             </div>
             <div class="col-sm-6">   
              <div class="ibox float-e-margins" style="height:100%">
                  <div class="ibox-title">
                      <h5>事件发生地分析<small></small></h5>
                  </div>
                  <div class="ibox-content" id="tempTable" style="height:450px">
                  	<div id="chartsArea5" class="animated" style="height:400px">
                  		<div class="echarts" id="charts_area5" style="height:400px"></div>
                  	</div>
                  </div>
              </div>
             </div>
          </div> 
          <div class="row">
          	<div class="col-sm-12">   
              <div class="ibox float-e-margins" style="height:100%">
                  <div class="ibox-title">
                      <h5>客户服务统计<small></small></h5>
                  </div>
                  <div class="ibox-content" id="tempTable" style="height:450px">
                  	<div id="tableArea" style="display:none" class="animated ">
                   	<div id="toolbar"></div>
					<table class="table-no-bordered" id="chargeInfo"></table>	
                  	</div>
                  	<div id="chartsArea1" class="animated" style="height:400px">
                  		<div class="echarts" id="charts_area1" style="height:400px"></div>
                  	</div>
                  </div>
              </div>
             </div>
         </div>    
    </div>
</body>

</html>
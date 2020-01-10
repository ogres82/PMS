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
<title>财务统计</title> 
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/Hplus/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<link href="${ContextPath}/Hplus/css/plugins/datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="${ContextPath}/Hplus/js/plugins/staps/jquery.steps.min.js"></script>
<script src="${ContextPath}/Hplus/js/plugins/echarts/echarts.min.js"></script>
<script src="${ContextPath}/Hplus/js/plugins/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${ContextPath}/Hplus/js/plugins/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${ContextPath}/report/chargeReport.js"></script>
</head>
<body class="gray-bg" style="height:">
    <div class="wrapper wrapper-content" style="height:">
    
  
   		 
   			<div class="row" style="">
	   			<div class="col-sm-6">
		                <div class="ibox float-e-margins" style="">
		                <!-- 
		                    <div class="ibox-title">
		                    
		                        <h5 class="col-sm-4">区域收费情况<small></small></h5>
		                        
		                        <div class="form-group form-group-sm" id="month1" style="display:none;float:left;margin-top:-3px;margin-bottom:0px;position:relative;left:20%;">
			                    	<label class="control-label col-sm-3" style="line-height:25px;padding:0;">日期</label>
			                    	<div class="col-sm-9">
			                    		<input class="form-control" id="monthPicker1" type="text" style="height:25px;line-height:25px;"/>
			                    	</div>
		                    	</div>
		                    	<div class="form-group form-group-sm" id="year1" style="display:none;float:left;margin-top:-3px;margin-bottom:0px;position:relative;left:20%;">
			                    	<label class="col-sm-3 control-label" style="line-height:25px;padding:0;">日期</label>
			                    	<div class="col-sm-9">
			                    		<input class="form-control" id="yearPicker1" type="text" style="height:25px;line-height:25px;"/>
			                    	</div>
		                    	</div>
		                        <div class="ibox-tools">
		                            <a class="dropdown-toggle" data-toggle="dropdown" href="table_data_tables.html#">
		                               	 时间选项      <i class="fa fa-calendar"></i>
		                            </a>
		                            <ul class="dropdown-menu dropdown-user">
			                            <li><a onclick="allIn(1)">全部</a>
			                            </li>
			                            <li><a onclick="byMonth(1)">按月</a>
			                            </li>
			                            <li><a onclick="byYear(1)">按年</a>
			                            </li>
			                        </ul>
		                        </div>
		                    </div>
		                    <div class="ibox-content" id="tempTable" style="height:450px">
		                    	<div id="tableArea" style="display:none" class="animated ">
			                    	<div id="toolbar"></div>
									<table class="table-no-bordered" id="chargeInfo"></table>	
		                    	</div>
		                    	
		                    	<div id="chartsArea1" class="animated" style="height:400px">
		                    		<div class="echarts" id="charts_area1" style="height:400px"></div>
		                    	</div>
		                    	<div id="chartsBuild1" class="animated" style="height:400px;display:none">
		                    		<div class="echarts" id="charts_build1" style="height:400px"></div>
		                    		<div style="float:right" id="back"><a href="####" onclick="back()">返回上一级</a></div>
		                    	</div>
		                    </div> 
		                </div>-->
		                	<div class="ibox-title">
		                        <h5 class="col-sm-4">收费年统计<small></small></h5>
		                    </div>
		                    <div class="ibox-content" id="tempTable" style="height:350px">
		                    	<div id="chartsYear" class="animated" style="height:320px">
		                    		<div class="echarts" id="charts_year" style="height:320px"></div>
		                    	</div>
		                    </div> 
		                </div>
		        </div>
		        <div class="col-sm-6">    
		                <div class="ibox float-e-margins" style="">
		                <!-- 
		                    <div class="ibox-title">
		                        <h5 class="col-sm-4">收费项收费情况<small></small></h5>
		                        <div class="form-group form-group-sm" id="month2" style="display:none;float:left;margin-top:-3px;margin-bottom:0px;position:relative;left:20%;">
			                    	<label class="control-label col-sm-3" style="line-height:25px;padding:0;">日期</label>
			                    	<div class="col-sm-9">
			                    		<input class="form-control" id="monthPicker2" type="text" style="height:25px;line-height:25px;"/>
			                    	</div>
		                    	</div>
		                    	<div class="form-group form-group-sm" id="year2" style="display:none;float:left;margin-top:-3px;margin-bottom:0px;position:relative;left:20%;">
			                    	<label class="col-sm-3 control-label" style="line-height:25px;padding:0;">日期</label>
			                    	<div class="col-sm-9">
			                    		<input class="form-control" id="yearPicker2" type="text" style="height:25px;line-height:25px;"/>
			                    	</div>
		                    	</div>
		                        <div class="ibox-tools">
		                            
		                            <a class="dropdown-toggle" data-toggle="dropdown" href="table_data_tables.html#">
		                               	 时间选项      <i class="fa fa-calendar"></i>
		                            </a>
		                            <ul class="dropdown-menu dropdown-user">
			                            <li><a onclick="allIn(2)">全部</a>
			                            </li>
			                            <li><a onclick="byMonth(2)">按月</a>
			                            </li>
			                            <li><a onclick="byYear(2)">按年</a>
			                            </li>
			                        </ul>
		                        </div>
		                    </div>
		                    <div class="ibox-content" id="tempTable" style="height:450px">
		                    	<div id="tableArea" style="display:none" class="animated ">
			                    	<div id="toolbar"></div>
									<table class="table-no-bordered" id="chargeInfo"></table>	
		                    	</div>
		                    	<div id="chartsArea2" class="animated" style="height:400px">
		                    		<div class="echarts" id="charts_area2" style="height:400px"></div>
		                    	</div>
		                    </div>
		                </div> 
		                -->
		                <div class="ibox-title">
		                        <h5 class="col-sm-4">收费月统计<small></small></h5>
		                </div>
	                    <div class="ibox-content" id="tempTable" style="height:350px">
	                    	<div id="chartsMonth" class="animated" style="height:320px">
	                    		<div class="echarts" id="charts_month" style="height:320px"></div>
	                    	</div>
	                    </div> 
		          </div>
		      </div>      
	      </div>
          <div class="row">
          	<div class="col-sm-6">   
	              <div class="ibox float-e-margins" style="">
	                  <div class="ibox-title">
	                      <h5>小区收费统计<small></small></h5>
	                  </div>
	                  <div class="ibox-content" id="tempTable" style="height:350px">
	                  	<div id="chartsArea" class="animated" style="height:320px">
	                  		<div class="echarts" id="charts_area" style="height:320px"></div>
	                  	</div>
	                  </div>
	              </div>
             </div>
             <div class="col-sm-6">   
	              <div class="ibox float-e-margins" style="">
	                  <div class="ibox-title">
	                      <h5>收费项目收费统计<small></small></h5>
	                  </div>
	                  <div class="ibox-content" id="tempTable" style="height:350px">
	                  	<div id="chartsType" class="animated" style="height:320px">
	                  		<div class="echarts" id="charts_type" style="height:320px"></div>
	                  	</div>
	                  </div>
	              </div>
             </div>
         </div>    
    </div>
</body>

</html>
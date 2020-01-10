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
<title>收费流水信息</title> 
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/ChargeManager/ChargeSerial.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    	<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
	    <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
	   			</div> 
			   		
		   	</div>
	   	</div>
	   	<i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
	   	<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
   	
	    
		
		<div class="row">
   			<div class="col-sm-12">
   				<blockquote class="layui-elem-quote" style="background-color:white">
   					<div class="row">
   						<div class="col-sm-10">
   							<div class="row">
   								<div class="col-sm-2">
									<select class="form-control" name="type" id="charge_type_name_select">
										  <option disabled selected style='display:none;' value="">收费项名称</option>
								  	</select>
							  	</div>
							  	<div class="col-sm-2">
									<select class="form-control" name="type" id="charge_type_select">
										  <option disabled selected style='display:none;' value="">类型</option>
								  	</select>
							  	</div>
							  	<div class="col-sm-2">
									<select class="form-control" name="type" id="paid_mode_select">
										  <option disabled selected style='display:none;' value="">收款方式</option>
								  	</select>
							  	</div>
							  	<div class="col-sm-2">
									<select class="form-control" name="type" id="state_select">
										  <option disabled selected style='display:none;' value="">状态</option>
								  	</select>
							  	</div>
								<div class="col-sm-2">
									<input type="text" name="beginTime" id="beginTime" class="form-control layer-date" onclick="laydate.render({elem:this});" placeholder="开始时间" >
								</div>
								<div class="col-sm-2">
									<input type="text" name="endTime" id="endTime" class="form-control layer-date" onclick="laydate.render({elem:this});" placeholder="结束时间">
								</div>
   							</div>
						 </div>
						 <div class="col-sm-2" style="float:right">
						 	<button id="buttonSearch" class="btn btn-primary" type="button">查询</button>
						  	<button id="clearSearch" onclick="clearSearch()" class="btn btn-primary" type="button">清空</button>
						 </div>
					 </div>
   				</blockquote>
   			</div>
   		</div>
		
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>收费流水信息<small></small></h5>	                        
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="chargeSerial"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	        </div>
   		
   		
   		
              
       <!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" style="width:900px">
			<div class="modal-content">
			  <div class="modal-header" style="background:#18a689">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel" style="color:white">费用详情</h4>
			  </div>
			  <div class="modal-body">
					<div class="ibox-content" id="detailTable">
						<table class="table-no-bordered" id="chargeSerialDetailInfo"></table>		                    	
	                </div>
			  </div>			  
			</div>
		  </div>
		</div>              
    </div>
</body>

</html>
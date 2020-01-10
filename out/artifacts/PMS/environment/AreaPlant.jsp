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
<title>植被区域管理</title> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/environment/AreaPlant.js"></script>
<link href="${ContextPath}/Hplus/bootstrap/css/bootstrap-editable.css" rel="stylesheet" />
<script src="${ContextPath}/Hplus/bootstrap/js/bootstrap-editable.js"></script>
 <script src="${ContextPath}/Hplus/bootstrap/js/bootstrap-table-editable.js"></script>

<style>
  #moreSearch:hover{
  	cursor:pointer;
  }
</style>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    
	    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
	    <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
		   	    </div>
		   	</div>
	   	</div>
	   	<i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
	   	<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
   		
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>植被区域管理<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="areaInfo"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	        </div>
      
		<!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:900px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalTitle" style="color:white"></h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm1" class="form-horizontal" role="form">
					
								<fieldset id="basicInfo">
								<legend style="font-size:15px;border:0;"></legend>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-2 control-label">植被区域名称</label>
											  <div class="col-sm-4">
													<input class="form-control validate[required]" id="areaName" type="text" placeholder="必填项"/>
											  </div>
											    <label class="col-sm-2 control-label">责任人</label>
											  <div class="col-sm-4">
											  	<input class="form-control validate[required]" id="areaResPerson" type="text" placeholder="必填项"/>
											  </div>	
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-2 control-label" >备注</label>
											  <div class="col-sm-10">
													<input class="form-control" id="remark" type="text"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm plantview">
									      <label class="col-sm-2 control-label">种植的植被</label>
										  <div class="col-sm-6">
											<table class="table-no-bordered" id="areaPlantView"></table>	
										  </div>
								       </div>
								  </fieldset>
			
							</form>
		          		</div>
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btPlantAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
				    </div>
				    <input class="form-control" id="areaId" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		 	
		 	
		 <!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:1000px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalTitle1" style="color:white"></h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm2" class="form-horizontal" role="form">
					
								<fieldset id="basicInfo">
								<!-- 
									<div class="form-group form-group-sm ">
										<label class="col-sm-6 control-label" style="text-align:center">植被列表</label>
										<label class="col-sm-6 control-label" style="text-align:center">添加植被</label>
									</div>
								     -->
								   <div class="form-group form-group-sm ">
									  <div class="col-sm-6">
										<table class="table-no-bordered" id="plantInfo"></table>	
									  </div>
									  <div class="col-sm-6">
										<table class="table-no-bordered" id="areaPlantInfo"></table>	
									  </div>
								   </div>
								</fieldset>
			
							</form>
		          		</div>
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btPlantAreaAdd" onclick="openSavePlantAreaButton()" class="btn btn-primary " type="button" >保存</button>
				    </div>
				    <input class="form-control" id="areaId1" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		 	
    </div>
</body>

</html>
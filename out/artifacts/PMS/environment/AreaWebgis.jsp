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
<title>区域管理</title> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/common/webgis/messenger.js"></script>
<script type="text/javascript" src="${ContextPath}/common/webgis/json2.js"></script>
<script type="text/javascript" src="${ContextPath}/environment/AreaWebgis.js"></script>
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
	   	<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
   		
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-4">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>植被区域<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable" style="overflow:auto">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="areaInfo"></table>	
	                    </div>
	                </div>
	            </div>
	            
	            <div class="col-sm-8">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>地图<small></small></h5>
	                    </div>
	                    <div class="ibox-content">
	          				<div class="row">
						   		<!--这里必须使用ChildFrame作为id，如果要修改这个id，则同时需要修改patent.js中对应的Id 
						   		http://120.77.2.35:8088  http://192.168.1.150:8088/WebGISModel.html?data={"moduleName":"AreaMange","busData":{"typeName":"g_security_area"}}
						   		-->
							    <iframe src='http://120.77.2.35:8080/webgis/webgis.html' id="ChildFrame" frameborder="0" marginheight="0"
							            marginwidth="0" style="width: 100%;"></iframe>
							    <!--这个应用要放在iframe后面，因为这里面在调用的时候引用了Iframe http://192.168.1.39:8080/BS/lewan3.17-old.html-->
							    <script type="text/javascript" src="${ContextPath}/common/webgis/parent.js"></script>
						   	</div>
						   	<div style="height:40px;"></div>
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
											  <label class="col-sm-2 control-label">区域名称</label>
											  <div class="col-sm-4">
													<input class="form-control validate[required]" id="areaName" type="text" disabled/>
											  </div>
											  <label class="col-sm-2 control-label">区域面积</label>
											  <div class="col-sm-4">
													<input class="form-control validate[required]" id="areaGisArea" type="text" disabled/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-2 control-label">责任人</label>
											  <div class="col-sm-4">
											  		<div class="input-group">
														<input type="text" class="form-control validate[required]" id="areaResPersonName" placeholder="员工号/姓名/部门/岗位">
														<div class="input-group-btn">
															<button type="button" class="btn btn-white btn-sm dropdown-toggle" data-toggle="dropdown">
																<span class="caret"></span>
															</button>
															<ul class="dropdown-menu dropdown-menu-right" role="menu">
															</ul>
														</div>
											 		</div>
													<input class="form-control" type="hidden" id="areaResPerson" type="text"/>
											  </div>	
											  <label class="col-sm-2 control-label" >备注</label>
											  <div class="col-sm-4">
													<input class="form-control" id="remark" type="text"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm" id="plantadd">
										  <div class="col-sm-7">
											<table class="table-no-bordered" id="plantInfo"></table>	
										  </div>
										  <div class="col-sm-1" style="text-align:center;padding:0;margin-top:100px;">
											 <button onclick="selectPlant(1)" class="btn btn-primary" type="button" style="margin-top:20px;"><i class="fa fa-plus"></i>&nbsp;添加</button>
						    		 		 <button onclick="selectPlant(2)" class="btn btn-default" type="button" style="margin-top:10px;"><i class="fa fa-trash-o"></i> 删除</button>
										  </div>
										  <div class="col-sm-3">
											<table class="table-no-bordered" id="areaPlantInfo"></table>	
										  </div>
								   	   </div>
									   <div class="form-group form-group-sm" id="plantview">
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
				    <input class="form-control" id="areaGisId" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		 	
    </div>
</body>

</html>
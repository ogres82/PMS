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
<title>车位区域信息</title> 
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/basicInfo/ParkingArea.js"></script>
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
	                        <h5>车位区域信息<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="parkingAreaInfo"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	        </div>
      
		<!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:800px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalTitle" style="color:white">车库信息</h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm1" class="form-horizontal" role="form">
					
								<fieldset id="basicInfo">
								<legend style="font-size:15px;border:0;"></legend>
									  
									   <div class="form-group form-group-sm ">
									   		 
											  <label class="col-sm-2 control-label">所属楼盘</label>
											  <div class="col-sm-4">
													<select class="form-control validate[required]" id="build_id" onchange = "changeAllArea(this)"> 
													  <option value="">---请选择---</option>
													</select> 
											  </div>
											  <label class="col-sm-2 control-label">所属小区</label>
											  <div class="col-sm-4">
													<select class="form-control validate[required]" id="belongComId" disabled="disabled"> 
													  <option value="">---请选择---</option>
													</select> 
											  </div>
									   </div>
									    <div class="form-group form-group-sm ">
											  <label class="col-sm-2 control-label">车库名称</label>
											  <div class="col-sm-4">
													<input class="form-control" id="parkName" type="text"/>
											  </div>
											  <label class="col-sm-2 control-label" >车库编号</label>
											  <div class="col-sm-4">
													<input class="form-control validate[required]" id="parkCode" type="text" placeholder="必填项"/>
											  </div>
									   </div>
									    <div class="form-group form-group-sm ">
											  <label class="col-sm-2 control-label" >车库位置</label>
											  <div class="col-sm-10">
													<input class="form-control validate[required]" id="parkPosition" type="text" placeholder="必填项"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-2 control-label">车库状态</label>
											  <div class="col-sm-4">
													<select class="form-control validate[required]" id="parkState" placeholder="必填项"> 
													  <option value="">---请选择---</option>
													</select> 
											  </div>
											  <label class="col-sm-2 control-label" >启用日期</label>
											  <div class="col-sm-4">
													<input class="form-control laydate-icon layer-date" id="usingDate" onclick="laydate.render({elem: this});" type="text"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-2 control-label">层数</label>
											  <div class="col-sm-4">
													<input class="form-control validate[required]" id="floors" type="text" placeholder="必填项"/>
											  </div>
											  <label class="col-sm-2 control-label" >车位数</label>
											  <div class="col-sm-4">
													<input class="form-control validate[required]" id="parkNum" type="text" placeholder="必填项"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-2 control-label" >备注</label>
											  <div class="col-sm-10">
													<input class="form-control" id="remark" type="text" />
											  </div>
									   </div>
								  </fieldset>
			
							</form>
		          		</div>
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btParkingAreaAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
				    </div>
				    <input class="form-control" id="parkId" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
    </div>
</body>

</html>
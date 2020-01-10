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
<title>车位信息</title> 
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/basicInfo/Parking.js"></script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    
	    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
		   		
		   	    </div>
			    <div class="col-md-6"  style="margin-top:10px;float:left">
					<button type="button" class="btn btn-primary pull-right" id="moreSearch">
					    更多查询 <span class="caret"></span>
					</button>
		   		</div>    		
		   	</div>
   		</div>
   		<i class="fa fa-search" style="position:fixed;right:122px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
		<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
   		
	    <!-- 更多查询区域  -->
	   	<div class ="row ibox float-e-margins" style="display:none;text-align:center;margin-left:auto;margin-right:auto;" id="more_search">
			<div class="ibox-content col-sm-12 b-r">
				<form class="form-horizontal">
							   
					<div class="form-group" style="margin-top:20px;">
						<label class="col-sm-1 control-label">业主姓名</label>
	
						<div class="col-sm-3">
							<input type="text"  class="form-control"> 
						</div>
	
						<label class="col-sm-1 control-label">收费类型</label>
	
						<div class="col-sm-3">
							<input type="text"  class="form-control">
						</div>
						<label class="col-sm-1 control-label">房间编号</label>
	
						<div class="col-sm-3">
							<input type="text" class="form-control">
						</div>
					</div>
					<div class="col-sm-12">
						<button type="button" style="float:right;" class="btn btn-primary"><i class="fa fa-search"></i>查询</button> 
					</div>
				 </form>
			</div>
	    </div>
		   
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>车位信息<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="parkingInfo"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	        </div>
      
		<!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:1020px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalTitle" style="color:white">业主信息</h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm1" class="form-horizontal" role="form">
					
								<fieldset id="basicInfo">
								<legend style="font-size:15px;border:0;"></legend>
									  
									   <div class="form-group form-group-sm ">
									   		 
											  <label class="col-sm-1 control-label">所属楼盘</label>
											  <div class="col-sm-3">
													<select class="form-control validate[required]" id="build_id" onchange = "changeAllArea(this)"> 
													  <option value="">---请选择---</option>
													</select>  
											  </div>
											  <label class="col-sm-1 control-label" >所属小区</label>
											  <div class="col-sm-3">
													<select class="form-control validate[required]" id="community_id" onchange = "changeAreaProperty(this)" disabled="disabled"> 
													  <option value="">---请选择---</option>
													</select>  
											  </div>
											  <label class="col-sm-1 control-label" >所属车库</label>
											  <div class="col-sm-3">
													<select class="form-control validate[required]" id="belongParkNo" disabled="disabled"> 
													  <option value="">---请选择---</option>
													</select>  
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
									   		 
											  <label class="col-sm-1 control-label">车位编号</label>
											  <div class="col-sm-3">
													<input class="form-control validate[required]" id="carportNo" type="text" placeholder="必填项"/>
											  </div>
											  <label class="col-sm-1 control-label" >车位状态</label>
											  <div class="col-sm-3">
													<select class="form-control validate[required]" id="carportStatus"> 
													  <option value="">---请选择---</option>
													</select> 
											  </div>
											  <label class="col-sm-1 control-label" >车位楼层</label>
											  <div class="col-sm-3">
													<input class="form-control validate[required]" id="carportFloor" type="text" placeholder="必填项"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
									   		  <label class="col-sm-1 control-label" >车位类型</label>
											  <div class="col-sm-3">
													<select class="form-control" id="carportType"> 
													  <option value="">---请选择---</option>
													</select> 
											  </div>
											  <input class="form-control" id="ownerId" type="text" style="display:none"/>
									   		  <label class="col-sm-1 control-label" >业主姓名</label>
											  <div class="col-sm-3">
											  		<div class="input-group">
														<input class="form-control" id="ownerName" type="text" placeholder="手机号"/>
														<span class="input-group-btn">
														  <button class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="height:30px;" type="button">
															 <span class="caret"></span>
														  </button>
														  <ul class="dropdown-menu dropdown-menu-right" role="menu">
														  </ul>
													   </span>
												   </div>
											  </div>
											  <label class="col-sm-1 control-label">业主电话</label>
											  <div class="col-sm-3">
													<input class="form-control" id="ownerPhone" type="text"/>
											  </div>
											  
											  
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label" >车牌号</label>
											  <div class="col-sm-3">
													<input class="form-control" id="licensePlateNo" type="text"/>
											  </div>
											  <label class="col-sm-1 control-label" >备注</label>
											  <div class="col-sm-7">
													<input class="form-control" id="remark" type="text" />
											  </div>
									   </div>
								  </fieldset>
			
							</form>
		          		</div>
		          		<!-- 
		          		<div id="tab-2" class="tab-pane" >
			          		<div style="height: 400px">
			          			<iframe src="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=7501" frameborder="0" marginheight="0" marginwidth="0" height="100%" width="100%" id="grapIframe"></iframe>
			          		</div>
	          			</div>
	          			 -->
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btParkingAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
				    </div>
				    <input class="form-control" id="carportId" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
    </div>
</body>

</html>
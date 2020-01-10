<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser" %> 
<%@ page import="com.bstek.bdf2.core.context.ContextHolder" %>
<% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
    DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
    request.setAttribute("loginUser",user);
    String rpt_id=request.getParameter("rpt_id");
    request.setAttribute("rpt_id",rpt_id);
%>
<!DOCTYPE html>
<html>
<head>
<title>派工信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
var rpt_id='${rpt_id}';
</script>
<script type="text/javascript" src="${ContextPath}/assignwork/DisPatchHandle.js">
</script>
</head>
<body class="gray-bg">

 <!-- 弹出窗口区域，触发事件后弹出  -->
			<div class="modal-dialog8" style="width:100%" id="windowId">
				<div class="modal-content">
					<div class="modal-header" style="background:#18a689">
						<h4 class="modal-title" id="myModalLabel" style="color:white">客户处理</h4>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs">
			              	<li class="active"><a data-toggle="tab" href="#tab-1">基本信息</a>
			              	</li>
			              	<li class=""><a data-toggle="tab" href="#tab-2">流程图</a>
			              	</li>
		          		</ul>
					
						<div class="tab-content">
		          			<div id="tab-1" class="tab-pane active">
		          			
		          				<form id="myForm2" class="form-horizontal" role="form">
			          				<fieldset>
			          					<legend style="font-size:15px"></legend>
										<div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">派工单号</label>
											  <div class="col-sm-3">
													<input class="form-control" id="dispatch_id" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">事件单号</label>
											  <div class="col-sm-3">
													<input class="form-control" id="mtn_id" type="text"  readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">							      
											  <label class="col-sm-1 control-label">报事人</label>
											  <div class="col-sm-3">
													<input class="form-control" id="rpt_name" type="text"  readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">呼入电话</label>
											  <div class="col-sm-3">
													<input class="form-control" id="in_call" type="text"  readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">地址</label>
											  <div class="col-sm-3">
													<input type="text" style="" class="form-control" id="addres" readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">受理人</label>
											  <div class="col-sm-3">
													<input class="form-control" id="createby" type="text"  readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">受理时间</label>
											  <div class="col-sm-3">
													<input class="form-control" id="createTime" type="text"  readonly="readonly"/>
											  </div>
											 
									   </div>
			
									   <div class="form-group form-group-sm ">
										  <label class="col-sm-1 control-label" >报修描述</label>
										  <div class="col-sm-11">
											 <textarea class="form-control" rows="3" style="height:60px" id="mtn_detail"  readonly="readonly"></textarea>
										  </div>
									   </div>
									   <div class="form-group form-group-sm ">
										  <label class="col-sm-1 control-label" >紧急程度</label>
										  <div class="col-sm-3">
											  <select class="form-control" id="mtn_emergency"> 
												  <option value="">--请选择--</option>
											  </select>  
										  </div>
										  <label class="col-sm-1 control-label" >派工状态</label>
										  <div class="col-sm-3">
											 <select class="form-control" id="dispatch_status" disabled="disabled"> 
													  <option value="">--请选择--</option>
											 </select>  
										  </div>
										   <label class="col-sm-1 control-label">派工时间</label>
											  <div class="col-sm-3">
													<input class="form-control laydate-icon layer-date" id="dispatch_time" type="text"  readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">处理人</label>
											  <div class="col-sm-3">
													<div class="input-group">
													   <input type="text" class="form-control validate[required]" id="dispatch_handle_id"  style="display:none" />
													   <input type="text" class="form-control validate[required]" id="deptName"  style="display:none">
													   <input type="text" class="form-control validate[required]" placeholder="请输入部门名称" id="dispatch_handle_username" >
													   <span class="input-group-btn">
															  <button class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="height:30px;" type="button">
																 <span class="caret"></span>
															  </button>
															  <ul class="dropdown-menu dropdown-menu-right" role="menu">
															  </ul>
													   </span>
													</div>
											  </div>
											 
									   </div>
									   <div class="form-group form-group-sm " id="weixiu">
											  <label class="col-sm-1 control-label">维修类别</label>
											  <div class="col-sm-3">
													<select class="form-control" id="mtn_type" onchange="changeMtnPriority()"> 
													  <option value="">--请选择--</option>
												  </select>  
											  </div>
											  <div id="div_mtn_priority">
												  <label class="col-sm-1 control-label">维修意见</label>
												  <div class="col-sm-3">
														<select class="form-control" id="mtn_priority"> 
														  <option value="">--请选择--</option>
													  </select>  
												  </div>
											  </div>
											  
											   <label class="col-sm-1 control-label">到达时间</label>
											  <div class="col-sm-3">
													<input class="form-control laydate-icon layer-date" id="dispatch_arrive_time" type="text" />
											  </div>
									   </div>
									   <div class="form-group form-group-sm " id="weixucl">
											  <label class="col-sm-1 control-label">材料费(元)</label>
											  <div class="col-sm-3">
													<input class="form-control validate[custom[number]]" id="material_cost" type="text"  placeholder="输入材料费" onblur="this.value=outputmoney(this.value);"/>
											  </div>
											   <label class="col-sm-1 control-label ">人工费(元)</label>
											  <div class="col-sm-3">
													<input class="form-control validate[custom[number]]" id="labor_cost" type="text" placeholder="输入人工费" onblur="this.value=outputmoney(this.value);"/>
											  </div>
											  
											  <label class="col-sm-1 control-label">总额(元)</label>
											  <div class="col-sm-3">
													<input class="form-control validate[custom[number]]" id="dispatch_expense" type="text" onblur="this.value=outputmoney(this.value);"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm " id="weixiujg">
										  <label class="col-sm-1 control-label" >维修结果</label>
										  <div class="col-sm-11">
											 <textarea class="form-control" style="height:60px" rows="3" id="dispatch_result"></textarea>
										  </div>
									   </div>
									   <div class="form-group form-group-sm " id="xiaodan">
										  <label class="col-sm-1 control-label">销单时间</label>
										  <div class="col-sm-3">
												<input class="form-control" id="dispatch_finish_time" type="text" readonly="readonly"/>
										  </div>
									   </div>
									   <div class="form-group form-group-sm "></div>
									   <div class="modal-footer">
<!-- 											<button type="button" onclick="javascript:window.opener=null;window.close();" class="btn btn-default" data-dismiss="modal" >关闭</button>
 -->											<button id="dispbtn5" onclick="openDispatchButton(5)" class="btn btn-primary " type="button"> 派工</button>
											<button id="dispbtn6" onclick="openDispatchButton(6)" class="btn btn-primary " type="button"> 现场确认</button>
											<button id="dispbtn7" onclick="openDispatchButton(7)" class="btn btn-primary " type="button"> 销单</button>
											<button id="dispbtn8" onclick="openDispatchButton(8)" class="btn btn-primary " type="button"> 取消</button>
									   </div>
									</fieldset>
								</form>
		          			</div>
		          			<div id="tab-2" class="tab-pane" >
				          		<div style="height: 400px">
				          			<iframe src="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=507501" frameborder="0" marginheight="0" marginwidth="0" height="100%" width="100%" id="grapIframe"></iframe>
				          		</div>
	          			</div>
		          		</div>
		          		
					</div>
				</div>
		
		<!-- 派工结束 --> 

</body>
</html>
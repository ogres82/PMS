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
<link href="${ContextPath}/Hplus/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet" />
<style>
	.fixed-table-body{
		overflow:inherit;
	}
	#textCount {
    font-weight: 700;
    font-size: 18px;
    font-style. normal;
    font-family: '楷体';
    color:#9c0028;
    }
   #myspan{display:block;float:right;}
	
</style>
<script type="text/javascript" src="${ContextPath}/assignwork/AssignWorkMessage.js">
</script>
</head>
<body class="gray-bg">

 <!-- 弹出窗口区域，触发事件后弹出  -->
			<div class="modal-dialog8" style="width:100%" id="windowId">
				<div class="modal-content">
					<div class="modal-header" style="background:#18a689">
						<h4 class="modal-title" id="myModalLabel" style="color:white">事件信息</h4>
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
											   		  <div><!-- 隐藏保存业主id及房间id -->
												  	  	 <input class="form-control" type="hidden" id="ownerId"  />							      
													  	 <input class="form-control" type="hidden" id="roomId"  />
													  	 <input class="form-control" type="hidden" id="roomNo"  />
													  </div>
													  <label class="col-sm-1 control-label">报事人</label>
													  <div class="col-sm-3">
															<input id="rpt_name" type="text" class="form-control validate[required]" placeholder="报事人必填项"/>
													  </div>
													  <label class="col-sm-1 control-label" >地址</label>
													  <div class="col-sm-7">
															<input type="text" class="form-control validate[required]" placeholder="地址必填" id="addres" />
													  </div>
											   </div>
										
									  <div class="form-group form-group-sm ">
											   		  <label class="col-sm-1 control-label" >电话</label>
													  <div class="col-sm-3">
															<input class="form-control validate[required]"  placeholder="电话号码必填" id="in_call" type="text" />
													  </div>
											   		  <label class="col-sm-1 control-label">事件来源</label>
													  <div class="col-sm-3">
															<select class="form-control" id="event_source"> 
															  <option value="">---请选择---</option>
														    </select>  
													  </div>
													  <label class="col-sm-1 control-label" >事件类别</label>
													  <div class="col-sm-3">
															<select class="form-control" id="event_type" onchange="changeEventType(this)"> 
															  <option value="">---请选择---</option>
														    </select>  
													  </div>
													  <!--  <label class="col-sm-1 control-label">事件单号</label>-->
													  <div class="col-sm-3">
															<input class="form-control"  id="rpt_id" type="hidden" readonly="readonly"/>
													  </div>
													  								  
											   </div>
									    <div class="form-group form-group-sm ">
													  <label class="col-sm-1 control-label" >关联录音</label>
													  <div class="col-sm-3">
															<input class="form-control" id="addres_record" type="text" readonly="readonly"/>
													  </div>
													  <label class="col-sm-1 control-label">受理人</label>
													  <div class="col-sm-3">
															<input class="form-control" id="createby" type="text" readonly="readonly"/>
													  </div>
													  <label class="col-sm-1 control-label" >受理时间</label>
													  <div class="col-sm-3">
															<input class="form-control" id="createTime" type="text" readonly="readonly"/>
													  </div>
											   </div>
			
									</fieldset>
									 <fieldset id="bxdetail" style="display:">
												<legend style="font-size:15px">--报修明细--</legend>
												<div class="form-group form-group-sm ">
												  <label class="col-sm-1 control-label" >报修描述</label>
												  <div class="col-sm-11">
													 <textarea rows="3" id="mtn_detail"  style="height:60px" class="form-control validate[required]"   placeholder="报修描述必填"></textarea>
												   <div id="myspan">  
												       还可输入<span id="textCount">500</span>个<br />
												   </div>  
												  </div>							  
											   </div>
											   <div class="form-group form-group-sm ">
												  <label class="col-sm-1 control-label" >紧急程度</label>
												  <div class="col-sm-3">
													  <select class="form-control" id="mtn_emergency" onchange="changeEmergency(this)"> 
														  <option value="">--请选择--</option>
													  </select>  
												  </div>
												  <label class="col-sm-1 control-label" >事件状态</label>
												  <div class="col-sm-3">
													 <select class="form-control" id="dispatch_status" disabled="disabled"> 
														  <option value="">--请选择--</option>
													  </select>  
												  </div>
											      <label class="col-sm-1 control-label" >完成时间</label>
												  <div class="col-sm-3">
													 <input class="form-control" id="dispatch_finish_time" type="text" readonly="readonly"/>
												  </div>
											   </div>
											
									   <div class="form-group form-group-sm " id="handleId">
											  <label class="col-sm-1 control-label">处理人</label>
											  <div class="col-sm-3">
													<div class="input-group">
													   <input type="text" class="form-control validate[required]" id="dispatch_handle_id"  style="display:none" />
													   <input type="text" class="form-control validate[required]" id="deptName"  style="display:none">
													   <input type="text" class="form-control validate[required]" name='dispatch_handle_username' placeholder="请输入部门名称" id="dispatch_handle_username"  >
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
					                      <fieldset>
										 	<legend style="font-size:15px">--图片--</legend>
										 		<div id="eventImgShow">
				                    			</div> 
										 </fieldset>
										  	   <div class="form-group form-group-sm "></div>
											   <div class="modal-footer">
													<button id="btAssigenAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
													<button id="btDisPatchHandle" onclick="openSendDisPatchButton()" class="btn btn-primary " type="button" >派工</button>
											   </div>
											<input class="form-control" id="rpt_kid" type="hidden" />
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
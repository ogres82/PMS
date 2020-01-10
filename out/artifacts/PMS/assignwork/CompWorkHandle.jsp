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
<title>投诉派工单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
var rpt_id='${rpt_id}';
</script>
<script type="text/javascript" src="${ContextPath}/assignwork/CompWorkHandle.js">
</script>
</head>
<body class="gray-bg">

		<div class="modal-dialog8" style="width:100%" id="windowId">
				<div class="modal-content">
					<div class="modal-header" style="background:#18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel" style="color:white">投诉派工单信息</h4>
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
													<input class="form-control" id="comp_id" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">事件单号</label>
											  <div class="col-sm-3">
													<input class="form-control" id="mtn_id" type="text" readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">投诉人</label>
											  <div class="col-sm-3">
													<input class="form-control" id="rpt_name" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">呼入电话</label>
											  <div class="col-sm-3">
													<input class="form-control" id="in_call" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">地址</label>
											  <div class="col-sm-3">
													<input class="form-control" id="addres" type="text" readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">受理人</label>
											  <div class="col-sm-3">
													<input class="form-control" id="createby" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">受理时间</label>
											  <div class="col-sm-3">
													<input class="form-control" id="createTime" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">派工时间</label>
											  <div class="col-sm-3">
													<input class="form-control laydate-icon layer-date" id="comp_createTime" type="text" readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
										  <label class="col-sm-1 control-label" >投诉情况</label>
										  <div class="col-sm-11">
											 <textarea class="form-control" rows="3" style="height:60px" id="comp_detail"></textarea>
										  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label" >投诉级别</label>
											  <div class="col-sm-3">
												  <select class="form-control" id="comp_emergency" disabled="disabled">
													  <option>--请选择--</option>
												  </select>  
											  </div>
											  <label class="col-sm-1 control-label" >派工状态</label>
											  <div class="col-sm-3">
												  <select class="form-control" id="comp_status" disabled="disabled">
													  <option>--请选择--</option>
												  </select>
											  </div>
											  <label class="col-sm-1 control-label">处理人</label>
											  <div class="col-sm-3">
													<div class="input-group">
													   <input type="text" class="form-control" id="comp_operator_id" style="display:none">
													   <input type="text" class="form-control validate[required]" placeholder="请输入部门名称" id="comp_operator_username" readonly="readonly">													   
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
									   <div class="form-group form-group-sm " style="display:none">
											  <label class="col-sm-1 control-label">处理过程</label>
											  <div class="col-sm-11">
													<input class="form-control" id="comp_process" type="text" style="height: 60px"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm " id="custmReply">
										  <label class="col-sm-1 control-label">客服回复</label>
										  <div class="col-sm-11">
											 <textarea class="form-control validate[required]" rows="3" id="comp_reply"  style="height: 60px"></textarea>
										  </div>
									   </div>					
									   <div class="form-group form-group-sm " id="myresult">
										  <label class="col-sm-1 control-label" >处理结果</label>
										  <div class="col-sm-11">
											 <textarea class="form-control validate[required]" rows="3" id="comp_result" style="height: 60px"></textarea>
										  </div>
									   </div>
									   <div class="form-group form-group-sm " id="handleResult">
										  <label class="col-sm-1 control-label">是否解决</label>
										  <div class="col-sm-3">
												  <select class="form-control  validate[required]" id="comp_solve"> 
													  <option>--请选择--</option>
												  </select>  
										  </div>
										  <label class="col-sm-1 control-label">满意度</label>
										  <div class="col-sm-3">
												  <select class="form-control  validate[required]" id="comp_degree"> 
													  <option>--请选择--</option>
												  </select>  
										  </div>
										  <label class="col-sm-1 control-label">销单时间</label>
										  <div class="col-sm-3">
												<input class="form-control" id="comp_finish_time" type="text" readonly="readonly"/>
										  </div>
									   </div>
									   <div class="form-group form-group-sm "></div>
									   <div class="modal-footer">
<!-- 											<button type="button" onclick="javascript:window.opener=null;window.close();"  class="btn btn-default" data-dismiss="modal">关闭</button>
 -->										<button id="compBtn5" onclick="openCompButton(5)" class="btn btn-primary " type="button">派工</button>
				    		  				<button id="compBtn6" onclick="openCompButton(6)" class="btn btn-primary " type="button">处理</button>
											<button id="compBtn7" onclick="openCompButton(7)" class="btn btn-primary " type="button">消单</button>
											<button id="btAssigenHandle" onclick="openCompHandleButton()" class="btn btn-primary " type="button" >回复</button>
											
									   </div>
								   </fieldset>
								</form>
							
							</div>
							<div id="tab-2" class="tab-pane" >
				          		<div style="height: 400px">
				          			<iframe src="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=217507" frameborder="0" marginheight="0" marginwidth="0" height="100%" width="100%" id="grapIframe"></iframe>
				          		</div>
		          			</div>
						</div>
					</div>
				</div>
			</div>
		</div>
    	<!-- 弹出结束窗口 -->
    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>
</html>
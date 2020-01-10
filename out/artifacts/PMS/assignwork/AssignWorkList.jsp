<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser"%>
<%@ page import="com.bstek.bdf2.core.context.ContextHolder"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
	request.setAttribute("loginUser", user);
%>

<!DOCTYPE html>
<html>
<head>
<title>报障受理信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/basicInfo/fileinput.min.css" rel="stylesheet" />
<script type="text/javascript" src="${ContextPath}/basicInfo/fileinput.min.js"></script>
<script type="text/javascript" src="${ContextPath}/assignwork/AssignWorkList.js"></script>
<link href="${ContextPath}/Hplus/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet" />
<script type="text/javascript" src="${ContextPath}/Hplus/js/plugins/fancybox/jquery.fancybox.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<style>
.fixed-table-body {
	overflow: inherit;
}

#textCount {
	font-weight: 700;
	font-size: 18px; font-style . normal;
	font-family: '楷体';
	color: #9c0028;
}

#myspan {
	display: block;
	float: right;
}

.input-sm, .form-horizontal .form-group-sm .form-control {
	height: 34px;
}

.hidden-xs {
	display: inline !important;
}
</style>
</head>
<body class="gray-bg">

	<div class="wrapper wrapper-content">
		<div style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;" id="topToolbar"></div>
			</div>
		</div>
		<i class="fa fa-search" style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<div class="row">
			<div class="col-sm-12">
				<blockquote class="layui-elem-quote" style="background-color: white">
					<div class="row">
						<div class="col-sm-2">
							<select class="form-control" name="type" id="businessFromSearch">
								<option disabled selected style='display: none;' value="">事件来源</option>
								<option value="0">呼叫中心</option>
								<option value="1">客户端</option>
								<option value="2">门户网站</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="eventTypeSearch">
								<option disabled selected style='display: none;' value="">事件类别</option>
								<option value="0">报障报修</option>
								<option value="1">投诉建议</option>
							</select>
						</div>
						<div class="col-sm-4">
							<button id="buttonSearch" class="btn btn-primary" type="button">查询</button>
							<button id="clearSearch" onclick="clearSearch()" class="btn btn-primary" type="button">清空</button>
						</div>
					</div>
				</blockquote>
			</div>
		</div>
		<!-- 数据表格区域 -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							事件信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id=assignInfo></table>
					</div>
				</div>
			</div>
		</div>
		<!-- 数据区域结束 -->

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1020px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">事件信息</h4>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#tab-1">基本信息</a></li>
							<li class=""><a data-toggle="tab" href="#tab-2">流程图</a></li>

						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<form id="myForm1" class="form-horizontal" role="form">

									<fieldset id="basicInfo">
										<legend style="font-size: 15px"></legend>
										<div class="form-group form-group-sm " id="ownerSearch">
											<label class="col-sm-1 control-label">业主查询</label>
											<div class="col-sm-11">
												<div class="input-group">
													<input type="text" class="form-control" placeholder="房号/姓名/电话" id="searchInput" /> <span class="input-group-btn">
														<button class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="height: 30px;" type="button">
															<span class="caret"></span>
														</button>
														<ul class="dropdown-menu dropdown-menu-right" role="menu">
														</ul>
													</span>
												</div>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<div>
												<!-- 隐藏保存业主id及房间id -->
												<input class="form-control" type="hidden" id="ownerId" /> <input class="form-control" type="hidden" id="roomId" /> <input class="form-control" type="hidden" id="roomNo" />
											</div>
											<label class="col-sm-1 control-label">报事人</label>
											<div class="col-sm-3">
												<input id="rpt_name" type="text" class="form-control validate[required]" placeholder="报事人必填项" />
											</div>
											<label class="col-sm-1 control-label">地址</label>
											<div class="col-sm-7">
												<input type="text" class="form-control validate[required]" placeholder="地址必填" id="addres" />
											</div>
										</div>

										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">电话</label>
											<div class="col-sm-3">
												<input class="form-control validate[required]" placeholder="电话号码必填" id="in_call" type="text" />
											</div>
											<label class="col-sm-1 control-label">事件来源</label>
											<div class="col-sm-3">
												<select class="form-control" id="event_source">
													<option value="">---请选择---</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">事件类别</label>
											<div class="col-sm-3">
												<select class="form-control" id="event_type" onchange="changeEventType(this)">
													<option value="">---请选择---</option>
												</select>
											</div>
											<!--  <label class="col-sm-1 control-label">事件单号</label>-->
											<div class="col-sm-3">
												<input class="form-control" id="rpt_id" type="hidden" readonly="readonly" />
											</div>

										</div>

										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">关联录音</label>
											<div class="col-sm-3">
												<input class="form-control" id="addres_record" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">受理人</label>
											<div class="col-sm-3">
												<input class="form-control" id="createby" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">受理时间</label>
											<div class="col-sm-3">
												<input class="form-control" id="createTime" type="text" readonly="readonly" />
											</div>
										</div>
									</fieldset>
									<fieldset id="bxdetail" style="display:">
										<legend style="font-size: 15px">--报修明细--</legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">报修描述</label>
											<div class="col-sm-11">
												<textarea rows="3" id="mtn_detail" style="height: 60px" class="form-control validate[required]" placeholder="报修描述必填"></textarea>
												<div id="myspan">
													还可输入<span id="textCount">500</span>个<br />
												</div>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">紧急程度</label>
											<div class="col-sm-3">
												<select class="form-control" id="mtn_emergency" onchange="changeEmergency(this)">
													<option value="">--请选择--</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">事件状态</label>
											<div class="col-sm-3">
												<select class="form-control" id="dispatch_status" disabled="disabled">
													<option value="">--请选择--</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">完成时间</label>
											<div class="col-sm-3">
												<input class="form-control" id="dispatch_finish_time" type="text" readonly="readonly" />
											</div>
										</div>

										<div class="form-group form-group-sm " id="handleId">
											<label class="col-sm-1 control-label">处理人</label>
											<div class="col-sm-3">
												<div class="input-group">
													<input type="text" class="form-control validate[required]" id="dispatch_handle_id" style="display: none" /> <input type="text" class="form-control validate[required]" id="deptName" style="display: none"> <input type="text" class="form-control validate[required]" name='dispatch_handle_username' placeholder="请输入部门名称" id="dispatch_handle_username"> <span class="input-group-btn">
														<button class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="height: 30px;" type="button">
															<span class="caret"></span>
														</button>
														<ul class="dropdown-menu dropdown-menu-right" role="menu">
														</ul>
													</span>
												</div>
											</div>

										</div>
									</fieldset>
									<fieldset id="tsdetail" style="display: none">
										<legend style="font-size: 15px">--投诉建议--</legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">投诉内容</label>
											<div class="col-sm-11">
												<textarea class="form-control validate[required]" rows="3" id="comp_detail" style="height: 60px"></textarea>
											</div>
										</div>
										<!--    <div class="form-group form-group-sm " id="reply">
												  <label class="col-sm-1 control-label" >客户回复</label>
												  <div class="col-sm-11">
													 <textarea class="form-control" rows="3" id="comp_reply" style="height:60px"></textarea>
												  </div>							  
											   </div>-->
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">投诉级别</label>
											<div class="col-sm-3">
												<select class="form-control" id="comp_emergency">
													<option value="">--请选择--</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">事件状态</label>
											<div class="col-sm-3">
												<select class="form-control" id="comp_status" disabled="disabled">
													<option value="">--请选择--</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">完成时间</label>
											<div class="col-sm-3">
												<input class="form-control" id="finish_time" type="text" readonly="readonly" />
											</div>
										</div>
										<div class="form-group form-group-sm " id="handleIds">
											<label class="col-sm-1 control-label">处理人</label>
											<div class="col-sm-3">
												<div class="input-group">
													<input type="text" class="form-control validate[required]" id="comp_operator_id" style="display: none" /> <input type="text" class="form-control validate[required]" id="deptName" style="display: none"> <input type="text" class="form-control validate[required]" placeholder="" id="comp_operator_username"> <span class="input-group-btn">
														<button class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="height: 30px;" type="button">
															<span class="caret"></span>
														</button>
														<ul class="dropdown-menu dropdown-menu-right" role="menu">
														</ul>
													</span>
												</div>
											</div>
										</div>
										<div class="form-group form-group-sm " id="custmReply">
											<label class="col-sm-1 control-label">客服回复</label>
											<div class="col-sm-11">
												<textarea class="form-control" rows="3" id="comp_reply" style="height: 60px"></textarea>
											</div>
										</div>
									</fieldset>
									<fieldset>
										<legend style="font-size: 15px">--报修图片--</legend>
										<div class="form-group form-group-sm" id="uploadQuaDiv">
											<div class="col-sm-10">
												<input id="imgDiv" name="kartik-input-710[]" type="file" multiple class="file-loading" />
												<div id="kv-error-3" style="margin-top: 10px; display: none"></div>
											</div>
										</div>
										<input type="text" id="img_url" style="display: none">
										<div id="eventImgShow"></div>
									</fieldset>
									<div class="form-group form-group-sm "></div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" onclick="javascript:window.opener=null;window.load();" data-dismiss="modal">关闭</button>
										<button id="btAssigenAdd" onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
										<button id="btAssigenHandle" onclick="openHandleButton()" class="btn btn-primary " type="button">处理</button>
										<button id="btDisPatchHandle" onclick="openSendDisPatchButton()" class="btn btn-primary " type="button">派工</button>
										<button id="dispbtn8" onclick="openCancButton(8)" class="btn btn-primary " type="button">业主取消</button>
										<button id="dispbtn9" onclick="openCompHandleButton()" class="btn btn-primary " type="button">投诉处理</button>
									</div>
									<input class="form-control" id="rpt_kid" type="hidden" />
								</form>
							</div>
							<div id="tab-2" class="tab-pane">
								<div style="height: 400px">
									<iframe src="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=507504" frameborder="0" marginheight="0" marginwidth="0" height="100%" width="100%" id="grapIframe"></iframe>
								</div>
							</div>
						</div>

					</div>
					<!-- 弹出窗口区域，触发事件后弹出   结束 -->
				</div>
			</div>
		</div>
	</div>
</body>
</html>
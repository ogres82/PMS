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
<title>家政业主派工</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/houseWork/houseWorkVisit.js"></script>
<script type="text/javascript" src="${ContextPath}/Hplus/js/plugins/iCheck/icheck.min.js"></script>
<style>
.fixed-table-body {
	overflow: inherit;
}
</style>

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<i class="fa fa-search" style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 条件查询 -->
		<div class="row">
			<div class="col-sm-12">
				<blockquote class="layui-elem-quote" style="background-color: white">
					<div class="row">
						<div class="col-sm-2">
							<select class="form-control" name="type" id="businessFromSearch">
								<option disabled selected style='display: none;' value="">事件来源</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="CommNameSearch">
								<option disabled selected style='display: none;' value="">小区名称</option>
							</select>
						</div>
						<div class="col-sm-2">
							<input type="text" class="form-control layer-date" placeholder="受理日期" id="dateSearch" />
						</div>

						<div class="col-sm-2">
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
							家政回访信息<small></small>
						</h5>

					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id=evnetSendInfo></table>
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
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">事件单号</label>
											<div class="col-sm-3">
												<input class="form-control" id="event_no" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">事件来源</label>
											<div class="col-sm-3">
												<select class="form-control" id="event_source" disabled="disabled" >
													<option value="">---请选择---</option>
												</select>
											</div>

											<label class="col-sm-1 control-label">联系人</label>
											<div class="col-sm-3">
												<input id="rpt_name" type="text" class="form-control validate[required]" placeholder="报事人必填项" readonly="readonly" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<div>
												<!-- 隐藏保存业主id及房间id -->
												<input class="form-control" type="hidden" id="ownerId" type="text" /> <input class="form-control" type="hidden" id="roomId" type="text" />
											</div>

											<label class="col-sm-1 control-label">联系电话</label>
											<div class="col-sm-3">
												<input class="form-control validate[required,custom[phone]]" placeholder="电话号码必填" id="in_call" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">地址</label>
											<div class="col-sm-3">
												<div class="input-group">
													<input type="text" class="form-control validate[required]" placeholder="房号/姓名/电话" id="addres" readonly="readonly" /> <span class="input-group-btn">
														<button class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="height: 30px;" type="button">
															<span class="caret"></span>
														</button>
														<ul class="dropdown-menu dropdown-menu-right" role="menu">
														</ul>
													</span>
												</div>
											</div>
											<label class="col-sm-1 control-label">预约时间</label>
											<div class="col-sm-3">
												<input class="form-control"  id="pre_time" type="text" readonly="readonly" disabled="disabled" />
											</div>
										</div>

										<div class="form-group form-group-sm ">

											<label class="col-sm-1 control-label">事件内容</label>
											<div class="col-sm-11">
												<textarea rows="3" id="event_content" style="height: 60px" class="form-control validate[required]" placeholder="必填" readonly="readonly"></textarea>
											</div>

										</div>


										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">关联录音</label>
											<div class="col-sm-3">
												<input class="form-control" id="record_id" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">受理人</label>
											<div class="col-sm-3">
												<input class="form-control" id="verify_oper_id" type="hidden" readonly="readonly" /> <input class="form-control" id="verify_oper_name" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">受理时间</label>
											<div class="col-sm-3">
												<input class="form-control" id="id" type="hidden" readonly="readonly" /> <input class="form-control" id="accept_time" type="text" readonly="readonly" />
											</div>
										</div>
									</fieldset>


									<fieldset id="send" style="display:">
										<legend style="font-size: 15px">--派工--</legend>

										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">处理人</label>
											<div class="col-sm-3">
												<div class="input-group">
													<input type="text" class="form-control" id="comp_operator_id" style="display: none" disabled="disabled"> <input type="text" class="form-control" placeholder="请输入部门名称" id="comp_operator_username" disabled="disabled" readonly="readonly"> <span class="input-group-btn">
														<button class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="height: 30px;" type="button">
															<span class="caret"></span>
														</button>
														<ul class="dropdown-menu dropdown-menu-right" role="menu">
														</ul>
													</span>
												</div>
											</div>
											<label class="col-sm-1 control-label">派工时间</label>
											<div class="col-sm-3">
												<input class="form-control "  id="dispatch_finish_time" type="text" readonly="readonly" disabled="disabled" />
											</div>
											<label class="col-sm-1 control-label">事件状态</label>
											<div class="col-sm-3">
												<select class="form-control" id="dispatch_status" disabled="disabled">

												</select>
											</div>
										</div>

										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">到达时间</label>
											<div class="col-sm-3">
												<input class="form-control "  id="arrv_time" type="text" readonly="readonly" />
											</div>
										</div>

									</fieldset>
									<fieldset id="hfdetail">
										<legend style="font-size: 15px">--客户回访--</legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">回访记录</label>
											<div class="col-sm-11">
												<textarea class="form-control validate[required]" rows="3" id="visit_content" style="height: 60px"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">回访评级</label>
											<div class="col-sm-3">
												<select class="form-control validate[required]" id="visit_evaluate">
													<option></option>
												</select>
											</div>
											<label class="col-sm-1 control-label">回访录音</label>
											<div class="col-sm-3">
												<input class="form-control" id="visit_record" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">回访来源</label>
											<div class="col-sm-3">
												<select class="form-control validate[required]" id="visit_from">
													<option></option>
												</select>
											</div>
										</div>
									</fieldset>
									<div class="form-group form-group-sm "></div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
										<button id="btVisit" onclick="openHandleButton()" class="btn btn-primary " type="button">回访</button>
										<button id="btfile" onclick="openEventSendXdButton()" class="btn btn-primary " type="button">归档</button>
									</div>
									<input class="form-control" id="send_id" type="hidden" />
								</form>
							</div>
							<div id="tab-2" class="tab-pane">
								<div style="height: 400px">
									<iframe src="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=312501" frameborder="0" marginheight="0" marginwidth="0" height="100%" width="100%" id="grapIframe"></iframe>
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
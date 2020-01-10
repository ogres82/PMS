<!DOCTYPE html>
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

<html>
<head>
<title>回访信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/assignwork/VisitWorkList.js"></script>
<link href="${ContextPath}/Hplus/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet" />
<script type="text/javascript" src="${ContextPath}/Hplus/js/plugins/fancybox/jquery.fancybox.js"></script>

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">

		<i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
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
							<select class="form-control" name="type" id="eventTypeSearch">
								<option disabled selected style='display: none;' value="">事件类别</option>
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
							待回访信息<small></small>
						</h5>

					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id=visitInfo></table>
					</div>
				</div>
			</div>
		</div>
		<!-- 数据区域结束 -->

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModalVisit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
							<li class=""><a data-toggle="tab" href="#tab-3">相关图片</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<form id="myForm1" class="form-horizontal" role="form">

									<fieldset id="basicInfo">
										<legend style="font-size: 15px"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">事件单号</label>
											<div class="col-sm-3">
												<input class="form-control" id="rpt_id" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">事件来源</label>
											<div class="col-sm-3">
												<select class="form-control" id="event_source" disabled="disabled">
													<option value="">---请选择---</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">事件类别</label>
											<div class="col-sm-3">
												<select class="form-control" id="event_type" onchange="changeEventType(this)" disabled="disabled">
													<option value="">---请选择---</option>
												</select>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<div>
												<!-- 隐藏保存业主id及房间id -->
												<input class="form-control" type="hidden" id="ownerId" type="text" /> <input class="form-control" type="hidden" id="roomId" type="text" />
											</div>
											<label class="col-sm-1 control-label">报事人</label>
											<div class="col-sm-3">
												<input class="form-control" id="rpt_name" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">呼入电话</label>
											<div class="col-sm-3">
												<input class="form-control" id="in_call" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">地址</label>
											<div class="col-sm-3">
												<div class="input-group">
													<input type="text" class="form-control" placeholder="房号/姓名/电话" id="address" readonly="readonly" />
												</div>
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
												<textarea class="form-control" rows="3" style="height: 60px" id="mtn_detail" readonly="readonly"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">紧急程度</label>
											<div class="col-sm-3">
												<select class="form-control" id="mtn_emergency" disabled="disabled">
													<option value="">--请选择--</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">派工状态</label>
											<div class="col-sm-3">
												<select class="form-control" id="dispatch_status" disabled="disabled">
													<option value="">--请选择--</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">派工时间</label>
											<div class="col-sm-3">
												<input class="form-control" id="dispatch_time" type="text" disabled="disabled" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">维修类别</label>
											<div class="col-sm-3">
												<select class="form-control" id="mtn_type" onchange="changeMtnPriority()" disabled="disabled">
													<option value="">--请选择--</option>
												</select>
											</div>
											<div id="div_mtn_priority">
												<label class="col-sm-1 control-label">维修意见</label>
												<div class="col-sm-3">
													<select class="form-control" id="mtn_priority" disabled="disabled">
														<option value="">--请选择--</option>
													</select>
												</div>
											</div>
											<label class="col-sm-1 control-label">处理人</label>
											<div class="col-sm-3">
												<div class="input-group">
													<input type="text" class="form-control" id="dispatch_handle_username" style="display: none"> <input type="text" class="form-control" placeholder="请输入部门名称" id="dispatch_handle_id" disabled="disabled"> <span class="input-group-btn">
														<button class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="height: 30px;" type="button">
															<span class="caret"></span>
														</button>
														<ul class="dropdown-menu dropdown-menu-right" role="menu">
														</ul>
													</span>
												</div>
											</div>

										</div>

										<div class="form-group form-group-sm " id="tool">
											<label class="col-sm-1 control-label">维修用料</label>
											<div class="col-sm-7">
												<div class="input-group">
													<input type="text" class="form-control" id="dispatch_tools" disabled="disabled"> <span class="input-group-btn">
														<button class="btn btn-default btn-sm" type="button">
															<i class="fa fa-search"></i>
														</button>
													</span>
												</div>
											</div>
										</div>

										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">到达时间</label>
											<div class="col-sm-3">
												<input class="form-control laydate-icon layer-date" id="dispatch_arrive_time" type="text"  disabled="disabled" />
											</div>

											<label class="col-sm-1 control-label">维修费用</label>
											<div class="col-sm-3">
												<input class="form-control" id="dispatch_expense" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">完成时间</label>
											<div class="col-sm-3">
												<input class="form-control" id="dispatch_finish_time" type="text" readonly="readonly" />
											</div>
										</div>
										<div class="form-group form-group-sm " id="reject">
											<label class="col-sm-1 control-label">拒绝原因</label>
											<div class="col-sm-11">
												<textarea class="form-control" rows="3" style="height: 60px" id="rejectReason" readonly="readonly"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm " id="dis_result">
											<label class="col-sm-1 control-label">材料费</label>
											<div class="col-sm-11">
												<textarea class="form-control" style="height: 60px" rows="3" id="dispatch_result" disabled="disabled" hidden="hidden"></textarea>
											</div>
											<label class="col-sm-1 control-label">完成时间</label>
											<div class="col-sm-3">
												<input class="form-control" id="dispatch_finish_time" type="text" readonly="readonly" />
											</div>
										</div>
									</fieldset>
									<fieldset id="tsdetail" style="display: none">
										<legend style="font-size: 15px">--投诉建议--</legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">投诉情况</label>
											<div class="col-sm-11">
												<textarea class="form-control" rows="3" style="height: 60px" id="comp_detail" disabled="disabled"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">投诉级别</label>
											<div class="col-sm-3">
												<select class="form-control" id="comp_emergency" disabled="disabled">
													<option>--请选择--</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">派工状态</label>
											<div class="col-sm-3">
												<select class="form-control" id="comp_status" disabled="disabled">
													<option>--请选择--</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">处理人</label>
											<div class="col-sm-3">
												<div class="input-group">
													<input type="text" class="form-control" id="comp_operator_id" disabled="disabled"> <span class="input-group-btn">
														<button class="btn btn-default btn-sm" type="button">
															<i class="fa fa-search"></i>
														</button>
													</span>
												</div>
											</div>
										</div>
										<!-- <div class="form-group form-group-sm ">
														  <label class="col-sm-1 control-label">处理过程</label>
														  <div class="col-sm-11">
																<input class="form-control" id="comp_process" type="text" style="height: 60px" disabled="disabled"/>
														  </div>
												   </div>	 -->
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">处理结果</label>
											<div class="col-sm-11">
												<textarea class="form-control" rows="3" id="comp_result" style="height: 60px" disabled="disabled"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">客服回复</label>
											<div class="col-sm-11">
												<textarea class="form-control" rows="3" id="comp_reply" style="height: 60px" disabled="disabled"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<!-- <label class="col-sm-1 control-label">是否解决</label>
													  <div class="col-sm-3">
															  <select class="form-control" id="comp_solve" disabled="disabled"> 
																  <option>--请选择--</option>
															  </select>  
													  </div> -->
											<!-- 
													  <label class="col-sm-1 control-label">满意度</label>
													  <div class="col-sm-3">
															  <select class="form-control" id="comp_degree" disabled="disabled"> 
																  <option>--请选择--</option>
															  </select>  
													  </div> -->
											<label class="col-sm-1 control-label">完成时间</label>
											<div class="col-sm-3">
												<input class="form-control" id="comp_finish_time" type="text" readonly="readonly" />
											</div>
										</div>

									</fieldset>
									<fieldset id="hfdetail">
										<legend style="font-size: 15px">--客户回访--</legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">回访记录</label>
											<div class="col-sm-11">
												<textarea class="form-control validate[required]" rows="3" id="dispatch_visit_record" style="height: 60px"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">回访评级</label>
											<div class="col-sm-3">
												<select class="form-control validate[required]" id="dispatch_visit_lev">
													<option></option>
												</select>
											</div>
											<label class="col-sm-1 control-label">回访来源</label>
											<div class="col-sm-3">
												<select class="form-control validate[required]" id="event_source1">
													<option></option>
												</select>
											</div>
											<label class="col-sm-1 control-label">回访录音</label>
											<div class="col-sm-3">
												<input class="form-control" id="dispatch_visit_recording" type="text" readonly="readonly" />
											</div>


										</div>
									</fieldset>
									<div class="form-group form-group-sm "></div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
										<button id="btVisitAdd" onclick="visitSaveButton(1)" class="btn btn-primary " type="button">回访</button>
										<!--  	<button id="btVisitLev" onclick="visitSaveButton(2)" class="btn btn-primary " type="button" >归档</button>-->
									</div>
									<input class="form-control" id="rpt_kid" type="hidden" />
								</form>
							</div>
							<div id="tab-2" class="tab-pane">
								<div style="height: 400px">
									<iframe src="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=5001" frameborder="0" marginheight="0" marginwidth="0" height="100%" width="100%" id="grapIframe"></iframe>
								</div>
							</div>
							<div id="tab-3" class="tab-pane">
								<div>
									<fieldset>
										<legend style="font-size: 15px">--报修图片--</legend>
										<div id="eventImgShow"></div>
									</fieldset>
									<fieldset>
										<legend style="font-size: 15px">--完成图片--</legend>
										<div id="finishImgShow"></div>
									</fieldset>
								</div>
							</div>
						</div>
					</div>
					<!-- 弹出窗口区域，触发事件后弹出   结束 -->
					<!--  end  content -->
				</div>
			</div>
		</div>
	</div>
</body>
</html>
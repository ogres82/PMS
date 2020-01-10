<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>事件信息</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript"
	src="${ContextPath}/secManage/tsecurityevent.js"></script>

<script type="text/javascript">
	loginUserName = "${loginUser.username}";
	loginUserCname = "${loginUser.cname}";
</script>
<style>
#moreSearch:hover {
	cursor: pointer;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">

		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div
			style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;"
					id="topToolbar">
					<!-- <button id="btn1" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
		   			 <button id="btn2" onclick="openButtonWindow(2)" class="btn btn-primary " disabled type="button"><i class="fa fa-edit"></i> 编辑</button>
		   			 <button id="btn3" onclick="openButtonWindow(3)" class="btn btn-primary " disabled type="button"><i class="fa fa-eye"></i> 查看</button>
		    		 <button id="btn4" onclick="openButtonWindow(4)" class="btn btn-primary " disabled type="button"><i class="fa fa-trash-o"></i> 删除</button>
		   			 <button id="btn5" onclick="changeView(1)" class="btn btn-primary " type="button" style="display:none"><i class="fa fa-bar-chart"></i> 日历展示</button>
		   			 <button id="btn6" onclick="changeView(2)" class="btn btn-primary " type="button"><i class="fa fa-list-alt"></i> 报表展示</button> -->
				</div>
				<div class="col-md-6" style="margin-top: 10px; float: left">
					<p
						style="font-size: 14px; margin-top: 10px; margin-right: 1%; width: 90px; float: right"
						id="moreSearch">
						更多查询&nbsp;<i class="fa fa-angle-down"></i>
					</p>
				</div>
			</div>
		</div>
		<i class="fa fa-search"
			style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->


		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							事件信息<small></small>
						</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="dropdown-toggle" data-toggle="dropdown"
								href="table_data_tables.html#"> <i class="fa fa-wrench"></i>
							</a> <a class="close-link"> <i class="fa fa-times"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="tableArea" style="width: 500px; display: inline"
							class="animated">
							<div id="toolbar"></div>
							<table class="table-no-bordered" id="empInfo"></table>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							事件信息<small></small>
						</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="dropdown-toggle" data-toggle="dropdown"
								href="table_data_tables.html#"> <i class="fa fa-wrench"></i>
							</a> <a class="close-link"> <i class="fa fa-times"></i>
							</a>
						</div>
					</div>
					<div id="chartsArea" class="animated"
						style="width: 500px; display: inline; height: 100%">
						<div id="calendar" style="height: 100%"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1020px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalTitle" style="color: white">安保事件信息</h4>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<form id="myForm1" class="form-horizontal" role="form">

									<fieldset id="basicInfo">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">事件编号</label>
											<div class="col-sm-3">
												<input type="hidden" id="eventId" name="eventId"> <input
													class="form-control validate[required]" id="eventCode"
													readonly="readonly" type="text" placeholder="必填项" />
											</div>
											<label class="col-sm-1 control-label">事件标题</label>
											<div class="col-sm-3">
												<input class="form-control validate[required]"
													id="eventTitle" type="text" placeholder="必填项" />
											</div>
											<label class="col-sm-1 control-label">事件类别</label>
											<div class="col-sm-3">
												<select class="form-control validate[required]"
													id="eventType">
													<option value="">---请选择---</option>
													<option value="0">消防</option>
													<option value="1">交通</option>
													<option value="2">治安</option>
												</select>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">事件发生时间</label>
											<div class="col-sm-3">
												<input
													class="form-control laydate-icon layer-date validate[required]"
													type="text" id="eventHappenTime" name="eventHappenTime">
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">事件描述</label>
											<div class="col-sm-11">
												<textarea class="form-control validate[required] " rows="6"
													id="eventDetailText" style="height: 60px"></textarea>
												<input type="hidden" id="eventDetail" name="eventDetail">
											</div>
											<!-- <div class="col-sm-3">
													<input class="form-control validate[required,custom[chinaId]]" id="eventDetail" type="text" placeholder="必填项"/>
											  </div> -->
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">处理结果</label>
											<div class="col-sm-11">
												<textarea class="form-control validate[required]" rows="6"
													id="eventResultText" style="height: 60px"></textarea>
												<input type="hidden" id="eventResult" name="eventResult">
											</div>
											<!-- <div class="col-sm-3">
													<input class="form-control validate[required]" id="eventResult" type="text"/>
											  </div> -->
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">备注</label>
											<div class="col-sm-11">
												<textarea class="form-control" rows="6" id="eventRemarkText"
													style="height: 60px"></textarea>
												<input type="hidden" id="eventRemark" name="eventRemark">
											</div>
											<!-- <div class="col-sm-3">
													<input class="form-control validate[required]" id="eventRemark" type="text"/>
											  </div> -->
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">是否突发事件</label>
											<div class="col-sm-3">
												<select class="form-control validate[required]" id="eventBurst">
													<option value="">---请选择---</option>
													<option value="0">否</option>
													<option value="1">是</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">处理负责人</label>
											<div class="col-sm-3">
												<input class="form-control" id="eventChargerId" type="text"
													readOnly="readonly" />
											</div>
											<label class="col-sm-1 control-label">记录时间</label>
											<div class="col-sm-3">
												<input class="form-control" id="eventRecTime" type="text"
													readOnly="readonly" />
											</div>
										</div>
									</fieldset>

									<div class="form-group form-group-sm "></div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">关闭</button>
										<button id="btEmpAdd" onclick="openSaveButton()"
											class="btn btn-primary " type="button">保存</button>
									</div>
									<input class="form-control" id="empId" type="hidden" />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	</div>

	<script>
		laydate.render({
			elem : '#eventHappenTime',
			istime : true,
			event : 'click',
			festival : true, //显示节日
			type : 'datetime',
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm1').validationEngine('hide');
			}
		});
	</script>
</body>

</html>
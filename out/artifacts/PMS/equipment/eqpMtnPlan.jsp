<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设备保养计划</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript"
	src="${ContextPath}/equipment/eqpMtnPlan.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">

		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div class="row" style="">
			<div class="col-md-6" style="float: left; margin-top: 10px;">
				<button id="btn_add" onclick="openButtonWindow(1)"
					class="btn btn-primary " type="button">
					<i class="fa fa-plus"></i>&nbsp;制定计划
				</button>
				<button id="btn_edit" onclick="openButtonWindow(2)"
					class="btn btn-primary " type="button">
					<i class="fa fa-edit"></i> 编辑
				</button>
				<button id="btn_del" onclick="openButtonWindow(3)"
					class="btn btn-primary " type="button">
					<i class="fa fa-trash-o"></i> 删除
				</button>
			</div>
			<div class="col-md-6" style="margin-top: 10px; float: left">
				<p style="font-size: 14px; margin-top: 10px; float: right"
					id="moreSearch">
					更多查询&nbsp;<i class="fa fa-angle-down"></i>
				</p>
				<div class="form-group" style="" id="search_top">
					<span style="float: right; width: 100px;" class="input-group-btn">
						<button type="button" style="border-radius: 0 5px 5px 0;"
							class="btn btn-primary">
							<i class="fa fa-search"></i>查询
						</button>
					</span> <input class="form-control"
						style="float: right; width: 140px; margin-left: 5px;" type="text"
						style="" placeholder="房间号">
				</div>
			</div>
		</div>

		<!-- 更多查询区域  -->
		<div class="row ibox float-e-margins"
			style="display: none; text-align: center; margin-left: auto; margin-right: auto;"
			id="more_search">
			<div class="ibox-content col-sm-12 b-r">
				<form class="form-horizontal">

					<div class="form-group" style="margin-top: 20px;">
						<label class="col-sm-1 control-label">业主姓名</label>

						<div class="col-sm-3">
							<input type="text" class="form-control">
						</div>

						<label class="col-sm-1 control-label">收费类型</label>

						<div class="col-sm-3">
							<input type="text" class="form-control">
						</div>
						<label class="col-sm-1 control-label">房间编号</label>

						<div class="col-sm-3">
							<input type="text" class="form-control">
						</div>
					</div>
					<div class="col-sm-12">
						<button type="button" style="float: right;"
							class="btn btn-primary">
							<i class="fa fa-search"></i>查询
						</button>
					</div>
				</form>
			</div>
		</div>

		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							设备保养计划<small></small>
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
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="eqpMtnPlan"></table>

					</div>

				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 900px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">保养计划</h4>
					</div>
					<div class="modal-body">
						<form id="myForm1" class="form-horizontal" role="form">
							<fieldset id="eqpFiles">
								<legend style="font-size: 15px; border: 0;">设备信息</legend>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">设备名称</label>
									<div class="col-sm-4">
										<input class="form-control" id="eqpName" type="text" />
									</div>
									<label class="col-sm-2 control-label">设备状态</label>
									<div class="col-sm-4">
										<input class="form-control" id="eqpStatus" type="text" />
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">安装地点</label>
									<div class="col-sm-4">
										<input class="form-control" id="eqpAddress" type="text" />
									</div>
									<label class="col-sm-2 control-label">规格型号</label>
									<div class="col-sm-4">
										<input class="form-control" id="eqpModel" type="text" />
									</div>
								</div>
							</fieldset>
							<fieldset id="planInfo">
								<legend style="font-size: 15px; border: 0;">计划信息</legend>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">计划编码</label>
									<div class="col-sm-4">
										<input class="form-control" id="planCode" type="text" />
									</div>
									<label class="col-sm-2 control-label">计划制定日期</label>
									<div class="col-sm-4">
										<input class="form-control" id="planMakeDate" type="text" />
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">设备名称</label>
									<div class="col-sm-4">
										<input class="form-control" id="eqpName2" type="text" />
									</div>
									<label class="col-sm-2 control-label">制定人</label>
									<div class="col-sm-4">
										<input class="form-control" id="planMaker" type="text" />
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">计划开始日期</label>
									<div class="col-sm-4">
										<input class="form-control" id="planStartDate" type="text" />
									</div>
									<label class="col-sm-2 control-label">计划结束日期</label>
									<div class="col-sm-4">
										<input class="form-control" id="planEndDate" type="text" />
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">保养项目</label>
									<div class="col-sm-4">
										<input class="form-control" id="planMtnContent" type="text" />
									</div>
									<label class="col-sm-2 control-label">保养级别</label>
									<div class="col-sm-4">
										<input class="form-control" id="planMtnLevel" type="text" />
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">负责人</label>
									<div class="col-sm-4">
										<input class="form-control" id="planCharger" type="text" />
									</div>
								</div>
							</fieldset>
						</form>
					</div>

				</div>
				<div class="form-group form-group-sm "></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="btEmpAdd" onclick="openSaveButton()"
						class="btn btn-primary " type="button">保存</button>
				</div>
				<input class="form-control" id="empId" type="hidden" />
			</div>
			<!-- 弹出窗口区域，触发事件后弹出   结束 -->

		</div>
	</div>
</body>

</html>
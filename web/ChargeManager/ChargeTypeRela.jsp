<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>业主收费项信息</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/ChargeManager/ChargeTypeRela.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;" id="topToolbar"></div>
			</div>
		</div>
		<i class="fa fa-search" style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->

		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							业主收费项信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="chargeTypeRela"></table>

					</div>

				</div>
			</div>
		</div>




		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 800px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">收费项关联信息</h4>
					</div>
					<div class="modal-body">
						<form id="myForm" class="form-horizontal" action="${ContextPath}/msgandnotice/list.app?method=save" method="post">

							<div class="form-group form-group-sm " id="ownerSearch">
								<label class="col-sm-2 control-label">业主查询</label>
								<div class="col-sm-10">
									<div class="input-group">
										<input type="text" class="form-control" placeholder="房号/姓名/电话/楼宇" id="searchInput" /> <span class="input-group-btn">
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
								<label class="col-sm-2 control-label">房间编号</label>
								<div class="col-sm-4">
									<input class="form-control validate[required]" name="room_no" id="room_no" type="text" readonly="true" />
								</div>
								<label class="col-sm-2 control-label">业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主</label>
								<div class="col-sm-4">
									<input class="form-control validate[required]" name="owner_name" id="owner_name" type="text" readonly="true" />
								</div>
								<input name="room_id" id="room_id" type="text" style="display: none" /> <input name="owner_id" id="owner_id" type="text" style="display: none" />
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">项目名称</label>
								<div class="col-sm-4">
									<select class="form-control validate[required]" id="charge_type_name">
										<option value="">---请选择---</option>
									</select>
								</div>
								<label class="col-sm-2 control-label">项目编号</label>
								<div class="col-sm-4">
									<input class="form-control validate[required,custom[number],minSize[4],maxSize[4]]" name="charge_type_no" id="charge_type_no" type="text" readonly="true" />
								</div>
								<input name="charge_type_id" id="charge_type_id" type="text" style="display: none" /> <input name="type_flag" id="type_flag" type="text" style="display: none" />
							</div>
							<div class="form-group form-group-sm "></div>
							<div class="modal-footer">
								<button type="button" id="button_close" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button type="button" id="button_save" onclick="save()" class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!-- 批量新增弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myBatchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 800px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="batchModalLabel" style="color: white">批量新增</h4>
					</div>
					<div class="modal-body">
						<form id="myBatchForm" class="form-horizontal" action="${ContextPath}/msgandnotice/list.app?method=save" method="post">
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">项目名称</label>
								<div class="col-sm-4">
									<select class="form-control validate[required]" id="batch_charge_type_name">
										<option value="">---请选择---</option>
									</select>
								</div>
								<label class="col-sm-2 control-label">项目编号</label>
								<div class="col-sm-4">
									<input class="form-control validate[required,custom[number],minSize[4],maxSize[4]]" name="batch_charge_type_no" id="batch_charge_type_no" type="text" readonly="true" />
								</div>
								<input name="charge_type_id" id="batch_charge_type_id" type="text" style="display: none" /> <input name="batch_type_flag" id="batch_type_flag" type="text" style="display: none" />
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">单位查询</label>
								<div class="col-sm-4">
									<div class="input-group">
										<input class="form-control validate[required]" name="batch_belong_unit_input" id="batch_belong_unit_input" type="text" placeholder="必填项  可查询（所属单位）">
										<div class="input-group-btn">
											<button type="button" class="btn btn-white dropdown-toggle" style="height: 30px;" data-toggle="dropdown">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu dropdown-menu-right" role="menu">
											</ul>
										</div>
									</div>
								</div>
								<label class="col-sm-2 control-label">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位</label>
								<div class="col-sm-4">
									<input class="form-control validate[required]" name="batch_belong_unit" id="batch_belong_unit" type="text" readonly="true" />
								</div>
								<input name="batch_belong_unit_id" id="batch_belong_unit_id" type="text" style="display: none" />
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">楼宇查询</label>
								<div class="col-sm-4">
									<div class="input-group">
										<input class="form-control validate[required]" name="batch_storied_build_input" id="batch_storied_build_input" type="text" placeholder="必填项  可查询（楼宇）">
										<div class="input-group-btn">
											<button type="button" class="btn btn-white dropdown-toggle" style="height: 30px;" data-toggle="dropdown">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu dropdown-menu-right" role="menu">
											</ul>
										</div>
									</div>
								</div>
								<label class="col-sm-2 control-label">楼宇名称</label>
								<div class="col-sm-4">
									<input class="form-control validate[required,custom[number],minSize[4],maxSize[4]]" name="batch_storied_build_name" id="batch_storied_build_name" type="text" disabled="disabled" />
								</div>
								<input name="batch_storied_build_id" id="batch_storied_build_id" type="text" style="display: none" />
							</div>
							<div class="form-group form-group-sm "></div>
							<div class="modal-footer">
								<button type="button" id="batch_button_close" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button type="button" id="batch_button_save" onclick="batchSave()" class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>


		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myCheckModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 800px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">详情</h4>
					</div>
					<div class="modal-body">
						<form id="myCheckForm" class="form-horizontal">
							<div class="form-group form-group-sm " id="ownerSearch">
								<label class="col-sm-2 control-label">所属单位</label>
								<div class="col-sm-4">
									<input class="form-control" name="belong_unit_check" id="belong_unit_check" type="text" />
								</div>
								<label class="col-sm-2 control-label">房&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</label>
								<div class="col-sm-4">
									<input class="form-control" name="room_no_check" id="room_no_check" type="text" />
								</div>
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主</label>
								<div class="col-sm-4">
									<input class="form-control" name="owner_name_check" id="owner_name_check" type="text" />
								</div>
								<label class="col-sm-2 control-label">计费方式</label>
								<div class="col-sm-4">
									<input class="form-control" name="drop_charge_mode_check" id="drop_charge_mode_check" type="text" />
								</div>
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">项目编号</label>
								<div class="col-sm-4">
									<input class="form-control" name="charge_type_no_check" id="charge_type_no_check" type="text" />
								</div>
								<label class="col-sm-2 control-label">项目名称</label>
								<div class="col-sm-4">
									<input class="form-control" name="charge_type_name_check" id="charge_type_name_check" type="text" />
								</div>
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">收费方式</label>
								<div class="col-sm-4">
									<input class="form-control" name="drop_charge_way_check" id="drop_charge_way_check" type="text" />
								</div>
								<label class="col-sm-2 control-label">收费类型</label>
								<div class="col-sm-4">
									<input class="form-control" name="drop_charge_type_check" id="drop_charge_type_check" type="text" />
								</div>
							</div>
							<div class="form-group form-group-sm "></div>
							<div class="modal-footer">
								<button type="button" id="button_close" class="btn btn-default" data-dismiss="modal">关闭</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
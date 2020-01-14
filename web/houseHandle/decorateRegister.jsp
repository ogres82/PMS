<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<title>装修登记</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript"
	src="${ContextPath}/houseHandle/decorateRegister.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div
			style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;"
					id="topToolbar"></div>
			</div>
		</div>
		<i class="fa fa-search"
			style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div  -->
		<div class="" style="width: 100%; height: 45px;"></div>

		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							装修信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="housePropertyInfo"></table>

					</div>

				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1000px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalTitle" style="color: white">登记信息</h4>
					</div>
					<div class="modal-body">
						<div id="tab-1" class="tab-pane active">
							<form id="myForm1" class="form-horizontal" role="form">

								<fieldset id="basicInfo">
									<legend style="font-size: 15px; border: 0;"></legend>

									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">房间</label>
										<div class="col-sm-10">
											<div class="input-group">
												<input type="text" class="form-control validate[required]"
													id="roomNo_d" placeholder="房间号/楼宇/小区/楼盘">
												<div class="input-group-btn">
													<button type="button"
														class="btn btn-white btn-sm dropdown-toggle"
														data-toggle="dropdown">
														<span class="caret"></span>
													</button>
													<ul class="dropdown-menu dropdown-menu-right" role="menu">
													</ul>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">客户</label>
										<div class="col-sm-4">
											<input class="form-control" id="ownerName" type="text"
												disabled="disabled" />
										</div>
										<label class="col-sm-2 control-label">房间面积（㎡）</label>
										<div class="col-sm-4">
											<input class="form-control" id="buildArea" type="text"
												disabled="disabled" />
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">装修开始时间</label>
										<div class="col-sm-4">
											<input
												class="form-control laydate-icon layer-date validate[required]"
												id="decorateStartDate" type="text" placeholder="必填项" />
										</div>
										<label class="col-sm-2 control-label">拟结束时间</label>
										<div class="col-sm-4">
											<input
												class="form-control laydate-icon layer-date validate[required]"
												id="decoratePlanDate" type="text" placeholder="必填项" />
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">负责人</label>
										<div class="col-sm-4">
											<input class="form-control validate[required]" id="operName"
												type="text" placeholder="必填项" />
										</div>
										<label class="col-sm-2 control-label">负责人电话</label>
										<div class="col-sm-4">
											<input class="form-control validate[required]" id="operPhone"
												type="text" placeholder="必填项" />
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">装修内容</label>
										<div class="col-sm-10">
											<textarea class="form-control" id="decorateContent"
												style="height: 80px"></textarea>
										</div>
									</div>

									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">备注</label>
										<div class="col-sm-10">

											<textarea class="form-control" id="remark"
												style="height: 80px"></textarea>
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">收费标准</label>
										<div class="col-sm-10">
											<button id="d_btn1" onclick="openChargeWindow(1)"
												class="btn btn-primary " type="button">
												<i class="fa fa-plus"></i>&nbsp;装修申请
											</button>

										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<table class="table-no-bordered" id="chargeStandardInfo"></table>
										</div>
									</div>
								</fieldset>

							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button id="btDecoApplyClose" type="button"
							class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btDecoApplyAdd" onclick="openSaveButton()"
							class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="roomId" type="hidden" /> <input
						class="form-control" id="roomNo" type="hidden" /> <input
						class="form-control" id="ownerId" type="hidden" />
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->


		<!-- 弹出窗口区域，触发事件后弹出  -->
		<!-- 收费项新增表单 -->
		<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 500px; margin-top: 100px;">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">收费信息</h4>
					</div>
					<div class="modal-body">

						<div id="tab-1" class="tab-pane active">
							<form id="myForm1" class="form-horizontal" role="form">

								<fieldset id="basicInfo">
									<legend style="font-size: 15px; border: 0;"></legend>

									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">收费项</label>
										<div class="col-sm-10">
											<select class="form-control" id="charge_type_id">
												<option value="">---请选择---</option>
											</select>
										</div>
									</div>
								</fieldset>

							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btRetainerAdd" onclick="retainerSaveButton()"
							class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="charge_type_no" type="hidden" /> <input
						class="form-control" id="charge_price" type="hidden" /> <input
						class="form-control" id="charge_type_no" type="hidden" />
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->


		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal3" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1000px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">装修完成</h4>
					</div>
					<div class="modal-body">
						<div id="tab-1" class="tab-pane active">
							<form id="myForm2" class="form-horizontal" role="form">

								<fieldset id="basicInfo">
									<legend style="font-size: 15px; border: 0;"></legend>

									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">房间</label>
										<div class="col-sm-10">
											<input class="form-control" id="roomNo_f" type="text"
												disabled="disabled" />
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">装修开始时间</label>
										<div class="col-sm-4">
											<input class="form-control laydate-icon layer-date"
												id="decorateStartDate_f" disabled="disabled" type="text" />
										</div>
										<label class="col-sm-2 control-label">装修结束时间</label>
										<div class="col-sm-4">
											<input
												class="form-control laydate-icon layer-date validate[required]"
												id="decorateEndDate" type="text" placeholder="必填项" />
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">已缴费用明细</label>
										<div class="col-sm-10">
											<table class="table-no-bordered" id="paymentDetailsInfo"></table>
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">验收说明</label>
										<div class="col-sm-10">
											<input class="form-control" id="decorateInstructions"
												type="text" />
										</div>
									</div>
								</fieldset>

							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btHousePropertyAdd"
							onclick="openDecorateFinishButton()" class="btn btn-primary "
							type="button">保存</button>
					</div>
					<input class="form-control" id="roomId" type="hidden" />
					<input class="form-control" id="ownerId_f" type="hidden" />
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->

	</div>
	<script>
		laydate.render({
			elem : '#decorateStartDate',
			//format: 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm1').validationEngine('validate');
			}
		});
		laydate.render({
			elem : '#decoratePlanDate',
			//format: 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm1').validationEngine('validate');
				var start = $("#decorateStartDate").val();
				var end = $("#decoratePlanDate").val();

				if (start > end && end != "") {
					layer.msg('结束时间不能小于开始时间！', {
						time : 2000
					});
					$("#decoratePlanDate").val("");
				}
			}
		});

		laydate.render({
			elem : '#decorateEndDate',
			//format: 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm2').validationEngine('validate');
				var start = $("#decorateStartDate_f").val();
				var end = $("#decorateEndDate").val();

				if (start > end && end != "") {
					layer.msg('结束时间不能小于开始时间！', {
						time : 2000
					});
					$("#decorateEndDate").val("");
				}
			}
		});
	</script>
</body>

</html>
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
<title>业主收房登记</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript"
	src="${ContextPath}/houseHandle/receiveHouse.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript">
	loginUserName = "${loginUser.username}";
	loginUserCname = "${loginUser.cname}";
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div
			style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;"
					id="topToolbar"></div>
			</div>
		</div>
		<i class="fa fa-search"
			style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->

		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							接房信息<small></small>
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
													id="roomNo_d" placeholder="房间号/楼宇/小区/楼盘"/>
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
										<label class="col-sm-2 control-label">业主姓名</label>
										<div class="col-sm-4">
											<input class="form-control" id="ownerName" type="text"
												disabled="disabled" />
										</div>
										<label class="col-sm-2 control-label">建筑面积（㎡）</label>
										<div class="col-sm-4">
											<input class="form-control" id="buildArea" type="text"
												disabled="disabled" />
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">起征时间</label>
										<div class="col-sm-4">
											<input
												class="form-control laydate-icon layer-date validate[required]"
												id="chargeDate" type="text" placeholder="必填项" />
										</div>
										<label class="col-sm-2 control-label">实际收房日期</label>
										<div class="col-sm-4">
											<input
												class="form-control laydate-icon layer-date validate[required]"
												id="reciveRoomDate" type="text" placeholder="必填项" />
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">办理人</label>
										<div class="col-sm-4">
											<input class="form-control" id="receiver" type="text"
												disabled="disabled" />
										</div>
									</div>

									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">关联收费项</label>
										<div class="col-sm-10">
											<button id="retainerSave" onclick="openRetainerWindow(1)"
												class="btn btn-primary " type="button">
												<i class="fa fa-plus"></i>&nbsp;增加
											</button>
											<button id="retainerDel" onclick="openRetainerWindow(2)"
												class="btn btn-primary " type="button">
												<i class="fa fa-edit"></i> 删除
											</button>
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<table class="table-no-bordered" id="retainerInfo"></table>
										</div>
									</div>
								</fieldset>

							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btReciveHouseAdd" onclick="openSaveButton()"
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
	</div>
	<script>
		laydate.render({
			elem : '#reciveRoomDate',
			//format: 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm1').validationEngine('validate');
			}
		});
		laydate.render({
			elem : '#chargeDate',
			//format: 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm1').validationEngine('validate');
			}
		});
	</script>
</body>

</html>
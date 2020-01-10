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
<title>业主信息</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript" src="${ContextPath}/basicInfo/Owner.js"></script>
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
		<div class="row">
			<div class="col-sm-12">
				<blockquote class="layui-elem-quote" style="background-color: white">
					<div class="row">
						<div class="col-sm-2">
							<select class="form-control" name="type" id="regStateSearch">
								<option disabled selected style='display: none;' value="">注册状态</option>
								<option value="0">未注册</option>
								<option value="1">已注册</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="ownerIdentitySearch">
								<option disabled selected style='display: none;' value="">业主身份</option>
							</select>
						</div>
						<div class="col-sm-8" style="float: right">
							<button id="buttonSearch" class="btn btn-primary" type="button">查询</button>
							<button id="clearSearch" onclick="clearSearch()" class="btn btn-primary" type="button">清空</button>
						</div>
					</div>
				</blockquote>
			</div>
		</div>

		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							业主信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="ownerInfo"></table>

					</div>

				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1020px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">业主信息</h4>
					</div>
					<div class="modal-body">
						<form id="myForm1" class="form-horizontal" role="form">

							<fieldset id="basicInfo">
								<legend style="font-size: 15px; border: 0;"></legend>

								<div class="form-group form-group-sm ">
									<label class="col-sm-1 control-label">业主姓名</label>
									<div class="col-sm-3">
										<input class="form-control validate[required]" id="ownerName" type="text" placeholder="必填项" />
									</div>
									<label class="col-sm-1 control-label">手机号</label>
									<div class="col-sm-3">
										<input class="form-control validate[required]" id="phone" type="text" placeholder="必填项" />
									</div>
									<label class="col-sm-1 control-label">联系电话</label>
									<div class="col-sm-3">
										<input class="form-control" id="telPhone" type="text" />
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-1 control-label">身份证号</label>
									<div class="col-sm-3">
										<input class="form-control validate[required]" id="cardId" onblur="getBrith()" type="text" placeholder="必填项" />
									</div>
									<label class="col-sm-1 control-label">出生日期</label>
									<div class="col-sm-3">
										<input class="form-control laydate-icon layer-date validate[required]" id="birthDate" type="text" />
									</div>
									<label class="col-sm-1 control-label">性别</label>
									<div class="col-sm-3">
										<select class="form-control" id="sex">
											<option value="男">男</option>
											<option value="女">女</option>
										</select>
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-1 control-label">紧急联系人</label>
									<div class="col-sm-3">
										<input class="form-control" id="emergencyContact" type="text" />
									</div>
									<label class="col-sm-1 control-label">紧急电话</label>
									<div class="col-sm-3">
										<input class="form-control validate[custom[mobilephone]]" id="emergencyContactPhone" type="text" />
									</div>
									<label class="col-sm-1 control-label">业主类型</label>
									<div class="col-sm-3">
										<select class="form-control validate[required]" id="ownerType">
											<option value="">---请选择---</option>
										</select>
									</div>

								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-1 control-label">车牌号</label>
									<div class="col-sm-3">
										<input class="form-control" id="carId" type="text" />
									</div>
									<label class="col-sm-1 control-label">身份类别</label>
									<div class="col-sm-3">
										<select class="form-control" disabled id="ownerIdentity">
											<option value="">---请选择---</option>
										</select>
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-1 control-label">备注</label>
									<div class="col-sm-11">
										<textarea class="form-control" id="remark" style="height: 50px"></textarea>
									</div>
								</div>
							
							</fieldset>

						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btOwnerAdd" onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="ownerId" type="hidden" />

				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->

		<!-- 查看业主和家属  -->
		<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1020px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">业主及家属信息</h4>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#tab-owner">业主信息</a></li>
							<li class=""><a data-toggle="tab" href="#tab-family">家属信息</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-owner" class="tab-pane active">
								<form class="form-horizontal" role="form">

									<fieldset id="basicInfo">
										<legend style="font-size: 15px; border: 0;"></legend>

										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">业主姓名</label>
											<div class="col-sm-3">
												<input class="form-control validate[required]" id="ownerName_v" type="text" disabled placeholder="必填项" />
											</div>
											<label class="col-sm-1 control-label">手机号</label>
											<div class="col-sm-3">
												<input class="form-control" id="phone_v" type="text" disabled placeholder="必填项" />
											</div>
											<label class="col-sm-1 control-label">联系电话</label>
											<div class="col-sm-3">
												<input class="form-control" id="telPhone_v" disabled type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">身份证号</label>
											<div class="col-sm-3">
												<input class="form-control" id="cardId_v" type="text" disabled placeholder="必填项" />
											</div>
											<label class="col-sm-1 control-label">出生日期</label>
											<div class="col-sm-3">
												<input class="form-control laydate-icon layer-date" disabled onclick="laydate.render({elem: this});" id="birthDate_v" type="text" />
											</div>
											<label class="col-sm-1 control-label"  >性别</label>
											<div class="col-sm-3">
												<select class="form-control" id="sex_v" disabled >
													<option value="男">男</option>
													<option value="女">女</option>
												</select>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">紧急联系人</label>
											<div class="col-sm-3">
												<input class="form-control" id="emergencyContact_v" disabled type="text" />
											</div>
											<label class="col-sm-1 control-label">紧急电话</label>
											<div class="col-sm-3">
												<input class="form-control" id="emergencyContactPhone_v" disabled type="text" />
											</div>
											<label class="col-sm-1 control-label">业主类型</label>
											<div class="col-sm-3">
												<input class="form-control" id="ownerType_v" type="text" disabled="disabled" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">车牌号</label>
											<div class="col-sm-3">
												<input class="form-control" id="carId_v" disabled type="text" />
											</div>
											<label class="col-sm-1 control-label">身份类别</label>
											<div class="col-sm-3">
												<input class="form-control" id="ownerIdentity_v" type="text" disabled="disabled" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">备注</label>
											<div class="col-sm-11">
												<textarea class="form-control" id="remark_v" style="height: 50px" disabled="disabled"></textarea>
											</div>
										</div>
									</fieldset>

								</form>
							</div>
							<div id="tab-family" class="tab-pane">

								<div class="feed-activity-list" id="family" style="padding-left: 20px; padding-top: 30px;"></div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 查看业主和家属    结束 -->

	</div>
</body>

</html>
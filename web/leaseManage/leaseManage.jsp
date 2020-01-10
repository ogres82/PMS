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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>租赁管理信息</title>

<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/leaseManage/leaseManage.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>

<script type="text/javascript">
	loginUserName = "${loginUser.username}";
	loginUserCname = "${loginUser.cname}";
</script>

</head>
<body class="gray-bg">
	<iframe src="" name="show" style="width: 0; height: 0"></iframe>
	<div class="wrapper wrapper-content">

		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;" id="topToolbar"></div>
			</div>
		</div>


		<i class="fa fa-search" style="position: fixed; right: 12px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->

		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- <div class="row">
			<div class="col-sm-12">
				<blockquote class="layui-elem-quote" style="background-color: white">
					<div class="row">
						<div class="col-sm-2">
							<select class="form-control" name="type" id="SelectLv">
								<option selected value=2>考核指标</option>
								<option value=0>专业名称</option>
								<option value=1>考核类别</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="kpiLv0">
								<option disabled selected style='display: none;' value="">专业名称</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="kpiLv1">
								<option disabled selected style='display: none;' value="">类别名称</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="kpiTypeS">
								<option disabled selected style='display: none;' value="">考核类别</option>
								<option value="0">员工考核</option>
								<option value="1">部门考核</option>
							</select>
						</div>

						<div class="col-sm-2">
							<button id="buttonSearch" class="btn btn-primary" type="button">查询</button>
							<button id="clearSearch" onclick="clearSearch()" class="btn btn-primary" type="button">清空</button>
						</div>
					</div>
				</blockquote>
			</div>
		</div> -->

		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							租赁信息<small></small>
						</h5>

					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="leaseManageInfo"></table>
					</div>
				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  开始-->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 980px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalTitle" style="color: white"></h4>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<form id="myForm1" class="form-horizontal" role="form">
								<fieldset id="">
									<legend style="font-size: 15px; border: 0;"></legend>

									<div class="form-group form-group-sm">
										<label class="col-sm-2 control-label ">选择商铺</label>
										<div class="col-sm-10">
											<div class="input-group">
												<input type="text" class="form-control" id="roomInfo" placeholder="房间号/楼宇/小区/楼盘">
												<div class="input-group-btn">
													<button type="button" class="btn btn-white btn-sm dropdown-toggle" data-toggle="dropdown">
														<span class="caret"></span>
													</button>
													<ul class="dropdown-menu dropdown-menu-right" role="menu">
													</ul>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group form-group-sm">
										<label class="col-sm-2 control-label">商铺地址</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required] " id="roomAddr" type="text" readonly />
										</div>
										<label class="col-sm-2 control-label">建筑面积</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required] " id="buildArea" type="text" readonly />
										</div>
									</div>
									<div class="form-group form-group-sm">
										<label class="col-sm-2 control-label">租户姓名</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required] " id="lesseesName" type="text" placeholder="必填项" />
										</div>
										<label class="col-sm-2 control-label">身份证号码</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required] " id="cardId" type="text" placeholder="必填项" />
										</div>
									</div>
									<div class="form-group form-group-sm">
										<label class="col-sm-2 control-label">联系电话</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required,integer] " id="phone" type="text" placeholder="必填项" />
										</div>
										<label class="col-sm-2 control-label ">出租月数</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required,integer] " id="monthsNum" type="text" placeholder="必填项" />
										</div>
									</div>


									<div class="form-group form-group-sm">
										<label class="col-sm-2 control-label">出租价格</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required,integer] " id="monthsPrice" type="text" placeholder="必填项" />
										</div>
										<label class="col-sm-2 control-label">总价</label>
										<div class="col-sm-4">
											<input class="form-control" id="totalPrice" type="text" readonly />
										</div>
									</div>

									<div class="form-group form-group-sm">
										<label class="col-sm-2 control-label">起租时间</label>
										<div class="col-sm-4">
											<input class="form-control laydate-icon layer-date validate[required] " id="startTime" type="text" placeholder="必填项" />
										</div>
										<label class="col-sm-2 control-label">终止时间</label>
										<div class="col-sm-4">
											<input class="form-control" id="endTime" type="text" readonly />
										</div>
									</div>
									<div class="form-group form-group-sm">
										<label class="col-sm-2 control-label">备注</label>
										<div class="col-sm-10">
											<textarea class="form-control" id="remark" style="height: 100px"></textarea>
										</div>
									</div>
									<legend style="font-size: 15px; border: 0;"></legend>
									<br>
								</fieldset>
							</form>
						</div>
						<div class="form-group form-group-sm "></div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							<button id="btAdd" onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
						</div>
						<input class="form-control" id="roomId" type="hidden" /><input class="form-control" id="leaseId" type="hidden" /> <input class="form-control" id="insertTime" type="hidden" /> <input class="form-control" id="updateTime" type="hidden" />
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出  结束  -->


	</div>

</body>

</html>
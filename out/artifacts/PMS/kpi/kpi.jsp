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
<title>考核指标信息</title>

<%@ include file="/common/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" href="${ContextPath}/kpi/multi-select.css">

<script type="text/javascript" src="${ContextPath}/kpi/jquery.multi-select.js"></script>
<script type="text/javascript" src="${ContextPath}/kpi/kpi.js"></script>
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
		<div class="row">
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
		</div>

		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							考核指标信息<small></small>
						</h5>

					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="kpiInfo"></table>
					</div>
				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  开始-->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1100px">
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

									<div class="form-group form-group-sm kpiLv0Ins">
										<label class="col-sm-2 control-label ">专业名称</label>
										<div class="col-sm-4">
											<select class="form-control" name="type" id="kpiLv0Ins">
												<option disabled selected style='display: none;' value="">----选择专业-----</option>
											</select>
										</div>
									</div>
									<div class="form-group form-group-sm kpiLv1Ins">
										<label class="col-sm-2 control-label">类别名称</label>
										<div class="col-sm-4">
											<select class="form-control" name="type" id="kpiLv1Ins">
												<option disabled selected style='display: none;' value="">----选择类别-----</option>
											</select>
										</div>
									</div>
									<div class="form-group form-group-sm kpiName">
										<label class="col-sm-2 control-label">指标名称</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required] " id="kpiName" type="text" />
										</div>
									</div>

									<div class="form-group form-group-sm kpiValue">
										<label class="col-sm-2 control-label">分值</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required,integer] " id="kpiValue" type="text" placeholder="必填项" />
										</div>
									</div>
									<div class="form-group form-group-sm kpiType">
										<label class="col-sm-2 control-label ">考核类别</label>
										<div class="col-sm-4">
											<select class="form-control" name="type" id="kpiType" onchange="doubleboxShow()">
												<option value="0" selected >员工考核</option>
												<option value="1">部门考核</option>
											</select>
										</div>
									</div>

									<div class="form-group form-group-sm kpiDetail">
										<label class="col-sm-2 control-label">工作指标</label>
										<div class="col-sm-10">
											<textarea class="form-control validate[required]" id="kpiDetail" style="height: 150px"></textarea>
										</div>
									</div>
									<div class="form-group form-group-sm kpiMethod">
										<label class="col-sm-2 control-label">核查方法及扣分</label>
										<div class="col-sm-10">
											<textarea class="form-control validate[required]" id="kpiMethod" style="height: 100px"></textarea>
										</div>
									</div>
									<div class="form-group form-group-sm empPost">
										<label class="col-sm-2 control-label">考核岗位</label>
										<div class="col-sm-10">
											<select multiple="multiple" size="10" id="doublebox" name="doublebox">
											</select>
										</div>
									</div>
										<div class="form-group form-group-sm empDept">
										<label class="col-sm-2 control-label">考核部门</label>
										<div class="col-sm-10">
											<select multiple="multiple" size="10" id="doublebox1" name="doublebox1">
											</select>
										</div>
									</div>
									<div class="form-group form-group-sm kpiRateBtn">
										<label class="col-sm-2 control-label">检查频率</label>
										<div class="col-sm-10">
											<button id="kpiRateInsert" onclick="openRateWindow(1)" class="btn btn-primary " type="button">
												<i class="fa fa-plus"></i>&nbsp;增加
											</button>
											<button id="kpiRateDel" onclick="openRateWindow(2)" class="btn btn-primary " type="button">
												<i class="fa fa-edit"></i> 删除
											</button>
										</div>
									</div>
									<div class="form-group form-group-sm kpiRateInfo">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<table class="table-no-bordered" id="kpiRate"></table>
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
							<button id="btKpiAdd" onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
						</div>
						<input class="form-control" id="kpiId" type="hidden" /> <input class="form-control" id="insertDate" type="hidden" /> <input class="form-control" id="updateDate" type="hidden" />
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出  结束  -->

		<!-- 新增频率表单  开始-->
		<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 500px; margin-top: 100px;">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">收费信息</h4>
					</div>
					<div class="modal-body">
						<form id="myForm2" class="form-horizontal" role="form">
							<fieldset id="kpiRateInfo">
								<legend style="font-size: 15px; border: 0;"></legend>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">考核岗位</label>
									<div class="col-sm-10">
										<select class="form-control validate[required]" name="type" id="empPostIns">
											<option disabled selected style='display: none;' value="">----选择考核岗位-----</option>
										</select>
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">检查周期</label>
									<div class="col-sm-10">
										<select class="form-control validate[required]" name="type" id="rateUnit">
											<option disabled selected style='display: none;' value="">----选择频率单位-----</option>
											<option value=1>天</option>
											<option value=7>周</option>
											<option value=14>2周</option>
											<option value=30>月</option>
										</select>
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">检查次数</label>
									<div class="col-sm-10">
										<input class="form-control  validate[required,integer] " id="rateValue" type="text" placeholder="必填项" />
									</div>
								</div>
							</fieldset>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btRateAdd" onclick="rateSaveButton()" class="btn btn-primary " type="button">保存</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 新增频率表单  结束-->
	</div>

</body>

</html>
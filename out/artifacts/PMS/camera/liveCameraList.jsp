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
	String companyName = request.getParameter("companyName");
	request.setAttribute("companyName", companyName);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>监控管理</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/camera/liveCameraList.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<!-- <div style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;"
					id="topToolbar"></div>
			</div>
		</div> -->
		<!-- <i class="fa fa-search"
			style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i> -->
		<!-- 占位div -->
		<!-- <div class="" style="width: 100%; height: 45px;"></div> -->
		<!-- 占位div -->

		<div class="row">
			<div class="col-sm-12">
				<blockquote class="layui-elem-quote" style="background-color: white">
					<div class="row">
						<div class="col-sm-2">
							<select class="form-control" name="type" id="regionSelect" >
								<option selected value="">所有小区</option>
							</select>
						</div>
						<div class="col-sm-2">
							<input class="form-control" id="searchName" type="text" style=""
								placeholder="监控或小区名称" />
						</div>
						<div class="col-sm-2">
							<button id="btn_ser" class="btn btn-primary"
								onclick="searchClick()" type="button">查询</button>
							<button id="btn_add" class="btn btn-primary"
								onclick="addNewClick()" type="button">新增</button>
							<button id="btn_del" class="btn btn-primary"
								onclick="deleteClick()" type="button">删除</button>
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
							<span id="companyName"></span>监控管理<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="cameraTableDiv">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="cameraTable"></table>
					</div>
				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalTitle" style="color: white"></h4>
					</div>
					<div class="modal-body">
						<div>
							<form id="myForm1" class="form-horizontal" role="form">
								<fieldset id="basicInfo">
									<legend style="font-size: 15px; border: 0;"></legend>
									<div class="form-group">
										<label class="col-sm-2 control-label">所属小区</label>
										<div class="col-sm-10">
											<select class="form-control validate[required]" name="type"
												id="editRegionSelect">
												<option selected value="">请选择小区</option>
											</select>
										</div>
										<!-- 	<label class="col-sm-1 control-label">监控名称</label>
										<div class="col-sm-3">
											<input class="form-control validate[required]" id="name"
												type="text" placeholder="监控名称必填" />
										</div>
										<label class="col-sm-1 control-label">监控地址</label>
										<div class="col-sm-3">
											<input class="form-control validate[required]" id="address"
												type="text" placeholder="监控地址必填" />
										</div> -->
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">监控名称</label>
										<div class="col-sm-10">
											<input class="form-control validate[required]" id="name"
												type="text" placeholder="监控名称必填" />
										</div>

									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label">监控地址</label>
										<div class="col-sm-10">
											<input class="form-control validate[required]" id="address"
												type="text" placeholder="http://" />
										</div>
									</div>
									
									<div class="form-group">

										<label class="col-sm-2 control-label">摄像头型号</label>
										<div class="col-sm-10">
											<input class="form-control validate[required]" id="model"
												type="text" placeholder="摄像头型号" />
										</div>
									</div>
									
									<div class="form-group">

										<label class="col-sm-2 control-label">摄像头序列号</label>
										<div class="col-sm-10">
											<input class="form-control validate[required]" id="serialNumber"
												type="text" placeholder="摄像头序列号" />
										</div>
									</div>
									
									<div class="form-group">

										<label class="col-sm-2 control-label">摄像头验证码</label>
										<div class="col-sm-10">
											<input class="form-control validate[required]" id="verificationCode"
												type="text" placeholder="摄像头验证码" />
										</div>
									</div>
									
									<div class="form-group" id="createDateDiv" style="display:none">

										<label class="col-sm-2 control-label">添加时间</label>
										<div class="col-sm-10">
											<input class="form-control" id="createDate"
												type="text" readonly="readonly"/>
										</div>
									</div>
								</fieldset>
							</form>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button id="btnCameraAdd" class="btn btn-primary" type="button">保存</button>
							</div>
							<input class="form-control" id="id" type="hidden" />
						</div>
					</div>
				</div>
			</div>

</div>
		</div>
</body>
</html>
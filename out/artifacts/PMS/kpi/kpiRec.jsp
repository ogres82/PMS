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
<title>考核记录信息</title>

<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/kpi/kpiRec.js"></script>
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
							<select class="form-control" name="type" id="deptIdSearch">
								<option disabled selected style='display: none;' value="">部门名称</option>
							</select>
						</div>
						<div class="col-sm-2">
							<input type="text" class="form-control layer-date" placeholder="考核日期" id="dateSearch" />
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
							考核记录信息<small></small>
						</h5>

					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="kpiRecInfo"></table>
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
	
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label ">部门名称</label>
										<div class="col-sm-4">
											<select class="form-control" name="type" id="empDept">
												<option disabled selected style='display: none;' value="">----请选择-----</option>
											</select>
										</div>
										<label class="col-sm-2 control-label">岗位名称</label>
										<div class="col-sm-4">
											<select class="form-control" name="type" id="empPost">
												<option disabled selected style='display: none;' value="">-----请选择-----</option>
											</select>
										</div>
									</div>

									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">扣分值</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required,integer] " id="kpiValue" type="text" placeholder="必填项" />
										</div>
										<label class="col-sm-2 control-label">被考核人员</label>
										<div class="col-sm-4">
											<input class="form-control  validate[required] " id="empName" type="text" placeholder="必填项" />
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
										<fieldset>
											<label class="col-sm-2 control-label">考核图片</label>
											<div id="imgUlrs"></div>
										</fieldset>
									</div>
									<legend style="font-size: 15px; border: 0;"></legend>
									<br>
								</fieldset>
							</form>
						</div>
						<div class="form-group form-group-sm "></div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							<button id="btRecAdd" onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
						</div>
						<input class="form-control" id="recId" type="hidden" /> 
						<input class="form-control" id="insertDate" type="hidden" /> 
						<input class="form-control" id="kpiId" type="hidden" />
						<input class="form-control" id="reason" type="hidden" />
						<input class="form-control" id="empId" type="hidden" />
						<input class="form-control" id="operName" type="hidden" />
						
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出  结束  -->
	</div>
</body>
</html>
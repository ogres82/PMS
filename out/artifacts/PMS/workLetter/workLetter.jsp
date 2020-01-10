<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser"%>
<%@ page import="com.bstek.bdf2.core.context.ContextHolder"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
	String deptId = user.getDepts().get(0).getId();
	String deptName = user.getDepts().get(0).getName();

	request.setAttribute("loginUser", user);
	request.setAttribute("deptId", deptId);
	request.setAttribute("deptName", deptName);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工作函管理</title>
<%@ include file="/common/taglibs.jsp"%>



<script type="text/javascript" src="${ContextPath}/common/js/fileinput.min.js"></script>
<script type="text/javascript" src="${ContextPath}/workLetter/workLetter.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/zh.js"></script>

<link href="${ContextPath}/Hplus/css/fileinput.min.css" rel="stylesheet" />
<link href="${ContextPath}/Hplus/css/fileinput-rtl.min.css" rel="stylesheet" />

<script type="text/javascript">
	loginUserName = "${loginUser.username}";
	loginUserCname = "${loginUser.cname}";
	deptId = "${deptId}";
	deptName = "${deptName}";
</script>

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">

		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;" id="topToolbar"></div>
			</div>
		</div>
		<i class="fa fa-search" style="position: fixed; right: 122px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->


		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							工作函信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="workLetterInfo"></table>

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
						<h4 class="modal-title" id="myModalTitle" style="color: white"></h4>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<form id="myForm1" class="form-horizontal" role="form">

									<fieldset id="basicInfo">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">部门名称</label>
											<div class="col-sm-4">
												<input class="form-control" id="deptName" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-2 control-label">操作人</label>
											<div class="col-sm-4">
												<input class="form-control" id="operName" type="text" readonly="readonly" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">创建时间</label>
											<div class="col-sm-4">
												<input class="form-control" id="insertDate" type="text" readonly="readonly" />
											</div>
											<label class="col-sm-2 control-label">修改时间</label>
											<div class="col-sm-4">
												<input class="form-control" id="updateDate" type="text" readonly="readonly" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">工作函名称</label>
											<div class="col-sm-4">
												<input class="form-control validate[required]" id="docName" type="text" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">备注</label>
											<div class="col-sm-10">
												<textarea class="form-control" style="height: 65px" id="remark" rows="3" cols="145"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm">
											<label class="col-sm-2 control-label">工作函文档</label>
											<div class="col-sm-10" id="docFiles"></div>
										</div>
										<div class="form-group form-group-sm " id="uploadFile">
											<label class="col-sm-2 control-label">上传附件</label>
									
											<div class="col-sm-10"> 
											<p class="help-block">支持word、PDF格式，大小不超过25.0M</p>
												<input id="fileUp"  multiple type="file"  >
												  
											</div>
										</div>
									</fieldset>

								</form>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btContractAdd" onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="docUrls" type="hidden" /> <input class="form-control" id="letId" type="hidden" /> <input class="form-control" id="deptId" type="hidden" /> <input class="form-control" id="operId" type="hidden" />
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	</div>

</body>

</html>
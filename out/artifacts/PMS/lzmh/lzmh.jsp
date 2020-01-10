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
<title>门禁用户信息</title>
<%@ include file="/common/taglibs.jsp"%> 
<link href="${ContextPath}/Hplus/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">

<script type="text/javascript" src="${ContextPath}/lzmh/lzmh.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script src="${ContextPath}/Hplus/js/plugins/treeview/bootstrap-treeview.js"></script>

</head>
<body>
	<div class="wrapper wrapper-content">
		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div style="width: 100%; height: 55px; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;" id="topToolbar"></div>
			</div>
		</div>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->


		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							门禁用户信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="lzmhUserInfo"></table>

					</div>

				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1000px;">
				<div class="modal-content" style="height: 600px">
					<div class="modal-header" style="background: #18a689; height: 8%">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalTitle" style="color: white"></h4>
					</div>
					<div class="modal-body" style="height: 80%">
						<form id="myForm1" class="form-horizontal" role="form">
							<legend style="font-size: 15px; border: 0;"></legend>
							<div class="form-group form-group-sm ">
									<div class="col-sm-4 pre-scrollable">
										<div id="tree"></div>
									</div>
									<div class="col-sm-8">
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">房间名称</label>
											<div class="col-sm-10">
												<input class="form-control validate[required]" id="roomAddr" type="text" readonly="readonly" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">用户名称</label>
											<div class="col-sm-10">
												<input class="form-control validate[required]" id="ownerName" type="text" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">手机号码</label>
											<div class="col-sm-10">
												<input class="form-control validate[required,custom[mobilephone]]" id="phone" type="text" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm cardId">
											<label class="col-sm-2 control-label">身份证号</label>
											<div class="col-sm-10">
												<input class="form-control validate[required,custom[chinaIdLoose]]" id="cardId" type="text" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">用户身份</label>
											<div class="col-sm-4">
												<select class="form-control validate[required]" id="identityName">
													<option value="">---请选择---</option>
												</select>
											</div>
											<label class="col-sm-2 control-label">是否启用</label>
											<div class="col-sm-4">
												<select class="form-control validate[required]" id="ownerState">
													<option value="1">启用</option>
													<option value="0">停用</option>
												</select>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">有效期间</label>
											<div class="col-sm-4">
												<input class="form-control validate[required]" id="validityDate" type="text" placeholder="必填项" />
											</div>
										<!-- </div>
										<div class="form-group form-group-sm "> -->
											<label class="col-sm-2 control-label">有效时间</label>
											<div class="col-sm-4">
												<input class="form-control validate[required]" id="validityTime" type="text" placeholder="必填项" />
											</div>
										</div>
									</div>
							</div>
						</form>

					</div>
					<div class="form-group form-group-sm "></div>
					<div class="modal-footer" style="height: 12%">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btAdd" onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="buildId" type="hidden" /> 
					<input class="form-control" id="userId" type="hidden" />
				</div>
			</div>
		</div>
	</div>
	<!-- 弹出窗口区域，触发事件后弹出   结束 -->

</body>

</html>
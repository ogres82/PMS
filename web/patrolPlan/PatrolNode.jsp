<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>巡逻点管理</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/patrolPlan/PatrolNode.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/jquery.jqprint-0.3.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/jquery.PrintArea.js"></script>
<link rel="stylesheet" href="css/printstylesheet.css" media="print" />

<script type="text/javascript">
	loginUserName = "${loginUser.username}";
	loginUserCname = "${loginUser.cname}";
</script>

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">

		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;" id="topToolbar"></div>
				<div class="col-md-6" style="margin-top: 10px; float: left"></div>
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
							巡逻点信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="patrolNodeInfo"></table>

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
						<h4 class="modal-title" id="myModalTitle1" style="color: white"></h4>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<form id="myForm1" class="form-horizontal" role="form">

									<fieldset id="">
										<legend style="font-size: 15px; border: 0;"></legend>

										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">巡逻点名称</label>
											<div class="col-sm-3">
												<input class="form-control validate[required]" id="patrolNodeName" onblur="checkNodeName(this.value)" type="text" placeholder="必填项" /> <input class="form-control " id="patrolNodeId" type="hidden" />
											</div>
											<label class="col-sm-1 control-label">状态</label>
											<div class="col-sm-3">
												<select class="form-control" id="patrolState">
													<option value="0" selected="selected">执行</option>
													<option value="1">作废</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">操作人员</label>
											<div class="col-sm-3">
												<input class="form-control " id="userName" type="text" readonly="readonly" /> <input class="form-control " id="userId" type="hidden" />
											</div>
										</div>
									</fieldset>
								</form>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id=btSave onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="Id" type="hidden" />
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，结束   -->
	
		<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div id="qrcode" style="display: none"></div>
			<div id="print">
				<img class="titleImg" src='image/title.png' />
				<div style="color: #209E85; font-size: 16px; margin-bottom: 15px;">智慧乐湾 ● 巡逻点</div>
				<div style="background-color: #FDFBF3;">
					<img class="qrcodeImg" src='' id='myImg'  />
				</div>
				<div id="codeTitle"></div>
			</div>
		</div>
	
	</div>
</body>

</html>
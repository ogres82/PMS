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
<title>用户维护</title>
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/Hplus/css/plugins/iCheck/custom.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${ContextPath}/Hplus/js/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript"
	src="${ContextPath}/system/userMaintain.js"></script>
<script type="text/javascript"
	src="${ContextPath}/common/js/jquery.cxselect.min.js"></script>

<script type="text/javascript">
	loginUserName = "${loginUser.username}";
	loginUserCname = "${loginUser.cname}";
</script>
<style>
#moreSearch:hover {
	cursor: pointer;
}

.fixed-table-body {
	overflow: inherit;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">

		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div
			style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;">
					<button id="btn_add" class="btn btn-primary" type="button">
						<i class="fa fa-plus"></i>&nbsp;新增
					</button>
					<button id="btn_del" class="btn btn-primary" type="button">
						<i class="fa fa-trash-o"></i> 删除
					</button>
				</div>
			</div>
		</div>
		<i class="fa fa-search"
			style="position: fixed; right: 15px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->

		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							用户信息<small></small>
						</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="dropdown-toggle" data-toggle="dropdown"
								href="table_data_tables.html#"> <i class="fa fa-wrench"></i>
							</a> <a class="close-link"> <i class="fa fa-times"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="userInfo"></table>
					</div>
				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 600px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
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
											<label class="col-sm-2 control-label">人员信息</label>
											<div class="col-sm-10">
												<div class="input-group">
													<input type="text" class="form-control"
														placeholder="请输入部门|姓名|电话" id="empInfo"> <span
														class="input-group-btn">
														<button class="btn btn-white dropdown-toggle"
															data-toggle="dropdown" style="height: 30px;"
															type="button" id="dropdown">
															<span class="caret"></span>
														</button>
														<ul class="dropdown-menu dropdown-menu-right" role="menu">
														</ul>
													</span>
												</div>
											</div>

										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">用户名</label>
											<div class="col-sm-10">
												<input
													class="form-control validate[required,custom[onlyLetterNumber],ajax[checkUsername]]"
													id="username" type="text" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">中文名</label>
											<div class="col-sm-10">
												<input class="form-control validate[required]" id="cname"
													type="text" placeholder="必填项" /> <input
													class="form-control" id="ename" type="hidden" />
											</div>
										</div>

										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">密码</label>
											<div class="col-sm-10">
												<input class="form-control validate[required]" id="password"
													type="password" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">确认密码</label>
											<div class="col-sm-10">
												<input
													class="form-control validate[required,custom[checkRePwd]]"
													id="repassword" type="password" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">手机</label>
											<div class="col-sm-10">
												<input
													class="form-control validate[required,custom[mobilephone]]"
													id="mobile" type="text" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">出生日期</label>
											<div class="col-sm-4">
												<input
													class="form-control laydate-icon layer-date validate[required]"
													id="birthday" type="text" readonly />
											</div>
											<label class="col-sm-2 control-label">邮箱</label>
											<div class="col-sm-4">
												<input class="form-control" id="email" type="text" /> <input
													class="form-control" id="address" type="hidden" />
											</div>

										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">性别</label>
											<div class="col-sm-4">
												<label class="radio i-checks checkbox-inline"><input
													type="radio" value="1" name="male" checked> <i></i>
													男</label> <label class="radio i-checks checkbox-inline"><input
													type="radio" value="0" name="male"> <i></i> 女</label>
											</div>
											<label class="col-sm-2 control-label">是否可用</label>
											<div class="col-sm-4">
												<label class="checkbox-inline i-checks"> <input
													type="checkbox" value="true" id="enabled">
												</label> <input type="hidden" value="true" id="administrator" />
											</div>
										</div>

										<div class="form-group form-group-sm " id="deptAndPos">
											<label class="col-sm-2 control-label">所属部门</label>
											<div class="col-sm-5">
												<select class="dept form-control validate[required]"
													data-value="" data-first-title="选择部门" id="dept"></select>
											</div>
											<div class="col-sm-5">
												<select class="pos form-control validate[required]"
													data-value="" data-first-title="选择岗位" id="pos"></select>
											</div>
										</div>
										<div class="role form-group form-group-sm ">
											<label class="col-sm-2 control-label">角色</label>
											<div class="col-sm-10">
												<input class="form-control" id="roleName" type="text" />
											</div>
										</div>
									</fieldset>
								</form>
							</div>
						</div>
					</div>
					<div class="form-group form-group-sm "></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btUserAdd" onclick="openSaveButton()"
							class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="username_" type="hidden" />
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->

	</div>
	<script>
		laydate.render({
			elem : '#birthday',
			//format: 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm1').validationEngine('hide');
			}
		});
	</script>
</body>

</html>
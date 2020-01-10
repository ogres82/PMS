<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<title>生活热线管理</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/hotLine/HotLine.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript">
	loginUserName = "${loginUser.username}";
	loginUserCname = "${loginUser.cname}";
</script>
<style>
#moreSearch:hover {
	cursor: pointer;
}

.input-sm,.form-horizontal .form-group-sm .form-control {
	height: 34px;
}

.folder-list li {
	line-height: 40px;
}

.folder-list li a {
	float: left;
	line-height: 40px;
	padding: 0;
	width: 400px;
	white-space: nowrap;
	overflow: hidden;
	-o-text-overflow: ellipsis;
	text-overflow: ellipsis;
}

.delFile {
	cursor: pointer;
	margin-left: 140px;
	padding: 5px;
	border: 1px solid #e5e6e7;
	border-radius: 2px;
}

.delFile:hover {
	border: 1px solid #66CDAA;
}

.cmt-ico .head-ico img {
	border: 1px dashed #F60;
	padding: 2px;
}

.pop-ico {
	position: absolute;
	width: 250px;
	min-height: 130px;
	top: 50px;
	left: 0px;
	background: #FFF;
	border: 1px solid #D7D7D7;
	z-index: 100
}

.pop-ico .ico-title {
	height: 20px;
	background-color: #F0F0F0;
	color: #0078B6;
	padding-top: 5px;
	padding-left: 5px;
	padding-right: 5px
}

.pop-ico .ico-title a {
	cursor: pointer;
	display: block;
	width: 15px;
	height: 15px;
	text-align: center;
	position: absolute;
	right: 5px;
	top: 5px;
}

.pop-ico .ico-list {
	padding: 7px
}

.pop-ico .ico-list a img {
	border: 1px dashed #DDD;
	cursor: pointer;
}

.pop-ico .ico-list a:hover img,.pop-ico .ico-list .cur img {
	border: 1px dashed #F60;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">

		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div
			style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;"
					id="topToolbar"></div>
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
							生活热线信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="hotLineInfo"></table>

					</div>
				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1020px">
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

									<fieldset id="">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">热线图标</label>
											<div class="col-sm-11">
												<!-- 图标选择 start -->
												<div class="cmt-ico" style="position: relative">
													<a class="head-ico show-ico-btn" title="选择图标">
														<img class="focus-ico" id="focus-ico" src="" alt="选择图标" width="58" height="80">
													</a> 
													<input type="hidden" id="imgUrl" name="imgUrl" value="" >
													<!-- 弹出窗口  start-->
													<div class="pop-ico" style="display: none">
														<div class="ico-title">
															<span>选择图标</span> <a title="关闭窗口" class="hide-ico-btn">x</a>
														</div>
														<div class="ico-list" id="ico-list"></div>
													</div>
													<!-- 弹出窗口  end-->
												</div>
												<!-- 图标选择 end -->
											</div>

										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">热线名称</label>
											<div class="col-sm-3">
												<input class="form-control validate[required]" id="name"
													type="text" placeholder="必填项" />
											</div>
											<label class="col-sm-1 control-label">热线号码</label>
											<div class="col-sm-3">
												<input class="form-control validate[required]"
													id="telephone" type="text" placeholder="必填项" />
											</div>
											<label class="col-sm-1 control-label">热线类型</label>
											<div class="col-sm-3">
												<select class="form-control validate[required]"
													id="lineType">
													<option value="">---请选择---</option>
													<option value="0">乐湾国际</option>
													<option value="1">生活服务</option>
												</select>
											</div>
										</div>

										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">操作人员</label>
											<div class="col-sm-3">
												<input class="form-control " id="userName"
													type="text" readonly="readonly" />
													<input class="form-control " id="userId"
													type="hidden" readonly="readonly" />
											</div>
											<label class="col-sm-1 control-label">更新时间</label>
											<div class="col-sm-3">
												<input class="form-control "
													id="updateDate" type="text" readonly="readonly" />
											</div>
										</div>

									</fieldset>

								</form>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btHotLineAdd" onclick="openSaveButton()"
							class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="Id" type="hidden" /> 
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	</div>
</body>

</html>
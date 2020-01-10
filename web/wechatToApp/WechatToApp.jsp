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
<title>微信公众号素材管理</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript" src="${ContextPath}/wechatToApp/WechatToApp.js"></script>

<style>


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
							微信公众号素材信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="materialInfo"></table>

					</div>
				</div>
			</div>
		</div>		
	</div>
</body>

</html>
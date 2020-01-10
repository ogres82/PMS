<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser"%>
<%@ page import="com.bstek.bdf2.core.context.ContextHolder"%>
<%@ page import="com.alibaba.fastjson.JSON"%>
<%@ page import="com.alibaba.fastjson.JSONArray"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
	request.setAttribute("loginUser", user);
	request.setAttribute("userdepts", JSON.toJSONString(user.getDepts()));
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>员工月度考核信息</title>

<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/kpi/assessmentInfo.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>

<script type="text/javascript">
    var loginUserName = "${loginUser.username}";
	var loginUserCname = "${loginUser.cname}";
	var userdepts = ${userdepts};
</script>
<style type="text/css">
.laydate_table { 
	display: none;
}
#laydate_hms{
	display: none !important;
}
</style>

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
							<input type="text" class="form-control layer-date" placeholder="考核季度" id="dateSearch" />
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
							员工月度考核信息<small></small>
						</h5>

					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="assessmentInfo"></table>
					</div>
				</div>
			</div>
		</div>

		
	</div>
</body>
</html>
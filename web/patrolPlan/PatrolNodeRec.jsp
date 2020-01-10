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
<title>巡逻记录查询</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/patrolPlan/PatrolNodeRec.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">

		<div class="row">
			<div class="col-sm-12">
				<blockquote class="layui-elem-quote" style="background-color: white">
					<div class="row">
						<div class="col-sm-2">
							<select class="form-control" name="type" id="planIdSearch">
								<option disabled selected style='display: none;' value="">计划名称</option>

							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="nodeIdSearch">
								<option disabled selected style='display: none;' value="">巡逻点名称</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="userIdSearch">
								<option disabled selected style='display: none;' value="">巡逻人</option>
							</select>
						</div>
						<div class="col-sm-2">
							<input class="form-control layer-date " id="insertDateSearch" type="text" placeholder="巡逻日期" />

						</div>
						<div class="col-sm-4">
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
							巡逻记录<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="patrolNodeRecInfo"></table>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
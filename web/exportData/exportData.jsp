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
<title>数据导出</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript" src="${ContextPath}/exportData/exportData.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<blockquote class="layui-elem-quote" style="background-color: white">
					<div class="row">
						<div class="col-sm-2">
							<select class="form-control" id="selectType" onchange="select(this)">
								<option value="0">业主资料板块</option>
								<option value="1">物业费板块</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="communityIdSearch">
								<option disabled selected style='display: none;' value="">所属小区</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="storiedBuildIdSearch">
								<option disabled selected style='display: none;' value="">所属楼宇</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="roomTypeSearch">
								<option disabled selected style='display: none;' value="">房间类型</option>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="type" id="roomStateSearch">
								<option disabled selected style='display: none;' value="">房间状态</option>
							</select>
						</div>
						<div class="col-sm-2" style="float: right">
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
					<!-- <div class="ibox-title">
						<h5>
							业主资料板块<small></small>
						</h5>
					</div> -->
					<div class="ibox-content" id="tempTable1" style="display: none">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="ownerRoomInfo"></table>
					</div>
					<div class="ibox-content" id="tempTable2" style="display: none">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="roomInChargeInfo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
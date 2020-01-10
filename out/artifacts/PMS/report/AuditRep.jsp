<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>物业费年度统计</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript" src="${ContextPath}/report/AuditRep.js"></script>

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div
			style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;"
					id="topToolbar"></div>

			</div>
		</div>
		<i class="fa fa-search"
			style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->

		<div class="row">
			<div class="col-sm-12">
				<blockquote class="layui-elem-quote" style="background-color: white">
					<div class="row">
						<div class="col-sm-10">
							<div class="row">
								<div class="col-sm-3">
									<select class="form-control" name="type"
										id="charge_type_select">
										<option selected value="AuditRep">统计报表</option>
									<!-- 	<option value="AuditDetail">报表明细</option> -->
									</select>
								</div>
								<div class="col-sm-3">
									<input type="text" name=" beginTime" id="beginTime"
										class="form-control layer-date"
										onclick="laydate.render({elem: this,type: 'year'});" placeholder="开始时间">
								</div>
								<div class="col-sm-3">
									<input type="text" name=" endTime" id="endTime"
										class="form-control layer-date"
										onclick="laydate.render({elem: this,type: 'year'});" placeholder="结束时间">
								</div>
						
							</div> 
						</div>
						<div class="col-sm-2" style="float: right">
							<button id="buttonSearch" class="btn btn-primary" type="button">查询</button>
							<button id="clearSearch" onclick="clearSearch()"
								class="btn btn-primary" type="button">清空</button>
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
							物业费年度统计<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="AuditRep"></table>

					</div>

				</div>
			</div>
		</div>

	</div>
</body>
</html>
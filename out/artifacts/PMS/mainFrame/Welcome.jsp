<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>首页</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="js/index.js"></script>
<script src="${ContextPath}/Hplus/js/plugins/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${ContextPath}/mainFrame/Welcome.js"></script>
<style>
.todo-list>li .label {
	font-size: 12px;
}
</style>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">

		<div class="row">
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<span class="label label-primary pull-right">当前代办</span>
						<h5>待办</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins" id="topTask"></h1>
						<div class="stat-percent font-bold text-success">
							<i class="fa fa-calendar"></i>
						</div>
						<small>总任务数</small>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<span class="label label-success pull-right">最新</span>
						<h5>员工</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins" id="empNum"></h1>
						<div class="stat-percent font-bold text-success">
							<i class="fa fa-users"></i>
						</div>
						<small>员工总数</small>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<span class="label label-info pull-right">本月</span>
						<h5>服务</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins" id="serviceNumMonth"></h1>
						<div class="stat-percent font-bold text-info" id="serviceNumTotal">
							<i class="fa fa-user"></i>
						</div>
						<small>总服务数</small>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<span class="label label-danger pull-right">最新</span>
						<h5>入住率</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins" id="inRate"></h1>
						<div class="stat-percent font-bold text-danger" id="roomNum">
							<i class="fa fa-home"></i>
						</div>
						<small>总房间数</small>
					</div>
				</div>
			</div>
		</div>
		<!-- 待办结束 -->

		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>服务</h5>

					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-9">
								<div class="echarts" id="sample_echarts"></div>
							</div>
							<div class="col-sm-3" id="current_monthInfo"></div>
						</div>
					</div>

				</div>
			</div>
		</div>
		<!-- 服务结束 -->

		<div class="row">
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>消息</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="close-link"> <i class="fa fa-times"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content ibox-heading">
						<h3>
							<i class="fa fa-envelope-o"></i> 新消息
						</h3>
						<small id="unreadMsg"></small>
					</div>
					<div class="ibox-content">
						<div class="feed-activity-list" id="msgs"></div>
					</div>
				</div>
			</div>

			<div class="col-sm-8">

				<div class="row">
					<div class="col-sm-6">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>用户欠费列表</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="close-link"> <i class="fa fa-times"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content">
								<table class="table table-hover no-margins">
									<thead>
										<tr>
											<th>状态</th>
											<th>到期日</th>
											<th>业主名</th>
											<th>金额</th>
										</tr>
									</thead>
									<tbody id="oweList">

									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>任务列表</h5>
								<div class="ibox-tools">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="close-link"> <i class="fa fa-times"></i>
									</a>
								</div>
							</div>
							<div class="ibox-content" style="padding: 5px;">
								<ul class="todo-list m-t small-list ui-sortable" id="taskList">

								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 消息结束 -->

	</div>

</body>

</html>
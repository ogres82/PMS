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
<title>报障受理信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript"
	src="${ContextPath}/webDiagram/modelerList.js">
	
</script>

</head>
<body class="gray-bg">

	<div class="wrapper wrapper-content">
		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div class="row" style="">
			<div class="col-md-6" style="float: left; margin-top: 10px;">
				<button id="btn1" onclick="openButtonWindow(1)"
					class="btn btn-primary " type="button">
					<i class="fa fa-plus"></i>&nbsp;新增
				</button>
			</div>
			<!-- 数据表格区域 -->
			<div class="row">
				<div class="col-sm-12">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>
								流程图信息<small></small>
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
							<table class="table-no-bordered" id=diagramInfo></table>
						</div>
					</div>
				</div>
			</div>
			<!-- 数据区域结束 -->

		</div>
	</div>
</body>
</html>
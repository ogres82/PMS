<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser"%>
<%@ page import="com.bstek.bdf2.core.context.ContextHolder"%>
<%
	DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
	request.setAttribute("loginUser", user);
	String companyName = request.getParameter("companyName");
	request.setAttribute("companyName", companyName);

	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>视频监控</title>
<%@ include file="/common/taglibs.jsp"%>
<script src="${ContextPath}/Hplus/plugins/treeview/bootstrap-treeview.js"></script>
<link href="${ContextPath}/Hplus/plugins/treeview/bootstrap-treeview.css"
	rel="stylesheet" />
<script type="text/javascript" src="${ContextPath}/camera/liveCamera.js"></script>
</head>
<body class="gray-bg">
	<script src="https://open.ys7.com/sdk/js/1.3/ezuikit.js"></script>
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="overflow: auto;">
						<div id="treeview"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-9">
				<div class="ibox float-e-margins">
					<div class="ibox-content" id="content" style="overflow: auto;">
						<video id="myPlayer" poster="" controls playsInline webkit-playsinline autoplay>
							<!-- <source id="cameraId" src="" type="rtmp/flv" />
								<source id="cameraId" src="" type="application/x-mpegURL" /> -->
						</video>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
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

	java.util.Properties prop = new java.util.Properties();
	try {
		prop = com.jdry.pms.comm.util.FileUtil.getProperties("hikvision.properties");
	} catch (Exception e) {
		e.printStackTrace();
	}
	String userName = prop.getProperty("userName");
	String pw = prop.getProperty("pw");
	String ipAdd = prop.getProperty("ipAdd");
	String port = prop.getProperty("port");
%>	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>视频监控</title>
<%@ include file="/common/taglibs.jsp"%>
<script src="${ContextPath}/Hplus/plugins/treeview/bootstrap-treeview.js"></script>
<link href="${ContextPath}/Hplus/plugins/treeview/bootstrap-treeview.css" rel="stylesheet" />
<script type="text/javascript" src="${ContextPath}/camera/jquery.i18n.properties.min.js"></script>
<script type="text/javascript" src="${ContextPath}/camera/camera.js"></script>
<script type="text/javascript">
    var userName ='<%=userName%>';
	var pw = '<%=pw%>';
	var ipAdd = '<%=ipAdd%>';
	var port = '<%=port%>';
</script>
</head>
<body class="gray-bg">
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
					<div class="ibox-content" style="overflow: auto;" id="PreviewOcxDiv">
						<object classid="clsid:AC036352-03EB-4399-9DD0-602AB1D8B6B9"
							id="PreviewOcx" width="100%" height="85%" name="ocx"> </object>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
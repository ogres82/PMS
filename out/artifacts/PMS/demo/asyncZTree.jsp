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
<title>ZTreeV3</title>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="${ContextPath}/zTree3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript" src="${ContextPath}/zTree3/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${ContextPath}/zTree3/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${ContextPath}/zTree3/js/jquery.ztree.exedit.min.js"></script>
</head>
<body>
<h1>zTree</h1>
<div class="content_wrap">
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
</div>

</body>
</html>
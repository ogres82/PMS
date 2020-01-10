<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/test/test.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/md5.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

<div>
    <ul id="tree" class="ztree" style="width:230px; overflow:auto;"></ul>
</div>
</body>
</html>
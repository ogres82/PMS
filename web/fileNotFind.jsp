<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
	<%@ include file="/common/taglibs.jsp"%>
    <title>文件不存在</title>
    <script type="text/javascript"> 
	var i = 5; 
	var intervalid; 
	intervalid = setInterval("fun()", 1000); 
	function fun() { 
	if (i == 0) { 
	window.location.href = "ContractInfo.jsp"; 
	clearInterval(intervalid); 
	} 
	document.getElementById("mes").innerHTML = i; 
	i--; 
	} 
</script> 
</head>

<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1>500</h1>
        <h3 class="font-bold">文件未找到！</h3>

        <div class="error-desc">
            抱歉，文件不存在或者被删除了~ <br/><br/>
            <p>将在 <span id="mes">5</span> 秒钟后返回上一页！或者点击>><a href="javascript:history.go(-1)">返回</a></p>
        </div>
    </div>
</body>

</html>
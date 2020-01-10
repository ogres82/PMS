<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.security.web.WebAttributes"%>
<%@ page import="com.bstek.bdf2.core.context.ContextHolder" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>

<%!
private String getAuthenticationExceptionMessage(){
    Exception exp=(Exception)ContextHolder.getHttpSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    if(exp==null){
        exp=(Exception)ContextHolder.getRequest().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
    if(exp!=null){
        return exp.getMessage();
    }
    return null;
     
}
%>
<%
String error=getAuthenticationExceptionMessage();
if(error == null){
	error = "";
}
%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>乐湾国际物业综合信息管理平台</title>
	
    <link rel="shortcut icon" href="../mainFrame/favicon.ico"> 
    <link href="${ContextPath}/login/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${ContextPath}/login/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
	<link href="${ContextPath}/login/css/custom.css" rel="stylesheet">
    <link href="${ContextPath}/login/css/animate.min.css" rel="stylesheet">
    <link href="${ContextPath}/login/css/style.min.css?v=4.0.0" rel="stylesheet">
    <!--[if lt IE 8]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    
</head>

<body style="background:#376B6D" class="">
	<div style="height:77px;line-height:77px;background:#305A56;margin-top:0px">
		<img src="css/img_logo.png" style="margin-left:25%;" />
		<div style="float:right;margin-right:25%">
			<p class="text-muted" style="color:#FDFBF3;font-size:20px;font-family:Microsoft YaHei;line-height:77px;">乐湾国际物业综合信息管理平台 </p>
		</div>
	</div>
    <div class="middle-box text-center loginscreen  animated fadeInDown" style="width:400px;height:320px;background:#E7E5DD;margin-top:5%;border-radius:8px">
        <div>
            <div style="">

                <h2 style="color:#209E85;font-family:Microsoft YaHei;font-size:20px;font-weight:bold;">登录</h2>

            </div>
            <form class="m-t" style="margin-top:40px" onsubmit="return checkForm()" role="form" action="../security_check_" method="post">
                <div class="form-group">
                    <input type="text" name="username_" id="username_" style="width:299px;text-align:center;margin-right:auto;margin-left:auto;border-radius:4px;font-family:Microsoft YaHei" class="form-control" placeholder="用户名">
                </div>
                <div class="form-group">
                    <input type="password" name="password_" id="password_" style="width:299px;text-align:center;margin-right:auto;margin-left:auto;border-radius:4px;font-family:Microsoft YaHei" class="form-control" placeholder="密码">
                </div>
                <button type="submit"  name="btn_Login" style="width:299px;text-align:center;margin-right:auto;margin-left:auto;border-radius:4px;font-family:Microsoft YaHei"  class="btn btn-primary block m-b">登录</button>
				<div style="float:right;margin-right:50px">
					<label><input type="checkbox" name="remember_me_" class="i-checks" style=";font-family:Microsoft YaHei">自动登录</label>
				</div>
            </form>
            <div align="left" style="margin-left:50px;margin-bottom:5px;font-size:12px;color:red" id="errorInfo"></div>

        </div>
    </div>
	<div style="margin-top:20px">
		<p class="text-muted text-center" style="color:#FDFBF3;font-family:Microsoft YaHei">Copyright©2016 贵州巨动睿云科技有限责任公司</p>
	</div>
	
    <script src="${ContextPath}/login/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ContextPath}/login/js/bootstrap.min.js?v=3.3.5"></script>
	<script src="${ContextPath}/login/js/icheck.min.js"></script>
    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
	 
	<script>
        $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green"})});
    </script>
    <script>
    $(function(){
    	var error="<%=error%>";
    	if(error && error!=""){
    		if(error=="Bad credentials")error="用户名不存在！";
    		if(error=="The password is invalid")error="密码错误！";
    		$("#errorInfo").html(error);
    	}else{
    		$("#errorInfo").html("");
    	}
    	$("#username_").val("");
    	$("#password_").val("");
    	$("#username_").focus();
    });
    window.checkForm=function(){
    	var errorInfo=$("#errorInfo");
    	var username=$("#username_").val();
    	if(username==""){
    		errorInfo.html("用户名不能为空！");
    		$("#username_").focus();
    		return false;
    	}
    	var password=$("#password_").val();
    	if(password==""){
    		errorInfo.html("密码不能为空！");
    		$("#password_").focus();
    		return false;
    	}	
    	
    }
    </script>
</body>

</html>
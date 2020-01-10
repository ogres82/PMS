<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<%@ include file="/common/taglibs.jsp"%>
<script src="${ContextPath}/system/appAuth/appAuthorize.js"></script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    
   		 <div class="row">
	   		 <div class="col-sm-5">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>角色</h5>
	                    
	                </div>
	                <div class="ibox-content" style="overflow:auto">
	                    <table id="roleInfo" class="table table-no-bordered"></table>
	                </div>
	            </div>
	        </div>
	        <div class="col-sm-7">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>菜单URL</h5>
	                    
	                </div>
	                <div class="ibox-content" style="overflow:auto">
	                   <!--  <div id="urlTree" class="test" style="height:100%"></div>
	                    <div class="spiner-example" id="loading" style="display:none;height:100%">
                            <div class="sk-spinner sk-spinner-fading-circle">
                                <div class="sk-circle1 sk-circle"></div>
                                <div class="sk-circle2 sk-circle"></div>
                                <div class="sk-circle3 sk-circle"></div>
                                <div class="sk-circle4 sk-circle"></div>
                                <div class="sk-circle5 sk-circle"></div>
                                <div class="sk-circle6 sk-circle"></div>
                                <div class="sk-circle7 sk-circle"></div>
                                <div class="sk-circle8 sk-circle"></div>
                                <div class="sk-circle9 sk-circle"></div>
                                <div class="sk-circle10 sk-circle"></div>
                                <div class="sk-circle11 sk-circle"></div>
                                <div class="sk-circle12 sk-circle"></div>
                            </div>
                        </div>
                         -->
                        <table id="appUrlInfo" class="table table-no-bordered"></table>
	                    <div id="btn" style="float:right;margin-top:10px;display:none" >
				   			 <button id="btn_save" onclick="save()" class="btn btn-primary " type="button"> 保存</button>
				   	    </div>
	                </div>
	            </div>
	        </div>
		</div>	 	

    </div>
</body>

</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser" %> 
<%@ page import="com.bstek.bdf2.core.context.ContextHolder" %>
<% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
    DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
    request.setAttribute("loginUser",user);
%>

<!DOCTYPE html>
<html>
<head>
<title>流程测试</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function openButtonWindow(type){
	
	
	
		if(type==1){
			layer.confirm("您确定要开始流程吗?",{
				skin: 'layui-layer-molv', 
				btn: ['确定','取消']
			},function(){
		        var url="./../test/activit.app?method=nextStep";
   			   
				$.post(url,
   						
   	    		        function(data){
   							layer.alert(data, {
   							  skin: 'layui-layer-molv' //样式类名
   							  ,closeBtn: 0
   							}, function(){
   								var locationUrl="./../assignwork/NewFile.jsp";
	   							window.location.href=locationUrl;
   							});
   						});
			},function(){
				return;
			})
		}
		if(type==2)
		{
			
			layer.confirm("您确定要开始流程吗?",{
				skin: 'layui-layer-molv', 
				btn: ['确定','取消']
			},function(){
		        var url="./../test/activit.app?method=start";
   			   
				$.post(url,
   						
   	    		        function(data){
   							layer.alert(data, {
   							  skin: 'layui-layer-molv' //样式类名
   							  ,closeBtn: 0
   							}, function(){
   								var locationUrl="./../assignwork/NewFile.jsp";
	   							window.location.href=locationUrl;
   							});
   						});
			},function(){
				return;
			})	
			
		}
		
	
}



</script>

</head>
<body class="gray-bg">
<div style="height: 400px">
					          			<iframe src="./../graph/graphProcessDefinition.app?opt='add'&processDefinitionId=507509" frameborder="0" marginheight="0" marginwidth="0" height="100%" width="100%" id="grapIframe"></iframe>
					          		</div>
    
	       	<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
			<div class="row" style="">
	    		<div  class="col-md-6" style="float:left;margin-top:10px;" >
	    		 <button id="btn1" onclick="openButtonWindow(2)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;开始流程</button>
	    		  <button id="btn1" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;下一步</button>
	    		 
	    		</div>
	    		
	    	</div>
	    	
	    	
	    	
	  
	        
	     
					          		
			          		
			          		
				 		
				
			 
		
</body>
</html>
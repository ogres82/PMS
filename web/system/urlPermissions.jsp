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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>URL权限维护</title> 
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/Hplus/css/plugins/iCheck/custom.css" rel="stylesheet">
<script type="text/javascript" src="${ContextPath}/Hplus/js/plugins/iCheck/icheck.min.js"></script>
<link href="${ContextPath}/Hplus/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
<script src="${ContextPath}/Hplus/js/plugins/treeview/bootstrap-treeview.js"></script>
<script type="text/javascript" src="${ContextPath}/system/urlPermissions.js"></script>

<script type="text/javascript">
 loginUserName="${loginUser.username}";
 loginUserCname="${loginUser.cname}";
</script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-5">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>角色<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable" style="overflow:auto">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="roleInfo"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	            
	            <div class="col-sm-7">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>勾选菜单<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable" style="position:relative">
	          				<div class="row">
						   		<div class="col-md-6" style="position:absolute;top:10px;float:left;margin-bottom:10px;">
						   			 <button id="btn_save" class="btn btn-primary" type="button"><i class="fa fa-plus"></i>&nbsp;保存</button>
						   			 <label class="checkbox-inline i-checks" style="margin-left:10px;">
			                         <input type="checkbox" value="true" id="administrator"> 是否自动勾选子节点</label>
						   	    </div>
						   	</div>
						   	<div style="height:40px;"></div>
						   	<div class="row" id="treeDiv" style="overflow:auto;position:relative">
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
				          		<div id="urlTree" class="test" style="overflow:auto"></div>
			          		</div>
	                    </div>
	                </div>
	            </div>
	            
	        </div>
      
		<!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:900px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" style="color:white">选择用户</h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm1" class="form-horizontal" role="form">
					
								<fieldset>
								<legend style="font-size:15px;border:0;"></legend>
									   <div class="form-group form-group-sm">
											 
											  <div class="col-sm-5" style="padding:0">
												 <table class="table-no-bordered" id="userChoose"></table>
											  </div>
											  <div class="col-sm-2" style="text-align:center;padding:0">
												 <button onclick="selectUser(1)" class="btn btn-primary" type="button" style="margin-top:20px;"><i class="fa fa-plus"></i>&nbsp;添加</button>
							    		 		 <button onclick="selectUser(2)" class="btn btn-default" type="button" style="margin-top:10px;"><i class="fa fa-trash-o"></i> 删除</button>
											  </div>
											  <div class="col-sm-5" style="padding:0">
												 <table class="table-no-bordered" id="userSelected"></table>
											  </div>
									   </div>
								  </fieldset>
			
								  	   <div class="form-group form-group-sm "></div>
									   <div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
											<button onclick="openSaveButton()" class="btn btn-primary " type="button" >确定</button>
									   </div>
									   <input class="form-control" id="roleId" type="hidden" />
							</form>
		          		</div>
	          		</div>
	          		</div>
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		 	
		 			<!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:900px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" style="color:white">选择岗位</h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm1" class="form-horizontal" role="form">
					
								<fieldset>
								<legend style="font-size:15px;border:0;"></legend>
									   <div class="form-group form-group-sm">
											 
											  <div class="col-sm-5" style="padding:0">
												 <table class="table-no-bordered" id="positionChoose"></table>
											  </div>
											  <div class="col-sm-2" style="text-align:center;padding:0">
												 <button onclick="selectPosition(1)" class="btn btn-primary" type="button" style="margin-top:20px;"><i class="fa fa-plus"></i>&nbsp;添加</button>
							    		 		 <button onclick="selectPosition(2)" class="btn btn-default" type="button" style="margin-top:10px;"><i class="fa fa-trash-o"></i> 删除</button>
											  </div>
											  <div class="col-sm-5" style="padding:0">
												 <table class="table-no-bordered" id="positionSelected"></table>
											  </div>
									   </div>
								  </fieldset>
			
								  	   <div class="form-group form-group-sm "></div>
									   <div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
											<button onclick="saveSelectedPosition()" class="btn btn-primary " type="button" >确定</button>
									   </div>
							</form>
		          		</div>
	          		</div>
	          		</div>
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		 	
		 			<!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:600px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" style="color:white">选择部门</h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm1" class="form-horizontal" role="form">
					
								<fieldset>
								<legend style="font-size:15px;border:0;"></legend>
									   <div class="form-group form-group-sm">
										  <div class="col-sm-12" style="padding:0">
											 <table class="table-no-bordered" id="deptChoose"></table>
										  </div>
									   </div>
								  </fieldset>
			
								  	   <div class="form-group form-group-sm "></div>
									   <div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
											<button onclick="saveSelectedDept()" class="btn btn-primary " type="button" >确定</button>
									   </div>
							</form>
		          		</div>
	          		</div>
	          		</div>
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		 	
    </div>
</body>

</html>
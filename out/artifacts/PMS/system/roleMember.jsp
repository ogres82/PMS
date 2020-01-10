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
<title>角色成员维护</title> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/system/roleMember.js"></script>

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
	                        <h5>角色成员信息<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable" style="overflow:auto">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="roleInfo"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	            
	            <div class="col-sm-7">
	                <div class="ibox float-e-margins">
	                    
	                    <div class="ibox-content" id="tempTable" style="overflow:auto">
	                    	<ul class="nav nav-tabs">
			              	<li class="active"><a data-toggle="tab" href="#tab-user">用户</a></li>
			              	<!-- <li class=""><a data-toggle="tab" href="#tab-position">岗位</a></li>
			              	<li class=""><a data-toggle="tab" href="#tab-dept">部门</a></li>  -->
		          		</ul>
		          		<div class="tab-content">
			          		<div id="tab-user" class="tab-pane active">
			          		    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
								<div class="row">
							   		<div class="col-md-6" style="float:left;margin-top:10px;">
							   			 <button id="btn_user_add" class="btn btn-primary" type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
							    		 <button id="btn_user_del" class="btn btn-primary" disabled type="button"><i class="fa fa-trash-o"></i> 删除</button>
							   	    </div>
							   	</div>
		          				<table class="table-no-bordered" id="user"></table>
			          		</div>
		          			<div id="tab-position" class="tab-pane" >
		          				<div class="row">
							   		<div class="col-md-6" style="float:left;margin-top:10px;">
							   			 <button id="btn_position_add" class="btn btn-primary" type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
							    		 <button id="btn_position_del" class="btn btn-primary" disabled type="button"><i class="fa fa-trash-o"></i> 删除</button>
							   	    </div>
							   	</div>
				          		<table class="table-no-bordered" id="position"></table>
		          			</div>
		          			<div id="tab-dept" class="tab-pane" >
		          				<div class="row">
							   		<div class="col-md-6" style="float:left;margin-top:10px;">
							   			 <button id="btn_dept_add" class="btn btn-primary" type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
							    		 <button id="btn_dept_del" class="btn btn-primary" disabled type="button"><i class="fa fa-trash-o"></i> 删除</button>
							   	    </div>
							   	</div>
				          		<table class="table-no-bordered" id="dept"></table>
		          			</div>
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
			
							</form>
		          		</div>
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button onclick="openSaveButton()" class="btn btn-primary " type="button" >确定</button>
				    </div>
				    <input class="form-control" id="roleId" type="hidden" />
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
							</form>
		          		</div>
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button onclick="saveSelectedPosition()" class="btn btn-primary " type="button" >确定</button>
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
			
							</form>
		          		</div>
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button onclick="saveSelectedDept()" class="btn btn-primary " type="button" >确定</button>
				    </div>
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		 	
    </div>
</body>

</html>
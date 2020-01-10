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
<title>部门维护</title>
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/Hplus/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
<link href="${ContextPath}/Hplus/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
<script src="${ContextPath}/Hplus/js/plugins/treeview/bootstrap-treeview.js"></script>
<script src="${ContextPath}/system/deptMaintain.js"></script>
</head>
<body class="gray-bg" style="">
    <div class="wrapper wrapper-content">
    	 <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			 <div class="row">
		   		 <div class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
		   		 	<button id="btn_add" class="btn btn-primary" onclick="buttonEvent(1)" style="margin-right:5px;" type="button"> 新增根部门 </button>
		   		 	<button id="btn_addSub" class="btn btn-primary" onclick="buttonEvent(2)" style="margin-right:5px;" type="button"> 新增子部门 </button>
		   		 	<button id="btn_del" class="btn btn-primary" onclick="buttonEvent(3)" style="margin-right:5px;" type="button"> 删除部门 </button>
		   	     </div>
		   	 </div>
	     </div>
	     <div class="" style="width:100%;height:45px;"></div>
   		 <div class="row" style="">
	   		 <div class="col-sm-4" style="">
	            <div class="ibox float-e-margins" style="">
	                <div class="ibox-title">
	                    <h5>部门</h5>
	                </div>
	                <div class="ibox-content" style="overflow:auto">
	                    <div id="deptTree" class="test"></div>
	                </div>
	            </div>
	        </div>
	        <div class="col-sm-8">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>部门详情</h5>
	                    
	                </div>
	                <div class="ibox-content" style="overflow:auto">
	                    <form class="form-horizontal">
	                        <div class="form-group">
								  <label class="col-sm-1 control-label">部门ID</label>
								  <div class="col-sm-11">
								  		<input class="form-control" id="parentId" type="text" style="display:none"/>
										<input class="form-control" id="id" type="text"/>
								  </div>
						   </div>
						   
						   <div class="form-group">
								  <label class="col-sm-1 control-label">部门名称</label>
								  <div class="col-sm-11">
										<input class="form-control" id="name" type="text"/>
								  </div>
						   </div>
						   
						   <div class="form-group">
								  <label class="col-sm-1 control-label">部门描述</label>
								  <div class="col-sm-11">
									 <textarea rows="4" id="desc"  style="height:80px" class="form-control"></textarea>
								  </div>
						   </div>
						   <div id="btn" style="float:right;margin-top:10px" >
				   			  <button id="btn_save" onclick="save()" class="btn btn-primary " type="button"> 保存</button>
				   	       </div>
					   </form>
	                </div>
	            </div>
	        </div>
		</div>	 	

    </div>
</body>

</html>
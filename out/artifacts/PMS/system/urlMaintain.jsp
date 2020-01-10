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
<title>菜单维护</title>
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/Hplus/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
<link href="${ContextPath}/Hplus/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
<script src="${ContextPath}/Hplus/js/plugins/treeview/bootstrap-treeview.js"></script>
<script src="${ContextPath}/common/js/jquery.blockUI.js"></script>
<script src="${ContextPath}/system/urlMaintain.js"></script>
<script src="${ContextPath}/common/js/util.js"></script>

</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    	 <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			 <div class="row">
		   		 <div class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
		   		 	<button id="btn_add" class="btn btn-primary" onclick="buttonEvent(1)" style="margin-right:5px;" type="button"> 新增根节点 </button>
		   		 	<button id="btn_addSub" class="btn btn-primary" onclick="buttonEvent(2)" style="margin-right:5px;" type="button"> 新增子节点 </button>
		   		 	<button id="btn_del" class="btn btn-primary" onclick="buttonEvent(3)" style="margin-right:5px;" type="button"> 删除节点 </button>
		   	     </div>
		   	 </div>
	     </div>
	     <div class="" style="width:100%;height:45px;"></div>
   		 <div class="row" style="">
	   		 <div class="col-sm-4" style="">
	            <div class="ibox float-e-margins" style="">
	                <div class="ibox-title">
	                    <h5>菜单树</h5>
	                    
	                </div>
	                <div class="ibox-content" style="overflow:auto">
	                    <div id="urlTree" class="test"></div>
	                </div>
	            </div>
	        </div>
	        <div class="col-sm-8">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>菜单详情</h5>
	                    
	                </div>
	                <div class="ibox-content" style="overflow:auto">
	                    <form class="form-horizontal">
	                        <div class="form-group">
								  <label class="col-sm-1 control-label">名称</label>
								  <div class="col-sm-11">
								  		<input class="form-control" id="id" type="text" style="display:none"/>
								  		<input class="form-control" id="order" type="text" style="display:none"/>
								  		<input class="form-control" id="parentId" type="text" style="display:none"/>
										<input class="form-control" id="name" type="text"/>
								  </div>
						   </div>
						   <div class="form-group">
								  <label class="col-sm-1 control-label">是否导航</label>
								  <div class="col-sm-11">
									  <div class="radio radio-success radio-inline">
	                                      <input type="radio" id="forNavi1" value="option1" name="radioInline"/>
	                                      <label for="inlineRadio1"> 是 </label>
	                                  </div>
	                                  <div class="radio radio-success radio-inline">
	                                      <input type="radio" id="forNavi1" value="option2" name="radioInline" checked="checked"/>
	                                      <label for="inlineRadio2"> 否 </label>
	                                  </div>
								  </div>
						   </div>
						   <div class="form-group">
								  <label class="col-sm-1 control-label">URL</label>
								  <div class="col-sm-11">
										<input class="form-control" id="url" type="text"/>
								  </div>
						   </div>
						   <div class="form-group">
								  <label class="col-sm-1 control-label">图标</label>
								  <div class="col-sm-11">
										<input class="form-control" id="icon" type="text"/>
								  </div>
						   </div>
						   <div class="form-group">
								  <label class="col-sm-1 control-label">描述</label>
								  <div class="col-sm-11">
										<input class="form-control" id="desc" type="text"/>
								  </div>
						   </div>
						   <div id="btn" style="float:right;margin-top:10px" >
				   			  <button id="btn_save" onclick="save()"  class="btn btn-primary " type="button"> 保存</button>
				   	       </div>
					   </form>
	                </div>
	            </div>
	        </div>
		</div>	 	

    </div>
</body>

</html>
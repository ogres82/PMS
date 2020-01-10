<!DOCTYPE html>
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
<html>
<head>
<title>仓库档案管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/assertStockManage/wareHouseFiles.js">
</script>

</head>
<body class="gray-bg">

      <div class="wrapper wrapper-content">
	       	<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
			<div class="row" style="">
	    		<div  class="col-md-6" style="float:left;margin-top:10px;" >
	    		  <button id="btn1" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
	    		  <button id="btn2" onclick="openButtonWindow(2)" class="btn btn-primary " type="button"><i class="fa fa-edit"></i> 编辑</button>
	    		  <button id="btn3" onclick="openButtonWindow(3)" class="btn btn-primary " type="button"><i class="fa fa-eye"></i> 查看</button>
	    		  <button id="btn4" onclick="openButtonWindow(4)" class="btn btn-primary " type="button"><i class="fa fa-trash-o"></i> 删除</button>
	    		</div>
	    		<div class="col-md-6"  style="margin-top:10px;float:left">
					<p style="font-size:14px;margin-top:10px;float:right;cursor:pointer;" id="moreSearch">
						更多查询&nbsp;<i class="fa fa-angle-down"></i>
	                </p>
					<div class="form-group" style="" id="search_top" >
						<span style="float:right;width:100px;" class="input-group-btn">
						   <button type="button" style="border-radius:0 5px 5px 0;" class="btn btn-primary"><i class="fa fa-search"></i>查询</button> 
						</span>
						<input class="form-control" style="float:right;width:140px;margin-left:5px;" type="text" style="" placeholder="仓库名称">
					</div> 
	    		</div>
	    	</div>
	    	
	    	
	    	<!-- 更多查询区域  -->
	    	<div class ="row ibox float-e-margins" style="display:none;text-align:center;margin-left:auto;margin-right:auto;" id="more_search">
				<div class="ibox-content col-sm-12 b-r">
					<form class="form-horizontal">
								   
						<div class="form-group" style="margin-top:20px;">
							<label class="col-sm-1 control-label">物资编码</label>
							<div class="col-sm-3">
								<input type="text"  class="form-control"> 
							</div>
							<label class="col-sm-1 control-label">物资类别</label>
							<div class="col-sm-3">
								<input type="text"  class="form-control">
							</div>
						</div>
						<div class="col-sm-12">
							<button type="button" style="float:right;" class="btn btn-primary"><i class="fa fa-search"></i>查询</button> 
						</div>
					 </form>
				</div>
		    </div>
	    	
		<!-- 数据表格区域 -->
		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>仓库档案信息<small></small></h5>
	                        <div class="ibox-tools">
	                            <a class="collapse-link">
	                                <i class="fa fa-chevron-up"></i>
	                            </a>
	                            <a class="dropdown-toggle" data-toggle="dropdown" href="table_data_tables.html#">
	                                <i class="fa fa-wrench"></i>
	                            </a>
	                            
	                            <a class="close-link">
	                                <i class="fa fa-times"></i>
	                            </a>
	                        </div>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="wareHouseFileInfo"></table>	
	                    </div>
	                </div>
	            </div>
	        </div>
	      <!-- 数据区域结束 -->  
	        
	     <!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" style="width:960px">
			<div class="modal-content">
			  <div class="modal-header" style="background:#18a689">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel" style="color:white">仓库档案信息</h4>
			  </div>
			  <div class="modal-body">
				<form id="myForm1" class="form-horizontal" role="form">
					<fieldset id="supplieTyeInfo">
						   <div class="form-group form-group-sm ">
								  <label class="col-sm-2 control-label">仓库编码</label>
								  <div class="col-sm-4">
										<input class="form-control" id="warehouse_code" type="text" readonly="readonly">
								  </div>
								  <label class="col-sm-2 control-label">仓库名称</label>
								  <div class="col-sm-4">
										<input class="form-control validate[required]" id="warehouse_name" type="text" />
								  </div>
						   </div>
						    <div class="form-group form-group-sm ">
								  <label class="col-sm-2 control-label" >仓库地址</label>
								  <div class="col-sm-4">
										<input class="form-control validate[required]" id="warehouse_address" type="text" />
								  </div>
								   <label class="col-sm-2 control-label">&nbsp;联系人</label>
								  <div class="col-sm-4">
										<input class="form-control validate[required]" id="link_man" type="text" />
								  </div>								  
						   </div>
						    <div class="form-group form-group-sm ">
								  <label class="col-sm-2 control-label" >联系电话</label>
								  <div class="col-sm-4">
										<input class="form-control validate[required,custom[phone]]" id="link_phone" type="text" />
								  </div>
								   <label class="col-sm-2 control-label">&nbsp;&nbsp;其他</label>
								  <div class="col-sm-4">
										<input class="form-control" id="other" type="text" />
								  </div>								  
						   </div>
					  </fieldset>
					
					  	   <div class="form-group form-group-sm "></div>
						   <div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button id="btAddsupplietype" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
						   </div>
						<input class="form-control" id="warehouse_id" type="hidden" />
				</form>

			  </div>
			  
			</div>
		  </div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	 </div>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title>物资类别</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/assertStockManage/supplieType.js">
</script>

</head>
<body class="gray-bg">

      <div class="wrapper wrapper-content">
	       	<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
	       	<div style="width:100%;height:45px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
				<div class="row" style="">
		    		<div  class="col-md-6" style="float:left;margin-top:10px;" >
		    		  <button id="btn1" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
		    		  <button id="btn2" onclick="openButtonWindow(2)" class="btn btn-primary " type="button"><i class="fa fa-edit"></i> 编辑</button>
		    		  <button id="btn3" onclick="openButtonWindow(3)" class="btn btn-primary " type="button"><i class="fa fa-eye"></i> 查看</button>
		    		  <button id="btn4" onclick="openButtonWindow(4)" class="btn btn-primary " type="button"><i class="fa fa-trash-o"></i> 删除</button>
		    		</div>
		    	</div>
	    	</div>
	    	
	    	<i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
	   		<!-- 占位div -->
	   		<div class="" style="width:100%;height:45px;"></div>
	    	
		<!-- 数据表格区域 -->
		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>物资信息<small></small></h5>
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
							<table class="table-no-bordered" id="supplietypeInfo"></table>	
	                    </div>
	                </div>
	            </div>
	        </div>
	      <!-- 数据区域结束 -->  
	        
	     <!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" style="width:900px">
			<div class="modal-content">
			  <div class="modal-header" style="background:#18a689">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel" style="color:white">物资信息</h4>
			  </div>
			  <div class="modal-body">
				<form id="myForm1" class="form-horizontal" role="form">
					<fieldset id="supplieTyeInfo">
						   <div class="form-group form-group-sm ">
								  <label class="col-sm-2 control-label">物资编码</label>
								  <div class="col-sm-4">
										<input class="form-control" id="supply_code" type="text" readonly="readonly">
								  </div>
								   <label class="col-sm-2 control-label">物资类别</label>
								  <div class="col-sm-4">
								       
								      <select class="form-control validate[required]" id="parent_supp_id"> 
										  <option value=""></option>
									   </select> 
									
								  </div>
								  
						   </div>
						    <div class="form-group form-group-sm ">
						    <label class="col-sm-2 control-label">详细分类</label>
								  <div class="col-sm-4">
										<input class="form-control validate[required]" id="type_name" type="text" />
								  </div>
								  <label class="col-sm-2 control-label" >其他</label>
								  <div class="col-sm-4">
										<input class="form-control" id="type_orther" type="text" />
								  </div>								  
						   </div>
					  </fieldset>
					
					  	   <div class="form-group form-group-sm "></div>
						   <div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button id="btAddsupplietype" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
						   </div>
						<input class="form-control" id="supplytype_id" type="hidden" />
				</form>

			  </div>
			  
			</div>
		  </div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	 </div>
</body>
</html>
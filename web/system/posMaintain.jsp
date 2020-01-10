<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>岗位维护</title> 
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/Hplus/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<script src="${ContextPath}/Hplus/js/plugins/staps/jquery.steps.min.js"></script>
<script type="text/javascript" src="${ContextPath}/system/posMaintain.js"></script>
<style>
	.fixed-table-body{
		overflow:inherit;
	}
</style>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    
		<div style="width:100%;height:45px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row" style="">
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
 		   			 <button id="btn_add" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i> 新增岗位</button> 
 		    		 <button id="btn_del" onclick="openButtonWindow(3)" class="btn btn-primary " type="button"><i class="fa fa-trash-o"></i> 删除岗位</button> 
		   	    </div>
		   	</div>
	   	</div>
	   	<i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
   		<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
   	
	    
		   
   		<!-- 数据表格区域  -->  
   		<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>岗位信息<small></small></h5>
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
						<table class="table-no-bordered" id="posList"></table>	
                    	
                    </div>
                    
                </div>
            </div>
        </div>
	     
	   
		<!-- 弹出modal  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width:800px">
				 <div class="modal-content">
					 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalLabel" style="color:white">岗位信息</h4>
					 </div>
			 		 <div class="modal-body">
			         		<form id="myForm1" class="form-horizontal" role="form">
								   <div class="form-group form-group-sm ">
										  <label class="col-sm-2 control-label">ID</label>
										  <div class="col-sm-10">
												<input class="form-control validate[required]" id="id" type="text" placeholder="必填项"/>
										  </div>
								   </div>
								   <div class="form-group form-group-sm ">
										  <label class="col-sm-2 control-label">岗位名称</label>
										  <div class="col-sm-10">
												<input class="form-control validate[required]" id="name" type="text" placeholder="必填项"/>
										  </div>
								   </div>
								   <div class="form-group form-group-sm ">
										  <label class="col-sm-2 control-label">岗位描述</label>
										  <div class="col-sm-10">
											 <textarea rows="4" id="desc"  style="height:80px" class="form-control"></textarea>
										  </div>
								   </div>
							</form>
				    	</div>
			          	<div class="form-group form-group-sm "></div>
					    <div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							<button id="btn_save" onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
					    </div>
				 </div>		 	
			</div>
	    </div>
    </div>
</body>

</html>
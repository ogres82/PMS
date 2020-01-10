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
<title>商铺管理</title> 
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/basicInfo/fileinput.min.css" rel="stylesheet" />
<script type="text/javascript" src="${ContextPath}/basicInfo/fileinput.min.js"></script>
<script type="text/javascript" src="${ContextPath}/shop/ShopsInfo.js"></script>

<style>
  #moreSearch:hover{
  	cursor:pointer;
  }
  
  .input-sm, .form-horizontal .form-group-sm .form-control{
	height:34px;
  }
 .hidden-xs{
	display:inline !important;
 }
 
</style>
 <script type="text/javascript">  
 function preview(file)
 {
 var prevDiv = document.getElementById('preview');
 if (file.files && file.files[0])
 {
 var reader = new FileReader();
 reader.onload = function(evt){
 prevDiv.innerHTML = '<img src="' + evt.target.result + '" width="100%" height="100%"/>';
}  
 reader.readAsDataURL(file.files[0]);
}
 else  
 {
 prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
 }
 }
 </script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    
	    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
	    <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
		   		
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
	                        <h5>商铺管理<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="decCompanyInfo"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	        </div>
      
		<!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:1000px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalTitle" style="color:white"></h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm1" class="form-horizontal" role="form">
					
								<fieldset id="basicInfo">
								<legend style="font-size:15px;border:0;"></legend>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-2 control-label">商铺名称</label>
											  <div class="col-sm-4">
													<input class="form-control validate[required]" id="name" type="text" placeholder="必填项"/>
											  </div>
											  <label class="col-sm-2 control-label" >联系电话</label>
											  <div class="col-sm-4">
												<input class="form-control validate[required]" id="telephone" type="text"/>
											  </div>  	
									   </div>
									   <div class="form-group form-group-sm ">
									   		  <label class="col-sm-2 control-label">商铺简介</label>
											  <div class="col-sm-10">
											  	<textarea class="form-control validate[required]" id="summary" style="height:100px;"></textarea>
											  </div>
									   </div>
									   <div class="form-group form-group-sm" id="uploadLogoDiv">
									   	 
									      <label class="col-sm-2 control-label">上传Logo</label>
										  <div class="col-sm-10">
										     <input id="logo" name="kartik-input-710[]" type="file" multiple class="file-loading" onchange="preview(this)">
											 <div id="kv-error-2" style="margin-top:10px;display:none"></div>
										  </div>
								       </div>
								       <div class="form-group form-group-sm">
									      <label class="col-sm-2 control-label">logo预览</label>
										  <div class="col-sm-10">
										     <div id="preview" style="width:150px;height:100px;padding:5px;border:1px solid #e5e6e7"></div>
										  </div>
								       </div>
								  </fieldset>
			
							</form>
		          		</div>
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btDecCompanyAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
				    </div>
				    <input class="form-control" id="id" type="hidden" />
				    <input class="form-control" id="logoPath" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		 
		 
		 <!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:1000px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalTitle2" style="color:white"></h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form class="form-horizontal" role="form">
					
								<fieldset id="basicInfo">
								<legend style="font-size:15px;border:0;"></legend>
									   <div class="form-group form-group-sm checkerInfo">
											<table class="table-no-bordered" id="checkerInfo"></table> 
									   </div>
									    <div class="form-group form-group-sm consultorInfo">
											<table class="table-no-bordered" id="consultorInfo"></table> 
									   </div>
								  </fieldset>
			
							</form>
		          		</div>
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				    </div>
				    <input class="form-control" id="checkId" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		 		<!-- 弹出窗口区域，触发事件后弹出   结束 -->	
    
		 	
    </div>
</body>

</html>
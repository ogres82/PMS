<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser" %> 
<%@ page import="com.bstek.bdf2.core.context.ContextHolder" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
    DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
    request.setAttribute("loginUser",user);
    String companyName = request.getParameter("companyName");
    request.setAttribute("companyName",companyName);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商铺商品管理</title> 
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/basicInfo/fileinput.min.css" rel="stylesheet" />
<script type="text/javascript" src="${ContextPath}/basicInfo/fileinput.min.js"></script>
<script type="text/javascript" src="${ContextPath}/shop/ShopsGoods.js"></script>

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
 
 var companyName = "${companyName}";
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
	                        <h5><span id="companyName"></span>-商品管理<small></small></h5>
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
					 <ul class="nav nav-tabs">
			            	<li id="t-1" class="active"><a id="a1" data-toggle="tab" href="#tab-1">商品信息</a>
			            	</li>
			            	<li id="t-3" class=""><a id="a3" data-toggle="tab" href="#tab-3">商品图片</a>
			            	</li>
			       	</ul>
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm1" class="form-horizontal" role="form">
					
								<fieldset id="basicInfo">
								<legend style="font-size:15px;border:0;"></legend>
									   <div class="form-group form-group-sm ">
									    <label class="col-sm-1 control-label">商铺名称</label>
											  <div class="col-sm-3">
											  <select class="form-control validate[required]" id="companySelect"   >
											  <option value=''>请选择商铺</option>
											  </select>
													<!-- <input class="form-control validate[required]" id="name" type="text" placeholder="必填项"/> -->
											  </div>
											  <label class="col-sm-1 control-label">商品名称</label>
											  <div class="col-sm-3">
													<input class="form-control validate[required]" id="name" type="text" placeholder="必填项"/>
											  </div>
											  <label class="col-sm-1 control-label" >商品价格</label>
											  <div class="col-sm-3">
												<input class="form-control validate[custom[number],required ]" id="price" type="text"/>
											  </div>  	
									   </div>
									   <div class="form-group form-group-sm ">
									   		  <label class="col-sm-1 control-label">商品简介</label>
											  <div class="col-sm-11">
											     <div class="ibox-tools"></div>
											     <div class="summernote"></div>
										     	 <input type="hidden" id="content_img_path">
											  </div>
									   </div>
								  </fieldset>
							</form>
		          		</div>
		          		
		          		<div id="tab-3" class="tab-pane">
			          		<form id="myForm3" class="form-horizontal" role="form">
									<fieldset id="">
									<legend style="font-size:15px;border:0;"></legend>
										<div class="row">
								            <div class="col-sm-12 animated fadeInRight" id="imgs">								            
								            </div>
		       							</div>
						          		<div class="form-group form-group-sm" id="uploadImgDiv">
						          			<label class="col-sm-1 control-label">图片上传</label>
						          			<div class="col-sm-11">
							          			 <input id="logo" name="kartik-input-710[]" type="file" multiple class="file-loading" accept="image/gif, image/jpeg">
												 <div id="kv-error-1" style="margin-top:10px;display:none"></div>
											</div>
						          		</div>
										<input class="form-control" id="imgRelativePath" type="hidden"/>    
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
	          		</div>
	          		</div>
		 		</div>
		 		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
    </div>
</body>

</html>
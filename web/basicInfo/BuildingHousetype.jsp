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
<title>楼宇户型</title> 
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/basicInfo/fileinput.min.css" rel="stylesheet" />
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript" src="${ContextPath}/basicInfo/fileinput.min.js"></script>
<script type="text/javascript" src="${ContextPath}/basicInfo/BuildingHousetype.js"></script>
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
</head>
<body>
    <div class="wrapper wrapper-content">
    
	    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
	    <div style="width:100%;height:55px;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
		   		
		   	    </div>
		   	</div>
   		</div>
   		<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
   		
		   
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>楼宇户型信息<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="buildingHousetypeInfo"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	        </div>
      
		<!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:600px">
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
										  <label class="col-sm-2 control-label">户型名称</label>
										  <div class="col-sm-10">
												<input class="form-control validate[required]" id="typeName" type="text" placeholder="必填项"/>
										  </div>
									   </div>
									   <div class="form-group form-group-sm ">
								   		  <label class="col-sm-2 control-label" >备注</label>
										   <div class="col-sm-10">
												<input class="form-control" id="remark" type="text" />
										   </div>
									   </div>
								  </fieldset>
			
							</form>
		          		</div>
	          		</div>
	          		</div>
			  	    <div class="form-group form-group-sm "></div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btHousetypeAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
				    </div>
				    <input class="form-control" id="typeId" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		 	
		 	<!--图片管理开始-->
        <!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="imgManageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:1000px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" style="color:white">户型图管理</h4>
		 </div>
		 <div class="modal-body">
		 			<ul class="nav nav-tabs">
		              	<li class="active"><a data-toggle="tab" href="#tab-imgupload">户型图上传</a>
		              	</li>
		              	<li class=""><a data-toggle="tab" href="#tab-imgmanange">户型图管理</a>
		              	</li>
	          		</ul>
	          		<div class="tab-content">
		          		<div id="tab-imgupload" class="tab-pane active">
		          		 <form id="myForm2" class="form-horizontal" role="form">
		          		   <fieldset id="basicInfo">
				          		  <legend style="font-size:15px;border:0;"></legend>
		     				 	<div class="form-group form-group-sm ">
									  <label class="col-sm-2 control-label">户型图名称</label>
									  <div class="col-sm-10">
											<input class="form-control validate[required]" id="imgName" type="text" placeholder="必填项"/>
									  </div>
							    </div>
							    <div class="form-group form-group-sm">
										   	 
								      <label class="col-sm-2 control-label">图片上传</label>
									  <div class="col-sm-10">
									     <input id="imgManage" name="kartik-input-710[]" type="file" multiple class="file-loading" />
										 <div id="kv-error-4" style="margin-top:10px;display:none"></div>
									  </div>
						        </div>
					        </fieldset>
					        </form>
			      		</div>
	          			<div id="tab-imgmanange" class="tab-pane" >
			          		<div class="row">
					            <div class="col-sm-12 animated fadeInRight" id="imgs">
					            
					            </div>

       						 </div>
	          			</div>
	          		</div>
          		</div>
			    <div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button onclick="openSaveImgButton()" class="btn btn-primary " type="button" >保存</button>
			    </div>
			    <input class="form-control" id="imgTypeId" type="hidden" />
			    <input class="form-control" id="imgPath" type="hidden" />
       		</div>
     	</div>
	</div>
 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
    <!--图片管理结束-->     
    
    
     <!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="viewImg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="margin-top:-50px;">
		 <div class="modal-dialog" style="width:80%">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="viewImgTitle" style="color:white"></h4>
		 </div>
		 <div class="modal-body" style="padding:0px;">
	          <img id="bigImg" src="" width="100%"/>
         		</div>
         	</div>
          </div>
 		</div>
	 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->	
		 	
    </div>
</body>

</html>
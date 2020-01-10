<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>物管人员</title>

    <link href="${ContextPath}/Hplus/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${ContextPath}/Hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">

    <link href="${ContextPath}/Hplus/css/animate.min.css" rel="stylesheet">
    <link href="${ContextPath}/Hplus/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
    <link href="${ContextPath}/basicInfo/fileinput.min.css" rel="stylesheet" />

	<style>
		.headImg:hover{
		 	text-decoration:underline;
		}
		.input-sm, .form-horizontal .form-group-sm .form-control{
			height:34px;
		  }
	</style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row" id="allEmps">
        
        </div>
        
        <!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:800px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalLabel" style="color:white">上传头像（200×200像素最佳）</h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm1" class="form-horizontal" role="form">
								<fieldset id="basicInfo">
								<legend style="font-size:15px;border:0;"></legend>
								   <div class="form-group form-group-sm">
								      <label class="col-sm-2 control-label">选择头像</label>
									  <div class="col-sm-10">
									     <input id="imgManage" name="kartik-input-710[]" type="file" multiple class="file-loading" />
										 <div id="kv-error-4" style="margin-top:10px;display:none"></div>
										 <input class="form-control" id="empId" type="hidden" />
									  </div>
					        	   </div>
								  </fieldset>
							</form>
		          		</div>
	          		</div>
	          	</div>
	          </div>
	         </div>
		 </div>
		 <!-- 弹出窗口区域，触发事件后弹出   结束 -->
    </div>
    <script src="${ContextPath}/Hplus/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ContextPath}/Hplus/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${ContextPath}/Hplus/js/content.min.js?v=1.0.0"></script>
    <script src="${ContextPath}/Hplus/js/plugins/layer/layer.js"></script>
    
    <script type="text/javascript" src="${ContextPath}/basicInfo/fileinput.min.js"></script>
    
    <script src="contacts.js"></script>
    
</body>

</html>
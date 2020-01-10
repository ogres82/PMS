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
<title>物业论坛</title>

<%@ include file="/common/taglibs.jsp"%>
<script src="${ContextPath}/Hplus/js/plugins/peity/jquery.peity.min.js"></script>
<script src="${ContextPath}/Hplus/js/content.min.js?v=1.0.0"></script>
<link href="${ContextPath}/basicInfo/fileinput.min.css" rel="stylesheet" />
<script type="text/javascript" src="${ContextPath}/basicInfo/fileinput.min.js"></script>
<script type="text/javascript" src="${ContextPath}/bbs/forum.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript">
	var loginUserName = '${loginUser.username}';
</script>
<style>
 .input-sm, .form-horizontal .form-group-sm .form-control{
	height:34px;
  }
 .hidden-xs{
	display:inline !important;
 }
</style>
</head>
<body class="gray-bg">
    <div class="row">
        <div class="wrapper wrapper-content">
            <div class="row">
                <div class="col-sm-12">
                    <div class="wrapper wrapper-content">
						<div class="col-sm-12">
	                        <div class="ibox-content m-b-sm border-bottom">
	                            <div class="p-xs">
	                                <div class="pull-left m-r-md">
	                                    <i class="fa fa-globe text-navy mid-icon"></i>
	                                </div>
	                                <h2>欢迎来到乐湾国际物业论坛</h2>
	                            </div>
	                        </div>
                        </div>
             
                        <div class="col-sm-12">
			                <div class="ibox float-e-margins">
			                    <div class="ibox-content" id="tempTable">
			                    	<div>
			                        	<button id="btn_add" onclick="newTopic()" class="btn btn-primary" type="button">发表帖子</button>
			                        </div>
			                    	
									<table class="table-no-bordered" id="topicList"></table>	
			                    </div>
			                </div>
			            </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            	<div class="modal-dialog" style="width:1000px">
            		<div class="modal-content">
            			<div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalTitle" style="color:white">发帖</h4>
						</div>
            			<div class="modal-body">
            				<form id="myForm1" class="form-horizontal" role="form">
								<fieldset id="">
								<legend style="font-size:15px;border:0;"></legend>
									
					          		<div class="form-group form-group-sm ">
					          			<label class="col-sm-1 control-label">标题</label>
					          			<div class="col-sm-11">
						          			 <input type="text" class="form-control" id="title">
										</div>
					          		</div>
									<div class="form-group form-group-sm ">
					          			<label class="col-sm-1 control-label">帖子内容</label>
					          			<div class="col-sm-11">
						          			 <textarea row="3" style="height:120px" class="form-control" id="content"></textarea>
										</div>
					          		</div>
					          		
					          		<div class="form-group form-group-sm ">
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
            			<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							<button id="btnSave" onclick="save()" class="btn btn-primary " type="button" >发表</button>
					   </div>
            		</div>
            	</div>
            </div>
        </div>
    </div>
</body>

</html>
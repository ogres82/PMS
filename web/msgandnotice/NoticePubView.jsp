<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
    <script src="${ContextPath}/Hplus/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ContextPath}/Hplus/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${ContextPath}/Hplus/js/plugins/jeditable/jquery.jeditable.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/laydate/laydate.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${ContextPath}/Hplus/js/content.min.js?v=1.0.0"></script>
    <script src="${ContextPath}/Hplus/js/demo/form-validate-demo.min.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/summernote/summernote.min.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/summernote/summernote-zh-CN.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/layer/layer.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/layer/layer.js"></script>

    <link href="${ContextPath}/Hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/animate.min.css" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/style.min.css?v=4.1.0" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/plugins/dataTables/dataTables.bootstrap.css?v=4.1.0" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/bootstrap.min.css?v=3.3.5" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/plugins/summernote/summernote.css" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/plugins/summernote/summernote-bs3.css" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/style.min.css?v=4.1.0" rel="stylesheet" />
    <script type="text/javascript">
       $(document).ready(function(){
       	   $("#eg").addClass("no-padding");
           $(".click2edit").summernote({lang:"zh-CN",focus:true})
           //$('#myModal').modal(); 
       });
    </script>
  </head>
<body class="gray-bg">

				<div class="modal-content">
				  <div class="modal-header" style="background:#18a689">
					<!-- <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
					<h4 class="modal-title" id="myModalLabel" style="color:white">公告查看</h4>
				  </div>
				  <div class="modal-body">
					<form id="myForm" class="form-horizontal" action="${ContextPath}/msgandnotice/list.app?method=save" method="post">
						  <div class="form-group form-group-sm ">
							  <label class="col-sm-2 control-label">标题</label>
							  <div class="col-sm-4">
								 <input class="form-control" name="ntcSubject" value="${notice.ntcSubject}" id="ntcSubject" type="text" />
							  </div>
							  <label class="col-sm-2 control-label">审核人</label>
							  <div class="col-sm-4">
								 <input class="form-control" name="ntcAudit" id="ntcAudit" value="${notice.ntcAuditor}" type="text"/>
							  </div>
						   </div>
						   <div class="form-group form-group-sm ">
							 <label class="col-sm-2 control-label">公告内容</label>
                              <div class="col-sm-12">
				                <div class="ibox float-e-margins">
				                    <div class="ibox-title">
				                        
				                        <div class="ibox-tools"></div>
				                    </div>
				                    <div class="ibox-content" id="eg">
				                        <div class="click2edit wrapper">${notice.ntcContent}</div>
				                    </div>
				                </div>
                              </div>
                              <input type="hidden" id="ntcContent" name="ntcContent">
						   </div>
						   <div class="form-group form-group-sm ">
							  <label class="col-sm-2 control-label">创建人</label>
							  <div class="col-sm-4">
								 <input class="form-control" name="ntcCreatorName" id="ntcCreatorName" type="text" readonly="readonly" />
								 <input name="ntcCreatorId" id="ntcCreatorId" type="hidden"/>
							  </div>
							  <label class="col-sm-2 control-label">创建时间</label>
							  <div class="col-sm-4">
							     <fmt:parseDate value="${notice.ntcCreateTime}" var="date" pattern="yyyy-MM-dd HH:mm:ss" />
							  </div>
						   </div>
						   <div class="form-group form-group-sm ">
							  <label class="col-sm-2 control-label">状态</label>
							  <div class="col-sm-4">
								 <input class="form-control" name="ntcStatus" id="ntcStatus" type="text" readonly="readonly" />
							  </div>
						   </div>
						   <div class="form-group form-group-sm "></div>
						   <div class="modal-footer">
								<!-- <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button> -->
						   </div>
					</form>
	
				  </div>
				  
				</div>
		
</body>
</html>
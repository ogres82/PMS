<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser" %> 
<%@ page import="com.bstek.bdf2.core.context.ContextHolder" %>
<% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
    DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
    request.setAttribute("loginUser",user);
    String rpt_id=request.getParameter("rpt_id");
    request.setAttribute("rpt_id",rpt_id);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>公告审核</title> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
var rpt_id='${rpt_id}';
</script>
<script type="text/javascript" src="${ContextPath}/msgandnotice/NoticeAuditHandler.js"></script>
</head>
<body>
   
		<div class="modal-content">
				  <div class="modal-header" style="background:#18a689">
					<h4 class="modal-title" id="myModalLabel" style="color:white">审核公告</h4>
				  </div>
				  <div class="modal-body">
				    <ul class="nav nav-tabs">
		              	<li id="li1" class="active"><a data-toggle="tab" href="#tab-1"><i class="fa fa-money"></i>基本信息</a>
		              	</li>
		              	<li class="li2"><a data-toggle="tab" href="#tab-2"><i class="fa fa-calendar"></i>流程图</a>
		              	</li>
	          		</ul>
	          		<div class="tab-content">
				      <div id="tab-1" class="tab-pane active">
							<form id="myForm" class="form-horizontal" action="${ContextPath}/msgandnotice/noticeaudit.app?method=update" method="post">
								  <div class="form-group form-group-sm " style="margin-top:20px">
									  <label class="col-sm-1 control-label">标题</label>
									  <div class="col-sm-3">
									  	 <input type="hidden" id="ntcId" name="ntcId">
										 <input class="form-control" name="ntcSubject" id="ntcSubject" type="text" readonly="readonly" aria-required="true"/>
									  </div>
								   </div>
								   <div class="form-group form-group-sm ">
								      <label class="col-sm-1 control-label">公告内容</label>
									  <div class="col-sm-11">
									     <div class="ibox-tools"></div>
									     <div class="summernote">
									        <br /><br /><br />
									        <br /><br /><br />
									     </div>
									     <input type="hidden" id="ntcContent" name="ntcContent">
		                              </div>
								   </div>
								   
								   <div class="form-group form-group-sm ">
									  <label class="col-sm-1 control-label">创建人</label>
									  <div class="col-sm-3">
										 <input class="form-control" name="ntcCreatorName" id="ntcCreatorName" type="text" readonly="readonly" aria-required="true"/>
										 <input name="ntcCreatorId" id="ntcCreatorId" type="hidden"/>
									  </div>
									  <label class="col-sm-1 control-label">创建时间</label>
									  <div class="col-sm-3">
										 <input class="form-control" name="ntcCreateTime" id="ntcCreateTime" type="text" readonly="readonly"  aria-required="true"/>
									  </div>
									  <label class="col-sm-1 control-label">状态</label>
									  <div class="col-sm-3">
									  	 <select class="form-control m-b" name="ntcStatus" id="ntcStatus" disabled="disabled">
									  	      <option value="20">待审</option>
									  	      <option value="21">待发布</option>
									  	      <option value="11">驳回</option>
									  	      <option value="30">已发布</option>
									  	      <option value="10">草稿</option>
									  	      <option value="22">审核中</option>
									  	 </select>   
										 <!-- <input class="form-control" name="ntcStatusName" id="ntcStatusName" type="text" required="" readonly="true" aria-required="true"/> -->
									  </div>
								   </div>
								   <div class="form-group form-group-sm ">
								      <label class="col-sm-1 control-label">是否通过</label>
									  <div class="col-sm-3">
									  	 <select class="form-control m-b" name="passFlag" id="passFlag">
									  	      <option value="1">通过</option>
									  	      <option value="0">驳回</option>
									  	 </select> 
									   </div>
								   </div>
								   <label class="col-sm-1 control-label">审核意见</label>
								   <div class="col-sm-11">
									   <textarea class="form-control" rows="6" id="auditContentText" id="auditContentText"></textarea>
								   </div>
									  <input type="hidden" id="auditContent" name="auditContent" />
								   <div class="form-group form-group-sm "></div>
								   <div class="modal-footer">
										<button id="btAssigenHandle" onclick="save()" class="btn btn-primary " type="button" >确定</button>
								   </div>
							</form>
						  </div>
						  <div id="tab-2" class="tab-pane" >
			          		<div style="height: 400px">
			          			<iframe src="./../graph/graphProcessDefinition.app?processDefinitionId=15001" frameborder="0" marginheight="0" marginwidth="0" height="100%" width="100%" id="grapIframe"></iframe>
			          		</div>
	          		      </div>
					  </div>
	
				  </div>
				  
		</div>
</body>
</html>
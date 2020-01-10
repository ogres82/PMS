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
<title>公告审核 </title> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/msgandnotice/NoticeAudit.js"></script>
<script type="text/javascript">
function openWindow(type,id){
		 selections = getIdSelections();
		 var length=selections.length;
		 var url="${ContextPath}"+"/msgandnotice/list.app?method=viewForAjax&noticeId="+id;
		    $.post(url,
		    	   function(data){
				   var list = eval(data);
				        $("#ntcId").val(list.ntcId);
			            $("#ntcCreatorId").val(list.ntcCreatorId);
			            $("#ntcCreatorName").val(list.ntcCreator);
			            $("#ntcCreateTime").val(list.ntcCreateTime);
			            $("#ntcStatus").val(list.ntcStatus);
			            $("#ntcSubject").val(list.ntcSubject); 
			            $("#ntcAudit").val(list.ntcAuditor);
			            $(".summernote").summernote('code',list.ntcContent);
		    			var proInsId=list.processInstanceId;
					    var grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+proInsId+"&numb="+generateMixed(4);
					    $("#grapIframe").attr("src",grapIframe);
		    			$('#myModal').modal();

				    });
		 
}
//关闭对话框的时候清空所有数据
function emptyAll(){
	$(".summernote").summernote('code',"<br /><br /><br /><br /><br /><br />");
	$("#ntcCreatorId").val("");
	$("#ntcId").val("");
    $("#ntcCreatorName").val("");
    $("#ntcCreateTime").val("");
    $("#ntcStatus").val("");
    $("#ntcSubject").val(""); 
    $("#ntcAudit").val("");
}
//保存 表单中的内容 
function save(){
	var flag = $('#myForm').validationEngine('validate');
	if(flag){
		var content=$(".summernote").summernote('code');
		
		$("#ntcStatus").removeAttr("disabled"); 
		var content=$("#auditContentText").val();
		$("#auditContent").val(content);
		var jsonuserinfo = $('#myForm').serializeObject();
		var url="./../msgandnotice/noticeaudit.app?method=update";
		$.post(url,
		        jsonuserinfo,
			    function(data){
					layer.msg('操作成功',
					          {time:2000},
							  function(){
							    $("#chargeInfo").bootstrapTable('refresh');
							    $("#myModal").modal('hide');
								
							  })
					 });
	   }else{
		   layer.msg('表单验证未通过！',{
				time: 3000
			});		
	   }
	
}
</script>
</head>
<body class="gray-bg">
   <div class="wrapper wrapper-content">
   		<div style="width:100%;height:45px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
	        <div class="row" >
	            <div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
	    		  <!-- <button id="btn5" onclick="openWindow(5)" class="btn btn-primary " type="button"><i class="fa fa-commenting-o"></i> 审核</button> -->
	    		</div>
		    </div>
	    </div>
	    <i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
   		<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
        <!-- 更多查询区域  -->
	    	<div class ="row ibox float-e-margins" style="display:none;text-align:center;margin-left:auto;margin-right:auto;" id="more_search">
				<div class="ibox-content col-sm-12 b-r">
					<form class="form-horizontal">
								   
						<div class="form-group" style="margin-top:20px;">
							<label class="col-sm-1 control-label">标题</label>
	
							<div class="col-sm-3">
								<input type="text"  class="form-control"> 
							</div>
	
							<label class="col-sm-1 control-label">状态</label>
	
							<div class="col-sm-3">
								<input type="text"  class="form-control">
							</div>
							<label class="col-sm-1 control-label">创建时间起</label>
	
							<div class="col-sm-3">
								<input type="text" class="form-control">
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
	                        <h5>公告信息<small></small></h5>
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
							<table class="table-no-bordered" id="chargeInfo"></table>	
	                    </div>
	                </div>
	            </div>
	        </div>
        <!-- 弹出窗口区域，触发事件后弹出  -->
	       <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			  <div class="modal-dialog" style="width:1000px">
				<div class="modal-content">
				  <div class="modal-header" style="background:#18a689">
					<button type="button" onclick="emptyAll()" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
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
										 <input class="form-control" name="ntcSubject" id="ntcSubject" type="text" readonly="readonly" required="required" aria-required="true"/>
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
										 <input class="form-control" name="ntcCreateTime" id="ntcCreateTime" type="text" readonly="readonly" required="required" aria-required="true"/>
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
									   <textarea class="form-control validate[required]" rows="6" id="auditContentText" id="auditContentText"></textarea>
								   </div>
									  <input type="hidden" id="auditContent" name="auditContent" />
								   <div class="form-group form-group-sm "></div>
								   <div class="modal-footer">
										<button type="button" onclick="emptyAll()" class="btn btn-default" data-dismiss="modal">关闭</button>
										<button type="button" onclick="save()" class="btn btn-primary">保存</button>
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
			  </div>
			</div>
	</div>
  </body>
</html>
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
<title>公告管理</title> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/msgandnotice/NoticePub.js"></script>
<script type="text/javascript">
loginUserName='${loginUser.username}';
loginUserCname='${loginUser.cname}';
function openWindow(type,id){
	//新增
	 if(type==1){
		emptyAll();
		$.ajax({
	        type: "post",
	        url: "./../system/privilege.app?method=initData",
	        dataType: "json",
			async : false,
	        success: function(data)
	        {
	        	var list = eval(data);
	        	for(var j=0;j<list.length;j++){
	        		if(list[j].type == "01"){
	        			$("#ntcAudit").val(list[j].positionId);
	        		}
	        	}
	        },
	        failure:function(xhr,msg)
	        {
	        	layer.msg('操作失败！',{
					time: 1500
				}, function(){
					
				});
	        }
	    });
		$("#myModalLabel").html("新增公告");
		$("#saveBtn").show();
		$("#saveBtn").html("保存");
		$("#ntcAudit").attr("disabled","disabled");
		$("#ntcSubject").attr("disabled",false);
		$('.summernote').summernote('enable');
		var loginUserName="${loginUser.username}";
        var loginUserCname="${loginUser.cname}";
        $("#ntcCreatorId").val(loginUserName);
        
        
        //待修改：配置审核岗位
//        $("#ntcAudit").val("20160407001");
        $("#ntcCreatorName").val(loginUserCname);
        var datetime=getNowFormatDate(true);
        $("#ntcCreateTime").val(datetime);
        $("#ntcStatus").val("20");
        var grapIframe="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=7501&numb="+generateMixed(4);
        $("#grapIframe").attr("src",grapIframe);
        $("#auditInfos").html("");
        $('#myModal').modal();
		
	 }
	 //编辑
	 if(type==2){
		 $("#myModalLabel").html("编辑公告");
		 $("#saveBtn").show();
		 $("#ntcAudit").attr("disabled","disabled");
		 $("#ntcAudit").attr("disabled","disabled");
		 $("#ntcSubject").attr("disabled",false);
		 $("#saveBtn").html("保存");
		 selections = getIdSelections();
		 var length=selections.length;
		 var url="./../msgandnotice/list.app?method=viewForAjax&noticeId="+id;
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
			            var proInsId=list.processInstanceId;
				        var grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+proInsId+"&numb="+generateMixed(4);
				        $("#grapIframe").attr("src",grapIframe);
			            $(".summernote").summernote('code',list.ntcContent);
			            var action="./../msgandnotice/list.app?method=update";
			            $("#myForm").attr("action",action);
			            var html="";
			            for(var i=0;i<list.msgandnoticeNoticeAuditinfos.length;i++){
			            	html+="<tr data-index=\""+i+"\"><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditor+"</td><td style=\"text-align: center; vertical-align: middle;width:400px; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditContnt+"</td><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcCreateTime+"</td></tr>"
			            }
			            $("#auditInfos").html("");
			            $("#auditInfos").html(html);
			            if(list.ntcStatus==11&&list.ntcCreatorId=="${loginUser.username}"){
			            	$('#myModal').modal();
			            }else{
			            	layer.alert("您不可编辑该公告！",{
								skin: 'layui-layer-molv'
							});
			            }
				    });
		 
	 }
	 //查看
 	 if(type==3){
 		$("#myModalLabel").html("查看公告");
 		selections = getIdSelections();
 		$("#saveBtn").hide();
		$("#ntcAudit").attr("disabled","disabled");
		var url="./../msgandnotice/list.app?method=viewForAjax&noticeId="+id;
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
			            var html="";
			            for(var i=0;i<list.msgandnoticeNoticeAuditinfos.length;i++){
			            	html+="<tr data-index=\""+i+"\"><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditor+"</td><td style=\"text-align: center; vertical-align: middle;width:400px \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditContnt+"</td><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcCreateTime+"</td></tr>"
			            }
			            $("#auditInfos").html("");
			            $("#auditInfos").html(html);
			            $(".summernote").summernote('code',list.ntcContent);
			            var proInsId=list.processInstanceId;
				        var grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+proInsId+"&numb="+generateMixed(4);
				        $("#grapIframe").attr("src",grapIframe);
				        $('#myModal').modal();
				    });
		 
 	 }
 	 //删除
 	 if(type==4){
		 selections = getIdSelections();
		 var length=selections.length;
		 if(length==0){
			 layer.alert("请至少选择一条数据进行删除！",{
					skin: 'layui-layer-molv'
				});
		 }else{
			 layer.confirm("您确定要删除所选信息吗?",{
					skin: 'layui-layer-molv', 
					btn: ['确定','取消']
				},function(){
			        var url="${ContextPath}"+"/msgandnotice/list.app?method=deleteByAjax"
	   			    var deleteIds = JSON.stringify(selections);
					$.post( url,
	   						{deleteId:deleteIds},
	   						function(data){
	   							layer.msg('操作成功',
	   							          {time:2000},
	   									  function(){
	   									    $("#chargeInfo").bootstrapTable('refresh');
	   									  })
	   					});
				},function(){
					return;
				})
		 }
		 
 	 }//发布
 	 if(type==5){
 		$("#myModalLabel").html("发布公告");
  		selections = getIdSelections();
		$("#ntcAudit").attr("disabled","disabled");
		$("#ntcSubject").attr("disabled","disabled");
		$('.summernote').summernote('disable');
  		$("#saveBtn").show();
  		$("#saveBtn").html("发布");
 		    var url="./../msgandnotice/list.app?method=viewForAjax&noticeId="+id;
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
 				        var html="";
			            for(var i=0;i<list.msgandnoticeNoticeAuditinfos.length;i++){
			            	html+="<tr data-index=\""+i+"\"><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditor+"</td><td style=\"text-align:left; vertical-align: middle;width:400px; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditContnt+"</td><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcCreateTime+"</td></tr>"
			            }
			            $("#auditInfos").html("");
			            $("#auditInfos").html(html);
 				       if(list.ntcStatus==21&&list.ntcCreatorId=="${loginUser.username}"){
			            	$('#myModal').modal();
			            }else{
			            	layer.alert("您不可发布该公告！",{
								skin: 'layui-layer-molv'
							});
			            }
 				    });
 		 
 	 }
}
</script>
</head>
<body class="gray-bg">
     <div class="wrapper wrapper-content">
       <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
          <div class="row" >
                <div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
	    		  <!-- <button id="btn1" onclick="openWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
	    		  <button id="btn2" onclick="openWindow(2)" class="btn btn-primary " type="button"><i class="fa fa-edit"></i> 编辑</button>
	    		  <button id="btn3" onclick="openWindow(3)" class="btn btn-primary " type="button"><i class="fa fa-eye"></i> 查看</button>
	    		  <button id="btn4" onclick="openWindow(4)" class="btn btn-primary " type="button"><i class="fa fa-trash-o"></i> 删除</button>
	    		  <button id="btn6" onclick="openWindow(5)" class="btn btn-primary " type="button"><i class="fa fa-file-powerpoint-o"></i> 发布</button> -->
	    		</div>
	    		<!-- <div class="col-md-6"  style="margin-top:10px;float:left">
					<p style="font-size:14px;margin-top:10px;float:right" id="moreSearch">
						更多查询&nbsp;<i class="fa fa-angle-down"></i>
	                </p>
	    		</div> -->    		
	      </div>
       </div>
       <i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
   		<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
   		<!-- 占位div -->	    
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
					<h4 class="modal-title" id="myModalLabel" style="color:white">添加公告</h4>
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
						<form id="myForm" class="form-horizontal" action="${ContextPath}/msgandnotice/list.app?method=save" method="post">
							  <div class="form-group form-group-sm" style="margin-top:20px">
								  <label class="col-sm-1 control-label">标题</label>
								  <div class="col-sm-6">
								  	 <input type="hidden" id="ntcId" name="ntcId">
									 <input class="form-control validate[required]" name="ntcSubject" id="ntcSubject" type="text" aria-required="true"/>
								  </div>
								  <label class="col-sm-1 control-label">审核人</label>
								  <div class="col-sm-4">
									 <select class="form-control" name="ntcAudit" id="ntcAudit" disabled="disabled"></select>
								  </div>
							   </div>
							   <div class="form-group form-group-sm ">
							      <label class="col-sm-1 control-label">公告内容</label>
								  <div class="col-sm-11">
								     <div class="ibox-tools"></div>
								     <div class="summernote">
								     </div>
								     <!-- <input type="hidden" id="ntcContent" name="ntcContent"> -->
								     <!-- <div><textarea id="ntcContent" name="ntcContent"></textarea></div> -->
	                              </div>
							   </div>
							   
							   <div class="form-group form-group-sm ">
								  <label class="col-sm-1 control-label">创建人</label>
								  <div class="col-sm-3">
									 <input class="form-control" name="ntcCreatorName" id="ntcCreatorName" type="text" readonly="readonly" aria-required="true"/>
									 <input name="ntcCreatorId" id="ntcCreatorId" type="hidden"/>
									 <input name="ntcPicPath" id="ntcPicPath" type="hidden" />
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
							       <label id="auditTableDiv" class="col-sm-1 control-label">审核流程<i class="fa fa-angle-down"></i></label>
							   </div>
							   <div id="auditTableInfoDiv" class="form-group form-group-sm" style="display:none;">
							      <label class="col-sm-1 control-label"></label>
							      <div class="col-sm-11">
									 <div class="bootstrap-table">
										  <div class="fixed-table-container" style="padding-bottom: 0px;">
										    <div class="fixed-table-body">
											    <table class="table-no-bordered table table-hover table-striped" id="auditInfo">
												  <thead>
												    <tr>
													  <th style="text-align: center; " data-field="ntcAuditor" tabindex="0">
													    <div class="th-inner ">审核人</div><div class="fht-cell"></div></th>
													  <th style="text-align: center; vertical-align: middle; " data-field="ntcAuditContnt" tabindex="0">
													    <div class="th-inner ">审核意见</div><div class="fht-cell"></div></th>
													  <th style="text-align: center; " data-field="ntcCreateTime" tabindex="0">
													    <div class="th-inner ">审核时间</div><div class="fht-cell"></div></th>
													</tr>
												  </thead>
												  <tbody id="auditInfos"></tbody>
												</table>
											  </div>
										  </div>
	                                   </div>  
		                          </div>
							   </div>
							   <div class="form-group form-group-sm "></div>
							   <div class="modal-footer">
									<button type="button" onclick="emptyAll()" class="btn btn-default" data-dismiss="modal">关闭</button>
									<button id="saveBtn" type="button" onclick="save()" class="btn btn-primary">保存</button>
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

 
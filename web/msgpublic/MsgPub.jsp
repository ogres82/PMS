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
<title>账单信息</title> 
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="${ContextPath}/Hplus/plugins/upload/control/css/zyUpload.css" type="text/css"> 
<!-- 引用核心层插件 -->
<script src="${ContextPath}/Hplus/plugins/upload/core/zyFile.js"></script>
<!-- 引用控制层插件 -->
<script src="${ContextPath}/Hplus/plugins/upload/control/js/zyUploadMsgPub.js"></script>

<script type="text/javascript" src="${ContextPath}/msgpublic/MsgPub.js"></script>
<style>
  #moreSearch:hover{
  	cursor:pointer;
  }
  .input-sm, .form-horizontal .form-group-sm .form-control{
  	height:34px;
  }
  .folder-list li{
  	line-height:40px;
  }
  
  .folder-list li a{
  	float:left;
  	line-height:40px;
  	padding:0;
  	width:400px;
  	white-space:nowrap;
  	overflow:hidden;
  	-o-text-overflow:ellipsis;
  	text-overflow:ellipsis;
  }
  
  .delFile{
      cursor:pointer;margin-left:140px;padding:5px;border:1px solid #e5e6e7;border-radius:2px;
  }
  .delFile:hover{
  	  border:1px solid #66CDAA ;
  }
</style>
<script type="text/javascript">
function openWindow(type,id){
	
	ZYFILE.uploadFile = [];
	ZYFILE.lastUploadFile = [];          // 上一次选择的文件数组，方便继续上传使用
	ZYFILE.perUploadFile = [];      
	ZYFILE.fileInput = null;
	$(".contractFile").hide();
	//新增
	 if(type==1){
		uploadeFilesNum=0;
		$("#myModalLabel").html("添加信息");
		$("#auditInfos").html("");
		$("#ntcSubject").val("");
		$("#saveBtn").show();
		$(".uploadFile").show();
		$("#saveBtn").html("保存");
		$("#ntcAudit").attr("disabled","disabled");
		var loginUserName="${loginUser.username}";
        var loginUserCname="${loginUser.cname}";
        $("#ntcCreatorId").val(loginUserName);
        $("#ntcAudit").val("24");
        $("#ntcCreatorName").val(loginUserCname);
        var datetime=getNowFormatDate(true);
        $("#ntcCreateTime").val(datetime);
        $("#ntcStatus").val("20");
        var grapIframe="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=7501&numb="+generateMixed(4);
        $("#grapIframe").attr("src",grapIframe); 
        $('#myModal').modal();
		
	 }
	 //编辑
	 if(type==2){
		 $("#myModalLabel").html("编辑信息");
		 $(".contractFile").show();
		 $(".uploadFile").show();
		 $("#saveBtn").show();
	  	 $("#saveBtn").html("保存");
		 $("#ntcAudit").attr("disabled","disabled");
		 selections = getIdSelections();
		    var url="./../msgpublic/list.app?method=viewForAjax&noticeId="+id;
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
			            $(".summernote").code(list.ntcContent);
			            var action="./../msgpublic/list.app?method=update";
			            $("#myForm").attr("action",action);
			            var html="";
			            for(var i=0;i<list.msgandnoticeNoticeAuditinfos.length;i++){
			            	var passFlag="";
			            	if(list.msgandnoticeNoticeAuditinfos[i].ntcPassFlag==0){
			            		passFlag="驳回";
			            	}else{
			            		passFlag="通过";
			            	}
			            	html+="<tr data-index=\""+i+"\"><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditor+"</td>"+"<td style=\"text-align: center; \">"+passFlag+"</td>"+"<td style=\"text-align: center; vertical-align: middle;width:400px; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditContnt+"</td><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcCreateTime+"</td></tr>";
			            }
			            $("#auditInfos").html("");
			            $("#auditInfos").html(html);
			            $(".contractFile").show();
			            if(list.ntcStatus==11&&list.ntcCreatorId=="${loginUser.username}"){
			            	$('#myModal').modal();
			            	url="./../contract/contractInfo.app?method=searchContractFile&contractId="+list.ntcId;
		        			$.post(url,function(data){
		        				var list = eval(data);
		        				if(list!=null){
		        					var htmlStr = "<ul class='folder-list' style='padding: 0 20px'>";
		        					for(var i = 0;i<list.length;i++){
		        						htmlStr += "<li>"+
		        						"<a onclick='downloadFile(this)' fileId='"+list[i].fileId+"'>" +
		        						"<i class='fa fa-file'></i> "+list[i].fileName + "</a>" +
		        						"<span style='margin-left:20px'>上传时间："+getNowFormatDate(false,list[i].fileUploadTime)+"</span> "+
		        						"<span onclick='delFile(this)' fileId='"+list[i].fileId+"' class='delFile'><i class='fa fa-trash' style='color:red;font-size:15px;'></i>删除</span>"+
		        						"</li>";
		        					}
		        					htmlStr += "</ul>";
		        					$("#contractFile").html(htmlStr);
		        				}else{
		        					$(".contractFile").hide();
		        					$("#contractFile").html("");
		        				}
		        			});
			            }else{
			            	layer.alert("您不可编辑该条信息！",{
								skin: 'layui-layer-molv'
							});
			            }
				    });
		 
	 }
	 //查看
 	 if(type==3){
 		$("#myModalLabel").html("查看信息");
 		$(".contractFile").show();
 		$(".uploadFile").hide();
 		console.log($(".contractFile"));
 		selections = getIdSelections();
 		$("#saveBtn").hide();
		$("#ntcAudit").attr("disabled","disabled");
		    var url="./../msgpublic/list.app?method=viewForAjax&noticeId="+id;
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
			            	var passFlag="";
			            	if(list.msgandnoticeNoticeAuditinfos[i].ntcPassFlag==0){
			            		passFlag="驳回";
			            	}else{
			            		passFlag="通过";
			            	}
			            	html+="<tr data-index=\""+i+"\"><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditor+"</td>"+"<td style=\"text-align: center; \">"+passFlag+"</td>"+"<td style=\"text-align: center; vertical-align: middle;width:400px; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditContnt+"</td><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcCreateTime+"</td></tr>";
			            }
			            $("#auditInfos").html("");
			            $("#auditInfos").html(html);
			            $(".summernote").code(list.ntcContent);
			            var proInsId=list.processInstanceId;
				        var grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+proInsId+"&numb="+generateMixed(4);
				        $("#grapIframe").attr("src",grapIframe);
				        
				        var url1="./../contract/contractInfo.app?method=searchContractFile&contractId="+list.ntcId;
	        			$.post(url1,function(data){
	        				var list = eval(data);
	        				if(list!=null){
	        					
	        					var htmlStr = "<ul class='folder-list' style='padding: 0 20px;'>";
	        					for(var i = 0;i<list.length;i++){
	        						htmlStr += "<li style='white-space:nowrap'>"+
	        						"<a onclick='downloadFile(this)' fileId='"+list[i].fileId+"'>" +
	        						"<i class='fa fa-file'></i> "+list[i].fileName + "</a>" +
	        						"<span style='margin-left:20px'>上传时间："+getNowFormatDate(false,list[i].fileUploadTime)+"</span> "+
	        						"<span onclick='delFile(this)' fileId='"+list[i].fileId+"' class='delFile'><i class='fa fa-trash' style='color:red;font-size:15px;'></i>删除</span>"+
	        						"</li>";
	        					}
	        					htmlStr += "</ul>";
	        					$("#contractFile").html(htmlStr);
	        				}else{
	        					$(".contractFile").hide();
	        					$("#contractFile").html("");
	        				}
	        			});
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
			        var url="${ContextPath}"+"/msgpublic/list.app?method=deleteByAjax"
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
 		$("#myModalLabel").html("发布信息");
  		selections = getIdSelections();
  		$(".uploadFile").hide();
		$("#ntcAudit").attr("disabled","disabled");
  		$("#saveBtn").show();
  		$("#saveBtn").html("发布");
 		    var url="./../msgpublic/list.app?method=viewForAjax&noticeId="+id;
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
 			            $(".summernote").code(list.ntcContent);
 			            var proInsId=list.processInstanceId;
 				        var grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+proInsId+"&numb="+generateMixed(4);
 				        $("#grapIframe").attr("src",grapIframe);
 				        var html="";
			            for(var i=0;i<list.msgandnoticeNoticeAuditinfos.length;i++){
			            	var passFlag="";
			            	if(list.msgandnoticeNoticeAuditinfos[i].ntcPassFlag==0){
			            		passFlag="驳回";
			            	}else{
			            		passFlag="通过";
			            	}
			            	html+="<tr data-index=\""+i+"\"><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditor+"</td>"+"<td style=\"text-align: center; \">"+passFlag+"</td>"+"<td style=\"text-align: center; vertical-align: middle;width:400px; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcAuditContnt+"</td><td style=\"text-align: center; \">"+list.msgandnoticeNoticeAuditinfos[i].ntcCreateTime+"</td></tr>";
			            }
			            $("#auditInfos").html("");
			            $("#auditInfos").html(html);
 				       if(list.ntcStatus==21&&list.ntcCreatorId=="${loginUser.username}"){
 				    	  $('#myModal').modal();
			            	url="./../contract/contractInfo.app?method=searchContractFile&contractId="+list.ntcId;
		        			$.post(url,function(data){
		        				var list = eval(data);
		        				if(list!=null){
		        					var htmlStr = "<ul class='folder-list' style='padding: 0 20px'>";
		        					for(var i = 0;i<list.length;i++){
		        						htmlStr += "<li>"+
		        						"<a onclick='downloadFile(this)' fileId='"+list[i].fileId+"'>" +
		        						"<i class='fa fa-file'></i> "+list[i].fileName + "</a>" +
		        						"<span style='margin-left:20px'>上传时间："+getNowFormatDate(false,list[i].fileUploadTime)+"</span> "+
		        						"<span onclick='delFile(this)' fileId='"+list[i].fileId+"' class='delFile'><i class='fa fa-trash' style='color:red;font-size:15px;'></i>删除</span>"+
		        						"</li>";
		        					}
		        					htmlStr += "</ul>";
		        					$("#contractFile").html(htmlStr);
		        					$(".contractFile").show();
		        				}else{
		        					$(".contractFile").hide();
		        					$("#contractFile").html("");
		        				}
		        			});
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
						<div class="form-group">
							<label class="col-sm-1 control-label">创建时间止</label>
	
							<div class="col-sm-3">
								<input type="text" class="form-control"> 
							</div>
	
							<label class="col-sm-1 control-label">创建人</label>
	
							<div class="col-sm-3">
								<input type="text" class="form-control">
							</div>
							
							<label class="col-sm-1 control-label">审核人</label>
	
							<div class="col-sm-3">
								<input type="text" class="form-control">
							</div>					
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">发布人</label>
	
							<div class="col-sm-3">
								<input type="text" class="form-control"> 
							</div>
	
							<label class="col-sm-1 control-label">发布时间起</label>
	
							<div class="col-sm-3">
								<input type="text" class="form-control">
							</div>
							
							<label class="col-sm-1 control-label">发布时间止</label>
	
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
	                        <h5>信息公开<small></small></h5>
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
			  <div class="modal-dialog" style="width:1020px">
				<div class="modal-content">
				  <div class="modal-header" style="background:#18a689">
					<button type="button" onclick="emptyAll()" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel" style="color:white">添加消息公开</h4>
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
							   <div class="form-group form-group-sm contractFile">
									  <label class="col-sm-1 control-label" >附件内容</label>
									  <div class="col-sm-11" id ="contractFile">
									  
									  </div>
							   </div>
							   
							   <div class="form-group form-group-sm uploadFile">
								  <label class="col-sm-1 control-label" >上传附件</label>
								  <div class="col-sm-11">
									  <div id="demo" class="demo"></div>
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
													    <div class="th-inner ">审核操作</div><div class="fht-cell"></div></th>
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
							   <input class="form-control" id="contractId" name="contractId" type="hidden" />
							   <input class="form-control" id="fileIds" name="fileIds" type="hidden"/>
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

 
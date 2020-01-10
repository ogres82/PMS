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
<script type="text/javascript" src="${ContextPath}/msgandnotice/MessageTemp.js"></script>
<script type="text/javascript">
function openWindow(type,id){
	//新增
	 if(type==1){
		$("#myModalLabel").html("添加模板");
        var datetime=getNowFormatDate(true);
        $("#msgTempCtime").val(datetime);
        $("#btnSave").show();
        $('#myModal').modal();
	 }
	 //编辑
	 if(type==2){
		 $("#myModalLabel").html("编辑模板");
		 $("#btnSave").show();
		 selections = getIdSelections();
		    var url="${ContextPath}"+"/msgandnotice/msgtemp.app?method=viewForAjax&tempId="+id;
		    $.post(url,
		    	   function(data){
				   var list = eval(data);
				        $("#msgTempId").val(list.msgTempId);
			            $("#msgTempCtime").val(list.msgTempCtime);
			            $("#msgTempSubject").val(list.msgTempSubject); 
			            $(".summernote").code(list.msgTempContent);
			            var action="${ContextPath}"+"/msgandnotice/msgtemp.app?method=update"
			            $("#myForm").attr("action",action);
		    			$('#myModal').modal();
				    });
	 }
 	 if(type==3){
 		$("#btnSave").hide();
 		$("#myModalLabel").html("查看模板");
		    var url="${ContextPath}"+"/msgandnotice/msgtemp.app?method=viewForAjax&tempId="+id;
		    $.post(url,
		    	   function(data){
				   var list = eval(data);
				        $("#msgTempId").val(list.msgTempId);
			            $("#msgTempCtime").val(list.msgTempCtime);
			            $("#msgTempSubject").val(list.msgTempSubject); 
			            $(".summernote").code(list.msgTempContent);
			            var action="${ContextPath}"+"/msgandnotice/msgtemp.app?method=update"
			            $("#myForm").attr("action",action);
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
			        var url="${ContextPath}"+"/msgandnotice/msgtemp.app?method=deleteByAjax"
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
		 
 	 }
}
//关闭对话框的时候清空所有数据
function emptyAll(){
	$('.summernote').code("");
	$("#msgTempId").val("");
    $("#msgTempCtime").val("");
    $("#msgTempSubject").val(""); 
}
//保存 表单中的内容 
function save(){
	var flag = $('#myForm').validationEngine('validate');
	var url;
	var arr;
	if(flag){
		    var content=$('.summernote').code();
			content=content.replace(/<[^>]+>/g,"");//去掉所有的html标记
			var subject=$("#msgTempSubject").val();
			var msgTempId=$("#msgTempId").val();
			var msgTempCtime=$("#msgTempCtime").val();
			if(msgTempId!=''){
				 url="${ContextPath}"+"/msgandnotice/msgtemp.app?method=update";
				 arr  =
			     {
			         "msgTempContent" :content,
			         "msgTempSubject":subject,
			         "msgTempId":msgTempId
			     }
			}else{
			     url="${ContextPath}"+"/msgandnotice/msgtemp.app?method=save";
			     arr  =
			     {
			         "msgTempContent" :content,
			         "msgTempSubject":subject,
			         "msgTempCtime":msgTempCtime
			     }
			}
			if(content==""){
				layer.msg('请填写模板内容！',{
					time: 3000
				});	
			}else{
				$.post(url,
						arr,
					    function(data){
							layer.msg('操作成功',
							          {time:1000},
									  function(){
									    $("#chargeInfo").bootstrapTable('refresh');
									    $("#myModal").modal('hide');
										emptyAll();
									  })
							  });
				
			  }
		//$("#myModal").modal('hide');
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
		    		  <!-- <button id="btn1" onclick="openWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
		    		  <button id="btn2" onclick="openWindow(2)" class="btn btn-primary " type="button"><i class="fa fa-edit"></i> 编辑</button>
		    		  <button id="btn3" onclick="openWindow(4)" class="btn btn-primary " type="button"><i class="fa fa-trash-o"></i> 删除</button> -->
		    		
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
	                        <h5>消息模板<small></small></h5>
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
					<h4 class="modal-title" id="myModalLabel" style="color:white">添加模板</h4>
				  </div>
				  <div class="modal-body">
					<form id="myForm" class="form-horizontal" action="${ContextPath}/msgandnotice/msgtemp.app?method=save" method="post">
						  <div class="form-group form-group-sm ">
							  <label class="col-sm-1 control-label">模板标题</label>
							  <div class="col-sm-3">
							  	 <input type="hidden" id="msgTempId" name="msgTempId">
								 <input class="form-control validate[required]" name="msgTempSubject" id="msgTempSubject" type="text"  aria-required="true"/>
							  </div>
						   </div>
						   <div class="form-group form-group-sm ">
						      <label class="col-sm-1 control-label">模板内容</label>
							  <div class="col-sm-11">
							     <div class="ibox-tools"></div>
							     <div class="summernote"></div>
							     <input type="hidden" id="msgTempContent" name="msgTempContent">
                              </div>
						   </div>
						   
						   <div class="form-group form-group-sm ">
							  <label class="col-sm-1 control-label">创建时间</label>
							  <div class="col-sm-3">
								 <input class="form-control" name="msgTempCtime" id="msgTempCtime" type="text" readonly="readonly" required="required" aria-required="true"/>
							  </div>
						   </div>
						   <div class="form-group form-group-sm "></div>
						   <div class="modal-footer">
								<button type="button" onclick="emptyAll()" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button id="btnSave" type="button" onclick="save()" class="btn btn-primary">保存</button>
						   </div>
					</form>
	
				  </div>
				  
				</div>
			  </div>
			</div>
	</div>
  </body>
</html>
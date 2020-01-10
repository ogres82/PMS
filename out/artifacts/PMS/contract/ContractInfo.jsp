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
<title>合同管理</title> 
<%@ include file="/common/taglibs.jsp"%>

<!--  <link rel="stylesheet" type="text/css" href="${ContextPath}/Hplus/upload/css/default.css"> 
<link href="${ContextPath}/Hplus/upload/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />	
<script src="${ContextPath}/Hplus/upload/js/fileinput.js" type="text/javascript"></script>
<script src="${ContextPath}/Hplus/upload/js/fileinput_locale_zh.js" type="text/javascript"></script> -->

<link rel="stylesheet" href="${ContextPath}/Hplus/plugins/upload/control/css/zyUpload.css" type="text/css"> 
<!-- 引用核心层插件 -->
<script src="${ContextPath}/Hplus/plugins/upload/core/zyFile.js"></script>
<!-- 引用控制层插件 -->
<script src="${ContextPath}/Hplus/plugins/upload/control/js/zyUpload.js"></script>
<!-- 引用初始化JS -->
<script src="${ContextPath}/Hplus/plugins/upload/core/jq22.js"></script>

<script type="text/javascript" src="${ContextPath}/contract/ContractInfo.js"></script>

<script type="text/javascript">
 loginUserName="${loginUser.username}";
 loginUserCname="${loginUser.cname}";
</script>
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
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    
	    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
	    <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
		   		
		   	    </div>
			    <div class="col-md-6"  style="margin-top:10px;float:left">
					<button type="button" class="btn btn-primary pull-right" id="moreSearch">
					    更多查询 <span class="caret"></span>
					</button>
		   		</div>    		
		   	</div>
	   	</div>
	   	<i class="fa fa-search" style="position:fixed;right:122px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
	   	<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
   		
		   
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>合同信息<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="contractInfo"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	        </div>
      
		<!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:1020px">
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
											  <label class="col-sm-1 control-label">合同编号</label>
											  <div class="col-sm-3">
													<input class="form-control validate[required]" id="contractCode" type="text" placeholder="必填项"/>
											  </div>
											  <label class="col-sm-1 control-label">合同名称</label>
											  <div class="col-sm-3">
											  	<input class="form-control validate[required]" id="contractName" type="text" placeholder="必填项"/>
											  </div>	
											  <label class="col-sm-1 control-label">合同类型</label>
											  <div class="col-sm-3">
											  	<select class="form-control validate[required]" id="contractType"> 
													  <option value="">---请选择---</option>
												</select>  
											  </div>					  
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">合同简述</label>
											  <div class="col-sm-11">
													<textarea class="form-control validate[required]" style="height:65px" id="contractDetail" rows="3" cols="145" placeholder="必填项"></textarea>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label" >乙方名称</label>
											  <div class="col-sm-3">
													<input class="form-control validate[required]" id="contractPbName" type="text" placeholder="必填项"/>
											  </div>
											  <label class="col-sm-1 control-label" >乙方联系人</label>
											  <div class="col-sm-3">
											  		<input class="form-control validate[required]" id="contractPbLinker" type="text" placeholder="必填项"/>
											  </div>
											   <label class="col-sm-1 control-label" >乙方联系方式</label>
											  <div class="col-sm-3">
													<input class="form-control validate[required,custom[mobilephone]]" id="contractPbPhone" placeholder="必填项" type="text"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											 
											  <label class="col-sm-1 control-label">签订日期</label>
											  <div class="col-sm-3">
													<input class="form-control laydate-icon layer-date validate[required]" id="contractSignDate" type="text" placeholder="必填项"/>
											  </div>
											  <label class="col-sm-1 control-label">结束日期</label>
											  <div class="col-sm-3">
													<input class="form-control laydate-icon layer-date validate[required]" id="contractEndDate" type="text" placeholder="必填项"/>
											  </div>
											   <label class="col-sm-1 control-label">合同期限</label>
											  <div class="col-sm-3">
													<input class="form-control validate[custom[number]]" id="contractPeriod" type="text"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											 
											  <label class="col-sm-1 control-label" >合同金额(万)</label>
											  <div class="col-sm-3">
													<input class="form-control validate[custom[number]]" id="contractPrice" type="text"/>
											  </div>
											   <label class="col-sm-1 control-label" >合同状态</label>
											  <div class="col-sm-3">
													<select class="form-control validate[required]" id="contractStatus"> 
													  <option value="">---请选择---</option>
													</select>  
											  </div>
									   </div>
									  
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label" >备注</label>
											  <div class="col-sm-11">
													<input class="form-control" id="remark" type="text"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm contractFile">
											  <label class="col-sm-1 control-label" >合同文档</label>
											  <div class="col-sm-11" id ="contractFile">
											  
											  </div>
									   </div>
									   <div class="form-group form-group-sm " id="uploadFile">
											  <label class="col-sm-1 control-label" >上传附件</label>
											  <div class="col-sm-11">
											    	<div id="demo" class="demo"></div>
											  </div>
									   </div>
								  </fieldset>
			
							</form>
		          		</div>
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btContractAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
				    </div>
				    <input class="form-control" id="contractId" type="hidden" />
				    <input class="form-control" id="fileIds" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
    </div>
    <script>
    laydate.render({
        elem: '#contractSignDate',
        //format: 'YYYY年MM月DD日',
        festival: true, //显示节日
        choose: function(datas){ //选择日期完毕的回调
        	$('#myForm1').validationEngine('hide');
        }
    });
    laydate.render({
        elem: '#contractEndDate',
        //format: 'YYYY年MM月DD日',
        festival: true, //显示节日
        choose: function(datas){ //选择日期完毕的回调
        	$('#myForm1').validationEngine('hide');
        	var start = $("#contractSignDate").val();
        	var end = $("#contractEndDate").val();
        	
        	if(start>end && end!=""){
        		 layer.msg('结束日期不能小于签订日期！',{time: 2000});
        		 $("#contractEndDate").val("");
        	}
        }
    });
    </script>
</body>

</html>
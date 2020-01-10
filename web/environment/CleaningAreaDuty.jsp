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
<title>保洁计划</title> 
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/environment/CleaningAreaDuty.js"></script>
<script type="text/javascript">
 loginUserName="${loginUser.username}";
 loginUserCname="${loginUser.cname}";
</script>
<style>
  #moreSearch:hover{
  	cursor:pointer;
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
		   	</div>
	   	</div>
	   	<i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
	   	<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
   		
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>保洁计划<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="areaDutyInfo"></table>	
	                    	
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
											  <label class="col-sm-1 control-label">计划编码</label>
											  <div class="col-sm-5">
												<input class="form-control validate[required]" id="planCode" type="text" placeholder="必填项"/>
											  </div>
											  <label class="col-sm-1 control-label">开始日期</label>
											  <div class="col-sm-5">
											  	<input class="form-control laydate-icon layer-date validate[required]" id="cleanBeginDate" type="text" placeholder="必填项"/>
											  </div>	
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">结束日期</label>
											  <div class="col-sm-5">
											  	<input class="form-control laydate-icon layer-date validate[required]" id="cleanEndDate" type="text"/>
											  </div>	
											   <label class="col-sm-1 control-label">保洁类别</label>
											  <div class="col-sm-5">
											  	<select class="form-control validate[required]" id="cleaningType"> 
													  <option value="">---请选择---</option>
												</select>  
											  </div>	
									   </div>
									   
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label" >保洁区域</label>
											  <div class="col-sm-5">
													<input class="form-control" id="cleaningArea" type="text"/>
											  </div>
											   <label class="col-sm-1 control-label" >保洁内容</label>
											  <div class="col-sm-5">
													<input class="form-control" id="cleaningDetail" type="text"/>
											  </div>
									   </div>
									    <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label" >保洁责任人</label>
											  <div class="col-sm-5">
													<input class="form-control" id="cleaningPerson" type="text"/>
											  </div>
									   </div>
								  </fieldset>
			
							</form>
		          		</div>
	          		</div>
	          		</div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btAreaDutyAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
				    </div>
				    <input class="form-control" id="planId" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
    </div>
     <script>
    laydater.render({
        elem: '#cleanBeginDate',
        //format: 'YYYY年MM月DD日',
        festival: true, //显示节日
        choose: function(datas){ //选择日期完毕的回调
        	$('#myForm1').validationEngine('hide');
        }
    });
    laydate.render({
        elem: '#cleanEndDate',
        //format: 'YYYY年MM月DD日',
        festival: true, //显示节日
        choose: function(datas){ //选择日期完毕的回调
        	$('#myForm1').validationEngine('hide');
        	var start = $("#cleanBeginDate").val();
        	var end = $("#cleanEndDate").val();
        	
        	if(start>end && end!=""){
        		layer.msg('结束日期不能小于开始日期！',{time: 2000});
        		 $("#cleanEndDate").val("");
        	}
        }
    });
    </script>
</body>

</html>
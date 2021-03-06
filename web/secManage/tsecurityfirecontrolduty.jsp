<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser" %> 
<%@ page import="com.bstek.bdf2.core.context.ContextHolder" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
    DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
    request.setAttribute("loginUser",user);
    int size = user.getPositions().size();
    String positionIds = "";
    if(size>0){
    	for(int i = 0;i<size;i++){
    		if(i==0){
    			positionIds = user.getPositions().get(i).getId();
    		}else{
    			positionIds += " "+ user.getPositions().get(i).getId();
    		}
    		
    	}
    }
    request.setAttribute("positions",positionIds);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title> 
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/secManage/tsecurityfirecontrolduty.js"></script>

<script type="text/javascript">
 loginUserName="${loginUser.username}";
 loginUserCname="${loginUser.cname}";
 positions="${positions}";
</script>
<style>
  #moreSearch:hover{
  	cursor:pointer;
  }
  .fixed-table-body{
		overflow:inherit;
	}
</style>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    
	    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
	    <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
		   			 <!-- <button id="btn1" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;交班</button>
		   			 <button id="btn2" onclick="openButtonWindow(2)" class="btn btn-primary " disabled type="button"><i class="fa fa-edit"></i> 编辑</button>
		   			 <button id="btn3" onclick="openButtonWindow(3)" class="btn btn-primary " disabled type="button"><i class="fa fa-eye"></i> 查看</button>
		    		 <button id="btn5" onclick="openButtonWindow(5)" class="btn btn-primary " disabled type="button"><i class="fa fa-trash-o"></i> 接班</button>
		   	    	 <button id="btn6" onclick="openButtonWindow(6)" class="btn btn-primary " disabled type="button"><i class="fa fa-trash-o"></i> 审阅</button>
		    		 <button id="btn4" onclick="openButtonWindow(4)" class="btn btn-primary " disabled type="button"><i class="fa fa-trash-o"></i> 删除</button> -->
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
	                        <h5>交接班信息<small></small></h5>
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
							<table class="table-no-bordered" id="empInfo"></table>	
	                    	
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
											  <label class="col-sm-1 control-label">记录编码</label>
											  <div class="col-sm-5">
											        <input type="hidden" id="recId" />
													<input class="form-control validate[required]" id="recCode" type="text" readonly="readonly" placeholder="必填项"/>
											  </div>
											  <label class="col-sm-1 control-label">日期</label>
											  <div class="col-sm-5">
											  	<input class="form-control laydate-icon layer-date validate[required]" id="shiftDate" type="text"  placeholder="必填项"/>
											  </div>	
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">交班人</label>
											  <div class="col-sm-5">
											     <select class="form-control validate[required]" id="shiftPasserId" disabled="disabled"> 
														  <option value="">---请选择---</option>
												  </select>
											  </div>
											  <label class="col-sm-1 control-label">班次</label>
											  <div class="col-sm-5">
											   	<select class="form-control validate[required]" id="shiftType"> 
													  <option value="">---请选择---</option>
													  <option value="0">早班</option>
													  <option value="1">晚班</option>
												</select>  
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
									      <label class="col-sm-1 control-label" >交接班标识</label>
										  <div class="col-sm-5">
											  <select class="form-control validate[required]" id="shiftMarker" disabled="disabled"> 
													  <option value="">---请选择---</option>
													  <option value="0">未接班-</option>
													  <option value="1">已接班</option>
											  </select>
										  </div>
									   </div>
									   <div id="jiebancase" class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label" >接班记录</label>
											  <div class="col-sm-6">
											      <textarea class="form-control validate[required] " rows="6" id="shiftPassCase" style="height:60px"></textarea>
												  <!-- <input class="form-control  validate[required,custom[mobilephone]] " id="shiftPassCase" type="text" placeholder="必填项"/> -->
											  </div>
											  <label class="col-sm-1 control-label" >接班时间</label>
											  <div class="col-sm-3">
													<input class="form-control laydate-icon layer-date validate[required]" id="shiftTakeTime" type="text"  placeholder="必填项"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm">
											  
											  <label class="col-sm-1 control-label">接班人</label>
											  <div class="col-sm-5">
													<select class="form-control validate[required]" id="shiftTakerId" >
													   <option value="">---请选择---</option>
													</select>
													
											  </div>
											  <label class="col-sm-1 control-label">审阅人</label>
											  <div class="col-sm-5">
											        <input type="text" class="form-control" id="shiftCheckerId"> 
											  </div>
									   </div>
									   <div class="form-group form-group-sm" id="shenyuediv">
									       <label class="col-sm-1 control-label" >审阅标识</label>
										   <div class="col-sm-5">
												<select class="form-control validate[required]" id="shiftCheckMarker"  disabled="disabled" >
											         <option value="">---请选择---</option>
												     <option value="0">未审阅-</option>
												     <option value="1">已审阅</option>
												 </select>
										   </div>
										   <label class="col-sm-1 control-label" >审阅时间</label>
										   <div class="col-sm-5">
											   <input type="text" class="form-control laydate-icon layer-date validate[required]" id="shiftCheckTime" placeholder="必填项"/> 
										   </div>
									   </div>
								  </fieldset>
			
								  	   <div class="form-group form-group-sm "></div>
									   <div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
											<button id="btEmpAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
									   </div>
									   <input class="form-control" id="empId" type="hidden" />
							</form>
		          		</div>
	          		</div>
	          		</div>
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
    </div>
    
    <script>
	    laydate.render({
	        elem: '#shiftDate',
	        event: 'click',
	        //format: 'YYYY年MM月DD日',
	        festival: true, //显示节日
	        type:'datetime',
	        choose: function(datas){ //选择日期完毕的回调
	        	$('#myForm1').validationEngine('hide');
	        }
	    });
	    laydate.render({
	        elem: '#shiftTakeTime',
	        event: 'click',
	        //format: 'YYYY年MM月DD日',
	        festival: true, //显示节日
	        type:'datetime',
	        choose: function(datas){ //选择日期完毕的回调
	        	$('#myForm1').validationEngine('hide');
	        }
	    });
	    laydate.render({
	        elem: '#shiftCheckTime',
	        event: 'click',
	        //format: 'YYYY年MM月DD日',
	        festival: true, //显示节日
	        type:'datetime',
	        choose: function(datas){ //选择日期完毕的回调
	        	$('#myForm1').validationEngine('hide');
	        }
	    });
    </script>
</body>

</html>
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
<title>派工信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/assignwork/DispatchWorkList.js">
</script>
<style>
	.fixed-table-body{
		overflow:inherit;
	}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	
	      <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
<!-- 		   		 <button id="btn1" onclick="openDispatchButton(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;派工</button> -->
<!-- 	    		  <button id="btn3" onclick="openDispatchButton(3)" class="btn btn-primary " disabled type="button"><i class="fa fa-eye"></i> 查看</button> -->
<!-- 	    		  <button id="btn4" onclick="openDispatchButton(4)" class="btn btn-primary " disabled type="button"><i class="fa fa-trash-o"></i> 删除</button> -->
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
	      
	      <!-- 更多查询区域  -->
	    	<div class ="row ibox float-e-margins" style="display:none;text-align:center;margin-left:auto;margin-right:auto;" id="more_search">
				<div class="ibox-content col-sm-12 b-r">
					<form class="form-horizontal">
								   
						<div class="form-group" style="margin-top:20px;">
							<label class="col-sm-1 control-label">投诉人</label>
	
							<div class="col-sm-3">
								<input type="text"  class="form-control"> 
							</div>
	
							<label class="col-sm-1 control-label">地址</label>
	
							<div class="col-sm-3">
								<input type="text"  class="form-control">
							</div>
							<label class="col-sm-1 control-label">受理人</label>
	
							<div class="col-sm-3">
								<input type="text" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">投诉级别</label>	
							<div class="col-sm-3">
								<input type="text" class="form-control"> 
							</div>
							<label class="col-sm-1 control-label">处理状态</label>	
							<div class="col-sm-3">
								<input type="text" class="form-control"> 
							</div>	
							<label class="col-sm-1 control-label">处理人</label>	
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
	    	<!-- 更多查询结束 -->
	      
	      
	    	
	    	<!-- 按钮结束 -->
	    	
	    <!-- 数据表格区域 -->
		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>报障派工和处理信息<small></small></h5>
	                        
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id=dispatchDataInfo></table>	
	                    </div>
	                </div>
	            </div>
	        </div>
	      <!-- 数据区域结束 -->
	      
	      	        <!-- 弹出窗口区域，触发事件后弹出  -->
	      <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width:1020px">
				<div class="modal-content">
					<div class="modal-header" style="background:#18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel" style="color:white">报修派工单信息</h4>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs">
			              	<li class="active"><a data-toggle="tab" href="#tab-1">基本信息</a>
			              	</li>
			              	<li class=""><a data-toggle="tab" href="#tab-2">流程图</a>
			              	</li>
		          		</ul>
					
						<div class="tab-content">
		          			<div id="tab-1" class="tab-pane active">
		          			
		          				<form id="myForm2" class="form-horizontal" role="form">
			          				<fieldset>
			          					<legend style="font-size:15px"></legend>
										<div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">派工单号</label>
											  <div class="col-sm-3">
													<input class="form-control" id="dispatch_id" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">事件单号</label>
											  <div class="col-sm-3">
													<input class="form-control" id="mtn_id" type="text"  readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">							      
											  <label class="col-sm-1 control-label">报事人</label>
											  <div class="col-sm-3">
													<input class="form-control" id="rpt_name" type="text"  readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">呼入电话</label>
											  <div class="col-sm-3">
													<input class="form-control" id="in_call" type="text"  readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">地址</label>
											  <div class="col-sm-3">
													<input type="text" style="" class="form-control" id="addres" readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">受理人</label>
											  <div class="col-sm-3">
													<input class="form-control" id="createby" type="text"  readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">受理时间</label>
											  <div class="col-sm-3">
													<input class="form-control" id="createTime" type="text"  readonly="readonly"/>
											  </div>
											 
									   </div>
			
									   <div class="form-group form-group-sm ">
										  <label class="col-sm-1 control-label" >报修描述</label>
										  <div class="col-sm-11">
											 <textarea class="form-control" rows="3" style="height:60px" id="mtn_detail"  readonly="readonly"></textarea>
										  </div>
									   </div>
									   <div class="form-group form-group-sm ">
										  <label class="col-sm-1 control-label" >紧急程度</label>
										  <div class="col-sm-3">
											  <select class="form-control" id="mtn_emergency"> 
												  <option value="">--请选择--</option>
											  </select>  
										  </div>
										  <label class="col-sm-1 control-label" >派工状态</label>
										  <div class="col-sm-3">
											 <select class="form-control" id="dispatch_status" disabled="disabled"> 
													  <option value="">--请选择--</option>
											 </select>  
										  </div>
										   <label class="col-sm-1 control-label">派工时间</label>
											  <div class="col-sm-3">
													<input class="form-control" id="dispatch_time" type="text" disabled="disabled" readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">处理人</label>
											  <div class="col-sm-3">
													<div class="input-group">
													   <input type="text" class="form-control validate[required]" id="dispatch_handle_id"  style="display:none" />
													   <input type="text" class="form-control validate[required]" id="deptName"  style="display:none">
													   <input type="text" class="form-control validate[required]" placeholder="请输入部门名称" id="dispatch_handle_username" >
													   <span class="input-group-btn">
															  <button class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="height:30px;" type="button">
																 <span class="caret"></span>
															  </button>
															  <ul class="dropdown-menu dropdown-menu-right" role="menu">
															  </ul>
													   </span>
													</div>
											  </div>
											 
									   </div>
									    <div class="form-group form-group-sm ">
							  <label class="col-sm-1 control-label">附加处理人</label>
								<div class="col-sm-11">
									<div class="input-group">
										 <input type="text" class="form-control" id="handerNames" name="handerNames" readonly>
										 <input type="hidden" class="form-control" id="handerIds" name="handerIds">
										 <span class="input-group-btn">
											 <button class="btn btn-default btn-sm" id='fjbtn' type="button" onclick="func1();">
												 <i class="fa fa-search"></i>
											 </button>
										 </span>
									</div>
								 </div>
						   </div>
									   <div class="form-group form-group-sm " id="weixiu">
											  <label class="col-sm-1 control-label">维修类别</label>
											  <div class="col-sm-3">
													<select class="form-control validate[required]" id="mtn_type" onchange="changeMtnPriority()"> 
													  <option value=""></option>
												  </select>  
											  </div>
											  <div id="div_mtn_priority">
												  <label class="col-sm-1 control-label">维修意见</label>
												  <div class="col-sm-3">
														<select class="form-control layer-date validate[required]" id="mtn_priority"> 
														  <option value="">--请选择--</option>
													  </select>  
												  </div>
											  </div>
											  
											   <label class="col-sm-1 control-label">到达时间</label>
											  <div class="col-sm-3">
													<input class="form-control laydate-icon layer-date validate[required]"  id="dispatch_arrive_time" type="text" "/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm " id="weixucl">
											  <label class="col-sm-1 control-label">材料费(元)</label>
											  <div class="col-sm-3">
													<input class="form-control validate[custom[number]]" id="material_cost" type="text"  placeholder="输入材料费" onblur="this.value=outputmoney(this.value);"/>
											  </div>
											   <label class="col-sm-1 control-label ">人工费(元)</label>
											  <div class="col-sm-3">
													<input class="form-control validate[custom[number]]" id="labor_cost" type="text" placeholder="输入人工费" onblur="this.value=outputmoney(this.value);"/>
											  </div>
											  
											  <label class="col-sm-1 control-label">总额(元)</label>
											  <div class="col-sm-3">
													<input class="form-control validate[custom[number]]" id="dispatch_expense" type="text" onblur="this.value=outputmoney(this.value);"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm " id="weixiujg">
										  <label class="col-sm-1 control-label" >维修结果</label>
										  <div class="col-sm-11">
											 <textarea class="form-control" style="height:60px" rows="3" id="dispatch_result"></textarea>
										  </div>
									   </div>
									   <div class="form-group form-group-sm " id="xiaodan">
										  <label class="col-sm-1 control-label">销单时间</label>
										  <div class="col-sm-3">
												<input class="form-control" id="dispatch_finish_time" type="text" readonly="readonly"/>
										  </div>
									   </div>
									   <div class="form-group form-group-sm "></div>
									   <div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal" onclick="refushtable();">关闭</button>
											<button id="dispbtn5" onclick="openSaveButton(5)" class="btn btn-primary " type="button"> 派工</button>
											<button id="dispbtn6" onclick="openSaveButton(6)" class="btn btn-primary " type="button"> 现场确认</button>
											<button id="dispbtn7" onclick="openSaveButton(7)" class="btn btn-primary " type="button"> 销单</button>
											<button id="dispbtn8" onclick="openSaveButton(8)" class="btn btn-primary " type="button"> 业主取消</button>
											<button id="dispbtn9" onclick="openEditButton(9)" class="btn btn-primary " type="button"> 保存</button>
									   </div>
									</fieldset>
								</form>
		          			</div>
		          			<div id="tab-2" class="tab-pane" >
				          		<div style="height: 400px">
				          			<iframe src="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=7501" frameborder="0" marginheight="0" marginwidth="0" height="100%" width="100%" id="grapIframe"></iframe>
				          		</div>
	          			</div>
		          		</div>
		          		
					</div>
				</div>
			</div>
		</div>
		<!-- 派工结束 --> 

</body>
</html>
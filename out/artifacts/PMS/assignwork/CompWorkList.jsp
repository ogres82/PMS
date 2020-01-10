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
<title>投诉信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/assignwork/CompWorkList.js">
</script>
<style>
	.fixed-table-body{
		overflow:inherit;
	}
</style>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated">
	      
	      
	       <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
<!-- 		   		  <button id="btn1" onclick="openCompButton(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;派工</button> -->
<!-- 	    		  <button id="btn3" onclick="openCompButton(3)" class="btn btn-primary " disabled type="button"><i class="fa fa-eye"></i> 查看</button> -->
<!-- 	    		  <button id="btn4" onclick="openCompButton(4)" class="btn btn-primary " disabled type="button"><i class="fa fa-trash-o"></i> 删除</button> -->
<!-- 	    		 <button id="btn5"  onclick="openCompButton(8)" class="btn btn-primary " disabled type="button"><i class="fa fa-trash-o"></i> 处理</button> -->
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
	    	
	    	<!-- 数据表格区域  -->
		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>投诉派工和处理信息<small></small></h5>
	                        
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id=compDataInfo></table>	
	                    </div>
	                </div>
	            </div>
	        </div>
	        <!-- 数据加载结束 -->
	        
	        <!-- 弹出窗口区域，触发事件后弹出  -->
	      <div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width:1020px">
				<div class="modal-content">
					<div class="modal-header" style="background:#18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel" style="color:white">投诉派工单信息</h4>
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
													<input class="form-control" id="comp_id" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">事件单号</label>
											  <div class="col-sm-3">
													<input class="form-control" id="mtn_id" type="text" readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">投诉人</label>
											  <div class="col-sm-3">
													<input class="form-control" id="rpt_name" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">呼入电话</label>
											  <div class="col-sm-3">
													<input class="form-control" id="in_call" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">地址</label>
											  <div class="col-sm-3">
													<input class="form-control" id="addres" type="text" readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label">受理人</label>
											  <div class="col-sm-3">
													<input class="form-control" id="createby" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">受理时间</label>
											  <div class="col-sm-3">
													<input class="form-control" id="createTime" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">派工时间</label>
											  <div class="col-sm-3">
													<input class="form-control laydate-icon layer-date" id="comp_createTime" type="text" readonly="readonly"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
										  <label class="col-sm-1 control-label" >投诉情况</label>
										  <div class="col-sm-11">
											 <textarea class="form-control" rows="3" style="height:60px" id="comp_detail"></textarea>
										  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label" >投诉级别</label>
											  <div class="col-sm-3">
												  <select class="form-control" id="comp_emergency" disabled="disabled">
													  <option>--请选择--</option>
												  </select>  
											  </div>
											  <label class="col-sm-1 control-label" >派工状态</label>
											  <div class="col-sm-3">
												  <select class="form-control" id="comp_status" disabled="disabled">
													  <option>--请选择--</option>
												  </select>
											  </div>
											  <label class="col-sm-1 control-label" id="opernameLabe">处理人</label>
											  <div class="col-sm-3" id="opernameDiv">
													<div class="input-group">
													   <input type="text" class="form-control" id="comp_operator_id" style="display:none">
													   <input type="text" class="form-control validate[required]" placeholder="请输入部门名称" id="comp_operator_username" >													   
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
									   <div class="form-group form-group-sm " style="display:none">
											  <label class="col-sm-1 control-label">处理过程</label>
											  <div class="col-sm-11">
													<input class="form-control" id="comp_process" type="text" style="height: 60px"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm " id="custmReply">
										  <label class="col-sm-1 control-label">客服回复</label>
										  <div class="col-sm-11">
											 <textarea class="form-control validate[required]" rows="3" id="comp_reply"  style="height: 60px"></textarea>
										  </div>
									   </div>					
									   <div class="form-group form-group-sm " id="myresult">
										  <label class="col-sm-1 control-label" >处理结果</label>
										  <div class="col-sm-11">
											 <textarea class="form-control validate[required]" rows="3" id="comp_result" style="height: 60px"></textarea>
										  </div>
									   </div>
									   <div class="form-group form-group-sm " id="handleResult">
										  <label class="col-sm-1 control-label">是否解决</label>
										  <div class="col-sm-3">
												  <select class="form-control  validate[required]" id="comp_solve"> 
													  <option>--请选择--</option>
												  </select>  
										  </div>
										  <label class="col-sm-1 control-label">满意度</label>
										  <div class="col-sm-3">
												  <select class="form-control  validate[required]" id="comp_degree"> 
													  <option>--请选择--</option>
												  </select>  
										  </div>
										  <label class="col-sm-1 control-label">销单时间</label>
										  <div class="col-sm-3">
												<input class="form-control" id="comp_finish_time" type="text" readonly="readonly"/>
										  </div>
									   </div>
									   <div class="form-group form-group-sm "></div>
									   <div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
											<button id="compBtn5" onclick="openSaveButton(5)" class="btn btn-primary " type="button">派工</button>
				    		  				<button id="compBtn6" onclick="openSaveButton(6)" class="btn btn-primary " type="button">投诉处理</button>
											<button id="compBtn7" onclick="openSaveButton(7)" class="btn btn-primary " type="button">销单</button>
											<button id="btAssigenHandle" onclick="openCompHandleButton()" class="btn btn-primary " type="button" >回复</button>
											
									   </div>
								   </fieldset>
								</form>
							
							</div>
							<div id="tab-2" class="tab-pane" >
				          		<div style="height: 400px">
				          			<iframe src="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=217541" frameborder="0" marginheight="0" marginwidth="0" height="100%" width="100%" id="grapIframe"></iframe>
				          		</div>
		          			</div>
						</div>
					</div>
				</div>
			</div>
		</div>
    	<!-- 弹出结束窗口 -->
	    
    </div>


    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
    <!-- <label id="doradoView" style="display: " /> -->
</body>
</html>
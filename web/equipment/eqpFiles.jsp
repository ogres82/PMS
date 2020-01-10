<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设备档案</title> 
<%@ include file="/common/taglibs.jsp"%>
<link href="${ContextPath}/Hplus/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<script src="${ContextPath}/Hplus/js/plugins/staps/jquery.steps.min.js"></script>
<script type="text/javascript" src="${ContextPath}/equipment/eqpFiles.js"></script>
<style>
	.fixed-table-body{
		overflow:inherit;
	}
</style>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    
	    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div style="width:100%;height:45px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row" style="">
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
<!-- 		   			 <button id="btn_add" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i> 新增设备</button> -->
<!-- 		    		 <button id="btn_del" onclick="openButtonWindow(3)" class="btn btn-primary " type="button"><i class="fa fa-trash-o"></i> 删除设备</button> -->
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
	                        <h5>设备信息<small></small></h5>
	                        
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="eqpFiles"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	        </div>
	     
	   
		<!-- 弹出modal  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:900px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalLabel" style="color:white">设备档案</h4>
		 </div>
 		 <div class="modal-body">
 		 	
			 		<ul class="nav nav-tabs">
		              	<li id="t-1" class="active"><a id="a1">基本信息</a>
		              	</li>
		              	<li id="t-2" class=""><a id="a2">使用情况</a>
		              	</li>
		              	<li id="t-3" class=""><a id="a3">维保单位</a>
		              	<!--  <li class=""><a data-toggle="tab" href="#tab-3">维保单位</a>-->
		              	</li>
	          		</ul>
	          		
		          		<div class="tab-content">
			          		<div id="tab-1" class="tab-pane active">
			          			<form id="myForm1" class="form-horizontal" role="form">
									<fieldset id="basicInfo">
									<legend style="font-size:15px;border:0;"></legend>
										   <div class="form-group form-group-sm " style="display:none">
												  <div class="col-sm-4">
												  	<input class="form-control" id="eqp_id" type="text"/>
												  </div>	
										   </div>
										   <div class="form-group form-group-sm ">
												  <label class="col-sm-2 control-label">楼盘</label>
												  <div class="col-sm-4">
														<input class="form-control" id="eqp_belong_area" type="text"  readonly="readonly"/>
												  </div>
												  <label class="col-sm-2 control-label">安装地点</label>
												  <div class="col-sm-4">
												  	<input class="form-control" id="eqp_address" type="text"/>
												  </div>	
										   </div>
										   <div class="form-group form-group-sm ">
												  <label class="col-sm-2 control-label">设备名称</label>
												  <div class="col-sm-4">
														<input class="form-control validate[required]" id="eqp_name" type="text" placeholder="必填项"/>
												  </div>
												  <label class="col-sm-2 control-label">设备类型</label>
												  <div class="col-sm-4">
													  <select class="form-control" id="eqp_type"> 
													  	    <option value="">---请选择---</option>
												      </select>
											      </div>
										   </div>
										   <div class="form-group form-group-sm ">
												  <label class="col-sm-2 control-label">规格型号</label>
												  <div class="col-sm-4">
														<input class="form-control" id="eqp_model" type="text"/>
												  </div>
												  <label class="col-sm-2 control-label">设备状态</label>
												  <div class="col-sm-4">
													  <select class="form-control" id="eqp_status"> 
													  	    <option value="">---请选择---</option>
												      </select>
											      </div>
										   </div>
										   <div class="form-group form-group-sm ">
												  <label class="col-sm-2 control-label">保养周期</label>
												  <div class="col-sm-4">
													  <select class="form-control validate[required]" id="eqp_maintain_cycle"> 
													  	    <option value="">---请选择---</option>
												      </select>
											      </div>
										   </div>
									</fieldset>
								</form>
							</div>
							
							
							<div id="tab-2" class="tab-pane" >
								<form id="myForm2" class="form-horizontal" role="form">
									<fieldset id="usedSitu">
										<legend style="font-size:15px;border:0;"></legend>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">安装日期</label>
													  <div class="col-sm-4">
															<input class="form-control laydate-icon layer-date validate[required]" placeholder="必填项" id="eqp_install_date" type="text" onclick="laydate.render({elem:this});"/>
													  </div>
													  <label class="col-sm-2 control-label">报废日期</label>
													  <div class="col-sm-4">
													  		<input class="form-control laydate-icon layer-date" id="eqp_destroy_date" type="text" onclick="laydate.render({elem:this});"/>
													  </div>	
											   </div>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">使用年限</label>
													  <div class="col-sm-4">
															<input class="form-control validate[custom[integer]]" id="eqp_lifetime" type="text"/>
													  </div>
													  <label class="col-sm-2 control-label">已用月数</label>
													  <div class="col-sm-4">
													  	<input class="form-control validate[custom[integer]]" id="eqp_used_months" type="text"/>
													  </div>	
											   </div>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">维修次数</label>
													  <div class="col-sm-4">
															<input class="form-control validate[custom[integer]]" id="eqp_fixed_times" type="text"/>
													  </div>
													  <label class="col-sm-2 control-label">保养次数</label>
													  <div class="col-sm-4">
													  	<input class="form-control validate[custom[integer]]" id="eqp_maintain_times" type="text"/>
													  </div>	
											   </div>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">最近保养日期</label>
													  <div class="col-sm-4">
															<input class="form-control laydate-icon layer-date" id="eqp_maintain_last" type="text"  disabled="disabled"/>
													  </div>
													  <label class="col-sm-2 control-label">下次保养日期</label>
													  <div class="col-sm-4">
													  	<input class="form-control laydate-icon layer-date" id="eqp_maintain_next" type="text" disabled="disabled"/>
													  </div>	
											   </div>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">设备原值（元）</label>
													  <div class="col-sm-4">
															<input class="form-control validate[required,custom[number]]" id="eqp_origin_value" type="text" placeholder="必填项"/>
													  </div>
													  <label class="col-sm-2 control-label">净残值率（%）</label>
													  <div class="col-sm-4">
													  	<input class="form-control validate[required,custom[number]" id="eqp_value_rate" type="text" placeholder="必填项"/>
													  </div>
											   </div>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">设备净值（元）</label>
													  <div class="col-sm-4">
													  	<input class="form-control" id="eqp_current_value" type="text" readonly="readonly"/>
													  </div>	
											   </div>
										  </fieldset>
								</form>
	          				</div>
	          				
	          				
	          				<div id="tab-3" class="tab-pane" >
		          				<form id="myForm3" class="form-horizontal" role="form">
									<fieldset id="mtnUnit">
										<legend style="font-size:15px;border:0;"></legend>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">维保单位</label>
													  <div class="col-sm-4">
															<input class="form-control" id="eqp_maintain_unit" type="text"/>
													  </div>
													  <label class="col-sm-2 control-label">维保单位电话</label>
													  <div class="col-sm-4">
													  	<input class="form-control" id="eqp_maintain_phone" type="text"/>
													  </div>	
											   </div>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">维保单位地址</label>
													  <div class="col-sm-4">
															<input class="form-control" id="eqp_maintain_address" type="text"/>
													  </div>
													  <label class="col-sm-2 control-label">设备维保人</label>
													  <div class="col-sm-4">
													  	<input class="form-control" id="eqp_maintain_person" type="text"/>
													  </div>	
											   </div>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">生成厂家</label>
													  <div class="col-sm-4">
															<input class="form-control" id="eqp_manu" type="text"/>
													  </div>
													  <label class="col-sm-2 control-label">厂家地址</label>
													  <div class="col-sm-4">
													  	<input class="form-control" id="eqp_manu_address" type="text"/>
													  </div>	
											   </div>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">厂家电话</label>
													  <div class="col-sm-4">
															<input class="form-control" id="eqp_manu_phone" type="text"/>
													  </div>
													  <label class="col-sm-2 control-label">登记日期</label>
													  <div class="col-sm-4">
													  	<input class="form-control laydate-icon layer-date" id="eqp_register_date" type="text" onclick="laydate.render({elem:this,type:'datetime'});"/>
													  </div>	
											   </div>
											   <div class="form-group form-group-sm ">
													  <label class="col-sm-2 control-label">备注</label>
													  <div class="col-sm-10">
													  	<input class="form-control" id="eqp_remark" type="text"/>
													  </div>	
											   </div>
									</fieldset>
								</form>
	          				</div>
	          				 	   
						</div>
					
	          	</div>
	          	<div class="form-group form-group-sm "></div>
			    <div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="lastStep" onclick="formSwitch(0)" disabled="disabled">上一步</button>
					<button type="button" class="btn btn-primary" id="nextStep" onclick="formSwitch(1)">下一步</button>
					<button id="eqpSave" onclick="openSaveButton()" class="btn btn-primary " type="button" disabled="disabled">保存</button>
			    </div>
			    <input class="form-control" id="empId" type="hidden" />
		 	</div>		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->

    	</div>
    </div>
  </div>
</body>

</html>
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
<title>账单信息</title> 
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/ChargeManager/ChargeInfo.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    	<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
	    <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
			    </div>
			    		
		   	</div>
	   	</div>
	   	<i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
	   	<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->   	
	   
		
		<div class="row">
   			<div class="col-sm-12">
   				<blockquote class="layui-elem-quote" style="background-color:white">
   					<div class="row">
   						<div class="col-sm-10">
   							<div class="row">
   								<div class="col-sm-3">
									<select class="form-control" name="type" id="charge_type_name_select">
										  <option disabled selected style='display:none;' value="">收费项名称</option>
								  	</select>
							  	</div>
							  	<div class="col-sm-3">
									<select class="form-control" name="type" id="paid_mode_select">
										  <option disabled selected style='display:none;' value="">收款方式</option>
								  	</select>
							  	</div>
								<div class="col-sm-3">
									<input type="text" name="beginTime" id="beginTime" class="form-control layer-date" onclick="laydate.render({elem:this});" placeholder="开始时间" >
								</div>
								<div class="col-sm-3">
									<input type="text" name="endTime" id="endTime" class="form-control layer-date" onclick="laydate.render({elem:this});" placeholder="结束时间">
								</div>
   							</div>
						 </div>
						 <div class="col-sm-2" style="float:right">
						 	<button id="buttonSearch" class="btn btn-primary" type="button">查询</button>
						  	<button id="clearSearch" onclick="clearSearch()" class="btn btn-primary" type="button">清空</button>
						 </div>
					 </div>
   				</blockquote>
   			</div>
   		</div>
		   
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>账单信息<small></small></h5>
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
		  <div class="modal-dialog" style="width:800px">
			<div class="modal-content">
			  <div class="modal-header" style="background:#18a689">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel" style="color:white">账单信息</h4>
			  </div>
			  <div class="modal-body">
				<form id="myForm" class="form-horizontal" action="" method="post">						
					   <div class="form-group form-group-sm ">
						  <label class="col-sm-2 control-label">账单编号</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="charge_no" id="charge_no" type="text" required="" aria-required="true"/>
						  </div>
						  <label class="col-sm-2 control-label">房间编号</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="room_no" id="room_no" type="text" required="" aria-required="true"/>
						  </div>
					   </div>
					   <div class="form-group form-group-sm ">
						  <label class="col-sm-2 control-label">业主</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="owner_name" id="owner_name" type="text" /> 
						  </div>
						  <label class="col-sm-2 control-label" >收费项编号</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="charge_type_no" id="charge_type_no" type="text" />
						  </div>
					   </div>
					   <div class="form-group form-group-sm ">
						  <label class="col-sm-2 control-label" >收费项名称</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="charge_type_name" id="charge_type_name" type="text" />
						  </div>
						  <label class="col-sm-2 control-label" >单价</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="price" id="price" type="text" />
						  </div>
					   </div>
					   <div class="form-group form-group-sm ">
						  <label class="col-sm-2 control-label" >数量</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="count" id="count" type="text" />
						  </div>
						  <label class="col-sm-2 control-label" >倍率</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="rate" id="rate" type="text" />
						  </div>
					   </div>
					   <div class="form-group form-group-sm ">
						  <label class="col-sm-2 control-label" >开始日期</label>
						  <div class="col-sm-4">
							 <input class="form-control laydate-icon layer-date" name="begin_time" id="begin_time" onclick="laydate.render({elem:this,type:'datetime'});" type="text" />
						  </div>
						  <label class="col-sm-2 control-label" >结束日期</label>
						  <div class="col-sm-4">
							  <input class="form-control laydate-icon layer-date" name="end_time" id="end_time" onclick="laydate.render({elem:this,type:'datetime'});" type="text" />
						  </div>
					   </div>
					   <div class="form-group form-group-sm ">
						  <label class="col-sm-2 control-label" >应收金额</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="receive_amount" id="receive_amount" type="text" />
						  </div>
						  <label class="col-sm-2 control-label" >实收金额</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="paid_amount" id="paid_amount" type="text" />
						  </div>
					   </div>
					   <div class="form-group form-group-sm ">
						  <label class="col-sm-2 control-label" >欠费金额</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="arrearage_amount" id="arrearage_amount" type="text" />
						  </div>
						  <label class="col-sm-2 control-label" >状态</label>
						  <div class="col-sm-4">
						  	<select class="form-control" id="state"> 
							  <option value="">---请选择---</option>
							</select>
						  </div>
					   </div>
					   <div class="form-group form-group-sm ">
						  <label class="col-sm-2 control-label" >付款方式</label>
						  <div class="col-sm-4">
						  	<select class="form-control" id="paid_mode"> 
							  <option value="">---请选择---</option>
							</select>
						  </div>
						  <label class="col-sm-2 control-label" >付款日期</label>
						  <div class="col-sm-4">
							 <input class="form-control laydate-icon layer-date" name="paid_date" id="paid_date" onclick="laydate.render({elem:this,type:'datetime'});" type="text" />
						  </div>
					   </div>
					   <div class="form-group form-group-sm ">
						  <label class="col-sm-2 control-label" >操作人</label>
						  <div class="col-sm-4">
							  <input class="form-control" name="oper_emp_id" id="oper_emp_id" type="text" />
						  </div>
						  <label class="col-sm-2 control-label" >更新日期</label>
						  <div class="col-sm-4">
							 <input class="form-control laydate-icon layer-date" name="update_date" id="update_date" onclick="laydate.render({elem:this});" type="text" />
						  </div>
					   </div>
					   <div class="form-group form-group-sm ">
						  <label class="col-sm-2 control-label" >备注</label>
						  <div class="col-sm-4">
							 <input class="form-control" name="remark" id="remark" type="text" />
						  </div>
					   </div>
					   <div class="form-group form-group-sm "></div>
					   <div class="modal-footer">
					        <input name="charge_id" id="charge_id" type="text" style="width:1px;visibility: hidden"/>
							<button type="button" id="button_close" class="btn btn-default" data-dismiss="modal">关闭</button>
							<button type="button" id="button_save" onclick="save()" class="btn btn-primary" data-dismiss="modal">保存</button>
					   </div>
				</form>	
			  </div>			  
			</div>
		  </div>
		</div>              
    </div>
</body>

</html>
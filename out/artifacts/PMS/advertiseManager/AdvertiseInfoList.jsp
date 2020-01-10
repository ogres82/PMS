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
<title>广告管理</title> 
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/advertiseManager/AdvertiseInfoList.js"></script>

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
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" >
		   			 <button id="btn1" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
		   			 <button id="btn2" onclick="openButtonWindow(2)" class="btn btn-primary " disabled type="button"><i class="fa fa-edit"></i> 编辑</button>
		   			 <button id="btn3" onclick="openButtonWindow(3)" class="btn btn-primary " disabled type="button"><i class="fa fa-eye"></i> 查看</button>
		    		 <button id="btn4" onclick="openButtonWindow(4)" class="btn btn-primary " disabled type="button"><i class="fa fa-trash-o"></i> 删除</button>
		   	    </div>
			    <div class="col-md-6"  style="margin-top:10px;float:left">
					<p style="font-size:14px;margin-top:10px;margin-right:1%;width:90px;float:right" id="moreSearch">
						更多查询&nbsp;<i class="fa fa-angle-down"></i>
	                </p>
		   		</div>    		
		   	</div>
	   	</div>
	   	<!-- 占位div -->
   		<div class="" style="width:100%;height:55px;"></div>
   		<!-- 占位div -->   		

		   
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>物业人员信息<small></small></h5>
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
							<table class="table-no-bordered" id="advertiseInfo"></table>	
	                    	
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
											 <input class="form-control" id="adv_pos_id" type="hide" />
											  <label class="col-sm-1 control-label">广告位编号</label>
											  <div class="col-sm-3">
													<input class="form-control" id="adv_pos_code" type="text" readonly="readonly"/>
											  </div>
											  <label class="col-sm-1 control-label">广告商</label>
											  <div class="col-sm-3">
											  	<input class="form-control validate[required]" id="adv_business" type="text" placeholder="必填项"/>
											  </div>	
											  <label class="col-sm-1 control-label">广告商电话</label>
											  <div class="col-sm-3">
											  	<input class="form-control validate[required]" id="adv_business_phone" type="text" placeholder="必填项"/>
											  </div>	
											 				  
									   </div>
									   <div class="form-group form-group-sm ">
									    	 <label class="col-sm-1 control-label">合同编号</label>
											  <div class="col-sm-3">
													<input class="form-control validate[required]" id="contract_code" type="text" placeholder="必填项"/>
											  </div>
											   <label class="col-sm-1 control-label">价格</label>
											  <div class="col-sm-3">
											  		<input class="form-control validate[required]" id="price" type="text" placeholder="必填项"/>
											  </div>
											  <label class="col-sm-1 control-label" >时限</label>
											  <div class="col-sm-3">
													<input class="form-control validate[required]" id="dur_time" type="text" placeholder="必填项"/>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
									   		  
											 
											  <label class="col-sm-1 control-label" >广告位位置</label>
											  <div class="col-sm-3">
											  		<input class="form-control" id="adv_position" type="text"></input>
											  </div>
									   </div>
									   <div class="form-group form-group-sm ">
											  <label class="col-sm-1 control-label" >广告位大小</label>
											  <div class="col-sm-3">
													<input class="form-control" id="adv_pos_size" type="text"/>
											  </div>
											  <label class="col-sm-1 control-label">状态</label>
											  <div class="col-sm-3">
													<select class="form-control validate[required]" id="adv_pos_status"> 
													  <option value="">---请选择---</option>
													</select>  
											  </div>
											  <label class="col-sm-1 control-label" >广告位类别</label>
											  <div class="col-sm-3">
													<select class="form-control validate[required]" id="adv_pos_type"> 
													  <option value="">---请选择---</option>
													</select>  
											  </div>
									   </div>
									   
									    <div class="form-group form-group-sm ">
											   <label class="col-sm-1 control-label" >备注/描述</label>
												  <div class="col-sm-11">
													 <textarea rows="3" id="adv_memo" style="height:60px" class="form-control"></textarea>
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
</body>

</html>
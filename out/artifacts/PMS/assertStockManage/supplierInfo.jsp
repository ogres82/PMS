<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

<html>
<head>
<title>供应商管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/assertStockManage/supplierInfo.js">
</script>

</head>
<body class="gray-bg">

      <div class="wrapper wrapper-content">
	       	<div style="width:100%;height:45px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
				<div class="row" style="">
		    		<div  class="col-md-6" style="float:left;margin-top:10px;" >
		    		  <button id="btn1" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
		    		  <button id="btn2" onclick="openButtonWindow(2)" class="btn btn-primary " type="button"><i class="fa fa-edit"></i> 编辑</button>
		    		  <button id="btn3" onclick="openButtonWindow(3)" class="btn btn-primary " type="button"><i class="fa fa-eye"></i> 查看</button>
		    		  <button id="btn4" onclick="openButtonWindow(4)" class="btn btn-primary " type="button"><i class="fa fa-trash-o"></i> 删除</button>
		    		</div>
		    	</div>
	    	</div>
	    	<i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
	   		<!-- 占位div -->
	   		<div class="" style="width:100%;height:45px;"></div>
	    	
		<!-- 数据表格区域 -->
		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>供应商信息<small></small></h5>
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
							<table class="table-no-bordered" id=supplierInfo></table>	
	                    </div>
	                </div>
	            </div>
	        </div>
	      <!-- 数据区域结束 -->  
	        
	     <!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" style="width:1060px">
			<div class="modal-content">
			  <div class="modal-header" style="background:#18a689">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel" style="color:white">供应商信息</h4>
			  </div>
			  <div class="modal-body">
				<form id="myForm1" class="form-horizontal" role="form">
					
					<fieldset id="basicInfo">
						   <div class="form-group form-group-sm ">
								  <label class="col-sm-1 control-label">商家编码</label>
								  <div class="col-sm-3">
										<input class="form-control" id="suppliy_code1" type="text" readonly="readonly"/>
								  </div>
								  <label class="col-sm-1 control-label">供应商</label>
								  <div class="col-sm-3">
										<input class="form-control validate[required]" id="suppliy_name1" type="text" /> 
								  </div>
								  <label class="col-sm-1 control-label" >联系人</label>
								  <div class="col-sm-3">
										<input class="form-control validate[required]" id="link_name1" type="text" />  
								  </div>								  
						   </div>
						   <div class="form-group form-group-sm ">
								  <label class="col-sm-1 control-label">联系电话</label>
								  <div class="col-sm-3">
										<input class="form-control validate[required,custom[phone]]" id="link_phone1" type="text" />
								  </div>
								  <label class="col-sm-1 control-label" >地址</label>
								  <div class="col-sm-3">
										<input class="form-control" id="link_address1" type="text" />
								  </div>
								  <label class="col-sm-1 control-label" >传真</label>
								  <div class="col-sm-3">
										<input class="form-control" id="fax1" type="text" />
								  </div>
						   </div>
						   <div class="form-group form-group-sm ">
								  <label class="col-sm-1 control-label" >邮编</label>
								  <div class="col-sm-3">
										<input class="form-control" id="zip_code" type="text" />
								  </div>
								  <label class="col-sm-1 control-label">网址</label>
								  <div class="col-sm-3">
										<input class="form-control validate[custom[url]]" id="url" type="text" />
								  </div>
								  <label class="col-sm-1 control-label" >银行</label>
								  <div class="col-sm-3">
										<input class="form-control" id="bank" type="text" />
								  </div>
						   </div>
						    <div class="form-group form-group-sm ">
								  <label class="col-sm-1 control-label" >银行账户</label>
								  <div class="col-sm-3">
										<input class="form-control" id="bank_account" type="text" />
								  </div>
								  <label class="col-sm-1 control-label">手机</label>
								  <div class="col-sm-3">
										<input class="form-control validate[required,custom[phone]]" id="link_moble" type="text" />
								  </div>
								  <label class="col-sm-1 control-label" >QQ</label>
								  <div class="col-sm-3">
										<input class="form-control" id="qq" type="text" />
								  </div>
						   </div>
						    <div class="form-group form-group-sm ">
								  <label class="col-sm-1 control-label" >其他</label>
								  <div class="col-sm-3">
										<input class="form-control" id="other" type="text" />
								  </div>
						   </div>
					  </fieldset>

					  	   <div class="form-group form-group-sm "></div>
						   <div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button id="btSupplieInfoAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
						   </div>
						<input class="form-control" id="suppliy_id" type="hidden" />
				</form>

			  </div>
			  
			</div>
		  </div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	 </div>
</body>
</html>
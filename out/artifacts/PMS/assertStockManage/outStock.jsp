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
<title>出库信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/assertStockManage/outStockVercher.js">
</script>
<link href="${ContextPath}/Hplus/bootstrap/css/bootstrap-editable.css" rel="stylesheet" />
<script src="${ContextPath}/Hplus/bootstrap/js/bootstrap-editable.js"></script>
 <script src="${ContextPath}/Hplus/bootstrap/js/bootstrap-table-editable.js"></script>
 
</head>
<body class="gray-bg">
      <div class="wrapper wrapper-content">
			<div class="" style="float:left;margin-top:3px;">
				 <button type="button" onclick="openButtonWindow(2)"  class="btn btn-primary"><i class="fa fa-plus"></i>&nbsp;新增单据</button>
				 <button type="button" onclick="getDataForm()" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;保存单据</button>				 
			</div>
			<div class="" style="width:100%;height:50px;"></div>
	    	<div class ="row ibox float-e-margins" style="text-align:center;margin-left:auto;margin-right:auto;">
				<div class="ibox-content col-sm-12 b-r" style="height:159px;">
					<form class="form-horizontal" id="orderForm">
						<div class="form-group" style="margin-top:20px;">
							<label class="col-sm-1 control-label">单据编码</label>
							<div class="col-sm-3">
								<input type="text"  class="form-control" id="voucher_code" readonly="readonly"> 
							</div>
							<label class="col-sm-1 control-label">发生日期</label>
							<div class="col-sm-3">
							<input class="form-control" id="occurren_date" type="text"  readonly="readonly"/>
							</div>
							<label class="col-sm-1 control-label">所属库存</label>
							<div class="col-sm-3">
								<select class="form-control" id="owne_stock" disabled="disabled"> 
								</select>  
							</div>
						</div>
						<div class="form-group" style="margin-top:20px;">
							<label class="col-sm-1 control-label">经手人</label>
							<div class="col-sm-3">
								<input type="text"  class="form-control" id="t_handler"readonly="readonly" > 
							</div>
							<!-- <label class="col-sm-1 control-label">供应商</label>
							<div class="col-sm-3">
							<select class="form-control" id="suppliy_code"> 
									<option value="">---请选择---</option>
								</select>  
							</div> -->
							<label class="col-sm-1 control-label">其他</label>
							<div class="col-sm-3">
								<input type="text"  class="form-control" id="other">
							</div>
						</div>
						 <div class="ibox float-e-margins">
						</div>
					 </form>
				</div>
		    </div>
	    	
		<!-- 数据表格区域 -->
		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>出库明细单<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
						<table class="table-no-bordered" id="itmsFileInfo"></table>	
	                    </div>
	                </div>
	            </div>
	        </div>
	      <!-- 数据区域结束 -->  
	        
	    	 <!-- 弹出窗口区域，触发事件后弹出  -->
		     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				<div class="modal-dialog" style="width:1020px">
						<div class="modal-content">
						  <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalLabel" style="color:white">出库物品</h4>
						  </div>
						  <div class="modal-body">
		                    	<div id="toolbar"></div>
								<table class="table-no-bordered" id="test11"></table>	
						  </div>
						  <div class="modal-footer">
					    	  <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					    	  <button id="btn3" onclick="getItemList()" class="btn btn-primary " type="button">确定</button>
					      </div>
						  
					</div>
				</div>
			</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	 </div>
</body>
</html>
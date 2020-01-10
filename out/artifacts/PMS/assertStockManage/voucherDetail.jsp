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
<title>单据管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/assertStockManage/voucherDetail.js">
</script>

</head>
<body class="gray-bg">

      <div class="wrapper wrapper-content">
      		<div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
				<div class="row" style="">
		    		<div  class="col-md-6" style="float:left;margin-top:10px;" >
		    		  <button id="btn3" onclick="openButtonWindow(3)" class="btn btn-primary " type="button"><i class="fa fa-eye"></i> 查看详情</button>
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
	    	
	    	<!-- 更多查询区域  -->
	    	<div class ="row ibox float-e-margins" style="display:none;text-align:center;margin-left:auto;margin-right:auto;" id="more_search">
				<div class="ibox-content col-sm-12 b-r">
					<form class="form-horizontal">
								   
						<div class="form-group" style="margin-top:20px;">
							<label class="col-sm-1 control-label">时间</label>
							<div class="col-sm-3">
								<input type="text"  class="form-control"> 
							</div>
							<label class="col-sm-1 control-label">类型</label>
							<div class="col-sm-3">
								<input type="text"  class="form-control">
							</div>
						</div>
						<div class="col-sm-12">
							<button type="button" style="float:right;" class="btn btn-primary"><i class="fa fa-search"></i>查询</button> 
						</div>
					 </form>
				</div>
		    </div>
	    	
		<!-- 数据表格区域 -->
		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>单据信息<small></small></h5>
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
							<table class="table-no-bordered" id="voucherDetail"></table>	
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
				<h4 class="modal-title" id="myModalLabel" style="color:white">单据详细信息</h4>
			  </div>
			  <div class="modal-body">
				<form id="myForm1" class="form-horizontal" role="form">
					<fieldset id="itmsFileInfo">
						   <div class="form-group form-group-sm ">
								  <label class="col-sm-1 control-label">单据编码</label>
								  <div class="col-sm-3">
										<input class="form-control" id="voucher_code" type="text" readonly="readonly">
								  </div>
								  <label class="col-sm-1 control-label">时间</label>
								  <div class="col-sm-3">
										<input class="form-control" id="occurren_date" type="text" readonly="readonly"/>
								  </div>
								  <label class="col-sm-1 control-label" >所属库存</label>
								  <div class="col-sm-3">
								  <input class="form-control" id="warehouse_name" type="text" readonly="readonly"/>
								  </div>
						   </div>
						    <div class="form-group form-group-sm ">
								  
								   <label class="col-sm-1 control-label">经手者</label>
								  <div class="col-sm-3">
										 <input class="form-control" id="t_handler" type="text" readonly="readonly"/>
								  </div>	
								  	
								  <label class="col-sm-1 control-label" >供应商</label>
								  <div class="col-sm-3">
								  <input class="form-control" id="suppliy_name" type="text" readonly="readonly"/>
								  </div>	
								  
								   <label class="col-sm-1 control-label">其他</label>
								  <div class="col-sm-3">
										<input class="form-control" id="other" type="text" readonly="readonly"/>
								  </div>					  
						   </div>
						    <div class="form-group form-group-sm ">
								  <label class="col-sm-1 control-label" >类型</label>
								  <div class="col-sm-3">
								  <input class="form-control" id="instok_typename" type="text" readonly="readonly"/>
								  </div>
						   </div>
					  </fieldset>
		<fieldset id="childTable">
		<legend style="font-size:15px">物品信息</legend>
		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-content" id="tempTable">
							<table class="table-no-bordered" id=itemInfo></table>	
	                    </div>
	                </div>
	            </div>
	        </div>
	      <!-- 数据区域结束 -->  
	</fieldset>
					  
					  
					  
					  
					  	   <div class="form-group form-group-sm "></div>
						   <div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal" onclick="clearn_Content();">关闭</button>
						   </div>
						<input class="form-control" id="voucher_id" type="hidden" />
				</form>

			  </div>
			  
			</div>
		  </div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	 </div>
</body>
</html>
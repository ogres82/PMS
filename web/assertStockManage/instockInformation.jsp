<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser"%>
<%@ page import="com.bstek.bdf2.core.context.ContextHolder"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
	request.setAttribute("loginUser", user);
%>

<html>
<head>
<title>库存信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/assertStockManage/instockInformation.js">
	
</script>

</head>
<body class="gray-bg">

	<div class="wrapper wrapper-content">
		<div style="width: 100%; height: 45px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row" style="">
				<div class="col-md-6" style="float: left; margin-top: 10px;">
					<button id="btn3" onclick="openButtonWindow(3)" class="btn btn-primary " type="button">
						<i class="fa fa-eye"></i> 查看
					</button>
				</div>
			</div>
		</div>
		<i class="fa fa-search" style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->
		<!-- 更多查询区域 
	    	<div class ="row ibox float-e-margins" style="display:none;text-align:center;margin-left:auto;margin-right:auto;" id="more_search">
				<div class="ibox-content col-sm-12 b-r">
					<form class="form-horizontal">
								   
						<div class="form-group" style="margin-top:20px;">
							<label class="col-sm-1 control-label">物品编码</label>
							<div class="col-sm-3">
								<input type="text"  class="form-control"> 
							</div>
							<label class="col-sm-1 control-label">物资类别</label>
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
	    	 -->
		<!-- 数据表格区域 -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							物品库存信息<small></small>
						</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="dropdown-toggle" data-toggle="dropdown" href="table_data_tables.html#"> <i class="fa fa-wrench"></i>
							</a> <a class="close-link"> <i class="fa fa-times"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="instockInfo"></table>
					</div>
				</div>
			</div>
		</div>
		<!-- 数据区域结束 -->

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 800px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">库存信息</h4>
					</div>
					<div class="modal-body">
						<form id="myForm1" class="form-horizontal" role="form">
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">物品名称</label>
								<div class="col-sm-4">
									<input class="form-control" id="item_name" type="text" readonly="readonly">
								</div>
								<label class="col-sm-2 control-label">物品型号</label>
								<div class="col-sm-4">
									<input class="form-control" id="bar_code" type="text" readonly="readonly" />
								</div>
							</div>
							<div class="form-group form-group-sm ">

								<label class="col-sm-2 control-label">物品类别</label>
								<div class="col-sm-4">
									<select class="form-control" id="item_Ptype" readonly="readonly" disabled="disabled">
										<option value="">---请选择---</option>
									</select>
								</div>
								<label class="col-sm-2 control-label">物品类型</label>
								<div class="col-sm-4">
									<select class="form-control" id="item_type" readonly="readonly" disabled="disabled">
										<option value="">---请选择---</option>
									</select>
								</div>
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">单位</label>
								<div class="col-sm-4">
									<select class="form-control" id="item_unit" readonly="readonly" disabled="disabled">
										<option value="">---请选择---</option>
									</select>
								</div>
								<label class="col-sm-2 control-label">数量</label>
								<div class="col-sm-4">
									<input class="form-control" id="suppliy_num" type="text" readonly="readonly" />
								</div>
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">所属库存</label>
								<div class="col-sm-4">
									<input class="form-control" id="warehouse_name" type="text" readonly="readonly" />
								</div>
								<label class="col-sm-2 control-label">库存上限</label>
								<div class="col-sm-4">
									<input class="form-control" id="stock_uplimit" type="text" readonly="readonly" />
								</div>

							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">库存下限</label>
								<div class="col-sm-4">
									<input class="form-control" id="stock_lowerlimit" type="text" readonly="readonly" />
								</div>
								<label class="col-sm-2 control-label">单价</label>
								<div class="col-sm-4">
									<input class="form-control" id="unit_price" type="text" readonly="readonly" />
								</div>
							</div>
							<div class="form-group form-group-sm ">

								<label class="col-sm-2 control-label">总价</label>
								<div class="col-sm-4">
									<input class="form-control" id="sum_price" type="text" readonly="readonly" />
								</div>
							</div>
							<div class="form-group form-group-sm "></div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button id="btAddinstockFiles" onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
							</div>
							<input class="form-control" id="item_id" type="hidden" />
						</form>

					</div>

				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	</div>
</body>
</html>
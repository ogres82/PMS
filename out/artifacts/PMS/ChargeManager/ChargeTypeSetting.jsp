<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>收费项类别</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/ChargeManager/ChargeTypeSetting.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;" id="topToolbar"></div>
			</div>
		</div>

		<i class="fa fa-search" style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->


		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							收费项类别信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="chargeTypeSetting"></table>

					</div>

				</div>
			</div>
		</div>




		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 800px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalTitle" style="color: white">收费项设定</h4>
					</div>
					<div class="modal-body">
						<form id="myForm" class="form-horizontal" action="${ContextPath}/ChargeManager/ChargeType.app?method=saveOrUpdate" method="post">
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">项目编号</label>
								<div class="col-sm-4">
									<input class="form-control validate[required,custom[number],minSize[4],maxSize[4],ajax[ajaxValidateTypeNo]]" name="charge_type_no" id="charge_type_no" type="text" placeholder="必填项" />
								</div>
								<label class="col-sm-2 control-label">项目名称</label>
								<div class="col-sm-4">
									<input class="form-control validate[required]" name="charge_type_name" id="charge_type_name" type="text" placeholder="必填项" />
								</div>
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">计费方式</label>
								<div class="col-sm-4">
									<select class="form-control validate[required]" id="charge_mode">
										<option value="">---请选择---</option>
									</select>
								</div>
								<label class="col-sm-2 control-label">收费方式</label>
								<div class="col-sm-4">
									<select class="form-control validate[required]" id="charge_way">
										<option value="">---请选择---</option>
									</select>
								</div>
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">收费类型</label>
								<div class="col-sm-4">
									<select class="form-control validate[required]" id="charge_type">
										<option value="">---请选择---</option>
									</select>
								</div>
								<label class="col-sm-2 control-label">单价(元)</label>
								<div class="col-sm-4">
									<input class="form-control validate[required,custom[number]]" name="charge_price" id="charge_price" type="text" />
								</div>
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-2 control-label">是否物业费</label>
								<div class="col-sm-4">
									<input class="checkbox i-checks" type="checkbox" name="type_flag" id="type_flag" />
								</div>
							</div>
							<!-- 					   <div class="form-group form-group-sm "> -->
							<!-- 						  <label class="col-sm-2 control-label" >计费周期数</label> -->
							<!-- 						  <div class="col-sm-4"> -->
							<!-- 							 <input class="form-control validate[optional,custom[integer]]" name="charge_cycle_count" id="charge_cycle_count" type="text" /> -->
							<!-- 						  </div> -->
							<!-- 						  <label class="col-sm-2 control-label" >周期单位</label> -->
							<!-- 						  <div class="col-sm-4"> -->
							<!-- 							  <select class="form-control" id="charge_cycle_unit">  -->
							<!-- 								  <option value="">---请选择---</option> -->
							<!-- 								</select> -->
							<!-- 						  </div> -->
							<!-- 					   </div> -->
							<div class="form-group form-group-sm "></div>
							<div class="modal-footer">
								<input name="charge_type_id" id="charge_type_id" type="text" style="width: 1px; visibility: hidden" /> <input name="oper_type" id="oper_type" type="text" style="width: 1px; visibility: hidden" />
								<button type="button" id="button_close" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button type="button" id="button_save" onclick="save()" class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
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
<title>欠费信息</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/ChargeManager/ChargeArrearInfo.js"></script>
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

		<div class="col-sm-12">
			<blockquote class="layui-elem-quote" style="background-color: white">
				<div class="row">
					<div class="col-sm-3">
						<select class="form-control" name="type" id="communityNameSearch">
							<option disabled selected style='display: none;' value="">所属小区</option>
						</select>
					</div>
					<div class="col-sm-3">
						<select class="form-control" name="type" id="chargeArrNum">
							<option disabled selected style='display: none;' value="">欠费期数</option>
							<option value="3">欠费3个月以上</option>
							<option value="6">欠费6个月以上</option>
							<option value="12">欠费1年以上</option>
						</select>
					</div>
					<div class="col-sm-2" style="float: right">
						<button id="buttonSearch" class="btn btn-primary" type="button">查询</button>
						<button id="clearSearch" onclick="clearSearch()" class="btn btn-primary" type="button">清空</button>
					</div>
				</div>
			</blockquote>
		</div>


		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							欠费信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="chargeArrearInfo"></table>
					</div>
				</div>
			</div>
		</div>


		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="mySendModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 800px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">短信历史</h4>
					</div>
					<div class="modal-body">
						<div class="ibox-content" id="detailTable">
							<table class="table-no-bordered" id="sendHistory"></table>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="sendModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 930px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" style="color: white">发送短信</h4>
					</div>
					<div class="modal-body">
						<form id="myForm" class="form-horizontal" action="${ContextPath}/msgandnotice/mgssend.app?method=save" method="post">
							<div class="form-group form-group-sm ">
								<label class="col-sm-1 control-label">接收人</label>
								<div class="col-sm-11">
									<div class="input-group">
										<input type="text" class="form-control" id="msgReceiverNames" name="msgReceiverNames" readonly> <input type="hidden" class="form-control" id="msgReceiverIds" name="msgReceiverIds">
									</div>
								</div>
							</div>
							<div class="form-group form-group-sm ">
								<label class="col-sm-1 control-label">短信类型</label>
								<div class="col-sm-4">
									<select class="form-control" id="msg_type" onchange="showTemp()">
										<option value="0">不带滞纳金的模板</option>
										<option value="1">带滞纳金的模板</option>
									</select>
								</div>
							</div>							

							<div class="form-group form-group-sm ">
								<label class="col-sm-1 control-label">短信模版</label>
								<div class="col-sm-11">
									<textarea class="form-control" rows="6" id="msgContentText" style="height: 60px"></textarea>
									<input type="hidden" id="msgContent" name="msgContent">
								</div>
							</div>

							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button type="button" onclick="send()" class="btn btn-primary" data-dismiss="modal" id="btnSave">发送</button>
							</div>
						</form>

					</div>

				</div>
			</div>
		</div>

	</div>
</body>

</html>
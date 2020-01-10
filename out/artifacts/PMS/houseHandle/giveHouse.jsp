<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser" %> 
<%@ page import="com.bstek.bdf2.core.context.ContextHolder" %>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
    request.setAttribute("loginUser",user);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>地产交房登记</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/houseHandle/giveHouse.js"></script>
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
							交房信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="housePropertyInfo"></table>

					</div>

				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 800px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">登记信息</h4>
					</div>
					<div class="modal-body">
						<div id="tab-1" class="tab-pane active">
							<form id="myForm1" class="form-horizontal" role="form">

								<fieldset id="basicInfo">
									<legend style="font-size: 15px; border: 0;"></legend>

									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">房间</label>
										<div class="col-sm-10">
											<div class="input-group">
												<input type="text" class="form-control validate[required]" id="roomNo_d" placeholder="房间号/楼宇/小区/楼盘">
												<div class="input-group-btn">
													<button type="button" class="btn btn-white btn-sm dropdown-toggle" data-toggle="dropdown">
														<span class="caret"></span>
													</button>
													<ul class="dropdown-menu dropdown-menu-right" role="menu">
													</ul>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">客户</label>
										<div class="col-sm-10">
											<div class="input-group">
												<input type="text" class="form-control validate[required]" id="owner" placeholder="业主姓名/电话号码">
												<div class="input-group-btn">
													<button type="button" class="btn btn-white btn-sm dropdown-toggle" data-toggle="dropdown">
														<span class="caret"></span>
													</button>
													<ul class="dropdown-menu dropdown-menu-right" role="menu">
													</ul>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group form-group-sm ">
										<label class="col-sm-2 control-label">收房日期</label>
										<div class="col-sm-10">
											<input class="form-control laydate-icon layer-date validate[required]" id="makeRoomDate" type="text" placeholder="必填项" />
										</div>
									</div>
								</fieldset>

							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btGiveHouseAdd" onclick="openSaveButton()" class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="roomId" type="hidden" /> <input class="form-control" id="roomNo" type="hidden" /> <input class="form-control" id="ownerId" type="hidden" />
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1020px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" style="color: white">查看</h4>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<form id="myForm1" class="form-horizontal" role="form">

									<fieldset id="basicInfo">
										<legend style="font-size: 15px; border: 0;">房屋信息</legend>

										<div class="form-group form-group-sm ">

											<label class="col-sm-1 control-label">所属楼盘</label>
											<div class="col-sm-3">
												<input class="form-control" id="build_name" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">所属小区</label>
											<div class="col-sm-3">
												<input class="form-control" id="community_name" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">所属楼宇</label>
											<div class="col-sm-3">
												<input class="form-control" id="storied_build_name" type="text" readonly />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">房间号</label>
											<div class="col-sm-3">
												<input class="form-control" id="roomNo_detail" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">户型</label>
											<div class="col-sm-3">
												<input class="form-control" id="houseType" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">建筑面积</label>
											<div class="col-sm-3">
												<input class="form-control" id="buildArea" type="text" readonly />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">套内面积</label>
											<div class="col-sm-3">
												<input class="form-control" id="withinArea" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">房间类型</label>
											<div class="col-sm-3">
												<input class="form-control" id="room_type_name" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">房间状态</label>
											<div class="col-sm-3">
												<input class="form-control" id="room_state_name" type="text" readonly />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">收费对象</label>
											<div class="col-sm-3">
												<input class="form-control" id="charge_object_name" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">收房日期</label>
											<div class="col-sm-3">
												<input class="form-control" id="receiveRoomDate" type="text" readonly />
											</div>

										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">房间备注</label>
											<div class="col-sm-11">
												<input class="form-control" id="remark" type="text" readonly />
											</div>
										</div>
										<legend style="font-size: 15px; border: 0;">业主信息</legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">客户名称</label>
											<div class="col-sm-3">
												<input class="form-control" id="ownerName" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">电话号码</label>
											<div class="col-sm-3">
												<input class="form-control" id="phone" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">身份证号</label>
											<div class="col-sm-3">
												<input class="form-control" id="cardId" type="text" readonly />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">固定电话</label>
											<div class="col-sm-3">
												<input class="form-control" id="telPhone" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">业主类型</label>
											<div class="col-sm-3">
												<input class="form-control" id="ownerTypeName" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">出生日期</label>
											<div class="col-sm-3">
												<input class="form-control" id="birthDate" type="text" readonly />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">车牌号</label>
											<div class="col-sm-3">
												<input class="form-control" id="carId" type="text" readonly />
											</div>
											<label class="col-sm-1 control-label">客户身份</label>
											<div class="col-sm-3">
												<input class="form-control" id="ownerIdentityName" type="text" readonly />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">业主备注</label>
											<div class="col-sm-11">
												<input class="form-control" id="ownerRemark" type="text" readonly />
											</div>
										</div>
									</fieldset>

								</form>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	</div>
	<script>
		laydate.render({
			elem : '#makeRoomDate',
			//format: 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm1').validationEngine('validate');
			}
		});
	</script>
</body>

</html>
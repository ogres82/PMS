<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设备保养记录</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript"
	src="${ContextPath}/equipment/eqpMtnRec.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">

		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div
			style="width: 100%; height: 45px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row" style="">
				<div class="col-md-6" style="float: left; margin-top: 10px;"
					id="topToolbar">
					<!-- 		   			 <button id="btn_add" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i> 新增</button> -->
					<!-- 		    		 <button id="btn_del" onclick="openButtonWindow(3)" class="btn btn-primary " type="button"><i class="fa fa-trash-o"></i> 删除</button> -->
				</div>
			</div>
		</div>
		<i class="fa fa-search"
			style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->


		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							设备保养记录<small></small>
						</h5>

					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="eqpMtnRec"></table>

					</div>

				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 900px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">保养记录</h4>
					</div>
					<div class="modal-body">
						<form id="myForm1" class="form-horizontal" role="form">
							<fieldset id="planInfo">
								<legend style="font-size: 15px; border: 0;"></legend>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">设备名称</label>
									<div class="col-sm-4">
										<div class="input-group">
											<input type="text" class="form-control validate[required]"
												placeholder="设备名称/必填项" id="eqpName" /> <span
												class="input-group-btn">
												<button class="btn btn-white dropdown-toggle"
													data-toggle="dropdown" style="height: 30px;" type="button">
													<span class="caret"></span>
												</button>
												<ul class="dropdown-menu dropdown-menu-right" role="menu">
												</ul>
											</span>
										</div>
										<input style="display: none" class="form-control" id="eqpId"
											type="text" /> <input style="display: none"
											class="form-control" id="mtnRecId" type="text" />
									</div>
									<label class="col-sm-2 control-label">保养人</label>
									<div class="col-sm-4">
										<input class="form-control" id="eqpMtnPerson" type="text" />
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">保养日期</label>
									<div class="col-sm-4">
										<input
											class="form-control laydate-icon layer-date validate[required]"
											placeholder="必填项" id="eqpMtnDate" type="text" />
									</div>
									<label class="col-sm-2 control-label">保养结束日期</label>
									<div class="col-sm-4">
										<input
											class="form-control laydate-icon layer-date validate[required]"
											placeholder="必填项" id="eqpMtnEnddate" type="text" />
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">保养类别</label>
									<div class="col-sm-4">
										<select class="form-control validate[required]"
											id="eqpMtnType">
											<option value="">---请选择---</option>
										</select>
									</div>
									<label class="col-sm-2 control-label">保养项目</label>
									<div class="col-sm-4">
										<input class="form-control" id="eqpMtnContent" type="text" />
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">保养工时</label>
									<div class="col-sm-4">
										<input class="form-control validate[custom[number]]"
											id="eqpMtnTime" type="text" />
									</div>
									<label class="col-sm-2 control-label">保养费用</label>
									<div class="col-sm-4">
										<input class="form-control validate[custom[number]]"
											id="eqpMtnFee" type="text" />
									</div>
								</div>
								<div class="form-group form-group-sm ">
									<label class="col-sm-2 control-label">保养详情</label>
									<div class="col-sm-10">
										<textarea class="form-control" rows="3" id="eqpMtnDetail"
											style="height: 60px"></textarea>
									</div>

								</div>
							</fieldset>
						</form>
					</div>
					<div class="form-group form-group-sm "></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btn_save" onclick="openSaveButton()"
							class="btn btn-primary " type="button">保存</button>
					</div>
				</div>

			</div>
			<!-- 弹出窗口区域，触发事件后弹出   结束 -->

		</div>

		<script>
			laydate.render({
				elem : '#eqpMtnDate',
				event : 'click',
				//format: 'YYYY年MM月DD日',
				festival : true, //显示节日
				format : 'YYYY-MM-DD',
				choose : function(datas) { //选择日期完毕的回调
					$('#myForm1').validationEngine('hide');
				}
			});
			laydate.render({
				elem : '#eqpMtnEnddate',

				event : 'click',
				//format: 'YYYY年MM月DD日',
				festival : true, //显示节日
				format : 'YYYY-MM-DD',
				choose : function(datas) { //选择日期完毕的回调
					$('#myForm1').validationEngine('hide');
				}
			});
		</script>
	</div>
</body>

</html>
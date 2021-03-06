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
<title>单元信息</title>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript"
	src="${ContextPath}/basicInfo/BuildingUnit/BuildingUnit.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
</head>
<body>
	<div class="wrapper wrapper-content">

		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div
			style="width: 100%; height: 55px; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;"
					id="topToolbar"></div>
			</div>
		</div>
		<!-- 占位div -->
		<div class="" style="width: 100%; height: 45px;"></div>
		<!-- 占位div -->

		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							单元信息<small></small>
						</h5>
					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="buildingUnitInfo"></table>

					</div>

				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 800px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel" style="color: white">楼宇信息</h4>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<form id="myForm1" class="form-horizontal" role="form">

									<fieldset id="basicInfo">
										<legend style="font-size: 15px; border: 0;"></legend>

										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">所属楼盘</label>
											<div class="col-sm-10">
												<select class="form-control validate[required]"
													id="belongBuild"">
													<option value="">---请选择---</option>
												</select>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">所属小区</label>
											<div class="col-sm-10">
												<select class="form-control validate[required]"
													id="belongComm" >
													<option value="">---请选择---</option>
												</select>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">楼宇名称</label>
											<div class="col-sm-10">
												<select class="form-control validate[required]"
													id="storiedBuild" >
													<option value="">---请选择---</option>
												</select>
											</div>
										</div>
								
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">单元名称</label>
											<div class="col-sm-10">
												<input class="form-control validate[required]"
													id="unitName" type="text" placeholder="必填项"  />
											</div>
										</div>
										
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">联掌同步</label>
											<div class="col-sm-10">
											<input class="form-control"
													id="isScny" type="text" readonly = "readonly" />
											</div>
										</div>
									</fieldset>

								</form>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btBuildingUnitAdd" onclick="openSaveButton()"
							class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="buildid" type="hidden" /> 
					<input class="form-control" id="buildLzId" type="hidden" />
					<input class="form-control" id="communityId" type="hidden" /> 
					<input class="form-control" id="communityIdLzId" type="hidden" /> 
					<input class="form-control" id="storiedBuildId" type="hidden" />
					<input class="form-control" id="storiedBuildLzId" type="hidden" /> 
					<input class="form-control" id="unitId" type="hidden" />
					<input class="form-control" id="unitLzId" type="hidden" />
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	</div>
</body>

</html>
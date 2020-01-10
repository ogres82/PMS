<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser"%>
<%@ page import="com.bstek.bdf2.core.context.ContextHolder"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
	request.setAttribute("loginUser", user);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>物管人员信息</title>

<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/basicInfo/Emp.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>

<script type="text/javascript">
	loginUserName = "${loginUser.username}";
	loginUserCname = "${loginUser.cname}";
</script>
<style>
#moreSearch:hover {
	cursor: pointer;
}
</style>
</head>
<body class="gray-bg">
	<iframe src="" name="show" style="width: 0; height: 0"></iframe>
	<div class="wrapper wrapper-content">

		<!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
		<div
			style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
			<div class="row">
				<div class="col-md-6" style="float: left; margin-top: 10px;"
					id="topToolbar">
				</div>				
			</div>
		</div>
		<i class="fa fa-search"
			style="position: fixed; right: 12px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
		<!-- 占位div -->
		
		<div class="" style="width: 100%; height: 45px;"></div>
		<div class="row">
   			<div class="col-sm-12">
   				<blockquote class="layui-elem-quote" style="background-color:white">
   					<div class="row">
					  	<div class="col-sm-2">
							<select class="form-control" name="type" id="deptIdFromSearch">
								  <option disabled selected style='display:none;' value="">部门</option>
						  	</select>
					  	</div>
					  	<div class="col-sm-2">
							<select class="form-control" name="type" id="posIdFromSearch">
								  <option disabled selected style='display:none;' value="">岗位</option>
						  	</select>
					  	</div>
					  	<div class="col-sm-2">
							<select class="form-control" name="type" id="empStateFromSearch">
								  <option disabled selected style='display:none;' value="">员工状态</option>
								  <option value="1">在职</option>
						  	   	  <option value="0">离职</option>
						  	</select>
					  	</div>
					   	<div class="col-sm-2">
							<input type="text" class="form-control layer-date " onclick="laydate.render({ elem: this});" id="startDateFromSearch" placeholder="开始日期">
					  	</div>
					  	<div class="col-sm-2">
							<input type="text" class="form-control layer-date " onclick="laydate.render({ elem: this});" id="endDateFromSearch" placeholder="结束日期">
					  	</div>
					  	<div class="col-sm-2">
						  	<button id="buttonSearch" class="btn btn-primary" type="button" >查询</button>
						  	<button id="clearSearch" onclick="clearSearch()" class="btn btn-primary" type="button">清空</button>
						</div>
					 </div>
   				</blockquote>
   			</div>
   		</div>		

		<!-- 数据表格区域  -->
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							物业人员信息<small></small>
						</h5>

					</div>
					<div class="ibox-content" id="tempTable">
						<div id="toolbar"></div>
						<table class="table-no-bordered" id="empInfo"></table>

					</div>

				</div>
			</div>
		</div>

		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 1100px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalTitle" style="color: white"></h4>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs">
							<li id="t-1" class="active"><a id="a1" data-toggle="tab"
								href="#tab-1">员工基础信息</a></li>
							<li id="t-2" class=""><a id="a2" data-toggle="tab"
								href="#tab-2">入职企业内部信息</a></li>
							<li id="t-3" class=""><a id="a3" data-toggle="tab"
								href="#tab-3">学历与资质</a></li>
							<li id="t-4" class=""><a id="a4" data-toggle="tab"
								href="#tab-4">劳动合同履行</a></li>
							<li id="t-5" class=""><a id="a5" data-toggle="tab"
								href="#tab-5">辅助信息</a></li>
							<li id="t-6" class=""><a id="a6" data-toggle="tab"
								href="#tab-6">薪酬、社保与公积金信息</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<form id="myForm1" class="form-horizontal" role="form">
									<fieldset id="">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">单位</label>
											<div class="col-sm-3">
												<input class="form-control" id="empWorkUnit" type="text" />
											</div>
											<label class="col-sm-1 control-label">工号</label>
											<div class="col-sm-3">
												<input class="form-control validate[required]" id="empNo"
													type="text" placeholder="必填项" />
											</div>
											<label class="col-sm-1 control-label">姓名</label>
											<div class="col-sm-3">
												<input class="form-control validate[required]" id="empName"
													type="text" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">身份证号</label>
											<div class="col-sm-3">
												<input
													class="form-control validate[required,custom[chinaIdLoose]]"
													id="empIdNo" type="text" placeholder="必填项"
													onblur="autoFill()" />
											</div>
											<label class="col-sm-1 control-label">联系电话</label>
											<div class="col-sm-3">
												<input
													class="form-control  validate[required,custom[mobilephone]] "
													id="empPhone" type="text" placeholder="必填项" />
											</div>
											<label class="col-sm-1 control-label">性别</label>
											<div class="col-sm-3">
												<select class="form-control" id="empSex">
													<option value="0">女</option>
													<option value="1">男</option>
												</select>
											</div>

										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">民族</label>
											<div class="col-sm-3">
												<input class="form-control validate[required]"
													id="empNation" type="text" />
											</div>
											<label class="col-sm-1 control-label">政治面貌</label>
											<div class="col-sm-3">
												<input class="form-control validate[required]"
													id="empPolitics" type="text" />
											</div>
											<label class="col-sm-1 control-label">出生日期</label>
											<div class="col-sm-3">
												<input class="form-control" disabled="disabled"
													id="empBirth" type="text" />
											</div>

										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">年龄</label>
											<div class="col-sm-3">
												<input class="form-control" disabled="disabled" id="empAge"
													type="text" />
											</div>
											<label class="col-sm-1 control-label">出生月份</label>
											<div class="col-sm-3">
												<input class="form-control" disabled="disabled"
													id="empBirthMon" type="text" />
											</div>
											<label class="col-sm-1 control-label">年龄分段</label>
											<div class="col-sm-3">
												<input class="form-control" disabled="disabled"
													id="empAgeGroup" type="text" />
											</div>
										</div>
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">录用流程表</label>
											<div class="col-sm-3">
												<input class="form-control" id="empFlowChart" type="text" />
											</div>
											<label class="col-sm-1 control-label">个人简历</label>
											<div class="col-sm-3">
												<input class="form-control" id="empResume" type="text" />
												
											</div>
											<label class="col-sm-1 control-label">员工登记表</label>
											<div class="col-sm-3">												
												<input class="form-control" id="empRegForm" type="text" />										
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">保密协议</label>
											<div class="col-sm-3">	
												<input class="form-control" id="empAgreement" type="text" />
											</div>
											<label class="col-sm-1 control-label">照片</label>
											<div class="col-sm-3">
												<input class="form-control" id="empPhoto" type="text" />
											</div>
											<label class="col-sm-1 control-label">体检报告</label>
											<div class="col-sm-3">
												<input class="form-control" id="empCheckupReport" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">

											<label class="col-sm-1 control-label">身份证(复)</label>
											<div class="col-sm-3">
												<input class="form-control" id="empIDCardCopy" type="text" />
											</div>
											<label class="col-sm-1 control-label">户口簿(复)</label>
											<div class="col-sm-3">
												<input class="form-control" id="empBookletCopy" type="text" />
											</div>
											<label class="col-sm-1 control-label">毕业证(复)</label>
											<div class="col-sm-3">
												<input class="form-control" id="empDiplomaCopy" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">离职证明</label>
											<div class="col-sm-3">
												<input class="form-control" id="empLeaveCertificate" type="text" />
											</div>
											<label class="col-sm-1 control-label">驾驶执照</label>
											<div class="col-sm-3">
												<input class="form-control" id="empDriverLicense" type="text" />
											</div>
											<label class="col-sm-1 control-label">职称证书</label>
											<div class="col-sm-3">
												<input class="form-control" id="empTitleCertificate" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">担保人资料</label>
											<div class="col-sm-3">
												<input class="form-control" id="empBondsmanInfo" type="text" />
											</div>
											<label class="col-sm-1 control-label">担保书</label>
											<div class="col-sm-3">
												<input class="form-control" id="empBond" type="text" />
											</div>
										</div>
										<br>
									</fieldset>
								</form>
							</div>
							<div id="tab-2" class="tab-pane">
								<form id="myForm2" class="form-horizontal" role="form">
									<fieldset id="">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">板块</label>
											<div class="col-sm-3">
												<input class="form-control" id="empPlate" type="text" />
											</div>
											<label class="col-sm-1 control-label">部门</label>
											<div class="col-sm-3">
												<select class="form-control validate[required]"
													id="empDeptId">
													<option value="">---请选择---</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">岗位</label>
											<div class="col-sm-3">
												<select class="form-control validate[required]"
													id="empPostId">
													<option value="">---请选择---</option>
												</select>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">工作职场</label>
											<div class="col-sm-3">
												<input class="form-control" id="empWorkPlace" type="text" />
											</div>
											<label class="col-sm-1 control-label">入职时间</label>
											<div class="col-sm-3">
												<input class="form-control laydate-icon layer-date"
													id="empEntryTime" type="text" onclick="laydate.render({ elem: this});" />
											</div>
											<label class="col-sm-1 control-label">离职时间</label>
											<div class="col-sm-3">
												<input class="form-control laydate-icon layer-date"
													id="empQuitTime" type="text" onclick="laydate.render({ elem: this});" />
											</div>

										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">试用期</label>
											<div class="col-sm-3">
												<select class="form-control" id="empProbation">
													<option value="1个月" selected="selected">1个月</option>
													<option value="3个月">3个月</option>
													<option value="6个月">6个月</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">转正情况</label>
											<div class="col-sm-3">
												<select class="form-control" id="empRegularize">
													<option value="">---请选择---</option>
													<option value="1">已转正</option>
													<option value="0">未转正</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">转正时间</label>
											<div class="col-sm-3">
												<input class="form-control laydate-icon layer-date"
													id="empRegularizeTime" type="text" onclick="laydate.render({ elem: this});" />
											</div>
										</div>
										<div class="form-group form-group-sm">
											<label class="col-sm-1 control-label">我司工龄</label>
											<div class="col-sm-3">
												<input class="form-control" id="empWorkAge"
													disabled="disabled" type="text" />
											</div>
											<label class="col-sm-1 control-label">职能层级</label>
											<div class="col-sm-3">
												<select class="form-control" id="empPositionLevl">
													<option value="">---请选择---</option>
													<option value="0">专员</option>
													<option value="1">主管</option>
													<option value="2">经理/副经理</option>
													<option value="3">总监/副总监</option>
													<option value="4">分管副总</option>
													<option value="5">总经理</option>
													<option value="6">副总裁</option>
													<option value="7">总裁/董事长</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">技术层级</label>
											<div class="col-sm-3">
												<select class="form-control" id="empTechLevl">
													<option value="">---请选择---</option>
													<option value="0">助理技术员/施工员</option>
													<option value="1">技术员/施工员</option>
													<option value="2">助理工程师</option>
													<option value="3">工程师</option>
													<option value="4">主管/高级工程师</option>
													<option value="5">特殊技术人才</option>
													<option value="6">总工</option>
												</select>
											</div>

										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">任职时间</label>
											<div class="col-sm-3">
												<input class="form-control laydate-icon layer-date"
													id="empTenureDate" type="text" onclick="laydate.render({ elem: this});" />
											</div>
											<label class="col-sm-1 control-label">人事档案存放地</label>
											<div class="col-sm-3">
												<input class="form-control" id="empFileStorePos" type="text" />
											</div>
											<label class="col-sm-1 control-label">人事档案编号</label>
											<div class="col-sm-3">
												<input class="form-control" id="empFileNo" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">岗位平调</label>
											<div class="col-sm-11">
												<textarea rows="3" style="height: 60px" class="form-control"
													id="empPosChange1"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">岗位晋升/降职</label>
											<div class="col-sm-11">
												<textarea rows="3" style="height: 60px" class="form-control"
													id="empPosChange2"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">培训记录</label>
											<div class="col-sm-11">
												<textarea rows="3" style="height: 60px" class="form-control"
													id="empTrainingRec"></textarea>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">奖惩记录</label>
											<div class="col-sm-11">
												<textarea rows="3" style="height: 60px" class="form-control"
													id="empRewordPunish"></textarea>
											</div>
										</div>
									</fieldset>
								</form>
							</div>
							<div id="tab-3" class="tab-pane">
								<form id="myForm3" class="form-horizontal" role="form">
									<fieldset id="">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">学历</label>
											<div class="col-sm-3">
												<select class="form-control" id="empEducation">
													<option value="">---请选择---</option>
													<option value="0">小学</option>
													<option value="1">初中</option>
													<option value="2">高中</option>
													<option value="3">中专</option>
													<option value="4">大专</option>
													<option value="5">本科</option>
													<option value="6">研究生</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">是否全日制</label>
											<div class="col-sm-3">
												<select class="form-control" id="empEduFullTime">
													<option value="">---请选择---</option>
													<option value="1">是</option>
													<option value="0">否</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">毕业院校</label>
											<div class="col-sm-3">
												<input class="form-control" id="empGraduateUni" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">所学专业</label>
											<div class="col-sm-3">
												<input class="form-control" id="empMajor" type="text" />
											</div>
											<label class="col-sm-1 control-label">专业证书</label>
											<div class="col-sm-3">
												<input class="form-control" id="empCertificate" type="text" />
											</div>
											<label class="col-sm-1 control-label">专业证书编号</label>
											<div class="col-sm-3">
												<input class="form-control" id="empCertifiNo" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">发证日期</label>
											<div class="col-sm-3">
												<input class="form-control laydate-icon layer-date"
													id="empCertifiDate" type="text" onclick="laydate.render({ elem: this});" />
											</div>
											<label class="col-sm-1 control-label">证书有效期</label>
											<div class="col-sm-3">
												<input class="form-control laydate-icon layer-date"
													id="empCertifiExpireDate" type="text" onclick="laydate.render({ elem: this});" />
											</div>
											<label class="col-sm-1 control-label">复审日期</label>
											<div class="col-sm-3">
												<input class="form-control laydate-icon layer-date"
													id="empCertifiCheckDate" type="text" onclick="laydate.render({ elem: this});" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">专业资质证书等级</label>
											<div class="col-sm-3">
												<input class="form-control" id="empCertifiLev" type="text" />
											</div>
											<label class="col-sm-1 control-label">是否有扫描件</label>
											<div class="col-sm-3">
												<select class="form-control" id="empCertifiScan">
													<option value="">---请选择---</option>
													<option value="1">是</option>
													<option value="0">否</option>
												</select>
											</div>
										</div>
									</fieldset>
								</form>
							</div>
							<div id="tab-4" class="tab-pane">
								<form id="myForm4" class="form-horizontal" role="form">
									<fieldset id="">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">用工类型</label>
											<div class="col-sm-4">
												<select class="form-control" id="empEmployType">
													<option value="0" selected="selected">劳动合同</option>
													<option value="1">劳务合同</option>
													<option value="2">聘用合同</option>
													<option value="3">非全日制用工</option>
													<option value="9">其它</option>
												</select>
											</div>
											<label class="col-sm-2 control-label">劳动合同主体</label>
											<div class="col-sm-4">
											<select class="form-control" id="empContractSubject">
													<option value="贵州乐湾物业" selected="selected">贵州乐湾物业</option>
													<option value="高尔夫">高尔夫</option>
													<option value="国际学校-小学">国际学校-小学</option>
													<option value="国际学校-中学">国际学校-中学</option>
													<option value="宏德公司">宏德公司</option>
												</select>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">起始时间</label>
											<div class="col-sm-4">
												<input class="form-control laydate-icon layer-date"
													id="empContractBeginDate" type="text" onclick="laydate.render({ elem: this});" />
											</div>
											<label class="col-sm-2 control-label">终止时间</label>
											<div class="col-sm-4">
												<input class="form-control laydate-icon layer-date"
													id="empContractEndDate" type="text" onclick="laydate.render({ elem: this});" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">合同剩余天数</label>
											<div class="col-sm-4">
												<input class="form-control" id="empContractRemain"
													disabled="disabled" type="text" />
											</div>
											<label class="col-sm-2 control-label">合同到期提醒</label>
											<div class="col-sm-4">
												<input class="form-control" id="empContractMark"
													disabled="disabled" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">劳动关系状态</label>
											<div class="col-sm-4">
												<select class="form-control" id="empLaborStatus">
													<option value="0">在职</option>
													<option value="1">停薪留职</option>
													<option value="2">协保</option>
													<option value="3">离岗退养</option>
													<option value="4">内退</option>
													<option value="5">其他</option>
												</select>
											</div>
										</div>
									</fieldset>
								</form>

							</div>
							<div id="tab-5" class="tab-pane">
								<form id="myForm5" class="form-horizontal" role="form">
									<fieldset id="">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">前工作单位及职务</label>
											<div class="col-sm-7">
												<input class="form-control" id="empPreWorkUnit" type="text" />
											</div>
											<label class="col-sm-1 control-label">初次工作时间</label>
											<div class="col-sm-3">
												<input class="form-control laydate-icon layer-date"
													id="empFirstWorkDate" type="text" onclick="laydate.render({ elem: this});" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">婚姻状况</label>
											<div class="col-sm-3">
												<select class="form-control" id="empMarriage">
													<option value="">---请选择---</option>
													<option value="1">已婚</option>
													<option value="0">未婚</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">生育状况</label>
											<div class="col-sm-3">
												<select class="form-control" id="empBear">
													<option value="">---请选择---</option>
													<option value="1">已生育</option>
													<option value="0">未生育</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">特长</label>
											<div class="col-sm-3">
												<input class="form-control" id="empSpecial" type="text" />
											</div>

										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">户籍地址</label>
											<div class="col-sm-11">
												<input class="form-control" id="empAccountAddress"
													type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">家庭住址</label>
											<div class="col-sm-11">
												<input class="form-control" id="empAddress" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">手机</label>
											<div class="col-sm-3">
												<input class="form-control" id="empMobilePhone" type="text" />
											</div>
											<label class="col-sm-1 control-label">应聘渠道</label>
											<div class="col-sm-3">
												<select class="form-control" id="empApply">
													<option value="">---请选择---</option>
													<option value="员工介绍">员工介绍</option>
													<option value="网站招聘">网站招聘</option>
													<option value="人才市场">人才市场</option>
													<option value="校园招聘">校园招聘</option>
													<option value="其他方式">其他方式</option>
												</select>
											</div>
											<label class="col-sm-1 control-label">是否住宿</label>
											<div class="col-sm-3">
												<select class="form-control" id="empResidence">
													<option value="1">是</option>
													<option value="0">否</option>
												</select>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">紧急联系人电话及关系</label>
											<div class="col-sm-3">
												<input class="form-control" id="empContactInfo" type="text" />
											</div>
											<label class="col-sm-1 control-label">户口</label>
											<div class="col-sm-3">
												<input class="form-control" id="empAccount" type="text" />
											</div>
											<label class="col-sm-1 control-label">户口性质</label>
											<div class="col-sm-3">
												<input class="form-control" id="empAccountProperties"
													type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-1 control-label">备注</label>
											<div class="col-sm-11">
												<textarea rows="3" style="height: 60px" class="form-control"
													id="empRemark"></textarea>
											</div>
										</div>
									</fieldset>
								</form>
							</div>
							<div id="tab-6" class="tab-pane">
								<form id="myForm6" class="form-horizontal" role="form">
									<fieldset id="">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">参保单位编号</label>
											<div class="col-sm-4">
												<input class="form-control" id="empInsuredUnitNo"
													type="text" />
											</div>
											<label class="col-sm-2 control-label">社会保障号</label>
											<div class="col-sm-4">
												<input class="form-control" id="empInsuredNo" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">参保基数</label>
											<div class="col-sm-4">
												<input class="form-control" id="empInsuredFee" type="text" />
											</div>
											<label class="col-sm-2 control-label">参保时间</label>
											<div class="col-sm-4">
												<input class="form-control laydate-icon layer-date"
													id="empInsuredDate" type="text" onclick="laydate.render({ elem: this});" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">汇缴单位</label>
											<div class="col-sm-4">
												<input class="form-control" id="empFundsUnitNo" type="text" />
											</div>
											<label class="col-sm-2 control-label">公积金个人账号</label>
											<div class="col-sm-4">
												<input class="form-control" id="empFundsNo" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">汇缴基数</label>
											<div class="col-sm-4">
												<input class="form-control" id="empFundsFee" type="text" />
											</div>
											<label class="col-sm-2 control-label">汇缴时间</label>
											<div class="col-sm-4">
												<input class="form-control laydate-icon layer-date"
													id="empFundsDate" type="text" onclick="laydate()" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">试用期工资</label>
											<div class="col-sm-4">
												<input class="form-control" id="empProbationWages"
													type="text" />
											</div>
											<label class="col-sm-2 control-label">转正工资</label>
											<div class="col-sm-4">
												<input class="form-control" id="empFullWages" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">转正体现月</label>
											<div class="col-sm-4">
												<input class="form-control laydate-icon layer-date"
													id="empFullWagesDate" type="text" onclick="laydate.render({ elem: this});" />
											</div>
											<label class="col-sm-2 control-label">是否体现</label>
											<div class="col-sm-4">
												<select class="form-control" id="empFullWagesFlag">
													<option value="0" selected="selected">否</option>
													<option value="1">是</option>
												</select>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">现工资</label>
											<div class="col-sm-4">
												<input class="form-control" id="empRealWages" type="text" />
											</div>
											<label class="col-sm-2 control-label">调薪情况</label>
											<div class="col-sm-4">
												<input class="form-control" id="empRaiseWages" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">银行名称</label>
											<div class="col-sm-4">
												<input class="form-control" id="empBankName" type="text" />
											</div>
											<label class="col-sm-2 control-label">银行卡号</label>
											<div class="col-sm-4">
												<input class="form-control" id="empBankCardNo" type="text" />
											</div>
										</div>
									</fieldset>
								</form>
							</div>
						</div>
					</div>
					<div class="form-group form-group-sm "></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btEmpAdd" onclick="openSaveButton()"
							class="btn btn-primary " type="button">保存</button>
					</div>
					<input class="form-control" id="empId" type="hidden" />
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
	</div>
	<script>
		laydate.render({
			elem : '#empBirth',
			//format: 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm1').validationEngine('hide');
			}
		});
		$("#laydate_today").bind('click', function() {
			
			$('#myForm1').validationEngine('hide');
		});
		laydate.render({
			elem : '#empEntryTime',
			//format: 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm1').validationEngine('hide');
			}
		});
		laydate.render({
			elem : '#empQuitTime',
			//format: 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				$('#myForm1').validationEngine('hide');
				var start = $("#empEntryTime").val();
				var end = $("#empQuitTime").val();

				if (start > end && end != "") {
					layer.msg('离职时间不能小于入职时间！', {
						time : 2000
					});
					$("#empQuitTime").val("");
				}
			}
		});
	</script>
</body>

</html>
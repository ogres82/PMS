<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>房间概况</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/basicInfo/houseDetail.js"></script>
</head>
<style>
 .input-sm, .form-horizontal .form-group-sm .form-control{
	height:34px;
  }
 .hidden-xs{
	display:inline !important;
 }
</style>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row animated fadeInDown">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>房间概况 </h5>
					</div>
					<div class="ibox-content">
						<form id="varifyForm" class="form-horizontal" role="form">
							<div class="form-group form-group-sm" style="margin-top:25px" id="ownerSearch">
								<label class="col-sm-1 control-label" >业主查询</label>
								<div class="col-sm-5">
										<div class="input-group">
											<input type="text" class="form-control"  placeholder="姓名/电话" id="searchInput" />
											<span class="input-group-btn">
											<button class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="height:34px;" type="button">
												 <span class="caret"></span>
											</button>
											<ul class="dropdown-menu dropdown-menu-right" role="menu">
											</ul>
										   	</span>
									   </div>
								 </div>
							</div>	
							<div style="width:1000px;margin-left:30px;display:none" id="houseInfo" >
								<table class="table table-bordered white-bg">
		                            <tbody>
		                                <tr bgColor=#EBEBEB>
		                                	<th  rowspan="4" bgColor=#C9C9C9>房间信息</th>
		                                    <td>
		                                        <span id="sparkline1">所属小区</span>
		                                    </td>
		                                    <td id="community">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline1">所属楼栋</span>
		                                    </td>
		                                    <td id="building">
		                                    </td>
		                                </tr>
		                                <tr>
		                                    <td>
		                                        <span id="sparkline2">房间编号</span>
		                                    </td>
		                                    <td id="roomNo">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline3">房间类型</span>
		                                    </td>
		                                    <td id="roomType">
		                                    </td>
		                                </tr>
		                                <tr bgColor=#EBEBEB>
		                                    <td>
		                                        <span id="sparkline3">建筑面积（㎡）</span>
		                                    </td>
		                                    <td id="buildArea">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline3">套内面积（㎡）</span>
		                                    </td>
		                                    <td id="withinArea">
		                                    </td>
		                                </tr>
		                                <tr>
		                                	<td>
		                                        <span id="sparkline3">物管费（每月）</span>
		                                    </td>
		                                    <td id="price">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline3">房间状态</span>
		                                    </td>
		                                    <td id="roomState">
		                                    </td>
		                                </tr>
		                                <tr bgColor=#EBEBEB>
		                                	<th  rowspan="3"  bgColor=#C9C9C9>房间统计</th>
		                                    <td>
		                                        <span id="sparkline4">最近缴费日期</span>
		                                    </td>
		                                    <td id="paidDate">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline4">最近缴费金额（元）</span>
		                                    </td>
		                                    <td id="paidAmount">
		                                    </td>
		                                </tr>
		                                <tr>
		                                    <td>
		                                        <span id="sparkline5">累计应收金额（元）</span>
		                                    </td>
		                                    <td id="receiveTotal">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline5">累计实收金额（元）</span>
		                                    </td>
		                                    <td id="paidTotal">
		                                    </td>
		                                </tr>
		                                <tr bgColor=#EBEBEB>
		                                    <td>
		                                        <span id="sparkline6">累计欠费金额（元）</span>
		                                    </td>
		                                    <td id="arrearageTotal">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline6">预存余额（元）</span>
		                                    </td>
		                                    <td id="amount">
		                                    </td>
		                                </tr>
		                                <tr>
		                                	<th  rowspan="5" bgColor=#C9C9C9>业主资料</th>
		                                    <td>
		                                        <span id="sparkline4">业主姓名</span>
		                                    </td>
		                                    <td id="ownerName">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline5">属性</span>
		                                    </td>
		                                    <td id="ownerIdentity">
		                                    </td>
		                                </tr>
		                                <tr bgColor=#EBEBEB>
		                                    <td>
		                                        <span id="sparkline6">业主类别</span>
		                                    </td>
		                                    <td id="ownerType">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline6">手机号</span>
		                                    </td>
		                                    <td id="phone">
		                                    </td>
		                                </tr>
		                                <tr>
		                                    <td>
		                                        <span id="sparkline6">身份证号</span>
		                                    </td>
		                                    <td id="cardId">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline6">出生日期</span>
		                                    </td>
		                                    <td id="birthDate">
		                                    </td>
		                                </tr>
		                                <tr bgColor=#EBEBEB>
		                                    <td>
		                                        <span id="sparkline6">交房日期</span>
		                                    </td>
		                                    <td id="receiveRoomDate">
		                                    </td>
		                                    <td>
		                                        <span id="sparkline6">装修日期</span>
		                                    </td>
		                                    <td id="decorateStartDate">
		                                    </td>
		                                </tr>
		                                <tr>
		                                    <td>
		                                        <span id="sparkline6">入住日期</span>
		                                    </td>
		                                    <td id="decorateEndDate">
		                                    </td>
		                                </tr>
		                            </tbody>
		                        </table>
                    		</div>
						</form>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
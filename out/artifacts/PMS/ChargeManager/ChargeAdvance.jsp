<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    request.setAttribute("ContextPath", path);
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>缴费信息</title>
    <%@ include file="/common/taglibs.jsp" %>

    <script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
    <script type="text/javascript"
            src="${ContextPath}/ChargeManager/ChargeAdvance.js"></script>
    <link href="${ContextPath}/common/fileinput/css/fileinput.min.css"
          rel="stylesheet"/>
    <script type="text/javascript"
            src="${ContextPath}/common/fileinput/js/fileinput.min.js"></script>
    <script type="text/javascript"
            src="${ContextPath}/common/fileinput/js/locales/zh.js"></script>

</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="col-sm-12">
        <div class="ibox">
            <div class="ibox-title">
                <div class="col-sm-5" style="top: -10px;">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="房号/姓名/电话"
                               id="searchInput"/> <span class="input-group-btn">
								<button class="btn btn-white dropdown-toggle"
                                        data-toggle="dropdown" type="button">
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu dropdown-menu-right" role="menu">
								</ul>
							</span>
                    </div>
                </div>
                <div class="col-sm-4" style="top: -10px;">
                    <button id="clearSearch" onclick="clearSearch()"
                            class="btn btn-primary" type="button">清空
                    </button>
                </div>
            </div>
            <!-- 数据表格区域  -->
            <div class="ibox-content">
                <div class="row">
                    <div class="col-sm-3">
                        <select class="form-control" id="charge_type_select" onchange="refreshChargeSerial()">
                            <option disabled selected style='display: none;' value="">收费类型</option>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <select class="form-control" id="paid_mode_select" onchange="refreshChargeSerial()">
                            <option disabled selected style='display: none;' value="">收款方式</option>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" id="beginTime"
                               class="form-control layer-date"
                               onclick="laydate.render({elem: this,done: function(value, date, endDate) {refreshChargeSerial();}});"
                               placeholder="开始时间"/>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" id="endTime"
                               class="form-control layer-date"
                               onclick="laydate.render({elem: this,done: function(value, date, endDate) {refreshChargeSerial();}});"
                               placeholder="结束时间"/>
                    </div>
                </div>
                <div id="toolbar"></div>
                <table class="table-no-bordered" id="chargeAdvance"></table>
                <input id="roomId" type="hidden"/>
                <input id="ownerId" type="hidden"/>
                <input id="docUrls" type="hidden"/>
                <input id="oper_emp_id" type="hidden">
            </div>
        </div>
    </div>
</div>

</body>
</html>
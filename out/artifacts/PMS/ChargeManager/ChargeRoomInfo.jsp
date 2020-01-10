<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    request.setAttribute("ContextPath", path);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>物业费明细</title>
    <%@ include file="/common/taglibs.jsp" %>

    <script type="text/javascript" src="${ContextPath}/ChargeManager/ChargeRoomInfo.js"></script>
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
    <i class="fa fa-search"
       style="position: fixed; right: 17px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
    <!-- 占位div -->
    <div class="" style="width: 100%; height: 45px;"></div>
    <!-- 占位div -->


    <div class="row">
        <div class="col-sm-12">
            <blockquote class="layui-elem-quote" style="background-color: white">
                <div class="row">
                    <div class="col-sm-2">
                        <select class="form-control" name="type" id="communityNameSearch">
                            <option disabled selected style='display: none;' value="">所属小区</option>
                        </select>
                    </div>
                    <div class="col-sm-2">
                        <select class="form-control" name="type" id="roomTypeSearch">
                            <option disabled selected style='display: none;' value="">房间类型</option>
                        </select>
                    </div>
                    <div class="col-sm-2">
                        <select class="form-control" name="type" id="roomStateSearch">
                            <option disabled selected style='display: none;' value="">房间状态</option>
                        </select>
                    </div>
                    <div class="col-sm-2" style="float: right">
                        <button id="buttonSearch" class="btn btn-primary" type="button">查询</button>
                        <button id="clearSearch" onclick="clearSearch()" class="btn btn-primary" type="button">清空
                        </button>
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
                        物业费信息<small></small>
                    </h5>
                </div>
                <div class="ibox-content" id="tempTable">
                    <div id="toolbar"></div>
                    <table class="table-no-bordered" id="chargeRoomInfo"></table>
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
                    <h4 class="modal-title" id="myModalLabel" style="color: white">缴费明细</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group form-group-sm ">
                        <table class="table-no-bordered" id="chargeAdvance"></table>
                    </div>
                    <div class="modal-footer">
                        <input id="roomId" type="hidden">
                        <button type="button" id="button_close" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>
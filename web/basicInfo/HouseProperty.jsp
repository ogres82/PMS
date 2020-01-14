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
    <title>房间信息</title>
    <%@ include file="/common/taglibs.jsp" %>
    <link href="fileinput.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="fileinput.min.js"></script>

    <script type="text/javascript"
            src="${ContextPath}/basicInfo/HouseProperty.js"></script>
    <script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>

</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">

    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
    <div
            style="width: 100%; height: 55px; background-color: #f3f3f4; position: fixed; left: 0; top: 0; padding: 0 10px; z-index: 1000">
        <div class="row">
            <div class="col-md-6" style="float: left; margin-top: 10px;"
                 id="topToolbar"></div>

        </div>
    </div>
    <i class="fa fa-search"
       style="position: fixed; right: 122px; top: 19px; z-index: 1001; color: #e7eaec; font-size: 15px;"></i>
    <!-- 占位div -->
    <div class="" style="width: 100%; height: 45px;"></div>
    <!-- 占位div -->

    <div class="row">
        <div class="col-sm-12">
            <blockquote class="layui-elem-quote" style="background-color: white">
                <div class="row">
                    <div class="col-sm-2">
                        <select class="form-control" name="type" id="communityNameSearch">

                        </select>
                    </div>
                    <div class="col-sm-2">
                        <select class="form-control" name="type"
                                id="storiedBuildNameSearch">
                        </select>
                    </div>
                    <div class="col-sm-2">
                        <select class="form-control" name="type" id="unitNameSearch">
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
                    <div class="col-sm-2">
                        <button id="buttonSearch" class="btn btn-primary" type="button">查询</button>
                        <button id="clearSearch" onclick="clearSearch()"
                                class="btn btn-primary" type="button">清空
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
                        房间信息<small></small>
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
        <div class="modal-dialog" style="width: 1020px">
            <div class="modal-content">
                <div class="modal-header" style="background: #18a689">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel" style="color: white">房间信息</h4>
                </div>
                <div class="modal-body">
                    <div class="tab-content">
                        <div id="tab-1" class="tab-pane active">
                            <form id="myForm1" class="form-horizontal" role="form">

                                <fieldset id="basicInfo">
                                    <legend style="font-size: 15px; border: 0;"></legend>

                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">所属楼盘</label>
                                        <div class="col-sm-4">
                                            <select class="form-control validate[required]" id="buildId">
                                                <option value="">---请选择---</option>
                                            </select>
                                        </div>
                                        <label class="col-sm-2 control-label">所属小区</label>
                                        <div class="col-sm-4">
                                            <select class="form-control validate[required]"
                                                    id="communityId">
                                                <option value="">---请选择---</option>
                                            </select>
                                        </div>

                                    </div>
                                    <div class="form-group form-group-sm ">

                                        <label class="col-sm-2 control-label">所属楼宇</label>
                                        <div class="col-sm-4">
                                            <select class="form-control validate[required]"
                                                    id="belongSbId">
                                                <option value="">---请选择---</option>
                                            </select>
                                        </div>
                                        <label class="col-sm-2 control-label">所属单元</label>
                                        <div class="col-sm-4">
                                            <select class="form-control validate[required]" id="unitId">
                                                <option value="">---请选择---</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">房间类型</label>
                                        <div class="col-sm-4">
                                            <select class="form-control validate[required]"
                                                    id="roomType">
                                                <option value="">---请选择---</option>
                                            </select>
                                        </div>
                                        <label class="col-sm-2 control-label">房间状态</label>
                                        <div class="col-sm-4">
                                            <select class="form-control" id="roomState">
                                                <option value="">---请选择---</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">房间号</label>
                                        <div class="col-sm-4">
                                            <input class="form-control validate[required]" id="roomNo"
                                                   type="text" placeholder="必填项"/>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">建筑面积</label>
                                        <div class="col-sm-4">
                                            <input
                                                    class="form-control validate[required,custom[number]]"
                                                    id="buildArea" type="text" placeholder="必填项"/>
                                        </div>
                                        <label class="col-sm-2 control-label">套内面积</label>
                                        <div class="col-sm-4">
                                            <input class="form-control validate[custom[number]]"
                                                   id="withinArea" type="text" placeholder="必填项"/>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">地产收房日期</label>
                                        <div class="col-sm-4">
                                            <input class="form-control laydate-icon layer-date"
                                                   id="makeRoomDate" type="text"/>
                                        </div>
                                        <label class="col-sm-2 control-label">实际收房日期</label>
                                        <div class="col-sm-4">
                                            <input class="form-control laydate-icon layer-date"
                                                   id="receiveRoomDate" type="text"/>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">备注</label>
                                        <div class="col-sm-10">
                                            <input class="form-control" id="remark" type="text"/>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset id="otherInfo">
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">房间信息</label>
                                        <div class="col-sm-8">
                                            <input class="form-control" id="roomAddrs" type="text" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">业主信息</label>
                                        <div class="col-sm-8">
                                            <input class="form-control" id="ownerInfo" type="text" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">费用信息</label>
                                        <div class="col-sm-8">
                                            <input class="form-control" id="chargeInfo" type="text"
                                                   readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">联掌信息</label>
                                        <div class="col-sm-8">
                                            <input class="form-control" id="lzmhInfo" type="text" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">客户</label>
                                        <div class="col-sm-8">
                                            <div class="input-group">
                                                <input type="text" class="form-control validate[required]" id="owner"
                                                       placeholder="业主姓名/电话号码">
                                                <div class="input-group-btn">
                                                    <button type="button" class="btn btn-white btn-sm dropdown-toggle"
                                                            data-toggle="dropdown">
                                                        <span class="caret"></span>
                                                    </button>
                                                    <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">新业主信息</label>
                                        <div class="col-sm-8">
                                            <input class="form-control" id="newOwnerInfo" type="text" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">收费项</label>
                                        <div class="col-sm-8">
                                            <select class="form-control" id="chargeType">
                                                <option value="">---请选择---</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group form-group-sm ">
                                        <label class="col-sm-2 control-label">新费用信息</label>
                                        <div class="col-sm-8">
                                            <input class="form-control" id="newChargeInfo" type="text"
                                                   readonly="readonly"/>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="btHousePropertyAdd" onclick="openSaveButton()"
                            class="btn btn-primary " type="button">保存
                    </button>
                    <button type="button" id="unOwner" class="btn btn-primary"
                            onclick="manage('unOwner')">解除绑定业主
                    </button>
                    <button type="button" id="repOwner" class="btn btn-primary"
                            onclick="manage('repOwner')">更换业主
                    </button>
                    <button type="button" id="repCharge" class="btn btn-primary"
                            onclick="manage('repCharge')">修改房间费用
                    </button>
                    <button type="button" id="sycnLz" class="btn btn-primary"
                            onclick="manage('sycnLz')">同步联掌
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <input class="form-control" id="roomId" type="hidden"/>
                    <input class="form-control" id="lzRoomId" type="hidden"/>
                    <input class="form-control" id="lzRoomOwnerId" type="hidden"/>
                    <input class="form-control" id="onwerId" type="hidden"/>
                </div>
            </div>
        </div>
        <!-- 弹出窗口区域，触发事件后弹出   结束 -->
    </div>
</div>

</body>

</html>
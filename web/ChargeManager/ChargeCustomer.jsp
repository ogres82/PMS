<%@ page import="com.bstek.bdf2.core.context.ContextHolder" %>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    request.setAttribute("ContextPath", path);
    DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
    request.setAttribute("loginUser", user);
%>
<!DOCTYPE html>
<html>

<head>
    <title>客户收费</title>
    <%@ include file="/common/taglibs.jsp" %>
    <link rel="shortcut icon" href="../Hplus/favicon.ico">
    <link href="${ContextPath}/Hplus/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${ContextPath}/Hplus/css/style.min.css?v=4.0.0" rel="stylesheet">

    <script type="text/javascript" src="${ContextPath}/ChargeManager/ChargeCustomer.js"></script>
    <script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
    <link href="${ContextPath}/common/fileinput/css/fileinput.min.css"
          rel="stylesheet"/>
    <script type="text/javascript"
            src="${ContextPath}/common/fileinput/js/fileinput.min.js"></script>
    <script type="text/javascript"
            src="${ContextPath}/common/fileinput/js/locales/zh.js"></script>
    <script type="text/javascript">
        var loginUserName = "${loginUser.username}";
        var loginUserCname = "${loginUser.cname}";
    </script>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-6">
            <div class="ibox">
                <div class="ibox-content">
                    <div class="input-group">
                        <input type="text" placeholder="查找业主  (房号、业主、电话、楼宇)" class="form-control" id="searchClient">
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-right" role="menu">
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="ibox">
                <div class="ibox-content">
                    <form id="myFrom" class="form-horizontal">
                        <div class="row">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">房号</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" readonly="readonly" id="roomAddrs">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">业主</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" readonly="readonly" id="ownerInfo">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">物业费信息</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" readonly="readonly" id="chargeInfo">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">应收合计</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" readonly="readonly" id="totalCharge">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">优惠合计</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="totalReduceMount" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">实收合计</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="totalPaidAmount" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">付款方式</label>
                                <div class="col-sm-9">
                                    <select class="form-control" id="paidMode">
                                        <option value="">---请选择---</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">收费时间</label>
                                <div class="col-sm-9">
                                    <input class="form-control validate[required] layer-date" id="paidDate"
                                           placeholder="必填项 " type="text"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">单&nbsp;&nbsp;&nbsp;据&nbsp;&nbsp;&nbsp;号</label>
                                <div class="col-sm-9">
                                    <input type="text" id="receiptId" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</label>
                                <div class="col-sm-9">
                                    <input type="text" id="remark" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">减免凭证</label>
                                <div class="col-sm-9">
                                    <input id="input-b5" type="file" class="file" >
                                    <input type="hidden" id="reduceUrl">
                                </div>
                            </div>
                        </div>
                        <div class="" style="margin-top: 20px; margin-left: 200px">
                            <button id="btnPayee" class="btn btn-primary" type="button" onclick="paid()">&nbsp;确认收款
                            </button>
                        </div>
                        <input type="hidden" id="roomId">
                        <input type="hidden" id="ownerId">
                        <input type="hidden" id="roomNo">
                        <input type="hidden" id="phone">
                        <input type="hidden" id="ownerName">
                        <input type="hidden" id="expDate">
                        <input type="hidden" id="communityName">
                        <input type="hidden" id="storiedBuildName">
                        <input type="hidden" id="unitName">
                        <input type="hidden" id="roomType">
                    </form>
                </div>
            </div>

        </div>


        <div class="col-sm-6">
            <div class="ibox ">
                <div class="ibox-title">
                    <div class="row">
                        <div class="form-group ">
                            <div class="col-sm-12">
                                <button id="btnAdd" class="btn btn-primary" type="button" disabled="disabled">
                                    <i class="fa fa-plus"></i>&nbsp;添加费用
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="form-group form-group-sm ">
                            <div class="col-sm-12 full-height-scroll">
                                <table class="table table-striped table-hover" id="chargeCustomer"></table>
                            </div>
                        </div>

                    </div>
                </div>
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
                <h4 class="modal-title" id="myModalLabel" style="color: white">新增费用</h4>
            </div>
            <div class="modal-body">
                <form id="myForm" class="form-horizontal" method="post">
                    <div class="form-group form-group-sm ">
                        <label class="col-sm-2 control-label">项目名称</label>
                        <div class="col-sm-6">
                            <select class="form-control validate[required]" id="chargeType"
                                    onchange="chargeTypeChange()">
                            </select>
                            <input type="hidden" id="chargeTypeNo">
                            <input type="hidden" id="chargeTypeName">
                            <input type="hidden" id="chargeTypeId">
                        </div>
                    </div>
                    <div class="form-group form-group-sm">
                        <label class="col-sm-2 control-label">面积</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" readonly="readonly" id="buildArea">
                        </div>
                    </div>
                    <div class="form-group form-group-sm">
                        <label class="col-sm-2 control-label">单价</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" readonly="readonly" id="chargePrice">
                        </div>
                    </div>
                    <div class="form-group form-group-sm ">
                        <label class="col-sm-2 control-label">实收金额</label>
                        <div class="col-sm-6">
                            <input class="form-control" id="receiveAmount"
                                   type="text" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="form-group form-group-sm typeNum" style="display:none">
                        <label class="col-sm-2 control-label">数量</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control text-center" value="1" id="typeNum"
                                   onchange="chargeTypeChange()">
                        </div>
                    </div>
                    <div class="form-group form-group-sm monthsNum" style="display:none">
                        <label class="col-sm-2 control-label">月份数量</label>
                        <div class="col-sm-6">
                            <select class="form-control monthsNum" id="monthsNum" onchange="chargeTypeChange()">
                                <option selected value=1>1个月</option>
                                <option value=3>3个月</option>
                                <option value=6>6个月</option>
                                <option value=12>1年</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group form-group-sm ">
                        <label class="col-sm-2 control-label">应收金额</label>
                        <div class="col-sm-6">
                            <input class="form-control validate[required,custom[number]]" id="arrAmount"
                                   type="text" placeholder="必填项 "/>
                        </div>
                    </div>
                    <div class="form-group form-group-sm ">
                        <label class="col-sm-2 control-label">减免金额</label>
                        <div class="col-sm-6">
                            <input class="form-control validate[required,custom[number]]" id="reduceMount" value="0.00"
                                   type="text" placeholder="必填项 " onchange="AccReduce(this.value)"/>
                        </div>
                    </div>

                    <div class="form-group form-group-sm ">
                        <label class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-6">
                            <input class="form-control validate[required] layer-date " id="beginDate"
                                   placeholder="必填项 " type="text"/>
                        </div>
                    </div>
                    <div class="form-group form-group-sm ">
                        <label class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-6">
                            <input class="form-control layer-date validate[required] " id="endDate"
                                   placeholder="必填项 " type="text"/>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="button_close" class="btn btn-default" data-dismiss="modal">关闭
                        </button>
                        <button type="button" id="button_save" onclick="save()" class="btn btn-primary">保存</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>

</html>
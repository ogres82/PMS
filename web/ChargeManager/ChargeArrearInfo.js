$(function () {
    toolbarBtnInit();

    initAreaPropertyDrop("");
    for (var i = 0; i < btnIdArray.length; i++) {
        if (btnIdArray[i] == "btn_send") {
            $("#btn_send").bind('click', function () {
                btnSend();
            });
        }
    }
    // 初始化Table
    var oTable = new TableInit();
    oTable.Init();


    // //初始化详情Table
    var detailSend = new sendedTableInit();
    detailSend.Init();
    //
    // 查询按钮
    $('#buttonSearch').click(function () {
        $('#chargeArrearInfo').bootstrapTable('refresh', null);
    });

    $(".search").css({
        'position': 'fixed',
        'right': '10px',
        'top': '0',
        'z-index': '1000',
        'width': '240px'
    });
    $(".search input").attr("placeholder", "搜索");
    $(".search input").css({
        'padding-right': '23px'
    });

});

function btnSend() {
    var arr = $('#chargeArrearInfo').bootstrapTable('getSelections');
    if (arr.length == 0) {
        layer.alert("请选择记录记录后操作", {
            skin: 'layui-layer-molv'
        });
        return;
    } else {
        showTemp();
    }

}

var TableInit = function () {
    var oTableInit = new Object();
    oTableInit.Init = function () {
        $('#chargeArrearInfo').bootstrapTable({
            url: getRootPath() + "ChargeManager/ChargeRoomInfoList.app?method=arrearage", // 请求后台的URL（*）
            toolbar: '#toolbar', // 工具按钮用哪个容器
            striped: true,
            showFooter: true,
            search: true,
            searchOnEnterKey: true,
            showColumns: true, // 是否显示所有的列
            sortable: false,
            sortOrder: "asc",
            sidePagination: "client", // 分页方式：client 客户端分页，server服务端分页（*）
            cache: false,
            queryParams: oTableInit.queryParams,
            showExport: "true",
            minimumCountColumns: 2, // 最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 10, // 每页的记录行数（*）
            pageList: '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
            columns: [{
                field: 'sta',
                checkbox: true
            }, {
                field: 'communityId',
                visible: false
            }, {
                field: 'communityName',
                visible: false
            }, {
                field: 'storiedBuildId',
                visible: false
            }, {
                field: 'storiedBuildName',
                visible: false
            }, {
                field: 'unitId',
                visible: false
            }, {
                field: 'unitName',
                visible: false
            }, {
                field: 'roomId',
                visible: false
            }, {
                field: 'roomNo',
                visible: false
            }, {
                field: 'ownerId',
                visible: false
            },  {
                field: 'delyMonths',
                visible: false
            }, {
                field: 'delyDays',
                visible: false
            }, {
                field: 'roomAddrs',
                title: '房号'
            }, {
                field: 'ownerName',
                title: '业主'
            }, {
                field: 'phone',
                title: '电话'
            }, {
				field: 'monthsPrice',
				title: '每月应收费'
			}, {
                field: 'receiveAmount',
                title: '欠费金额'
            }, {
                field: 'delayPay',
                title: '滞纳金',
                formatter: countLateFeeFormate
            }, {
                field: 'totalPay',
                title: '总计',
                formatter: sumFeeFormate
            }, {
                field: 'beginDate',
                title: '开始时间',
                formatter: dateFormate
            }, {
                field: 'endDate',
                title: '结束时间',
                formatter: dateFormate
            }, {
                field: 'operate',
                title: '操作',
                align: 'center',
                events: operateEvents,
                formatter: operateFormatter
            }]
        });
    };

    // 操作列的显示风格
    function operateFormatter(value, row, index) {
        return tableBtnInit();
    }

    // 操作列的事件
    window.operateEvents = {
        'click #a_sended': function (e, value, row, index) {
            showSendHistory(row);
        }
    };

    oTableInit.queryParams = function (params) {
        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码
            search: params.search,
            communityNameSearch: $("#communityNameSearch").val(),
            ArrNum: $("#chargeArrNum").val()
        };
        return temp;
    };
    dateFormate = function (value) {
        if (value == null || value == "") {
            return "";
        } else {
            return json2Date(value);
        }

    };
    countLateFeeFormate = function (value, row, index) {
        var delyDays;

        if (row.delyMonths == 0) {
            delayPay = row.receiveAmount * 0.003 * row.delyDays;
        } else {
            delayPay = row.monthsPrice * 0.003 * adds(0, row.delyMonths, 1) * 30;
        }
        return Math.round(delayPay);
    };
    sumFeeFormate = function (value, row, index) {
        var delyDays;
        if (row.delyMonths === 0) {
            delyDays = row.receiveAmount * 0.003 * row.delyDays;
        } else {
            delyDays = row.monthsPrice * 0.003 * adds(0, row.delyMonths, 1) * 30;
        }
        var totalPay = row.receiveAmount + Math.round(delyDays);
        return totalPay.toFixed(2);
    };
    numberFormate = function (value, row) {
        return outputmoney("" + value);
    };
    return oTableInit;
};

// 已发送的短信
var sendedTableInit = function () {
    var oTableInit = new Object();
    oTableInit.Init = function () {
        $('#sendHistory').bootstrapTable({
            url:  getRootPath() + 'ChargeManager/ChargeInfoList.app?method=sendMessageHistory', // 请求后台的URL（*）
            striped: true,
            showFooter: true,
            showColumns: true, // 是否显示所有的列
            sortable: false,
            pagination: true,
            sortOrder: "asc",
            sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            queryParams: oTableInit.queryParams,
            showExport: "true",
            minimumCountColumns: 2, // 最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 10000, // 每页的记录行数（*）
            pageList: '[10000]', // 可供选择的每页的行数（*）
            columns: [{
                field: 'sta',
                checkbox: true
            }, {
                field: 'create_time',
                title: '发送时间',
                formatter: dateFormate
            }, {
                field: 'create_name',
                title: '创建人'
            }, {
                field: 'receive_name',
                title: '接收人'
            }, {
                field: 'phone',
                title: '电话'
            }, {
                field: 'content',
                title: '内容'
            }]
        });
    };

    oTableInit.queryParams = function (params) {
        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码
            search: params.search
        };
        return temp;
    };
    dateFormate = function (value) {
        if (value == null || value == "") {
            return "";
        } else {
            return json2TimeStamp(value);
        }

    };

    return oTableInit;

};

// 去掉html标签
function delHtmlTag(str) {
    return str.replace(/<[^>]+>/g, "");// 去掉所有的html标记
}

function showTemp() {
    var msgType = $('#msg_type').val();
    var arr = $('#chargeArrearInfo').bootstrapTable('getSelections');
    var ownerName = "";
    $.each(arr, function (index, item) {
        ownerName += item.ownerName + ",";
    });
    if (msgType == '0') {
        var url = "./../msgandnotice/mgssend.app?method=getMsgTempContent&tempId=40286e815561e8a7015561eb3a1a0000";
    } else {
        var url = "./../msgandnotice/mgssend.app?method=getMsgTempContent&tempId=f4c97577a1a111e7b9911051721b3e1f";
    }
    $.post(url, function (data) {
        var list = eval(data);
        var delHtmlContent = delHtmlTag(list.msgTempContent);
        $("#msgContentText").val(delHtmlContent);
        $("#msgContent").val(delHtmlContent);
    });

    $('#sendModal').modal();
    $("#msgReceiverNames").val(ownerName);
}


function send() {
    var arr = $('#chargeArrearInfo').bootstrapTable('getSelections');
    sendMessage(arr);
}

// 欠费短信
function sendMessage(obj) {
    var selects = JSON.stringify(obj);

    var data = {
        arrearInfo: selects,
        msgType: $("#msg_type").val()
    }

    $.ajax({
        type: "post",
        data: data,
        url: getRootPath() + 'ChargeManager/ChargeInfoList.app?method=sendMessage',
        async: true,
        success: function (data) {
            layer.msg('发送成功！', {
                time: 1000
            }, function () {

            });
        },
        failure: function (data) {
            layer.msg('发送失败！', {
                time: 1000
            }, function () {

            });
        }

    });
}


// 显示详情
function showSendHistory(obj) {

    $('#sendHistory').bootstrapTable('refresh', {
        query: {
            phone: obj.phone
        }
    });
    $("#mySendModal").modal();
}

/**
 * 初始化小区下拉框
 */
function initAreaPropertyDrop(buildId) {
    var url = "./../houseProperty/housePropertyList.app?method=initAreaPropertyDrop";
    $.post(url, {
        buildId: buildId
    }, function (data) {
        var list = eval(data);
        for (var j = 0; j < list.length; j++) {
            var detailList = list[j];
            $("<option value='" + detailList.communityName + "'>" + detailList.communityName + "</option>").appendTo("#communityNameSearch");
        }
        ;
    });
}

function clearSearch() {
    $("#communityNameSearch").val("");
    $("#chargeArrNum").val("");
    $("#buttonSearch").click();
}
//累加函数用户计算 滞纳金
function adds(starNum, endNum, step) {
    var sum = 0;
    for (var i = starNum; i <= endNum; i = i + step) {
        sum += i;
    }
    return sum;
}
$(function () {
    toolbarBtnInit();
    initDrops();
    initAreaPropertyDrop("");

    // 初始化Table
    var oTable = new TableInit();
    oTable.Init();


    var advance = new advanceTableInit();
    advance.Init();

    // 查询按钮
    $('#buttonSearch').click(function () {
        $('#chargeRoomInfo').bootstrapTable('refresh', null);
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
var TableInit = function () {
    var oTableInit = new Object();
    oTableInit.Init = function () {
        $('#chargeRoomInfo').bootstrapTable({
            url: getRootPath() + "ChargeManager/ChargeRoomInfoList.app?method=list", // 请求后台的URL（*）
            toolbar: '#toolbar', // 工具按钮用哪个容器
            striped: true,
            showFooter: true,
            search: true,
            searchOnEnterKey: true,
            showColumns: true, // 是否显示所有的列
            showRefresh: true, // 是否显示刷新按钮
            showToggle: true, // 是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server", // 分页方式：client客户端分页，server 服务端分页（*）
            cache: false,
            // clickToSelect: true,
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
                title: '序号',// 标题 可不加
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'roomId',
                visible: false,
                title: '房间ID'
            }, {
                field: 'communityName',
                visible: false,
                title: '小区名称'
            }, {
                field: 'storiedBuildName',
                visible: false,
                title: '楼宇名称'
            }, {
                field: 'roomNo',
                visible: false,
                title: '房间编号'
            }, {
                field: 'roomType',
                visible: false,
                title: '房屋类型'
            }, {
                field: 'roomState',
                visible: false
            }, {
                field: 'roomStateName',
                title: '房间状态'
            }, {
                field: 'roomAddrs',
                title: '栋号'
            }, {
                field: 'ownerName',
                title: '业主姓名'
            }, {
                field: 'phone',
                title: '业主电话'
            }, {
                field: 'makeRoomDate',
                title: '物业费起征时间',
                formatter: dataFormat
            }, {
                field: 'buildArea',
                title: '面积'
            }, {
                field: 'chargePrice',
                title: '单价'
            }, {
                field: 'monthsPrice',
                title: '每月应收费'
            }, {
                field: 'totalPaidAmount',
                title: '缴费合计',
                formatter: function (value, row, index) {
                    return '<a class="a_check" href="javascript:void(0)" title="缴费明细" >' + value + '</a> ';
                },
                events: checkEvents
            }, {
                field: 'amount',
                title: '余额'
            }, {
                field: 'beginDate',
                title: '催交日期',
                formatter: dataFormat
            }, {
                field: 'endDate',
                title: '截止日期',
                formatter: dataFormat
            }, {
                field: 'arrearsState',
                title: '欠费状态',
                formatter: function (value, row, index) {
                    var a = "";
                    if (value == "欠费") {
                        var a = '<span style="color:#c12e2a;"><i class="fa fa-times-circle-o" aria-hidden="true"></i>' + value + '</span>';
                    } else if (value == "未欠费") {
                        var a = '<span style="color:#3e8f3e"><i class="fa fa-check-circle-o" aria-hidden="true"></i>' + value + '</span>';
                    } else {
                        var a = '<span style="color:#1e438f"><i class="fa fa-exclamation-circle" aria-hidden="true"></i>' + "未计费" + '</span>';
                    }
                    return a;
                }
            }, {
                field: 'receiveAmount',
                title: '欠费金额'
            }]
        });
    };

    function dataFormat(value, row, index) {
        if (value != '') {
            return json2Date(value);
        } else {
            return '';
        }
    };
    //显示缴费详情
    window.checkEvents = {
        'click .a_check': function (e, value, row, index) {
            var url = getRootPath() + "ChargeManager/ChargeRoomInfoList.app?method=payRec&typeFlag='01'" + "&roomId='" + row.roomId + "'";
            $("#chargeAdvance").bootstrapTable('refresh', {url: url});
            $("#myModal").modal();
        }
    };

    // 操作列的显示风格
    function operateFormatter(value, row, index) {
        return tableBtnInit();
    }

    oTableInit.queryParams = function (params) {
        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码
            search: params.search,
            communityId: $('#communityNameSearch').val(),
            roomState: $('#roomStateSearch').val(),
            roomType: $('#roomTypeSearch').val()
        };
        return temp;
    };
    return oTableInit;
};


// 初始化下拉框
function initDrops() {
    var url = getRootPath() + "houseProperty/housePropertyList.app?method=initDropAll";
    $.post(url, function (data) {

        var list = eval(data);
        for (var j = 0; j < list.length; j++) {
            var detailList = list[j];
            var code = detailList[0];
            if (code == 'room_type') {
                $("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#roomTypeSearch");
            }
            if (code == 'room_state') {
                ;
                $("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#roomStateSearch");
            }

        }
        ;
    });

}

/**
 * 初始化小区下拉框
 */
function initAreaPropertyDrop(buildId) {
    var url = getRootPath() + "houseProperty/housePropertyList.app?method=initAreaPropertyDrop";
    $.post(url, {
        buildId: buildId
    }, function (data) {
        var list = eval(data);
        for (var j = 0; j < list.length; j++) {
            var detailList = list[j];
            $("<option value='" + detailList.communityId + "'>" + detailList.communityName + "</option>").appendTo("#communityNameSearch");
        }
        ;
    });
}

function clearSearch() {
    $("#communityNameSearch").val("");
    $("#roomTypeSearch").val("");
    $("#roomStateSearch").val("");
    $('#chargeRoomInfo').bootstrapTable('refresh', null);
}


var advanceTableInit = function () {
    var oTableInit = new Object();
    oTableInit.Init = function () {
        $('#chargeAdvance').bootstrapTable({
            //url: "", // 请求后台的URL（*）
            striped: true,
            pagination: false,
            sortOrder: "asc",
            //url:getRootPath() + "ChargeManager/ChargeRoomInfoList.app?method=payRec&typeFlag=01",
            sidePagination: "client", // 分页方式：client客户端分页，server服务端分页（*）
            pagination: true,
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 5, // 每页的记录行数（*）
            pageList: '[5, 10, 15, 20,ALL]', // 可供选择的每页的行数（*）
            columns: [{
                field: 'chargeTypeName',
                title: '收费项名称'
            }, {
                field: 'paidAmount',
                title: '实收金额'
            }, {
                field: 'paidDate',
                title: '收款日期',
                formatter: dateFormate
            }, {
                field: 'paidModeName',
                title: '收款方式'
            }, {
                field: 'operEmpName',
                title: '收款人'
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

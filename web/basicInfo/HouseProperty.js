selections = [];
var options = {
    buildCtrlId: "buildId",
    communityCtrlId: "communityId",
    belongCtrlId: "belongSbId",
    unitCtrlId: "unitId"
};
var roomFilter;

var optionsSearch = {
    communityCtrlId: "communityNameSearch",
    belongCtrlId: "storiedBuildNameSearch",
    unitCtrlId: "unitNameSearch"
};
var roomSearch;

$(function () {
    initPermissions();
    init();
    initDrops();
    roomFilter = new RoomFilter(options);
    roomSearch = new RoomFilter(optionsSearch);
    // 定位表格查询框
    $(".search").css({
        'position': 'fixed',
        'right': '10px',
        'top': '0',
        'z-index': '1000',
        'width': '200px'

    });
    $(".search input").attr("placeholder", "搜索");
    $(".search input").css({
        'padding-right': '15px'
    });
    $('#myForm1').validationEngine();

    initBtnEvent();

    $('#buttonSearch').click(function () {
        $('#housePropertyInfo').bootstrapTable('refresh');
    });
    laydate.render({
        elem: '#makeRoomDate',
        // format: 'YYYY年MM月DD日',
        festival: true, // 显示节日
        choose: function (datas) { // 选择日期完毕的回调
            $('#myForm1').validationEngine('hide');
        }
    });

    laydate.render({
        elem: '#receiveRoomDate',
        // format: 'YYYY年MM月DD日',
        festival: true, // 显示节日
        choose: function (datas) { // 选择日期完毕的回调
            $('#myForm1').validationEngine('hide');
        }
    });

});

function initPermissions() {
    toolbarBtnInit(); // 初始化工具条按钮
}

function initBtnEvent() {
    var urlmethod = "";
    for (var i = 0; i < btnIdArray.length; i++) {
        // 新增
        if (btnIdArray[i] == "btn_add") {
            $("#btn_add").bind('click', function () {
                $('#myForm1').validationEngine('hide');
                $('#btHousePropertyAdd').show();

                clearForm();
                $("#roomState").val("0");
                $("#myModalTitle").html("新增");
                $('#myModal1').modal({
                    backdrop: 'static',
                    keyboard: false
                });

            });
        }
        // 删除
        if (btnIdArray[i] == "btn_del") {
            $("#btn_del").bind('click', function () {
                $('#myForm1').validationEngine('hide');
                selections = getIdSelections();
                urlmethod = "method=deleteHouseProperty";
                layer.confirm("您确定要删除所选信息吗?", {
                    skin: 'layui-layer-molv',
                    btn: ['确定', '取消']
                }, function () {
                    var url = getRootPath() + "houseProperty/housePropertyList.app?" + urlmethod;
                    $.post(url, {
                        roomId: selections + ""
                    }, function (data) {
                        layer.msg(eval(data), {
                            time: 2000
                        }, function () {
                            $('#housePropertyInfo').bootstrapTable('refresh');
                        });
                    });
                }, function () {
                    return;
                });
            });
        }

    }
}

function init() {
    // 初始化Table
    oTable = new TableInit();
    oTable.Init();

};

var TableInit = function () {
    var oTableInit = new Object();
    oTableInit.Init = function () {
        $('#housePropertyInfo').bootstrapTable({
            url: getRootPath() + 'houseProperty/housePropertyList.app?method=roomOfownerInfo', // 请求后台的URL（*）
            toolbar: '#toolbar', // 工具按钮用哪个容器
            striped: true,
            search: true,
            searchOnEnterKey: true,
            showColumns: true, // 是否显示所有的列
            showRefresh: true, // 是否显示刷新按钮
            showToggle: true, // 是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "client", // 分页方式：client 客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: false,
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
                field: 'state',
                checkbox: true,
            }, {
                field: 'daysPrice',
                visible: false
            }, {
                field: 'chargeStateName',
                visible: false
            }, {
                field: 'monthsPrice',
                visible: false
            }, {
                field: 'chargeState',
                visible: false
            }, {
                field: 'ownerId',
                visible: false
            }, {
                field: 'sex',
                visible: false
            }, {
                field: 'sex',
                visible: false
            }, {
                field: 'cardId',
                visible: false
            }, {
                field: 'birthDate',
                visible: false
            }, {
                field: 'roomId',
                visible: false,
                title: 'ID'
            }, {
                field: 'buildId',
                visible: false
            }, {
                field: 'communityId',
                visible: false
            }, {
                field: 'storiedBuildId',
                visible: false
            }, {
                field: 'unitId',
                visible: false
            }, {
                field: 'buildName',
                visible: false
            }, {
                field: 'communityName',
                visible: false
            }, {
                field: 'storiedBuildName',
                visible: false
            }, {
                field: 'unitName',
                visible: false
            }, {
                field: 'roomNo',
                visible: false
            }, {
                field: 'roomLzId',
                visible: false
            }, {
                field: 'roomState',
                visible: false
            }, {
                field: 'roomType',
                visible: false
            }, {
                field: 'chargeTypeNo',
                visible: false
            }, {
                field: 'chargePrice',
                visible: false
            }, {
                field: 'chargeTypeName',
                visible: false
            }, {
                field: 'roomAddrs',
                title: '房间号'
            }, {
                field: 'buildArea',
                title: '建筑面积'
            }, {
                field: 'withinArea',
                title: '套内面积'
            }, {
                field: 'roomTypeName',
                title: '房间类型'
            }, {
                field: 'roomStateName',
                title: '房间状态'
            }, {
                field: 'makeRoomDate',
                title: '地产收房时间',
                formatter: dateFormate
            }, {
                field: 'receiveRoomDate',
                title: '实际收房时间',
                formatter: dateFormate
            }, {
                field: 'chargeDate',
                title: '物业费起征日期',
                formatter: dateFormate
            }, {
                field: 'ownerName',
                title: '业主'
            }, {
                field: 'phone',
                title: '电话'
            }, {
                field: 'operate',
                title: '操作',
                align: 'center',
                width: '10%',
                events: operateEvents,
                formatter: operateFormatter
            }],
            onCheck: function (row, e) {
                tableCheckEvents();
            },
            onUncheck: function (row, e) {
                tableCheckEvents();
            },
            onCheckAll: function (rows) {
                $("#btn_del").attr("disabled", false);
            },
            onUncheckAll: function (rows) {
                $("#btn_del").attr("disabled", true);
            },
            onLoadSuccess: function (rows) {
                $("#btn_del").attr("disabled", true);
            }
        });
    };

    function operateFormatter(value, row, index) {
        return tableBtnInit();
    }

    // 操作列的事件
    window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
            editOrCheck(row, 1);
        },
        'click #a_edit': function (e, value, row, index) {
            editOrCheck(row, 2);
        },
        'click #a_manage': function (e, value, row, index) {
            editOrCheck(row, 3);
        }
    };
    oTableInit.queryParams = function (params) {
        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码
            search: encodeURI(params.search),
            communityId: $("#communityNameSearch").val(),
            storiedBuildId: $("#storiedBuildNameSearch").val(),
            unitId: $("#unitNameSearch").val(),
            roomType: $("#roomTypeSearch").val(),
            roomState: $("#roomStateSearch").val()
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
    return oTableInit;

};

// 表格选择事件
function tableCheckEvents() {
    var r = $('#housePropertyInfo').bootstrapTable('getSelections');
    if (r.length == 0) {
        $("#btn_del").attr("disabled", true);
    }
    if (r.length == 1) {
        $("#btn_del").attr("disabled", false);
    }
}

// 初始化下拉框
function initDrops() {
    var url = getRootPath() + "houseProperty/housePropertyList.app?method=initDropAll";
    $.post(url, function (data) {

        var list = eval(data);
        for (var j = 0; j < list.length; j++) {
            var detailList = list[j];
            var code = detailList[0];
            if (code == 'room_type') {
                $("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#roomType");
                $("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#roomTypeSearch");
            }
            if (code == 'room_state') {
                $("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#roomState");
                $("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#roomStateSearch");
            }
        }
        ;
    });

}

/**
 * 选择框
 *
 * @returns
 */
function getIdSelections() {
    return $.map($('#housePropertyInfo').bootstrapTable('getSelections'), function (row) {
        return row.roomId;
    });
}

/**
 * 保存操作
 */
function openSaveButton() {
    var flag = $('#myForm1').validationEngine('validate');
    if (flag) {
        var addJson = getDataForm();
        $.ajax({
            type: "post",
            url: getRootPath() + "houseProperty/housePropertyList.app?method=checkRoomNo",
            data: addJson,
            dataType: "json",
            async: false,
            success: function (data) {
                if (data == "succese") {
                    $.ajax({
                        type: "post",
                        url: getRootPath() + "houseProperty/housePropertyList.app?method=save",
                        data: {
                            housePropertyInfo: JSON.stringify(addJson)
                        },
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            layer.msg('操作成功！', {
                                time: 2000
                            }, function () {
                                $('#housePropertyInfo').bootstrapTable('refresh');
                                $('#myModal1').modal('hide');
                            });

                        },
                        failure: function (xhr, msg) {
                            layer.msg('操作失败！', {
                                time: 2000
                            }, function () {

                            });
                        }
                    });
                } else {
                    layer.msg('房间号重复！', {
                        time: 1000
                    }, function () {

                    });
                }
            },
            failure: function (xhr, msg) {
                layer.msg('操作失败！', {
                    time: 2000
                }, function () {

                });
            }
        });

    } else {
        layer.msg('表单验证未通过！', {
            time: 3000
        });
    }
}

/**
 * 获取表单数据
 *
 * @returns {___anonymous7092_7851}
 */
function getDataForm() {

    var buildName = $("#buildId").find("option:selected").text();
    var communityName = $("#communityId").find("option:selected").text();
    var storiedBuildName = $("#belongSbId").find("option:selected").text();
    var unitName = $("#unitId").find("option:selected").text();
    var unitId = $("#unitId").val();

    var addJson = {
        buildId: $("#buildId").val(),
        buildName: buildName,
        communityId: $("#communityId").val(),
        communityName: communityName,
        belongSbId: $("#belongSbId").val(),
        storiedBuildName: storiedBuildName,
        roomId: $("#roomId").val(),
        roomNo: $("#roomNo").val(),
        houseType: $("#houseType").val(),
        buildArea: $("#buildArea").val(),
        withinArea: $("#withinArea").val(),
        makeRoomDate: $("#makeRoomDate").val(),
        receiveRoomDate: $("#receiveRoomDate").val(),
        roomType: $("#roomType").val(),
        roomState: $("#roomState").val(),
        chargeObject: "0",
        unitId: unitId,
        unitName: unitName,
        remark: $("#remark").val()
    };
    return addJson;
}

// 清空表单
function clearForm() {

    $("#roomId").val("");
    $("#roomNo").val("");
    $("#belongSbId").val("");
    $("#houseType").val("");
    $("#buildArea").val("");
    $("#withinArea").val("");
    $("#makeRoomDate").val("");
    $("#receiveRoomDate").val("");
    $("#roomType").val("");
    $("#roomState").val("");
    $("#remark").val("");
    $("#buildId").val("");
    $("#communityId").val("");
    roomFilter.init();

}

// 查看和编辑
function editOrCheck(obj, type) {
    $('#myForm1').validationEngine('hide');
    $("#otherInfo").attr("style", "display:none;");
    $("#basicInfo").attr("style", "display:none;");

    $("#btHousePropertyAdd").attr("style", "display:none;");
    $("#unOwner").attr("style", "display:none;");
    $("#repOwner").attr("style", "display:none;");
    $("#repCharge").attr("style", "display:none;");
    $("#sycnLz").attr("style", "display:none;");

    if (type == 1) {
        $('#btHousePropertyAdd').hide();
        $("#myModalTitle").html("查看");
        $("#roomType").attr("disabled", "disabled");
        $("#roomState").attr("disabled", "disabled");
        $("#roomNo").attr("disabled", "disabled");
        $("#buildArea").attr("disabled", "disabled");
        $("#withinArea").attr("disabled", "disabled");
        $("#makeRoomDate").attr("disabled", "disabled");
        $("#receiveRoomDate").attr("disabled", "disabled");
        $("#remark").attr("disabled", "disabled");
        $("#basicInfo").attr("style", "display:block;");
    } else if (type == 2) {
        $('#btHousePropertyAdd').show();
        $("#myModalTitle").html("编辑");
        $("#roomType").removeAttr("disabled");
        $("#roomState").removeAttr("disabled");
        $("#roomNo").removeAttr("disabled");
        $("#buildArea").removeAttr("disabled");
        $("#withinArea").removeAttr("disabled");
        $("#makeRoomDate").removeAttr("disabled");
        $("#receiveRoomDate").removeAttr("disabled");
        $("#remark").removeAttr("disabled");
        $("#basicInfo").attr("style", "display:block;");
        $("#btHousePropertyAdd").attr("style", "display:inline;");
    } else {
        $("#otherInfo").attr("style", "display:block;");
        $("#unOwner").attr("style", "display:inline;");
        $("#repOwner").attr("style", "display:inline;");
        $("#repCharge").attr("style", "display:inline;");
        $("#sycnLz").attr("style", "display:inline;");
    }
    options = {
        buildDef: obj.buildId,
        buildDisabled: true,
        communityDef: obj.communityId,
        communityDisabled: true,
        belongDef: obj.storiedBuildId,
        belongDisabled: true,
        unitDef: obj.unitId,
        unitDisabled: true
    }
    roomFilter.setVal(options);
    $("#roomId").val(obj.roomId);
    $("#roomNo").val(obj.roomNo);
    $("#belongSbId").val(obj.storiedBuildId);
    $("#houseType").val(obj.houseType);
    $("#buildArea").val(obj.buildArea);
    $("#withinArea").val(obj.withinArea);
    $("#makeRoomDate").val(json2Date(obj.makeRoomDate));
    $("#receiveRoomDate").val(json2Date(obj.receiveRoomDate));
    $("#roomType").val(obj.roomType);
    $("#roomState").val(obj.roomState);
    $("#remark").val(obj.remark);
    $("#buildId").val(obj.buildId);
    $("#communityId").val(obj.communityId);
    $("#roomAddrs").val(obj.roomAddrs);
    $("#ownerInfo").val(obj.ownerName + "-" + obj.phone);
    $("#onwerId").val(obj.ownerId);
    $("#lzId").val(obj.roomLzId);
    $("#chargeInfo").val("面积：" + obj.buildArea + "² |" + "物业费名称：" + obj.chargeTypeName + " | " + "单价：" + obj.chargePrice + "元 | " + " 每月物业费：" + obj.monthsPrice + "元");
    if (obj.roomLzId === null || obj.roomLzId === "") {
        $("#lzmhInfo").val("未同步联掌系统!");
    } else {
        $("#lzmhInfo").val("已同步联掌系统!");
    }
    $('#myModal1').modal({
        backdrop: 'static',
        keyboard: false
    });
}

function clearSearch() {
    $("#roomTypeSearch").val("");
    $("#roomStateSearch").val("");
    $("#communityNameSearch").val("");
    $("#storiedBuildNameSearch").val("");
    $("#unitNameSearch").val("");
    roomSearch.init();
    $('#buttonSearch').click();
}

function manage(type) {
    var ownerId = $("#onwerId").val() || '';
    var roomId = $("#roomId").val();
    if (type === "unOwner" && ownerId != "") {
        layer.confirm('您确认要解除该房间绑定的业主吗？', {btn: ['确定', '取消'], title: "提示"}, function () {
            $.ajax({
                type: "post",
                url: getRootPath() + "houseProperty/housePropertyList.app?method=unOwner",
                data: {"roomId": roomId},
                dataType: "json",
                async: false,
                success: function (data) {
                    //console.log(data);
                    if (data == "success") {
                        layer.msg('重置成功', {icon: 1});
                    } else {
                        layer.msg('重置失败', {icon: 2});
                    }
                }
            });
        });
    } else {
        alert("房间未绑定业主，无法进行解除绑定");
    }
    if (type === "repOwner") {
        alert(1);
    }
    if (type === "repCharge") {
        alert(2);
    }
    if (type === "sycnLz") {
        alert(3);
    }
}
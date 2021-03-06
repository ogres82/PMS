var ChargeType;
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
    initSearch();
    initChargeNoDrops();
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
        elem: '#makeRoomDate',
        // format: 'YYYY年MM月DD日',
        festival: true, // 显示节日
        choose: function (datas) { // 选择日期完毕的回调
            $('#myForm1').validationEngine('hide');
        }
    });


    laydate.render({
        elem: '#chargeDate',
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
            sidePagination: "server", // 分页方式：client 客户端分页，server服务端分页（*）
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
                field: 'lzRoomOwnerId',
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
                field: 'roomLzId'
            }, {
                field: 'roomState',
                visible: false
            }, {
                field: 'roomType',
                visible: false
            }, {
                field: 'chargeTypeId',
                visible: false
            }, {
                field: 'chargeTypeNo',
                visible: false
            }, {
                field: 'chargePrice',
                visible: false
            },  {
                field: 'roomAddrs',
                title: '房间号'
            }, {
                field: 'ownerName',
                title: '业主'
            }, {
                field: 'phone',
                title: '电话'
            },{
                field: 'buildArea',
                title: '建筑面积'
            }, {
                field: 'withinArea',
                title: '套内面积'
            },  {
                field: 'chargeTypeName',
                title: '收费名称'
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
            },  {
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
        'click #a_unowner': function (e, value, row, index) {
            editOrCheck(row, 3);
        },
        'click #a_repowner': function (e, value, row, index) {
            editOrCheck(row, 4);
        },
        'click #a_changecost': function (e, value, row, index) {
            editOrCheck(row, 5);
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
        var addJson = getDataForm("edit");
        console.log(addJson);
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
function getDataForm(type) {

    var addJson = {};
    var buildName = $("#buildId").find("option:selected").text();
    var communityName = $("#communityId").find("option:selected").text();
    var storiedBuildName = $("#belongSbId").find("option:selected").text();
    var unitName = $("#unitId").find("option:selected").text();
    var unitId = $("#unitId").val();

    if (type == "edit") {
        addJson = {
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
            lzId :parseInt($("#lzRoomId").val()||0),
            remark: $("#remark").val()
        };
    }
    if (type == "charge") {
        var days = new Date().DateDiff("d", $("#chargeDate").val());
        console.log(days);
        var chargeState = "0";
        if (days >= 0) {
            chargeState = "1";
        }
        addJson = {
            type_flag: "01",
            charge_type_id: $("#newChargeTypeId").val() || $("#chargeTypeId").val(),
            charge_type_no: $("#newChargeTypeNo").val() || $("#chargeTypeNo").val(),
            room_id: $("#roomId").val(),
            room_no: $("#roomNo").val(),
            owner_id: $("#ownerId").val(),
            charge_date: $("#chargeDate").val(),
            charge_state: chargeState,
            amount: 0,
            is_del: "0"
        };
    }
    if (type == "repOwner"){

    }
    return addJson;
}

// 清空表单
function clearForm() {
    $("#roomId").val("");
    $("#roomNo").val("");
    $("#lzRoomId").val("");
    $("#chargeTypeNo").val("");
    $("#lzRoomOwnerId").val("");
    $("#ownerId").val("");
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
    $("#phone").val("");
    roomFilter.init();
}

// 查看和编辑
function editOrCheck(obj, type) {
    $('#myForm1').validationEngine('hide');

    $("#repOwner").attr("disabled", "disabled");

    $("#otherInfo").attr("style", "display:none;");
    $("#basicInfo").attr("style", "display:none;");


    $("#newOwnerDiv").attr("style", "display:none;");
    $("#newOwnerInfoDiv").attr("style", "display:none;");


    $("#newChargeInfoDiv").attr("style", "display:none;");
    $("#newChargeSelectDiv").attr("style", "display:none;");
    $("#chargeDateDiv").attr("style", "display:none;");

    $("#btHousePropertyAdd").attr("style", "display:none;");
    $("#unOwner").attr("style", "display:none;");
    $("#repOwner").attr("style", "display:none;");
    $("#repCharge").attr("style", "display:none;");
    $("#sycnLz").attr("style", "display:none;");
    //查看
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
    }
    //编辑
    if (type == 2) {
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
    }
    //解绑用户
    if (type == 3) {
        $("#otherInfo").attr("style", "display:block;");
        $("#unOwner").attr("style", "display:inline;");
    }
    //更换用户
    if (type == 4) {
        $("#otherInfo").attr("style", "display:block;");
        $("#repOwner").attr("style", "display:inline;");

        $("#newOwnerDiv").attr("style", "display:block;");
        $("#newOwnerInfoDiv").attr("style", "display:block;");
        $("#newOwnerId").val("");
        $("#ownerSelect").val("");
        $("#newOwnerInfo").val("");
    }
    //更改收费信息
    if (type == 5) {
        $("#otherInfo").attr("style", "display:block;");
        $("#repCharge").attr("style", "display:inline;");

        $("#newChargeInfoDiv").attr("style", "display:block;");
        $("#newChargeSelectDiv").attr("style", "display:block;");
        $("#chargeDateDiv").attr("style", "display:block;");
        $("#newChargeTypeNo").val("");
        $("#newChargeTypeId").val("");
        $("#newChargeType").val("");
        $("#newChargeInfo").val("");

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
    $("#phone").val(obj.phone);

    $("#chargeTypeNo").val(obj.chargeTypeNo);
    $("#chargeTypeId").val(obj.chargeTypeId);

    $("#buildId").val(obj.buildId);
    $("#communityId").val(obj.communityId);
    $("#roomAddrs").val(obj.roomAddrs);
    $("#ownerInfo").val(obj.ownerName + "-" + obj.phone);
    $("#ownerId").val(obj.ownerId);
    $("#chargeDate").val(json2Date(obj.chargeDate));

    $("#lzRoomId").val(obj.roomLzId);
    $("#lzRoomOwnerId").val(obj.lzRoomOwnerId);
    $("#chargeInfo").val("面积：" + obj.buildArea + "² |" + "物业费名称：" + obj.chargeTypeName + " | " + "单价：" + obj.chargePrice + "元 | " + " 每月物业费：" + obj.monthsPrice + "元");
    if (obj.roomLzId === null || obj.roomLzId === "") {
        $("#lzmhInfo").val("房间信息未同步联掌系统!");
    } else {
        $("#lzmhInfo").val("房间信息已同步联掌系统!");
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
    clearForm();
    roomSearch.init();
    $('#buttonSearch').click();
}

function manage(type) {
    var roomId = $("#roomId").val();
    var url = getRootPath();
    var strConfirm = "";
    var data;

    if (type === "unOwner") {
        url = url + "houseProperty/housePropertyList.app?method=unOwner";
        strConfirm = "您确认要解除该房间绑定的业主吗？";
        data = {"roomId": roomId};
    }
    if (type === "repOwner") {
        url = url + "houseProperty/housePropertyList.app?method=repOwner";
        strConfirm = "您确认要更换该房间的业主吗？";

        data ={roomId:roomId, lzRoomOwnerId:$("#lzRoomOwnerId").val(), newOwnerId:$("#newOwnerId").val(), phone:$("#phone").val(), lzRoomId:$("#lzRoomId").val()||0};
    }
    if (type === "repCharge") {
        url = url + "houseProperty/housePropertyList.app?method=repCharge";
        strConfirm = "您确认要更换该房间收费项目以及起征日期？";
        data = {"chargeTypeRoomRelaInfo": JSON.stringify(getDataForm("charge"))};
    }

    layer.confirm(strConfirm, {btn: ['确定', '取消'], title: "提示"}, function () {
        $.ajax({
            type: "post",
            url: url,
            data: data,
            dataType: "json",
            async: false,
            success: function (data) {
                //console.log(data);
                if (data == "success") {
                    layer.msg('操作成功', {icon: 1});
                } else {
                    layer.msg('操作失败！', {icon: 2});
                }
                $('#housePropertyInfo').bootstrapTable('refresh', null);
                $("#myModal1").modal('hide');
            }
        });
    });

}


/* 搜索框初始化 */
function initSearch() {
    // 业主下拉框
    $("#ownerSelect").bsSuggest({
        url: encodeURI("./../ownerInfo/ownerList.app?code=utf-8&method=ownerInfo&keyword="),
        showHeader: true,
        allowNoKeyword: true,
        keyField: 'keyword',
        getDataMethod: "url",
        delayUntilKeyup: true,
        effectiveFields: ["ownerName", "phone"],
        effectiveFieldsAlias: {
            ownerName: "业主姓名",
            phone: "电话号码"
        },
        // 预处理
        fnPreprocessKeyword: function (keyword) {
            // 请求数据前，对输入关键字作预处理
            return encodeURI(encodeURI(keyword));
        },
        processData: function (json) { // url 获取数据时，对数据的处理，作为 getData 的回调函数
            var index, len, data = {
                value: []
            };
            if (!json || json.length === 0) {
                return false;
            }
            data.value = json;
            // 字符串转化为 json 对象
            return data;
        }
    }).on("onDataRequestSuccess", function (e, result) {
        console.log("onDataRequestSuccess: ", result)
    }).on("onSetSelectValue", function (e, keyword, data) {
        console.log("onSetSelectValue: ", keyword, data);
        $("#newOwnerInfo").val("业主姓名：" + data.ownerName + " |业主电话：" + data.phone);
        $("#newOwnerId").val(data.ownerId);
        $("#ownerSelect").val("");
        $("#repOwner").removeAttr("disabled");
    }).on("onUnsetSelectValue", function (e) {
        console.log("onUnsetSelectValue")
    });
}

function initChargeNoDrops() {
    var url = getRootPath() + "ChargeManager/ChargeType.app?method=dropdownChargeType";
    $.post(url, function (data) {
        ChargeType = eval(data);
        for (var j = 0; j < ChargeType.length; j++) {
            var detailList = ChargeType[j];
            //不添加家政费用和维修费用
            if ((detailList[3] != '01')) {
                continue;
            }
            // 收费类型
            $("<option value='" + detailList + "'>" + detailList[2] +
                "</option>").appendTo("#newChargeType");
        }
        ;
    });
}

function setChargeInfo(value) {
    var selectValue = value.split(",");
    var chargeTypeNo = $("#chargeTypeNo").val();
    var buildArea = $("#buildArea").val();
    if (selectValue[1] == chargeTypeNo) {
        $("#newChargeInfo").val("");
        $("#newChargeTypeId").val("");
        $("#newChargeTypeNo").val("");
        $("#newChargeType").val("");
        alert("变更后的收费项目不能与之前的一样，请重新选择收费项目！");
    } else {
        $("#repCharge").removeAttr("disabled");
        var monthsPrice = Number(buildArea) * Number(selectValue[5]);
        $("#newChargeInfo").val("面积：" + buildArea + "² |" + "物业费名称：" + selectValue[2] + " | " + "单价：" + selectValue[5] + "元 | " + " 每月物业费：" + monthsPrice.toFixed(2) + "元");
        $("#newChargeTypeId").val(selectValue[0]);
        $("#newChargeTypeNo").val(selectValue[1]);
    }
}
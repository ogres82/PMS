var Directory;

$(function () {
    initDropdownHouseType();
    getDirectory();
    toolbarBtnInit();
    // 初始化Table
    var TableInit = new chargeAdvanceTableInit();
    TableInit.Init();

    // 添加检验
    $('#myForm').validationEngine();

});

/* 编辑按钮，弹出对话框 */
function edit(obj, type) {
    if (type == "a_del") {
        layer.confirm("您确定要删除信息吗?", {
            btn: ['确定', '取消'], // 按钮
            shade: false
        }, function () {
            delCharge(obj);
        }, function () {
        });
        return;
    }
    if (type == "a_down") {
        reduceDown(obj);
        return;
    }
    if (type == "a_prestore") {
        layer.confirm('确定将押金转预存么？', {
            btn: ['确定', '取消'], // 按钮
            shade: false
            // 不显示遮罩
        }, function () {
            depositToPrestore(obj);
        }, function () {

        });
        return;
    }
}

var chargeAdvanceTableInit = function () {
    var oTableInit = new Object();
    oTableInit.Init = function () {
        $('#chargeAdvance').bootstrapTable({
            url: './../ChargeManager/ChargeSerial.app?method=serialList', // 请求后台的URL（*）
            toolbar: '#toolbar', // 工具按钮用哪个容器
            striped: true,
            //showFooter : true,
            search: false,
            searchOnEnterKey: true,
            showColumns: true, // 是否显示所有的列
            showRefresh: true, // 是否显示刷新按钮
            showToggle: true, // 是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            showExport: "true",
            minimumCountColumns: 2, // 最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 10, // 每页的记录行数（*）
            pageList: '[10, 25, 50, 100, ALL]', // 可供选择的每页的行数（*）
            columns: [{
                field: 'sta',
                checkbox: true
            }, {
                field: 'serial_id',
                visible: false
            }, {
                field: 'room_id',
                visible: false
            }, {
                field: 'order_id',
                visible: false
            }, {
                field: 'room_addr',
                title: '房号'
            }, {
                field: 'owner_name',
                title: '业主'
            }, {
                field: 'charge_type_name',
                title: '收费项名称'
            }, {
                field: 'paid_amount',
                title: '实收金额',
                formatter: numberFormate
            }, {
                field: 'receive_amount',
                title: '应收金额',
                formatter: numberFormate
            }, {
                field: 'reduce_mount',
                title: '优惠金额',
                formatter: numberFormate
            }, {
                field: 'paid_date',
                title: '收款日期',
                formatter: dateFormate
            }, {
                field: 'drop_paid_mode',
                title: '收款方式'
            }, {
                field: 'oper_emp_name',
                title: '收款人'
            }, {
                field: 'receipt_id',
                title: '收据单号'
            }, {
                field: 'remark',
                width: '10%',
                title: '备注'
            }, {
                field: 'reduce_url',
                visible: false
            }, {
                field: 'type_flag',
                visible: false
            }, {
                field: 'operate',
                title: '操作',
                align: 'center',
                events: operateEvents,
                formatter: operateFormatter
            }]
        });
    };

    function operateFormatter(value, row, index) {
        var data = [{
            text: 'type_flag',
            value: '02',
            btn: 'a_prestore'
        }];
        var obj = eval(data);
        return tableBtnInitLocal(row, obj);
    };

    // 操作列的事件
    window.operateEvents = {
        'click #a_del': function (e, value, row, index) {
            edit(row, "a_del");
        },
        'click #a_down': function (e, value, row, index) {
            edit(row, "a_down");
        },
        'click #a_prestore': function (e, value, row, index) {
            edit(row, "a_prestore");
        },
    };

    oTableInit.queryParams = function (params) {
        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码
            search: params.search,
            room_id: $("#roomId").val(),
            begin_time: $("#beginTime").val(),
            end_time: $("#endTime").val(),
            charge_type_select: $("#charge_type_select").val(),
            paid_mode_select: $("#paid_mode_select").val()
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
    numberFormate = function (value, row) {
        if (row.state == '02') {
            return outputmoney("" + (0 - Number(value)));
        } else if (row.state == '01') {
            return outputmoney("" + value);
        }
    };
    return oTableInit;
};

//下载减免凭证
function reduceDown(obj) {
    if (typeof docUrl === 'undefined' || docUrl === null || docUrl === "") {
        alert("缴费记录无减免凭证可以下载！");
    } else {
        var docUrl =  JSON.parse(obj.reduce_url);
        var downUrl = getRootPath() + "fileUpload.app?method=download&filepath=";
        window.open(downUrl+docUrl.key);
    }
}

function delCharge(obj) {
    var data = {"serial_id":obj.serial_id};
    $.ajax({
        type: "post",
        data: data,
        url: getRootPath() + 'ChargeManager/ChargeSerial.app?method=returnPay',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data != null && data.status != 200) {
                layer.msg('操作失败，“' + data.msg + '”！', {
                    time: 3000
                });
                return;
            }
            layer.msg('操作成功！', {
                time: 2000
            }, function () {
                $('#chargeAdvance').bootstrapTable('refresh', null);
            });
        }
    });
}

// 押金转预存
function depositToPrestore(obj) {
    var selects = JSON.stringify(obj);
    var data = {
        select: selects
    };
    $.ajax({
        type: "post",
        data: data,
        url: getRootPath() + 'ChargeManager/ChargeSerial.app?method=toPrestore',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data != null && data.status != 200) {
                layer.msg('操作失败，“' + data.msg + '”！', {
                    time: 3000
                });
                return;
            }
            layer.msg('操作成功！', {
                time: 2000
            }, function () {
                $('#chargeAdvance').bootstrapTable('refresh', null);
            });
        }
    });
}

// 初始化下拉框：房间业主信息
function initDropdownHouseType() {
    // 初始化下拉框：房间业主信息
    var addressSuggest = $("#searchInput").bsSuggest({
        url: getRootPath() + "ChargeManager/ChargeType.app?method=dropdownHourseOwner&keyword=",
        showHeader: true,
        allowNoKeyword: true,
        keyField: 'room_no',
        getDataMethod: "url",
        delayUntilKeyup: true,
        effectiveFields: ["communityName", "storiedBuildName", "unitName", "roomNo", "ownerName", "phone", "roomStateName"],
        effectiveFieldsAlias: {
            communityName: "小区",
            storiedBuildName: "楼宇",
            unitName: "单元",
            roomNo: "房间号",
            ownerName: "业主",
            phone: "电话",
            roomStateName: "房屋状态"
        },
        // 预处理
        fnPreprocessKeyword: function (keyword) {
            // 请求数据前，对输入关键字作预处理
            return encodeURI(encodeURI(keyword));
        },
        processData: function (json) { // url 获取数据时，对数据的处理，作为
            // getData 的回调函数
            var index, len, data = {
                value: []
            };
            if (!json || !json.result || json.result.length === 0) {
                return false;
            }
            len = json.result.length;
            for (index = 0; index < len; index++) {
                data.value.push({
                    "communityName": json.result[index][0],
                    "storiedBuildName": json.result[index][1],
                    "unitName": json.result[index][2],
                    "roomNo": json.result[index][3],
                    "ownerName": json.result[index][4],
                    "phone": json.result[index][5],
                    "roomState": json.result[index][6],
                    "roomStateName": json.result[index][7],
                    "roomType": json.result[index][8],
                    "chargeDate": json.result[index][9],
                    "chargePrice": json.result[index][10],
                    "monthsPrice": json.result[index][11],
                    "ownerId": json.result[index][12],
                    "roomId": json.result[index][13],
                    "buildArea": json.result[index][14],
                    "expDate": json.result[index][15],
                    "arrAmount": json.result[index][16],
                    "roomAddrs": json.result[index][17]
                });
            }
            // 字符串转化为 js 对象
            return data;
        }
    }).on("onDataRequestSuccess", function (e, result) {
        //console.log("onDataRequestSuccess: ", result)
    }).on("onSetSelectValue", function (e, keyword, data) {
        $("#ownerId").val(data.ownerId);
        $("#roomId").val(data.roomId);
        $("#searchInput").val("");
        $('#chargeAdvance').bootstrapTable('refresh', null);
    }).on("onUnsetSelectValue", function (e) {
        //console.log("onUnsetSelectValue")
    });
}

function clearSearch() {
    $("#charge_type_select").val("");
    $("#paid_mode_select").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#roomId").val("");
    $("#ownerId").val("");
    $("#searchInput").val("");
    $('#chargeAdvance').bootstrapTable('refresh', null);
}


function getDirectory() {
    $.ajax({
        //请求方式
        type: "POST",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        //请求地址
        url: getRootPath() + "DirDirectory.app?method=queryAll",
        //服务器返回数据的类型，例如xml,String,Json等
        dataType: "json",
        success: function (result) {
            Directory = result;
            for (var j = 0; j < Directory.length; j++) {
                var detailList = Directory[j];
                var code = detailList.code;
                var codeValue = detailList.code_detail;
                var codeName = detailList.code_detail_name
                // 支付方式
                if (code == 'charge_paid_mode') {
                    if (codeValue == '01') {
                        continue;
                    }
                    $("<option value='" + codeValue + "'>" + codeName + "</option>").appendTo("#paid_mode_select");
                }
                // 收费类型
                if (code == 'charge_type_flag') {
                    if (codeValue == '99') {
                        continue;
                    }
                    $("<option value='" + codeValue + "'>" + codeName + "</option>").appendTo("#charge_type_select");
                }
            }
            ;
        },
        //请求失败，包含具体的错误信息
        error: function (e) {
            console.log(e.status);
            console.log(e.responseText);
        }
    });
}

function refreshChargeSerial() {
    $('#chargeAdvance').bootstrapTable('refresh', null);
}

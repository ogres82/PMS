var ChargeType;
var loginUserName = "";
$(function () {
    laydate.render({
        elem: '#beginDate',
        event: 'click',
        //format: 'YYYY年MM月DD日',
        festival: true, //显示节日
        done: function (value, date, endDate) { //选择日期完毕的回调
            $('#myForm').validationEngine('hide');
        }
    });
    laydate.render({
        elem: '#endDate',
        event: 'click',
        //format: 'YYYY年MM月DD日',
        festival: true, //显示节日
        done: function (value, date, endDate) { //选择日期完毕的回调
            $('#myForm').validationEngine('hide');
        }
    });
    laydate.render({
        elem: '#paidDate',
        event: 'click',
        value: new Date(),
        type: 'datetime',
        //format: 'YYYY年MM月DD日',
        festival: true, //显示节日
        done: function (value, date, endDate) { //选择日期完毕的回调
            $('#myForm').validationEngine('hide');
        }
    });
    // 初始化Table
    var oTable = new TableInit();
    oTable.Init();
    // 初始化下拉框
    initDrops();
    // 初始化下拉框：房间业主信息
    initDropdownHouseType();

    // 初始化收费项下拉框
    initChargeNoDrops();
    // 新增按钮
    $('#btnAdd').click(function () {
        add();
    });
    $("#input-b5").fileinput({
        language: 'zh', //设置语言
        uploadAsync: true,
        showPreview: false,
        showUpload: false,
        showRemove: true,
        showCaption: true,//是否显示文件标题，默认为true
        maxFileCount: 1, //每次上传允许的最大文件数。如果设置为0，则表示允许的文件数是无限制的。默认为0
        uploadUrl: getRootPath() + "mainFrame/mainFrameInfo.app?method=fileUpload&folder=chargeFile"
    }).on("filebatchselected", function (event, files) {
        // 选择文件后立即触发上传方法
        $("#input-b5").fileinput("upload");
    }).on('fileuploaded', function (event, data) {
        var msg = JSON.parse(data.response);
        var fileUrls = eval(msg.data);
        var reduceUrl = $("#reduceUrl").val();
        if (reduceUrl.length > 0) {
            deleteFile(reduceUrl);
        }
        if (msg.status == 1) {
            $("#reduceUrl").val(JSON.stringify(fileUrls[0]));
        }
    }).on('filecleared', function (event, data, msg) {
        var filePath = $("#reduceUrl").val();
        if (filePath == '') {
            return;
        }
        deleteFile(filePath);
    });
    // 添加检验
    $('#myForm').validationEngine();

});

function paid() {
    var data = [];
    var chargeInfo = $('#chargeCustomer').bootstrapTable('getData');
    var userInfo = {
        owner_id: $('#ownerId').val(),//业主id
        owner_name: $('#ownerName').val(),//业主姓名
        room_id: $('#roomId').val(),//房间id
        room_no: $('#roomNo').val(),//房间编号
        state: "01",//收支状态
        charge_type: "01",//费用类型
        advance_amount: 0,//预收金额
        paid_date: $('#paidDate').val(),//收款日期
        paid_mode: $('#paidMode').val(),//收款方式
        ticket_mode: "",//开票方式
        oper_emp_id: loginUserName,//操作人
        charge_info_id: "",//账单id
        remark: $('#remark').val(),//备注
        receipt_id: $('#receiptId').val(),//收据单号
        get_mount: 0,//前台收到金额
        odd_mount: 0,//找零金额
        odd_mode: "",//找零方式
        reduce_mode: "",//减免方式
        community_name: $('#communityName').val(),//小区名称
        storied_build_name: $('#storiedBuildName').val(),//楼宇名称
        room_type: $('#roomType').val(),//房间类型
        reduce_url: $('#reduceUrl').val()  //减免凭证
    }
    for (keys in chargeInfo) {
        var newObj = {};
        var charge = {
            charge_type_no: chargeInfo[keys].chargeTypeNo,//'收费编号'
            charge_type_name: chargeInfo[keys].chargeTypeName,//'收费名称'
            receive_amount: chargeInfo[keys].receiveAmount,//'应收金额'
            reduce_mount: chargeInfo[keys].reduceMount,//'减免金额'
            paid_amount: chargeInfo[keys].paidAmount,//'实收金额'
            begin_date: chargeInfo[keys].beginDate,//'开始日期'
            end_date: chargeInfo[keys].endDate//'结束日期'
        };
        Object.assign(newObj, userInfo, charge);
        data.push(newObj)
    }
    pay(data);
}

// 收费
function pay(obj) {
    // var flag = $('#myForm').validationEngine('validate');
    // debugger;
    // if (flag) {
    var paidmode = $('#paidMode').val();
    if(paidmode=="")//收款方式
    {
        alert("请选择支付方式");
    }else {
        initBlockUI();
        $.ajax({
            type: "post",
            data: {chargeInfo: JSON.stringify(obj)},
            url: getRootPath() + "ChargeManager/ChargeInfoList.app?method=batchPaid",
            async: true,
            dataType: 'json',
            success: function (data) {
                if (data != null && data.status != 200) {
                    layer.msg('操作失败，“' + data.msg + '”！', {
                        time: 3000
                    });
                    return;
                }
                layer.msg('操作成功！', {
                    time: 1000
                }, function () {
                    $.unblockUI();
                    $("#roomAddrs").val("");
                    $("#ownerInfo").val("");
                    $("#chargeInfo").val("");
                    $("#totalCharge").val(0.00);
                    $("#totalReduceMount").val(0.00);
                    $("#totalPaidAmount").val(0.00);
                    $("#paidDate").val(new Date().format("yyyy-MM-dd"));
                    $("#remark").val("");
                    $("#receiptId").val("");
                    $("#paidMode").val("");
                    $("#roomId").val("");
                    $("#ownerId").val("");
                    $("#roomNo").val("");
                    $("#phone").val("");
                    $("#ownerName").val("");
                    $("#expDate").val("");
                    $("#communityName").val("");
                    $("#storiedBuildName").val("");
                    $("#unitName").val("");
                    $("#roomType").val("");
                    $("#input-b5").fileinput('clear');
                    $('#chargeCustomer').bootstrapTable('removeAll');

                });
            }
        });
    }
    // }
    // else {
    //     layer.msg('表单验证未通过！', {
    //         time: 2000
    //     });
    // }
}

// 新增
function add() {
    $('#myForm').validationEngine('hide');
    // 清空

    $("#chargeTypeName").val("");
    $("#chargeTypeId").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#chargeTypeNo").val("");
    $("#receiveAmount").val(0);
    $("#reduceMount").val(0);
    $("#chargeType").val("");
    $(".typeNum").attr("style", "display:none;")
    $(".monthsNum").attr("style", "display:none;")
    $("#myModal").modal('show');
}

// 表单保存
function save() {
    var flag = $('#myForm').validationEngine('validate');
    if (flag) {
        var rowData = {
            chargeTypeNo: $("#chargeTypeNo").val(),
            chargeTypeName: $("#chargeTypeName").val(),
            receiveAmount: $("#receiveAmount").val(),
            reduceMount: $("#reduceMount").val(),
            paidAmount: $("#paidAmount").val(),
            beginDate: new Date($("#beginDate").val()).getTime(),
            endDate: new Date($("#endDate").val()).getTime()
        };
        $('#chargeCustomer').bootstrapTable('append', rowData);
        countCharge(0);
        $("#myModal").modal('hide');
    }

}

var TableInit = function () {
    var oTableInit = new Object();
    oTableInit.Init = function () {
        $('#chargeCustomer')
            .bootstrapTable({
                url: "", // 请求后台的URL（*）
                sortable: false,
                sortOrder: "asc",
                sortable: false,
                sidePagination: "client", // 分页方式：client客户端分页，server服务端分页（*）
                cache: false,
                minimumCountColumns: 2, // 最少允许的列数
                buttonsAlign: "left",
                showPaginationSwitch: false,
                pageNumber: 1, // 初始化加载第一页，默认第一页
                pageSize: 20, // 每页的记录行数（*）
                pageList: '[100,ALL]', // 可供选择的每页的行数（*）
                columns: [{
                    field: 'id',
                    halign: "center",
                    align: "center",
                    title: '序号',
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                }, {
                    field: 'chargeTypeNo',
                    visible: false,
                    title: '收费编号'
                }, {
                    field: 'chargeTypeName',
                    halign: "center",
                    title: '收费名称'
                }, {
                    field: 'receiveAmount',
                    halign: "center",
                    title: '应收金额',
                    align: "right"
                }, {
                    field: 'reduceMount',
                    halign: "center",
                    title: '减免金额',
                    align: "right"
                }, {
                    field: 'paidAmount',
                    halign: "center",
                    title: '实收金额',
                    align: "right",
                    formatter: reciveFormate
                }, {
                    field: 'beginDate',
                    halign: "center",
                    title: '开始日期',
                    formatter: dateFormate
                }, {
                    field: 'endDate',
                    halign: "center",
                    title: '结束日期',
                    formatter: dateFormate
                }, {
                    field: 'operate',
                    halign: "center",
                    title: '操作',
                    align: 'center',
                    events: operateEvents,
                    formatter: operateFormatter
                }], onLoadSuccess: function () {
                    countCharge(0);
                }
            });
    };

    // 操作列的显示风格
    function operateFormatter(value, row, index) {
        return [
            '<a class="remove" href="javascript:void(0)" title="删除">',
            '<i class="fa fa-close"></i>',
            '</a>'
        ].join('');
    }

    // 操作列的事件
    window.operateEvents = {
        'click .remove': function (e, value, row, index) {

            $('#chargeCustomer').bootstrapTable('remove', {
                field: 'chargeTypeNo',
                values: [row.chargeTypeNo]
            });
            countCharge(0);
        }
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
            return json2Date(value);
        }

    };
    reciveFormate = function (value, row, index) {
        return Number(row.receiveAmount) - Number(row.reduceMount);
    }
    return oTableInit;
};

// 初始化下拉框
function initDrops() {
    var url = getRootPath() + "ChargeManager/ChargeInfoList.app?method=initDropAll";
    $.post(url, function (data) {
        var list = eval(data);
        for (var j = 0; j < list.length; j++) {
            var detailList = list[j];
            var code = detailList[0];
            // 付款方式
            if (code == 'charge_paid_mode') {
                if (detailList[2] == "抵扣") {
                    continue;
                } else {
                    $("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#paidMode");
                }
            }
        }
        ;
    });
}

// 初始化下拉框：房间业主信息
function initDropdownHouseType() {
    // 初始化下拉框：房间业主信息
    var addressSuggest = $("#searchClient")
        .bsSuggest({
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
                if (!json || !json.result ||
                    json.result.length === 0) {
                    return false;
                }
                len = json.result.length;
                for (index = 0; index < len; index++) {
                    data.value
                        .push({
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
                            "beginDate": json.result[index][15],
                            "arrAmount": json.result[index][16],
                            "roomAddrs": json.result[index][17],
                            "chargeTypeNo": json.result[index][18],
                            "makeRoomDate": json.result[index][19],
                            "receiveRoomDate": json.result[index][20]
                        });
                }
                // 字符串转化为 js 对象
                return data;
            }
        }).on("onDataRequestSuccess", function (e, result) {
        }).on("onSetSelectValue", function (e, keyword, data) {
            if (data.roomState == '1') {
                alert("该业主还未办理收房业务，无法进行缴费！");
                return;
            } else {
                $("#roomId").val(data.roomId);
                $("#roomAddrs").val(data.roomAddrs + "(面积：" + data.buildArea + "²|交房：" + json2Date(data.makeRoomDate) + "|收房：" + json2Date(data.receiveRoomDate) + "|)");
                $("#chargeInfo").val("单价：" + data.chargePrice + "元|物业费：" + data.monthsPrice + "元|起征日期：" + json2Date(data.chargeDate) + "|" + "截止日期：" + json2Date(data.beginDate) + "|");
                $("#roomNo").val(data.roomNo);
                $("#ownerId").val(data.ownerId);
                $("#ownerInfo").val(data.ownerName + '-' + data.phone);
                $("#phone").val(data.roomId);
                $("#ownerName").val(data.ownerName);
                $("#buildArea").val(data.buildArea);
                $("#expDate").val(data.beginDate);
                $("#communityName").val(data.communityName);
                $("#storiedBuildName").val(data.storiedBuildName);
                $("#unitName").val(data.unitName);
                $("#roomType").val(data.roomType);
                $("#searchClient").val("");
                $("#reduceUrl").val("");
                $("#input-b5").fileinput('clear');
                $("#totalCharge").val(0.00);
                $("#btnAdd").removeAttr("disabled");
                $("#chargeType").find("option").remove();
                addChargeType(data.chargeTypeNo);
                // var url = getRootPath() + "ChargeManager/ChargeRoomInfoList.app?method=arrearage" + "&roomId='" + data.roomId + "'";
                // $("#chargeCustomer").bootstrapTable('refresh', {url: url});
            }
        }).on("onUnsetSelectValue", function (e) {
        });
}


// 初始化收费项数据
function initChargeNoDrops() {
    var url = getRootPath() + "ChargeManager/ChargeType.app?method=dropdownChargeType";
    $.post(url, function (data) {
        ChargeType = eval(data);
    });
}

//添加收费项目下拉菜单
function addChargeType(chargeTypeNo) {
    $("<option value=''>---请选择---</option>").appendTo("#chargeType");
    for (var j = 0; j < ChargeType.length; j++) {
        var detailList = ChargeType[j];
        //不添加家政费用和维修费用
        if ((detailList[3] == '01' && detailList[1] != chargeTypeNo) || detailList[3] == '99') {
            continue;
        }
        // 收费类型
        $("<option value='" + detailList + "'>" + detailList[2] +
            "</option>").appendTo("#chargeType");
    }
    ;
}

//计算费用清单里面的合计
function countCharge(value) {
    var chargeInfo = $('#chargeCustomer').bootstrapTable('getData');
    var totalFee = 0.00;
    var totalReduceMount = 0.00;
    var totalPaidAmount = 0.00;
    for (var i = 0; i < chargeInfo.length; i++) {
        totalFee = totalFee + Number(chargeInfo[i].receiveAmount);
        totalReduceMount = totalReduceMount + Number(chargeInfo[i].reduceMount);
        totalPaidAmount = totalFee - totalReduceMount;
    }
    $("#totalCharge").val(totalFee.toFixed(2));
    $("#totalReduceMount").val(totalReduceMount.toFixed(2));
    $("#totalPaidAmount").val(totalPaidAmount.toFixed(2));
}

function chargeTypeChange() {
    var chargeType = $("#chargeType").val();
    var chargeTypeInfo = chargeType.split(",");
    $("#chargeTypeId").val(chargeTypeInfo[0]);
    $("#chargeTypeNo").val(chargeTypeInfo[1]);
    $("#chargeTypeName").val(chargeTypeInfo[2]);
    var buildArea = Number($("#buildArea").val());
    var typeFlag = chargeTypeInfo[3];// 00 杂费;01 物业费;02 装修押金;03 停车费;99	系统内部使用费用
    var chargeMode = chargeTypeInfo[4];//计费方式 01:建面 02定额 03公式
    var chargePrice = Number(chargeTypeInfo[5]);//单价
    $("#chargePrice").val(chargeTypeInfo[5]);

    var mothsNum = 1;
    var typeNum = 1;
    var arrAmount = 0.00;
    $(".typeNum").attr("style", "display:none;")
    $(".monthsNum").attr("style", "display:none;")

    switch (typeFlag) {
        case "00":
            $(".typeNum").attr("style", "display:block;");
            typeNum = Number($("#typeNum").val());
            if (chargeMode == "01") {
                arrAmount = buildArea * chargePrice * typeNum;
            } else {
                arrAmount = chargePrice * typeNum;
            }
            $("#beginDate").val(new Date().format("yyyy-MM-dd"))
            $("#endDate").val(new Date().AddMonths(1).format("yyyy-MM-dd"));
            break;
        case "01":
            mothsNum = Number($("#monthsNum").val());
            $(".monthsNum").attr("style", "display:block;");
            var beginDate = new Date(json2Date($("#expDate").val()));
            $("#beginDate").val(beginDate.format("yyyy-MM-dd"))

            var beginTime = new Date($("#beginDate").val());
            $("#endDate").val(beginTime.AddMonths(mothsNum).format("yyyy-MM-dd"));
            arrAmount = buildArea * chargePrice * mothsNum;
            break;
        case "02":
            arrAmount = chargePrice;
            $("#beginDate").val(new Date().format("yyyy-MM-dd"))
            $("#endDate").val(new Date().AddMonths(3).format("yyyy-MM-dd"));
            break;
        case "03":
            $(".monthsNum").attr("style", "display:block;");
            mothsNum = Number($("#monthsNum").val());
            arrAmount = chargePrice * mothsNum;
            break;
        default:
            break;
    }
    ;
    $("#paidAmount").val(arrAmount);
    $("#receiveAmount").val(arrAmount);
};

function AccReduce(value,type) {
    //调整减免
    if(type=='01'){
        var receiveAmount = $("#receiveAmount").val();
        var paidAmount = Number(receiveAmount) - Number(value);
        if (paidAmount < 0) {
            alert("减免金额已经大于应收金额，请重新填写");
        }
    }else{
        var reduceMount = $("#reduceMount").val();
        var paidAmount = Number(value) - Number(reduceMount)
    }
    $("#paidAmount").val(paidAmount);
};

function deleteFile(obj) {
    var filePath = JSON.parse(obj);
    var data = {
        filePath: filePath.key
    };
    $.ajax({
        type: "post",
        data: data,
        url: getRootPath() + "mainFrame/mainFrameInfo.app?method=fileDelete",
        dataType: 'json',
        success: function (data) {
            var jsonData = JSON.parse(data);
            if (jsonData != null && jsonData.status != 1) {
                layer.msg('操作失败！', {
                    time: 3000
                });
                return;
            }
        }
    })
}
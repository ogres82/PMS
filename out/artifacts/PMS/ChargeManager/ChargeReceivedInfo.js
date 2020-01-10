
$(function() {
	toolbarBtnInit();

	// 初始化Table
	var oTable = new TableInit();
	oTable.Init();

	$(".search").css({
		'position' : 'fixed',
		'right' : '115px',
		'top' : '0',
		'z-index' : '1000',
		'width' : '240px'
	});
	$(".search input").attr("placeholder", "搜索");
	$(".search input").css({
		'padding-right' : '23px'
	});

});
var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#chargeReceivedInfo').bootstrapTable({
			url : './../ChargeManager/ChargeInfoList.app?method=ReceivedList', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			showFooter : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "asc",
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			// clickToSelect: true,
			queryParams : oTableInit.queryParams,
			showExport : "true",
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			showPaginationSwitch : false,
			pagination : true,
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
			columns : [ {
				field : 'sta',
				checkbox : true,
				footerFormatter : totalLabel
			}, {
				field : 'community_name',
				visible : false
			}, {
				field : 'storied_build_name',
				visible : false
			}, {
				field : 'room_no',
				visible : false
			},{
				field : 'room_addrs',
				title : '房号',
				formatter : roomAddrs,
				footerFormatter : totalReceive
			}, {
				field : 'owner_name',
				title : '业主',
				footerFormatter : totalPaid
			}, {
				field : 'charge_type_name',
				title : '收费项名称'
			}, {
				field : 'price',
				title : '单价',
				formatter : numberFormate
			}, {
				field : 'count',
				title : '面积'
			}, {
				field : 'receive_amount',
				title : '应收金额',
				formatter : numberFormate
			}, {
				field : 'paid_amount',
				title : '实收金额',
				formatter : numberFormate
			}, {
				field : 'begin_time',
				title : '计费开始日期',
				formatter : dateFormate
			// 该列数据转换，执行dateFormate函数
			}, {
				field : 'end_time',
				title : '计费结束日期',
				formatter : dateFormate
			}, {
				field : 'paid_date',
				title : '收款日期',
				formatter : dateTimeFormate
			}, {
				field : 'drop_paid_mode',
				title : '收款方式'
			}, {
				field : 'oper_emp_id',
				title : '收款人'
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				events : operateEvents,
				formatter : operateFormatter
			} ]
		});
	};

	// 操作列的显示风格
	function operateFormatter(value, row, index) {
		return tableBtnInit();
	}
	// 操作列的事件
	window.operateEvents = {
		'click #a_check' : function(e, value, row, index) {
			edit(row, "check");
		}
	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : params.search
		};
		return temp;
	};
	dateFormate = function(value) {
		if (value == null || value == "") {
			return "";
		} else {
			return json2Date(value);
		}

	};

	dateTimeFormate = function(value) {
		if (value == null || value == "") {
			return "";
		} else {
			return json2TimeStamp(value);
		}

	};

	// 第一列当成了小计说明
	totalLabel = function() {
		return '小计：'
	}

	// 应收栏汇总
	totalReceive = function(data) {
		var total = 0;
		$.each(data, function(i, row) {
			total = accAdd(total, row.receive_amount);
		});
		return "应收金额：" + outputmoney("" + total) + "元";
	};

	// 已收栏汇总
	totalPaid = function(data) {
		var total = 0;
		$.each(data, function(i, row) {
			total = accAdd(total, row.paid_amount);
		});
		return "实收金额：" + outputmoney("" + total) + "元";
	};

	numberFormate = function(value, row) {
		return outputmoney("" + value);
	};
	
	// 地址拼接
	roomAddrs = function(value, row) {
		var strBuild = row.storied_build_name;
		if (row.room_type == '2') {
			strBuild = '';
		}
		return row.community_name + strBuild + row.room_no;
	};


	return oTableInit;

};

function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}

/* 编辑按钮，弹出对话框 */
function edit(obj, type) {
	$("#charge_no").val(obj.charge_no);
	$("#room_no").val(obj.room_no);
	$("#owner_name").val(obj.owner_name);
	$("#charge_type_no").val(obj.charge_type_no);
	$("#charge_type_name").val(obj.charge_type_name);
	$("#price").val(obj.price);
	$("#count").val(obj.count);
	$("#rate").val(obj.rate);
	$("#begin_time").val(json2Date(obj.begin_time));
	$("#end_time").val(json2Date(obj.end_time));
	$("#receive_amount").val(obj.receive_amount);
	$("#paid_amount").val(obj.paid_amount);
	$("#arrearage_amount").val(obj.arrearage_amount);
	$("#state").val(obj.state);
	$("#paid_mode").val(obj.drop_paid_mode);
	$("#paid_date").val(json2TimeStamp(obj.paid_date));
	$("#oper_emp_id").val(obj.oper_emp_id);
	$("#update_date").val(json2TimeStamp(obj.update_date));
	$("#remark").val(obj.remark);
	$("#charge_id").val(obj.charge_id);

	if (type == "check") {
		$("#button_save").hide();
		$("#button_close").hide();
		$("#myForm .form-control").attr("disabled", true);
	}

	$("#myModal").modal();
}

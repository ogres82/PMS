$(function() {
	toolbarBtnInit();

	// 初始化Table
	var oTable = new TableInit();
	oTable.Init();

	// 初始化下拉框
	initDrops();
	initChargeNoDrops();

	// 查询按钮
	$('#buttonSearch').click(function() {
		$('#chargeInfo').bootstrapTable('refresh', null);
	});

	// 编辑按钮
	$('#btnEdit').click(function() {
		var arr = $('#chargeInfo').bootstrapTable('getSelections');
		if (arr.length == 0) {
			layer.alert("请选择一条记录操作", {
				skin : 'layui-layer-molv'
			});
			return;
		} else if (arr.length > 1) {
			layer.alert("只能选择一条记录操作", {
				skin : 'layui-layer-molv'
			});
			return;
		} else {
			edit(arr[0]);
		}

	});

	// 删除按钮
	$('#btnDel').click(function() {
		var arr = $('#chargeInfo').bootstrapTable('getSelections');
		if (arr.length == 0) {
			layer.alert("请选择一条记录操作", {
				skin : 'layui-layer-molv'
			});
			return;
		} else if (arr.length > 1) {
			layer.alert("只能选择一条记录操作", {
				skin : 'layui-layer-molv'
			});
			return;
		}

		layer.confirm("您确定要删除所选信息吗?", {
			skin : 'layui-layer-molv',
			btn : [ '确定', '取消' ]
		}, function() {
			del(arr[0]);
			layer.closeAll('dialog');
		}, function() {
			return;
		})
	});

	$(".search").css({
		'position' : 'fixed',
		'right' : '10px',
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
		$('#chargeInfo').bootstrapTable({
			url : './../ChargeManager/ChargeInfoList.app?method=list', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			showFooter : true,
			search : true,
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
				field : 'community_name',
				visible : false
			}, {
				field : 'storied_build_name',
				visible : false
			}, {
				field : 'room_type',
				visible : false
			}, {
				field : 'room_no',
				visible : false
			}, {
				field : 'room_addrs',
				title : '房号',
				formatter : roomAddrs,
				footerFormatter : totalLabel
			}, {
				field : 'owner_name',
				title : '业主',
				footerFormatter : totalReceive
			}, {
				field : 'charge_type_name',
				title : '收费项名称',
				footerFormatter : totalPaid
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
			show(row);
		}
	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : params.search,
			begin_time : $("#beginTime").val(),
			end_time : $("#endTime").val(),
			paid_mode_select : $("#paid_mode_select").val(),
			charge_type_name_select : $("#charge_type_name_select").val()
		};
		return temp;
	};
	dateTimeFormate = function(value) {
		if (value == null || value == "") {
			return "";
		} else {
			return json2TimeStamp(value);
		}

	};

	dateFormate = function(value) {
		if (value == null || value == "") {
			return "";
		} else {
			return json2Date(value);
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

// 显示详情
function show(obj) {
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
	$("#paid_mode").val(obj.paid_mode);
	$("#paid_date").val(json2Date(obj.paid_date));
	$("#oper_emp_id").val(obj.oper_emp_id);
	$("#update_date").val(json2Date(obj.update_date));
	$("#remark").val(obj.remark);
	$("#charge_id").val(obj.charge_id);

	$("#button_save").hide();
	$("#button_close").hide();
	$("#myForm .form-control").attr("disabled", true);
	$("#myModal").modal();
}

/* 编辑按钮，弹出对话框 */
function edit(obj) {
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
	$("#paid_mode").val(obj.paid_mode);
	$("#paid_date").val(json2Date(obj.paid_date));
	$("#oper_emp_id").val(obj.oper_emp_id);
	$("#update_date").val(json2Date(obj.update_date));
	$("#remark").val(obj.remark);
	$("#charge_id").val(obj.charge_id);

	$("#myForm .form-control").attr("disabled", false);
	$("#button_save").show();
	$("#button_close").show();
	$("#myModal").modal();
}

function del(obj) {
	$.ajax({
		type : "post",
		data : obj,
		url : './../ChargeManager/ChargeInfoList.app?method=delete',
		async : true,
		success : function(data) {
			$('#chargeInfo').bootstrapTable('refresh', null);
			return;
		}
	});
}

// 保存 表单中的内容
function save() {
	datas = {
		charge_no : $("#charge_no").val(),
		room_no : $("#room_no").val(),
		owner_name : $("#owner_name").val(),
		charge_type_no : $("#charge_type_no").val(),
		charge_type_name : $("#charge_type_name").val(),
		price : $("#price").val(),
		count : $("#count").val(),
		rate : $("#rate").val(),
		begin_time : $("#begin_time").val(),
		end_time : $("#end_time").val(),
		receive_amount : $("#receive_amount").val(),
		paid_amount : $("#paid_amount").val(),
		arrearage_amount : $("#arrearage_amount").val(),
		state : $("#state").val(),
		paid_mode : $("#paid_mode").val(),
		paid_date : $("#paid_date").val(),
		oper_emp_id : $("#oper_emp_id").val(),
		update_date : $("#update_date").val(),
		remark : $("#remark").val(),
		charge_id : $("#charge_id").val()
	};

	$.ajax({
		type : "post",
		data : datas,
		url : './../ChargeManager/ChargeInfoList.app?method=update',
		async : true,
		success : function(data) {
			$('#chargeInfo').bootstrapTable('refresh', null);
		}
	});
}

// 初始化下拉框
function initDrops() {
	var url = "./../ChargeManager/ChargeInfoList.app?method=initDropAll";
	$.post(url, function(data) {

		var list = eval(data);
		// alert(list);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			var code = detailList[0];
			// 账单状态
			if (code == 'charge_state') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#state");
			}
			// 支付方式
			if (code == 'charge_paid_mode') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#paid_mode");
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#paid_mode_select");
			}

		}
		;
	});

}

// 初始化收费项下拉框
function initChargeNoDrops() {
	var url = "./../ChargeManager/ChargeType.app?method=dropdownChargeType";
	$.post(url, function(data) {
		var list = eval(data);
		// alert(list);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			// 收费类型
			$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#charge_type_name_select");
		}
		;
	});

}

function clearSearch() {
	$("#charge_type_name_select").val("");
	$("#paid_mode_select").val("");
	$("#beginTime").val("");
	$("#endTime").val("");
}
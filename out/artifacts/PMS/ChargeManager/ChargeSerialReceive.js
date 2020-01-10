$(function() {
	toolbarBtnInit();
	// 初始化Table
	var oTable = new TableInit();
	oTable.Init();
	// 初始化详情Table
	var detail = new TableDetial();
	detail.Init();
	// 初始化下拉框
	initDrops();
	initChargeNoDrops();
	// 查询按钮
	$('#buttonSearch').click(function() {
		$('#chargeSerial').bootstrapTable('refresh', null);
	});
	// 定位表格查询框
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
		$('#chargeSerial').bootstrapTable({
			url : './../ChargeManager/ChargeSerial.app?method=serialList&charge_state=01', // 请求后台的URL（*）
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
			pageList : '[10, 25, 50, 100, ALL]', // 可供选择的每页的行数（*）
			columns : [ {
				field : 'sta',
				checkbox : true,
				footerFormatter : totalLabel
			}, {
				field : 'community_name',
				visible : false
			}, {
				field : 'room_type',
				visible : false
			}, {
				field : 'storied_build_name',
				visible : false
			}, {
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
				field : 'receive_amount',
				title : '应收金额',
				formatter : numberFormate
			}, {
				field : 'paid_amount',
				title : '实收金额',
				formatter : numberFormate
			}, {
				field : 'begin_date',
				title : '开始时间',
				formatter : dateFormate
			}, {
				field : 'end_date',
				title : '结束时间',
				formatter : dateFormate
			}, {
				field : 'paid_date',
				title : '收款日期',
				formatter : dateTimeFormate
			}, {
				field : 'drop_charge_type',
				title : '类型'
			}, {
				field : 'drop_paid_mode',
				title : '收款方式'
			}, {
				field : 'drop_state',
				title : '状态'
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
			state_select : $("#state_select").val(),
			charge_type_select : $("#charge_type_select").val(),
			paid_mode_select : $("#paid_mode_select").val(),
			charge_type_name_select : $("#charge_type_name_select").val()
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

	numberFormate = function(value, row) {
		if (row.state == '02') {
			return outputmoney("" + (0 - Number(value)));
		} else if (row.state == '01') {
			return outputmoney("" + value);
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
			if (row.state == '02') {
				total = accAdd(total, 0 - row.receive_amount);
			} else if (row.state == '01') {
				total = accAdd(total, row.receive_amount);
			}
		});
		return "应收金额：" + outputmoney("" + total) + "元";
	};

	// 已收栏汇总
	totalPaid = function(data) {
		var total = 0;
		$.each(data, function(i, row) {
			if (row.state == '02') {
				total = accAdd(total, 0 - row.paid_amount);
			} else if (row.state == '01') {
				total = accAdd(total, row.paid_amount);
			}
		});
		return "实收金额：" + outputmoney("" + total) + "元";
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

// 收费详情
var TableDetial = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#chargeSerialDetailInfo').bootstrapTable({
			url : './../ChargeManager/ChargeSerial.app?method=chargeSerialDetailInfo', // 请求后台的URL（*）
			striped : true,
			showFooter : true,
			showColumns : false, // 是否显示所有的列
			sortable : false,
			pagination : true,
			sortOrder : "asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			queryParams : oTableInit.queryParams,
			showExport : false,
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			showPaginationSwitch : false,
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10000, // 每页的记录行数（*）
			pageList : '[10000]', // 可供选择的每页的行数（*）
			columns : [ {
				field : 'sta',
				checkbox : true,
				footerFormatter : totalLabel
			}, {
				field : 'room_no',
				title : '房号',
				footerFormatter : totalReceiveDe
			}, {
				field : 'owner_name',
				title : '业主',
				footerFormatter : totalPaidDe
			}, {
				field : 'charge_type_name',
				title : '收费项名称'
			}, {
				field : 'receive_amount',
				title : '应收金额',
				formatter : numberFormateDetail
			}, {
				field : 'paid_amount',
				title : '实收金额',
				formatter : numberFormateDetail
			}, {
				field : 'begin_time',
				title : '开始日期',
				formatter : dateFormate
			// 该列数据转换，执行dateFormate函数
			}, {
				field : 'end_time',
				title : '结束日期',
				formatter : dateFormate
			}, {
				field : 'paid_date',
				title : '收款日期',
				formatter : dateFormate
			}, {
				field : 'drop_paid_mode',
				title : '收款方式'
			}, {
				field : 'oper_emp_id',
				title : '收款人'
			}, {
				field : 'remark',
				title : '备注'
			} ]
		});
	};

	// 操作列的显示风格
	function operateFormatter(value, row, index) {
		return [ '<a class="like" href="javascript:void(0)" title="Like">', '<i class="glyphicon glyphicon-heart">详情</i>', '</a>  ' ].join('');
	}
	// 操作列的事件
	window.operateEvents = {
		'click .like' : function(e, value, row, index) {
			show(row);
		}
	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : params.search,
			date_select : $("#date_select").val(),
			date_search : $("#date_search").val()
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

	// 应收栏汇总
	totalReceiveDe = function(data) {
		var total = 0;
		$.each(data, function(i, row) {
			total = accAdd(total, row.receive_amount);
		});
		return "应收金额：" + outputmoney("" + total) + "元";
	};

	// 已收栏汇总
	totalPaidDe = function(data) {
		var total = 0;
		$.each(data, function(i, row) {
			total = accAdd(total, row.paid_amount);
		});
		return "实收金额：" + outputmoney("" + total) + "元";
	};

	numberFormateDetail = function(value, row) {
		return outputmoney("" + value);
	};

	return oTableInit;

};

// 显示详情
function show(obj) {

	$('#chargeSerialDetailInfo').bootstrapTable('refresh', {
		query : {
			serial_id : obj.serial_id
		}
	});
	$("#myModal").modal();
}

function add() {
	$('#myForm').validationEngine('hide');

	$("#room_no").val(null);
	$("#owner_name").val(null);
	$("#room_id").val(null);
	$("#owner_id").val(null);
	$("#paid_type").val(null);
	$("#count").val(null);
	$("#paid_date").val(null);
	$("#paid_mode").val(null);

	$("#myModal").modal();

	// 初始化下拉框：房间业主
	initDropdownHouseType();
}

// 保存 表单中的内容
function save() {
	var flag = $('#myForm').validationEngine('validate');
	if (flag) {
		datas = {
			room_no : $("#room_no").val(),
			owner_name : $("#owner_name").val(),
			room_id : $("#room_id").val(),
			owner_id : $("#owner_id").val(),
			paid_type : $("#paid_type").val(),
			count : $("#count").val(),
			paid_date : $("#paid_date").val(),
			paid_mode : $("#paid_mode").val()
		};

		$.ajax({
			type : "post",
			data : datas,
			url : './../ChargeManager/ChargeSerial.app?method=insert',
			async : true,
			success : function(data) {
				$('#myModal').modal('hide');
				$('#chargeAdvance').bootstrapTable('refresh', null);
			}
		});
	} else {
		layer.msg('表单验证未通过！', {
			time : 2000
		});
	}

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
			// 状态
			if (code == 'charge_serial_state') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#state");
			}
			// 支付方式
			if (code == 'charge_paid_mode') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#paid_mode");
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#paid_mode_select");
			}
			// 支付方式
			if (code == 'charge_serial_type') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#paid_type");
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#charge_type_select");
			}

		}
		;
	});

}

// 初始化下拉框：房间业主信息
function initDropdownHouseType() {
	// 初始化下拉框：房间业主信息
	var addressSuggest = $("#room_no").bsSuggest({
		url : "./../ChargeManager/ChargeType.app?method=dropdownHourseOwner&keyword=",
		showHeader : true,
		allowNoKeyword : false,
		keyField : 'room_no',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : ["community_name", "storied_build_name","unit_name","room_no", "owner_name", "phone"],
		effectiveFieldsAlias : {
			community_name : "小区",
			storied_build_name : "楼宇",
			unit_name : "单元",
			room_no : "房间号",
			owner_name : "业主",
			phone : "电话"
		},

		// 预处理
		fnPreprocessKeyword : function(keyword) {
			// 请求数据前，对输入关键字作预处理
			return encodeURI(keyword);
		},

		processData : function(json) { // url 获取数据时，对数据的处理，作为 getData 的回调函数

			var index, len, data = {
				value : []
			};
			if (!json || !json.result || json.result.length === 0) {
				return false;
			}

			len = json.result.length;

			for (index = 0; index < len; index++) {
				data.value.push({
					"community_name": json.result[index][0],
					"storied_build_name": json.result[index][1],
					"unit_name": json.result[index][2],
					"room_no": json.result[index][3],
					"owner_name": json.result[index][4],
					"phone": json.result[index][5],		
					"room_state": json.result[index][6],
					"room_state_name": json.result[index][7],
					"room_type": json.result[index][8],
					"charge_date": json.result[index][9],
					"charge_price": json.result[index][10],
					"months_price": json.result[index][11],
					"owner_id": json.result[index][12],
					"room_id": json.result[index][13]
				});
			}
			// 字符串转化为 js 对象

			return data;
		}
	}).on("onDataRequestSuccess", function(e, result) {
		console.log("onDataRequestSuccess: ", result)
	}).on("onSetSelectValue", function(e, keyword, data) {
		console.log("onSetSelectValue: ", keyword, data);
		$("#room_id").val(data.room_id);
		$("#room_no").val(data.room_no);
		$("#owner_id").val(data.owner_id);
		$("#owner_name").val(data.owner_name);
	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});
}

// 初始化下拉框：收费项类型
function initDropdownChargeType() {
	// 初始化下拉框：收费项类型
	var addressSuggest = $("#paid_type").bsSuggest({
		url : "./../ChargeManager/ChargeType.app?method=dropdownChargeType&type=02&keyword=",
		showHeader : true,
		allowNoKeyword : false,
		keyField : 'charge_type_name',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : [ "charge_type_no", "charge_type_name" ],
		effectiveFieldsAlias : {
			charge_type_no : "项目编号",
			charge_type_name : "项目名称"
		},

		// 预处理
		fnPreprocessKeyword : function(keyword) {
			// 请求数据前，对输入关键字作预处理
			return encodeURI(encodeURI(keyword));
		},

		processData : function(json) { // url 获取数据时，对数据的处理，作为 getData 的回调函数

			var index, len, data = {
				value : []
			};
			if (!json || !json.result || json.result.length === 0) {
				return false;
			}

			len = json.result.length;

			for (index = 0; index < len; index++) {
				data.value.push({
					"charge_type_no" : json.result[index][1],
					"charge_type_name" : json.result[index][2],
					"charge_type_id" : json.result[index][0]
				});
			}
			// 字符串转化为 js 对象

			return data;
		}
	}).on("onDataRequestSuccess", function(e, result) {
	}).on("onSetSelectValue", function(e, keyword, data) {
		$("#paid_type").val(data.charge_type_name);
	}).on("onUnsetSelectValue", function(e) {
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
	$("#charge_type_select").val("");
	$("#paid_mode_select").val("");
	$("#state_select").val("");
	$("#beginTime").val("");
	$("#endTime").val("");
}
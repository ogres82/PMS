selections = [];

$(function() {
	initPermissions();
	initBtnEvent();
	init();
	initSearch();// 初始化搜索框
	initReciveDrop();
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
	$('#myForm1').validationEngine();
	$('#myForm2').validationEngine();
	$(document).ajaxStop($.unblockUI);
});

function initPermissions() {
	toolbarBtnInit(); // 初始化工具条按钮
}

function initBtnEvent() {
	var urlmethod = "";
	for (var i = 0; i < btnIdArray.length; i++) {
		// 新增
		if (btnIdArray[i] == "btn_add") {
			$("#btn_add").bind('click', function() {
				clearForm();
				$('#myForm1').validationEngine('hide');
				$('#myForm2').validationEngine('hide');
				$("#btDecoApplyAdd").show();
				$("#btDecoApplyClose").show();
				$("#d_btn1").attr("disabled", false);
				$("#d_btn2").attr("disabled", false);
				$("#roomNo").attr("disabled", false);
				$("#roomNo_d").attr("disabled", false);
				$("#decorateStartDate").attr("disabled", false);
				$("#decoratePlanDate").attr("disabled", false);
				$("#remark").attr("disabled", false);
				$("#myModalTitle").html("装修申请");
				$('#chargeStandardInfo').bootstrapTable("removeAll");
				$('#myModal1').modal({
					backdrop : 'static',
					keyboard : false
				});
			});
		}
		// 删除
		if (btnIdArray[i] == "btn_del") {
			$("#btn_del").bind('click', function() {
				var selections = getIdSelections();
				urlmethod = "method=delDecorate";
				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = "./../houseProperty/housePropertyList.app?" + urlmethod;
					$.post(url, {
						roomId : selections + ""
					}, function(data) {
						if (eval(data)  == "success") {
							layer.msg('操作成功！', {
								time : 2000
							}, function() {
								$('#housePropertyInfo').bootstrapTable('refresh');
							});
						} else {
							layer.msg('操作失败', {
								time : 2000
							});
						}
					});
				}, function() {
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
	new chargeStandardTable().Init();
	new paymentDetailsTable().Init();
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#housePropertyInfo').bootstrapTable({
			url : './../houseProperty/housePropertyList.app?method=decorateHouseList', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "asc",
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : false,
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
				field : 'state',
				checkbox : true,
			}, {
				field : 'roomId',
				visible : false,
				title : 'ID'
			}, {
				field : 'build_name',
				visible : false,
				title : '所属楼盘'
			}, {
				field : 'community_name',
				title : '所属小区'
			}, {
				field : 'storied_build_name',
				title : '所属楼宇'
			}, {
				field : 'roomNo',
				title : '房间号'
			}, {
				field : 'buildArea',
				title : '建筑面积（㎡）'
			}, {
				field : 'ownerName',
				title : '客户名称'
			}, {
				field : 'decorateStartDate',
				title : '装修开始时间',
				formatter : dateFormate
			}, {
				field : 'decoratePlanDate',
				title : '拟结束时间',
				formatter : dateFormate
			}, {
				field : 'decorateEndDate',
				title : '装修结束时间',
				formatter : dateFormate
			}, {
				field : 'room_state_name',
				title : '房间状态'
			}, {
				field : 'di.operName',
				title : '装修负责人'
			},  {
				field : 'di.operPhone',
				title : '负责人号码'
			},{
				field : 'di.remark',
				visible : false,
				title : '备注'
			},{
				field : 'operate',
				title : '操作',
				align : 'center',
				width : '15%',
				events : operateEvents,
				formatter : operateFormatter
			} ]
		});
	};
	function operateFormatter(value, row, index) {
		return tableBtnInit();
	}
	// 操作列的事件
	window.operateEvents = {
		'click #a_check' : function(e, value, row, index) {
			editOrCheck(row, 1);
		},
		'click #a_edit' : function(e, value, row, index) {
			editOrCheck(row, 2);
		},
		'click #a_finish' : function(e, value, row, index) {
			editOrCheck(row, 3);
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
	return oTableInit;

};

/**
 * 初始化收费项下拉框
 */
function initReciveDrop() {
	var url = "./../houseProperty/housePropertyList.app?method=initDecorateProject";
	$.post(url, function(data) {

		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			// 员工部门
			$("<option value='" + detailList.charge_type_id + "' data='" + JSON.stringify(detailList) + "'>" + detailList.charge_type_name + "</option>").appendTo("#charge_type_id");
		}
		;
	});
}

// 收费标准列表
var chargeStandardTable = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#chargeStandardInfo').bootstrapTable({
			url : './../houseProperty/housePropertyList.app?method=decorateChargeInfo', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : false,
			search : false,
			searchOnEnterKey : false,
			showColumns : false, // 是否显示所有的列
			showRefresh : false, // 是否显示刷新按钮
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : true,
			queryParams : oTableInit.queryParams,
			showExport : false,
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			showPaginationSwitch : false,
			pagination : false,
			columns : [ {
				field : 'charge_type_no',
				visible : false,
				title : 'no'
			}, {
				field : 'room_id',
				visible : false,
				title : 'ID'
			}, {
				field : 'room_no',
				visible : false,
				title : '房间号'
			}, {
				field : 'owner_id',
				visible : false,
				title : 'ownerId'
			}, {
				field : 'charge_type_name',
				title : '收费项目'
			}, {
				field : 'charge_mode',
				title : '计费方式'
			}, {
				field : 'charge_way',
				title : '收费方式'
			}, {
				field : 'charge_paid_mode',
				title : '支付方式',
				formatter : mapMode
			}, {
				field : 'charge_type',
				title : '收费类型'
			}, {
				field : 'price',
				title : '单价'
			}, {
				field : 'receive_amount',
				title : '指定金额'
			} ]
		});
	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			roomId : $("#roomId").val(),
			ownerId : $("#ownerId").val()
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
	mapMode = function(value, row) {
		if (row.charge_type_name.indexOf("押金") >= 0) {
			return "现金";
		} else {
			return "-";
		}
	};
	return oTableInit;

};
// 装修完成列表
var paymentDetailsTable = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#paymentDetailsInfo').bootstrapTable({
			url : './../houseProperty/housePropertyList.app?method=decorateFinishInfo', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : false,
			searchOnEnterKey : false,
			sortable : false,
			sortOrder : "asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : true,
			queryParams : oTableInit.queryParams,
			showExport : false,
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			showPaginationSwitch : false,
			columns : [ {
				field : 'charge_type_name',
				title : '收费项目'
			}, {
				field : 'receive_amount',
				title : '金额'
			}, {
				field : 'paid_date',
				title : '收款日期',
				formatter : dateFormate
			} ]
		});
	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			roomId : $("#roomId").val(),
			ownerId : $("#ownerId_f").val()
		};
		return temp;
	};
	dateFormate = function(value) {
		if (value == null || value == "") {
			return "";
		} else {
			return json2TimeStamp(value);
		}

	};
	return oTableInit;

};
/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($('#housePropertyInfo').bootstrapTable('getSelections'), function(row) {
		return row.roomId;
	});
}

/**
 * 保存操作
 */
function openSaveButton() {
	layer.confirm("保存后不能修改，您确定要保存吗?", {
		skin : 'layui-layer-molv',
		btn : [ '确定', '取消' ]
	}, function() {

		var flag = $('#myForm1').validationEngine('validate');
		var start = $("#decorateStartDate").val();
		var end = $("#decoratePlanDate").val();
		if (start > end && end != "") {
			layer.msg('结束时间不能小于开始时间！', {
				time : 2000
			});
			return;
		}
		var chargeStandardInfo = $('#chargeStandardInfo').bootstrapTable("getData");
		if (chargeStandardInfo.length == 0) {
			layer.msg("未选择收费项", {
				time : 2000
			});
		}
		if (flag) {
			var addJson = getDataForm();
			var roomId = $("#roomId").val();
			if (roomId == "" || roomId == null) {
				layer.msg('房间号未选择！', {
					time : 2000
				});
				return;
			}
			$.ajax({
				type : "post",
				url : "./../houseProperty/housePropertyList.app?method=addDecorateApply",
				data : addJson,
				dataType : "json",
				async : true,
				success : function(data) {
					console.log((data));
					layer.msg(data, {
						time : 2000
					}, function() {
						$('#housePropertyInfo').bootstrapTable('refresh');
						$('#myModal1').modal('hide');
					});

				},
				failure : function(xhr, msg) {

				}
			});
		} else {
			layer.msg('表单验证未通过！', {
				time : 3000
			});
		}
	}, function() {
		return;
	})

}

/**
 * 获取表单数据
 * 
 * @returns {___anonymous7092_7851}
 */
function getDataForm() {

	var roomId = $("#roomId").val();
	var ownerName = $("#ownerName").val();
	var decorateStartDate = $("#decorateStartDate").val();
	var decoratePlanDate = $("#decoratePlanDate").val();
	var remark = $("#remark").val();
	var chargeStandardInfo = $('#chargeStandardInfo').bootstrapTable("getData");
	var decorateInfo = {
			roomId:roomId,
			operName:$("#operName").val(),
			operPhone:$("#operPhone").val(),
			decorateContent:$("#decorateContent").val(),
			remark : remark
			}
	var addJson = {
		roomId : roomId,
		ownerName : ownerName,
		decorateStartDate : decorateStartDate,
		decoratePlanDate : decoratePlanDate,
		chargeStandardInfo : JSON.stringify(chargeStandardInfo),
		decorateInfo : JSON.stringify(decorateInfo)
	};
	return addJson;
}

// 增加预收费
function retainerSaveButton() {
	var chargeValue = $("#charge_type_id").val();
	if (chargeValue == "") {
		layer.msg('请选择收费项！', {
			time : 2000
		});
		return;
	}
	$('#myForm1').validationEngine('hide');
	var str = $("#charge_type_id option:selected").attr("data");
	var obj = jQuery.parseJSON(str);

	var roomId = $("#roomId").val();
	var roomNo = $("#roomNo").val();
	var ownerId = $("#ownerId").val();
	var charge_paid_mode = "-";
	if (obj.charge_type_name.indexOf("押金") >= 0) {
		charge_paid_mode = "现金";
	}

	var data = {
		room_id : roomId,
		room_no : roomNo,
		owner_id : ownerId,
		charge_type_name : obj.charge_type_name,
		charge_type_no : obj.charge_type_no,
		charge_mode : obj.drop_charge_mode,
		charge_way : obj.drop_charge_way,
		charge_type : obj.drop_charge_type,
		charge_paid_mode : charge_paid_mode,
		price : obj.charge_price,
		receive_amount : obj.charge_price
	}

	var r = $('#chargeStandardInfo').bootstrapTable('getData');
	var flag = false;
	for (var i = 0; i < r.length; i++) {
		if (r[i].charge_type_no == obj.charge_type_no) {
			layer.msg('不能重复选择！', {
				time : 2000
			});
			flag = true;
			break;
		}
	}

	if (!flag) {
		$('#chargeStandardInfo').bootstrapTable('append', data);
		$("#myModal2").modal('hide');
	}
}

// 装修申请弹框
function openChargeWindow(type) {
	if (type == 1) {
		var roomId = $("#roomId").val();
		if (roomId == "") {
			layer.msg('请选择房间号！', {
				time : 2000
			});
			return;
		}
		$("#charge_type_id").val("");
		$('#myModal2').modal({
			backdrop : 'static',
			keyboard : false
		});
	}
	if (type == 2) {

	}
}

// 装修完成保存
function openDecorateFinishButton() {
	var flag = $('#myForm2').validationEngine('validate');
	if (flag) {
		var addJson = {
			roomId : $("#roomId").val(),
			decorateEndDate : $("#decorateEndDate").val(),
			decorateInstructions : $("#decorateInstructions").val()
		}
		$.ajax({
			type : "post",
			url : "./../houseProperty/housePropertyList.app?method=decorateFinish",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg(data, {
					time : 2000
				}, function() {
					$('#housePropertyInfo').bootstrapTable('refresh');
					$('#myModal3').modal('hide');
				});

			},
			failure : function(xhr, msg) {
			}
		});
	} else {
		layer.msg('表单验证未通过！', {
			time : 3000
		});
	}
}

// 清空表单
function clearForm() {

	$("#roomId").val("");
	$("#roomNo").val("");
	$("#roomNo_d").val("");
	$("#ownerName").val("");
	$("#buildArea").val("");
	$("#decorateStartDate").val("");
	$("#decoratePlanDate").val("");
	$("#operName").val("");
	$("#operPhone").val("");
	$("#decorateContent").val("");
	$("#remark").val("");
	$("#ownerId_f").val("");
}


/* 搜索框初始化 */
function initSearch() {
	// 房间下拉框
	$("#roomNo_d").bsSuggest({
		url : "./../houseProperty/housePropertyList.app?method=decorateHouseInfo&keyword=",
		showHeader : true,
		allowNoKeyword : true,
		keyField : 'keyword',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : [ "build_name", "community_name", "storied_build_name", "roomNo", "ownerName" ],
		effectiveFieldsAlias : {
			build_name : "楼盘",
			community_name : "小区",
			storied_build_name : "楼宇",
			roomNo : "房间号",
			ownerName : "业主姓名"
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
			if (!json || json.length === 0) {
				return false;
			}
			data.value = json;

			return data;
		}
	}).on("onDataRequestSuccess", function(e, result) {
		console.log("onDataRequestSuccess: ", result)
	}).on("onSetSelectValue", function(e, keyword, data) {
		console.log("onSetSelectValue: ", keyword, data);
		$("#roomNo_d").val(data.build_name + "-" + data.community_name + "-" + data.storied_build_name + "-" + data.roomNo);
		$("#roomNo").val(data.roomNo);
		$("#roomId").val(data.roomId);
		$("#ownerId").val(data.ownerId);
		$("#ownerName").val(data.ownerName);
		$("#buildArea").val(data.buildArea);
	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue");
		$("#roomId").val("");
		$("#ownerId").val("");
		$("#buildArea").val("");
	});

}

// 查看和编辑
function editOrCheck(obj, type) {
	clearForm();
	$('#myForm1').validationEngine('hide');
	if (type == 3) {
		if (obj.roomState == "4") {
			layer.msg('该房间已入住！', {
				time : 2000
			});
		} else {
			$("#ownerId_f").val(obj.ownerId);
			$("#roomId").val(obj.roomId);
			$("#roomNo_f").val(obj.build_name + "-" + obj.community_name + "-" + obj.storied_build_name + "-" + obj.roomNo);
			$("#decorateStartDate_f").val(json2Date(obj.decorateStartDate));
			$("#decoratePlanDate").val("");
			$("#roomNo_f").attr("disabled", true);
			$('#paymentDetailsInfo').bootstrapTable('refresh');
			$('#myModal3').modal({
				backdrop : 'static',
				keyboard : false
			});
		}
	} else {
		if (type == 1) {
			$("#btDecoApplyAdd").hide();
			$("#myModalTitle").html("查看");
			$("#btDecoApplyAdd").hide();
			$("#d_btn1").attr("disabled", true);
			$("#decorateStartDate").attr("disabled", true);
			$("#decoratePlanDate").attr("disabled", true);
			$("#remark").attr("disabled", true);

			$("#operName").attr("disabled", true);
			$("#operPhone").attr("disabled", true);
			$("#decorateContent").attr("disabled", true);
		} else {
			$("#btDecoApplyAdd").show();
			$("#myModalTitle").html("编辑");
			$("#decorateStartDate").removeAttr("disabled");
			$("#decoratePlanDate").removeAttr("disabled");
			$("#remark").removeAttr("disabled");
			$("#d_btn1").removeAttr("disabled");

			$("#operName").removeAttr("disabled");
			$("#operPhone").removeAttr("disabled");
			$("#decorateContent").removeAttr("disabled");
		}
		$("#roomNo_d").attr("disabled", true);
		$("#roomNo").attr("disabled", true);
		$("#roomId").val(obj.roomId);
		$("#roomNo").val(obj.roomNo);
		$("#roomNo_d").val(obj.build_name + "-" + obj.community_name + "-" + obj.storied_build_name + "-" + obj.roomNo);
		$("#ownerId").val(obj.ownerId);
		$("#ownerName").val(obj.ownerName);
		$("#remark").val(obj.di.remark);
		$("#operName").val(obj.di.operName);
		$("#operPhone").val(obj.di.operPhone);
		$("#decorateContent").val(obj.di.decorateContent);
		$("#decorateStartDate").val(json2Date(obj.decorateStartDate));
		$("#decoratePlanDate").val(json2Date(obj.decoratePlanDate));
		$("#buildArea").val(obj.buildArea);
		$('#chargeStandardInfo').bootstrapTable("refresh");
		$('#myModal1').modal({
			backdrop : 'static',
			keyboard : false
		});
	}

}



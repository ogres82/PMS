selections = [];
var loginUserName = "";
var loginUserCname = "";
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
				$("#roomNo_d").removeAttr("disabled");
				$('#btReciveHouseAdd').show();
				$("#retainerSave").attr("disabled", false);
				$("#retainerDel").attr("disabled", false);
				$("#roomNo_d").attr("disabled", false);
				new retainerInfoTable().Init();
				$('#retainerInfo').bootstrapTable("refresh");
				$("#reciveRoomDate").attr("disabled", false);
				$("#roomNo").attr("disabled", false);
				$("#receiver").val(loginUserCname)
				$("#myModalTitle").html("业主收房登记");
				$('#myModal1').modal({
					backdrop : 'static',
					keyboard : false
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

/**
 * 初始化收费项下拉框
 */
function initReciveDrop() {
	var url = "./../houseProperty/housePropertyList.app?method=initRetainerProject";
	$.post(url, function(data) {
		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			$("<option value='" + detailList.charge_type_id + "' data='" + JSON.stringify(detailList) + "'>" + detailList.charge_type_name + "</option>").appendTo("#charge_type_id");
		};
	});
}

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#housePropertyInfo').bootstrapTable({
			url : './../houseProperty/housePropertyList.app?method=reciveHouseList', // 请求后台的URL（*）
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
			columns : [{
				field : 'roomId',
				visible : false,
				title : 'ID'
			}, {
				field : 'build_name',
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
				field : 'houseType',
				visible : false,
				title : '户型'
			}, {
				field : 'buildArea',
				title : '建筑面积（㎡）'
			}, {
				field : 'ownerName',
				title : '客户名称'
			}, {
				field : 'chargeDate',
				title : '物业费起征日期',
				formatter : dateFormate
			}, {
				field : 'receiveRoomDate',
				title : '实际收房日期',
				formatter : dateFormate
			}, {
				field : 'withinArea',
				visible : false,
				title : '套内面积'
			}, {
				field : 'room_type_name',
				visible : false,
				title : '房间类型'
			}, {
				field : 'room_state_name',
				visible : false,
				title : '房间状态'
			}, {
				field : 'charge_object_name',
				visible : false,
				title : '收费对象'
			}, {
				field : 'remark',
				visible : false,
				title : '备注'
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				width : '10%',
				events : operateEvents,
				formatter : operateFormatter
			}]
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

// 预收费用表格
var retainerInfoTable = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#retainerInfo').bootstrapTable({
			url : './../houseProperty/housePropertyList.app?method=chargeTypeList', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
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
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
			columns : [{
				field : 'state',
				checkbox : true,
			}, {
				field : 'room_id',
				visible : false,
				title : 'ID'
			}, {
				field : 'update_emp_id',
				visible : false,
				title : 'ID'
			}, {
				field : 'owner_id',
				visible : false,
				title : 'onwer'
			}, {
				field : 'chargeDate',
				visible : false,
				title : '起征日期'
			}, {
				field : 'charge_type_id',
				visible : false,
				title : '收费项ID'
			}, {
				field : 'charge_price',
				visible : false,
				title : '单价'
			}, {
				field : 'room_no',
				title : '房间号'
			}, {
				field : 'charge_type_no',
				title : '收费项编号'
			}, {
				field : 'charge_type_name',
				title : '收费项名称'
			}]
		});
	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			roomId : $("#roomId").val()
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
 * 保存操作
 */
function openSaveButton() {
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = getDataForm();
		if (addJson == false) {
			layer.msg('未关联收费项！', {
				time : 2000
			});
			return;
		}
		var roomId = $("#roomId").val();
		if (roomId == "" || roomId == null) {
			layer.msg('房间号未选择！', {
				time : 2000
			});
			return;
		}
		initBlockUI();
		$.ajax({
			type : "post",
			url : "./../houseProperty/housePropertyList.app?method=receiveHouse",
			data : addJson,
			dataType : "json",
			async : true,
			success : function(data) {
				if (data == "success") {
					layer.msg('登记成功！', {
						time : 2000
					}, function() {
						$('#housePropertyInfo').bootstrapTable('refresh');
						$('#myModal1').modal('hide');
					});
				} else {
					layer.msg('登记失败！', {
						time : 2000
					}, function() {

					});
				}
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
 * 获取表单数据
 * 
 * @returns {___anonymous7092_7851}
 */
function getDataForm() {

	var roomId = $("#roomId").val();
	var reciveRoomDate = $("#reciveRoomDate").val();
	var chargeDate = $("#chargeDate").val();
	var retainerInfo = $('#retainerInfo').bootstrapTable("getData");

	if (retainerInfo.length == 0) {
		return false;
	}
	var addJson = {
		roomId : roomId,
		reciveRoomDate : reciveRoomDate,
		chargeDate : chargeDate,
		operId : loginUserName,
		retainerInfo : JSON.stringify(retainerInfo)
	};
	return addJson;
}

// 二级弹出窗口
function openRetainerWindow(type) {
	if (type == 1) {
		var roomId = $("#roomId").val();
		var chargeDate = $("#chargeDate").val();
		if (roomId == "") {
			layer.msg('请选择房间号！', {
				time : 2000
			});
			return;
		}
		if (chargeDate == "") {
			layer.msg('请输入物业费起征日期！', {
				time : 2000
			});
			return;
		}

		$("#charge_type_id").val("");
		$('#myModal2').modal();
	}
	if (type == 2) {
		var selects = $('#retainerInfo').bootstrapTable('getSelections');
		if (selects.length == 0) {
			layer.msg('请选择至少一条记录操作！', {
				time : 2000
			});
			return;
		}
		var charge_type_id = new Array();
		for (var i = 0; i < selects.length; i++) {
			charge_type_id.push(selects[i].charge_type_id);
		}
		$('#retainerInfo').bootstrapTable('remove', {
			field : 'charge_type_id',
			values : charge_type_id
		});
	}
}

// 清空表单
function clearForm() {
	$("#roomNo_d").val("");
	$("#roomId").val("");
	$("#roomNo").val("");
	$("#ownerName").val("");
	$("#reciveRoomDate").val("");
	$("#chargeDate").val("");
	$("#buildArea").val("");
	$("#receiver").val("");
	$('#retainerInfo').bootstrapTable("removeAll");
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
	var str = $("#charge_type_id option:selected").attr("data");
	var obj = jQuery.parseJSON(str);

	var roomId = $("#roomId").val();
	var roomNo = $("#roomNo").val();
	var ownerId = $("#ownerId").val();
	var chargeState = setChargeState($("#chargeDate").val());
	var data = {
		charge_type_id : obj.charge_type_id,
		room_id : roomId,
		room_no : roomNo,
		owner_id : ownerId,
		charge_type_name : obj.charge_type_name,
		charge_type_no : obj.charge_type_no,
		charge_price : obj.charge_price,
		charge_state : chargeState,
		type_flag : obj.type_flag
	}
	var r = $('#retainerInfo').bootstrapTable('getData');
	var flag = false;
	for (var i = 0; i < r.length; i++) {
		if (r[i].charge_type_id == obj.charge_type_id) {
			layer.msg('不能重复选择！', {
				time : 2000
			});
			flag = true;
			break;
		}

		if (r[i].charge_type_name.indexOf("物业费") != -1 && obj.charge_type_name.indexOf("物业费") != -1) {
			layer.msg('只能选择一种物业费！', {
				time : 2000
			});
			flag = true;
			break;
		}
	}

	if (!flag) {
		$('#retainerInfo').bootstrapTable('append', data);
		$("#myModal2").modal('hide');
	}
}

/* 搜索框初始化 */
function initSearch() {
	// 房间下拉框
	$("#roomNo_d").bsSuggest({
		url : "./../houseProperty/housePropertyList.app?method=receiveHouseInfo&keyword=",
		showHeader : true,
		allowNoKeyword : true,
		keyField : 'keyword',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : ["build_name", "community_name", "storied_build_name", "roomNo"],
		effectiveFieldsAlias : {
			build_name : "楼盘",
			community_name : "小区",
			storied_build_name : "楼宇",
			roomNo : "房间号"
		},
		// 预处理
		fnPreprocessKeyword : function(keyword) {
			// 请求数据前，对输入关键字作预处理
			return encodeURI(encodeURI(keyword));
		},
		processData : function(json) { // url 获取数据时，对数据的处理，作为
			// getData 的回调函数

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
	$('#myForm1').validationEngine('hide');
	if (type == 1) {
		$('#btReciveHouseAdd').hide();
		$("#myModalTitle").html("查看");
		$("#retainerSave").attr("disabled", true);
		$("#retainerDel").attr("disabled", true);
		$("#roomNo_d").attr("disabled", true);
		$("#chargeDate").attr("disabled", true);
	} else {
		$('#btReciveHouseAdd').show();
		$("#roomNo_d").attr("disabled", "disabled");
		$("#myModalTitle").html("编辑");
	}

	$("#reciveRoomDate").attr("disabled", true);
	$("#roomNo").attr("disabled", true);
	$("#roomId").val(obj.roomId);
	$("#roomNo").val(obj.roomNo);
	$("#roomNo_d").val(obj.build_name + "-" + obj.community_name + "-" + obj.storied_build_name + "-" + obj.roomNo);
	$("#ownerId").val(obj.ownerId);
	$("#ownerName").val(obj.ownerName);
	var receiveRoomDate = obj.receiveRoomDate;
	if (receiveRoomDate != "" && receiveRoomDate != null) {
		$("#reciveRoomDate").val(json2Date(receiveRoomDate));
	} else {
		$("#reciveRoomDate").val("");
	}
	$("#buildArea").val(obj.buildArea);

	$("#chargeDate").val(dateFormate(obj.chargeDate));
	new retainerInfoTable().Init();
	$('#retainerInfo').bootstrapTable("refresh");
	var urlmethod = "method=viewReceiveHouse&roomId=" + obj.roomId;
	$.ajax({
		type : "post",
		url : "./../houseProperty/housePropertyList.app?" + urlmethod,
		dataType : "json",
		async : false,
		success : function(data) {
			$("#receiver").val(data[0].cname);
		}
	});

	$('#myModal1').modal();

}
function setChargeState(chargeDate) {
	var date = new Date();
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	var iDate = new Date(chargeDate);
	if (iDate.getTime() > lastDay.getTime()) {
		return '0';
	} else {
		return '1';
	}
}

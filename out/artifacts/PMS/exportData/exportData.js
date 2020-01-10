var dirDirectoryDetail = new Array()
$(function() {
	// initPermissions();
	// initDrops();
	init();
	initDrops();
	initBuildingPropertyDrop("");
	initAreaPropertyDrop("");
	initDirectory();
	$('#buttonSearch').click(function() {
		if ($('#selectType').val() == 0) {
			$('#ownerRoomInfo').bootstrapTable('refresh');
		} else {
			$('#roomInChargeInfo').bootstrapTable('refresh');
		}
	});
});

function initPermissions() {
	toolbarBtnInit(); // 初始化工具条按钮
}

function init() {

	// 初始化Table
	ownerTable = new ownerTableInit();
	ownerTable.Init();

	roomTable = new roomTableInit();
	roomTable.Init();
	if ($('#selectType').val() == 0) {
		$("#tempTable1").show();
		$("#tempTable2").hide();
	} else {
		$("#tempTable1").hide();
		$("#tempTable2").show();
	}
};

var ownerTableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#ownerRoomInfo').bootstrapTable({
			url : './../expBasicInfo/ownerRoomInfoExp.app?method=list', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : false,
			queryParams : oTableInit.queryParams,
			showExport : "true",
			exportTypes : [ 'excel' ],
			exportDataType : "all",
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			showPaginationSwitch : false,
			pagination : true,
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
			columns : [ {
				field : 'ownerId',
				visible : false
			}, {
				field : 'roomId',
				visible : false
			}, {
				field : 'communityId',
				visible : false
			}, {
				field : 'belongSbId',
				visible : false
			}, {
				field : 'roomNo',
				visible : false
			}, {
				field : 'communityName',
				visible : false
			}, {
				field : 'storiedBuildName',
				visible : false
			}, {
				field : 'roomAddrs',
				title : '房间号',
				formatter : addrsFormat
			}, {
				field : 'roomType',
				title : '房间类型',
				formatter : function(value, row, index) {
					return explainDirectory(value, "room_type");
				}
			}, {
				field : 'ownerName',
				title : '业主姓名'
			}, {
				field : 'sex',
				title : '性别',
				formatter : getSex
			}, {
				field : 'receiveRoomDate',
				title : '收房日期',
				formatter : dateFormat
			}, {
				field : 'buildArea',
				title : '建筑面积'
			}, {
				field : 'withinArea',
				title : '套内面积'
			}, {
				field : 'phone',
				title : '联系电话'
			}, {
				field : 'carId',
				title : '车牌号'
			}, {
				field : 'cardId',
				title : '身份证'
			}, {
				field : 'emergencyContact',
				visible : false
			}, {
				field : 'emergencyContactPhone',
				visible : false
			}, {
				field : 'ContactPhone',
				title : '紧急联系人及电话',
				formatter : formatContact
			}, {
				field : 'roomState',
				title : '房间状态',
				formatter : function(value, row, index) {
					return explainDirectory(value, "room_state");
				}
			} ],
			onCheck : function(row, e) {
				tableCheckEvents();
			},
			onUncheck : function(row, e) {
				tableCheckEvents();
			}
		});
	};
	function operateFormatter(value, row, index) {
		return tableBtnInit();
	}
	function formatContact(value, row) {
		var emergencyContact = row.emergencyContact || "无";
		var emergencyContactPhone = row.emergencyContactPhone || "无";

		return "紧急联系人:" + emergencyContact + ";" + " 紧急联系人电话:" + emergencyContactPhone;
	}
	// 根据身份证号获取性别
	function getSex(value, row, index) {
		var sexno, sex
		var cardId = row.cardId || '';
		if (cardId.length == 18) {
			sexno = cardId.substring(16, 17)
		} else if (cardId.length == 15) {
			sexno = cardId.substring(14, 15)
		} else {
			return '--'
		}
		var tempid = sexno % 2;
		if (tempid == 0) {
			sex = '女'
		} else {
			sex = '男'
		}
		return sex
	}

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : params.search,
			communityIdSearch : $("#communityIdSearch").val(),
			storiedBuildIdSearch : $("#storiedBuildIdSearch").val(),
			roomTypeSearch : $("#roomTypeSearch").val(),
			roomStateSearch : $("#roomStateSearch").val()
		};
		return temp;
	};
	dateFormat = function(value) {
		if (value == null || value == "") {
			return "";
		} else {
			return json2Date(value);
		}

	};
	addrsFormat = function(value, row) {
		var strBuild = row.storiedBuildName;
		if (row.roomType == '2') {
			strBuild = '';
		}
		return row.communityName + strBuild + row.roomNo;
	};

	return oTableInit;

};
var roomTableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#roomInChargeInfo').bootstrapTable({
			url : './../expBasicInfo/roomChargeInfoExp.app?method=list', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : false, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : false,
			queryParams : oTableInit.queryParams,
			showExport : "true",
			exportTypes : ['excel'],
			exportDataType : "all",
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			showPaginationSwitch : false,
			pagination : true,
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
			columns : [ {
				field : 'roomId',
				visible : false
			}, {
				field : 'communityName',
				visible : false
			}, {
				field : 'storiedBuildName',
				visible : false
			}, {
				field : 'roomNo',
				visible : false
			}, {
				field : 'cardId',
				visible : false
			}, {
				field : 'roomAddrs',
				title : '房间号',
				formatter : addrsFormat
			}, {
				field : 'roomType',
				title : '房屋类型',
				formatter : function(value, row, index) {
					return explainDirectory(value, "room_type");
				}
			}, {
				field : 'ownerName',
				title : '业主姓名'
			}, {
				field : 'phone',
				title : '联系电话'
			}, {
				field : 'sex',
				title : '性别',
				formatter : getSex
			}, {
				field : 'receiveRoomDate',
				title : '收房日期',
				formatter : dateFormate
			}, {
				field : 'buildArea',
				title : '建筑面积'
			}, {
				field : 'withinArea',
				title : '套内面积',
				visible : false
			}, {
				field : 'chargePrice',
				visible : false,
				title : '单价'
			}, {
				field : 'totalPrice',
				title : '每月物业费'
			}, {
				field : 'makeRoomDate',
				title : '开始时间',
				formatter : dateFormate
			}, {
				field : 'endTime',
				title : '结束时间',
				formatter : monthCalculate
			}, {
				field : 'serialAmount',
				visible : false,
				title : '缴费金额'
			}, {
				field : 'arrearageNum',
				title : '欠费月数'
			}, {
				field : 'arrearageAmount',
				title : '欠费金额'
			}, {
				field : 0,
				title : '减免'
			}, {
				field : 0,
				title : '滞纳金'
			} ],
			onCheck : function(row, e) {
				tableCheckEvents();
			},
			onUncheck : function(row, e) {
				tableCheckEvents();
			}
		});
	};
	function operateFormatter(value, row, index) {
		return tableBtnInit();
	}

	// 根据身份证号获取性别
	function getSex(value, row, index) {
		var sexno, sex
		var cardId = row.cardId || '';
		if (cardId.length == 18) {
			sexno = cardId.substring(16, 17)
		} else if (cardId.length == 15) {
			sexno = cardId.substring(14, 15)
		} else {
			return '--'
		}
		var tempid = sexno % 2;
		if (tempid == 0) {
			sex = '女'
		} else {
			sex = '男'
		}
		return sex
	}

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : params.search,
			communityIdSearch : $("#communityIdSearch").val(),
			storiedBuildIdSearch : $("#storiedBuildIdSearch").val(),
			roomTypeSearch : $("#roomTypeSearch").val(),
			roomStateSearch : $("#roomStateSearch").val()
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
	addrsFormat = function(value, row) {
		var strBuild = row.storiedBuildName;
		if (row.roomType == '2') {
			strBuild = '';
		}
		return row.communityName + strBuild + row.roomNo;
	};

	function monthCalculate(value, row, inde) {

		var startDate = new Date(row.makeRoomDate);
		var serialAmount = row.serialAmount || 0;

		if (serialAmount != 0) {

			// 转化计算后的结果，只有两个位小数
			var tmpAmount = (serialAmount / row.totalPrice).toFixed(1);

			var numArray = tmpAmount.split('.')
			var months = parseInt(numArray[0]);
			var days = parseInt(numArray[1] * 30 / 100);

			startDate.setFullYear(startDate.getFullYear());
			startDate.setMonth(startDate.getMonth() + months);
			startDate.setDate(startDate.getDate() + days);
		}

		return dateFormat(startDate, "yyyy-MM-dd");
	}
	return oTableInit;

};

function initDrops() {
	var url = "./../houseProperty/housePropertyList.app?method=initDropAll";
	$.post(url, function(data) {

		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			var code = detailList[0];
			if (code == 'room_type') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#roomTypeSearch");
			}
			if (code == 'room_state') {
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
	var url = "./../houseProperty/housePropertyList.app?method=initAreaPropertyDrop";
	$.post(url, {
		buildId : buildId
	}, function(data) {

		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			$("<option value='" + detailList.communityId + "'>" + detailList.communityName + "</option>").appendTo("#communityIdSearch");
		}
		;
	});
}

/**
 * 初始化楼宇下拉框
 */
function initBuildingPropertyDrop(communityId) {
	var url = "./../houseProperty/housePropertyList.app?method=initBuildingPropertyDrop";
	$.post(url, {
		communityId : communityId
	}, function(data) {
		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			$("<option value='" + detailList.storiedBuildId + "'>" + detailList.storiedBuildName + "</option>").appendTo("#storiedBuildIdSearch");
		}
		;
	});
}
function select(obj) {
	var selectId = $(obj).val();
	clearSearch();
	debugger;
	if (selectId == '0') {
		$("#roomStateSearch").show();
		$("#tempTable2").hide();
		$("#tempTable1").show();
		$('#ownerRoomInfo').bootstrapTable('refresh');
	} else {
		$("#roomStateSearch").hide();
		$("#tempTable2").show();
		$("#tempTable1").hide();
		$('#roomInChargeInfo').bootstrapTable('refresh');
	}
}

function clearSearch() {
	$("#communityIdSearch").val("");
	$("#storiedBuildIdSearch").val("");
	$("#roomTypeSearch").val("");
	$("#roomStateSearch").val("");
	$('#buttonSearch').click();
}

// 初始化字典数据
function initDirectory() {
	var url = "./../expBasicInfo/ownerRoomInfoExp.app?method=initDropAll";
	$.post(url, function(data) {
		dirDirectoryDetail = eval(data);
	});
}

// 解释表格中的code的含义
function explainDirectory(value, code) {
	for (var j = 0; j < dirDirectoryDetail.length; j++) {
		var detailList = dirDirectoryDetail[j];
		var dirCode = detailList[0];
		if (dirCode == code) {
			if (detailList[1] == value) {
				return detailList[2];
			}
		}
	}
}

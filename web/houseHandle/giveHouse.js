selections = [];

$(function() {
	initPermissions();
	initBtnEvent();
	init();
	initSearch();// 初始化搜索框
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
				$('#myForm1').validationEngine('hide');
				clearForm();
				$("#myModalTitle").html("地产交房登记");
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
				urlmethod = "method=deleteGiveHouse";
				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = "./../houseProperty/housePropertyList.app?" + urlmethod;
					$.post(url, {
						roomId : selections + ""
					}, function(data) {
						debugger;
						if (data == "success") {
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
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#housePropertyInfo').bootstrapTable({
			url : './../houseProperty/housePropertyList.app?method=giveHouseList', // 请求后台的URL（*）
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
				field : 'phone',
				title : '手机号码'
			}, {
				field : 'telPhone',
				visible : false,
				title : '固定号码'
			}, {
				field : 'cardId',
				visible : false,
				title : '身份证号'
			}, {
				field : 'birthDate',
				visible : false,
				title : '出生日期',
				formatter : dateFormate
			}, {
				field : 'carId',
				visible : false,
				title : '车牌号'
			}, {
				field : 'ownerTypeName',
				visible : false,
				title : '业主类型'
			}, {
				field : 'ownerIdentityName',
				visible : false,
				title : '业主身份'
			}, {
				field : 'ownerRemark',
				visible : false,
				title : '业主备注'
			}, {
				field : 'makeRoomDate',
				title : '地产收房日期',
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
				title : '房间状态'
			}, {
				field : 'charge_object_name',
				visible : false,
				title : '收费对象'
			}, {
				field : 'remark',
				visible : false,
				title : '房间备注'
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				width : '10%',
				events : operateEvents,
				formatter : operateFormatter
			} ],
			onCheck : function(row, e) {
				tableCheckEvents();
			},
			onUncheck : function(row, e) {
				tableCheckEvents();
			},
			onCheckAll : function(rows) {
				$("#btn_del").attr("disabled", false);
			},
			onUncheckAll : function(rows) {
				$("#btn_del").attr("disabled", true);
			},
			onLoadSuccess : function(rows) {
				$("#btn_del").attr("disabled", true);
			}
		});
	};
	function operateFormatter(value, row, index) {
		return tableBtnInit();
	}
	// 操作列的事件
	window.operateEvents = {
		'click #a_check' : function(e, value, row, index) {
			checkDetail(row);
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

/**
 * 表格选择框
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($('#housePropertyInfo').bootstrapTable('getSelections'), function(row) {
		return row.roomId + "," + row.ownerId;
	});
}

/**
 * 保存操作
 */
function openSaveButton() {
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = getDataForm();
		var roomId = $("#roomId").val();
		if (roomId == "" || roomId == null) {
			layer.msg('房间号未选择！', {
				time : 2000
			});
			return;
		}
		var ownerId = $("#ownerId").val();
		if (ownerId == "" || ownerId == null) {
			layer.msg('业主未选择！', {
				time : 2000
			});
			return;
		}
		$.ajax({
			type : "post",
			url : "./../houseProperty/housePropertyList.app?method=giveHouse",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data == "success") {
					layer.msg('登记成功！', {
						time : 2000
					}, function() {
						$('#housePropertyInfo').bootstrapTable('refresh');
						$('#myModal1').modal('hide');
					});
				} else {
					layer.msg('登记失败,该房间已交房！', {
						time : 2000
					}, function() {
						$('#myModal1').modal('hide');
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
 * 获取表单数据
 * 
 * @returns {___anonymous7092_7851}
 */
function getDataForm() {

	var addJson = {
		roomId : $("#roomId").val(),
		roomNo : $("#roomNo").val(),
		ownerId : $("#ownerId").val(),
		makeRoomDate : $("#makeRoomDate").val()
	};
	return addJson;
}

// 清空表单
function clearForm() {
	$("#owner").val("");
	$("#ownerId").val("");
	$("#roomId").val("");
	$("#roomNo").val("");
	$("#roomNo_d").val("");
	$("#makeRoomDate").val("");
}

function getNowFormatDate(flag, vardate) {
	if (flag == false && (vardate == null || vardate == ''))
		return "-";
	var date;
	if (flag == true) {
		date = new Date();
	} else {
		date = new Date(vardate);
	}
	var seperator1 = "-";
	var seperator2 = ":";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	var hour = date.getHours(); // 获取当前小时数(0-23)
	var min = date.getMinutes(); // 获取当前分钟数(0-59)
	var sec = date.getSeconds(); // 获取当前秒数(0-59)
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	if (hour >= 1 && hour <= 9) {
		hour = "0" + hour;
	}
	if (min >= 0 && min <= 9) {
		min = "0" + min;
	}
	if (sec >= 0 && sec <= 9) {
		sec = "0" + sec;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate + " " + hour + seperator2 + min + seperator2 + sec;
	return currentdate;
}

/* 搜索框初始化 */
function initSearch() {
	// 房间下拉框
	$("#roomNo_d").bsSuggest({
		url : "./../houseProperty/housePropertyList.app?method=houseInfo&keyword=",
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

	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});

	// 业主下拉框
	$("#owner").bsSuggest({
		url : encodeURI("./../ownerInfo/ownerList.app?code=utf-8&method=ownerInfo&keyword="),
		showHeader : true,
		allowNoKeyword : true,
		keyField : 'keyword',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : [ "ownerName", "phone" ],
		effectiveFieldsAlias : {
			ownerName : "业主姓名",
			phone : "电话号码"

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

			// 字符串转化为 json 对象
			return data;
		}
	}).on("onDataRequestSuccess", function(e, result) {
		console.log("onDataRequestSuccess: ", result)
	}).on("onSetSelectValue", function(e, keyword, data) {
		console.log("onSetSelectValue: ", keyword, data);
		$("#ownerId").val(data.ownerId);
		$("#owner").val(data.ownerName);

	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});

}

function checkDetail(obj) {
	$("#build_name").val(obj.build_name);
	$("#community_name").val(obj.community_name);
	$("#storied_build_name").val(obj.storied_build_name);
	$("#houseType").val(obj.houseType);
	$("#roomNo_detail").val(obj.roomNo);
	$("#buildArea").val(obj.buildArea);
	$("#withinArea").val(obj.withinArea);
	$("#room_type_name").val(obj.room_type_name);
	$("#room_state_name").val(obj.room_state_name);
	$("#charge_object_name").val(obj.charge_object_name);
	$("#ownerName").val(obj.ownerName);
	$("#phone").val(obj.phone);
	$("#telPhone").val(obj.telPhone);
	$("#cardId").val(obj.cardId);
	$("#birthDate").val(json2Date(obj.birthDate));
	$("#carId").val(obj.carId);
	$("#ownerTypeName").val(obj.ownerTypeName);
	$("#ownerIdentityName").val(obj.ownerIdentityName);
	$("#remark").val(obj.remark);
	$("#ownerRemark").val(obj.ownerRemark);
	$('#myModal2').modal({
		backdrop : 'static',
		keyboard : false
	});
}

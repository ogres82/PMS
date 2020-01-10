$(function() {
	toolbarBtnInit();
	for (var i = 0; i < btnIdArray.length; i++) {
		if (btnIdArray[i] == "btn_add") {
			$("#btn_add").bind('click', function() {
				add();
			});
		} else if (btnIdArray[i] == "btn_batch") {
			$("#btn_batch").bind('click', function() {
				batchAdd();
			});
		} else if (btnIdArray[i] == "btn_del") {
			$("#btn_del").bind('click', function() {
				btnDel();
			});
		}
	}

	// 初始化收费项下拉框，只有周期性的
	initChargeNoDrops();
	$("#charge_type_name").change(function() {
		var charge = this.value.split(",");
		$("#type_flag").val(charge[2]);
		$("#charge_type_no").val(charge[1]);
		$("#charge_type_id").val(charge[0]);
	});

	$("#batch_charge_type_name").change(function() {
		var charge = this.value.split(",");
		$("#batch_type_flag").val(charge[2]);
		$("#batch_charge_type_no").val(charge[1]);
		$("#batch_charge_type_id").val(charge[0]);
	});

	// 初始化Table
	var oTable = new TableInit();
	oTable.Init();
	// 查询按钮
	$('#button_search').click(function() {
		$('#chargeTypeRela').bootstrapTable('refresh', null);
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

	// 添加自定义校验
	var obj = $.validationEngineLanguage.allRules;
	obj["ajaxValidatePropertyRela"] = {
		"url" : "./../ChargeManager/ChargeType.app?method=ajaxValidatePropertyRela",
		"alertText" : "* 当前房间已关联物业收费项！",
		'alertTextOk' : '* ok',
	// "alertTextLoad": "* 验证中，请稍候..."
	};

	// 添加检验
	$('#myForm').validationEngine();
	$('#myBatchForm').validationEngine();

});

function btnDel() {
	var arr = $('#chargeTypeRela').bootstrapTable('getSelections');
	if (arr.length == 0) {
		layer.alert("请选择一条记录操作", {
			skin : 'layui-layer-molv'
		});
		return;
	}

	layer.confirm("您确定要删除所选信息吗?", {
		skin : 'layui-layer-molv',
		btn : [ '确定', '取消' ]
	}, function() {
		del(arr);
		layer.closeAll('dialog');
	}, function() {
		return;
	})
}

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#chargeTypeRela').bootstrapTable({
			url : './../ChargeManager/ChargeType.app?method=typeRelaList', // 请求后台的URL（*）
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
				checkbox : true
			}, {
				field : 'belong_unit',
				title : '所属小区'
			}, {
				field : 'storied_build_name',
				title : '所属楼宇'
			}, {
				field : 'room_no',
				title : '房间编号'
			}, {
				field : 'owner_name',
				title : '业主'
			}, {
				field : 'charge_type_no',
				title : '项目编号'
			}, {
				field : 'charge_type_name',
				title : '项目名称'
			}, {
				field : 'drop_charge_mode',
				title : '计费方式'
			}, {
				field : 'charge_price',
				title : '单价(元)',
				formatter : numberFormate
			}, {
				field : 'drop_charge_way',
				title : '收费方式'
			}, {
				field : 'drop_charge_type',
				title : '收费类型'
			}, {
				field : 'charge_cycle_count',
				title : '计费周期数'
			}, {
				field : 'drop_charge_cycle_unit',
				title : '周期单位'
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				events : operateEvents,
				formatter : operateFormatter
			} ]
		});
	};

	function operateFormatter(value, row, index) {

		return tableBtnInit();
	}
	;

	// 操作列的事件
	window.operateEvents = {
		'click #a_check' : function(e, value, row, index) {
			edit(row, "check");
		},
		'click #a_edit' : function(e, value, row, index) {
			edit(row, "edit");
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

	numberFormate = function(value, row) {
		return outputmoney("" + value);
	};

	return oTableInit;

};

// 新增
function add() {
	$('#myForm').validationEngine('hide');
	// 清空
	$("#charge_type_no").val(null);
	$("#charge_type_name").val(null);
	$("#charge_type_id").val(null);
	$("#room_no").val(null);
	$("#owner_name").val(null);
	$("#room_id").val(null);
	$("#owner_id").val(null);
	$("#searchInput").val(null);

	$("#myModal").modal();

	// 初始化下拉框：收费项类型
	// initDropdownChargeType();

	// 初始化下拉框：房间业主
	initDropdownHouseType();

}

// 批量新增
function batchAdd() {
	var flag = $('#myBatchForm').validationEngine('hide');
	// 清空
	$("#batch_charge_type_name").val(null);
	$("#batch_charge_type_no").val(null);
	$("#batch_charge_type_id").val(null);
	$("#batch_belong_unit").val(null);
	$("#batch_storied_build_name").val(null);
	$("#batch_belong_unit_id").val(null);
	$("#batch_storied_build_id").val(null);
	$("#batch_belong_unit_input").val(null);

	$("#myBatchModal").modal();

	// 初始化下拉框：收费项类型
	// initDropdownChargeTypeBatch();

	// 初始化下拉框：管理处（小区）
	initDropdownBelongUnit();

	// 初始化下拉框：楼宇
	initDropdownStoriedBuild();

}

/* 编辑按钮，弹出对话框 */
function edit(obj, type) {
	if (type == "check") {
		$("#belong_unit_check").attr("disabled", "true");
		$("#room_no_check").attr("disabled", "true");
		$("#owner_name_check").attr("disabled", "true");
		$("#drop_charge_mode_check").attr("disabled", "true");
		$("#charge_type_no_check").attr("disabled", "true");
		$("#charge_type_name_check").attr("disabled", "true");
		$("#drop_charge_way_check").attr("disabled", "true");
		$("#drop_charge_type_check").attr("disabled", "true");
	} else {

	}

	$("#belong_unit_check").val(obj.belong_unit);
	$("#room_no_check").val(obj.room_no);
	$("#owner_name_check").val(obj.owner_name);
	$("#drop_charge_mode_check").val(obj.drop_charge_mode);
	$("#charge_type_no_check").val(obj.charge_type_no);
	$("#charge_type_name_check").val(obj.charge_type_name);
	$("#drop_charge_way_check").val(obj.drop_charge_way);
	$("#drop_charge_type_check").val(obj.drop_charge_type);

	$("#myCheckModal").modal();
}

// 删除
function del(obj) {
	var selects = JSON.stringify(obj);
	var data = {
		delSelects : selects
	}
	$.ajax({
		type : "post",
		data : data,
		url : './../ChargeManager/ChargeType.app?method=deleteRela',
		async : true,
		success : function(data) {
			$('#chargeTypeRela').bootstrapTable('refresh', null);
			return;
		}
	});
}

// 批量保存 表单中的内容
function batchSave() {
	var flag = $('#myBatchForm').validationEngine('validate');
	if (flag) {
		var datas = {
			batch_type_flag : $("#batch_type_flag").val(),
			batch_charge_type_name : $("#batch_charge_type_name").val(),
			batch_charge_type_no : $("#batch_charge_type_no").val(),
			batch_charge_type_id : $("#batch_charge_type_id").val(),
			batch_belong_unit : $("#batch_belong_unit").val(),
			batch_storied_build_name : $("#batch_storied_build_name").val(),
			batch_belong_unit_id : $("#batch_belong_unit_id").val(),
			batch_storied_build_id : $("#batch_storied_build_id").val()
		};

		$
				.ajax({
					type : "post",
					data : datas,
					url : './../ChargeManager/ChargeType.app?method=saveOrUpdateRelaBatch',
					async : true,
					success : function(data) {
						layer.msg('操作成功！', {
							time : 1000
						}, function() {
							$('#myBatchModal').modal('hide');
							$('#chargeTypeRela')
									.bootstrapTable('refresh', null);
						});
					}
				});
	} else {
		layer.msg('表单验证未通过！', {
			time : 2000
		});
	}

}

// 保存 表单中的内容
function save() {
	var flag = $('#myForm').validationEngine('validate');
	var flagUniq = false;

	if (flag) {

		$
				.ajax({
					type : "post",
					url : './../ChargeManager/ChargeType.app?method=ajaxValidatePropertyRelaSec',
					data : {
						room_id : $("#room_id").val(),
						type_flag : $("#type_flag").val(),
						charge_type_no : $("#charge_type_no").val()
					},
					async : true,
					success : function(data) {
						if (data == "true") {
							flagUniq = true;
						} else {
							flagUniq = false;
						}

						if (flag && flagUniq) {
							var datas = {
								charge_type_no : $("#charge_type_no").val(),
								charge_type_name : $("#charge_type_name").val(),
								charge_type_id : $("#charge_type_id").val(),
								room_no : $("#room_no").val(),
								owner_name : $("#owner_name").val(),
								type_flag : $("#type_flag").val(),
								room_id : $("#room_id").val(),
								owner_id : $("#owner_id").val()
							};

							$
									.ajax({
										type : "post",
										data : datas,
										url : './../ChargeManager/ChargeType.app?method=saveOrUpdateRela',
										async : true,
										success : function(data) {
											layer
													.msg(
															'操作成功！',
															{
																time : 1000
															},
															function() {
																$('#myModal')
																		.modal(
																				'hide');
																$(
																		'#chargeTypeRela')
																		.bootstrapTable(
																				'refresh',
																				null);
															});
										}
									});
						} else {
							layer.msg('当前房间已关联物业收费！', {
								time : 3000
							});
						}

					}
				});
	}

}

// 初始化下拉框：收费项类型
function initDropdownChargeType() {
	// 初始化下拉框：收费项类型
	var addressSuggest = $("#charge_type_name")
			.bsSuggest(
					{
						url : "./../ChargeManager/ChargeType.app?method=dropdownChargeType&type=01&keyword=",
						showHeader : true,
						allowNoKeyword : false,
						keyField : 'charge_type_name',
						getDataMethod : "url",
						delayUntilKeyup : true,
						effectiveFields : [ "charge_type_no",
								"charge_type_name" ],
						effectiveFieldsAlias : {
							charge_type_no : "项目编号",
							charge_type_name : "项目名称"
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
							if (!json || !json.result
									|| json.result.length === 0) {
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
				console.log("onDataRequestSuccess: ", result)
			}).on("onSetSelectValue", function(e, keyword, data) {
				console.log("onSetSelectValue: ", keyword, data);
				$("#charge_type_no").val(data.charge_type_no);
				$("#charge_type_name").val(data.charge_type_name);
				$("#charge_type_id").val(data.charge_type_id);
			}).on("onUnsetSelectValue", function(e) {
				console.log("onUnsetSelectValue")
			});
}

// 初始化下拉框：房间业主信息
function initDropdownHouseType() {
	// 初始化下拉框：房间业主信息
	var addressSuggest = $("#searchInput")
			.bsSuggest(
					{
						url : "./../ChargeManager/ChargeType.app?method=dropdownHourseOwner&keyword=",
						showHeader : true,
						allowNoKeyword : true,
						keyField : 'room_no',
						getDataMethod : "url",
						delayUntilKeyup : true,
						effectiveFields : [ "community_name",
								"storied_build_name", "unit_name", "room_no",
								"owner_name", "phone" ],
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
							return encodeURI(encodeURI(keyword));
						},

						processData : function(json) { // url 获取数据时，对数据的处理，作为
														// getData 的回调函数

							var index, len, data = {
								value : []
							};
							if (!json || !json.result
									|| json.result.length === 0) {
								return false;
							}

							len = json.result.length;

							for (index = 0; index < len; index++) {
								data.value
										.push({
											"community_name" : json.result[index][0],
											"storied_build_name" : json.result[index][1],
											"unit_name" : json.result[index][2],
											"room_no" : json.result[index][3],
											"owner_name" : json.result[index][4],
											"phone" : json.result[index][5],
											"room_state" : json.result[index][6],
											"room_state_name" : json.result[index][7],
											"room_type" : json.result[index][8],
											"charge_date" : json.result[index][9],
											"charge_price" : json.result[index][10],
											"months_price" : json.result[index][11],
											"owner_id" : json.result[index][12],
											"room_id" : json.result[index][13]
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

// 初始化下拉框：管理处（小区）
function initDropdownBelongUnit() {
	// 初始化下拉框：管理处（小区）
	var addressSuggest = $("#batch_belong_unit_input")
			.bsSuggest(
					{
						url : "./../ChargeManager/ChargeType.app?method=dropdownBelongUnit&keyword=",
						showHeader : true,
						allowNoKeyword : true,
						keyField : 'community_name',
						getDataMethod : "url",
						delayUntilKeyup : true,
						effectiveFields : [ "community_name" ],
						effectiveFieldsAlias : {
							community_name : "管理处"
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
							if (!json || !json.result
									|| json.result.length === 0) {
								return false;
							}

							len = json.result.length;

							for (index = 0; index < len; index++) {
								data.value.push({
									"community_id" : json.result[index][0],
									"community_name" : json.result[index][1]
								});
							}
							// 字符串转化为 js 对象

							return data;
						}
					}).on("onDataRequestSuccess", function(e, result) {
				console.log("onDataRequestSuccess: ", result)
			}).on("onSetSelectValue", function(e, keyword, data) {
				console.log("onSetSelectValue: ", keyword, data);
				$("#batch_belong_unit").val(data.community_name);
				$("#batch_belong_unit_id").val(data.community_id);

				$("#batch_storied_build_name").val(null);
				$("#batch_storied_build_id").val(null);
				$("#batch_storied_build_input").val(null);
			}).on("onUnsetSelectValue", function(e) {
				console.log("onUnsetSelectValue")
			});
}

// 初始化下拉框：楼宇
function initDropdownStoriedBuild() {
	// 初始化下拉框：楼宇
	var addressSuggest = $("#batch_storied_build_input")
			.bsSuggest(
					{
						url : "./../ChargeManager/ChargeType.app?method=dropdownStoriedBuild&keyword=",
						showHeader : true,
						allowNoKeyword : true,
						keyField : 'storied_build_name',
						getDataMethod : "url",
						delayUntilKeyup : true,
						effectiveFields : [ "storied_build_name" ],
						effectiveFieldsAlias : {
							storied_build_name : "楼宇"
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
							if (!json || !json.result
									|| json.result.length === 0) {
								return false;
							}

							len = json.result.length;

							for (index = 0; index < len; index++) {
								data.value
										.push({
											"storied_build_id" : json.result[index][0],
											"storied_build_name" : json.result[index][1]
										});
							}
							// 字符串转化为 js 对象

							return data;
						}
					}).on("onDataRequestSuccess", function(e, result) {
				console.log("onDataRequestSuccess: ", result)
			}).on("onSetSelectValue", function(e, keyword, data) {
				console.log("onSetSelectValue: ", keyword, data);
				$("#batch_storied_build_name").val(data.storied_build_name);
				$("#batch_storied_build_id").val(data.storied_build_id);
			}).on("onUnsetSelectValue", function(e) {
				console.log("onUnsetSelectValue")
			});
}

// 初始化下拉框：收费项类型（批量）
function initDropdownChargeTypeBatch() {
	// 初始化下拉框：收费项类型
	var addressSuggest = $("#batch_charge_type_name")
			.bsSuggest(
					{
						url : "./../ChargeManager/ChargeType.app?method=dropdownChargeType&type=01&keyword=",
						showHeader : true,
						allowNoKeyword : false,
						keyField : 'charge_type_name',
						getDataMethod : "url",
						delayUntilKeyup : true,
						effectiveFields : [ "charge_type_no",
								"charge_type_name" ],
						effectiveFieldsAlias : {
							charge_type_no : "项目编号",
							charge_type_name : "项目名称"
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
							if (!json || !json.result
									|| json.result.length === 0) {
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
				console.log("onDataRequestSuccess: ", result)
			}).on("onSetSelectValue", function(e, keyword, data) {
				console.log("onSetSelectValue: ", keyword, data);
				$("#batch_charge_type_no").val(data.charge_type_no);
				$("#batch_charge_type_name").val(data.charge_type_name);
				$("#batch_charge_type_id").val(data.charge_type_id);
			}).on("onUnsetSelectValue", function(e) {
				console.log("onUnsetSelectValue")
			});
}

// 初始化收费项下拉框
function initChargeNoDrops() {
	var url = "./../ChargeManager/ChargeType.app?method=dropdownChargeType&type=01";
	$.post(url, function(data) {
		var list = eval(data);
		// alert(list);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			var chargeIdNo = detailList[0] + "," + detailList[1] + ","
					+ detailList[3];
			// 收费类型
			$(
					"<option value='" + chargeIdNo + "'>" + detailList[2]
							+ "</option>").appendTo("#charge_type_name");
			$(
					"<option value='" + chargeIdNo + "'>" + detailList[2]
							+ "</option>").appendTo("#batch_charge_type_name");

		}
		;
	});

}

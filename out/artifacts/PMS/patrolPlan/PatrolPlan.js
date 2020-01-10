selections = [];
var loginUserName = "";
var loginUserCname = "";

$(function() {
	init();
	initPermissions();
	initBtnEvent();
	initLayDate();
});

function initPermissions() {
	toolbarBtnInit(); // 初始化工具条按钮
}

function initBtnEvent() {

	for (var i = 0; i < btnIdArray.length; i++) {
		// 新增
		if (btnIdArray[i] == "btn_add") {
			$("#btn_add").bind('click', function() {
				$('#myForm1').validationEngine('hide');
				$('#btSave').show();
				clearForm();
				$("#myModalTitle1").html("新增");
				$('#myModal1').modal({
					backdrop : 'static',
					keyboard : false
				});
			});
		}
		// 删除
		if (btnIdArray[i] == "btn_del") {
			$("#btn_del").bind('click', function() {
				$('#myForm1').validationEngine('hide');
				selections = getIdSelections("planInfo");
				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = getRootPath() + 'patrolPlan/patrolPlan.app?method=delete';
					$.post(url, {
						id : selections + ""
					}, function(data) {
						layer.msg(data, {
							time : 2000
						}, function() {
							$('#patrolPlanInfo').bootstrapTable('refresh');
						});
					});
				}, function() {
					return;
				});
			});
		}
	}
}

function initLayDate() {
	var start = {
		elem : '#startDate',
		min : laydate.now(), // 设定最小日期为当前日期
		max : '2099-06-16', // 最大日期
		istime : false,
		istoday : false,
		choose : function(datas) {
			end.min = datas; // 开始日选好后，重置结束日的最小日期
			end.start = datas // 将结束日的初始值设定为开始日

		}
	};
	var end = {
		elem : '#endDate',
		min : $('#startDate').val(),
		max : '2099-06-16',
		istime : false,
		istoday : false,
		choose : function(datas) {
			start.max = datas; // 结束日选好后，重置开始日的最大日期
		}
	};

	laydate.render(start);
	laydate.render(end);
}

function init() {
	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
	new patrolNodeInfoTable().Init();
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#patrolPlanInfo').bootstrapTable({
			url : getRootPath() + 'patrolPlan/patrolPlan.app?method=list', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
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
				field : 'patrolPlanId',
				visible : false,
				title : 'ID'
			}, {
				field : 'planName',
				title : '计划名称'
			}, {
				field : 'planType',
				title : '计划类型',
				formatter : function(value, row, index) {
					if (value == "01") {
						return '固定计划';
					} else {
						return '临时计划';
					}
				}
			}, {
				field : 'planTimes',
				title : '计划频率',
				formatter : function(value, row, index) {
					return `${value}次/天`
				}
			}, {
				field : 'startDate',
				title : '开始时间',
				formatter : json2Date
			}, {
				field : 'endDate',
				title : '结束时间',
				formatter : json2Date
			}, {
				field : 'gmtCreate',
				visible : false,
				title : '创建时间'
			}, {
				field : 'gmtModified',
				visible : false,
				title : '修改时间'
			}, {
				field : 'operName',
				title : '操作人'
			}, {
				field : 'operId',
				visible : false,
				title : '操作人ID'
			}, {
				field : 'patrolNodeNum',
				title : '巡逻点数量'
			}, {
				field : 'remark',
				title : '备注'
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				width : '10%',
				events : operateEvents,
				formatter : operateFormatter
			} ],
			onCheck : function(row, e) {
				tableCheckEvents(1);
			},
			onUncheck : function(row, e) {
				tableCheckEvents(1);
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
		// 表格搜索框的值
		};
		return temp;
	};

	return oTableInit;

};

// 加载计划与巡逻点的关联点
var patrolPlanNodeInfoTable = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#patrolPlanNodeInfo').bootstrapTable({
			url : getRootPath() + 'patrolPlan/patrolPlan.app?method=getPlanOfNode', // 请求后台的URL（*）
			striped : true,
			searchOnEnterKey : true,
			sortable : false,
			sortOrder : "asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : false,
			queryParams : oTableInit.queryParams,
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
				field : 'id',
				visible : false,
				title : 'ID'
			}, {
				field : 'patrolPlanId',
				visible : false,
				title : '计划id'
			}, {
				field : 'patrolPlanName',
				title : '计划名称'
			}, {
				field : 'patrolNodeId',
				visible : false,
				title : '巡逻点id'
			}, {
				field : 'patrolNodeName',
				title : '巡逻点名称'
			}, {
				field : 'gmtCreate',
				title : '创建时间',
				formatter : json2TimeStamp
			}, {
				field : 'gmtModified',
				visible : false,
				title : '修改时间'
			} ],
			onCheck : function(row, e) {
				tableCheckEvents(2);
			},
			onUncheck : function(row, e) {
				tableCheckEvents(2);
			},
			onCheckAll : function(rows) {
				$("#btn_nodeDel").attr("disabled", false);
			},
			onUncheckAll : function(rows) {
				$("#btn_nodeDel").attr("disabled", true);
			},
			onLoadSuccess : function(rows) {
				$("#btn_nodeDel").attr("disabled", true);
			}
		});
	};
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			patrolPlanId : $("#patrolPlanId").val()
		};
		return temp;
	};
	function operateFormatter(value, row, index) {
		return tableBtnInit();
	}
	return oTableInit;
};

// 初始化巡逻点信息
var patrolNodeInfoTable = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#patrolNodeInfo').bootstrapTable({
			url : getRootPath() + 'patrolPlan/patrolNode.app?method=list', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			sortable : false,
			sortOrder : "asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : false,
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			showPaginationSwitch : false,
			pagination : true,
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 5, // 每页的记录行数（*）
			pageList : '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
			columns : [ {
				field : 'state',
				checkbox : true,
			}, {
				field : 'patrolNodeId',
				visible : false,
				title : 'ID'
			}, {
				field : 'patrolNodeName',
				title : '巡逻点名称'
			}, {
				field : 'patrolState',
				title : '状态',
				formatter : function(value, row, index) {
					if (value == "1") {
						return '作废';
					} else {
						return '执行';
					}
				}
			}, {
				field : 'operName',
				title : '操作人'
			}, {
				field : 'operId',
				visible : false,
				title : '操作人ID'
			}, {
				field : 'createTime',
				title : '创建时间',
				formatter : json2TimeStamp
			} ]
		});
	};
	return oTableInit;
};
// 表格选择事件
function tableCheckEvents(type) {
	// 1为：计划信息列表 ；2为：计划和巡逻点的关联列表
	if (type == 1) {
		var r = $('#patrolPlanInfo').bootstrapTable('getSelections');
		if (r.length == 0) {
			$("#btn_del").attr("disabled", true);
		}
		if (r.length == 1) {
			$("#btn_del").attr("disabled", false);
		}
	}
	if (type == 2) {
		var r = $('#patrolPlanNodeInfo').bootstrapTable('getSelections');
		if (r.length == 0) {
			$("#btn_nodeDel").attr("disabled", true);
		}
		if (r.length == 1) {
			$("#btn_nodeDel").attr("disabled", false);
		}
	}
}

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections(tableId) {
	if (tableId == "planInfo") {
		return $.map($('#patrolPlanInfo').bootstrapTable('getSelections'), function(row) {
			return row.patrolPlanId;
		});
	}
	if (tableId == "nodeInfo") {
		return $.map($('#patrolNodeInfo').bootstrapTable('getSelections'), function(row) {
			return row.patrolNodeId;
		});
	}
	if (tableId == "palnNodeInfo") {
		return $.map($('#patrolPlanNodeInfo').bootstrapTable('getSelections'), function(row) {
			return row.id;
		});
	}

}

/**
 * 各类操作
 * 
 */
function buttonClick(elemId) {
	var jsonData = getDataForm(elemId);
	var type = "post";
	var url = "";
	if (elemId == "btSave") {
		var flag = $('#myForm1').validationEngine('validate');
		if (!flag) {
			layer.msg('表单验证未通过！', {
				time : 2000
			});
			return;
		}
		if ($('#patrolPlanId').val() == "") {
			url = getRootPath() + 'patrolPlan/patrolPlan.app?method=insertPlan';			
			var func = function(data) {
				layer.msg('保存成功！', {
					time : 2000
				}, function() {
					$('#patrolPlanInfo').bootstrapTable('refresh');
					$('#patrolPlanId').val(data);
				});
			};
			
		} else {
			url = getRootPath() + 'patrolPlan/patrolPlan.app?method=save';
			var func = function(data) {
				layer.msg('保存成功！', {
					time : 2000
				}, function() {
					$('#patrolPlanInfo').bootstrapTable('refresh');
					$('#myModal1').modal('hide');
				});
			};
		}

	}
	if (elemId == "btn_nodeSave") {
		url = getRootPath() + 'patrolPlan/patrolPlan.app?method=insertNode';
		var func = function(data) {
			layer.msg('保存成功！', {
				time : 2000
			}, function() {
				$('#patrolPlanNodeInfo').bootstrapTable('refresh');
				$('#patrolPlanInfo').bootstrapTable('refresh');
				$('#myModal2').modal('hide');
			});
		};
	}
	if (elemId == "btn_nodeDel") {
		url = getRootPath() + 'patrolPlan/patrolPlan.app?method=delNode';
		var func = function(data) {
			layer.msg('删除成功！', {
				time : 2000
			}, function() {
				$('#patrolPlanNodeInfo').bootstrapTable('refresh');
				$('#patrolPlanInfo').bootstrapTable('refresh');
			});
		};
	}
	$.ajax({
		type : type,
		url : url,
		data : jsonData,
		dataType : "json",
		async : false,
		success : func,
		failure : function(xhr, msg) {
			layer.msg('操作失败！', {
				time : 3000
			}, function() {
				return;
			});
		}
	});	
}

/**
 * 获取表单数据
 * 
 * @returns
 */
function getDataForm(elemId) {
	if (elemId == "btSave") {
		var data = {
			patrolPlanId : $("#patrolPlanId").val(),
			planName : $("#planName").val(),
			planType : $("#planType").val(),
			planTimes : $("#planTimes").val(),
			startDate : $("#startDate").val(),
			endDate : $("#endDate").val(),
			gmtCreate : $("#gmtCreate").val() || getNowFormatDate(true),
			gmtModified : getNowFormatDate(true),
			operId : loginUserName,
			remark : $("#remark").val()
		};
		var addJson = {
			data : JSON.stringify(data)
		};
	}
	if (elemId == "btn_nodeSave") {
		var PlanId = $("#patrolPlanId").val() || "";
		var nodeId = getIdSelections("nodeInfo");
		var data = [];
		nodeId.forEach(function(value, index) {
			var json = {
				'id' : PlanId + '_' + value,
				'patrolPlanId' : PlanId,
				'patrolNodeId' : value,
				'gmtCreate' : getNowFormatDate(true),
				'gmtModified' : getNowFormatDate(true)
			}
			data.push(json);
		});
		var addJson = {
			data : JSON.stringify(data)
		};
	}
	if (elemId == "btn_nodeDel") {
		var addJson = {
			id : getIdSelections("palnNodeInfo") + ""
		};
	}
	return addJson;
}

// 清空表单
function clearForm() {
	$('#myForm1').validationEngine('hide');
	$("#planName").val("");
	$("#patrolPlanId").val("");
	$("#planType").val("01");
	$("#userName").val("");
	$("#planTimes").val("");
	$("#startDate").val("");
	$("#endDate").val("");
	$("#gmtCreate").val("");
	$("#gmtModified").val("");
	$("#userName").val(loginUserCname);
	$("#userId").val(loginUserName);
	$("#remark").val("");
	// 初始化计划与巡逻点的关联Table
	new patrolPlanNodeInfoTable().Init();
	$('#patrolPlanNodeInfo').bootstrapTable('removeAll');
	$('#btn_nodeDel').attr("disabled", true);
	$('#btn_nodeSave').attr("disabled", false);
	$("#planName").attr("disabled", false);
	$("#planType").attr("disabled", false);
	$("#planTimes").attr("disabled", false);
	$("#userName").attr("disabled", false);
	$("#startDate").attr("disabled", false);
	$("#endDate").attr("disabled", false);
	$("#remark").attr("disabled", false);

}

// 验证巡逻点是否重复
function checkPlanName(planName) {
	var allData = $('#patrolPlanInfo').bootstrapTable('getData');
	var len = allData.length;
	for (var i = 0; i < len; i++) {
		if (planName == allData[i].planName) {
			layer.msg('计划名称已存在，请重新输入！', {
				time : 1000
			}, function() {
				$("#planName").val("");
				return;
			});
		}
	}
}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	$("#planName").val(obj.planName);
	$("#patrolPlanId").val(obj.patrolPlanId);
	$("#planType").val(obj.planType);
	$("#userName").val(obj.operName);
	$("#userId").val(obj.operId);
	$("#planTimes").val(obj.planTimes);
	$("#startDate").val(json2Date(obj.startDate));
	$("#endDate").val(json2Date(obj.endDate));
	$("#gmtCreate").val(obj.gmtCreate);
	$("#gmtModified").val(obj.gmtModified);
	$("#remark").val(obj.remark);
	// 初始化计划与巡逻点的关联Table
	new patrolPlanNodeInfoTable().Init();
	if (type == 1) {
		$("#myModalTitle1").html("查看");
		$('#patrolPlanNodeInfo').bootstrapTable('refresh');
		$('#btn_openNode').attr("disabled", true);
		$('#btn_nodeDel').attr("disabled", true);
		$("#planName").attr("disabled", true);
		$("#planType").attr("disabled", true);
		$("#planTimes").attr("disabled", true);
		$("#userName").attr("disabled", true);
		$("#startDate").attr("disabled", true);
		$("#endDate").attr("disabled", true);
		$("#remark").attr("disabled", true);
		$('#patrolPlanNodeInfo').bootstrapTable('hideColumn', 'state');

		$('#btSave').hide();
	} else {
		$("#myModalTitle1").html("编辑");
		$('#patrolPlanNodeInfo').bootstrapTable('refresh');
		$('#btn_openNode').attr("disabled", false);
		// $('#btn_nodeDel').attr("disabled", false);
		$("#planName").attr("disabled", false);
		$("#planType").attr("disabled", false);
		$("#planTimes").attr("disabled", false);
		$("#userName").attr("disabled", false);
		$("#startDate").attr("disabled", false);
		$("#endDate").attr("disabled", false);
		$("#remark").attr("disabled", false);
		$('#patrolPlanNodeInfo').bootstrapTable('showColumn', 'state');
		$('#btSave').show();
	}
	$('#myModal1').modal();
}


function openNodeInfo() {
	if ($('#patrolPlanId').val() == "") {
		layer.msg('请先保存计划信息后才能新增相关巡逻点！', {
			time : 2000
		});
		return;
	}
	var ids = $.map($('#patrolPlanNodeInfo').bootstrapTable('getData'), function(row) {
		return row.patrolNodeId;
	});
	if (ids.length > 0) {
		$('#patrolNodeInfo').bootstrapTable('remove', {
			field : 'patrolNodeId',
			values : ids
		});
	}
	$("#myModalTitle2").html("巡逻点信息");
	$('#myModal2').modal();

}
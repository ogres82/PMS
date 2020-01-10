selections = [];
var loginUserName = "";
var loginUserCname = "";

$(function() {
	init();
	initPermissions();
	initBtnEvent();
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
				$("#myModalTitle").html("新增");
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
				selections = getIdSelections();

				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = getRootPath() + 'patrolPlan/patrolNode.app?method=delete';
					$.post(url, {
						id : selections + ""
					}, function(data) {
						layer.msg(eval(data), {
							time : 2000
						}, function() {
							$('#patrolNodeInfo').bootstrapTable('refresh');
						});
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
		$('#patrolNodeInfo').bootstrapTable({
			url : getRootPath() + 'patrolPlan/patrolNode.app?method=list', // 请求后台的URL（*）
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
				formatter : dateFormate
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
		'click #a_print' : function(e, value, row, index) {
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
	dateFormate = function(value) {
		if (value == null || value == "") {
			return "";
		} else {
			return json2TimeStamp(value);
		}

	};
	return oTableInit;

};
// 表格选择事件
function tableCheckEvents() {
	var r = $('#patrolNodeInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
}

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {

	return $.map($('#patrolNodeInfo').bootstrapTable('getSelections'), function(row) {
		return row.patrolNodeId;
	});
}

/**
 * 保存操作
 */
function openSaveButton() {
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = getDataForm();
		$.ajax({
			type : "post",
			url : getRootPath() + 'patrolPlan/patrolNode.app?method=save',
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#patrolNodeInfo').bootstrapTable('refresh');
					$('#myModal1').modal('hide');
				});
			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！', {
					time : 3000
				}, function() {

				});
			}
		});
	} else {
		layer.msg('表单验证未通过！', {
			time : 2000
		});
	}

}

/**
 * 获取表单数据
 * 
 * @returns
 */
function getDataForm() {

	var addJson = {
		patrolNodeId : $("#patrolNodeId").val(),
		patrolNodeName : $("#patrolNodeName").val(),
		operId : loginUserName,
		patrolState : $("#patrolState").val()
	};
	return addJson;
}

// 清空表单
function clearForm() {
	$("#patrolNodeId").val("");
	$("#patrolNodeName").val("");
	$("#userName").val(loginUserCname);
	$("#userId").val(loginUserName);
}

// 验证巡逻点是否重复
function checkNodeName(patrolNodeName) {
	var allData = $('#patrolNodeInfo').bootstrapTable('getData');
	var len = allData.length;
	for (var i = 0; i < len; i++) {
		if (patrolNodeName == allData[i].patrolNodeName) {
			layer.msg('巡逻点已存在，请重新输入！', {
				time : 1000
			}, function() {
				$("#patrolNodeName").val("");
				return;
			});
		}
	}
}

function printQr(patrolNodeName) {
	$("canvas").attr("id", "erw");
	var canvas = document.getElementById('erw');
	var image = new Image();
	var strDataURI = canvas.toDataURL("image/png");
	document.getElementById('myImg').src = strDataURI;
	$('#codeTitle').html("巡逻点：" + patrolNodeName);

	if (!!window.ActiveXObject || "ActiveXObject" in window) {
		$("#print").jqprint();
	} else {
		$("#print").jqprint();
	}

}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	if (type == 1) {
		var qrContent = {
			patrolNodeId : obj.patrolNodeId,
			patrolNodeName : obj.patrolNodeName
		};
		createQrcode("qrcode", JSON.stringify(qrContent), 220, 220);
		printQr(obj.patrolNodeName);

	} else {
		$('#patrolNodeName').attr("disabled", false);
		$('#patrolState').attr("disabled", false);
		$('#btSave').show();
		$("#myModalTitle").html("编辑");
		$("#patrolNodeId").val(obj.patrolNodeId);
		$("#patrolNodeName").val(obj.patrolNodeName);
		$("#userName").val(obj.operName);
		$("#userId").val(obj.operId);
		$("#patrolState").val(obj.patrolState);
		$('#myModal1').modal();
	}

}

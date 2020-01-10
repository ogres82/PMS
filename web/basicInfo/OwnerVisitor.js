selections = [];

$(function() {
	init();
	initPermissions();
	initBtnEvent();
});

function init() {

	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
};

function initPermissions() {
	toolbarBtnInit(); // 初始化工具条按钮
}

function initBtnEvent() {
	var urlmethod = "";
	for (var i = 0; i < btnIdArray.length; i++) {
		// 删除
		if (btnIdArray[i] == "btn_del") {
			$("#btn_del").bind('click', function() {
				$('#myForm1').validationEngine('hide');
				selections = getIdSelections();

				urlmethod = "method=deletePropertyOwner";

				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = "./../ownerInfo/ownerList.app?" + urlmethod;
					$.post(url, {
						ownerId : selections + ""
					}, function(data) {
						layer.msg(eval(data), {
							time : 2000
						}, function() {
							$('#ownerVisitor').bootstrapTable('refresh');
						});
					});
				}, function() {
					return;
				});
			});
		}
	}
}

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#ownerVisitor').bootstrapTable({
			url : './../ownerInfo/ownerList.app?method=ownerVisitorlist', // 请求后台的URL（*）
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
				field : 'ownerId',
				visible : false,
				title : 'ID'
			}, {
				field : 'ownerName',
				title : '游客姓名'
			}, {
				field : 'phone',
				title : '手机号码'
			}, {
				field : 'telPhone',
				visible : false,
				title : '联系电话'
			}, {
				field : 'cardId',
				visible : false,
				title : '身份证号'
			}, {
				field : 'birthDate',
				visible : false,
				title : '生日',
				formatter : dateFormate
			}, {
				field : 'carId',
				visible : false,
				title : '车牌号'
			}, {
				field : 'owner_type_name',
				visible : false,
				title : '业主类型'
			}, {
				field : 'owner_identity_name',
				title : '身份'
			}, {
				field : 'remark',
				title : '备注'
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

//表格选择事件
function tableCheckEvents() {
	var r = $('#ownerVisitor').bootstrapTable('getSelections');
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
	return $.map($('#ownerVisitor').bootstrapTable('getSelections'), function(row) {
		return row.ownerId;
	});
}


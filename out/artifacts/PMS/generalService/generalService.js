selections = [];
var loginUserName = "";
var loginUserCname = "";
var houseZtree = "";

$(function () {
	init();
	initPermissions();
	initBtnEvent();
	houseZtree = initZtree();
	fuzzySearch('houseZTree','#roomInfo',null,true);
});

function initPermissions() {
	toolbarBtnInit(); // 初始化工具条按钮
}

function initBtnEvent() {

	for (var i = 0; i < btnIdArray.length; i++) {
		// 新增
		if (btnIdArray[i] == "btn_add") {
			$("#btn_add").bind('click', function () {
				$('#myForm1').validationEngine('hide');
				$('#btHotLineAdd').show();
				clearForm();
				$("#myModalTitle").html("新增");
				$('#myModal1').modal({
					backdrop: 'static',
					keyboard: false
				});

			});
		}
		// 删除
		if (btnIdArray[i] == "btn_del") {
			$("#btn_del").bind('click', function () {
				$('#myForm1').validationEngine('hide');
				selections = getIdSelections();

				layer.confirm("您确定要删除所选信息吗?", {
					skin: 'layui-layer-molv',
					btn: ['确定', '取消']
				}, function () {
					var url = getRootPath() + 'hotLine.app?method=delete';
					$.post(url, {
						id: selections + ""
					}, function (data) {
						layer.msg(eval(data), {
							time: 2000
						}, function () {
							$('#hotLineInfo').bootstrapTable('refresh');
						});
					});
				}, function () {
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

function zTreeOnClick(event, treeId, treeNode) {
    alert(treeNode.id + ", " + treeNode.name);
};


var TableInit = function () {
	var oTableInit = new Object();
	oTableInit.Init = function () {
		$('#hotLineInfo').bootstrapTable({
			url: getRootPath() + 'hotLine.app?method=list', // 请求后台的URL（*）
			toolbar: '#toolbar', // 工具按钮用哪个容器
			striped: true,
			search: true,
			searchOnEnterKey: true,
			showColumns: true, // 是否显示所有的列
			showRefresh: true, // 是否显示刷新按钮
			showToggle: true, // 是否显示详细视图和列表视图的切换按钮
			sortable: false,
			sortOrder: "asc",
			sidePagination: "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache: false,
			clickToSelect: false,
			queryParams: oTableInit.queryParams,
			showExport: "true",
			minimumCountColumns: 2, // 最少允许的列数
			buttonsAlign: "left",
			showPaginationSwitch: false,
			pagination: true,
			pageNumber: 1, // 初始化加载第一页，默认第一页
			pageSize: 10, // 每页的记录行数（*）
			pageList: '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
			columns: [{
				field: 'state',
				checkbox: true,
			}, {
				field: 'id',
				visible: false,
				title: 'ID'
			}, {
				field: 'imgUrl',
				title: '热线图标',
				formatter: function (value, row, index) {
					return '<img  src="' + value + '" width="58" height="80"  >';
				}
			}, {
				field: 'name',
				title: '热线名称'
			}, {
				field: 'telephone',
				title: '热线电话'
			}, {
				field: 'lineType',
				title: '热线类型',
				formatter: function (value, row, index) {
					if (value == 1) {
						return '生活服务';
					} else {
						return '乐湾国际';
					}
				}
			}, {
				field: 'userName',
				title: '操作人'
			}, {
				field: 'userID',
				visible: false,
				title: '操作人ID'
			}, {
				field: 'updateDate',
				title: '更新时间',
				formatter: dateFormate
			}, {
				field: 'operate',
				title: '操作',
				align: 'center',
				width: '10%',
				events: operateEvents,
				formatter: operateFormatter
			}],
			onCheck: function (row, e) {
				tableCheckEvents();
			},
			onUncheck: function (row, e) {
				tableCheckEvents();
			},
			onCheckAll: function (rows) {
				$("#btn_del").attr("disabled", false);
			},
			onUncheckAll: function (rows) {
				$("#btn_del").attr("disabled", true);
			},
			onLoadSuccess: function (rows) {
				$("#btn_del").attr("disabled", true);
			}
		});
	};
	function operateFormatter(value, row, index) {
		return tableBtnInit();
	}
	// 操作列的事件
	window.operateEvents = {
		'click #a_check': function (e, value, row, index) {
			editOrCheck(row, 1);
		},
		'click #a_edit': function (e, value, row, index) {
			editOrCheck(row, 2);
		},

	};
	oTableInit.queryParams = function (params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit: params.limit, // 页面大小
			offset: params.offset, // 页码
			search: params.search
			// 表格搜索框的值
		};
		return temp;
	};
	dateFormate = function (value) {
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
	var r = $('#hotLineInfo').bootstrapTable('getSelections');
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

	return $.map($('#hotLineInfo').bootstrapTable('getSelections'), function (row) {
		return row.id;
	});
}

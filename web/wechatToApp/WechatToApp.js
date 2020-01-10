selections = [];

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
		// 同步数据
		if (btnIdArray[i] == "btn_syn") {
			$("#btn_syn").bind('click', function() {
				initBlockUI();
				var url = '../wechatToApp.app?method=syn';
				$.post(url, function(data) {
					$.unblockUI();
					layer.msg(eval(data), {
						time : 2000
					}, function() {
						$('#materialInfo').bootstrapTable('refresh');
					});
				});
			});
		}
		// 删除
		if (btnIdArray[i] == "btn_del") {
			$("#btn_del").bind('click', function() {
				var array = new Array();
				getIdSelections(array);

				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = '../wechatToApp.app?method=del';
					$.post(url,{"data" : JSON.stringify(array)}, function(data) {
						layer.msg(eval(data), {
							time : 2000
						}, function() {
							$('#materialInfo').bootstrapTable('refresh');
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
		$('#materialInfo').bootstrapTable({
			url : '../wechatToApp.app?method=list', // 请求后台的URL（*）
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
				field : 'thumbMediaId',
				visible : false,
				title : 'Id'
			}, {
				field : 'mediaId',
				visible : false,
				title : 'mediaId'
			}, {
				field : 'thumbUrl',
				visible : false,
				title : '素材图片',
				formatter : function(value, row, index) {
					return '<img  src="' + value + '" width="120" height="80"  >';
				}
			}, {
				field : 'title',
				width : '25%',
				title : '素材标题'
			}, {
				field : 'digest',
				width : '35%',
				title : '素材简介'
			}, {
				field : 'createTime',
				title : '创建时间',
				width : '10%',
				formatter : dateFormate
			}, {
				field : 'updateTime',
				title : '更新时间',
				width : '10%',
				formatter : dateFormate
			}, {
				field : 'url',
				visible : false,
				title : '素材地址'
			}, {
				field : 'publish',
				title : '发布状态',
				width : '10%',
				formatter : function(value, row, index) {
					if (value == "1") {
						return '已发布';
					} else {
						return '未发布';
					}
				}
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
		'click #a_pub' : function(e, value, row, index) {
			publist(row);
		},
		'click #a_view' : function(e, value, row, index) {
			preview(row);
		}
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
			return json2TimeStamp(value * 1000);
		}

	};
	return oTableInit;

};
// 表格选择事件
function tableCheckEvents() {
	var r = $('#materialInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
}

function preview(row) {
	var frameHeight = (window.screen.height - 736) / 2 + 'px';
	var frameWidth = (window.screen.width - 414) / 2 + 'px';
	window.open(row.url, '', 'height=736,width=414,top=' + frameHeight + ',left=' + frameWidth + ',menubar=no,scrollbars=yes,resizable=no,location=no, status=no');

}

function publist(row) {
	if (row.publish == "1") {
		layer.msg("该条素材已经发布！", {
			time : 2000
		})
		return;
	}
	var url = '../wechatToApp.app?method=publish';
	$.post(url, {
		thumbMediaId : row.thumbMediaId,
		mediaId : row.mediaId
	}, function(data) {
		layer.msg(eval(data), {
			time : 2000
		}, function() {
			$('#materialInfo').bootstrapTable('refresh');
		});
	});
}

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections(array) {
	selections = $('#materialInfo').bootstrapTable('getSelections');
	for (var i = 0; i < selections.length; i++) {
		var jsonObj = {};
		jsonObj.thumbMediaId = selections[i].thumbMediaId;
		jsonObj.mediaId = selections[i].mediaId;
		array.push(jsonObj);
	}
}

selections = [];

$(function() {
	init();
	// initPermissions();
	initPlanDrop();
	initNodeDrop();
	initUserDrop();
	initLayDate();
	$('#buttonSearch').click(function() {
		$('#patrolNodeRecInfo').bootstrapTable('refresh');
	});
});

function initPermissions() {
	toolbarBtnInit(); // 初始化工具条按钮
}

function init() {
	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#patrolNodeRecInfo').bootstrapTable({
			url : getRootPath() + 'patrolPlan/patrolNodeRec.app?method=list', // 请求后台的URL（*）
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
			pageList : '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）patrolNodeId
			columns : [ {
				field : 'state',
				checkbox : true,
			}, {
				field : 'patrolRecId',
				visible : false,
				title : 'patrolRecId'
			}, {
				field : 'patrolPlanId',
				visible : false,
				title : 'patrolPlanId'
			}, {
				field : 'patrolNodeId',
				visible : false,
				title : 'patrolNodeId'
			}, {
				field : 'planName',
				title : '巡逻计划名称'
			}, {
				field : 'patrolNodeName',
				title : '巡逻点名称'
			}, {
				field : 'patrolUserName',
				title : '巡逻人'
			}, {
				field : 'patrolUserId',
				visible : false,
				title : '巡逻人ID'
			}, {
				field : 'patrolContent',
				title : '巡逻内容'
			}, {
				field : 'insertTime',
				title : '巡逻时间',
				formatter : json2TimeStamp
			} ]
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
			search : encodeURI(params.search), // 表格搜索框的值
			planIdSearch : $("#planIdSearch").val(),
			nodeIdSearch : $("#nodeIdSearch").val(),
			userIdSearch : $("#userIdSearch").val(),
			insertDateSearch : $("#insertDateSearch").val()
		};
		return temp;
	};

	return oTableInit;

};

function initLayDate() {
	var insertDate = {
		elem : '#insertDateSearch',
		max : '2099-06-16', // 最大日期
		istime : false,
		istoday : false,
		choose : function(datas) {
			end.start = datas; // 将结束日的初始值设定为开始日
		}
	};
	laydate.render(insertDate);
}

function clearSearch() {
	$("#planIdSearch").val("");
	$("#nodeIdSearch").val("");
	$("#userIdSearch").val("");
	$("#insertDateSearch").val("");
	$('#buttonSearch').click();
}

/**
 * 初始化计划下拉框
 */
function initPlanDrop() {
	var url = getRootPath() + 'patrolPlan/patrolPlan.app?method=list';
	$.post(url, function(data) {
		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			$("<option value='" + detailList.patrolPlanId + "'>" + detailList.planName + "</option>").appendTo("#planIdSearch");
		}
		;
	});
}
/**
 * 初始化巡逻点下拉框
 */
function initNodeDrop() {
	var url = getRootPath() + 'patrolPlan/patrolNode.app?method=list';
	$.post(url, function(data) {
		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			$("<option value='" + detailList.patrolNodeId + "'>" + detailList.patrolNodeName + "</option>").appendTo("#nodeIdSearch");
		}
		;
	});
}

/**
 * 初始化秩序维护部员工下拉框
 */
function initUserDrop() {
	var url = getRootPath() + "user/userMaintain.app?method=list";
	$.post(url, function(data) {
		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			$("<option value='" + detailList.username + "'>" + detailList.cname + "</option>").appendTo("#userIdSearch");
		}
		;
	});
}



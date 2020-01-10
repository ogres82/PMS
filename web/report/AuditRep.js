$(function() {


	// 初始化Table
	var oTable = new TableInit();
	oTable.Init();

	// 查询按钮
	$('#buttonSearch').click(function() {
		$('#AuditRep').bootstrapTable('refresh', null);
	});

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

});

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#AuditRep').bootstrapTable({
			url : getRootPath()+'report/chargeReport.app?method=AuditRep', // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			showFooter : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : true,
			queryParams : oTableInit.queryParams,
			showExport : "true",
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			showPaginationSwitch : false,
			pagination : true,
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : '[10, 25, 50, 100, ALL]', // 可供选择的每页的行数（*）
			columns : [{
				field : 'community_name',
				title : '小区名称'
			}, {
				field : 'CHARGE_MONTHS',
				title : '本年应收月数'
			}, {
				field : 'CUR_YEAR_AMOUNT_TAX',
				title : '本年应确认收入（含税）'
			}, {
				field : 'CUR_GIVE_MONTH',
				title : '赠免物业费月数'
			}, {
				field : 'CUR_GIVE_AMOUNT',
				title : '赠免物业费金额'
			}, {
				field : 'ROOM_OPEN_AMOUNT',
				title : '赠免物业费由房开补贴金额'
			}, {
				field : 'CUR_YEAR_AMOUNT',
				title : '本年实际应收物业费'
			}, {
				field : 'CUR_YEAR_PRO_MONTH',
				title : '本年实际交费月数'
			}, {
				field : 'CUR_YEAR_PRO_AMOUNT',
				title : '本年实际收费'
			}, {
				field : 'PAID_YEAR',
				title : '交费时间'
			}, {
				field : 'TMP_AMOUNT',
				title : '此列勿删'
			}, {
				field : 'END_YEAR_AMOUNT',
				title : '年末应收账款余额'
			}, {
				field : 'END_YEAR_PRO_AMOUNT',
				title : '年末预收账款余额'
			}, {
				field : 'MATCHED_AMOUNT',
				title : '配比当期应收已收金额'
			}]
		});
	};
	
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : params.search,
			beginYear : $("#beginTime").val()||'2018',
			endYear : $("#endTime").val()||'2018'
		};
		return temp;
	};	

	return oTableInit;
};
//initBlockUI();$.unblockUI();

function clearSearch() {
	$("#charge_type_select").val("");
	$("#queryTime").val("");
}
selections = [];

$(function() {
	initPermissions();
	init();
	// 定位表格查询框
	$(".search").css({
		'position' : 'fixed',
		'right' : '10px',
		'top' : '0',
		'z-index' : '1000',
		'width' : '240px'
	});
	$(".search input").css({
		'padding-right' : '23px'
	});
	$('#myForm1').validationEngine();
	initBtnEvent();
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
				$('#btAllAreaAdd').show();

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

				urlmethod = "method=deleteAllArea";

				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = "./../allAreaInfo/allAreaList.app?" + urlmethod;
					$.post(url, {
						buildId : selections + ""
					}, function(data) {
						layer.msg(eval(data), {
							time : 2000
						}, function() {
							$('#allAreaInfo').bootstrapTable('refresh');
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
		$('#allAreaInfo').bootstrapTable({
			url : './../allAreaInfo/allAreaList.app?method=list', // 请求后台的URL（*）
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
				field : 'buildId',
				visible : false,
				title : 'ID'
			}, {
				field : 'lzId',
				visible : false
			}, {
				field : 'buildName',
				title : '楼盘'
			}, {
				field : 'remark',
				title : '备注'
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
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
	var r = $('#allAreaInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
}

// 保存 表单中的内容
function save() {
	$("#myForm").submit();
}

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($('#allAreaInfo').bootstrapTable('getSelections'), function(row) {
		return row.buildId;
	});
}

/**
 * 保存操作
 */
function openSaveButton() {
	$('#myForm1').validationEngine('hide');
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = getDataForm();
		$.ajax({
			type : "post",
			url : "./../allAreaInfo/allAreaList.app?method=save",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#allAreaInfo').bootstrapTable('refresh');
					$('#myModal1').modal('hide');
				});

			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！', {
					time : 2000
				}, function() {

				});
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
		buildId : $("#buildId").val(),
		lzID : $("#lzId").val(),
		buildName : $("#buildName").val(),
		remark : $("#remark").val()
	};
	return addJson;
}

// 清空表单
function clearForm() {
	$("#buildId").val("");
	$("#lzId").val("");
	$("#buildName").val("");
	$("#remark").val("");
}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	if (type == 1) {
		$('#btAllAreaAdd').hide();
		$("#myModalTitle").html("查看");
	} else {
		$('#btAllAreaAdd').show();
		$("#myModalTitle").html("编辑");
	}
	$("#buildId").val(obj.buildId);
	$("#lzId").val(obj.lzId);
	$("#buildName").val(obj.buildName);
	$("#remark").val(obj.remark);

	$('#myModal1').modal();
}

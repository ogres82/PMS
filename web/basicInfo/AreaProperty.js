selections = [];

$(function() {
	initPermissions();
	init();
	initBuildDrop();
	// 定位表格查询框
	/*
	 * $(".search").css({ 'position':'fixed', 'right':'10px', 'top':'0',
	 * 'z-index':'1000', 'width':'240px' }); $(".search input").css({
	 * 'padding-right':'23px' });
	 */
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
				$('#btAreaPropertyAdd').show();

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
			$("#btn_del").bind(
					'click',
					function() {
						$('#myForm1').validationEngine('hide');
						selections = getIdSelections();

						urlmethod = "method=deleteAreaProperty";

						layer.confirm("您确定要删除所选信息吗?", {
							skin : 'layui-layer-molv',
							btn : [ '确定', '取消' ]
						}, function() {
							var url = getRootPath()
									+ "areaPropertyInfo/areaPropertyList.app?"
									+ urlmethod;
							$.post(url, {
								communityId : selections + ""
							}, function(data) {
								layer.msg(eval(data), {
									time : 2000
								}, function() {
									$('#areaPropertyInfo').bootstrapTable(
											'refresh');
								});
							});
						}, function() {
							return;
						})
					});
		}
	}
}

function init() {

	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
	$(".summernote").summernote({
		lang : "zh-CN"
	});
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#areaPropertyInfo')
				.bootstrapTable(
						{
							url : getRootPath()
									+ 'areaPropertyInfo/areaPropertyList.app?method=list', // 请求后台的URL（*）
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
								field : 'communityId',
								visible : false,
								title : 'ID'
							}, {
								field : 'belongBuildId',
								visible : false,
								title : 'buildID'
							},{
								field : 'lzBuildId',
								visible : false,
								title : 'lzBuildId'
							},{
								field : 'lzId',
								visible : false,
								title : 'lzId'
							}, {
								field : 'communityName',
								title : '小区'
							}, {
								field : 'buildName',
								title : '所属楼盘'
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
	var r = $('#areaPropertyInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
}

/**
 * 初始化部门下拉框
 */
function initBuildDrop() {
	var url = getRootPath()
			+ "areaPropertyInfo/areaPropertyList.app?method=initBuildDrop";
	$.post(url, function(data) {

		var list = eval(data);

		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			// 员工部门
			$(
					"<option value='" + detailList.buildId + "'>"
							+ detailList.buildName + "</option>").appendTo(
					"#belongBuildId");

		}
		;
	});
}

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($('#areaPropertyInfo').bootstrapTable('getSelections'),
			function(row) {
				return row.communityId;
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
			url : getRootPath()
					+ "areaPropertyInfo/areaPropertyList.app?method=save",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#areaPropertyInfo').bootstrapTable('refresh');
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
		communityId : $("#communityId").val(),
		lzId : $("#lzId").val(),
		communityName : $("#communityName").val(),
		belongBuildId : $("#belongBuildId").val(),
		lzBuildId : $("#lzBuildId").val(),
		remark : $("#remark").val()
	};
	return addJson;
}

// 清空表单
function clearForm() {
	$("#communityId").val("");
	$("#communityName").val("");
	$("#belongBuildId").val("");
	$("#lzBuildId").val("");
	$("#lzId").val("");
	$("#remark").val("");	
	
}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	if (type == 1) {
		$('#btAreaPropertyAdd').hide();
		$("#myModalTitle").html("查看");
	} else {
		$('#btAreaPropertyAdd').show();
		$("#myModalTitle").html("编辑");
	}
	$("#communityId").val(obj.communityId);
	$("#communityName").val(obj.communityName);
	$("#belongBuildId").val(obj.belongBuildId);
	$("#lzBuildId").val(obj.lzBuildId);
	$("#lzId").val(obj.lzId);
	$("#remark").val(obj.remark);
	$('#myModal1').modal();
}

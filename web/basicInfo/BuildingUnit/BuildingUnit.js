selections = [];
var options = {
	buildCtrlId : "belongBuild",
	communityCtrlId : "belongComm",
	belongCtrlId : "storiedBuild",
	unitCtrlId :"unittest"
};
var roomFilter;

$(function() {
	init();
	initPermissions();
	initBtnEvent();
	roomFilter = new RoomFilter(options);
	$('#myForm1').validationEngine();
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
				$('#btBuildingUnitAdd').show();
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
						urlmethod = "method=del";
						layer.confirm("您确定要删除所选信息吗?", {
							skin : 'layui-layer-molv',
							btn : [ '确定', '取消' ]
						}, function() {
							var url = getRootPath() + "buildingUnitInfo.app?"
									+ urlmethod;
							$.post(url, {
								unitId : selections + ""
							}, function(data) {
								layer.msg(eval(data), {
									time : 2000
								}, function() {
									$('#buildingUnitInfo').bootstrapTable(
											'refresh');
									$('#myModal1').modal('hide');
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
		$('#buildingUnitInfo').bootstrapTable({
			url : getRootPath() + 'buildingUnitInfo.app?method=list', // 请求后台的URL（*）
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
				field : 'buildid',
				visible : false,
				title : 'buildid'
			}, {
				field : 'buildLzId',
				visible : false,
				title : 'buildLzId'
			}, {
				field : 'communityId',
				visible : false,
				title : 'communityId'
			}, {
				field : 'communityIdLzId',
				visible : false,
				title : 'communityIdLzId'
			}, {
				field : 'storiedBuildId',
				visible : false,
				title : 'storiedBuildId'
			}, {
				field : 'storiedBuildLzId',
				visible : false,
				title : 'storiedBuildLzId'
			}, {
				field : 'unitId',
				visible : false,
				title : 'unitId'
			}, {
				field : 'unitLzId',
				visible : false,
				title : 'unitLzId'
			}, {
				field : 'buildname',
				title : '楼盘'
			}, {
				field : 'communityName',
				title : '小区'
			}, {
				field : 'storiedBuildName',
				title : '楼宇'
			}, {
				field : 'unitName',
				title : '单元'
			}, {
				field : 'isSycn',
				formatter : sycnFormate,
				title : '同步联掌门户'
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
		}
	};
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : params.search
		};
		return temp;
	};

	sycnFormate = function(value) {
		if (value == "0") {
			return "已同步";
		} else {
			return "未同步";
		}
	};
	return oTableInit;

};

// 表格选择事件
function tableCheckEvents() {
	var r = $('#buildingUnitInfo').bootstrapTable('getSelections');
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
	return $.map($('#buildingUnitInfo').bootstrapTable('getSelections'),
			function(row) {
				return row.unitId;
			});
}

/**
 * 保存操作
 */
function openSaveButton() {
	var flag = $('#myForm1').validationEngine('validate');
	var addJson = getDataForm();
    console.log(addJson);
	if (flag) {
		$.ajax({
			type : "post",
			url : getRootPath() + "buildingUnitInfo.app?method=save",
			data : {
				buildUnitInfo : JSON.stringify(addJson)
			},
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#buildingUnitInfo').bootstrapTable('refresh');
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
	var builUnit = roomFilter.val();
	var addJson = {
		unitId : $("#unitId").val(),
		unitName : $("#unitName").val(),
		lzId : $("#unitLzId").val(),
		belongSbId : builUnit.belongCtrlId
	};
	return addJson;
}

// 清空表单
function clearForm() {
	$("#belongBuild").val("");
	$("#belongComm").val("");
	$("#storiedBuild").val("");
	$("#unitName").val("");
	$("#isScny").val("");
	$("#buildid").val("");
	$("#buildLzId").val("");
	$("#communityId").val("");
	$("#communityIdLzId").val("");
	$("#storiedBuildId").val("");
	$("#storiedBuildLzId").val("");
	$("#unitId").val("");
	$("#unitLzId").val("");
	roomFilter.init();
}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	options = {
		buildDef : obj.buildid,
		buildDisabled : true,
		communityDef : obj.communityId,
		communityDisabled : true,
		belongDef : obj.storiedBuildId,
		belongDisabled : true
	}
	roomFilter.setVal(options);
	$("#unitName").val(obj.unitName);
	$("#isScny").val(obj.isScny);

	$("#buildLzId").val(obj.buildLzId);
	$("#communityIdLzId").val(obj.communityIdLzId);
	$("#storiedBuildLzId").val(obj.storiedBuildLzId);
	$("#unitId").val(obj.unitId);
	$("#unitLzId").val(obj.unitLzId);
	if (type == 1) {
		$('#btBuildingUnitAdd').hide();
		$("#myModalTitle").html("查看");
	} else {
		$('#btBuildingUnitAdd').show();
		$("#myModalTitle").html("编辑");
	}
	$("#belongBuild").attr("disabled", true);
	$("#belongComm").attr("disabled", true);
	$("#storiedBuild").attr("disabled", true);

	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});

}



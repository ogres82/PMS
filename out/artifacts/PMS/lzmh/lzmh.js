selections = [];
tree = [ {
	text : "乐湾国际一期",
	nodes : [ {
		text : "依山云墅",
		nodes : [ {
			text : "楼宇1",
			nodes : [ {
				text : "单元1",
				nodes : [ {
					text : "23-1"
				} ]
			} ]
		}, {
			text : "楼宇2"
		} ]
	}, {
		text : "湖语美郡"
	} ]
} ];

$(function() {
	initDate();
	initPermissions();
	init();
	initBtnEvent();
	initDrops();
	$('#tree').treeview({
		data : tree, // data is not optional
		levels : 2
	});
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
				$('#btAdd').show();

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

						urlmethod = "method=deleteAllArea";

						layer.confirm("您确定要删除所选信息吗?", {
							skin : 'layui-layer-molv',
							btn : [ '确定', '取消' ]
						}, function() {
							var url = getRootPath()
									+ "allAreaInfo/allAreaList.app?"
									+ urlmethod;
							$.post(url, {
								buildId : selections + ""
							}, function(data) {
								layer.msg(eval(data), {
									time : 2000
								},
										function() {
											$('#allAreaInfo').bootstrapTable(
													'refresh');
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
		$('#lzmhUserInfo').bootstrapTable({
			url : getRootPath() + "lzmh.app?method=userList", // 请求后台的URL（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "asc",
			uniqueId : "userId",
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
				field : 'userId',
				visible : false
			}, {
				field : 'roomId',
				visible : false
			}, {
				field : 'buildName',
				title : '楼盘'
			}, {
				field : 'communityName',
				title : '小区'
			}, {
				field : 'storiedBuildName',
				title : '楼宇'
			}, {
				field : 'unitName',
				visible : false,
				title : '单元'
			}, {
				field : 'roomNo',
				title : '房间'
			}, {
				field : 'ownerName',
				title : '业主'
			}, {
				field : 'phone',
				title : '电话'
			}, {
				field : 'validityStartDate',
				visible : false
			}, {
				field : 'validityEndDate',
				visible : false
			}, {
				field : 'validityDate',
				title : '有效期间',
				formatter : validityDate
			}, {
				field : 'validityStartTime',
				visible : false
			}, {
				field : 'validityEndTime',
				visible : false
			}, {
				field : 'validityTime',
				title : '有效时间',
				formatter : validityTime
			}, {
				field : 'ownerState',
				title : '状态',
				formatter : function(value, row) {
					if (value == 1) {
						return "启用";
					} else {
						return "禁用";
					}

				}
			}, {
				field : 'identityName',
				title : '身份',
				formatter : identityName
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
	validityDate = function(value, row) {
		return row.validityStartDate + " - " + row.validityEndDate
	};
	validityTime = function(value, row) {
		return row.validityStartTime + " - " + row.validityEndTime;
	};
	identityName = function(value, row) {
		if (value == '0') {
			return "业主"
		}
		if (value == '1') {
			return "家属"
		}
		if (value == '2') {
			return "租客"
		}
		if (value == '3') {
			return "游客"
		}
		return ""
	};

	return oTableInit;

};

// 表格选择事件
function tableCheckEvents() {
	var r = $('#lzmhUserInfo').bootstrapTable('getSelections');
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
	return $.map($('#lzmhUserInfo').bootstrapTable('getSelections'), function(
			row) {
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
			url : getRootPath() + "allAreaInfo/allAreaList.app?method=save",
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
		userId : $("#userId").val(),
		validityStartDate : $("#validityDate").val().substring(0,10),
		validityEndDate : $("#validityDate").val().substring(13,23),
		validityStartTime : $("#validityTime").val().substring(0,8),
		validityEndTime : $("#validityTime").val().substring(11,19),
		identityName : $("#identityName").val(),
		state : $("#ownerState").val()
	};
	return addJson;
}

// 清空表单
function clearForm() {
	$("#userId").val("");
	$("#roomAddr").val("");
	$("#ownerName").val("");
	$("#phone").val("");
	$("#identityName").val(0);
	$("#validityDate").val("2018-01-01 - 2070-01-01");
	$("#validityTime").val("00:00:00 - 23:59:59");

	$(".cardId").show();
	$('#tree').show();

}

// 查看和编辑
function editOrCheck(obj, type) {
	var roomAddr = obj.buildName + "-" + obj.communityName + "-"
			+ obj.storiedBuildName + "-" + obj.roomNo;
	$(".cardId").hide();
	$('#tree').hide();
	$('#myForm1').validationEngine('hide');
	$("#ownerName").attr("disabled", true);
	$("#phone").attr("disabled", true);
	// $("#id").attr("disabled",true);
	if (type == 1) {
		$('#btAdd').hide();
		$("#myModalTitle").html("查看");
		$("#validityDate").attr("disabled", true);
		$("#validityTime").attr("disabled", true);
		$("#identityName").attr("disabled", true);
		$("#ownerState").attr("disabled", true);

	} else {
		$('#btAdd').show();
		$("#myModalTitle").html("编辑");
		$('#tree').hide();
		$("#validityDate").attr("disabled", false);
		$("#validityTime").attr("disabled", false);
		$("#identityName").attr("disabled", false);
		$("#ownerState").attr("disabled", false);
	}
	$("#userId").val(obj.userId);
	$("#roomAddr").val(roomAddr);
	$("#ownerName").val(obj.ownerName);
	$("#phone").val(obj.phone);
	$("#identityName").val(obj.identityName);
	$("#ownerState").val(obj.ownerState);
	$("#validityDate").val(obj.validityStartDate + " - " + obj.validityEndDate);
	$("#validityTime").val(obj.validityStartTime + " - " + obj.validityEndTime);
	$('#myModal1').modal();
	alert($("#validityTime").val().substring(0,8));
}

// 初始化下拉框
function initDrops() {
	var url = getRootPath() + "ownerInfo/ownerList.app?method=initDropAll";
	$.post(url, function(data) {

		var list = eval(data);
		for (var j = 0; j < list.length; j++) {

			var detailList = list[j];
			var code = detailList[0];

			if (code == 'owner_identity') {
				$(
						"<option value='" + detailList[1] + "'>"
								+ detailList[2] + "</option>").appendTo(
						"#identityName");
			}
		}
		;
	});

}

function initDate() {
	laydate.render({
		elem : '#validityDate',
		range : '-'
	});
	laydate.render({
		elem : '#validityTime',
		type : 'time',
		range : '-'
	});

}

// function getTree() {
// // Some logic to retrieve, or generate tree structure
// return data;
// }

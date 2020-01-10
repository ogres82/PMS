var loginUserName = "";
var loginUserCname = "";
var kpiLv0 = new Array();
var kpiLv1 = new Array();
var doublebox;
selections = [];
postSelected = [];
deptSelected = [];

$(function() {
	initPermissions();
	initEmpPostDeptDrop();
	initAllDrop();
	// 定位表格查询框
	$(".search").css({
		'position' : 'fixed',
		'right' : '10px',
		'top' : '0',
		'z-index' : '1000',
		'width' : '200px'

	});
	$(".search input").attr("placeholder", "搜索");
	$(".search input").css({
		'padding-right' : '15px'
	});
	$('#myForm1').validationEngine();
	init();
	initBtnEvent();

	$('#buttonSearch').click(function() {
		$('#kpiInfo').bootstrapTable('refresh');
	});

});

function initPermissions() {
	toolbarBtnInit(); // 初始化工具条按钮
}

function initBtnEvent() {

	for (var i = 0; i < btnIdArray.length; i++) {
		// 新增
		if (btnIdArray[i] == "btn_add") {
			$("#btn_add").bind('click', function() {
				var skpiLv = $('#SelectLv').val();
				$('#myForm1').validationEngine('hide');
				$('#btKpiAdd').show();
				$('#kpiRateInsert').show();
				$('#kpiRateDel').show();

				showKpiAdd(skpiLv, 1);
				clearForm();

				$('#kpiRate').bootstrapTable('refresh');
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
				var skpiLv = $('#SelectLv').val();
				$('#myForm1').validationEngine('hide');
				selections = getIdSelections(1);

				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = getRootPath() + "assessment/kpi.app?method=kpiDel&kpiLv=" + skpiLv;
					$.post(url, {
						kpiId : selections + ""
					}, function(data) {
						layer.msg(eval(data), {
							time : 2000
						}, function() {
							$('#kpiInfo').bootstrapTable('refresh');
							$('#kpiRate').bootstrapTable('refresh');
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

	new kpiRateTableInit().Init();
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#kpiInfo').bootstrapTable({
			url : getRootPath() + "assessment/kpi.app?method=kpiList",
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "kpiId asc",
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
				title : '序号',// 标题 可不加
				formatter : function(value, row, index) {
					return index + 1;
				}
			}, {
				field : 'kpiId',
				visible : false,
				title : 'ID'
			}, {
				field : 'kpiParentId',
				visible : false
			}, {
				field : 'kpiLv',
				visible : false
			}, {
				field : 'operId',
				visible : false
			}, {
				field : 'empPostId',
				visible : false
			}, {
				field : 'empPostName',
				visible : false
			}, {
				field : 'deptName',
				visible : false
			}, {
				field : 'kpiLv0Name',
				title : '专业名称',
				formatter : kpilv0NameFormat
			}, {
				field : 'kpiLv1Name',
				title : '分类名称',
				formatter : kpilv1NameFormat
			}, {
				field : 'kpiName',
				title : '指标名称'
			}, {
				field : 'kpiDetail',
				width : '28%',
				title : '工作指标常态化'
			}, {
				field : 'kpiValue',
				title : '分值'
			}, {
				field : 'kpiMethod',
				width : '25%',
				title : '核查方法及扣分'
			}, {
				field : 'empPostDeptName',
				title : '考核部门/岗位',
				formatter : function(value, row, index) {
					return row.empPostName || row.deptName;
				}
			}, {
				field : 'kpiType',
				title : '考核类别',
				formatter : function(value) {
					if (value == "0") {
						return "员工考核指标"
					} else {
						return "部门考核指标"
					}
				}
			}, {
				field : 'kpiRate',
				title : '频率',
				formatter : kpiRateFormat
			}, {
				field : 'operName',
				title : '操作员'
			}, {
				field : 'insertDate',
				title : '创建时间',
				visible : false,
				formatter : dateFormat
			}, {
				field : 'updateDate',
				visible : false,
				title : '更新时间',
				formatter : dateFormat
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				width : '8%',
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
				var skpiLv = $('#SelectLv').val();
				$("#btn_del").attr("disabled", true);
				buttonSearch(skpiLv);
				showColumns(skpiLv);

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
			search : encodeURI(params.search)
			
		};
		return temp;
	};
	dateFormat = function(value) {
		if (value == null || value == "") {
			return "";
		} else {
			return json2TimeStamp(value);
		}

	};
	kpiRateFormat = function(value) {
		var strValue = "";

		for (var i = 0, len = value.length; i < len; i++) {
			strValue += value[i].empPostName + ":" + value[i].rateValue + "次/" + rateTouUnit(value[i].rateDays) + "<br>";
		}
		return strValue;
	};
	kpilv1NameFormat = function(value, row) {

		for (var i = 0, len = kpiLv1.length; i < len; i++) {
			if (kpiLv1[i].kpiId == row.kpiParentId) {
				return kpiLv1[i].kpiName;
			}
		}
		return "";
	};
	kpilv0NameFormat = function(value, row) {
		var kpiParentId = "";
		if (row.kpiLv == 1) {
			kpiParentId = row.kpiParentId;
		} else {
			for (var i = 0, len = kpiLv1.length; i < len; i++) {
				if (kpiLv1[i].kpiId == row.kpiParentId) {

					kpiParentId = kpiLv1[i].kpiParentId;
				}
			}
		}
		for (var j = 0; j < kpiLv0.length; j++) {
			if (kpiLv0[j].kpiId == kpiParentId) {
				return kpiLv0[j].kpiName;
			}
		}
		return "";
	};
	return oTableInit;
};

// 表格选择事件
function tableCheckEvents() {
	// kpi的table使用
	var r = $('#kpiInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
	// rate的table使用
	var row = $('#kpiRate').bootstrapTable('getSelections');
	if (row.length == 0) {
		$("#kpiRateDel").attr("disabled", true);
	}
	if (row.length == 1) {
		$("#kpiRateDel").attr("disabled", false);
	}
}

function rateTouUnit(rateDays) {
	var rateUnit = "";

	switch (rateDays) {
	case 1:
		rateUnit = "天";
		break;
	case 7:
		rateUnit = "周";
		break;
	case 14:
		rateUnit = "两周";
		break;
	case 30:
		rateUnit = "月";
		break;
	default:
		rateUnit = "";
	}
	return rateUnit;
}

/**
 * 指标下来菜单
 */
function initAllDrop() {
	$("#kpiLv0").empty();
	$("#kpiLv0Ins").empty();
	$("#kpiLv1").empty();
	$("#kpiLv1Ins").empty();
	$("#kpiLv0").append("<option disabled selected style='display: none;' value=''>专业名称</option>");
	$("#kpiLv0Ins").append("<option disabled selected style='display: none;' value=''>专业名称</option>");
	$("#kpiLv1").append("<option disabled selected style='display: none;' value=''>类别名称</option>");
	$("#kpiLv1Ins").append("<option disabled selected style='display: none;' value=''>类别名称</option>");
	$.post(getRootPath() + "assessment/kpi.app?method=kpiList", function(data) {
		var fromData = eval(data);
		initKpiDetail(fromData);
		for (var i = 0, len = fromData.length; i < len; i++) {
			var detailList = fromData[i];
			if (detailList.kpiLv == '0') {
				$("<option value='" + detailList.kpiId + "'>" + detailList.kpiName + "</option>").appendTo("#kpiLv0");
				$("<option value='" + detailList.kpiId + "'>" + detailList.kpiName + "</option>").appendTo("#kpiLv0Ins");
			}
			if (detailList.kpiLv == '1') {
				$("<option value='" + detailList.kpiId + "'>" + detailList.kpiName + "</option>").appendTo("#kpiLv1");
				$("<option value='" + detailList.kpiId + "'>" + detailList.kpiName + "</option>").appendTo("#kpiLv1Ins");
			}
		}
	});

}

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections(type) {
	if (type == 1) {
		return $.map($('#kpiInfo').bootstrapTable('getSelections'), function(row) {
			return row.kpiId;
		});
	} else {
		return $.map($('#kpiRate').bootstrapTable('getSelections'), function(row) {
			return row.rateId;
		});
	}

}
function initEmpPostDeptDrop() {
	// 岗位数据
	$.post(getRootPath() + 'system/posMaintain.app?method=load', function(data) {
		var empPostInfo = eval(data);
		for (var i = 0, len = empPostInfo.length; i < len; i++) {
			$("<option value='" + empPostInfo[i].id + "'>" + empPostInfo[i].name + "</option>").appendTo("#doublebox");
			$("<option value='" + empPostInfo[i].id + "'>" + empPostInfo[i].name + "</option>").appendTo("#empPostIns");
		}
		$('#doublebox').multiSelect({
			afterSelect : function(values) {
				postSelected.push(values);
			},
			afterDeselect : function(values) {
				postSelected.splice($.inArray(values, postSelected), 1);
			}
		});
	});
	// 部门数据
	$.post(getRootPath() + 'system/deptMaintain.app?method=load', function(data) {
		var empDeptInfo = eval(data);
		for (var i = 0, len = empDeptInfo.length; i < len; i++) {
			$("<option value='" + empDeptInfo[i].id + "'>" + empDeptInfo[i].name + "</option>").appendTo("#doublebox1");
		}
		$('#doublebox1').multiSelect({
			afterSelect : function(values) {
				deptSelected.push(values);
			},
			afterDeselect : function(values) {
				deptSelected.splice($.inArray(values, deptSelected), 1);
			}
		});
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
			url : getRootPath() + "assessment/kpi.app?method=kpiSave",
			data : {
				kpiInfo : JSON.stringify(addJson)
			},
			dataType : "json",
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					initAllDrop();
					$('#kpiInfo').bootstrapTable('refresh');
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
		kpiName : $("#kpiName").val(),
		kpiValue : $("#kpiValue").val(),
		kpiDetail : $("#kpiDetail").val(),
		kpiMethod : $("#kpiMethod").val(),
		kpiType : $("#kpiType").val(),
		insertDate : $("#insertDate").val() || dateFormat(new Date(), 'yyyy-MM-dd HH:mm:ss'),
		updateDate : $("#updateDate").val() || dateFormat(new Date(), 'yyyy-MM-dd HH:mm:ss'),
		kpiId : $("#kpiId").val(),
		empPostId : (postSelected + "" || deptSelected + ""),
		kpiLv : $("#SelectLv").val(),
		kpiParentId : $("#kpiLv1Ins").val() || $("#kpiLv0Ins").val(),
		kpiRate : $('#kpiRate').bootstrapTable('getData'),
		operId : loginUserName
	};

	return addJson;
}

// 清空表单
function clearForm() {
	postSelected.splice(0, postSelected.length);
	deptSelected.splice(0, postSelected.length);
	$("#kpiLv0Ins").val("");
	$("#kpiLv1Ins").val("");
	$("#kpiName").val("");
	$("#kpiValue").val("");
	$("#kpiDetail").val("");
	$("#kpiMethod").val("");
	$("#insertDate").val("");
	$("#updateDate").val("");
	$("#kpiId").val("");
	$("#kpiType").val("0");
	$('#doublebox').multiSelect('deselect_all');
	$('#doublebox1').multiSelect('deselect_all');
	$("#kpiRate").val("");
	$('#myFrom1,input,select,textarea').attr('readonly', false);
}

// 查看和编辑
function editOrCheck(obj, type) {
	var skpiLv = $('#SelectLv').val();
	var kpiType = $('#kpiType').val();
	$('#myForm1').validationEngine('hide');
	clearForm();
	if (type == 1) {
		$('#btKpiAdd').hide();
		$('#kpiRateInsert').hide();
		$('#kpiRateDel').hide();
		$("#myModalTitle").html("查看");

		$('#myFrom1,input,select,textarea').attr('readonly', true);
	} else {
		$('#btKpiAdd').show();
		$('#kpiRateInsert').show();
		$('#kpiRateDel').show();
		$("#myModalTitle").html("编辑");
		$('#myFrom1,input,select,textarea').attr('readonly', false);
	}
	
	$("#kpiType").val(obj.kpiType);
	showKpiAdd(skpiLv, 2);
	setDoubleboxValue(obj.empPostId);
	$("#kpiLv0Ins").val(getPreParentId(obj));
	$("#kpiLv1Ins").val(obj.kpiParentId);
	$("#kpiName").val(obj.kpiName);
	$("#kpiValue").val(obj.kpiValue);
	$("#kpiDetail").val(obj.kpiDetail);
	$("#kpiMethod").val(obj.kpiMethod);
	$("#insertDate").val(json2TimeStamp(obj.insertDate));
	$("#updateDate").val(json2TimeStamp(obj.updateDate));
	$("#kpiId").val(obj.kpiId);
	$("#kpiType").val(obj.kpiType);
	$('#kpiRate').bootstrapTable('refresh');
	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});
}

function clearSearch() {
	$("#kpilv0").val("");
	$("#kpiLv1").val("");
	$("#empPost").val("");
	$("#SelectLv").val("2");
	$("#kpiTypeS").val("");

	$("#rateUnit").val("");
	$("#empPostIns").val("");
	$("#rateValue").val("");
	$('#buttonSearch').click();
}

// 初始kpi数据
function initKpiDetail(data) {
	kpiLv0 = [];
	kpiLv1 = [];
	for (var i = 0, len = data.length; i < len; i++) {
		if (data[i].kpiLv == '0') {
			kpiLv0.push(data[i]);
		}
		if (data[i].kpiLv == '1') {
			kpiLv1.push(data[i]);
		}
	}
}

// 根据下拉菜单的选，显示需要填写的信息 type：1=新增；2=编辑
function showKpiAdd(kpiLv, type) {

	$('.kpiLv0Ins').hide();
	$('.kpiLv1Ins').hide();
	$('.kpiName').hide();
	$('.kpiValue').hide();
	$('.kpiDetail').hide();
	$('.kpiMethod').hide();
	$('.empPost').hide();
	$('.empDept').hide();
	$('.kpiRateBtn').hide();
	$('.kpiRateInfo').hide();
	$('.kpiType').hide();

	if (kpiLv == 0) {
		$('.kpiName').show();
		$('.kpiValue').show();
		if ($('#kpiType').val() == "0") {
			$('.empPost').show();
		} else {

			$('.empDept').show();
		}
		$('.kpiType').show();
	}
	if (kpiLv == 1) {
		$('.kpiLv0Ins').show();
		$('.kpiName').show();
		$('.kpiValue').show();
	}
	if (kpiLv == 2) {
		$('.kpiLv0Ins').show();
		$('.kpiLv1Ins').show();
		$('.kpiValue').show();
		$('.kpiDetail').show();
		$('.kpiMethod').show();
		if (type == 2) {
			$('.kpiRateBtn').show();
			$('.kpiRateInfo').show();
		}
	}
}

// 根据kpi层级显示table相应的字段
function showColumns(kpiLv) {
	if (kpiLv == 2) {
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiName');
		$('#kpiInfo').bootstrapTable('hideColumn', 'operName');
		$('#kpiInfo').bootstrapTable('hideColumn', 'empPostDeptName');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiType');
		$('#kpiInfo').bootstrapTable('showColumn', 'kpiDetail');
		$('#kpiInfo').bootstrapTable('showColumn', 'kpiMethod');
		$('#kpiInfo').bootstrapTable('showColumn', 'kpiRate');
		$('#kpiInfo').bootstrapTable('showColumn', 'kpiLv0Name');
		$('#kpiInfo').bootstrapTable('showColumn', 'kpiLv1Name');
	}
	if (kpiLv == 0) {
		$('#kpiInfo').bootstrapTable('showColumn', 'kpiName');
		$('#kpiInfo').bootstrapTable('showColumn', 'operName');
		$('#kpiInfo').bootstrapTable('showColumn', 'empPostDeptName');
		$('#kpiInfo').bootstrapTable('showColumn', 'kpiType');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiDetail');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiMethod');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiRate');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiLv0Name');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiLv1Name');
	}
	if (kpiLv == 1) {
		$('#kpiInfo').bootstrapTable('showColumn', 'kpiName');
		$('#kpiInfo').bootstrapTable('hideColumn', 'operName');
		$('#kpiInfo').bootstrapTable('hideColumn', 'empPostName');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiDetail');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiMethod');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiRate');
		$('#kpiInfo').bootstrapTable('showColumn', 'kpiLv0Name');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiLv1Name');
		$('#kpiInfo').bootstrapTable('hideColumn', 'kpiType');
	}
}

// 根据条件进行查询
function buttonSearch(kpiLv) {

	var kpiLv1 = $('#kpiLv1').val() || "";
	var kpiTypeS = $('#kpiTypeS').val() || "";

	var search = {
		'kpiLv' : parseInt(kpiLv)
	};

	if (kpiLv1 != '') {
		search = $.extend(search, {
			'kpiParentId' : kpiLv1
		});
	}
	if (kpiTypeS != '') {
		search = $.extend(search, {
			'kpiType' : kpiTypeS
		});
	}	
	
	$('#kpiInfo').bootstrapTable('filterBy', search);
}

// 获取父节点的父节点，哈哈
function getPreParentId(obj) {
	var kpiParentId = obj.kpiParentId;
	if (obj.kpiLv == '2') {
		for (var i = 0, len = kpiLv1.length; i < len; i++) {
			if (kpiParentId == kpiLv1[i].kpiId) {
				return kpiLv1[i].kpiParentId;
			}
		}
	} else {
		return "";
	}
};

var kpiRateTableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#kpiRate').bootstrapTable({
			url : getRootPath() + "assessment/kpi.app?method=rateList",
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			sortable : false,
			sortOrder : "kpiId asc",
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : false,
			queryParams : oTableInit.queryParams,
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			columns : [ {
				field : 'state',
				checkbox : true,
			}, {
				title : '序号',// 标题 可不加
				formatter : function(value, row, index) {
					return index + 1;
				}
			}, {
				field : 'rateId',
				visible : false
			}, {
				field : 'empPostName',
				title : '岗位名称'
			}, {
				field : 'kpiId',
				visible : false
			}, {
				field : 'empPostId',
				visible : false
			}, {
				field : 'rateValue',
				visible : false,
				title : '检查次数'
			}, {
				field : 'rateDays',
				visible : false,
				title : '检查周期'
			}, {
				field : 'rate',
				title : '检查频率',
				formatter : rateFormat
			} ],
			onCheck : function(row, e) {
				tableCheckEvents();
			},
			onUncheck : function(row, e) {
				tableCheckEvents();
			},
			onCheckAll : function(rows) {
				$("#kpiRateDel").attr("disabled", false);
			},
			onUncheckAll : function(rows) {
				$("#kpiRateDel").attr("disabled", true);
			},
			onLoadSuccess : function(rows) {
				$("#kpiRateDel").attr("disabled", true);
			}
		});
	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			kpiId : $("#kpiId").val()
		};
		return temp;
	};
	rateFormat = function(value, row) {

		return row.rateValue + "次/" + rateTouUnit(row.rateDays);
	}
	return oTableInit;
};

// 二级弹出窗口
function openRateWindow(type) {
	if (type == 1) {
		var kpiId = $("#kpiId").val();
		if (kpiId == "") {
			layer.msg('请先填写考核指标信息！', {
				time : 2000
			});
			return;
		}
		clearSearch();
		$('#myModal2').modal();
	}
	if (type == 2) {
		selections = getIdSelections(2);
		layer.confirm("您确定要删除所选信息吗?", {
			skin : 'layui-layer-molv',
			btn : [ '确定', '取消' ]
		}, function() {
			var url = getRootPath() + "assessment/kpi.app?method=rateDel";
			$.post(url, {
				rateId : selections + ""
			}, function(data) {
				layer.msg(eval(data), {
					time : 2000
				}, function() {
					$('#kpiRate').bootstrapTable('refresh');
					$('#kpiInfo').bootstrapTable('refresh');
				});
			});
		}, function() {
			return;
		});
	}
}

function rateSaveButton() {
	var empPostId = $("#empPostIns").val();
	var empPostName = $("#empPostIns").find("option:selected").text();

	var rateDays = $("#rateUnit").val();
	var kpiId = $("#kpiId").val();
	var rateValue = $("#rateValue").val();

	var data = {
		recId : "",
		empPostId : empPostId,
		rateDays : parseInt(rateDays),
		kpiId : kpiId,
		rateValue : rateValue,
		empPostName : empPostName
	}

	$('#kpiRate').bootstrapTable('append', data);
	$("#myModal2").modal('hide');
}

function setDoubleboxValue(strValue) {
	if ($('#SelectLv').val() != '0') {
		return;
	}
	var arrValue = strValue.split(",");
	var kpiType = $('#kpiType').val();
	if (kpiType == "0") {
		$('#doublebox').multiSelect('deselect_all');
		$("#doublebox").multiSelect('select', arrValue);
		$("#doublebox").multiSelect('refresh');
	}
	$('#doublebox1').multiSelect('deselect_all');
	$("#doublebox1").multiSelect('select', arrValue);
	$("#doublebox1").multiSelect('refresh');
}

function doubleboxShow() {
	var kpiType = $('#kpiType').val();
	$('.empPost').hide();
	$('.empDept').hide();
	if (kpiType == "0") {
		$('.empPost').show();
	} else {
		$('.empDept').show();
	}
}

var loginUserName = "";
var loginUserCname = "";

selections = [];

$(function() {

	initPermissions();
	initLayDate();
	initBtnEvent();
	init();
	initDeptPostDrop();
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
	

	$('#buttonSearch').click(function() {
		$('#kpiRecInfo').bootstrapTable('refresh');
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
				$('#myForm1').validationEngine('hide');
				$('#btKpiAdd').show();
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
				$('#myForm1').validationEngine('hide');
				selections = getIdSelections();

				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = getRootPath() + "assessment/kpi.app?method=kpiDel&kpiLv=" + skpiLv;
					$.post(url, {
						recId : selections + ""
					}, function(data) {
						layer.msg(eval(data), {
							time : 2000
						}, function() {
							$('#kpiRecInfo').bootstrapTable('refresh');
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
		$('#kpiRecInfo').bootstrapTable({
			url : getRootPath() + "assessment/kpi.app?method=kpiRecList",
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "insertDate asc",
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
				field : 'recId',
				visible : false
			}, {
				field : 'kpiId',
				visible : false
			}, {
				field : 'empId',
				visible : false
			}, {
				field : 'imgUlrs',
				visible : false
			}, {
				field : 'operId',
				visible : false
			}, {
				field : 'empDeptId',
				visible : false
			}, {
				field : 'empPostId',
				visible : false
			}, {
				field : 'reason',
				visible : false,
				title : '扣分理由'
			}, {
				field : 'empDeptName',
				title : '部门名称'
			}, {
				field : 'empPostName',
				title : '岗位名称'
			}, {
				field : 'kpiValue',
				title : '扣分值'
			}, {
				field : 'empName',
				title : '被考核人员'
			}, {
				field : 'kpiDetail',
				title : '考核内容'
			}, {
				field : 'kpiMethod',
				title : '扣分依据'
			}, {
				field : 'operName',
				title : '考核人'
			}, {
				field : 'insertDate',
				title : '考核时间',
				width : '8%',
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
			search : encodeURI(params.search),
			//operId :loginUserName,
			empDeptId : $("#deptIdSearch").val(),
			insertDate : $("#dateSearch").val()			
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
	return oTableInit;
};

// 表格选择事件
function tableCheckEvents() {
	// kpi的table使用
	var r = $('#kpiRecInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
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
 * 选择框
 * 
 * @returns
 */
function getIdSelections(type) {
	return $.map($('#kpiRecInfo').bootstrapTable('getSelections'), function(row) {
		return row.recId;
	});
}

function initDeptPostDrop() {
	// 岗位名称数据
	$.post(getRootPath() + 'system/posMaintain.app?method=load', function(data) {
		var empPostInfo = eval(data);
		for (var i = 0, len = empPostInfo.length; i < len; i++) {
			$("<option value='" + empPostInfo[i].id + "'>" + empPostInfo[i].name + "</option>").appendTo("#empPost");
		}
	});
	
	$.post(getRootPath() +'system/deptMaintain.app?method=load', function(data) {
		var empDeptInfo = eval(data);
		for (var i = 0, len = empDeptInfo.length; i < len; i++) {
			$("<option value='" + empDeptInfo[i].id + "'>" + empDeptInfo[i].name + "</option>").appendTo("#empDept");
			$("<option value='" + empDeptInfo[i].id + "'>" + empDeptInfo[i].name + "</option>").appendTo("#deptIdSearch");
		}
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
				kpiRecInfo : JSON.stringify(addJson)
			},
			dataType : "json",
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					initAllDrop();
					$('#kpiRecInfo').bootstrapTable('refresh');
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
		insertDate : $("#insertDate").val() || dateFormat(new Date(), 'yyyy-MM-dd HH:mm:ss'),
		updateDate : $("#updateDate").val() || dateFormat(new Date(), 'yyyy-MM-dd HH:mm:ss'),
		kpiId : $("#kpiId").val(),
		empPostId : $("#empPost").val(),
		kpiLv : $("#SelectLv").val(),
		kpiParentId : $("#kpiLv1Ins").val() || $("#kpiLv0Ins").val(),
		kpiRate : $('#kpiRate').bootstrapTable('getData'),
		operId : loginUserName
	};

	return addJson;
}

// 清空表单
function clearForm() {

	$("#recId").val("");
	$("#kpiId").val("");
	$("#empId").val("");
	$("#imgUlrs").val("");
	$("#operId").val("");
	$("#empDept").val("");
	$("#empPost").val("");
	//$("#empDeptName").val("");
	//$("#empPostName").val("");
	$("#reason").val("");
	$("#empName").val("");
	$("#kpiDetail").val("");
	$("#kpiMethod").val("");
	$("#kpiName").val("");
	$("#kpiValue").val("");
	$("#kpiDetail").val("");
	$("#kpiMethod").val("");
	$("#operName").val("");
	$("#insertDate").val("");
	$('#myFrom1,input,select,textarea').attr('readonly', false);
}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	if (type == 1) {
		$('#btRecAdd').hide();
		$("#myModalTitle").html("查看");

		$('#myFrom1,input,select,textarea').attr('readonly', true);
	} else {
		$('#btRecAdd').show();
		$("#myModalTitle").html("编辑");
		$('#myFrom1,input,select,textarea').attr('readonly', false);
	}

	$("#recId").val(obj.recId);
	$("#kpiId").val(obj.kpiId);
	$("#empId").val(obj.empId);
	$("#imgUlrs").val(obj.imgUlrs);
	$("#operId").val(obj.operId);
	$("#empDept").val(obj.empDeptId);
	$("#empPost").val(obj.empPostId);
	//$("#empDeptName").val(obj.empDeptName);
	//$("#empPostName").val(obj.empPostName);
	$("#reason").val(obj.reason);
	$("#empName").val(obj.empName);
	$("#kpiDetail").val(obj.kpiDetail);
	$("#kpiMethod").val(obj.kpiMethod);
	$("#kpiName").val(obj.kpiName);
	$("#kpiValue").val(obj.kpiValue);
	$("#kpiDetail").val(obj.kpiDetail);
	$("#kpiMethod").val(obj.kpiMethod);
	$("#operName").val(obj.operName);
	$("#insertDate").val(json2TimeStamp(obj.insertDate));
	
	
	$("#imgUlrs").empty();// 每一次请求，先把上一次的清空
	
	var imgurl = obj.imgUlrs.split(',');
	if (imgurl.length>0) {
		var html = [];
		//var imgArray = JSON.parse(imgurl);
		for (var i = 0; i < imgurl.length; i++) {
			html.push("<a class='fancybox' href='" + imgurl[i] + "'><img alt='img' src='" + imgurl[i] + "' onload='AutoResizeImage(0,150,this)'/></a>");
		}
		var img = html.join("");
		$("#imgUlrs").append(img);
	}
	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});
}

function clearSearch() {
	$("#deptIdSearch").val("");
	$("#dateSearch").val("");
	$('#buttonSearch').click();
}

function initLayDate() {
	var dateSearch = {
		elem : '#dateSearch',
		max : '2099-06-16', // 最大日期
		istime : false,
		istoday : false
	};
	laydate.render(dateSearch);
}


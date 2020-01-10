var loginUserName = "";
var loginUserCname = "";
var userdepts= [];
selections = [];

$(function() {

	alert(userString);
    alert(userString[0].name);
	initPermissions();
	initLayDate();
	
	init();
	initDeptDrop();
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
		$('#assessmentInfo').bootstrapTable('refresh');
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
		$('#assessmentInfo').bootstrapTable({
			url : getRootPath() + "assessment/kpi.app?method=recMonthly",
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "empDeptId asc",
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
				field : 'empId',
				visible : false
			}, {
				field : 'empPostId',
				visible : false
			}, {
				field : 'empDeptId',
				visible : false
			}, {
				field : 'empName',
				title : '员工姓名'
			}, {
				field : 'empSex',
				title : '员工性别'
			}, {
				field : 'empDeptName',
				title : '部门名称'
			}, {
				field : 'empPostName',
				title : '岗位名称'
			}, {
				field : 'kpiValue',
				title : '季度总分',
				formatter : kpiValueFormat
			}, {
				field : 'months',
				title : '考核月份'
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
				// $("#btn_del").attr("disabled", false);
			},
			onUncheckAll : function(rows) {
				// $("#btn_del").attr("disabled", true);
			},
			onLoadSuccess : function(rows) {
				// $("#btn_del").attr("disabled", true);

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
			empDeptId : $("#deptIdSearch").val(),
			months : $("#dateSearch").val()
		};
		return temp;
	};
	kpiValueFormat = function(value) {
		return 100 - value;
	};
	return oTableInit;
};

function initDeptDrop() {

	$.post(getRootPath() + 'system/deptMaintain.app?method=load', function(data) {
		var empDeptInfo = eval(data);
		for (var i = 0, len = empDeptInfo.length; i < len; i++) {
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
	// $("#empDeptName").val("");
	// $("#empPostName").val("");
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
	// $("#empDeptName").val(obj.empDeptName);
	// $("#empPostName").val(obj.empPostName);
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
	if (imgurl.length > 0) {
		var html = [];
		// var imgArray = JSON.parse(imgurl);
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
		type : 'month',
		max : '2099-12', // 最大日期
		istime : false,
		istoday : false
	};
	laydate.render(dateSearch);
}

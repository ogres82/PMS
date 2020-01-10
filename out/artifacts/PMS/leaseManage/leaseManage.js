var loginUserName = "";
var loginUserCname = "";
var operate = "";
selections = [];

$(function() {
	init();
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
		$('#leaseManageInfo').bootstrapTable('refresh');
	});

});

function init() {
	// 初始化Table
	oTable = new TableInit();
	oTable.Init();

	toolbarBtnInit(); // 初始化工具条按钮
	initBtnEvent();
	initAllDrop();
	
	laydate.render({
		elem : '#startTime',
		// format: 'YYYY年MM月DD日',
		festival : true, // 显示节日
		choose : function(datas) { // 选择日期完毕的回调
			$('#myForm1').validationEngine('hide');
		}
	});
};

function initBtnEvent() {

	for (var i = 0; i < btnIdArray.length; i++) {
		// 新增
		if (btnIdArray[i] == "btn_add") {
			$("#btn_add").bind('click', function() {
				$('#myForm1').validationEngine('hide');
				$('#btAdd').show();
				$("#myModalTitle").html("新增");
				$('#myModal1').modal({
					backdrop : 'static',
					keyboard : false
				});
			});
		}
		// 删除
		if (btnIdArray[i] == "btn_del") {
			$("#btn_del").bind('click',
			function() {
				$('#myForm1').validationEngine('hide');
				selections = getIdSelections();
				layer.confirm("您确定要删除所选信息吗?",{skin : 'layui-layer-molv',btn : [ '确定', '取消' ]},
				function() {
					var url = getRootPath()	+ "leaseManage.app?method=delLeaseInfo";
					
					$.post(url,{leaseId : selections + ""},
						function(data) {
							layer.msg(eval(data),{time : 2000},
								function() {
									$('#leaseManageInfo').bootstrapTable('refresh');
								});
							});
				}, function() {
					return;
				});
			});
		}
	}
}

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#leaseManageInfo').bootstrapTable({
			url : getRootPath() + "leaseManage.app?method=leaseInfo",
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true,
			search : true,
			searchOnEnterKey : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			sortable : false,
			sortOrder : "roomId asc",
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
				field : 'buildId',
				visible : false,
				title : 'buildId'
			}, {
				field : 'communityId',
				visible : false,
				title : 'communityId'
			}, {
				field : 'storiedBuildId',
				visible : false,
				title : 'storiedBuildId'
			}, {
				field : 'roomId',
				visible : false,
				title : 'roomId'
			}, {
				field : 'leaseId',
				visible : false,
				title : 'leaseId'
			}, {
				field : 'roomAddr',
				title : '商铺地址'
			}, {
				field : 'buildArea',
				title : '建筑面积'
			}, {
				field : 'lesseesName',
				title : '租户姓名'
			}, {
				field : 'cardId',
				title : '身份证号码'
			}, {
				field : 'phone',
				title : '联系电话'
			}, {
				field : 'monthsNum',
				title : '出租月数'
			}, {
				field : 'monthsPrice',
				title : '出租价格'
			}, {
				field : 'totalPrice',
				title : '总价'
			}, {
				field : 'startTime',
				formatter : dateFormat,
				title : '起租时间'
			}, {
				field : 'endTime',
				formatter : dateFormat,
				title : '终止时间'
			}, {
				field : 'insertTime',
				visible : false,
				title : '创建时间'
			}, {
				field : 'updateTime',
				visible : false,
				title : '更新时间'
			}, {
				field : 'operId',
				visible : false
			}, {
				field : 'operName',
				title : '操作员'
			}, {
				field : 'remark',
				title : '备注'
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
		'click #a_renew' : function(e, value, row, index) {
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
			return json2Date(value);
		}

	};
	totalPrice = function(value, row, index) {
		return row.monthsPrice * row.monthsNum;
	};

	return oTableInit;
};

// 表格选择事件
function tableCheckEvents() {
	// leaseManageInfo的table使用
	var r = $('#leaseManageInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
}

/**
 * 指标下来菜单
 */
function initAllDrop() {
	// 房间下拉框
	$("#roomInfo").bsSuggest({
		url : getRootPath() + "leaseManage.app?method=getShopInfo&keyword=",
		showHeader : true,
		allowNoKeyword : true,
		keyField : 'keyword',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : [ "roomAddr", "buildArea" ],
		effectiveFieldsAlias : {
			roomAddr : "商铺地址",
			buildArea : "建筑面积"
		},
		// 预处理
		fnPreprocessKeyword : function(keyword) {
			// 请求数据前，对输入关键字作预处理
			return encodeURI(keyword);
		},
		processData : function(json) { // url 获取数据时，对数据的处理，作为 getData 的回调函数

			var index, len, data = {
				value : []
			};
			if (!json || json.length === 0) {
				return false;
			}
			data.value = json;

			return data;
		}
	}).on("onDataRequestSuccess", function(e, result) {
		console.log("onDataRequestSuccess: ", result)
	}).on("onSetSelectValue", function(e, keyword, data) {
		console.log("onSetSelectValue: ", keyword, data);
		$("#roomAddr").val(data.roomAddr);
		$("#buildArea").val(data.buildArea);
		$("#roomId").val(data.roomId);
		$("#roomInfo").val("");

	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});
}

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {			
	return $.map($('#leaseManageInfo').bootstrapTable('getSelections'), function(row) {
		return row.leaseId;
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
			url : getRootPath() + "leaseManage.app?method=addLeaseInfo&operate="+operate,
			data : {
				leaseManageInfo : JSON.stringify(addJson)
			},
			dataType : "json",
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#leaseManageInfo').bootstrapTable('refresh');
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
		roomId : $("#roomId").val(),
		leaseId : $("#leaseId").val(),
		lesseesName : $("#lesseesName").val(),
		phone : $("#phone").val(),
		cardId : $("#cardId").val(),
		monthsNum : $("#monthsNum").val(),
		monthsPrice : $("#monthsPrice").val(),
		startTime : $("#startTime").val(),
		state : "1",
		remark : $("#remark").val(),
		insertTime : $("#insertTime").val()	|| dateFormat(new Date(), 'yyyy-MM-dd HH:mm:ss'),
		updateTime : dateFormat(new Date(), 'yyyy-MM-dd HH:mm:ss'),
		operId : loginUserName,
		deleteIs : "0"
	};

	return addJson;
}

// 清空表单
function clearForm() {
	$("#roomId").val("");
	$("#lesseesName").val("");
	$("#phone").val("");
	$("#cardId").val("");
	$("#monthsNum").val("");
	$("#monthsPrice").val("");
	$("#startTime").val("");
	$("#endTime").val("");
	$("#remark").val("");
	$("#insertTime").val("");
	$("#updateTime").val("");
	$("#leaseId").val("");
	$("#roomAddr").val("");
	$("#buildArea").val("");
	$("#totalPrice").val("");
}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	$("#roomId").val(obj.roomId);
	$("#lesseesName").val(obj.lesseesName);
	$("#phone").val(obj.phone);
	$("#cardId").val(obj.cardId);
	$("#monthsNum").val(obj.monthsNum);
	$("#monthsPrice").val(obj.monthsPrice);
	$("#startTime").val(json2Date(obj.startTime));
	$("#endTime").val(json2Date(obj.endTime));
	$("#remark").val(obj.remark);
	$("#insertTime").val(obj.insertTime);
	$("#updateTime").val(obj.updateTime);
	$("#leaseId").val(obj.leaseId);
	$("#roomAddr").val(obj.roomAddr);
	$("#buildArea").val(obj.buildArea);
	$("#totalPrice").val(obj.totalPrice);	
	
	if (type == 1) {
		operate = "renew";
		$("#myModalTitle").html("续租");
		$("#startTime").val("");
		$("#endTime").val("");
		$("#monthsNum").val("");
		$("#monthsPrice").val("");
		$("#insertTime").val("");
		$("#updateTime").val("");
		$("#totalPrice").val("");	
		$("#lesseesName").attr("readOnly",true);
		$("#phone").attr("readOnly",true);
		$("#cardId").attr("readOnly",true);		
	} else {
		operate = "update";
		$("#myModalTitle").html("编辑");		
		$("#lesseesName").attr("readOnly",false);
		$("#phone").attr("readOnly",false);
		$("#cardId").attr("readOnly",false);	
	}


	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});
}

//function clearSearch() {
//	$("#kpilv0").val("");
//	$("#kpiLv1").val("");
//	$("#empPost").val("");
//	$("#SelectLv").val("2");
//	$("#kpiTypeS").val("");
//
//	$("#rateUnit").val("");
//	$("#empPostIns").val("");
//	$("#rateValue").val("");
//	$('#buttonSearch').click();
//}

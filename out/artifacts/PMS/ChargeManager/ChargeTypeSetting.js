$(function() {
	toolbarBtnInit();
	for (var i = 0; i < btnIdArray.length; i++) {
		if (btnIdArray[i] == "btn_add") {
			$("#btn_add").bind('click', function() {
				add();
			});
		} else if (btnIdArray[i] == "btn_del") {
			$("#btn_del").bind('click', function() {
				btnDel();
			});

		}
	}


	// 初始化下拉框
	initDrops();

	$("#charge_way").change(function() {
		// if(this.value=="02")
		// {
		// $("#charge_cycle_count").val("");
		// $("#charge_cycle_unit").val("");
		// $("#charge_cycle_count").attr("disabled","disabled");
		// $("#charge_cycle_unit").attr("disabled","disabled");
		// }else{
		// $("#charge_cycle_count").removeAttr("disabled");
		// $("#charge_cycle_unit").removeAttr("disabled");
		// }
	});

	// 初始化Table
	var oTable = new TableInit();
	oTable.Init();

	// 查询按钮
	$('#button_search').click(function() {
		$('#chargeTypeSetting').bootstrapTable('refresh', null);
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
	// 添加自定义校验
	var obj = $.validationEngineLanguage.allRules;
	obj["ajaxValidateTypeNo"] = {
		"url" : "./../ChargeManager/ChargeType.app?method=ajaxValidateTypeNo",
		"alertText" : "* 该编号已存在！",
		'alertTextOk' : '* ok',
	// "alertTextLoad": "* 验证中，请稍候..."
	};

	// 添加检验
	$('#myForm').validationEngine();
});

function btnDel() {
	var arr = $('#chargeTypeSetting').bootstrapTable('getSelections');
	if (arr.length == 0) {
		layer.alert("请选择一条记录操作", {
			skin : 'layui-layer-molv'
		});
		return;
	}

	layer.confirm("您确定要删除所选信息吗?", {
		skin : 'layui-layer-molv',
		btn : [ '确定', '取消' ]
	}, function() {
		del(arr);
		layer.closeAll('dialog');
	}, function() {
		return;
	});
}

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#chargeTypeSetting').bootstrapTable({
			url : './../ChargeManager/ChargeType.app?method=typeList', // 请求后台的URL（*）
			// toolbar: '#toolbar', //工具按钮用哪个容器
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
			// clickToSelect: true,
			queryParams : oTableInit.queryParams,
			showExport : "true",
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			showPaginationSwitch : false,
			pagination : true,
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 500, // 每页的记录行数（*）
			pageList : '[500]', // 可供选择的每页的行数（*）
			columns : [ {
				field : 'sta',
				checkbox : true
			}, {
				field : 'charge_type_no',
				title : '项目编号'
			}, {
				field : 'charge_type_name',
				title : '项目名称'
			}, {
				field : 'drop_charge_mode',
				title : '计费方式'
			}, {
				field : 'drop_charge_way',
				title : '收费模式'
			}, {
				field : 'drop_charge_type',
				title : '收费类型'
			}, {
				field : 'charge_cycle_count',
				title : '计费周期'
			}, {
				field : 'drop_charge_cycle_unit',
				title : '周期单位'
			}, {
				field : 'charge_price',
				title : '单价(元)',
				formatter : numberFormate
			}, {
				field : 'update_emp_id',
				title : '更新人'
			}, {
				field : 'update_date',
				title : '更新时间',
				formatter : dateFormate
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				events : operateEvents,
				formatter : operateFormatter
			} ]
		});
	};

	function operateFormatter(value, row, index) {

		return tableBtnInit();
	};

	// 操作列的事件
	window.operateEvents = {
		'click #a_check' : function(e, value, row, index) {
			edit(row, "check");
		},
		'click #a_edit' : function(e, value, row, index) {
			edit(row, "edit");
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
	numberFormate = function(value, row) {
		return outputmoney("" + value);
	};
	return oTableInit;
};

// 新增
function add() {
	$('#myForm').validationEngine('hide');
	$("#charge_type_no").removeAttr("disabled");

	$("#charge_type_name").removeAttr("disabled");
	$("#charge_mode").removeAttr("disabled");
	$("#charge_price").removeAttr("disabled");
	$("#charge_way").removeAttr("disabled");
	$("#charge_type").removeAttr("disabled");
	$("#type_flag").removeAttr("disabled");
	$("#type_flag").removeAttr("checked");

	$("#charge_type_id").val("");
	$("#charge_type_no").val("");
	$("#charge_type_name").val("");
	$("#charge_mode").val("");
	$("#charge_price").val("");
	$("#charge_way").val("");
	$("#charge_type").val("");
	$("#charge_cycle_count").val("");
	$("#charge_cycle_unit").val("");

	$('#button_save').show();
	$("#oper_type").val(1);
	$("#myModal").modal();
}

/* 编辑按钮，弹出对话框 */
function edit(obj, type) {
	var flag = $('#myForm').validationEngine('hide');
	$("#charge_type_no").attr("disabled", "true");
	$("#charge_type_name").attr("disabled", "true");

	if (type == "check") {
		$("#myModalTitle").html("详情");
		$('#button_save').hide();
		$("#charge_mode").attr("disabled", "true");
		$("#charge_price").attr("disabled", "true");
		$("#charge_way").attr("disabled", "true");
		$("#charge_type").attr("disabled", "true");
		$("#type_flag").attr("disabled", "true");

	} else {
		$("#myModalTitle").html("收费项编辑");
		$('#button_save').show();
		$("#charge_mode").removeAttr("disabled");
		$("#charge_price").removeAttr("disabled");
		$("#charge_way").removeAttr("disabled");
		$("#charge_type").removeAttr("disabled");
		$("#type_flag").removeAttr("disabled");

		$("#oper_type").val(2);
	}

	$("#charge_type_id").val(obj.charge_type_id);
	$("#charge_type_no").val(obj.charge_type_no);
	$("#charge_type_name").val(obj.charge_type_name);
	$("#charge_mode").val(obj.charge_mode);
	$("#charge_price").val(obj.charge_price);
	$("#charge_way").val(obj.charge_way);
	$("#charge_type").val(obj.charge_type);
	$("#charge_cycle_count").val(obj.charge_cycle_count);
	$("#charge_cycle_unit").val(obj.charge_cycle_unit);
	if (obj.type_flag == '01') {
		$('#type_flag')[0].checked = true;
	} else {
		$('#type_flag').removeAttr("checked");
	}
	$("#myModal").modal();
}

// 删除
function del(obj) {
	var selects = JSON.stringify(obj);
	var data = {
		delSelects : selects
	}
	$.ajax({
		type : "post",
		data : data,
		url : './../ChargeManager/ChargeType.app?method=delete',
		async : true,
		success : function(data) {
			$('#chargeTypeSetting').bootstrapTable('refresh', null);
			return;
		}
	});
}

// 保存 表单中的内容
function save() {
	var flag = $('#myForm').validationEngine('validate');
	var flagUniq = false;

	// 处理是否物业费
	var type_flag = '00';
	var tmp = $('#type_flag')[0].checked;
	if (tmp) {
		type_flag = '01';
	} else {
		type_flag = '00';
	}

	var type = $("#oper_type").val();
	if (type == 2) {
		if (flag) {
			var datas = {
				charge_type_id : $("#charge_type_id").val(),
				charge_type_no : $("#charge_type_no").val(),
				charge_type_name : $("#charge_type_name").val(),
				charge_mode : $("#charge_mode").val(),
				charge_price : parseFloat($("#charge_price").val()),
				charge_way : $("#charge_way").val(),
				charge_type : $("#charge_type").val(),
				charge_cycle_count : $("#charge_cycle_count").val(),
				type_flag : type_flag,
				charge_cycle_unit : $("#charge_cycle_unit").val()
			};

			$.ajax({
				type : "post",
				data : datas,
				url : './../ChargeManager/ChargeType.app?method=saveOrUpdate',
				async : true,
				success : function(data) {
					layer.msg('操作成功！', {
						time : 1000
					}, function() {
						$('#myModal').modal('hide');
						$('#chargeTypeSetting').bootstrapTable('refresh', null);
					});
				}
			});
		} else {
			layer.msg('表单验证未通过！', {
				time : 2000
			});
		}
		// 处理新增
	} else {
		$.ajax({
			type : "post",
			url : './../ChargeManager/ChargeType.app?method=ajaxValidateTypeNoSec',
			data : {
				fieldValue : $("#charge_type_no").val()
			},
			async : true,
			success : function(data) {
				if (data == "true") {
					flagUniq = true;
				} else {
					flagUniq = false;
				}

				if (flag && flagUniq) {
					var datas = {
						charge_type_id : $("#charge_type_id").val(),
						charge_type_no : $("#charge_type_no").val(),
						charge_type_name : $("#charge_type_name").val(),
						charge_mode : $("#charge_mode").val(),
						charge_price : parseFloat($("#charge_price").val()),
						charge_way : $("#charge_way").val(),
						charge_type : $("#charge_type").val(),
						charge_cycle_count : $("#charge_cycle_count").val(),
						type_flag : type_flag,
						charge_cycle_unit : $("#charge_cycle_unit").val()
					};

					$.ajax({
						type : "post",
						data : datas,
						url : './../ChargeManager/ChargeType.app?method=saveOrUpdate',
						async : true,
						success : function(data) {
							layer.msg('操作成功！', {
								time : 1000
							}, function() {
								$('#myModal').modal('hide');
								$('#chargeTypeSetting').bootstrapTable('refresh', null);
							});
						}
					});
				} else {
					layer.msg('表单验证未通过！', {
						time : 2000
					});
				}

			}
		});
	}

}

// 初始化下拉框
function initDrops() {
	var url = "./../ChargeManager/ChargeInfoList.app?method=initDropAll";
	$.post(url, function(data) {

		var list = eval(data);
		// alert(list);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			var code = detailList[0];
			// 收费类型
			if (code == 'charge_type') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#charge_type");
			}
			// 收费方式
			if (code == 'charge_way') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#charge_way");
			}
			// 计费方式
			if (code == 'charge_mode') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#charge_mode");
			}
			// 周期单位
			if (code == 'charge_cycle_unit') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#charge_cycle_unit");
			}

		}
		;
	});

}

// 添加jquery 校验
function addTypeNoValidate() {
	$.extend($.validationEngineLanguage.allRules, {
		"ajaxRegisterCheck" : {
			"url" : "/sys/bus.do?method=register_check",
			"extraData" : "dt=" + (new Date()).getTime(),
			"alertText" : "* 验证失败！",
			"alertTextLoad" : "* 验证中，请稍候..."
		}
	});

}


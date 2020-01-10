selections = [];
var loginUserName = "";
var loginUserCname = "";
var cxSelectApi;
var deptAndpos;
$(function() {
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	});
	$.get("./../user/userMaintain.app?method=initDropList", function(data) {
		deptAndpos = JSON.parse(data);
	});
	init();
	addValidateRule();// 增加校验规则
	$('#myForm1').validationEngine();
	initBtnEvent();
	initSearchEmpInfo();
	initDropList();

});

function addValidateRule() {
	var obj = $.validationEngineLanguage.allRules;
	obj["checkUsername"] = {
		"url" : "./../user/userMaintain.app",
		"extraData" : "method=checkUsername",
		"alertTextOk" : "* 验证通过",
		"alertText" : "* 用户已存在"
	};
	obj["checkRePwd"] = {
		"func" : function(field, rules, i, options) {
			return (field.val() == $("#password").val()) ? true : false;
		},
		"alertText" : "* 两次密码不一致"
	};
}

function initBtnEvent() {
	var urlmethod = "";
	$("#btn_add").bind('click', function() {
		$('#myForm1').validationEngine('hide');
		clearForm();
		$('#btUserAdd').show();

		$("#username").attr("disabled", false);
		$("#password").attr("disabled", false);
		$("#repassword").attr("disabled", false);

		$("#myModalTitle").html("添加用户信息");
		$('#myModal1').modal({
			backdrop : 'static',
			keyboard : false
		});

	});

	$("#btn_del").bind('click', function() {
		selections = getIdSelections();
		urlmethod = "method=deleteUser";
		layer.confirm("您确定要删除所选信息吗?", {
			skin : 'layui-layer-molv',
			btn : [ '确定', '取消' ]
		}, function() {
			var url = "./../user/userMaintain.app?" + urlmethod;
			$.post(url, {
				username : selections + ""
			}, function(data) {
				layer.msg(eval(data), {
					time : 2000
				}, function() {
					$('#userInfo').bootstrapTable('refresh');
				});
			});
		}, function() {
			return;
		})

	});

}

function init() {

	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#userInfo').bootstrapTable({
			url : './../user/userMaintain.app?method=list', // 请求后台的URL（*）
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
				field : 'username',
				title : '用户名'
			}, {
				field : 'cname',
				title : '中文名'
			}, {
				field : 'ename',
				visible : false,
				title : '英文名'
			}, {
				field : 'male',
				title : '性别',
				formatter : sexF
			}, {
				field : 'mobile',
				title : '手机'
			}, {
				field : 'birthday',
				title : '出生日期',
				formatter : dateFormate
			}, {
				field : 'email',
				visible : false,
				title : '邮箱'
			}, {
				field : 'deptName',
				title : '部门'
			}, {
				field : 'deptId',
				visible : false,
				title : '部门ID'
			}, {
				field : 'positionName',
				title : '岗位'
			}, {
				field : 'positionId',
				visible : false,
				title : '岗位ID'
			}, {
				field : 'roleName',
				title : '角色'
			}, {
				field : 'roleId',
				visible : false,
				title : '角色ID'
			}, {
				field : 'address',
				visible : false,
				title : '地址'
			}, {
				field : 'administrator',
				visible : false,
				title : '管理员'
			}, {
				field : 'companyId',
				visible : false,
				title : '公司ID'
			}, {
				field : 'createDate',
				title : '创建日期',
				formatter : dateFormate
			}, {
				field : 'enabled',
				title : '状态',
				formatter : stateFormate
			}, {
				field : 'operate',
				title : '操作',
				width : '15%',
				align : 'center',
				formatter : operateFormatter,
				events : operateEvents
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
		var html = '<a id="a_edit">编辑 <span style="color:#CCC">|</span> </a>' + '<a id="a_resetpw">重置密码 </a>';
		return html;
	}
	// 操作列的事件
	window.operateEvents = {
		'click #a_edit' : function(e, value, row, index) {
			edit(row);
		},
		'click #a_resetpw' : function(e, value, row, index) {
			resetPassword(row);
		}
	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : params.search
		// 表格搜索框的值
		};
		return temp;
	};
	dateFormate = function(value, type) {
		if (value == null || value == "") {
			return "";
		} else {
			return json2Date(value);
		}

	};
	isOrNot = function(value) {
		if (value == true) {
			return "是";
		} else {
			return "否";
		}
	};
	sexF = function(value) {
		if (value == true) {
			return "男";
		} else {
			return "女";
		}
	};
	stateFormate = function(value) {
		if (value == true) {
			return "启动";
		} else {
			return "停用";
		}
	};
	return oTableInit;

};
// 表格选择事件
function tableCheckEvents() {
	var r = $('#userInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
}

// 毫秒转时间YYYY-MM-DD hh:mm:ss
function json2TimeStamp(milliseconds) {
	var datetime = new Date();
	datetime.setTime(milliseconds);
	var year = datetime.getFullYear();
	// 月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}
// 毫秒转日期YYYY-MM-DD
function json2Date(milliseconds) {
	if (milliseconds) {
		var datetime = new Date();
		datetime.setTime(milliseconds);
		var year = datetime.getFullYear();
		// 月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
		var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
		var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
		return year + "-" + month + "-" + date;
	} else {
		return "";
	}
}

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($('#userInfo').bootstrapTable('getSelections'), function(row) {
		return row.username;
	});
}

/**
 * 保存操作
 */
function openSaveButton() {

	var flag = $('#myForm1').validationEngine('validate');
	var fieldId = "username";
	var fieldValue = $("#username").val();
	var username_ = $("#username_").val();
	$.ajax({
		type : "post",
		url : "./../user/userMaintain.app?method=checkUsername",
		data : {
			fieldId : fieldId,
			fieldValue : fieldValue
		},
		dataType : "json",
		async : false,
		success : function(data) {
			$('#myForm1').validationEngine('validate');
			if (data[1] == false && username_ == "") {
				layer.msg('表单验证未通过！', {
					time : 3000
				});
			} else {
				if (flag) {

					var addJson = getDataForm();
					$.ajax({
						type : "post",
						url : "./../user/userMaintain.app?method=save",
						data : addJson,
						dataType : "json",
						async : false,
						success : function(data) {
							if (data = "success") {
								layer.msg('保存成功！', {
									time : 2000
								}, function() {
									$('#userInfo').bootstrapTable('refresh');
									$('#myModal1').modal('hide');
								});
							} else {
								layer.msg('保存失败！', {
									time : 2000
								});
							}
						}
					});
				} else {
					layer.msg('表单验证未通过！', {
						time : 3000
					});
				}
			}
		}
	});
}

/**
 * 获取表单数据
 * 
 * @returns {___anonymous7092_7851}
 */
function getDataForm() {

	var addJson = {
		username_ : $("#username_").val(),
		username : $("#username").val(),
		password : $("#password").val(),
		cname : $("#cname").val(),
		ename : $("#ename").val(),
		email : $("#email").val(),
		male : $("input[name='male']:checked").val(),
		mobile : $("#mobile").val(),
		birthday : $("#birthday").val(),
		deptId : $("#dept").val(),
		positionId : $("#pos").val(),
		enabled : $("#enabled")[0].checked
	};

	return addJson;
}

// 清空表单
function clearForm() {
	$("#username_").val("");
	$("#username").val("");
	$("#password").val("");
	$("#repassword").val("");
	$("#cname").val("");
	$("#ename").val("");
	$("#email").val("");
	$("input[value='1']").iCheck('check');
	$("#mobile").val("");
	$("#birthday").val("");
	$("#roleName").val("");
	$('#enabled').iCheck('uncheck');

	$("#empInfo").attr("disabled", false);
	$("#dropdown").attr("disabled", false);
	$("#empInfo").val("");
	$(".role").hide();
	cxSelectApi.clear(0);
	cxSelectValue("0", deptAndpos);
}

// 查看和编辑
function edit(obj) {
	$('#myForm1').validationEngine('hide');
	$('#btUserAdd').show();
	$("#myModalTitle").html("修改");

	// 隐藏自动搜索窗口
	$("#empInfo").attr("disabled", true);
	$("#dropdown").attr("disabled", true);

	$("#username").attr("disabled", true);
	$("#roleName").attr("disabled", true);
	$("#password").attr("disabled", true);
	$("#repassword").attr("disabled", true);
	$("#username").val(obj.username);
	$("#username_").val(obj.username);// 隐藏的id
	$("#password").val(obj.password);
	$("#repassword").val(obj.password);
	$("#cname").val(obj.cname);
	$("#ename").val(obj.ename);
	$("#email").val(obj.email);
	$("#roleName").val(obj.roleName);
	$("#male").val(obj.male);
	if (obj.male == "1") {
		$("input[value='1']").iCheck('check');
	} else {
		$("input[value='0']").iCheck('check');
	}

	$("#mobile").val(obj.mobile);

	$("#roleId").val(obj.roleId);
	$("#birthday").val(json2Date(obj.birthday));
	if (obj.enabled == true) {
		$('#enabled').iCheck('check');
	} else {
		$('#enabled').iCheck('uncheck');
	}

	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});
	// 设置下拉菜单

	cxSelectApi.clear(0);
	var data = [ {
		v : obj.deptId,
		n : obj.deptName,
		s : [ {
			v : obj.positionId,
			n : obj.positionName
		} ]
	} ];
	cxSelectValue("1", data);
	cxSelectValue("0", deptAndpos);
}

// 重置密码
function resetPassword(row) {
	$.ajax({
		type : "post",
		url : "./../user/userMaintain.app?method=resetPassword",
		data : {
			"username" : row.username
		},
		dataType : "json",
		async : false,
		success : function(data) {
			layer.msg('密码重置为：' + data, {
				time : 4000
			}, function() {
				$('#userInfo').bootstrapTable('refresh');
			});
		}
	});
}

function initSearchEmpInfo() {
	var empInfo = $("input#empInfo").bsSuggest({
		url : "./../search.app?type=empInfo&keyword=",
		showHeader : true,
		allowNoKeyword : true,
		keyField : 'cname',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : [ "deptName", "posName", "cname", "mobile", "status" ],
		effectiveFieldsAlias : {
			deptName : "部门",
			posName : "岗位",
			cname : "姓名",
			mobile : "电话",
			status : "状态"
		},
		fnPreprocessKeyword : function(keyword) {
			// 请求数据前，对输入关键字作预处理
			return encodeURI(encodeURI(keyword));
		},
		processData : function(json) { // url 获取数据时，对数据的处理，作为 getData 的回调函数
			var index, len, data = {
				value : []
			};
			if (!json || !json.result || json.result.length === 0) {
				return false;
			}

			len = json.result.length;
			// alert(JSON.stringify(json.result[0][2]));
			for (index = 0; index < len; index++) {
				data.value.push({
					"cname" : json.result[index][0],
					"mobile" : json.result[index][1],
					"sex" : json.result[index][2],
					"deptId" : json.result[index][3],
					"deptName" : json.result[index][4],
					"posId" : json.result[index][5],
					"posName" : json.result[index][6],
					"brith" : json.result[index][7],
					"status" : json.result[index][8],
					"address" : json.result[index][9]
				});
			}
			// 字符串转化为 json 对象
			return data;
		}
	}).on("onDataRequestSuccess", function(e, result) {
		console.log("onDataRequestSuccess: ", result)
	}).on("onSetSelectValue", function(e, keyword, data) {
		console.log("onSetSelectValue: ", keyword, data);

		$("#username").val(data.mobile);
		$("#cname").val(data.cname);
		$("#mobile").val(data.mobile);
		$("#birthday").val(data.brith);
		if (data.sex == "1") {
			$("input[value='1']").iCheck('check');
		} else {
			$("input[value='0']").iCheck('check');
		}

		var data = [ {
			v : data.deptId,
			n : data.deptName,
			s : [ {
				v : data.posId,
				n : data.posName
			} ]
		} ];
		cxSelectApi.clear(0);
		cxSelectValue("1", data);
		cxSelectValue("0", deptAndpos);

	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});
}

// 设置多级下拉菜单值 type = 0 就是初始化全部值，type = 1 设置单值
function cxSelectValue(type, setData) {

	if (type == "1") {
		cxSelectApi.setOptions({
			data : setData,
			required: 1
		});
		$("#dept").val(setData[0].v);
	} else {
		cxSelectApi.setOptions({
			data : setData,
			required: 0
		});
	}
}
function initDropList() {
	// 初始化部门岗位下拉菜单
	$("#deptAndPos").cxSelect({
		selects : [ "dept", "pos" ],
		jsonName : 'n',
		jsonValue : 'v',
		jsonSub : 's',
		data : deptAndpos
	}, function(api) {
		cxSelectApi = api;
	})
}

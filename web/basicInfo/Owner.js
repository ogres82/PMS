selections = [];

$(function() {
	initPermissions();
	initDrops();
	init();
	addValidateRule();
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
	$('#myForm1').validationEngine();
	initBtnEvent();
	laydate.render({
		elem : '#birthDate',
		// format: 'YYYY年MM月DD日',
		festival : true, // 显示节日
		choose : function(datas) { // 选择日期完毕的回调
			$('#myForm1').validationEngine('hide');
		}
	});
	$('#buttonSearch').click(function() {
		$('#ownerInfo').bootstrapTable('refresh');
	});
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
				$('#btOwnerAdd').show();
				$("#ownerName").removeAttr("disabled");
				$("#phone").removeAttr("disabled");

				clearForm();
				$("#ownerIdentity").val("0");
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

				urlmethod = "method=deletePropertyOwner";

				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = "./../ownerInfo/ownerList.app?" + urlmethod;
					$.post(url, {
						ownerId : selections + ""
					}, function(data) {
						layer.msg(eval(data), {
							time : 2000
						}, function() {
							$('#ownerInfo').bootstrapTable('refresh');
						});
					});
				}, function() {
					return;
				});
			});
		}
	}
}

function addValidateRule() {
	var obj = $.validationEngineLanguage.allRules;
	obj["checkUniPhone"] = {
		"url" : "./../ownerInfo/ownerList.app",
		"extraData" : "method=checkUniPhone",
		"alertTextOk" : "* 验证通过",
		"alertText" : "* 号码已存在"
	};
}

function init() {
	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#ownerInfo').bootstrapTable({
			url : './../ownerInfo/ownerList.app?method=list', // 请求后台的URL（*）
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
				field : 'ownerId',
				visible : false,
				title : 'ID'
			}, {
				field : 'ownerName',
				title : '业主姓名'
			}, {
				field : 'phone',
				title : '手机号码'
			}, {
				field : 'telPhone',
				visible : false,
				title : '联系电话'
			}, {
				field : 'cardId',
				visible : false,
				title : '身份证号'
			}, {
				field : 'birthDate',
				title : '生日'
			}, {
				field : 'sex',
				title : '性别'
			}, {
				field : 'carId',
				title : '车牌号'
			}, {
				field : 'ownerTypeName',
				visible : false,
				title : '业主类型'
			}, {
				field : 'ownerIdentityName',
				title : '身份'
			}, {
				field : 'regState',
				title : '注册状态',
				formatter : regState
			}, {
				field : 'ownerAddrs',
				title : '绑定房产'
			}, {
				field : 'emergencyContact',
				visible : false,
			}, {
				field : 'emergencyContactPhone',
				visible : false,
			},  {
				field : 'remark',
				visible : false,
				title : '备注'
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				width : '10%',
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
	function regState(value, row, index) {
		if (value == '1') {
			return '已注册';
		}
		return '未注册';
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
			search : params.search,
			regState : $("#regStateSearch").val(),
			ownerIdentity : $("#ownerIdentitySearch").val()
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
	var r = $('#ownerInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
}

// 初始化下拉框
function initDrops() {
	var url = "./../ownerInfo/ownerList.app?method=initDropAll";
	$.post(url, function(data) {

		var list = eval(data);
		for (var j = 0; j < list.length; j++) {

			var detailList = list[j];
			var code = detailList[0];

			if (code == 'owner_type') {
				$(
						"<option value='" + detailList[1] + "'>"
								+ detailList[2] + "</option>").appendTo(
						"#ownerType");
			}
			if (code == 'owner_identity') {
				$(
						"<option value='" + detailList[1] + "'>"
								+ detailList[2] + "</option>").appendTo(
						"#ownerIdentity");
				$(
						"<option value='" + detailList[1] + "'>"
								+ detailList[2] + "</option>").appendTo(
						"#ownerIdentitySearch");
			}
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
	return $.map($('#ownerInfo').bootstrapTable('getSelections'),
			function(row) {
				return row.ownerId;
			});
}

/**
 * 保存操作
 */
function openSaveButton() {
	var flag = $('#myForm1').validationEngine('validate');
	var fieldId = "phone";
	var fieldValue = $("#phone").val();
	var phoneV = $("#phone_v").val();
	var ownerId = $("#ownerId").val();
	

	if(fieldValue==phoneV){
		var isChange = '0';//是否修改电话号码状态,0为未修改，1为修改
	}else{
		var isChange = '1';//是否修改电话号码状态,0为未修改，1为修改
	}

	$.ajax({
		type : "post",
		url : "./../ownerInfo/ownerList.app?method=checkUniPhone",
		data : {
			fieldId : fieldId,
			fieldValue : fieldValue
		},
		dataType : "json",
		async : false,
		success : function(data) {
			$('#myForm1').validationEngine('validate');
			if (data[1] == false && ownerId == "") {
				layer.msg('手机号码已存在！', {
					time : 3000
				});
			} else {
				if (flag) {

					var addJson = getDataForm();
					$.ajax({
						type : "post",
						url : "./../ownerInfo/ownerList.app?method=save&isChange="+isChange,
						data : addJson,
						dataType : "json",
						async : false,
						success : function(data) {
							layer.msg('操作成功！', {
								time : 2000
							}, function() {
								$('#ownerInfo').bootstrapTable('refresh');
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
		ownerId : $("#ownerId").val(),
		ownerName : $("#ownerName").val(),
		sex : $("#sex").val(),
		phone : $("#phone").val(),
		telPhone : $("#telPhone").val(),
		cardId : $("#cardId").val(),
		birthDate : $("#birthDate").val(),
		carId : $("#carId").val(),
		ownerType : $("#ownerType").val(),
		ownerIdentity : $("#ownerIdentity").val(),
		emergencyContact : $("#emergencyContact").val(),
		emergencyContactPhone : $("#emergencyContactPhone").val(),
		remark : $("#remark").val()
	};
	return addJson;
}

// 清空表单
function clearForm() {

	$("#ownerId").val("");
	$("#ownerName").val("");
	$("#phone").val("");
	$("#telPhone").val("");
	$("#sex").val("男");
	$("#cardId").val("");
	$("#birthDate").val("");
	$("#carId").val("");
	$("#ownerType").val("");
	$("#ownerIdentity").val("");
	$("#emergencyContact").val("")
	$("#emergencyContactPhone").val("")
	$("#remark").val("");
	$("#ownerAddrs").val("");
	
}
// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	//查看窗口赋值
	$("#ownerName_v").val(obj.ownerName);
	$("#sex_v").val(obj.sex);
	$("#phone_v").val(obj.phone);
	$("#telPhone_v").val(obj.telPhone);
	$("#cardId_v").val(obj.cardId);
	$("#birthDate_v").val(obj.birthDate);
	$("#carId_v").val(obj.carId);
	$("#ownerType_v").val(obj.ownerTypeName);
	$("#ownerIdentity_v").val(obj.ownerIdentityName);
	$("#emergencyContact_v").val(obj.emergencyContact);
	$("#emergencyContactPhone_v").val(obj.emergencyContactPhone);
	$("#remark_v").val(obj.remark);
	$("#family").html("");
	$("#ownerId").val(obj.ownerId);
	$("#ownerName").val(obj.ownerName);
	$("#sex").val(obj.sex);
	$("#phone").val(obj.phone);
	$("#telPhone").val(obj.telPhone);
	$("#cardId").val(obj.cardId);
	$("#birthDate").val(obj.birthDate);
	$("#carId").val(obj.carId);
	$("#ownerType").val(obj.ownerType);
	$("#ownerIdentity").val(obj.ownerIdentity);
	$("#oemergencyContact").val(obj.emergencyContact);
	$("#emergencyContactPhone").val(obj.emergencyContactPhone);
	$("#remark").val(obj.remark);
	if (type == 1) {
		$('#btOwnerAdd').hide();
		$("#myModalTitle").html("查看");
		var url = "./../ownerInfo/ownerList.app?method=getFamily&ownerId="
				+ obj.ownerId;
		$.post(url,
			function(data) {
				if (data != "") {
					var html = "";
					for (var i = 0; i < data.length; i++) {
						var ownerName = typeof (data[i].ownerName) == "undefined" ? "无姓名"
								: data[i].ownerName;
						var owner_identity_name = typeof (data[i].owner_identity_name) == "undefined" ? ""
								: data[i].owner_identity_name;
						var phone = typeof (data[i].phone) == "undefined" ? ""
								: data[i].phone;
						html += '<div class="feed-element">'
								+ '<div class="media-body ">'
								+ '<strong class="col-sm-2"><i class="fa fa-user"></i> '
								+ ownerName
								+ '</strong><strong class="col-sm-2">身份：'
								+ owner_identity_name
								+ '  </strong>'
								+ '<strong class="col-sm-3"><i class="fa fa-phone"></i> '
								+ phone + '</strong>' + '<br>'
								+ '</div>' + '</div>';
					}
					$("#family").html(html);
				} else {
					$("#family").html("无家属信息");
				}
			});

		$('#myModal2').modal({
			backdrop : 'static',
			keyboard : false
		});
	} else {
		$('#btOwnerAdd').show();
		$("#myModalTitle").html("编辑");
		//$("#phone").removeAttr("disabled");
		$('#myModal1').modal({
			backdrop : 'static',
			keyboard : false
		});
	}
}

function clearSearch() {
	$("#regStateSearch").val("");
	$("#ownerIdentitySearch").val("");	
	$('#ownerInfo').bootstrapTable('refresh');
}

function getBrith() {

	var cardId = $("#cardId").val() || "";
	var birthDate = "";
	if (cardId == "") {
		return;
	} else {
		if (cardId.length == 18) {
			birthDate = cardId.substring(6, 14);
		} else {
			birthDate = '19' + cardId.substring(6, 12)
		}
	}
	birthDate = birthDate.substring(0, 4) + "-" + birthDate.substring(4, 6)
			+ "-" + birthDate.substring(6, 8)
	$("#birthDate").val(birthDate);
}

selections = [];

$(function() {
	init();
	initDrops();
	initSearchHandler();
	initSearchCompHandler();
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

	imgInput("imgDiv", "./../assigenwork/assignlist.app?method=inputFile&folder=repair");
	initSearch();// 初始化搜索框
	$('#myForm1').validationEngine();
	$(".fancybox").fancybox();

	$('#buttonSearch').click(function() {
		$('#assignInfo').bootstrapTable('refresh');
	});

});

$(function() {
	toolbarBtnInit();// 初始化权限按钮
	for (var i = 0, len = btnIdArray.length; i < len; i++) {
		if (btnIdArray[i] == "btn1") {
			$("#btn1").bind('click', function() {
				clear_info();
				openButtonWindow(1);
			});
		} else if (btnIdArray[i] == "btn4") {

			$("#btn4").bind('click', function() {
				clear_info();
				// $('#myModal1').hide();
				openButtonWindow(4);
			});
		}
	}
	$("#btn4").attr("disabled", true);

});
/**
 * 刷新数据表单
 */
function buttonreresh() {
	$('#refresh').click();
}
// 控制输入字符
$(document).ready(function() {
	$("#mtn_detail").keydown(function() {
		var curLength = $("#mtn_detail").val().length;
		if (curLength >= 501) {
			var num = $("#mtn_detail").val().substr(0, 500);
			$("#mtn_detail").val(num);
			alert("超过字数限制，多出的字将被截断！");
		} else {
			$("#textCount").text(500 - $("#mtn_detail").val().length)
		}
	})
})

function init() {

	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#assignInfo').bootstrapTable({
			url : './../assigenwork/assignlist.app?method=listWork', // 请求后台的URL（*）
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
				field : 'rpt_kid',
				checkbox : true,
				align : 'center',
				valign : 'middle'
			}, {
				field : 'rpt_id',
				title : '事件单号',
				sortable : true,
				editable : true,
				align : 'center'
			}, {
				field : 'rpt_name',
				title : '报事人',
				sortable : true,
				editable : true,
				align : 'center'
			}, {
				field : 'event_source_name',
				title : '事件来源',
				align : 'center',
				valign : 'middle',
				sortable : true
			}, {
				field : 'event_type_name',
				title : '事件类别',
				sortable : true,
				align : 'center',
			}, {
				field : 'addres',
				title : '地址',
				align : 'center',
				valign : 'middle',
				sortable : true
			}, {
				field : 'createby_name',
				title : '受理人',
				align : 'center',
				valign : 'middle',
				sortable : true
			}, {
				field : 'event_state',
				title : '状态',
				sortable : true,
				editable : true,
				align : 'center',
				formatter : stateFormat
			}, {
				field : 'createTime',
				title : '受理时间',
				align : 'center',
				sortable : true
			}, {
				field : 'finishTime',
				title : '完成时间',
				align : 'center',
				valign : 'middle',
				sortable : true,
				visible : false
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				width : '15%',
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
				$("#btn4").attr("disabled", false);
			},
			onUncheckAll : function(rows) {
				$("#btn2").attr("disabled", true);
				$("#btn3").attr("disabled", true);
				$("#btn4").attr("disabled", true);
			}
		});
	};

	function operateFormatter(value, row, index) {
		var rpt_idStr = row.rpt_id.substring(0, 2);
		if (rpt_idStr == 'TS')// 咨询建议私有
		{
			var privateBtn = table_btn;
			var result = new Array();
			if (privateBtn.length > 3) {
				for (var i = 0; i < privateBtn.length; i++) {
					if (i > 1) {
						if (i == 2) {
							result.push('<div class="dropdown" style="display:inline"><a class="dropdown-toggle" data-toggle="dropdown" id="more" href="javascript:void(0)"> 更多<b class="caret"></b></a><ul class="dropdown-menu  m-t-xs">');

							if (privateBtn[i].btnName == '取消') {

							} else {
								result.push('<li><a id="' + table_btn[i].btnId + '">' + table_btn[i].btnName + '</a></li>');
							}
						} else {
							if (privateBtn[i].btnName != '取消') {
								result.push('<li><a id="' + table_btn[i].btnId + '">' + table_btn[i].btnName + '</a></li>');
							}

						}
					} else {
						result.push('<a id="' + table_btn[i].btnId + '">' + table_btn[i].btnName + ' <span style="color:#CCC">|</span> </a>');
					}
				}
				result.push('</ul></div>');
			} else {
				for (var i = 0; i < privateBtn.length; i++) {
					if (privateBtn[i].btnId == 'a_send' || privateBtn[i].btnId == 'a_check' || privateBtn[i].btnId == 'a_edit' || privateBtn[i].btnId == 'a_handle') {
						if (i == privateBtn.length - 2) {
							result.push('<a id="' + privateBtn[i].btnId + '">' + privateBtn[i].btnName + ' </a>');
						} else {
							result.push('<a id="' + privateBtn[i].btnId + '">' + privateBtn[i].btnName + '</a> <span style="color:#CCC">|</span> ');
						}
					}

				}
			}
			return result.join('');
		} else {
			var result = new Array();
			var privateBtn = table_btn;
			if (privateBtn.length > 3) {
				for (var i = 0; i < privateBtn.length; i++) {
					if (i > 1) {
						if (i == 2) {
							result.push('<div class="dropdown" style="display:inline"><a class="dropdown-toggle" data-toggle="dropdown" id="more" href="javascript:void(0)"> 更多<b class="caret"></b></a><ul class="dropdown-menu  m-t-xs">');
							result.push('<li><a id="' + privateBtn[i].btnId + '">' + privateBtn[i].btnName + '</a></li>');

						} else {
							if (privateBtn[i].btnName != '处理') {
								result.push('<li><a id="' + privateBtn[i].btnId + '">' + privateBtn[i].btnName + '</a></li>');
							}

						}
					} else {
						result.push('<a id="' + privateBtn[i].btnId + '">' + privateBtn[i].btnName + ' <span style="color:#CCC">|</span> </a>');
					}
				}
				result.push('</ul></div>');
			} else {
				for (var i = 0; i < table_btn.length; i++) {
					if (i == table_btn.length - 1) {
						result.push('<a id="' + privateBtn[i].btnId + '">' + privateBtn[i].btnName + ' </a>');
					} else {
						result.push('<a id="' + privateBtn[i].btnId + '">' + privateBtn[i].btnName + '</a> <span style="color:#CCC">|</span> ');
					}
				}
			}

			return result.join('');

		}
	}
	// 操作列的事件
	window.operateEvents = {

		'click #a_check' : function(e, value, row, index) {

			$("#handleId").show();
			$("#handleIds").show();

			$('#dispatch_handle_username').attr("disabled", "true");
			$('#comp_operator_username').attr("disabled", "true");
			show(row, 3)
		},
		'click #a_send' : function(e, value, row, index) {
			$("#dispatch_handle_username").attr("disabled", false);
			$("#handleId").show();
			$("#handleIds").show();// 新加
			$("#custmReply").hide();
			show(row, 1)
		},
		'click #a_edit' : function(e, value, row, index) {
			$("#custmReply").hide();
			show(row, 2)
		},
		'click #a_cancel' : function(e, value, row, index) {
			$("#handleId").hide();
			$("#custmReply").hide();
			show(row, 4)
		},
		'click #a_handle' : function(e, value, row, index)// 处理
		{

			$("#custmReply").show();
			show(row, 5)
		}

	};
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : encodeURI(params.search),
			businessFromSearch : $("#businessFromSearch").val(),
			eventTypeSearch : $("#eventTypeSearch").val()
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
	var r = $('#assignInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn2").attr("disabled", true);
		$("#btn3").attr("disabled", true);
		$("#btn4").attr("disabled", true);
		$("#btn5").attr("disabled", true);
	}
	if (r.length == 1) {
		var head = r[0].rpt_id.substring(0, 2);
		if (head == "TS") {
			$("#btn5").attr("disabled", false);
		} else {
			$("#btn5").attr("disabled", true);
		}
		$("#btn2").attr("disabled", false);
		$("#btn3").attr("disabled", false);
		$("#btn4").attr("disabled", false);

	}
	if (r.length > 1) {
		$("#btn2").attr("disabled", true);
		$("#btn3").attr("disabled", true);
		$("#btn5").attr("disabled", true);
	}
}

/* 搜索框初始化 */
function initSearch() {
	var addressSuggest = $("input#searchInput").bsSuggest({
		url : encodeURI("./../search.app?type=owner&keyword="),
		showHeader : true,
		allowNoKeyword : true,
		keyField : 'roomNo',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : [ "addres", "roomNo", "ownerName", "phone" ],
		effectiveFieldsAlias : {
			roomNo : "房间号",
			ownerName : "业主名",
			phone : "手机号",
			addres : "地址"
		},
		// 预处理
		fnPreprocessKeyword : function(keyword) {
			// 请求数据前，对输入关键字作预处理
			return encodeURI(encodeURI(keyword));
		},
		processData : function(json) { // url 获取数据时，对数据的处理，作为 getData
			// 的回调函数
			var index, len, data = {
				value : []
			};
			if (!json || !json.result || json.result.length === 0) {
				return false;
			}

			len = json.result.length;

			for (index = 0; index < len; index++) {
				data.value.push({
					"searchInput" : json.result[index][1] + "-" + json.result[index][3] + "-" + json.result[index][5] + "（" + json.result[index][7] + "）",
					"roomId" : json.result[index][4],
					"ownerName" : json.result[index][7],
					"ownerId" : json.result[index][6],
					"phone" : json.result[index][8],
					"roomNum" : json.result[index][5],
					"addres" : json.result[index][1] + "-" + json.result[index][3] + "-" + json.result[index][5],
					"roomNo" : json.result[index][5]
				});
			}
			// 字符串转化为 json 对象

			return data;
		}
	}).on("onDataRequestSuccess", function(e, result) {
		console.log("onDataRequestSuccess: ", result)
	}).on("onSetSelectValue", function(e, keyword, data) {
		console.log("onSetSelectValue: ", keyword, data);
		// alert(JSON.stringify(data));
		$("#ownerId").val(data.ownerId);
		$("#rpt_name").val(data.ownerName);
		$("#roomId").val(data.roomId);
		$("#roomNo").val(data.roomNum);
		$("#addres").val(data.addres);
		$("#in_call").val(data.phone);
		$("#searchInput").val(data.searchInput);
	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});

}

// 新增列表里面的查询和编辑
function show(obj, type) {

	$('#myForm1').validationEngine('hide');
	var rpt_id = obj.rpt_id;
	var rpt_idStr = rpt_id.substring(0, 2);
	var urlmethod = "";
	var ts_type = "";
	if (type == 1) {
		if (obj.event_state == 0) {
			$("#ownerSearch").hide();
			$("#btAssigenHandle").hide();
			$("#btDisPatchHandle").show();
			$('#btAssigenAdd').hide();
			$("#dispbtn9").hide();
			$("#dispbtn8").hide();

			ts_type = "1";
			disableEdit();
			if (rpt_idStr == 'TS') {
				$("#comp_operator_username").show();
				$("#comp_operator_username").attr("disabled", false);
				$('#comp_emergency').attr("disabled", 'true');
				// $("#handleId").show();
			}
			clear_info();
			$('#comp_status').attr("disabled", "true");
			urlmethod = "method=viewAssign&assignId=" + rpt_id;
		} else {
			layer.alert("该工单不在派工环节");
			return;
		}

	} else if (type == 2) {
		// 修改
		if (obj.event_state == 0) {
			if (obj.event_source == "0") {
				$("#ownerSearch").hide();
				$("#btAssigenHandle").hide();
				$("#btDisPatchHandle").hide();
				$('#btAssigenAdd').show();
				$("#dispbtn8").hide();
				$("#dispbtn9").hide();
				$('#comp_emergency').attr("disabled", 'true');
				ableEdit();
				clear_info();
				$('#comp_status').attr("disabled", "true");
				urlmethod = "method=viewAssign&assignId=" + rpt_id;
			} else {
				$('#event_source').attr("disabled", true);
				$('#event_type').attr("disabled", true);
				$('#rpt_name').attr("disabled", true);
				$('#in_call').attr("disabled", true);
				$('#addres').attr("disabled", true);
				$('#mtn_detail').attr("disabled", false);
				$('#mtn_emergency').attr("disabled", false);
				$('#comp_detail').attr("disabled", false);
				$('#comp_reply').attr("disabled", false);
				// $('#comp_emergency').attr("disabled",false);
				$('#comp_status').attr("disabled", false);
				$("#ownerSearch").hide();
				$("#btAssigenHandle").hide();
				$("#btDisPatchHandle").hide();
				$('#btAssigenAdd').show();
				$("#dispbtn8").hide();
				$("#dispbtn9").hide();
				$('#comp_emergency').attr("disabled", 'true');
				clear_info();
				$('#comp_status').attr("disabled", "true");
				urlmethod = "method=viewAssign&assignId=" + rpt_id;
			}

		} else {
			layer.alert("该工单不可编辑，只有未派工可编辑");
			return;
		}

	} else if (type == 3) {

		$('#dispatch_handle_username').attr("disabled", "true");
		$("#btAssigenHandle").hide();
		$('#btAssigenAdd').hide();
		$("#ownerSearch").hide();
		$("#btDisPatchHandle").hide();
		$("#dispbtn8").hide();
		$("#dispbtn9").hide();
		disableEdit();
		urlmethod = "method=viewAssign&assignId=" + rpt_id;
	} else if (type == 4)// 取消
	{
		var rpt_idStr = rpt_id.substring(0, 2);
		if (rpt_idStr == 'TS') {
			layer.alert("咨询建议不可取消");
			return;

		}
		$("#btAssigenHandle").hide();
		$('#btAssigenAdd').hide();
		$("#ownerSearch").hide();
		$("#btDisPatchHandle").hide();
		$("#dispbtn9").hide();
		$("#dispbtn8").show();
		disableEdit();
		urlmethod = "method=viewAssign&assignId=" + rpt_id;
	} else if (type == 5)// 处理
	{

		$("#btAssigenHandle").hide();
		$('#btAssigenAdd').hide();
		$("#ownerSearch").hide();
		$("#btDisPatchHandle").hide();
		$("#dispbtn9").show();
		$("#dispbtn8").hide();
		$("#handleIds").hide();
		disableEdit();
		ts_type = "0";
		urlmethod = "method=viewAssign&assignId=" + rpt_id;
	}

	var url = "./../assigenwork/assignlist.app?" + urlmethod;
	$.post(url, function(data) {
		var list = eval(data);
		var event_type_data = list.event_type;
		var processInstanceId = list.processInstanceId;
		changeEventType(event_type_data, processInstanceId);
		nameFormatCName("createby", list.createby);
		$("#createTime").val(getNowFormatDate(false, list.createTime));
		$("#rpt_kid").val(list.rpt_kid);
		$("#rpt_id").val(list.rpt_id);
		$("#rpt_name").val(list.rpt_name);
		$("#in_call").val(list.in_call);
		$("#addres").val(list.address);
		$("#owner_type").val(list.owner_type);
		$("#event_source").val(list.event_source);
		$("#event_time").val(list.event_time);
		$("#event_type").val(event_type_data);

		// 报障
		$("#mtn_detail").val(list.mtn_detail);		
		showstatic(list.mtn_detail);
		$("#comp_reply").val(list.comp_reply);
		$("#mtn_emergency").val(list.mtn_emergency);
		$("#dispatch_status").val(list.dispatch_status);

		$("#ownerId").val(list.owner_id);
		$("#roomId").val(list.owner_house);
		$("#roomNo").val(list.roomNo);
		// 投诉
		$("#comp_status").val(list.comp_status);
		if (ts_type == '0') {
			$("#comp_emergency").val("0");
		} else {
			$("#comp_emergency").val("1");
		}
		$("#comp_detail").val(list.comp_detail);
		if (list.comp_reply == 'null') {
			$("#comp_reply").val("");
		} else {
			$("#comp_reply").val(list.comp_reply);
		}
		$("#dispatch_finish_time").val(getNowFormatDate(false, list.finishTime));

		$("#imgDiv").fileinput('clear');
		$("#uploadQuaDiv").hide();
		// 展示图片
		initEventImg(list.rpt_id)

	});
	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});

}
// 清空上一次访问的数据
function clear_info() {
	$("#createTime").val("");
	$("#rpt_kid").val("");
	$("#rpt_id").val("");
	$("#rpt_name").val("");
	$("#in_call").val("");
	$("#addres").val("");
	$("#owner_type").val("");
	$("#event_source").val("");
	$("#event_time").val("");
	$("#event_type").val("");

	$("#dispatch_handle_id").val("");
	$("#dispatch_handle_username").val("");
	// 报障
	$("#mtn_detail").val("");
	$("#comp_reply").val("");
	$("#mtn_emergency").val("");
	$("#dispatch_status").val("");
	$("#img_url").val("");
	$("#imgDiv").fileinput('clear');
	$("#uploadQuaDiv").show();
	// 投诉

	$("#comp_status").val("");
	$("#comp_operator_username").val("");
	$("#comp_detail").val("");
	$("#dispatch_finish_time").val("");

}

/**
 * 前排按钮监控
 * 
 * @param type
 */
function openButtonWindow(type) {
	$('#myForm1').validationEngine('hide');
	$('#btAssigenAdd').removeAttr("disabled");
	var isOpen = false;
	selections = getIdSelections();
	var length = selections.length;
	if (type == 1 || type == 4) {
		isOpen = true;
	} else {
		if (length == 1) {
			isOpen = true;
		} else {
			if (length == 0) {
				layer.alert("请选择一条记录操作", {
					skin : 'layui-layer-molv'
				});
			} else {
				layer.alert("只能选择一条记录操作", {
					skin : 'layui-layer-molv'
				});
			}

		}
	}

	if (isOpen) {
		var rpt_id = 0;
		selections = getIdSelections();
		rpt_id = selections[0];
		var urlmethod = "";
		// 新增
		if (type == 1) {
			$('#myModal1').modal({
				backdrop : 'static',
				keyboard : false
			});
			urlmethod = "method=addAssign";
			$("#dispatch_status").val(0);
			$("#btAssigenAdd").show();
			$("#dispbtn8").hide();
			$("#dispbtn9").hide();
			$("#btAssigenHandle").hide();
			$("#searchInput").val("");
			$("#ownerSearch").show();
			$("#custmReply").hide();
			$("#comp_emergency").attr("disabled", false);
			ableEdit();
		} else if (type == 2) {
			$('#myModal1').modal({
				backdrop : 'static',
				keyboard : false
			});
			// 修改
			$("#ownerSearch").hide();
			$("#btAssigenHandle").hide();

			ableEdit();
			$('#comp_status').attr("disabled", "true");
			urlmethod = "method=viewAssign&assignId=" + rpt_id;
		} else if (type == 3 || type == 5) {
			$('#myModal1').modal({
				backdrop : 'static',
				keyboard : false
			});
			// 查看
			if (type == 3) {
				$("#btAssigenHandle").hide();
				$('#btAssigenAdd').attr("disabled", "true");
				$("#ownerSearch").hide();
				disableEdit();
			} else {
				$("#btAssigenHandle").show();
				$("#btAssigenAdd").hide();
				$("#ownerSearch").hide();
			}
			urlmethod = "method=viewAssign&assignId=" + rpt_id;
		} else if (type == 4) {
			// 删除
			urlmethod = "method=deleteAssign";
		}
		if (type == 4) {
			layer.confirm("您确定要删除所选信息吗?", {
				skin : 'layui-layer-molv',
				btn : [ '确定', '取消' ]
			}, function() {
				var url = "./../assigenwork/assignlist.app?" + urlmethod;
				var deleteIds = JSON.stringify(selections);
				$.post(url, {
					assignId : deleteIds
				}, function(data) {
					layer.alert(data, {
						skin : 'layui-layer-molv' // 样式类名
						,
						closeBtn : 0
					}, function() {
						var locationUrl = "./../assignwork/AssignWorkList.jsp";
						window.location.href = locationUrl;
					});
				});
			}, function() {
				return;
			})
		} else {
			var url = "./../assigenwork/assignlist.app?" + urlmethod;
			$.post(url, function(data) {
				var list = eval(data);
				var event_type_data = list.event_type;
				var processInstanceId = list.processInstanceId;
				changeEventType(event_type_data, processInstanceId);
				nameFormatCName("createby", list.createby);
				$("#createTime").val(getNowFormatDate(false, list.createTime));
				$("#rpt_kid").val(list.rpt_kid);
				$("#rpt_id").val(list.rpt_id);
				$("#rpt_name").val(list.rpt_name);
				$("#in_call").val(list.in_call);
				$("#addres").val(list.address);
				$("#owner_type").val(list.owner_type);
				$("#event_source").val(list.event_source);
				$("#event_time").val(list.event_time);
				$("#event_type").val(event_type_data);
				// 报障
				$("#mtn_detail").val(list.mtn_detail);
				$("#comp_reply").val(list.comp_reply);
				$("#mtn_emergency").val(list.mtn_emergency);
				$("#dispatch_status").val(list.dispatch_status);
				// 投诉

				$("#comp_status").val(list.comp_status);
				$("#comp_emergency").val(list.comp_emergency);
				$("#comp_detail").val(list.comp_detail);
				$("#dispatch_finish_time").val(getNowFormatDate(false, list.finishTime));

				// 展示图片
				initEventImg(list.rpt_id)

			});
			selections = getIdSelections();
		}

	}
}

// 查看设置
function disableEdit() {
	$('#event_source').attr("disabled", "true");
	$('#event_type').attr("disabled", "true");
	$('#rpt_name').attr("disabled", "true");
	$('#in_call').attr("disabled", "true");
	$('#addres').attr("disabled", "true");
	$('#mtn_detail').attr("disabled", "true");
	$('#mtn_emergency').attr("disabled", "true");
	$('#comp_detail').attr("disabled", "true");
	$('#comp_status').attr("disabled", "true");
}
// 新增和编辑
function ableEdit() {
	$('#event_source').attr("disabled", false);
	$('#event_type').attr("disabled", false);
	$('#rpt_name').attr("disabled", false);
	$('#in_call').attr("disabled", false);
	$('#addres').attr("disabled", false);
	$('#mtn_detail').attr("disabled", false);
	$('#mtn_emergency').attr("disabled", false);
	$('#comp_detail').attr("disabled", false);
	$('#comp_reply').attr("disabled", false);
	$('#comp_status').attr("disabled", false);

}

function openSaveButton() {
	$("#dispatch_handle_id").removeClass("form-control validate[required]");
	$("#dispatch_handle_id").addClass("form-control");
	$("#dispatch_handle_username").removeClass("form-control validate[required]");
	$("#dispatch_handle_username").addClass("form-control");

	$("#comp_operator_id").removeClass("form-control validate[required]");
	$("#comp_operator_id").addClass("form-control");
	$("#comp_operator_username").removeClass("form-control validate[required]");
	$("#comp_operator_username").addClass("form-control");

	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = getDataForm();
		initBlockUI();
		$.ajax({
			type : "post",
			url : "./../assigenwork/assignlist.app?method=save",
			data : addJson,
			dataType : "json",
			async : true,
			success : function(data) {
				$.unblockUI();
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#assignInfo').bootstrapTable('refresh');
					$('#myModal1').modal('hide');
				});
				// closeLayer();

			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！请联系技术人员。', {
					time : 2000
				}, function() {
					$('#myModal1').modal('hide');
				})
			}
		});
	}
}

// 咨询建议直接处理
function openHandleButton() {
	var addJson = getDataForm();
	initBlockUI();
	$.ajax({
		type : "post",
		url : "./../assigenwork/assignlist.app?method=handle",
		data : addJson,
		dataType : "json",
		async : true,
		success : function(data) {
			$.unblockUI();
			layer.msg('操作成功！', {
				time : 2000
			}, function() {
				$('#assignInfo').bootstrapTable('refresh');
				$('#myModal1').modal('hide');
			});
			// closeLayer();

		},
		failure : function(xhr, msg) {
			layer.msg('操作失败！请联系技术人员。', {
				time : 2000
			}, function() {
				$('#myModal1').modal('hide');
			})
		}
	});

}

function getDataForm() {
	var addJson = {
		createTime : $("#createTime").val(),
		rpt_kid : $("#rpt_kid").val(),
		rpt_id : $("#rpt_id").val(),
		rpt_kid : $("#rpt_kid").val(),
		rpt_name : $("#rpt_name").val(),
		in_call : $("#in_call").val(),
		addres : $("#addres").val(),
		owner_type : $("#owner_type").val(),
		event_source : $("#event_source").val(),
		event_time : $("#event_time").val(),
		event_type : $("#event_type").val(),

		ownerId : $("#ownerId").val(),
		roomId : $("#roomId").val(),
		roomNum : $("#roomNo").val(),
		// 报修
		mtn_detail : $("#mtn_detail").val(),
		mtn_emergency : $("#mtn_emergency").val(),
		dispatch_status : $("#dispatch_status").val(),
		comp_reply : $("#comp_reply").val(),
		img_url : $("#img_url").val(),
		// 投诉
		comp_status : $("#comp_status").val(),
		comp_emergency : $("#comp_emergency").val(),
		comp_detail : $("#comp_detail").val()
	};
	return addJson;
}

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($('#assignInfo').bootstrapTable('getSelections'), function(row) {
		return row.rpt_id
	});
}

// 人的中文名字显示
function nameFormatCName(cName, value) {
	// alert(value);
	var addJson = {
		userName : value
	};
	$.ajax({
		type : "post",
		url : "./../assigenwork/assignlist.app?method=nameformat",
		data : addJson,
		dataType : "json",
		async : false,
		success : function(data) {
			$("#" + cName).val(data);
		},
		failure : function(xhr, msg) {

		}
	});
}

// 状态转换
function stateFormat(value, row, index) {

	if (value == 0) {
		return [ '<span class="label label-danger">未派工</span>' ].join('');
	} else if (value == 1) {

		return [ '<span class="label label-primary">已派工</span>' ].join('');
	} else if (value == 2) {

		return [ '<span class="label label-primary">处理中</span>' ].join('');
	} else if (value == 3) {
		return [ '<span class="label label-warning">待回访</span>' ].join('');

	} else if (value == 4) {

		return [ '已回访' ].join('');
	} else if (value == 5) {

		return [ '<span class="label label-success">已归档</span>' ].join('');
	}

}

// 时间显示
function timeFormatCreateTime(value, row, index) {
	var timeFormant = "-";
	if (null == value || value == '') {

	} else {
		timeFormant = getNowFormatDate(false, value);
	}
	return [ timeFormant ].join('');
}

function getNowFormatDate(flag, vardate) {
	if (flag == false && (vardate == null || vardate == ''))
		return "-";
	var date;
	if (flag == true) {
		date = new Date();
	} else {
		date = new Date(vardate);
	}
	var seperator1 = "-";
	var seperator2 = ":";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	var hour = date.getHours(); // 获取当前小时数(0-23)
	var min = date.getMinutes(); // 获取当前分钟数(0-59)
	var sec = date.getSeconds(); // 获取当前秒数(0-59)
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	if (hour >= 1 && hour <= 9) {
		hour = "0" + hour;
	}
	if (min >= 0 && min <= 9) {
		min = "0" + min;
	}
	if (sec >= 0 && sec <= 9) {
		sec = "0" + sec;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate + " " + hour + seperator2 + min + seperator2 + sec;
	return currentdate;
}

// 初始化下拉框
function initDrops() {
	var url = "./../assigenwork/assignlist.app?method=initDropAll";
	$.post(url, function(data) {

		var list = eval(data);
		// alert(list);
		for (var j = 0; j < list.length; j++) {
			// alert(list[j].main_event_source+"==="+list[j].main_event_type);
			var detailList = list[j];
			var code = detailList[0];
			// 事件类型
			if (code == 'main_event_type') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#event_type");
			}

			// 事件来源
			if (code == 'main_event_source') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#event_source");
			}

			// mtn_emergency 紧急程度
			// 投诉基本,一样
			if (code == 'main_mtn_emergency') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#mtn_emergency");
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#comp_emergency");

			}

			// dispatch_status 派工单状态
			if (code == 'main_mtn_dispatch_status') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#dispatch_status");
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#comp_status");
			}

		}
		;
	});
	// return rtnValue;
}

/**
 * 事件类型下拉框隐藏
 * 
 * @param index
 */
function changeEventType(index, processInstanceId) {
	var grapIframe = "";
	if (null == processInstanceId || processInstanceId == '' || processInstanceId == undefined || processInstanceId == 'undefined') {
		if ($("#event_type").val() == "0" || index == "0") {
			grapIframe = "./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=507501&numb=" + generateMixed(4);
		} else {
			grapIframe = "./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=217557&numb=" + generateMixed(4);
		}
	} else {
		grapIframe = "./../graph/graphProcessDefinition.app?processDefinitionId=" + processInstanceId + "&numb=" + generateMixed(4);
	}

	if ($("#event_type").val() == "0" || index == "0") {
		$("#bxdetail").show();
		$("#btSendHandle").show();
		$("#tsdetail").hide();
		$("#mtn_emergency").val("");
		$("#imgDiv").fileinput('clear');
		$("#uploadQuaDiv").show();
	} else {
		$("#dispatch_handle_id").removeClass("form-control validate[required]");
		$("#dispatch_handle_id").addClass("form-control");
		$("#dispatch_handle_username").removeClass("form-control validate[required]");
		$("#dispatch_handle_username").addClass("form-control");
		$("#bxdetail").hide();
		$("#btSendHandle").hide();
		$("#tsdetail").show();
		$("#comp_emergency").val("");
		$("#imgDiv").fileinput('clear');
		$("#uploadQuaDiv").hide();

	}

	$("#grapIframe").attr("src", grapIframe);

}
var chars = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ];

function generateMixed(n) {
	var res = "";
	for (var i = 0; i < n; i++) {
		var id = Math.ceil(Math.random() * 35);
		res += chars[id];
	}
	return res;
}

/**
 * 操作成功后2秒自动关闭
 * 
 * @param layerName
 */
function closeLayer() {
	setTimeout(function() {// IE6、7不会提示关闭
		$('#myModal1').modal('hide'); // 执行关闭
		// buttonreresh();
	}, 2000);
}

// function initEventImg(rpt_id) {
// var html = [];
// var url = "./../assigenwork/assignlist.app?method=getEventImg&rpt_id="
// + rpt_id;
// $.post(url, function(data) {
// var list = eval(data);
// for (var i = 0; i < list.length; i++) {
//
// html.push("<a class='fancybox' href='" + list[i].img_url
// + "' title='" + list[i].event_id + "'>"
// + "<img alt='image' src='" + list[i].img_url + "'/></a>");
// }
// var img = html.join("");
// $("#eventImgShow").empty();// 每一次请求，先把上一次的清空
// $("#eventImgShow").append(img);
//
// });
//
// }

function initEventImg(rpt_id) {
	var html = [];
	var url = "./../assigenwork/assignlist.app?method=getEventImg&rpt_id=" + rpt_id;
	$.post(url, function(data) {
		var list = eval(data);
		for (var i = 0; i < list.length; i++) {
			html.push("<a class='fancybox' href='" + list[i].img_url + "' title='" + list[i].event_id + "'><img alt='img' src='" + list[i].img_url + "' onload='AutoResizeImage(0,150,this)'/></a>");
		}
		var img = html.join("");
		$("#eventImgShow").empty();// 每一次请求，先把上一次的清空
		$("#eventImgShow").append(img);
	});
}

function AutoResizeImage(maxWidth, maxHeight, objImg) {
	var img = new Image();
	img.src = objImg.src;
	var hRatio;
	var wRatio;
	var Ratio = 1;
	var w = img.width;
	var h = img.height;
	wRatio = maxWidth / w;
	hRatio = maxHeight / h;
	if (maxWidth == 0 && maxHeight == 0) {
		Ratio = 1;
	} else if (maxWidth == 0) {//
		if (hRatio < 1)
			Ratio = hRatio;
	} else if (maxHeight == 0) {
		if (wRatio < 1)
			Ratio = wRatio;
	} else if (wRatio < 1 || hRatio < 1) {
		Ratio = (wRatio <= hRatio ? wRatio : hRatio);
	}
	if (Ratio < 1) {
		w = w * Ratio;
		h = h * Ratio;
	}
	objImg.height = h;
	objImg.width = w;
}

// 直接派工，未保存
function openSendDisPatchButton() {
	$("#dispatch_handle_id").removeClass("form-control ");
	$("#dispatch_handle_id").addClass("form-control validate[required]");
	$("#dispatch_handle_username").removeClass("form-control ");
	$("#dispatch_handle_username").addClass("form-control validate[required]");

	$("#comp_operator_id").removeClass("form-control ");
	$("#comp_operator_id").addClass("form-control validate[required]");
	$("#comp_operator_username").removeClass("form-control ");
	$("#comp_operator_username").addClass("form-control validate[required]");
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = sendDispatchForm();
		if (addJson == '1') {
			layer.alert("请选择投诉级别为紧急或重大");
			return;
		} else {
			initBlockUI();
			$.ajax({
				type : "post",
				url : "./../assigenwork/directDispatch.app?method=directDispatch",
				data : addJson,
				dataType : "json",
				async : true,
				success : function(data) {
					$.unblockUI();
					layer.msg(data + '操作成功！', {
						time : 2000
					}, function() {
						$('#assignInfo').bootstrapTable('refresh');
						$('#myModal1').modal('hide');
					});
				},
				failure : function(xhr, msg) {
					layer.msg('操作失败！请联系技术人员。', {
						time : 2000
					}, function() {
						$('#myModal1').modal('hide');
					})
				}
			});

		}
	}
}

function sendDispatchForm() {
	rpt_id = $("#rpt_id").val();
	comp_emergency = $("#comp_emergency").val();
	event_type = $("#event_type").val();
	if (event_type == '1') {
		var rpt_idStr = rpt_id.substring(0, 2);
		if (comp_emergency == '0') {
			return "1";
		} else {

			addJson = {
				rpt_id : $("#rpt_id").val(),
				rpt_name : $("#rpt_name").val(),
				in_call : $("#in_call").val(),
				addres : $("#addres").val(),
				owner_type : $("#owner_type").val(),
				event_source : $("#event_source").val(),
				event_time : $("#event_time").val(),
				event_type : $("#event_type").val(),
				ownerId : $("#ownerId").val(),
				roomId : $("#roomId").val(),
				roomNum : $("#roomNo").val(),
				// 报修
				mtn_detail : $("#mtn_detail").val(),
				mtn_emergency : $("#mtn_emergency").val(),
				dispatch_status : $("#dispatch_status").val(),
				comp_reply : $("#comp_reply").val(),
				dispatch_handle_id : $("#comp_operator_id").val(),
				dispatch_handle_username : $("#comp_operator_username").val(),

				comp_detail : $("#comp_detail").val(),
				comp_emergency : $("#comp_emergency").val(),

				comp_status : $("#comp_status").val(),
				finish_time : $("#finish_time").val()

			};

			return addJson;
		}
	} else {
		addJson = {
			rpt_id : $("#rpt_id").val(),
			rpt_name : $("#rpt_name").val(),
			in_call : $("#in_call").val(),
			addres : $("#addres").val(),
			owner_type : $("#owner_type").val(),
			event_source : $("#event_source").val(),
			event_time : $("#event_time").val(),
			event_type : $("#event_type").val(),
			ownerId : $("#ownerId").val(),
			roomId : $("#roomId").val(),
			roomNum : $("#roomNo").val(),
			// 报修
			mtn_detail : $("#mtn_detail").val(),
			mtn_emergency : $("#mtn_emergency").val(),
			dispatch_status : $("#dispatch_status").val(),
			comp_reply : $("#comp_reply").val(),
			dispatch_handle_id : $("#dispatch_handle_id").val(),
			dispatch_handle_username : $("#dispatch_handle_username").val(),

			comp_detail : $("#comp_detail").val(),
			comp_emergency : $("#comp_emergency").val(),
			img_url : $("#img_url").val(),
			comp_status : $("#comp_status").val(),
			finish_time : $("#finish_time").val()

		};

		return addJson;
	}
}
// 取消按钮
function openCancButton(index) {

	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = {
			mtn_id : $("#rpt_id").val(),
			type : index
		};
		$.ajax({
			type : "post",
			url : "./../assigenwork/assignlist.app?method=dispatchSave",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#assignInfo').bootstrapTable('refresh');
					$('#myModal1').modal('hide');
				});
				// closeLayer();

			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！请联系技术人员。', {
					time : 2000
				}, function() {
					$('#myModal2').modal('hide');
				})
			}
		});
	}

}

/* 处理人搜索框初始化 */
function initSearchHandler() {
	var addressSuggest = $("input#dispatch_handle_username").bsSuggest({
		url : "./../search.app?type=engine&keyword=",
		showHeader : true,
		allowNoKeyword : true,
		keyField : 'cname',
		getDataMethod : "url",
		delayUntilKeyup : true,
		// effectiveFields:
		// ["deptName","position","cname","workState","workTimes"],
		effectiveFields : [ "deptName", "position", "cname" ],
		effectiveFieldsAlias : {
			deptName : "部门",
			position : "岗位",
			cname : "姓名",
		// workState:"状态",
		// workTimes:"派工次数"
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

			for (index = 0; index < len; index++) {
				// if(json.result[index][6] == '0'){
				// json.result[index][6] = '空闲中'
				// }else if(json.result[index][6] == '1'){
				// json.result[index][6] = '工作中'
				// }else{
				// json.result[index][6] = ""
				// }
				data.value.push({
					"userName" : json.result[index][0],
					"cname" : json.result[index][1],
					"deptName" : json.result[index][3],
					"position" : json.result[index][5],
				// "workState": json.result[index][6],
				// "workTimes": json.result[index][7]

				});
			}
			// 字符串转化为 json 对象

			return data;
		}
	}).on("onDataRequestSuccess", function(e, result) {
		console.log("onDataRequestSuccess: ", result)
	}).on("onSetSelectValue", function(e, keyword, data) {
		console.log("onSetSelectValue: ", keyword, data);
		$("#dispatch_handle_id").val(data.userName);
		$("#deptName").val(data.deptName);
		$("#dispatch_handle_username").val(data.cname);
	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});

}

function openCompHandleButton() {
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = getDataForm2();
		if (addJson == '1') {
			layer.alert("投诉级别非一般情况不可直接处理");
			return;
		} else {

			$.ajax({
				type : "post",
				url : "./../assigenwork/assignlist.app?method=handle",
				data : addJson,
				dataType : "json",
				async : false,
				success : function(data) {
					layer.msg('操作成功！', {
						time : 2000
					}, function() {
						$('#assignInfo').bootstrapTable('refresh');
						$('#myModal1').modal('hide');
					});
					// closeLayer();

				},
				failure : function(xhr, msg) {
					layer.msg('操作失败！请联系技术人员。', {
						time : 2000
					}, function() {
						$('#myModal1').modal('hide');
					})
				}
			});
		}
	}
}

function getDataForm2() {

	var flag = $("#comp_emergency").val();
	if (flag == '0') {
		var addJson = {
			createTime : $("#createTime").val(),
			in_call : $("#in_call").val(),
			addres : $("#addres").val(),
			owner_type : $("#owner_type").val(),
			event_source : $("#event_source").val(),
			createTime : $("#createTime").val(),
			comp_createTime : $("#comp_createTime").val(),
			// 报修
			mtn_detail : $("#mtn_detail").val(),
			mtn_emergency : $("#mtn_emergency").val(),
			dispatch_status : $("#dispatch_status").val(),
			comp_reply : $("#comp_reply").val(),
			// 投诉
			mtn_id : $("#rpt_id").val(),
			comp_operator_id : $("#dispatch_handle_id").val(),
			comp_emergency : $("#comp_emergency").val(),
			comp_detail : $("#comp_detail").val(),
			comp_status : $("#comp_status").val(),
			comp_emergency : $("#comp_emergency").val(),
			comp_detail : $("#comp_detail").val()
		};
		return addJson;
	} else {
		return "1";
	}
}

function initSearchCompHandler() {
	var addressSuggest = $("input#comp_operator_username").bsSuggest({
		url : "./../search.app?type=allEmp&keyword=",
		showHeader : true,
		allowNoKeyword : true,
		keyField : 'cname',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : [ "deptName", "position", "cname" ],
		// effectiveFields:
		// ["deptName","position","cname","workState","workTimes"],
		effectiveFieldsAlias : {
			deptName : "部门",
			position : "岗位",
			cname : "姓名",
		// workState:"状态",
		// workTimes:"派工次数"
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

			for (index = 0; index < len; index++) {
				// if(json.result[index][6] == '0'){
				// json.result[index][6] = '空闲中'
				// }else if(json.result[index][6] == '1'){
				// json.result[index][6] = '工作中'
				// }else{
				// json.result[index][6] = ""
				// }
				data.value.push({
					"userName" : json.result[index][0],
					"cname" : json.result[index][1],
					"deptName" : json.result[index][3],
					"position" : json.result[index][5],
				// "workState": json.result[index][6],
				// "workTimes": json.result[index][7]

				});
			}
			// 字符串转化为 json 对象

			return data;
		}
	}).on("onDataRequestSuccess", function(e, result) {
		console.log("onDataRequestSuccess: ", result)
	}).on("onSetSelectValue", function(e, keyword, data) {
		console.log("onSetSelectValue: ", keyword, data);
		$("#comp_operator_id").val(data.userName);
		$("#comp_operator_username").val(data.cname);
	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});

}

function showstatic(mtn_detail) {
	var value = mtn_detail;
	if (value == undefined) {
		return;
	} else {
		var curLength = value.length;
		if (curLength >= 501) {
			var num = $("#mtn_detail").val().substr(0, 500);
			$("#mtn_detail").val(num);
			alert("超过字数限制，多出的字将被截断！");
		} else {
			$("#textCount").text(500 - $("#mtn_detail").val().length)
		}
	}
}

function clearSearch() {
	$("#businessFromSearch").val("");
	$("#eventTypeSearch").val("");
}

function imgInput(ctrlName, uploadUrl) {
	var control = $('#' + ctrlName);
	$("#imgDiv").fileinput({
		uploadUrl : uploadUrl, // server upload action
		uploadAsync : false,
		showPreview : true,
		showUpload : true,
		allowedFileExtensions : [ 'jpg', 'png' ],
		maxFileCount : 6,
		elErrorContainer : '#kv-error-3'
	}).on('filebatchpreupload', function(event, data, id, index) {
		// $('#kv-success-3').html('<h4>Upload Status</h4><ul></ul>').hide();
	}).on('filebatchuploadsuccess', function(event, data) {

		var msg = eval('(' + data.response + ')');
		if (msg.status == 1) {
			$("#img_url").val(msg.data);
			layer.msg("成功", {
				time : 2000
			}, function() {
				// $('#importModal').modal('hide');
			});
		} else {
			layer.msg(msg.data, {
				time : 2000
			});
		}
	});
}

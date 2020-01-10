selections = [];

$(function() {
	init();
	initDrops();
	initEventContent();

	initCommDrops();
	initLayDate();
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

	// 查询按钮
	$('#buttonSearch').click(function() {
		$('#assignInfo').bootstrapTable('refresh');
	});

	initSearch();// 初始化搜索框
	$('#myForm1').validationEngine();
	initSearchHandler();
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	});
});

function clearSearch() {
	$("#businessFromSearch").val("");
	$("#CommNameSearch").val("");
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
// 初始化下拉框
function initCommDrops() {
	var url = "./../areaPropertyInfo/areaPropertyList.app?method=list";
	$.post(url, function(data) {
		var obj = JSON.parse(data);
		var list = obj.rows;
		for (var i = 0, len = list.length; i < len; i++) {
			$("<option value='" + list[i].communityName + "'>" + list[i].communityName + "</option>").appendTo("#CommNameSearch");
		}
	});
}

$(function() {
	toolbarBtnInit();// 初始化权限按钮
	for (var i = 0, len = btnIdArray.length; i < len; i++) {
		if (btnIdArray[i] == "btn_add") {
			$("#btn_add").bind('click', function() {
				$("#btHouseworkdelete").hide();
				openButtonWindow(1);
			});
		} else if (btnIdArray[i] == "btn_del") {
			$("#btn_del").bind('click', function() {
				openButtonWindow(4);
			});
		}
	}
	$("#btn_del").attr("disabled", true);

});

/**
 * 刷新数据表单
 */
function buttonreresh() {
	$('#refresh').click();
}

// 控制输入字符
$(document).ready(function() {
	$("#event_content").keydown(function() {
		var curLength = $("#event_content").val().length;
		if (curLength >= 501) {
			var num = $("#event_content").val().substr(0, 500);
			$("#event_content").val(num);
			alert("超过字数限制，多出的字将被截断！");
		} else {
			$("#textCount").text(500 - $("#event_content").val().length)
		}
	})
})

function init() {

	// 初始化Table
	oTable = new TableInit();
	oTable.Init();

};

function initEventContent() {
	// 后台请求数据

	$.ajax({
		type : "post",
		url : "./../houseWork/workType.app?method=listWorkType",
		dataType : "json",
		async : false,
		success : function(data) {
			$("#eventContent").append("<label>&nbsp;&nbsp;</label>" + showHtml(data));
		},
		failure : function(xhr, msg) {
		}
	});

}

function showHtml(menuData) {
	var html = [];
	var str = "";
	var strname = "";
	if (menuData != null) {
		for (var i = 0; i < menuData.length; i++) {
			html.push('<input type="checkbox" onclick="getCheckBox_data();"  id="chk_' + i + '" name="checkbox"  value="' + menuData[i][1] + '"/>' + menuData[i][1] + '&nbsp;&nbsp;&nbsp;');

		}
		return html.join("");
	} else
		return "";
}

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#assignInfo').bootstrapTable({
			url : './../houseWork/accept.app?method=listHouseWork', // 请求后台的URL（*）
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
				field : 'stat',
				checkbox : true,
				align : 'center',
				valign : 'middle'
			}, {
				field : 'event_no',
				title : '事件单号',
				sortable : true,
				editable : true,
				align : 'center'

			}, {
				field : 'event_title',
				title : '事件标题',
				sortable : true,
				editable : true,
				align : 'center',
				visible : false
			}, {
				field : 'event_source_name',
				title : '事件来源',
				align : 'center',
				valign : 'middle',
				sortable : true
			}, {
				field : 'call_phone',
				title : '呼入电话',
				sortable : true,
				align : 'center',
			}, {
				field : 'accept_time',
				title : '受理时间',
				align : 'center',
				valign : 'middle',
				sortable : true
			}, {
				field : 'pre_time',
				title : '预处理时间',
				align : 'center',
				valign : 'middle',
				sortable : true
			}, {
				field : 'verify_oper_name',
				title : '受理人',
				align : 'center',
				sortable : true
			}, {
				field : 'verify_oper_id',
				title : '受理人',
				align : 'center',
				editable : true,
				sortable : true,
				visible : false
			}, {
				field : 'rpt_name',
				title : '申请人',
				sortable : true,
				editable : true,
				align : 'center'
			}, {
				field : 'user_address',
				title : '地址',
				align : 'center',
				valign : 'middle',
				sortable : true
			}, {
				field : 'event_state',
				title : '事件状态',
				align : 'center',
				valign : 'middle',
				sortable : true,
				formatter : stateFormat
			}, {
				field : 'record_id',
				title : '关联录音',
				sortable : true,
				editable : true,
				align : 'center'
			}, {
				field : 'bpm_processId',
				title : '流程编号',
				sortable : true,
				editable : true,
				align : 'center',
				visible : false
			}, {
				field : 'event_source',
				title : '事件来源',
				sortable : true,
				editable : true,
				align : 'center',
				visible : false
			}

			, {
				field : 'event_content',
				title : '内容',
				sortable : true,
				editable : true,
				align : 'center',
				visible : false
			}, {
				field : 'id',
				title : '编号',
				sortable : true,
				editable : true,
				align : 'center',
				visible : false
			}, {
				field : 'other',
				title : '备注',
				sortable : true,
				editable : true,
				align : 'center',
				visible : false
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				width : '15%',
				events : operateEvents,
				formatter : operateFormatter
			}

			],
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
				$("#btn2").attr("disabled", true);
				$("#btn3").attr("disabled", true);
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
			show(row, 1);
		},
		'click #a_send' : function(e, value, row, index) {
			show(row, 2);
		},
		'click #a_edit' : function(e, value, row, index) {
			show(row, 3);
		},
		'click #a_cancel' : function(e, value, row, index) {
			show(row, 4);
		}

	};

	// 操作列的显示风格
	function operateFormatterbak(value, row, index) {
		return [ '<a class="like" href="javascript:void(0)">', '' + row.event_no + '', '</a>  ' ].join('');
	}
	// 操作列的事件
	window.operateEventsbak = {
		'click .like' : function(e, value, row, index) {
			clear_houseWorkInfo();
			show(row);
		}
	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : encodeURI(params.search),
			businessFromSearch : $("#businessFromSearch").val(),
			CommNameSearch : encodeURI($("#CommNameSearch").val() || ""),
			dateSearch : $("#dateSearch").val()
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

// 清除上次访问内容
function clear_houseWorkInfo() {

	$("#event_no").val("");
	$("#event_source").val("");
	$("#rpt_name").val("");
	$("#in_call").val("");
	$("#addres").val("");
	$("#event_title").val("");
	$("#pre_time").val("");
	$("#event_content").val("");
	$("#verify_oper_id").val("");
	$("#accept_time").val("");
	$("#other").val("");
	$("#handle_content").val("");
	$("#comp_operator_id").val("");
	$("#eventContent").val("");
	$("#comp_operator_username").val("");
	$("#ownerSearch").hide();
}

// 显示详情
function show(obj, type) {

	if (type == 1)// 查看
	{
		shouHouseWorkInfo(type);
	} else if (type == 2) {
		sendHouseWorkInfo(type);

	} else if (type == 3) {
		tempSaveHouseWorkInfo(type);

	} else if (type == 4) {
		cancelHouseWorkInfo(type);

	}
	$("#comp_operator_username").val("");
	changeEventType(1, obj.bpm_processId);
	$("#event_no").val(obj.event_no);
	$("#event_source").val(obj.event_source);
	$("#rpt_name").val(obj.rpt_name);
	$("#in_call").val(obj.call_phone);
	$("#addres").val(obj.user_address);
	$("#event_title").val(obj.event_title);
	$("#pre_time").val(obj.pre_time);
	$("#event_content").val(obj.event_content);
	showstatic(obj.event_content);
	$("#verify_oper_id").val(obj.verify_oper_id);
	$("#verify_oper_name").val(obj.verify_oper_name);
	$("#id").val(obj.id);
	$("#accept_time").val(obj.accept_time);
	$("#other").val(obj.other);
	getHouseWork_eventContent(obj.event_content);
	// $("#myModal1").modal();
	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});
}
// 查看
function shouHouseWorkInfo(type) {

	$("#event_no").attr("disabled", true);
	$("#event_source").attr("disabled", true);
	$("#rpt_name").attr("disabled", true);
	$("#in_call").attr("disabled", true);
	$("#addres").attr("disabled", true);
	$("#event_title").attr("disabled", true);
	$("#pre_time").attr("disabled", true);
	$("#event_content").attr("disabled", true);
	$("#verify_oper_id").attr("disabled", true);
	$("#verify_oper_name").attr("disabled", true);
	$("#id").attr("disabled", true);
	$("#accept_time").attr("disabled", true);
	$("#other").attr("disabled", true);
	$("#comp_operator_username").attr("disabled", true);
	$("#ownerSearch").hide();
	$("#btAssigenAdd").hide();
	$("#btAssigenHandle").hide();
	$("#btHouseworkdelete").hide();

}

// 派工
function sendHouseWorkInfo(type) {
	$("#event_no").attr("disabled", false);
	$("#event_source").attr("disabled", false);
	$("#rpt_name").attr("disabled", false);
	$("#in_call").attr("disabled", false);
	$("#addres").attr("disabled", false);
	$("#event_title").attr("disabled", false);
	$("#pre_time").attr("disabled", false);
	$("#event_content").attr("disabled", false);
	$("#verify_oper_id").attr("disabled", false);
	$("#verify_oper_name").attr("disabled", false);
	$("#id").attr("disabled", false);
	$("#accept_time").attr("disabled", false);
	$("#other").attr("disabled", false);
	$("#comp_operator_username").attr("disabled", false);
	$("#ownerSearch").hide();
	$("#btAssigenAdd").hide();
	$("#btAssigenHandle").show();
	$("#btHouseworkdelete").hide();

}
// 临时保存
function tempSaveHouseWorkInfo(type) {
	$("#event_no").attr("disabled", false);
	$("#event_source").attr("disabled", false);
	$("#rpt_name").attr("disabled", false);
	$("#in_call").attr("disabled", false);
	$("#addres").attr("disabled", false);
	$("#event_title").attr("disabled", false);
	$("#pre_time").attr("disabled", false);
	$("#event_content").attr("disabled", false);
	$("#verify_oper_id").attr("disabled", false);
	$("#verify_oper_name").attr("disabled", false);
	$("#id").attr("disabled", false);
	$("#accept_time").attr("disabled", false);
	$("#other").attr("disabled", false);
	$("#comp_operator_username").attr("disabled", false);
	$("#ownerSearch").hide();
	$("#btAssigenAdd").show();
	$("#btAssigenHandle").hide();
	$("#btHouseworkdelete").hide();

}
// 取消工单
function cancelHouseWorkInfo(type) {
	$("#event_no").attr("disabled", true);
	$("#event_source").attr("disabled", true);
	$("#rpt_name").attr("disabled", true);
	$("#in_call").attr("disabled", true);
	$("#addres").attr("disabled", true);
	$("#event_title").attr("disabled", true);
	$("#pre_time").attr("disabled", true);
	$("#event_content").attr("disabled", true);
	$("#verify_oper_id").attr("disabled", true);
	$("#verify_oper_name").attr("disabled", true);
	$("#id").attr("disabled", true);
	$("#accept_time").attr("disabled", true);
	$("#other").attr("disabled", true);
	$("#comp_operator_username").attr("disabled", true);
	$("#ownerSearch").hide();
	$("#btAssigenAdd").hide();
	$("#btAssigenHandle").hide();
	$("#btHouseworkdelete").show();
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

// 表格选择事件
function tableCheckEvents() {

	var r = $('#assignInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn2").attr("disabled", true);
		$("#btn3").attr("disabled", true);
		$("#btn_del").attr("disabled", true);
		$("#btn5").attr("disabled", true);
	}
	if (r.length == 1) {

		// var head=r[0].rpt_id.substring(0,2);
		// if(head=="IM")
		// {
		// $("#btn5").attr("disabled",false);
		// }else
		// {
		// $("#btn5").attr("disabled",true);
		// }

		$("#btn2").attr("disabled", false);
		$("#btn3").attr("disabled", false);
		$("#btn_del").attr("disabled", false);

	}
	if (r.length > 1) {
		$("#btn2").attr("disabled", true);
		$("#btn3").attr("disabled", true);
		$("#btn5").attr("disabled", true);
		$("#btn_del").attr("disabled", false);
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
		effectiveFields : [ "roomNo", "ownerName", "phone" ],
		effectiveFieldsAlias : {
			roomNo : "房间号",
			ownerName : "业主名",
			phone : "手机号"
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
				data.value.push({
					"roomId" : json.result[index][4],
					"ownerName" : json.result[index][7],
					"ownerId" : json.result[index][6],
					"phone" : json.result[index][8],
					"roomNo" : json.result[index][5],
					"addres" : json.result[index][1] + "-" + json.result[index][3] + "-" + json.result[index][5],
					"searchInput" : json.result[index][1] + "-" + json.result[index][3] + "-" + json.result[index][5] + "（" + json.result[index][7] + "）",
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
		$("#in_call").val(data.phone);
		$("#addres").val(data.addres);
		$("#searchInput").val(data.searchInput);
	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});

}

/**
 * 前排按钮监控
 * 
 * @param type
 */
function openButtonWindow(type) {
	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});
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
			urlmethod = "method=addHouseWork";
			$("#dispatch_status").val(0);
			$("#btAssigenAdd").show();
			$("#searchInput").val("");
			clear_houseWorkInfo();
			$("#ownerSearch").show();
			ableEdit();
		} else if (type == 2) {
			// 修改
			$("#btAssigenHandle").hide();
			$("#ownerSearch").hide();
			ableEdit();
			urlmethod = "method=viewAssign&assignId=" + rpt_id;
		} else if (type == 3 || type == 5) {
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

			urlmethod = "method=deleteHouseWork";
			// alert(urlmethod);
		}

		if (type == 4) {
			if (selections.length == 0) {

				layer.alert("请选则要删除的事件", {
					skin : 'layui-layer-molv' // 样式类名
					,
					closeBtn : 0,
				}, function() {

				});
			} else {
				layer.confirm("您确定要删除所选信息吗?", {
					skin : 'layui-layer-molv',
					btn : [ '确定', '取消' ]
				}, function() {
					var url = "./../houseWork/accept.app?" + urlmethod;
					var deleteIds = JSON.stringify(selections);
					$.post(url, {
						workId : deleteIds
					}, function(data) {
						layer.alert(data, {
							skin : 'layui-layer-molv' // 样式类名
							,
							closeBtn : 0
						}, function() {
							var locationUrl = "./../houseWork/houseWorkEvent.jsp";
							window.location.href = locationUrl;
						});
					});
				}, function() {
					return;
				})
			}
		} else {
			var url = "./../houseWork/accept.app?" + urlmethod;
			$.post(url, function(data) {
				var list = eval(data);
				var event_type_data = list.event_type;
				// var processInstanceId = list.processInstanceId;
				// changeEventType(event_type_data,processInstanceId);
				// nameFormatCName("createby",list.createby);
				$("#accept_time").val(getNowFormatDate(false, list.accept_time));
				$("#event_no").val(list.event_no);
				$("#verify_oper_id").val(list.verify_oper_id);
				$("#verify_oper_name").val(list.verify_oper_name);

				$("#comp_status").val(list.comp_status);
				$("#comp_emergency").val(list.comp_emergency);
				$("#comp_detail").val(list.comp_detail);
				$("#finish_time").val(getNowFormatDate(false, list.finishTime));
			});
			// $('#myModal1').modal();
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
	$('#comp_reply').attr("disabled", "true");
	$('#comp_emergency').attr("disabled", "true");
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
	$('#comp_emergency').attr("disabled", false);
	$('#comp_status').attr("disabled", false);

}

function openSaveButton() {
	$("#comp_operator_username").removeClass("form-control validate[required]");
	$("#comp_operator_username").addClass("form-control");
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		$("#btAssigenAdd").attr("disabled", "true");
		// chk();
		var addJson = getDataForm();
		// alert(addJson);
		$.ajax({
			type : "post",
			url : "./../houseWork/accept.app?method=houseWorkSave",
			data : addJson,
			dataType : "json",
			async : true,
			success : function(data) {
				layer.msg(data + '操作成功！', {
					time : 500
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

// 家政派工
function openHandleButton() {
	var flag = $('#myForm1').validationEngine('validate');
	var id = $("#comp_operator_id").val();
	if (id == null || id == '') {
		layer.alert("处理人不存在，不能派工");
		return;
	}
	if (flag) {
		var addJson = getSendForm();
		$.ajax({
			type : "post",
			url : "./../houseWork/accept.app?method=eventSend",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
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
// 待派工环节坐席取消工单
function openQxButton() {

	layer.confirm("您确定要取消工单吗?", {
		skin : 'layui-layer-molv',
		btn : [ '确定', '取消' ]
	}, function() {
		var addJson = getSendForm();
		$.ajax({
			type : "post",
			url : "./../houseWork/accept.app?method=eventDel",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！' + data, {
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
	}, function() {
		return;
	})

}

// 派工数据组装
function getSendForm() {
	var addJson = {
		event_no : $("#event_no").val(),
		event_from : $("#event_source").val(),
		ownerId : $("#ownerId").val(),
		roomId : $("#roomId").val(),
		rpt_name : $("#rpt_name").val(),
		link_phone : $("#in_call").val(),
		addres : $("#addres").val(),
		event_title : $("#event_title").val(),
		pre_time : $("#pre_time").val(),
		event_content : $("#event_content").val(),
		record_id : $("#record_id").val(),
		verify_oper_id : $("#verify_oper_id").val(),
		accept_time : $("#accept_time").val(),
		id : $("#id").val(),

		// 派工
		handle_content : $("#handle_content").val(),
		comp_operator_id : $("#comp_operator_id").val(),

		comp_operator_username : $("#comp_operator_username").val(),
		dispatch_finish_time : $("#dispatch_finish_time").val(),
		dispatch_status : $("#dispatch_status").val(),
	};
	return addJson;

}

function getDataForm() {
	var addJson = {
		event_no : $("#event_no").val(),
		event_from : $("#event_source").val(),
		ownerId : $("#ownerId").val(),
		roomId : $("#roomId").val(),
		rpt_name : $("#rpt_name").val(),
		link_phone : $("#in_call").val(),
		addres : $("#addres").val(),
		event_title : $("#event_title").val(),
		pre_time : $("#pre_time").val(),
		event_content : $("#event_content").val(),
		record_id : $("#record_id").val(),
		verify_oper_id : $("#verify_oper_id").val(),
		accept_time : $("#accept_time").val(),
		id : $("#id").val(),
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
		return row.id
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

		return [ '<span class="label label-primary">已接收</span>' ].join('');
	} else if (value == 2) {

		return [ '<span class="label label-primary">已处理</span>' ].join('');
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
			// debugger;
			var detailList = list[j];
			var code = detailList[0];
			// 事件类型
			if (code == 'main_event_type') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#event_type");
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#eventTypeSearch");
			}

			// 事件来源
			if (code == 'main_event_source') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#event_source");
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#businessFromSearch");
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
			grapIframe = "./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=5001&numb=" + generateMixed(4);
		} else {
			grapIframe = "./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=217557&numb=" + generateMixed(4);
		}
	} else {
		grapIframe = "./../graph/graphProcessDefinition.app?processDefinitionId=" + processInstanceId + "&numb=" + generateMixed(4);
	}

	if ($("#event_type").val() == "0" || index == "0") {
		$("#bxdetail").show();
		$("#tsdetail").hide();
	} else {
		$("#bxdetail").hide();
		$("#tsdetail").show();
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

function initSearchHandler() {

	var addressSuggest = $("input#comp_operator_username").bsSuggest({
		url : "./../search.app?type=service&keyword=",
		showHeader : true,
		allowNoKeyword : true,
		keyField : 'cname',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : [ "deptName", "position", "cname" ],
		effectiveFieldsAlias : {
			deptName : "部门",
			position : "岗位",
			cname : "姓名"
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
				data.value.push({
					"userName" : json.result[index][0],
					"cname" : json.result[index][1],
					"deptName" : json.result[index][3],
					"position" : json.result[index][5]

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
	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});

}

/**
 * 操作成功后2秒自动关闭
 * 
 * @param layerName
 */
function closeLayer() {
	setTimeout(function() {// IE6、7不会提示关闭
		$('#myModal1').modal('hide'); // 执行关闭
	}, 2000);
}

/**
 * 选择家政服务内容
 * 
 * @param data
 */
function getHouseWork_eventContent(data) {
	var arr = new Array();
	arr = data.split(",");// 
	var obj = document.getElementsByName('checkbox'); // 选择所有name="'checkbox'"的对象，返回数组
	var s = '';
	for (var i = 0; i < obj.length; i++) {
		for (var j = 0; j < arr.length; j++) {
			if (obj[i].value == arr[j]) {

				$("#chk_" + i).attr("checked", "checked");
			}
		}
	}

}

function getCheckBox_data() { // jquery获取复选框值
	var chk_value = [];
	$('input[name="checkbox"]:checked').each(function() {

		chk_value.push($(this).val());
	});
	$("#event_content").val(chk_value);
}

function close_page() {
	window.location.href = "./../houseWork/houseWorkEvent.jsp"
	$('#myModal1').modal('hide'); // 执行关闭

}

function showstatic(mtn_detail) {
	var value = mtn_detail;
	var curLength = value.length;
	if (curLength >= 501) {
		var num = $("#event_content").val().substr(0, 500);
		$("#event_content").val(num);
		alert("超过字数限制，多出的字将被截断！");
	} else {
		$("#textCount").text(500 - $("#event_content").val().length)
	}
}

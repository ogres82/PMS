selections = [];

$(function() {
	init();
	initDrops();
	initsearchDrops();
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

	initSearch();// 初始化搜索框
	$('#myForm1').validationEngine();
	$(".fancybox").fancybox();

	// 查询按钮
	$('#buttonSearch').click(function() {
		$('#assignInfo').bootstrapTable('refresh');
	});

});

function clearSearch() {
	$("#businessFromSearch").val("");
	$("#eventTypeSearch").val("");
	$("#CommNameSearch").val("");
	$("#dateSearch").val("");
	$("#eventStatusSearch").val("");
	$('#buttonSearch').click();
}

function initLayDate() {
	var dateSearch =laydate.render({
		elem : '#dateSearch',		
		max : '2099-06-16', // 最大日期
		istime : false,
		istoday : false
	});
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
		if (btnIdArray[i] == "btn1") {
			$("#btn1").bind('click', function() {
				openButtonWindow(1);
			});
		} else if (btnIdArray[i] == "btn4") {
			$("#btn4").bind('click', function() {
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

function init() {
	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#assignInfo').bootstrapTable({
			url : './../assigenwork/assignWorkSearch.app?method=assignWorkSearch', // 请求后台的URL（*）
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
				field : 'order_state',
				title : '工单状态',
				align : 'center',
				valign : 'middle',
				sortable : true
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

		return tableBtnInit();
	}
	// 操作列的事件
	window.operateEvents = {
		'click #a_check' : function(e, value, row, index) {
			show(row, 3)
		},
		'click #a_edit' : function(e, value, row, index) {
			show(row, 2)
		}

	};
	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : encodeURI(params.search),
			businessFromSearch : $("#businessFromSearch").val(),
			eventTypeSearch : $("#eventTypeSearch").val(),
			CommNameSearch : encodeURI($("#CommNameSearch").val() || ""),
			dateSearch : $("#dateSearch").val(),
			eventStatusSearch : $("#eventStatusSearch").val()
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
		allowNoKeyword : false,
		keyField : 'roomNo',
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : [ "roomNo", "ownerName", "phone" ],
		effectiveFieldsAlias : {
			roomNo : "房间号",
			ownerName : "业主名",
			phone : "手机号"
		},
		// 预处理
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
					"searchInput" : json.result[index][1] + "-" + json.result[index][3] + "-" + json.result[index][5] + "（" + json.result[index][7] + "）",
					"addres" : json.result[index][1] + "-" + json.result[index][3] + "-" + json.result[index][5],
					"roomId" : json.result[index][4],
					"ownerName" : json.result[index][7],
					"ownerId" : json.result[index][6],
					"phone" : json.result[index][8],
					"roomNum" : json.result[index][5],
					"roomNo" : json.result[index][5],
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

	clear_info();
	var rpt_id = obj.rpt_id;
	var urlmethod = "";
	if (type == 3) {
		// btAssigenHandle
		$("#btAssigenHandle").hide();
		$('#btAssigenAdd').hide();
		$("#ownerSearch").hide();
		disableEdit();
		urlmethod = "method=viewAssignSearch&assignId=" + rpt_id;
	}

	var url = "./../assigenwork/assignWorkSearch.app?" + urlmethod;
	$.post(url, function(data) {

		var list = eval(data);
		var event_type_data = list.event_type;
		var processInstanceId = list.processInstanceId;

		changeEventType(event_type_data, processInstanceId);
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
		$("#mtn_detail").val(fromatMntdetail(list.mtn_detail));	
		
		$("#comp_reply").val(list.comp_reply);
		$("#mtn_emergency").val(list.mtn_emergency);
		$("#dispatch_status").val(list.dispatch_status);

		var rpt_id = list.rpt_id;
		var strId = rpt_id.substring(0, 2);
		if (strId == 'BX') {
			$("#dispatchBX").show();
			$("#dispatchWXLB").show();
			$("#dispatchFee").show();
			$("#dispatch_arrive_time").val(subStringTime(list.dispatch_arrive_time));
			$("#dispatch_time").val(subStringTime(list.dispatch_time));
			$("#mtn_type").val(list.mtn_type);
			$("#mtn_priority").val(list.mtn_priority);
			$("#dispatch_handle_id").val(list.dispatch_handle_id);
			$("#dispatch_expense").val(list.dispatch_expense);
			nameFormatCName("createby", list.createby);
			// 回访
			$("#dispatch_visit_lev").val(list.dispatch_visit_lev);
			var record = 'null' == list.dispatch_visit_record ? "" : list.dispatch_visit_record;
			$("#dispatch_visit_record").val(record);
			var dispatch_evaluate = 'null' == list.dispatch_evaluate ? "" : list.dispatch_evaluate;
			$("#dispatch_evaluate").val(dispatch_evaluate);

			var recording = 'null' == list.dispatch_visit_recording ? "" : list.dispatch_visit_recording;
			$("#dispatch_visit_recording").val(recording);
			$("#event_source1").val(list.event_source);
		} else {
			$("#dispatchBX").hide();
			$("#dispatchWXLB").hide();
			$("#dispatchFee").hide();
			nameFormatCName("createby", list.comp_createby);
			// 回访
			$("#dispatch_visit_lev").val(list.comp_visit_lev);
			$("#dispatch_visit_record").val(list.comp_visit_record);
			$("#dispatch_visit_recording").val(list.comp_visit_recording);
			$("#event_source1").val(list.event_source);
		}
		// 投诉
		$("#comp_chuliren").val(list.comp_chuliren);
		$("#comp_reply").val(list.comp_reply);
		$("#comp_status").val(list.comp_status);
		$("#comp_emergency").val(list.comp_emergency);
		$("#comp_detail").val(list.event_content);
		$("#comp_result").val(list.comp_result);
		$("#dispatch_finish_time").val(subStringTime(list.dispatch_finish_time));
		$("#finish_time").val(subStringTime(list.comp_finish_time));
		// 展示图片
		// initEventImg(list.rpt_id)
		var imgurl = list.img_url;
		$("#eventImgShow").empty();// 每一次请求，先把上一次的清空
		if (typeof (imgurl) != "undefined" && imgurl != null && imgurl != "[]") {
			var html = [];
			var imgArray = JSON.parse(imgurl);
			for (var i = 0; i < imgArray.length; i++) {
				html.push("<a class='fancybox' href='" + imgArray[i] + "'><img alt='img' src='" + imgArray[i] + "' onload='AutoResizeImage(0,150,this)'/></a>");
			}
			var img = html.join("");
			$("#eventImgShow").append(img);
		}

		var finishImgUrl = list.finishImgUrl;
		$("#finishImgShow").empty()
		if (finishImgUrl != null && finishImgUrl != "") {
			var html = [];
			var finishImgUrlArray = finishImgUrl.split(",");
			for (var i = 0; i < finishImgUrlArray.length; i++) {
				html.push("<a class='fancybox' href='" + finishImgUrlArray[i] + "'><img alt='img' src='" + finishImgUrlArray[i] + "' onload='AutoResizeImage(0,150,this)'/></a>");
			}
			var finishImg = html.join("");
			$("#finishImgShow").append(finishImg);
		}
		if (list.mtn_priority == "1" || list.dispatch_result == "拒绝维修" || list.order_state == "拒单") {
			$("#rejectReason").val(list.rejectReason);
			$("#reject").show();
		} else {
			$("#reject").hide();
		}
	});
	$('#myModal1').modal();

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
	// 报障
	$("#mtn_detail").val("");
	$("#comp_reply").val("");
	$("#mtn_emergency").val("");
	$("#dispatch_status").val("");
	$("#dispatch_visit_lev").val("");
	$("#dispatch_visit_record").val("");
	$("#dispatch_evaluate").val("");
	// 投诉

	$("#comp_status").val("");
	$("#comp_emergency").val("");
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
			urlmethod = "method=addAssign";
			$("#dispatch_status").val(0);
			$("#btAssigenAdd").show();
			$("#btAssigenHandle").hide();
			$("#searchInput").val("");
			$("#ownerSearch").show();
			ableEdit();
		} else if (type == 2) {
			// 修改
			$("#ownerSearch").hide();
			$("#btAssigenHandle").hide();

			ableEdit();
			$('#comp_status').attr("disabled", "true");
			urlmethod = "method=viewAssignSearch&assignId=" + rpt_id;
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
			urlmethod = "method=viewAssignSearch&assignId=" + rpt_id;
		} else if (type == 4) {
			// 删除
			// var deleteIds = JSON.stringify(selections);
			urlmethod = "method=deleteAssign";
			// alert(urlmethod);
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

			$('#myModal1').modal();
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
	$('#dispatch_evaluate').attr("disabled", "true");
	$('#dispatch_visit_record').attr("disabled", "true");
	$('#dispatch_visit_lev').attr("disabled", "true");
	$('#event_source1').attr("disabled", "true");

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
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		$("#btAssigenAdd").attr("disabled", "true");
		var addJson = getDataForm();
		// alert(addJson);
		$.ajax({
			type : "post",
			url : "./../assigenwork/assignlist.app?method=save",
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
	// alert(addJson);
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
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#eventStatusSearch");
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

function initEventImg(rpt_id) {
	var html = [];
	var url = "./../assigenwork/assignlist.app?method=getEventImg&rpt_id=" + rpt_id;
	$.post(url, function(data) {
		var list = eval(data);
		for (var i = 0; i < list.length; i++) {
			html.push("<a class='fancybox' href='" + list[i].img_url + "' title='" + list[i].event_id + "'><img alt='image' src='" + list[i].img_url + "' onload='AutoResizeImage(0,170,this)'/></a>");
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

function showstatic(mtn_detail) {
	var value = mtn_detail;
	var curLength = value.length;
	if (curLength >= 501) {
		var num = $("#mtn_detail").val().substr(0, 500);
		$("#mtn_detail").val(num);
		alert("超过字数限制，多出的字将被截断！");
	} else {
		$("#textCount").text(500 - $("#mtn_detail").val().length)
	}
}

function changeMtnPriority() {
	var mtn_type = $('#mtn_type').val();
	if (mtn_type == 1) {
		$('#div_mtn_priority').show();
	} else {
		$('#div_mtn_priority').hide();
	}

}

// 初始化下拉框
function initsearchDrops() {
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
			}

			// 事件来源
			if (code == 'main_event_source') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#event_source");
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#event_source1");
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

			// 紧急程度
			if (code == 'main_mtn_emergency') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#mtn_emergency");
			}

			// mtn_emergency 维修类别
			if (code == 'main_mtn_type') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#mtn_type");
			}

			// dispatch_status 派工单状态
			if (code == 'main_mtn_dispatch_status') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#dispatch_status");
			}

			// mtn_priority 业主维修意见
			if (code == 'main_disp_mtn_priority') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#mtn_priority");
			}

			// dispatch_visit_lev 回访状态
			if (code == 'main_mtn_visit_lev') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#dispatch_visit_lev");
			}

			// comp_status 派工单状态
			if (code == 'main_comp_solve') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#comp_solve");
			}

			// comp_status 派工单状态
			if (code == 'main_comp_degree') {
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#comp_degree");
			}
		}
		;
	});
	// return rtnValue;
}

// 时间转换针对于时间到达毫秒情况
function subStringTime(time) {
	var changeTieme = time.substring(0, time.lastIndexOf("."))
	return changeTieme;
}

function fromatMntdetail(value) {
	var end = value.indexOf("现场联系人");
	var strTmp = value.substring(0,end); 
	if(end==-1){
		return value;
	}
	return strTmp;
}
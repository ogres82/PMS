selections = [];

$(function() {
	init();
	initDrops();
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

	$(".fancybox").fancybox();
	$('#myForm1').validationEngine();

	// 查询按钮
	$('#buttonSearch').click(function() {
		$('#visitInfo').bootstrapTable('refresh');
	});
});

function clearSearch() {
	$("#businessFromSearch").val("");
	$("#eventTypeSearch").val("");
	$("#CommNameSearch").val("");
	$("#dateSearch").val("");
	$('#buttonSearch').click();
	
}

function initLayDate() {
	var dateSearch = laydate.render({
		elem : '#dateSearch',
		max : '2099-06-16', // 最大日期
		istime : false,
		istoday : false
	});
}


$(function() {
	toolbarBtnInit();// 初始化权限按钮
});

function init() {
	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#visitInfo').bootstrapTable({
			url : './../visitnwork/visitlist.app?method=visitlist', // 请求后台的URL（*）
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
			columns : [

			{
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
				field : 'event_source_name',
				title : '事件来源',
				align : 'center',
				valign : 'middle',
				sortable : true
			// formatter: rpt_name
			}, {
				field : 'event_type_name',
				title : '事件类别',
				sortable : true,
				align : 'center',
			}, {
				field : 'rpt_name',
				title : '报事人',
				sortable : true,
				align : 'center',
			}, {
				field : 'addres',
				title : '地址',
				align : 'center',
				valign : 'middle',
				sortable : true
			}, {
				field : 'dispatch_id',
				title : '派工单号',
				align : 'center',
				valign : 'middle',
				sortable : true,
				visible : false
			}, {
				field : 'dispatch_status_name',
				title : '状态',
				align : 'center',
				sortable : true,
				formatter : stateFormat
			}, {
				field : 'createTime',
				title : '受理时间',
				align : 'center',
				valign : 'middle',
				sortable : true
			}, {
				field : 'createby_name',
				title : '受理人',
				align : 'center',
				valign : 'middle',
				sortable : true
			// formatter:
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
				$("#btnVisit3").attr("disabled", true);
				$("#btnVisit1").attr("disabled", true);
			},
			onUncheckAll : function(rows) {

				$("#btnVisit3").attr("disabled", true);
				$("#btnVisit1").attr("disabled", true);

			}
		});
	};

	function operateFormatter(value, row, index) {

		return tableBtnInit();
	}
	// 操作列的事件
	window.operateEvents = {
		'click #a_check' : function(e, value, row, index) {
			openVisitButton(3, row);

		},
		'click #a_visit' : function(e, value, row, index) {
			openVisitButton(1, row);
		}

	};
	oTableInit.queryParams = function(params) {
		var rpt_name = $("#rpt_name").val();
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : encodeURI(params.search),
			rpt_name : encodeURI(rpt_name),
			businessFromSearch : $("#businessFromSearch").val(),
			eventTypeSearch : $("#eventTypeSearch").val(),
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
	function stateFormat(value) {
		if (value == "未派工") {
			return [ '<span class="label label-danger">' + value + '</span>' ].join('');
		} else if (value == "已接收" || value == "处理中") {
			return [ '<span class="label label-primary">' + value + '</span>' ].join('');
		} else if (value == "待回访") {
			return [ '<span class="label label-warning">' + value + '</span>' ].join('');
		} else if (value == "已归档") {
			return [ '<span class="label label-success">' + value + '</span>' ].join('');
		} else {
			return [ value ].join('');
		}
	}
	;
	return oTableInit;
};

// 表格选择事件
function tableCheckEvents() {
	var r = $('#visitInfo').bootstrapTable('getSelections');
	var status = "";
	if (r.length != 0) {
		status = r[0].dispatch_status;
	}
	if (r.length == 0) {

		$("#btnVisit3").attr("disabled", true);
		$("#btnVisit1").attr("disabled", true);

	}
	if (r.length == 1) {
		if (status == 5) {
			$("#btnVisit1").attr("disabled", true);
		} else {
			$("#btnVisit1").attr("disabled", false);
		}
		$("#btnVisit3").attr("disabled", false);
		$("#btVisitAdd").attr("disabled", false);

	}
	if (r.length > 1) {
		$("#btnVisit3").attr("disabled", true);
		$("#btnVisit1").attr("disabled", true);
	}
}

function openVisitButton(index, obj) {
	$("#dis_result").hide();
	$("#tool").hide();
	// 1===回访,3===查看
	$('#btVisitAdd').removeAttr("disabled");
	$('#btVisitLev').removeAttr("disabled");
	var urlmethod = "";
	var rpt_id = obj.rpt_id;
	urlmethod = "method=viewVisit&assignId=" + rpt_id;
	if (index == 3) {
		$('#btVisitAdd').hide();
		$('#btVisitLev').hide();
		$("#dispatch_visit_record").attr("disabled", "true");
		$("#dispatch_visit_lev").attr("disabled", "true");
		$("#event_source1").attr("disabled", "true");

	} else {
		$('#btVisitAdd').show();
		$('#btVisitLev').hide();
		$("#dispatch_visit_record").attr("disabled", false);
		$("#dispatch_visit_lev").attr("disabled", false);
		$("#event_source1").attr("disabled", false);
	}
	openMyModal1(urlmethod);
}

function openMyModal1(urlmethod) {
	var url = "./../visitnwork/visitlist.app?" + urlmethod;
	var grapIframe = "";
	var processInstanceId = "";
	$.post(url, function(data) {
		var list = eval(data);
		var event_type_data = list[0].event_type;
		changeEventType(event_type_data);
		var processInstanceId = list[0].processInstanceId;
		nameFormatCName("createby", list[0].createby);
		var dispatch = list[1];
		var comp = list[2];
		if (null != comp && comp != 'null') {
			$("#comp_id").val(comp.comp_id);
			$("#comp_createTime").val(getNowFormatDate(false, comp.comp_createTime));
			$("#comp_detail").val(comp.comp_detail);
			$("#comp_emergency").val(comp.comp_emergency);
			$("#comp_status").val(comp.comp_status);
			$("#comp_reply").val(comp.comp_reply);
			$("#comp_operator_id").val(comp.comp_operator);
			nameFormatCName("createby", comp.comp_createby);
			$("#comp_process").val(comp.comp_process);
			$("#comp_result").val(comp.comp_result);
			$("#comp_solve").val(comp.comp_solve);
			$("#comp_degree").val(comp.comp_degree);
			$("#comp_finish_time").val(getNowFormatDate(false, comp.comp_finish_time));
			$("#mtn_id").val(comp.mtn_id);
			$("#dispatch_visit_lev").val(comp.comp_visit_lev);
			$("#dispatch_visit_record").val(comp.comp_visit_record);
			$("#dispatch_visit_recording").val(comp.comp_visit_recording);
		}
		if (null != dispatch && dispatch != 'null') {
			$("#dispatch_id").val(dispatch.dispatch_id);
			$("#dispatch_time").val(getNowFormatDate(false, dispatch.dispatch_time));
			$("#sl_time").val(getNowFormatDate(false, dispatch.sl_time));
			$("#mtn_detail").val(dispatch.mtn_detail);
			$("#mtn_emergency").val(dispatch.mtn_emergency);
			$("#dispatch_status").val(dispatch.dispatch_status);
			$("#dispatch_handle_id").val(dispatch.dispatch_handle_name);// dispatch.dispatch_handle_id
			$("#dispatch_handle_username").val(dispatch.dispatch_handle_id);
			$("#mtn_type").val(dispatch.mtn_type);
			$("#dispatch_arrive_time").val(getNowFormatDate(false, dispatch.dispatch_arrive_time));
			$("#dispatch_tools").val(dispatch.dispatch_tools);
			$("#dispatch_expense").val(dispatch.dispatch_expense);
			$("#dispatch_result").val(dispatch.dispatch_result);
			$("#dispatch_finish_time").val(getNowFormatDate(false, dispatch.dispatch_finish_time));
			$("#mtn_id").val(dispatch.mtn_id);
			$("#mtn_priority").val(dispatch.mtn_priority);
			$("#dispatch_visit_lev").val(dispatch.dispatch_visit_lev);
			$("#dispatch_visit_record").val(dispatch.dispatch_visit_record);
			$("#dispatch_visit_recording").val(dispatch.dispatch_visit_recording);
			var finishImgUrl = dispatch.finishImgUrl;
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
			if (dispatch.mtn_priority == "1" || dispatch.dispatch_result == "拒绝维修") {
				$("#rejectReason").val(dispatch.rejectReason);
				$("#reject").show();
			} else {
				$("#reject").hide();
			}
		}
		// event_source
		$("#rpt_id").val(list[0].rpt_id);
		$("#rpt_name").val(list[0].rpt_name);
		$("#in_call").val(list[0].in_call);
		$("#address").val(list[0].address);
		// $("#createby").val(list[0].createby);
		$("#createTime").val(getNowFormatDate(false, list[0].createTime));
		var imgurl = list[0].img_url;
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
		$("#event_source").val(list[0].event_source);
		$("#event_source1").val(list[0].event_source);
		$("#event_type").val(list[0].event_type);

		processInstanceId = list[0].processInstanceId;
		if (processInstanceId == null || processInstanceId == '' || processInstanceId == undefined || processInstanceId == 'undefined') {

		} else {
			grapIframe = "./../graph/graphProcessDefinition.app?processDefinitionId=" + processInstanceId + "&numb=" + generateMixed(4);
		}

		$("#grapIframe").attr("src", grapIframe);
		changeMtnPriority();
	});
	$('#myModalVisit').modal();
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

/**
 * 保存数据
 * 
 * @param index
 */
function visitSaveButton(type) {

	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = {
			dispatch_visit_lev : $("#dispatch_visit_lev").val(),
			dispatch_visit_record : $("#dispatch_visit_record").val(),
			dispatch_visit_recording : $("#dispatch_visit_recording").val(),
			rpt_id : $("#rpt_id").val(),
			event_type : $("#event_type").val(),
			type : type
		};
		$.ajax({
			type : "post",
			url : "./../visitnwork/visitlist.app?method=visitSave",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {

				layer.msg('回访成功！', {
					time : 2000
				}, function() {
					$('#visitInfo').bootstrapTable('refresh');
					$('#myModalVisit').modal('hide');
				});

			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！请联系技术人员。', {
					time : 2000
				}, function() {
					$('#myModalVisit').modal('hide');
				})
			}
		});
	}
}

function closeLayer() {
	setTimeout(function() {// IE6、7不会提示关闭
		$('#myModalVisit').modal('hide'); // 执行关闭
	}, 2000);
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

function changeEventType(index) {

	if (index == "0") {
		$("#bxdetail").show();
		$("#tsdetail").hide();
	} else {
		$("#bxdetail").hide();
		$("#tsdetail").show();
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

function getNowFormatDate(flag, vardate) {

	if ((vardate == undefined) || (!flag && (null == vardate || vardate == ''))) {
		return "-";
	}
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

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($('#visitInfo').bootstrapTable('getSelections'), function(row) {
		return row.rpt_id
	});
}

// 初始化下拉框
function initDrops() {
	var url = "./../assigenwork/assignlist.app?method=initDropAll";
	$.post(url, function(data) {
		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
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
				$("<option value='" + detailList[1] + "'>" + detailList[2] + "</option>").appendTo("#event_source1");
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
}

// 初始化下拉框
function initCommDrops() {
	var url = "./../areaPropertyInfo/areaPropertyList.app?method=list";	
	$.post(url, function(data) {		
		var obj = JSON.parse(data);
		var list = obj.rows;
		for(var i=0,len=list.length;i<len;i++){
			$("<option value='" + list[i].communityName + "'>" + list[i].communityName + "</option>").appendTo("#CommNameSearch");
		}
	});
}

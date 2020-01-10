selections = [];
var loginUserName = "";
var loginUserCname = "";

$(function() {
	init();
	initPermissions();
	initImgs();
	initBtnEvent();
});

// 初始化图片列表
function initImgs() {
	var url = getRootPath() + "hotLine.app?method=initImgs";
	console.log(url);

	$.post(url, function(data) {
		
		var list = eval(data);
		console.log(list);
		var htmlStr = "";
		for (var i = 0; i < list.length; i++) {
			var detailList = list[i];
			htmlStr += "<a data-src='" + detailList.imgUrl + "' class='cur'>";
			htmlStr += "<img width='58' height='80' src='" + detailList.imgUrl + "'>";
			htmlStr += "</a>";
		}
		$("#ico-list").html(htmlStr);
		initImgEvent();
	}, "json");
}

function initImgEvent() {

	var ICO = new Object();
	ICO.show = function() {
		$(".pop-ico").fadeIn();
	};

	ICO.hide = function() {
		$(".pop-ico").fadeOut();
	};

	var $box = $(".pop-ico");
	var $showbtn = $(".show-ico-btn");
	var $headico = $("input[name='imgUrl']");
	var $hidebtn = $box.find(".hide-ico-btn");
	var $icolist = $box.find(".ico-list");

	$showbtn.bind("click", ICO.show);
	$hidebtn.bind("click", ICO.hide);

	// 点击选择头像
	$("> a", $icolist).each(function() {
		$(this).bind("click", function() {
			$(this).addClass("cur").siblings().removeClass("cur");
			var src = $(this).find("img").attr("src");
			$showbtn.find("img").attr("src", src);
			$headico.val($(this).attr("data-src"));
			ICO.hide();
		});
	});
}

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
				$('#btHotLineAdd').show();
				clearForm();
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
					var url = getRootPath() + 'hotLine.app?method=delete';
					$.post(url, {
						id : selections + ""
					}, function(data) {
						layer.msg(eval(data), {
							time : 2000
						}, function() {
							$('#hotLineInfo').bootstrapTable('refresh');
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
		$('#hotLineInfo').bootstrapTable({
			url : getRootPath() + 'hotLine.app?method=list', // 请求后台的URL（*）
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
				field : 'id',
				visible : false,
				title : 'ID'
			}, {
				field : 'imgUrl',
				title : '热线图标',
				formatter : function(value, row, index) {
					return '<img  src="' + value + '" width="58" height="80"  >';
				}
			}, {
				field : 'name',
				title : '热线名称'
			}, {
				field : 'telephone',
				title : '热线电话'
			}, {
				field : 'lineType',
				title : '热线类型',
				formatter : function(value, row, index) {
					if (value == 1) {
						return '生活服务';
					} else {
						return '乐湾国际';
					}
				}
			}, {
				field : 'userName',
				title : '操作人'
			}, {
				field : 'userID',
				visible : false,
				title : '操作人ID'
			}, {
				field : 'updateDate',
				title : '更新时间',
				formatter : dateFormate
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
			search : params.search
		// 表格搜索框的值
		};
		return temp;
	};
	dateFormate = function(value) {
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
	var r = $('#hotLineInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
}



/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {

	return $.map($('#hotLineInfo').bootstrapTable('getSelections'), function(row) {
		return row.id;
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
			url : getRootPath() + 'hotLine.app?method=save',
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#hotLineInfo').bootstrapTable('refresh');
					$('#myModal1').modal('hide');
				});
			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！', {
					time : 3000
				}, function() {

				});
			}
		});
	} else {
		layer.msg('表单验证未通过！', {
			time : 2000
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
		imgUrl : $("#imgUrl").val(),
		name : $("#name").val(),
		telephone : $("#telephone").val(),
		lineType : $("#lineType").val(),
		userId : $("#userId").val(),
		updateDate : $("#updateDate").val(),
		id : $("#Id").val()
	};
	return addJson;
}


// 清空表单
function clearForm() {
	$("#focus-ico").attr('src', "");
	$("#Id").val("");
	$("#imgUrl").val("");
	$("#name").val("");
	$("#telephone").val("");
	$("#lineType").val("");
	$("#userName").val(loginUserCname);
	$("#userId").val(loginUserName);
	$("#updateDate").val(json2TimeStamp($.now()));
}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	if (type == 1) {
		$('#name').attr("disabled", true);
		$('#telephone').attr("disabled", true);
		$('#lineType').attr("disabled", true);
		$('#btHotLineAdd').hide();
		$("#myModalTitle").html("查看");
	} else {
		initImgEvent();
		$('#name').attr("disabled", false);
		$('#telephone').attr("disabled", false);
		$('#lineType').attr("disabled", false);
		$('#btHotLineAdd').show();
		$("#myModalTitle").html("编辑");
		// clearForm();
	}
	$("#focus-ico").attr('src', obj.imgUrl);
	$("#Id").val(obj.id);
	$("#imgUrl").val(obj.imgUrl);
	$("#name").val(obj.name);
	$("#telephone").val(obj.telephone);
	$("#lineType").val(obj.lineType);
	$("#userId").val(obj.userId);
	$("#userName").val(obj.userName);
	$("#updateDate").val(json2TimeStamp(obj.updateDate));
	$('#myModal1').modal();
}

selections = [];
var loginUserName = "";
var loginUserCname = "";
var deptId = "";
var deptName = "";

$(function() {

	initPermissions();
	initBtnEvent();
	init();
	initFileInput("fileUp", getRootPath() + "mainFrame/mainFrameInfo.app?method=inputFile&folder=workletter");

	// 定位表格查询框
	$(".search").css({
		'position' : 'fixed',
		'right' : '115px',
		'top' : '0',
		'z-index' : '1000',
		'width' : '240px'
	});
	$(".search input").attr("placeholder", "搜索");
	$('#myForm1').validationEngine();
});

function initPermissions() {
	toolbarBtnInit(); // 初始化工具条按钮
}

function initBtnEvent() {
	for (var i = 0; i < btnIdArray.length; i++) {
		// 新增
		if (btnIdArray[i] == "btn_add") {
			$("#btn_add").bind('click', function() {
				$('#myForm1').validationEngine('hide');
				$("#contractFile").hide();
				$('#btContractAdd').show();
				$("#uploadFile").show();
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
					var url = getRootPath() + "workLetter.app?method=del";

					$.post(url, {
						letIds : selections + ""
					}, function(data) {
						layer.msg(eval(data), {
							time : 2000
						}, function() {
							$('#workLetterInfo').bootstrapTable('refresh');
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
		$('#workLetterInfo').bootstrapTable({
			url : getRootPath() + "workLetter.app?method=list", // 请求后台的URL（*）
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
				field : 'letId',
				visible : false
			}, {
				field : 'deptId',
				visible : false
			}, {
				field : 'operId',
				visible : false
			}, {
				field : 'docUrls',
				// visible : false
				title : 'test'
			}, {
				field : 'deptName',
				title : '所属部门'
			}, {
				field : 'docName',
				title : '工作函名称'
			}, {
				field : 'operName',
				title : '操作人员'
			}, {
				field : 'insertDate',
				title : '签订日期',
				formatter : dateFormate
			}, {
				field : 'updateDate',
				title : '结束日期',
				formatter : dateFormate
			}, {
				field : 'remark',
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
			search : params.search,// 表格搜索框的值
			deptId : deptId

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
	var r = $('#workLetterInfo').bootstrapTable('getSelections');
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
	return $.map($('#workLetterInfo').bootstrapTable('getSelections'), function(row) {
		return row.letId;
	});
}

/**
 * 保存操作
 */
function openSaveButton() {
	// $('#fileUp').fileinput('refresh', {showCaption: false}).fileinput('disable');
	var flag = $('#myForm1').validationEngine('validate');

	if (flag) {

		var addJson = getDataForm();
		$.ajax({
			type : "post",
			url : getRootPath() + "workLetter.app?method=save",
			data : {
				wlInfo : JSON.stringify(addJson)
			},
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#workLetterInfo').bootstrapTable('refresh');
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
		letId : $("#letId").val(),
		docName : $("#docName").val(),
		deptId : deptId,
		operId : loginUserName,
		docUrls : $("#docUrls").val(),
		remark : $("#remark").val(),
		insertDate : $("#insertDate").val() || dateFormat(new Date(), 'yyyy-MM-dd HH:mm:ss'),
		updateDate : dateFormat(new Date(), 'yyyy-MM-dd HH:mm:ss')
	};
	return addJson;
}

// 下载文件
function downloadFile(obj) {
	var fileId = $(obj).attr("fileId");
	var url = getRootPath() + "fileUpload.app?method=download&filepath=" + fileId;
	window.location.href = url;
}
// 删除文件
function delFile(obj) {
	var fileId = $(obj).attr("fileId");
	debugger;
	var docUrl = $("#docUrls").val();
	var url = docUrl.split(";");
	for (var i = 0, len = url.length; i < len; i++) {
		if (url[i] == fileId) {
			url.splice(i, 1);
		}
	}
	$(obj).parent().remove();
	var strUrl = url.join(";");

	$("#docUrls").val(strUrl);
}

// 清空表单
function clearForm() {
	$("#letId").val("");
	$("#docName").val("");
	$("#deptId").val(deptId);
	$("#deptName").val(deptName);
	$("#operId").val(loginUserName);
	$("#operName").val(loginUserCname);
	$("#docUrls").val("");
	$("#remark").val("");
	$("#insertDate").val(dateFormat(new Date(), 'yyyy-MM-dd HH:mm:ss'));
	$("#updateDate").val(dateFormat(new Date(), 'yyyy-MM-dd HH:mm:ss'));
	$('#fileUp').fileinput('clear');
}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	if (type == 1) {
		$('#btContractAdd').hide();
		$("#myModalTitle").html("查看");
		$("#uploadFile").hide();
		$("#docName").attr("readonly", true);
		$("#remark").attr("readonly", true);
	} else {
		$('#btContractAdd').show();
		$("#myModalTitle").html("编辑");
		$("#uploadFile").show();
		$("#docName").attr("readonly", false);
		$("#remark").attr("readonly", false);
		clearForm();
	}

	$("#contractFile").show();

	$("#letId").val(obj.letId);
	$("#docName").val(obj.docName);
	$("#deptId").val(obj.deptId);
	$("#deptName").val(obj.deptName);
	$("#operId").val(obj.operId);
	$("#operName").val(obj.operName);
	$("#docUrls").val(obj.docUrls);
	$("#remark").val(obj.remark);
	$("#insertDate").val(getNowFormatDate(false, obj.insertDate));
	$("#updateDate").val(getNowFormatDate(false, obj.updateDate));

	var docUrl = $("#docUrls").val();
	if (docUrl.length > 0) {
		var img = docUrl.split(";");

		if (img.length > 0) {

			var htmlStr = "<ul class='folder-list' style='padding: 0 20px'>";
			for (var i = 0; i < img.length; i++) {

				var del = "";
				if (type == 2) {
					del = "<span onclick='delFile(this)' fileId='" + img[i] + "' class='delFile'><i class='fa fa-trash' style='color:red;font-size:15px;'></i>删除</span>";
				}
				htmlStr += "<li>" + "<a class='fa fa-file' onclick='downloadFile(this)' fileId='" + img[i] + "'>" + $("#docName").val() + i + "</a>"

				+ del + "</li>";
			}
			htmlStr += "</ul>";
			$("#docFiles").show();
			$("#docFiles").html(htmlStr);
		}
	} else {
		$("#docFiles").hide();
		$("#docFiles").html("");
	}
	$('#myModal1').modal();

}

function initFileInput(ctrlName, uploadUrl) {
	var control = $('#' + ctrlName);
	control.fileinput({
		language : 'zh', // 设置语言
		uploadUrl : uploadUrl, // 上传地址
		uploadAsync : false,
		showPreview : true,
		showUpload : true,
		dropZoneEnabled : true,// 是否显示拖拽区域
		removeFromPreviewOnError : true,// 当选择的文件不符合规则时，例如不是指定后缀文件、大小超出配置等，选择的文件不会出现在预览框中，只会显示错误信息
		allowedPreviewTypes : [ 'image' ],
		allowedFileExtensions : [ 'docx', 'doc', 'pdf' ],// 接收的文件后缀
		maxFileSize : 25000,// 单位为kb，如果为0表示不限制文件大小
		maxFileCount : 3,// 表示允许同时上传的最大文件个数

	}).on('filebatchpreupload', function(event, data, id, index) {

	}).on('filebatchuploadsuccess', function(event, data) {

		var result = JSON.parse(data.response);
		var docUrls = $("#docUrls").val();
		if (result.status == 1) {
			if (docUrls.length > 0) {
				docUrls += ";" + result.data;
				$("#docUrls").attr("value", docUrls);
			} else {
				$("#docUrls").attr("value", result.data);
			}
			layer.msg("上传成功", {
				time : 2000
			}, function() {
			});
		} else {
			layer.msg(result.data, {
				time : 2000
			});
		}
	});

}

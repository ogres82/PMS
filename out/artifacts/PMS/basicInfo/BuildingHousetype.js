selections = [];

$(function() {
	initPermissions();
	init();
	// 定位表格查询框
	/*
	 * $(".search").css({ 'position':'fixed', 'right':'10px', 'top':'0',
	 * 'z-index':'1000', 'width':'240px' }); $(".search input").css({
	 * 'padding-right':'23px' });
	 */
	$('#myForm1').validationEngine();
	initBtnEvent();
	imgManageInput(
			"qualification",
			getRootPath()
					+ "buildingHousetypeInfo/typeList.app?method=inputFile&folder=building_img");
	$('#myForm2').validationEngine();
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
				$('#btHousetypeAdd').show();

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
			$("#btn_del").bind(
					'click',
					function() {
						$('#myForm1').validationEngine('hide');
						selections = getIdSelections();
						urlmethod = "method=deleteBuildingHousetype";
						layer.confirm("您确定要删除所选信息吗?", {
							skin : 'layui-layer-molv',
							btn : [ '确定', '取消' ]
						}, function() {
							var url = getRootPath()
									+ "buildingHousetypeInfo/typeList.app?"
									+ urlmethod;
							$.post(url, {
								typeId : selections + ""
							}, function(data) {
								layer.msg(eval(data), {
									time : 2000
								}, function() {
									$('#buildingHousetypeInfo').bootstrapTable(
											'refresh');
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
		$('#buildingHousetypeInfo').bootstrapTable(
				{
					url : getRootPath()
							+ 'buildingHousetypeInfo/typeList.app?method=list', // 请求后台的URL（*）
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
						field : 'state',
						checkbox : true,
					}, {
						field : 'typeId',
						visible : false,
						title : 'ID'
					}, {
						field : 'typeName',
						title : '户型名称'
					}, {
						field : 'remark',
						title : '备注'
					}, {
						field : 'operate',
						title : '操作',
						align : 'center',
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
		'click #a_img' : function(e, value, row, index) {
			imgManage(row);
		}
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
	return oTableInit;

};

// 表格选择事件
function tableCheckEvents() {
	var r = $('#buildingHousetypeInfo').bootstrapTable('getSelections');
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
	return $.map($('#buildingHousetypeInfo').bootstrapTable('getSelections'),
			function(row) {
				return row.typeId;
			});
}

/**
 * 保存操作
 */
function openSaveButton() {
	$('#myForm1').validationEngine('hide');
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = getDataForm();
		$.ajax({
			type : "post",
			url : getRootPath()
					+ "buildingHousetypeInfo/typeList.app?method=save",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#buildingHousetypeInfo').bootstrapTable('refresh');
					$('#myModal1').modal('hide');
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
		typeId : $("#typeId").val(),
		typeName : $("#typeName").val(),
		remark : $("#remark").val()
	};
	return addJson;
}

// 清空表单
function clearForm() {
	$("#typeId").val("");
	$("#typeName").val("");
	$("#remark").val("");
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
	var currentdate = year + seperator1 + month + seperator1 + strDate + " "
			+ hour + seperator2 + min + seperator2 + sec;
	return currentdate;
}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	if (type == 1) {
		$('#btHousetypeAdd').hide();
		$("#myModalTitle").html("查看");
	} else {
		$('#btHousetypeAdd').show();
		$("#myModalTitle").html("编辑");
	}
	$("#typeId").val(obj.typeId);
	$("#typeName").val(obj.typeName);
	$("#remark").val(obj.remark);

	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});
}

// 初始化fileinput控件
function imgManageInput(ctrlName, uploadUrl) {
	var control = $('#' + ctrlName);

	$("#imgManage").fileinput({
		uploadUrl : uploadUrl, // server upload action
		uploadAsync : false,
		showPreview : true,
		showUpload : false,
		allowedFileExtensions : [ 'jpg', 'png' ],
		maxFileCount : 1,
		elErrorContainer : '#kv-error-4'
	}).on('filebatchpreupload', function(event, data, id, index) {
		// $('#kv-success-4').html('<h4>Upload Status</h4><ul></ul>').hide();
	}).on('filebatchuploadsuccess', function(event, data) {
		var msg = eval('(' + data.response + ')');
		if (msg.status == 1) {
			$("#imgPath").val(msg.data);
			layer.msg("上传成功", {
				time : 2000
			}, function() {

			});
		} else {
			layer.msg(msg.message, {
				time : 2000
			});
		}
	}).on("filebatchselected", function(event, files) {
		$("#imgManage").fileinput("upload");
	});
	;
}
// 这个地方在util里面已经有了就不用在这个地方写了

// /**
// * 获取项目根路径：http://localhost:8083/proj
// */
// function getRootPath(){
// //获取当前网址，如： http://localhost:8083/proj/meun.jsp
// var curWwwPath = window.document.location.href;
// //获取主机地址之后的目录，如： proj/meun.jsp
// var pathName = window.document.location.pathname;
// var pos = curWwwPath.indexOf(pathName);
// //获取主机地址，如： http://localhost:8083
// var localhostPath = curWwwPath.substring(0, pos);
// //获取带"/"的项目名，如：/proj
// var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);
// return(localhostPath + projectName);
// }

function deleteImg(obj) {
	layer.confirm("您确定要删除吗?", {
		skin : 'layui-layer-molv',
		btn : [ '确定', '取消' ]
	}, function(index) {
		var url = getRootPath()
				+ "buildingHousetypeInfo/typeList.app?method=delImg";
		$.post(url, {
			imgId : $(obj).attr("imgId")
		}, function(data) {

		});
		$(obj).parent().parent().remove();
		layer.close(index);
	}, function() {
		return;
	});
}

function viewImg(obj) {
	$("#bigImg").attr("src", $(obj).attr("url"));
	$("#viewImgTitle").html($(obj).attr("name"));
	$("#viewImg").modal({
		backdrop : 'static',
		keyboard : false
	});
}

function imgManage(obj) {
	$('#myForm2').validationEngine('hide');
	$("#imgName").val("");
	$("#imgPath").val("");
	$("#imgManage").fileinput('clear');
	$("#imgTypeId").val(obj.typeId);

	var url = getRootPath()
			+ "buildingHousetypeInfo/typeList.app?method=getImgs";
	$
			.post(
					url,
					{
						typeId : obj.typeId
					},
					function(data) {
						if (data != "[]") {
							var result = eval(data);
							var element = '<div class="form-group form-group-sm "></div>';
							for (var i = 0; i < result.length; i++) {

								var url = getRootPath() + result[i].imgUrl;
								var path = result[i].imgPath;
								path = path
										.substring(path.lastIndexOf("/") + 1);
								element += '<div class="file-box"> '
										+ '<div class="file"> '
										+ '<a> '
										+ ' <span class="corner"></span> '
										+

										' <div class="image">'
										+ ' <a onclick="viewImg(this)" url="'
										+ url
										+ '" name="'
										+ path
										+ '"> '
										+ ' 	<img alt="image" class="img-responsive" src="'
										+ url
										+ '">'
										+ ' </a>'
										+ ' </div>'
										+ '<div class="file-name" style="overflow:hidden;">'
										+ '<br><small>'
										+ result[i].imgName
										+ '</small>'
										+ '<a class="delimg pull-right" imgId="'
										+ result[i].imgId
										+ '" onclick="deleteImg(this)" style="margin-left:40px;color:#CD6889;font-size:15px;"><i class="fa fa-trash-o"></i></a>'
										+ ' </div>' + ' </a>' + '</div>'
										+ '</div>';
							}
							$("#imgs").html(element);
						} else {
							if ($("#imgs").length > 0) {
								$("#imgs")
										.html(
												'<div style="line-height:50px">无户型图</div>');
							}
						}

					});
	$('#imgManageModal').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/**
 * 图片管理保存操作
 */
function openSaveImgButton() {
	var flag = $('#myForm2').validationEngine('validate');
	if (flag) {
		var imgName = $("#imgName").val();
		var imgPath = $("#imgPath").val();
		if (imgPath == "" || imgPath == null) {
			layer.msg('未上传图片！', {
				time : 2000
			});
			return false;
		}
		var typeId = $("#imgTypeId").val();
		var addJson = {
			imgName : imgName,
			imgPath : imgPath,
			typeId : typeId
		}

		$.ajax({
			type : "post",
			url : getRootPath()
					+ "buildingHousetypeInfo/typeList.app?method=saveImg",
			data : addJson,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg("保存成功", {
					time : 2000
				}, function() {
					$('#imgManageModal').modal('hide');
				});
			}
		});
	} else {
		layer.msg('表单验证未通过！', {
			time : 2000
		});
	}
}

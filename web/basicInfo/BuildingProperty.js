selections = [];

$(function() {
	initPermissions();
	$.ajaxSetup({
		async : false
	});
	init();
	initDrops();
	initAllAreaDrop();
	initHouseTypeDrop();
	// 定位表格查询框
	/*
	 * $(".search").css({ 'position':'fixed', 'right':'10px', 'top':'0',
	 * 'z-index':'1000', 'width':'240px' }); $(".search input").css({
	 * 'padding-right':'23px' });
	 */
	$('#myForm1').validationEngine();
	initBtnEvent();
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
				$('#btBuildingPropertyAdd').show();

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
			$("#btn_del")
					.bind(
							'click',
							function() {
								$('#myForm1').validationEngine('hide');
								selections = getIdSelections();

								urlmethod = "method=deleteBuildingProperty";

								layer
										.confirm(
												"您确定要删除所选信息吗?",
												{
													skin : 'layui-layer-molv',
													btn : [ '确定', '取消' ]
												},
												function() {
													var url = getRootPath()
															+ "buildingPropertyInfo/buildingPropertyList.app?"
															+ urlmethod;
													$
															.post(
																	url,
																	{
																		storiedBuildId : selections
																				+ ""
																	},
																	function(
																			data) {
																		layer
																				.msg(
																						eval(data),
																						{
																							time : 2000
																						},
																						function() {
																							$(
																									'#buildingPropertyInfo')
																									.bootstrapTable(
																											'refresh');
																							$(
																									'#myModal1')
																									.modal(
																											'hide');
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
		$('#buildingPropertyInfo')
				.bootstrapTable(
						{
							url : getRootPath()
									+ 'buildingPropertyInfo/buildingPropertyList.app?method=list', // 请求后台的URL（*）
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
								field : 'storiedBuildId',
								visible : false,
								title : 'ID'
							}, {
								field : 'belongCommId',
								visible : false,
								title : 'belongCommId'
							}, {
								field : 'build_id',
								visible : false,
								title : 'build_id'
							}, {
								field : 'lzId',
								visible : false,
								title : 'lzId'
							}, {
								field : 'storiedBuildName',
								title : '楼宇名称'
							}, {
								field : 'storiedTypeName',
								title : '楼宇类别'
							}, {
								field : 'houseTypeName',
								title : '楼宇户型'
							}, {
								field : 'community_name',
								title : '所属小区'
							}, {
								field : 'build_name',
								title : '所属楼盘'
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
	var r = $('#buildingPropertyInfo').bootstrapTable('getSelections');
	if (r.length == 0) {
		$("#btn_del").attr("disabled", true);
	}
	if (r.length == 1) {
		$("#btn_del").attr("disabled", false);
	}
}

// 初始化下拉框
function initDrops() {
	var url = getRootPath()
			+ "buildingPropertyInfo/buildingPropertyList.app?method=initDropAll";
	$.post(url, function(data) {
		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			var code = detailList[0];
			// 员工状态
			if (code == 'storied_type') {
				$(
						"<option value='" + detailList[1] + "'>"
								+ detailList[2] + "</option>").appendTo(
						"#storiedType");
			}
		}
		;
	});

}

/**
 * 初始化楼盘下拉框
 */
function initAllAreaDrop() {
	var url = getRootPath()
			+ "buildingPropertyInfo/buildingPropertyList.app?method=initAllAreaDrop";
	$.post(url, function(data) {

		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			$(
					"<option value='" + detailList.buildId + "'>"
							+ detailList.buildName + "</option>").appendTo(
					"#build_id");
		}
		;
	});
}

/**
 * 初始化小区下拉框
 */
function initAreaPropertyDrop(buildId) {
	var url = getRootPath()
			+ "buildingPropertyInfo/buildingPropertyList.app?method=initAreaPropertyDrop";
	$.post(url, {
		buildId : buildId
	}, function(data) {

		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];

			$(
					"<option value='" + detailList.communityId + "'>"
							+ detailList.communityName + "</option>").appendTo(
					"#belongCommId");

		}
		;
	});
}

/**
 * 初始化户型
 */
function initHouseTypeDrop() {
	var url = getRootPath()
			+ "buildingPropertyInfo/buildingPropertyList.app?method=initHouseTypeDrop";
	$.post(url, function(data) {
		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			$(
					"<option value='" + detailList.typeId + "'>"
							+ detailList.typeName + "</option>").appendTo(
					"#houseTypeId");
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
	return $.map($('#buildingPropertyInfo').bootstrapTable('getSelections'),
			function(row) {
				return row.storiedBuildId;
			});
}

/**
 * 保存操作
 */
function openSaveButton() {
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var addJson = getDataForm();
		$
				.ajax({
					type : "post",
					url : getRootPath()
							+ "buildingPropertyInfo/buildingPropertyList.app?method=save",
					data : addJson,
					dataType : "json",
					async : false,
					success : function(data) {
						layer.msg('操作成功！', {
							time : 2000
						}, function() {
							$('#buildingPropertyInfo')
									.bootstrapTable('refresh');
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

/**
 * 获取表单数据
 * 
 * @returns {___anonymous7092_7851}
 */
function getDataForm() {

	var addJson = {
		storiedBuildId : $("#storiedBuildId").val(),
		belongCommId : $("#belongCommId").val(),
		houseTypeId : $("#houseTypeId").val(),
		storiedBuildName : $("#storiedBuildName").val(),
		remark : $("#remark").val(),
		storiedType : $("#storiedType").val(),
		lzId : $("#lzId").val()
	};
	return addJson;
}

/**
 * 前排按钮监控
 * 
 * @param type
 */
function openButtonWindow(type) {
	$('#myForm1').validationEngine('hide');
	$('#btBuildingPropertyAdd').show();
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
		var storiedBuildId = 0;
		selections = getIdSelections();
		storiedBuildId = selections[0];
		var urlmethod = "";
		// 新增
		if (type == 1) {
			urlmethod = "method=addBuildingProperty";
			$("#myModalTitle").html("新建");
		} else if (type == 4) {
			// 删除
			urlmethod = "method=deleteBuildingProperty";
		}

		if (type == 4) {
			layer.confirm("您确定要删除所选信息吗?", {
				skin : 'layui-layer-molv',
				btn : [ '确定', '取消' ]
			}, function() {
				var url = getRootPath()
						+ "buildingPropertyInfo/buildingPropertyList.app?"
						+ urlmethod;
				$.post(url, {
					storiedBuildId : selections + ""
				}, function(data) {
					layer.msg(eval(data), {
						time : 2000
					}, function() {
						$('#buildingPropertyInfo').bootstrapTable('refresh');
						$('#myModal1').modal('hide');
					});
				});
			}, function() {
				return;
			})
		} else if (type == 1) {
			clearForm();
			$('#myModal1').modal({
				backdrop : 'static',
				keyboard : false
			});
		}
	}
}

// 清空表单
function clearForm() {
	$("#build_id").val("");
	$("#lzId").val("");
	$("#storiedBuildId").val("");
	$("#storiedBuildName").val("");
	$("#belongCommId").val("");
	$("#storiedType").val("");
	$("#houseTypeId").val("");
	$("#remark").val("");
}

function changeAllArea(obj) {
	$('#belongCommId option').each(function() {
		var str = $(this).text();
		if (str.indexOf("请选择") < 0) {
			$(this).remove();
		}
	});
	var buildId = $(obj).val();
	if (buildId == "") {
		$("#belongCommId").attr("disabled", true);
	} else {
		initAreaPropertyDrop(buildId);
		$('#belongCommId').removeAttr("disabled");
	}
}

// 查看和编辑
function editOrCheck(obj, type) {
	$('#myForm1').validationEngine('hide');
	if (type == 1) {
		$('#btBuildingPropertyAdd').hide();
		$("#myModalTitle").html("查看");
	} else {
		$('#btBuildingPropertyAdd').show();
		$("#myModalTitle").html("编辑");
	}
	initAreaPropertyDrop("");
	$("#belongCommId").attr("disabled", true);

	$("#build_id").val(obj.build_id);
	$("#lzId").val(obj.lzId);
	$("#storiedBuildId").val(obj.storiedBuildId);
	$("#storiedBuildName").val(obj.storiedBuildName);
	$("#belongCommId").val(obj.belongCommId);
	$("#storiedType").val(obj.storiedType);
	$("#houseTypeId").val(obj.houseTypeId);
	$("#remark").val(obj.remark);

	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});

}

function imgManage(obj) {
	if (obj.houseTypeId != "" && obj.houseTypeId != null
			&& typeof (obj.houseTypeId) != "undefined") {
		var url = getRootPath()
				+ "buildingHousetypeInfo/typeList.app?method=getImgs";
		$
				.post(
						url,
						{
							typeId : obj.houseTypeId
						},
						function(data) {
							if (data != "null") {
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
											+ '<br><small>' + result[i].imgName
											+ '</small>' + ' </div>' + ' </a>'
											+ '</div>' + '</div>';
								}
								$("#imgs").html(element);
							} else {
								if ($("#imgs").length > 0) {
									$("#imgs").html("");
								}
							}

						});
		$('#imgManageModal').modal({
			backdrop : 'static',
			keyboard : false
		});
	} else {
		layer.msg("没有户型图", {
			time : 2000
		});
	}
}

function viewImg(obj) {
	$("#bigImg").attr("src", $(obj).attr("url"));
	$("#viewImgTitle").html($(obj).attr("name"));
	$("#viewImg").modal({
		backdrop : 'static',
		keyboard : false
	});
}


selections = [];

$(function() {
	init();
	initDrops();// 初始化下拉框
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","输入关键字");
	$('#orderForm').validationEngine();
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
	$(".summernote").summernote({
		lang : "zh-CN"
	});
	var i = 0;
	$("#moreSearch")
			.bind(
					'click',
					function() {
						$("#more_search")
								.slideToggle(
										"slow",
										function() {
											if (i == 0) {
												i = 1;
												$("#moreSearch")
														.html(
																"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收起   <i class='fa fa-angle-up'></i>");
											} else {
												i = 0;
												$("#moreSearch")
														.html(
																"更多查询    <i class='fa fa-angle-down'></i>");
											}
										})
					});
};

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#itmsFileInfo')
				.bootstrapTable(
						{
							url : './../assertStockManage/showSItmsFilesInfo.app?method=getItmsFilesInfo', // 请求后台的URL（*）
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
							clickToSelect : true,
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
								field : 'item_id',
								checkbox : true,
								align : 'center',
								valign : 'middle'
							}, {
								field : 'bar_code',
								title : '物品条码',
								align : 'center',
								valign : 'middle',
								sortable : true
							// formatter: rpt_name
							}, {
								field : 'item_code',
								title : '物品编码',
								sortable : true,
								editable : true,
								align : 'center'
							}, {
								field : 'item_name',
								title : '物品名称',
								sortable : true,
								align : 'center',
							}, {
								field : 'type_name',
								title : '物品类型',
								sortable : true,
								align : 'center',
							}, {
								field : 'suppliy_num',
								title : '现有库存',
								sortable : true,
								align : 'center',
							} ]
						});
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

/**
 * 前排按钮监控
 * 
 * @param type
 */
function openButtonWindow(type) {
	initClassifi();
	$('#btAdditmsFiles').removeAttr("disabled");
	var isOpen = false;
	selections = getIdSelections();
	var length = selections.length;
	if (type == 1 || type == 4) {
		isOpen = true;
	} else {
		if (length == 0 || length == 1) {
			isOpen = true;
		} else {

			layer.alert("只能选择一条记录操作", {
				skin : 'layui-layer-molv'
			});

		}
	}

	if (isOpen) {
		var bar_code = 0;
		selections = getIdSelections();
		bar_code = selections[0];
		var urlmethod = "";
		// 新增
		if (type == 1) {
			cleanData();
			urlmethod = "method=add";
		} else if (type == 2) {
			showEdit();
			// 修改
			urlmethod = "method=preQuaryItmsFilese&bar_code=" + bar_code;
		} else if (type == 3) {
			// 查看
			showInfo();
			urlmethod = "method=preQuaryItmsFilese&bar_code=" + bar_code;
		} else if (type == 4) {
			// 删除
			urlmethod = "method=deleteItmsFiles";
			// alert(urlmethod);
		}

		if (type == 4) {
			layer
					.confirm(
							"您确定要删除所选信息吗?",
							{
								skin : 'layui-layer-molv',
								btn : [ '确定', '取消' ]
							},
							function() {
								var url = "./../assertStockManage/showSItmsFilesInfo.app?"
										+ urlmethod;
								var deleteIds = JSON.stringify(selections);
								$
										.post(
												url,
												{
													bar_codes : deleteIds
												},
												function(data) {
													layer
															.alert(
																	data,
																	{
																		skin : 'layui-layer-molv'
																	})
													setTimeout(
															function() {// IE6、7不会提示关闭
																window.location.href = "./../assertStockManage/itmsFiles.jsp"
															}, 2000);

												});
							}, function() {
								// return;
							})
		} else if (type == 2 || type == 3) {
			if (bar_code != undefined && bar_code.length > 0) {
				var url = "./../assertStockManage/showSItmsFilesInfo.app?"
						+ urlmethod;
				$.post(url, function(data) {

					var obj = eval(data);
					$("#item_id").val(obj.item_id);
					$("#item_code").val(obj.item_code);
					$("#bar_code").val(obj.bar_code);
					$("#item_name").val(obj.item_name);
					$("#item_type").val(obj.item_type);
					$("#item_unit").val(obj.item_unit);
					$("#stock_uplimit").val(obj.stock_uplimit);
					$("#stock_lowerlimit").val(obj.stock_lowerlimit);
					$("#defu_inprice").val(obj.defu_inprice);
					$("#defu_outprice").val(obj.defu_outprice);
					$("#stock_avgprice").val(obj.stock_avgprice);
					$("#item_flag").val(obj.item_flag);
					$("#item_Ptype").val(obj.item_Ptype);

				});
				if (type == 3) {
					$("#btAdditmsFiles").hide();

				}else
				{
					$("#btAdditmsFiles").show();
				}
				$('#myModal1').modal();
			} else {
				layer.alert("请选择一条记录操作", {
					skin : 'layui-layer-molv'
				});
				return;
			}
		} else if (type == 1) {
			var url = "./../assertStockManage/showSItmsFilesInfo.app?"+ urlmethod;
			$.post(url, function(data) {
				var obj = eval(data);
				$("#bar_code").val(obj.bar_code)
			});

			$('#myModal1').modal();

		}
	}

}
//查看效果
function showInfo()
{
	$('#btAssigenAdd').attr("disabled", "true");
	$('#item_name').attr("disabled", "true");
	$('#item_Ptype').attr("disabled", "true");
	$('#defu_outprice').attr("disabled", "true");
	$('#item_type').attr("disabled", "true");
	$('#suppliy_num').attr("disabled", "true");
	$('#item_unit').attr("disabled", "true");
	$('#stock_lowerlimit').attr("disabled", "true");
	$('#stock_uplimit').attr("disabled", "true");
	$('#defu_inprice').attr("disabled", "true");

}

//编辑效果
function showEdit()
{
	$('#btAssigenAdd').attr("disabled", false);
	$('#item_name').attr("disabled", false);
	$('#item_Ptype').attr("disabled", false);
	$('#defu_outprice').attr("disabled", false);
	$('#item_type').attr("disabled", false);
	$('#suppliy_num').attr("disabled", false);
	$('#item_unit').attr("disabled", false);
	$('#stock_lowerlimit').attr("disabled", false);
	$('#stock_uplimit').attr("disabled", false);
	$('#defu_inprice').attr("disabled", false);

}
//清楚数据
function cleanData()
{
	$('#btAssigenAdd').attr("disabled", false);
	$('#item_name').val("");
	$('#item_Ptype').val("");
	$('#defu_outprice').val("");
	$('#item_type').val("");
	$('#suppliy_num').val("");
	$('#item_unit').val("");
	$('#stock_lowerlimit').val("");
	$('#stock_uplimit').val("");
	$('#defu_inprice').val("");


}


function openSaveButton() {
	var flag = $('#myForm1').validationEngine('validate');
	if(flag){
	$("#btAddsupplietype").attr("disabled", "true");
	var addJson = getDataForm();
	$
			.ajax({
				type : "post",
				url : "./../assertStockManage/showSItmsFilesInfo.app?method=save",
				data : addJson,
				dataType : "json",
				async : false,
				success : function(data) {
					if (data == "保存成功"||data =="修改成功") {
						layer.alert(data, {
							skin : 'layui-layer-molv'
						});
						setTimeout(
								function() {// IE6、7不会提示关闭
									window.location.href = "./../assertStockManage/itmsFiles.jsp"
								}, 2000);

					} else {
						layer.msg('<font coler="red">操作失败！联系管理员</font>', 2, 1,
								function() {
									alert("bb");
								});
					}

					closeLayer();

				},
				failure : function(xhr, msg) {

				}
			});
	}
}

function getDataForm() {
	var addJson = {
		item_id : $("#item_id").val(),
		item_code : $("#item_code").val(),
		bar_code : $("#bar_code").val(),
		item_name : $("#item_name").val(),
		item_type : $("#item_type").val(),
		item_unit : $("#item_unit").val(),
		stock_uplimit : $("#stock_uplimit").val(),
		stock_lowerlimit : $("#stock_lowerlimit").val(),
		defu_inprice : $("#defu_inprice").val(),
		defu_outprice : $("#defu_outprice").val(),
		item_code : $("#item_code").val(),
		item_flag : $("#item_flag").val(),
		item_Ptype : $("#item_Ptype").val()
	};
	return addJson;
}

/**
 * 选择框
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($('#itmsFileInfo').bootstrapTable('getSelections'), function(
			row) {
		return row.bar_code;
	});
}

// 初始化物品类别
function initDrops()
{
	var url = "./../assertStockManage/showSupplieTyeList.app?method=initDropAll";
	$.post(url, function(data) {
		var list = eval(data);
		for ( var j = 0; j < list.length; j++) 
		{
			 var code = list[j].code;
			 if(code=="item_type")
			 {
				$("<option value='" + list[j].id + "'>"+ list[j].code_detail_name + "</option>").appendTo("#item_Ptype");
			 }
			 if(code=="item_unit")
			{
				 $("<option value='" + list[j].id + "'>"+ list[j].code_detail_name + "</option>").appendTo("#item_unit");
			}
	    }
	});
	
}


function showChild(obj)
{
		
	$('#item_type option').each(function(){
		var str = $(this).text();
		if(str.indexOf("请选择")<0)
		{
			$(this).remove();
		}
	});
	
	var Pid = $(obj).val();
	if(Pid==""){
		$('#item_Ptype').attr("disabled",true);
	}else{
		initChildPropertyDrop(Pid);
		$('#item_type').removeAttr("disabled");
	}
	
	
	

}

function initChildPropertyDrop(Pid)
{
	var url = "./../assertStockManage/showSItmsFilesInfo.app?method=findSuppTypInfo&parent_supp_id="+Pid;
	$.post(url, function(data) {
		var list = eval(data);
		for ( var j = 0; j < list.length; j++)
		{
				$("<option value='" + list[j].supply_code + "'>"+ list[j].type_name + "</option>").appendTo("#item_type");
		}
	});


}

function initClassifi()
{
	
	var url = "./../assertStockManage/showSupplieTyeList.app?method=querySuppTypeList";
	$.post(url, function(data) {
		var list = eval(data);
		for ( var j = 0; j < list.length; j++)
		{
				$("<option value='" + list[j].supply_code + "'>"+ list[j].type_name + "</option>").appendTo("#item_type");
		}
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
		// buttonreresh();
	}, 2000);
}

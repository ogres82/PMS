selections = [];

$(function() {
	init();
	initDrops();// 初始化下拉框
	initDropsStock();
	initform();
	
	
});

function initform()
{
	$("#suppliy_code").attr("disabled","true");
	$("#other").attr("disabled","true");
}


/**
 * 刷新数据表单
 */
function buttonreresh() {
	$('#refresh').click();
}

function init() {

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
							url : './../assertStockManage/voucherShowList.app?method=getTempDatas', // 请求后台的URL（*）
							toolbar : '#toolbar', // 工具按钮用哪个容器
							striped : false,
							search : false,
							searchOnEnterKey : false,
							showColumns : false, // 是否显示所有的列
							showRefresh : false, // 是否显示刷新按钮
							showToggle : false, // 是否显示详细视图和列表视图的切换按钮
							sortable : false,
							sortOrder : "asc",
							sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
							cache : false,
							clickToSelect : true,
							minimumCountColumns : 2, // 最少允许的列数
							buttonsAlign : "left",
							showPaginationSwitch : false,
							pagination : true,
							pageNumber : 1, // 初始化加载第一页，默认第一页
							pageSize : 10, // 每页的记录行数（*）
							pageList : '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
							columns : [
							{
								field : 'instock_id',
								title : '物品ID',
								align : 'center',
								valign : 'middle',
								visible:false
							},{
								field : 'item_code',
								title : '物品编码',
								sortable : true,
								align : 'center'
							}, {
								field : 'item_name',
								title : '物品名称',
								align : 'center',
								valign : 'middle',
								sortable : true
							// formatter: rpt_name
							}, {
								field : 'item_type',
								title : '物品型号',
								sortable : true,
								align : 'center',
								visible:false
							}, {
								field : 'type_name',
								title : '物品类型',
								sortable : true,
								align : 'center',
							}, {
								field : 'unit_price',
								title : '单价',
								sortable : true,
								align : 'center',
							}, {
								field : 'add_num',
								title : '出库数量',
								sortable : true,
								editable : true,
								align : 'center'
							}, {
								field : 'suppliy_num',
								title : '现有数量',
								sortable : true,
								align : 'center'
							}, {
								field : 'stock_lowerlimit',
								title : '库存下限',
								sortable : true,
								align : 'center'
							}, {
								field : 'stock_uplimit',
								title : '库存上限',
								sortable : true,
								align : 'center'
							}, {
								field : 'oper_name',
								title : '操作者',
								sortable : true,
								align : 'center'
							} , {
								field : 'oper_id',
								title : '',
								sortable : true,
								align : 'center',
								visible:false
									
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
	$('#btAdditmsFiles').removeAttr("disabled");
		if (type == 2) {
			childTable();
			$('#myModal1').modal();
		}
}

function childTable() {
	$('#test11').bootstrapTable({
		url : './../assertStockManage/voucherShowList.app?method=getItemFiles', // 请求后台的URL（*）
		striped : false,
		search : true,
		searchOnEnterKey : false,
		showColumns : false, // 是否显示所有的列
		showRefresh : false, // 是否显示刷新按钮
		showToggle : false, // 是否显示详细视图和列表视图的切换按钮
		sortable : false,
		sortOrder : "asc",
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		cache : false,
		clickToSelect : true,
		minimumCountColumns : 2, // 最少允许的列数
		buttonsAlign : "left",
		showPaginationSwitch : false,
		pagination : true,
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pageSize : 10, // 每页的记录行数（*）
		pageList : '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
		columns : [ {
			field : 'instock_id',
			checkbox : true,
			align : 'center',
			valign : 'middle'
		}, {
			field : 'item_name',
			title : '物资名称',
			sortable : true,
			align : 'center'
		}, {
			field : 'item_code',
			title : '物品编码',
			sortable : true,
			align : 'center'
		}, {
			field : 'item_type',
			title : '物品类型',
			align : 'center',
			valign : 'middle',
			sortable : true,
		    visible:false
		}, {
			field : 'type_name',
			title : '物品类型',
			align : 'center',
			valign : 'middle',
			sortable : true
		// formatter: rpt_name
		}, {
			field : 'suppliy_num',
			title : '现有数量',
			sortable : true,
			align : 'center',
		}, {
			field : 'item_unit',
			title : '单位',
			sortable : true,
			align : 'center',
			visible:false
		}, {
			field : 'stock_uplimit',
			title : '库存上限',
			sortable : true,
			align : 'center',
		}, {
			field : 'stock_lowerlimit',
			title : '库存下限',
			sortable : true,
			align : 'center',
		}, {
			field : 'defu_inprice',
			title : '单价',
			sortable : true,
			align : 'center',
		}, {
			field : 'defu_outprice',
			title : '出库价',
			sortable : true,
			align : 'center',
		}

		]
	});

}

function openSaveButton() {
	$("#btAddsupplietype").attr("disabled", "true");
	var addJson = getDataForm();
	$.ajax({
				type : "post",
				url : "./../assertStockManage/showSItmsFilesInfo.app?method=save",
				data : addJson,
				dataType : "json",
				async : false,
				success : function(data) {
					if (data == "success") {
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

function getDataForm() {
	
	var voucher_code = $("#voucher_code").val();
	if (voucher_code == "") {
		layer.alert("请新增单据", {
			skin : 'layui-layer-molv'
		});
		return;
	} else {
		
		
		//var suppliy_code=$("#suppliy_code").val();
		var stock=$("#owne_stock").val();
		selections = getIdSelections();
		if(stock=="")
		 {
		    	layer.alert("所属库存必填", {
					skin : 'layui-layer-molv'
				});
		    	return;	
		 }
		/*if(suppliy_code=="")
	    {
	    	layer.alert("供应商必填", {
				skin : 'layui-layer-molv'
			});
	    	return;
	    }*/
	   
		if(selections=="")
		{
			layer.alert("入库明细必选", {
				skin : 'layui-layer-molv'
			});
	    	return;	
			
		}
		
		var addJson = {
			voucher_code : $("#voucher_code").val(),
			occurren_date : $("#occurren_date").val(),
			owne_stock : $("#owne_stock").val(),
			t_handler : $("#t_handler").val(),
			suppliy_code : "",//$("#suppliy_code").val(),暂时不要
			other : $("#other").val()
			
		};
		if(selections!=""){
			
			var instock=selections;
			var j=0;
			for(var i=0;i<instock.length;i++)//运算是否下限
			{
				
				var assintNum= parseInt(instock[i].add_num);//出库量量
				var tempNum=parseInt(instock[i].add_num)-parseInt(instock[i].suppliy_num);//现有库存量
				var lowerNum=parseInt(instock[i].stock_lowerlimit);//库存下限
				if(tempNum>0)
				{
					
					layer.alert("物品："+instock[i].item_name+"库存容量已达下限,现有库存量"+instock[i].suppliy_num+" 个,不能按 "+parseInt(instock[i].add_num)+"个出库，请重新选择等量物品", {
						skin : 'layui-layer-molv'
					});
					j=++i;
					return;
				}
				
			}
		if(j>0)
		{
			var tempNum=parseInt(instock[j].stock_uplimit)-parseInt(instock[j].suppliy_num);
			layer.alert(instock[j].item_name+"库存容量已达下限,现有库存量"+instock[j].suppliy_num+" 个", {
				skin : 'layui-layer-molv'
			});
			
			return;	
			
		}else if(j==0){	
			
		// 提交数据
		var url = "./../assertStockManage/outStockShowList.app?method=submitOutStockData";
		var instockJson = JSON.stringify(selections);
		var orderJson= JSON.stringify(addJson);
		$.post(url, {
			instockJson : instockJson,
			orderJson : orderJson
		}, function(data) {
			 layer.alert(data.msg, {
					skin : 'layui-layer-molv'
				});
			setTimeout(function() {// IE6、7不会提示关闭
				window.location.href = "./../assertStockManage/outStock.jsp"
			}, 2000);

		 });
		
		}
	 }
		
		
	}
}

//--------------物品出口copy 后再写单据
function getOutStockOrder()
{
	var flag = "";
	var url = "./../assertStockManage/outStockShowList.app?method=queryOutStockOrder";
	$.post(url, function(data) {
		var obj = eval(data);
		$("#voucher_code").val(obj.voucher_code);
		$("#t_handler").val(obj.t_handler);
		$("#bar_code").val(obj.bar_code);
		$("#item_name").val(obj.item_name);
		$("#occurren_date").val(obj.occurren_date);
		$("#item_type").val(obj.item_type);
		$("#item_unit").val(obj.item_unit);
		$("#stock_uplimit").val(obj.stock_uplimit);
		$("#stock_lowerlimit").val(obj.stock_lowerlimit);
		$("#defu_inprice").val(obj.defu_inprice);
		$("#defu_outprice").val(obj.defu_outprice);
		$("#stock_avgprice").val(obj.stock_avgprice);
		$("#item_flag").val(obj.item_flag);
		flag = obj.voucher_code;
	});	
}

function getItemList() {
	selectionss = getItemIdSelections();
	var arr = JSON.stringify(selectionss);
	var length = selectionss.length;
	$.ajax({
				type : "post",
				url : "./../assertStockManage/voucherShowList.app?method=getTempData&selectionss="
						+ encodeURI(encodeURI(arr)),
				dataType : "json",
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				async : false,
				success : function(data) {
					$('#myModal1').modal('hide'); // 执行关闭
					intMyTable();
				},
				failure : function(xhr, msg) {

				}
			});
	
	if(length>0)
		{
		
		getOutStockOrder();
		showOutStock();
		}

}


function showOutStock()
{
	$("#suppliy_code").attr("disabled",false);
	$("#other").attr("disabled",false);
}

function intMyTable() {
	// 初始化Table
	oTable = new TableInit();
	oTable.Init();
}

/**
 * 选择框
 * 
 * @returns
 */
function getItemIdSelections() {
	return $.map($('#test11').bootstrapTable('getSelections'), function(row) {
		return row;
	});
}

/**
 * 选择框二
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($('#itmsFileInfo').bootstrapTable('getData'), function(
			row) {
		return row;
	});
}

// 初始化供应商下拉框
function initDrops() {
	var url = "./../assertStockManage/showSupplierInfo.app?method=querySupplierInfo";
	$.post(url, function(data) {
		var list = eval(data);
		for ( var j = 0; j < list.length; j++) {
			var detailList = list[j];
			// 供应商
			$(
					"<option value='" + detailList[0] + "'>" + detailList[1]
							+ "</option>").appendTo("#suppliy_code");
		}
		;
	});
	// return rtnValue;
}

// 所属库存
function initDropsStock() {

	var url = "./../assertStockManage/warHouseFilesList.app?method=queryWarHouesInfos";
	$.post(url, function(data) {
		var list = eval(data);
		for ( var j = 0; j < list.length; j++) {
			var detailList = list[j];
			// 供应商
			$(
					"<option value='" + detailList[0] + "'>" + detailList[1]
							+ "</option>").appendTo("#owne_stock");
		}
		;
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

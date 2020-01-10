/**
 * 
 */
$(function() {
	iniRegionList();
	iniCameraTable();
});

/**
 * 初始化监控列表
 * 
 * @returns
 */
function iniCameraTable() {
	$('#cameraTable').bootstrapTable({
		url : './../camera/camera.app?method=cameraList', // 请求后台的URL（*）
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
		clickToSelect : false,
		queryParams : cameraTableQueryParams,
		showExport : false,
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
			field : 'communityName',
			title : '小区名称'
		}, {
			field : 'cameraName',
			title : '监控名称'
		}, {
			field : 'cameraAddress',
			title : '监控地址'
		}, {
			field : 'operate',
			title : '操作',
			align : 'center',
			width : '15%',
			events : operateEvents,
			formatter : operateFormatter
		} ],
	
		// ,
		// events : operateEvents,
		// formatter : operateFormatter
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
}
//表格选择事件
function tableCheckEvents(){
	var r = $('#cameraTable').bootstrapTable('getSelections');
	if(r.length==0){
		$("#btn_del").attr("disabled",true);
	}
	if(r.length==1){
		$("#btn_del").attr("disabled",false);
	}
}

function operateFormatter() {
	var html = '<a id="a_check">编辑 </a>';
	return html;
}
// 操作列的事件
window.operateEvents = {
	'click #a_check' : function(e, value, row, index) {
		clearFrom();
		$("#id").val(row.id);
		$("#name").val(row.cameraName);
		$("#address").val(row.cameraAddress);
		$("#editRegionSelect").val(row.communityId);
		$("#btnCameraAdd").attr("onclick","openSaveButton()");
		$("#myModalTitle").html("编辑");
		$('#myModal1').modal({
			backdrop : 'static',
			keyboard : false
		});
	}
};
function cameraTableQueryParams(params) {
	var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		limit : params.limit, // 页面大小
		offset : params.offset, // 页码
		search : params.search, // 表格搜索框的值
		regionId : $("#regionSelect").find("option:selected").val(),
		searchName : $("#searchName").val()
	};
	return temp; 
}
/**
 * 初始化小区下拉列表
 * 
 * @returns
 */
function iniRegionList() {
	var url = "./../houseProperty/housePropertyList.app?method=initAreaPropertyDrop";
	$.post(url, null, function(data) {
		var list = eval(data);
		for (var j = 0; j < list.length; j++) {
			var detailList = list[j];
			$(
					"<option value='" + detailList.communityId + "'>"
							+ detailList.communityName + "</option>").appendTo(
					"#regionSelect");
			$(
					"<option value='" + detailList.communityId + "'>"
							+ detailList.communityName + "</option>").appendTo(
					"#editRegionSelect");
		}
		;
	});
}
function searchClick() {	 
	$('#cameraTable').bootstrapTable('refresh');
}
function addNewClick() {
	clearFrom();
	$("#btnCameraAdd").attr("onclick","openSaveButton()");
	$("#myModalTitle").html("新增");
	$('#myModal1').modal({
		backdrop : 'static',
		keyboard : false
	});	
}
function deleteClick() {
	var r = $('#cameraTable').bootstrapTable('getSelections');
	var idStr = "";
	if(r.length>0){
		for (var i = 0; i < r.length; i++) {
			idStr += r[i].id+",";
		}
		console.log(idStr);
		$.ajax({
	        type: "post",
	        url:"./../camera/camera.app?method=deleteCamera",
	        data: {idStr:idStr},
	        dataType: "json",
			async : true,
	        success: function(data)
	        {
				if(data=="failed"){
					layer.msg('删除失败！',{time: 2000});
				}else{
					$('#cameraTable').bootstrapTable('refresh');
					layer.msg('删除成功！',{time: 2000});
				}
	        }
	    });
	}
}
//保存操作
function openSaveButton(){
	var flag = $('#myForm1').validationEngine('validate');
	if(flag){
		$.ajax({
	        type: "post",
	        url:"./../camera/camera.app?method=saveCamera",
	        data: getFrom(),
	        dataType: "json",
			async : true,
	        success: function(data)
	        {
				if(data=="failed"){
					layer.msg('保存失败！',{time: 2000});
				}else{
					$('#myModal1').modal('hide');
					$('#cameraTable').bootstrapTable('refresh');
					layer.msg('保存成功！',{time: 2000});
				}
	        }
	    });
	}else{
		layer.msg('表单验证失败！',{time: 2000});
	}
}
//获取表单数据
function getFrom(){
	var community = $("#editRegionSelect").val();
	var id = $("#id").val();
	var name = $("#name").val();
	var address = $("#address").val();
	var addJson = {
			id:id,
			name:name,
			address:address,
			community:community
		};
	return addJson;
}
//清空表单数据
function clearFrom(){
	$("#myForm1").validationEngine("hide");
	$("#editRegionSelect").val("");
	$("#id").val("");
	$("#name").val("");
	$("#address").val("");
}
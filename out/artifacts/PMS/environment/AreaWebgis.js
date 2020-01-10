$(function () {
	initPermissions();
	init();
	initSearch();
	initBtnEvent();
	initLayout();
	$('#myForm1').validationEngine();
	$(document).ajaxStop($.unblockUI);
});

function initLayout(){
	//根据屏幕分辨率设置ibox高度
	var frameHeight = $(window).height()-150+'px';
	$("#ChildFrame").height($(window).height()-150+'px');
	$(".ibox-content").height(frameHeight);
}

function initBlockUI(){
	$.blockUI({
    	message:'<h4>操作中，请稍后...</h4>',
    	css:{
    		border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff',
            zIndex:'100000000000000'
    	}
    }); 
}

function initPermissions(){
	toolbarBtnInit(); //初始化工具条按钮
}

selections = [];
function initBtnEvent(){
    var urlmethod = "";
    for(var i=0;i<btnIdArray.length;i++){
    	//删除
    	if(btnIdArray[i]=="btn_del"){
    		$("#btn_del").bind('click',function(){
    			$('#myForm1').validationEngine('hide');
    			selections = getIdSelections();
    			urlmethod = "method=deleteArea";
    			
    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    				var url="./../areaPlant/areaPlantList.app?"+urlmethod;
    				$.post(url,
    						{areaId:selections+""},
    						function(data){
    							layer.msg(eval(data),{
    								time: 2000
    							}, function(){
    								$('#areaInfo').bootstrapTable('refresh');
    							});
    						});
    			},function(){
    				return;
    			});
    		});
    	}
    }
}

/**
 * 选择数据
 * @returns
 */
function getIdSelections() {
    return $.map($('#areaInfo').bootstrapTable('getSelections'), function (row) {
        return row.areaId;
    });
}

function init(){
    //初始化Table
    new TableInit().Init();
    $('#areaInfo').on('check.bs.table',function(row,data){
    	GoChildPage(data.areaGisId);
    });
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#areaInfo').bootstrapTable({
			url: './../areaPlant/areaPlantList.app?method=list',         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: true,
    		searchOnEnterKey: true,
    		//showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: false,
            queryParams: oTableInit.queryParams,
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: '[10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
    		{
                field: 'state',
                checkbox: true,
            }, {
            	field: 'areaId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'areaName',
            	title: '区域名称'
            }, {
                field: 'areaResPersonName',
                visible:false,
                title: '责任人'
            }, {
                field: 'areaResPerson',
                visible:false,
                title: '责任人'
            }, {
            	field: 'plantTypeNum',
            	visible:false,
                title: '植被类别数量'
            }, {
            	field: 'remark',
            	visible:false,
                title: '备注'
            }, {
            	field: 'operate',
                title: '操作',
                align: 'center',
                width: '40%',
                events: operateEvents,
                formatter: operateFormatter
            }
            ],
            onCheck:function(row,e){
            	tableCheckEvents();
            },
            onUncheck: function(row,e){
            	tableCheckEvents();
            },
            onCheckAll: function(rows){
        		$("#btn_del").attr("disabled",false);
            },
            onUncheckAll: function(rows){
        		$("#btn_del").attr("disabled",true);
            },
            onLoadSuccess: function(rows){
        		$("#btn_del").attr("disabled",true);
            }
		});
	};
	function operateFormatter(value, row, index) {
		return tableBtnInit();
    }
	//操作列的事件
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
        	editOrCheck(row,1);
        },
		'click #a_edit': function (e, value, row, index) {
			editOrCheck(row,2);
	    },
		'click #a_addplant': function (e, value, row, index) {
			editOrCheck(row,3);
	    }
    };
	oTableInit.queryParams = function (params) {
	    var tplant = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search   //表格搜索框的值
	    };
	    return tplant;
	};
	dateFormate = function(value){
		if(value==null || value==""){
		    return "";
		}else{
			return json2Date(value);
		}
		
	};
	
	return oTableInit;
	
};

//表格选择事件
function tableCheckEvents(){
	var r = $('#areaInfo').bootstrapTable('getSelections');
	if(r.length==0){
		$("#btn_del").attr("disabled",true);
	}
	if(r.length==1){
		$("#btn_del").attr("disabled",false);
	}
}

var plantTable = function (){
	var ss = new Object();
	ss.Init = function (){
		$('#plantInfo').bootstrapTable({
			url: './../plant/plantList.app?method=list',         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: true,
    		searchOnEnterKey: false,
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: true,
            queryParams: ss.queryParams,
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: '[10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
    		{
                field: 'state',
                checkbox: true,
            }, {
            	field: 'plantId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'plantName',
            	title: '待选植被名称'
            }, {
                field: 'plantMtnStandard',
                title: '养护标准'
            }, {
                field: 'plantTypeName',
                title: '植被类别'
            }, {
            	field: 'remark',
                title: '备注'
            }
            ]
		});
	};
	
	ss.queryParams = function (params) {
	    var tplant = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search   //表格搜索框的值
	    };
	    return tplant;
	};
	dateFormate = function(value){
		if(value==null || value==""){
		    return "";
		}else{
			return json2Date(value);
		}
		
	};
	
	return ss;
	
};

var areaPlantTable = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#areaPlantInfo').bootstrapTable({
			url: './../areaPlant/areaPlantList.app?method=areaPlantList',         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		showColumns: false,                  //是否显示所有的列
            showRefresh: false,                  //是否显示刷新按钮
            showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
            showRefresh: true,
            cache: false,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            //pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 100,                       //每页的记录行数（*）
            pageList: '[10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
            {
                field: 'state',
                checkbox: true,
            },
    	    {
            	field: 'plantId',
            	visible:false,
            	title: 'plantId'
            }, {
            	field: 'areaId',
            	visible:false,
            	title: 'areaId'
            }, {
            	field: 'plantName',
            	title: '已选植被名称'
            }
            ]
		});
	};
	
	oTableInit.queryParams = function (params) {
	    var tplant = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search,   //表格搜索框的值
	        areaId: $("#areaId").val()
	    };
	    return tplant;
	};
	dateFormate = function(value){
		if(value==null || value==""){
		    return "";
		}else{
			return json2Date(value);
		}
		
	};
	return oTableInit;
};

var areaPlantView = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#areaPlantView').bootstrapTable({
			url: './../areaPlant/areaPlantList.app?method=areaPlantList',         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		showColumns: false,                  //是否显示所有的列
            showRefresh: false,                  //是否显示刷新按钮
            showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
            showRefresh: false,
            cache: false,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: '[10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
    	    {
            	field: 'plantId',
            	visible:false,
            	title: 'plantId'
            }, {
            	field: 'plantName',
            	title: '植被名称'
            }
            ]
		});
	};
	
	oTableInit.queryParams = function (params) {
	    var tplant = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search,   //表格搜索框的值
	        areaId: $("#areaId").val()
	    };
	    return tplant;
	};
	dateFormate = function(value){
		if(value==null || value==""){
		    return "";
		}else{
			return json2Date(value);
		}
		
	};
	
	return oTableInit;
	
};

//查看和编辑
function editOrCheck(obj,type){
	$('#myForm1').validationEngine('hide');
	if(type==1){
		$("#myModalTitle").html("查看");
		$('#btPlantAdd').hide();
		$("#plantadd").hide();
		$("#plantview").show();
		$("#areaResPersonName").attr("disabled",true);
		$("#remark").attr("disabled",true);
	}else{
		$("#myModalTitle").html("编辑");
	}
	$("#myModalTitle").html(obj.plantName);
	
	$("#areaId").val(obj.areaId);
	$("#areaName").val(obj.areaName);
	$("#areaResPerson").val(obj.areaResPerson);
	$("#areaResPersonName").val(obj.areaResPersonName);
	$("#areaGisArea").val(obj.areaGisArea);
	$("#remark").val(obj.remark);
	new areaPlantView().Init();
	$('#areaPlantView').bootstrapTable('refresh');
	
	$('#myModal1').modal({backdrop: 'static', keyboard: false});
}

/**
 * 保存操作
 */
function openSaveButton(){
	var flag = $('#myForm1').validationEngine('validate');
	if(flag){
    	var addJson = getDataForm();
    	initBlockUI();
    	$.ajax({
            type: "post",
            url: "./../areaPlant/areaPlantList.app?method=save",
            data: addJson,
            dataType: "json",
    		async : true,
            success: function(data)
            {
            	layer.msg(data,{
    				time: 2000
    			}, function(){
    				$('#areaInfo').bootstrapTable('refresh');
    				$('#myModal1').modal('hide');
    			});
            }
        });
	}else{
		layer.msg('表单验证未通过！',{
			time: 2000
		});
	}
	
}

/**
 * 获取表单数据
 */
function getDataForm(){
	var areaPlantInfo = $('#areaPlantInfo').bootstrapTable("getData");
	var addJson = {
			areaId:$("#areaId").val(),
			areaGisId:$("#areaGisId").val(),
			areaName:$("#areaName").val(),
			areaResPerson:$("#areaResPerson").val(),
			areaGisArea:$("#areaGisArea").val(),
			remark:$("#remark").val(),
			areaPlantInfo:JSON.stringify(areaPlantInfo)
		};
	return addJson;
}

/*搜索框初始化*/
function initSearch(){
	//房间下拉框
	$("#areaResPersonName").bsSuggest({
	    url: "./../areaPlant/areaPlantList.app?method=bsEmpInfo&keyword=",
	    showHeader: true,
	    allowNoKeyword: true,
	    keyField: 'keyword',
	    getDataMethod: "url",
	    delayUntilKeyup: true,
	    effectiveFields: ["empNo","empName"],
	    effectiveFieldsAlias: {
	    	empNo:"工号",
	    	empName:"姓名"
	    },
	    //预处理
        fnPreprocessKeyword: function(keyword) {
        		//请求数据前，对输入关键字作预处理
        	 return encodeURI(encodeURI(keyword));
    	},
	    processData: function (json) {    // url 获取数据时，对数据的处理，作为 getData 的回调函数
	        var index, len, data = {value: []};
	        if (!json || json.length === 0) {
	            return false;
	        }
	        data.value=json;
	        return data;
	    }
		}).on("onDataRequestSuccess",
		function(e, result) {
		    console.log("onDataRequestSuccess: ", result)
		}).on("onSetSelectValue",
		function(e, keyword, data) {
		    console.log("onSetSelectValue: ", keyword, data);
		    $("#areaResPerson").val(data.empId);
		    $("#areaResPersonName").val(data.empName);
		}).on("onUnsetSelectValue",	
		function(e) {
		    console.log("onUnsetSelectValue")
		});
}

function selectPlant(type){
	if(type==1){
		var r = $('#plantInfo').bootstrapTable('getSelections');
		var repeatPlant = new Array();
		for(var i=0;i<r.length;i++){
			var url="./../areaPlant/areaPlantList.app?method=checkUniquePlant";
			$.ajax({
                type: "post",
                url: url,
                data: {areaId:$("#areaId").val(),plantId:r[i].plantId},
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	if(data==true){
                		var data = {
            				plantId:r[i].plantId,
            				plantName:r[i].plantName
            			}
            			var id = new Array();
            			id.push(r[i].plantId);
            			$('#areaPlantInfo').bootstrapTable('remove', {field: 'plantId', values: id});
            			$('#areaPlantInfo').bootstrapTable('append', data);
    		    	}else{
    		    		repeatPlant.push(r[i].plantName);
    		    	}
                }
            });
		}
		if(repeatPlant.length>0){
			layer.alert("当前选择的["+repeatPlant.join(",")+"]在该区域已添加，请重新选择");
		}
		
	}else{
		var r = $('#areaPlantInfo').bootstrapTable('getSelections');
		var id = new Array();
		for(var i=0;i<r.length;i++){
			var data = {
				plantId:r[i].plantId,
				plantName:r[i].plantName
			}
			id.push(r[i].plantId);
		}
		$('#areaPlantInfo').bootstrapTable('remove', {field: 'plantId', values: id});
	}
}

//子页面调用的父页面后，父页面进行逻辑处理的代码
//functionName:父页面提供给子页面调用的函数名称
//arg：子页面调用父页面时候传的参数
function ChildMessageDeal(functionName, arg) {
	var obj = arg;
	var areaName = obj.Name;
	var areaGisId = obj.GraphicID;
	clearForm();
	$.ajax({
        type: "post",
        url: "./../areaPlant/areaPlantList.app?method=checkUnique",
        data: {areaGisId:areaGisId},
        dataType: "json",
		async : false,
        success: function(data)
        {
        	if(data!=null){
        		$("#areaId").val(data.areaId);
        		$("#areaResPerson").val(data.areaResPerson);
        		$("#areaResPersonName").val("");
        		$("#areaResPersonName").val(data.areaResPersonName);
        		$("#remark").val(data.remark);
        	}
        	$("#remark").attr("disabled",false);
        	$("#areaResPersonName").attr("disabled",false);
        	$("#plantadd").show();
        	$("#plantview").hide();
        	$('#btPlantAdd').show();
        	new plantTable().Init();
        	new areaPlantTable().Init();
        	$('#plantInfo').bootstrapTable('refresh');
        	$('#areaPlantInfo').bootstrapTable('refresh');
        	$("#areaName").val(obj.Name);
        	$("#areaGisId").val(obj.GraphicID);
        	$("#areaGisArea").val(obj.Area.toFixed(3));
        	$("#myModalTitle").html("新增区域");
        	$('#myModal1').modal({backdrop: 'static', keyboard: false});
        }
    });
}

//清空表单
function clearForm(){
	$("#areaId").val("");
	$("#areaName").val("");
	$("#areaResPerson").val("");
	$("#areaResPersonName").val("");
	$("#areaGisId").val("");
	$("#areaGisArea").val("");
	$("#remark").val("");
}

function ShowChildMessage(args) {
    alert("接收到子页面消息：" + args);
}
function GoChildPage(areaGisId) {
    //父页面调用GIS页面的函数方法
    //function：表示子页面提供给父页面调用的函数名称
    //我是父页面：父页面调用时候传递的参数，参数格式具体又api进行确定
    SenMessgaeToChild("locationByGraphicId", {GraphicIds:areaGisId});
}

selections = [];
$(function () {
	initPermissions();
	initBtnEvent();
	init();
	//定位表格查询框
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","搜索");
	$(".search input").css({
		'padding-right':'23px'
	});
	$('#myForm1').validationEngine();
	initSearch();
});

function initPermissions(){
	toolbarBtnInit(); //初始化工具条按钮
}

function initBtnEvent(){
	var urlmethod = "";
	for(var i=0;i<btnIdArray.length;i++){
		//新增
		if(btnIdArray[i]=="btn_add"){
			$("#btn_add").bind('click',function(){
				$('#myForm1').validationEngine('hide');
				$(".plantview").hide();
		    	$('#btPlantAdd').show();
    			clearForm();
    			$("#myModalTitle").html("新增");
    			$('#myModal1').modal({backdrop: 'static', keyboard: false});
    		
			});
		}
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

function init(){
	
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
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
    		showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: false,
            queryParams: oTableInit.queryParams,
            showExport: "true",
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
            	title: '植被区域名称'
            }, {
                field: 'areaResPersonName',
                visible:false,
                title: '责任人'
            }, {
                field: 'areaResPerson',
                title: '责任人'
            }, {
            	field: 'plantTypeNum',
                title: '植被类别数量'
            }, {
            	field: 'remark',
                title: '备注'
            }, {
            	field: 'operate',
                title: '操作',
                align: 'center',
                width: '15%',
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
	            	title: '植被名称'
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
	            ],
	            onCheck:function(row,e){
	            	tableCheckPlant();
	            },
	            onUncheck: function(row,e){
	            	var plantId = new Array();
	    			plantId.push(row.plantId);
	    			$('#areaPlantInfo').bootstrapTable('remove', {field: 'plantId', values: plantId});
	            },
	            onCheckAll: function(rows){
	            	tableCheckPlant();
	            },
	            onUncheckAll: function(rows){
	            	$('#areaPlantInfo').bootstrapTable('removeAll');
	            }
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
	
	function tableCheckPlant(){
		var r = $('#plantInfo').bootstrapTable('getSelections');
		for(var i=0;i<r.length;i++){
			var data = {
					areaId:$("#areaId1").val(),
					plantId:r[i].plantId,
					plantName:r[i].plantName,
					plantNum:0
			}
			var plantId = new Array();
			plantId.push(r[i].plantId);
			$('#areaPlantInfo').bootstrapTable('remove', {field: 'plantId', values: plantId});
			$('#areaPlantInfo').bootstrapTable('append', data);
		}
	}
	
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
	            	field: 'areaId',
	            	visible:false,
	            	title: 'areaId'
	            }, {
	            	field: 'plantName',
	            	title: '植被名称'
	            }, {
	                field: 'plantNum',
	                editable : true,
	                title: '数量'
	            }
	            ]
			});
		};
		
		oTableInit.queryParams = function (params) {
		    var tplant = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		        limit: params.limit,   //页面大小
		        offset: params.offset,  //页码
		        search: params.search,   //表格搜索框的值
		        areaId: $("#areaId1").val()
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
	            	field: 'areaId',
	            	visible:false,
	            	title: 'areaId'
	            }, {
	            	field: 'plantName',
	            	title: '植被名称'
	            }, {
	                field: 'plantNum',
	                editable : true,
	                title: '数量'
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
	
    //毫秒转时间YYYY-MM-DD hh:mm:ss
    function json2TimeStamp(milliseconds){
        var datetime = new Date();
        datetime.setTime(milliseconds);
        var year=datetime.getFullYear();
             //月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
        var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
        var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
        return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
    }
    //毫秒转日期YYYY-MM-DD
    function json2Date(milliseconds){
        var datetime = new Date();
        datetime.setTime(milliseconds);
        var year=datetime.getFullYear();
             //月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        return year + "-" + month + "-" + date;
    }
    
    /**选择框
     * 
     * @returns
     */
    function getIdSelections() {
        return $.map($('#areaInfo').bootstrapTable('getSelections'), function (row) {
            return row.areaId;
        });
    }
    
    function getNameSelections() {
        return $.map($('#areaInfo').bootstrapTable('getSelections'), function (row) {
            return row.areaName;
        });
    }
    
    /**
     * 保存操作
     */
    function openSaveButton(){
    	
    	var flag = $('#myForm1').validationEngine('validate');
    	if(flag){
        	var addJson = getDataForm();
        	
        	$.ajax({
                type: "post",
                url: "./../areaPlant/areaPlantList.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
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
    
    //区域增加植被
    function openSavePlantAreaButton(){
    	
    	var areaPlantInfo = $('#areaPlantInfo').bootstrapTable("getData");
    	var addJson = {
    	    areaId:$("areaId1").val(),
			areaPlantInfo:JSON.stringify(areaPlantInfo)
    	};
    	
    	$.ajax({
            type: "post",
            url: "./../areaPlant/areaPlantList.app?method=saveAreaPlant",
            data: addJson,
            dataType: "json",
    		async : false,
            success: function(data)
            {
            	
            	layer.msg(data,{
    				time: 2000
    			}, function(){
    				$('#areaInfo').bootstrapTable('refresh');
    				$('#myModal2').modal('hide');
    			});
            }
        });
    }
    
    /**
     * 获取表单数据
     * @returns {___anonymous7092_7851}
     */
    function getDataForm(){
    	
    	var addJson = {
    			areaId:$("#areaId").val(),
    			areaName:$("#areaName").val(),
    			areaResPerson:$("#areaResPerson").val(),
    			remark:$("#remark").val()
    		};
    	return addJson;
    }
    
    //清空表单
    function clearForm(){
    	$("#areaId").val("");
		$("#areaName").val("");
		$("#areaResPerson").val("");
		$("#remark").val("");
    }
    
    function getNowFormatDate(flag,vardate) {
    	if(flag == false && (vardate==null || vardate==''))
    		return null;
    	var date;
    	if(flag==true){
        	date = new Date();
    	}else{
    		date=new Date(vardate);
    	}
        var seperator1 = "-";
        var seperator2 = ":";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var hour=date.getHours();       //获取当前小时数(0-23)
        var min=date.getMinutes();     //获取当前分钟数(0-59)
        var sec=date.getSeconds();     //获取当前秒数(0-59)
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
        }if (sec >= 0 && sec <= 9) {
        	sec = "0" + sec;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate
                + " " + hour + seperator2 + min
                + seperator2 + sec;
        return currentdate;
    }
    
    /*搜索框初始化*/
    function initSearch(){
    	//房间下拉框
    	$("#areaResPerson").bsSuggest({
    	    url: "./../areaPlant/areaPlantList.app?method=userInfo&keyword=",
    	    showHeader: true,
    	    allowNoKeyword: false,
    	    keyField: 'keyword',
    	    getDataMethod: "url",
    	    delayUntilKeyup: true,
    	    effectiveFields: ["cname","username","deptName","position"],
    	    effectiveFieldsAlias: {
    	    	cname:"姓名",
    	    	username:"用户名",
    	    	deptName:"部门",
    	    	positionName:"岗位"
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
    		    $("#areaResPersonName").val(data.cname);
    		}).on("onUnsetSelectValue",	
    		function(e) {
    		    console.log("onUnsetSelectValue");
    		    $("#areaResPerson").val("");
    		});
    	
    }
    
  //查看和编辑
    function editOrCheck(obj,type){
    	$('#myForm1').validationEngine('hide');
		if(type==3){
			$("#myModalTitle1").html("管理植被信息");
			$("#areaId1").val(obj.areaId);
			new plantTable().Init();
			new areaPlantTable().Init();
			$('#plantInfo').bootstrapTable('refresh');
			$('#areaPlantInfo').bootstrapTable('refresh');
			$('#myModal2').modal();
			$("#myModal2 .search").css({
				'position':'relative',
				'left':'-204px',
				'width':'240px'
			});
		}else{
			if(type==1){
				$('#btPlantAdd').hide();
				$("#myModalTitle").html("查看");
			}else{
				$('#btPlantAdd').show();
				$("#myModalTitle").html("编辑");
			}
			$("#myModalTitle").html(obj.plantName);
			
			$("#areaId").val(obj.areaId);
			$("#areaId1").val(obj.areaId);
	   		$("#areaName").val(obj.areaName);
			$("#areaResPerson").val(obj.areaResPerson);
			$("#remark").val(obj.remark);
			
			if(type == 3){
				new areaPlantView().Init();
				$('#areaPlantView').bootstrapTable('refresh');
				$(".plantview").show();
			}
			
			$('#myModal1').modal();
		
		}
	
	}
    
    
    
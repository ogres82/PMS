selections = [];

$(function () {
	initPermissions();//初始化按钮权限
	init();
//	initDrops();//初始化下拉框
	initSearch();//初始化搜索框
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","输入关键字");
	$('#myForm1').validationEngine();
});

function initPermissions(){
	toolbarBtnInit(); //初始化工具条按钮
}

function init(){
	
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
    $("#btn_add").bind('click',function(){
    	openButtonWindow(1);
    });
    $("#btn_del").bind('click',function(){
    	openButtonWindow(3);
    });
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#eqpFixRec').bootstrapTable({
			url: '../equipment/equipmentFixList.app?method=list',         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: true,
//    		searchOnEnterKey: true,
    		showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "client",			//分页方式：client客户端分页，server服务端分页（*）
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
                checkbox: true
            }, {
            	field: 'fixRecId',
            	visible:false,
            	title: 'ID'
            }, {
                field: 'eqpName',
                title: '设备名称'
            }, {
                field: 'eqpFixContent',
                title: '维修项目'
            }, {
            	field: 'eqpFixDate',
                title: '维修日期',
                formatter:dateFormate
            }, {
            	field: 'eqpFixEnddate',
                title: '维修结束日期',
                formatter:dateFormate
            }, {
                field: 'eqpFixTime',
                title: '维修工时'
            }, {
                field: 'eqpFixFee',
                title: '维修费用'
            }, {
            	field: 'operate',
                title: '操作',
                events: operateEvents,
                formatter: operateFormatter
            }
            ]
		});
	};
	
	function operateFormatter(value, row, index) {
		return tableBtnInit();
    }
	//操作列的事件
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
        	check(row);
        },
		'click #a_edit': function (e, value, row, index) {
	    	edit(row);
	    },
        
    };
	
	oTableInit.queryParams = function (params) {
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search   //表格搜索框的值
	    };
	    return temp;
	};
	dateFormate = function(value){
		if(value==null || value==""){
		    return "";
		}else{
			return json2Date(value);
		}
		
	};
	stateFormat = function(value){
		if(value==null || value==""){
		    return "";
		}else if(value=="0"){
			return "周期保养";
		}else if(value=="1"){
			return "特殊保养";
		}
	}
	return oTableInit;
	
};
	
	
    //毫秒转时间YYYY-MM-DD hh:mm:ss
    function json2TimeStamp(milliseconds){
    	if(milliseconds==null || milliseconds==""){
    		return "";
    	}
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
    	if(milliseconds==null || milliseconds==""){
    		return "";
    	}
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
        return $.map($('#eqpFixRec').bootstrapTable('getSelections'), function (row) {
            return row.fixRecId;
        });
    }
    
    /**
     * 保存操作
     */
    function openSaveButton(){
//    	$("#eqpSave").attr("disabled","true");
    	var flag = $('#myForm1').validationEngine('validate');
    	if(flag){
    		var addJson = getDataForm();
        	//alert(addJson);
        	$.ajax({
                type: "post",
                url: "../equipment/equipmentFixList.app?method=addOrUpdate",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	layer.msg('操作成功！',{
        				time: 2000
        			}, function(){
        				$('#eqpFixRec').bootstrapTable('refresh');
        				$('#myModal1').modal('hide');
        			});
                },
                failure:function(xhr,msg)
                {
                	layer.msg('操作失败！',{
        				time: 2000
        			}, function(){
        				
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
     * @returns {___anonymous7092_7851}
     */
    function getDataForm(){
    	var addJson = {
    			fixRecId:$("#fixRecId").val(),
    			eqpId:$("#eqpId").val(),
    			eqpFixPerson:$("#eqpFixPerson").val(),
    			eqpFixDate:$("#eqpFixDate").val(),
    			eqpFixEnddate:$("#eqpFixEnddate").val(),
    			eqpFixContent:$("#eqpFixContent").val(),
    			eqpFixTime:$("#eqpFixTime").val(),
    			eqpFixFee:$("#eqpFixFee").val(),
    			eqpFixDetail:$("#eqpFixDetail").val(),
    			eqpHandlRes:$("#eqpHandlRes").val(),
    			
    		};
    	return addJson;
    }
    
    /**前排按钮监控
     * 
     * @param type
     */
    function openButtonWindow(type){
    	$('#btn_add').removeAttr("disabled"); 
    	var isOpen = false;
    	selections = getIdSelections();
    	var length=selections.length;
    	if(type==1 || type==3){
    		isOpen = true;
    	}else{
    		if(length==1){
    			isOpen = true;
    		 }else{
    			 if(length==0){
    				 layer.alert("请选择一条记录操作",{
    						skin: 'layui-layer-molv'
    					});
    			 }else{
    				 layer.alert("只能选择一条记录操作",{
    						skin: 'layui-layer-molv'
    					});
    			 }
    			 
    			 
    		 }
    	}
    	
    	if(isOpen){
    		var fixRecId = 0;
    		selections = getIdSelections();
    		fixRecId=selections[0];
    		var urlmethod = "";
    		//新增
    		if(type==1){
//    			urlmethod = "method=add";
    		}else if(type==2){
    			//修改
    			urlmethod = "method=edit&fixRecId="+fixRecId;
    		}else if(type==3){
    			//删除
    			//var deleteIds = JSON.stringify(selections);
    			urlmethod = "method=delete";
    			//alert(urlmethod);
    		}
    		
    		if(type==3){
    			if(length==0){
    				layer.alert("请选择一条记录操作",{
						skin: 'layui-layer-molv'
					});
    			}else{
    				layer.confirm("您确定要删除所选信息吗?",{
        				skin: 'layui-layer-molv', 
        				btn: ['确定','取消']
        			},function(){
        		        var url="../equipment/equipmentFixList.app?"+urlmethod;
        				$.post(url,
           						{fixRecId:selections+""},
           	    		        function(data){
           							layer.msg(data,{
           			    				time: 2000
           			    			}, function(){
           			    				$('#eqpFixRec').bootstrapTable('refresh');
           			    			});
           						});
        			},function(){
        				return;
        			})
    			}
    		}else if(type==1){
    			clearForm();
    			$('#myModal1').modal();
    		}else{
    			var url="../equipment/equipmentFixList.app?"+urlmethod;
    		    $.post(url,function(data){
    		   		var list = JSON.parse(data);
    		   		
    		   		$("#fixRecId").val(list.fixRecId);
    		   		$("#eqpId").val(list.eqpId);
    		   		$("#eqpName").val(list.eqpName);
        			$("#eqpFixPerson").val(list.eqpFixPerson);
        			$("#eqpFixDate").val(json2Date(list.eqpFixDate));
        			$("#eqpFixEnddate").val(json2Date(list.eqpFixEnddate));
        			$("#eqpFixContent").val(list.eqpFixContent);
        			$("#eqpFixTime").val(list.eqpFixTime);
        			$("#eqpFixFee").val(list.eqpFixFee);
        			$("#eqpFixDetail").val(list.eqpFixDetail);
        			$("#eqpHandlRes").val(list.eqpHandlRes);
        			
    			});
    			$('#myModal1').modal();
    		}
    		
    	}
    }
    
    //清空表单
    function clearForm(){
    	$("#fixRecId").val("");
    	$("#eqpId").val("");
    	$("#eqpName").val("");
		$("#eqpFixPerson").val("");
		$("#eqpFixDate").val("");
		$("#eqpFixEnddate").val("");
		$("#eqpFixContent").val("");
		$("#eqpFixTime").val("");
		$("#eqpFixFee").val("");
		$("#eqpFixDetail").val("");
		$("#eqpHandlRes").val("");
    }
    
    
    function getNowFormatDate(flag,vardate) {
    	if(flag == false && (vardate==null || vardate==''))
    		return "-";
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
    
    
    function initSearch(){
    	//设备下拉框
    	$("#eqpName").bsSuggest({
    	    url: "../search.app?type=equipment&keyword=",
    	    showHeader: false,
    	    allowNoKeyword: true,
    	    keyField: 'eqpName',
    	    getDataMethod: "url",
    	    delayUntilKeyup: true,
    	    effectiveFields: ["eqpName"],
    	    
    	    //预处理
	        fnPreprocessKeyword: function(keyword) {
	        		//请求数据前，对输入关键字作预处理
	        	 return encodeURI(encodeURI(keyword));
	        	},
    	    processData: function (json) {    // url 获取数据时，对数据的处理，作为 getData 的回调函数
    	    	var index, len, data = {value: []};
    	        if (!json || !json.result || json.result.length === 0) {
    	        	data.value.push({"eqpName":"查无数据"});
    	            return data;
    	        }

    	        len = json.result.length;

    	        for (index = 0; index < len; index++) {
    	            data.value.push({
    	                "eqpId": json.result[index][0],
    	                "eqpName": json.result[index][1],
    	            });
    	        }
    	       
    	        return data;
    	    }
    		}).on("onDataRequestSuccess",
    		function(e, result) {
    		    console.log("onDataRequestSuccess: ", result)
    		}).on("onSetSelectValue",
    		function(e, keyword, data) {
    		    console.log("onSetSelectValue: ", keyword, data);
    		    $("#eqpId").val(data.eqpId);
    		}).on("onUnsetSelectValue",	
    		function(e) {
    		    console.log("onUnsetSelectValue")
    		});
    }
    	
    function edit(obj){			
    	
    	$("#fixRecId").val(obj.fixRecId);
   		$("#eqpId").val(obj.eqpId);
   		$("#eqpName").val(obj.eqpName);
		$("#eqpFixPerson").val(obj.eqpFixPerson);
		$("#eqpFixDate").val(json2Date(obj.eqpFixDate));
		$("#eqpFixEnddate").val(json2Date(obj.eqpFixEnddate));
		$("#eqpFixContent").val(obj.eqpFixContent);
		$("#eqpFixTime").val(obj.eqpFixTime);
		$("#eqpFixFee").val(obj.eqpFixFee);
		$("#eqpFixDetail").val(obj.eqpFixDetail);
		$("#eqpHandlRes").val(obj.eqpHandlRes);
    	
		$("#btn_save").show();
    	$("#myForm .form-control").attr("disabled",false);
    	$("#myModal1").modal();
    }
    
    
    function check(obj){			
    	$("#fixRecId").val(obj.fixRecId);
   		$("#eqpId").val(obj.eqpId);
   		$("#eqpName").val(obj.eqpName);
		$("#eqpFixPerson").val(obj.eqpFixPerson);
		$("#eqpFixDate").val(json2Date(obj.eqpFixDate));
		$("#eqpFixEnddate").val(json2Date(obj.eqpFixEnddate));
		$("#eqpFixContent").val(obj.eqpFixContent);
		$("#eqpFixTime").val(obj.eqpFixTime);
		$("#eqpFixFee").val(obj.eqpFixFee);
		$("#eqpFixDetail").val(obj.eqpFixDetail);
		$("#eqpHandlRes").val(obj.eqpHandlRes);
    	
		$("#btn_save").hide();
    	$("#myForm .form-control").attr("disabled",false);
    	$("#myModal1").modal();
    }
    

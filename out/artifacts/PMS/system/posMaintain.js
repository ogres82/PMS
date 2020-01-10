selections = [];

$(function () {
	init();
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


function init(){
	
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#posList').bootstrapTable({
			url: '../system/posMaintain.app?method=load',         //请求后台的URL（*）
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
            	field: 'id',
            	title: 'ID'
            }, {
                field: 'name',
                title: '岗位名称'
            }, {
                field: 'desc',
                title: '岗位描述'
            }, {
            	field: 'createDate',
                title: '创建日期',
                formatter:dateFormate
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
		return ['<a id="a_edit">编辑</a>'].join('');
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
        return $.map($('#posList').bootstrapTable('getSelections'), function (row) {
            return row.id;
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
                url: "../system/posMaintain.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	layer.msg('操作成功！',{
        				time: 1500
        			}, function(){
        				$('#posList').bootstrapTable('refresh');
        				$('#myModal1').modal('hide');
        			});
                },
                failure:function(xhr,msg)
                {
                	layer.msg('操作失败！',{
        				time: 1500
        			}, function(){
        				
        			});
                }
            });
    	}else{
    		layer.msg('表单验证未通过！',{
				time: 1500
			});
    	}
    	
    }
    
    /**
     * 获取表单数据
     * @returns {___anonymous7092_7851}
     */
    function getDataForm(){
    	var addJson = {
    			id:$("#id").val(),
    			name:$("#name").val(),
    			desc:$("#desc").val()
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
    		var id = 0;
    		selections = getIdSelections();
    		id=selections[0];
    		var urlmethod = "";
    		//新增
    		if(type==1){
    			$("#id").attr('disabled',false);
    		}else if(type==3){//删除
    			urlmethod = "method=delete";
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
        		        var url="../system/posMaintain.app?"+urlmethod;
        				$.post(url,
           						{ids:selections+""},
           	    		        function(data){
           							layer.msg(data,{
           			    				time: 2000
           			    			}, function(){
           			    				$('#posList').bootstrapTable('refresh');
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
    			var url="../system/posMaintain.app?"+urlmethod;
    		    $.post(url,function(data){
    		   		var list = JSON.parse(data);
    		   		
    		   		$("#id").val(list.id);
    		   		$("#name").val(list.name);
    		   		$("#desc").val(list.desc);
    			});
    			$('#myModal1').modal();
    		}
    		
    	}
    }
    
    //清空表单
    function clearForm(){
    	$("#id").val("");
   		$("#name").val("");
		$("#desc").val("");
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
    
    
    	
    function edit(obj){			
    	$("#id").val(obj.id);
   		$("#name").val(obj.name);
		$("#desc").val(obj.desc);
    	$("#id").attr('disabled',true);
    	$("#myModal1").modal();
    }
    
    
    

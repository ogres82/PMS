selections = [];
var loginUserName="";
var loginUserCname="";
$(function () {
	initPermissions();
	initBtnEvent();
	init();
	initDrops();
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
				clearForm();
				$('#myForm1').validationEngine('hide');
		    	$('#btAreaDutyAdd').show();
    			$("#myModalTitle").html("新增");
    			$("#plantMtnPersonName").val(loginUserCname);
    			$("#plantMtnPerson").val(loginUserName);
    			$('#myModal1').modal({backdrop: 'static', keyboard: false});
    		
			});
		}
		//删除
		if(btnIdArray[i]=="btn_del"){
			$("#btn_del").bind('click',function(){
				$('#myForm1').validationEngine('hide');
				selections = getIdSelections();
				
				urlmethod = "method=deleteAeaDuty";

    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="./../areaDuty/areaDutyList.app?"+urlmethod;
    				$.post(url,
       						{planId:selections+""},
       	    		        function(data){
       							layer.msg(eval(data),{
       			    				time: 2000
       			    			}, function(){
       			    				$('#areaDutyInfo').bootstrapTable('refresh');
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
    $(".summernote").summernote({lang:"zh-CN"});
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#areaDutyInfo').bootstrapTable({
			url: './../areaDuty/areaDutyList.app?method=list',         //请求后台的URL（*）
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
            	field: 'planId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'planCode',
            	title: '计划编码'
            }, {
                field: 'cleanBeginDate',
                title: '开始日期',
                formatter: dateFormate
            }, {
                field: 'cleanEndDate',
                title: '结束日期',
                formatter: dateFormate
            }, {
                field: 'cleaningType',
                title: '保洁类别',
                formatter:mapValue
                
            }, {
            	field: 'cleaningArea',
                title: '保洁区域'
            }, {
            	field: 'cleaningDetail',
                title: '保洁内容'
            }, {
            	field: 'cleaningPerson',
                title: '保洁责任人'
            }, {
            	field: 'operate',
                title: '操作',
                align: 'center',
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
	mapValue = function(value){
		if(value==0){
			return "公共道路";
		}
		if(value==1){
			return "住宅楼道";
		}
		if(value==2){
			return "办公区域";
		}
		if(value==3){
			return "商业区域";
		}
	}
	return oTableInit;
	
};
	//表格选择事件
	function tableCheckEvents(){
		var r = $('#areaDutyInfo').bootstrapTable('getSelections');
    	if(r.length==0){
    		$("#btn_del").attr("disabled",true);
    	}
    	if(r.length==1){
    		$("#btn_del").attr("disabled",false);
    	}
	}
	
	//初始化下拉框
	function initDrops(){
		var url="./../areaDuty/areaDutyList.app?method=initDropAll";
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				 
				 for(var j=0;j<list.length;j++){
					 
					 var detailList = list[j];
					 var code = detailList[0];
					 //员工状态
					if(code=='cleaning_type'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#cleaningType");
					}
		                 
		        };
			}
	    );
		
	}
    
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
        return $.map($('#areaDutyInfo').bootstrapTable('getSelections'), function (row) {
            return row.planId;
        });
    }
    
    /**
     * 保存操作
     */
    function openSaveButton(){
    	var start = $("#cleanBeginDate").val();
    	var end = $("#cleanEndDate").val();
    	
    	if(start>end && end!=""){
    		layer.msg("结束日期不能小于开始日期",{
				time: 2000
			});
    		 return;
    	}
    	var flag = $('#myForm1').validationEngine('validate');
    	if(flag){
        	var addJson = getDataForm();
        	
        	$.ajax({
                type: "post",
                url: "./../areaDuty/areaDutyList.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	
                	layer.msg(data,{
        				time: 2000
        			}, function(){
        				$('#areaDutyInfo').bootstrapTable('refresh');
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
     * @returns {___anonymous7092_7851}
     */
    function getDataForm(){
    	
    	var addJson = {
    			planId:$("#planId").val(),
    			planCode:$("#planCode").val(),
    			cleanBeginDate:$("#cleanBeginDate").val(),
    			cleanEndDate:$("#cleanEndDate").val(),
    			cleaningType:$("#cleaningType").val(),
    			cleaningArea:$("#cleaningArea").val(),
    			cleaningDetail:$("#cleaningDetail").val(),
    			cleaningPerson:$("#cleaningPerson").val()
    		};
    	return addJson;
    }
    
    //清空表单
    function clearForm(){
    	$("#planId").val("");
		$("#planCode").val("");
		$("#cleanBeginDate").val("");
		$("#cleanEndDate").val("");
		$("#cleaningType").val("");
		$("#cleaningArea").val("");
		$("#cleaningDetail").val("");
		$("#cleaningPerson").val("");
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
    
    
  //查看和编辑
    function editOrCheck(obj,type){
    	$('#myForm1').validationEngine('hide');
    	if(type==1){
    		$('#btAreaDutyAdd').hide();
    		$("#myModalTitle").html("查看");
    	}else{
    		$('#btAreaDutyAdd').show();
    		$("#myModalTitle").html("编辑");
    	}
		
		$("#planId").val(obj.planId);
		$("#planCode").val(obj.planCode);
		$("#cleanBeginDate").val(getNowFormatDate(false,obj.cleanBeginDate));
		$("#cleanEndDate").val(getNowFormatDate(false,obj.cleanEndDate));
		$("#cleaningType").val(obj.cleaningType);
		$("#cleaningArea").val(obj.cleaningArea);
		$("#cleaningDetail").val(obj.cleaningDetail);
		$("#cleaningPerson").val(obj.cleaningPerson);
		
		$('#myModal1').modal();
	}
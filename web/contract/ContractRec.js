selections = [];
var loginUserName="";
var loginUserCname="";
$(function () {
	initPermissions();
	initBtnEvent();
	init();
	//initDrops();
	initSearch();//初始化搜索框
	//定位表格查询框
	$(".search").css({
		'position':'fixed',
		'right':'115px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","搜索");
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
				$('#myForm1').validationEngine('hide');
		    	$('#btContractRecAdd').show();
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
				
				urlmethod = "method=deleteContractRec";

    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="./../contract/contractRec.app?"+urlmethod;
    				$.post(url,
       						{recId:selections+""},
       	    		        function(data){
       							layer.msg(eval(data),{
       			    				time: 2000
       			    			}, function(){
       			    				$('#contractRecInfo').bootstrapTable('refresh');
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
    var i=0;
    $("#moreSearch").bind('click',function(){
   	 $("#more_search").slideToggle("slow",function(){
   		if(i==0){					
   			i=1;
   			$("#moreSearch").html('更多查询&nbsp;<span class="caret"></span>');
   			$("#moreSearch").addClass("dropup");
   		}else{
   			i=0;
   			$("#moreSearch").html('更多查询 <span class="caret"></span>');
   			$("#moreSearch").removeClass("dropup");
   		}
   	})
   });
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#contractRecInfo').bootstrapTable({
			url: './../contract/contractRec.app?method=list',         //请求后台的URL（*）
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
            	field: 'recId',
            	visible:false,
            	title: 'recId'
            }, {
            	field: 'contractId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'contractCode',
            	title: '合同编号'
            }, {
                field: 'contractName',
                title: '合同名称'
            }, {
                field: 'contractDealContent',
                title: '执行内容'
            }, {
                field: 'recTime',
                title: '记录日期',
                formatter: dateFormate
            }, {
            	field: 'recPerson',
            	title: '记录人'
            }, {
            	field: 'remark',
            	title: '备注'
            }, {
            	field: 'operate',
                title: '操作',
                align: 'center',
                width: '10%',
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
	//表格选择事件
	function tableCheckEvents(){
		var r = $('#contractRecInfo').bootstrapTable('getSelections');
    	if(r.length==0){
    		$("#btn_del").attr("disabled",true);
    	}
    	if(r.length==1){
    		$("#btn_del").attr("disabled",false);
    	}
	}
	
	//初始化下拉框
	function initDrops(){
		var url="./../contract/contractRec.app?method=initDropAll";
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 //员工部门
					$("<option value='"+detailList.areaId+"'>"+detailList.areaName+"</option>").appendTo("#areaId");
					
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
        return $.map($('#contractRecInfo').bootstrapTable('getSelections'), function (row) {
            return row.recId;
        });
    }
    
    function getNameSelections() {
        return $.map($('#contractRecInfo').bootstrapTable('getSelections'), function (row) {
            return row.empName;
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
                url: "./../contract/contractRec.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	layer.msg('操作成功！',{
        				time: 2000
        			}, function(){
        				$('#contractRecInfo').bootstrapTable('refresh');
        				$('#myModal1').modal('hide');
        			});
                },
                failure:function(xhr,msg)
                {
                	layer.msg('操作失败！',{
        				time: 3000
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
    			recId:$("#recId").val(),
    			contractId:$("#contractId").val(),
    			contractName:$("#contractName").val(),
    			contractCode:$("#contractCode").val(),
    			contractDealContent:$("#contractDealContent").val(),
    			recTime:$("#recTime").val(),
    			recPerson:$("#recPerson").val(),
    			remark:$("#remark").val()
    		};
    	
    	return addJson;
    }
    
    //清空表单
    function clearForm(){
    	$("#recId").val("");
    	$("#contractId").val("");
    	$("#contractCode").val("");
		$("#contractName").val("");
		$("#contractDealContent").val("");
		$("#recTime").val("");
		$("#recPerson").val("");
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
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
    
    
    /*搜索框初始化*/
    function initSearch(){
    	var addressSuggest = $("#contractName").bsSuggest({
    	    url: "./../contract/contractRec.app?method=searchContract&keyword=",
    	    showHeader: true,
    	    allowNoKeyword: false,
    	    keyField: 'contractName',
    	    getDataMethod: "url",
    	    delayUntilKeyup: true,
    	    effectiveFields: ["contractCode","contractName"],
    	    effectiveFieldsAlias: {
    	    	contractCode:"合同编码",
	    		contractName:"合同名称"
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
    	        
    	        //字符串转化为 json 对象
    	        return data;
    	    }
    		}).on("onDataRequestSuccess",
    		function(e, result) {
    		    console.log("onDataRequestSuccess: ", result)
    		}).on("onSetSelectValue",
    		function(e, keyword, data) {
    		    console.log("onSetSelectValue: ", keyword, data);
    		    $("#contractId").val(data.contractId);
    		    $("#contractCode").val(data.contractCode);
    		    $("#contractName").val(data.contractName);
    		}).on("onUnsetSelectValue",	
    		function(e) {
    		    console.log("onUnsetSelectValue");
    		    $("#contractId").val("");
    		    $("#contractCode").val("");
    		});
    	
    }
  
    
  //查看和编辑
    function editOrCheck(obj,type){
    	$('#myForm1').validationEngine('hide');
    	if(type==1){
    		$('#btContractRecAdd').hide();
    		$("#myModalTitle").html("查看");
    	}else{
    		$('#btContractRecAdd').show();
    		$("#myModalTitle").html("编辑");
    	}

   		$("#recId").val(obj.recId);
   		$("#contractCode").val(obj.contractCode);
   		$("#contractName").val(obj.contractName);
   		$("#contractId").val(obj.contractId);
		$("#contractDealContent").val(obj.contractDealContent);
		$("#recTime").val(getNowFormatDate(false,obj.recTime));
		$("#recPerson").val(obj.recPerson);
		$("#remark").val(obj.remark);
			
		$('#myModal1').modal({backdrop: 'static', keyboard: false});

    }
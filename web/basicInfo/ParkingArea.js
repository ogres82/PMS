selections = [];

$(function () {
	initPermissions();
	$.ajaxSetup({   
        async : false  
	}); 
	init();
	//initDrops();
	initSearch();//初始化搜索框
	initAllAreaDrop();
	initParkingAreaStateDrop();
	initAreaPropertyDrop("");
	//定位表格查询框
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$('#myForm1').validationEngine();
	initBtnEvent();
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
				$('#btParkingAreaAdd').show();

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
				
				urlmethod = "method=deleteParkingArea";

    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="./../parkingArea/parkingAreaList.app?"+urlmethod;
    				$.post(url,
       						{parkId:selections+""},
       	    		        function(data){
       							layer.msg(eval(data),{
       			    				time: 2000
       			    			}, function(){
       			    				$('#parkingAreaInfo').bootstrapTable('refresh');
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
		$('#parkingAreaInfo').bootstrapTable({
			url: './../parkingArea/parkingAreaList.app?method=list',         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: true,
    		searchOnEnterKey: true,
    		showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "client",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: false,
//            queryParams: oTableInit.queryParams,
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
            	field: 'parkId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'build_name',
            	title: '所属楼盘'
            }, {
            	field: 'community_name',
            	title: '所属小区'
            }, {
            	field: 'parkName',
            	title: '车库名称'
            }, {
            	field: 'parkCode',
            	title: '车库编号'
            }, {
            	field: 'parkState',
            	title: '车库状态'
            }, {
                field: 'parkNum',
                title: '车位数'
            }, {
                field: 'usingDate',
                title: '启用日期'
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
	        search: params.search
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
	var r = $('#parkingAreaInfo').bootstrapTable('getSelections');
	if(r.length==0){
		$("#btn_del").attr("disabled",true);
	}
	if(r.length==1){
		$("#btn_del").attr("disabled",false);
	}
}
	
	//初始化下拉框
	function initDrops(){
		var url="./../parkingArea/parkingAreaList.app?method=initDropAll";
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 var code = detailList[0];
					 //员工状态
					if(code=='emp_status'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#empStatus");
					}
		                 
		        };
			}
	    );
		
	}
	
	/**
	 * 初始化楼盘下拉框
	 */
	function initAllAreaDrop(){
		var url="./../buildingPropertyInfo/buildingPropertyList.app?method=initAllAreaDrop";
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					
					$("<option value='"+detailList.buildId+"'>"+detailList.buildName+"</option>").appendTo("#build_id");
					
		        };
			}
	    );
	}
	
	/**
	 * 初始化小区下拉框
	 */
	function initAreaPropertyDrop(buildId){
		var url="./../buildingPropertyInfo/buildingPropertyList.app?method=initAreaPropertyDrop";
	    $.post(url,
	    	  {buildId:buildId},
		      function(data){
	    	
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 
					$("<option value='"+detailList.communityId+"'>"+detailList.communityName+"</option>").appendTo("#belongComId");
					
		        };
			}
	    );
	}
	
	/**
	 * 初始化车库状态下拉框
	 */
	function initParkingAreaStateDrop(){
		var url="./../parkingArea/parkingAreaList.app?method=initParkingAreaState";
	    $.post(url,
		      function(data){
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 var code = detailList[0];
					 if(code == "parking_area_state"){
						 $("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#parkState");
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
        return $.map($('#parkingAreaInfo').bootstrapTable('getSelections'), function (row) {
            return row.parkId;
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
	            url: "./../parkingArea/parkingAreaList.app?method=save",
	            data: addJson,
	            dataType: "json",
	    		async : false,
	            success: function(data)
	            {
	            	layer.msg('操作成功！',{
	    				time: 2000
	    			}, function(){
	    				$('#parkingAreaInfo').bootstrapTable('refresh');
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
				time: 3000
			});
    	}
    }
    
    /**
     * 获取表单数据
     * @returns {___anonymous7092_7851}
     */
    function getDataForm(){
    	
    	var addJson = {
    			parkId:$("#parkId").val(),
    			parkName:$("#parkName").val(),
    			belongComId:$("#belongComId").val(),
    			build_id:$("#build_id").val(),
    			remark:$("#remark").val(),
    			parkCode:$("#parkCode").val(),
    			parkPosition:$("#parkPosition").val(),
    			parkState:$("#parkState").val(),
    			usingDate:$("#usingDate").val(),
    			floors:$("#floors").val(),
    			parkNum:$("#parkNum").val()
    		};
    	return addJson;
    }
    
    //清空表单
    function clearForm(){
    	
		$("#parkId").val("");
		$("#parkName").val("");
		$("#belongComId").val("");
		$("#build_id").val("");
		$("#remark").val("");
		$("#parkCode").val("");
   		$("#parkPosition").val("");
		$("#parkState").val("");
		$("#usingDate").val("");
		$("#floors").val("");
		$("#parkNum").val("");
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
    
    //楼盘下拉事件
    function changeAllArea(obj){
    	$('#belongComId option').each(function(){
    		var str = $(this).text();
    		if(str.indexOf("请选择")<0){
    			$(this).remove();
    		}
    	});
    	var buildId = $(obj).val();
    	if(buildId==""){
    		$('#belongComId').attr("disabled",true);
    	}else{
    		initAreaPropertyDrop(buildId);
    		$('#belongComId').removeAttr("disabled");
    	}
    }
    
    /*搜索框初始化*/
    function initSearch(){
    	var addressSuggest = $("input#addres").bsSuggest({
    	    url: "./../parkingArea/parkingAreaList.app?type=owner&keyword=",
    	    showHeader: true,
    	    allowNoKeyword: false,
    	    keyField: 'empNo',
    	    getDataMethod: "url",
    	    delayUntilKeyup: true,
    	    effectiveFields: ["empNo","empName","empContactPhone"],
    	    effectiveFieldsAlias: {
    	    	empNo:"员工号",
    	    	empName:"员工姓名",
    	    	empContactPhone:"联系电话"
    	    },
    	    processData: function (json) {    // url 获取数据时，对数据的处理，作为 getData 的回调函数
    	        var index, len, data = {value: []};
    	        if (!json || !json.result || json.result.length === 0) {
    	            return false;
    	        }

    	        len = json.result.length;

    	        for (index = 0; index < len; index++) {
    	            data.value.push({
    	                "roomNo": json.result[index][1]+"-"+json.result[index][3]+"-"+json.result[index][5],
    	                "roomId": json.result[index][4],
    	                "ownerName": json.result[index][7],
    	                "ownerId": json.result[index][6],
    	                "phone": json.result[index][8],
    	                
    	            });
    	        }
    	        //字符串转化为 json 对象
    	        
    	        return data;
    	    }
    		}).on("onDataRequestSuccess",
    		function(e, result) {
    		    console.log("onDataRequestSuccess: ", result)
    		}).on("onSetSelectValue",
    		function(e, keyword, data) {
    		    console.log("onSetSelectValue: ", keyword, data);
//    		    alert(JSON.stringify(data));
    		    $("#ownerId").val(data.ownerId);
    		    $("#rpt_name").val(data.ownerName);
    		    $("#roomId").val(data.roomId);
    		    $("#in_call").val(data.phone);
    		}).on("onUnsetSelectValue",	
    		function(e) {
    		    console.log("onUnsetSelectValue")
    		});
    	
    }
    
  //查看和编辑
    function editOrCheck(obj,type){
    	$('#myForm1').validationEngine('hide');
    	if(type==1){
    		$("#myModalTitle").html("查看");
    		$('#btParkingAreaAdd').hide(); 
    	}else{
    		$("#myModalTitle").html("编辑");
    		$('#btParkingAreaAdd').show(); 
    	}
		
		$('#belongComId').attr("disabled",true);
	   		
   		$("#parkId").val(obj.parkId);
   		$("#parkName").val(obj.parkName);
		$("#belongComId").val(obj.belongComId);
		$("#build_id").val(obj.build_id);
		$("#remark").val(obj.remark);
		$("#parkCode").val(obj.parkCode);
   		$("#parkPosition").val(obj.parkPosition);
		$("#parkState").val(obj.parkState);
		$("#usingDate").val(obj.usingDate);
		$("#floors").val(obj.floors);
		$("#parkNum").val(obj.parkNum);
			
		$('#myModal1').modal();
	
	}
    
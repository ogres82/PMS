selections = [];

$(function () {
	initPermissions();
	$.ajaxSetup({   
        async : false  
	}); 
	init();
	initDrops();
	initSearch();//初始化搜索框
	initAllAreaDrop();
	initAreaPropertyDrop("");
	initParkingAreaProp("");
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
				$('#btParkingAdd').show();

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
				
				urlmethod = "method=deleteParking";

    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="./../parking/parkingList.app?"+urlmethod;
    				$.post(url,
       						{carportId:selections+""},
       	    		        function(data){
   								layer.msg(eval(data),{time: 2000}, function(){
   									$('#parkingInfo').bootstrapTable('refresh');
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
		$('#parkingInfo').bootstrapTable({
			url: './../parking/parkingList.app?method=list',         //请求后台的URL（*）
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
            	field: 'carportId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'build_name',
            	title: '所属楼盘'
            }, {
            	field: 'community_name',
            	title: '所属小区'
            }, {
            	field: 'park_name',
            	title: '车位区域'
            }, {
            	field: 'carportNo',
            	title: '车位号'
            }, {
            	field: 'carport_status_name',
            	title: '车位状态'
            }, {
            	field: 'roomNo',
            	title: '房间号'
            }, {
            	field: 'ownerName',
            	title: '业主'
            }, {
            	field: 'ownerPhone',
            	title: '业主电话'
            }, {
            	field: 'licensePlateNo',
            	title: '车牌号'
            },  {
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
	var r = $('#parkingInfo').bootstrapTable('getSelections');
	if(r.length==0){
		$("#btn_del").attr("disabled",true);
	}
	if(r.length==1){
		$("#btn_del").attr("disabled",false);
	}
}
	
	//初始化下拉框
	function initDrops(){
		var url="./../parking/parkingList.app?method=initDropAll";
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				 //alert(list);
				 for(var j=0;j<list.length;j++){
					 //alert(list[j].main_event_source+"==="+list[j].main_event_type);
					 //debugger;
					 var detailList = list[j];
					 var code = detailList[0];
					 //员工状态
					if(code=='carport_status'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#carportStatus");
					}
		                 
		        };
			}
	    );
		
	}
	
	/**
	 * 初始化楼盘下拉框
	 */
	function initAllAreaDrop(){
		var url="./../parking/parkingList.app?method=initAllAreaDrop";
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
		var url="./../parking/parkingList.app?method=initAreaPropertyDrop";
	    $.post(url,
	    		{buildId:buildId},
		      function(data){
	    	
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 
					$("<option value='"+detailList.communityId+"'>"+detailList.communityName+"</option>").appendTo("#community_id");
					
		        };
			}
	    );
	}
	
	/**
	 * 初始区域下拉框
	 */
	function initParkingAreaProp(communityId){
		var url="./../parking/parkingList.app?method=initParkingAreaProp";
	    $.post(url,
	    		{communityId:communityId},
		      function(data){
	    	
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 
					$("<option value='"+detailList.parkId+"'>"+detailList.parkName+"</option>").appendTo("#belongParkNo");
					
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
        return $.map($('#parkingInfo').bootstrapTable('getSelections'), function (row) {
            return row.carportId;
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
	            url: "./../parking/parkingList.app?method=save",
	            data: addJson,
	            dataType: "json",
	    		async : false,
	            success: function(data)
	            {
	            	layer.msg('操作成功！',{
	    				time: 2000
	    			}, function(){
	    				$('#parkingInfo').bootstrapTable('refresh');
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
    			carportId:$("#carportId").val(),
    			community_id:$("#community_id").val(),
    			belongParkNo:$("#belongParkNo").val(),
    			carportNo:$("#carportNo").val(),
    			carportStatus:$("#carportStatus").val(),
    			carportFloor:$("#carportFloor").val(),
    			carportType:$("#carportType").val(),
    			ownerId:$("#ownerId").val(),
    			licensePlateNo:$("#licensePlateNo").val(),
    			remark:$("#remark").val()
    		};
    	return addJson;
    }
    
    //清空表单
    function clearForm(){
    	$("#carportId").val("");
    	$("#community_id").val("");
		$("#belongParkNo").val("");
		$("#carportNo").val("");
		$("#carportStatus").val("");
		$("#ownerName").val("");
		$("#ownerPhone").val("");
		$("#licensePlateNo").val("");
		$("#build_id").val("");
		$("#carportFloor").val("");
		$("#carportType").val("");
		$("#remark").val("");
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
    	$('#community_id option').each(function(){
    		var str = $(this).text();
    		if(str.indexOf("请选择")<0){
    			$(this).remove();
    		}
    	});
    	$('#belongParkNo option').each(function(){
    		var str = $(this).text();
    		if(str.indexOf("请选择")<0){
    			$(this).remove();
    		}
    	});
    	var buildId = $(obj).val();
    	if(buildId==""){
    		$('#community_id').attr("disabled",true);
    		$('#belongParkNo').attr("disabled",true);
    	}else{
    		initAreaPropertyDrop(buildId);
    		$('#community_id').removeAttr("disabled");
    	}
    }
    //小区下拉事件
    function changeAreaProperty(obj){
    	$('#belongParkNo option').each(function(){
    		var str = $(this).text();
    		if(str.indexOf("请选择")<0){
    			$(this).remove();
    		}
    	});
    	var communityId = $(obj).val();
    	if(communityId==""){
    		$('#belongParkNo').attr("disabled",true);
    	}else{
    		initParkingAreaProp(communityId);
    		$('#belongParkNo').removeAttr("disabled");
    	}
    }
    
    
    /*搜索框初始化*/
    function initSearch(){
    	var ownerSuggest = $("input#ownerName").bsSuggest({
    	    url: encodeURI("./../search.app?type=owner&keyword="),
    	    showHeader: true,
    	    allowNoKeyword: false,
    	    keyField: 'ownerName',
    	    getDataMethod: "url",
    	    delayUntilKeyup: true,
    	    effectiveFields: ["roomNo","ownerName","phone"],
    	    effectiveFieldsAlias: {
    	    	roomNo:"房间号",
    	    	ownerName:"业主名",
    	    	phone:"手机号"
    	    },
    	    processData: function (json) {    // url 获取数据时，对数据的处理，作为 getData 的回调函数
    	        var index, len, data = {value: []};
    	        if (!json || !json.result || json.result.length === 0) {
    	            return false;
    	        }

    	        len = json.result.length;

    	        for (index = 0; index < len; index++) {
    	            data.value.push({
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
    		    $("#ownerPhone").val(data.phone);
    		}).on("onUnsetSelectValue",	
    		function(e) {
    		    console.log("onUnsetSelectValue")
    		});
    	
    }
    
    
  //查看和编辑
    function editOrCheck(obj,type){
    	$('#myForm1').validationEngine('hide');
    	if(type==1){
    		$('#btParkingAdd').hide();
    		$("#myModalTitle").html("查看");
    	}else{
    		$('#btParkingAdd').show();
    		$("#myModalTitle").html("编辑");
    	}

		$('#community_id').attr("disabled",true);
		$('#belongParkNo').attr("disabled",true);
	    	
   		$("#carportId").val(obj.carportId);
		$("#community_id").val(obj.community_id);
		$("#belongParkNo").val(obj.belongParkNo);
		$("#carportNo").val(obj.carportNo);
		$("#carportStatus").val(obj.carportStatus);
		$("#carportFloor").val(obj.carportFloor);
		$("#carportType").val(obj.carportType);
		$("#licensePlateNo").val(obj.licensePlateNo);
		$("#build_id").val(obj.build_id);
		$("#ownerName").val(obj.ownerName);
		$("#ownerPhone").val(obj.ownerPhone);
		$("#remark").val(obj.remark);
		
		$('#myModal1').modal({backdrop: 'static', keyboard: false});
	}

    
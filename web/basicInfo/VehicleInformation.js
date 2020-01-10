selections = [];

$(function () {
	initPermissions();
	init();
	//定位表格查询框
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").css({
		'padding-right':'23px'
	});
	$('#myForm1').validationEngine();
	initBtnEvent();
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
				$('#btBasicCarAdd').show();

    			clearForm();
    			//disabledFalseAll();
    			$("#myModalTitle").html("新增");
    			$('#myModal1').modal({backdrop: 'static', keyboard: false});
    		
			});
		}
		//删除
		if(btnIdArray[i]=="btn_del"){
			$("#btn_del").bind('click',function(){
				$('#myForm1').validationEngine('hide');
				selections = getIdSelections();
				urlmethod = "method=deleteBasicCar";
    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="./../basicInfo/BasicCarList.app?"+urlmethod;
    				$.post(url,
       						{plateNo:selections+""},
       	    		        function(data){
       							layer.msg(eval(data),{
       			    				time: 2000
       			    			}, function(){
       			    				$('#basicCarInfo').bootstrapTable('refresh');
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
		$('#basicCarInfo').bootstrapTable({
			url: './../basicInfo/BasicCarList.app?method=list',         //请求后台的URL（*）
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
            	field: 'buildId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'plateNo',
            	title: '车牌号'
            }, {
                field: 'ownerName',
                title: '车主'
            }, {
            	field: 'cardNo',
            	title: '卡号'
            }, {
                field: 'carType',
                title: '车辆类型',
                formatter: mapValueCarType
            }, {
            	field: 'carColor',
            	title: '车辆颜色',
            	formatter: mapValueCarColor
            }, {
                field: 'plateColor',
                title: '车牌颜色',
                formatter: mapValuePlateColor
            }, {
            	field: 'plateType',
            	title: '车牌类型',
            	formatter: mapValuePlateType
            }, {
                field: 'plateStart',
                title: '发证日期',
                formatter: json2Date
            }, {
            	field: 'carBrand',
            	title: '车辆品牌'
            }, {
                field: 'driver',
                title: '驾驶员姓名'
            }, {
                field: 'driverPhone',
                title: '驾驶员手机号码'
            }, {
                field: 'num',
                title: '核载人数'
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
	    'click #a_topup': function (e, value, row, index) {
			vehicleTopup(row);
	    }
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
	mapValueCarType = function(value){
		switch(value){
			case "1":return "小型车";
			case "2":return "大型车";
			case "0":return "其他";
			default:return "";
		}
	};
	mapValueCarColor = function(value){
		switch(value){
			case "1":return "白色";
			case "2":return "银色";
			case "3":return "灰色";
			case "4":return "黑色";
			case "5":return "红色";
			case "6":return "深蓝";
			case "7":return "蓝色";
			case "8":return "黄色";
			case "9":return "绿色";
			case "10":return "棕色";
			case "11":return "粉色";
			case "12":return "紫色";
			case "0":return "其他颜色";
			default:return "";
		}
	};
	mapValuePlateColor = function(value){
		switch(value){
		case "0":return "蓝色";
		case "1":return "黄色";
		case "2":return "白色";
		case "3":return "黑色";
		case "4":return "绿色";
		case "5":return "民航黑色";
		case "0xff":return "其他";
		default:return "";
		}
	};
	mapValuePlateType = function(value){
		switch(value){
		case "0":return "标注民用车与军车车牌";
		case "1":return "02式民用车牌";
		case "2":return "武警车车牌";
		case "3":return "警车车牌";
		case "4":return "民用车双行尾牌";
		case "5":return "事关车牌";
		case "6":return "农用车车牌";
		default:return "";
		}
	};
	return oTableInit;
	
};

//表格选择事件
function tableCheckEvents(){
	var r = $('#basicCarInfo').bootstrapTable('getSelections');
	if(r.length==0){
		$("#btn_del").attr("disabled",true);
	}
	if(r.length==1){
		$("#btn_del").attr("disabled",false);
	}
}	

	//初始化下拉框
	function initDrops(){
		var url="./../basicInfo/BasicCarList.app?method=initDropAll";
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
					if(code=='emp_status'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#empStatus");
					}
		                 
		        };
			}
	    );
		
	}
	
	/**
	 * 初始化部门下拉框
	 */
	function initDeptDrop(){
		var url="./../basicInfo/BasicCarList.app?method=initDeptDrop";
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 //员工部门
					$("<option value='"+detailList.id+"'>"+detailList.name+"</option>").appendTo("#empDeptId");
					
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
    
    /**
     * 选择框
     * @returns
     */
    function getIdSelections() {
        return $.map($('#basicCarInfo').bootstrapTable('getSelections'), function (row) {
            return row.plateNo;
        });
    }
    
    /**
     * 保存操作
     */
    function openSaveButton(){
    	$('#myForm1').validationEngine('hide');
    	var flag = $('#myForm1').validationEngine('validate');
    	if(flag){
	    	var addJson = getDataForm();
	    	$.ajax({
	            type: "post",
//	            url: "./../hikvision.app?method=/pms/addPlatParkCarInfo",
	            url: "./../basicInfo/BasicCarList.app?method=save",
	            data: addJson,
	            dataType: "json",
	    		async : false,
	            success: function(data)
	            {
	            	if(data=="success"){
	            		layer.msg('操作成功！',{
	            			time: 2000
	            		}, function(){
	            			$('#basicCarInfo').bootstrapTable('refresh');
	            			$('#myModal1').modal('hide');
	            		});
	            	}else{
	            		layer.msg('操作失败！',{time: 2000});
	            	}
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
    			plateNo:$("#plateNo").val(),
    			ownerId:$("#ownerId").val(),
    			cardNo:$("#cardNo").val(),
    			carType:$("#carType").val(),
    			carColor:$("#carColor").val(),
    			plateColor:$("#plateColor").val(),
    			plateType:$("#plateType").val(),
    			plateStart:$("#plateStart").val(),
    			carBrand:$("#carBrand").val(),
    			driver:$("#driver").val(),
    			driverPhone:$("#driverPhone").val(),
    			num:$("#num").val(),
    			hkPersonId:$("#hkPersonId").val()
    		};
    	return addJson;
    }
    
    //清空表单
    function clearForm(){
		$("#plateNo").val("");
   		$("#ownerId").val("");
		$("#cardNo").val("");
		$("#carType").val("");
   		$("#carColor").val("");
		$("#plateColor").val("");
		$("#plateType").val("");
   		$("#plateStart").val("");
		$("#carBrand").val("");
		$("#driver").val("");
   		$("#driverPhone").val("");
		$("#num").val("");
		$("#hkPersonId").val("");
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
    
    //查看和编辑
    function editOrCheck(obj,type){
    	$('#myForm1').validationEngine('hide');
    	if(type==1){
    		$('#btBasicCarAdd').hide();
    		$("#myModalTitle").html("查看");
    		disabledAll();
    	}else{
    		$('#btBasicCarAdd').show();
    		$("#myModalTitle").html("编辑");
    		disabledFalseAll();
    	}
    	
   		$("#plateNo").val(obj.plateNo);
   		$("#ownerId").val(obj.ownerId);
   		$("#owner").val(obj.ownerName);
		$("#cardNo").val(obj.cardNo);
		$("#carType").val(obj.carType);
   		$("#carColor").val(obj.carColor);
		$("#plateColor").val(obj.plateColor);
		$("#plateType").val(obj.plateType);
   		$("#plateStart").val(json2Date(obj.plateStart));
		$("#carBrand").val(obj.carBrand);
		$("#driver").val(obj.driver);
   		$("#driverPhone").val(obj.driverPhone);
		$("#num").val(obj.num);
		$("#hkPersonId").val(obj.hkPersonId);
		
		$('#myModal1').modal({backdrop: 'static', keyboard: false});
	}
    
    function vehicleTopup(obj){
    	var param = "plateNo="+obj.plateNo+"&pageNo=1&pageSize=10";
    	$.ajax({
            type: "post",
//            url: "./../hikvision.app?method=/pms/getPlatCarParkingLocation",
            url: "./../basicInfo/BasicCarList.app?method=vehicleTopup",
            data: {param,param},
            dataType: "json",
    		async : false,
            success: function(data)
            {
            	if(data=="success"){
            		layer.msg('操作成功！',{
            			time: 2000
            		}, function(){
            			$('#basicCarInfo').bootstrapTable('refresh');
            			$('#myModal1').modal('hide');
            		});
            	}else{
            		layer.msg('操作失败！',{time: 2000});
            	}
            }
        });
    }
    
    //表单置为不可编辑
    function disabledAll(){
    	$("#owner").attr("disabled",true);
    	$("#plateNo").attr("disabled",true);
   		$("#ownerId").attr("disabled",true);
		$("#cardNo").attr("disabled",true);
		$("#carType").attr("disabled",true);
   		$("#carColor").attr("disabled",true);
		$("#plateColor").attr("disabled",true);
		$("#plateType").attr("disabled",true);
   		$("#plateStart").attr("disabled",true);
		$("#carBrand").attr("disabled",true);
		$("#driver").attr("disabled",true);
   		$("#driverPhone").attr("disabled",true);
		$("#num").attr("disabled",true);
		$("#hkPersonId").attr("disabled",true);
    }
    
     //表单置为可编辑
    function disabledFalseAll(){
    	$("#owner").attr("disabled",false);
    	$("#plateNo").attr("disabled",false);
   		$("#ownerId").attr("disabled",false);
		$("#cardNo").attr("disabled",false);
		$("#carType").attr("disabled",false);
   		$("#carColor").attr("disabled",false);
		$("#plateColor").attr("disabled",false);
		$("#plateType").attr("disabled",false);
   		$("#plateStart").attr("disabled",false);
		$("#carBrand").attr("disabled",false);
		$("#driver").attr("disabled",false);
   		$("#driverPhone").attr("disabled",false);
		$("#num").attr("disabled",false);
		$("#hkPersonId").attr("disabled",false);
    }
    
    /*搜索框初始化*/
    function initSearch(){
    	
    	//业主下拉框
    	$("#owner").bsSuggest({
    	    url: encodeURI("./../basicInfo/BasicCarList.app?code=utf-8&method=ownerInfo&keyword="),
    	    showHeader: true,
    	    allowNoKeyword: true,
    	    keyField: 'keyword',
    	    getDataMethod: "url",
    	    delayUntilKeyup: true,
    	    effectiveFields: ["ownerName","phone"],
    	    effectiveFieldsAlias: {
    	    	ownerName:"业主姓名",
    	    	phone:"电话号码"
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
    		    $("#ownerId").val(data.ownerId);
    		    $("#hkPersonId").val(data.ownerHkId);
    		    $("#owner").val(data.ownerName);
    		   
    		}).on("onUnsetSelectValue",	
    		function(e) {
    		    console.log("onUnsetSelectValue")
    		});
    }
    
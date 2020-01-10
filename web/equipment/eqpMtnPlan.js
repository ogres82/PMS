selections = [];

$(function () {
	init();
	initDrops();//初始化下拉框
	initSearch();//初始化搜索框
});


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
   			$("#moreSearch").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收起   <i class='fa fa-angle-up'></i>");
   		}else{
   			i=0;
   			$("#moreSearch").html("更多查询    <i class='fa fa-angle-down'></i>");
   		}
   	})
   });
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#eqpMtnPlan').bootstrapTable({
			url: '../../equipment/equipmentPlanList.app?method=list',         //请求后台的URL（*）
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
            clickToSelect: true,
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
                field: 'eqpName',
                title: '设备名称'
            }, {
                field: 'eqpModel',
                title: '计划开始日期',
                formatter:dateFormate
            }, {
            	field: 'eqpStatus',
                title: '计划结束日期',
                formatter:dateFormate
            }, {
            	field: 'eqpUsedMonths',
                title: '保养级别',
                formatter:stateFormat
            }, {
                field: 'eqpMtnLast',
                title: '制定人'
            }, {
                field: 'eqpMtnNext',
                title: '负责人'
            }
            ]
		});
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
			return "正常";
		}else if(value=="1"){
			return "维保中";
		}
	}
	return oTableInit;
	
};
	
	//初始化下拉框
	function initDrops(){
		var url="../../equipment/equipmentList.app?method=initDropAll";
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 //alert(list[j].main_event_source+"==="+list[j].main_event_type);
					 //debugger;
					 var detailList = list[j];
					 var code = detailList[0];
					 //设备类型
					if(code=='equipment_type'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#eqp_type");
					//设备状态
					}else if(code=='equipment_status'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#eqp_status");
					//设备周期	
					}else if(code=='equipment_cycle'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#eqp_maintain_cycle");
					}
		                 
		        };
			}
	    );
		
	}
	
	
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
        return $.map($('#eqpFiles').bootstrapTable('getSelections'), function (row) {
            return row.eqpId;
        });
    }
    
    /**
     * 保存操作
     */
    function openSaveButton(){
//    	$("#eqpSave").attr("disabled","true");
    	var addJson = getDataForm();
    	//alert(addJson);
    	$.ajax({
            type: "post",
            url: "../../equipment/equipmentList.app?method=addOrUpdate",
            data: addJson,
            dataType: "json",
    		async : false,
            success: function(data)
            {
            	layer.msg('操作成功！',{
    				time: 2000
    			}, function(){
    				$('#eqpFiles').bootstrapTable('refresh');
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
    }
    
    /**
     * 获取表单数据
     * @returns {___anonymous7092_7851}
     */
    function getDataForm(){
    	
    	var addJson = {
    			eqpId:$("#eqp_id").val(),
    			eqpBelongArea:$("#eqp_belong_area").val(),
    			eqpAddress:$("#eqp_address").val(),
    			eqpName:$("#eqp_name").val(),
    			eqpType:$("#eqp_type").val(),
    			eqpModel:$("#eqp_model").val(),
    			eqpStatus:$("#eqp_status").val(),
    			eqpMtnCycle:$("#eqp_maintain_cycle").val(),
    			eqpInstallDate:$("#eqp_install_date").val(),
    			eqpDestroyDate:$("#eqp_destroy_date").val(),
    			eqpLifetime:$("#eqp_lifetime").val(),
    			eqpUsedMonths:$("#eqp_used_months").val(),
    			eqpFixedTimes:$("#eqp_fixed_times").val(),
    			eqpMtnTimes:$("#eqp_maintain_times").val(),
    			eqpMtnLast:$("#eqp_maintain_last").val(),
    			eqpMtnNext:$("#eqp_maintain_next").val(),
    			eqpOriginValue:$("#eqp_origin_value").val(),
    			eqpValueRate:$("#eqp_value_rate").val(),
    			eqpCurrentValue:$("#eqp_current_value").val(),
    			eqpMtnUnit:$("#eqp_maintain_unit").val(),
    			eqpMtnPhone:$("#eqp_maintain_phone").val(),
    			eqpMtnAddress:$("#eqp_maintain_address").val(),
    			eqpMtnPerson:$("#eqp_maintain_person").val(),
    			eqpManu:$("#eqp_manu").val(),
    			eqpManuPhone:$("#eqp_manu_address").val(),
    			eqpManuAddress:$("#eqp_manu_phone").val(),
    			eqpRegisterDate:$("#eqp_register_date").val(),
    			eqpRemark:$("#eqp_remark").val()
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
    		var eqpId = 0;
    		selections = getIdSelections();
    		eqpId=selections[0];
    		var urlmethod = "";
    		//新增
    		if(type==1){
//    			urlmethod = "method=add";
    		}else if(type==2){
    			//修改
    			urlmethod = "method=edit&eqpId="+eqpId;
    		}else if(type==3){
    			//删除
    			//var deleteIds = JSON.stringify(selections);
    			urlmethod = "method=delete";
    			//alert(urlmethod);
    		}
    		
    		if(type==3){
    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="../../equipment/equipmentList.app?"+urlmethod;
    				$.post(url,
       						{eqpId:selections+""},
       	    		        function(data){
       							layer.msg(data,{
       			    				time: 2000
       			    			}, function(){
       			    				$('#eqpFiles').bootstrapTable('refresh');
       			    			});
       							/**
       							layer.alert(data, {
       							  skin: 'layui-layer-molv' //样式类名
       							  ,closeBtn: 0
       							}, function(){
       								$('#empInfo').bootstrapTable('refresh');
       							});
       							*/
       						});
    			},function(){
    				return;
    			})
    		}else if(type==1){
    			clearForm();
    			$('#myModal1').modal();
    		}else{
    			var url="../../equipment/equipmentList.app?"+urlmethod;
    		    $.post(url,function(data){
    		   		var list = JSON.parse(data);
    		   		$("#eqp_id").val(list.eqpId);
    		   		$("#eqp_name").val(list.eqpName);
        			$("#eqp_address").val(list.eqpAddress);
        			$("#eqp_belong_area").val(list.eqpBelongArea);
        			$("#eqp_type").val(list.eqpType);
        			$("#eqp_model").val(list.eqpModel);
        			$("#eqp_status").val(list.eqpStatus);
        			$("#eqp_maintain_cycle").val(list.eqpMtnCycle);
        			$("#eqp_install_date").val(json2Date(list.eqpInstallDate));
        			$("#eqp_destroy_date").val(json2Date(list.eqpDestroyDate));
        			$("#eqp_lifetime").val(list.eqpLifetime);
        			$("#eqp_used_months").val(list.eqpUsedMonths);
        			$("#eqp_fixed_times").val(list.eqpFixedTimes);
        			$("#eqp_maintain_times").val(list.eqpMtnTimes);
        			$("#eqp_maintain_last").val(json2Date(list.eqpMtnLast));
        			$("#eqp_maintain_next").val(json2Date(list.eqpMtnNext));
        			$("#eqp_origin_value").val(list.eqpOriginValue);
        			$("#eqp_value_rate").val(list.eqpValueRate);
        			$("#eqp_current_value").val(list.eqpCurrentValue);
        			$("#eqp_maintain_unit").val(list.eqpMtnUnit);
        			$("#eqp_maintain_phone").val(list.eqpMtnPhone);
        			$("#eqp_maintain_address").val(list.eqpMtnAddress);
        			$("#eqp_maintain_person").val(list.eqpMtnPerson);
        			$("#eqp_manu").val(list.eqpManu);
        			$("#eqp_register_date").val(json2Date(list.eqpRegisterDate));
        			$("#eqp_manu_phone").val(list.eqpManuPhone);
        			$("#eqp_manu_address").val(list.eqpManuAddress);
        			$("#eqp_remark").val(list.eqpRemark);
    			});
    			$('#myModal1').modal();
    		}
    		
    	}
    }
    
    //清空表单
    function clearForm(){
    	$("#eqp_id").val("");
    	$("#eqp_belong_area").val("");
		$("#eqp_address").val("");
		$("#eqp_name").val("");
		$("#eqp_type").val("");
		$("#eqp_model").val("");
		$("#eqp_status").val("");
		$("#eqp_maintain_cycle").val("");
		$("#eqp_install_date").val("");
		$("#eqp_destroy_date").val("");
		$("#eqp_lifetime").val("");
		$("#eqp_used_months").val("");
		$("#eqp_fixed_times").val("");
		$("#eqp_maintain_times").val("");
		$("#eqp_maintain_last").val("");
		$("#eqp_maintain_next").val("");
		$("#eqp_origin_value").val("");
		$("#eqp_value_rate").val("");
		$("#eqp_current_value").val("");
		$("#eqp_maintain_unit").val("");
		$("#eqp_maintain_phone").val("");
		$("#eqp_maintain_address").val("");
		$("#eqp_maintain_person").val("");
		$("#eqp_manu").val("");
		$("#eqp_manu_address").val("");
		$("#eqp_manu_phone").val("");
		$("#eqp_register_date").val("");
		$("#eqp_remark").val("");
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

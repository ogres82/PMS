selections = [];

$(function(){
	init();
	initDrops();
	//定位表格查询框
	$(".search").css({
		'position':'fixed',
		'right':'115px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","搜索");
	$(".search input").css({
		'padding-right':'23px'
	});
	
	
	initSearch();//初始化搜索框

	$('#myForm1').validationEngine();
	initSearchHandler();
	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",});
});


$(function(){
	toolbarBtnInit();//初始化权限按钮
	for(var i=0,len=btnIdArray.length;i<len;i++)
	{
	 if(btnIdArray[i]=="btn1")
	 {
	 $("#btn1").bind('click',function()
	  {
			 openButtonWindow(1);
	  });
	 }else if(btnIdArray[i]=="btn4")
	 {
		 $("#btn4").bind('click',function()
		{
		 openButtonWindow(4);
		});
	 }
	}
	$("#btn4").attr("disabled",true);
	
});

/**
 * 刷新数据表单
 */
function buttonreresh(){
	$('#refresh').click();
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
		$('#evnetSendInfo').bootstrapTable({
			url: './../houseWork/evnetsend.app?method=listEventSend',         //请求后台的URL（*）
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
                        field: 'stat',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    }, {
                        field: 'send_no',
                        title: '派工单号',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       
                    }, {
                        field: 'event_no',
                        title: '事件单号',
                        sortable: true,
                        editable: true,
                        align: 'center',
                    }, {
                        field: 'event_title',
                        title: '事件标题',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                    },{
                        field: 'event_source_name',
                        title: '事件来源',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter: rpt_name
                    },{
                        field: 'call_phone',
                        title: '呼入电话',
                        sortable: true,
                        align: 'center',
                    }, {
                        field: 'accept_time',
                        title: '受理时间',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'pre_time',
                        title: '预处理时间',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'verify_oper_id',
                        title: '受理人',
                        align: 'center',
                        //valign: 'middle',
                        sortable: true,
                        visible:false
                        //formatter:timeFormatCreateTime
                    }, {
                        field: 'rpt_name',
                        title: '申请人',
                        sortable: true,
                        editable: true,
                        align: 'center',
                       // formatter:stateFormat
                    }, {
                        field: 'oper_name',
                        title: '处理人',
                        sortable: true,
                        editable: true,
                        align: 'center',
                       // formatter:stateFormat
                    }  ,{
                        field: 'user_address',
                        title: '地址',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter:
                    }, {
                        field: 'event_state',
                        title: '事件状态',
                        sortable: true,
                        editable: true,
                        align: 'center',
                       
                        formatter:stateFormat
                    }, {
                        field: 'houseKeepingPay',
                        title: '家政服务费(元)',
                        sortable: true,
                        editable: true,
                        align: 'center'
                       // visible:false
                       // formatter:stateFormat
                    }, {
                        field: 'record_id',
                        title: '关联录音',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    } 
                    , {
                        field: 'bpm_processId',
                        title: '流程编号',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    } 
                    , {
                        field: 'event_source',
                        title: '事件来源',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    }
                   
                    , {
                        field: 'event_content',
                        title: '内容',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    }
                    , {
                        field: 'id',
                        title: '编号',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    }
                    , {
                        field: 'oper_id',
                        title: '处理人ID',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    }
                    , {
                        field: 'send_time',
                        title: '派工时间',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    }, {
                        field: 'send_state',
                        title: '状态',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    }, {
                        field: 'send_id',
                        title: '派工单编号',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    }
                    , {
                        field: 'handle_content',
                        title: '处理内容',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    }
                    , {
                        field: 'arrv_time',
                        title: '到达时间',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    },  {
                        field: 'verify_oper_name',
                        title: '受理人',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                       // formatter:stateFormat
                    },{
                    	field: 'operate',
                        title: '操作',
                        align: 'center',
                        width:'15%',
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
           		$("#btn4").attr("disabled",false);
               },
               onUncheckAll: function(rows){
           		$("#btn2").attr("disabled",true);
           		$("#btn3").attr("disabled",true);
           		$("#btn4").attr("disabled",true);
               }
		});
	};
	function operateFormatter(value, row, index) {
		
		return tableBtnInit();
    }
	//操作列的事件
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) 
        {
        	show(row,1);
        },
		'click #a_handle': function (e, value, row, index)
		{
			show(row,2);
	    },
	    'click #a_refuseEventSend': function (e, value, row, index)
		{
			show(row,3);
	    },
	    'click #a_cancel': function (e, value, row, index)
		{
			show(row,4);
	    }
        
    };
	
	
	oTableInit.queryParams = function (params) {
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: encodeURI(params.search)
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

//显示详情
function show(obj,type){
	if(type==1)
	{
	 showHouseDispatch(type)
	}else if(type==2)
	{
		if(obj.event_state==2)
		{
			layer.alert("该工单已处理,请选择销单");
			return;
		}
		 handleHouseDispatch(type)
	}
	else if(type==3)
	{
		if(obj.event_state==2)
		{
			layer.alert("该工单已处理,请选择销单");
			return;
		}
		refuseHouseDispatch(type);
	}
	else if(type==4)
	{
	 if(obj.event_state==1)
		{
			layer.alert("该工单未处理，不能销单");
			return;
		}
		cancelHouseDisptch(type)
	}
	
	$('#myForm1').validationEngine('hide');
	changeEventType(1,obj.bpm_processId);
	
	$("#event_no").val(obj.event_no);
	$("#event_source").val(obj.event_source);
	$("#rpt_name").val(obj.rpt_name);
	$("#in_call").val(obj.call_phone);
	$("#addres").val(obj.user_address);
	$("#event_title").val(obj.event_title);
	$("#pre_time").val(obj.pre_time);
	$("#event_content").val(obj.event_content);
	$("#verify_oper_id").val(obj.verify_oper_id);
	$("#verify_oper_name").val(obj.verify_oper_name);
	$("#id").val(obj.id);
	$("#accept_time").val(obj.accept_time);
	
	//派工
	$("#comp_operator_id").val(obj.oper_id);
	$("#send_id").val(obj.send_id);
	$("#comp_operator_username").val(obj.oper_name);
	$("#dispatch_finish_time").val(subStringTime(obj.send_time));
	$("#dispatch_status").val(obj.event_state);
	$("#handle_content").val(obj.handle_content);
	$("#arrv_time").val(subStringTime(obj.arrv_time));
	$("#houseKeepingPay").val(obj.houseKeepingPay);
	$("#myModal1").modal();
}
//查看
function showHouseDispatch(type)
{
	$("#arrv_time").attr("disabled",true);
	$("#houseKeepingPay").attr("disabled",true);
	$("#btAssigenHandle").hide();
	$("#btdeleteEventSend").hide();
	$("#btdrefuseEventSend").hide();

}
//现场确认
function handleHouseDispatch(type)
{
	$("#arrv_time").attr("disabled",false);
	$("#houseKeepingPay").attr("disabled",false);
	$("#btAssigenHandle").show();
	$("#btdeleteEventSend").hide();
	$("#btdrefuseEventSend").hide();

}

function refuseHouseDispatch(type)
{
	$("#arrv_time").attr("disabled",true);
	$("#houseKeepingPay").attr("disabled",true);
	$("#btAssigenHandle").hide();
	$("#btdeleteEventSend").hide();
	$("#btdrefuseEventSend").show();

}
//消单
function cancelHouseDisptch(type)
{
	$("#arrv_time").attr("disabled",true);
	$("#houseKeepingPay").attr("disabled",true);
	$("#btAssigenHandle").hide();
	$("#btdeleteEventSend").show();
	$("#btdrefuseEventSend").hide();

}

//人的中文名字显示
function nameFormatCName(cName,value) {
	//alert(value);
	var addJson = {
		userName:value
	};
	$.ajax({
        type: "post",
        url: "./../assigenwork/assignlist.app?method=nameformat",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {
        	$("#"+cName).val(data);
        },
        failure:function(xhr,msg)
        {
			
        }
    });
}


//表格选择事件
function tableCheckEvents(){
	var r = $('#evnetSendInfo').bootstrapTable('getSelections');
	
	if(r.length==0){
		$("#btn2").attr("disabled",true);
		$("#btn3").attr("disabled",true);
		$("#btn4").attr("disabled",true);
		$("#btn5").attr("disabled",true);
		$("#btn1").attr("disabled",true);
	}
	if(r.length==1){
		
		var head=r[0].event_no.substring(0,2);
		if(head=="IM")
		{
		 $("#btn5").attr("disabled",false);
		}else
		{
		 $("#btn5").attr("disabled",true);
		}
		$("#btn2").attr("disabled",false);
		$("#btn3").attr("disabled",false);
		$("#btn4").attr("disabled",false);
		$("#btn1").attr("disabled",false);
		
	}
	if(r.length>1){
		$("#btn1").attr("disabled",true);
		$("#btn2").attr("disabled",true);
		$("#btn3").attr("disabled",true);
		$("#btn5").attr("disabled",true);
	}
}

/*搜索框初始化*/
function initSearch(){
	var addressSuggest = $("input#addres").bsSuggest({
	    url: encodeURI("./../search.app?type=owner&keyword="),
	    showHeader: true,
	    allowNoKeyword: true,
	    keyField: 'roomNo',
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
//		    alert(JSON.stringify(data));
		    $("#ownerId").val(data.ownerId);
		    $("#rpt_name").val(data.ownerName);
		    $("#roomId").val(data.roomId);
		    $("#in_call").val(data.phone);
		}).on("onUnsetSelectValue",	
		function(e) {
		    console.log("onUnsetSelectValue")
		});
	
}


/**前排按钮监控
 * 
 * @param type
 */
function openButtonWindow(type){
	$('#btAssigenAdd').removeAttr("disabled"); 
	var isOpen = false;
	selections = getIdSelections();
	var length=selections.length;
	if(type==1 || type==4){
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
		var rpt_id = 0;
		selections = getIdSelections();
		rpt_id=selections[0];
		var urlmethod = "";
		//新增
		if(type==1){
			urlmethod = "method=addHouseWork";
			$("#dispatch_status").val(0);
			$("#btAssigenAdd").show();
			
			ableEdit();
		}else if(type==2){
			//修改
			$("#btAssigenHandle").hide();
			ableEdit();
			urlmethod = "method=viewAssign&assignId="+rpt_id;
		}else if(type==3||type==5){
			//查看
			if(type==3){
				$("#btAssigenHandle").hide();
			    $('#btAssigenAdd').attr("disabled","true"); 
			    disableEdit();
			}else
				{
				$("#btAssigenHandle").show();
				$("#btAssigenAdd").hide();
				}
			urlmethod = "method=viewAssign&assignId="+rpt_id;
		}else if(type==4){
			//删除
			//var deleteIds = JSON.stringify(selections);
			urlmethod = "method=deleteHouseWork";
			//alert(urlmethod);
		}
		
		if(type==4){
			layer.confirm("您确定要删除所选信息吗?",{
				skin: 'layui-layer-molv', 
				btn: ['确定','取消']
			},function(){
		        var url="./../houseWork/evnetsend.app?"+urlmethod;
   			    var deleteIds = JSON.stringify(selections);
				$.post(url,
   						{deleteIds:deleteIds},
   	    		        function(data){
   							layer.alert(data, {
   							  skin: 'layui-layer-molv' //样式类名
   							  ,closeBtn: 0
   							}, function(){
   								var locationUrl="./../houseWork/houseWorkDispatch.jsp";
	   							window.location.href=locationUrl;
   							});
   						});
			},function(){
				return;
			})
		}else{
			var url="./../houseWork/accept.app?"+urlmethod;
		    $.post(url,function(data){
		   		var list = eval(data);
		   		var event_type_data=list.event_type;
		   		//var processInstanceId = list.processInstanceId;
		   		//changeEventType(event_type_data,processInstanceId);
		   		//nameFormatCName("createby",list.createby);
		   		$("#accept_time").val(getNowFormatDate(false,list.accept_time));
		   		$("#event_no").val(list.event_no);
		   		$("#verify_oper_id").val(list.verify_oper_id);
		   		
		   		
		   		$("#comp_status").val(list.comp_status);
		   		$("#comp_emergency").val(list.comp_emergency);
		   		$("#comp_detail").val(list.comp_detail); 
		   		$("#finish_time").val(getNowFormatDate(false,list.finishTime));
			});
           
			$('#myModal1').modal();
		}
		
	}
}


//查看设置
function disableEdit()
{
	 $('#event_source').attr("disabled","true"); 
	  $('#event_type').attr("disabled","true");
	  $('#rpt_name').attr("disabled","true"); 
	  $('#in_call').attr("disabled","true"); 
	  $('#addres').attr("disabled","true"); 
	  $('#mtn_detail').attr("disabled","true"); 
	  $('#mtn_emergency').attr("disabled","true"); 
	  $('#comp_detail').attr("disabled","true"); 
	  $('#comp_reply').attr("disabled","true");
	  $('#comp_emergency').attr("disabled","true");
	  $('#comp_status').attr("disabled","true");


}
//新增和编辑
function ableEdit()
{
	  $('#event_source').attr("disabled",false); 
	  $('#event_type').attr("disabled",false);
	  $('#rpt_name').attr("disabled",false); 
	   $('#in_call').attr("disabled",false); 
	  $('#addres').attr("disabled",false); 
	  $('#mtn_detail').attr("disabled",false); 
	  $('#mtn_emergency').attr("disabled",false); 
	  $('#comp_detail').attr("disabled",false); 
	  $('#comp_reply').attr("disabled",false);
	  $('#comp_emergency').attr("disabled",false);
	  $('#comp_status').attr("disabled",false);

}


//家政处理
function openHandleButton()
{  
	
	
	var flag = $('#myForm1').validationEngine('validate');
	if(flag){
	var addJson = getSendForm();
	$.ajax({
        type: "post",
        url: "./../houseWork/evnetsend.app?method=eventSendHandle",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {
			layer.msg('操作成功！',{
				time: 2000
			}, function(){
				$('#evnetSendInfo').bootstrapTable('refresh');
				$('#myModal1').modal('hide');
			});
        },
        failure:function(xhr,msg)
        {
			layer.msg('操作失败！请联系技术人员。',{
				time:2000
			},function(){
				$('#myModal1').modal('hide');
			})
        }
    });
	}

}

//派工数据组装
function getSendForm()
{
	var houseKeepingPay=$("#houseKeepingPay").val();
	var money=tran2Digital(houseKeepingPay);
	 var addJson = {
			id:$("#id").val(),//事件ID
			event_no:$("#event_no").val(),//事件单号
		//派工
			send_id:$("#send_id").val(),
			handle_content:$("#handle_content").val(),
			arrv_time:$("#arrv_time").val(),
			houseKeepingPay:money,
			
		};
	return addJson;

}



//消单
function openEventSendXdButton()
{
	var addJson = getSendSigleForm();
	$.ajax({
        type: "post",
        url: "./../houseWork/evnetsend.app?method=eventSendXiaoDan",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {
			layer.msg('操作成功！',{
				time: 2000
			}, function(){
				$('#evnetSendInfo').bootstrapTable('refresh');
				$('#myModal1').modal('hide');
			});
        },
        failure:function(xhr,msg)
        {
			layer.msg('操作失败！请联系技术人员。',{
				time:2000
			},function(){
				$('#myModal1').modal('hide');
			})
        }
    });
}

//物管拒单
function openRefuseEventButton()
{
	
	var addJson = getSendSigleForm();
	$.ajax({
        type: "post",
        url: "./../houseWork/evnetsend.app?method=refuseEventSendOrder",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {
			layer.msg(data+'操作成功！',{
				time: 2000
			}, function(){
				$('#evnetSendInfo').bootstrapTable('refresh');
				$('#myModal1').modal('hide');
			});
        },
        failure:function(xhr,msg)
        {
			layer.msg('操作失败！请联系技术人员。',{
				time:2000
			},function(){
				$('#myModal1').modal('hide');
			})
        }
    });

}

//消单实体
function getSendSigleForm()
{
	var addJson = {
			id:$("#id").val(),//事件ID
			event_no:$("#event_no").val(),//事件单号
		//派工
			send_id:$("#send_id").val(),
			arrv_time:$("#arrv_time").val(),
		};
	return addJson;

}



function getDataForm(){
	var addJson = {
			event_no:$("#event_no").val(),
			event_from:$("#event_source").val(),
			ownerId:$("#ownerId").val(),
			roomId:$("#roomId").val(),
			rpt_name:$("#rpt_name").val(),
			link_phone:$("#in_call").val(),
			addres:$("#addres").val(),
			event_title:$("#event_title").val(),
			pre_time:$("#pre_time").val(),
			event_content:$("#event_content").val(),
			record_id:$("#record_id").val(),
			verify_oper_id:$("#verify_oper_id").val(),
			accept_time:$("#accept_time").val(),
			id:$("#id").val(),
		};
	return addJson;
}

/**选择框
 * 
 * @returns
 */
function getIdSelections() {
	// $('#evnetSendInfo').bootstrapTable('getSelections')
    return $.map($('#evnetSendInfo').bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

//人的中文名字显示
function nameFormatCName(cName,value) {
	//alert(value);
	var addJson = {
		userName:value
	};
	$.ajax({
        type: "post",
        url: "./../assigenwork/assignlist.app?method=nameformat",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {
        	$("#"+cName).val(data);
        },
        failure:function(xhr,msg)
        {
			
        }
    });
}

//状态转换
function stateFormat(value, row, index) {
	if(value==0)
	{
		return ['<span class="label label-danger">未派工</span>'].join('');
	}else if(value==1)
	{
		
		return ['<span class="label label-primary">已派工</span>'].join('');	
	}
	else if(value==2)
	{
		
		return ['<span class="label label-primary">已处理</span>'].join('');	
	}
	else if(value==3)
	{
		return ['<span class="label label-warning">待回访</span>'].join('');
		
	}
	else if(value==4)
	{
		
		return ['已回访'].join('');
	}else if(value==5)
	{
		
		return ['<span class="label label-success">已归档</span>'].join('');	
	}
	
		
	
	
}

//时间显示
function timeFormatCreateTime(value, row, index) {
	var timeFormant = "-";
	if(null == value||value==''){
		
	}else{
		timeFormant = getNowFormatDate(false,value);
	}
	return [timeFormant].join('');
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

//初始化下拉框
function initDrops(){
	var url="./../assigenwork/assignlist.app?method=initDropAll";
    $.post(url,
	      function(data){
    	
			 var list = eval(data);
			 //alert(list);
			 for(var j=0;j<list.length;j++){
				 //alert(list[j].main_event_source+"==="+list[j].main_event_type);
				 //debugger;
				 var detailList = list[j];
				 var code = detailList[0];
				 //事件类型
				if(code=='main_event_type'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#event_type");
				}
				
				//事件来源
				if(code=='main_event_source'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#event_source");
				}
				
				//mtn_emergency  紧急程度
				//投诉基本,一样
				if(code=='main_mtn_emergency'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#mtn_emergency");
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#comp_emergency");
					
				}
				
				//dispatch_status  派工单状态
				if(code=='main_mtn_dispatch_status'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#dispatch_status");
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#comp_status");
				}
	                 
	        };
		}
    );
	//return rtnValue;
}






/**事件类型下拉框隐藏
 * 
 * @param index
 */
function changeEventType(index,processInstanceId){
	var grapIframe="";
	if(null == processInstanceId || processInstanceId=='' || processInstanceId==undefined || processInstanceId=='undefined'){
		if($("#event_type").val()=="0" || index=="0"){
			grapIframe="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=5001&numb="+generateMixed(4);
		}else{
			grapIframe="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=217557&numb="+generateMixed(4);
		}
	}else{
		grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+processInstanceId+"&numb="+generateMixed(4);
	}
	
	if($("#event_type").val()=="0" || index=="0"){
		$("#bxdetail").show();
		$("#tsdetail").hide();
	}else{
		$("#bxdetail").hide();
		$("#tsdetail").show();
	}
	
	$("#grapIframe").attr("src",grapIframe); 

}
var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

function generateMixed(n) {
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()*35);
        res += chars[id];
    }
    return res;
}


function initSearchHandler(){
	var addressSuggest = $("input#comp_operator_username").bsSuggest({
	    url: "./../search.app?type=employee&keyword=",
	    showHeader: true,
	    allowNoKeyword: true,
	    keyField: 'cname',
	    getDataMethod: "url",
	    delayUntilKeyup: true,
	    effectiveFields: ["deptName","position","cname","workState","workTimes"],
	    effectiveFieldsAlias: {
	    	deptName:"部门",
	    	position:"岗位",
	    	cname:"姓名",
	    	workState:"状态",
	    	workTimes:"派工次数"
	    },
	    fnPreprocessKeyword: function(keyword) {
    		//请求数据前，对输入关键字作预处理
    	    return encodeURI(encodeURI(keyword));
	    },
	    processData: function (json) {    // url 获取数据时，对数据的处理，作为 getData 的回调函数
	    	var index, len, data = {value: []};
	        if (!json || !json.result || json.result.length === 0) {
	            return false;
	        }

	        len = json.result.length;

	        for (index = 0; index < len; index++) {
	        	if(json.result[index][6] == '0'){
	        		json.result[index][6] = '空闲中'
	        	}else if(json.result[index][6] == '1'){
	        		json.result[index][6] = '工作中'
	        	}else{
	        		json.result[index][6] = ""
	        	}
	            data.value.push({
	                "userName": json.result[index][0],
	                "cname": json.result[index][1],
	                "deptName": json.result[index][3],
	                "position": json.result[index][5],
	                "workState": json.result[index][6],
	                "workTimes": json.result[index][7]
	                
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
		    $("#comp_operator_id").val(data.userName);
		}).on("onUnsetSelectValue",	
		function(e) {
		    console.log("onUnsetSelectValue")
		});
	
}


//状态转换
function stateFormat(value, row, index) {
	if(value==0)
	{
		return ['<span class="label label-danger">未派工</span>'].join('');
	}else if(value==1)
	{
		
		return ['<span class="label label-primary">已派工</span>'].join('');	
	}
	else if(value==2)
	{
		
		return ['<span class="label label-primary">处理中</span>'].join('');	
	}
	else if(value==3)
	{
		return ['<span class="label label-warning">待回访</span>'].join('');
		
	}
	else if(value==4)
	{
		
		return ['已回访'].join('');
	}else if(value==5)
	{
		
		return ['<span class="label label-success">已归档</span>'].join('');	
	}
	
		
	
	
}

/**操作成功后2秒自动关闭
 * 
 * @param layerName
 */
function closeLayer(){
	setTimeout(function(){//IE6、7不会提示关闭  
		$('#myModal1').modal('hide'); //执行关闭
		//buttonreresh();
	}, 2000);
}

//时间转换针对于时间到达毫秒情况
function subStringTime(time)
{
	var changeTieme=time.substring(0,time.lastIndexOf(".")) 
    return changeTieme;

}



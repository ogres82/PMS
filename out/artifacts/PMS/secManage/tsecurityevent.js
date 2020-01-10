selections = [];
var loginUserName="";
var loginUserCname="";
$(function () {
	initPermissions();
	init();
	initDrops();
	initSearch();//初始化搜索框
	initDeptDrop();
	initPostionDrop();
	//定位表格查询框
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","输入关键字");
	$('#myForm1').validationEngine();
	initBtnEvent();
});
function initPermissions(){
	toolbarBtnInit(); //初始化工具条按钮
}
function initBtnEvent(){
	$("#btn1").bind("click",function(){
		openButtonWindow(1);
	})
	$("#btn4").bind("click",function(){
		openButtonWindow(4);
	})
}
$(document).ready(function(){
	/*$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",});
	$("#external-events div.external-event").each(function(){
		var d={title:$.trim($(this).text())};
		$(this).data("eventObject",d);
		$(this).draggable({
			zIndex:999,revert:true,revertDuration:0})
		});*/
		var b=new Date();
		var c=b.getDate();
		var a=b.getMonth();
		var e=b.getFullYear();
		$("#calendar").fullCalendar({
			header:{left:"prev,next",
			        center:"title",
					right:"month"},
			editable:true,
			droppable:true,
			drop:function(g,h){
				   var f=$(this).data("eventObject");
				   var d=$.extend({},f);
				   d.start=g;
				   d.allDay=h;
		           $("#calendar").fullCalendar("renderEvent",d,true);
		           if($("#drop-remove").is(":checked")){$(this).remove()}
				 },
			eventClick: function(calEvent, jsEvent, view) {
	              showEvent(calEvent.id);
	            },
			events:function(start,end,callback) {
				    $("#calendar").fullCalendar('removeEvents'); //清空上次加载的日程
                    var fstart  = $.fullCalendar.formatDate(start, "yyyy-MM-dd HH:mm");  
                    var fend  = $.fullCalendar.formatDate(end, "yyyy-MM-dd HH:mm"); 
				    $.ajax({
				      type: "POST",
				      data: {"start":fstart,"end":fend},
				      url: "./../secmanage/eventList.app?method=getEvents",
				      success: function (data){
					  	var eventsTemp = eval(data);
						var events=[];
				        for(var i = 0; i < eventsTemp.length; i++){
						  var start=eventsTemp[i].start;
				          var startDate= new Date(Date.parse(start.replace(/-/g, "/")));
				          var obj = new Object();  
				          obj.title = eventsTemp[i].title;
						  obj.id=eventsTemp[i].id;
				          obj.start = startDate;
						  obj.editable=false;
						  events.push(obj);
				        }
						callback(events);
				      },
				      error:function (){
				         alert("服务器异常，请联系管理员！");
				      }
				    });
				}
			})
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
		$('#empInfo').bootstrapTable({
			url: './../secmanage/eventList.app?method=list',         //请求后台的URL（*）
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
            	field: 'eventId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'eventCode',
            	title: '事件编号'
            }, {
                field: 'eventTitle',
                title: '事件标题'
            }, {
                field: 'eventType',
                title: '事件类型',
				formatter:eventTypeFormat
            }, {
            	field: 'eventBurst',
                title: '是否突发事件',
				formatter:eventBurstFormat
            }, {
            	field: 'eventHappenTime',
                title: '事件发生时间'
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
            	$("#btn2").attr("disabled",true);
        		$("#btn3").attr("disabled",true);
        		$("#btn4").attr("disabled",false);
            },
            onUncheckAll: function(rows){
        		$("#btn2").attr("disabled",true);
        		$("#btn3").attr("disabled",true);
        		$("#btn4").attr("disabled",true);
            }
		});
	};
	function eventTypeFormat(value, row, index) {
		if(value==0)
		{
			return "消防";
		}else if(value==1){
			return "交通";
		}else{
			return "治安";
		}
    }
	function eventBurstFormat(value, row, index) {
		if(value==0)
		{
			return "否";
		}else{
			return "是";
		}
    }
	function operateFormatter(value, row, index) {
        return tableBtnInit();
    }
	//操作列的事件
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
        	editOrCheck(row,3);
        },
		'click #a_edit': function (e, value, row, index) {
			editOrCheck(row,2);
	    },
        
    };
	//查看和编辑
    function editOrCheck(obj,type){
		console.log(obj);
    	openButtonWindow(type,obj.eventId)
	}
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
		var r = $('#empInfo').bootstrapTable('getSelections');
    	if(r.length==0){
    		$("#btn2").attr("disabled",true);
    		$("#btn3").attr("disabled",true);
    		$("#btn4").attr("disabled",true);
    	}
    	if(r.length==1){
    		$("#btn2").attr("disabled",false);
    		$("#btn3").attr("disabled",false);
    		$("#btn4").attr("disabled",false);
    	}
    	if(r.length>1){
    		$("#btn2").attr("disabled",true);
    		$("#btn3").attr("disabled",true);
    	}
	}
	
	//初始化下拉框
	function initDrops(){
		var url="./../empInfo/empList.app?method=initDropAll";
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
		var url="./../empInfo/empList.app?method=initDeptDrop";
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
	
	
	/**
	 * 初始化岗位下拉框
	 */
	function initPostionDrop(){
		var url="./../empInfo/empList.app?method=initPositionDrop";
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 //员工岗位
					$("<option value='"+detailList.id+"'>"+detailList.name+"</option>").appendTo("#empPostId");
					
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
        return $.map($('#empInfo').bootstrapTable('getSelections'), function (row) {
            return row.eventId;
        });
    }
    
    function getNameSelections() {
        return $.map($('#empInfo').bootstrapTable('getSelections'), function (row) {
            return row.empName;
        });
    }
    /**
     * 保存操作
     */
    function openSaveButton(){
    	
    	var flag = $('#myForm1').validationEngine('validate');
    	if(flag){
    		$("#btEmpAdd").attr("disabled","true");
        	var addJson = getDataForm();
        	$.ajax({
                type: "post",
                url: "./../secmanage/eventList.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	layer.msg('操作成功！',{
        				time: 2000
        			}, function(){
        				$('#empInfo').bootstrapTable('refresh');
        				$('#myModal1').modal('hide');
						$('#calendar').fullCalendar('refetchEvents');
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
			    eventId:$("#eventId").val(),
    			eventCode:$("#eventCode").val(),
    			eventTitle:$("#eventTitle").val(),
    			eventType:$("#eventType").val(),
    			eventHappenTime:$("#eventHappenTime").val(),
    			eventDetailText:$("#eventDetailText").val(),
    			eventResultText:$("#eventResultText").val(),
    			eventRemarkText:$("#eventRemarkText").val(),
    			eventBurst:$("#eventBurst").val(),
    			eventChargerId:$("#eventChargerId").val(),
    			eventRecTime:$("#eventRecTime").val(),
    		};
    	return addJson;
    }
    
    /**前排按钮监控
     * 
     * @param type
     */
    function openButtonWindow(type,id){
    	$('#myForm1').validationEngine('hide');
    	$('#btEmpAdd').removeAttr("disabled"); 
    	$('#btEmpAdd').show();
    	var isOpen = false;
    	selections = getIdSelections();
    	var length=selections.length;
    	if(type==1 || type==4){
    		isOpen = true;
    	}else{
    		if(id){
    			isOpen = true;
    		 }
    	}
    	
    	if(isOpen){
    		var empId = 0;
    		selections = getIdSelections();
    		empId=id;
    		var empName= getNameSelections()[0];
    		var urlmethod = "";
    		//新增
    		if(type==1){
    			urlmethod = "method=addEmp";
    		}else if(type==2){
    			//修改
    			urlmethod = "method=edit&empId="+empId;
    		}else if(type==3){
    			//查看
    			$('#btEmpAdd').attr("disabled","true"); 
    			$('#btEmpAdd').hide();
    			urlmethod = "method=viewEmp&empId="+empId;
    		}else if(type==4){
    			//删除
    			//var deleteIds = JSON.stringify(selections);
    			urlmethod = "method=delete";
    			//alert(urlmethod);
    		}
    		
    		if(type==4){
    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="./../secmanage/eventList.app?"+urlmethod;
    				$.post(url,
       						{empId:selections+""},
       	    		        function(data){
       							layer.msg(eval(data),{
       			    				time: 2000
       			    			}, function(){
       			    				$('#empInfo').bootstrapTable('refresh');
									$('#calendar').fullCalendar('refetchEvents');
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
				var url="./../secmanage/eventList.app?method=add";
    		    $.post(url,function(data){
					clearForm();
					var res=eval(data);
					var now=getNowFormatDate(true);
					$("#eventCode").val(res.event_no);
					$("#eventRecTime").val(now);
					$("#eventChargerId").val(loginUserCname);
    			    //$("#myModalTitle").html("新增");
    			    $('#myModal1').modal();
					//var event_no=data.event_no;
					//alert(event_no);
				});
    			
    		}else{
    			$("#myModalTitle").html(empName);
    			var url="./../secmanage/eventList.app?"+urlmethod;
    		    $.post(url,function(data){
    		   		var list = eval(data);
    		   		$("#eventCode").val(list.eventCode);
    		   		$("#eventId").val(list.eventId);
        			$("#eventTitle").val(list.eventTitle);
        			$("#eventType").val(list.eventType);
        			$("#eventHappenTime").val(list.eventHappenTime);
        			$("#eventDetailText").val(list.eventDetail);
        			$("#eventResultText").val(list.eventResult);
        			$("#eventRemarkText").val(list.eventRemark);
        			$("#eventBurst").val(list.eventBurst);
        			$("#eventChargerId").val(list.eventChargerId);
        			$("#eventRecTime").val(list.eventRecTime);
    			});
    			$('#myModal1').modal();
    		}
    		
    	}
    }
    
    //清空表单
    function clearForm(){
    	$("#eventType").val("");
    	$("#eventCode").val("");
		$("#eventTitle").val("");
		$("#eventHappenTime").val("");
		$("#eventDetailText").val("");
		$("#eventResultText").val("");
		$("#eventRemarkText").val("");
		$("#eventBurst").val("");
		$("#eventChargerId").val("");
		$("#eventRecTime").val("");
		
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
    	var addressSuggest = $("input#addres").bsSuggest({
    	    url: "./../empInfo/empList.app?type=owner&keyword=",
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
 function changeView(type){
	$('#tableArea').removeAttr("class").attr("class", "");
	$('#chartsArea').removeAttr("class").attr("class", "");
	if(type==1){
		tout();
		setTimeout(thide,300);
		setTimeout(cshow,300);
		$('#btn5').hide();
		$('#btn6').show();
	}
	if(type==2){
		cout();
		setTimeout(chide,300);
		setTimeout(tshow,300);
		$('#btn5').show();
		$('#btn6').hide();
	}
}

function tshow(){
	$('#tableArea').show().addClass("animated slideInLeft");
}
function tout(){
	$('#tableArea').show().addClass("animated slideOutRight");
}
function thide(){
	$('#tableArea').hide();
}
function cshow(){
	$('#chartsArea').show().addClass("animated slideInLeft");
}
function cout(){
	$('#chartsArea').addClass("animated slideOutRight");
}
function chide(){
	$('#chartsArea').hide();
}

 
 
 
 
 function showEvent(eventId){
 	$('#myForm1').validationEngine('hide');
	$('#btEmpAdd').attr("disabled","true"); 
    $('#btEmpAdd').hide();
	var urlmethod = "method=viewEmp&empId="+eventId;
	$("#myModalTitle").html("查看");
    var url="./../secmanage/eventList.app?"+urlmethod;
    $.post(url,function(data){
   		var list = eval(data);
   		$("#eventCode").val(list.eventCode);
   		$("#eventId").val(list.eventId);
		$("#eventTitle").val(list.eventTitle);
		$("#eventType").val(list.eventType);
		$("#eventHappenTime").val(list.eventHappenTime);
		$("#eventDetailText").val(list.eventDetail);
		$("#eventResultText").val(list.eventResult);
		$("#eventRemarkText").val(list.eventRemark);
		$("#eventBurst").val(list.eventBurst);
		$("#eventChargerId").val(list.eventChargerId);
		$("#eventRecTime").val(list.eventRecTime);
	});
	$('#myModal1').modal();
 }

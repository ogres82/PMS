$(function(){
	initData();
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
	//初始化下拉框
	initSearchHandler();//初始化搜索框
	$('#myForm2').validationEngine();
});


$(function(){
	toolbarBtnInit();//初始化权限按钮
	for(var i=0,len=btnIdArray.length;i<len;i++)
	{
	 if(btnIdArray[i]=="btn4")
	 {
		 $("#btn4").bind('click',function()
		{
			 openDispatchButton(4);
		});
	 }
	}
	$("#btn4").attr("disabled",true);
	
});

function initData(){
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
}

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#dispatchDataInfo').bootstrapTable({
			url: './../assigenwork/assignlist.app?method=listDispatch',         //请求后台的URL（*）
			//url: './../assigenwork/assignlist.app?method=listDispatch', 
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
                        field: 'dispatch_kid',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    }, {
                        field: 'mtn_id',
                        title: '关联报修单',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        
                    }, {
                        field: 'createTime',
                        title: '受理时间',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter:timeFormatCreateTime
                    }, {
                        field: 'createby_name',
                        title: '受理人',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, 
                     {
                        field: 'rpt_name',
                        title: '报事人',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    },
                    {
                        field: 'in_call',
                        title: '联系电话',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    },{
                        field: 'dispatch_id',
                        title: '派工单号',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                    }, {
                        field: 'addres',
                        title: '地址',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        
                    },{
                        field: 'dispatch_expense',
                        title: '维修费用',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter: rpt_name
                    }, {
                        field: 'dispatch_handle_name',
                        title: '处理人',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    },{
                        field: 'dispatch_status_name',
                        title: '派工状态',
                        sortable: true,
                        align: 'center',
                        formatter: stateFormat
                    }, {
                        field: 'dispatch_finish_time',
                        title: '销单时间',
                        align: 'center',
                        //valign: 'middle',
                        sortable: true
                        //formatter:timeFormatCreateTime
                    }, {
                    	field: 'operate',
                        title: '操作',
                        align: 'center',
                        width:'15%',
                        events: operateEvents,
                        formatter: operateFormatter
                    }
               ],onCheck:function(row,e){
               		tableCheckEvents();
               },
               onUncheck: function(row,e){
               		tableCheckEvents();
               },
               onCheckAll: function(rows){
           			$("#btn4").attr("disabled",false);
               },
               onUncheckAll: function(rows){
           			$("#btn3").attr("disabled",true);
               }
		});
	};
   function operateFormatter(value, row, index) {
		return tableBtnInit();
    }
	//操作列的事件
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
        	$('#dispbtn5').hide();
   			$('#dispbtn6').hide();
   			$('#dispbtn7').hide();
   			$('#dispbtn8').hide();
   			$('#dispbtn9').hide();
   			$('#dispatch_arrive_time').attr("disabled","true"); 
        	openDispatchTableButton(3,row);
        },
		'click #a_send': function (e, value, row, index){
			clear_disPathchInfo();
			$('#dispbtn5').show();
   			$('#dispbtn6').hide();
   			$('#dispbtn7').hide();
   			$('#dispbtn8').hide();
   			$('#dispbtn9').hide();
   			if(row.dispatch_status=='0'){
   				openDispatchTableButton(1,row);
   			}else{
   				layer.alert("已派工");
   				return;
   			}
	    },
	    'click #a_comfirm': function (e, value, row, index){
	    	$('#dispbtn5').hide();
   			$('#dispbtn6').show();
   			$('#dispbtn7').hide();
   			$('#dispbtn8').hide();
   			$('#dispbtn9').hide();
   			if(row.dispatch_status=='1')
   			{
   				openDispatchTableButton(2,row);
   			}else
   			{
   				layer.alert("已现场确认，请选择销单");
   				return;
   			}
	    },
	    'click #a_cancel': function (e, value, row, index)
		{
	    	$('#dispbtn5').hide();
   			$('#dispbtn6').hide();
   			$('#dispbtn7').show();
   			$('#dispbtn8').hide();
   			$('#dispatch_arrive_time').attr("disabled","true");
   			if(row.dispatch_status=='2')
   			{
   				openDispatchTableButton(10,row);
   				
   			}else
   			{
   				layer.alert("不可销单，工单已经不处于可取消流程中");
   				return;
   			}
	    	
	    },
	    'click #a_owncancel': function (e, value, row, index)
		{
	    	$('#dispbtn5').hide();
   			$('#dispbtn6').hide();
   			$('#dispbtn7').hide();
   			$('#dispbtn8').show();
   			$('#dispbtn9').hide();
   			$('#material_cost').attr("disabled",true); 
   			$('#labor_cost').attr("disabled",true); 
   			$('#dispatch_expense').attr("disabled",true); 
   			if(row.dispatch_status=='0'||row.dispatch_status=='1')
   			{
   				openDispatchTableButton(11,row);
   				
   			}else
   			{
   				layer.alert("不可取消，工单已经不处于可取消流程中");
   				return;
   			}
	    	
	    }, 'click #a_dispatchBtn': function (e, value, row, index) 
        {
	    	$('#dispbtn5').hide();
   			$('#dispbtn6').hide();
   			$('#dispbtn7').hide();
   			$('#dispbtn8').hide();
   			$('#dispbtn9').show();
   			$('#dispatch_arrive_time').attr("disabled","true"); 
   			
        	openDispatchTableButton(15,row);
        }
        
    };
	
	oTableInit.queryParams = function (params) {
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search:encodeURI(params.search)
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
	function stateFormat(value) {
		if(value == "未派工"){
			return ['<span class="label label-danger">'+value+'</span>'].join('');
		}else if(value == "已派工" || value == "处理中"){
			return ['<span class="label label-primary">'+value+'</span>'].join('');
		}else if(value == "待回访"){
			return ['<span class="label label-warning">'+value+'</span>'].join('');
		}else if(value == "已归档"){
			return ['<span class="label label-success">'+value+'</span>'].join('');
		}else{
			return [value].join('');
		}
	};
	return oTableInit;
	
};




//表格选择事件
function tableCheckEvents(){
	var r = $('#dispatchDataInfo').bootstrapTable('getSelections');
	if(r.length==0){
		$("#btn3").attr("disabled",true);
		$("#btn4").attr("disabled",true);
	}
	if(r.length==1){
		$("#btn3").attr("disabled",false);
		$("#btn4").attr("disabled",false);
	}
	if(r.length>1){
		$("#btn2").attr("disabled",true);
		$("#btn3").attr("disabled",true);
		$("#btn5").attr("disabled",true);
	}
}
/*处理人搜索框初始化*/
function initSearchHandler(){
	var addressSuggest = $("input#dispatch_handle_username").bsSuggest({
	    url: "./../search.app?type=engine&keyword=",
	    showHeader: true,
//	    allowNoKeyword: false,
	    autoDropup: true,
	    keyField: 'cname',
	    getDataMethod: "url",
	    delayUntilKeyup: true,
	    effectiveFields: ["deptName","position","cname"],
//	    effectiveFields: ["deptName","position","cname","workState","workTimes"],
	    effectiveFieldsAlias: {
	    	deptName:"部门",
	    	position:"岗位",
	    	cname:"姓名",
//	    	workState:"状态",
//	    	workTimes:"派工次数"
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
//	        	if(json.result[index][6] == '0'){
//	        		json.result[index][6] = '空闲中'
//	        	}else if(json.result[index][6] == '1'){
//	        		json.result[index][6] = '工作中'
//	        	}else{
//	        		json.result[index][6] = ""
//	        	}
	            data.value.push({
	                "userName": json.result[index][0],
	                "cname": json.result[index][1],
	                "deptName": json.result[index][3],
	                "position": json.result[index][5],
//	                "workState": json.result[index][6],
//	                "workTimes": json.result[index][7]
	                
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
		    $("#dispatch_handle_id").val(data.userName);
		    $("#deptName").val(data.deptName);
		    $("#dispatch_handle_username").val(data.cname);
		}).on("onUnsetSelectValue",	
		function(e) {
		    console.log("onUnsetSelectValue")
		});
}


//列表按钮数据控制

function openDispatchTableButton(type,obj){
	$('#myForm2').validationEngine('hide');
	var isOpen = false;
	var urlmethod = "";
	if(type==3){
		if(obj.dispatch_status==0||obj.dispatch_status==1){
			hidecontent();
		}
		disableEdit();//只准查看，不准修改
	}else if(type==2)
	{
		ableEdit();
	}else if(type==10)	
	{
		disableEdit();
	}else if(type==11)//业主取消工单
	{
		disableEdit();//只准查看，不准修改
	}else if(type==15){
		disableEdit();
		$('#dispatch_handle_username').attr("disabled",false);
		$('#fjbtn').attr("disabled",false);
	} else {
		//showButton();
		ableEdit();
	}
	rpt_id=obj.mtn_id;
	//派工按钮和查看
	if(type==3 || type==1||type==2||type==10||type==11){
		clear_disPathchInfo();
		urlmethod = "method=dispatchView&assignId="+rpt_id;
		var url="./../assigenwork/assignlist.app?"+urlmethod;
		loadData(url);
	}else if(type==15)
	{
		urlmethod = "method=dispatchView&assignId="+rpt_id;
		var url="./../assigenwork/assignlist.app?"+urlmethod;
		loadData(url);
	}else
	{
		openSaveButton(type);
	}
}




function openDispatchButton(type){
	$('#myForm2').validationEngine('hide');
	var isOpen = false;
	selections = getIdSelections();
	var urlmethod = "";
	var length=selections.length;
	if(type==4){
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
		if(type==4){
			//删除
			urlmethod = "method=dispatchDelete";
			deletaDispatch(urlmethod);
		}else{
			if(type==3){
				//hiddenButton();
				disableEdit();//只准查看，不准修改
			}else{
				//showButton();
				ableEdit();
			}
			rpt_id=selections[0];
			//派工按钮和查看
			if(type==3 || type==1){
				clear_disPathchInfo();
				urlmethod = "method=dispatchView&assignId="+rpt_id;
				var url="./../assigenwork/assignlist.app?"+urlmethod;
				loadData(url);
			}else{
				openSaveButton(type);
			}
		}
	}
}

//清空
function clear_disPathchInfo()
{
		$("#rpt_id").val("");
		$("#rpt_name").val("");
		$("#in_call").val("");
		$("#addres").val("");
		$("#createTime").val("");
		$("#dispatch_time").val("");
		$("#sl_time").val("");
		$("#mtn_detail").val("");
		$("#mtn_emergency").val("");
		$("#dispatch_status").val("");
		$("#dispatch_handle_id").val("");
		$("#mtn_type").val("");	
   		$("#dispatch_expense").val("");
   		$("#material_cost").val("");
   		$("#labor_cost").val("");
   		$("#dispatch_result").val("");
   		$("#dispatch_finish_time").val("");
}

//不可编辑
function disableEdit()
{
	$('#mtn_emergency').attr("disabled",true); 
	$('#dispatch_handle_id').attr("disabled",true); 
	$('#dispatch_handle_username').attr("disabled",true); 
	$('#mtn_type').attr("disabled",true); 
	$('#mtn_priority').attr("disabled",true); 
	$('#dispatch_time').attr("disabled",true);
	$('#material_cost').attr("disabled",true); 
	$('#labor_cost').attr("disabled",true); 
	$('#dispatch_tools').attr("disabled",true); 
	$('#dispatch_expense').attr("disabled",true); 
	$('#dispatch_result').attr("disabled",true); 
	$('#mtn_priority').attr("disabled",true); 
	$('#handerNames').attr("disabled",true); 
	$('#fjbtn').attr("disabled",true);
	
}

//编辑
function ableEdit()
{
	$('#mtn_emergency').attr("disabled",false); 
	$('#dispatch_handle_id').attr("disabled",false); 
	$('#dispatch_handle_username').attr("disabled",false); 
	$('#mtn_type').attr("disabled",false); 
	$('#mtn_priority').attr("disabled",false); 
	$('#dispatch_tools').attr("disabled",false); 
	$('#dispatch_expense').attr("disabled",false); 
	$('#dispatch_result').attr("disabled",false); 
	$('#mtn_priority').attr("disabled",false); 	
	$('#fjbtn').attr("disabled",false);

}

//隐藏不要的内容
function hidecontent()
{
	$("#weixiu").hide();
	$("#weixucl").hide();
	$("#weixiujg").hide();
	$("#xiaodan").hide();

}

//展示
function showContent()
{
	$('#mtn_emergency').attr("disabled","true"); 
	$("#weixiu").show();
	$("#weixucl").show();
	$("#weixiujg").show();
	$("#xiaodan").show();
}
function deletaDispatch(urlmethod){

	layer.confirm("您确定要删除所选信息吗?",{
		skin: 'layui-layer-molv', 
		btn: ['确定','取消']
	},function(){
        var url="./../assigenwork/assignlist.app?"+urlmethod;
		var deleteIds = JSON.stringify(selections);
		//alert(url);
		$.post(url,
				{assignId:deleteIds},
   		        function(data){
						layer.alert(data, {
						  skin: 'layui-layer-molv' //样式类名
						  ,closeBtn: 0
						}, function(){
							var locationUrl="./../assignwork/DispatchWorkList.jsp";
							window.location.href=locationUrl;
						});
					});
	},function(){
		return;
	});

}

function openEditButton(index){
	var flag = $("#dispatch_handle_id").val();
	var dispatch_handle_username= $("#dispatch_handle_username").val();
	var addJson = getDataForm(index);
	$.ajax({
        type: "post",
        url: "./../assigenwork/assignlist.app?method=dispatchEditeSave",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {
        	layer.msg('修改成功！',{
				time: 2000
			}, function(){
				$('#dispatchDataInfo').bootstrapTable('refresh');
				$('#myModal2').modal('hide');
			});
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！请联系技术人员。',{
				time:2000
			},function(){
				$('#myModal2').modal('hide');
			})
        }
    });
}
/**
 * 派工按钮，分派人
 * 现场测试确认
 * 消单
 * 
 */

function openSaveButton(index){
	var flag = $("#dispatch_handle_id").val();
	var dispatch_handle_username= $("#dispatch_handle_username").val();
	//hiddenButton();
	var flag = $('#myForm2').validationEngine('validate');
	if(flag){
	var addJson = getDataForm(index);
	$.ajax({
        type: "post",
        url: "./../assigenwork/assignlist.app?method=dispatchSave",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {
        	layer.msg('操作成功！',{
				time: 2000
			}, function(){
				$('#dispatchDataInfo').bootstrapTable('refresh');
				$('#myModal2').modal('hide');
			});
//			closeLayer();
    		
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！请联系技术人员。',{
				time:2000
			},function(){
				$('#myModal2').modal('hide');
			})
        }
    });
	}
}

/**操作成功后2秒自动关闭
 * 
 * @param layerName
 */
function closeLayer(){
	setTimeout(function(){//IE6、7不会提示关闭  
		$('#myModal2').modal('hide'); //执行关闭
		//buttonreresh();
	}, 2000);
}


/**取数据
 * 
 * @param ndex
 * @returns {___anonymous5480_5481}
 */
function getDataForm(index){
	//加载不同的数据
	var addJson = {};
	//dispatch_handle_id=$("#dispatch_handle_id").val()
	if(index==5){
		addJson = {
			mtn_id:$("#mtn_id").val(),
			dispatch_handle_id:$("#dispatch_handle_id").val(),
			dispatch_handle_name:$("#dispatch_handle_username").val(),
			deptName:$("#deptName").val(),
			mtn_emergency:$("#mtn_emergency").val(),
			type:index
		};
	}else if(index==6||index==9){
		addJson = {
			mtn_id:$("#mtn_id").val(),
			mtn_type:$("#mtn_type").val(),
			dispatch_tools:$("#dispatch_tools").val(),
			dispatch_expense:$("#dispatch_expense").val(),
			dispatch_result:$("#dispatch_result").val(),
			mtn_priority:$("#mtn_priority").val(),
			handerNames:$("#handerNames").val(),
			handerIds:$("#handerIds").val(),
			dispatch_handle_id:$("#dispatch_handle_id").val(),
			dispatch_handle_name:$("#dispatch_handle_username").val(),
			deptName:$("#deptName").val(),
			material_cost:tran2Digital($("#material_cost").val()),
			labor_cost:tran2Digital($("#labor_cost").val()),
			dispatch_expense:tran2Digital($("#dispatch_expense").val()),
			type:index
		};
		
	}else if(index==7){
		addJson = {
			mtn_id:$("#mtn_id").val(),
			type:index
		};
		$('#dispbtn6').attr("disabled","true");
	}
else if(index==8){
	addJson = {
		mtn_id:$("#mtn_id").val(),
		type:index
	};
}
	
	return addJson;
}

/**派工，查看加载数据
 * 
 * @param url
 */
function loadData(url){
	$.post(url,function(data){
   		var list = eval(data);
   		showContent();
   		nameFormatCName("createby",list[0].createby);
   		$("#dispatch_id").val(list[1].dispatch_id);
   		$("#rpt_id").val(list[0].rpt_id);
   		$("#rpt_name").val(list[0].rpt_name);
   		$("#in_call").val(list[0].in_call);
   		$("#addres").val(list[0].address);
   		$("#handerNames").val(list[0].hander_names);
   		//$("#createby").val(list[0].createby);
   		$("#createTime").val(getNowFormatDate(false,list[0].createTime));
   		$("#dispatch_time").val(getNowFormatDate(false,list[1].createTime));//派工时间
   		$("#sl_time").val(getNowFormatDate(false,list[1].sl_time));
   		$("#mtn_detail").val(list[1].mtn_detail);
   		$("#mtn_emergency").val(list[1].mtn_emergency);
   		$("#dispatch_status").val(dispatch_status);
   		$("#dispatch_handle_id").val(list[1].dispatch_handle_id);
   		//alert(list[1].dispatch_handle_username);
   		$("#dispatch_handle_username").val(list[1].dispatch_handle_name);
   		$("#mtn_type").val(list[1].mtn_type);
   		$("#dispatch_arrive_time").val(getNowFormatDate(false,list[1].dispatch_arrive_time));
   		$("#dispatch_tools").val(list[1].dispatch_tools);
   		var sumprice=list[1].dispatch_expense;
   		var materprice=list[1].material_cost;
   		var laberprice=list[1].labor_cost;
   		if(typeof(sumprice) != 'undefined' || sumprice==""){
   			$("#dispatch_expense").val(outputmoney(sumprice));
   		}
   		if(typeof(materprice) != 'undefined' || materprice==""){
   			$("#material_cost").val(outputmoney(materprice+""));
   		}
   		if(typeof(laberprice) != 'undefined' || laberprice==""){
   			$("#labor_cost").val(outputmoney(laberprice+""));
   		}
   		$("#dispatch_result").val(list[1].dispatch_result);
   		$("#dispatch_finish_time").val(getNowFormatDate(false,list[1].dispatch_finish_time));
   		$("#mtn_id").val(list[1].mtn_id);
   		$("#mtn_priority").val(list[1].mtn_priority);
   		var grapIframe="";
   		var processInstanceId = list[0].processInstanceId;
   		//alert(processInstanceId);
   		grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+processInstanceId+"&numb="+generateMixed(4);
   		$("#grapIframe").attr("src",grapIframe); 
   		changeMtnPriority();
	});
	$('#myModal2').modal();
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

function hiddenButton(){
	$('#dispbtn5').attr("disabled","true"); 
	$('#dispbtn6').attr("disabled","true");
	$('#dispbtn7').attr("disabled","true");
	$('#dispbtn8').attr("disabled","true");
}


function showButton(){
	$('#dispbtn5').removeAttr("disabled");
	$('#dispbtn6').removeAttr("disabled");
	$('#dispbtn7').removeAttr("disabled"); 
}

function getIdSelections() {
    return $.map($('#dispatchDataInfo').bootstrapTable('getSelections'), function (row) {
        return row.mtn_id
    });
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
				 //紧急程度
				if(code=='main_mtn_emergency'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#mtn_emergency");
				}
				
				//mtn_emergency  维修类别
				if(code=='main_mtn_type'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#mtn_type");
				}
	            
				//dispatch_status  派工单状态
				if(code=='main_mtn_dispatch_status'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#dispatch_status");
				}
				
				//mtn_priority  业主维修意见
				if(code=='main_disp_mtn_priority'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#mtn_priority");
				}
	        };
		}
    );
	//return rtnValue;
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

function changeMtnPriority(){
	var mtn_type = $('#mtn_type').val();
	if(mtn_type==1){
		$('#div_mtn_priority').show();
	}else{
		$('#div_mtn_priority').hide();
	}
	
}

function getNowFormatDate(flag,vardate) {
	if(!flag && (null == vardate || vardate ==''))
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


function refushtable()
{
	
	$('#dispatchDataInfo').bootstrapTable('refresh');
	
	$('#myModal2').modal('hide');
};


function func1(){
	layer.open({
		  title:['选择附加处理人','color:white;'],
		  type: 2,
		  area: ['700px', '430px'],
		  fix: false, //不固定
		  maxmin: true,
		  content: 'SelectHandler.jsp'
		});
}
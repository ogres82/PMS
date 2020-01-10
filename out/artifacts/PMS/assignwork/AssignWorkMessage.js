$(function(){
	
	initDrops();
	initSearchHandler();
	//初始化下拉框
	$('#myForm2').validationEngine();
	 dispatchHandle(rpt_id)
	
	  
});

function dispatchHandle(rpt_id)
{ 
	clear_disPathchInfo();
	urlmethod = "method=dispatchView&assignId="+rpt_id;
	var url="./../assigenwork/assignlist.app?"+urlmethod;
	loadData(url);
}

//控制输入字符
$(document).ready(function(){
	$("#mtn_detail").keydown(function(){
		var curLength=$("#mtn_detail").val().length;	
		if(curLength>=501){
			var num=$("#mtn_detail").val().substr(0,500);
			$("#mtn_detail").val(num);
			alert("超过字数限制，多出的字将被截断！" );
		}
		else{
			$("#textCount").text(500-$("#mtn_detail").val().length)
		}
	})
}) 


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

function closeMyWindow()
{
	window.close(); 
}
//不可编辑
function disableEdit()
{
	$('#mtn_emergency').attr("disabled","true"); 
	$('#dispatch_handle_id').attr("disabled","true"); 
	$('#dispatch_handle_username').attr("disabled","true"); 
	$('#mtn_type').attr("disabled","true"); 
	$('#mtn_priority').attr("disabled","true"); 
	
	$('#material_cost').attr("disabled","true"); 
	$('#labor_cost').attr("disabled","true"); 
	$('#dispatch_tools').attr("disabled","true"); 
	$('#dispatch_expense').attr("disabled","true"); 
	$('#dispatch_result').attr("disabled","true"); 
	$('#mtn_priority').attr("disabled","true"); 
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
    $('#dispatch_handle_username').attr("disabled","true");
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


//保存
function openSaveButton(){
	$("#dispatch_handle_id").removeClass("form-control validate[required]");
	$("#dispatch_handle_id").addClass("form-control");
	$("#dispatch_handle_username").removeClass("form-control validate[required]");
	$("#dispatch_handle_username").addClass("form-control");
	
	$("#comp_operator_id").removeClass("form-control validate[required]");
	$("#comp_operator_id").addClass("form-control");
	$("#comp_operator_username").removeClass("form-control validate[required]");
	$("#comp_operator_username").addClass("form-control");
	
	
	var flag = $('#myForm2').validationEngine('validate');
	if(flag){
	$("#btAssigenAdd").attr("disabled","true");
	var addJson = getDataForm();
	//alert(addJson);
	$.ajax({
        type: "post",
        url: "./../assigenwork/assignlist.app?method=save",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {
			layer.msg('操作成功！',{
				time: 2000
			}, function(){
				var parentDom = parent.$('#evnetSendInfo');
				 parent.$('#evnetSendInfo').bootstrapTable('refresh');
				 var index = parent.layer.getFrameIndex(window.name); 	
				 parent.layer.close(index);
			});
//			closeLayer();
    		
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


function getDataForm(){
	var addJson = {
			createTime:$("#createTime").val(),
			rpt_kid:$("#rpt_kid").val(),
			rpt_id:$("#rpt_id").val(),
			rpt_kid:$("#rpt_kid").val(),
			rpt_name:$("#rpt_name").val(),
			in_call:$("#in_call").val(),
			addres:$("#addres").val(),
			owner_type:$("#owner_type").val(),
			event_source:$("#event_source").val(),
			event_time:$("#event_time").val(),
			event_type:$("#event_type").val(),
			
			ownerId:$("#ownerId").val(),
			roomId:$("#roomId").val(),
			roomNum:$("#roomNo").val(),
			//报修
			mtn_detail:$("#mtn_detail").val(),
			mtn_emergency:$("#mtn_emergency").val(),
			dispatch_status:$("#dispatch_status").val(),
			comp_reply:$("#comp_reply").val(),
			//投诉
			comp_status:$("#comp_status").val(),
			comp_emergency:$("#comp_emergency").val(),
			comp_detail:$("#comp_detail").val()
		};
	return addJson;
}


//直接派工，未保存
function openSendDisPatchButton()
{
	$("#dispatch_handle_id").removeClass("form-control ");
	$("#dispatch_handle_id").addClass("form-control validate[required]");
	$("#dispatch_handle_username").removeClass("form-control ");
	$("#dispatch_handle_username").addClass("form-control validate[required]");
	
	$("#comp_operator_id").removeClass("form-control ");
	$("#comp_operator_id").addClass("form-control validate[required]");
	$("#comp_operator_username").removeClass("form-control ");
	$("#comp_operator_username").addClass("form-control validate[required]");
	var flag = $('#myForm1').validationEngine('validate');
	if(flag){
		var addJson = sendDispatchForm();
		if(addJson=='1')
		{
			layer.alert("请选择投诉级别为紧急或重大");
			return;
		}else{
		$.ajax({
	        type: "post",
	        url: "./../assigenwork/directDispatch.app?method=directDispatch",
	        data: addJson,
	        dataType: "json",
			async : false,
	        success: function(data)
	        {
				layer.msg(data+'操作成功！',{
					time: 2000
				}, function(){
					var parentDom = parent.$('#evnetSendInfo');
					 parent.$('#evnetSendInfo').bootstrapTable('refresh');
					 var index = parent.layer.getFrameIndex(window.name); 	
					 parent.layer.close(index);
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
}


function sendDispatchForm()
{
	rpt_id=$("#rpt_id").val();
	comp_emergency=$("#comp_emergency").val();
	event_type=$("#event_type").val();
	if(event_type=='1'){
		var rpt_idStr=rpt_id.substring(0,2);
		if(comp_emergency=='0')
		{
			 return "1";
		}else
		{
		
			addJson = {
					rpt_id:$("#rpt_id").val(),
					rpt_name:$("#rpt_name").val(),
					in_call:$("#in_call").val(),
					addres:$("#addres").val(),
					owner_type:$("#owner_type").val(),
					event_source:$("#event_source").val(),
					event_time:$("#event_time").val(),
					event_type:$("#event_type").val(),
					ownerId:$("#ownerId").val(),
					roomId:$("#roomId").val(),
					roomNum:$("#roomNo").val(),
					//报修
					mtn_detail:$("#mtn_detail").val(),
					mtn_emergency:$("#mtn_emergency").val(),
					dispatch_status:$("#dispatch_status").val(),
					comp_reply:$("#comp_reply").val(),
					dispatch_handle_id:$("#comp_operator_id").val(),
					dispatch_handle_username:$("#comp_operator_username").val(),
					
					comp_detail:$("#comp_detail").val(),
					comp_emergency:$("#comp_emergency").val(),
					
					comp_status:$("#comp_status").val(),
					finish_time:$("#finish_time").val()
					
					};
			
			return addJson;
		}
	}
	else{	
	addJson = {
			rpt_id:$("#rpt_id").val(),
			rpt_name:$("#rpt_name").val(),
			in_call:$("#in_call").val(),
			addres:$("#addres").val(),
			owner_type:$("#owner_type").val(),
			event_source:$("#event_source").val(),
			event_time:$("#event_time").val(),
			event_type:$("#event_type").val(),
			ownerId:$("#ownerId").val(),
			roomId:$("#roomId").val(),
			roomNum:$("#roomNo").val(),
			//报修
			mtn_detail:$("#mtn_detail").val(),
			mtn_emergency:$("#mtn_emergency").val(),
			dispatch_status:$("#dispatch_status").val(),
			comp_reply:$("#comp_reply").val(),
			dispatch_handle_id:$("#dispatch_handle_id").val(),
			dispatch_handle_username:$("#dispatch_handle_username").val(),
			
			comp_detail:$("#comp_detail").val(),
			comp_emergency:$("#comp_emergency").val(),
			
			comp_status:$("#comp_status").val(),
			finish_time:$("#finish_time").val()
			
			};
			
     return addJson;
	}
} 

/**派工，查看加载数据
 * 
 * @param url
 */
function loadData(url){
	$.post(url,function(data){
   		var list = eval(data);
   	    var rpt_idStr=list[0].rpt_id.substring(0,2);
   	   if(rpt_idStr=='BX')
		{
   		 $("#btAssigenHandle").hide();
   		 $("#dispbtn9").hide();
   		   
		}
   		nameFormatCName("createby",list[0].createby);
   		$("#dispatch_id").val(list[1].dispatch_id);
   		$("#rpt_id").val(list[0].rpt_id);
   	
   		$("#rpt_name").val(list[0].rpt_name);
   		$("#in_call").val(list[0].in_call);
   		$("#addres").val(list[0].address);
   		$("#event_source").val(list[0].event_source);
   		$("#event_type").val(list[0].event_type);
   		//$("#createby").val(list[0].createby);
   		$("#createTime").val(getNowFormatDate(false,list[0].createTime));
   		$("#dispatch_time").val(getNowFormatDate(false,list[1].dispatch_time));
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


/*处理人搜索框初始化*/
function initSearchHandler(){
	var addressSuggest = $("input#dispatch_handle_username").bsSuggest({
	    url: "./../search.app?type=engine&keyword=",
	    showHeader: true,
	    allowNoKeyword: true,
	    keyField: 'cname',
	    getDataMethod: "url",
	    delayUntilKeyup: true,
//	    effectiveFields: ["deptName","position","cname","workState","workTimes"],
	    effectiveFields: ["deptName","position","cname"],
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

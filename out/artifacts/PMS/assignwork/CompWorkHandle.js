
$(function(){
	
	initDrops();
	//初始化下拉框
	$('#myForm2').validationEngine();
	 openComp_info(rpt_id)
	  
});

function openComp_info(rpt_id)
{
	clear_disPathchInfo();
	urlmethod = "method=compView&assignId="+rpt_id;
	var url="./../assigenwork/assignlist.app?"+urlmethod;
	loadData(url,1);
}



function openCompButton(type){
	showButton();
	var isOpen = true;
	var urlmethod = "";
	if(isOpen){
		 if(type==1){
			$("#compBtn5").show();
			$("#compBtn6").show()
			$("#compBtn7").show()
			ableEdit();
		}

		 if(type==8)//处理
		{
			
			var rpt_id=selections[0];
			urlmethod = "method=compView&assignId="+rpt_id;
			var url="./../assigenwork/assignlist.app?"+urlmethod;
			$("#btAssigenHandle").show();
			loadData(url,type);
			$("#compBtn5").hide();
			$("#compBtn6").hide()
			$("#compBtn7").hide()
		}else{
			openSaveButton(type);
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

/**
 * 派工按钮，分派人
 * 现场测试确认
 * 消单
 */
function openSaveButton(index){
	var flag = $('#myForm2').validationEngine('validate');
	  if(flag&&index=="5")
	  {
		var addJson = getDataForm(index);
		excuteForm(addJson);
	   }
	  else if(flag&&index=="6")
	  {
		var addJson = getDataForm(index);
		excuteForm(addJson);
	   }
	  if(flag&&index=="7")
	  {
		var addJson = getDataForm(index);
		excuteForm(addJson);
	  }
}

//独立出后台提交部分
function excuteForm(addJson)
{
	$.ajax({
        type: "post",
        url: "./../assigenwork/assignlist.app?method=CompSave",
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

				
				//$('#compDataInfo').bootstrapTable('refresh');
				//$('#myModal3').modal('hide');
			});
//			closeLayer();
    		
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！请联系技术人员。',{
				time:2000
			},function(){
				$('#myModal3').modal('hide');
			})
        }
    });

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
	//派工
	if(index==5){
		addJson = {
			mtn_id:$("#mtn_id").val(),
			comp_operator_id:$("#comp_operator_id").val(),
			comp_emergency:$("#comp_emergency").val(),
			comp_detail:$("#comp_detail").val(),
			comp_reply:$("#comp_reply").val(),
			type:index
		};
	}else if(index==6){
		
		//处理
		addJson = {
			mtn_id:$("#mtn_id").val(),
			comp_result:$("#comp_result").val(),
			comp_process:$("#comp_process").val(),
			comp_solve:$("#comp_solve").val(),
			comp_degree:$("#comp_degree").val(),
			comp_detail:$("#comp_detail").val(),
			comp_emergency:$("#comp_emergency").val(),
			comp_result:$("#comp_result").val(),
			type:index
		};
	}else if(index==7){
		//消单
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
function loadData(url,type){

	var grapIframe="";
	var processInstanceId="";
	showButton();
	$.post(url,function(data){
   		var list = eval(data);
   		var comp_status = list[1].comp_status;
   		var comp_emergency=list[1].comp_emergency;//投诉级别
   		if(type==3){
			hiddenButton();
		}else{
			if(comp_status==0){
	   			$('#compBtn7').attr("disabled","true");
	   			$('#compBtn6').attr("disabled","true");
	   		}else if(comp_status >= 3){
	   			$('#compBtn5').attr("disabled","true");
	   			$('#compBtn6').attr("disabled","true");
	   			$("#compBtn7").attr("disabled","true");
	   			$("#btAssigenHandle").attr("disabled","true");
	   		}else if(type==8&&comp_emergency!="0")
	   		{
	   			$("#btAssigenHandle").hide();
	   		}else if(comp_status==2)
	   		{
	   			$("#compBtn6").attr("disabled","true");	
	   			$('#compBtn5').attr("disabled","true");
	   		}
	   		else{
	   			showButton();
	   			$('#compBtn5').attr("disabled","true");
	   		}
		}
        if(comp_status==0&&comp_emergency==1)
        	{
        	
        	  $('#comp_reply').attr("disabled",false);	
	   		  $('#custmReply').show();
	   		  $('#myresult').hide();
	   		  $('#handleResult').hide();
	   		  
        	}
        if(comp_status==1&&comp_emergency==1)
    	{
    	
          $('#comp_reply').attr("disabled","true");	
    	  $('#comp_emergency').attr("disabled","true");	
    	  $('#comp_status').attr("disabled","true");
    	  $('#comp_result').attr("disabled",false);
   		  $('#custmReply').show();
   		  $('#myresult').show();
   		  $('#handleResult').show();
   		  
    	}
        if((comp_status==2||comp_status==3)&&comp_emergency==1)
    	{
        	
          $('#comp_reply').attr("disabled","true");	
    	  $('#comp_emergency').attr("disabled","true");	
    	  $('#comp_status').attr("disabled","true");
    	  $('#comp_result').attr("disabled","true");
    	  
    	  $('#comp_solve').attr("disabled","true");
    	  $('#comp_degree').attr("disabled","true");
    	  $('#comp_detail').attr("disabled","true");
    	  $('#handleResult').attr("disabled","true");
   		  $('#custmReply').show();
   		  $('#myresult').show();
   		  $('#handleResult').show();
   		 $('#compBtn6').attr("disabled","true");
    	}
        
        if(comp_status==0&&comp_emergency==0)//咨询建议一般情况
    	{
    	
          $('#comp_reply').attr("disabled",false);	
    	  $('#comp_emergency').attr("disabled","true");	
    	$("#comp_detail").attr("disabled",false);
    	  $('#comp_status').attr("disabled","true");
    	  $('#comp_result').attr("disabled",false);
   		  $('#custmReply').show();
   		  $('#myresult').hide();
   		  $('#handleResult').show();
   		  
    	}
   		nameFormatCName("createby",list[0].createby);
   		$("#comp_id").val(list[1].comp_id);
   		$("#rpt_id").val(list[0].rpt_id);
   		$("#rpt_name").val(list[0].rpt_name);
   		$("#in_call").val(list[0].in_call);
   		$("#addres").val(list[0].addres);
   		
   		$("#createTime").val(getNowFormatDate(false,list[0].createTime));
   		$("#comp_createTime").val(getNowFormatDate(false,list[1].comp_createTime));
   		$("#comp_detail").val(list[1].comp_detail);
   		$("#comp_emergency").val(list[1].comp_emergency);
   		$("#comp_status").val(comp_status);
   		$("#comp_operator_id").val(list[1].comp_operator_id);
   		$("#comp_operator_username").val(list[2].comp_operator_name);
   		$("#comp_process").val(list[1].comp_process);
   		$("#comp_result").val(list[1].comp_result);
   		$("#comp_solve").val(list[1].comp_solve);
   		$("#comp_degree").val(list[1].comp_degree);
   		$("#comp_reply").val(list[1].comp_reply);
   		$("#comp_result").val(list[1].comp_result);
   		//alert(list[1].comp_finish_time);
   		$("#comp_finish_time").val(getNowFormatDate(false,list[1].comp_finish_time));
   		$("#mtn_id").val(list[1].mtn_id);
   		processInstanceId = list[0].processInstanceId;
   		if(processInstanceId==null || processInstanceId=='' || processInstanceId==undefined || processInstanceId=='undefined'){
   		
   		}else{
   			grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+processInstanceId+"&numb="+generateMixed(4);
   		}
   		
   		$("#grapIframe").attr("src",grapIframe); 
	});
	
	
	$('#myModal3').modal();
	

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
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#comp_emergency");
				}
	            
				//comp_status  派工单状态
				if(code=='main_mtn_dispatch_status'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#comp_status");
				}
				
				//comp_status  派工单状态
				if(code=='main_comp_solve'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#comp_solve");
				}
				
				//comp_status  派工单状态
				if(code=='main_comp_degree'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#comp_degree");
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

function hiddenButton(){
	$('#compBtn5').attr("disabled","true"); 
	$('#compBtn6').attr("disabled","true");
	$('#compBtn7').attr("disabled","true");
}


function showButton(){
	$('#compBtn5').removeAttr("disabled");
	$('#compBtn6').removeAttr("disabled");
	$('#compBtn7').removeAttr("disabled");
}




function openCompHandleButton()
{
	var flag = $('#myForm1').validationEngine('validate');
	if(flag){
	var addJson = getDataForm2();
	if(addJson=='1')
	{  layer.alert("投诉级别非一般情况不可直接处理");
		return;
	}else{
	
	$.ajax({
        type: "post",
        url: "./../assigenwork/assignlist.app?method=handle",
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

				//$('#assignInfo').bootstrapTable('refresh');
				//$('#myModal1').modal('hide');
			});
//			closeLayer();
    		
        },
        failure:function(xhr,msg)
        {
			layer.msg('操作失败！请联系技术人员。',{
				time:2000
			},function(){
				var parentDom = parent.$('#evnetSendInfo');
				parent.$('#evnetSendInfo').bootstrapTable('refresh');
				 var index = parent.layer.getFrameIndex(window.name); 	
				 parent.layer.close(index); 
			})
        }
    });
  }
	}
}

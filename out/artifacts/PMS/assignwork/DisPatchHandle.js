$(function(){
	
	initDrops();
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



function openDispatchButton(type)
{
	openSaveButton(type);
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

/**
 * 派工按钮，分派人
 * 现场测试确认
 * 消单
 */
function openSaveButton(index){
	
	var flag = $("#dispatch_handle_id").val();
	var dispatch_handle_username= $("#dispatch_handle_username").val();
	if(dispatch_handle_username=="")
		{
		layer.alert("处理人不能为空");
		 return;
		}
	else{
	hiddenButton();
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
				var parentDom = parent.$('#evnetSendInfo');
				parent.$('#evnetSendInfo').bootstrapTable('refresh');
				 var index = parent.layer.getFrameIndex(window.name); 
				 parent.layer.close(index); 

				//window.close(); 
				//window.location.href='/PMS/assignwork/DispatchWorkList.jsp';
			});
//			closeLayer();
    		
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！请联系技术人员。',{
				time:2000
			},function(){
				window.close();
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
	}else if(index==6){
		addJson = {
			mtn_id:$("#mtn_id").val(),
			mtn_type:$("#mtn_type").val(),
			dispatch_tools:$("#dispatch_tools").val(),
			dispatch_expense:$("#dispatch_expense").val(),
			dispatch_result:$("#dispatch_result").val(),
			mtn_priority:$("#mtn_priority").val(),
			
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
   		var dispatch_status = list[1].dispatch_status;
   		if(dispatch_status==0){
   			$('#dispbtn6').attr("disabled","true");
   			$('#dispbtn7').attr("disabled","true");
   			$('#dispbtn8').show();
   			hidecontent();
   		}else if(dispatch_status >= 3){
   			$('#material_cost').attr("disabled","true");
   			$('#labor_cost').attr("disabled","true");
   			$('#dispatch_expense').attr("disabled","true");
   			$('#dispbtn5').attr("disabled","true");
   			$('#dispbtn6').attr("disabled","true");
   			$('#dispbtn7').attr("disabled","true");
   			$('#dispbtn8').hide();
   		}else if(dispatch_status==1)
   			{
   			$('#material_cost').attr("disabled",false);
   			$('#labor_cost').attr("disabled",false);
   			$('#dispatch_expense').attr("disabled",false);
   			
   			$('#dispbtn5').attr("disabled","true");
   			$('#dispbtn7').attr("disabled","true");
   			$('#dispbtn8').show();
   			showContent();
   			}
   		else if(dispatch_status==2)
   		{
   			$('#material_cost').attr("disabled","true");
   			$('#labor_cost').attr("disabled","true");
   			$('#dispatch_expense').attr("disabled","true");
   			$('#dispbtn5').attr("disabled","true");
   			$('#dispbtn6').attr("disabled","true");
   			$('#dispbtn8').show();
   		}
   		else{
   			//showButton();
   			//$('#dispbtn6').attr("disabled","true");
   			$('#dispbtn5').attr("disabled","true");
   			$('#dispbtn8').hide();
   		}
   		
   		nameFormatCName("createby",list[0].createby);
   		$("#dispatch_id").val(list[1].dispatch_id);
   		$("#rpt_id").val(list[0].rpt_id);
   		$("#rpt_name").val(list[0].rpt_name);
   		$("#in_call").val(list[0].in_call);
   		$("#addres").val(list[0].address);
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


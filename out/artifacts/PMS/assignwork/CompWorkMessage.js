$(function(){
	
	initDrops();
	initSearchCompHandler();
	//初始化下拉框
	$('#myForm2').validationEngine();
	 dispatchHandle(rpt_id)
	
	  
});

function dispatchHandle(rpt_id)
{ 
	//clear_disPathchInfo();
	urlmethod = "method=viewAssign&assignId="+rpt_id;
	var url="./../assigenwork/assignlist.app?"+urlmethod;
	 $.post(url,function(data){
			var list = eval(data);
			var event_type_data=list.event_type;
			var processInstanceId = list.processInstanceId;
			changeEventType(event_type_data,processInstanceId);
			//$("#createby").val(nameFormatCName(list.createby));
			//nameFormatCName
			nameFormatCName("createby",list.createby);
			$("#createTime").val(getNowFormatDate(false,list.createTime));
			$("#rpt_kid").val(list.rpt_kid);
			$("#rpt_id").val(list.rpt_id);
			$("#rpt_name").val(list.rpt_name);
			$("#in_call").val(list.in_call);
			$("#addres").val(list.address);
			$("#owner_type").val(list.owner_type);
			$("#event_source").val(list.event_source);
			$("#event_time").val(list.event_time);
			$("#event_type").val(list.event_type);
			
			//报障
			$("#mtn_detail").val(list.mtn_detail);
			//showstatic(list.mtn_detail);
			$("#comp_reply").val(list.comp_reply);
			$("#mtn_emergency").val(list.mtn_emergency);
			$("#dispatch_status").val(list.dispatch_status);
			
			$("#ownerId").val(list.owner_id);
			$("#roomId").val(list.owner_house);
			$("#roomNo").val(list.roomNo);
			//投诉
			$("#comp_status").val(list.comp_status);
			//if(ts_type=='0'){
			$("#comp_emergency").val(list.comp_emergency);
			var state=list.comp_emergency;
			if(state=='1')
				{
				$("#custmReply").hide();
				$("#btAssigenHandle").hide();
				$("#btDisPatchHandle").show();
				$("#handleIds").show();
				}else
				{
					$("#custmReply").show();
					$("#btAssigenHandle").show();
					$("#btDisPatchHandle").hide();
					$("#handleIds").hide();
				}
			//}else
			//{
			//	$("#comp_emergency").val("1");	
			///}
			$("#comp_detail").val(list.comp_detail);
			if(list.comp_reply=='null'){
				$("#comp_reply").val("");	
			}else{
			$("#comp_reply").val(list.comp_reply);
			}
			$("#dispatch_finish_time").val(getNowFormatDate(false,list.finishTime));
			
			//展示图片
			initEventImg(list.rpt_id)
		
		});
//		$('#myModal1').modal();
	 	$('#myModal1').modal({backdrop: 'static', keyboard: false});

	}


/**事件类型下拉框隐藏
 * 
 * @param index
 */
function changeEventType(index,processInstanceId){
	var grapIframe="";
	if(null == processInstanceId || processInstanceId=='' || processInstanceId==undefined || processInstanceId=='undefined'){
		if($("#event_type").val()=="0" || index=="0"){
			grapIframe="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=507501&numb="+generateMixed(4);
		}else{
			grapIframe="./../graph/graphProcessDefinition.app?opt=add&processDefinitionId=217557&numb="+generateMixed(4);
		}
	}else{
		grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+processInstanceId+"&numb="+generateMixed(4);
	}
	
	if($("#event_type").val()=="0" || index=="0"){
		$("#bxdetail").show();
		$("#btSendHandle").show();
		$("#tsdetail").hide();
		$("#mtn_emergency").val("");
	}else{
		$("#dispatch_handle_id").removeClass("form-control validate[required]");
		$("#dispatch_handle_id").addClass("form-control");
		$("#dispatch_handle_username").removeClass("form-control validate[required]");
		$("#dispatch_handle_username").addClass("form-control");
		$("#bxdetail").hide();
		$("#btSendHandle").hide();
		$("#tsdetail").show();
		$("#comp_emergency").val("");
		
		
		
	}
	
	$("#grapIframe").attr("src",grapIframe); 

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



var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
function generateMixed(n) {
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()*35);
        res += chars[id];
    }
    return res;
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

function initEventImg(rpt_id)
{
	var html = [];
	var url="./../assigenwork/assignlist.app?method=getEventImg&rpt_id="+rpt_id;
    $.post(url,function(data){
   		var list = eval(data);
   		for(var i=0;i<list.length;i++)
   	    {
   		
   		html.push("<a class='fancybox' href='"+list[i].img_url+"' title='"+list[i].event_id+"'>" +
   				   "<img alt='image' src='"+list[i].img_url+"'/></a>");
   	    }
   	   var img= html.join("");
     	$("#eventImgShow").empty();//每一次请求，先把上一次的清空
     	$("#eventImgShow").append(img);
   	   
	});
  
}


function initEventImg(rpt_id)
{
	var html = [];
	var url="./../assigenwork/assignlist.app?method=getEventImg&rpt_id="+rpt_id;
    $.post(url,function(data){
   		var list = eval(data);
   		for(var i=0;i<list.length;i++)
   	    {
   			html.push("<a class='fancybox' href='"+list[i].img_url+"' title='"+list[i].event_id+"'><img alt='image' src='"+list[i].img_url+"' onload='AutoResizeImage(0,170,this)'/></a>");
   	    }
   		var img= html.join("");
   		$("#eventImgShow").empty();//每一次请求，先把上一次的清空
     	$("#eventImgShow").append(img);
	});
}

function AutoResizeImage(maxWidth,maxHeight,objImg){
	var img = new Image();
	img.src = objImg.src;
	var hRatio;
	var wRatio;
	var Ratio = 1;
	var w = img.width;
	var h = img.height;
	wRatio = maxWidth / w;
	hRatio = maxHeight / h;
	if (maxWidth ==0 && maxHeight==0){
		Ratio = 1;
	}else if (maxWidth==0){//
		if (hRatio<1) Ratio = hRatio;
	}else if (maxHeight==0){
		if (wRatio<1) Ratio = wRatio;
	}else if (wRatio<1 || hRatio<1){
		Ratio = (wRatio<=hRatio?wRatio:hRatio);
	}
	if (Ratio<1){
		w = w * Ratio;
		h = h * Ratio;
	}
	objImg.height = h;
	objImg.width = w;
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



function initSearchCompHandler(){
	var addressSuggest = $("input#comp_operator_username").bsSuggest({
	    url: "./../search.app?type=allEmp&keyword=",
	    showHeader: true,
	    allowNoKeyword: true,
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
	            data.value.push({
	                "userName": json.result[index][0],
	                "cname": json.result[index][1],
	                "deptName": json.result[index][3],
	                "position": json.result[index][5],
	                
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
		    $("#comp_operator_username").val(data.cname);
		}).on("onUnsetSelectValue",	
		function(e) {
		    console.log("onUnsetSelectValue")
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
	
	
	var flag = $('#myForm1').validationEngine('validate');
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
	var flag = $('#myForm2').validationEngine('validate');
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


//咨询建议直接处理
function openHandleButton()
{
	var addJson = getDataForm();
	//alert(addJson);
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


function getDataForm(){
	var addJson = {
			createTime:$("#createTime").val(),
			rpt_kid:$("#rpt_kid").val(),
			mtn_id:$("#rpt_id").val(),
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

selections = [];
$(function () {
	var i=0;
    $(".summernote").summernote({lang:"zh-CN"});
    initAuditors();
	layer.config({
    	    extend: 'extend/layer.ext.js'
    	});
	openHouseWork_info(rpt_id);
});
 function openHouseWork_info(rpt_id){
	urlmethod = "method=viewForAjaxByNo&noticeNo="+rpt_id;
	var url="./../msgandnotice/list.app?"+urlmethod;
	//var url="${ContextPath}"+"/msgandnotice/list.app?method=viewForAjax&noticeId="+id;
	loadData(url);
 }
function loadData(url){
    $.post(url,
    	   function(data){
		   var list = eval(data);
		        $("#ntcId").val(list.ntcId);
	            $("#ntcCreatorId").val(list.ntcCreatorId);
	            $("#ntcCreatorName").val(list.ntcCreator);
	            $("#ntcCreateTime").val(list.ntcCreateTime);
	            $("#ntcStatus").val(list.ntcStatus);
	            $("#ntcSubject").val(list.ntcSubject); 
	            $("#ntcAudit").val(list.ntcAuditor);
	            $(".summernote").summernote('code',list.ntcContent);
    			var proInsId=list.processInstanceId;
			    var grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+proInsId+"&numb="+generateMixed(4);
			    $("#grapIframe").attr("src",grapIframe);
    			$('#myModal').modal();

		    });
}
function show(obj){	
	changeEventType(1,obj.bpm_processId);
	$("#event_no").val(obj.event_no);
	$("#event_source").val(obj.event_from);
	$("#rpt_name").val(obj.rpt_name);
	$("#in_call").val(obj.call_phone);
	$("#addres").val(obj.user_address);
	$("#event_title").val(obj.event_title);
	$("#pre_time").val(obj.pre_time);
	$("#event_content").val(obj.event_content);
	//$("#verify_oper_id").val(obj.verify_oper_id);
	nameFormatCName('verify_oper_id',obj.verify_oper_id);
	$("#id").val(obj.id);
	$("#accept_time").val(timeFormatCreateTime(obj.accept_time));
	
	//派工
	$("#comp_operator_id").val(obj.oper_id);
	$("#send_id").val(obj.send_id);
	$("#comp_operator_username").val(obj.oper_name);
	$("#dispatch_finish_time").val(timeFormatCreateTime(obj.send_time));
	$("#dispatch_status").val(obj.event_state);
	$("#handle_content").val(obj.handle_content);
	$("#arrv_time").val(timeFormatCreateTime(obj.arrv_time));
	$("#houseKeepingPay").val(obj.houseKeepingPay);
}
function openWindow(type){
		 selections = getIdSelections();
		 var length=selections.length;
		 if( length==0||length>1){
			 layer.alert("请选择一条记录进行操作",{
					skin: 'layui-layer-molv'
				});
		 }else{
			var id=selections[0];
		    var url="${ContextPath}"+"/msgandnotice/list.app?method=viewForAjax&noticeId="+id;
		    $.post(url,
		    	   function(data){
				   var list = eval(data);
				        $("#ntcId").val(list.ntcId);
			            $("#ntcCreatorId").val(list.ntcCreatorId);
			            $("#ntcCreatorName").val(list.ntcCreator);
			            $("#ntcCreateTime").val(list.ntcCreateTime);
			            $("#ntcStatus").val(list.ntcStatus);
			            $("#ntcSubject").val(list.ntcSubject); 
			            $("#ntcAudit").val(list.ntcAuditor);
			            $(".summernote").summernote('code',list.ntcContent);
		    			var proInsId=list.processInstanceId;
					    var grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+proInsId+"&numb="+generateMixed(4);
					    $("#grapIframe").attr("src",grapIframe);
		    			$('#myModal').modal();

				    });
		 }
}
//关闭对话框的时候清空所有数据
function emptyAll(){
	$('.summernote').code("<br /><br /><br /><br /><br /><br />");
	$("#ntcCreatorId").val("");
	$("#ntcId").val("");
    $("#ntcCreatorName").val("");
    $("#ntcCreateTime").val("");
    $("#ntcStatus").val("");
    $("#ntcSubject").val(""); 
    $("#ntcAudit").val("");
}
function save(){
	var content=$(".summernote").summernote('code');
	$("#ntcStatus").removeAttr("disabled"); 
	var content=$("#auditContentText").val();
	$("#auditContent").val(content);
	var jsonuserinfo = $('#myForm').serializeObject();
	var url="./../msgandnotice/noticeaudit.app?method=update";
	$.post(url,
	        jsonuserinfo,
		    function(data){
				layer.msg('操作成功',
				          {time:2000},
						  function(){
				        	  var parentDom = parent.$('#evnetSendInfo');
				        	  parent.$('#evnetSendInfo').bootstrapTable('refresh');
				        	  var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				        	  parent.layer.close(index); //再执行关闭  
						  })
				  });
}
    function getIdSelections() {
        return $.map($('#chargeInfo').bootstrapTable('getSelections'), function (row) {
            return row.ntcId
        });
    }

    function responseHandler(res) {
        $.each(res.rows, function (i, row) {
            row.state = $.inArray(row.id, selections) !== -1;
        });
        return res;
    }

    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
            html.push('<p><b>' + key + ':</b> ' + value + '</p>');
        });
        return html.join('');
    }
   $.fn.serializeObject = function(){    
   var o = {};    
   var a = this.serializeArray();    
   $.each(a, function() {    
       if (o[this.name]) {    
           if (!o[this.name].push) {    
               o[this.name] = [o[this.name]];    
           }    
           o[this.name].push(this.value || '');    
       } else {    
           o[this.name] = this.value || '';    
       }    
   });    
   return o;    
};  
var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
function generateMixed(n) {
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()*35);
        res += chars[id];
    }
    return res;
}
    function operateFormatter(value, row, index) {
        return [
            '<a class="like" href="javascript:void(0)" title="Like">',
            '<i class="glyphicon glyphicon-heart"></i>',
            '</a>  ',
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
    }
    //状态处理函数
    function operateStatus(value, row, index) {
    	if(value==20){
    		return ['待审'].join('');
    	}else if(value==21){
    		return ['待发布'].join('');
    	}else if(value==30){
    		return ['已发布'].join('');
    	}else if(value==11){
    		return ['驳回'].join('');
    	}else if(value==22){
    		return ['审核中'].join('');
    	}else{
    		return ['起草'].join('');
    	}
    	
       
    }
    window.operateEvents = {
        'click .like': function (e, value, row, index) {
            alert('You click like action, row: ' + JSON.stringify(row));
        },
        'click .remove': function (e, value, row, index) {
            $table.bootstrapTable('remove', {
                field: 'id',
                values: [row.id]
            });
        }
    };

    function totalTextFormatter(data) {
        return 'Total';
    }

    function totalNameFormatter(data) {
        return data.length;
    }

    function totalPriceFormatter(data) {
        var total = 0;
        $.each(data, function (i, row) {
            total += +(row.price.substring(1));
        });
        return '$' + total;
    }

    function getHeight() {
        return $(window).height() - $('h1').outerHeight(true);
    }
	    function getScript(url, callback) {
        var head = document.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        script.src = url;

        var done = false;
        // Attach handlers for all browsers
        script.onload = script.onreadystatechange = function() {
            if (!done && (!this.readyState ||
                    this.readyState == 'loaded' || this.readyState == 'complete')) {
                done = true;
                if (callback)
                    callback();

                // Handle memory leak in IE
                script.onload = script.onreadystatechange = null;
            }
        };

        head.appendChild(script);
        return undefined;
    }
  //ajax获取审核人
    function initAuditors(){
    	var url="./../msgandnotice/list.app?method=getAuditor";
        $.post(url,
		      function(data){
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
		               $("<option value='"+list[j].id+"'>"+list[j].name+"</option>").appendTo("#ntcAudit");  
		        	};
				  });
    	//return rtnValue;
    }
    //将当前时间转换成格式为YYYY-mm-dd HH:mm:ss格式的字符串 flag为true时，说明是新增 ，date为当前时间 否则为传递date参数转换成字符串
    function getNowFormatDate(flag,vardate) {
    	var date;
    	if(flag==true){
        	date = new Date();
    	}else{
    		date=vardate;
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
    function func1(){
    	alert($("#selectAge").val());
    }
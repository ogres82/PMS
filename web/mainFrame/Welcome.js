$(function(){
	//initEcharts();
	initService_statics();
	initCount();
	initEmpNum();
	initInRate();
	initServiceNum();
	initMsg();
	initOweList();
	initTaskList();
});

function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}
//服务统计报表
function initService_statics()
{
	var url="./../report/serviceReport.app?method=getServiceInfo";
    $.post(url,function(data){
    	var obj = document.getElementById('service_id');
    	initEcharts(obj,data,"客户服务统计");
    	initCurrentMonthReport(data);
	});




}


function initEcharts(element,data,title){
	var obj = eval('('+data+')');
	var labels = new Array();
	var line1 = new Array();
	var line2 = new Array();
	var line3 = new Array();
	var line4 = new Array();
	var line5 =new Array();
	var line6 =new Array();
	var gz = obj.line1;
	var zx = obj.line2;
	var jz=obj.line3;
	var myd=obj.line4;
	var zxMyd=obj.line5;
	var jzfw=obj.line6;
	for(var i=0;i<gz.length;i++)
	{
		labels.push(gz[i][3]);
		line1.push(gz[i][0]);
		
	}
	for(var i=0;i<zx.length;i++)
	{
		line2.push(zx[i][0]);
	}
	for(var i=0;i<jz.length;i++)
	{
		line3.push(jz[i][0]);
	}
	
	for(var i=0;i<jz.length;i++)
	{
		line4.push(myd[i][0]);
	}
	
	for(var i=0;i<jz.length;i++)
	{
		line5.push(zxMyd[i][0]);
	}
	for(var i=0;i<jz.length;i++)
	{
		line6.push(zxMyd[i][0]);
	}
	var myChart = echarts.init(document.getElementById('sample_echarts'));
	var option = {
		    tooltip: {
		        trigger: 'axis'
		    },
		    toolbox: {
		        feature: {
//		            dataView: {show: true, readOnly: false},
//		            magicType: {show: true, type: ['line', 'bar']},
//		            restore: {show: true},
//		            saveAsImage: {show: true}
		        }
		    },
		    legend: {
		        data:['报障报修','家政服务','咨询建议','满意度']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: labels
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '单',
		            min: 0,
//		            max: 100,
//		            interval: 100,
		            axisLabel: {
		                formatter: '{value} '
		            }
		        },
		        {
		            type: 'value',
		            name: '满意度',
		            min: 0,
		            max: 100,
//		            interval: 25,
		            axisLabel: {
		                formatter: '{value} %'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'报障报修',
		            type:'bar',
		            data:line1
		        },
		        {
		            name:'家政服务',
		            type:'bar',
		            data:line2
		        },
		        {
		            name:'咨询建议',
		            type:'bar',
		            data:line3
		        },
		        {
		            name:'满意度',
		            type:'line',
		            yAxisIndex: 1,
		            data:line4
		        }
		        ,
		        {
		            name:'满意度',
		            type:'line',
		            yAxisIndex: 1,
		            data:line5
		        }
		        ,
		        {
		            name:'满意度',
		            type:'line',
		            yAxisIndex: 1,
		            data:line6
		        }
		    ]
		};
	myChart.setOption(option);
}

function initCurrentMonthReport(data)
{
	var date = new Date().format('yyyyMM');
	var obj = eval('('+data+')');
	var labels = new Array();
	var line1 = "";
	var line2 = "";
	var line3 = "";
	var line4 =  "";//报章报修满意度
	var line5 ="";
	var line6 ="";
	var gz = obj.line1;
	var zx = obj.line2;
	var jz=obj.line3;
	var myd=obj.line4;
	var zxMyd=obj.line5;
	var jzfw=obj.line6;
	
	for(var i=0;i<gz.length;i++)
	{
	  if(gz[i][3]==date)
	  {
		line1=gz[i][0];
	   }
	}
	for(var i=0;i<zx.length;i++)
	{
		if(zx[i][3]==date)
		{
		line2=zx[i][0];
		 }
	}
	for(var i=0;i<jz.length;i++)
	{
		if(jz[i][3]==date)
		{
		line3=jz[i][0];
		}
	}
	
	//满意度
	for(var i=0;i<jz.length;i++)
	{
		 if(jz[i][3]==date){
		  line4=myd[i][0];
		 }
	}
	
	for(var i=0;i<jz.length;i++)
	{
		 if(jz[i][3]==date){
		line5=zxMyd[i][0];
		 }
	}
	for(var i=0;i<jz.length;i++)
	{ if(jz[i][3]==date){
		line6=zxMyd[i][0];
	  }
	}
	
	if(!line1 || typeof(line1)=="undefined"){
		line1 = 0;
	}
	if(!line2 || typeof(line2)=="undefined"){
		line2 = 0;
	}
	if(!line3 || typeof(line3)=="undefined"){
		line3 = 0;
	}
	if(!line4 || typeof(line4)=="undefined"){
		line4 = 0;
	}
	if(!line5 || typeof(line5)=="undefined"){
		line5 = 0;
	}
	if(!line6 || typeof(line6)=="undefined"){
		line6 = 0;
	}
	
	  var h='<ul class="stat-list">';
   	  h += ' <li> <h2 class="no-margins">'+line1+'</h2><small>本月报障报修满意度</small><div class="stat-percent">'+line4+'% <i class="fa fa-level-up text-navy"></i> </div> <div class="progress progress-mini"> <div style="width: '+line4+'%;" class="progress-bar"></div> </div> </li>';
   	  h += ' <li> <h2 class="no-margins">'+line2+'</h2><small>本月家政服务满意度</small><div class="stat-percent">'+line5+'% <i class="fa fa-level-down text-navy"></i> </div> <div class="progress progress-mini"> <div style="width: '+line5+'%;" class="progress-bar"></div> </div> </li>';
   	  h += ' <li> <h2 class="no-margins">'+line3+'</h2><small>本月咨询建议满意度</small><div class="stat-percent">'+line6+'% <i class="fa fa-bolt text-navy"></i> </div> <div class="progress progress-mini"> <div style="width: '+line6+'%;" class="progress-bar"></div> </div> </li>';
   	  $("#current_monthInfo").html(h);	
	         


}

function initCount()
{
	var url="./../top/topTask.app?method=getTopTask";
	 $.post(url,
			  function(data){
			 	if(!data || typeof(data)=="undefined"){
		    		data = 0;
		    	}
		    	var h = '<a class="timer count-title" id="count-number" data-to="'+data+'" data-speed="1500" onclick="show()" url="topTask.jsp" style="color:inherit">'+data+'</a>';
		    	$("#topTask").html(h);
			  });
}

function initEmpNum(){
	var url="./../empInfo/empList.app?method=getEmpAllNum";
    $.post(url,
	  function(data){
    	if(!data || typeof(data)=="undefined"){
    		data = 0;
    	}
    	var h = '<a class="timer count-title" id="count-number" data-to="'+data+'" data-speed="1500" onclick="viewReport(this)" url="EmpDistribution.jsp" style="color:inherit">'+data+'</a>';
    	$("#empNum").html(h);
	  });
}

function initServiceNum(){
	var url="./../report/serviceReport.app?method=history";
    $.post(url,
	  function(data){
    	var loadData = eval(data);
    	var sum = 0;
    	var curMonthSum = 0;
    	var date = new Date().format('yyyyMM');
    	for(var i=0;i<loadData.length;i++){
    		sum = sum + loadData[i].count;
    		if(loadData[i].months == date){
    			curMonthSum = curMonthSum + loadData[i].count;
    		}
    	}
    	if(!curMonthSum || typeof(curMonthSum)=="undefined"){
    		curMonthSum = 0;
    	}
    	var h = '<a class="timer count-title" id="count-number" data-to="'+curMonthSum+'" data-speed="1500" onclick="viewReport(this)" url="serviceReport.jsp" style="color:inherit">'+curMonthSum+'</a>';
    	$("#serviceNumMonth").html(h);
    	$("#serviceNumTotal").html(sum+" <i class='fa fa-user'></i>")
    	$('.timer').each(count);
	  });
}

function initInRate(){
	var url="./../ownerChart/ownerChartList.app?method=roomAndInRate";
    $.post(url,
	  function(data){
    	var v = data[0]/data[1]*100;
    	var vStr = v.toFixed(2)+"";
    	var result = vStr.substring(vStr.indexOf("."),vStr.indexOf(".")+3);  
    	var h = '<a class="timer count-title" id="count-number" data-to="'+v.toFixed(2)+'" data-speed="1500" onclick="viewReport(this)" url="OwnerChart.jsp" style="color:inherit">'+v.toFixed(2)+'</a>';
    	$("#inRate").html(h);
    	$("#roomNum").html(data[1]+" <i class='fa fa-home'></i>");
    	$('.timer').each(count);
    	setInterval("$('#inRate #count-number').html('"+vStr+"%')",2000);
	  });
}

function initMsg(){
	var url='./../mainFrame/mainFrameInfo.app?method=unreadMsg';
	$.post(url,
	    function(data){
		if(data==""){
			data = "0";
		}
		$("#unreadMsg").text("您有"+data+"条未读消息");
	});
	var url="./../mainFrame/mainFrameInfo.app?method=myReceiveMsg";
    $.post(url,
	  function(data){
    	var result = new Array();
    	if(data!="" && data!=null){
    		result = eval(data);
    	}
    	var html = "";
    	var dataLength=result.length;
    	if(dataLength>4){
    		dataLength = 4;
    	}
    	for(var i=0;i<dataLength;i++){
    			html +='<div class="feed-element">'+
	                '<div>'+
	                    '<small class="pull-right text-navy">'+getDateDiff(result[i].sendDate)+'</small>'+
	                    '<strong style="line-height:30px;">'+result[i].username+'</strong>'+
	                    '<div>'+result[i].title+'</div>'+
	                    '<small class="text-muted">'+json2TimeStamp(result[i].sendDate)+'</small>'+
	                '</div>'+
	            '</div>';
    	}
    	html +='<div class="feed-element" style="text-align:right"><a onclick="callParentviewMsg()">更多>></a></div>'
    	$("#msgs").html(html);
	  });
}

function initOweList(){
	var url="./../mainFrame/mainFrameInfo.app?method=oweList";
    $.post(url,
	  function(data){
    	var r = new Array();
    	if(data!="" && data!=null){
    		r = eval(data);
    	}
    	var html = "";
    	var dataLength=r.length;
    	if(dataLength>10){
    		dataLength = 10;
    	}
    	for(var i=0;i<dataLength;i++){
    		html +='<tr>'+
                '<td><span class="label label-warning">欠费</span>'+
                '</td>'+
                '<td><i class="fa fa-clock-o"></i> '+json2Date(r[i].end_time)+'</td>'+
                '<td>'+r[i].owner_name+'</td>'+
                '<td class="text-navy"> <i class="fa fa-cny"></i> '+(r[i].total_pay).toFixed(2)+'</td>'+
            '</tr>';
    	}
    	html +='<tr><td colspan="4"><div style="float:right"><a onclick="viewReport(this)" url="ChargeArrearInfo.jsp">更多>></a></div></td></tr>'
    	$("#oweList").html(html);
    });
}

function initTaskList(){
	
	var url="./../mainFrame/mainFrameInfo.app?method=taskList";
    $.post(url,
	  function(data){
    	var r = new Array();
    	if(data!="" && data!=null){
    		r = eval(data);
    	}
    	var html = "";
    	var dataLength=r.length;
    	if(dataLength>10){
    		dataLength = 10;
    	}
    	for(var i=0;i<dataLength;i++){
    		var value = r[i][6];
    		if(value==0)
    		{
    			value = "未派工";
    		}else if(value==1)
    		{
    			value = "已接收";
    		}
    		else if(value==2)
    		{
    			value = "处理中";
    		}
    		else if(value==3)
    		{
    			value = "待回访";
    		}
    		else if(value==4)
    		{
    			value = "已回访";
    		}else if(value==5)
    		{
    			value = "已归档";
    		}else if(value==20)
    		{
    			value = "待审核";
    		}
    		else if(value==22)
    		{
    			value = "审核中";
    		}
    		html +='<li style="line-height:30px">'+
                '<div class="row">'+
            '<span class="col-sm-3">'+r[i][7]+'</span>'+
            '<span class="col-sm-2 label label-primary" style="line-height:20px;margin-top:2px">'+value+'</span>'+
            '<span class="col-sm-6 text-right"><i class="fa fa-clock-o"></i> '+json2TimeStamp(r[i][1])+'</span>'+
          '</div>'+
        '</li>';
    	}
    	if(html!=""){
    		$("#taskList").html(html);
    	}else{
    		$("#taskList").html('<li style="line-height:30px;text-align:center">无任务</li>');
    	}
    });
}

function show()
{
	window.location.href="/PMS/toptask/topTask.jsp";
}

function viewReport(obj){
	parent.openTab(obj);
}

function callParentviewMsg(){
	parent.viewMsg();
}

function magic_number(value) {
	var num = $("#count-number");
    num.animate({count: value}, {
        duration: 500,
        step: function() {
            num.text(String(parseInt(this.count)));
		}
	});
};

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
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

//获取时间差
function getDateDiff(dateTimeStamp){
	var minute = 1000 * 60;
	var hour = minute * 60;
	var day = hour * 24;
	var halfamonth = day * 15;
	var month = day * 30;
	var now = new Date().getTime();
	var diffValue = now - dateTimeStamp;
	if(diffValue < 0){
		//若日期不符则弹出窗口告之
		//alert("结束日期不能小于开始日期！");
	}
	var monthC =diffValue/month;
	var weekC =diffValue/(7*day);
	var dayC =diffValue/day;
	var hourC =diffValue/hour;
	var minC =diffValue/minute;
	if(monthC>=1){
		result= parseInt(monthC) + "个月前";
	}
	else if(weekC>=1){
		result= parseInt(weekC) + "周前";
	}
	else if(dayC>=1){
		result= parseInt(dayC) +"天前";
	}
	else if(hourC>=1){
		result= parseInt(hourC) +"个小时前";
	}
	else if(minC>=1){
		result= parseInt(minC) +"分钟前";
	}else
		result="刚刚";
	return result;
}
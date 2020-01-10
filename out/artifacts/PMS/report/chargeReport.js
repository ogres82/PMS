$(function(){
	initDataYear();
//	init();
//	initDatetime();
	initDataMonth();
	initDataArea();
	initDataType();
	
});

function initDatetime(){
	$('#monthPicker2').datetimepicker({
	    format: 'yyyy-mm',
	    language: 'zh-CN',
	    startView: 'year',
	    minView: 'year',
	    maxView: 'year',
	    autoclose: true
	}).on('changeMonth',function(ev){
		var date = json2Date(ev.date.valueOf(),"month");
		var url = "../../report/chargeReport.app?method=type&date2="+date;
		$.post(url,
			    function(data){
					loadData = eval(data);
					initEcharts2(loadData);
					
				}
		);
	});
	
	
	$('#yearPicker2').datetimepicker({
	    format: 'yyyy',
	    language: 'zh-CN',
	    startView: 'decade',
	    minView: 'decade',
	    maxView: 'decade',
	    autoclose: true
	}).on('changeYear',function(ev){
		var date = json2Date(ev.date.valueOf(),"year");
		var url = "../../report/chargeReport.app?method=type&date2="+date;
		$.post(url,
			    function(data){
					loadData = eval(data);
					initEcharts2(loadData);
					
				}
		);
	});
	
	$('#monthPicker1').datetimepicker({
	    format: 'yyyy-mm',
	    language: 'zh-CN',
	    startView: 'year',
	    minView: 'year',
	    maxView: 'year',
	    autoclose: true
	}).on('changeMonth',function(ev){
		var date = json2Date(ev.date.valueOf(),"month");
		var url = "../../report/chargeReport.app?method=area&date1="+date;
		$.post(url,
			    function(data){
					loadData = eval(data);
					initEcharts1(loadData);
					
				}
		);
	});
	
	
	$('#yearPicker1').datetimepicker({
	    format: 'yyyy',
	    language: 'zh-CN',
	    startView: 'decade',
	    minView: 'decade',
	    maxView: 'decade',
	    autoclose: true
	}).on('changeYear',function(ev){
		var date = json2Date(ev.date.valueOf(),"year");
		var url = "../../report/chargeReport.app?method=area&date1="+date;
		$.post(url,
			    function(data){
					loadData = eval(data);
					initEcharts1(loadData);
					
				}
		);
	});
}

function json2Date(milliseconds,type){
	if(milliseconds==null || milliseconds==""){
		return "";
	}
    var datetime = new Date();
    datetime.setTime(milliseconds);
    var year=datetime.getFullYear();
         //月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
//    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    if(type == "month"){
    	return year + month;
    }else if(type == "year"){
    	return year;
    }else{
    	return "";
    }
    
}

function initDataYear(){
	var url = "../../report/chargeReport.app?method=year";
	$.post(url,
		    function(data){
				loadData = eval(data);
				initEchartsYear(loadData);
				
			}
	);
}
function initEchartsYear(loadData){
	if(typeof(loadData)=="undefined"){
		$('#charts_year').html("<p style='text-align:center;line-height:400px;font-size:20px'>暂无数据</p>");
		return;
	}
	var myChart = echarts.init(document.getElementById('charts_year'));
	var years = new Array();
	var rate = new Array();
	var paidAmount = new Array();
	var arrearageAmount = new Array();
	for(var i=0;i<loadData.length;i++){
		var p1 = {year:'',value:''};
		var p2 = {year:'',value:''};
		p1.value = loadData[i].paidAmount;
		p1.year = loadData[i].years;
		p2.value = loadData[i].arrearageAmount;
		p2.year = loadData[i].years;
		years.push(loadData[i].years);
		paidAmount.push(p1);
		arrearageAmount.push(p2);
		var a = loadData[i].paidAmount/loadData[i].receiveAmount*100;
		rate.push(a.toFixed(2));
	}
	var option = {
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['欠费金额','实收金额','收费率']
		    },
		    grid: {
	            top: '12%',
	            left: '1%',
	            right: '10%',
	            containLabel: true
	        },
	        dataZoom : {
	            show : true,
	            realtime : true,
	            start : 40,
	            end : 60
	        },
		    xAxis: [
		        {
		            type: 'category',
		            data: years
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '元',
//		            min: 0,
//		            max: 1000000,
//		            interval: 10000,
		            axisLabel: {
		                formatter: '{value} '
		            }
		        },
		        {
		            type: 'value',
		            name: '收费率',
		            min: 0,
		            max: 100,
//		            interval: 10,
		            axisLabel: {
		                formatter: '{value} %'
		            }
		        }
		    ],
		    series: [
				{
				    name:'实收金额',
				    type:'bar',
				    stack:'金额',
				    barWidth:50,
				    data:paidAmount
				},     
		        {
		            name:'欠费金额',
		            type:'bar',
		            stack:'金额',
		            barWidth:50,
		            data:arrearageAmount
		        },
		        {
		            name:'收费率',
		            type:'line',
		            yAxisIndex: 1,
		            data:rate
		        }
		    ],
		};
	myChart.setOption(option);
	myChart.on('click', function (params) {
		initDataMonth(params.data.year);
	});
	
}

function initDataMonth(year){
	if(typeof(year)=="undefined"){
		year = json2Date(new Date(),"year");
	}
	var url = "../../report/chargeReport.app?method=month&year="+year;
	$.post(url,
		    function(data){
				loadData = eval(data);
				initEchartsMonth(loadData);
				
			}
	);
}
function initEchartsMonth(loadData){
	var myChart = echarts.init(document.getElementById('charts_month'));
	var months = new Array();
	var rate = new Array();
	var paidAmount = new Array();
	var arrearageAmount = new Array();
	for(var i=0;i<loadData.length;i++){
		var p1 = {year:'',value:''};
		var p2 = {year:'',value:''};
		p1.value = loadData[i].paidAmount;
		p1.month = loadData[i].months;
		p2.value = loadData[i].arrearageAmount;
		p2.month = loadData[i].months;
		months.push(loadData[i].months);
		paidAmount.push(p1);
		arrearageAmount.push(p2);
		var a = loadData[i].paidAmount/loadData[i].receiveAmount*100;
		rate.push(a.toFixed(2));
	}
	var option = {
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['欠费金额','实收金额','收费率']
		    },
		    grid: {
	            top: '12%',
	            left: '1%',
	            right: '10%',
	            containLabel: true
	        },
		    xAxis: [
		        {
		            type: 'category',
		            data: months
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '元',
//		            min: 0,
//		            max: 1000000,
//		            interval: 10000,
		            axisLabel: {
		                formatter: '{value} '
		            }
		        },
		        {
		            type: 'value',
		            name: '收费率',
		            min: 0,
		            max: 100,
//		            interval: 10,
		            axisLabel: {
		                formatter: '{value} %'
		            }
		        }
		    ],
		    series: [
				{
				    name:'实收金额',
				    type:'bar',
				    stack:'金额',
//				    barWidth:20,
				    data:paidAmount
				},     
		        {
		            name:'欠费金额',
		            type:'bar',
		            stack:'金额',
//		            barWidth:40,
		            data:arrearageAmount
		        },
		        {
		            name:'收费率',
		            type:'line',
		            yAxisIndex: 1,
		            data:rate
		        }
		    ],
		};
	myChart.setOption(option);
	myChart.on('click', function (params) {
		initDataArea(params.data.month);
		initDataType(params.data.month);
	});
}

function initDataArea(month){
	if(typeof(month)=="undefined"){
		month = json2Date(new Date(),"month");
	}
	var url = "../../report/chargeReport.app?method=area&month="+month;
	$.post(url,
		    function(data){
				loadData = eval(data);
				initEchartsArea(loadData);
				
			}
	);
}

function initEchartsArea(loadData){
	if(typeof(loadData)=="undefined"){
		$('#charts_area').html("<p style='text-align:center;line-height:400px;font-size:20px'>暂无数据</p>");
		return;
	}
	var myChart = echarts.init(document.getElementById('charts_area'));
	var communityName = new Array();
	var rate = new Array();
	var paidAmount = new Array();
	var arrearageAmount = new Array();
	for(var i=0;i<loadData.length;i++){
		var community1 = {id:'',value:''};
		community1.id = loadData[i].communityId;
		community1.value = loadData[i].paidAmount;
		paidAmount.push(community1);
		var community2 = {id:'',value:''};
		community2.id = loadData[i].communityId;
		community2.value = loadData[i].arrearageAmount;
		arrearageAmount.push(community2);
		var a = loadData[i].paidAmount/loadData[i].receiveAmount*100;
		rate.push(a.toFixed(2));
		communityName.push(loadData[i].communityName);
	}
	/*柱状图*/
	var option = {
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['欠费金额','实收金额','收费率']
		    },
		    grid: {
	            top: '12%',
	            left: '1%',
	            right: '10%',
	            containLabel: true
	        },
		    xAxis: [
		        {
		            type: 'category',
		            data: communityName
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '元',
//		            min: 0,
//		            max: 1000000,
//		            interval: 10000,
		            axisLabel: {
		                formatter: '{value} '
		            }
		        },
		        {
		            type: 'value',
		            name: '收费率',
		            min: 0,
		            max: 100,
//		            interval: 10,
		            axisLabel: {
		                formatter: '{value} %'
		            }
		        }
		    ],
		    series: [
				{
				    name:'实收金额',
				    type:'bar',
				    stack:'金额',
				    barWidth:30,
				    data:paidAmount
				},     
		        {
		            name:'欠费金额',
		            type:'bar',
		            stack:'金额',
		            barWidth:30,
		            data:arrearageAmount
		        },
		        {
		            name:'收费率',
		            type:'line',
		            yAxisIndex: 1,
		            data:rate
		        }
		    ],
		};
	myChart.setOption(option);
}

function initDataType(month){
	if(typeof(month)=="undefined"){
		month = json2Date(new Date(),"month");
	}
	var url = "../../report/chargeReport.app?method=type&month="+month;
	$.post(url,
		    function(data){
				loadData = eval(data);
				initEchartsType(loadData);
				
			}
	);
}

function initEchartsType(loadData){
	if(typeof(loadData)=="undefined" || loadData==""){
		$('#charts_area2').html("<p style='text-align:center;line-height:400px;font-size:20px'>暂无数据</p>");
		return;
	}
	var myChart = echarts.init(document.getElementById('charts_type'));
	var chargeTypeName = new Array();
	var rate = new Array();
	var paidAmount = new Array();
	var arrearageAmount = new Array();
	for(var i=0;i<loadData.length;i++){
		chargeTypeName.push(loadData[i].chargeTypeName);
		paidAmount.push(loadData[i].paidAmount);
		arrearageAmount.push(loadData[i].arrearageAmount);
		if(loadData[i].receiveAmount == 0){
			rate.push(0);
		}else{
			var a = loadData[i].paidAmount/loadData[i].receiveAmount*100;
			rate.push(a.toFixed(2));
		}
	}
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
		        data:['欠费金额','实收金额','收费率']
		    },
		    grid: {
	            top: '12%',
	            left: '1%',
	            right: '10%',
	            containLabel: true
	        },
		    xAxis: [
		        {
		            type: 'category',
		            axisLabel:{
                      interval:0,
                      rotate:45,
                      margin:2,
                      textStyle:{
                          color:"#000000"
                      }
		            },
		            data: chargeTypeName
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '元',
//		            min: 0,
//		            max: 1000000,
//		            interval: 10000,
		            axisLabel: {
		                formatter: '{value} '
		            }
		        },
		        {
		            type: 'value',
		            name: '收费率',
		            min: 0,
		            max: 100,
//		            interval: 10,
		            axisLabel: {
		                formatter: '{value} %'
		            }
		        }
		    ],
		    series: [
				{
				    name:'实收金额',
				    type:'bar',
				    stack:'金额',
				    barWidth:30,
				    data:paidAmount
				},     
		        {
		            name:'欠费金额',
		            type:'bar',
		            stack:'金额',
		            barWidth:30,
		            data:arrearageAmount
		        },
		        {
		            name:'收费率',
		            type:'line',
		            yAxisIndex: 1,
		            data:rate
		        }
		    ],
//		    color: ['#7CCD7C','#228B22'],
		};
	myChart.setOption(option);
}


function init(){
	
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#chargeInfo').bootstrapTable({
			url: '../../report/chargeReport.app?method=area',         //请求后台的URL（*）
//			data: loadData,
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: false,
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
            	field: 'communityName',
            	title: '小区'
            }, {
                field: 'receiveAmount',
                title: '应收'
            }, {
                field: 'paidAmount',
                title: '实收',
//                formatter: stateFormat
            }, {
            	field: 'arrearageAmount',
                title: '未收',
//                formatter:dateFormate
            }, {
            	field: 'rate',
                title: '收费率（%）',
                formatter: rateFormate
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
	rateFormate = function(value,row,index){
		var paidAmount = row.paidAmount;
		var receiveAmount = row.receiveAmount;
		var rate = paidAmount/receiveAmount*100;
		return rate.toFixed(2);
	}
	
	return oTableInit;
	
};

function allIn(type){
	if(type == 1){
		$('#year1').hide();
		$('#month1').hide();
		initData1();
	}
	if(type == 2){
		$('#year2').hide();
		$('#month2').hide();
		initData2();
	}
}

function byMonth(type){
	if(type == 1){
		$('#year1').hide();
		$('#month1').show();
		var date = new Date;
		var year=date.getFullYear(); 
		var month=date.getMonth()+1;
		month =(month<10 ? "0"+month:month); 
		var defaultDate = (year.toString()+month.toString());
		$('#monthPicker1').val(getCurMonth());
		var url = "../../report/chargeReport.app?method=area&date1="+defaultDate;
		$.post(url,
			    function(data){
					loadData = eval(data);
					initEcharts1(loadData);
				}
		);
	}
	if(type == 2){
		$('#month2').show();
		$('#year2').hide();
		var date = new Date;
		var year=date.getFullYear(); 
		var month=date.getMonth()+1;
		month =(month<10 ? "0"+month:month); 
		var defaultDate = (year.toString()+month.toString());
		$('#monthPicker2').val(getCurMonth());
		var url = "../../report/chargeReport.app?method=type&date2="+defaultDate;
		$.post(url,
			    function(data){
					loadData = eval(data);
					initEcharts2(loadData);
				}
		);
		
	}
}

function byYear(type){
	if(type == 1){
		$('#year1').show();
		$('#month1').hide();
		var date = new Date;
		var year=date.getFullYear(); 
		var defaultDate = year.toString();
		$('#yearPicker1').val(getCurYear());
		var url = "../../report/chargeReport.app?method=area&date1="+defaultDate;
		$.post(url,
			    function(data){
					loadData = eval(data);
					initEcharts1(loadData);
				}
		);
	}
	if(type == 2){
		$('#year2').show();
		$('#month2').hide();
		var date = new Date;
		var year=date.getFullYear(); 
		var defaultDate = year.toString();
		$('#yearPicker2').val(getCurYear());
		var url = "../../report/chargeReport.app?method=type&date2="+defaultDate;
		$.post(url,
			    function(data){
					loadData = eval(data);
					initEcharts2(loadData);
				}
		);
	}
}


function openButtonWindow(type){
	$('#tableArea').removeAttr("class").attr("class", "");
	$('#chartsArea').removeAttr("class").attr("class", "");
	if(type==1){
		tout();
		setTimeout(thide,300);
		setTimeout(cshow,300);
		$('#btn1').hide();
		$('#btn2').show();
	}
	if(type==2){
		cout();
		setTimeout(chide,300);
		setTimeout(tshow,300);
		$('#btn1').show();
		$('#btn2').hide();
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

function getCurMonth(){
	return new Date().format('yyyy-MM');
}

function getCurYear(){
	return new Date().format('yyyy');
}

function back(){
	$("#chartsBuild1").hide();
//	$("#chartsArea1").show();
	
	setTimeout(testShow,100);
	initData1();
}
function testShow(){
	$("#chartsArea1").show();
}
$(function(){
	initData1();
	initData5();
	initData6();
	initData7();
	initData8();
	initEcharts2();
	initEcharts3();
	initEcharts4();
});



function initData1(){
	var url = "../../report/serviceReport.app?method=getServiceInfo";
	$.post(url,
		    function(data)
		    {
			initEcharts1(data);
 	}
	);
}
//区域事件分析
function initData5(){
	var url = "../../report/serviceReport.app?method=eventAnalysis";
	$.post(url,
		    function(data)
		    {
		    initEcharts5(data);
 	       }
	);
}

//业主满意度
function initData6(){
	var url = "../../report/serviceReport.app?method=eventSatisfaAnalysis";
	$.post(url,
		    function(data)
		    {
		    initEcharts4(data);
 	       }
	);
}

//事件响应时长分析
function initData7(){
	var url = "../../report/serviceReport.app?method=eventResponse";
	$.post(url,
		    function(data)
		    {
		    initEcharts3(data);
 	       }
	);
}



//事件发生时间分析
function initData8(){
	var url = "../../report/serviceReport.app?method=eventTimeAnalysis";
	$.post(url,
		    function(data)
		    {
		    initEcharts2(data);
	       }
	);
}

function initEcharts1(data){
	
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
	
	
	var myChart = echarts.init(document.getElementById('charts_area1'));
	var option = {
		    tooltip: {
		        trigger: 'axis'
		    },
		    toolbox: {
		        feature: {
		        }
		    },
		    legend: {
		        data:['报障报修','咨询建议','家政服务','报障报修满意率','咨询建议满意率','家政服务满意率']
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
		            data: labels
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '单',
		            axisLabel: {
		                formatter: '{value} '
		            }
		        },
		        {
		            type: 'value',
		            name: '满意度',
		            min: 0,
		            max: 100,
		            interval: 25,
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
		            data:line3
		        },
		        {
		            name:'咨询建议',
		            type:'bar',
		            data:line2
		        },
		        {
		            name:'报障报修满意率',
		            type:'line',
		            yAxisIndex: 1,
		            data:line4
		        },
		        {
		            name:'家政服务满意率',
		            type:'line',
		            yAxisIndex: 1,
		            data:line6
		        },
		        {
		            name:'咨询建议满意率',
		            type:'line',
		            yAxisIndex: 1,
		            data:line5
		        },
		    ]
		};
	myChart.setOption(option);
}

function initEcharts4(data){
	
	var obj = eval('('+data+')');
	var fcmy = obj.line1;
	var jbmy = obj.line2;
	var bmy = obj.line3;
	
	var myChart = echarts.init(document.getElementById('charts_area4'));
	option = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: ['非常满意','基本满意','不满意']
		    },
		    series : [
		        {
		        	name: '所占比例',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
		                {value:fcmy, name:'非常满意'},
		                {value:jbmy, name:'基本满意'},
		                {value:bmy, name:'不满意'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
	myChart.setOption(option);
}

function initEcharts5(data){
	var obj = eval('('+data+')');
	var labels = new Array();
	var line1 = new Array();
	var line_zx = new Array();
	var line_jz = new Array();
	var gz = obj.line1;
	var zx= obj.line2;
	var jz=obj.line3;
	for(var i=0;i<gz.length;i++)
	{
		labels.push(gz[i][0]);
		line1.push(gz[i][1]);
		
	}
	for(var i=0;i<zx.length;i++)
	{
		line_zx.push(zx[i][1]);
		
	}
	for(var i=0;i<jz.length;i++)
	{
		line_jz.push(jz[i][1]);
		
	}
	var myChart = echarts.init(document.getElementById('charts_area5'));
	var option = {
		    tooltip: {
		        trigger: 'axis'
		    },
		    toolbox: {
		        feature: {
//		            dataView: {show: true, readOnly: false},
//		            magicType: {show: true, type: ['bar']},
//		            restore: {show: true},
//		            saveAsImage: {show: true}
		        }
		    },
		    legend: {
		        data:['故障报修','家政服务','咨询建议']
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
		            data:labels
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '单',
//		            min: 0,
//		            max: 1000000,
//		            interval: 10000,
		            axisLabel: {
		                formatter: '{value} '
		            }
		        }
		    ],
		    series: [
				{
				    name:'故障报修',
				    type:'bar',
				    stack:'数量',
				    barWidth:30,
				    data:line1
				},     
		        {
		            name:'家政服务',
		            type:'bar',
		            stack:'数量',
		            barWidth:30,
		            data:line_jz
		        },     
		        {
		            name:'咨询建议',
		            type:'bar',
		            stack:'数量',
		            barWidth:30,
		            data:line_zx
		        }
		    ],
//		    color: ['#7CCD7C','#228B22'],
		};
	myChart.setOption(option);
}

function getMaxArray(a1,a2,a3){
	var a = [a1,a2,a3];
	var max = a[0].length;
	var b = 0;
	for (var i = 1; i < a.length; i++) {
	    if (max<a[i].length) {
	        max=a[i].length;
	        b=i;
	    }
	}
	return a[b];
}

function initEcharts2(data){
    var obj = eval('('+data+')');
	var json = eval('('+obj+')');
	var myChart = echarts.init(document.getElementById('charts_area2'));
	var hours = ['24点', '1点', '2点', '3点', '4点', '5点', '6点',
	        '7点', '8点', '9点','10点','11点',
	        '12点', '13点', '14点', '15点', '16点', '17点',
	        '18点', '19点', '20点', '21点', '22点', '23点'];
	var days = [ '周一', '周二','周三','周四','周五','周六','周日'];
	var data =json;
	option = {
	    
	    legend: {
	        data: ['发生事件'],
	        left: 'right'
	    },
	    polar: {},
	    tooltip: {
	        formatter: function (params) {
	            return days[params.value[0]]+' '+hours[params.value[1]]+' 发生事件 '+params.value[2]+' 次';
	        }
	    },
	    angleAxis: {
	        type: 'category',
	        data: hours,
	        boundaryGap: false,
	        splitLine: {
	            show: true,
	            lineStyle: {
	                color: '#ddd',
	                type: 'dashed'
	            }
	        },
	        axisLine: {
	            show: false
	        }
	    },
	    radiusAxis: {
	        type: 'category',
	        data: days,
	        axisLine: {
	            show: false
	        },
	        axisLabel: {
	            rotate: 45
	        }
	    },
	    series: [{
	        name: 'Punch Card',
	        type: 'scatter',
	        coordinateSystem: 'polar',
	        symbolSize: function (val) {
	            return val[2] * 2;
	        },
	        data: data,
	        animationDelay: function (idx) {
	            return idx * 5;
	        }
	    }]
	};
	myChart.setOption(option);
}

function initEcharts3(data){
	var obj = eval('('+data+')');
	
	var json = eval('('+obj+')');
	var myChart = echarts.init(document.getElementById('charts_area3'));
	var hours = ['0min', '30min', '60min', '90min', '120min', '150min', '180min'];
	var eventTypes = ['故障报修', '家政服务', '咨询建议'];
	var data =json;
	data = data.map(function (item) {
	    return [item[1], item[0], item[2]];
	});

	option = {
	    legend: {
	        data: ['发生事件'],
	        left: 'right'
	    },
	    tooltip: {
	        position: 'top',
	        formatter: function (params) {
	            return '响应时间 '+hours[params.value[0]]+' '+eventTypes[params.value[1]]+' 为 '+params.value[2]+' 次';
	        }
	    },
	    grid: {
	        left: 2,
	        bottom: 10,
	        right: 10,
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        data: hours,
	        boundaryGap: false,
	        splitLine: {
	            show: true,
	            lineStyle: {
	                color: '#ddd',
	                type: 'dashed'
	            }
	        },
	        axisLine: {
	            show: false
	        }
	    },
	    yAxis: {
	        type: 'category',
	        data: eventTypes,
	        axisLine: {
	            show: false
	        }
	    },
	    series: [{
	        name: 'Punch Card',
	        type: 'scatter',
	        symbolSize: function (val) {
	            return val[2] * 1;
	        },
	        data: data,
	        animationDelay: function (idx) {
	            return idx * 5;
	        }
	    }]
	};
	myChart.setOption(option);
}


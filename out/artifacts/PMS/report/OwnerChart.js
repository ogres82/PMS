$(function(){
	initEmpSex();
	initCommunity();
	initMonthIn();
});

function initEmpSex(){
	var url= "./../ownerChart/ownerChartList.app?method=ownerInByRoomType";
    $.post(url,function(data){
    	var obj = document.getElementById('roomType');
    	initPieChart(obj,data,"房间类型入住情况");
	});
}

function initCommunity(){
	var url= "./../ownerChart/ownerChartList.app?method=ownerInByCommunity";
    $.post(url,function(data){
    	var obj = document.getElementById('roomCommunity');
    	initChart1(obj,data,"小区入住情况");
	});
}

function initMonthIn(){
	var url= "./../ownerChart/ownerChartList.app?method=ownerInByMonth";
    $.post(url,function(data){
    	var obj = document.getElementById('monthIn');
    	initChart2(obj,data,"月入住情况");
	});
}

function initPieChart(element,data,title){
	var obj = eval('('+data+')');
	var names = new Array();
	var datas = new Array();
	var datas1 = new Array();
	var inC = obj.inC;
	var outC = obj.outC;
	for(var i=0;i<inC.length;i++){
		names.push(inC[i][1]);
		var obj = {};
		obj.value = inC[i][0];
		obj.name = inC[i][1];
		datas.push(obj);
	}
	for(var j=0;j<outC.length;j++){
		names.push(outC[j][1]);
		var obj = {};
		obj.value = outC[j][0];
		obj.name = outC[j][1];
		datas1.push(obj);
	}
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(element);
    // 指定图表的配置项和数据
    var option = {
    	    tooltip: {
    	        trigger: 'item',
    	        formatter: "{a} <br/>{b}: {c} ({d}%)"
    	    },
    	    legend: {
    	        orient: 'vertical',
    	        x: 'left',
    	        data:names
    	    },
    	    series: [
    	        {
    	            name:'房间类型',
    	            type:'pie',
    	            selectedMode: 'single',
    	            radius: [0, '30%'],

    	            label: {
    	                normal: {
    	                    position: 'inner'
    	                }
    	            },
    	            labelLine: {
    	                normal: {
    	                    show: false
    	                }
    	            },
    	            data:datas
    	        },
    	        {
    	            name:'入住情况',
    	            type:'pie',
    	            radius: ['40%', '55%'],

    	            data:datas1
    	        }
    	    ]
    	};


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    window.onresize = myChart.resize;
}

function initChart1(element,data,title){
	
	var obj = eval('('+data+')');
	var labels = new Array();
	var line1 = new Array();
	var line2 = new Array();
	var line3 = new Array();
	var inC = obj.line1;
	var outC = obj.line2;
	for(var i=0;i<inC.length;i++){
		labels.push(inC[i][1]);
		line1.push(inC[i][0]);
		line2.push(outC[i][0]);
		var a = outC[i][0]/inC[i][0]*100;
		line3.push(a.toFixed(2));
	}
	
	var myChart = echarts.init(element);
	
	var option = {
			tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['房间数','入住数','入住率']
		    },
		    xAxis: [
		        {
		            type: 'category',
		            axisTick: {
		                alignWithLabel: true
		            },
		            data: labels
		        }
		    ],
		    yAxis: [
			        {
			            type: 'value',
			            name: '房间数',
//			            min: 0,
//			            max: 250,
//			            interval: 50,
			            axisLabel: {
			                formatter: '{value} 间'
			            }
			        },
			        {
			            type: 'value',
			            name: '入住率',
//			            min: 0,
//			            max: 25,
//			            interval: 5,
			            axisLabel: {
			                formatter: '{value} %'
			            }
			        }
			    ],
		    series: [
		        {
		            name:'房间数',
		            type:'bar',
		            barWidth : 40,//柱图宽度
		            stack: 'two',
		            data:line1
		        },
		        {
		            name:'入住数',
		            type:'bar',
		            barWidth : 40,//柱图宽度
		            stack: 'two',
		            data:line2
		        },
		        {
		            name:'入住率',
		            type:'line',
		            yAxisIndex: 1,
		            data:line3
		        }
		    ]
		};
	myChart.setOption(option);
	$(window).resize(myChart.resize);
}


function initChart2(element,data,title){
	
	var labels = new Array();
	var line = new Array();
	for(var i=0;i<data.length;i++){
		labels.push(data[i][1]);
		line.push(data[i][0]);
	}
	
	var myChart = echarts.init(element);
	
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
		        data:['入住数']
		    },
		    dataZoom : {
	            show : true,
	            realtime : true,
	            start : 0,
	            end : 90
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
		            name: '入住数',
//		            min: 0,
//		            max: 250,
//		            interval: 50,
		            axisLabel: {
		                formatter: '{value} 人'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'入住数',
		            type:'bar',
		            barWidth : 40,//柱图宽度
		            data:line
		        }
		    ]
		};
	myChart.setOption(option);
	$(window).resize(myChart.resize);
}
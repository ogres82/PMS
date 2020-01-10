$(function(){
	initOwnerSex();
	initOwnerAge();
	initOwnerVip();
});

function initOwnerSex(){
	var url= "./../ownerChart/ownerChartList.app?method=ownerSexChart";
    $.post(url,function(data){
    	var obj = document.getElementById('ownerSex');
    	initChart(obj,data,"性别比例");
	});
}


function initOwnerAge(){
	var url= "./../ownerChart/ownerChartList.app?method=ownerAgeChart";
    $.post(url,function(data){
    	var obj = document.getElementById('ownerAge');
    	initChart(obj,data,"年龄比例");
	});
}

function initOwnerVip(){
	var url= "./../ownerChart/ownerChartList.app?method=ownerVipChart";
    $.post(url,function(data){
    	var obj = document.getElementById('ownerVip');
    	initChart1(obj,data,"部门比例");
	});
}

function initChart(element,data,title){
	var names = new Array();
	var datas = new Array();
	for(var i=0;i<data.length;i++){
		names.push(data[i][1]);
		var obj = {};
		obj.value = data[i][0];
		obj.name = data[i][1];
		datas.push(obj);
	}
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(element);
    // 指定图表的配置项和数据
    var option = {
    	    tooltip : {
    	        trigger: 'item',
    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },
    	    legend: {
    	        orient: 'vertical',
    	        left: 'left',
    	        data: names
    	    },
    	    series : [
    	        {
    	            name: title,
    	            type: 'pie',
    	            radius : '55%',
    	            center: ['50%', '60%'],
    	            data:datas,
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

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $(window).resize(myChart.resize);
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
		    toolbox: {
		        feature: {
//		            dataView: {show: true, readOnly: false},
//		            magicType: {show: true, type: ['line', 'bar']},
//		            restore: {show: true},
//		            saveAsImage: {show: true}
		        }
		    },
		    legend: {
		        data:['会员类型1','会员类型2']
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
		            name: '会员数',
		            axisLabel: {
		                formatter: '{value} 人'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'会员类型1',
		            type:'bar',
		            barWidth : 40,//柱图宽度
		            data:line1
		        },
		        {
		            name:'会员类型2',
		            type:'bar',
		            barWidth : 40,//柱图宽度
		            data:line2
		        }
		    ]
		};
	myChart.setOption(option);
	$(window).resize(myChart.resize);
}
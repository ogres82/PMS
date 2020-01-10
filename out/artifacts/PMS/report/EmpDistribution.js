$(function(){
	initEmpSex();
	initEmpAge();
	initEmpDept();
	initEmpDegree();
	$('.timer').each(count);
});

function initEmpSex(){
	var url= "./../empChart/empChartList.app?method=empSexChart";
    $.post(url,function(data){
    	var empSex = document.getElementById('empSex');
    	initChart(empSex,data,"性别比例");
	});
}


function initEmpAge(){
	var url= "./../empChart/empChartList.app?method=empAgeChart";
    $.post(url,function(data){
    	var empSex = document.getElementById('empAge');
    	initChart(empSex,data,"年龄比例");
	});
}

function initEmpDept(){
	var url= "./../empChart/empChartList.app?method=empDeptChart";
    $.post(url,function(data){
    	var empSex = document.getElementById('empDept');
    	initChart(empSex,data,"部门比例");
	});
}

function initEmpDegree(){
	var url= "./../empChart/empChartList.app?method=empDegreeChart";
    $.post(url,function(data){
    	var empSex = document.getElementById('empDegree');
    	initChart(empSex,data,"学历比例");
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
    
    /**
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
    	            name:title,
    	            type:'pie',
    	            radius: ['50%', '70%'],
    	            avoidLabelOverlap: false,
    	            label: {
    	                normal: {
    	                    show: false,
    	                    position: 'center'
    	                },
    	                emphasis: {
    	                    show: true,
    	                    textStyle: {
    	                        fontSize: '30',
    	                        fontWeight: 'bold'
    	                    }
    	                }
    	            },
    	            labelLine: {
    	                normal: {
    	                    show: false
    	                }
    	            },
    	            data:datas
    	        }
    	    ]
    	};
	*/

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}
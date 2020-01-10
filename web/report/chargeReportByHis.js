$(function(){
	initData();
	init();
	initEcharts();
	
});
function initData(){
	var url = "../../report/chargeReport.app?method=history";
	$.post(url,
		    function(data){
				loadData = eval(data);
				initEcharts(loadData);
				
			}
	);
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
			url: '../../report/chargeReport.app?method=history',         //请求后台的URL（*）
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
            	field: 'months',
            	title: '月份'
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


function initEcharts(loadData){
	var myChart = echarts.init(document.getElementById('charts_area'));
	var months = new Array();
	var rate = new Array();
	var paidAmount = new Array();
	var arrearageAmount = new Array();
	for(var i=0;i<loadData.length;i++){
		months.push(loadData[i].months);
		paidAmount.push(loadData[i].paidAmount);
		arrearageAmount.push(loadData[i].arrearageAmount);
		var a = loadData[i].paidAmount/loadData[i].receiveAmount*100;
		rate.push(a.toFixed(2));
	}
	var option = {
		    tooltip: {
		        trigger: 'axis'
		    },
		    toolbox: {
		        feature: {
		            dataView: {show: true, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore: {show: true},
		            saveAsImage: {show: true}
		        }
		    },
		    legend: {
		        data:['欠费金额','实收金额']
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
//		            min: 0,
//		            max: 100,
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
				    barWidth:100,
				    data:paidAmount
				},     
		        {
		            name:'欠费金额',
		            type:'bar',
		            stack:'金额',
		            barWidth:100,
		            data:arrearageAmount
		        },
		        {
		            name:'收费率',
		            type:'line',
		            yAxisIndex: 1,
		            data:rate
		        }
		    ]
		};
	myChart.setOption(option);
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

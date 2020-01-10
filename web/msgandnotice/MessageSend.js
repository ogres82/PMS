selections = [];
$(function () {
	initPermissions();
	var i=0;
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
    $(".summernote").summernote({lang:"zh-CN"});
    //initAuditors();
	initMsgTemp();
	layer.config({
    	    extend: 'extend/layer.ext.js'
    	});
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","输入关键字");
	initBtnEvent();
});
function initPermissions(){
	toolbarBtnInit(); //初始化工具条按钮
}
function initBtnEvent(){
	$("#btn1").bind("click",function(){
		openWindow(1);
	})
	$("#btn3").bind("click",function(){
		openWindow(4);
	})
}
var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#chargeInfo').bootstrapTable({
			url: './../msgandnotice/mgssend.app?method=listMsg',         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: true,
    		searchOnEnterKey: true,
    		showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: false,
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
                        field: 'state',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    }, {
                        field: 'msgTemp.msgTempSubject',
                        title: '短信模板',
                        sortable: true,
                        editable: true,
                        align: 'center'
                    },{
                        field: 'msgReceivers.ownerName',
                        title: '接收人',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'msgReceivers.phone',
                        title: '接收人电话',
                        sortable: true,
                        align: 'center',
                    }, {
		            	field: 'operate',
		                title: '操作',
		                align: 'center',
		                events: operateEvents,
		                formatter: operateFormatter
                   }
               ]
		});
	};
	function operateFormatter(value, row, index) {
        return tableBtnInit();
    }
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
        	editOrCheck(row,2);
        },
    };
	function editOrCheck(obj,type){
		
    	openWindow(type,obj.msgSendId)
	}
	oTableInit.queryParams = function (params) {
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search
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
	return oTableInit;
	
};
	
    function getIdSelections() {
        return $.map($('#chargeInfo').bootstrapTable('getSelections'), function (row) {
            return row.msgSendId
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
    	}else{
    		return ['起草'].join('');
    	}
    	
       
    }

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
  //ajax获取短信模板
    function initMsgTemp(){
    	var url="./../msgandnotice/mgssend.app?method=getMsgTemp";
        $.post(url,
		      function(data){
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
		               $("<option value='"+list[j].msgTempId+"'>"+list[j].msgTempSubject+"</option>").appendTo("#msgTemp");  
		        	};
				  });
    	//return rtnValue;
    }
	//ajax审核人
    function initAuditors(){
    	var url="./../msgandnotice/mgssend.app?method=getAuditor";
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
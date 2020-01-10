var selections = [];
$(function () {
	init();
	//定位表格查询框
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","搜索");
	$(".search input").css({
		'padding-right':'23px'
	});
	$('#myForm1').validationEngine();
	initBtnEvent();
	initSearch();
});

function initBtnEvent(){
	var urlmethod = "";
	$("#btn_add").bind('click',function(){
		$('#myForm1').validationEngine('hide');
		$('#btBtnUrlAdd').show();
		$("#pageName").attr("disabled",false);
		clearForm();
		$("#myModalTitle").html("添加页面按钮");
		$('#myModal1').modal({backdrop: 'static', keyboard: false});
	});

	$("#btn_del").bind('click',function(){
		selections = getIdSelections();
		urlmethod = "method=deleteSystemBtnUrl";
		layer.confirm("您确定要删除所选信息吗?",{
			skin: 'layui-layer-molv', 
			btn: ['确定','取消']
		},function(){
	        var url=getRootPath()+"system/btnUrl.app?"+urlmethod;
			$.post(url,
					{id:selections+""},
			        function(data){
						layer.msg(eval(data),{
		    				time: 2000
		    			}, function(){
		    				$('#btnUrlInfo').bootstrapTable('refresh');
		    			});
					});
		},function(){
			return;
		})
	});

}

function init(){
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#btnUrlInfo').bootstrapTable({
			url: getRootPath()+"system/btnUrl.app?method=list",         //请求后台的URL（*）
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
            }, {
            	field: 'id',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'pageName',
            	title: '页面名称'
            }, {
            	field: 'urlName',
            	title: '页面URL'
            }, {
                field: 'btnId',
                title: '按钮ID'
            }, {
                field: 'btnName',
                title: '按钮名称'
            }, {
            	field: 'btnOrder',
                title: '按钮排序'
            },{
            	field: 'operate',
                title: '操作',
                width: '15%',
                align: 'center',
                formatter: operateFormatter,
                events: operateEvents
            }
            ],
            onCheck:function(row,e){
            	tableCheckEvents();
            },
            onUncheck: function(row,e){
            	tableCheckEvents();
            },
            onCheckAll: function(rows){
        		$("#btn_del").attr("disabled",false);
            },
            onUncheckAll: function(rows){
        		$("#btn_del").attr("disabled",true);
            },
            onLoadSuccess: function(rows){
        		$("#btn_del").attr("disabled",true);
            }
		});
	};
	
	function operateFormatter(value, row, index) {
		var html = '<a id="a_check">查看 <span style="color:#CCC">|</span> </a>'+
			'<a id="a_edit">编辑 </a>';
        return html;
    }
	//操作列的事件
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
        	editOrCheck(row,1);
        },
		'click #a_edit': function (e, value, row, index) {
			editOrCheck(row,2);
	    }
    };
	
	oTableInit.queryParams = function (params) {
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search   //表格搜索框的值
	    };
	    return temp;
	};
	return oTableInit;
	
};
	//表格选择事件
	function tableCheckEvents(){
		var r = $('#btnUrlInfo').bootstrapTable('getSelections');
    	if(r.length==0){
    		$("#btn_del").attr("disabled",true);
    	}
    	if(r.length==1){
    		$("#btn_del").attr("disabled",false);
    	}
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
    	if(milliseconds){
    		var datetime = new Date();
    		datetime.setTime(milliseconds);
    		var year=datetime.getFullYear();
    		//月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
    		var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    		var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    		return year + "-" + month + "-" + date;
    	}else{
    		return "";
    	}
    }
    
    /**选择框
     * 
     * @returns
     */
    function getIdSelections() {
        return $.map($('#btnUrlInfo').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }
    
    /**
     * 保存操作
     */
    function openSaveButton(){
    	
    	var flag = $('#myForm1').validationEngine('validate');
    	if(flag){
    		$.ajax({
    			type: "post",
    			url: getRootPath()+"system/btnUrl.app?method=save",
    			data: getDataForm(),
    			dataType: "json",
    			async : false,
    			success: function(data)
    			{
    				if(data="success"){
    					layer.msg('保存成功！',{
    						time: 2000
    					}, function(){
    						$('#btnUrlInfo').bootstrapTable('refresh');
    						$('#myModal1').modal('hide');
    					});
    				}else{
    					layer.msg('保存失败！',{
    						time: 2000
    					});
    				}
    				
    			}
    		});
    	}else{
    		layer.msg('表单验证失败！',{
				time: 2000
			});
    	}
    }
    
    /**
     * 获取表单数据
     * @returns {___anonymous7092_7851}
     */
    function getDataForm(){
    	var addJson = {
			id:$("#id").val(),
			urlId:$("#urlId").val(),
			urlName:$("#urlName").val(),
			btnId:$("#btnId").val(),
			btnName:$("#btnName").val(),
			btnOrder:$("#btnOrder").val()
    	};
    	return addJson;
    }
    
    //清空表单
    function clearForm(){
    	$("#id").val("");
    	$("#urlId").val("");
		$("#urlName").val("");
		$("#pageName").val("");
		$("#btnId").val("");
		$("#btnName").val("");
		$("#btnOrder").val("");
    }
    
    function getNowFormatDate(flag,vardate) {
    	if(flag == false && (vardate==null || vardate==''))
    		return null;
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
    
    //查看和编辑
    function editOrCheck(obj,type){
    	$('#myForm1').validationEngine('hide');
    	if(type==1){
    		$('#btBtnUrlAdd').hide();
    		$("#myModalTitle").html("查看");
    	}else{
    		$('#btBtnUrlAdd').show();
    		$("#myModalTitle").html("修改");
    	}
    	$("#id").val(obj.id);
		$("#urlId").val(obj.urlId);
		$("#urlName").val(obj.urlName);
		$("#btnId").val(obj.btnId);
		$("#btnName").val(obj.btnName);
		$("#btnOrder").val(obj.btnOrder);
		$("#pageName").val(obj.pageName);
		$("#pageName").attr("disabled",true);
		$('#myModal1').modal({backdrop: 'static', keyboard: false});
	}
    
    /*智能搜索框初始化*/
    function initSearch(){
    	//房间下拉框
    	$("#pageName").bsSuggest({
    	    url: getRootPath()+"system/btnUrl.app?method=getPageList&keyword=",
    	    showHeader: true,
    	    allowNoKeyword: true,
    	    keyField: 'keyword',
    	    getDataMethod: "url",
    	    delayUntilKeyup: true,
    	    effectiveFields: ["pageName","urlName"],
    	    effectiveFieldsAlias: {
    	    	pageName:"页面名称",
    	    	urlName:"页面URL"
    	    },
    	    //预处理
	        fnPreprocessKeyword: function(keyword) {
	        		//请求数据前，对输入关键字作预处理
	        	 return encodeURI(encodeURI(keyword));
	        },
    	    processData: function (json) {    // url 获取数据时，对数据的处理，作为 getData 的回调函数
    	        var index, len, data = {value: []};
    	        if (!json || json.length === 0) {
    	            return false;
    	        }
    	        data.value=json;
    	        return data;
    	    }
    		}).on("onDataRequestSuccess",
    		function(e, result) {
    		    console.log("onDataRequestSuccess: ", result)
    		}).on("onSetSelectValue",
    		function(e, keyword, data) {
    		    console.log("onSetSelectValue: ", keyword, data);
    		    $("#pageName").val(data.pageName);
    		    $("#urlId").val(data.urlId);
    		    $("#urlName").val(data.urlName);
    		}).on("onUnsetSelectValue",	
    		function(e) {
    		    console.log("onUnsetSelectValue")
    		});
    }

  //获取项目根路径
    function getRootPath(){
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath=window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName=window.document.location.pathname;
        var pos=curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht=curWwwPath.substring(0,pos);
        //获取带"/"的项目名，如：/uimcardprj
        var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+2);
        return(localhostPaht+projectName);
    }
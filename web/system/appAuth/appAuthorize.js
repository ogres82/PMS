var roleBtnArray = [];
var overallRoleId = "";
var overallUrlId = "";
var overallBtnId = "";
var valueArray = [];
$(function () {
	init();
//	$(".search").css({
//		'position':'fixed',
//		'left':'78%',
//		'top':'0',
//		'z-index':'1000',
//		'width':'240px'
//	});
//	$(".search input").attr("placeholder","输入关键字");
//	$('#myForm1').validationEngine();
	
	//根据屏幕分辨率设置ibox高度
	var  frameHeight = $(document.body).height()-10+'px';
	$(".ibox-content").height(frameHeight);
});


function init(){
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
    btnTable = new btnTableInit();
	btnTable.Init();
};
var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#roleInfo').bootstrapTable({
			url: '../../../system/appAuthorize.app?method=rolelist',         //请求后台的URL（*）
//			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: false,
    		classes: "table table-no-bordered",
//    		searchOnEnterKey: true,
    		showColumns: false,                  //是否显示所有的列
//          showRefresh: true,                  //是否显示刷新按钮
            showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "client",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: true,
            singleSelect: true,
            queryParams: oTableInit.queryParams,
            showExport: false,
            minimumCountColumns: 1,     //最少允许的列数
            showPaginationSwitch: false,
            pagination: false,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: '[10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
            onCheck: selectRole,
            columns: [ 
  			  {
	  			field: 'state',
	            checkbox: true
  			  }, {
              	field: 'roleId',
              	visible:false,
              	title: 'ID'
              }, {
                field: 'name',
              }, {
                field: 'desc',
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
	
	return oTableInit;
	
};
	
	function selectRole(row,$element){
		overallRoleId = row.id;
		btnTable = new btnTableInit();
		btnTable.Init();
		$('#appUrlInfo').bootstrapTable('refresh');
		
	}
	
	var btnTableInit = function(){
		var bTableInit = new Object();
		bTableInit.Init = function (){
			$('#appUrlInfo').bootstrapTable({
				url: '../../../system/appAuthorize.app?method=urllist',         //请求后台的URL（*）
//				toolbar: '#toolbar',                //工具按钮用哪个容器
	    		striped: true,
	    		search: false,
	    		classes: "table table-no-bordered",
//	    		searchOnEnterKey: true,
	    		showColumns: false,                  //是否显示所有的列
//	            showRefresh: true,                  //是否显示刷新按钮
	    		showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
	            sortable: false,
	            sortOrder: "asc",
	            sidePagination: "client",			//分页方式：client客户端分页，server服务端分页（*）
	            cache: false,
	            clickToSelect: true,
	            singleSelect: false,
	            queryParams: bTableInit.queryParams,
	            showExport: false,
	            minimumCountColumns: 1,     //最少允许的列数
	            showPaginationSwitch: false,
	            pagination: false,
	            pageNumber: 1,                       //初始化加载第一页，默认第一页
	            pageSize: 10,                       //每页的记录行数（*）
	            pageList: '[10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
//	            onCheck: ,
	            onLoadSuccess: checkBtn,
	            columns: [ 
      			{
      				field: 'state',
                    checkbox: true
      			}, {
                  	field: 'id',
                  	visible: false
                }, {
                  	field: 'urlId',
                  	title: 'URL_ID'
                }, {
                    field: 'name',
                    title: '功能名称'
                },{
                    field: 'urlName',
                    title: '路径'
                },{
                    field: 'orderNo',
                    title: '排序'
                },{
                	field: 'roleId',
                  	visible: true,
	                formatter: dataFormat
                }
                ]
			});
			
		};
		
		bTableInit.queryParams = function (params) {
			
		    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		        limit: params.limit,   //页面大小
		        offset: params.offset,  //页码
		        search: params.search,   //表格搜索框的值
		        roleId: overallRoleId
		    };
		    
		    return temp;
		};
		
		dataFormat = function(value,row){
			if(value != null){
				valueArray.push(row.urlId);
			}
		};
		return bTableInit;
	};
	
	function checkBtn(){
		$('#appUrlInfo').bootstrapTable('checkBy', {field:'urlId', values:valueArray});
		valueArray = [];  //清空数组，等待切换url重新赋值
		$('#btn').show();
	};
    
    /**
     * 保存操作
     */
    
    
    function save() {
    	var obj = $('#appUrlInfo').bootstrapTable('getData');
    	for(var i=0;i<obj.length;i++){
    		delete obj[i].name;
    		delete obj[i].orderNo;
    		delete obj[i].urlName;
    		if(obj[i].roleId == null){
    			obj[i].roleId = overallRoleId;
    		}
    	}
    	var urls = {urls:JSON.stringify(obj)}
    	$.ajax({
            type: "post",
            url: "../../../system/appAuthorize.app?method=save",
            data: urls,
            dataType: "json",
    		async : false,
            success: function(data)
            {
            	layer.msg("操作成功！",{
    				time: 1500
    			}, function(){
    				$('#appUrlInfo').bootstrapTable('refresh');
    			});
            },
            failure:function(xhr,msg)
            {
            	layer.msg('操作失败！',{
    				time: 1500
    			}, function(){
    				
    			});
            }
        });
    	
    }
    
    
    
    
 
    
   

    

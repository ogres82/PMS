var roleUrlBtn = {
					id : "",	
				    roleId : "",
				    urlId : "",
				    btnId : ""
				  };
var roleBtnArray = [];
var overallRoleId = "";
var overallUrlId = "";
var overallBtnId = "";
var valueArray = [];

$(function () {
	init();
//	function getInfo()
//	{
//	var s = "";
//	s += " 网页可见区域宽："+ document.body.clientWidth;
//	s += " 网页可见区域高："+ document.body.clientHeight;
//	s += " 网页可见区域宽："+ document.body.offsetWidth + " (包括边线和滚动条的宽)";
//	s += " 网页可见区域高："+ document.body.offsetHeight + " (包括边线的宽)";
//	s += " 网页正文全文宽："+ document.body.scrollWidth;
//	s += " 网页正文全文高："+ document.body.scrollHeight;
//	s += " 网页被卷去的高(ff)："+ document.body.scrollTop;
//	s += " 网页被卷去的高(ie)："+ document.documentElement.scrollTop;
//	s += " 网页被卷去的左："+ document.body.scrollLeft;
//	s += " 网页正文部分上："+ window.screenTop;
//	s += " 网页正文部分左："+ window.screenLeft;
//	s += " 屏幕分辨率的高："+ window.screen.height;
//	s += " 屏幕分辨率的宽："+ window.screen.width;
//	s += " 屏幕可用工作区高度："+ window.screen.availHeight;
//	s += " 屏幕可用工作区宽度："+ window.screen.availWidth;
//	s += " 你的屏幕设置是 "+ window.screen.colorDepth +" 位彩色";
//	s += " 你的屏幕设置 "+ window.screen.deviceXDPI +" 像素/英寸";
//	alert (s);
//	}
//	getInfo();
	
	//根据屏幕分辨率设置ibox高度
	var  frameHeight = $(document.body).height()-10+'px';
	$(".ibox-content").height(frameHeight);
});



function init(){
	
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#roleInfo').bootstrapTable({
			url: '../../../system/btnAuthorize.app?method=rolelist',         //请求后台的URL（*）
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
			},          
    		{
            	field: 'roleId',
            	visible:false,
            	title: 'ID'
            }, {
                field: 'name',
            },{
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
	dateFormate = function(value){
		if(value==null || value==""){
		    return "";
		}else{
			return json2Date(value);
		}
		
	};
	
	return oTableInit;
	
};
	
	function selectRole(row,$element){
		overallRoleId = row.id;
		$("#urlTree").hide();
		$("#loading").show();
		var url="../../../system/btnAuthorize.app?method=urltree&roleId="+row.id;
		
		$.post(url,function(data){
			var menu = eval(data);
			var treeData = getTreeData(menu);
			$("#loading").hide();
			$("#urlTree").show();
			var $urlTree = $("#urlTree").treeview({
				data:treeData,
				selectedBackColor:'#1ab394',
				onNodeSelected: selectUrl
			});
			$urlTree.treeview('expandAll', { levels: 3, silent: true });
		});
	}
	
	function selectUrl(event,data){
		if(data.nodes != null || data._nodes != null){
			return;
		}else{
			$('#btn').hide();
			var urlId = data.href;
			overallUrlId = urlId;
			btnTable = new btnTableInit();
			btnTable.Init();
			$('#btnInfo').bootstrapTable('refresh');
		}
		
	}
	
	var btnTableInit = function(){
		var bTableInit = new Object();
		bTableInit.Init = function (){
			$('#btnInfo').bootstrapTable({
				url: '../../../system/btnAuthorize.app?method=btnlist',         //请求后台的URL（*）
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
	            	visible:false
	            }, {
	            	field: 'btnId',
		            visible:false
		        }, {
	                field: 'btnName'
	            }, {
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
		        urlId: overallUrlId,
		        roleId: overallRoleId
		    };
		    
		    return temp;
		};
		
		dataFormat = function(value,row){
			if(value != null){
				valueArray.push(row.btnId);
			}
		};
		return bTableInit;
	};
	
	function checkBtn(){
		$('#btnInfo').bootstrapTable('checkBy', {field:'btnId', values:valueArray});
		valueArray = [];  //清空数组，等待切换url重新赋值
		$('#btn').show();
	};
    
    /**
     * 保存操作
     */
    
    
    function save() {
//    	$('#btnInfo').bootstrapTable('refresh');
    	var obj = $('#btnInfo').bootstrapTable('getData');
    	for(var i=0;i<obj.length;i++){
//    		delete obj[i].btnName;
    		if(obj[i].roleId == null){
    			obj[i].roleId = overallRoleId;
    		}
    	}
    	var btns = {btns:JSON.stringify(obj)}
    	
    	$.ajax({
            type: "post",
            url: "../../../system/btnAuthorize.app?method=save",
            data: btns,
            dataType: "json",
    		async : false,
            success: function(data)
            {
            	layer.msg("操作成功！",{
    				time: 1500
    			}, function(){
    				$('#btnInfo').bootstrapTable('refresh');
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
    

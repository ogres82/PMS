selections = [];
var loginUserName="";
var loginUserCname="";
$(function () {
	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green"});
	$("#treeDiv").css({'height':window.document.body.clientHeight-150+"px"});
//	initPermissions();
	init();
	initBtnEvent();
	//根据屏幕分辨率设置ibox高度
	var  frameHeight = $(document.body).height()-10+'px';
	$(".ibox-content").height(frameHeight);
	$(document).ajaxStop($.unblockUI);
});

function initBlockUI(){
	$.blockUI({
    	message:'<h4>操作中，请稍后...</h4>',
    	css:{
    		border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff' 
    	}
    }); 
}

function initPermissions(){
	toolbarBtnInit(); //初始化工具条按钮
}

function initBtnEvent(){
    var urlmethod = "";
	//用户新增
	$("#btn_save").bind('click',function(){
		initBlockUI();
		var row = $('#roleInfo').bootstrapTable('getSelections');
		var roleId = row[0].id;
		var ss = $('#urlTree').treeview('getChecked');
		var nodes = $.map($('#urlTree').treeview('getChecked'), function (row) {
            return row.href;
        });
		var addJson = {
			roleId:roleId,
			ids:nodes+""
		};
    	$.ajax({
            type: "post",
            url: "./../url/urlPermissions.app?method=save",
            data: addJson,
            dataType: "json",
    		async : true,
            success: function(data)
            {
            	if(data =="true"){
            		layer.msg('操作成功！',{
            			time: 2000
            		});
            	}else{
            		layer.msg('操作失败！请稍后重试',{
            			time: 3000
            		});
            	}
            }
        });
	});
}

function init(){
	
    //初始化Table
    new TableInit().Init();
    $('#roleInfo').on('load-success.bs.table',function(data){
    	$('#roleInfo').bootstrapTable('check',0);
    });
    $('#roleInfo').on('check.bs.table',function(row,data){
    	$("#loading").show();
    	$("#urlTree").hide();
    	initUrlTree(data.id);
    });
    
};

function initUrlTree(roleId){
	var url="./../url/urlPermissions.app?method=list&roleId="+roleId;
	$.post(url,function(data){
		$("#loading").hide();
		$("#urlTree").show();
		var $urlTree = $("#urlTree").treeview({
			data:data,
			highlightSelected:false,
		    multiSelect:true,
		    showCheckbox:true,
		    onNodeChecked:function(event,node){
		    	var obj = $("#administrator")[0].checked;
		    	if(obj){
		    		checkChildNode(node);
		    	}
		    	if(node.hasOwnProperty("parentId")){
		    		checkParentNode(node);
		    	}
		    },
		    onNodeUnchecked:function(event,node){
		    	if(node.hasOwnProperty("nodes")){
		    		var nodes = node.nodes;
		    		for(var i=0;i<nodes.length;i++){
		    			$('#urlTree').treeview('uncheckNode', [ nodes[i].nodeId, { silent: false } ]);
		    		}
		    	}
		    }
		});
		$urlTree.treeview('expandAll', { levels: 3, silent: true });
	});
}

function checkParentNode(node){
	if(node.hasOwnProperty("parentId")){
		var parentNode = node.parentId;
		$('#urlTree').treeview('checkNode', [ parentNode, { silent: false } ]);
		checkParentNode(parentNode);
	}
}

function checkChildNode(node){
	if(node.hasOwnProperty("nodes")){
		var nodes = node.nodes;
		for(var i=0;i<nodes.length;i++){
			$('#urlTree').treeview('checkNode', [ nodes[i].nodeId, { silent: false } ]);
			checkChildNode(nodes[i].nodeId);
		}
	}
}

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#roleInfo').bootstrapTable({
			url: './../role/roleMaintain.app?method=list',         //请求后台的URL（*）
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
            clickToSelect: true,
            singleSelect: true,
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
            	field: 'name',
            	title: '角色名'
            }, {
                field: 'desc',
                title: '描述'
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

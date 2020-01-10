$(function(){
	init();
});


function init(){
	
    oTable = new TableInit();
    oTable.Init();
   
};



var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#diagramInfo').bootstrapTable({
			url: './../model/diagram.app?opt=modelerList',         //请求后台的URL（*）
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
                        field: 'id',
                        title: 'id',
                        align: 'center',
                        valign: 'middle'
                    	field: 'id',
                        title: '事件单号',
                        sortable: true,
                        editable: true,
                        align: 'center'
                    }, {
                        field: 'key',
                        title: 'key',
                        sortable: true,
                        editable: true,
                        align: 'center'
                    }, {
                        field: 'name',
                        title: 'name',
                        sortable: true,
                        editable: true,
                        align: 'center'
                    },{
                        field: 'version',
                        title: 'version',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter: rpt_name
                    },{
                        field: 'category',
                        title: 'category',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'createTime',
                        title: 'createTime',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'lastUpdateTime',
                        title: 'lastUpdateTime',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'deploymentId',
                        title: 'deploymentId',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'metaInfo',
                        title: 'metaInfo',
                        sortable: true,
                        align: 'center',
                    }, {
                        field: 'id',
                        title: 'Item Operate',
                        align: 'center',
                        events: operateEvents,
                        formatter: operateFormatter
                    }
               ]
		});
		
	};
	
	window.operateEvents = {
	        'click.like': function (e, value, row, index) {
	            alert('You click like action, row: ' + JSON.stringify(row));
	        },
	        'click.remove': function (e, value, row, index) {
	            $table.bootstrapTable('remove', {
	                field: 'id',
	                values: [row.id]
	            });
	        }
	    };	

	
	function operateFormatter(value, row, index) {
        return [       	
            '<a class="like" href="javascript:void(0)" title="Like">修改',
            '<i class="glyphicon glyphicon-heart"></i>',
            '</a>  ',
            '<a class="remove" href="javascript:void(0)" title="Remove">删除 ',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
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
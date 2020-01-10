selections = [];
var loginUserName="";
var loginUserCname="";
$(function () {
	initPermissions();
	init();
	
	$('#myForm1').validationEngine();
	initBtnEvent();
	//根据屏幕分辨率设置ibox高度
	var  frameHeight = $(document.body).height()-10+'px';
	$(".ibox-content").height(frameHeight);
});

function initPermissions(){
	toolbarBtnInit(); //初始化工具条按钮
}

function initBtnEvent(){
	    var urlmethod = "";
		//用户新增
		$("#btn_user_add").bind('click',function(){
			new userChoose().Init();
			new userSelected().Init();
			$('#userChoose').bootstrapTable('refresh');
			$('#userSelected').bootstrapTable('removeAll');
			$('#myModal1').modal({backdrop: 'static', keyboard: false});
		
		});
		//用户删除
		$("#btn_user_del").bind('click',function(){
			
			selections = getUserSelections();
			urlmethod = "method=deleteMember";

			layer.confirm("您确定要删除所选信息吗?",{
				skin: 'layui-layer-molv', 
				btn: ['确定','取消']
			},function(){
		        var url="./../role/roleMember.app?"+urlmethod;
				$.post(url,
   						{memberId:selections+""},
   	    		        function(data){
   							layer.msg(eval(data),{
   			    				time: 2000
   			    			}, function(){
   			    				$('#user').bootstrapTable('refresh');
   			    			});
   						});
			},function(){
				return;
			})
    	});
		
		//岗位新增
		$("#btn_position_add").bind('click',function(){
			new positionChoose().Init();
			new positionSelected().Init();
			$('#positionChoose').bootstrapTable('refresh');
			$('#positionSelected').bootstrapTable('removeAll');
			$('#myModal2').modal({backdrop: 'static', keyboard: false});
		
		});
		
		//岗位删除
		$("#btn_position_del").bind('click',function(){
			
			selections = getPositionSelections();
			urlmethod = "method=deleteMember";

			layer.confirm("您确定要删除所选信息吗?",{
				skin: 'layui-layer-molv', 
				btn: ['确定','取消']
			},function(){
		        var url="./../role/roleMember.app?"+urlmethod;
				$.post(url,
   						{memberId:selections+""},
   	    		        function(data){
   							layer.msg(eval(data),{
   			    				time: 2000
   			    			}, function(){
   			    				$('#position').bootstrapTable('refresh');
   			    			});
   						});
			},function(){
				return;
			})
    	});
		
		//部门新增
		$("#btn_dept_add").bind('click',function(){
			new deptChoose().Init();
			$('#deptChoose').bootstrapTable('refresh');
			$('#myModal3').modal({backdrop: 'static', keyboard: false});
		
		});
		
		//部门删除
		$("#btn_dept_del").bind('click',function(){
			
			selections = getDeptSelections();
			urlmethod = "method=deleteMember";

			layer.confirm("您确定要删除所选信息吗?",{
				skin: 'layui-layer-molv', 
				btn: ['确定','取消']
			},function(){
		        var url="./../role/roleMember.app?"+urlmethod;
				$.post(url,
   						{memberId:selections+""},
   	    		        function(data){
   							layer.msg(eval(data),{
   			    				time: 2000
   			    			}, function(){
   			    				$('#dept').bootstrapTable('refresh');
   			    			});
   						});
			},function(){
				return;
			})
    	});
}

function init(){
	
    //初始化Table
    new TableInit().Init();
    $('#roleInfo').on('load-success.bs.table',function(data){
    	$('#roleInfo').bootstrapTable('check',0);
    	new userTable().Init();
    	new positionTable().Init();
    	new deptTable().Init();
    });
    $('#roleInfo').on('check.bs.table',function(row,data){
    	$('#user').bootstrapTable('refresh');
    	$('#position').bootstrapTable('refresh');
    	$('#dept').bootstrapTable('refresh');
    });
    
};

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
	
var userTable = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#user').bootstrapTable({
			url: './../role/roleMember.app?method=list',         //请求后台的URL（*）
    		striped: true,
    		searchOnEnterKey: true,
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
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
            	field: 'username',
            	title: '登录名'
            }, {
            	field: 'cname',
            	title: '用户'
            }, {
                field: 'granted',
                title: '是否授权',
                align: 'center',
                formatter:operateFormatter
            }
            ],
            onCheck:function(row,e){
            	tableCheckEvents();
            },
            onUncheck: function(row,e){
            	tableCheckEvents();
            },
            onCheckAll: function(rows){
        		$("#btn_user_del").attr("disabled",false);
            },
            onUncheckAll: function(rows){
        		$("#btn_user_del").attr("disabled",true);
            },
            onLoadSuccess: function(rows){
        		$("#btn_user_del").attr("disabled",true);
            }
		});
	};
	
	function operateFormatter(value, row, index) {
		if(value==true){
			return "授权";
		}else{
			return "未授权";
		}
    }
	
	oTableInit.queryParams = function (params) {
		var row = $('#roleInfo').bootstrapTable('getSelections'); 
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search,  //表格搜索框的值
	        roleId: row[0].id,
	        type: "user"
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
//表格选择事件
function tableCheckEvents(){
	var r = $('#user').bootstrapTable('getSelections');
	if(r.length==0){
		$("#btn_user_del").attr("disabled",true);
	}
	if(r.length==1){
		$("#btn_user_del").attr("disabled",false);
	}
}

var positionTable = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#position').bootstrapTable({
			url: './../role/roleMember.app?method=list',         //请求后台的URL（*）
    		striped: true,
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
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
            	field: 'positionId',
            	visible:false,
            	title: 'positionId'
            }, {
            	field: 'position.name',
            	title: '岗位'
            }, {
                field: 'granted',
                title: '是否授权',
                align: 'center',
                formatter:operateFormatter
            }
            ],
            onCheck:function(row,e){
            	positionCheckEvents();
            },
            onUncheck: function(row,e){
            	positionCheckEvents();
            },
            onCheckAll: function(rows){
        		$("#btn_position_del").attr("disabled",false);
            },
            onUncheckAll: function(rows){
        		$("#btn_position_del").attr("disabled",true);
            },
            onLoadSuccess: function(rows){
        		$("#btn_position_del").attr("disabled",true);
            }
		});
	};
	
	function operateFormatter(value, row, index) {
		if(value==true){
			return "授权";
		}else{
			return "未授权";
		}
    }
	//操作列的事件
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
        	editOrCheck(row,1);
        },
		'click #a_edit': function (e, value, row, index) {
			editOrCheck(row,2);
	    },
        
    };
	
	oTableInit.queryParams = function (params) {
		var row = $('#roleInfo').bootstrapTable('getSelections'); 
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search,  //表格搜索框的值
	        roleId: row[0].id,
	        type: "position"
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

//表格选择事件
function positionCheckEvents(){
	var r = $('#position').bootstrapTable('getSelections');
	if(r.length==0){
		$("#btn_position_del").attr("disabled",true);
	}
	if(r.length==1){
		$("#btn_position_del").attr("disabled",false);
	}
}

var deptTable = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#dept').bootstrapTable({
			url: './../role/roleMember.app?method=list',         //请求后台的URL（*）
            sortable: false,
            sortOrder: "asc",
            sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
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
            	field: 'deptId',
            	visible:false,
            	title: 'deptId'
            }, {
            	field: 'dept.name',
            	title: '部门'
            }, {
                field: 'granted',
                title: '是否授权',
                align: 'center',
                formatter:operateFormatter
            }
            ],
            onCheck:function(row,e){
            	deptCheckEvents();
            },
            onUncheck: function(row,e){
            	deptCheckEvents();
            },
            onCheckAll: function(rows){
        		$("#btn_dept_del").attr("disabled",false);
            },
            onUncheckAll: function(rows){
        		$("#btn_dept_del").attr("disabled",true);
            },
            onLoadSuccess: function(rows){
        		$("#btn_dept_del").attr("disabled",true);
            }
		});
	};
	
	function operateFormatter(value, row, index) {
		if(value==true){
			return "授权";
		}else{
			return "未授权";
		}
    }
	//操作列的事件
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
        	editOrCheck(row,1);
        },
		'click #a_edit': function (e, value, row, index) {
			editOrCheck(row,2);
	    },
        
    };
	
	oTableInit.queryParams = function (params) {
		var row = $('#roleInfo').bootstrapTable('getSelections'); 
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search,  //表格搜索框的值
	        roleId: row[0].id,
	        type: "dept"
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

//表格选择事件
function deptCheckEvents(){
	var r = $('#dept').bootstrapTable('getSelections');
	if(r.length==0){
		$("#btn_dept_del").attr("disabled",true);
	}
	if(r.length==1){
		$("#btn_dept_del").attr("disabled",false);
	}
}


var userChoose = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#userChoose').bootstrapTable({
			url: './../areaPlant/areaPlantList.app?method=userInfo',         //请求后台的URL（*）
            sortable: false,
            sortOrder: "asc",
            search: true,
            sidePagination: "client",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            maintainSelected:true,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: '[5, 10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
            {
                field: 'state',
                checkbox: true,
            }, {
            	field: 'username',
            	title: '用户名'
            }, {
            	field: 'cname',
            	title: '姓名'
            }, {
            	field: 'deptName',
            	title: '部门'
            }]
		});
	};
	
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

var userSelected = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#userSelected').bootstrapTable({
    		striped: true,
            sortOrder: "asc",
            sidePagination: "client",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            maintainSelected:true,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: '[5, 10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
            {
                field: 'state',
                checkbox: true,
            }, {
            	field: 'username',
            	title: '用户名'
            }, {
            	field: 'cname',
            	title: '姓名'
            }]
		});
	};
	
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

var positionChoose = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#positionChoose').bootstrapTable({
			url: './../role/roleMember.app?method=positionList',         //请求后台的URL（*）
            sortable: false,
            sortOrder: "asc",
            sidePagination: "client",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            maintainSelected:true,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: '[5, 10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
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
            	title: '岗位'
            }]
		});
	};
	
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

var positionSelected = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#positionSelected').bootstrapTable({
//			url: './../areaPlant/areaPlantList.app?method=userInfo',         //请求后台的URL（*）
            sortable: false,
            sortOrder: "asc",
            sidePagination: "client",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            maintainSelected:true,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: '[5, 10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
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
            	title: '岗位'
            }]
		});
	};
	
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

var deptChoose = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#deptChoose').bootstrapTable({
			url: './../role/roleMember.app?method=deptList',         //请求后台的URL（*）
            sortable: false,
            sortOrder: "asc",
            sidePagination: "client",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            maintainSelected:true,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: '[5, 10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
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
            	title: '部门'
            }]
		});
	};
	
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
        var datetime = new Date();
        datetime.setTime(milliseconds);
        var year=datetime.getFullYear();
             //月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        return year + "-" + month + "-" + date;
    }
    
    
    
    /**选择框
     * 
     * @returns
     */
    function getUserSelections() {
        return $.map($('#user').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }
    
    function getPositionSelections() {
        return $.map($('#position').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }
    
    function getDeptSelections() {
        return $.map($('#dept').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }
    
    function getNameSelections() {
        return $.map($('#roleInfo').bootstrapTable('getSelections'), function (row) {
            return row.name;
        });
    }
    
    function getUsernameSelected() {
        return $.map($('#userSelected').bootstrapTable('getData'), function (row) {
            return row.username;
        });
    }
    
    function getPositionIdSelected() {
        return $.map($('#positionSelected').bootstrapTable('getData'), function (row) {
            return row.id;
        });
    }
    
    function getDeptIdSelected() {
        return $.map($('#deptChoose').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }
    
    /**
     * 保存操作
     */
    function openSaveButton(){
    	var flag = checkRepeatUser();
    	if(flag==""){
    		var addJson = getDataForm();
        	$.ajax({
                type: "post",
                url: "./../role/roleMember.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	layer.msg('操作成功！',{
        				time: 2000
        			}, function(){
        				$('#user').bootstrapTable('refresh');
        				$('#myModal1').modal('hide');
        			});
                },
                failure:function(xhr,msg)
                {
                	layer.msg('操作失败！',{
        				time: 3000
        			}, function(){
        				
        			});
                }
            });
    	}else if(flag==null){
    		layer.alert("请选择添加用户");
    	}else{
    		layer.alert("当前选择的["+flag+"]已添加，请重新选择");
    	}
        	
    }
    
    function saveSelectedPosition(){
    	var flag = checkRepeatPosition();
    	if(flag==""){
    		var addJson = getPositionDataForm();
        	$.ajax({
                type: "post",
                url: "./../role/roleMember.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	layer.msg('操作成功！',{
        				time: 2000
        			}, function(){
        				$('#position').bootstrapTable('refresh');
        				$('#myModal2').modal('hide');
        			});
                },
                failure:function(xhr,msg)
                {
                	layer.msg('操作失败！',{
        				time: 3000
        			}, function(){
        				
        			});
                }
            });
    	}else if(flag==null){
    		layer.alert("请选择添加岗位");
    	}else{
    		layer.alert("当前选择的["+flag+"]已添加，请重新选择");
    	}
    }
    
    function saveSelectedDept(){
    	var flag = checkRepeatDept();
    	if(flag==""){
    		var addJson = getDeptDataForm();
        	$.ajax({
                type: "post",
                url: "./../role/roleMember.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	layer.msg('操作成功！',{
        				time: 2000
        			}, function(){
        				$('#dept').bootstrapTable('refresh');
        				$('#myModal3').modal('hide');
        			});
                },
                failure:function(xhr,msg)
                {
                	layer.msg('操作失败！',{
        				time: 3000
        			}, function(){
        				
        			});
                }
            });
    	}else if(flag==null){
    		layer.alert("请选择添加部门");
    	}else{
    		layer.alert("当前选择的["+flag+"]已添加，请重新选择");
    	}
    }
    
    /**
     * 获取表单数据
     * @returns {___anonymous7092_7851}
     */
    function getDataForm(){
    	var row = $('#roleInfo').bootstrapTable('getSelections'); 
    	var addJson = {
    			id:getUsernameSelected().join(","),
    			type:"user",
    			roleId:row[0].id
    		};
    	return addJson;
    }
    
    function getPositionDataForm(){
    	var row = $('#roleInfo').bootstrapTable('getSelections'); 
    	var addJson = {
    			id:getPositionIdSelected().join(","),
    			type:"position",
    			roleId:row[0].id
    		};
    	return addJson;
    }
    
    function getDeptDataForm(){
    	var row = $('#roleInfo').bootstrapTable('getSelections'); 
    	var addJson = {
    			id:getDeptIdSelected().join(","),
    			type:"dept",
    			roleId:row[0].id
    		};
    	return addJson;
    }
    
    
    
    //清空表单
    function clearForm(){
    	$("#roleId").val("");
    	$("#name").val("");
		$("#desc").val("");
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
    		$('#btEmpAdd').hide();
    		$("#myModalTitle").html("查看");
    	}else{
    		$('#btEmpAdd').show();
    		$("#myModalTitle").html("编辑");
    	}
    	var urlmethod = "method=editRole&roleId="+obj.id;
		
		var url="./../role/roleMaintain.app?"+urlmethod;
	    $.post(url,function(data){
	    	debugger;
	   		var list = eval(data);
	   		$("#roleId").val(list.id);
	   		$("#name").val(list.name);
	   		$("#desc").val(list.desc);
		});
		$('#myModal1').modal({backdrop: 'static', keyboard: false});
	}
    
    function selectUser(type){
    	if(type==1){
    		var r = $('#userChoose').bootstrapTable('getSelections');
    		var repeatUser = new Array();
    		for(var i=0;i<r.length;i++){
    			var url="./../role/roleMember.app?method=checkUniqueMember";
    			$.ajax({
                    type: "post",
                    url: url,
                    data: {username:r[i].username},
                    dataType: "json",
            		async : false,
                    success: function(data)
                    {
                    	if(data=="true"){
        		    		var data = {
        							username:r[i].username,
        							cname:r[i].cname
        		    			}
        		    			var username = new Array();
        		    			username.push(r[i].username);
        		    			$('#userChoose').bootstrapTable('remove', {field: 'username', values: username});
        		    			$('#userSelected').bootstrapTable('remove', {field: 'username', values: username});
        		    			$('#userSelected').bootstrapTable('append', data);
        		    	}else{
        		    		repeatUser.push(r[i].username);
        		    	}
                    }
                });
    		}
    		if(repeatUser.length>0){
    			layer.alert("当前选择的["+repeatUser.join(",")+"]在其他角色中已添加，请重新选择");
    		}
    	}else{
    		var r = $('#userSelected').bootstrapTable('getSelections');
    		for(var i=0;i<r.length;i++){
    			var data = {
					username:r[i].username,
					cname:r[i].cname
    			}
    			var username = new Array();
    			username.push(r[i].username);
    			$('#userSelected').bootstrapTable('remove', {field: 'username', values: username});
    			$('#userChoose').bootstrapTable('remove', {field: 'username', values: username});
    			$('#userChoose').bootstrapTable('append', data);
    		}
    	}
    }
    
    function selectPosition(type){
    	if(type==1){
    		var r = $('#positionChoose').bootstrapTable('getSelections');
    		for(var i=0;i<r.length;i++){
    			var data = {
					id:r[i].id,
					name:r[i].name
    			}
    			var id = new Array();
    			id.push(r[i].id);
    			$('#positionChoose').bootstrapTable('remove', {field: 'id', values: id});
    			$('#positionSelected').bootstrapTable('remove', {field: 'id', values: id});
    			$('#positionSelected').bootstrapTable('append', data);
    		}
    	}else{
    		var r = $('#positionSelected').bootstrapTable('getSelections');
    		for(var i=0;i<r.length;i++){
    			var data = {
					id:r[i].id,
					name:r[i].name
    			}
    			var id = new Array();
    			id.push(r[i].id);
    			$('#positionSelected').bootstrapTable('remove', {field: 'id', values: id});
    			$('#positionChoose').bootstrapTable('remove', {field: 'id', values: id});
    			$('#positionChoose').bootstrapTable('append', data);
    		}
    	}
    }
    
    //判断是否包含重复项
    function checkRepeatUser(){
    	var row1 = $('#userSelected').bootstrapTable('getData');
    	if(row1.length==0){
    		return null;
    	}else{
	    	var row2 = $('#user').bootstrapTable('getData');
	    	var result = "";
	    	for(var i=0;i<row1.length;i++){
	    		for(var j=0;j<row2.length;j++){
	    			if(row1[i].username==row2[j].username){
	    				result += row2[j].cname+" ";
	    			}
	    		}
	    	}
	    	return result;
    	}
    }
    
    function checkRepeatPosition(){
    	var row1 = $('#positionSelected').bootstrapTable('getData');
    	if(row1.length==0){
    		return null;
    	}else{
	    	var row2 = $('#position').bootstrapTable('getData');
	    	var result = "";
	    	for(var i=0;i<row1.length;i++){
	    		for(var j=0;j<row2.length;j++){
	    			if(row1[i].id==row2[j].positionId){
	    				result += row2[j].position.name+" ";
	    			}
	    		}
	    	}
	    	return result;
    	}
    }
    
    function checkRepeatDept(){
    	var row1 = $('#deptChoose').bootstrapTable('getSelections');
    	if(row1.length==0){
    		return null;
    	}else{
    		var row2 = $('#dept').bootstrapTable('getData');
    		var result = "";
    		for(var i=0;i<row1.length;i++){
    			for(var j=0;j<row2.length;j++){
    				if(row1[i].id==row2[j].deptId){
    					result += row2[j].dept.name+" ";
    				}
    			}
    		}
    		return result;
    	}
    }

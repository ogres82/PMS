selections = [];
var userNames;
var loginUserName="";
var loginUserCname="";
var positions="";
var privilegePosition="";
$.ajaxSetup({
  async: false
  });
$(function () {
	initPermissions();
	initUserDrop();
	init();
	initSearch();//初始化搜索框
	initPrivilege();
	//定位表格查询框
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","输入关键字");
	$('#myForm1').validationEngine();
	initBtnEvent();
});
function initPermissions(){
	toolbarBtnInit(); //初始化工具条按钮
}
function initBtnEvent(){
	$("#btn1").bind("click",function(){
		openButtonWindow(1);
	})
	$("#btn4").bind("click",function(){
		openButtonWindow(4);
	})
}

function initPrivilege(){
	$.ajax({
        type: "post",
        url: "./../system/privilege.app?method=initData",
        dataType: "json",
		async : false,
        success: function(data)
        {
        	var list = eval(data);
        	for(var j=0;j<list.length;j++){
        		if(list[j].type == "03"){
        			privilegePosition = list[j].positionId;
        		}
        	}
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

function init(){
	
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
    $(".summernote").summernote({lang:"zh-CN"});
    var i=0;
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#empInfo').bootstrapTable({
			url: './../secmanage/firecontrolDutyList.app?method=list',         //请求后台的URL（*）
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
            	field: 'recId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'recCode',
            	title: '记录编码'
            }, {
                field: 'shiftType',
                title: '班次',
				formatter: shiftTypeFormate
            }, {
                field: 'shiftDate',
                title: '日期'
            }, {
            	field: 'shiftPasserId',
                title: '交班人',
				formatter: userFormate
            }, {
                field: 'shiftMarker',
                title: '交接班标识',
				formatter:shiftTakeFormate
            }, {
                field: 'shiftTakerId',
                title: '接班人',
                formatter: userFormate
            },{
                field: 'shiftTakeTime',
                title: '交接班时间'
            },{
                field: 'shiftCheckMarker',
                title: '审阅标识',
				formatter:shiftCheckFormate
            }, {
                field: 'shiftCheckerId',
                title: '审阅人'
            },{
                field: 'shiftCheckTime',
                title: '审阅时间'
            }, {
            	field: 'operate',
                title: '操作',
                align: 'center',
                events: operateEvents,
                formatter: operateFormatter
            }
            ],
            onCheck:function(row,e){
            	tableCheckEvents();
            },
            onUncheck: function(row,e){
            	tableCheckEvents();
            },
            onCheckAll: function(rows){
            	$("#btn2").attr("disabled",true);
        		$("#btn3").attr("disabled",true);
        		$("#btn4").attr("disabled",false);
            },
            onUncheckAll: function(rows){
        		$("#btn2").attr("disabled",true);
        		$("#btn3").attr("disabled",true);
        		$("#btn4").attr("disabled",true);
            }
		});
	};
	function operateFormatter(value, row, index) {
        return tableBtnInit();
    }
	//操作列的事件
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
        	editOrCheck(row,3);
        },
		'click #a_edit': function (e, value, row, index) {
			editOrCheck(row,2);
	    },
		'click #a_view': function (e, value, row, index) {
        	editOrCheck(row,6);
        },
		'click #a_take_sheet': function (e, value, row, index) {
			editOrCheck(row,5);
	    },
        
    };
	function editOrCheck(obj,type){
		console.log(obj);
    	openButtonWindow(type,obj.recId)
	}
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
    //把用户名转回去
    function userFormate(value,row,index){
		return userNames.get(value);
	}
	function shiftTypeFormate(value,row,index){
		if(value==0){
			return "早班";
		}else{
			return "晚班";
		}
	}
	function shiftTakeFormate(value,row,index){
		if(value==0){
			return "未接班";
		}else{
			return "已接班";
		}
	}
	function shiftCheckFormate(value,row,index){
		if(value==0){
			return "未审阅";
		}else{
			return "已审阅";
		}
	}
	//表格选择事件
	function tableCheckEvents(){
		var r = $('#empInfo').bootstrapTable('getSelections');
    	if(r.length==0){
    		$("#btn2").attr("disabled",true);
    		$("#btn3").attr("disabled",true);
    		$("#btn4").attr("disabled",true);
			$("#btn5").attr("disabled",true);
			$("#btn6").attr("disabled",true);
    	}
    	if(r.length==1){
    		$("#btn2").attr("disabled",false);
    		$("#btn3").attr("disabled",false);
    		$("#btn4").attr("disabled",false);
			$("#btn5").attr("disabled",false);
			$("#btn6").attr("disabled",false);
    	}
    	if(r.length>1){
    		$("#btn2").attr("disabled",true);
    		$("#btn3").attr("disabled",true);
			$("#btn5").attr("disabled",true);
			$("#btn6").attr("disabled",true);
    	}
	}
	//初始化各种主管
	function initUserDrop(){
		var url="./../secmanage/firecontrolDutyList.app?method=initUserDrop";
	    $.post(url,
		      function(data){
				 var list = eval(data);
				 var m = new Map(); 
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 $("<option value='"+detailList.username+"'>"+detailList.cname+"</option>").appendTo("#shiftTakerId");
					 $("<option value='"+detailList.username+"'>"+detailList.cname+"</option>").appendTo("#shiftPasserId");
				     m.put(detailList.username,detailList.cname);     
				}
				userNames=m;
			}
	    );
	}
	function Map() {     
	    /** 存放键的数组(遍历用到) */    
	    this.keys = new Array();     
	    /** 存放数据 */    
	    this.data = new Object();     
	         
	    /**   
	     * 放入一个键值对   
	     * @param {String} key   
	     * @param {Object} value   
	     */    
	    this.put = function(key, value) {     
	        if(this.data[key] == null){     
	            this.keys.push(key);     
	        }     
	        this.data[key] = value;     
	    };     
	         
	    /**   
	     * 获取某键对应的值   
	     * @param {String} key   
	     * @return {Object} value   
	     */    
	    this.get = function(key) {     
	        return this.data[key];     
	    };     
	         
	    /**   
	     * 删除一个键值对   
	     * @param {String} key   
	     */    
	    this.remove = function(key) {     
	        this.keys.remove(key);     
	        this.data[key] = null;     
	    };     
	         
	    /**   
	     * 遍历Map,执行处理函数   
	     *    
	     * @param {Function} 回调函数 function(key,value,index){..}   
	     */    
	    this.each = function(fn){     
	        if(typeof fn != 'function'){     
	            return;     
	        }     
	        var len = this.keys.length;     
	        for(var i=0;i<len;i++){     
	            var k = this.keys[i];     
	            fn(k,this.data[k],i);     
	        }     
	    };     
	         
	    /**   
	     * 获取键值数组(类似Java的entrySet())   
	     * @return 键值对象{key,value}的数组   
	     */    
	    this.entrys = function() {     
	        var len = this.keys.length;     
	        var entrys = new Array(len);     
	        for (var i = 0; i < len; i++) {     
	            entrys[i] = {     
	                key : this.keys[i],     
	                value : this.data[i]     
	            };     
	        }     
	        return entrys;     
	    };     
	         
	    /**   
	     * 判断Map是否为空   
	     */    
	    this.isEmpty = function() {     
	        return this.keys.length == 0;     
	    };     
	         
	    /**   
	     * 获取键值对数量   
	     */    
	    this.size = function(){     
	        return this.keys.length;     
	    };     
	         
	    /**   
	     * 重写toString    
	     */    
	    this.toString = function(){     
	        var s = "{";     
	        for(var i=0;i<this.keys.length;i++,s+=','){     
	            var k = this.keys[i];     
	            s += k+"="+this.data[k];     
	        }     
	        s+="}";     
	        return s;     
	    };     
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
    function getIdSelections() {
        return $.map($('#empInfo').bootstrapTable('getSelections'), function (row) {
            return row.recId;
        });
    }
   function getNameSelections() {
        return $.map($('#empInfo').bootstrapTable('getSelections'), function (row) {
            return row.empName;
        });
    } 
    /**
     * 保存操作
     */
    function openSaveButton(){
    	
    	var flag = $('#myForm1').validationEngine('validate');
    	if(flag){
    		$("#btEmpAdd").attr("disabled","true");
        	var addJson = getDataForm();
        	//alert(addJson);
        	$.ajax({
                type: "post",
                url: "./../secmanage/firecontrolDutyList.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	layer.msg('操作成功！',{
        				time: 2000
        			}, function(){
        				$('#empInfo').bootstrapTable('refresh');
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
    	}else{
    		layer.msg('表单验证未通过！',{
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
    			recId:$("#recId").val(),
    			recCode:$("#recCode").val(),
    			shiftType:$("#shiftType").val(),
    			shiftDate:$("#shiftDate").val(),
    			shiftPasserId:$("#shiftPasserId").val(),
    			shiftDutyNum:$("#shiftDutyNum").val(),
    			shiftDutyList:$("#shiftDutyList").val(),
    			shiftDutyVocate:$("#shiftDutyVocate").val(),
    			shiftDutyPerform:$("#shiftDutyPerform").val(),
    			shiftGoodsTransfer:$("#shiftGoodsTransfer").val(),
    			shiftTakeCase:$("#shiftTakeCase").val(),
    			shiftDutyCase:$("#shiftDutyCase").val(),
    			shiftPassCase:$("#shiftPassCase").val(),
    			shiftMarker:$("#shiftMarker").val(),
    			shiftTakerId:$("#shiftTakerId").val(),
    			shiftTakeTime:$("#shiftTakeTime").val(),
    			shiftCheckMarker:$("#shiftCheckMarker").val(),
    			shiftCheckerId:$("#shiftCheckerId").val(),
    			shiftCheckTime:$("#shiftCheckTime").val()
    		};
    	return addJson;
    }
    
    /**前排按钮监控
     * 
     * @param type
     */
    function openButtonWindow(type,id){
    	$('#myForm1').validationEngine('hide');
    	$('#btEmpAdd').removeAttr("disabled"); 
    	$('#btEmpAdd').show();
    	var isOpen = false;
    	selections = getIdSelections();
    	var length=selections.length;
    	if(type==1 || type==4){
    		isOpen = true;
    	}else{
    		if(id){
    			isOpen = true;
    		 }
    	}
    	
    	if(isOpen){
    		var empId = 0;
    		selections = getIdSelections();
    		empId=id;
    		var empName= getNameSelections()[0];
    		var urlmethod = "";
    		//新增
    		if(type==1){
    			urlmethod = "method=addEmp";
    		}else if(type==2||type==5||type==6){
    			//修改
    			urlmethod = "method=edit&empId="+empId;
    		}else if(type==3){
    			//查看
    			$('#btEmpAdd').attr("disabled","true"); 
    			$('#btEmpAdd').hide();
    			urlmethod = "method=viewEmp&empId="+empId;
    		}else if(type==4){
    			//删除
    			//var deleteIds = JSON.stringify(selections);
    			urlmethod = "method=delete";
    			//alert(urlmethod);
    		}
    		
    		if(type==4){
    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="./../secmanage/firecontrolDutyList.app?"+urlmethod;
    				$.post(url,
       						{empId:selections+""},
       	    		        function(data){
       							layer.msg(eval(data),{
       			    				time: 2000
       			    			}, function(){
       			    				$('#empInfo').bootstrapTable('refresh');
       			    			});
       							
       						});
    			},function(){
    				return;
    			})
    		}else if(type==1){
				var url="./../secmanage/firecontrolDutyList.app?method=add";
    		    $.post(url,function(data){
					clearForm();
					var res=eval(data);
					var now=getNowFormatDate(true);
					$("#shiftCheckerId").val("");
				    $("#recCode").prop("disabled", true);
    			    $("#shiftType").prop("disabled", false);
    			    $("#shiftDate").prop("disabled", false);
    			    $("#shiftPasserId").prop("disabled", true);
    			    $("#shiftDutyNum").prop("disabled", false);
    			    $("#shiftDutyList").prop("disabled", false);
    			    $("#shiftDutyVocate").prop("disabled", false);
    			    $("#shiftDutyPerform").prop("disabled", false);
    			    $("#shiftGoodsTransfer").prop("disabled", false);
    			    $("#shiftTakeCase").prop("disabled", false);
    			    $("#shiftDutyCase").prop("disabled", false);
    			    $("#shiftPassCase").prop("disabled", false);
    			    $("#shiftMarker").prop("disabled", true);
    			    $("#shiftTakerId").prop("disabled", false);
    			    $("#shiftCheckMarker").prop("disabled", true);
    			    $("#shiftTakeTime").prop("disabled", true);
    			    $("#shiftCheckerId").prop("disabled", true);
    			    $("#shiftCheckTime").prop("disabled", true);
					$("#jiebancase").hide();
					$("#shenyuediv").hide();
					$("#recCode").val(res.recCode);
					$("#shiftMarker").val(0);
					$("#shiftCheckMarker").val(0);
					$("#shiftPasserId").val(loginUserName);
    			    $("#myModalTitle").html("新增");
    			    $('#myModal1').modal();
				});
    		}else{
    			$("#myModalTitle").html(empName);
    			var url="./../secmanage/firecontrolDutyList.app?"+urlmethod;
    		    $.post(url,function(data){
    		   		var list = eval(data);
    		   		debugger;
					if(type==2&&list.shiftPasserId!=loginUserName){
						layer.alert("您不能编辑该条记录",{
    						skin: 'layui-layer-molv'
    					});
						return;
					}if(type==2&&list.shiftMarker==1){
						layer.alert("已接班不可编辑该条记录",{
    						skin: 'layui-layer-molv'
    					});
						return;
					}if(type==5&&list.shiftTakerId!=loginUserName){
						layer.alert("您不是该条值班记录的接班人，不可接班",{
    						skin: 'layui-layer-molv'
    					});
						return;
					}if(type==5&&list.shiftMarker==1){
						layer.alert("已接班，不可再次接班",{
    						skin: 'layui-layer-molv'
    					});
						return;
						
						//待修改：配置审核岗位
					}if(type==6&&positions.indexOf(privilegePosition)<0){
						layer.alert("您不是该条值班记录的审阅人，不可审阅",{
    						skin: 'layui-layer-molv'
    					});
						return;
					}if(type==6&&list.shiftCheckMarker==1){
						layer.alert("该记录已审阅,不可再审阅",{
    						skin: 'layui-layer-molv'
    					});
						return;
					}if(type==6&&list.shiftMarker!=1){
						layer.alert("该条值班记录还未接班，不可审阅",{
    						skin: 'layui-layer-molv'
    					});
						return;
					}if(type==5){
						$("#jiebancase").show();
						$("#shenyuediv").hide();
						$("#shiftMarker").prop("disabled", false);
						$("#recCode").prop("disabled", true);
        			    $("#shiftType").prop("disabled", true);
        			    $("#shiftDate").prop("disabled", true);
        			    $("#shiftPasserId").prop("disabled", true);
        			    $("#shiftDutyNum").prop("disabled", true);
        			    $("#shiftDutyList").prop("disabled", true);
        			    $("#shiftDutyVocate").prop("disabled", true);
        			    $("#shiftDutyPerform").prop("disabled", true);
        			    $("#shiftGoodsTransfer").prop("disabled", true);
        			    $("#shiftTakeCase").prop("disabled", false);
        			    $("#shiftDutyCase").prop("disabled", true);
        			    $("#shiftPassCase").prop("disabled", false);
        			    $("#shiftMarker").prop("disabled", true);
						$("#shiftMarker").val("1");
        			    $("#shiftTakerId").prop("disabled", true);
        			    $("#shiftCheckMarker").prop("disabled", true);
        			    $("#shiftTakeTime").prop("disabled", false);
        			    $("#shiftCheckerId").prop("disabled", true);
        			    $("#shiftCheckTime").prop("disabled", true);
						$("#shiftCheckerId").val(list.shiftCheckerId);
						$("#shiftCheckMarker").val(list.shiftCheckMarker);
						$("#myModalTitle").html("接班");
					}if(type==2){
						$("#shenyuediv").hide();
						$("#jiebancase").hide();
						$("#recCode").prop("disabled", true);
        			    $("#shiftType").prop("disabled", false);
        			    $("#shiftDate").prop("disabled", false);
        			    $("#shiftPasserId").prop("disabled", true);
        			    $("#shiftDutyNum").prop("disabled", false);
        			    $("#shiftDutyList").prop("disabled", false);
        			    $("#shiftDutyVocate").prop("disabled", false);
        			    $("#shiftDutyPerform").prop("disabled", false);
        			    $("#shiftGoodsTransfer").prop("disabled", false);
        			    $("#shiftTakeCase").prop("disabled", false);
        			    $("#shiftDutyCase").prop("disabled", false);
        			    $("#shiftPassCase").prop("disabled", false);
        			    $("#shiftMarker").prop("disabled", true);
        			    $("#shiftTakerId").prop("disabled", false);
        			    $("#shiftCheckMarker").prop("disabled", true);
        			    $("#shiftTakeTime").prop("disabled", true);
        			    $("#shiftCheckerId").prop("disabled", true);
        			    $("#shiftCheckTime").prop("disabled", true);
						$("#shiftCheckerId").val(list.shiftCheckerId);
						$("#shiftCheckMarker").val(list.shiftCheckMarker);
						$("#shiftMarker").val(list.shiftMarker);
						$("#myModalTitle").html("编辑");
					}if(type==6){
						$("#jiebancase").show();
						$("#shenyuediv").show();
						$("#recCode").prop("disabled", true);
        			    $("#shiftType").prop("disabled", true);
        			    $("#shiftDate").prop("disabled", true);
        			    $("#shiftPasserId").prop("disabled", true);
        			    $("#shiftDutyNum").prop("disabled", true);
        			    $("#shiftDutyList").prop("disabled", true);
        			    $("#shiftDutyVocate").prop("disabled", true);
        			    $("#shiftDutyPerform").prop("disabled", true);
        			    $("#shiftGoodsTransfer").prop("disabled", true);
        			    $("#shiftTakeCase").prop("disabled", true);
        			    $("#shiftDutyCase").prop("disabled", true);
        			    $("#shiftPassCase").prop("disabled", true);
        			    $("#shiftMarker").prop("disabled", true);
        			    $("#shiftTakerId").prop("disabled", true);
        			    $("#shiftCheckMarker").prop("disabled", true);
						$("#shiftCheckMarker").val("1");
        			    $("#shiftTakeTime").prop("disabled", true);
						$("#shiftCheckerId").val(loginUserCname);
        			    $("#shiftCheckerId").prop("disabled", true);
        			    $("#shiftCheckTime").prop("disabled", false);
						$("#shiftMarker").val(list.shiftMarker);
						$("#myModalTitle").html("审阅");
					}if(type==3){
						$("#jiebancase").show();
						$("#shenyuediv").show();
						$("#shiftCheckerId").val(list.shiftCheckerId);
						$("#myModalTitle").html("查看");
						$("#shiftCheckMarker").val(list.shiftCheckMarker);
						$("#shiftMarker").val(list.shiftMarker);
					}
    		   		$("#recId").val(list.recId);
    		   		$("#recCode").val(list.recCode);
        			$("#shiftType").val(list.shiftType);
        			$("#shiftDate").val(list.shiftDate);
        			$("#shiftPasserId").val(list.shiftPasserId);
        			$("#shiftDutyNum").val(list.shiftDutyNum);
        			$("#shiftDutyList").val(list.shiftDutyList);
        			$("#shiftDutyVocate").val(list.shiftDutyVocate);
        			$("#shiftDutyPerform").val(list.shiftDutyPerform);
        			$("#shiftGoodsTransfer").val(list.shiftGoodsTransfer);
        			$("#shiftTakeCase").val(list.shiftTakeCase);
        			$("#shiftDutyCase").val(list.shiftDutyCase);
        			$("#shiftPassCase").val(list.shiftPassCase);
        			$("#shiftTakerId").val(list.shiftTakerId);
        			$("#shiftTakeTime").val(list.shiftTakeTime);
        			$("#shiftCheckTime").val(list.shiftCheckTime);
        			$('#myModal1').modal();
    			});
    		}
    		
    	}
    }
    
    //清空表单
    function clearForm(){
    	        $("#recId").val("");
    			$("#recCode").val("");
    			$("#shiftType").val("");
    			$("#shiftDate").val("");
    			$("#shiftPasserId").val("");
    			$("#shiftDutyNum").val("");
    			$("#shiftDutyList").val("");
    			$("#shiftDutyVocate").val("");
    			$("#shiftDutyPerform").val("");
    			$("#shiftGoodsTransfer").val("");
    			$("#shiftTakeCase").val("");
    			$("#shiftDutyCase").val("");
    			$("#shiftPassCase").val("");
    			$("#shiftMarker").val("");
    			$("#shiftTakerId").val("");
    			$("#shiftTakeTime").val("");
    			$("#shiftCheckMarker").val("");
    			$("#shiftCheckerId").val("");
    			$("#shiftCheckTime").val("");
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
    
    
    /*搜索框初始化*/
    function initSearch(){
    	var addressSuggest = $("input#addres").bsSuggest({
    	    url: "./../empInfo/empList.app?type=owner&keyword=",
    	    showHeader: true,
    	    allowNoKeyword: false,
    	    keyField: 'empNo',
    	    getDataMethod: "url",
    	    delayUntilKeyup: true,
    	    effectiveFields: ["empNo","empName","empContactPhone"],
    	    effectiveFieldsAlias: {
    	    	empNo:"员工号",
    	    	empName:"员工姓名",
    	    	empContactPhone:"联系电话"
    	    },
    	    processData: function (json) {    // url 获取数据时，对数据的处理，作为 getData 的回调函数
    	        var index, len, data = {value: []};
    	        if (!json || !json.result || json.result.length === 0) {
    	            return false;
    	        }

    	        len = json.result.length;

    	        for (index = 0; index < len; index++) {
    	            data.value.push({
    	                "roomNo": json.result[index][1]+"-"+json.result[index][3]+"-"+json.result[index][5],
    	                "roomId": json.result[index][4],
    	                "ownerName": json.result[index][7],
    	                "ownerId": json.result[index][6],
    	                "phone": json.result[index][8],
    	                
    	            });
    	        }
    	        //字符串转化为 json 对象
    	        
    	        return data;
    	    }
    		}).on("onDataRequestSuccess",
    		function(e, result) {
    		    console.log("onDataRequestSuccess: ", result)
    		}).on("onSetSelectValue",
    		function(e, keyword, data) {
    		    console.log("onSetSelectValue: ", keyword, data);
//    		    alert(JSON.stringify(data));
    		    $("#ownerId").val(data.ownerId);
    		    $("#rpt_name").val(data.ownerName);
    		    $("#roomId").val(data.roomId);
    		    $("#in_call").val(data.phone);
    		}).on("onUnsetSelectValue",	
    		function(e) {
    		    console.log("onUnsetSelectValue")
    		});
    	
    }
    
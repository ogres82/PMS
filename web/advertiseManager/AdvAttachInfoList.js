selections = [];
var loginUserName="";
var loginUserCname="";
var defultUrl = "";
var openType = 1;
$(function () {
	
	defultUrl = "./../attach/advAttachInfor.app?";
	init();
	initDrops();
	initSearch();//初始化搜索框
	initPosCodeDrop();
	//initPostionDrop();
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
});


function init(){
	
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
    $(".summernote").summernote({lang:"zh-CN"});
    var i=0;
    $("#moreSearch").bind('click',function(){
   	 $("#more_search").slideToggle("slow",function(){
   		if(i==0){					
   			i=1;
   			$("#moreSearch").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收起   <i class='fa fa-angle-up'></i>");
   		}else{
   			i=0;
   			$("#moreSearch").html("更多查询    <i class='fa fa-angle-down'></i>");
   		}
   	})
   });
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#attachInfo').bootstrapTable({
			url: defultUrl+'method=list',         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: true,
    		searchOnEnterKey: true,
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
                field: 'p_id',
                checkbox: true,
            }, {
            	field: 'p_code',
            	title: '广告位编号'
            }, {
            	field: 'contract_code',
            	title: '合同编号'
            }, {
                field: 'adv_business',
                title: '广告商'
            }, {
                field: 'price',
                title: '价格'
            }, {
            	field: 'dur_time',
                title: '时限'
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
	//表格选择事件
	function tableCheckEvents(){
		var r = $('#attachInfo').bootstrapTable('getSelections');
    	if(r.length==0){
    		$("#btn2").attr("disabled",true);
    		$("#btn3").attr("disabled",true);
    		$("#btn4").attr("disabled",true);
    	}
    	if(r.length==1){
    		$("#btn2").attr("disabled",false);
    		$("#btn3").attr("disabled",false);
    		$("#btn4").attr("disabled",false);
    	}
    	if(r.length>1){
    		$("#btn2").attr("disabled",true);
    		$("#btn3").attr("disabled",true);
    	}
	}
	
	//初始化下拉框
	function initDrops(){
		
		var url= defultUrl+'method=initDropAll';
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 var code = detailList[0];
					if(code=='adv_pos_status'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#adv_pos_status");
					}
					
					if(code=='adv_pos_type'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#adv_pos_type");
					}
		                 
		        };
			}
	    );
		
	}
	
    
    
    
    function getNameSelections() {
        return $.map($('#attachInfo').bootstrapTable('getSelections'), function (row) {
            return row.a_id;
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
        	
        	$.ajax({
                type: "post",
                url: defultUrl+"method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	layer.msg('操作成功！',{
        				time: 2000
        			}, function(){
        				$('#attachInfo').bootstrapTable('refresh');
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
    			adv_pos_id:$("#adv_pos_id").val(),
    			adv_business:$("#adv_business").val(),
    			adv_business_phone:$("#adv_business_phone").val(),
    			contract_code:$("#contract_code").val(),
    			price:$("#price").val(),
    			dur_time:$("#dur_time").val(),
    			adv_position:$("#adv_position").val(),
    			adv_pos_size:$("#adv_pos_size").val(),
    			adv_pos_status:$("#adv_pos_status").val(),
    			adv_pos_type:$("#adv_pos_type").val(),
    			adv_memo:$("#adv_memo").val(),
    			adv_pos_code:$("#adv_pos_code").val(),
    			p_id:$("#p_id").val()
    		};
    	return addJson;
    }
    
    /**选择框
     * 
     * @returns
     */
    function getIdSelections() {
    	
        return $.map($('#attachInfo').bootstrapTable('getSelections'), function (row) {
            return row.a_id;
        });
    }
    
    /**前排按钮监控
     * 
     * @param type
     */
    function openButtonWindow(type){
    	openType = type;
    	$('#myForm1').validationEngine('hide');
    	$('#btEmpAdd').removeAttr("disabled"); 
    	$('#btEmpAdd').show();
    	var isOpen = false;
    	selections = getIdSelections();
    	var length=selections.length;
    	if(type==1 || type==4){
    		isOpen = true;
    	}else{
    		if(length==1){
    			isOpen = true;
    		 }else{
    			 if(length==0){
    				 layer.alert("请选择一条记录操作",{
    						skin: 'layui-layer-molv'
    					});
    			 }else{
    				 layer.alert("只能选择一条记录操作",{
    						skin: 'layui-layer-molv'
    					});
    			 }
    			 
    			 
    		 }
    	}
    	
    	
    	if(isOpen){
    		var posCode = 0;
    		selections = getIdSelections();
    		posCode=selections[0];
    		var empName= "新增";//getNameSelections()[0];
    		var urlmethod = "";
    		//新增
    		if(type==1){
    			urlmethod = "method=view";
    		}else if(type==2){
    			//修改
    			empName= "修改"
    			urlmethod = "method=view&posCode="+posCode;
    		}else if(type==3){
    			//查看
    			empName= "查看"
    			$('#btEmpAdd').attr("disabled","true"); 
    			$('#btEmpAdd').hide();
    			urlmethod = "method=view&posCode="+posCode;
    		}else if(type==4){
    			//删除
    			//var deleteIds = JSON.stringify(selections);
    			urlmethod = "method=deleteAttach";
    			
    		}
    		
    		if(type==4){
    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="./../attach/advAttachInfor.app?"+urlmethod;
    				$.post(url,
       						{posCode:selections+""},
       	    		        function(data){
       							layer.msg(data,{
       			    				time: 2000
       			    			}, function(){
       			    				$('#attachInfo').bootstrapTable('refresh');
       			    			});
       							
       						});
    			},function(){
    				return;
    			})
    		}else{
    			$("#myModalTitle").html(empName);
    			var url="./../attach/advAttachInfor.app?"+urlmethod;
    		    $.post(url,function(data){
    		   		var list = eval(data);
    		   		$("#p_id").val(list.adv_pos_id);
    		   		$("#adv_pos_code").val(list.adv_pos_code);
        			$("#contract_code").val(list.contract_code);
        			$("#price").val(list.price);
        			$("#dur_time").val(list.dur_time);
        			$("#adv_business").val(list.adv_business);
        			$("#adv_business_phone").val(list.adv_business_phone);
    			});
    			$('#myModal1').modal();
    		}
    		
    	}
    }
    
    
    /*搜索框初始化*/
    function initSearch(){
    	var addressSuggest = $("input#addres").bsSuggest({
    	    url: "./../attach/empList.app?type=owner&keyword=",
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

    		    $("#ownerId").val(data.ownerId);
    		    $("#rpt_name").val(data.ownerName);
    		    $("#roomId").val(data.roomId);
    		    $("#in_call").val(data.phone);
    		}).on("onUnsetSelectValue",	
    		function(e) {
    		    console.log("onUnsetSelectValue")
    		});
    	
    }
    
	/**
	 * 初始化广告位下拉框
	 */
	function initPosCodeDrop(){
		var url="./../attach/advAttachInfor.app?method=initPosCodeDrop";
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				
				 for(var j=0;j<list.length;j++){
					 var detailList = list[j];
					 //员工部门
					$("<option value='"+detailList.adv_pos_code+"'>"+detailList.adv_position+"</option>").appendTo("#adv_pos_code");
					
		        };
			}
	    );
	}
    
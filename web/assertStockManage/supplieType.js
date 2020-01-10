selections = [];

$(function(){
	init();
	initDrops();
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$('#myForm1').validationEngine();
});


/**
 * 刷新数据表单
 */
function buttonreresh(){
	$('#refresh').click();
}

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
		$('#supplietypeInfo').bootstrapTable({
			url: './../assertStockManage/showSupplieTyeList.app?method=getSuplieTypeInfo',         //请求后台的URL（*）
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
                        field: 'supplytype_id',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    },{
                        field: 'supply_code',
                        title: '物资编码',
                        sortable: true,
                        editable: true,
                        align: 'center'
                    },{
                        field: 'type_name',
                        title: '物资类别',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter: rpt_name
                    },{
                        field: 'type_orther',
                        title: '其他',
                        sortable: true,
                        align: 'center',
                    } 
               ]
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

/**前排按钮监控
 * 
 * @param type
 */
function openButtonWindow(type){
	$('#btAddsupplietype').removeAttr("disabled"); 
	var isOpen = false;
	selections = getIdSelections();
	var length=selections.length;
	if(type==1 || type==4){
		isOpen = true;
	}else{
		if( length==0||length==1){
			isOpen = true;
		 }else{

			 layer.alert("只能选择一条记录操作",{
				skin: 'layui-layer-molv'
			});
			 
		 }
	}
	
	if(isOpen){
		var supply_code = 0;
		selections = getIdSelections();
		supply_code=selections[0];
		var urlmethod = "";
		//新增
		if(type==1){
	   		
			$("#type_name").val("");
			$("#type_orther").val("");
			$("#supplytype_id").val("");
			$('#parent_supp_id').attr("disabled",false); 
	    	$('#type_name').attr("disabled",false); 
	    	$('#type_orther').attr("disabled",false); 
	    	
			urlmethod = "method=add";
		}else if(type==2){
			//修改
			urlmethod = "method=preQuarySupplieType&supply_code="+supply_code;
		}else if(type==3){
			//查看
			$('#btAssigenAdd').attr("disabled","true"); 
			urlmethod = "method=preQuarySupplieType&supply_code="+supply_code;
		}else if(type==4){
			//删除
			//var deleteIds = JSON.stringify(selections);
			urlmethod = "method=deleteSupplietye";
			//alert(urlmethod);
		}
		
		if(type==4){
			layer.confirm("您确定要删除所选信息吗?",{
				skin: 'layui-layer-molv', 
				btn: ['确定','取消']
			},function(){
				var deleteIds = JSON.stringify(selections);
				$.ajax({
					cache: false,
					type: "POST",
					url:"./../assertStockManage/showSupplieTyeList.app?"+urlmethod,	//把表单数据发送到ajax.jsp
					data:{supply_codes:deleteIds},	//要发送的是ajaxFrm表单中的数据
					async: false,
					success: function(data) {
						layer.msg('操作成功！',{
							time: 2000
						}, function(){
							$('#supplietypeInfo').bootstrapTable('refresh');
							$('#myModal1').modal('hide');
						});
						
						
						
						}
					});
			},function(){
				//return;
			})
		}else if(type==2||type==3){
			if(supply_code!=undefined && supply_code.length>0){
			var url="./../assertStockManage/showSupplieTyeList.app?"+urlmethod;
		    $.post(url,function(data){
		    	//var obj = eval('(' + data + ')');
		    	var obj=eval(data);
		   		$("#supply_code").val(obj.supply_code);
		   		$("#type_name").val(obj.type_name);
		   		$("#type_orther").val(obj.type_orther);
		   		$("#parent_supp_id").val(obj.parent_supp_id);
		   		$("#supplytype_id").val(obj.supplytype_id);
			});
		    if(type==3)
		    {
		    	$("#btAddsupplietype").hide(); 	
		    	$('#parent_supp_id').attr("disabled","true"); 
		    	$('#type_name').attr("disabled","true"); 
		    	$('#type_orther').attr("disabled","true"); 
		    }
		    if(type==2)
		    {
		    	$("#btAddsupplietype").show(); 	
		    	$('#parent_supp_id').attr("disabled",false); 
		    	$('#type_name').attr("disabled",false); 
		    	$('#type_orther').attr("disabled",false); 
		    }
			$('#myModal1').modal();
		 }else
			 {
			   layer.alert("请选择一条记录操作",{
					skin: 'layui-layer-molv'
				});
				return;
			 }
		}else if(type==1)
		{
			var url="./../assertStockManage/showSupplieTyeList.app?"+urlmethod;
		    $.post(url,function(data){
		   		var list = eval(data);
		   		$("#supply_code").val(list.supply_code);
			});
			$('#myModal1').modal();	
			
		}
	}
	
}


function openSaveButton(){
	var flag = $('#myForm1').validationEngine('validate');
	if(flag){
	$("#btAddsupplietype").attr("disabled","true");
	var addJson = getDataForm();
	$.ajax({
        type: "post",
        url: "./../assertStockManage/showSupplieTyeList.app?method=save",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {   
        	if(data=="1")
        		{
        		layer.msg('<font coler="red">操作成功！</font>', 2, 1, function(){
    				alert("bb");
    			});
        		setTimeout(function(){//IE6、7不会提示关闭  
						window.location.href="./../assertStockManage/supplieType.jsp"
						//buttonreresh();
					}, 2000);
        		
            } else
            	{
            	layer.msg('<font coler="red">操作失败！联系管理员</font>', 2, 1, function(){
    				alert("bb");
    			});
            	}
			
			closeLayer();
    		
        },
        failure:function(xhr,msg)
        {
			
        }
    });
	}
}

function getDataForm(){
	var addJson = {
			supply_code:$("#supply_code").val(),
			supplytype_id:$("#supplytype_id").val(),
			type_name:$("#type_name").val(),
			type_orther:$("#type_orther").val(),
			parent_supp_id:$("#parent_supp_id").val()
		};
	return addJson;
}

/**选择框
 * 
 * @returns
 */
function getIdSelections() {
    return $.map($('#supplietypeInfo').bootstrapTable('getSelections'), function (row) {
        return row.supply_code
    });
}

function initDrops() {
	
	var url = "./../assertStockManage/showSupplieTyeList.app?method=initDropAll";
	$.post(url, function(data) {

		var list = eval(data);
		for ( var j = 0; j < list.length; j++) 
		{
				$("<option value='" + list[j].id + "'>"+ list[j].code_detail_name + "</option>").appendTo("#parent_supp_id");
	    }
	});
	
}


/**操作成功后2秒自动关闭
 * 
 * @param layerName
 */
function closeLayer(){
	setTimeout(function(){//IE6、7不会提示关闭  
		$('#myModal1').modal('hide'); //执行关闭
		//buttonreresh();
	}, 2000);
}

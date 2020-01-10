selections = [];

$(function(){
	init();
	initDrops();//初始化下拉框
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","输入关键字");
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
		$('#instockInfo').bootstrapTable({
			url: './../assertStockManage/showInstockInformation.app?method=getInstockInfo',         //请求后台的URL（*）
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
                        field: 'item_id',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    },{
                        field: 'instock_id',
                        title: '库存编号',
                        align: 'center',
                        valign: 'middle',
                        visible:false
                    },{
                        field: 'warehouse_name',
                        title: '所属库存',
                        sortable: true,
                        editable: true,
                        align: 'center',
                        visible:false
                        	
                    },{
                        field: 'bar_code',
                        title: '物品编码',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        visible:false
                    },{
                        field: 'item_name',
                        title: '物品名称',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'type_name',
                        title: '物品型号',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'item_type',
                        title: '物品型号',
                        sortable: true,
                        align: 'center',
                        visible:false
                    } ,{
                        field: 'code_detail_name',
                        title: '物品类别',
                        sortable: true,
                        align: 'center',
                        visible:false
                    },{
                        field: 'unit_price',
                        title: '单价(元)',
                        sortable: true,
                        align: 'center',
                    }  ,{
                        field: 'suppliy_num',
                        title: '现有数量',
                        sortable: true,
                        align: 'center',
                    } 
                    ,{
                        field: 'sum_price',
                        title: '总价(元)',
                        sortable: true,
                        align: 'center',
                    }  ,{
                        field: 'stock_lowerlimit',
                        title: '库存下限',
                        sortable: true,
                        align: 'center'
                    } 
                    ,{
                        field: 'stock_uplimit',
                        title: '库存上限',
                        sortable: true,
                        align: 'center'
                    } ,{
                        field: 'flag',
                        title: '库存标识',
                        sortable: true,
                        align: 'center',
                        formatter:stateFormat
                    }
               ]
		});
	};
	
	oTableInit.queryParams = function (params) {
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: encodeURI(params.search)
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
	initClassifi();
	$('#btAddinstockFiles').removeAttr("disabled"); 
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
		var instock_id = 0;
		selections = getIdSelections();
		instock_id=selections[0];
		var urlmethod = "";
		 if(type==3){
			//查看
			$('#btAddinstockFiles').attr("disabled","true"); 
			urlmethod = "method=preQuaryInstockInfo&instock_id="+instock_id;
			clearInfo();
		}
		
		 if(type==3){
			if(instock_id!=undefined && instock_id.length>0){
			var url="./../assertStockManage/showInstockInformation.app?"+urlmethod;
		    $.post(url,function(data){
		    	
		    	var obj=eval(data);
		   		$("#item_name").val(obj.item_name);
		   		$("#type_name").val(obj.type_name);
		   		$("#bar_code").val(obj.bar_code);
		   		$("#item_name").val(obj.item_name);
		   		$("#item_type").val(obj.item_type);
		   		$("#item_flag").val(obj.item_flag);
		   		$("#suppliy_num").val(obj.suppliy_num);
		   		$("#item_unit").val(obj.item_unit);
		   		$("#warehouse_name").val(obj.warehouse_name);
		   		$("#sum_price").val(obj.sum_price);
		   		$("#unit_price").val(obj.unit_price);
		   		$("#item_Ptype").val(obj.item_Ptype);
		   		
		   		$("#stock_lowerlimit").val(obj.stock_lowerlimit);
		   		$("#stock_uplimit").val(obj.stock_uplimit);
			});
		    if(type==3)
		    {
		    	$("#btAddinstockFiles").hide(); 	
		    	
		    }
			$('#myModal1').modal();
		 }
		}
	}
	
}
//清空上次留下的数据
function clearInfo()
{
	$("#item_name").val("");
		$("#type_name").val("");
		$("#bar_code").val("");
		$("#item_name").val("");
		$("#item_type").val("");
		$("#item_flag").val("");
		$("#suppliy_num").val("");
		$("#item_unit").val("");
		$("#warehouse_name").val("");
		$("#sum_price").val("");
		$("#unit_price").val("");
		$("#item_Ptype").val("");	
		$("#stock_lowerlimit").val("");
   		$("#stock_uplimit").val("");

}



/**选择框
 * 
 * @returns
 */
function getIdSelections() {

    return $.map($('#instockInfo').bootstrapTable('getSelections'), function (row) {
        return row.instock_id;
    });
}



//初始化物品类别
function initDrops()
{
	var url = "./../assertStockManage/showSupplieTyeList.app?method=initDropAll";
	$.post(url, function(data) {
		var list = eval(data);
		for ( var j = 0; j < list.length; j++) 
		{
			 var code = list[j].code;
		
			 if(code=="item_type")
			 {
				$("<option value='" + list[j].id + "'>"+ list[j].code_detail_name + "</option>").appendTo("#item_Ptype");
			 }
			 if(code=="item_unit")
			{
				 $("<option value='" + list[j].id + "'>"+ list[j].code_detail_name + "</option>").appendTo("#item_unit");
			}
	    }
	});
	
}


//状态转换
function stateFormat(value, row, index) {
	
	if(value==1)
	{
		return ['<span class="label label-danger">库存已到下限</span>'].join('');
	}else if(value==2)
	{
		
	 return ['<span class="label label-success">库存已到上限</span>'].join('');	
	}else if(value==3)
	{
	 return ['<span class="label label-warning">库存不足</span>'].join('');		
		
	}
	else
	{
	return ['<span class="label label-primary">库存数量充足</span>'].join('');	
		
	}
	
}



function initClassifi()
{
	
	var url = "./../assertStockManage/showSupplieTyeList.app?method=querySuppTypeList";
	$.post(url, function(data) {
		var list = eval(data);
		for ( var j = 0; j < list.length; j++)
		{
				$("<option value='" + list[j].supply_code + "'>"+ list[j].type_name + "</option>").appendTo("#item_type");
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

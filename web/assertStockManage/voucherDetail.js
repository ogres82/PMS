selections = [];

$(function(){
	init();
	initDrops();//初始化下拉框
	$(".search").css({
		'position':'fixed',
		'right':'115px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","搜索");
	$(".search input").css({
		'padding-right':'23px'
	});
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
    	   			$("#moreSearch").html('更多查询&nbsp;<span class="caret"></span>');
    	   			$("#moreSearch").addClass("dropup");
    	   		}else{
    	   			i=0;
    	   			$("#moreSearch").html('更多查询 <span class="caret"></span>');
    	   			$("#moreSearch").removeClass("dropup");
    	   		}
    	  })
    });
};


var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#voucherDetail').bootstrapTable({
			url: './../assertStockManage/voucherDetail.app?method=getVoucherDetailInfo',         //请求后台的URL（*）
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
                        field: 'voucher_id',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    },{
                        field: 'voucher_code',
                        title: '单据编码',
                        sortable: true,
                        editable: true,
                        align: 'center'
                    },{
                        field: 'warehouse_name',
                        title: '所属库存',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter: rpt_name
                    },{
                        field: 't_handler',
                        title: '经手者',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'suppliy_name',
                        title: '供应商',
                        sortable: true,
                        align: 'center',
                    } ,{
                        field: 'occurren_date',
                        title: '时间',
                        sortable: true,
                        align: 'center',
                    } ,{
                        field: 'instok_typename',
                        title: '类型',
                        sortable: true,
                        align: 'center',
                    } ,{
                        field: 'orther',
                        title: '备注',
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
		var voucher_code = 0;
		selections = getIdSelections();
		voucher_code=selections[0];
		var urlmethod = "";
		 if(type==3){
			
			if(voucher_code!=undefined && voucher_code.length>0){
			var url="./../assertStockManage/voucherDetail.app?method=preQuaryVoucher&voucher_code="+voucher_code;
		    $.post(url,function(data){
		    	
		    	var obj=eval(data);
		   		$("#voucher_id").val(obj.voucher_id);
		   		$("#voucher_code").val(obj.voucher_code);
		   		$("#occurren_date").val(obj.occurren_date);
		   		$("#owne_stock").val(obj.owne_stock);
		   		$("#t_handler").val(obj.t_handler);
		   		$("#warehouse_name").val(obj.warehouse_name);
		   		$("#suppliy_name").val(obj.suppliy_name);
		   		$("#other").val(obj.other);
		   		$("#instok_typename").val(obj.instok_typename);
		   	 
		   		childTable(obj.voucher_code);
			});
		   
			$('#myModal1').modal();
		 }else
			 {
			   layer.alert("请选择一条记录操作",{
					skin: 'layui-layer-molv'
				});
				return;
			 }
		}
	}
	
}

function childTable(code)
{
	    $('#itemInfo').bootstrapTable('refresh', {url: './../assertStockManage/showVoucherAndStockDetail.app?method=getVoucherAndStockDetail&voucher_id='+code});
		$('#itemInfo').bootstrapTable({
		url: './../assertStockManage/showVoucherAndStockDetail.app?method=getVoucherAndStockDetail&voucher_id='+code,         //请求后台的URL（*）
 		striped: false,
 		search: false,
 		searchOnEnterKey: false,
 		showColumns: false,                  //是否显示所有的列
         showRefresh: false,                  //是否显示刷新按钮
         showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
         sortable: false,
         sortOrder: "asc",
         sidePagination: "server",			//分页方式：client客户端分页，server服务端分页（*）
         cache: true,
         clickToSelect: true,
         minimumCountColumns: 2,     //最少允许的列数
         buttonsAlign: "left",
         showPaginationSwitch: false,
         pagination: true,
         pageNumber: 1,                       //初始化加载第一页，默认第一页
         pageSize: 10,                       //每页的记录行数（*）
         pageList: '[10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
         columns: [ 
           		
                   {
                       field: 'item_name',
                       title: '物资名称',
                       sortable: true,
                       editable: true,
                       align: 'center'
                   },
                   {
                       field: 'item_code',
                       title: '物品编码',
                       sortable: true,
                       editable: true,
                       align: 'center'
                   },{
                       field: 'code_detail_name',
                       title: '物品类型',
                       align: 'center',
                       valign: 'middle',
                       sortable: true
                       //formatter: rpt_name
                   },{
                       field: 'suppliy_num',
                       title: '数量',
                       sortable: true,
                       align: 'center',
                   },{
                       field: 'unit_price',
                       title: '单价',
                       sortable: true,
                       align: 'center',
                   } ,{
                       field: 'sum_price',
                       title: '总价',
                       sortable: true,
                       align: 'center',
                   } ,{
                       field: 'suppliy_name',
                       title: '供应商名称',
                       sortable: true,
                       align: 'center',
                   } 
                   ,{
                       field: 'oper_id',
                       title: '经手者',
                       sortable: true,
                       align: 'center',
                   } 
                   ,{
                       field: 'instock_time',
                       title: '更新时间',
                       sortable: true,
                       align: 'center',
                   } 
              ]
		});

}



/**选择框
 * 
 * @returns
 */
function getIdSelections() {
    return $.map($('#voucherDetail').bootstrapTable('getSelections'), function (row) {
        return row.voucher_code;
    });
}



//初始化下拉框
function initDrops(){
	var url="./../assertStockManage/showSItmsFilesInfo.app?method=initDropAll";
  $.post(url,
	      function(data){
  	
			 var list = eval(data);
			 
			 for(var j=0;j<list.length;j++){
				 var detailList = list[j];
				 var code = detailList[0];
				 //物品类别
				if(code=='item_type'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#item_type");
				}
				
				//单位
				if(code=='item_unit'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#item_unit");
				}
				//物品类型
				if(code=='item_flag'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#item_flag");
				}
	                 
	        };
		}
  );
	//return rtnValue;
}

function clearn_Content()
{
	 $("#voucher_id").val("");
		$("#voucher_code").val("");
		$("#occurren_date").val("");
		$("#owne_stock").val("");
		$("#t_handler").val("");
		$("#warehouse_name").val("");
		$("#suppliy_name").val("");
		$("#other").val("");
		$("#instok_typename").val("");
		//$('#itemInfo').bootstrapTable('refresh');

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

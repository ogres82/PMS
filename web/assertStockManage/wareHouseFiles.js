selections = [];

$(function(){
	init();
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
		$('#wareHouseFileInfo').bootstrapTable({
			url: './../assertStockManage/warHouseFilesList.app?method=getWareHouseInfo',         //请求后台的URL（*）
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
                        field: 'warehouse_id',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    },{
                        field: 'warehouse_code',
                        title: '仓库编码',
                        sortable: true,
                        editable: true,
                        align: 'center'
                    },{
                        field: 'warehouse_name',
                        title: '仓库名称',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter: rpt_name
                    },{
                        field: 'warehouse_address',
                        title: '仓库地址',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'link_man',
                        title: '联系人',
                        sortable: true,
                        align: 'center',
                    } ,{
                        field: 'link_phone',
                        title: '联系电话',
                        sortable: true,
                        align: 'center',
                    } ,{
                        field: 'other',
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
		var warehouse_code = 0;
		selections = getIdSelections();
		warehouse_code=selections[0];
		var urlmethod = "";
		//新增
		if(type==1){
			clearnContent();
			urlmethod = "method=add";
		}else if(type==2){
			//修改
			
			urlmethod = "method=preQuaryWareHouseFilese&warehouse_code="+warehouse_code;
		}else if(type==3){
			//查看
			$('#btAssigenAdd').attr("disabled","true"); 
			urlmethod = "method=preQuaryWareHouseFilese&warehouse_code="+warehouse_code;
		}else if(type==4){
			//删除
			//var deleteIds = JSON.stringify(selections);
			urlmethod = "method=deleteWareHouseFiles";
			//alert(urlmethod);
		}
		
		if(type==4){
			layer.confirm("您确定要删除所选信息吗?",{
				skin: 'layui-layer-molv', 
				btn: ['确定','取消']
			},function(){
		        var url="./../assertStockManage/warHouseFilesList.app?"+urlmethod;
   			    var deleteIds = JSON.stringify(selections);
				$.post(url,
   						{warehouse_codes:deleteIds},
   	    		        function(data){
   						 layer.alert(data,{
   							skin: 'layui-layer-molv'
   						})
   						setTimeout(function(){//IE6、7不会提示关闭  
   							window.location.href="./../assertStockManage/wareHouseFiles.jsp"
   						}, 2000);	
   						
   						
   						});
			},function(){
				//return;
			})
		}else if(type==2||type==3){
			if(warehouse_code!=undefined && warehouse_code.length>0){
			var url="./../assertStockManage/warHouseFilesList.app?"+urlmethod;
		    $.post(url,function(data){
		    	//var obj = eval('(' + data + ')');
		    	var obj=eval(data);
		   		$("#warehouse_id").val(obj.warehouse_id);
		   		$("#warehouse_code").val(obj.warehouse_code);
		   		$("#warehouse_name").val(obj.warehouse_name);
		   		$("#warehouse_address").val(obj.warehouse_address);
		   		$("#link_man").val(obj.link_man);
		   		$("#link_phone").val(obj.link_phone);
		   		$("#other").val(obj.other);
		   		
			});
		    if(type==3)
		    {
		    	showInfo()		
		    }else
		    {
		    	wareHouseEdite();	
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
			var url="./../assertStockManage/warHouseFilesList.app?"+urlmethod;
		    $.post(url,function(data){
		   		var list = eval(data);
		   		$("#warehouse_code").val(list.warehouse_code);
			});
			$('#myModal1').modal();	
			
		}
	}
	
}
//查看效果
function showInfo()
{
	
	$("#btAddsupplietype").hide(); 
	$('#warehouse_name').attr("disabled", "true");
	$('#warehouse_address').attr("disabled",  "true");
	$('#link_man').attr("disabled",  "true");
	$('#link_phone').attr("disabled",  "true");
	$('#other').attr("disabled",  "true");

}
//仓库信息编辑效果
function wareHouseEdite()
{
	
	$("#btAddsupplietype").show(); 
	$('#warehouse_name').attr("disabled", false);
	$('#warehouse_address').attr("disabled",  false);
	$('#link_man').attr("disabled",  false);
	$('#link_phone').attr("disabled", false);
	$('#other').attr("disabled",  false);

}
//清空内容
function clearnContent()
{
	$("#btAddsupplietype").show(); 
	$('#warehouse_name').val("");
	$('#warehouse_address').val("");
	$('#link_man').val("");
	$('#link_phone').val("");
	$('#other').val("");	
	$('#warehouse_name').attr("disabled", false);
	$('#warehouse_address').attr("disabled",  false);
	$('#link_man').attr("disabled",  false);
	$('#link_phone').attr("disabled", false);
	$('#other').attr("disabled",  false);


}


function openSaveButton(){
	
	var flag = $('#myForm1').validationEngine('validate');
	if(flag){
	$("#btAddsupplietype").attr("disabled","true");
	var addJson = getDataForm();
	$.ajax({
        type: "post",
        url: "./../assertStockManage/warHouseFilesList.app?method=save",
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
						window.location.href="./../assertStockManage/wareHouseFiles.jsp"
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
			warehouse_id:$("#warehouse_id").val(),
			warehouse_code:$("#warehouse_code").val(),
			warehouse_name:$("#warehouse_name").val(),
			warehouse_address:$("#warehouse_address").val(),
			link_man:$("#link_man").val(),
			link_phone:$("#link_phone").val(),
			other:$("#other").val()
		};
	return addJson;
}

/**选择框
 * 
 * @returns
 */
function getIdSelections() {
    return $.map($('#wareHouseFileInfo').bootstrapTable('getSelections'), function (row) {
        return row.warehouse_code;
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

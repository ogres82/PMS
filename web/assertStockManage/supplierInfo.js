selections = [];

$(function(){
	init();
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
		$('#supplierInfo').bootstrapTable({
			url: './../assertStockManage/showSupplierInfo.app?method=listSupplierInfo',         //请求后台的URL（*）
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
                        field: 'suppliy_id',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    },{
                        field: 'suppliy_code',
                        title: '供应商编码',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter: rpt_name
                    },{
                        field: 'suppliy_name',
                        title: '供应商名称',
                        sortable: true,
                        align: 'center',
                    }, {
                        field: 'link_name',
                        title: '联系人',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'link_phone',
                        title: '联系电话',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'link_address',
                        title: '联系地址',
                        align: 'center',
                        sortable: true,
                    }, {
                        field: 'fax',
                        title: '传真',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                    }, {
                        field: 'zip_code',
                        title: '邮编',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
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
	
	return oTableInit;
	
};

/**前排按钮监控
 * 
 * @param type
 */
function openButtonWindow(type){
	$('#btSupplieInfoAdd').removeAttr("disabled"); 
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
		var suppliy_code = 0;
		selections = getIdSelections();
		suppliy_code=selections[0];
		var urlmethod = "";
		//新增
		if(type==1){
			cancalRecond();
			urlmethod = "method=addSupplierInfo";
		}else if(type==2){
			showInfo();
			//修改
			urlmethod = "method=viewSupplierInfo&suppliy_code="+suppliy_code;
		}else if(type==3){
			//查看
			$('#btSupplieInfoAdd').attr("disabled","true"); 
			urlmethod = "method=viewSupplierInfo&suppliy_code="+suppliy_code;
		}else if(type==4){
			//删除
			//var deleteIds = JSON.stringify(selections);
			urlmethod = "method=deleteSupplierInfo";
			//alert(urlmethod);
		}
		
		if(type==4){
			layer.confirm("您确定要删除所选信息吗?",{
				skin: 'layui-layer-molv', 
				btn: ['确定','取消']
			},function(){
		        var url="./../assertStockManage/showSupplierInfo.app?"+urlmethod;
   			    var suppliy_codes = JSON.stringify(selections);
   			    debugger;
				$.post(url,
   						{suppliy_codes:suppliy_codes},
   						function(data){
                             alert(data);
      						 layer.alert(data,{
      							skin: 'layui-layer-molv'
      						})
      						setTimeout(function(){//IE6、7不会提示关闭  
      							window.location.href="./../assertStockManage/supplierInfo.jsp"
      						}, 2000);	
      						
      						
      						});
			},function(){
				return;
			})
		}else if(type==1){
			var url="./../assertStockManage/showSupplierInfo.app?"+urlmethod;
		    $.post(url,function(data){
		   		var list = eval(data);
		   		$("#suppliy_code1").val(list.suppliy_code1);
			});
			$('#myModal1').modal();
		}else if(type==2||type==3)
		{
			if(suppliy_code!=undefined && suppliy_code.length>0){
				var url="./../assertStockManage/showSupplierInfo.app?"+urlmethod;
			    $.post(url,function(data){
			    	var list = eval(data);
			   		$("#suppliy_code1").val(list.suppliy_code);
			   		$("#suppliy_name1").val(list.suppliy_name);
			   		$("#link_name1").val(list.link_name);
			   		$("#link_phone1").val(list.link_phone);
			   		$("#link_address1").val(list.link_address);
			   		$("#fax1").val(list.fax);
			   		$("#zip_code").val(list.zip_code);
			   		$("#url").val(list.url);
			   		$("#bank").val(list.bank);
			   		$("#bank_account").val(list.bank_account);
			   		$("#linke_moble").val(list.linke_moble);
			   		$("#qq").val(list.qq);
			   		$("#other").val(list.other)
			   		$("#suppliy_id").val(list.suppliy_id)
				});
			    if(type==3)
			    {
			    	$("#btAddsupplietype").hide(); 	
			    	$('#suppliy_code1').attr("disabled","true"); 
			    	$('#suppliy_name1').attr("disabled","true"); 
			    	$('#link_name1').attr("disabled","true"); 
			    	$('#link_phone1').attr("disabled","true"); 
			    	$('#link_address1').attr("disabled","true"); 
			    	$('#fax1').attr("disabled","true"); 
			    	$('#zip_code').attr("disabled","true"); 
			    	$('#url').attr("disabled","true"); 
			    	$('#bank').attr("disabled","true");
			    	$('#bank_account').attr("disabled","true");
			    	$('#link_moble').attr("disabled","true");
			    	$('#qq').attr("disabled","true");
			    	$('#other').attr("disabled","true");
			    	
			    }
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

//展示可编辑
function showInfo()
{
	$("#btAddsupplietype").hide(); 	
	$('#suppliy_code1').attr("disabled",false); 
	$('#suppliy_name1').attr("disabled",false); 
	$('#link_name1').attr("disabled",false); 
	$('#link_phone1').attr("disabled",false); 
	$('#link_address1').attr("disabled",false); 
	$('#fax1').attr("disabled",false); 
	$('#zip_code').attr("disabled",false); 
	$('#url').attr("disabled",false); 
	$('#bank').attr("disabled",false);
	$('#bank_account').attr("disabled",false);
	$('#link_moble').attr("disabled",false);
	$('#qq').attr("disabled",false);
	$('#other').attr("disabled",false);	
}
//清除上次纪录
function cancalRecond()
{
	$('#suppliy_code1').val("");
	$('#suppliy_name1').val("");
	$('#link_name1').val(""); 
	$('#link_phone1').val(""); 
	$('#link_address1').val(""); 
	$('#fax1').val(""); 
	$('#zip_code').val("");
	$('#url').val("");
	$('#bank').val("");
	$('#bank_account').val("");
	$('#link_moble').val("");
	$('#qq').val("");
	$('#other').val("");	



}


function openSaveButton(){
	
	var flag = $('#myForm1').validationEngine('validate');
	if(flag){
	$("#btSupplieInfoAdd").attr("disabled","true");
	var addJson = getDataForm();
	$.ajax({
        type: "post",
        url: "./../assertStockManage/showSupplierInfo.app?method=save",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {   
        	if(data=="1")
        		{
        		 layer.alert("操作成功",{
     				skin: 'layui-layer-molv'
     			});
        		setTimeout(function(){//IE6、7不会提示关闭  
						window.location.href="./../assertStockManage/supplierInfo.jsp"
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
			suppliy_id:$("#suppliy_id").val(),
			suppliy_code:$("#suppliy_code1").val(),
			suppliy_name:$("#suppliy_name1").val(),
			link_name:$("#link_name1").val(),
			link_phone:$("#link_phone1").val(),
			link_address:$("#link_address1").val(),
			fax:$("#fax1").val(),
			zip_code:$("#zip_code").val(),
			url:$("#url").val(),
			bank:$("#bank").val(),
			bank_account:$("#bank_account").val(),
			linke_moble:$("#linke_moble").val(),
			qq:$("#qq").val(),
			other:$("#other").val()
		};
	return addJson;
}

/**选择框
 * 
 * @returns
 */
function getIdSelections() {
    return $.map($('#supplierInfo').bootstrapTable('getSelections'), function (row) {
        return row.suppliy_code
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

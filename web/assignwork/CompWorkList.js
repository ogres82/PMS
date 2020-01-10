$(function(){
	initData();
	initDrops();
	initSearchHandler();
	$('#myForm2').validationEngine();
	//定位表格查询框
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

$(function(){
	toolbarBtnInit();//初始化权限按钮
	for(var i=0,len=btnIdArray.length;i<len;i++)
	{
	 if(btnIdArray[i]=="btn4")
	 {
		$("#btn4").bind('click',function()
		{
			openCompButton(4);
			
		});
	 }else if(btnIdArray[i]=="btn5")
		 {
			$("#btn5").bind('click',function()
			{
				openCompButton(8);
				
			});
		 }
	}
	$("#btn4").attr("disabled",true);
	$("#btn5").attr("disabled",true);
});

function initData(){
	
   oTable = new TableInit();
   oTable.Init();
   $(".summernote").summernote({lang:"zh-CN"});
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
}

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#compDataInfo').bootstrapTable({
			url: './../assigenwork/assignlist.app?method=listComp',         //请求后台的URL（*）
			//url: './../assigenwork/assignlist.app?method=listDispatch', 
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
                        field: 'comp_kid',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    }, {
                        field: 'mtn_id',
                        title: '投诉单',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        
                    }, {
                        field: 'comp_id',
                        title: '派工单号',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        visible:false
                        
                    }, {
                        field: 'rpt_name',
                        title: '投诉人',
                        sortable: true,
                        editable: true,
                        align: 'center'
                    },{
                        field: 'addres',
                        title: '地址',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter: rpt_name
                    },{
                        field: 'comp_emergency_name',
                        title: '投诉级别',
                        sortable: true,
                        align: 'center',
                    }, {
                        field: 'cname',
                        title: '受理人',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //formatter:timeFormatCreateTime
                    }, {
                        field: 'comp_status_name',
                        title: '派工状态',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        formatter: stateFormat
                    }, {
                        field: 'comp_operator_name',
                        title: '处理人',
                        align: 'center',
                        //valign: 'middle',
                        sortable: true
                        //formatter:timeFormatCreateTime
                    }, {
                        field: 'comp_finish_time',
                        title: '销单时间',
                        align: 'center',
                        //valign: 'middle',
                        sortable: true
                        //formatter:timeFormatCreateTime
                    }, {
                    	field: 'operate',
                        title: '操作',
                        align: 'center',
                        width:'15%',
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
	        'click #a_check': function (e, value, row, index) 
	        {
	        	disableEdit();
	        	$('#compBtn5').hide();
	   			$('#compBtn6').hide();
	   			$('#compBtn7').hide();
	   			$('#btAssigenHandle').hide();
	        	openCompOptionButton(3,row);
	        	
	        },
			'click #a_send': function (e, value, row, index)
			{
				clear_compInfo();
				var flag=viliStatu(row);//校验
				if(flag=="true")
				{
					return;
				}
				$('#compBtn5').show();
				$('#compBtn6').hide();
	   			$('#compBtn7').hide();
	   			$('#btAssigenHandle').hide();
	   			$('#comp_operator_username').show();
	   			
	   			
	   			if(row.comp_status=='0')
	   			{
	   				openCompOptionButton(4,row);
	   				
	   			}else
	   			{
	   				layer.alert("已派工");
	   				return;
	   			}
				
		    },
		    'click #a_handle': function (e, value, row, index)
			{
		    	var flag=viliStatu(row);//校验
				if(flag=="true")
				{
					return;
				}
		    	$('#compBtn5').hide();
				$('#compBtn6').show();
	   			$('#compBtn7').hide();
	   			$('#btAssigenHandle').hide();
	   			
	   			if(row.comp_status=='1')
	   			{
	   				openCompOptionButton(6,row);
	   				
	   			}else
	   			{
	   				layer.alert("已处理,请销单");
	   				return;
	   			}
		    	
		    },
		    'click #a_cancel': function (e, value, row, index)
			{
		    	
		    	var flag=viliStatu(row);//校验
		   
				if(flag=="true")
				{
					return;
				}
		    	$('#compBtn5').hide();
				$('#compBtn6').hide();
	   			$('#compBtn7').show();
	   			$('#btAssigenHandle').hide();
	   			if(row.comp_status=='2')
	   			{
	   				openCompOptionButton(7,row);
	   				
	   			}else if(row.comp_status=='1'&&row.comp_emergency=='1')
	   			{
	   				layer.alert("请先处理");
	   				return;
	   			}else
	   				{
	   				layer.alert("已销单");
	   				return;
	   				}
		    	
		    }
		   
	        
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
	
	function stateFormat(value) {
		if(value == "未派工"){
			return ['<span class="label label-danger">'+value+'</span>'].join('');
		}else if(value == "已派工" || value == "处理中"){
			return ['<span class="label label-primary">'+value+'</span>'].join('');
		}else if(value == "待回访"){
			return ['<span class="label label-warning">'+value+'</span>'].join('');
		}else if(value == "已归档"){
			return ['<span class="label label-success">'+value+'</span>'].join('');
		}else{
			return [value].join('');
		}
	};
	return oTableInit;
	
};

function viliStatu(row)
{
	if(row.comp_emergency==0&&row.comp_status=='0')
		{
		layer.alert("投诉级别一般的请直接处理");
		return"true";
		}
	
	if(row.comp_emergency==0&&row.comp_status=='1')
	{
	layer.alert("投诉级别一般的请直接处理");
	return "true";
	}
	if(row.comp_emergency==0&&row.comp_status=='2')
	{
	layer.alert("已处理完成");
	return "true";
	}
	if(row.comp_emergency==0&&row.comp_status=='3')
	{
	layer.alert("已处理完成,等待客服回访评价");
	return "true";
	}
	if(row.comp_emergency==0&&row.comp_status=='4')
	{
	layer.alert("已处理完成");
	return "true";
	}
	if(row.comp_emergency==0&&row.comp_status=='5')
	{
	layer.alert("已处理完成");
	return "true";
	}
}




//表格选择事件
function tableCheckEvents(){
	
	var r = $('#compDataInfo').bootstrapTable('getSelections');
	
	if(r.length==0){
		$("#btn3").attr("disabled",true);
		$("#btn4").attr("disabled",true);
		$("#btn5").attr("disabled",true);
	}
	if(r.length==1){
		var head=r[0].mtn_id.substring(0,2);
		var type=r[0].comp_emergency_name;
		
		if(head=="TS"&&type=="一般")
		{
		 $("#btn5").attr("disabled",false);
		 $("#btn1").attr("disabled",true)
		 $("#myresult").hide();
		 $("#handleResult").hide();
		 
		}else
		{
		 $("#btn5").attr("disabled","true");
		 $("#btn1").attr("disabled",false)
		 $("#myresult").show();
		 $("#handleResult").show();
		 $("#custmReply").hide();
		 $("#btAssigenHandle").hide();
		}
		$("#btn3").attr("disabled",false);
		$("#btn4").attr("disabled",false);
		//$("#compBtn7").show();
		
	}
	if(r.length>1){
		$("#btn2").attr("disabled",true);
		$("#btn3").attr("disabled",true);
		$("#btn5").attr("disabled",true);
	}
}

function initSearchHandler(){
	var addressSuggest = $("input#comp_operator_username").bsSuggest({
	    url: "./../search.app?type=allEmp&keyword=",
	    showHeader: true,
//	    allowNoKeyword: false,
	    keyField: 'cname',
	    getDataMethod: "url",
	    delayUntilKeyup: true,
//	    effectiveFields: ["deptName","position","cname","workState","workTimes"],
	    effectiveFields: ["deptName","position","cname"],
	    effectiveFieldsAlias: {
	    	deptName:"部门",
	    	position:"岗位",
	    	cname:"姓名",
//	    	workState:"状态",
//	    	workTimes:"派工次数"
	    },
	    fnPreprocessKeyword: function(keyword) {
    		//请求数据前，对输入关键字作预处理
    	    return encodeURI(encodeURI(keyword));
	    },
	    processData: function (json) {    // url 获取数据时，对数据的处理，作为 getData 的回调函数
	    	var index, len, data = {value: []};
	        if (!json || !json.result || json.result.length === 0) {
	            return false;
	        }

	        len = json.result.length;

	        for (index = 0; index < len; index++) {
//	        	if(json.result[index][6] == '0'){
//	        		json.result[index][6] = '空闲中'
//	        	}else if(json.result[index][6] == '1'){
//	        		json.result[index][6] = '工作中'
//	        	}else{
//	        		json.result[index][6] = ""
//	        	}
	            data.value.push({
	                "userName": json.result[index][0],
	                "cname": json.result[index][1],
	                "deptName": json.result[index][3],
	                "position": json.result[index][5],
//	                "workState": json.result[index][6],
//	                "workTimes": json.result[index][7]
	                
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
		    $("#comp_operator_id").val(data.userName);
		    $("#comp_operator_username").val(data.cname);
		}).on("onUnsetSelectValue",	
		function(e) {
		    console.log("onUnsetSelectValue")
		});
	
}

//适应权限按钮所写
function openCompOptionButton(type,obj){
	clear_compInfo();
	$('#myForm2').validationEngine('hide');
	debugger;
	var urlmethod = "";
	var rpt_id=obj.mtn_id;
	//派工按钮和查看
	urlmethod = "method=compView&assignId="+rpt_id;
	var url="./../assigenwork/assignlist.app?"+urlmethod;
	loadData(url,type);
}
//清除数据
function clear_compInfo()
{
		$("#comp_id").val("");
		$("#rpt_id").val("");
		$("#rpt_name").val("");
		$("#in_call").val("");
		$("#addres").val("");
		
		$("#createTime").val("");
		$("#comp_createTime").val("");
		$("#comp_detail").val("");
		$("#comp_emergency").val("");
		$("#comp_status").val("");
		$("#comp_operator_id").val("");
		$("#comp_operator_username").val("");
		$("#comp_process").val("");
		$("#comp_result").val("");
		$("#comp_solve").val("");
		$("#comp_degree").val("");
		$("#comp_reply").val("");
		$("#comp_result").val("");
		//alert(list[1].comp_finish_time);
		$("#comp_finish_time").val("");
		$("#mtn_id").val("");


}





function openCompButton(type){
	
	$('#myForm2').validationEngine('hide');
	
	//alert("aa");
	showButton();
	var isOpen = false;
	selections = getIdSelections();
	var urlmethod = "";
	
	var length=selections.length;
	if(type==4){
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
		if(type==3){
			disableEdit();
		}else if(type==1){
			$("#compBtn5").show();
			$("#compBtn6").show()
			$("#compBtn7").show()
			ableEdit();
		}
		
		//派工按钮和查看
		if(type==4){
			//删除
			urlmethod = "method=compDelete";
			deletaDispatch(urlmethod);
		}else if(type==3 || type==1){
			var rpt_id=selections[0];
			urlmethod = "method=compView&assignId="+rpt_id;
			var url="./../assigenwork/assignlist.app?"+urlmethod;
			loadData(url,type);
			if(type==3)
			{
				$("#btAssigenHandle").hide();
				
			}
		}else if(type==8)//处理
		{
			
			var rpt_id=selections[0];
			urlmethod = "method=compView&assignId="+rpt_id;
			var url="./../assigenwork/assignlist.app?"+urlmethod;
			$("#btAssigenHandle").show();
			loadData(url,type);
			
			$("#compBtn5").hide();
			$("#compBtn6").hide()
			$("#compBtn7").hide()
		}else{
			openSaveButton(type);
		}
	}
}

//查看处理
function disableEdit()
{
	
	 $('#comp_detail').attr("disabled","true"); 
	  $('#comp_emergency').attr("disabled","true");
	  $('#comp_status').attr("disabled","true"); 
	  $('#comp_operator_username').attr("disabled","true"); 
	  $('#comp_process').attr("disabled","true"); 
	  $('#myresult').attr("disabled","true"); 
	  $('#handleResult').attr("disabled","true"); 
	  $('#comp_solve').attr("disabled","true"); 
	  $('#comp_degree').attr("disabled","true");
	  $('#comp_emergency').attr("disabled","true");
	  $('#comp_reply').attr("disabled","true");
	

}
//可编辑效果
function ableEdit()
{
	 $('#comp_detail').attr("disabled",false); 
	  $('#comp_emergency').attr("disabled",false);
	  $('#comp_status').attr("disabled",false); 
	  $('#comp_operator_username').attr("disabled",false); 
	  $('#comp_process').attr("disabled",false); 
	  $('#myresult').attr("disabled",false); 
	  $('#handleResult').attr("disabled",false); 
	  $('#comp_solve').attr("disabled",false); 
	  $('#comp_degree').attr("disabled",false);
	  $('#comp_emergency').attr("disabled",false);
	 

}
function openCompHandleButton()
{
	var flag = $('#myForm2').validationEngine('validate');
	if(flag){
	var addJson = getDataForm2();
	$.ajax({
        type: "post",
        url: "./../assigenwork/assignlist.app?method=handle",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {
			layer.msg('操作成功！',{
				time: 2000
			}, function(){
				$('#compDataInfo').bootstrapTable('refresh');
				$('#myModal3').modal('hide');
			});
//			closeLayer();
    		
        },
        failure:function(xhr,msg)
        {
			layer.msg('操作失败！请联系技术人员。',{
				time:2000
			},function(){
				$('#myModal3').modal('hide');
			})
        }
    });
  }
}

function getDataForm2()
{

	//alert($("#addres").val());
	var addJson = {
			createTime:$("#createTime").val(),
			in_call:$("#in_call").val(),
			addres:$("#addres").val(),
			owner_type:$("#owner_type").val(),
			event_source:$("#event_source").val(),
			createTime:$("#createTime").val(),
			comp_createTime:$("#comp_createTime").val(),
			//报修
			mtn_detail:$("#mtn_detail").val(),
			mtn_emergency:$("#mtn_emergency").val(),
			dispatch_status:$("#dispatch_status").val(),
			comp_reply:$("#comp_reply").val(),
			//投诉
			mtn_id:$("#mtn_id").val(),
			comp_operator_id:$("#comp_operator_id").val(),
			comp_emergency:$("#comp_emergency").val(),
			comp_detail:$("#comp_detail").val(),
			comp_status:$("#comp_status").val(),
			comp_emergency:$("#comp_emergency").val(),
			comp_detail:$("#comp_detail").val()
		};
	return addJson;
	

}

/**删除方法
 * 
 * @param urlmethod
 */
function deletaDispatch(urlmethod){

	layer.confirm("您确定要删除所选信息吗?",{
		skin: 'layui-layer-molv', 
		btn: ['确定','取消']
	},function(){
        var url="./../assigenwork/assignlist.app?"+urlmethod;
		var deleteIds = JSON.stringify(selections);
		//alert(url);
		$.post(url,
				{assignId:deleteIds},
   		        function(data){
						layer.alert(data, {
						  skin: 'layui-layer-molv' //样式类名
						  ,closeBtn: 0
						}, function(){
							var locationUrl="./../assignwork/CompWorkList.jsp";
							window.location.href=locationUrl;
						});
					});
	},function(){
		return;
	});

}


/**
 * 派工按钮，分派人
 * 现场测试确认
 * 消单
 */
function openSaveButton(index){
var flag = $('#myForm2').validationEngine('validate');
  if(flag&&index=="5")
  {
	var addJson = getDataForm(index);
	excuteForm(addJson);
   }
  else if(flag&&index=="6")
  {
	var addJson = getDataForm(index);
	excuteForm(addJson);
   }
  if(flag&&index=="7")
  {
	var addJson = getDataForm(index);
	excuteForm(addJson);
  }
}

//独立出后台提交部分
function excuteForm(addJson)
{
	$.ajax({
        type: "post",
        url: "./../assigenwork/assignlist.app?method=CompSave",
        data: addJson,
        dataType: "json",
		async : false,
        success: function(data)
        {
        	layer.msg('操作成功！',{
				time: 2000
			}, function(){
				$('#compDataInfo').bootstrapTable('refresh');
				$('#myModal3').modal('hide');
				window.close();
			});
//			closeLayer();
    		
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！请联系技术人员。',{
				time:2000
			},function(){
				$('#myModal3').modal('hide');
			})
        }
    });

}



/**取数据
 * 
 * @param ndex
 * @returns {___anonymous5480_5481}
 */
function getDataForm(index){
	//加载不同的数据
	var addJson = {};
	//派工
	if(index==5){
		addJson = {
			mtn_id:$("#mtn_id").val(),
			comp_operator_id:$("#comp_operator_id").val(),
			comp_operator_username:$("#comp_operator_username").val(),
			comp_emergency:$("#comp_emergency").val(),
			comp_detail:$("#comp_detail").val(),
			comp_reply:$("#comp_reply").val(),
			type:index
		};
	}else if(index==6){
		//处理
		addJson = {
			mtn_id:$("#mtn_id").val(),
			comp_result:$("#comp_result").val(),
			comp_process:$("#comp_process").val(),
			comp_solve:$("#comp_solve").val(),
			comp_degree:$("#comp_degree").val(),
			comp_detail:$("#comp_detail").val(),
			comp_emergency:$("#comp_emergency").val(),
			comp_result:$("#comp_result").val(),
			type:index
		};
	}else if(index==7){
		//消单
		addJson = {
			mtn_id:$("#mtn_id").val(),
			type:index
		};
	}
	
	return addJson;
}

function hiddenButton(){
	$('#compBtn5').attr("disabled","true"); 
	$('#compBtn6').attr("disabled","true");
	$('#compBtn7').attr("disabled","true");
}


function showButton(){
	$('#compBtn5').removeAttr("disabled");
	$('#compBtn6').removeAttr("disabled");
	$('#compBtn7').removeAttr("disabled");
}

function getIdSelections() {
    return $.map($('#compDataInfo').bootstrapTable('getSelections'), function (row) {
        return row.mtn_id
    });
}

/**派工，查看加载数据
 * 
 * @param url
 */
function loadData(url,type){
	//$('#myModal3').modal();
	var grapIframe="";
	var processInstanceId="";
	//showButton();
	$.post(url,function(data){
   		var list = eval(data);
   		var comp_status = list[1].comp_status;
   		var comp_emergency=list[1].comp_emergency;//投诉级别
   		if(comp_emergency==1)
   			{
   		   $('#custmReply').hide();
   			}else
   			{
   			 $('#opernameLabe').hide();
   			 $('#opernameDiv').hide();
   			}
   		if(type==3){
			//hiddenButton();
		}else{
			$('#comp_operator_username').show();
			if(comp_status==0){
	   			//$('#compBtn7').attr("disabled","true");
	   			//$('#compBtn6').attr("disabled","true");
	   		}else if(comp_status >= 3){
	   			//$('#compBtn5').attr("disabled","true");
	   			//$('#compBtn6').attr("disabled","true");
	   			//$("#compBtn7").attr("disabled","true");
	   			//$("#btAssigenHandle").attr("disabled","true");
	   		}else if(type==8&&comp_emergency!="0")
	   		{
	   			$("#btAssigenHandle").hide();
	   		}else{
	   			//showButton();
	   			$('#compBtn5').attr("disabled","true");
	   		}
		}
        if(comp_status==0&&comp_emergency==1)
        	{
        	$('#custmReply').hide();
        	$('#comp_reply').attr("disabled",false);	
	   		 
	   		  $('#myresult').hide();
	   		  $('#handleResult').hide();
	   		  
        	}
        if(comp_status==1&&comp_emergency==1)
    	{
         
          $('#comp_reply').attr("disabled","true");	
    	  $('#comp_emergency').attr("disabled","true");	
    	  $('#comp_status').attr("disabled","true");
    	  $('#comp_result').attr("disabled",false);
   		  $('#custmReply').hide();
   		  $('#myresult').show();
   		  $('#handleResult').show();
   		  
    	}
        if((comp_status==2||comp_status==3)&&comp_emergency==1)
    	{
        	
          $('#comp_reply').attr("disabled","true");	
    	  $('#comp_emergency').attr("disabled","true");	
    	  $('#comp_status').attr("disabled","true");
    	  $('#comp_result').attr("disabled","true");
    	  
    	  $('#comp_solve').attr("disabled","true");
    	  $('#comp_degree').attr("disabled","true");
    	  $('#comp_detail').attr("disabled","true");
    	  $('#handleResult').attr("disabled","true");
   		
   		  $('#myresult').show();
   		  $('#handleResult').show();
   		  
    	}
        
        if(comp_status==0&&comp_emergency==0)//咨询建议一般情况
    	{
    	
          $('#comp_reply').attr("disabled",false);	
    	  $('#comp_emergency').attr("disabled","true");	
    	$("#comp_detail").attr("disabled",false);
    	  $('#comp_status').attr("disabled","true");
    	  $('#comp_result').attr("disabled",false);
   		  $('#custmReply').show();
   		  $('#myresult').hide();
   		  $('#handleResult').show();
   		  
    	}
   		nameFormatCName("createby",list[0].createby);
   		$("#comp_id").val(list[1].comp_id);
   		$("#mtn_id").val(list[0].rpt_id);
   		$("#rpt_name").val(list[0].rpt_name);
   		$("#in_call").val(list[0].in_call);
   		$("#addres").val(list[0].addres);
   		
   		$("#createTime").val(getNowFormatDate(false,list[0].createTime));
   		$("#comp_createTime").val(getNowFormatDate(false,list[1].comp_createTime));
   		$("#comp_detail").val(list[1].comp_detail);
   		$("#comp_emergency").val(list[1].comp_emergency);
   		$("#comp_status").val(comp_status);
   		$("#comp_operator_id").val(list[1].comp_operator_id);
   		$("#comp_operator_username").val(list[2].comp_operator_name);
   		debugger;
   		var operaterName=list[2].comp_operator_name;
   		if(list[1].comp_emergency=='0')
   		{
   		  $('#opernameLabe').hide();
			 $('#opernameDiv').hide();
   		}else if(list[1].comp_emergency=='1'){
   		if(operaterName!=undefined){
   		if(list[2].comp_operator_name.length>0&&comp_emergency==0)
   			{
   			
   		     $('#opernameLabe').show();
			 $('#opernameDiv').show();
   			}
   		}else
   		{
   			 $('#opernameLabe').show();
			 $('#opernameDiv').show();
   			
   		}
   		}
   		$("#comp_process").val(list[1].comp_process);
   		$("#comp_result").val(list[1].comp_result);
   		$("#comp_solve").val(list[1].comp_solve);
   		$("#comp_degree").val(list[1].comp_degree);
   		$("#comp_reply").val(list[1].comp_reply);
   		$("#comp_result").val(list[1].comp_result);
   		//alert(list[1].comp_finish_time);
   		$("#comp_finish_time").val(getNowFormatDate(false,list[1].comp_finish_time));
   		$("#mtn_id").val(list[1].mtn_id);
   		processInstanceId = list[0].processInstanceId;
   		if(processInstanceId==null || processInstanceId=='' || processInstanceId==undefined || processInstanceId=='undefined'){
   		
   		}else{
   			grapIframe="./../graph/graphProcessDefinition.app?processDefinitionId="+processInstanceId+"&numb="+generateMixed(4);
   		}
   		
   		$("#grapIframe").attr("src",grapIframe); 
	});
	
	
	$('#myModal3').modal();
	
}


var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

function generateMixed(n) {
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()*35);
        res += chars[id];
    }
    return res;
}

//人的中文名字显示
function nameFormatCName(cName,value) {
	//alert(value);
	var addJson = {
		userName:value
	};
	$.ajax({
      type: "post",
      url: "./../assigenwork/assignlist.app?method=nameformat",
      data: addJson,
      dataType: "json",
		async : false,
      success: function(data)
      {
      	$("#"+cName).val(data);
      },
      failure:function(xhr,msg)
      {
			
      }
  });
}

//初始化下拉框
function initDrops(){
	var url="./../assigenwork/assignlist.app?method=initDropAll";
    $.post(url,
	      function(data){
    	
			 var list = eval(data);
			 //alert(list);
			 for(var j=0;j<list.length;j++){
				 //alert(list[j].main_event_source+"==="+list[j].main_event_type);
				 //debugger;
				 var detailList = list[j];
				 var code = detailList[0];
				 //紧急程度
				if(code=='main_mtn_emergency'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#comp_emergency");
				}
	            
				//comp_status  派工单状态
				if(code=='main_mtn_dispatch_status'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#comp_status");
				}
				
				//comp_status  派工单状态
				if(code=='main_comp_solve'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#comp_solve");
				}
				
				//comp_status  派工单状态
				if(code=='main_comp_degree'){
					$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#comp_degree");
				}
	        };
		}
    );
	//return rtnValue;
}

//时间显示
function timeFormatCreateTime(value, row, index) {
	var timeFormant = "-";
	if(null == value||value==''){
		
	}else{
		timeFormant = getNowFormatDate(false,value);
	}
	return [timeFormant].join('');
}

function getNowFormatDate(flag,vardate) {
	
	if((vardate == undefined) || (!flag && (null == vardate || vardate ==''))){
		return "-";
	}
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

/**操作成功后2秒自动关闭
 * 
 * @param layerName
 */
function closeLayer(){
	setTimeout(function(){//IE6、7不会提示关闭  
		$('#myModal3').modal('hide'); //执行关闭
		//buttonreresh();
	}, 2000);
}
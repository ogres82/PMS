selections = [];
var loginUserName="";
var loginUserCname="";
$(function () {
	initPermissions();
	initBtnEvent();
	init();
	initDrops();
	initSearch();//初始化搜索框
	//定位表格查询框
	$(".search").css({
		'position':'fixed',
		'right':'115px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","搜索");
	$('#myForm1').validationEngine();
});

function initPermissions(){
	toolbarBtnInit(); //初始化工具条按钮
}

function initBtnEvent(){
	var urlmethod = "";
	for(var i=0;i<btnIdArray.length;i++){
		//新增
		if(btnIdArray[i]=="btn_add"){
			$("#btn_add").bind('click',function(){
				$('#myForm1').validationEngine('hide');
				ZYFILE.uploadFile = [];
		    	ZYFILE.lastUploadFile = [];          // 上一次选择的文件数组，方便继续上传使用
		    	ZYFILE.perUploadFile = [];      
		    	ZYFILE.fileInput = null;
				$(".contractFile").hide();
		    	$('#btContractAdd').show();
		    	$("#uploadFile").show();
    			clearForm();
    			$("#myModalTitle").html("新增");
    			$('#myModal1').modal({backdrop: 'static', keyboard: false});
    		
			});
		}
		//删除
		if(btnIdArray[i]=="btn_del"){
			$("#btn_del").bind('click',function(){
				$('#myForm1').validationEngine('hide');
				selections = getIdSelections();
				
				urlmethod = "method=deleteContract";

    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="./../contract/contractInfo.app?"+urlmethod;
    				$.post(url,
       						{contractId:selections+""},
       	    		        function(data){
       							layer.msg(eval(data),{
       			    				time: 2000
       			    			}, function(){
       			    				$('#contractInfo').bootstrapTable('refresh');
       			    			});
       						});
    			},function(){
    				return;
    			});
			});
		}
	}
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
		$('#contractInfo').bootstrapTable({
			url: './../contract/contractInfo.app?method=list',         //请求后台的URL（*）
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
            	field: 'contractId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'contractCode',
            	title: '合同编号'
            }, {
                field: 'contractName',
                title: '合同名称'
            }, {
                field: 'contractTypeName',
                title: '合同类型'
            }, {
            	field: 'contractDetail',
                title: '合同简述'
            }, {
            	field: 'contractPbName',
                title: '乙方名称'
            }, {
                field: 'contractPbLinker',
                visible:false,
                title: '乙方联系人'
            }, {
                field: 'contractPbPhone',
                visible:false,
                title: '乙方联系方式'
            }, {
                field: 'contractSignDate',
                title: '签订日期',
                formatter: dateFormate
            }, {
                field: 'contractEndDate',
                title: '结束日期',
                formatter: dateFormate
            }, {
            	field: 'contractPeriod',
            	visible:false,
            	title: '合同期限'
            }, {
            	field: 'contractPrice',
            	title: '合同金额(万)'
            }, {
            	field: 'contractStatusName',
            	title: '合同状态'
            }, {
            	field: 'remark',
            	visible:false,
            	title: '备注'
            }, {
            	field: 'operate',
                title: '操作',
                align: 'center',
                width: '10%',
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
        		$("#btn_del").attr("disabled",false);
            },
            onUncheckAll: function(rows){
        		$("#btn_del").attr("disabled",true);
            },
            onLoadSuccess: function(rows){
        		$("#btn_del").attr("disabled",true);
            }
		});
	};
	function operateFormatter(value, row, index) {
		return tableBtnInit();
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
		var r = $('#contractInfo').bootstrapTable('getSelections');
    	if(r.length==0){
    		$("#btn_del").attr("disabled",true);
    	}
    	if(r.length==1){
    		$("#btn_del").attr("disabled",false);
    	}
	}
	
	//初始化下拉框
	function initDrops(){
		var url="./../contract/contractInfo.app?method=initDropAll";
	    $.post(url,
		      function(data){
	    	
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
					 
					 var detailList = list[j];
					 var code = detailList[0];
					 //员工状态
					if(code=='contract_type'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#contractType");
					}
					if(code=='contract_status'){
						$("<option value='"+detailList[1]+"'>"+detailList[2]+"</option>").appendTo("#contractStatus");
					}
		                 
		        };
			}
	    );
		
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
        return $.map($('#contractInfo').bootstrapTable('getSelections'), function (row) {
            return row.contractId;
        });
    }
    
    function getNameSelections() {
        return $.map($('#contractInfo').bootstrapTable('getSelections'), function (row) {
            return row.empName;
        });
    }
    /**
     * 保存操作
     */
    function openSaveButton(){
    	var start = $("#contractSignDate").val();
    	var end = $("#contractEndDate").val();
    	
    	if(start>end && end!=""){
    		layer.msg('结束日期不能小于签订日期！',{time: 2000});
    		return;
    	}
    	var flag = $('#myForm1').validationEngine('validate');
    	if(flag){
        	var addJson = getDataForm();
        	$.ajax({
                type: "post",
                url: "./../contract/contractInfo.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	layer.msg('操作成功！',{
        				time: 2000
        			}, function(){
        				$('#contractInfo').bootstrapTable('refresh');
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
    			contractId:$("#contractId").val(),
    			contractCode:$("#contractCode").val(),
    			contractType:$("#contractType").val(),
    			contractName:$("#contractName").val(),
    			contractDetail:$("#contractDetail").val(),
    			contractPbName:$("#contractPbName").val(),
    			contractPbLinker:$("#contractPbLinker").val(),
    			contractPbPhone:$("#contractPbPhone").val(),
    			contractSignDate:$("#contractSignDate").val(),
    			contractEndDate:$("#contractEndDate").val(),
    			contractPeriod:$("#contractPeriod").val(),
    			contractPrice:$("#contractPrice").val(),
    			contractStatus:$("#contractStatus").val(),
    			fileIds:$("#fileIds").val(),
    			remark:$("#remark").val()
    		};
    	return addJson;
    }
    
    /**
     * 获取项目根路径：http://localhost:8083/proj
     */
    function getRootPath(){
        //获取当前网址，如： http://localhost:8083/proj/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： proj/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPath = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/proj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);
        return(localhostPath + projectName);
    }
    
    //下载文件
    function downloadFile(obj){
    	var fileId = $(obj).attr("fileId");
    	var url="./../contract/contractInfo.app?method=downloadContractFile&fileId="+fileId;
    	window.location.href = url;
    }
    //删除文件
    function delFile(obj){
    	layer.confirm("您确定要删除所选信息吗?",{
			skin: 'layui-layer-molv', 
			btn: ['确定','取消']
		},function(){
			var fileId = $(obj).attr("fileId");
	    	 url="./../contract/contractInfo.app?method=delContractFile&fileId="+fileId;
	    	 $.post(url,function(data){
	    		 var r = eval(data);
	    		 if(r=="failed"){
	    			 layer.msg("删除失败",{time: 2000});
	    		 }else{
	    			 layer.msg("删除成功",{time: 2000}, function(){
	    				$(obj).parent().remove();
	    			 });
	    		 }
	    	 });
		},function(){
			return;
		})
    }
    
    //清空表单
    function clearForm(){
    	$("#contractId").val("");
    	$("#contractCode").val("");
		$("#contractType").val("");
		$("#contractName").val("");
		$("#contractDetail").val("");
		$("#contractPbName").val("");
		$("#contractPbLinker").val("");
		$("#contractPbPhone").val("");
		$("#contractSignDate").val("");
		$("#contractEndDate").val("");
		$("#contractPeriod").val("");
		$("#contractPrice").val("");
		$("#contractStatus").val("");
		$("#remark").val("");
		$("#fileIds").val("");
		$("#preview").html("");//清空文件预览
		$("#fileIds").val("");
		$("#uploadInf").html("");//清空上传成功提示
		$("#status_info").html("选中0张文件，共0B。");
		$("#fileImage").val(null);
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
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
    
    
    /*搜索框初始化*/
    function initSearch(){
    	var addressSuggest = $("input#addres").bsSuggest({
    	    url: "./../contract/contractInfo.app?type=owner&keyword=",
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
    
    
function initUpload(){
	// 初始化插件
	$("#demo").zyUpload({
		width            :   "800px",                 // 宽度
		//height           :   "450px",                 // 宽度
		itemWidth        :   "120px",                 // 文件项的宽度
		itemHeight       :   "100px",                 // 文件项的高度
		url              :   "./../contract/contractInfo.app?method=upload",  // 上传文件的路径
		multiple         :   true,                    // 是否可以多个文件上传
		dragDrop         :   true,                    // 是否可以拖动上传文件
		del              :   true,                    // 是否可以删除文件
		finishDel        :   false,  				  // 是否在上传文件完成后删除预览
		
		//外部获得的回调接口
		onSelect: function(files, allFiles){                    // 选择文件的回调方法
			console.info("当前选择了以下文件：");
			console.info(files);
			console.info("之前没上传的文件：");
			console.info(allFiles);
		},
		onDelete: function(file, surplusFiles){                     // 删除一个文件的回调方法
			console.info("当前删除了此文件：");
			console.info(file);
			console.info("当前剩余的文件：");
			console.info(surplusFiles);
		}, 
		onSuccess: function(file, response){                    // 文件上传成功的回调方法
			debugger;
			console.info("----------------------"+response);
			var fileIds = $("#fileIds").val();
			if(fileIds!=null && fileIds !=""){
				
			}
		},
		onFailure: function(file){                    // 文件上传失败的回调方法
			console.info("此文件上传失败：");
			console.info(file);
		},
		onComplete: function(responseInfo){           // 上传完成的回调方法
			console.info("文件上传完成");
			console.info(responseInfo);
		}
	});
}
    

//查看和编辑
function editOrCheck(obj,type){
	$('#myForm1').validationEngine('hide');
	if(type==1){
		$('#btContractAdd').hide();
		$("#myModalTitle").html("查看");
		$("#uploadFile").hide();
	}else{
		$('#btContractAdd').show();
		$("#myModalTitle").html("编辑");
		$("#uploadFile").show();
		ZYFILE.uploadFile = [];
    	ZYFILE.lastUploadFile = [];          // 上一次选择的文件数组，方便继续上传使用
    	ZYFILE.perUploadFile = [];      
    	ZYFILE.fileInput = null;
    	clearForm();
	}

	$(".contractFile").show();
	$("#contractId").val(obj.contractId);
	$("#contractCode").val(obj.contractCode);
	$("#contractType").val(obj.contractType);
	$("#contractName").val(obj.contractName);
	$("#contractDetail").val(obj.contractDetail);
	$("#contractPbName").val(obj.contractPbName);
	$("#contractPbLinker").val(obj.contractPbLinker);
	$("#contractPbPhone").val(obj.contractPbPhone);
	$("#contractSignDate").val(getNowFormatDate(false,obj.contractSignDate));
	$("#contractEndDate").val(getNowFormatDate(false,obj.contractEndDate));
	$("#contractPeriod").val(obj.contractPeriod);
	$("#contractPrice").val(obj.contractPrice);
	$("#contractStatus").val(obj.contractStatus);
	$("#remark").val(obj.remark);
		
	    url="./../contract/contractInfo.app?method=searchContractFile&contractId="+obj.contractId;
		$.post(url,function(data){
			
			var list = eval(data);
			if(list!=null){
				
				var htmlStr = "<ul class='folder-list' style='padding: 0 20px'>";
				for(var i = 0;i<list.length;i++){
					var del = "";
					if(type==2){
						del = "<span onclick='delFile(this)' fileId='"+list[i].fileId+"' class='delFile'><i class='fa fa-trash' style='color:red;font-size:15px;'></i>删除</span>";
					}
					htmlStr += "<li>"+
					"<a onclick='downloadFile(this)' fileId='"+list[i].fileId+"'>" +
					"<i class='fa fa-file'></i> "+list[i].fileName + "</a>" +
					"<span style='margin-left:20px'>上传时间："+getNowFormatDate(false,list[i].fileUploadTime)+"</span> "+
					del+
					"</li>";
				}
				htmlStr += "</ul>";
				$("#contractFile").html(htmlStr);
			}else{
				$(".contractFile").hide();
				$("#contractFile").html("");
			}
		});
	$('#myModal1').modal();

}
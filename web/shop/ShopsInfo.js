selections = [];
$(function () {
	initPermissions();
	initBtnEvent();
	init();
	//定位表格查询框
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","搜索");
	$('#myForm1').validationEngine();
	initFileInput("logo", "./../decorate/decoCompany.app?method=inputFile&folder=logo");
	qualificationInput("qualification", "./../decorate/decoCompany.app?method=inputFile&folder=qualify");
	imgManageInput("qualification", "./../decorate/decoCompany.app?method=inputFile&folder=img");
	$('#myForm2').validationEngine();
});

function viewReport(obj){
	parent.openTab(obj);
}

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
		    	$('#btDecCompanyAdd').show();
		    	$("#uploadLogoDiv").show();
				$("#uploadQuaDiv").show();
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
				
				urlmethod = "method=deleteDecInfo";

    			layer.confirm("您确定要删除所选信息吗?",{
    				skin: 'layui-layer-molv', 
    				btn: ['确定','取消']
    			},function(){
    		        var url="./../decorate/decoCompany.app?"+urlmethod;
    				$.post(url,
       						{id:selections+""},
       	    		        function(data){
       							layer.msg(eval(data),{
       			    				time: 2000
       			    			}, function(){
       			    				$('#decCompanyInfo').bootstrapTable('refresh');
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
};

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#decCompanyInfo').bootstrapTable({
			url: './../decorate/decoCompany.app?method=list',         //请求后台的URL（*）
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
            	field: 'id',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'name',
            	title: '公司名称'
            }, {
                field: 'summary',
                title: '公司简介',
                formatter:textOverflow

            }, {
                field: 'telephone',
                title: '联系电话'
            }, {
            	field: 'logoUrl',
            	visible:false,
                title: '图片地址'
            }, {
            	field: 'editorId',
            	visible:false,
            	title: '编辑人ID'
            }, {
            	field: 'editorName',
                title: '编辑人'
            }, {
            	field: 'checker',
            	align: 'center',
                title: '查看人次',
                events: checkerEvents,
                formatter:clickFormatter
            }, {
            	field: 'consultor',
            	align: 'center',
                title: '咨询人次',
                events: consultorEvents,
                formatter:clickFormatter
            }, {
            	field: 'operate',
                title: '操作',
                align: 'center',
                width: '15%',
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
        		/*$("a[id='a_goods']").each(function(i) {
//        			$(this).attr("url","ShopsGoods.jsp");
        			$(this).attr("url","ShopsGoods.jsp?companyId="+rows.rows[i].id+"&companyName="+rows.rows[i].name);
        			$(this).attr('onclick', 'viewReport(this)');
    			});*/
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
		'click #a_goods': function (e, value, row, index) {
			location.href = getRootPath()+"/shop/ShopsGoods.jsp?companyId="+row.id+"&companyName="+row.name;
	    }
    };
	oTableInit.queryParams = function (params) {
	    var tplant = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search   //表格搜索框的值
	    };
	    return tplant;
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
	var r = $('#decCompanyInfo').bootstrapTable('getSelections');
	if(r.length==0){
		$("#btn_del").attr("disabled",true);
	}
	if(r.length==1){
		$("#btn_del").attr("disabled",false);
	}
}

//操作列的显示风格
function clickFormatter(value, row, index) {
    return '<a class="like" style="text-decoration:underline" href="javascript:void(0)">'+value+'</a>';
}

function textOverflow(value,row,index){
	return '<div class="text-nowrap" style="text-overflow:ellipsis;overflow:hidden;width:300px;">'+value+'</div>';
}
//操作列的事件
window.checkerEvents = {
    'click .like': function (e, value, row, index) {
    	$('.checkerInfo').show();
    	var id = row.id;
    	$("#checkId").val(id);
    	var element = "checkerInfo";
    	var url = './../decorate/decoCompany.app?method=checkerInfo';
    	new checkerInfo().Init(element,url);
    	$('#checkerInfo').bootstrapTable('refresh');
    	$('.consultorInfo').hide();
    	$("#myModal2").modal({backdrop: 'static', keyboard: false});
    	$("#myModalTitle2").html("查看人");
    }
};
//操作列的事件
window.consultorEvents = {
    'click .like': function (e, value, row, index) {
    	$('.consultorInfo').show();
    	var id = row.id;
    	$("#checkId").val(id);
    	var element = "consultorInfo";
    	var url = './../decorate/decoCompany.app?method=consultorInfo';
    	var tobj = new consultorInfo();
    	tobj.Init(element,url);
    	$('#consultorInfo').bootstrapTable('refresh');
    	$('.checkerInfo').hide();
    	$("#myModal2").modal({backdrop: 'static', keyboard: false});
    	$("#myModalTitle2").html("咨询人");
    }
};

var checkerInfo = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (element,url){
		$('#checkerInfo').bootstrapTable({
			url: url,         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: false,
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
                field: 'state',
                checkbox: true,
            }, {
            	field: 'ownerId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'roomId',
            	visible:false,
            	title: 'roomId'
            }, {
            	field: 'ownerName',
            	title: '业主姓名'
            }, {
            	field: 'phone',
            	title: '手机号码'
            }, {
            	field: 'num',
            	title: '次数'
            }
            ]
		});
	};
	
	oTableInit.queryParams = function (params) {
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search,
	        id:$("#checkId").val()
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
	
var consultorInfo = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (element,url){
		$('#consultorInfo').bootstrapTable({
			url: url,         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: false,
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
                field: 'state',
                checkbox: true,
            }, {
            	field: 'ownerId',
            	visible:false,
            	title: 'ID'
            }, {
            	field: 'roomId',
            	visible:false,
            	title: 'roomId'
            }, {
            	field: 'ownerName',
            	title: '业主姓名'
            }, {
            	field: 'phone',
            	title: '手机号码'
            }, {
            	field: 'num',
            	title: '次数'
            }
            ]
		});
	};
	
	oTableInit.queryParams = function (params) {
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search,
	        id:$("#checkId").val()
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
    function getIdSelections() {
        return $.map($('#decCompanyInfo').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }
    
    /**
     * 保存操作
     */
    function openSaveButton(){
    	var flag = $('#myForm1').validationEngine('validate');
    	if(flag){
    		var logoPath = $("#logoPath").val();
    		if(logoPath==""){
    			layer.msg('未上传logo！',{time: 2000});
    			return false;
    		}
        	var addJson = getDataForm();
        	
        	$.ajax({
                type: "post",
                url: "./../decorate/decoCompany.app?method=save",
                data: addJson,
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	
                	layer.msg("保存成功",{
        				time: 2000
        			}, function(){
        				$('#decCompanyInfo').bootstrapTable('refresh');
        				$('#myModal1').modal('hide');
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
     * 图片管理保存操作
     */
   function openSaveImgButton(){
	   var flag = $('#myForm2').validationEngine('validate');
   	   if(flag){
		   var imgType = $("#imgType").val();
		   var imgSummary = $("#imgSummary").val();
		   var imgUrl = $("#imgUrl").val();
		   if(imgUrl=="" || imgUrl==null){
			   layer.msg('未上传图片！',{
					time: 2000
				});
			   return false;
		   }
		   var companyId = $("#companyId").val();
		   var addJson = {
			   imgType:imgType,
			   imgSummary:imgSummary,
			   imgUrl:imgUrl,
			   id:companyId
		   }
		   
		   $.ajax({
	           type: "post",
	           url: "./../decorate/decoCompany.app?method=saveImg",
	           data: addJson,
	           dataType: "json",
	   		   async : false,
	           success: function(data)
	           {
	           	
	           	layer.msg("保存成功",{
	   				time: 2000
	   			}, function(){
	   				$('#imgManageModal').modal('hide');
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
    			id:$("#id").val(),
    			name:$("#name").val(),
    			summary:$("#summary").val(),
    			telephone:$("#telephone").val(),
    			logoPath:$("#logoPath").val(),
    			qualifyId:$("#qualifyId").val()
    		};
    	return addJson;
    }
    
    //清空表单
    function clearForm(){
    	$("#id").val("");
		$("#name").val("");
		$("#telephone").val("");
		$("#summary").val("");
		$("#logoPath").val("");
		$("#qualifyId").val("");
		$("#preview").html("");
		$("#viewQualify").html("");
		$("#logo").fileinput('clear');
		$("#qualification").fileinput('clear');
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
    
    
    //初始化fileinput控件（第一次初始化）
    function initFileInput(ctrlName, uploadUrl) {    
        var control = $('#' + ctrlName); 

        $("#logo").fileinput({
            uploadUrl: uploadUrl,
            uploadAsync: false,
            showPreview: false,
            showUpload: true,
            allowedFileExtensions: ['jpg', 'png'],
            maxFileCount: 1,
            elErrorContainer: '#kv-error-2'
        }).on('filebatchpreupload', function(event, data, id, index) {
//            $('#kv-success-2').html('<h4>Upload Status</h4><ul></ul>').hide();
        }).on('filebatchuploadsuccess', function(event, data) {
            
            var msg = eval('('+data.response+')');
            if(msg.status==1){
            	$("#logoPath").val(msg.data);
            	layer.msg("成功",{
            		time: 2000
            	}, function(){
//            		$('#importModal').modal('hide');
            	});
            }else{
            	layer.msg(msg.message,{
            		time: 2000
            	});
            }
        });
    }
    
  //初始化fileinput控件（第一次初始化）
    function qualificationInput(ctrlName, uploadUrl) {    
        var control = $('#' + ctrlName); 

        $("#qualification").fileinput({
            uploadUrl: uploadUrl, // server upload action
            uploadAsync: false,
            showPreview: true,
            showUpload: true,
            allowedFileExtensions: ['jpg', 'png'],
            maxFileCount: 100,
            elErrorContainer: '#kv-error-3'
        }).on('filebatchpreupload', function(event, data, id, index) {
//            $('#kv-success-3').html('<h4>Upload Status</h4><ul></ul>').hide();
        }).on('filebatchuploadsuccess', function(event, data) {
            
            var msg = eval('('+data.response+')');
            if(msg.status==1){
            	$("#qualifyId").val(msg.data);
            	layer.msg("成功",{
            		time: 2000
            	}, function(){
//            		$('#importModal').modal('hide');
            	});
            }else{
            	layer.msg(msg.data,{
            		time: 2000
            	});
            }
        });
    }
    
  //初始化fileinput控件（第一次初始化）
    function imgManageInput(ctrlName, uploadUrl) {    
        var control = $('#' + ctrlName); 

        $("#imgManage").fileinput({
            uploadUrl: uploadUrl, // server upload action
            uploadAsync: false,
            showPreview: true,
            showUpload: true,
            allowedFileExtensions: ['jpg', 'png'],
            maxFileCount: 1,
            elErrorContainer: '#kv-error-4'
        }).on('filebatchpreupload', function(event, data, id, index) {
//            $('#kv-success-4').html('<h4>Upload Status</h4><ul></ul>').hide();
        }).on('filebatchuploadsuccess', function(event, data) {
            
            var msg = eval('('+data.response+')');
            if(msg.status==1){
            	$("#imgUrl").val(msg.data);
            	layer.msg("上传成功",{
            		time: 2000
            	}, function(){
            		
            	});
            }else{
            	layer.msg(msg.message,{
            		time: 2000
            	});
            }
        });
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
    
    function deleteImg(obj){
    	
    		layer.confirm("您确定要删除吗?",{
    			skin: 'layui-layer-molv', 
    			btn: ['确定','取消']
    		},function(index){
    			var url="./../decorate/decoCompany.app?method=delImg";
				$.post(url,
   						{imgId:$(obj).attr("imgId")},
   	    		        function(data){
   							
   						});
    			$(obj).parent().parent().remove();
    			layer.close(index);
    		},function(){
    			return;
    		})
    }
    
    function viewImg(obj){
    	$("#bigImg").attr("src",$(obj).attr("url"));
    	$("#viewImgTitle").html($(obj).attr("name"));
    	$("#viewImg").modal({backdrop: 'static', keyboard: false});
    }
    
    //查看和编辑
    function editOrCheck(obj,type){
    	clearForm();
    	$('#myForm1').validationEngine('hide');
		if(type==3){
			$("#imgType").val("");
			$("#imgSummary").val("");
			$("#imgUrl").val("");
			$("#imgManage").fileinput('clear');
			$("#companyId").val(obj.id);
			
			var url="./../decorate/decoCompany.app?method=getImgs";
			$.post(url,
				{id:obj.id},
		        function(data){
					if(data!="null"){
						var result = eval(data);
						var element = '<div class="form-group form-group-sm "></div>';
						for(var i=0;i<result.length;i++){
							
							var url = getRootPath()+ result[i].imgUrl;
							var path = result[i].imgPath;
							path = path.substring(path.lastIndexOf("/")+1);
							var time = json2Date(result[i].uploadTime);
							element += '<div class="file-box"> '+
							'<div class="file"> '+
							'<a> '+
							' <span class="corner"></span> '+
							
							' <div class="image">'+
							' <a onclick="viewImg(this)" url="'+url+'" name="'+path+'"> '+
							' 	<img alt="image" class="img-responsive" src="'+url+'">'+
							' </a>'+
							' </div>'+
							'<div class="file-name" style="overflow:hidden;">'+
							path+
							' <br/>'+
							'<small>添加时间：'+time+'</small>'+
							'<a class="delimg" imgId="'+result[i].id+'" onclick="deleteImg(this)" style="margin-left:40px;color:#CD6889;font-size:15px;"><i class="fa fa-trash-o"></i></a>'+
							'<br><small>风格:'+result[i].imgType+'</small>'+
							' </div>'+
							' </a>'+
							'</div>'+
							'</div>';
						}
						$("#imgs").html(element);
					}else{
						if($("#imgs").length>0){ 
							$("#imgs").html('<div style="line-height:50px;">无数据<div>');
						}
					}
					
				});
			$('#imgManageModal').modal({backdrop: 'static', keyboard: false});
		}else{
			if(type==1){
				$('#btDecCompanyAdd').hide();
				$("#myModalTitle").html("查看");
				$("#uploadLogoDiv").hide();
				$("#uploadQuaDiv").hide();
			}else{
				$('#btDecCompanyAdd').show();
				$("#myModalTitle").html("编辑");
				$("#uploadLogoDiv").show();
				$("#uploadQuaDiv").show();
			}
			
			$("#id").val(obj.id);
			$("#name").val(obj.name);
			$("#summary").val(obj.summary);
			$("#telephone").val(obj.telephone);
			$("#logoPath").val(obj.logoPath);
			
			$("#preview").html('<img src="'+getRootPath()+obj.logoUrl+'" width="100%" height="100%"/>');
			
			var url="./../decorate/decoCompany.app?method=getQualify";
			$.post(url,
					{id:obj.id},
					function(data){
						if(data!="[]"){
							var obj = eval(data);
							var element = '<div class="form-group form-group-sm "></div>';
							for(var i=0;i<obj.length;i++){
								var path = obj[i].imgPath;
								path = path.substring(path.lastIndexOf("/")+1);
								var durl = getRootPath()+obj[i].imgUrl;
								element += '<div class="file-box"> '+
								'<div class="file"> '+
								'<a> '+
								' <span class="corner"></span> '+
								
								' <div class="image">'+
								' <a onclick="viewImg(this)" url="'+durl+'" name="'+path+'"> '+
								' 	<img alt="image" class="img-responsive" src="'+durl+'">'+
								' </a>'+
								' </div>'+
								'<div class="file-name" style="overflow:hidden;">'+
								path+
								' <br/>'+
//								'<small>添加时间：'+time+'</small>'+
//								'<a class="delimg" imgId="'+obj[i].id+'" onclick="deleteImg(this)" style="margin-left:40px;color:#CD6889;font-size:15px;"><i class="fa fa-trash-o"></i></a>'+
								' </div>'+
								' </a>'+
								'</div>'+
								'</div>';
							}
							$("#viewQualify").html(element);
						}else{
							$("#viewQualify").html('<div style="line-height:40px;">无数据<div>');
						}
						
					});
			
			$('#myModal1').modal({backdrop: 'static', keyboard: false});
			
		}
	
	}
    
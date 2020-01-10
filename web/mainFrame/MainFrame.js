var menu = "";
var currentUrl = "";
$(function(){
	$.ajaxSetup({   
        async : false  
	}); 
	//固定顶部
	//fixTop();
	initMenu();
	initEvents();
	addValidateRule();//增加校验规则
	$('#myForm1').validationEngine();
	$('#msgForm').validationEngine();
	$("[data-toggle='tooltip']").tooltip();
	initUnRead();
	initFileInput("./../mainFrame/mainFrameInfo.app?method=inputFile&folder=wg_user");
});

function initMenu(){
	loadUrls("");
	$("#side-menu").append(getMenuHtml(menu,""));
	$(".navbar-minimalize").click(function(){
		;$("#nav_logo").toggle();
	});
}

function addValidateRule(){
	var obj = $.validationEngineLanguage.allRules;
	obj["checkPwdIndex"] = {
			"url": "./../mainFrame/mainFrameInfo.app",
			// you may want to pass extra data on the ajax call
			"extraData": "method=checkPwd",
			// if you provide an "alertTextOk", it will show as a green prompt when the field validates
			"alertTextOk": "* 验证通过",
			"alertText": "* 密码不正确"
			//"alertTextLoad": "* 正在确认帐号名称是否有其他人使用，请稍等。"
	};
	obj["checkRePwd"] = {
			 "func": function(field,rules,i,options){
		            return (field.val()==$("#newPassword").val()) ? true : false;
		        },
	         "alertText": "* 两次密码不一致"
	};
}

function initEvents(){
	
	//退出
	$("#exit").click(function(){
		layer.confirm("您确定要退出吗?",{
			skin: 'layui-layer-molv', 
			btn: ['确定','取消']
		},function(){
			var url='./../mainFrame/mainFrameInfo.app?method=exit';
			$.post(url);
			window.location.href = currentUrl+"/security_logout_"; //退出系统
		},function(){
			return;
		})
	});
}


function fixTop(){
	$(".navbar-static-top").removeClass("navbar-static-top").addClass("navbar-fixed-top");
	$("body").removeClass("boxed-layout");
	$("body").addClass("fixed-nav");
	$("#boxedlayout").prop("checked", !1);
	localStorageSupport && localStorage.setItem("boxedlayout", "off"); 
	localStorageSupport && localStorage.setItem("fixednavbar", "on");
}

function loadUrls(parentId){
	 var url='./../mainFrame/mainFrameInfo.app?method=loadUrls';
	 $.post(url,
			{parentId:parentId},
	        function(data){
				menu = eval(data);
			});
}

function getMenuHtml(menuData,level){
	var html = [];
	if(level =="two"){
		html.push('<ul class="nav nav-second-level">');
	}else if(level =="three"){
		html.push('<ul class="nav nav-third-level">');
	}
	$(menuData).each(function(i,o){
		var url = o.url !=null && o.url !=""?o.url:"#";
		var name = o.name;
		var icon = o.icon;
		 if(o.children&&o.children.length>0){
			html.push('<li>');
			html.push('<a href="#"><i class="'+icon+'"></i> <span class="nav-label">'+name+'</span><span class="fa arrow"></span></a> ');
			if(o.hasOwnProperty('parentId')){
				html.push(getMenuHtml(o.children,"three"));
			}else{
				html.push(getMenuHtml(o.children,"two"));
			}
			html.push('</li>');
		 }else{
			 
			html.push('<li>');
			if(url=="#"){
				html.push('<a href="#"><i class="'+icon+'"></i> <span class="nav-label">'+name+'</span><span class="fa arrow"></span></a> ');
			}else{
				if(url.indexOf("http")!=-1){
					html.push('<a class="J_menuItem" href="'+url+'"><i class="'+icon+'"></i> <span class="nav-label">'+name+'</span></a> ');
				}else{
					html.push('<a class="J_menuItem" href="../'+url+'"><i class="'+icon+'"></i> <span class="nav-label">'+name+'</span></a> ');
				}
			}
			html.push('</li>');
			
		 }
		 
	});
	
	html.push("</ul>");
	
	return html.join("");
}


//修改密码
function changePwd(){
	clearForm();
	$('#myForm1').validationEngine('hide');
	$("#myModalTitle").html("修改密码");
	$("#changePwdModal").modal();
}

//发送消息
function sendMsg(){
	$("#msgTitle").val("");
	$("#content").val("");
	$("#receiversName").val("");
	$("#receivers").val("");
	$('#msgForm').validationEngine('hide');
	$("#sendMsgModal").modal();
}

//查看消息
function viewMsg(){
	new receiveMsg().Init();
	new hasSentMsg().Init();
	$('#receiveMsg').bootstrapTable('refresh');
	$('#hasSentMsg').bootstrapTable('refresh');
	$("#viewMsgModal").modal();
}

function clearForm(){
	$("#oldPwd").val("");
	$("#newPassword").val("");
	$("#renewPassword").val("");
}

/**
 * 保存操作
 */
function openSaveButton(){
	
	var flag = $('#myForm1').validationEngine('validate');
	if(flag){
		var url='./../mainFrame/mainFrameInfo.app?method=changePwd';
		 $.post(url,
			{newPassword:$("#newPassword").val()},
	        function(data){
			   if(data=="success"){
				   layer.msg('修改成功！',{
						time: 2000
				   }, function(){
					   $("#changePwdModal").modal('hide');
					   window.location.href = currentUrl+"/security_logout_"; //退出系统
    			   });
			   }else{
				   layer.msg('修改失败！',{
						time: 2000
				   });
			   }
			});
	}else{
		layer.msg('表单验证未通过！',{
			time: 2000
		});
	}
	
}

function sendMsgButton(){
	var title = $("#msgTitle").val();
	var content = $("#content").val();
	var receiver = $("#receivers").val();
	var flag = $('#msgForm').validationEngine('validate');
	if(flag){
		if(receiver!=""){
			 var addJson = {
				 title:title,
				 content:content,
				 receiver:receiver
			 }
			 var url='./../mainFrame/mainFrameInfo.app?method=sendMsg';
			 $.ajax({
	             type: "post",
	             url: url,
	             data: addJson,
	             dataType: "json",
	     		 async : false,
	             success: function(data)
	             {
	            	 debugger;
	            	if(data == "success"){
	        			layer.msg("发送成功",{time: 2000}, function(){
	        				$('#sendMsgModal').modal('hide');
	        			});
	            	}else{
	            		layer.msg("发送失败",{time: 2000});
	            	}
	             }
	         });	
		}else{
			layer.msg("未选择接收人",{
				time: 2000
			});
		}
	}else{
		layer.msg("表单验证未通过",{
			time: 2000
		});
	}
}

var receiveMsg = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#receiveMsg').bootstrapTable({
			url: './../mainFrame/mainFrameInfo.app?method=receiveMsgInfo',         //请求后台的URL（*）
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
            maintainSelected:true,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            showExport: "true",
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: '[5, 10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
    		{
            	field: 'id',
            	visible: false,
            	title: 'id'
            }, {
            	field: 'title',
            	title: '标题'
            }, {
            	field: 'sendDate',
            	title: '发送时间',
            	formatter:dateFormate
            }, {
            	field: 'username',
            	title: '发送人'
            }, {
            	field: 'receiverName',
            	title: '接收人'
            }, {
            	field: 'read',
            	title: '是否已读',
            	formatter:mapValue
            },{
                field: 'operate',
                title: '详情',
                align: 'center',
                events: operateEvents,
                formatter: operateFormatter
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
			return json2TimeStamp(value);
		}
		
	};
	mapValue = function(value){
		if(value==true || value=="true"){
		    return "是";
		}else{
			return "否";
		}
	};
	//操作列的显示风格
	function operateFormatter(value, row, index) {
        return [
            '<a class="like" href="javascript:void(0)" title="Like">',
            '<i class="glyphicon glyphicon-heart">详情</i>',
            '</a>  '].join('');
    }
	//操作列的事件
	window.operateEvents = {
        'click .like': function (e, value, row, index) {
        	show(row);
        	if(row.read == false){
        		var url='./../mainFrame/mainFrameInfo.app?method=updateMsg';
        		$.post(url,
        				{id:row.id},
        				function(data){});
        	}
        }
    };
	return oTableInit;
	
};

var hasSentMsg = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#hasSentMsg').bootstrapTable({
			url: './../mainFrame/mainFrameInfo.app?method=hasSentMsgInfo',         //请求后台的URL（*）
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
            maintainSelected:true,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            showExport: "true",
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: '[5, 10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
    	    {
            	field: 'id',
            	visible: false,
            	title: 'id'
            }, {
            	field: 'title',
            	title: '标题'
            }, {
            	field: 'sendDate',
            	title: '发送时间',
            	formatter:dateFormate
            }, {
            	field: 'receiverName',
            	title: '接收人'
            }, {
            	field: 'read',
            	title: '是否已读',
            	formatter:mapValue
            }, {
            	field: 'reply',
            	title: '是否为回复消息',
            	formatter:mapValue
            },{
                field: 'operate',
                title: '详情',
                align: 'center',
                events: operateEvents,
                formatter: operateFormatter
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
			return json2TimeStamp(value);
		}
		
	};
	mapValue = function(value){
		if(value==true || value=="true"){
		    return "是";
		}else{
			return "否";
		}
	};
	//操作列的显示风格
	function operateFormatter(value, row, index) {
        return [
            '<a class="like" href="javascript:void(0)" title="Like">',
            '<i class="glyphicon glyphicon-heart">详情</i>',
            '</a>  '].join('');
    }
	//操作列的事件
	window.operateEvents = {
        'click .like': function (e, value, row, index) {
        	show(row);
        }
    };
	return oTableInit;
	
};

//显示详情
function show(obj){
	$("#dTitle").val(obj.title);
	$("#dContent").val(obj.content);
	$("#msgDetailModal").modal();
}

//显示详情
function initUnRead(){
	$("#unreadMsg").text("");
	var url='./../mainFrame/mainFrameInfo.app?method=unreadMsg';
	$.post(url,
	    function(data){
		$("#unreadMsg").text(data);
	});
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
	if(milliseconds==null || milliseconds ==""){
		return null;
	}
    var datetime = new Date();
    datetime.setTime(milliseconds);
    var year=datetime.getFullYear();
         //月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date;
}

function func1(){
	layer.open({
		  title:['选择人员','color:white;'],
		  type: 2,
		  area: ['700px', '450px'],
		  fix: false, //不固定
		  maxmin: true,
		  content: 'SelectMsgReceiveer.jsp'
		});
}

//子页面调用tab页面
function openTab(obj){
	var url = $(obj).attr("url");
	$("a.J_menuItem").each(function(e,v){
		var href = v.href;
		if(href.indexOf(url)>0){
			$(v).click();
		}
	});
}

function preview(file)
{
	var prevDiv = document.getElementById('preview');
	if (file.files && file.files[0])
	{
		var reader = new FileReader();
		reader.onload = function(evt){
			prevDiv.innerHTML = '<img src="' + evt.target.result + '" width="100%" height="100%"/>';
		}  
		reader.readAsDataURL(file.files[0]);
	}
	else  
	{
		prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
	}
}

function uploadHeadImg(){
	$("#uploadHeadImgModal").modal({backdrop: 'static', keyboard: false});
}

//初始化fileinput控件（第一次初始化）
function initFileInput(uploadUrl) {    
    $("#logo").fileinput({
        uploadUrl: uploadUrl,
        uploadAsync: false,
        showPreview: false,
        showUpload: true,
        allowedFileExtensions: ['jpg', 'png'],
        maxFileCount: 1,
        elErrorContainer: '#kv-error-2'
    }).on('filebatchpreupload', function(event, data, id, index) {
//        $('#kv-success-2').html('<h4>Upload Status</h4><ul></ul>').hide();
    }).on('filebatchuploadsuccess', function(event, data) {
        
        var msg = eval('('+data.response+')');
        if(msg.status==1){
        	$("#logoPath").val(msg.data);
        	layer.msg("成功",{
        		time: 2000
        	}, function(){
//        		$('#importModal').modal('hide');
        	});
        }else{
        	layer.msg(msg.message,{
        		time: 2000
        	});
        }
    }).on("filebatchselected", function(event, files) {
    	$("#logo").fileinput("upload");
    });
}

function saveUploadImg(){
	var logoPath = $("#logoPath").val();
	var userName = $("#userName").val();
	if(logoPath==""){
		layer.msg("未上传图片",{time: 2000});
		return;
	}
	 $.ajax({
         type: "post",
         url: "./../mainFrame/mainFrameInfo.app?method=updateUserHeadImg",
         data: {userName:userName,logoPath:logoPath},
         dataType: "json",
	     async : false,
         success: function(data)
         {
        	if(data=="success"){
        		layer.msg("保存成功",{time: 2000}, function(){
        			$('#uploadHeadImgModal').modal('hide');
        			window.location.href = currentUrl+"/security_logout_"; //退出系统
        		});
        	}else{
        		layer.msg("保存失败",{time: 2000});
        	}
         }
     });
}


function imgerror(img){
	img.src="img/profile_small.jpg";
	img.onerror=null;   //控制不要一直跳动
}

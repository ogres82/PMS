selections = [];
ipAddr="";
$(function () {
	initPermissions();
	var i=0;
	var j=0;
	$("#auditTableDiv").bind('click',function(){
		$("#auditTableInfoDiv").slideToggle("slow",function(){
			if(j==0){					
				j=1;
				$("#auditTableDiv").html("审核流程<i class='fa fa-angle-up'></i>");
			}else{
				j=0;
				$("#auditTableDiv").html("审核流程<i class='fa fa-angle-down'></i>");
			}
		})
	});

    //初始化Table
    oTable = new TableInit();
    oTable.Init();
    $(".summernote").summernote({
		height:200,
		lang:"zh-CN",
		onImageUpload: function(files, editor, welEditable) {
            sendFile(files[0],editor,welEditable);
        }
        });
    initAuditors();
	layer.config({
    	    extend: 'extend/layer.ext.js'
    	});
	$(".search").css({
		'position':'fixed',
		'right':'10px',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});
	$(".search input").attr("placeholder","搜索");
	initIpaddr();
	$('#myForm').validationEngine();
	initBtnEvent();
});
function initPermissions(){
	toolbarBtnInit(); //初始化工具条按钮
}
function initBtnEvent(){
	$("#btn1").bind("click",function(){
		openWindow(1);
	})
	$("#btn4").bind("click",function(){
		openWindow(4);
	})
}
function initIpaddr(){
	var url="./../msgandnotice/list.app?method=getIpAddr";
	$.post(url,function(data){
		 ipAddr=data;
	    }
	);
}
function sendFile(file, editor, welEditable){
		var filename = false;  
		try{  
		     filename = file['name'];  
		}catch(e){
			filename = false;
		}  
		if(!filename){
			$(".note-alarm").remove();
		  }  
		//以上防止在图片在编辑器内拖拽引发第二次上传导致的提示错误  
		var ext = filename.substr(filename.lastIndexOf("."));  
		    ext = ext.toUpperCase();  
		var timestamp = new Date().getTime();  
		var name = timestamp+"_"+$(".summernote").attr('aid')+ext;  
		dataForm = new FormData(); 
		dataForm.append("file", file);
		dataForm.append("key",name);  
		dataForm.append("token",$(".summernote").attr('token'));
		$.ajax({  
		   data: dataForm,  
		   type: "POST",
		   url: "./../msgandnotice/list.app?method=uploadImg", //图片上传出来的url，返回的是图片上传后的路径，http格式  
		   contentType:false,
		   cache: false,
		   dataType:'JSON',
		   processData: false,  
		   success: function(data) {
			  var filePath=$("#ntcPicPath").val();
			  var newFilePath=data.fileName+";"+filePath;
			  $("#ntcPicPath").val(newFilePath);
			  var fileUrl=ipAddr+"fileUpload.app?method=download&filepath="+data.fileName;
			  $('.summernote').summernote('insertImage', fileUrl, "");
//			  editor.insertImage(welEditable,fileUrl);
		  },  
		  error:function(data){
		     $(".note-alarm").html("上传失败");  
		     setTimeout(function(){$(".note-alarm").remove();},3000);  
		    }  
		});
		
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
//关闭对话框的时候清空所有数据
function emptyAll(){
	$('.summernote').summernote('code',"");
	$("#ntcCreatorId").val("");
	$("#ntcId").val("");
    $("#ntcCreatorName").val("");
    $("#ntcCreateTime").val("");
    $("#ntcStatus").val("");
    $("#ntcSubject").val(""); 
    $("#ntcAudit").val("");
	$("#ntcPicPath").val("");
    var action="${ContextPath}"+"/msgandnotice/list.app?method=save";
    $("#myForm").attr("action",action);
}
//保存 表单中的内容 
$.fn.serializeObject = function()    
{    
   var o = {};    
   var a = this.serializeArray();    
   $.each(a, function() {    
       if (o[this.name]) {    
           if (!o[this.name].push) {    
               o[this.name] = [o[this.name]];    
           }    
           o[this.name].push(this.value || '');    
       } else {    
           o[this.name] = this.value || '';    
       }    
   });    
   return o;    
};
function save(){
	debugger;
	var flag = $('#myForm').validationEngine('validate');
	if(flag){
		var content=$('.summernote').summernote('code');
		var subject=$("#ntcSubject").val();
		$("#ntcStatus").removeAttr("disabled");
		$("#ntcAudit").removeAttr("disabled"); 
	//$("#ntcContent").val(content);
	var arr  =
     {
         "ntcContent" :content,
         "ntcSubject" :subject
     }
	var jsonuserinfo = $('#myForm').serializeObject();
	var result=$.extend( false,arr,jsonuserinfo); 
	var id=$("#ntcId").val();
	if(id!=''){
		var url="./../msgandnotice/list.app?method=update";
	}else{
	    var url="./../msgandnotice/list.app?method=save";
	}
     $.post(url,
	        result,
		    function(data){
				layer.msg('操作成功',
				          {time:1000},
						  function(){
						    $("#chargeInfo").bootstrapTable('refresh');
						    $("#myModal").modal('hide');
							emptyAll();
						  })
				  });
	}else{
		layer.msg('表单验证未通过！',{
				time: 3000
			});		
	}

  }

var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#chargeInfo').bootstrapTable({
			url: './../msgandnotice/list.app?method=listAjax',         //请求后台的URL（*）
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
                        align: 'center',
                        valign: 'middle'
                    }, {
                        field: 'ntcSubject',
                        title: '标题',
                        sortable: true,
                        editable: true,
                        align: 'left',
						halign:'center',
						width:'300px'
                    },{
                        field: 'ntcStatus',
                        title: '状态',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        formatter: operateStatus
                    },{
                        field: 'ntcCreateTime',
                        title: '创建时间',
                        sortable: true,
                        align: 'center',
                    }, {
                        field: 'ntcCreator',
                        title: '创建人',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
		            	field: 'operate',
		                title: '操作',
		                align: 'center',
		                events: operateEvents,
		                formatter: operateFormatter
                   }
               ]
			   
		  });
	};
	function operateFormatter(value, row, index) {
        return tableBtnInit();
    }
	//操作列的事件
	window.operateEvents = {
        'click #a_check': function (e, value, row, index) {
        	editOrCheck(row,3);
        },
		'click #a_edit': function (e, value, row, index) {
			editOrCheck(row,2);
	    },
        'click #a_publish': function (e, value, row, index) {
			editOrCheck(row,5);
	    },
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
	
    function getIdSelections() {
        return $.map($('#chargeInfo').bootstrapTable('getSelections'), function (row) {
            return row.ntcId
        });
    }

    function responseHandler(res) {
        $.each(res.rows, function (i, row) {
            row.state = $.inArray(row.id, selections) !== -1;
        });
        return res;
    }

    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
            html.push('<p><b>' + key + ':</b> ' + value + '</p>');
        });
        return html.join('');
    }
    //状态处理函数
    function operateStatus(value, row, index) {
    	if(value==20){
    		return ['待审'].join('');
    	}else if(value=='21'){
    		return ['待发布'].join('');
    	}else if(value==30){
    		return ['已发布'].join('');
    	}else if(value==22){
    		return ['审核中'].join('');
    	}else if(value==11){
    		return ['驳回'].join('');
    	}else{
    		return ['起草'].join('');
    	}
    	
       
    }
    function totalTextFormatter(data) {
        return 'Total';
    }

    function totalNameFormatter(data) {
        return data.length;
    }

    function totalPriceFormatter(data) {
        var total = 0;
        $.each(data, function (i, row) {
            total += +(row.price.substring(1));
        });
        return '$' + total;
    }

    function getHeight() {
        return $(window).height() - $('h1').outerHeight(true);
    }
	    function getScript(url, callback) {
        var head = document.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        script.src = url;

        var done = false;
        // Attach handlers for all browsers
        script.onload = script.onreadystatechange = function() {
            if (!done && (!this.readyState ||
                    this.readyState == 'loaded' || this.readyState == 'complete')) {
                done = true;
                if (callback)
                    callback();

                // Handle memory leak in IE
                script.onload = script.onreadystatechange = null;
            }
        };

        head.appendChild(script);
        return undefined;
    }
  //ajax获取审核人
    function initAuditors(){
    	var url="./../msgandnotice/list.app?method=getAuditor";
        $.post(url,
		      function(data){
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
		               $("<option value='"+list[j].id+"'>"+list[j].name+"</option>").appendTo("#ntcAudit");  
		        	};
				  });
    	//return rtnValue;
    }
    //将当前时间转换成格式为YYYY-mm-dd HH:mm:ss格式的字符串 flag为true时，说明是新增 ，date为当前时间 否则为传递date参数转换成字符串
    function getNowFormatDate(flag,vardate) {
    	var date;
    	if(flag==true){
        	date = new Date();
    	}else{
    		date=vardate;
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
	//查看和编辑
    function editOrCheck(obj,type){
    	openWindow(type,obj.ntcId)
	}
    function func1(){
    	alert($("#selectAge").val());
    }
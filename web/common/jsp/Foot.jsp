<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
</body>
  <script>
    var $table = $('#table'),
        $remove = $('#remove'),
        selections = [];

    function initTable() {
        $table.bootstrapTable({
            height: getHeight(),
            columns: [
                [
                    {
                        field: 'state',
                        checkbox: true,
                        //rowspan: 2,
                        align: 'center',
                        valign: 'middle'
                    }, {
                        field: 'ntcSubject',
                        title: '标题',
                        sortable: true,
                        editable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center'
                    },{
                        field: 'ntcStatus',
                        title: '状态',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        //events: operateEvents,
                        formatter: operateStatus
                    },{
                        field: 'ntcCreateTime',
                        title: '创建时间',
                        sortable: true,
                        align: 'center',
                        /* editable: {
                            type: 'text',
                            title: 'Item Price',
                            validate: function (value) {
                                value = $.trim(value);
                                if (!value) {
                                    return 'This field is required';
                                }
                                if (!/^$/.test(value)) {
                                    return 'This field needs to start width $.'
                                }
                                var data = $table.bootstrapTable('getData'),
                                    index = $(this).parents('tr').data('index');
                                console.log(data[index]);
                                return '';
                            }
                        }, */
                         footerFormatter: totalPriceFormatter
                    }, {
                        field: 'ntcCreator',
                        title: '创建人',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                        //events: operateEvents,
                        //formatter: operateFormatter
                    }/* 
                    
         
                    
                    {
                        title: 'Item Detail',
                        colspan: 3,
                        align: 'center'
                    } */
                ]
                /* [
                    {
                        field: 'name',
                        title: 'Item Name',
                        sortable: true,
                        editable: true,
                        footerFormatter: totalNameFormatter,
                        align: 'center'
                    }, {
                        field: 'price',
                        title: 'Item Price',
                        sortable: true,
                        align: 'center',
                        editable: {
                            type: 'text',
                            title: 'Item Price',
                            validate: function (value) {
                                value = $.trim(value);
                                if (!value) {
                                    return 'This field is required';
                                }
                                if (!/^$/.test(value)) {
                                    return 'This field needs to start width $.'
                                }
                                var data = $table.bootstrapTable('getData'),
                                    index = $(this).parents('tr').data('index');
                                console.log(data[index]);
                                return '';
                            }
                        },
                        footerFormatter: totalPriceFormatter
                    }, {
                        field: 'operate',
                        title: 'Item Operate',
                        align: 'center',
                        events: operateEvents,
                        formatter: operateFormatter
                    }
                ] */
            ]
        });
        // sometimes footer render error.
        setTimeout(function () {
            $table.bootstrapTable('resetView');
        }, 200);
        $table.on('check.bs.table uncheck.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);

            // save your data, here just save the current page
            selections = getIdSelections();
            // push or splice the selections if you want to save all data selections
        });
        $table.on('expand-row.bs.table', function (e, index, row, $detail) {
            if (index % 2 == 1) {
                $detail.html('Loading from ajax request...');
                $.get('LICENSE', function (res) {
                    $detail.html(res.replace(/\n/g, '<br>'));
                });
            }
        });
        $table.on('all.bs.table', function (e, name, args) {
            console.log(name, args);
        });
        $remove.click(function () {
            var ids = getIdSelections();
            $table.bootstrapTable('remove', {
                field: 'id',
                values: ids
            });
            $remove.prop('disabled', true);
        });
        $(window).resize(function () {
            $table.bootstrapTable('resetView', {
                height: getHeight()
            });
        });
    }

    function getIdSelections() {
        return $.map($table.bootstrapTable('getSelections'), function (row) {
            return row.id
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

    function operateFormatter(value, row, index) {
        return [
            '<a class="like" href="javascript:void(0)" title="Like">',
            '<i class="glyphicon glyphicon-heart"></i>',
            '</a>  ',
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
    }
    //状态处理函数
    function operateStatus(value, row, index) {
    	if(value==20){
    		return ['待审'].join('');
    	}else if(value==21){
    		return ['待发布'].join('');
    	}else if(value==30){
    		return ['已发布'].join('');
    	}else if(value==11){
    		return ['驳回'].join('');
    	}else{
    		return ['起草'].join('');
    	}
    	
       
    }
    window.operateEvents = {
        'click .like': function (e, value, row, index) {
            alert('You click like action, row: ' + JSON.stringify(row));
        },
        'click .remove': function (e, value, row, index) {
            $table.bootstrapTable('remove', {
                field: 'id',
                values: [row.id]
            });
        }
    };

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

    $(function () {
        var scripts = [
                location.search.substring(1) || '${ContextPath}/Hplus/bootstrap/js/bootstrap-table.js',
                '${ContextPath}/Hplus/bootstrap/js/bootstrap-table-export.js',
                '${ContextPath}/Hplus/bootstrap/js/tableExport.js',
                '${ContextPath}/Hplus/bootstrap/js/bootstrap-table-zh-CN.js',
                '${ContextPath}/Hplus/bootstrap/js/bootstrap-table-editable.js',
                '${ContextPath}/Hplus/bootstrap/js/bootstrap-editable.js'
            ],
            eachSeries = function (arr, iterator, callback) {
                callback = callback || function () {};
                if (!arr.length) {
                    return callback();
                }
                var completed = 0;
                var iterate = function () {
                    iterator(arr[completed], function (err) {
                        if (err) {
                            callback(err);
                            callback = function () {};
                        }
                        else {
                            completed += 1;
                            if (completed >= arr.length) {
                                callback(null);
                            }
                            else {
                                iterate();
                            }
                        }
                    });
                };
                iterate();
            };

        eachSeries(scripts, getScript, initTable);
         $(".summernote").summernote({lang:"zh-CN"});
        initAuditors();
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
        layer.config({
    	    extend: 'extend/layer.ext.js'
    	});
    });

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
    	var url="${ContextPath}"+"/msgandnotice/list.app?method=getAuditor";
        $.post(url,
		      function(data){
				 var list = eval(data);
				 for(var j=0;j<list.length;j++){
		               $("<option value='"+list[j].id+"'>"+list[j].name+"</option>").appendTo("#ntcAudit");  
		        	};
				  });
    	//return rtnValue;
    }
    //弹框 ，根据 传递 的不同参数 进行操作
    function openWindow(type){
    	//新增
    	 if(type==1){
    		var loginUserName="${loginUser.username}";
            var loginUserCname="${loginUser.cname}";
            $("#ntcCreatorId").val(loginUserName);
            $("#ntcCreatorName").val(loginUserCname);
            var datetime=getNowFormatDate(true);
            $("#ntcCreateTime").val(datetime);
            $("#ntcStatus").val("20");
            $('#myModal').modal();
    	 }
    	 //编辑
    	 if(type==2){
    		 var length=$("input[name='List_Selected']:checked").length;
    		 if( length==0||length>1){
    			 alert("请选择一条记录进行操作！");
    		 }else{
    			var id=$("input[name='List_Selected']:checked").attr("value");
    		    var url="${ContextPath}"+"/msgandnotice/list.app?method=viewForAjax&noticeId="+id;
    		    $.post(url,
    		    	   function(data){
					   	
    				   var list = eval(data);
    				        $("#ntcId").val(list.ntcId);
    			            $("#ntcCreatorId").val(list.ntcCreatorId);
    			            $("#ntcCreatorName").val(list.ntcCreator);
    				        var newTime = new Date(list.ntcCreateTime);
    			            $("#ntcCreateTime").val(getNowFormatDate(false,newTime));
    			            $("#ntcStatus").val(list.ntcStatus);
    			            $("#ntcSubject").val(list.ntcSubject); 
    			            $("#ntcAudit").val(list.ntcAuditor);
    			            $(".summernote").code(list.ntcContent);
    			            var action="${ContextPath}"+"/msgandnotice/list.app?method=update"
    			            $("#myForm").attr("action",action);
    		    			$('#myModal').modal();
    				    });
    		 }
    	 }
     	 if(type==3){
     		 
     	 }
     	 //删除
     	 if(type==4){
    		 var length=$("input[name='List_Selected']:checked").length;
    		 if(length==0){
    			 alert("请至少选择一条数据进行删除！");
    		 }else{
    			 var chk_value =[];
		         var url="${ContextPath}"+"/msgandnotice/list.app?method=deleteByAjax"
    			 $("input[name='List_Selected']:checked").each(function(){
    				 chk_value.push($(this).attr("value")); 
    			 });
    			 var deleteIds = JSON.stringify(chk_value);
    			 if(confirm('确实要删除该内容吗?')){
    				  $.post(url,
    						{deleteId:deleteIds},
    	    		        function(data){
    							alert(data);
    							var locationUrl="${ContextPath}"+"/msgandnotice/list.app?method=list";
    							window.location.href=locationUrl;
    						});
    			 }else{
    			 }
    		 }

     	 }if(type==5){
     		 
     	 }if(type==6){
     		 
     	 }
    }
    //关闭对话框的时候清空所有数据
    function emptyAll(){
    	$('.summernote').code("<br /><br /><br /><br /><br /><br />");
    	$("#ntcCreatorId").val("");
    	$("#ntcId").val("");
        $("#ntcCreatorName").val("");
        $("#ntcCreateTime").val("");
        $("#ntcStatus").val("");
        $("#ntcSubject").val(""); 
        $("#ntcAudit").val("");
        var action="${ContextPath}"+"/msgandnotice/list.app?method=save";
        $("#myForm").attr("action",action);
    }
    //保存 表单中的内容 
    function save(){
    	var content=$('.summernote').code();
    	$("#ntcStatus").removeAttr("disabled"); 
    	$("#ntcContent").val(content);
    	$("#myForm").submit();
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
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate
                + " " + date.getHours() + seperator2 + date.getMinutes()
                + seperator2 + date.getSeconds();
        return currentdate;
    }
    function func1(){
    	alert($("#selectAge").val());
    }
  </script>
 </html>
var firstOrder = 0;
var subOrder = 0;
var addMark = false; //用于判断是否选择了新增菜单节点，否则不予保存
$(function () {
	$.ajaxSetup({   
        async : false  
	}); 
	initUrlTree(2);
	//根据屏幕分辨率设置ibox高度
	var  frameHeight = $(document.body).height()-10+'px';
	$(".ibox-content").height(frameHeight);
	$(document).ajaxStop($.unblockUI);
});



function initBlockUI(){
	$.blockUI({
    	message:'<h4>操作中，请稍后...</h4>',
    	css:{
    		border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff' 
    	}
    }); 
}


function refreshUrlTree(type){
	initUrlTree(type);
}

function initUrlTree(type){
	 var url='./../system/urlMaintain.app?method=urltree';
	 $.post(url,
			{parentId:""},
	        function(data){
				menu = eval(data);
				var firstLevel = getTreeData(menu);
				var $urlTree = $("#urlTree").treeview({
					data:firstLevel,
					selectedBackColor:'#1ab394',
					onNodeSelected: selectUrl
				});
				$urlTree.treeview('expandAll', { levels: 3, silent: true });
			});
	 if(type == 2){
			selectInit();
	 }
}



function selectUrl(event,data){
	addMark = true;
	$("#parentId").val(data.parentid);
	$("#id").val(data.href);
	$("#name").val(data.text);
	$("#url").val(data.url);
	$("#icon").val(data.icon);
	$("#order").val(data.order);
	$("#desc").val(data.desc);
	if(data.forNavigation){
		$("input:radio[name='radioInline']").eq(0).prop('checked',true);
	}else{
		$("input:radio[name='radioInline']").eq(1).prop('checked',true);
	}
}

function buttonEvent(type){
	addMark = true;
	if(type == 1){   //新增根节点
		$("#id").val("");
		$("#parentId").val("");
		$("#name").val("新菜单");
		$("#url").val("");
		$("#icon").val("");
		$("#desc").val("");
		$("#order").val(firstOrder+1);
		$("input:radio[name='radioInline']").eq(0).prop('checked',true);
	}
	if(type == 2){   //新增子节点
		var a = $('#urlTree').treeview('getSelected');
		if(a.length == 0){
			layer.alert("请选择菜单节点",{
				skin: 'layui-layer-molv'
			});
			return;
		}else{
			var parentId = a[0].href;
			var url='./../system/urlMaintain.app?method=countChildren';
			$.post(url,
					{parentId:parentId},
			        function(data){
						var order = eval(data);
						$("#id").val("");
						$("#parentId").val(parentId);
						$("#name").val("新菜单");
						$("#order").val(order+1);
						$("#url").val("");
						$("#icon").val("");
						$("#desc").val("");
						$("input:radio[name='radioInline']").eq(0).prop('checked',true);
					});
			
		}
	}
	if(type == 3){  //删除节点
		var a = $('#urlTree').treeview('getSelected');
		if(a.length == 0){
			layer.alert("请选择菜单节点！",{
				skin: 'layui-layer-molv'
			});
			return;
		}
		var nodes = a[0].nodes;
		if(typeof(nodes) != "undefined"){
			layer.alert("请先刪除子节点！",{
				skin: 'layui-layer-molv'
			});
			return;
		}else{
			initBlockUI();
			var id = {id:a[0].href};
			$.ajax({
		        type: "post",
		        url: "./../system/urlMaintain.app?method=delete",
		        data: id,
		        dataType: "json",
				async : true,
		        success: function(data)
		        {
					layer.msg("删除成功！",{time:1500},
							function(){
						refreshUrlTree(2);
					});
					
		        },
		        failure:function(xhr,msg)
		        {
		        	layer.msg('操作失败！',{
						time: 1500
					}, function(){
						
					});
		        }
		    });
		}
		return;
	}
}

function save(){
	if(!addMark){
		layer.alert("请先选择需新增节点！",{
			skin: 'layui-layer-molv'
		});
		return;
	}
	initBlockUI();
	var forNavi;
	if($("#forNavi1").prop("checked")){
		forNavi = true;
	}else{
		forNavi = false;
	}
	var url = {
			id: $("#id").val(),
			name: $("#name").val(),
			url: $("#url").val(),
			icon: $("#icon").val(),
			desc: $("#desc").val(),
			parentId: $("#parentId").val(),
			order: $("#order").val(),
			forNavigation: forNavi
			}
	
	$.ajax({
        type: "post",
        url: "./../system/urlMaintain.app?method=save",
        data: url,
        dataType: "json",
		async : true,
        success: function(data)
        {
        	layer.msg("保存成功！",{time:1500},
        			function(){
        		refreshUrlTree(1);
        	});
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！',{
				time: 1500
			}, function(){
				
			});
        }
    });
}

function selectInit(){
	$('#urlTree').treeview('selectNode', [ 0, { silent: true } ]);
	var a = $('#urlTree').treeview('getSelected');
	$("#id").val(a[0].href);
	$("#name").val(a[0].text);
	$("#url").val(a[0].url);
	$("#icon").val(a[0].icon);
	$("#order").val(a[0].order);
	$("#desc").val(a[0].desc);
	if(a[0].forNavigation){
		$("input:radio[name='radioInline']").eq(0).prop('checked',true);
	}else{
		$("input:radio[name='radioInline']").eq(1).prop('checked',true);
	}
}
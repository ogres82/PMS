var dept = "";
var firstLevel = [];
var secondLevel = [];
var thirdLevel = [];

$(function () {
	$.ajaxSetup({   
        async : false  
	}); 
	initDeptTree(2);
	//根据屏幕分辨率设置ibox高度
	var  frameHeight = $(document.body).height()-10+'px';
	$(".ibox-content").height(frameHeight);
});

function Node(text,nodes,id,desc,parentId){
	this.text = text;
	this.nodes = nodes;
	this.id = id;
	this.desc = desc;
	this.parentId = parentId
}

function initDeptTree(type){
	loadDepts();
	getTreeData(dept);
	
	var $deptTree = $("#deptTree").treeview({
		data:firstLevel,
		selectedBackColor:'#1ab394',
		onNodeSelected: selectUrl
	});
	$deptTree.treeview('expandAll', { levels: 3, silent: true });
	if(type==2){
		selectInit();
	}
}

function refreshDeptTree(type){
	dept = "";
	firstLevel = [];
	secondLevel = [];
	thirdLevel = [];
	initDeptTree(type);
}

function loadDepts(parentId){
	 var url='./../system/deptMaintain.app?method=load';
	 $.post(url,
			{parentId:parentId},
	        function(data){
				dept = eval(data);
			});
}

function getTreeData(deptData){
	var length1 = deptData.length;
	firstOrder = length1;
	for(var i=0;i<length1;i++){
		var firstDept = deptData[i];
		var firstNode = new Node();
		firstNode.text = firstDept.name;
		firstNode.id = firstDept.id;
		firstNode.desc = firstDept.desc;
		firstNode.parentId = firstDept.parentId;
		loadDepts(firstDept.id);
		var subDept = dept;
		if(subDept.length>0){
			secondLevel = [];
			var length2 = subDept.length;
			for(var j=0;j<length2;j++){
				var secDept = subDept[j];
				var secondNode = new Node();
				secondNode.text = secDept.name;
				secondNode.id = secDept.id;
				secondNode.desc = secDept.desc;
				secondNode.parentId = secDept.parentId;
				loadDepts(secDept.id);
				var thirdDept = dept;
				if(thirdDept.length>0){
					thirdLevel = [];
					var length3 = thirdDept.length;
					for(var k=0;k<length3;k++){
						var trdDept = thirdDept[k];
						var thirdNode = new Node();
						thirdNode.text = trdDept.name;
						thirdNode.id = trdDept.id;
						thirdNode.desc = trdDept.desc;
						thirdNode.parentId = trdDept.parentId;
						thirdLevel.push(thirdNode);
					}
					secondNode.nodes = thirdLevel;
				}
				secondLevel.push(secondNode);
			}
			firstNode.nodes = secondLevel;
		}
		firstLevel.push(firstNode);
	}
}

function selectUrl(event,data){
	$("#id").val(data.id);
	$("#name").val(data.text);
	$("#parentId").val(data.parentId);
	$("#desc").val(data.desc);
}

function buttonEvent(type){
	if(type == 1){   //新增根节点
		$("#id").val("");
		$("#parentId").val("");
		$("#name").val("");
		$("#desc").val("");
	}
	if(type == 2){   //新增子节点
		var a = $('#deptTree').treeview('getSelected');
		if(a.length == 0){
			layer.alert("请选择部门节点",{
				skin: 'layui-layer-molv'
			});
			return;
		}else{
			var parentId = a[0].id;
			$("#parentId").val(parentId);
			$("#id").val("");
			$("#name").val("");
			$("#desc").val("");
		}
	}
	if(type == 3){  //删除节点
		var a = $('#deptTree').treeview('getSelected');
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
			var id = {id:a[0].id};
			$.ajax({
		        type: "post",
		        url: "./../system/deptMaintain.app?method=delete",
		        data: id,
		        dataType: "json",
				async : false,
		        success: function(data)
		        {
		        	layer.msg("操作中...",{
						time: 1500
					}, function(){
						refreshDeptTree(2);
						layer.msg("删除成功！",{time:1000});
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
	var dept = {
			id: $("#id").val(),
			name: $("#name").val(),
			desc: $("#desc").val(),
			parentId: $("#parentId").val()
			}
	$.ajax({
        type: "post",
        url: "./../system/deptMaintain.app?method=save",
        data: dept,
        dataType: "json",
		async : false,
        success: function(data)
        {
        	layer.msg("操作中...",{
				time: 1500
			}, function(){
				refreshDeptTree(1);
				layer.msg("保存成功！",{time:1000});
				
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
	$('#deptTree').treeview('selectNode', [ 0, { silent: true } ]);
	var a = $('#deptTree').treeview('getSelected');
	$("#id").val(a[0].id);
	$("#name").val(a[0].text);
	$("#desc").val(a[0].desc);
}
var type = true;
$(function() {
	initTree();
});
// 初始化小区摄像头列表树
function initTree() {
	$.ajax({
		type : "post",
		url : "./../camera/camera.app?method=treeList",
		dataType : "json",
		async : true,
		success : function(treeData) {
			$('#treeview').treeview({
				showTags : true,
				selectedBackColor : '#1ab394',
				collapseIcon : "fa fa-chevron-down", //节点展开时显示的图标        String
				expandIcon : "fa fa-chevron-right", //节点被折叠时显示的图标        String
				data : treeData,
				levels : 0,
				onNodeSelected : function(event, data) {
					// 事件代码...
					//                	address 监控地址，text 监控名称
					if (data.parnetId != undefined) {
						var id = document.getElementById("myPlayer");
						id.src = data.address;
						
						var player = new EZUIPlayer("myPlayer");
					}
				}
			});
		}
	});
}
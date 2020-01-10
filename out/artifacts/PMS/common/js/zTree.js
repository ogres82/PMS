var zTreeNodes = [];

$.ajax({
	// 请求方式
	type : "GET",
	// 请求的媒体类型
	contentType : "application/json;charset=UTF-8",
	// 请求地址
	url : getRootPath() + "ztree.app?method=getHouseTree",
	dataType : "JSON",
	async : false,
	// 数据，json字符串
	// data : JSON.stringify(list),
	// 请求成功
	success : function(result) {
		zTreeNodes = result;
		// console.log(zTreeNodes);

	},
	// 请求失败，包含具体的错误信息
	error : function(e) {
		console.log(e.status);
		console.log(e.responseText);
	}
});

var setting = {
	view : {
		addHoverDom : false,
		removeHoverDom : false,
		selectedMulti : false, // 设置是否允许同时选中多个节点。
		nameIsHTML : true, // 允许name支持html
		dblClickExpand : true	// 双击父节点自动展开
	},
	data : {
		key : {
			name : "name" // 节点显示的值
		},
		simpleData : {
			enable : true,// 如果为true，可以直接把从数据库中得到的List集合自动转换为Array格式。而不必转换为json传递
		}
	},
	callback: {
		onClick: zTreeOnClick
	}
};


function initZtree() {
	return $.fn.zTree.init($("#houseZTree"), setting, zTreeNodes);
}

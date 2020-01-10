$(document).ready(function() {
	$.fn.zTree.init($("#treeDemo"), setting);// 异步获取json格式数据，第三个参数传null或者空着
});
var setting = {
	// 勾选框
	check : {
		enable : true,
		chkboxType : {
			"Y" : "s",
			"N" : "ps"
		}
	},

	data : {
		key : {
			name : "name"
		},
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pid"
		}
	},

	async : {
		enable : true,
		url : "appInfo/getNodesByAsync",
		autoParam : [ "id"/* , "pid", "name" */],// 自动提交的参数（可提交多个）
		// otherParam:{"otherParam":"zTreeAsyncTest"}
		dataFilter : filter
	// 异步返回后经过Filter
	},
	callback : {
		onAsyncSuccess : zTreeOnAsyncSuccess,// 异步加载完成调用
		aOnAsyncError : zTreeOnAsyncError
	// 加载错误的fun
	}
};

/* 获取返回的数据，进行预操作，treeId是treeDemo,异步加载完之后走这个方法，responseData为后台返回数据 */
function filter(treeId, parentNode, responseData) {
	// responseData = responseData.jsonArray;
	if (!responseData) {
		return null;
	}
	return responseData;
}

// 异步加载完成时运行
function zTreeOnAsyncSuccess(event, treeId, msg) {
}
// 异步加载失败
function zTreeOnAsyncError(event, treeId, treeNode) {
	alertMsg.error("异步加载节点失败!");
}


function chooseSubmit() {

	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = zTree.getCheckedNodes(true);
	if (nodes.length == 0) {
		alertMsg.error("请选择设备！");
		return false;
	}
	var resultStr = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		if (nodes[i].isDevice == "true") {
			resultStr += nodes[i].name + ",";
		}
	}
	resultStr = resultStr.substring(0, resultStr.length - 1);
	alert(resultStr);
	if (resultStr == "") {
		// alert("请先选择一个节点");
		alertMsg.error("请选择设备！");
		return false;
	}

	$.ajax({
		url : 'appInfo/appUpdateBatch',
		type : "POST",
		datatype : "json",
		data : {
			ids : resultStr,
			server_id : '${server_id}',
			server_version : '${server_version}'
		},
		success : function(data) {
			if (data.statusCode == "200") {
				alertMsg.correct(data.message);
				$.pdialog.closeCurrent();
			} else {
				alertMsg.error(data.message);
				$.pdialog.closeCurrent();
			}
		}
	});
}
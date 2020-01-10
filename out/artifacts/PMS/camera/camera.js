var OCXobj = "";

$(function() {
	var frameHeight = $(document.body).height() + 50 + 'px';
	$(".ibox-content").height(frameHeight);
	OCXobj = document.getElementById("PreviewOcx");
	initTree();
	loginCMS();
});
// 初始化小区摄像头列表树
function initTree(){
	$.ajax({
        type: "post",
        url:"./../camera/camera.app?method=treeList",
        dataType: "json",
		async : true,
        success: function(treeData)
        {
        	$('#treeview').treeview({
                showTags: true,
                selectedBackColor:'#1ab394',
                collapseIcon: "fa fa-chevron-down", //节点展开时显示的图标        String
                expandIcon: "fa fa-chevron-right", //节点被折叠时显示的图标        String
                data: treeData,
                onNodeSelected:function(event, data) {
                    // 事件代码...
//                	address 监控地址，text 监控名称
                	console.log(data);
                	if(data.parnetId!=undefined){
                		startPreview(data.id);
                	}
            	}
        	});
        }        
    });
}

//打开视频监控
function startPreview(cameraId) {
	var ret = OCXobj.StartTask_Preview_FreeWnd(cameraId);
	if (ret != 0) {
		alert("StartTask_Preview_FreeWnd接口调用失败！错误码：" + OCXobj.GetLastError());
	}
}

/** ***登录CMS***** */
function loginCMS() {
	var ret = OCXobj.Login(ipAdd, port, userName, pw);
	// 判断登录是否成功
	if (ret != 0) {
		alert("监控服务器网络异常，请稍后再试！" + OCXobj.GetLastError());
	}
}

/** ***登出CMS***** */
function logoutCMS() {
	var ret = OCXobj.Logout();
	if (ret != 0) {
		alert("退出登录失败，请稍后再试！" + OCXobj.GetLastError());
	}
}

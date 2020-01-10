//创建跨域通信对象
var messenger = new Messenger('Parent', 'Messenger');
//使用状态WebGIS页面的的iframe来添加监听
//注意GISFrame 表示WebGIS容器的ID
messenger.addTarget(document.getElementById('ChildFrame').contentWindow, "Child");
//添加GIS页面处理结果回调处理函数监听
messenger.listen(function (msg) {
    //具体对GIS页面代码进行处理的逻辑
    var json = JSON.parse(msg);
    ChildMessageDeal(json.Function, json.Res);
});

function SenMessgaeToChild(functionName, arguments) {
    var json = { Function: functionName, Arg: arguments };
    var str = JSON.stringify(json);
    messenger.targets['Child'].send(str);
}
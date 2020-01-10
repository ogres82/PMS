/*tcCore.loadCss($url(">/Hplus/css/bootstrap.min.css?v=3.3.6"));
tcCore.loadCss($url(">/Hplus/css/font-awesome.min.css?v=4.4.0"));
tcCore.loadCss($url(">/Hplus/css/animate.min.css"));
tcCore.loadCss($url(">/Hplus/css/style.min.css?v=4.1.0"));
tcCore.loadCss($url(">/Hplus/css/plugins/dataTables/dataTables.bootstrap.css?v=4.1.0"));*/


tcCore.loadCss($url(">/Hplus/css/plugins/sweetalert/sweetalert.css?v=4.1.0"));

tcCore.loadJs($url(">/Hplus/js/plugins/laydate/laydate.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/dataTables/jquery.dataTables.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/dataTables/dataTables.bootstrap.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/validate/jquery.validate.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/validate/messages_zh.min.js"));
tcCore.loadJs($url(">/Hplus/js/demo/form-validate-demo.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/sweetalert/sweetalert.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/suggest/bootstrap-suggest.min.js"));

/*tcCore.loadJs($url(">/Hplus/js/jquery.min.js?v=2.1.4"));
tcCore.loadJs($url(">/Hplus/js/bootstrap.min.js?v=3.3.6"));
tcCore.loadJs($url(">/Hplus/js/plugins/metisMenu/jquery.metisMenu.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/layer/layer.min.js"));
tcCore.loadJs($url(">/Hplus/js/hplus.min.js?v=4.1.0"));
tcCore.loadJs($url(">/Hplus/js/contabs.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/pace/pace.min.js"));
tcCore.loadJs($url(">/Hplus/js/content.min.js?v=1.0.0"));
tcCore.loadJs($url(">/Hplus/js/plugins/jeditable/jquery.jeditable.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/dataTables/jquery.dataTables.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/dataTables/dataTables.bootstrap.js"));*/





/*搜索框初始化*/
var addressSuggest = $("input#address").bsSuggest({
    url: "http://localhost:8080/PMS/search.app?keyword=",
    showHeader: true,
    allowNoKeyword: false,
    keyField: 'roomNo',
    getDataMethod: "url",
    delayUntilKeyup: true,
    effectiveFields: ["roomNo","ownerName","phone"],
    effectiveFieldsAlias: {
    	roomNo:"房间号",
    	ownerName:"业主名",
    	phone:"手机号"
    },
    processData: function (json) {    // url 获取数据时，对数据的处理，作为 getData 的回调函数
        var index, len, data = {value: []};
        if (!json || !json.result || json.result.length === 0) {
            return false;
        }

        len = json.result.length;

        for (index = 0; index < len; index++) {
            data.value.push({
                "roomNo": json.result[index][1]+"-"+json.result[index][3]+"-"+json.result[index][5],
                "roomId": json.result[index][4],
                "ownerName": json.result[index][7],
                "ownerId": json.result[index][6],
                "phone": json.result[index][8],
                
            });
        }
        //字符串转化为 js 对象
        
        return data;
    }
	}).on("onDataRequestSuccess",
	function(e, result) {
	    console.log("onDataRequestSuccess: ", result)
	}).on("onSetSelectValue",
	function(e, keyword, data) {
	    console.log("onSetSelectValue: ", keyword, data);
//	    alert(JSON.stringify(data));
	    $("#ownerId").val(data.ownerId);
	    $("#rpt_name").val(data.ownerName);
	    $("#roomId").val(data.roomId);
	    $("#in_call").val(data.phone);
	}).on("onUnsetSelectValue",	
	function(e) {
	    console.log("onUnsetSelectValue")
	});



/*页面初始化*/
function init() {
	initFrame();
	
}


function initFrame() {
	
}

/*初始化表格及数据*/
function getMenuHtml(menuData,isFirstLevel){
	
	
}


//@Bind #exampleView.onReady
!function(){
	
	init();
} ;
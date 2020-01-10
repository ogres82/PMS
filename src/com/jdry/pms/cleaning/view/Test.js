/*tcCore.loadCss($url(">/Hplus/css/bootstrap.min.css?v=3.3.6"));
tcCore.loadCss($url(">/Hplus/css/font-awesome.min.css?v=4.4.0"));
tcCore.loadCss($url(">/Hplus/css/animate.min.css"));
tcCore.loadCss($url(">/Hplus/css/style.min.css?v=4.1.0"));
tcCore.loadCss($url(">/Hplus/css/plugins/sweetalert/sweetalert.css"));

tcCore.loadJs($url(">/Hplus/js/jquery.min.js?v=2.1.4"));
tcCore.loadJs($url(">/Hplus/js/bootstrap.min.js?v=3.3.6"));
tcCore.loadJs($url(">/Hplus/js/plugins/metisMenu/jquery.metisMenu.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/layer/layer.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/sweetalert/sweetalert.min.js"));
tcCore.loadJs($url(">/Hplus/js/hplus.min.js?v=4.1.0"));
tcCore.loadJs($url(">/Hplus/js/contabs.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/pace/pace.min.js"));*/

$(".profile-element span img").attr("src",$url(">/Hplus/img/profile_small.jpg"));
$(".J_iframe").attr("src",$url(">/com.jdry.pms.mainFrame.view.welCome.d"));
$(".hidden-xs").find("a[class='J_menuItem']").attr("href",$url(">/com.jdry.pms.mainFrame.view.welCome.d"));

$(".page-tabs-content a").attr("data-id",$url(">/com.jdry.pms.mainFrame.view.welCome.d"));

function init() {
	
	
	initFrame();
	//block();
	//initHead();
	//initBody();
	
	//initAlert();
	//initEvent();
}

$('.btn1').click(function () {
	alert('hello');
    swal({
        title: "您确定要删除这条信息吗",
        text: "删除后将无法恢复，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "删除",
        closeOnConfirm: false
    }, function () {
        swal("删除成功！", "您已经永久删除了这条信息。", "success");
    });
});


//@Bind #view.onReady
!function(){
	init();
} ;
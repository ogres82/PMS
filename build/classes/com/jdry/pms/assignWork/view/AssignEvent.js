/*更多查询区域*/
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

/*新增按钮事件*/
eventView.get("#addTemp").onClick=function(){
	
	$('#myModal1').modal();
}


$("#eventType").change(function(){
	if($("#eventType").val()=="0"){
		$("#bxdetail").show();
		$("#tsdetail").hide();
	}else{
		$("#bxdetail").hide();
		$("#tsdetail").show();
	}
});




//@Bind #eventView.onReady
!function(){
	
	init();
} ;
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

$("#empPostId").click(function(){
	
	
});

/*新增按钮，弹出对话框*/
tableView.get("#addTemp").onClick=function(){
	var positions = view.get("#dsSupplieType").get("data").toArray();
	$("#operator_flag").val("0");
	$('#myModal').modal();
}

/*编辑按钮，弹出对话框*/
tableView.get("#editTemp").onClick=function(){
	debugger;
	$("#operator_flag").val("1");
	var num = 0;
	var supplytype_id = "";
	$("#tempTable table tr td input").each(function(i,dom){
		
		if(dom.checked == true){
			num++;
			supplytype_id = dom.name;
		}
		
	});
	if(num>1){
		layer.alert("只能选择一条记录操作",{
			skin: 'layui-layer-molv'
		});
		return;
	}
	if(num==0){
		layer.alert("请选择一条记录操作",{
			skin: 'layui-layer-molv'
		});
		return;
	}
	view.get("#getSuppTypeInfo").set("parameter",{supplytype_id:supplytype_id}).execute(function(obj){
		
		var supplytype_id = $("#supplytype_id").val(obj.supplytype_id);
		var supply_code = $("#supply_code").val(obj.supply_code);
		var type_name = $("#type_name").val(obj.type_name);
		var type_orther = $("#type_orther").val(obj.type_orther);
		$('#myModal').modal();
	});

}

/*删除按钮，弹出对话框*/
tableView.get("#delTemp").onClick=function(){
	
	var num = 0;
	var supplytype_id = new Array();
	$("#tempTable table tr td input").each(function(i,dom){
		
		if(dom.checked == true){
			num++;
			supplytype_id.push(dom.name);
		}
		
	});
	
	if(num==0){
		layer.alert("请至少选择一条记录操作",{
			skin: 'layui-layer-molv'
		});
		return;
	}
	
	layer.confirm("您确定要删除所选信息吗?",{
		skin: 'layui-layer-molv', 
		btn: ['确定','取消']
	},function(){
		tableView.get("#delTempAjax").set("parameter",supplytype_id.toString()).execute(function(obj){
			if(obj == "success"){
				layer.alert("删除成功！",{
					skin: 'layui-layer-molv',
				});
				
			}else{
				layer.alert("删除失败，请联系技术人员！",{
					skin: 'layui-layer-molv',
				});
			}
		});
	},function(){
		return;
	})
	
	
}

/*保存按钮，保存数据*/
$("#saveAll").bind('click',function(){
	
	var flag = $("#operator_flag").val();
	var supplytype_id = $("#supplytype_id").val();
	var supply_code = $("#supply_code").val();
	var type_name = $("#type_name").val();
	var type_orther = $("#type_orther").val();
	
	
	var Temp =  {
			supplytype_id : supplytype_id,
			supply_code : supply_code,
			type_name : type_name,
			type_orther : type_orther
	}
	
	view.get("#dsSupplieType").insert(Temp);
	var updateTemp;
	if(flag == "0"){
		updateTemp=view.id("add");
	}else{
		Temp['supplytype_id'] = $("#supplytype_id").val();
		updateTemp=view.id("updateTemp");
	}

	updateTemp.set("parameter",Temp).execute(function(result) {
		if(result =="success"){
			swal({title:"提示",text:"保存成功！",type:"success"});
		}else{
			swal({title:"提示",text:"保存失败！",type:"error"});
		}
	});

	$('#myModal').modal('hide');
	
});

/*全选点击事件*/
$("#allCheck").change(function(){
	if($("#allCheck").is(":checked")){
		
		$("#tempTable table tr td input").attr("checked",true);
	}else{
		
		$("#tempTable table tr td input").attr("checked",false);
	}
	
});

/*页面初始化*/
function init() {
	initFrame();
	
	$(document).ready(
			function(){
				$(".dataTable-example").dataTable({
					"autoWidth":true,
					"lengthChange":false
				});			
			}
	);
}


function initFrame() {
	var menu = view.id("dsSupplieType").get("data").toArray();
	$("#tempTable table").append(getMenuHtml(menu,true));

}

/*初始化表格及数据*/
function getMenuHtml(menuData,isFirstLevel){
    var html = [];
	html.push('<tbody>');
	for (var i = 0; i < menuData.length; i++) {
		html.push('<tr class="gradeX">');
		html.push('<td><div class="checkbox i-checks sub-check"><input type = "checkbox" name="'+menuData[i]._data.supplytype_id+'" /></div></td>');
		html.push('<td>'+menuData[i]._data.supply_code+'</td>');
		html.push('<td>'+menuData[i]._data.type_name+'</td>');
		html.push('<td>'+menuData[i]._data.type_orther+'</td>');
		html.push('</tr>');
	}
	html.push('</tbody>');
	
	return html.join("");
	
}


//@Bind #tableView.onReady
!function(){
	
	init();
} ;
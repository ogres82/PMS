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
	
	var positions = view.get("#dsItemFiles").get("data").toArray();
	var depts = view.get("#dsSupplieType").get("data").toArray();
	$("#item_id option").remove();
	$("#supply_code option").remove();
	
	$("#item_id").append("<option></option>");
	$("#supply_code").append("<option></option>");
	var pos_str = "";
	var dept_str = "";
	for(var i =0;i<positions.length;i++){
		var id = positions[i]._data.item_id;
		var name = positions[i]._data.item_name;
		pos_str += "<option value = '"+id+"'>"+name+"</option>";
	}
	
	for(var j =0;j<depts.length;j++){
		var id = depts[j]._data.supply_code;
		var name = depts[j]._data.type_name;
		dept_str += "<option value = '"+id+"'>"+name+"</option>";
	}
	
	$("#item_id").append(pos_str);
	$("#supply_code").append(dept_str);
	$("#operator_flag").val("0");
	$('#myModal').modal();
}


/*编辑按钮，弹出对话框*/
tableView.get("#editTemp").onClick=function(){
	
	$("#operator_flag").val("1");
	var num = 0;
	var item_id = "";
	$("#tempTable table tr td input").each(function(i,dom){
		
		if(dom.checked == true){
			num++;
			item_id = dom.name;
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
	view.get("#getItemfiles").set("parameter",{item_id:item_id}).execute(function(obj){
		var item_id = $("#item_id").val(obj.item_id);
		var item_name = $("#item_name").val(obj.item_name);
		var item_code = $("#item_code").val(obj.item_code);
		var bar_code = $("#bar_code").val(obj.bar_code);
		var item_type = $("#item_type").val(obj.item_type);
		debugger;
		var supply_code = $("#supply_code").val(obj.item_flag);
		var suppliy_num = $("#suppliy_num").val(obj.suppliy_num);
		var item_flagName = $("#item_flagName").val(obj.item_flagName);
		var item_unit = $("#item_unit").val(obj.item_unit);
		var stock_lowerlimit = $("#stock_lowerlimit").val(obj.stock_lowerlimit);
		var stock_uplimit = $("#stock_uplimit").val(obj.stock_uplimit);
		var defu_inprice = $("#defu_inprice").val(obj.defu_inprice);
		var defu_outprice = $("#defu_outprice").val(obj.defu_outprice);
		$('#myModal').modal();
	});

}

/*删除按钮，弹出对话框*/
tableView.get("#delTemp").onClick=function(){
	var num = 0;
	var item_id = new Array();
	$("#tempTable table tr td input").each(function(i,dom){
		
		if(dom.checked == true){
			num++;
			item_id.push(dom.name);
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
		tableView.get("#delItemFilesAjax").set("parameter",item_id.toString()).execute(function(obj){
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
	var item_id=$("#item_id").val();
	var item_code = $("#item_code").val();
	var bar_code = $("#bar_code").val();
	var item_name = $("#item_name").val();
	var item_type = $("#item_type").val();
	var supply_code = $("#supply_code").val();
	var suppliy_num = $("#suppliy_num").val();
	var item_flagName = $("#item_flagName").val();
	var item_unit = $("#item_unit").val();
	var stock_lowerlimit = $("#stock_lowerlimit").val();
	var stock_uplimit = $("#stock_uplimit").val();
	var defu_inprice = $("#defu_inprice").val();
	var defu_outprice = $("#defu_outprice").val();
	
	
	var Temp =  {
			item_id:item_id,
			item_code : item_code,
			bar_code : bar_code,
			item_name : item_name,
			item_type : item_type,
			item_flag : supply_code,
			suppliy_num : suppliy_num,
			item_flagName : item_flagName,
			item_unit : item_unit,
			stock_lowerlimit : stock_lowerlimit,
			stock_uplimit : stock_uplimit,
			defu_inprice : defu_inprice,
			defu_outprice : defu_outprice
		
	}
	
	view.get("#dsItemFiles").insert(Temp);
	var updateTemp=view.id("add");
	

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





function initFrame() {
	var menu = view.id("dsItemFiles").get("data").toArray();
	
	$("#tempTable table").append(getMenuHtml(menu,true));

}

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


/*初始化表格及数据*/
function getMenuHtml(menuData,isFirstLevel){
    var html = [];
	html.push('<tbody>');
	for (var i = 0; i < menuData.length; i++) {
		    html.push('<tr class="gradeX">');
		    var item_id = menuData[i]._data.item_id;
			var item_code = menuData[i]._data.item_code;
			var bar_code = menuData[i]._data.bar_code;
			var item_name = menuData[i]._data.item_name;
			var item_type = menuData[i]._data.item_type;
			var type_name = menuData[i]._data.type_name;
			var suppliy_num = menuData[i]._data.suppliy_num;
			html.push('<td><div class="checkbox i-checks sub-check"><input type = "checkbox" name="'+item_id+'" /></div></td>');
			html.push('<td>'+(item_code!=null?item_code:"")+'</td>');
			html.push('<td>'+(bar_code!=null?bar_code:"")+'</td>');
			html.push('<td>'+(item_name!=null?item_name:"")+'</td>');
			html.push('<td>'+(item_type!=null?item_type:"")+'</td>');
			
			html.push('<td>'+(type_name!=null?type_name:"")+'</td>');
			html.push('<td>'+(suppliy_num!=null?suppliy_num:"")+'</td>');
		   
		    html.push('</tr>')
		
	}
	html.push('</tbody>');
	html.push('<tfoot>');
	html.push('</tfoot>');
	
	return html.join("");
}


//@Bind #tableView.onReady
!function(){
	init();
} ;
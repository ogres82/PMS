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
	var positions = view.get("#dsSupplierInfo").get("data").toArray();
	//$("#suppliy_id option").remove();
	$("#operator_flag").val("0");
	$('#myModal').modal();
}

/*编辑按钮，弹出对话框*/
tableView.get("#editTemp").onClick=function(){
	$("#operator_flag").val("1");
	var num = 0;
	var suppliy_id = "";
	$("#tempTable table tr td input").each(function(i,dom){
		
		if(dom.checked == true){
			num++;
			suppliy_id = dom.name;
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
	view.get("#getSuppInfo").set("parameter",{suppliy_id:suppliy_id}).execute(function(obj){
		debugger;
		var suppliy_id = $("#suppliy_id").val(obj.suppliy_id);
		var suppliy_code = $("#suppliy_code1").val(obj.suppliy_code);
		var suppliy_name = $("#suppliy_name1").val(obj.suppliy_name);
		var link_name = $("#link_name1").val(obj.link_name);
		var link_phone = $("#link_phone1").val(obj.link_phone);
		var link_address = $("#link_address").val(obj.link_address);
		var fax = $("#fax").val(obj.fax);
		var zip_code = $("#zip_code").val(obj.zip_code);
		var url = $("#url").val(obj.url);
		var bank = $("#bank").val(obj.bank);
		var bank_account = $("#bank_account").val(obj.bank_account);
		var linke_moble = $("#linke_moble").val(obj.linke_moble);
		var qq = $("#qq").val(obj.qq);
		var other = $("#other").val(obj.other);
		
		$('#myModal').modal();
	});

}

/*删除按钮，弹出对话框*/
tableView.get("#delTemp").onClick=function(){
	debugger;
	var num = 0;
	var suppliy_id = new Array();
	$("#tempTable table tr td input").each(function(i,dom){
		
		if(dom.checked == true){
			num++;
			suppliy_id.push(dom.name);
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
		tableView.get("#delTempAjax").set("parameter",suppliy_id.toString()).execute(function(obj){
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
	debugger;
	var flag = $("#operator_flag").val();
	var suppliy_id = $("#suppliy_id").val();
	var suppliy_code = $("#suppliy_code1").val();
	var suppliy_name = $("#suppliy_name1").val();
	var link_name = $("#link_name1").val();
	var link_phone = $("#link_phone1").val();
	var link_address = $("#link_address").val();
	var fax = $("#fax").val();
	var zip_code = $("#zip_code").val();
	var url = $("#url").val();
	var bank = $("#bank").val();
	var bank_account = $("#bank_account").val();
	var linke_moble = $("#linke_moble").val();
	var qq = $("#qq").val();
	var other = $("#other").val();
	
	var Temp =  {
			suppliy_id : suppliy_id,
			suppliy_code : suppliy_code,
			suppliy_name : suppliy_name,
			link_name : link_name,
			link_phone : link_phone,
			link_address : link_address,
			fax : fax,
			zip_code : zip_code,
			url : url,
			bank : bank,
			bank_account : bank_account,
			linke_moble : linke_moble,
			qq : qq,
			other : other
	}
	
	view.get("#dsSupplierInfo").insert(Temp);
	var updateTemp;
	if(flag == "0"){
		updateTemp=view.id("add");
	}else{
		Temp['suppliy_id'] = $("#suppliy_id").val();
		updateTemp=view.id("updateTemp");
	}

	updateTemp.set("parameter",Temp).execute(function(result) {
		if(result =="success"){
			view.get("#dsSupplierInfo").flush();
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
	var menu = view.id("dsSupplierInfo").get("data").toArray();
	$("#tempTable table").append(getMenuHtml(menu,true));

}

/*初始化表格及数据*/
function getMenuHtml(menuData,isFirstLevel){
	
	var html = [];
	html.push('<tbody>');
	for (var i = 0; i < menuData.length; i++) {
		    html.push('<tr class="gradeX">');
		    var suppliy_id = menuData[i]._data.suppliy_id;
			var suppliy_code = menuData[i]._data.suppliy_code;
			var suppliy_name = menuData[i]._data.suppliy_name;
			var link_name = menuData[i]._data.link_name;
			var link_phone = menuData[i]._data.link_phone;
			var link_address = menuData[i]._data.link_address;
			var fax = menuData[i]._data.fax;
			var zip_code = menuData[i]._data.zip_code;
			html.push('<td><div class="checkbox i-checks sub-check"><input type = "checkbox" name="'+suppliy_id+'" /></div></td>');
			html.push('<td>'+(suppliy_code!=null?suppliy_code:"")+'</td>');
			html.push('<td>'+(suppliy_name!=null?suppliy_name:"")+'</td>');
			html.push('<td>'+(link_name!=null?link_name:"")+'</td>');
			html.push('<td>'+(link_phone!=null?link_phone:"")+'</td>');
			html.push('<td>'+(link_address!=null?link_address:"")+'</td>');
			html.push('<td>'+(fax!=null?fax:"")+'</td>');
			html.push('<td>'+(zip_code!=null?zip_code:"")+'</td>');
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
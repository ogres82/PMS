tcCore.loadCss($url(">/Hplus/css/plugins/sweetalert/sweetalert.css?v=4.1.0"));

tcCore.loadJs($url(">/Hplus/js/plugins/laydate/laydate.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/dataTables/jquery.dataTables.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/dataTables/dataTables.bootstrap.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/validate/jquery.validate.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/validate/messages_zh.min.js"));
tcCore.loadJs($url(">/Hplus/js/demo/form-validate-demo.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/sweetalert/sweetalert.min.js"));

//tcCore.loadJs($url(">/Hplus/css/jquery.dataTables.min.css"));

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


function init() {
	initFrame();	
	
}


function initFrame() {
	//var menu = view.id("dsChargeInfo").get("data").toArray();	
	//$("#tempTable table").append(getMenuHtml(menu,true));
	
	var aa = chargeInfoView.id("dsChargeInfo").getData().toJSON();
	
	//var table = $('#chargeInfo').DataTable();
	//table.destroy();
	var table = $('#chargeInfo').DataTable({
	    data: aa,
	    columns: [
	        { data: 'owner_name' },
	        { data: 'charge_type_no' },
	        { data: 'charge_type_name' },
	        { data: 'room_id' }
	    ]
	});
	
	$('#chargeInfo tbody').on( 'click', 'tr', function(){
	    if ($(this).hasClass('selected')) {
	        $(this).removeClass('selected');
	    }
	    else {
	        table.$('tr.selected').removeClass('selected');
	        $(this).addClass('selected');
	    }
	} );
	
}

function getMenuHtml(menuData,isFirstLevel){
	
	var html = [];
	
	html.push('<tbody>');
	for (var i = 0; i < menuData.length; i++) {
		html.push('<tr class="gradeX">');
			html.push('<td>'+menuData[i]._data.owner_name+'</td>');
			html.push('<td>'+menuData[i]._data.charge_type_name+'</td>');
			html.push('<td>'+menuData[i]._data.room_no+'</td>');
			html.push('<td class="center">'+menuData[i]._data.receive_amount+'</td>');
			html.push('<td class="center">'+menuData[i]._data.paid_amount+'</td>');
			html.push('<td>'+menuData[i]._data.state+'</td>');
			html.push('<td>'+menuData[i]._data.paid_date+'</td>');
		html.push('</tr>');
	}
	html.push('</tbody>');
	
	return html.join("");
	
}

/*新增按钮，弹出对话框*/
/*
chargeInfoView.get("#addTemp").onClick=function(){
	var positions = view.get("#dsPosition").get("data").toArray();
	var depts = view.get("#dsDept").get("data").toArray();
	$("#empPostId option").remove();
	$("#empDeptId option").remove();
	
	$("#empPostId").append("<option></option>");
	$("#empDeptId").append("<option></option>");
	var pos_str = "";
	var dept_str = "";
	for(var i =0;i<positions.length;i++){
		var id = positions[i]._data.id;
		var name = positions[i]._data.name;
		pos_str += "<option value = '"+id+"'>"+name+"</option>";
	}
	for(var j =0;j<depts.length;j++){
		var id = depts[j]._data.id;
		var name = depts[j]._data.name;
		dept_str += "<option value = '"+id+"'>"+name+"</option>";
	}
	
	$("#empPostId").append(pos_str);
	$("#empDeptId").append(dept_str);
	$("#operator_flag").val("0");
	 
	$('#myModal').modal();
}
*/
/*编辑按钮，弹出对话框*/
chargeInfoView.get("#editTemp").onClick=function(){
	
	var table = $('#chargeInfo').DataTable();
	
	var obj = table.row('.selected').data();
		
	var empId = $("#charge_no").val(obj.charge_no);
	var empNo = $("#room_no").val(obj.room_no);
	var empName = $("#owner_name").val(obj.owner_name);
	var empSex = $("#charge_type_no").val(obj.charge_type_no);
	var empDeptId = $("#charge_type_name").val(obj.charge_type_name);
	var empPostId = $("#price").val(obj.price);
	var empBirth = $("#count").val(obj.count);
	var empIdNo = $("#rate").val(obj.rate);
	var empNation = $("#begin_time").val(obj.begin_time);
	var empNp = $("#end_time").val(obj.end_time);
	var empPostLev = $("#receive_amount").val(obj.receive_amount);
	var empTitle = $("#paid_amount").val(obj.paid_amount);
	var empPhone = $("#arrearage_amount").val(obj.arrearage_amount);
	var empEmail = $("#state").val(obj.state);
	var empContact = $("#paid_mode").val(obj.paid_mode);
	var empContactPhone = $("#paid_date").val(obj.paid_date);
	var empEntryTime = $("#oper_emp_id").val(obj.oper_emp_id);
	var empQuitTime = $("#update_date").val(obj.update_date);
	var empStatus = $("#remark").val(obj.remark);
	var charge_id = $("#charge_id").val(obj.charge_id);
	
	var ttt = $("#myModal").modal();
}

chargeInfoView.get("#delTemp").onClick=function(){
	var table = $('#chargeInfo').DataTable();
	table.row('.selected').remove().draw( false );	 
}


//@Bind #chargeInfoView.onReady
!function(){
	init();
} ;

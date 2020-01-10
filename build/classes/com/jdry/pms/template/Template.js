/*tcCore.loadCss($url(">/Hplus/css/bootstrap.min.css?v=3.3.6"));
tcCore.loadCss($url(">/Hplus/css/font-awesome.min.css?v=4.4.0"));
tcCore.loadCss($url(">/Hplus/css/animate.min.css"));
tcCore.loadCss($url(">/Hplus/css/style.min.css?v=4.1.0"));
tcCore.loadCss($url(">/Hplus/css/plugins/dataTables/dataTables.bootstrap.css?v=4.1.0"));*/

tcCore.loadCss($url(">/Hplus/css/plugins/sweetalert/sweetalert.css?v=4.1.0"));
tcCore.loadCss($url(">/Hplus/css/plugins/bootstrap-table/bootstrap-table.min.css"));

tcCore.loadJs($url(">/Hplus/js/plugins/laydate/laydate.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/dataTables/jquery.dataTables.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/dataTables/dataTables.bootstrap.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/validate/jquery.validate.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/validate/messages_zh.min.js"));
tcCore.loadJs($url(">/Hplus/js/demo/form-validate-demo.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/sweetalert/sweetalert.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/suggest/bootstrap-suggest.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/bootstrap-table/bootstrap-table.min.js"));
tcCore.loadJs($url(">/Hplus/js/plugins/bootstrap-table/bootstrap-table-zh-CN.min.js"));
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

/*编辑按钮，弹出对话框*/
tableView.get("#editTemp").onClick=function(){
	$("#operator_flag").val("1");
	var num = 0;
	var empId = "";
	$("#tempTable table tr td input").each(function(i,dom){
		
		if(dom.checked == true){
			num++;
			empId = dom.name;
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
	view.get("#getTemp").set("parameter",{empId:empId}).execute(function(obj){
		
		var empId = $("#empId").val(obj.empId);
		var empNo = $("#empNo").val(obj.empNo);
		var empName = $("#empName").val(obj.empName);
		var empSex = $("#empSex").val(obj.empSex);
		var empDeptId = $("#empDeptId").val(obj.empDeptId);
		var empPostId = $("#empPostId").val(obj.empPostId);
		var empBirth = $("#empBirth").val(obj.empBirth);
		var empIdNo = $("#empIdNo").val(obj.empIdNo);
		var empNation = $("#empNation").val(obj.empNation);
		var empNp = $("#empNp").val(obj.empNp);
		var empPostLev = $("#empPostLev").val(obj.empPostLev);
		var empTitle = $("#empTitle").val(obj.empTitle);
		var empPhone = $("#empPhone").val(obj.empPhone);
		var empEmail = $("#empEmail").val(obj.empEmail);
		var empContact = $("#empContact").val(obj.empContact);
		var empContactPhone = $("#empContactPhone").val(obj.empContactPhone);
		var empEntryTime = $("#empEntryTime").val(obj.empEntryTime);
		var empQuitTime = $("#empQuitTime").val(obj.empQuitTime);
		var empStatus = $("#empStatus").val(obj.empStatus);
		
		$('#myModal').modal();
	});

}

/*删除按钮，弹出对话框*/
tableView.get("#delTemp").onClick=function(){
	
	var num = 0;
	var empId = new Array();
	$("#tempTable table tr td input").each(function(i,dom){
		
		if(dom.checked == true){
			num++;
			empId.push(dom.name);
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
		tableView.get("#delTempAjax").set("parameter",empId.toString()).execute(function(obj){
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
	
	/*swal({
			title:"您确定要删除这条信息吗",text:"删除后将无法恢复，请谨慎操作！",
			type:"warning",
			showCancelButton:true,
			confirmButtonColor:"#DD6B55",
			confirmButtonText:"是的，我要删除！",
			cancelButtonText:"让我再考虑一下…",
			closeOnConfirm:false,
			closeOnCancel:false
			},
			function(isConfirm){
				if(isConfirm){
					
					tableView.get("#delTempAjax").set("parameter",empId.toString()).execute(function(obj){
						if(obj == "success"){
							swal("删除成功！","您已经永久删除了这条信息。","success")
							
						}else{
							swal({title:"提示",text:"删除失败！",type:"error"});
						}
					});
					
					
				}else{
					swal("已取消","您取消了删除操作！","error")
				}
			}
		);*/

}

/*保存按钮，保存数据*/
$("#saveAll").bind('click',function(){
	
	var flag = $("#operator_flag").val();
	var empNo = $("#empNo").val();
	var empName = $("#empName").val();
	var empSex = $("#empSex").val();
	var empDeptId = $("#empDeptId").val();
	var empPostId = $("#empPostId").val();
	var empBirth = $("#empBirth").val();
	var empIdNo = $("#empIdNo").val();
	var empNation = $("#empNation").val();
	var empNp = $("#empNp").val();
	var empPostLev = $("#empPostLev").val();
	var empTitle = $("#empTitle").val();
	var empPhone = $("#empPhone").val();
	var empEmail = $("#empEmail").val();
	var empContact = $("#empContact").val();
	var empContactPhone = $("#empContactPhone").val();
	var empEntryTime = $("#empEntryTime").val();
	var empQuitTime = $("#empQuitTime").val();
	var empStatus = $("#empStatus").val();
	
	var Temp =  {
	    empNo : empNo,
	    empName : empName,
	    empSex : empSex,
	    empDeptId : empDeptId,
		empPostId : empPostId,
		empBirth : empBirth,
		empIdNo : empIdNo,
		empNation : empNation,
		empNp : empNp,
		empPostLev : empPostLev,
		empTitle : empTitle,
		empPhone : empPhone,
		empEmail : empEmail,
		empContact : empContact,
		empContactPhone : empContactPhone,
		empEntryTime : new Date(),
		empQuitTime : new Date(),
		empStatus : empStatus
	}
	
	view.get("#dsTemp").insert(Temp);
	var updateTemp;
	if(flag == "0"){
		updateTemp=view.id("add");
	}else{
		Temp['empId'] = $("#empId").val();
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

var dataList = {value: []}, i = 5001;
while(i--) {
    dataList.value.push({
        id: i,
        word: Math.random() * 100000,
        description: 'http://lzw.me'
    });
}


/*搜索框初始化*/
var testBsSuggest = $("input#test").bsSuggest({
    url: "http://localhost:8080/PMS/search.app?keyword=",
    showHeader: true,
    allowNoKeyword: true,
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
                "ownerId": json.result[6],
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
	    alert(JSON.stringify(data));
	}).on("onUnsetSelectValue",	
	function(e) {
	    console.log("onUnsetSelectValue")
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
	var menu = view.id("dsTemp").get("data").toArray();
	$("#tempTable table").append(getMenuHtml(menu,true));

}

/*初始化表格及数据*/
function getMenuHtml(menuData,isFirstLevel){
	
	var html = [];
	
	html.push('<tbody>');
	for (var i = 0; i < menuData.length; i++) {
		html.push('<tr class="gradeX">');
		var empId = menuData[i]._data.empId;
			var empNo = menuData[i]._data.empNo;
			var empName = menuData[i]._data.empName;
			var empSex = menuData[i]._data.empSex;
			if(empSex!=null && empSex!=""){
				empSex = empSex==0?"男":"女";
			}
			var empBirth = menuData[i]._data.empBirth;
			if(empBirth!=null){
				empBirth = empBirth.substring(0,10);
			}
			var empIdNo = menuData[i]._data.empIdNo;
			html.push('<td><div class="checkbox i-checks sub-check"><input type = "checkbox" name="'+empId+'" /></div></td>');
			html.push('<td>'+(empNo!=null?empNo:"")+'</td>');
			html.push('<td>'+(empName!=null?empName:"")+'</td>');
			html.push('<td>'+(empSex!=null?empSex:"")+'</td>');
			html.push('<td class="center">'+(empBirth!=null?empBirth:"")+'</td>');
			html.push('<td class="center">'+(empIdNo!=null?empIdNo:"")+'</td>');
		html.push('</tr>');
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
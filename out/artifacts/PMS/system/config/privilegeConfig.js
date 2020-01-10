$(function () {
	//根据屏幕分辨率设置ibox高度
	var  frameHeight = $(document.body).height()-10+'px';
	$(".ibox-content").height(frameHeight);
	
	//初始化所有岗位
	initDrops();
	initData();
});


function initDrops(){
	$.ajax({
        type: "post",
        url: "./../../system/privilege.app?method=initDrops",
        dataType: "json",
		async : false,
        success: function(data)
        {
        	var list = eval(data);
        	for(var j=0;j<list.length;j++){
        		 var position = list[j];
				 $("<option value='"+position.id+"'>"+position.name+"</option>").appendTo("#positionName1");
				 $("<option value='"+position.id+"'>"+position.name+"</option>").appendTo("#positionName2");
				 $("<option value='"+position.id+"'>"+position.name+"</option>").appendTo("#positionName3");
				 $("<option value='"+position.id+"'>"+position.name+"</option>").appendTo("#positionName4");
        	}
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！',{
				time: 1500
			}, function(){
				
			});
        }
    });
}


function save(){
	$.ajax({
        type: "post",
        url: "./../../system/privilege.app?method=save",
        dataType: "json",
        data: getJsonData(),
		async : false,
        success: function(data)
        {
        	layer.msg('操作成功！',{
				time: 1500
			}, function(){
				
			});
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！',{
				time: 1500
			}, function(){
				
			});
        }
    });
}

function getJsonData(){
	var data = {
		id1:$("#id1").val(),
		id2:$("#id2").val(),
		id3:$("#id3").val(),
		id4:$("#id4").val(),
		type1:$("#type1").val(),
		type2:$("#type2").val(),
		type3:$("#type3").val(),
		type4:$("#type4").val(),
		positionId1:$("#positionName1").val(),
		positionId2:$("#positionName2").val(),
		positionId3:$("#positionName3").val(),
		positionId4:$("#positionName4").val(),
		positionName1:$("#positionName1").find("option:selected").text(), 
		positionName2:$("#positionName2").find("option:selected").text(), 
		positionName3:$("#positionName3").find("option:selected").text(), 
		positionName4:$("#positionName4").find("option:selected").text(),
	};
	return data;
}


function initData(){
	$.ajax({
        type: "post",
        url: "./../../system/privilege.app?method=initData",
        dataType: "json",
		async : false,
        success: function(data)
        {
        	debugger;
        	var list = eval(data);
        	for(var j=0;j<list.length;j++){
        		if(list[j].type == "01"){
        			$("#positionName1").val(list[j].positionId);
        			$("#id1").val(list[j].id);
        		}
        		if(list[j].type == "02"){
        			$("#positionName2").val(list[j].positionId);
        			$("#id2").val(list[j].id);
        		}
        		if(list[j].type == "03"){
        			$("#positionName3").val(list[j].positionId);
        			$("#id3").val(list[j].id);
        		}
        		if(list[j].type == "04"){
        			$("#positionName4").val(list[j].positionId);
        			$("#id4").val(list[j].id);
        		}
        	}
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！',{
				time: 1500
			}, function(){
				
			});
        }
    });
}
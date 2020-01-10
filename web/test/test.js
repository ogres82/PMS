function testGet(){
	var value = encodeURI('贵A81X88');
	var param = "plateNo="+value+"&pageNo=1&pageSize=15";
	$.ajax({
        type: "post",
        url: "../hikvision.app?method=/pms/getPlatFixedCardRechargeRecord",
        dataType: "json",
        data: {param:param},
		async : false,
        success: function(data)
        {
        	alert(data);
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！',{
				time: 2000
			}, function(){
				
			});
        }
    });
}

function testPost(){
	var param = {
			plateNo:'贵A88X89',
			ownerId:1,
			cardNo :'',
			carType :1,
//			carColor :0,
			plateColor :0,
			plateType :0,
			plateStart :'',
			carBrand :'',
			driver :'徐磊',
			driverPhone :'',
			num :5,
				};
	$.ajax({
        type: "post",
        url: "../hikvision.app?method=/pms/addPlatParkCarInfo",
        dataType: "json",
        data: param,
		async : false,
        success: function(data)
        {
        	alert(data);
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！',{
				time: 2000
			}, function(){
				
			});
        }
    });
}


function testCharge(){
	var param = {
				
				carIndexCode:'51fa3b2791df42b797cc5ebf15faf3b4',
				parkingSyscode:'eb3c23210bd74a9f933113ca37c933c0',
				startTime :'2016-05-21',
				endTime :'2017-01-21',
//				carColor :0,
				money :500,
				};
	$.ajax({
        type: "post",
        url: "../hikvision.app?method=/pms/reChargePlatCar",
        dataType: "json",
        data: param,
		async : false,
        success: function(data)
        {
        	alert(data);
        },
        failure:function(xhr,msg)
        {
        	layer.msg('操作失败！',{
				time: 2000
			}, function(){
				
			});
        }
    });
}

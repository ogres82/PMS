$(function() {
	initSearch();
});

function a() {
	$("#houseInfo").fadeOut();
	$("#houseInfo").slideDown(800);
}

function initSearch() {
	var addressSuggest = $("input#searchInput").bsSuggest({
		url : encodeURI("./../search.app?type=house&keyword="),
		showHeader : true,
		allowNoKeyword : false,
		showBtn : false,
		keyField : 'roomNo',
		// autoSelect:true,
		getDataMethod : "url",
		delayUntilKeyup : true,
		effectiveFields : [ "roomNo", "ownerName", "phone" ],
		effectiveFieldsAlias : {
			roomNo : "房间号",
			ownerName : "业主名",
			phone : "手机号"
		},
		// 预处理
		fnPreprocessKeyword : function(keyword) {
			// 请求数据前，对输入关键字作预处理
			return encodeURI(encodeURI(keyword));
		},
		processData : function(json) { // url 获取数据时，对数据的处理，作为 getData 的回调函数
			var index, len, data = {
				value : []
			};
			if (!json || !json.result || json.result.length === 0) {
				return false;
			}

			len = json.result.length;

			for (index = 0; index < len; index++) {
				data.value.push({
					"searchInput" : json.result[index][0] + "-" + json.result[index][1] + "-" + json.result[index][2] + "（" + json.result[index][18] + "）",
					"community" : json.result[index][0],
					"building" : json.result[index][1],
					"roomNo" : json.result[index][2],
					"roomType" : json.result[index][3],
					"buildArea" : json.result[index][4],
					"withinArea" : json.result[index][5],
					"roomState" : json.result[index][6],
					"receiveRoomDate" : json.result[index][7],
					"decorateStartDate" : json.result[index][8],
					"decorateEndDate" : json.result[index][9],
					"price" : json.result[index][10],
					"paidDate" : json.result[index][11],
					"paidAmount" : json.result[index][12],
					"roomId" : json.result[index][13],
					"receiveTotal" : json.result[index][14],
					"paidTotal" : json.result[index][15],
					"arrearageTotal" : json.result[index][16],
					"amount" : json.result[index][17],
					"ownerName" : json.result[index][18],
					"phone" : json.result[index][19],
					"telPhone" : json.result[index][20],
					"cardId" : json.result[index][21],
					"birthDate" : json.result[index][22],
					"carId" : json.result[index][23],
					"ownerType" : json.result[index][24],
					"ownerIdentity" : json.result[index][25]
				});
			}
			// 字符串转化为 json 对象

			return data;
		}
	}).on("onDataRequestSuccess", function(e, result) {
		console.log("onDataRequestSuccess: ", result)
	}).on("onSetSelectValue", function(e, keyword, data) {
		console.log("onSetSelectValue: ", keyword, data);
		$("#community").html(data.community);
		$("#roomNo").html(data.roomNo);
		$("#building").html(data.building);
		$("#roomType").html(roomType(data.roomType));
		$("#buildArea").html(data.buildArea);
		$("#withinArea").html(data.withinArea);
		$("#roomState").html(roomState(data.roomState));
		$("#receiveRoomDate").html(json2Date(data.receiveRoomDate));
		$("#decorateStartDate").html(json2Date(data.decorateStartDate));
		$("#decorateEndDate").html(json2Date(data.decorateEndDate));
		$("#price").html(moneyFormat(data.price));
		$("#paidDate").html(json2Date(data.paidDate));
		$("#paidAmount").html(moneyFormat(data.paidAmount));
		$("#receiveTotal").html(moneyFormat(data.receiveTotal));
		$("#paidTotal").html(moneyFormat(data.paidTotal));
		$("#arrearageTotal").html(moneyFormat(data.arrearageTotal));
		$("#amount").html(moneyFormat(data.amount));
		$("#ownerName").html(data.ownerName);
		$("#phone").html(data.phone);
		$("#cardId").html(data.cardId);
		$("#birthDate").html(json2Date(data.birthDate));
		$("#ownerType").html(ownerType(data.ownerType));
		$("#ownerIdentity").html(ownerIden(data.ownerIdentity));
		$("#houseInfo").fadeOut();
		$("#houseInfo").slideDown(800);
		$("#searchInput").val(data.searchInput);
	}).on("onUnsetSelectValue", function(e) {
		console.log("onUnsetSelectValue")
	});

}

function json2TimeStamp(milliseconds) {
	if (milliseconds == null || milliseconds == "") {
		return "";
	}
	var datetime = new Date();
	datetime.setTime(milliseconds);
	var year = datetime.getFullYear();
	// 月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}
// 毫秒转日期YYYY-MM-DD
function json2Date(milliseconds) {
	if (milliseconds == null || milliseconds == "") {
		return "";
	}
	var datetime = new Date();
	datetime.setTime(milliseconds);
	var year = datetime.getFullYear();
	// 月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	return year + "-" + month + "-" + date;
}

moneyFormat = function(value, row) {
	return outputmoney("" + value);
};

function roomType(value) {
	if (value == "0") {
		return "高层";
	} else if (value == "1") {
		return "洋房";
	} else if (value == "2") {
		return "别墅";
	} else {
		return "其他";
	}
}

function roomState(value) {
	if (value == "0") {
		return "已售";
	} else if (value == "1") {
		return "交房";
	} else if (value == "2") {
		return "接房";
	} else if (value == "3") {
		return "入住";
	} else {
		return "其他";
	}
}

function ownerIden(value) {
	if (value == "0") {
		return "业主";
	} else {
		return "其他";
	}
}
function ownerType(value) {
	if (value == "0") {
		return "一般";
	} else {
		return "重要";
	}
}
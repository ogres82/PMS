//获取项目根路径
function getRootPath(){
    // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    // 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    // 获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    // 获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+2);
    return(localhostPaht+projectName);
}

// 获取文件根路径
function getFilePath(){
    // 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    // 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    // 获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    // 获取带"/"的项目名，如：/uimcardprj
    return(localhostPaht+"/file/");
}


// 毫秒转时间YYYY-MM-DD hh:mm:ss
function json2TimeStamp(milliseconds){
	if(milliseconds==null || milliseconds==""){
		return "";
	}
    var datetime = new Date();
    datetime.setTime(milliseconds);
    var year=datetime.getFullYear();
         // 月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
}
// 毫秒转日期YYYY-MM-DD
function json2Date(milliseconds){
	if(milliseconds==null || milliseconds==""){
		return "";
	}
    var datetime = new Date();
    datetime.setTime(milliseconds);
    var year=datetime.getFullYear();
         // 月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date;
}



function getNowFormatDate(flag,vardate) {
	if(flag == false && (vardate==null || vardate==''))
		return null;
	var date;
	if(flag==true){
		date = new Date();
	}else{
		date=new Date(vardate);
	}
	var seperator1 = "-";
	var seperator2 = ":";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	var hour=date.getHours();       // 获取当前小时数(0-23)
	var min=date.getMinutes();     // 获取当前分钟数(0-59)
	var sec=date.getSeconds();     // 获取当前秒数(0-59)
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	if (hour >= 1 && hour <= 9) {
		hour = "0" + hour;
	}
	if (min >= 0 && min <= 9) {
		min = "0" + min;
	}if (sec >= 0 && sec <= 9) {
		sec = "0" + sec;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate
	+ " " + hour + seperator2 + min
	+ seperator2 + sec;
	return currentdate;
}

function dateFormat(now,mask)
{
    var d = now;
    var zeroize = function (value, length)
    {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++)
        {
            zeros += '0';
        }
        return zeros + value;
    };
 
    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0)
    {
        switch ($0)
        {
            case 'd': return d.getDate();
            case 'dd': return zeroize(d.getDate());
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
            case 'M': return d.getMonth() + 1;
            case 'MM': return zeroize(d.getMonth() + 1);
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
            case 'yy': return String(d.getFullYear()).substr(2);
            case 'yyyy': return d.getFullYear();
            case 'h': return d.getHours() % 12 || 12;
            case 'hh': return zeroize(d.getHours() % 12 || 12);
            case 'H': return d.getHours();
            case 'HH': return zeroize(d.getHours());
            case 'm': return d.getMinutes();
            case 'mm': return zeroize(d.getMinutes());
            case 's': return d.getSeconds();
            case 'ss': return zeroize(d.getSeconds());
            case 'l': return zeroize(d.getMilliseconds(), 3);
            case 'L': var m = d.getMilliseconds();
                if (m > 99) m = Math.round(m / 10);
                return zeroize(m);
            case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
            case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
            case 'Z': return d.toUTCString().match(/[A-Z]+$/);
            // Return quoted strings with the surrounding quotes removed
            default: return $0.substr(1, $0.length - 2);
        }
    });
};

// 删除指定字符串数组元素
function deleteArray(a,str){
	var index = $.inArray(str,a);
	if(index>=0){
		// arrayObject.splice(index,howmany,item1,.....,itemX)
		// 参数 描述
		// index 必需。整数，规定添加/删除项目的位置，使用负数可从数组结尾处规定位置。
		// howmany 必需。要删除的项目数量。如果设置为 0，则不会删除项目。
		// item1, ..., itemX 可选。向数组添加的新项目。
	  a.splice(index,1);
	  return a;
	}else{
	  alert("error");
	  return false;
	}
}


// 图片缩小处理
function DrawImage(ImgD, imageWidth, imageHeight) {
    var image = new Image();
    image.src = ImgD.src;
    if (image.width > 0 && image.height > 0) {
        flag = true;
        if (image.width / image.height >= imageWidth / imageHeight) {
            if (image.width > imageWidth) {
                ImgD.width = imageWidth;
                ImgD.height = (image.height * imageWidth) / image.width;
            } else {
                ImgD.width = image.width;
                ImgD.height = image.height;
            }
        }
        else {
            if (image.height > imageHeight) {
                ImgD.height = imageHeight;
                ImgD.width = (image.width * imageHeight) / image.height;
            } else {
                ImgD.width = image.width;
                ImgD.height = image.height;
            }
        }
    }
}

// 构造菜单节点结构
function Node(text,nodes,href,icon,url,desc,order,parentid,forNavigation){
	this.text = text;
	this.nodes = nodes;
	this.href = href;
	this.icon = icon;
	this.order = order;
	this.desc = desc;
	this.url = url;
	this.parentid = parentid;
	this.forNavigation = forNavigation;
}

// 获取生成treeview菜单数据结构
function getTreeData(menuData){
	var firstLevel = [];
	var secondLevel = [];
	var thirdLevel = [];
	var length1 = menuData.length;
	firstOrder = length1;
	for(var i=0;i<length1;i++){
		var firstMenu = menuData[i];
		var firstNode = new Node();
		firstNode.text = firstMenu.name;
		firstNode.href = firstMenu.id;
		firstNode.forNavigation = firstMenu.forNavigation;
		firstNode.icon = firstMenu.icon;
		firstNode.url = firstMenu.url;
		firstNode.desc = firstMenu.desc;
		firstNode.parentid = firstMenu.parentId;
		firstNode.order = firstMenu.order;
		var subMenu = firstMenu.children;
		if(typeof(subMenu)!="undefined"){
			secondLevel = [];
			var length2 = subMenu.length;
			for(var j=0;j<length2;j++){
				var secMenu = subMenu[j];
				var secondNode = new Node();
				secondNode.text = secMenu.name;
				secondNode.href = secMenu.id;
				secondNode.forNavigation = secMenu.forNavigation;
				secondNode.icon = secMenu.icon;
				secondNode.url = secMenu.url;
				secondNode.desc = secMenu.desc;
				secondNode.parentid = secMenu.parentId;
				secondNode.order = secMenu.order;
// loadUrls(secMenu.id);
				var thirdMenu = secMenu.children;
				if(typeof(thirdMenu)!="undefined"){
					thirdLevel = [];
					var length3 = thirdMenu.length;
					for(var k=0;k<length3;k++){
						var trdMenu = thirdMenu[k];
						var thirdNode = new Node();
						thirdNode.text = trdMenu.name;
						thirdNode.href = trdMenu.id;
						thirdNode.forNavigation = trdMenu.forNavigation;
						thirdNode.icon = trdMenu.icon;
						thirdNode.url = trdMenu.url;
						thirdNode.desc = trdMenu.desc;
						thirdNode.parentid = trdMenu.parentId;
						thirdNode.order = trdMenu.order;
						thirdLevel.push(thirdNode);
					}
					secondNode.nodes = thirdLevel;
				}
				secondLevel.push(secondNode);
			}
			firstNode.nodes = secondLevel;
		}
		firstLevel.push(firstNode);
	}
	return firstLevel;
}

/**
 * 遮掉层，加载中... 效果
 */
function initBlockUI(){
	$.blockUI({
    	message:'<img src="'+getRootPath()+'Hplus/img/loading.gif" /><h5 style="margin-left:15px">处理中...</h5>',
    	baseZ:2100,
    	css:{
    		border: 'none', 
            padding: '20px', 
            backgroundColor: '#fff', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .7, 
            color: '#000',
            borderRadius:'10px'
    	}
    }); 
}


// 二维码中文处理
function toUtf8(str) {   
    var out, i, len, c;   
    out = "";   
    len = str.length;   
    for(i = 0; i < len; i++) {   
    	c = str.charCodeAt(i);   
    	if ((c >= 0x0001) && (c <= 0x007F)) {   
        	out += str.charAt(i);   
    	} else if (c > 0x07FF) {   
        	out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));   
        	out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));   
        	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
    	} else {   
        	out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));   
        	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
    	}   
    }   
    return out;   
} 

// 创建二维码 elementId:创建二维码的div id；strContent：二维码内容；
function createQrcode(elementId,strContent,width,height){
	var str = toUtf8(strContent);
	$("#"+elementId).empty();
 	$("#"+elementId).qrcode({
 		render : "canvas",
 		width : width,
 		height : height,
 		text : str
 	});
}

/**
 * 合并单元格
 * 
 * @param data
 *            原始数据（在服务端完成排序）
 * @param fieldName
 *            合并属性名称
 * @param colspan
 *            合并列
 * @param target
 *            目标表格对象
 */
function mergeCells(data, fieldName, colspan, target) {
	// 声明一个map计算相同属性值在data对象出现的次数和
	var sortMap = {};
	for (var i = 0; i < data.length; i++) {
		for ( var prop in data[i]) {
			if (prop == fieldName) {
				var key = data[i][prop]
				if (sortMap.hasOwnProperty(key)) {
					sortMap[key] = sortMap[key] * 1 + 1;
				} else {
					sortMap[key] = 1;
				}
				break;
			}
		}
	}
	for ( var prop in sortMap) {
		console.log(prop, sortMap[prop])
	}
	var index = 0;
	for ( var prop in sortMap) {
		var count = sortMap[prop] * 1;
		$(target).bootstrapTable('mergeCells', {
			index : index,
			field : fieldName,
			colspan : colspan,
			rowspan : count
		});
		index += count;
	}
}


//判断字符是否为空的方法
function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}

Date.prototype.AddMonths = function (m) {
	var temp = new Date(this.getFullYear(), this.getMonth(), this.getDate(), this.getHours(), this.getMinutes(), this.getSeconds(), this.getMilliseconds());
	temp.setMonth(temp.getMonth() + m);
	return temp;
}
Date.prototype.format = function (fmt) {
	var o = {
		"M+": this.getMonth() + 1, // 月份
		"d+": this.getDate(), // 日
		"h+": this.getHours(), // 小时
		"m+": this.getMinutes(), // 分
		"s+": this.getSeconds(), // 秒
		"q+": Math.floor((this.getMonth() + 3) / 3), // 季度
		"S": this.getMilliseconds()
		// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
			.substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) :
				(("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
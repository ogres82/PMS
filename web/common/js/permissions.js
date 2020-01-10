var table_btn = new Array();
var toolbar_btn = new Array();
var btnIdArray = new Array();

function findIndex(obj,val) {
	for (var i = 0; i < obj.length; i++) {
		if (obj[i].btnId == val) return i;
	}
	return -1;
};

function arrayRemove(obj,val){	
	var index = findIndex(obj,val);
	if (index > -1) {
		return obj.splice(index, 1);
	}
}

//工具条按钮初始化
function toolbarBtnInit(){
	var pathName = window.document.location.pathname;
	var url=getRootPath()+ "empInfo/empList.app?method=initPermissions";
    $.ajax({
        type: "post",
        url: url,
        data: {pathName:pathName},
        dataType: "json",
		async : false,
        success: function(data)
        {
        	if(data.length==0){
        		console.log(pathName+"：--没有查到权限数据");
        	}
        	for(var i=0;i<data.length;i++){
        		var btnId = data[i].btnId;
        		var btnName = data[i].btnName;
        		if(btnId.indexOf("btn")!=-1){
        			toolbar_btn.push('<button id="'+btnId+'" class="btn btn-primary" style="margin-right:5px;" type="button"> '+btnName+' </button>');
        			btnIdArray.push(btnId);
        		}else{
        			var obj = {
						btnId:btnId,
						btnName:btnName
        			}
        			table_btn.push(obj);
        		}
        	}
        	$("#topToolbar").html(toolbar_btn.join(''));
        }
    });
	
};

//表格链接初始化
function tableBtnInit(){
	var result = new Array();
	if(table_btn.length>3){
		for(var i=0;i<table_btn.length;i++){
			if(i>1){
				if(i==2){
					result.push('<div class="dropdown" style="display:inline"><a class="dropdown-toggle" data-toggle="dropdown" id="more" href="javascript:void(0)"> 更多<b class="caret"></b></a><ul class="dropdown-menu  m-t-xs">');
					result.push('<li><a id="'+table_btn[i].btnId+'">'+table_btn[i].btnName+'</a></li>');
				}else{
					result.push('<li><a id="'+table_btn[i].btnId+'">'+table_btn[i].btnName+'</a></li>');
				}
			}else{
				result.push('<a id="'+table_btn[i].btnId+'">'+table_btn[i].btnName+' <span style="color:#CCC">|</span> </a>');
			}
		}
		result.push('</ul></div>');
	}else{
		for(var i=0;i<table_btn.length;i++){
			if(i==table_btn.length-1){
				result.push('<a id="'+table_btn[i].btnId+'">'+table_btn[i].btnName+' </a>');
			}else{
				result.push('<a id="'+table_btn[i].btnId+'">'+table_btn[i].btnName+'</a> <span style="color:#CCC">|</span> ');
			}
		}
	}
	
	return result.join('');
};


/*表格链接初始化，可以自定义, 预存押金页面调用该函数例子
 * function operateFormatter(value, row, index) {
		var data = [{text:'charge_type', value:'02', btn:'a_prestore'}
					];
		var obj = eval(data);
		return tableBtnInitLocal(row, obj);
    };
 * 
 */
function tableBtnInitLocal(row, objArray){
	var result = new Array();
	var tmp_btn = table_btn.slice(0);
	for(var ob=0;ob<objArray.length;ob++){
		var obj = objArray[ob];
		if(row[obj.text] != obj.value){
			arrayRemove(tmp_btn, obj.btn);
		}
	}
	
	if(tmp_btn.length>3){
		for(var i=0;i<tmp_btn.length;i++){
			if(i>1){
				if(i==2){
					result.push('<div class="dropdown" style="display:inline"><a class="dropdown-toggle" data-toggle="dropdown" id="more" href="javascript:void(0)"> 更多<b class="caret"></b></a><ul class="dropdown-menu  m-t-xs">');
					result.push('<li><a id="'+tmp_btn[i].btnId+'">'+tmp_btn[i].btnName+'</a></li>');
				}else{
					result.push('<li><a id="'+tmp_btn[i].btnId+'">'+tmp_btn[i].btnName+'</a></li>');
				}
			}else{
				result.push('<a id="'+tmp_btn[i].btnId+'">'+tmp_btn[i].btnName+' <span style="color:#CCC">|</span> </a>');
			}
		}
		result.push('</ul></div>');
	}else{
		for(var i=0;i<tmp_btn.length;i++){
			if(i==tmp_btn.length-1){
				result.push('<a id="'+tmp_btn[i].btnId+'">'+tmp_btn[i].btnName+' </a>');
			}else{
				result.push('<a id="'+tmp_btn[i].btnId+'">'+tmp_btn[i].btnName+'</a> <span style="color:#CCC">|</span> ');
			}
		}
	}
	
	return result.join('');
};

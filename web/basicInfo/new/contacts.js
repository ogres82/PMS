$(function () {
	init();
});

function init(){
	var url= "./../../empInfo/empList.app?method=initAllEmp";
    $.post(url,function(data){
   		var list = eval(data);
   		var html = "";
   		for(var i=0;i<list.length;i++){
   			var postName = list[i].post_name;
   			var empName = list[i].empName;
   			var deptName = list[i].dept_name;
   			var empEmail = list[i].empEmail;
   			var empPhone = list[i].empPhone;
   			var empSex = list[i].empSex;
   			var empHeadimg = list[i].empHeadimg;
   			var empId = list[i].empId;
   			var path = "../../Hplus/img/admin.jpg";
   			
   			if(empHeadimg){
   				path = getRootPath()+"/empInfo/empList.app?method=loadHeadimg&empId="+empId;
   			}
   			if(!postName){
   				postName = "";
   			}
   			if(!empName){
   				empName = "";
   			}
   			if(!deptName){
   				deptName = "";
   			}
   			if(!empEmail){
   				empEmail = "";
   			}
   			if(!empPhone){
   				empPhone = "";
   			}
   			var icon = "";
   			if(empSex=="0"){
   				empSex = "女";
   				icon = "fa fa-venus";
   			}else{
   				empSex = "男";
   				icon = "fa fa-mars";
   			}
   			
   			 html += '<div class="col-sm-4"> '+
			             '<div class="contact-box"> '+
			             '<a class="emp" target="_self" href="profile.jsp?id='+list[i].empId+'">' +
			                 '<div class="col-sm-5"> '+
			                     '<div class="text-center"> '+
			                         '<img alt="image" style="width:100px;height:100px;margin:0 auto" class="img-circle m-t-xs img-responsive" src="'+path+'"> '+
			                         '<div class="m-t-xs font-bold">'+postName+'</div> '+
			                         '<a id="'+list[i].empId+'" class="headImg" style="font-size:11px;color:#bbb;" onclick="uploadHeadImg(this)">编辑头像</a>'+
			                     '</div> '+
			                 '</div> '+
			                 '<div class="col-sm-7"> '+
			                     '<h3><strong>'+empName+'</strong></h3> '+
			                    	 '<p><i class="'+icon+'"></i> '+empSex+'</p> '+
			                     '<address> '+
			                     '<strong>'+deptName+'</strong><br> '+
			                     empEmail+'<br><br> '+
//			                     'Weibo:<a href="">http://weibo.com/xxx</a><br> '+
			                     '<abbr title="Phone">Tel:</abbr> '+empPhone+' '+
			                 '</address> '+
			                 '</div> '+
			                 '<div class="clearfix"></div> '+
			             '</a> '+
			         '</div> '+
			     '</div> ';
   		}
   		$("#allEmps").html(html);
   		$(".contact-box").each(function(){animationHover(this,"pulse")});
   		$(".emp").mouseover(function(){
   			$(".headImg").show();
   		});
	});
};

//初始化fileinput控件（第一次初始化）
function imgManageInput(ctrlName, uploadUrl) {    
    var control = $('#' + ctrlName); 

    $("#imgManage").fileinput({
        uploadUrl: uploadUrl, // server upload action
        uploadAsync: false,
        showPreview: true,
        showUpload: true,
        allowedFileExtensions: ['jpg', 'png'],
        maxFileCount: 1,
        elErrorContainer: '#kv-error-4'
    }).on('filebatchpreupload', function(event, data, id, index) {
//        $('#kv-success-4').html('<h4>Upload Status</h4><ul></ul>').hide();
    }).on('filebatchuploadsuccess', function(event, data) {
       
        var msg = eval('('+data.response+')');
        if(msg.status==1){
        	
        	layer.msg("上传成功",{
        		time: 2000
        	}, function(){
        		$("#myModal1").modal('hide');
        		window.location.href=window.location.href;
        	});
        }else{
        	layer.msg(msg.message,{
        		time: 2000
        	});
        }
    });
}

function uploadHeadImg(obj){
	var empId = $(obj).attr("id");
	$("#empId").val(empId);
	imgManageInput("qualification", "./../../empInfo/empList.app?method=inputFile&folder=headimg&empId="+$("#empId").val());
	$("#imgManage").fileinput('clear');
	$("#myModal1").modal();
}

/**
 * 获取项目根路径：http://localhost:8083/proj
 */
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/proj/meun.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： proj/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPath = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/proj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);
    return(localhostPath + projectName);
}


$(function () {
	init();
});

function init(){
	var url= "./../../empInfo/empList.app?method=initEmpDept";
    $.post(url,function(data){
    	
   		var list = eval(data);
   		var html = "";
   		for(var i=0;i<list.length;i++){
   			var emps = "";
   			$.ajax({
                type: "post",
                url: "./../../empInfo/empList.app?method=getEmpByDeptId&deptId="+list[i][1],
                dataType: "json",
        		async : false,
                success: function(data)
                {
                	var result = eval(data);
                	for(var j=0;j<result.length;j++){
                		var path = "../../Hplus/img/admin.jpg";
               			
               			if(result[j].empHeadimg){
               				path = getRootPath()+"/empInfo/empList.app?method=loadHeadimg&empId="+result[j].empId;
               			}
           				emps += '<a href="profile.jsp?id='+result[j].empId+'" target="_self"><img data-toggle="tooltip" data-placement="top" title="'+result[j].empName+'" alt="member" class="img-circle" src="'+path+'"></a> ';
                	}
                },
                failure:function(xhr,msg){}
            });
   			var percent = Math.ceil(Math.random()*100);
   			html += 
   			'<div class="col-sm-4"> '+
             '<div class="ibox"> '+
                '<div class="ibox-title"> '+
                    '<span class="label label-primary pull-right">NEW</span> '+
                    '<h5>IT- '+list[i][2]+' </h5> '+
                '</div> '+
                '<div class="ibox-content"> '+
                    '<div class="team-members"> '+
                    emps+
                    '</div> '+
                    '<h4>部门简介</h4> '+
                    '<p> '+
                    list[i][3]+
                    '</p> '+
                    '<div> '+
                        '<span>当前项目进度：</span> '+
                        '<div class="stat-percent">'+percent+'%</div> '+
                        '<div class="progress progress-mini"> '+
                            '<div style="width: '+percent+'%;" class="progress-bar"></div> '+
                        '</div> '+
                    '</div> '+
                    '<div class="row  m-t-sm"> '+
                        '<div class="col-sm-4"> '+
                            '<div class="font-bold">项目</div> '+
                            Math.ceil(Math.random()*20)+' '+
                        '</div> '+
                        '<div class="col-sm-4"> '+
                            '<div class="font-bold">周期</div> '+
                            Math.ceil(Math.random()*36)+'个月 '+
                        '</div> '+
                        '<div class="col-sm-4 text-right"> '+
                            '<div class="font-bold">预算</div> '+
                            '&yen;'+Math.ceil(Math.random()*1000000)+' <i class="fa fa-level-up text-navy"></i> '+
                        '</div> '+
                    '</div> '+
                '</div> '+
            '</div> '+
         '</div> ';
   		}
   		$("#teams").html(html);
   		$("[data-toggle='tooltip']").tooltip();
	});
};

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


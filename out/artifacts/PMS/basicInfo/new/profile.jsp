<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>个人资料</title>

    <link href="${ContextPath}/Hplus/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${ContextPath}/Hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">

    <link href="${ContextPath}/Hplus/css/animate.min.css" rel="stylesheet">
    <link href="${ContextPath}/Hplus/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInRight">
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>个人资料</h5>
                    </div>
                    <div>
                        <div class="ibox-content no-padding border-left-right">
                            <img id="headimg" style="width:100%;height:280px" alt="image" class="img-responsive" src="">
                        </div>
                        <div class="ibox-content profile-content">
                            <h4><strong id="empName"></strong></h4>
                            <p><i class="fa fa-map-marker"></i> 贵阳市中大国际A3-32-1室</p>
                            <h5>
                                    关于我
                                </h5>
                            <p>
                                会点前端技术，div+css啊，jQuery之类的，不是很精；热爱生活，热爱互联网，热爱新技术；有一个小的团队，在不断的寻求新的突破。
                            </p>
                            <div class="row m-t-lg">
                                <div class="col-sm-4">
                                    <span class="bar">5,3,9,6,5,9,7,3,5,2</span>
                                    <h5><strong>169</strong> 文章</h5>
                                </div>
                                <div class="col-sm-4">
                                    <span class="line">5,3,9,6,5,9,7,3,5,2</span>
                                    <h5><strong>28</strong> 关注</h5>
                                </div>
                                <div class="col-sm-4">
                                    <span class="bar">5,3,2,-1,-3,-2,2,3,5,2</span>
                                    <h5><strong>240</strong> 关注者</h5>
                                </div>
                            </div>
                            <div class="user-button">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <button type="button" class="btn btn-primary btn-sm btn-block"><i class="fa fa-envelope"></i> 发送消息</button>
                                    </div>
                                    <div class="col-sm-6">
                                        <button type="button" class="btn btn-default btn-sm btn-block"><i class="fa fa-coffee"></i> 赞助</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-8">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>个人简历</h5>
                    </div>
                    <div class="ibox-content">

                        <div>
                            <div class="feed-activity-list">

								<div class="feed-element">
                                    <a href="#" class="pull-left">
                                        <i class="fa fa-file-text-o" style="font-size:15px;vertical-align:bottom"></i>
                                    </a>
                                    <div class="media-body ">
                                        
                                        <strong style="font-size:15px"> 基本信息</strong>
                                        <br>
                                        <small class="text-muted"></small>
                                        <div class="items">
                                        	<p></p>
                                            <p>2009/09 -- 2013/07</p>
											<p>男 | 未婚 | 1992年1月生 | 户口：贵州-贵阳 | 现居住于: 贵州 贵阳 </p>
											<p>4年工作经验 | 中共党员(含预备党员)  </p>
											<p>身份证: 520202199201022000 </p>
											<p>15285630464 </p>
											<p>E-mail：buxiaochao@sina.cn </p>
											
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="feed-element">
                                    <a href="#" class="pull-left">
                                        <i class="fa fa-graduation-cap" style="font-size:15px;vertical-align:bottom"></i>
                                    </a>
                                    <div class="media-body ">
                                        <strong style="font-size:15px">教育经历</strong>
                                        <br>
                                        <small class="text-muted"></small>
                                        <div class="items">
                                        	<p></p>
                                            <p>2009/09 -- 2013/07</p>
											<p style="font-weight:bold">遵义师范学院 | 计算机科学与技术 | 本科</p>
                                        </div>
                                    </div>
                                </div>

                                <div class="feed-element">
                                    <a href="#" class="pull-left">
                                       <i class="fa fa-file-pdf-o" style="font-size:15px;vertical-align:bottom"></i>
                                    </a>
                                    <div class="media-body ">
                                        
                                        <strong style="font-size:15px"> 证书</strong>
                                        <br>
                                        <div class="items">
                                        	<p></p>
                                            <p>2012/11</p>
                                            <p style="font-weight:bold">高级工程师</p>
                                            <p>在加拿大达内IT培训集团培训结束后获得java高级软件工程师</p>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="feed-element">
                                    <a href="#" class="pull-left">
                                         <i class="fa fa-star-o" style="font-size:15px;vertical-align:bottom"></i>
                                    </a>
                                    <div class="media-body ">
                                        <strong style="font-size:15px"> 自我评价</strong> 
                                        <br>
										<div class="items">
                                        	<p></p>
                                            <p>到目前为止共参与开发三个JAVA WEB集成项目；三年java开发经验，精通java面向对象编程，熟练使用ext、easyUI、dorado等前端框架，报表工具有润乾报表、highcharts。对j2ee开发有较深的理解，熟练在linux服务器安装部署项目；熟练使用Strusts2、SpringMVC、Hibernate等框架。能阅读英语相关技术文档，编写项目验收相关文档。 熟练使用HTML5+CSS3+JAVASCRIPT开发网页，网页app。</p>
                                        </div>
                                    </div>
                                </div>
                                

                            </div>

                            <button class="btn btn-primary btn-block m"><i class="fa fa-arrow-down"></i> 显示更多</button>

                        </div>

                    </div>
                </div>

            </div>
        </div>
    </div>
    <a id="back" data-toggle="tooltip" target="_self" href="javascript:history.go(-1)" data-placement="left" title="返回" style="display:none;width:40px;height:40px;background-color:rgba(0,0,0,0.4);border-radius:20px;-webkit-border-radius:20px;position:fixed;top:20px;right:30px;z-index:100;padding:10px;"><i style="color:#fff;font-size:20px;" class="fa fa-arrow-left"></i></a>
    <script src="${ContextPath}/Hplus/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ContextPath}/Hplus/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${ContextPath}/Hplus/js/content.min.js?v=1.0.0"></script>
    <script src="${ContextPath}/Hplus/js/plugins/peity/jquery.peity.min.js"></script>
    <script src="${ContextPath}/Hplus/js/demo/peity-demo.min.js"></script>
	<script>
	  $(function(){
		  $("[data-toggle='tooltip']").tooltip();
		  var empId = GetQueryString("id");
		  var url= "./../../empInfo/empList.app?method=getEmpById&empId="+empId;
		    $.post(url,function(data){
		    	var empHeadimg = data.empHeadimg;
		    	var path = "../../Hplus/img/admin.jpg";
	   			
	   			if(empHeadimg){
	   				path = getRootPath()+"/empInfo/empList.app?method=loadHeadimg&empId="+empId;
	   			}
		   		$("#headimg").attr("src",path);
		   		$("#empName").html(data.empName);
			});
		  $("#back").fadeIn(3000);
		  
	  });
	  
	  function GetQueryString(name)
	  {
	       var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	       var r = window.location.search.substr(1).match(reg);
	       if(r!=null)return  unescape(r[2]); return null;
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
	</script>
</body>

</html>
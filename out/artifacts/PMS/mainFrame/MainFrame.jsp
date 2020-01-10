
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser"%>
<%@ page import="com.bstek.bdf2.core.context.ContextHolder"%>
<% String path = request.getContextPath();
	    request.setAttribute("ContextPath",path);
	    DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
	    request.setAttribute("loginUser",user);
	    int size = user.getPositions().size();
	    int deptSize = user.getDepts().size();
	    String positionName = "";
	    String deptName = "";
	    if(size>0){
	    	for(int i = 0;i<size;i++){
	    		if(i==0){
			    	positionName = user.getPositions().get(i).getName();
	    		}else{
	    			positionName += " "+ user.getPositions().get(i).getName();
	    		}
	    		
	    	}
	    }
	    if(deptSize>0){
	    	deptName = user.getDepts().get(0).getName();
	    }
	    String address = user.getAddress();
	    String headImg = user.getAddress();
	    if(address==null || address.isEmpty()){
	    	headImg = "img/profile_small.jpg";
	    }
	    request.setAttribute("positionName",positionName);
	    request.setAttribute("deptName",deptName);
	    request.setAttribute("headImg",headImg);
	%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>乐湾物业综合信息管理平台</title>

<!--[if lt IE 9]>
	    <meta http-equiv="refresh" content="0;ie.html" />
	    <![endif]-->

<link rel="shortcut icon" href="favicon.ico">
<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">
<link href="css/style.min.css?v=4.1.0" rel="stylesheet">
<link rel="stylesheet"
	href="${ContextPath}/Hplus/validate/css/validationEngine.jquery.css">
<link href="${ContextPath}/Hplus/dist/bootstrap-table.min.css"
	rel="stylesheet" />
<link
	href="${ContextPath}/Hplus/css/plugins/dataTables/dataTables.bootstrap.css?v=4.1.0"
	rel="stylesheet" />

<!-- 后台推送 -->
<link href="${ContextPath}/Hplus/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">


<link href="${ContextPath}/basicInfo/fileinput.min.css" rel="stylesheet" />


<script type="text/javascript" src="ajax-pushlet-client.js"></script>
<script type="text/javascript"> 
	        // 初始化
	        //PL._init(); 
	        
	        // 注册监听
	        PL.joinListen('/event/message'); 
	        function onData(event) { 
	        	var messages= decodeURIComponent(event.get("message"));
	        	var handles= decodeURIComponent(event.get("handle"));
	        	var contents= decodeURIComponent(event.get("content"));
	        	voiceAlert();
	        	var a1="您有一个工单："+messages+"需要处理，申报人："
	        	var a2=handles;
	        	var a3=" 处理内容："+contents;
	        	var infos=a1+a2+a3;
	        	$("#message").val(infos);
	        	$("#title").val(messages);
	        	$("#showtoast").trigger("click"); 
	        }
	        
	        function voiceAlert(){
		       	if(!$("#alertVoiceDiv").get(0)){
		       		$("<div id='alertVoiceDiv'></div>").appendTo("body");
		       	}
	//	       	if(!$("#bgMusic").get(0)){
		       		if (myBrowser()=="Chrome"){   //判断是否IE浏览器
		       			$("#alertVoiceDiv").html('<audio id="bgMusic"  autoplay="autoplay"><source = src="'+getRootPath()+'common/media/7815.mp3" type="audio/wav"></audio>');
		       		}else{
		       			$("#alertVoiceDiv").html('<embed id="bgMusic"  height="100" width="100" src="'+getRootPath()+'common/media/7815.mp3" hidden="true" disabled="true"></embed>');
		       		}
	//	       	}
	        }
	        
	        function myBrowser(){
	            var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
	            var isOpera = userAgent.indexOf("Opera") > -1;
	            if (isOpera) {
	                return "Opera";
	            }; //判断是否Opera浏览器
	            if (userAgent.indexOf("Firefox") > -1) {
	                return "FF";
	            } //判断是否Firefox浏览器
	            if (userAgent.indexOf("Chrome") > -1){
	            	return "Chrome";
	            }
	            if (userAgent.indexOf("Safari") > -1) {
	                return "Safari";
	            } //判断是否Safari浏览器
	            if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
	                return "IE";
	            }; //判断是否IE浏览器
	        }
	        function getRootPath(){
	            //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	            var curWwwPath=window.document.location.href;
	            //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	            var pathName=window.document.location.pathname;
	            var pos=curWwwPath.indexOf(pathName);
	            //获取主机地址，如： http://localhost:8083
	            var localhostPaht=curWwwPath.substring(0,pos);
	            //获取带"/"的项目名，如：/uimcardprj
	            var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+2);
	            return(localhostPaht+projectName);
	        }
	        
	     </script>
<style>
.head-img:hover {
	cursor: pointer;
}
</style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>

			</div>
			<div class="sidebar-collapse">
				<ul class="nav" id="side-menu">
					<li id="nav_logo" style="width: 220px; height: 61px;"><img
						alt="image" width="220" height="61"
						src="../images/header-profile-brand.png" /></li>
					<li class="nav-header">
						<div class="dropdown profile-element">
							<span onclick="uploadHeadImg()"><img alt="image"
								width="79" height="79" class="img-circle head-img"
								src="${headImg}" onerror="imgerror(this)" /></span> <a
								data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
								class="clear"> <span class="block m-t-xs"><strong
										class="font-bold">${loginUser.cname}</strong></span> <span
									class="text-muted text-xs block" style="font-size: 12px;">${deptName}</span>
									<span class="text-muted text-xs block" style="font-size: 12px;">${positionName}</span>
							</span>
							</a>
						</div>
						<div class="logo-element">乐湾</div>
					</li>

				</ul>
			</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<!-- 开始 -->
			<div class="row" style="display: none;">
				<div class="col-lg-12">

					<div class="row">
						<input id="title" type="hidden" class="form-control"> <input
							id="message" type="hidden" class="form-control"> <input
							id="closeButton" type="hidden" value="checked" class="input-mini">
						<input id="addBehaviorOnToastClick" type="checkbox"
							value="checked" checked="checked" hidden="hidden"
							class="input-mini"> <input id="showEasing" type="hidden"
							placeholder="swing, linear" class="form-control" value="swing"
							hidden="hidden"> <input id="hideEasing" type="hidden"
							placeholder="swing, linear" class="form-control" value="linear"
							hidden="hidden"> <input id="showMethod" type="hidden"
							placeholder="show, fadeIn, slideDown" class="form-control"
							value="fadeIn" hidden="hidden"> <input id="hideMethod"
							type="hidden" placeholder="hide, fadeOut, slideUp"
							class="form-control" value="fadeOut" hidden="hidden"> <input
							id="showDuration" type="hidden" placeholder="ms"
							class="form-control" value="400" hidden="hidden"> <input
							id="hideDuration" type="hidden" placeholder="ms"
							class="form-control" value="1000" hidden="hidden"> <input
							id="timeOut" type="hidden" placeholder="ms" class="form-control"
							value="7000" hidden="hidden"> <input type="radio"
							name="positions" value="toast-top-right" hidden="hidden">
						<input id="extendedTimeOut" type="hidden" placeholder="ms"
							class="form-control" value="1000" hidden="hidden">
						<div class="col-md-2">
							<div class="form-group" id="toastTypeGroup">
								<div class="radio">
									<label> <input type="radio" name="toasts"
										value="success" hidden="hidden">
									</label>
								</div>
								<div class="radio">
									<label class="radio"> <input type="radio" name="toasts"
										value="info" hidden="hidden">
									</label>
								</div>
							</div>
						</div>
						<div class="col-lg-12">

							<input type="hidden" class="btn btn-primary" id="showtoast"></input>
						</div>
					</div>
				</div>
			</div>


			<!-- 结束 -->


			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
					<div class="navbar-header">
						<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
							href="#"><i class="fa fa-bars"></i> </a>
					</div>
					<ul class="nav navbar-top-links navbar-right">
						<li class="dropdown" title="友情链接"><a
							class="dropdown-toggle count-info" data-toggle="dropdown"
							href="#"> <i class="fa fa-chain"
								style="font-size: 18px; position: relative; top: 1px;"></i>
						</a>
							<ul class="dropdown-menu dropdown-alerts">
								<li>
									<div class="text-center link-block">
										<a class="J_menuItem"> <i class="fa fa-chain"></i> <strong>
												友情链接</strong>
										</a>
									</div>
								</li>
								<li class="divider"></li>
								<li><a href="http://oa.hongde-group.com:9000/" target="_blank"> <i
										class="fa fa-internet-explorer"></i> 宏德集团OA
								</a></li>
								<li class="divider"></li>
								<li><a href="http://lwgj.lounsi.cc" target="_blank"> <i
										class="fa fa-internet-explorer"></i> 乐湾国际高尔夫俱乐部
								</a></li>
								<li class="divider"></li>
								<li><a href="http://www.lwgjwq.com" target="_blank"> <i
										class="fa fa-internet-explorer"></i> 乐湾国际温泉
								</a></li>
								<li class="divider"></li>
								<li><a href="http://www.lwgjsyxx.com" target="_blank">
										<i class="fa fa-internet-explorer"></i> 贵阳乐湾国际实验小学
								</a></li>
								<li class="divider"></li>
								<li><a href="http://www.gylwgjsyxx.com" target="_blank">
										<i class="fa fa-internet-explorer"></i> 贵阳乐湾国际实验学校
								</a></li>
							</ul></li>
						<li class="dropdown" data-toggle="tooltip" data-placement="bottom"
							title="修改密码"><a class="dropdown-toggle count-info"
							onclick="changePwd()"> <i class="fa fa-lock"
								style="font-size: 18px; position: relative; top: 1px;"></i>
						</a></li>
						<li class="dropdown" data-toggle="tooltip" data-placement="bottom"
							title="发送消息"><a class="dropdown-toggle count-info"
							onclick="sendMsg()"> <i class="fa fa-envelope"></i>
						</a></li>
						<li class="dropdown" data-toggle="tooltip" data-placement="bottom"
							title="查看消息"><a class="dropdown-toggle count-info"
							onclick="viewMsg()"> <i class="fa fa-comment"></i> <span
								class="label label-warning" id="unreadMsg"></span>
						</a></li>
						<li class="dropdown hidden-xs"><a
							class="right-sidebar-toggle" aria-expanded="false"> <i
								class="fa fa-tasks"></i> 主题
						</a></li>
					</ul>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
					<div class="page-tabs-content">
						<a href="javascript:;" class="active J_menuTab" data-id="">首页</a>
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>

					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a id="exit" class="roll-nav roll-right J_tabExit"><i
					class="fa fa fa-sign-out"></i> 退出</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
					src="${ContextPath}/mainFrame/Welcome.jsp" frameborder="0"
					data-id="" seamless></iframe>
			</div>
			<div class="footer">
				<div>
					&copy; 2016-2020 技术支持：<a href="#"
						target="_blank">贵州乐湾物业管理有限公司</a>
				</div>
			</div>
		</div>
		<!--右侧部分结束-->
		<!--右侧边栏开始-->
		<div id="right-sidebar">
			<div class="sidebar-container">

				<ul class="nav nav-tabs navs-3">

					<li class="active"><a data-toggle="tab" href="#tab-1"> <i
							class="fa fa-gear"></i> 主题
					</a></li>
				</ul>

				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> 主题设置
							</h3>
							<small><i class="fa fa-tim"></i>
								你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
						</div>
						<div class="skin-setttings">
							<div class="title">主题设置</div>
							<div class="setings-item">
								<span>收起左侧菜单</span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="collapsemenu"
											class="onoffswitch-checkbox" id="collapsemenu"> <label
											class="onoffswitch-label" for="collapsemenu"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span>固定顶部</span>

								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="fixednavbar"
											class="onoffswitch-checkbox" id="fixednavbar"> <label
											class="onoffswitch-label" for="fixednavbar"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span> 固定宽度 </span>

								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="boxedlayout"
											class="onoffswitch-checkbox" id="boxedlayout"> <label
											class="onoffswitch-label" for="boxedlayout"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="title">皮肤选择</div>
							<div class="setings-item default-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-0">
										默认皮肤 </a>
								</span>
							</div>
							<div class="setings-item blue-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-1">
										蓝色主题 </a>
								</span>
							</div>
							<div class="setings-item yellow-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-3">
										黄色/紫色主题 </a>
								</span>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!--右侧边栏结束-->

		<!--修改密码-->
		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="changePwdModal" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 500px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalTitle" style="color: white"></h4>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div class="tab-pane active">
								<form id="myForm1" class="form-horizontal" role="form">

									<fieldset id="basicInfo1">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-3 control-label">原密码</label>
											<div class="col-sm-9">
												<input
													class="form-control validate[required,ajax[checkPwdIndex]]"
													id="oldPwd" type="password" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-3 control-label">新密码</label>
											<div class="col-sm-9">
												<input class="form-control validate[required]"
													id="newPassword" type="password" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-3 control-label">重复新密码</label>
											<div class="col-sm-9">
												<input
													class="form-control validate[required,custom[checkRePwd]]"
													id="renewPassword" type="password" placeholder="必填项" />
											</div>
										</div>

									</fieldset>

									<div class="form-group form-group-sm "></div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">关闭</button>
										<button id="btContractAdd" onclick="openSaveButton()"
											class="btn btn-primary " type="button">保存</button>
									</div>
									<input class="form-control" id="contractId" type="hidden" /> <input
										class="form-control" id="fileIds" type="hidden" />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		<!--修改密码结束-->

		<!--发送消息开始-->
		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="sendMsgModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 800px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" style="color: white">发送消息</h4>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div class="tab-pane active">
								<form id="msgForm" class="form-horizontal" role="form">

									<fieldset id="basicInfo2">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">标题</label>
											<div class="col-sm-10">
												<input class="form-control validate[required]" id="msgTitle"
													type="text" placeholder="必填项" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">选择接收人</label>
											<div class="col-sm-10">
												<!--  <table class="table-no-bordered" id="receiverInfo"></table> -->
												<div class="input-group">
													<input type="text" class="form-control" id="receiversName"
														name="receiversName" readonly> <input
														type="hidden" class="form-control" id="receivers"
														name="receivers"> <span class="input-group-btn">
														<button class="btn btn-default btn-sm" type="button"
															onclick="func1();">
															<i class="fa fa-search"></i>
														</button>
													</span>
												</div>
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">内容</label>
											<div class="col-sm-10">
												<textarea class="form-control validate[required]"
													style="height: 150px" id="content" rows="3" cols="145"></textarea>
											</div>
										</div>

									</fieldset>

									<div class="form-group form-group-sm "></div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">关闭</button>
										<button id="msgAdd" onclick="sendMsgButton()"
											class="btn btn-primary " type="button">保存</button>
									</div>
									<input class="form-control" id="contractId1" type="hidden" />
									<input class="form-control" id="fileIds1" type="hidden" />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		<!--发送消息结束-->


		<!--查看消息开始-->
		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="viewMsgModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 700px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" style="color: white">查看消息</h4>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#tab-receive">我收到的信息</a>
							</li>
							<li class=""><a data-toggle="tab" href="#tab-send">我发送的信息</a>
							</li>
						</ul>
						<div class="tab-content">
							<div id="tab-receive" class="tab-pane active">
								<table class="table-no-bordered" id="receiveMsg"></table>
							</div>
							<div id="tab-send" class="tab-pane">
								<table class="table-no-bordered" id="hasSentMsg"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		<!--查看消息结束-->

		<!--消息详情开始-->
		<!-- 弹出窗口区域，触发事件后弹出  -->
		<div class="modal fade" id="msgDetailModal" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 700px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" style="color: white">消息详情</h4>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div class="tab-pane active">
								<form id="detailForm" class="form-horizontal" role="form">

									<fieldset id="basicInfo3">
										<legend style="font-size: 15px; border: 0;"></legend>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">标题</label>
											<div class="col-sm-10">
												<input class="form-control" id="dTitle" type="text" />
											</div>
										</div>
										<div class="form-group form-group-sm ">
											<label class="col-sm-2 control-label">内容</label>
											<div class="col-sm-10">
												<textarea class="form-control" style="height: 150px"
													id="dContent" rows="3" cols="145"></textarea>
											</div>
										</div>
									</fieldset>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，触发事件后弹出   结束 -->
		<!--消息详情结束-->

		<!-- 弹出窗口区域，上传头像  -->
		<div class="modal fade" id="uploadHeadImgModal" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" style="width: 600px">
				<div class="modal-content">
					<div class="modal-header" style="background: #18a689">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" style="color: white">上传头像</h4>
					</div>
					<div class="modal-body">
						<fieldset id="basicInfo4">
							<legend style="font-size: 15px; border: 0;"></legend>
							<div class="form-group form-group-sm">
								<label class="col-sm-2 control-label" style="margin-top: 4px">上传头像</label>
								<div class="col-sm-10">
									<input id="logo" name="kartik-input-710[]" type="file" multiple
										class="file-loading" onchange="preview(this)">
									<div id="kv-error-2" style="margin-top: 10px; display: none"></div>
								</div>
							</div>
							<div style="height: 20px"></div>
							<div class="form-group form-group-sm">
								<br /> <label class="col-sm-2 control-label">头像预览</label>
								<div class="col-sm-10">
									<div id="preview"
										style="width: 150px; height: 150px; padding: 5px; border: 1px solid #e5e6e7"></div>
								</div>
							</div>
						</fieldset>
						<br />
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button id="btDecCompanyAdd" onclick="saveUploadImg()"
								class="btn btn-primary " type="button">保存</button>
						</div>
						<input class="form-control" id="userName"
							value="${loginUser.username}" type="hidden" /> <input
							class="form-control" id="logoPath" type="hidden" />
					</div>
				</div>
			</div>
		</div>
		<!-- 弹出窗口区域，上传头像   结束 -->


	</div>
	<script src="js/jquery.min.js?v=2.1.4"></script>
	<script src="js/bootstrap.min.js?v=3.3.6"></script>

	<!-- 后台推送 -->
	<script src="${ContextPath}/Hplus/js/content.min.js"></script>
	<script src="${ContextPath}/Hplus/js/plugins/toastr/toastr.min.js"></script>

	<script src="${ContextPath}/Hplus/dist/bootstrap-table.js"></script>
	<script
		src="${ContextPath}/Hplus/dist/locale/bootstrap-table-zh-CN.min.js"></script>
	<script
		src="${ContextPath}/Hplus/validate/js/jquery.validationEngine-zh_CN.js"></script>
	<script
		src="${ContextPath}/Hplus/validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript"
		src="${ContextPath}/basicInfo/fileinput.min.js"></script>
	<script type="text/javascript"
		src="${ContextPath}/mainFrame/MainFrame.js"></script>
	<script src="js/jquery.metisMenu.js"></script>
	<script src="js/jquery.slimscroll.min.js"></script>
	<script src="js/contabs.min.js"></script>
	<script src="js/pace.min.js"></script>
	<script src="${ContextPath}/Hplus/js/plugins/layer/layer.js"></script>
	<script src="js/hplus.min.js?v=4.1.0"></script>
	<script src="${ContextPath}/Hplus/js/plugins/layer/layer.js"></script>
	<script type="text/javascript">
	    	currentUrl = "${ContextPath}";
	    </script>
	<script type="text/javascript">
	    
	    $(function() {
	        var i = -1;
	        var toastCount = 0;
	        var $toastlast;
	        var getMessage = function() {
	            var msg = "你有一个新的工单";
	            return msg
	        };
	       
	        $("#showtoast").click(function() {
	            var shortCutFunction = $("#toastTypeGroup input:radio:checked").val();
	            var msg = $("#message").val();
	            var title = "消息";
	            var $showDuration = $("#showDuration");
	            var $hideDuration = $("#hideDuration");
	            var $timeOut = $("#timeOut");
	            var $extendedTimeOut = $("#extendedTimeOut");
	            var $showEasing = $("#showEasing");
	            var $hideEasing = $("#hideEasing");
	            var $showMethod = $("#showMethod");
	            var $hideMethod = $("#hideMethod");
	            var toastIndex = toastCount++;
	            toastr.options = {
	                closeButton: $("#closeButton").prop("checked"),
	                debug: $("#debugInfo").prop("checked"),
	                positionClass: $("#positionGroup input:radio:checked").val() || "toast-top-right",
	                onclick: null
	            };
	            if ($("#addBehaviorOnToastClick").prop("checked")) {
	                toastr.options.onclick = function() {
	                 var rpt_id=$("#title").val();
	                 var flag=rpt_id.substring(0,2);
	             	//需要判断
	             	if(flag=='BX'){//报障保修
	             	 popModal('/PMS/assignwork/AssignWorkMessage.jsp?rpt_id='+rpt_id,'1000px','530px',rpt_id);
	             	 $('#evnetSendInfo').bootstrapTable('refresh');
	             	}else if(flag=='TS')//咨询建议
	             	{
	             		 popModal('/PMS/assignwork/CompWorkMessage.jsp?rpt_id='+rpt_id,'1000px','530px',rpt_id);
	             		 $('#evnetSendInfo').bootstrapTable('refresh');
	             		 
	             	}else if(flag=='IM')//家政服务
	             	 {
	             		 popModal('/PMS/houseWork/houseWorkDispatchMessage.jsp?rpt_id='+rpt_id,'1000px','530px',rpt_id);
	             		 $('#evnetSendInfo').bootstrapTable('refresh');
	             	 }
	                }
	            }
	            if ($showDuration.val().length) {
	                toastr.options.showDuration = $showDuration.val()
	            }
	            if ($hideDuration.val().length) {
	                toastr.options.hideDuration = $hideDuration.val()
	            }
	            if ($timeOut.val().length) {
	                toastr.options.timeOut = $timeOut.val()
	            }
	            if ($extendedTimeOut.val().length) {
	                toastr.options.extendedTimeOut = $extendedTimeOut.val()
	            }
	            if ($showEasing.val().length) {
	                toastr.options.showEasing = $showEasing.val()
	            }
	            if ($hideEasing.val().length) {
	                toastr.options.hideEasing = $hideEasing.val()
	            }
	            if ($showMethod.val().length) {
	                toastr.options.showMethod = $showMethod.val()
	            }
	            if ($hideMethod.val().length) {
	                toastr.options.hideMethod = $hideMethod.val()
	            }
	            if (!msg) {
	                msg = getMessage()
	            }
	            $("#toastrOptions").text("Command: toastr[" + shortCutFunction + ']("' + msg + (title ? '", "' + title: "") + '")\n\ntoastr.options = ' + JSON.stringify(toastr.options, null, 2));
	            var $toast = toastr[shortCutFunction](msg, title);
	            $toastlast = $toast;
	           
	        });
	        function getLastToast() {
	            return $toastlast
	        }
	       
	    });
	    
	    
	  //=====================================================================
	  //作  者：hezuping
	  //功能说明: 弹出一个模式窗口 
	  //输入参数: 路径,宽度,高度,参数(可选)
	  function popModal(url, width, height, par ) {
	  	layer.open({
	  		  type: 2,
	  		  title: false,
	  		  fixed: false,
	  		  maxmin: true,
	  		  area: [width, height],
	  		  content: url //iframe的url，no代表不显示滚动条
	  		 
	  		});
	  }
	
	    </script>
</body>

</html>

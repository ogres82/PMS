<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser"%>
<%@ page import="com.bstek.bdf2.core.context.ContextHolder"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ContextPath", path);
	DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
	request.setAttribute("loginUser", user);
	String topicId = request.getParameter("topicId");
	request.setAttribute("topicId", topicId);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/bbs/topic.js"></script>
<script type="text/javascript" src="${ContextPath}/common/js/util.js"></script>
<script type="text/javascript">
	var topicId = '${topicId}';
	var loginUserName = '${loginUser.username}';
</script>
<style type="text/css">
pre {
	white-space: pre-wrap; /*css-3*/
	white-space: -moz-pre-wrap; /*Mozilla,since1999*/
	white-space: -pre-wrap; /*Opera4-6*/
	white-space: -o-pre-wrap; /*Opera7*/
	word-wrap: break-word; /*InternetExplorer5.5+*/
}
</style>
</head>
<body class="gray-bg">

	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row" style="width: 60%; margin: 0 auto;">
			<div class="col-sm-12">
				<div class="ibox">
					<button id="btn_back" onclick="window.location.href='forum.jsp'" class="btn btn-primary" type="button">返回</button>
				</div>
				<div class="ibox">
					<div class="ibox-content text-left">
						<h3 class="m-b-xxs" id="title"></h3>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="social-feed-box">
							<div class="pull-right social-action dropdown">
								<div style="display: inline">
									<span><h5>操作</h5></span>
								
									<button data-toggle="dropdown" class="dropdown-toggle btn-white">
										<i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu m-t-xs">
										<li><a onclick="setTopic(1)">加精</a></li>
										<li><a onclick="setTopic(2)">置顶</a></li>
										<li><a onclick="cancelSetTopic(1)">取消加精</a></li>
										<li><a onclick="cancelSetTopic(2)">取消置顶</a></li>
									</ul>
								</div>
							</div>
							<div class="social-avatar">
								<div class="media-body">
									<span style="font-size: 14px" id="postUser"></span> <br> <small id="postTime" class="text-muted"></small>
								</div>
							</div>
							<div class="social-body">
							<pre id="content"></pre>
								<!-- <p id="content"></p> -->
								<div class="btn-group">
									<button class="btn btn-white btn-xs" id="zan">
										<i class="fa fa-thumbs-up"></i> 赞
									</button>
									
								</div>
							</div>
							<div class="social-footer">
								<div class="feed-element">
									<div class="media-body">
										<textarea class="form-control" id="commentDetail" placeholder="填写评论..."></textarea>
									</div>
									<div style="margin-top: 5px;" class="pull-right">
										<button class="btn btn-primary btn-xs" onclick="saveComment()" id="comment">
											<i class="fa fa-comments"></i> 评论
										</button>										
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
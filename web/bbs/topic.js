var topicId = topicId;
var replyToUserId = "";
$(function() {
	var topic = {
		topicId : topicId
	};
	$.ajax({
		type : "post",
		url : getRootPath() + "forum.app?method=loadTopic",
		data : topic,
		dataType : "json",
		async : false,
		success : function(data) {
			formatTopic(data);
		},
		failure : function(xhr, msg) {
			layer.msg('操作失败！', {
				time : 2000
			}, function() {

			});
		}
	});
	$.ajax({
		type : "post",
		url : getRootPath() + "forum.app?method=loadReply",
		data : topic,
		dataType : "json",
		async : false,
		success : function(data) {
			formatReply(data);
		},
		failure : function(xhr, msg) {
			layer.msg('操作失败！', {
				time : 2000
			}, function() {

			});
		}
	});

	$("a.replay").click(function() {
		var a = $(this).parent().next("div").id;
		var b = $(this).siblings("a")[0];
		$(".carea").hide();
		$(this).parent().next("div").show();
		$(this).parent().next("div").children(".media-body").children("textarea").attr("placeholder", "回复" + b.text);
		replyToUserId = b.id;
	});
});

function formatTopic(data) {
	if (data.ownerHeadImg != null && data.ownerHeadImg != "") {
		$(".social-avatar").prepend('<a href="" class="pull-left"><img alt="image" src=' + data.ownerHeadImg + '></a>');
	} else {
		$(".social-avatar").prepend('<a href="" class="pull-left"><img alt="image" src=""></a>');
	}
	$("#content").html(data.topicContent);
	$("#title").html(data.topicTitle);
	$("#postUser").html(data.userNickname);
	$("#postTime").html(json2TimeStamp(data.topicPostTime));
	if (typeof (data.imgUrl) != "undefined") {
		var imgArray = data.imgUrl.split(",");
		for (var i = 0; i < imgArray.length; i++) {
			var imgUrl = imgArray[i];
			$("#content").append('<br/><br/><img src="' + imgUrl + '" onload="javascript:DrawImage(this,356,436)"/>');
		}

	}
}

function formatReply(data) {
	// console.log(JSON.stringify(data));
	if (data.length != 0) {
		for (var i = 0; i < data.length; i++) {
			var replyUserNickname = data[i].replyUserNickname;
			var replyToUserNickname = data[i].replyToUserNickname;
			if (data[i].replyAddrs != "" && typeof (data[i].replyAddrs) != "undefined") {
				replyUserNickname += "(" + data[i].replyAddrs + ")";
			}
			if (data[i].replyToAddrs != "" && typeof (data[i].replyToAddrs) != "undefined") {
				replyToUserNickname += "(" + data[i].replyToAddrs + ")";
			}
			if (typeof (data[i].replyToUserId) == "undefined") {
				$(".social-footer").append(
						'<div class="feed-element" id="' + data[i].replyId + '"><div class="social-comment"> <a href="" class="pull-left">' + '</a><div class="media-body"><a id=' + data[i].replyUserId + '>' + replyUserNickname + '&nbsp</a><span>' + data[i].replyContent + '</span>' + '<br/><small class="text-muted">' + json2TimeStamp(data[i].replyTime) + '</small>' + '<a class="pull-right" onclick="delComment(\'' + data[i].replyId + '\');" >删除</a>' + '<a class="pull-right replay">回复&nbsp</a></div>' + '<div style="display:none" class="carea" id=' + data[i].replyTime + '><div class="media-body"><textarea class="form-control" id="" placeholder="填写评论..."></textarea></div>'
								+ '<div style="margin-top:5px;" class="pull-right"><button class="btn btn-primary btn-xs" onclick="saveReply(this)" id="comment">评论</button></div></div></div></div>');
			} else {
				$(".social-footer").append(
						'<div class="feed-element" id="' + data[i].replyId + '"><div class="social-comment"> <a href="" class="pull-left">' + '</a><div class="media-body"><a id=' + data[i].replyUserId + '>' + replyUserNickname + '&nbsp</a>回复&nbsp<a id=' + data[i].replyToUserId + '>' + replyToUserNickname + '&nbsp</a>' + '<span>' + data[i].replyContent + '</span><br/><small class="text-muted">' + json2TimeStamp(data[i].replyTime) + '</small>' + '<a class="pull-right" onclick="delComment(\'' + data[i].replyId + '\');" >删除</a>' + '<a class="pull-right replay">回复&nbsp</a></div>' + '<div style="display:none" class="carea" id=' + data[i].replyTime + '><div class="media-body"><textarea class="form-control" id="" placeholder="填写评论..."></textarea></div>'
								+ '<div style="margin-top:5px;" class="pull-right"><button class="btn btn-primary btn-xs" onclick="saveReply(this)" id="comment">评论</button></div></div></div></div>');
			}

		}
	}
}

function saveComment() {
	if ($.trim($("#commentDetail").val()) == "") {
		layer.msg("评论内容不能为空", {
			time : 1000
		});
		return;
	}
	var reply = {
		topicId : topicId,
		replyUserId : loginUserName,
		replyContent : $("#commentDetail").val()
	};
	$.ajax({
		type : "post",
		url : getRootPath() + "forum.app?method=addReply",
		data : reply,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data != null) {
				replyAddResult(data);
				$("#commentDetail").val("");
			} else {
				layer.msg('评论失败！', {
					time : 1500
				});
			}
		},
		failure : function(xhr, msg) {
			layer.msg('操作失败！', {
				time : 2000
			}, function() {

			});
		}
	});

}

function saveReply(obj) {
	var replyContent = $(obj).parent().prev(".media-body").children("textarea").val();
	if ($.trim(replyContent) == "") {
		layer.msg("回复内容不能为空", {
			time : 1000
		});
		return;
	}
	var reply = {
		topicId : topicId,
		replyUserId : loginUserName,
		replyToUserId : replyToUserId,
		replyContent : replyContent
	};
	$.ajax({
		type : "post",
		url : getRootPath() + "forum.app?method=addReply",
		data : reply,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data != null) {
				replyAddResult(data);
			} else {
				layer.msg('回复失败！', {
					time : 1500
				});
			}
		},
		failure : function(xhr, msg) {
			layer.msg('操作失败！', {
				time : 2000
			}, function() {

			});
		}
	});
}

function replyAddResult(data) {
	layer.msg('评论成功！', {
		time : 1500
	}, function() {
		$(".carea").hide();
		var replyUserNickname = data.replyUserNickname;
		var replyToUserNickname = data.replyToUserNickname;
		if (data.replyAddrs != "" && typeof (data.replyAddrs) != "undefined") {
			replyUserNickname += "(" + data.replyAddrs + ")";
		}
		if (data.replyToAddrs != "" && typeof (data.replyToAddrs) != "undefined") {
			replyToUserNickname += "(" + data.replyToAddrs + ")";
		}
		var htm = '<div class="feed-element" id="' + data[i].replyId + '"> ' + '<div class="social-comment"> <a href="" class="pull-left">' + '</a>' + '<div class="media-body">' + '<a id=' + data.replyUserId + '>' + replyUserNickname + '&nbsp</a>回复&nbsp' + '<a id=' + data.replyToUserId + '>' + replyToUserNickname + '&nbsp</a>' + '<span>' + data.replyContent + '</span><br/>' + '<small class="text-muted">' + json2TimeStamp(data.replyTime) + '</small>' + '<a class="pull-right" onclick="delComment(\'' + data[i].replyId + '\');" >删除</a>' + '<a class="pull-right replay">回复&nbsp</a>' + '</div>' + '<div style="display:none" class="carea" id=' + data.replyTime + '>' + '<div class="media-body"><textarea class="form-control" id="" placeholder="填写评论..."></textarea></div>'
				+ '<div style="margin-top:5px;" class="pull-right">' + '<button class="btn btn-primary btn-xs" onclick="saveReply(this)" id="comment">评论</button>' + '</div>' + '</div>' + '</div>' + '</div>';

		$(".social-footer").append(htm);
		window.scrollTo(0, document.body.scrollHeight);
		$("a.replay").click(function() {
			var a = $(this).parent().next("div").id;
			var b = $(this).siblings("a")[0];
			$(".carea").hide();
			$(this).parent().next("div").show();
			$(this).parent().next("div").children(".media-body").children("textarea").attr("placeholder", "回复" + b.text);
			replyToUserId = b.id;
		});
	});

}

function setTopic(type) {
	var topic = {
		topicId : topicId
	};
	if (type == "1") {
		$.ajax({
			type : "post",
			url : getRootPath() + "forum.app?method=setElite",
			data : topic,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('帖子加精成功！', {
					time : 1500
				}, function() {
					window.location.reload();
				});
			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！', {
					time : 2000
				}, function() {

				});
			}
		});
	} else if (type == "2") {
		$.ajax({
			type : "post",
			url : getRootPath() + "forum.app?method=setTop",
			data : topic,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('帖子置顶成功！', {
					time : 1500
				}, function() {
					window.location.reload();
				});
			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！', {
					time : 2000
				}, function() {

				});
			}
		});
	}
}

function cancelSetTopic(type) {
	var topic = {
		topicId : topicId
	};
	if (type == "1") {
		$.ajax({
			type : "post",
			url : getRootPath() + "forum.app?method=cancelElite",
			data : topic,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('帖子取消加精成功！', {
					time : 1500
				}, function() {
					window.location.reload();
				});
			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！', {
					time : 2000
				}, function() {

				});
			}
		});
	} else if (type == "2") {
		$.ajax({
			type : "post",
			url : getRootPath() + "forum.app?method=cancelTop",
			data : topic,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('帖子取消置顶成功！', {
					time : 1500
				}, function() {
					window.location.reload();
				});
			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！', {
					time : 2000
				}, function() {

				});
			}
		});
	}
}

function delComment(strReplyId) {

	var replyId = {
		replyId : strReplyId
	};

	$.ajax({
		type : "post",
		url : getRootPath() + "forum.app?method=delReply",
		data : replyId,
		dataType : "json",
		async : false,
		success : function(data) {
			layer.msg('删除评论成功！', {
				time : 1500
			}, function() {
				$("#" + strReplyId).remove();
			});
		},
		failure : function(xhr, msg) {
			layer.msg('操作失败！', {
				time : 2000
			}, function() {

			});
		}
	});
}

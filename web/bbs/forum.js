var section = "";
$(function() {
	init();
	$('#myForm1').validationEngine();
	$("#zan").click(function() {
		alert("a");
	});
	$("#comment").click(function() {
		alert("b");
	});
});

function init() {
	oTable = new TableInit();
	oTable.Init();
	fileInput();
}

function initSection() {
	$.ajax({
		type : "post",
		url : getRootPath() + "forum.app?method=loadSection",
		async : false,
		success : function(data) {
			formatSection(data);
		},
		failure : function(xhr, msg) {
			layer.msg('操作失败！', {
				time : 2000
			}, function() {

			});
		}
	});
}

var TableInit = function() {
	var oTableInit = new Object();
	oTableInit.Init = function() {
		$('#topicList').bootstrapTable({
			url :  getRootPath() + 'forum.app?method=loadTopicList', // 请求后台的URL（*）
			striped : true,
			searchOnEnterKey : true,
			classes : "table table-no-bordered",
			sortOrder : "asc",
			sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
			cache : false,
			clickToSelect : false,
			queryParams : oTableInit.queryParams,
			minimumCountColumns : 2, // 最少允许的列数
			buttonsAlign : "left",
			showPaginationSwitch : false,
			pagination : true,
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 50, // 每页的记录行数（*）
			pageList : '[10, 25, 50, 100,ALL]', // 可供选择的每页的行数（*）
			columns : [ {
				field : 'topicId',
				visible : false
			}, {
				field : 'topicTitle',
				title : '标题',
				width : '45%',
				formatter : topicFormat,
				events : topicEvents
			}, {
				field : 'userNickname',
				title : '作者',
				formatter : userFormat
			}, {
				field : 'topicPostTime',
				title : '发表时间',
				formatter : timeFormat
			}, {
				field : 'check',
				title : '回复/查看/点赞',
				formatter : numFormat
			}, {
				field : 'operate',
				title : '操作',
				width : '6%',
				align : 'left',
				formatter : operateFormatter,
				events : operateEvents
			} ],

		});
	};
	function topicFormat(value, row) {
		var result = '<a id="a_title" href="topic.jsp?topicId=' + row.topicId + '">' + value + '</a>';
		if (row.topicTop == "1") {
			result = result + [ '<span class="label label-warning">顶</span>' ].join('');
		}
		if (row.topicElite == "1") {
			result = result + [ '<span class="label label-danger">精</span>' ].join('');
		}
		return result;
	}
	function operateFormatter(value, row, index) {
		var html = '<a id="a_del">删帖 <span style="color:#CCC"></span> </a>';
		return html;
	}
	// 操作列的事件
	window.operateEvents = {
		'click #a_del' : function(e, value, row, index) {
			deleteTopic(row);
		}
	};

	window.topicEvents = {
		'click #a_title' : function(e, value, row, index) {
			operateTopic(row);
		}
	};

	oTableInit.queryParams = function(params) {
		var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, // 页面大小
			offset : params.offset, // 页码
			search : params.search, // 表格搜索框的值
			sectionId : section
		};
		return temp;
	};
	numFormat = function(value, row) {
		return row.topicCommentTimes + " / " + row.topicViewTimes+" / "+row.topicZanTimes;
	};
	userFormat = function(value) {
		return value;
	};
	timeFormat = function(value) {
		return json2TimeStamp(value);
	};

	return oTableInit;

};

function openTopic(topicId) {
	window.open("topic.jsp?topicId=" + topicId);
}

function fileInput() {
	$("#logo").fileinput({
		uploadUrl :  getRootPath() + 'forum.app?method=uploadImg&folder=forum', // server upload action
		uploadAsync : false,
		showPreview : true,
		showUpload : false,
		showRemove : false,
		allowedFileExtensions : [ 'jpg', 'png' ],
		maxFileCount : 3,
		elErrorContainer : '#kv-error-1'
	}).on('filebatchpreupload', function(event, data, id, index) {
	}).on("filebatchselected", function(event, files) {
		$("#logo").fileinput("upload").on('filebatchuploadsuccess', function(event, data) {
			var status = data.response.status;
			var paths = data.response.paths;
			if (status == "1") {
				$("#imgRelativePath").val(paths);
			}
		});
	});
};

function operateTopic(row) {
	$("#postUser").html(row.topicUserId);
	$("#postTime").html(json2TimeStamp(row.topicPostTime));
	$("#content").html(row.topicContent);
}

function formatSection(data) {
	data = eval(eval(data));
	var a = 0;
	for (var i = 0; i < data.length; i++) {
		if (data[i].sectionId == "1") {
			$("#section01").text(data[i].count);
		} else if (data[i].sectionId == "2") {
			$("#section02").text(data[i].count);
		} else if (data[i].sectionId == "3") {
			$("#section03").text(data[i].count);
		} else if (data[i].sectionId == "4") {
			$("#section04").text(data[i].count);
		}
		a = a + data[i].count;
	}
	$("#all").text(a);
}

function sectionFilter(type) {
	if (type == "1") {
		section = "1";
		oTable = new TableInit();
		oTable.Init();
		$('#topicList').bootstrapTable('refresh');
	} else if (type == "2") {
		section = "2";
		oTable = new TableInit();
		oTable.Init();
		$('#topicList').bootstrapTable('refresh');
	} else if (type == "3") {
		section = "3";
		oTable = new TableInit();
		oTable.Init();
		$('#topicList').bootstrapTable('refresh');
	} else if (type == "4") {
		section = "4";
		oTable = new TableInit();
		oTable.Init();
		$('#topicList').bootstrapTable('refresh');
	} else if (type == "0") {
		section = "";
		oTable = new TableInit();
		oTable.Init();
		$('#topicList').bootstrapTable('refresh');
	}
}

function newTopic() {
	$("#logo").fileinput('clear');
	$("#section").val("1");
	$("#title").val("");
	$("#content").val("");
	$("#imgRelativePath").val("");
	$('#myModal').modal();
}

function getData() {
	var data = {
		topicTitle : $("#title").val(),
		topicContent : $("#content").val(),
		imgUrls : $("#imgRelativePath").val(),
		topicUserId : loginUserName
	};
	return data;
}

function save() {
	var flag = $('#myForm1').validationEngine('validate');
	if (flag) {
		var data = getData();
		$.ajax({
			type : "post",
			url :  getRootPath() + "forum.app?method=save",
			data : data,
			dataType : "json",
			async : false,
			success : function(data) {
				layer.msg('操作成功！', {
					time : 2000
				}, function() {
					$('#topicList').bootstrapTable('refresh');
					$('#myModal').modal('hide');
				});
			},
			failure : function(xhr, msg) {
				layer.msg('操作失败！', {
					time : 2000
				}, function() {

				});
			}
		});
	} else {
		layer.msg('表单验证未通过！', {
			time : 2000
		});
	}
}

function deleteTopic(row) {
	layer.confirm("您确定要删除此贴?", {
		skin : 'layui-layer-molv',
		btn : [ '确定', '取消' ]
	}, function() {
		var url =  getRootPath() + 'forum.app?method=delete';
		$.post(url, {
			topicId : row.topicId
		}, function(data) {
			layer.msg('操作成功！', {
				time : 2000
			}, function() {
				$('#topicList').bootstrapTable('refresh');
			});
		});
	}, function() {
		return;
	});
}
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.bstek.bdf2.core.model.DefaultUser" %> 
<%@ page import="com.bstek.bdf2.core.context.ContextHolder" %>
<% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
    DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
    request.setAttribute("loginUser",user);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
   .input-sm, .form-horizontal .form-group-sm .form-control1{
    height: 80px;
    width:60%;
    padding: 5px 10px;
    font-size: 12px;
    line-height: 1.5;
    border-radius: 3px;
}
</style>
<title>账单信息</title> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/msgandnotice/MessageSend.js"></script>
<script type="text/javascript">
function openWindow(type,id){
	//新增
	 if(type==1){
        var datetime=getNowFormatDate(true);
        emptyAll();
        $("#btnSave").show();
        $('#myModal').modal();
	 }
	 //查看
	 if(type==2){
		 $("#btnSave").hide();
		 $("#myModalLabel").html("查看消息");
		 selections = getIdSelections();
		 var length=selections.length;
		    var url="${ContextPath}"+"/msgandnotice/mgssend.app?method=viewForAjax&sendId="+id;
		    $.post(url,
		    	   function(data){
				   var list = eval(data);
				        $("#msgContent").val(list.msgContent);
				        $("#msgContentText").val(list.msgContent);
			            $("#msgTemp").val(list.msgTempId);
			            $("#msgReceiverNames").val(list.msgReceivers.ownerName);
			            $("#msgCreateTime").val(list.msgCreateTime);
			            $(".summernote").code(list.msgContent);
		    			$('#myModal').modal();
				    });
		 
	 }
 	 if(type==3){
 		layer.tab({
 			area: ['1000px', '450px'],
	 		//skin: 'layui-layer-lan', 
 			tab: [{
 			    title: 'TAB1', 
 			    /* content: '记者：近日，韩国电视剧《太阳的后裔》在中国热播，受到了不少中国年轻人的热捧。有舆论认为，中国军队应该学习借鉴韩剧有益经验， 拍出能够展现中国军队新面貌的电视剧。请问如何评论？据报道，中国证实在吉布提建设保障设施之后，正在用魅力攻势来缓解外界对这种军事扩张的担忧。请问如何看待这种担忧？能否介绍下一步吉布提军事保障设施的建设计划？杨宇军：先声明一下，我也是军旅题材影视作品的粉丝。至于你提到的这部韩国电视剧，如果我在这里表扬它的话，可能有做广告之嫌，但是如果我对它提出一些批评的意见，我担心电视剧的发行方以及网上喜欢它的网友可能会给国防部例行记者会差评。所以，我就不再具体回应这个问题了。关于第二个问题，我觉得这样一种担忧完全是多余的。根据联合国有关决议，从2008年以来，中方已经先后派出22批海军舰艇编队60余艘次舰艇赴亚丁湾索马里海域执行护航任务。在执行任务期间，我们感到编队官兵休整、食品和油料补给以及舰船维护保养等方面都面临着一些实际困难，确实有必要实施就近高效的后勤保障。中方经与吉布提方面进行协商，就中方在吉布提建设保障设施这个事情达成了一致。这一设施将主要用于中国军队执行亚丁湾和索马里海域护航、维和、人道主义救援等任务的休整补给保障。中国在吉布提建立保障设施，是为了更好地承担国际责任和义务，保护中国的合法利益。需要强调的是，中国坚持走和平发展道路，坚定奉行防御性国防政策，坚定维护世界和地区的和平稳定，从不搞军备竞赛和军事扩张，这些都是不会改变的。目前，中方在吉布提保障设施的基础工程建设已经启动，中方已经派遣了部分人员开展相关工作。' */
 			    content:'dfsdf'  
 			}, {
 			    title: 'TAB2', 
 			    content: 'sdfsfsd'
 			  }]
 			});
 	 }
 	 //删除
 	 if(type==4){
		 selections = getIdSelections();
		 var length=selections.length;
		 if(length==0){
			 layer.alert("请至少选择一条数据进行删除！",{
					skin: 'layui-layer-molv'
				});
		 }else{
			 layer.confirm("您确定要删除所选信息吗?",{
					skin: 'layui-layer-molv', 
					btn: ['确定','取消']
				},function(){
			        var url="${ContextPath}"+"/msgandnotice/mgssend.app?method=deleteByAjax"
	   			    var deleteIds = JSON.stringify(selections);
					$.post(url,
	   						{deleteId:deleteIds},
	   	    		        function(data){
	   							layer.msg('操作成功',
	   							          {time:2000},
	   									  function(){
	   									    $("#chargeInfo").bootstrapTable('refresh');
	   									  })
	   						});
				},function(){
					return;
				})
		 }
		 
 	 }
}
//关闭对话框的时候清空所有数据
function emptyAll(){
	$('.summernote').code("");
    $("#msgReceiverNames").val("");
    $("#msgReceiverIds").val(""); 
    $("#msgTemp").val(""); 
    $("#msgFlag").val("");
    $("#msgContent").val("");
    $("#msgContentText").val("");
    var action="${ContextPath}"+"/msgandnotice/mgssend.app?method=save";
    $("#myForm").attr("action",action);
}
//保存 表单中的内容 
function save(){
	var flag = $('#myForm').validationEngine('validate');
	var url;
	var arr;
	if(flag){
			var msgContent=$("#msgContent").val();
			var msgTemp=$("#msgTemp").val();
			var msgReceiverIds=$("#msgReceiverIds").val();
			 url="${ContextPath}"+"/msgandnotice/mgssend.app?method=save";
			 arr  =
			  {
			         "msgContent" :msgContent,
			         "msgTemp":msgTemp,
			         "msgReceiverIds":msgReceiverIds
			 }
			if(msgContent==""){
				layer.msg('请填写模板内容！',{
					time: 3000
				});	
			}else{
				$.post(url,
						arr,
					    function(data){
							layer.msg('操作成功',
							          {time:1000},
									  function(){
									    $("#chargeInfo").bootstrapTable('refresh');
									    $("#myModal").modal('hide');
										emptyAll();
									  })
							  });
				
			  }
		//$("#myModal").modal('hide');
	}else{
		layer.msg('表单验证未通过！',{
			time: 3000
		});	
	}
}
//去掉html标签
function delHtmlTag(str){
	  return str.replace(/<[^>]+>/g,"");//去掉所有的html标记
	 }
function setContent(){
	var value=$("#msgTemp").val()
	var id=value;
    var url="${ContextPath}"+"/msgandnotice/mgssend.app?method=getMsgTempContent&tempId="+id;
    $.post(url,
    	   function(data){
		   var list = eval(data);
	       var delHtmlContent=delHtmlTag(list.msgTempContent);
	       $("#msgContentText").val(delHtmlContent);
	       $("#msgContent").val(delHtmlContent);
		 });
    }
function func1(){
	layer.open({
		  title:['选择人员','color:white;'],
		  type: 2,
		  area: ['700px', '430px'],
		  fix: false, //不固定
		  maxmin: true,
		  content: 'SelectMsgReceiveer.jsp'
		});
}
</script>
</head>
<body class="gray-bg">
   <div class="wrapper wrapper-content">
   		<div style="width:100%;height:45px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
	        <div class="row" >
	                <div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
		    		  <!-- <button id="btn1" onclick="openWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;新增</button> -->
		    		  <!-- <button id="btn2" onclick="openWindow(2)" class="btn btn-primary " type="button"><i class="fa fa-edit"></i> 查看</button>
		    		  <button id="btn3" onclick="openWindow(4)" class="btn btn-primary " type="button"><i class="fa fa-trash-o"></i> 删除</button> -->
		    		
		    		</div>
		    </div>
	    </div>
	    <i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
   		<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
	    
        <!-- 更多查询区域  -->
	    	<div class ="row ibox float-e-margins" style="display:none;text-align:center;margin-left:auto;margin-right:auto;" id="more_search">
				<div class="ibox-content col-sm-12 b-r">
					<form class="form-horizontal">
								   
						<div class="form-group" style="margin-top:20px;">
							<label class="col-sm-1 control-label">标题</label>
	
							<div class="col-sm-3">
								<input type="text"  class="form-control"> 
							</div>
	
							<label class="col-sm-1 control-label">状态</label>
	
							<div class="col-sm-3">
								<input type="text"  class="form-control">
							</div>
							<label class="col-sm-1 control-label">创建时间起</label>
	
							<div class="col-sm-3">
								<input type="text" class="form-control">
							</div>
						 </div>
						<div class="col-sm-12">
							<button type="button" style="float:right;" class="btn btn-primary"><i class="fa fa-search"></i>查询</button> 
						</div>
					 </form>
				</div>
		    </div>
		<!-- 数据表格区域 -->
		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>消息管理<small></small></h5>
	                        <div class="ibox-tools">
	                            <a class="collapse-link">
	                                <i class="fa fa-chevron-up"></i>
	                            </a>
	                            <a class="dropdown-toggle" data-toggle="dropdown" href="table_data_tables.html#">
	                                <i class="fa fa-wrench"></i>
	                            </a>
	                            
	                            <a class="close-link">
	                                <i class="fa fa-times"></i>
	                            </a>
	                        </div>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="chargeInfo"></table>	
	                    </div>
	                </div>
	            </div>
	        </div>
        <!-- 弹出窗口区域，触发事件后弹出  -->
	       <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			  <div class="modal-dialog" style="width:930px">
				<div class="modal-content">
				  <div class="modal-header" style="background:#18a689">
					<button type="button" onclick="emptyAll()" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel" style="color:white">添加消息</h4>
				  </div>
				  <div class="modal-body">
					<form id="myForm" class="form-horizontal" action="${ContextPath}/msgandnotice/mgssend.app?method=save" method="post">
						  <div class="form-group form-group-sm ">
							  <label class="col-sm-1 control-label">接收人</label>
							  <!-- <div class="col-sm-3">
							  	 <input type="hidden" id="msgTempId" name="msgTempId">
								 <input class="form-control" name="msgTempSubject" id="msgTempSubject" type="text" required="" aria-required="true"/>
							  </div> -->
								<div class="col-sm-11">
									<div class="input-group">
										 <input type="text" class="form-control" id="msgReceiverNames" name="msgReceiverNames" readonly>
										 <input type="hidden" class="form-control" id="msgReceiverIds" name="msgReceiverIds">
										 <span class="input-group-btn">
											 <button class="btn btn-default btn-sm" type="button" onclick="func1();">
												 <i class="fa fa-search"></i>
											 </button>
										 </span>
									</div>
								 </div>
						   </div>
						   <div class="form-group form-group-sm ">
						      <label class="col-sm-1 control-label">消息内容</label>
							  <div class="col-sm-11">
							     <!-- <div class="ibox-tools"></div>
							     <div class="summernote"></div> -->
							     <textarea class="form-control" rows="6" id="msgContentText" style="height:60px"></textarea>
							     <input type="hidden" id="msgContent" name="msgContent">
                              </div>
						   </div>
						   <div class="form-group form-group-sm ">
							  <label class="col-sm-1 control-label">短信模板</label>
							  <div class="col-sm-3">
								 <!-- <input class="form-control" name="ntcAudit" id="ntcAudit" type="text" required="" aria-required="true"/> -->
							     <select class="form-control m-b" name="msgTemp" id="msgTemp" onchange="setContent();">   
                                     <option value="">---请选择---</option>
                                 </select>   
							  </div>
							  <label class="col-sm-1 control-label">发送时间</label>
							  <div class="col-sm-3">
								 <input class="form-control" name="msgCreateTime" id="msgCreateTime" type="text" readonly/>
							  </div>
						   </div>
						   <div class="form-group form-group-sm "></div>
						   <div class="modal-footer">
								<button type="button" onclick="emptyAll()" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button type="button" onclick="save()" class="btn btn-primary" data-dismiss="modal" id="btnSave">保存</button>
						   </div>
					</form>
	
				  </div>
				  
				</div>
			  </div>
			</div>
	</div>
  </body>
</html>
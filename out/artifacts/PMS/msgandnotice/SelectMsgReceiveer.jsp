<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$(function () {
    //初始化Table
    oTable = new TableInit();
    oTable.Init();
    //$(".summernote").summernote({lang:"zh-CN"});
    //initAuditors();
	//initMsgTemp();
	layer.config({
    	    extend: 'extend/layer.ext.js'
    	});
});
var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#chargeInfo').bootstrapTable({
			url: './../msgandnotice/mgssend.app?method=listOwner',         //请求后台的URL（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
    		striped: true,
    		search: true,
    		searchOnEnterKey: true,
    		showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            sortable: false,
            sortOrder: "asc",
            sidePagination: "client",			//分页方式：client客户端分页，server服务端分页（*）
            cache: false,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            showExport: "true",
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: '[10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
    		
                    {
                        field: 'state',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    }, {
                        field: 'roomNo',
                        title: '房间号',
                        sortable: true,
                        editable: true,
                        align: 'center'
                    },{
                        field: 'ownerName',
                        title: '业主姓名',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'phone',
                        title: '业主电话',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'buildName',
                        title: '所属楼盘',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'communityName',
                        title: '所属小区',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'storiedBuildName',
                        title: '所属楼宇',
                        sortable: true,
                        align: 'center',
                    }
               ]
		});
	};
	
	oTableInit.queryParams = function (params) {
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	        limit: params.limit,   //页面大小
	        offset: params.offset,  //页码
	        search: params.search
	    };
	    return temp;
	};
	dateFormate = function(value){
		if(value==null || value==""){
		    return "";
		}else{
			return json2Date(value);
		}
		
	};
	return oTableInit;
	
};
function submitOwer(){
	 var index = parent.layer.getFrameIndex(window.name);
	 //parent.$('#parentIframe').text('我被改变了');
	 //parent.layer.tips('Look here', '#parentIframe', {time: 5000});
	 //parent.layer.close(index);
	 selections = getIdSelections();
	 selectionsNames=getNameSelections();
	 var submitStrIds=selections.join(";");
	 var submitStrNames=selectionsNames.join(";");
	 parent.$('#msgReceiverNames').val(submitStrNames);
	 parent.$('#msgReceiverIds').val(submitStrIds);
	 parent.layer.close(index);
	 
}
function getIdSelections() {
    return $.map($('#chargeInfo').bootstrapTable('getSelections'), function (row) {
        return row.ownerId
    });
}
function getNameSelections() {
    return $.map($('#chargeInfo').bootstrapTable('getSelections'), function (row) {
        return row.ownerName
    });
}
</script>
</head>
    <body >
           <div class="row">            
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="chargeInfo"></table>	
			    		      	<button id="btn1" style="float:right;" onclick="submitOwer();" class="btn btn-primary " type="button">确定</button>
	                    </div>
	                </div>
	            </div>
	            
	        </div>
	        
	</body>
</html>
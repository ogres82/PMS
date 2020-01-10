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
	layer.config({
   	    extend: 'extend/layer.ext.js'
   	});
});
var TableInit = function (){
	var oTableInit = new Object();
	oTableInit.Init = function (){
		$('#receiverInfo').bootstrapTable({
			url: './../areaPlant/areaPlantList.app?method=userInfo',         //请求后台的URL（*）
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
            maintainSelected:true,
            clickToSelect: true,
            queryParams: oTableInit.queryParams,
            showExport: "true",
            minimumCountColumns: 2,     //最少允许的列数
            buttonsAlign: "left",
            showPaginationSwitch: false,
            pagination: true,
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: '[5, 10, 25, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
            {
                field: 'state',
                checkbox: true,
            }, {
            	field: 'username',
            	title: '用户名'
            }, {
            	field: 'cname',
            	title: '姓名'
            }, {
            	field: 'deptName',
            	title: '部门'
            }, {
            	field: 'positionName',
            	title: '职位'
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
	 selections = getIdSelections();
	 selectionsNames=getNameSelections();
	 var submitStrIds=selections.join(",");
	 var submitStrNames=selectionsNames.join(",");
	 parent.$('#receiversName').val(submitStrNames);
	 parent.$('#receivers').val(submitStrIds);
	 parent.layer.close(index);
	 
}
function getIdSelections() {
    return $.map($('#receiverInfo').bootstrapTable('getSelections'), function (row) {
        return row.username
    });
}
function getNameSelections() {
    return $.map($('#receiverInfo').bootstrapTable('getSelections'), function (row) {
        return row.cname
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
							<table class="table-no-bordered" id="receiverInfo"></table>	
			    		      	<button id="btn1" style="float:right;" onclick="submitOwer();" class="btn btn-primary " type="button">确定</button>
	                    </div>
	                </div>
	            </div>
	            
	        </div>
	        
	</body>
</html>
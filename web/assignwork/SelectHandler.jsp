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
		$('#chargeInfo').bootstrapTable({
			url: './../assingwork/personControl.app?method=engine',  
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
            pageSize: 15,                       //每页的记录行数（*）
            pageList: '[15, 30, 50, 100,ALL]',        //可供选择的每页的行数（*）         
    		columns: [ 
    		
                    {
                        field: 'state',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    }, {
                        field: 'cname',
                        title: '姓名',
                        sortable: true,
                        editable: true,
                        align: 'center'
                    },{
                        field: 'dept_name',
                        title: '部门',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'deption_name',
                        title: '岗位',
                        sortable: true,
                        align: 'center',
                    },
                    /* {
                        field: 'work_state',
                        title: '状态',
                        sortable: true,
                        align: 'center',
                        visable: 
                        formatter:stateFormat
                    }, */
                    {
                        field: 'work_times',
                        title: '派工次数',
                        sortable: true,
                        align: 'center',
                    },{
                        field: 'userName',
                        title: 'user',
                        sortable: true,
                        visible:false,
                        align: 'center',
                    }
               ]
		});
	};
	
	oTableInit.queryParams = function (params) {
	    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	     search: encodeURI(params.search)
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

//状态转换
function stateFormat(value, row, index) {
	if(value==0)
	{
		return ['<span class="label label-danger">空闲中</span>'].join('');
	}else if(value==1)
	{
		
		return ['<span class="label label-primary">工作中</span>'].join('');	
	}
	
}


function submitOwer(){
	 var index = parent.layer.getFrameIndex(window.name);
	 selections = getIdSelections();
	 selectionsNames=getNameSelections();
	 var submitStrIds=selections.join(";");
	 var submitStrNames=selectionsNames.join(";");
	 parent.$('#handerNames').val(submitStrNames);
	 parent.$('#handerIds').val(submitStrIds);
	 parent.layer.close(index);
	 
}
function getIdSelections() {
    return $.map($('#chargeInfo').bootstrapTable('getSelections'), function (row) {
        return row.userName
    });
}
function getNameSelections() {
    return $.map($('#chargeInfo').bootstrapTable('getSelections'), function (row) {
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
							<table class="table-no-bordered" id="chargeInfo"></table>	
			    		      	<button id="btn1" style="float:right;" onclick="submitOwer();" class="btn btn-primary " type="button">确定</button>
	                    </div>
	                </div>
	            </div>
	            
	        </div>
	        
	</body>
</html>
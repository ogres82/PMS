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
<title>我的代办</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ContextPath}/toptask/topTask.js"></script>
<script type="text/javascript" src="${ContextPath}/Hplus/js/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript">

//=====================================================================
//作  者：yenange , 20120525
//功能说明: 弹出一个模式窗口 （测试：IE6/7/8 和 Firefox 正常， Chrome可以弹出， 但长宽有变而且不是模式， 它本身不支持）
//输入参数: 路径,宽度,高度,参数(可选)
function popModal(url, width, height, parameter ) {
	parameter = { event_no:'BX20160520000003',type:'1' };
  var feature = 'dialogWidth=' + width+'px'
+ ';dialogHeight=' + height + 'px'
+ ';dialogTop=' + (screen.height - height) / 2 + 'px'
+ ';dialogLeft=' + (screen.width - width) / 2 + 'px'
+ ';help:no;resizable:no;status=no;scroll:no';
  if(typeof(parameter)=="undefined")
    return window.showModalDialog(url, feature);
  else
    return window.showModalDialog(url, parameter, feature);
}
//=====================================================================
//功能说明: 弹出一个窗口
//输入参数: 路径,窗口名称,宽度,高度
function pop(helpurl, windowName, width, height) {
  var feature ='width=' + width
    + ',height=' + height
    + ',top=' + (screen.height - height) / 2
    + ',left=' + (screen.width - width) / 2
    +',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no';
  window.open(helpurl, windowName, feature);
}
</script>
</head>
<body>
  <div>
    名字<input id="Name" type="text" /><br />
    年龄<input id="Age" type="text" /><br />
    <input id="Button1" type="button" value="打开模式窗口" onclick="popModal('/PMS/houseWork/houseWorkDispatch.jsp','900','900','1')" />
  </div>
</body>
</html>

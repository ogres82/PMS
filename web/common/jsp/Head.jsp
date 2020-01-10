<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
 %>
        
    <link href="${ContextPath}/Hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/bootstrap/css/bootstrap-table.css" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/bootstrap/css/bootstrap-editable.css" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/bootstrap/css/examples.css" rel="stylesheet" />
    
    <script src="${ContextPath}/Hplus/bootstrap/js/jquery.min.js"></script>
    <script src="${ContextPath}/Hplus/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/jeditable/jquery.jeditable.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/laydate/laydate.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${ContextPath}/Hplus/js/content.min.js?v=1.0.0"></script>
    <script src="${ContextPath}/Hplus/js/demo/form-validate-demo.min.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/summernote/summernote.min.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/summernote/summernote-zh-CN.js"></script>
    <script src="${ContextPath}/Hplus/js/plugins/layer/layer.js"></script>
    <link href="${ContextPath}/Hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/animate.min.css" rel="stylesheet" />
    <%-- <link href="${ContextPath}/Hplus/css/style.min.css?v=4.1.0" rel="stylesheet" /> --%>
    <link href="${ContextPath}/Hplus/css/plugins/dataTables/dataTables.bootstrap.css?v=4.1.0" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/plugins/summernote/summernote.css" rel="stylesheet" />
    <link href="${ContextPath}/Hplus/css/plugins/summernote/summernote-bs3.css" rel="stylesheet" />

 </head>
    
    
    
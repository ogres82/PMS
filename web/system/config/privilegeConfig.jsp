<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <% String path = request.getContextPath();
    request.setAttribute("ContextPath",path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>审核权限配置</title>
<%@ include file="/common/taglibs.jsp"%>
<script src="${ContextPath}/system/config/privilegeConfig.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-6">
								<form class="form-horizontal">
			                    	 <div class="form-group">
										  <label class="col-sm-4 control-label">公告一级审核</label>
										  <div class="col-sm-8">
										  		<select class="form-control" id="positionName1"> 
													<option value=""></option>
												</select>
												<input class="form-control" id="id1" type="hidden"/>
												<input class="form-control" id="type1" type="hidden" value="01"/>
										  </div>
								     </div>
								     <div class="form-group">
										  <label class="col-sm-4 control-label">公告二级审核</label>
										  <div class="col-sm-8">
										  		<select class="form-control" id="positionName2"> 
													<option value=""></option>
												</select>
												<input class="form-control" id="id2" type="hidden"/>
												<input class="form-control" id="type2" type="hidden" value="02"/>
										  </div>
								     </div>
								     <div class="form-group">
										  <label class="col-sm-4 control-label">主管交接班审阅</label>
										  <div class="col-sm-8">
										  		<select class="form-control" id="positionName3"> 
													<option value=""></option>
												</select>
												<input class="form-control" id="id3" type="hidden"/>
												<input class="form-control" id="type3" type="hidden" value="03"/>
										  </div>
								     </div>
								     <div class="form-group">
										  <label class="col-sm-4 control-label">消防中心交接班审阅</label>
										  <div class="col-sm-8">
										  		<select class="form-control" id="positionName4"> 
													<option value=""></option>
												</select>
												<input class="form-control" id="id4" type="hidden"/>
												<input class="form-control" id="type4" type="hidden" value="04"/>
										  </div>
								     </div>
								     <div id="btn" style="float:right;margin-top:20px;">
								     	  <button id="btn_save" onclick="save()" class="btn btn-primary " type="button"> 保存</button>
								     </div>
	                    		</form>
							</div>
						
						</div>
	                    
	                </div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
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
<title>车辆信息</title> 
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ContextPath}/basicInfo/VehicleInformation.js"></script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
    
	    <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
	    <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" id="topToolbar">
		   		
		   	    </div>
		   	</div>
   		</div>
	   	<i class="fa fa-search" style="position:fixed;right:17px;top:19px;z-index:1001;color:#e7eaec;font-size:15px;"></i>
   		<!-- 占位div -->
   		<div class="" style="width:100%;height:45px;"></div>
   		<!-- 占位div -->
   		
		   
   		<!-- 数据表格区域  -->  
   		<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>车辆信息<small></small></h5>
	                    </div>
	                    <div class="ibox-content" id="tempTable">
	                    	<div id="toolbar"></div>
							<table class="table-no-bordered" id="basicCarInfo"></table>	
	                    	
	                    </div>
	                    
	                </div>
	            </div>
	        </div>
      
		<!-- 弹出窗口区域，触发事件后弹出  -->
	     <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		 <div class="modal-dialog" style="width:800px">
		 <div class="modal-content">
		 <div class="modal-header" style="background:#18a689">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalTitle" style="color:white"></h4>
		 </div>
		 <div class="modal-body">
	          		<div class="tab-content">
		          		<div id="tab-1" class="tab-pane active">
		          			<form id="myForm1" class="form-horizontal" role="form">
					
								<fieldset id="basicInfo">
								<legend style="font-size:15px;border:0;"></legend>
									  
									   <div class="form-group form-group-sm ">
										  <label class="col-sm-2 control-label">车牌号</label>
										  <div class="col-sm-4">
											<input class="form-control validate[required]" id="plateNo" type="text" placeholder="必填项"/>
										  </div>
										  <label class="col-sm-2 control-label" >车主</label>
										  <div class="col-sm-4">
											<input class="form-control" id="ownerId" type="hidden" />
											<div class="input-group">
												<input type="text" class="form-control validate[required]" id="owner" placeholder="业主姓名/电话号码">
												<div class="input-group-btn">
													<button type="button" class="btn btn-white btn-sm dropdown-toggle" data-toggle="dropdown">
														<span class="caret"></span>
													</button>
													<ul class="dropdown-menu dropdown-menu-right" role="menu">
													</ul>
												</div>
										    </div>
										  </div>
									   </div>
									   <div class="form-group form-group-sm ">
								   		  <label class="col-sm-2 control-label">卡号</label>
										  <div class="col-sm-4">
											<input class="form-control validate[required]" id="cardNo" type="text" placeholder="必填项"/>
										  </div>
										  <label class="col-sm-2 control-label" >车辆类型</label>
										  <div class="col-sm-4">
											<select class="form-control validate[required]" id="carType"> 
											  <option value="">---请选择---</option>
											  <option value="1">小型车</option>
											  <option value="2">大型车</option>
											  <option value="0">其他</option>
											</select>
										  </div>
									   </div>
									   <div class="form-group form-group-sm ">
								   		  <label class="col-sm-2 control-label">车辆颜色</label>
										  <div class="col-sm-4">
											<select class="form-control validate[required]" id="carColor"> 
											  <option value="">---请选择---</option>
											  <option value="1">白色</option>
											  <option value="2">银色</option>
											  <option value="3">灰色</option>
											  <option value="4">黑色</option>
											  <option value="5">红色</option>
											  <option value="6">深蓝</option>
											  <option value="7">蓝色</option>
											  <option value="8">黄色</option>
											  <option value="9">绿色</option>
											  <option value="10">棕色</option>
											  <option value="11">粉色</option>
											  <option value="12">紫色</option>
											  <option value="0">其他颜色</option>
											</select>
										  </div>
										  <label class="col-sm-2 control-label" >车牌颜色</label>
										  <div class="col-sm-4">
											<select class="form-control validate[required]" id="plateColor"> 
											  <option value="">---请选择---</option>
											  <option value="0">蓝色</option>
											  <option value="1">黄色</option>
											  <option value="2">白色</option>
											  <option value="3">黑色</option>
											  <option value="4">绿色</option>
											  <option value="5">民航黑色</option>
											  <option value="0xff">其他</option>
											</select>
										  </div>
									   </div>
									   <div class="form-group form-group-sm ">
								   		  <label class="col-sm-2 control-label">车牌类型</label>
										  <div class="col-sm-4">
											<select class="form-control validate[required]" id="plateType"> 
											  <option value="">---请选择---</option>
											  <option value="0">标注民用车与军车车牌</option>
											  <option value="1">02式民用车牌</option>
											  <option value="2">武警车车牌</option>
											  <option value="3">警车车牌</option>
											  <option value="4">民用车双行尾牌</option>
											  <option value="5">事关车牌</option>
											  <option value="6">农用车车牌</option>
											</select>
										  </div>
										  <label class="col-sm-2 control-label">发证日期</label>
										  <div class="col-sm-4">
											<input class="form-control laydate-icon layer-date validate[required]" id="plateStart" type="text" />
										  </div>
									   </div>
									   <div class="form-group form-group-sm ">
								   		  <label class="col-sm-2 control-label">车辆品牌</label>
										  <div class="col-sm-4">
											<input class="form-control validate[required]" id="carBrand" type="text" placeholder="必填项"/>
										  </div>
										  <label class="col-sm-2 control-label">驾驶员姓名</label>
										  <div class="col-sm-4">
											<input class="form-control" id="driver" type="text" />
										  </div>
									   </div>
									   <div class="form-group form-group-sm ">
								   		  <label class="col-sm-2 control-label">驾驶员手机号码</label>
										  <div class="col-sm-4">
											<input class="form-control validate[required,custom[mobilephone]]" id="driverPhone" type="text" placeholder="必填项"/>
										  </div>
										  <label class="col-sm-2 control-label">核载人数</label>
										  <div class="col-sm-4">
											<input class="form-control validate[custom[number]" id="num" type="text" />
										  </div>
									   </div>
								  </fieldset>
			
							</form>
		          		</div>
	          		</div>
	          		</div>
			  	    <div class="form-group form-group-sm "></div>
				    <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button id="btBasicCarAdd" onclick="openSaveButton()" class="btn btn-primary " type="button" >保存</button>
				    </div>
				    <input class="form-control" id="hkPersonId" type="hidden" />
	          		</div>
	          		</div>
		 		</div>
		 	<!-- 弹出窗口区域，触发事件后弹出   结束 -->
    </div>
    <script>
    laydate.render({
        elem: '#plateStart',
        //format: 'YYYY年MM月DD日',
        festival: true, //显示节日
        choose: function(datas){ //选择日期完毕的回调
        	$('#myForm1').validationEngine('hide');
        }
    });
    </script>
</body>

</html>
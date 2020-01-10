1.获取业务单号：

	@Resource
	CommUtil commUtil;

	String businessId = commUtil.getBusinessId("aa","Y");//取得业务单号：业务单号+（年月日，年月，年）+6位流水号
	说明：
	第一个参数：aa 业务系统，不缺分大小写
	第二个参数：Y 年序号     AA2016000001
		    M 年月序号   AA201601000001
		    D 年月日序号 AA20160106000001
	
2.获取guuid方法：

	@Resource
	CommUtil commUtil;

	String guuid = commUtil.getGuuid();

3.url 传递参数获取
        var id="${param.taskId}";

4.数字转义(下拉框ID与Name的匹配)
mapValues:${dorado.getDataProvider("dirctoryComm#getDeleteId").getResult()};

5.当前用户
CommUser.getUserName();
ContextHolder.getLoginUser().getUsername();

6.dbConnect
url:rdsa4okii62o50bl0326o.mysql.rds.aliyuncs.com
user:pms_admin
pwd:1234qwer

登录
user:admin
pwd:123456

7.数据验证
  唯一性验证，适应各字段的验证，参数为Hql
  ValidatorsDao.uniqueCheck
  调用方式，例：
  vDao.uniqueCheck("from Emp where empName='"+parameter+"'");


8. 表单验证
   统一使用jquery.validationEngine.js进行表单验证， 插件文件在 Hplus/validate目录下,文件已引入到 公共文件taglibs.jsp下
   使用方法详见：http://code.ciaoca.com/jquery/validation-engine/
	
   使用步骤，初始化 验证插件 $('#myForm1').validationEngine();  保存前调用var flag = $('#myForm1').validationEngine('validate'); 返回true 或者 false

   项目中参照 Emp.jsp

9. 固定页面顶部 按钮行     参照 Emp.jsp

   //定位表格查询框
    js里面添加代码如下：

	$(".search").css({
		'position':'fixed',
		'left':'65%',
		'top':'0',
		'z-index':'1000',
		'width':'240px'
	});

    jsp页面里面的代码参照如下：

     <!-- 按钮及查询区域，此处按钮为占位符，实际加载的为view中的按钮控件  -->
	    <div style="width:100%;height:55px;background-color:#f3f3f4;position:fixed;left:0;top:0;padding:0 10px;z-index:1000">
			<div class="row">
		   		<div  class="col-md-6" style="float:left;margin-top:10px;" >
		   			 <button id="btn1" onclick="openButtonWindow(1)" class="btn btn-primary " type="button"><i class="fa fa-plus"></i>&nbsp;新增</button>
		   			 <button id="btn2" onclick="openButtonWindow(2)" class="btn btn-primary " disabled type="button"><i class="fa fa-edit"></i> 编辑</button>
		   			 <button id="btn3" onclick="openButtonWindow(3)" class="btn btn-primary " disabled type="button"><i class="fa fa-eye"></i> 查看</button>
		    		 <button id="btn4" onclick="openButtonWindow(4)" class="btn btn-primary " disabled type="button"><i class="fa fa-trash-o"></i> 删除</button>
		   	    </div>
			    <div class="col-md-6"  style="margin-top:10px;float:left">
					<p style="font-size:14px;margin-top:10px;margin-right:1%;width:90px;float:right" id="moreSearch">
						更多查询&nbsp;<i class="fa fa-angle-down"></i>
	                </p>
		   		</div>    		
		   	</div>
	   	</div>
	   	<!-- 占位div -->
   		<div class="" style="width:100%;height:55px;"></div>
   		<!-- 占位div -->

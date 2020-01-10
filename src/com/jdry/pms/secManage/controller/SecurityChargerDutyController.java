package com.jdry.pms.secManage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.Emp;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.msgandnotice.util.CharacterUtil;
import com.jdry.pms.secManage.pojo.SecurityChargerDuty;
import com.jdry.pms.secManage.service.SecurityChargerDutyService;

@Repository
@Component
public class SecurityChargerDutyController  implements IController{

	@Resource
	private SecurityChargerDutyService service;
	@Resource
	CommUtil commUtil;
	@Override
	public boolean anonymousAccess() {
		
		return false;
	}
	
	public String getList(){
		List<Emp> result=service.queryAll(null, null);
		Map<Object, Object> info = new HashMap<Object, Object>();
	    info.put("empInfo", result);
		String b = JSON.toJSONString(info);
		b = b.substring(b.indexOf(":")+1, b.length()-1);
		return b;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		String empId=null == arg0.getParameter("empId")?"":arg0.getParameter("empId").toString();//事件ID

		try {
			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("list")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			    Page<SecurityChargerDuty> page =new Page<SecurityChargerDuty>(showCount, currentPage);
			    service.queryChargerDutyByParam(page, parameter, null);
			    List<SecurityChargerDuty> result=(List<SecurityChargerDuty>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("edit") || method.equals("viewEmp")){
				arg1.setContentType("application/json;charset=utf-8");
				SecurityChargerDuty securityChargerDuty = new SecurityChargerDuty();
				securityChargerDuty = service.getChargerDutyById(empId);
				String jsonString = JSON.toJSONString(securityChargerDuty);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("add")){
				Calendar rightNow = Calendar.getInstance();
				int hour = rightNow.get(Calendar.HOUR_OF_DAY);
				int min=rightNow.get(Calendar.MINUTE);
				System.out.println(hour);
				String key="";
				if(hour>=9&&hour<=17){
					key="ZB";
				}else{
					key="WB";
				}
				arg1.setContentType("application/json;charset=utf-8");
				String recCode = commUtil.getBusinessId(key,"D");
				String result="{\"recCode\":\""+recCode+"\"}";
				PrintWriter out = arg1.getWriter();
				out.println(result);
				out.flush();
			}if(method.equals("getUserName")){
				arg1.setContentType("application/json;charset=utf-8");
				String userId= null == arg0.getParameter("userId")?"":arg0.getParameter("userId").toString();
				String userName=service.getUserNameById(userId);
				String result="{\"name\":\""+userName+"\"}";
				PrintWriter out = arg1.getWriter();
				out.println(result);
				out.flush();
			}if(method.equals("save")){
				String recId= null == arg0.getParameter("recId")?"":arg0.getParameter("recId").toString();				
				String recCode= null == arg0.getParameter("recCode")?"":arg0.getParameter("recCode").toString();
				String shiftType= null == arg0.getParameter("shiftType")?"":arg0.getParameter("shiftType").toString();
				String shiftDate= null == arg0.getParameter("shiftDate")?"":arg0.getParameter("shiftDate").toString();
				String shiftPasserId= null == arg0.getParameter("shiftPasserId")?"":arg0.getParameter("shiftPasserId").toString();
				String shiftDutyNum= null == arg0.getParameter("shiftDutyNum")?"":arg0.getParameter("shiftDutyNum").toString();
				String shiftDutyList= null == arg0.getParameter("shiftDutyList")?"":arg0.getParameter("shiftDutyList").toString();
				String shiftDutyVocate= null == arg0.getParameter("shiftDutyVocate")?"":arg0.getParameter("shiftDutyVocate").toString();
				String shiftDutyPerform= null == arg0.getParameter("shiftDutyPerform")?"":arg0.getParameter("shiftDutyPerform").toString();
				String shiftGoodsTransfer= null == arg0.getParameter("shiftGoodsTransfer")?"":arg0.getParameter("shiftGoodsTransfer").toString();
				String shiftTakeCase= null == arg0.getParameter("shiftTakeCase")?"":arg0.getParameter("shiftTakeCase").toString();
				String shiftDutyCase= null == arg0.getParameter("shiftDutyCase")?"":arg0.getParameter("shiftDutyCase").toString();
				String shiftPassCase= null == arg0.getParameter("shiftPassCase")?"":arg0.getParameter("shiftPassCase").toString();
				String shiftMarker= null == arg0.getParameter("shiftMarker")?"":arg0.getParameter("shiftMarker").toString();
				String shiftTakerId= null == arg0.getParameter("shiftTakerId")?"":arg0.getParameter("shiftTakerId").toString();
				String shiftTakeTime= null == arg0.getParameter("shiftTakeTime")?"":arg0.getParameter("shiftTakeTime").toString();
				String shiftCheckMarker= null == arg0.getParameter("shiftCheckMarker")?"":arg0.getParameter("shiftCheckMarker").toString();
				String shiftCheckerId= null == arg0.getParameter("shiftCheckerId")?"":arg0.getParameter("shiftCheckerId").toString();
				String shiftCheckTime= null == arg0.getParameter("shiftCheckTime")?"":arg0.getParameter("shiftCheckTime").toString();

				SecurityChargerDuty scd = new SecurityChargerDuty();
				if(recId !=null && !recId.isEmpty()){
					scd.setRecId(recId);
				}
				scd.setRecCode(recCode);
				scd.setShiftCheckerId(shiftCheckerId);
				scd.setShiftCheckMarker(shiftCheckMarker);
				scd.setShiftCheckTime(DateUtil.convertStringToDate(shiftCheckTime, "yyyy-MM-dd HH:mm:ss"));
				scd.setShiftDate(DateUtil.convertStringToDate(shiftDate, "yyyy-MM-dd HH:mm:ss"));
				scd.setShiftDutyCase(shiftDutyCase);
				scd.setShiftDutyList(shiftDutyList);
				scd.setShiftDutyNum(Integer.valueOf(shiftDutyNum));
				scd.setShiftDutyPerform(shiftDutyPerform);
				scd.setShiftDutyVocate(shiftDutyVocate);
				scd.setShiftGoodsTransfer(shiftGoodsTransfer);
				scd.setShiftMarker(shiftMarker);
				scd.setShiftPassCase(shiftPassCase);
				scd.setShiftPasserId(shiftPasserId);
				scd.setShiftTakeCase(shiftTakeCase);
				scd.setShiftTakerId(shiftTakerId);
				scd.setShiftTakeTime(DateUtil.convertStringToDate(shiftTakeTime, "yyyy-MM-dd HH:mm:ss"));
				scd.setShiftType(shiftType);
				service.addSecurityChargerDuty(scd);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("delete")){

//				String deleteIds=arg0.getParameter("deleteId");
				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(empId)){
						service.deleteEmp(empId);
					}
					String jsonString = JSON.toJSONString("删除成功！");
					out.println(jsonString);
					out.flush();

				}catch(Exception e){
					e.printStackTrace();
					String jsonString = JSON.toJSONString("删除失败！");
					out.println(jsonString);
					out.flush();
				}
			
			}else if(method.equalsIgnoreCase("initUserDrop")){
				List<DefaultUser> users=service.getAllUser();
				String jsonString = JSON.toJSONString(users);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}else if(method.equalsIgnoreCase("initDropAll")){
				List<DirDirectoryDetail> positions=service.getDirectoryLikeCode("emp_status");
				String jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}else if(method.equalsIgnoreCase("initDeptDrop")){
				Map<String, Object> parameter = new HashMap<String, Object>();
				List<DefaultDept> depts=(List<DefaultDept>) service.queryDept(parameter);
				String jsonString = JSON.toJSONString(depts);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}else if(method.equalsIgnoreCase("initPositionDrop")){
				Map<String, Object> parameter = new HashMap<String, Object>();
				List<DefaultPosition> positions=(List<DefaultPosition>) service.queryPosition(parameter);
				String jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl() {
		return "/secmanage/chargerDutyList";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}

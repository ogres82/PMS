package com.jdry.pms.assignWork.controller;
/**
 * 测试专用
 */
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.VWorkCompEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.assignWork.service.ImpairedRepairService;
import com.jdry.pms.assignWork.service.PropertyManageComplaintService;
import com.jdry.pms.assignWork.service.ReportDetailInterService;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.soft.service.GrapService;
@Repository
@Component
public class PropertyManageServiceInterface implements IController {
 @Resource
  PropertyManageComplaintService propertyManageComplaintService;
 
 @Resource
	AssignWorkService assignWorkService;
 @Resource
	GrapService grapService;
 @Resource
	ReportDetailInterService reportDetailInterService;
 @Resource
	ImpairedRepairService impairedRepairService;
 @Resource
 ReportDetailInterface reportDetailInterface;
 @Resource
 ImpairedRepairServiceInterface impairedRepairServiceInterface;
//收费接口
	@Resource
	ChargeInfoService chargeInfoService;
	@Override
	public boolean anonymousAccess()
	{
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException 
	{
		String method=null == arg0.getParameter("method")?"":arg0.getParameter("method").toString();//方法
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		if(method.equals("getDispatchHistory"))
		{
			String status="";
			String message="";
			String comp_id=null == arg0.getParameter("data")?"":arg0.getParameter("data").toString();//业务编号
			WorkComplaintEntity work = JSON.parseObject(comp_id, WorkComplaintEntity.class);
			Map map = new HashMap();
			if(work.getComp_operator_id().equals(""))
			{
				status="2";	
				message="请传派工单号";	
				map.put("status", status);
				map.put("message", message);
				map.put("mtr_id", "");
			}else{
				List ls= propertyManageComplaintService.getDispatchHistory("hezuping");
				List res=new ArrayList();
			if(ls.size()<1)
			{
				status="1";	
				message="查询无数据";
			}else
			{
				for(int i=0,len=ls.size();i<len;i++)
				{
					Map mp=new HashMap();
					Object[] obj = (Object[])ls.get(i);
					String addres = null == obj[0]?"":obj[0].toString();
					mp.put("addres", addres);
					String event_content = null == obj[1]?"":obj[1].toString();
					mp.put("event_content", event_content);
					String createTime = null == obj[2]?"":obj[2].toString();
					mp.put("createTime", createTime);
					String event_state = null == obj[3]?"":obj[3].toString();
					mp.put("event_state", event_state);
					String code_detail_name = null == obj[4]?"":obj[4].toString();
					mp.put("code_detail_name", code_detail_name);
					res.add(mp);
					
				}
				status="1";	
				message="查询成功";	
			}
			map.put("status", status);
			map.put("message", message);
			map.put("data", res);
			}
			String jsonString = JSON.toJSONString(map);
		}
		if(method.equals("getDispatchComplantDetail"))//获取派工单详情
		{
			String status="";
			String message="";
			Map map = new HashMap();
			List res=propertyManageComplaintService.getDisPatchComplaintDetail("TS20160419000002");	
			if(res.size()<1)
			{
				status="1";	
				message="没有查询到相关数据";
			}else
			{
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Object[] obj = (Object[])res.get(0);
			String comp_detail = null == obj[0]?"":obj[0].toString();
			map.put("comp_detail", comp_detail);
			
			String comp_createTime = null == obj[1]?"":obj[1].toString();
			map.put("comp_createTime", comp_createTime);
			
			String comp_status = null == obj[2]?"":obj[2].toString();
			map.put("comp_status", comp_status);
			
			String comp_reply = null == obj[3]?"":obj[3].toString();
			map.put("comp_reply", comp_reply);
			
			String rpt_name = null == obj[4]?"":obj[4].toString();
			map.put("rpt_name", rpt_name);
			String in_call = null == obj[5]?"":obj[5].toString();
			map.put("in_call", in_call);
			String addres = null == obj[6]?"":obj[6].toString();
			map.put("addres", addres);
			
			status="1";	
			message="查询成功";
			}
			Map jsonMap=new HashMap();
			jsonMap.put("status", status);
			jsonMap.put("message", message);
			jsonMap.put("data", map);
			
			String jsonString = JSON.toJSONString(jsonMap);
		
		}
		if(method.equals("getComplaintHandle"))//投诉处理
		{
			String status="";
			String message="";
			String mtn_id="TS20160424000002";
			VWorkCompEntity vw=propertyManageComplaintService.getComplaintHandle(mtn_id);
			if(vw==null)
			{
				status="0";
				message="未到处理环节或已处理";
			}else
			{
				String mtnid=null==vw.getMtn_id()?"":vw.getMtn_id();
				String comp_operator_id=null==vw.getRpt_name()?"":vw.getRpt_name();//是否显示中文
				
				String comp_emergency=null==vw.getComp_emergency()?"":vw.getComp_emergency();
				String Comp_status=null==vw.getComp_status()?"":vw.getComp_status();
				String Comp_status_name= null == vw.getComp_status_name()?"":vw.getComp_status_name().toString();
				//String comp_result= null == vw.get?"":arg0.getParameter("comp_result").toString();
				//String comp_process= null == vw.getarg0.getParameter("comp_process")?"":arg0.getParameter("comp_process").toString();
				//String comp_solve= null == arg0.getParameter("comp_solve")?"":arg0.getParameter("comp_solve").toString();
				//String comp_degree= null == arg0.getParameter("comp_degree")?"":arg0.getParameter("comp_degree").toString();
				

				WorkMainEntity mainEntity = assignWorkService.getWorkMainById(mtn_id);
				String state = "";
				if(null == mainEntity){
					mainEntity = new WorkMainEntity();
				}
				WorkComplaintEntity complaintEntity = assignWorkService.getWorkComplById(mtn_id);
				
				if(null == complaintEntity){
					complaintEntity = new WorkComplaintEntity();
				}
				if(Comp_status.equals("1"))
				{
				complaintEntity.setComp_operator_id(comp_operator_id);
				complaintEntity.setComp_result("");
				complaintEntity.setComp_process("");
				complaintEntity.setComp_solve("");
				complaintEntity.setComp_degree("");
				complaintEntity.setComp_detail("");
				complaintEntity.setComp_emergency(comp_emergency);
				complaintEntity.setComp_createby(user.getUsername());
				complaintEntity.setComp_createTime(new Date());
				complaintEntity.setComp_status("2");
				state = complaintEntity.getComp_status();
				}
				assignWorkService.addWorkCompl(complaintEntity);
				String processinstanceid = mainEntity.getProcessInstanceId()+"";
				grapService.completeBussniseTask(processinstanceid, user.getCname(), null,state);
				
				Map map=new HashMap();
				map.put("rpt_id", mtnid);
				map.put("comp_createTime", vw.getCreateTime());
				map.put("comp_status", Comp_status);
				map.put("Comp_status_name", Comp_status_name);
				
			}
			
			
			
		}
		if(method.equals("getReportDetailInfo"))
		{
			
			
			
			String data=null == arg0.getParameter("data")?"":arg0.getParameter("data").toString();//业务编号
			System.out.println(reportDetailInterface.getReportDetailInfo(data));

			/* String status="";
			 String message="";
			 Map map = new HashMap();
			
			    List res=reportDetailInterService.getReportDetailList("BX20160523000001");
			    
			    List ls=reportDetailInterService.getReportDeailInfoList("BX20160523000001");
			    List detailLs=new ArrayList();
			    if(ls.size()<1)
			    {
			    	ls.add("无相关详情");
			    }else{
			    for(int i=0,len=ls.size();i<len;i++)
				{
			    	Map mp = new HashMap();
			    	Object[] obj = (Object[])ls.get(0);
					String dispatch_status = null == obj[0]?"":obj[0].toString();
					mp.put("status", dispatch_status);
					String createby = null == obj[1]?"":obj[1].toString();
					mp.put("handler", createby);
					String handler_dept = null == obj[2]?"":obj[2].toString();
					mp.put("handler_dept", handler_dept);
					String dispatch_sl_time = null == obj[3]?"":obj[3].toString();
					mp.put("handle_time", dispatch_sl_time);
					String handler_phone = null == obj[4]?"":obj[4].toString();
					mp.put("handler_phone", handler_phone);
					detailLs.add(mp);
					status="1";
					message="查询成功";
				 }
			    }
			   
			    if(res.size()<1)
			    {
			    	status="1";
					message="查询无记录";
			    }else
			    {
				Object[] obj = (Object[])res.get(0);
				String repair_time = null == obj[0]?"":obj[0].toString();
				map.put("repair_time", repair_time);
				String repair_content = null == obj[1]?"":obj[1].toString();
				map.put("repair_content", repair_content);
				//map.put("status", status);
				//map.put("message", message);
				map.put("data", detailLs);
				Map hp=new HashMap();
				hp.put("status", status);
				hp.put("message", message);
				hp.put("data", map);
				String jsonString = JSON.toJSONString(hp);*/
			   // }
			
		}
		if(method.equals("getRepairHistory"))
		{
			

			String status="";
			String message="";
			
			List res=reportDetailInterService.getRepairHistoryByPhone("13985411015");
			List ls=new ArrayList();
			Map map=new HashMap();
			if(res.size()<1)
			{
				status="0";
				message="查询无记录";
			}else
			{
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(int i=0,len=res.size();i<len;i++)
				{

			    	Map mp = new HashMap();
			    	Object[] obj = (Object[])res.get(i);
					String rpt_id = null == obj[0]?"":obj[0].toString();
					mp.put("rpt_id", rpt_id);
					String createTime = null == obj[1]?"":obj[1].toString();
					Date comp_createTime1 = null;
					String comp_createTime="";
					try {
						if (!createTime.equals("")) {
							comp_createTime1 = sd.parse(createTime);
							comp_createTime = sd.format(comp_createTime1);
						}
					} catch (ParseException e) {

						status="2";
						message = "后台异常，请联系技术人员";
					}
					
					
					map.put("comp_createTime", comp_createTime);
					String mtn_detail = null == obj[2]?"":obj[2].toString();
					mp.put("repair_content", mtn_detail);
					
					String dispatch_status = null == obj[3]?"":obj[3].toString();
					mp.put("comp_status", dispatch_status);
					String code_detail_name = null == obj[4]?"":obj[4].toString();
					mp.put("comp_statusName", code_detail_name);
					ls.add(mp);
					status="1";
					message="查询成功";
				}
				map.put("status", status);
				map.put("message", message);
				map.put("data", ls);
				String jsonString = JSON.toJSONString(map);
			}
			
			
		}
		if(method.equals("getPreHandleEvent"))
		{
			String mrid="TS20160429000008";
			String state="2";
			WorkMainEntity mainEntity = assignWorkService.getWorkMainById(mrid);
			mainEntity.setEvent_state(state);
			VWorkCompEntity vw=propertyManageComplaintService.getComplaintHandle(mrid);
			WorkComplaintEntity work=assignWorkService.getWorkComplById(mrid);
			work.setComp_status(state);
			assignWorkService.addWorkMain(mainEntity);
			//assignWorkService.addEventSend("");
			assignWorkService.addWorkCompl(work);
			
			String processinstanceid=mainEntity.getProcessInstanceId()+"";;
			grapService.completeBussniseTask(processinstanceid, user.getCname(),null,state);
			System.out.println(vw);
		}//报障报修物管端
		if(method.equals("getProptyImpairRepHistory"))
		{
			
			 Map map = new HashMap();
			 List ImpariList= impairedRepairService.getProptyImpairRepHistory("huanglin");
			 List ls=new ArrayList();
			 
			 if(ImpariList.size()<1)
			    {
			    	ls.add("无相关详情");
			    }else{
			    for(int i=0,len=ImpariList.size();i<len;i++)
				{
			    	Map mp = new HashMap();
			    	Object[] obj = (Object[])ImpariList.get(i);
					String rpt_id = null == obj[0]?"":obj[0].toString();
					mp.put("rpt_id", rpt_id);
					String createTime = null == obj[1]?"":obj[1].toString();
					mp.put("createTime", createTime);
					String event_content = null == obj[2]?"":obj[2].toString();
					mp.put("event_content", event_content);
					String address = null == obj[3]?"":obj[3].toString();
					mp.put("address", address);
					String dispatch_status = null == obj[4]?"":obj[4].toString();
					mp.put("comp_status", dispatch_status);
					ls.add(mp);
					
				 }
			    }
			    map.put("status", "1");
				map.put("message", "成功");
				map.put("data", ls);
				String jsonString = JSON.toJSONString(map);
			
		}
		if(method.equals("getProptyImpairRepDetailByEventId"))
		{
			
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List ls=new ArrayList();
			WorkMainEntity work=impairedRepairService.getProptyImpairRepDetailByEventId("BX20160505000001");
			Map map = new HashMap();
			if(work!=null)//如果报修单不为空，则执行
			{
			
				 List res=impairedRepairService.getDispatchStepByEventId(work.getRpt_id());
				 if(res.size()<1)
				    {
				    	ls.add("无相关详情");
				    }else{
				    for(int i=0,len=res.size();i<len;i++)
					{
				    	Map mp = new HashMap();
				    	Object[] obj = (Object[])res.get(i);
						String handle_time = null == obj[0]?"":obj[0].toString();
						String comp_createTime = "";
						Date comp_createTime1 = null;
						try {
							if (!handle_time.equals("")) {
								comp_createTime1 = sd.parse(handle_time);
								comp_createTime = sd.format(comp_createTime1);
							}
						} catch (ParseException e) {

							//status="0";
							//message = "后台异常，请联系技术人员";
						}
						mp.put("handle_time", comp_createTime);
						String cname = null == obj[1]?"":obj[1].toString();
						mp.put("cname", cname);
						String handler_dept = null == obj[2]?"":obj[2].toString();
						mp.put("handler_dept", handler_dept);
						String handler_phone = null == obj[3]?"":obj[3].toString();
						mp.put("handler_phone", handler_phone);
						String status = null == obj[4]?"":obj[4].toString();
						mp.put("comp_status", status);
						ls.add(mp);
					 }
				    String comp_createTime = "";
					if (!work.getCreateTime().equals("")) {
						comp_createTime = sd.format(work.getCreateTime());
					}
				    map.put("createTime",comp_createTime );
				    map.put("event_content", work.getEvent_content());
				    map.put("repair_name", work.getRpt_name());
				    map.put("phone", work.getIn_call());
				    map.put("address", work.getAddress()+"");
				    map.put("repairDetail", ls);

				    Map jmp=new HashMap();
				    jmp.put("status", "1");
				    jmp.put("message", "成功");
				    jmp.put("data", map);
				   
				    String jsonString = JSON.toJSONString(jmp);
				    }
			
			}
			
			
		}
		if(method.equals("dispachHandleByApp"))
		{
			
			boolean flag=false;
		    String data=arg0.getParameter("data");
			JSONObject Obj= (JSONObject) JSON.parse(data);
			String rpt_id=(String) Obj.get("rpt_id");//解析json
			String labor_cost=(String) Obj.get("labor_cost");
			String material_cost=(String) Obj.get("material_cost");
			String sum_price=(String) Obj.get("sum_price");
			String mtn_type=(String) Obj.get("mtn_type");
			String mtn_priority=(String) Obj.get("mtn_priority");
			
			WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(rpt_id);
			
			
			/**
			 * 驱动流程图
			 */
			WorkMainEntity mainEntity = assignWorkService.getWorkMainById(rpt_id);
			
			if(mainEntity == null){
				mainEntity = new WorkMainEntity();
			}
			try{
			Map map=new HashMap<String,Object>();
			
			map.put("room_id", mainEntity.getOwner_house());
			map.put("room_no", mainEntity.getRoomNo());
			map.put("owner_id", mainEntity.getOwner_id());
			map.put("owner_name", mainEntity.getRpt_name());
			map.put("begin_time", getSimp(new Date()));
			map.put("end_time",  getSimp(new Date()));
			
			map.put("oper_emp_id", dispatchEntity.getDispatch_handle_id());
			if(!("").equals(material_cost))
			{
				map.put("charge_type_no", "0007");
				map.put("charge_type_name", "维修材料费");
				map.put("receive_amount", material_cost);//材料费
				flag=chargeInfoService.addCharge(map);
				
			}
			if(!("").equals(labor_cost))
			{
				map.put("charge_type_no", "0006");
				map.put("charge_type_name", "人工费");
				map.put("receive_amount", labor_cost);//人工费
				flag=chargeInfoService.addCharge(map);	
			}
			}catch(Exception e)
			{
				
			}
			
			
			String state = "";
			dispatchEntity.setMtn_type(mtn_type);
			dispatchEntity.setDispatch_expense(sum_price);//总价
			if(labor_cost.equals(""))
			{
				dispatchEntity.setLabor_cost(0.0);
				
			}else{
			dispatchEntity.setLabor_cost(Double.parseDouble(labor_cost));
			}
			if(material_cost.equals(""))
			{
				dispatchEntity.setMaterial_cost(0.0);
			}else{
			 dispatchEntity.setMaterial_cost(Double.parseDouble(material_cost));
			 dispatchEntity.setDispatch_result("处理结果满意");
			}
			
			dispatchEntity.setDispatch_arrive_time(new Date());
			dispatchEntity.setDispatch_status("2");
			dispatchEntity.setMtn_priority(mtn_priority);//业主处理意见
		
			//流程条件
			Map<String,Object> variables = new HashMap<String,Object>();
			//flag==2 质保期...公共维修,  flag==1 现场确认及费用核算
			if(null != mtn_type && mtn_type.equals("1")){
				//日常紧急维修
				variables.put("flag", "2");
				//compFlag==1  业主拒绝维修         compFlag==2业主确认维修
				if(null != mtn_priority && mtn_priority.equals("0")){
					//业主维修意见
					variables.put("compFlag", "2");
				}else if(null != mtn_priority && mtn_priority.equals("1")){
					variables.put("compFlag", "1");
				}
			}else{
				variables.put("flag", "1");
			}
			
			state = dispatchEntity.getDispatch_status();
		    //派工历史纪录
			EventSendEntity send=new EventSendEntity();
			send.setHandle_time(dispatchEntity.getDispatch_arrive_time());
			send.setOwnHandler(dispatchEntity.getDispatch_handle_id());
			send.setEvent_id(dispatchEntity.getMtn_id());
			String deptName=assignWorkService.getdept(dispatchEntity.getDispatch_handle_id());
			String dept_name=null==deptName?"":deptName;
			if(dept_name.equals(""))
			{
				send.setHandler_dept("系统");	
			}else{
			send.setHandler_dept(dept_name);
			}
			String phone=assignWorkService.getHandlePhone(dispatchEntity.getDispatch_handle_id());
			send.setHandler_phone(phone);
			send.setStatus(state);
			
			//开始流程
			String processinstanceid = mainEntity.getProcessInstanceId()+"";
			
				if(null != processinstanceid && processinstanceid.length()>0){
					grapService.completeBussniseTask(processinstanceid, dispatchEntity.getDispatch_handle_id(), variables,state);
				}
			
			  if(null != processinstanceid && processinstanceid.length()>0){
				grapService.completeBussniseTask(processinstanceid, dispatchEntity.getDispatch_handle_id(), variables,state);
			  }
			assignWorkService.addWorkDispatch(dispatchEntity);
			assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(),dispatchEntity.getDispatch_status());//更新主表状态
			assignWorkService.addEventSend(send);
			
			    Map map=new HashMap();
			    map.put("status", "1");
				map.put("message", "成功");
				map.put("rpt_id", dispatchEntity.getMtn_id());
				String jsonString = JSON.toJSONString(map);
			    System.out.println(jsonString);
		}
		if(method.equals("impairedRepairFinsh"))
		{
			String state = "";
			String data=arg0.getParameter("data");
			JSONObject Obj= (JSONObject) JSON.parse(data);
			String rpt_id=(String) Obj.get("rpt_id");//解析json
			EventSendEntity send=new EventSendEntity();
			
			WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(rpt_id);
			WorkMainEntity mainEntity = assignWorkService.getWorkMainById(rpt_id);
			dispatchEntity.setDispatch_finish_time(new Date());
			dispatchEntity.setDispatch_status("3");
			state = dispatchEntity.getDispatch_status();
			
			send.setHandle_time(null);
			send.setOwnHandler(user.getUsername());
			send.setEvent_id(dispatchEntity.getMtn_id());
			String deptName=assignWorkService.getdept(user.getUsername());
			String dept_name=null==deptName?"":deptName;
			if(dept_name.equals(""))
			{
				send.setHandler_dept("系统");	
			}else{
			send.setHandler_dept(dept_name);
			}
			String phone=assignWorkService.getHandlePhone(user.getUsername());
			send.setHandler_phone(phone);
			send.setStatus(state);
			
			String processinstanceid = mainEntity.getProcessInstanceId()+"";
			assignWorkService.addWorkDispatch(dispatchEntity);
			
			//===
			mainEntity.setFinishTime(new Date());
			assignWorkService.addWorkMain(mainEntity);
			//完成任务
			assignWorkService.addWorkWorkState(dispatchEntity.getDispatch_handle_id(), 0);
			assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(),dispatchEntity.getDispatch_status());//更新主表状态
			assignWorkService.addEventSend(send);
			if(null != processinstanceid && processinstanceid.length()>0){
				grapService.completeBussniseTask(processinstanceid, user.getCname(), null,state);
			}
			Map map=new HashMap();
		    map.put("status", "1");
			map.put("message", "成功");
			map.put("rpt_id", dispatchEntity.getMtn_id());
			String jsonString = JSON.toJSONString(map);
		    System.out.println(jsonString);
			
		}
		if(method.equals("confirmBeforeCancel"))
		{
			String data=null == arg0.getParameter("data")?"":arg0.getParameter("data").toString();//业务编号
			System.out.println(impairedRepairServiceInterface.confirmBeforeCancel(data));
			
		}
		
	}

	@Override
	public String getUrl() {
		
		return "/properyManger/interfaceTest";
	}

	@Override
	public boolean isDisabled()
	{
		return false;
	}
	
	/**
	 * 时间转换
	 * @param time
	 * @return
	 */
	public  String getSimp(Date time)
	{
		
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		String time1=sd.format(time);
		return time1;
		
	}

}

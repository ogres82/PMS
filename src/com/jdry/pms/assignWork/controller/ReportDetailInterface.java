package com.jdry.pms.assignWork.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.assignWork.service.ImpairedRepairService;
import com.jdry.pms.assignWork.service.ReportDetailInterService;
import com.jdry.pms.assignWork.service.VisitWorkServerInterfaceService;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.chargeManager.pojo.ChargeInfoEntity;
import com.jdry.pms.chargeManager.service.ChargeInfoService;
import com.jdry.pms.comm.util.SpringUtil;
import com.soft.service.GrapService;

/**
 * 描述：报障保修业主端信息
 * @author hezuping
 *
 */
@Repository
@Component
public class ReportDetailInterface
{
	@Resource
	ReportDetailInterService reportDetailInterService;
	@Resource
	AssignWorkService assignWorkService;
	@Resource
	ImpairedRepairService impairedRepairService;
	@Resource
	GrapService grapService;
	@Resource
	ChargeInfoService chargeInfoService;
	@Resource
	VisitWorkServerInterfaceService vs;
	
	public static String EVENTSTATE="5";
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	static Logger log=Logger.getLogger(ReportDetailInterface.class);
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String getReportDetailInfo(String mtr_id)
	{
		 String status="";
		 String message="";
		 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Map map = new HashMap();
		if(reportDetailInterService == null)//创建上下文环境
		 {
			reportDetailInterService = (ReportDetailInterService) SpringUtil.getObjectFromApplication("reportDetailInterServiceImpl");
		 }
		
		if(chargeInfoService == null)//创建上下文环境
		 {
			chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		 }
		
		if(impairedRepairService == null)//创建上下文环境
		 {
			impairedRepairService = (ImpairedRepairService) SpringUtil.getObjectFromApplication("impairedRepairServiceImpl");
		 }
		
		if(vs == null)//创建上下文环境
		 {
			vs = (VisitWorkServerInterfaceService) SpringUtil.getObjectFromApplication("visitWorkServerInterfaceServiceImpl");
		 }
	
		    JSONObject pho = (JSONObject) JSON.parse(mtr_id);
		     String mtrid=(String) pho.get("mtr_id");
		
		    List res=reportDetailInterService.getReportDetailList(mtrid);
		    List ls=reportDetailInterService.getReportDeailInfoList(mtrid);
		    List detailLs=new ArrayList();
		    if(ls.size()<1)
		    {
		    	status="1";
				message="查询无记录";
		    }else{
		    for(int i=0,len=ls.size();i<len;i++)
			{
		    	Map<String ,String> mp = new HashMap<String ,String>();
		    	Object[] obj = (Object[])ls.get(i);
				String dispatch_status = null == obj[0]?"":obj[0].toString();
				mp.put("status", dispatch_status);
				String createby = null == obj[1]?"":obj[1].toString();
				String handlers = null == obj[5]?"":obj[5].toString();
				if(createby.equals(""))
				{
					mp.put("handler", ""+handlers);
				}else
				{
				mp.put("handler", createby);
				}
				String handler_dept = null == obj[2]?"":obj[2].toString();
				mp.put("handler_dept", handler_dept);
				String dispatch_sl_time = null == obj[3]?"":obj[3].toString();
				String comp_createTime = "";
				Date comp_createTime1 = null;
				try {
					if (!dispatch_sl_time.equals("")) {
						comp_createTime1 = sd.parse(dispatch_sl_time);
						comp_createTime = sd.format(comp_createTime1);
					}
				} catch (ParseException e) {

					status="0";
					message = "后台异常，请联系技术人员";
					log.error(message+e.getMessage());
					
				}
				
				mp.put("handle_time", comp_createTime);
				String handler_phone = null == obj[4]?"":obj[4].toString();
				mp.put("handler_phone", handler_phone);
				
				String mtn_type = null == obj[6]?"":obj[6].toString();
				mp.put("mtn_type", mtn_type);
				
				String head_img = null == obj[7]?"":obj[7].toString();
				mp.put("head_img", head_img);
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
			WorkMainEntity work=impairedRepairService.getProptyImpairRepDetailByEventId(mtrid);
			String repair_time = null == obj[0]?"":obj[0].toString();
			String rep_time="";
			try {
				Date time=sd.parse(repair_time);
				rep_time=sd.format(time);
			} catch (ParseException e) 
			{
				log.error("时间转换异常："+e.getMessage());
			}
			map.put("repair_time", rep_time);
			
			String repair_content = null == obj[1]?"":obj[1].toString();
			String address = null == obj[2]?"":obj[2].toString();
			map.put("repair_content", repair_content);
			//新加入业主端
			if(work!=null)
			{
				String img_url=null==work.getImg_url()?"[]":work.getImg_url();
				map.put("img_url", img_url);
			}
			//map.put("img_url", img_ls);
			String event_state = null == obj[3]?"":obj[3].toString();
			//map.put("event_state", event_state);
			//if(event_state.equals("2")||event_state.equals("3")||event_state.equals("4")||event_state.equals("5")){
				//待回访时候提供
				List<ChargeInfoEntity> chargList=chargeInfoService.queryChargeByWorkId(mtrid);
				if(chargList.size()>1)
				{
					String state=chargList.get(0).getState();
					String state2=chargList.get(1).getState();
					if(state.equals(state2))
					{
						if(state.equals("03"))
						{
							map.put("payFlag", "2");//1 已支付  2未支付 3 其他
						}else if(state.equals("02"))
						{
							map.put("payFlag", "1");//1 已支付  2未支付 3 其他
						}else
						{
							map.put("payFlag", "3");//1 已支付  2未支付 3 其他
						}
					}else
					{
						map.put("payFlag", "2");//1 已支付  2未支付 3 其他
						
					}
					
				}else if(chargList.size()==1)
				{
					String state=chargList.get(0).getState();
					
					if(state.equals("03"))
					{
						map.put("payFlag", "2");//1 已支付  2未支付 3 其他
					}else if(state.equals("02"))
					{
						map.put("payFlag", "1");//1 已支付  2未支付 3 其他
					}else
					{
						map.put("payFlag", "3");//1 已支付  2未支付 3 其他
					}	
					
				}else
				{
					map.put("payFlag", "3");//1 已支付  2未支付 3 其他
					
				}
	
			/*}else
			{
				map.put("payFlag", "3");//1 已支付  2未支付 3 其他
				
			}*/
				
			WorkMainDispatchEntity wm=vs.getVisitInfo(mtrid);
			String lev=null==wm.getDispatch_visit_lev()?"":wm.getDispatch_visit_lev();
			String evaluate=null==wm.getDispatch_evaluate()?"":wm.getDispatch_evaluate();
			String record=null==wm.getDispatch_visit_record()?"":wm.getDispatch_visit_record();
			map.put("dispatch_visit_lev", lev);//满意度
			map.put("dispatch_evaluate", evaluate);//标签
			map.put("dispatch_visit_record", record);//内容
			String reuslt=null==wm.getDispatch_result()?"":wm.getDispatch_result();
			if(!("").equals(reuslt)&&(reuslt.equals("业主取消服务")||reuslt.equals("拒绝维修")))//取消
			{
				map.put("cancelFlag", "1");//
				
			}else
			{
				map.put("cancelFlag", "3");
			}
			String mtn_type=null==wm.getMtn_type()?"":wm.getMtn_type();//维修类型 ，质保还是其他 1质保期，0公共维修，""其他
			map.put("mtn_type", mtn_type);
			
			map.put("data", detailLs);
			Map hp=new HashMap();
			hp.put("status", status);
			hp.put("message", message);
			hp.put("data", map);
			
			String jsonString = JSON.toJSONString(hp);
			return jsonString;
		    }
		    
		    Map hp=new HashMap();
			hp.put("status", status);
			hp.put("message", message);
			hp.put("data", map);
			String jsonString = JSON.toJSONString(hp);
			return jsonString;
	}
	
	/**
	 * 描述：获取报修历史
	 * @param phone
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getRepairHistory(String phone)
	{
		String status="";
		String message="";

		 SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(reportDetailInterService == null)//创建上下文环境
		 {
			reportDetailInterService = (ReportDetailInterService) SpringUtil.getObjectFromApplication("reportDetailInterServiceImpl");
		 }
		if(chargeInfoService == null)//创建上下文环境
		 {
			chargeInfoService = (ChargeInfoService) SpringUtil.getObjectFromApplication("chargeInfoImpl");
		 }
		JSONObject pho = (JSONObject) JSON.parse(phone);
		String call=(String) pho.get("phone");
		//JSONArray jsonArr = JSON.parseArray(phone);
		
		List res=reportDetailInterService.getRepairHistoryByPhone(call);
		List ls=new ArrayList();
		Map map=new HashMap();
		if(res.size()<1)
		{
			status="1";//已确认
			message="查询无记录";
		}else
		{
			
			for(int i=0,len=res.size();i<len;i++)
			{

		    	Map mp = new HashMap();
		    	Object[] obj = (Object[])res.get(i);
				String rpt_id = null == obj[0]?"":obj[0].toString();
				mp.put("rpt_id", rpt_id);
				String createTime = null == obj[1]?"":obj[1].toString();
				String comp_createTime = "";
				Date comp_createTime1 = null;
				try {
					if (!createTime.equals("")) {
						comp_createTime1 = sd.parse(createTime);
						comp_createTime = sd.format(comp_createTime1);
					}
				} catch (ParseException e) {

					status="0";
					message = "后台异常，请联系技术人员";
					log.error(message+e.getMessage());
				}
				mp.put("repair_createTime", comp_createTime);
				String mtn_detail = null == obj[2]?"":obj[2].toString();
				mp.put("repair_content", mtn_detail);
				
				String dispatch_status = null == obj[3]?"":obj[3].toString();
				mp.put("comp_status", dispatch_status);
				String code_detail_name = null == obj[4]?"":obj[4].toString();
				mp.put("comp_statusName", code_detail_name);
				
				String dispatch_result = null == obj[5]?"":obj[5].toString();
				if(!("").equals(dispatch_result)&&(dispatch_result.equals("业主取消服务")||dispatch_result.equals("拒绝维修")))//取消
				{
					mp.put("cancelFlag", "1");//
					
				}else
				{
					mp.put("cancelFlag", "3");
				}
				String state = null == obj[6]?"":obj[6].toString();
				if(state.equals("03"))
				{
					mp.put("payFlag", "2");//1 已支付  2未支付 3 其他
				}else if(state.equals("02"))
				{
					mp.put("payFlag", "1");//1 已支付  2未支付 3 其他
				}else
				{
					mp.put("payFlag", "3");//1 已支付  2未支付 3 其他
				}
			//	if(dispatch_status.equals("3")||dispatch_status.equals("4")||dispatch_status.equals("5")){
					//待回访时候提供
				 /* 
					List<ChargeInfoEntity> chargList=chargeInfoService.queryChargeByWorkId(rpt_id);
					
					if(chargList.size()>1)
					{
						String state=chargList.get(0).getState();
						String state2=chargList.get(1).getState();
						if(state.equals(state2))
						{
							if(.equals("03"))
							{
								mp.put("payFlag", "2");//1 已支付  2未支付 3 其他
							}else if(state.equals("02"))
							{
								mp.put("payFlag", "1");//1 已支付  2未支付 3 其他
							}else
							{
								mp.put("payFlag", "3");//1 已支付  2未支付 3 其他
							}
						}else
						{
							mp.put("payFlag", "2");//1 已支付  2未支付 3 其他
							
						}
						
					}else if(chargList.size()==1)
					{
						String state=chargList.get(0).getState();
						if(state.equals("03"))
						{
							mp.put("payFlag", "2");//1 已支付  2未支付 3 其他
						}else if(state.equals("02"))
						{
							mp.put("payFlag", "1");//1 已支付  2未支付 3 其他
						}else
						{
							mp.put("payFlag", "3");//1 已支付  2未支付 3 其他
						}	
						
					}else
					{
						mp.put("payFlag", "3");//1 已支付  2未支付 3 其他
						
					}*/
					
					/*}else
				{
					mp.put("payFlag", "3");//1 已支付  2未支付 3 其他
					
				}*/
				ls.add(mp);
				status="1";
				message="查询成功";
			}
			map.put("status", status);
			map.put("message", message);
			map.put("data", ls);
			
			String jsonString = JSON.toJSONString(map);
			
			return jsonString;
		}
		map.put("status", status);
		map.put("message", message);
		map.put("data", ls);
		String jsonString = JSON.toJSONString(map);
		return jsonString;
		//return""; 20170115
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
	
	
	/**
	 * 报障报修业主取消
	 * @param data
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String gusetRepairCancel(String data)
	{
		
		if(assignWorkService == null)//创建上下文环境
		 {
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		 }
		if(grapService == null)//创建上下文环境
		 {
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		 }
		
		String status="";
		String message="";
		JSONObject pho = (JSONObject) JSON.parse(data);
		String mtn_id=(String) pho.get("mtn_id");
		String type=(String) pho.get("type");
		String cust_id=(String)pho.get("cust_no");
		
		WorkMainEntity mainEntity = assignWorkService.getWorkMainById(mtn_id);
		String processinstanceid = mainEntity.getProcessInstanceId()+"";
		Map<String,Object> variables = null;
		Map delmap=new HashMap();
		String state = "";
		EventSendEntity send=new EventSendEntity();
		if(mainEntity == null){
			mainEntity = new WorkMainEntity();
		}
		WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(mtn_id);
		
		if(mainEntity.getEvent_state().equals("0"))
		{
			delmap.put("flag", "2");//取消标识
			/*if(type.equals("")||type!="1")
			{
				type="1";
			}*/
			type="1";
			if(null != processinstanceid && processinstanceid.length()>0){
				grapService.completeBussniseTask(processinstanceid,cust_id, delmap,type);
			}
			 status="1";
			 message="操作成功";
		}else if(mainEntity.getEvent_state().equals("1"))
		{
			type="2";
			/*if(type.equals("")||type!="2")
			{
				type="2";
			}*/
			delmap.put("flag", "3");//现场确认之前
			if(null != processinstanceid && processinstanceid.length()>0){
				grapService.completeBussniseTask(processinstanceid, cust_id, delmap,type);
			}
			 status="1";
			 message="操作成功";
		}
		
		if(null != processinstanceid && processinstanceid.length()>0)
		{
			grapService.completeBussniseTask(processinstanceid,  cust_id, null,"1");
			status="1";
			message="操作成功";
		}
		
		
		mainEntity.setEvent_state(EVENTSTATE);
		mainEntity.setFinishTime(new Date());
		mainEntity.setOrder_state("取消");
		dispatchEntity.setDispatch_status(EVENTSTATE);
		dispatchEntity.setDispatch_finish_time(new Date());
//		dispatchEntity.setDispatch_handle_id(cust_id);
//		dispatchEntity.setDispatch_handle_name("");
		dispatchEntity.setDispatch_result("业主取消服务");
		assignWorkService.addWorkMain(mainEntity);
		//派工纪录
		send.setStatus(EVENTSTATE);
		send.setHandle_time(dispatchEntity.getDispatch_time());
	
		send.setEvent_id(dispatchEntity.getMtn_id());
		String deptName=assignWorkService.getdept(cust_id);
		PropertyOwner own=assignWorkService.getCustName(cust_id);
		String dept_name=null==deptName?"":deptName;
		if(dept_name.equals(""))
		{
			send.setHandler_dept("业主");	
		}else{
		send.setHandler_dept(dept_name);
		}
		
		//String phone=assignWorkService.getHandlePhone(cust_id);
		if(own!=null)
		{
			send.setOwnHandler(own.getOwnerName());
			send.setHandler_phone(own.getPhone());
		}
		
		send.setStatus(dispatchEntity.getDispatch_status());
		try{
		assignWorkService.addWorkDispatch(dispatchEntity);
		assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(),dispatchEntity.getDispatch_status());//更新主表状态
		assignWorkService.addEventSend(send);
		}catch (Exception e) {
			status="0";
			message="操作失败，联系技术人员";
			log.error(message+e.getMessage());
		}
		variables=new HashMap<String, Object>();
		variables.put("status", status);
		variables.put("message", message);
		variables.put("mtn_id", "工单："+mainEntity.getRpt_id()+" 已取消");
		String jsonString = JSON.toJSONString(variables);
		return jsonString;	
	}
	
	/**
	 * 描述：业主评价
	 * @param JsonEval
	 * @return
	 */
	public String ownerEvaluate(String JsonEval)
	{
		
		
		if(assignWorkService == null)//创建上下文环境
		 {
			assignWorkService = (AssignWorkService) SpringUtil.getObjectFromApplication("assignWorkServiceImpl");
		 }
		if(grapService == null)//创建上下文环境
		 {
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		 }
		
		String status="0";
		String message="失败";
		String str="";
		try {
		 str = URLDecoder.decode(JsonEval, "UTF-8");
		} catch (UnsupportedEncodingException e) {
	       log.error("转码失败："+e.getMessage());
		}
		JSONObject pho = (JSONObject) JSON.parse(str);
		String mtn_id=(String) pho.get("mtn_id");
		String dispatch_visit_record=(String) pho.get("dispatch_visit_record");
		String dispatch_visit_lev=(String)pho.get("dispatch_visit_lev");
		String dispatch_evaluate=(String)pho.get("dispatch_evaluate");
		String handle_name=(String)pho.get("handle_name");
		String event_type= null == (String)pho.get("event_type")?"0":(String)pho.get("event_type");
		
		
		/**
		 * 驱动流程图
		 */
		WorkMainEntity mainEntity = assignWorkService.getWorkMainById(mtn_id);
		Map<String,Object> variables = null;
		String state = "";
		
		if(mainEntity == null){
			mainEntity = new WorkMainEntity();
		}
		
		if(null != event_type && event_type.equals("0")){//报障报修
			WorkMainDispatchEntity dispatchEntity = assignWorkService.getWorkDispatchById(mtn_id);
			if(null == dispatchEntity){
				dispatchEntity = new WorkMainDispatchEntity();
			}
            if(dispatch_visit_lev.equals("非常满意"))
            {
            	dispatchEntity.setDispatch_visit_lev("0");
            }else if(dispatch_visit_lev.equals("满意"))
            {
            	dispatchEntity.setDispatch_visit_lev("1");
            }else if(dispatch_visit_lev.equals("不满意"))
            {
            	dispatchEntity.setDispatch_visit_lev("2");
            }
			
			dispatchEntity.setDispatch_evaluate(dispatch_evaluate);
			dispatchEntity.setDispatch_visit_record(dispatch_visit_record);
		    dispatchEntity.setDispatch_status("4");
			dispatchEntity.setDispatch_finish_time(new Date());
			assignWorkService.addWorkDispatch(dispatchEntity);
			
			state = dispatchEntity.getDispatch_status();
			EventSendEntity send=new EventSendEntity();
			send.setHandle_time(dispatchEntity.getDispatch_finish_time());
			send.setOwnHandler(handle_name);
			send.setEvent_id(dispatchEntity.getMtn_id());
			//String deptName=assignWorkService.getdept(handle_name);
			send.setHandler_dept("");	
			String phone=assignWorkService.getHandlePhone(handle_name);
			send.setHandler_phone(phone);
			send.setStatus(state);
			assignWorkService.addEventSend(send);
			
			
			//第二次模拟归档
			dispatchEntity.setDispatch_status("5");
			assignWorkService.addWorkDispatch(dispatchEntity);
			
			send.setStatus(state);
			assignWorkService.addEventSend(send);
			
			
			EventSendEntity send1=new EventSendEntity();
			send1.setHandle_time(new Date());
			send1.setOwnHandler(handle_name);
			send1.setEvent_id(dispatchEntity.getMtn_id());
			//String deptName=assignWorkService.getdept(handle_name);
			send1.setHandler_dept("");	
			String phone1=assignWorkService.getHandlePhone(handle_name);
			send1.setHandler_phone(phone1);
			send1.setStatus("5");
			assignWorkService.addEventSend(send1);
			
			assignWorkService.updateWorkMainState(dispatchEntity.getMtn_id(),dispatchEntity.getDispatch_status());//更新主表状态
			status="1";
			message="成功";
			
			
			
			
		}else if(null != event_type && event_type.equals("1")){//咨询建议
			WorkComplaintEntity complaintEntity = assignWorkService.getWorkComplById(mtn_id);
			if(null == complaintEntity){
				complaintEntity = new WorkComplaintEntity();
			}
			complaintEntity.setComp_visit_lev(dispatch_visit_lev);
			complaintEntity.setComp_visit_record(dispatch_visit_record);
		
			complaintEntity.setComp_status("4");
			complaintEntity.setComp_finish_time(new Date());
			state = complaintEntity.getComp_status();
			assignWorkService.addWorkCompl(complaintEntity);
			
			
			status="1";
			message="成功";
		}
		
		
		
		String processinstanceid = mainEntity.getProcessInstanceId()+"";
		if(null != processinstanceid && processinstanceid.length()>0){
			//默认为系统
			grapService.completeBussniseTask(processinstanceid, "admin", variables,state);
		}
		
		if(null != processinstanceid && processinstanceid.length()>0){
			//默认为系统
			grapService.completeBussniseTask(processinstanceid, "admin", variables,state);
		}
		variables=new HashMap<String, Object>();
		variables.put("status", status);
		variables.put("message", message);
		variables.put("mtn_id", "工单："+mainEntity.getRpt_id()+" 评价成功");
		String jsonString = JSON.toJSONString(variables);
		return jsonString;
	}
}

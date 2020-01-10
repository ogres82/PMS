package com.jdry.pms.assignWork.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.assignWork.dao.ImpairedRepairDao;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.service.ImpairedRepairService;
import com.jdry.pms.comm.util.SpringUtil;
import com.soft.service.GrapService;

@Repository
@Component
public class ImpairedRepairServiceImpl implements ImpairedRepairService {

	@Resource
	ImpairedRepairDao impairedRepairDao;
	@Resource
	GrapService grapService;
	private static Object lock=new Object();
	@Override
	public List getProptyImpairRepHistory(String handleId) 
	{
		return impairedRepairDao.getProptyImpairRepHistory(handleId);
	}

	
	@Override
	public WorkMainEntity getProptyImpairRepDetailByEventId(String rpt_id)
	{
		
		return impairedRepairDao.getProptyImpairRepDetailByEventId(rpt_id);
	}


	@Override
	public List getDispatchStepByEventId(String rpt_id) {
		return impairedRepairDao.getDispatchStepByEventId(rpt_id);
	}


	@Override
	public String dipatchByClient(Map<Object, Object> parameter){
		// TODO Auto-generated method stub
		if(grapService == null){
			grapService = (GrapService) SpringUtil.getObjectFromApplication("grapServiceImpl");
		}
		WorkMainEntity assignWork = (WorkMainEntity) parameter.get("assignWork");
		synchronized(lock) {
			if(!this.varifyDispatch(assignWork.getRpt_id())){
				return "{\"status\":\"0\",\"message\":\"工单已被抢！请下次手再快一些。\"}";
			}
			String dispatch_handle_username = parameter.get("dispatch_handle_username").toString();
			String jsonString = "";
			try {
				String processKey = "assignNewProcess";
				String processId="";
				if(assignWork.getProcessInstanceId()==null || ("").equals(assignWork.getProcessInstanceId())){
					processId = grapService.startBussniseTask(processKey);
					if(null == processId || processId.length()<1){
						processId = "0";
					}
					grapService.completeBussniseTask(processId, null, null);
					assignWork.setProcessInstanceId(Long.parseLong(processId));
				}else{
					processId=assignWork.getProcessInstanceId()+"";
				}
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("flag", "1");
				if(null != processId && processId.length()>0){
					grapService.completeBussniseTask(processId, dispatch_handle_username, map,"1");
				}
				impairedRepairDao.dipatchByClient(parameter);
				jsonString = "{\"status\":\"1\",\"message\":\"抢单成功！\"}";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				jsonString = "{\"status\":\"0\",\"message\":\"抢单失败！请稍后再试。\"}";
				e.printStackTrace();
			}
			return jsonString;
		}
	}
	//判断工单是否已派工
	private boolean varifyDispatch(String rptId){
		return impairedRepairDao.varifyDispatch(rptId);
	}


	@Override
	public List<?> queryAllNoDispatch() {
		// TODO Auto-generated method stub
		return impairedRepairDao.queryAllNoDispatch();
	}
}

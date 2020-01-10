package com.jdry.pms.assignWork.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.service.VisitWorkServerInterfaceService;
import com.jdry.pms.comm.util.SpringUtil;

/**
 *对外评价接口——APP
 * @author hezuping
 *
 */
@Repository
@Component
public class VisitWorkServerInterface 
{
	@Resource
	VisitWorkServerInterfaceService vs;
	
	/**
	 * 评价详情
	 * 报文待确定
	 * @param Json
	 * @return
	 */
	public WorkMainDispatchEntity getVisitInfo(String Json)
	{
		JSONObject pho = (JSONObject) JSON.parse(Json);
		String mtrid=(String) pho.get("mtr_id");
		 if(vs == null)
		 {
			 vs = (VisitWorkServerInterfaceService) SpringUtil.getObjectFromApplication("visitWorkServerInterfaceServiceImpl");
		 }	
		 WorkMainDispatchEntity wm=vs.getVisitInfo(mtrid);
		 return wm;
	}
	
	

}

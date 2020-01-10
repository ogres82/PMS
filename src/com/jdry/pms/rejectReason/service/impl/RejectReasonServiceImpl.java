package com.jdry.pms.rejectReason.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.rejectReason.dao.RejectReasonDao;
import com.jdry.pms.rejectReason.pojo.RejectReasonEntity;

@Repository
@Component
public class RejectReasonServiceImpl {
	@Resource
	private RejectReasonDao dao;
	
	
	public String getRejectReason(String data){
		JSONObject obj = JSON.parseObject(data);
		String rejectType = null == obj.getString("rejectType")?"" : obj.getString("rejectType");
		
		if(dao == null){
			dao = (RejectReasonDao) SpringUtil.getObjectFromApplication("rejectReasonDao");
		}
		
		List<RejectReasonEntity> list = dao.getRejectReason(rejectType, 5);
		
		if (list != null && list.size() > 0) {			
			String jsonString = JSON.toJSONString(list);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":"+jsonString+"}";
		}else {

			return "{\"status\":\"0\",\"message\":\"查无数据！\",\"data\":\"\"}";
		}
	}
	
}

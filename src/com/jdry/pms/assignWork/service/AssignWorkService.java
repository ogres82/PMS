package com.jdry.pms.assignWork.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assignWork.pojo.DispatchImageEntity;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.VWorkCompEntity;
import com.jdry.pms.assignWork.pojo.VWorkDispatchEntity;
import com.jdry.pms.assignWork.pojo.VWorkMainEntity;
import com.jdry.pms.assignWork.pojo.VWorkVisitEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;

@Component
public interface AssignWorkService {

	public void getWorkMain(Page<VWorkMainEntity> page,Map<String, Object> parameter,Criteria criteria) throws Exception;
	
	public WorkMainEntity getWorkMainById(String id);
	
	public void addWorkMain(WorkMainEntity mainEntity);
	
	public void deleteWorkMain(String id);
	
	public void addWorkDispatch(WorkMainDispatchEntity dispathcEntity);
	
	public WorkMainDispatchEntity getWorkDispatchById(String id);
	
	public void deleteWorkDispatch(String id);
	
	public void getWorkDispatch(Page<VWorkDispatchEntity> page,Map<String, Object> parameter,Criteria criteria) throws Exception;
	
	public List getDispatchWorkById(String id);
	
	public WorkComplaintEntity getWorkComplById(String id);
	public void addWorkCompl(WorkComplaintEntity complaintEntity);
	
	public void deleteWorkComp(String id);
	
	public void getWorkComp(Page<VWorkCompEntity> page,Map<String, Object> parameter,Criteria criteria) throws Exception;
	public List getCompWorkById(String mtn_id);
	
	
	public void getWorkVisit(Page<VWorkVisitEntity> page,Map<String, Object> parameter,Criteria criteria) throws Exception;
	
	public List getHandleWorkers(String keyword,String type);
	
	public List getVisitWorkById(String id);
	
	public void addWorkWorkState(String userName,Integer state);
	
	public String getWorkMainState(String mtn_id);
	
	public void addEventSend(EventSendEntity send);

	public String getdept(String createby);

	public String getHandlePhone(String dispatch_handle_id);

	public void updateWorkMainState(String mtn_id,String state);

	public void saveImg(DispatchImageEntity img);

	public List<DispatchImageEntity> getEventImgByRptId(String rpt_id);

	public void updateWorkMainHanders(String rpt_id, String handerNames) throws SQLException;

	public PropertyOwner getCustName(String cust_id);
	
	public void updateDispatchWorkMainHanders(String rpt_id, String handerName,String handlerId) throws SQLException;
	
}

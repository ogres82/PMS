package com.jdry.pms.assignWork.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.assignWork.dao.AssignWorkComplDao;
import com.jdry.pms.assignWork.dao.AssignWorkDao;
import com.jdry.pms.assignWork.dao.AssignWorkDispatchDao;
import com.jdry.pms.assignWork.dao.AssignWorkVisitDao;
import com.jdry.pms.assignWork.pojo.DispatchImageEntity;
import com.jdry.pms.assignWork.pojo.EventSendEntity;
import com.jdry.pms.assignWork.pojo.VWorkCompEntity;
import com.jdry.pms.assignWork.pojo.VWorkDispatchEntity;
import com.jdry.pms.assignWork.pojo.VWorkMainEntity;
import com.jdry.pms.assignWork.pojo.VWorkVisitEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
import com.jdry.pms.assignWork.pojo.WorkMainWorkerStateEntity;
import com.jdry.pms.assignWork.service.AssignWorkService;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.comm.service.impl.ExpireEventService;

@Component
public class AssignWorkServiceImpl implements AssignWorkService{

	@Resource
	AssignWorkDao assignWorkDao;
	
	@Resource
	AssignWorkDispatchDao assignWorkDispatchDao;
	
	@Resource
	AssignWorkComplDao complDao;
	
	@Resource
	AssignWorkVisitDao workVisitDao;
	
	@Override
	public void getWorkMain(Page<VWorkMainEntity> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
		// TODO Auto-generated method stub
		assignWorkDao.getAll(page,parameter,criteria);
	}

	@Override
	public WorkMainEntity getWorkMainById(String id) {
		// TODO Auto-generated method stub
		return assignWorkDao.getWorkMainById(id);
	}

	@Override
	public void addWorkMain(WorkMainEntity mainEntity) {
		// TODO Auto-generated method stub
		assignWorkDao.addWorkMain(mainEntity);
	}

	@Override
	public void addWorkDispatch(WorkMainDispatchEntity dispathcEntity) {
		assignWorkDispatchDao.addWorkDispatch(dispathcEntity);
		ExpireEventService.dispatchToRedis(dispathcEntity);
	}
	
	@Override
	public WorkMainDispatchEntity getWorkDispatchById(String bxid) {
		return assignWorkDispatchDao.getWorkDispatchById(bxid);
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteWorkMain(String id) {
		// TODO Auto-generated method stub
		assignWorkDao.deleteWorkMain(id);
	}

	
	/**
	 * 删除
	 */
	@Override
	public void deleteWorkDispatch(String id) {
		// TODO Auto-generated method stub
		assignWorkDispatchDao.deleteWorkDispatch(id);
	}

	@Override
	public void getWorkDispatch(Page<VWorkDispatchEntity> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		// TODO Auto-generated method stub
		assignWorkDispatchDao.getAllDispatchWork(page,parameter,criteria);
	}

	@Override
	public List getDispatchWorkById(String mtn_id) {
		// TODO Auto-generated method stub
		return assignWorkDispatchDao.getDispatchWorkById(mtn_id);
	}

	@Override
	public WorkComplaintEntity getWorkComplById(String id) {
		// TODO Auto-generated method stub
		return complDao.getWorkComplById(id);
	}

	@Override
	public void addWorkCompl(WorkComplaintEntity complaintEntity) {
		// TODO Auto-generated method stub
		complDao.addWorkCompl(complaintEntity);
	}

	@Override
	public void deleteWorkComp(String id) {
		// TODO Auto-generated method stub
		complDao.deleteWorkComp(id);
	}

	@Override
	public void getWorkComp(Page<VWorkCompEntity> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		// TODO Auto-generated method stub
		complDao.getWorkComp(page, parameter, criteria);
	}
	
	@Override
	public List getCompWorkById(String mtn_id) {
		// TODO Auto-generated method stub
		return complDao.getCompWorkById(mtn_id);
	}

	@Override
	public void getWorkVisit(Page<VWorkVisitEntity> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		// TODO Auto-generated method stub
		workVisitDao.getWorkVisit(page, parameter, criteria);
	}

	@Override
	public List getHandleWorkers(String keyword,String type) {
		// TODO Auto-generated method stub
		return assignWorkDao.getHandleWorkers(keyword,type);
	}
	
	
	@Override
	public List getVisitWorkById(String rpt_id) {
		// TODO Auto-generated method stub
		return workVisitDao.getVisitWorkById(rpt_id);
	}
	
	/**
	 * state  0--闲置              1--分派任务
	 */
	@Override
	public void addWorkWorkState(String userName,Integer state) {
		// TODO Auto-generated method stub
		WorkMainWorkerStateEntity workMainWorkerStateEntity = assignWorkDao.geWorkWorkStateByUser(userName);
		if(null == workMainWorkerStateEntity){
			workMainWorkerStateEntity = new WorkMainWorkerStateEntity();
			workMainWorkerStateEntity.setUser_name(userName);
			workMainWorkerStateEntity.setWork_times(0);
			workMainWorkerStateEntity.setWork_state(0);
		}
		Integer times = null == workMainWorkerStateEntity.getWork_times()?0:workMainWorkerStateEntity.getWork_times();
		if(state==0){
			
		}else if(state==1){
			workMainWorkerStateEntity.setWork_times(times+1);
		}
		workMainWorkerStateEntity.setWork_state(state);
		assignWorkDao.addWorkWorkState(workMainWorkerStateEntity);
	}

	@Override
	public String getWorkMainState(String mtn_id) {
		// TODO Auto-generated method stub
		return assignWorkDao.getWorkMainState(mtn_id);
	}

	@Override
	public void addEventSend(EventSendEntity send) {
		
		assignWorkDao.addEventSend(send);
	}

	@Override
	public String getdept(String createby) {
		
		return assignWorkDao.getDept(createby);
	}

	@Override
	public String getHandlePhone(String dispatch_handle_id) {
		
		return assignWorkDao.getHandlePhone(dispatch_handle_id);
	}

	@Override
	public void updateWorkMainState(String mtn_id, String state) {
		
		try {
			assignWorkDao.updateWorkMainState(mtn_id,state);
		} catch (SQLException e)
		{
			
		}
	}

	@Override
	public void saveImg(DispatchImageEntity img) {
		assignWorkDao.saveImg(img);
		
	}

	@Override
	public List<DispatchImageEntity> getEventImgByRptId(String rpt_id) {
		if(rpt_id.equals(""))
		{
		 return null;	
		}
		return assignWorkDao.getEventImgByRptId(rpt_id);
	}

	@Override
	public void updateWorkMainHanders(String rpt_id, String handerNames) throws SQLException{
		assignWorkDao.updateWorkMainHanders(rpt_id,handerNames);
	}

	@Override
	public PropertyOwner getCustName(String cust_id) {
		return assignWorkDao.getCustName(cust_id);
	}

	@Override
	public void updateDispatchWorkMainHanders(String rpt_id, String handerName, String handlerId) throws SQLException{
		// TODO Auto-generated method stub
		assignWorkDao.updateDispatchWorkMainHanders(rpt_id, handerName, handlerId);
	}
	
	
	
}

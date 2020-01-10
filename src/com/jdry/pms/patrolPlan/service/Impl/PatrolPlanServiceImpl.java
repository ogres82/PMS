package com.jdry.pms.patrolPlan.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.jdry.pms.patrolPlan.dao.PatrolPlanDao;
import com.jdry.pms.patrolPlan.dao.PatrolPlanNodeDao;
import com.jdry.pms.patrolPlan.pojo.PatrolPlanEntity;
import com.jdry.pms.patrolPlan.pojo.PatrolPlanNodeEntity;
import com.jdry.pms.patrolPlan.service.PatrolPlanService;

@Repository
@Component

public class PatrolPlanServiceImpl implements PatrolPlanService {
	@Resource
	PatrolPlanNodeDao ppnDao;
	
	@Resource
	PatrolPlanDao ppDao;

	@Override
	public List<PatrolPlanEntity> queryPatrolPlan() {
		// TODO Auto-generated method stub
		return ppDao.queryPatrolPlan();
	}

	@Override
	public void savePatrolPlan(PatrolPlanEntity ppe) {
		// TODO Auto-generated method stub
		ppDao.savePatrolPlan(ppe);
	}

	@Override
	public void delPatrolPlan(String patrolPlanIds) {
		// TODO Auto-generated method stub
		ppDao.delPatrolPlan(patrolPlanIds);
	}

	@Override
	public void savePatrolPlanNode(String data) {
		// TODO Auto-generated method stub

		List<PatrolPlanNodeEntity> list = JSON.parseArray(data, PatrolPlanNodeEntity.class);
		int len = list.size();
		for(int i=0;i<len;i++){
			ppnDao.savePatrolPlanNode(list.get(i));
		}
	}

	@Override
	public void delPatrolPlanNode(String id) {
		// TODO Auto-generated method stub
		ppnDao.delPlanNodeById(id);
	}

	@Override
	public List<PatrolPlanNodeEntity> queryPatrolPlanNode(long patrolPlanId) {
		// TODO Auto-generated method stub
		return ppnDao.queryPatrolPlanNode(patrolPlanId);
	}
}

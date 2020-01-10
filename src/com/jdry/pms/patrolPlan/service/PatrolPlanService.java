package com.jdry.pms.patrolPlan.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jdry.pms.patrolPlan.pojo.PatrolPlanEntity;
import com.jdry.pms.patrolPlan.pojo.PatrolPlanNodeEntity;

@Repository
public interface PatrolPlanService {
	public List<PatrolPlanEntity> queryPatrolPlan();

	public void savePatrolPlan(PatrolPlanEntity ppe);

	public void delPatrolPlan(String patrolPlanIds);

	public void savePatrolPlanNode(String data);
	
	public void delPatrolPlanNode(String id);
	
	public List<PatrolPlanNodeEntity> queryPatrolPlanNode(long patrolPlanId);
}

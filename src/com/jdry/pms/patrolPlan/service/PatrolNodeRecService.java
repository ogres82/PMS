package com.jdry.pms.patrolPlan.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.patrolPlan.pojo.PatrolNodeRecEntity;
import com.jdry.pms.patrolPlan.pojo.PlanNodeRecEntity;

@Repository
public interface PatrolNodeRecService {

	public List<PatrolNodeRecEntity> queryPatrolNodeRec(String patrolUserId);

	public void queryNodeRec(Page<PlanNodeRecEntity> page, Map<String, Object> parameter, Criteria criteria);

}

package com.jdry.pms.patrolPlan.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jdry.pms.patrolPlan.pojo.PatrolNodeEntity;

@Repository
public interface PatrolNodeService {

	public List<PatrolNodeEntity> queryPatrolNode(int rows, Map<String, Object> parameter);

	public void savePatrolNode(PatrolNodeEntity pne);

	public void delPatrolNode(String patrolNodeIds);
}

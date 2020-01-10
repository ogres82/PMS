package com.jdry.pms.patrolPlan.service.Impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.patrolPlan.dao.PatrolNodeDao;
import com.jdry.pms.patrolPlan.pojo.PatrolNodeEntity;
import com.jdry.pms.patrolPlan.service.PatrolNodeService;

@Repository
@Component
public class PatrolNodeServiceImpl implements PatrolNodeService{
	@Resource
	PatrolNodeDao dao;
	
	@Override
	public List<PatrolNodeEntity> queryPatrolNode(int rows, Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return dao.queryPatrolNode(rows, parameter);
	}

	@Override
	public void savePatrolNode(PatrolNodeEntity pne) {
		// TODO Auto-generated method stub
		dao.savePatrolNode(pne);
	}

	@Override
	public void delPatrolNode(String patrolNodeIds) {
		// TODO Auto-generated method stub
		dao.delPatrolNode(patrolNodeIds);
	}

}

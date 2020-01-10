package com.jdry.pms.visitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.visitor.dao.VisitManageDao;
import com.jdry.pms.visitor.pojo.VisitorRec;
import com.jdry.pms.visitor.service.VisitManageService;
@Repository
@Component
public class VisitManageServiceImpl implements VisitManageService{
	@Resource
	VisitManageDao dao;
	@Override
	public void addVisitRec(VisitorRec rec) {
		// TODO Auto-generated method stub
		dao.addVisitRec(rec);
	}
	@Override
	public List<VisitorRec> loadVisitRec(String managerId) {
		// TODO Auto-generated method stub
		return dao.loadVisitRec(managerId);
	}
	@Override
	public List<VisitorRec> loadAllVisitRec() {
		// TODO Auto-generated method stub
		return dao.loadAllVisitRec();
	}
	@Override
	public List<VisitorRec> loadVisitRecByOwner(String ownerId) {
		// TODO Auto-generated method stub
		return dao.loadVisitRecByOwner(ownerId);
	}

}

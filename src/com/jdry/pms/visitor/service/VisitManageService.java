package com.jdry.pms.visitor.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jdry.pms.visitor.pojo.VisitorRec;
@Repository
public interface VisitManageService {

	public void addVisitRec(VisitorRec rec);
	public List<VisitorRec> loadVisitRec(String managerId);
	public List<VisitorRec> loadAllVisitRec();
	public List<VisitorRec> loadVisitRecByOwner(String ownerId);
}

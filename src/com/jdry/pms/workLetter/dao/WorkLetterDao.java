package com.jdry.pms.workLetter.dao;

import org.springframework.stereotype.Repository;

import com.jdry.pms.comm.dao.impl.BaseDaoImpl;
import com.jdry.pms.workLetter.pojo.WorkLetter;

@Repository
public class WorkLetterDao extends BaseDaoImpl<WorkLetter> {
	public WorkLetterDao(){
		super();
	}	
}

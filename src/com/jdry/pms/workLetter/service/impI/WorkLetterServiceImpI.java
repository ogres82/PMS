package com.jdry.pms.workLetter.service.impI;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jdry.pms.comm.dao.impl.BaseDaoImpl;
import com.jdry.pms.workLetter.dao.WorkLetterDao;
import com.jdry.pms.workLetter.pojo.WorkLetter;
import com.jdry.pms.workLetter.service.WorkLetterService;

@Service
public class WorkLetterServiceImpI extends BaseDaoImpl<WorkLetter> implements WorkLetterService{
	@Resource
	WorkLetterDao dao;
}


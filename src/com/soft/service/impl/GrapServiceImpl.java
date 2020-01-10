package com.soft.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.soft.dao.ConsoleDao;
import com.soft.service.GrapService;

@Component
public class GrapServiceImpl implements GrapService {

	@Resource
	ConsoleDao consoleDao;

	@Override
	public String startBussniseTask(String processkey) {
		// TODO Auto-generated method stub
		return consoleDao.startBussniseTask(processkey);
	}

	@Override
	public void completeBussniseTask(String processinstanceid, String assignee,Map<String, Object> variables) {
		// TODO Auto-generated method stub
		consoleDao.completeBussniseTask(processinstanceid, assignee, variables);
	}

	@Override
	public void completeBussniseTask(String processinstanceid, String assignee,
			Map<String, Object> variables, String state) {
		// TODO Auto-generated method stub
		consoleDao.completeBussniseTask(processinstanceid, assignee, variables,state);
	}

	@Override
	public void completeHouseWorkBussniseTask(String processinstanceid,
			String assignee, Map<String, Object> variables, String state) {
		consoleDao.completeHouseWorkBussniseTask(processinstanceid,assignee,variables,state);
		
	}
	
}

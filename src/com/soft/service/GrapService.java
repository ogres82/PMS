package com.soft.service;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface GrapService {
	public String startBussniseTask(String processkey);
	public void completeBussniseTask(String processinstanceid,String assignee,Map<String,Object> variables);
	public void completeBussniseTask(String processinstanceid,String assignee,Map<String,Object> variables,String state);
	
	public void completeHouseWorkBussniseTask(String processinstanceid,String assignee,Map<String,Object> variables,String state);
}

package com.soft;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.Expose;
import com.soft.control.ConsoleController;
import com.soft.dao.ConsoleDao;

@Component
public class ProcessInstancesView {

	@Resource
	ConsoleDao consoleDao;
	
	@Resource
	ConsoleController consoleController;

	@Expose
	public void deployment() throws ParseException {// Map<String,Object>
													// parameter
		consoleDao.deployment();// service.saveWorksheet(parameter);
	}

	/**
	 * 显示流程定义列表.
	 */
	@DataProvider
	public List<ProcessDefinition> listProcessDefinitions() {
		
		return consoleDao.listProcessDefinitions();
	}

	@Expose
	public void startTask() {
		consoleDao.startTask();
	}
}

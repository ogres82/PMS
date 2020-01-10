package com.soft.control;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.interceptor.Command;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.controller.IController;
import com.soft.cmd.HistoryProcessInstanceDiagramCmd;
import com.soft.cmd.ProcessDefinitionDiagramCmd;
import com.soft.dao.ConsoleDao;

@Component
public class ConsoleController implements IController{
	@Resource
	ProcessEngine processEngine;
	
	@Resource
	ConsoleDao consoleDao;
	
    /**
     * 显示流程定义图形.
     */
    public void graphProcessDefinition(String processDefinitionId,HttpServletResponse response) throws Exception {
        Command<InputStream> cmd = new ProcessDefinitionDiagramCmd(processDefinitionId);

        InputStream is = processEngine.getManagementService().executeCommand(cmd);
        response.setContentType("image/png");

        IOUtils.copy(is, response.getOutputStream());
    }

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("come in ...");
		String opt=null == arg0.getParameter("opt")?"":arg0.getParameter("opt").toString();//新增，只是查看流程图
		String processDefinitionId=null == arg0.getParameter("processDefinitionId")?"":arg0.getParameter("processDefinitionId").toString();//业务绑定流程图
		try {
			//graphProcessDefinition(processDefinitionId,arg1);
			if(opt.equals("add")){
				processDefinitionId = consoleDao.processDefinitionById(processDefinitionId);
				if(null !=processDefinitionId && processDefinitionId.length()>0){
					graphProcessDefinition(processDefinitionId,arg1);
				}
				
			}else{
				graphHistoryProcessInstance(processDefinitionId,arg1);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/graph/graphProcessDefinition";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
    /**
     * 流程跟踪
     * 
     * @throws Exception
     */
    public void graphHistoryProcessInstance(String processInstanceId,HttpServletResponse response) throws Exception {
    	
        Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);

        InputStream is = processEngine.getManagementService().executeCommand(cmd);
        response.setContentType("image/png");
        IOUtils.copy(is, response.getOutputStream());
    }
}

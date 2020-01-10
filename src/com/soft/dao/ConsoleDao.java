package com.soft.dao;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;

@Repository
public class ConsoleDao extends HibernateDao {

	@Resource
	private ProcessEngine processEngine;
	
	@Resource
	RuntimeService runtimeService;
	
	@Resource
	TaskService taskService;

	public void deployment() throws ParseException {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("bpmn2/assignNewProcess.bpmn20.xml").deploy();
	}
	
	/**
	 * 显示流程定义列表.
	 */
	public List<ProcessDefinition> listProcessDefinitions() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
		return processDefinitions;
	}
	
	
	/**开始任务
	 * 
	 */ 
	public void startTask(){
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("assignNewProcess");
		System.out.println(instance.getProcessInstanceId());
	}
	
	
	public List<Task> listTasks(){
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().list();
		return tasks;
	}
	
	/**根据发布编号得到发布名称，用于查看流程图
	 * 
	* @Title: processDefinitionById 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param deployId
	* @param @return    设定文件 
	* @return ProcessDefinition    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-6 上午11:42:35 
	* @throws
	 */
	public String processDefinitionById(String deployId) {
		String processKey = "";
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
		if(null != processDefinition){
			processKey = processDefinition.getId();
		}
		return processKey;
	}
	
	/**开始流程,直接驱动到下一步去
	 * 
	* @Title: startBussniseTask 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param processkey
	* @param @return    设定文件 
	* @return String    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-6 下午3:06:02 
	* @throws
	 */
	public String startBussniseTask(String processkey){
		if(null == processkey){
			processkey = "";
		}
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processkey);
		String processinstanceid = instance.getProcessInstanceId();
		Task task = taskService.createTaskQuery().processInstanceId(processinstanceid).singleResult();
		return processinstanceid;
	}
	
	
	/**完成流程到下一步
	 * 
	* @Title: completeBussniseTask 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param processinstanceid
	* @param @param assignee
	* @param @param variables    设定文件 
	* @return void    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-6 下午3:06:13 
	* @throws
	 */
	public void completeBussniseTask(String processinstanceid,String assignee,Map<String,Object> variables){
		completeBussniseTask(processinstanceid, assignee, variables,null);
	}
	
	/**根据状态驱动流程图   报修
	 * 
	 * @param processinstanceid
	 * @param assignee
	 * @param variables
	 */
	public void completeBussniseTask(String processinstanceid,String assignee,Map<String,Object> variables,String state){
		if(null == processinstanceid){
			processinstanceid = "";
		}
		Task task = taskService.createTaskQuery().processInstanceId(processinstanceid).singleResult();
		boolean isCompleteGrap =false;
		if(null != task){
			/**
			 * state 状态等于
			 * 1--驱动 “工单派工”  , 投诉派工
			 * 2--工单接收确认,业主意见,维修, 投诉处理
			 * 3--销单,消单
			 * 4--业主回访
			 * 5--归档
			 */
			String taskName = task.getName();
			if(null == taskName){
				taskName = "";
			}
			if(state==null){
				isCompleteGrap = true;
			}else if(null !=state && state.length()>0){
				if(state.equals("1") && (taskName.equals("工单派工") || taskName.equals("投诉派工"))){
					isCompleteGrap = true;
				}else if(state.equals("2") && (taskName.equals("工单接收确认") || taskName.equals("业主意见") || taskName.equals("维修") || taskName.equals("投诉处理"))){
					isCompleteGrap = true;
				}else if(state.equals("3") && (taskName.equals("销单") || taskName.equals("消单")||taskName.equals("维修"))){
					isCompleteGrap = true;
				}else if(state.equals("4") && taskName.equals("业主回访")){
					isCompleteGrap = true;
				}else if(state.equals("5") && (taskName.equals("工单归档") || taskName.equals("归档"))){
					isCompleteGrap = true;
				}
				else if(state.equals("6") && (taskName.equals("投诉受理") || taskName.equals("归档"))){
					isCompleteGrap = true;
				}
				else if(state.equals("1") && (taskName.equals("投诉受理")||taskName.equals("业主取消"))){
					isCompleteGrap = true;
				}
				else if(state.equals("1") && (taskName.equals("投诉派工"))){
					isCompleteGrap = true;
				}
				else if(state.equals("2") && (taskName.equals("投诉处理"))){
					isCompleteGrap = true;
				}
			}
			
			if(isCompleteGrap){
				String taskId = task.getId();
				task.setAssignee(assignee);
				if(null != variables && variables.size()>0){
					String flag = null == variables.get("flag")?"":variables.get("flag").toString();
					String compFlag = null == variables.get("compFlag")?"":variables.get("compFlag").toString();
					if((null != flag && flag.length()>0) && null != compFlag && compFlag.length()>0){
						
						if(null != taskName && taskName.equals("工单接收确认")){
							Map<String,Object> flagVariable = new HashMap<String,Object>();
							
							flagVariable.put("flag", flag);
							taskService.complete(taskId,flagVariable);
							
							task = taskService.createTaskQuery().processInstanceId(processinstanceid).singleResult();
							if(null != compFlag){
								taskId = task.getId();
								flagVariable = new HashMap<String,Object>();
								flagVariable.put("compFlag", compFlag);
								taskService.complete(taskId,flagVariable);
							}
							}else if(null != taskName && (taskName.equals("业主处理意见") || taskName.equals("业主意见"))){
							Map<String,Object> flagVariable = new HashMap<String,Object>();
							flagVariable = new HashMap<String,Object>();
							flagVariable.put("compFlag", compFlag);
							taskService.complete(taskId,flagVariable);
						}else{
							taskService.complete(taskId);
						}
					}else if(null != flag && flag.length()>0){
						Map<String,Object> flagVariable = new HashMap<String,Object>();
						flagVariable.put("flag", flag);
						taskService.complete(taskId,flagVariable);
					}else if(null != compFlag && compFlag.length()>0){
						Map<String,Object> flagVariable = new HashMap<String,Object>();
						flagVariable.put("compFlag", compFlag);
						taskService.complete(taskId,flagVariable);
					}else{
						taskService.complete(taskId,variables);
					}
				}else{
					taskService.complete(taskId);
				}
			}
		}
	}

	public void completeHouseWorkBussniseTask(String processinstanceid,String assignee, Map<String, Object> variables, String state)
	{
		if(null == processinstanceid){
			processinstanceid = "";
		}
		Task task = taskService.createTaskQuery().processInstanceId(processinstanceid).singleResult();
		boolean isCompleteGrap =false;
		if(null != task){
			String taskName = task.getName();
			if(null == taskName){
				taskName = "";
			}
			if(state==null){
				isCompleteGrap = true;
			}else if(null !=state && state.length()>0)
			{
				if(state.equals("1") && (taskName.equals("派工")))
				{
					isCompleteGrap = true;
				}else if(state.equals("3") && (taskName.equals("现场处理")))
				{
					isCompleteGrap = true;
				}else if(state.equals("4") && (taskName.equals("消单")))
				{
					isCompleteGrap = true;
				}
				else if(state.equals("5") && (taskName.equals("业主回访"))||(taskName.equals("归档")))
				{
					isCompleteGrap = true;
				}
				
				
			}
			if(isCompleteGrap){
				String taskId = task.getId();
				task.setAssignee(assignee);
				if(null != variables && variables.size()>0){
					String flag = null == variables.get("flag")?"":variables.get("flag").toString();
					String compFlag = null == variables.get("compFlag")?"":variables.get("compFlag").toString();
					if((null != flag && flag.length()>0) && null != compFlag && compFlag.length()>0){
						
						if(null != taskName && taskName.equals("工单接收确认")){
							Map<String,Object> flagVariable = new HashMap<String,Object>();
							flagVariable.put("flag", flag);
							taskService.complete(taskId,flagVariable);
							
							task = taskService.createTaskQuery().processInstanceId(processinstanceid).singleResult();
							if(null != task){
								taskId = task.getId();
								flagVariable = new HashMap<String,Object>();
								flagVariable.put("compFlag", compFlag);
								taskService.complete(taskId,flagVariable);
							}
						}else if(null != taskName && (taskName.equals("业主处理意见") || taskName.equals("业主意见"))){
							Map<String,Object> flagVariable = new HashMap<String,Object>();
							flagVariable = new HashMap<String,Object>();
							flagVariable.put("compFlag", compFlag);
							taskService.complete(taskId,flagVariable);
						}else{
							taskService.complete(taskId);
						}
					}else if(null != flag && flag.length()>0){
						Map<String,Object> flagVariable = new HashMap<String,Object>();
						flagVariable.put("flag", flag);
						taskService.complete(taskId,flagVariable);
					}else if(null != compFlag && compFlag.length()>0){
						Map<String,Object> flagVariable = new HashMap<String,Object>();
						flagVariable.put("compFlag", compFlag);
						taskService.complete(taskId,flagVariable);
					}else{
						taskService.complete(taskId,variables);
					}
				}else{
					taskService.complete(taskId);
				}
			}
		}
		
		
		
		
		
	}
}

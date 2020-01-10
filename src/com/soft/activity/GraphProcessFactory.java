/**   
* @Title: GraphProcessFactory.java 
* @Package com.soft.activiti 
* @Description: TODO
* @author huangyq huangyq@gz.csg.cn   
* @date 2014-5-18 下午12:45:23 
* @version V1.0   
*/ 
package com.soft.activity;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.activiti.engine.ProcessEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/** 
 * @ClassName: GraphProcessFactory 
 * @Description: TODO
 * @author huangyq huangyq@gz.csg.cn  
 * @date 2014-5-18 下午12:45:23 
 *  
 */
public class GraphProcessFactory {
	
//	private BpmProcessService bmpService;

	public InputStream graphImage(ProcessEngine processEngine,String processInstanceId,String opt){
//		DoradoContext application1 = DoradoContext.getCurrent();
//		bmpService = (BpmProcessServiceImpl) this.getObjectFromApplication(application1.getServletContext(),"bpmProcessServiceImpl");
//		
//		Command<InputStream> cmd = null;
//		System.out.println("processInstanceId=="+processInstanceId);
//		if(opt.equals("definitionId")){
//			List<BpmProcessEntity> bpmEntitys = bmpService.getByBpmId(processInstanceId);
//			for(int i = 0;i<bpmEntitys.size();i++){
//				BpmProcessEntity bpmEntity = bpmEntitys.get(i);
//				processInstanceId = bpmEntity.getBpmProinstanceId();
//				if(null == processInstanceId || ("").equals(processInstanceId)){
//					
//				}else{
//					//取第一个，返回只有第一笔
//					break;
//				}
//			}
//			cmd = new ProcessDefinitionDiagramCmd(processInstanceId);
//		}else{
//			cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);
//		}
		
		return null;
//		return processEngine.getManagementService().executeCommand(cmd);
	}
	
	/**
	 * @param servletContext
	 * @param beanName
	 * @return
	 * 此方法是主要是为了在Listener中从spring中获得类的对象
	 */
	private Object getObjectFromApplication(ServletContext servletContext,
			String beanName) {
		// 通过WebApplicationContextUtils 得到Spring容器的实例。
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		// 返回Bean的实例。
		return application.getBean(beanName);
	}
}

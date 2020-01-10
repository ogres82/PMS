/**   
* @Title: GraphProcess.java 
* @Package com.soft.activiti 
* @Description: TODO
* @author huangyq huangyq@gz.csg.cn   
* @date 2014-5-13 上午11:28:44 
* @version V1.0   
*/ 
package com.soft.activity;

/** 
 * @ClassName: GraphProcess 
 * @Description: TODO
 * @author huangyq huangyq@gz.csg.cn  
 * @date 2014-5-13 上午11:28:44 
 *  
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GraphProcess extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	@Resource
//	private BpmProcessDao bpmDao;

	private static final Log logger = LogFactory.getLog(GraphProcess.class);

	private String processInstanceId = "";
	private String opt = "";
	private ProcessEngine processEngine;
//	private RepositoryService repositoryService;
//	private RuntimeService runtimeService;
//	private TaskService taskService;
//	private HistoryService historyService;
//	private ProcessEngineConfiguration processEngineConfiguration;
	
//	private List<ProcessDefinition> processDefinitions;
	
	/**
	 * Constructor of the object.
	 */
	public GraphProcess() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("start");
		
		processInstanceId = (null == request.getParameter("processInstanceId") || ("").equals(request.getParameter("processInstanceId")))?"":request.getParameter("processInstanceId").toString();

		opt = (null == request.getParameter("opt") || ("").equals(request.getParameter("opt")))?"":request.getParameter("opt").toString();

		response.setContentType("text/html");
        
        InputStream is = new GraphProcessFactory().graphImage(processEngine,processInstanceId, opt);//
        response.setContentType("image/png");

        int len = 0;
        byte[] b = new byte[1024];

        while ((len = is.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
        
       
	}
	
	/**
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
		processInstanceId = (null == request.getParameter("processInstanceId") || ("").equals(request.getParameter("processInstanceId")))?"":request.getParameter("processInstanceId").toString();

		//repositoryService.createDeployment().addClasspathResource("bpmn2/MyActiviti.bpmn20.xml").deploy();
		//ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myActiviti");
//		RepositoryService repositoryService = processEngine.getRepositoryService();
		List<Task> tasks = taskService.createTaskQuery().list();
		Map<String,Object> map = new HashMap<String,Object>();
//		processInstanceId = 5;
		map.put("intVar", 5);
//		String processInstanceId="5";
//		for(Task task:tasks){
//			processInstanceId = task.getProcessInstanceId();
//			//taskService.complete(task.getId(),map);
//		}
		
//	    processDefinitions = repositoryService.createProcessDefinitionQuery().active().list();
		response.setContentType("text/html");
		
//		for(ProcessDefinition processDefinition:processDefinitions){
//			processInstanceId = processDefinition.getId();
//		}
		System.out.println("processInstanceId=="+processInstanceId);
        Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);
//        Command<InputStream> cmd = new ProcessDefinitionDiagramCmd(processInstanceId);

        InputStream is = processEngine.getManagementService().executeCommand(cmd);
        response.setContentType("image/png");

        int len = 0;
        byte[] b = new byte[1024];

        while ((len = is.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
        
       
	}
	 */

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
		
		processEngine = ProcessEngines.getDefaultProcessEngine();
//		repositoryService = processEngine.getRepositoryService();
//		runtimeService = processEngine.getRuntimeService();
//		taskService = processEngine.getTaskService();
//		historyService = processEngine.getHistoryService();
	}

}


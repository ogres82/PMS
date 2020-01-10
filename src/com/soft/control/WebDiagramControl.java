package com.soft.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.controller.IController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.soft.util.JsonMapper;

@Repository
@Component
public class WebDiagramControl implements IController{
	
	@Resource
	RepositoryService repositoryService;
	
	@Resource
	ProcessEngine processEngine;
	
	JsonMapper jsonMapper = new JsonMapper();

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String open(){
		String modelId = "";
		Model model = null;
		//如果获取存在modelId对应的模型，将跳转到/workflow/bpm/modeler.html?modelId={modelId}页面
		if (null != modelId && modelId.length()>0){
			model = this.repositoryService.getModel(modelId.trim());
		}else{
			if (model == null) {
				model = repositoryService.newModel();
				repositoryService.saveModel(model);
			}
		}
		modelId = model.getId();
		return modelId;//"/webDiagram/modeler.html";
	}
	
	/**发布流程
	 * 
	 * @param modelId
	 * @return
	 * @throws Exception
	 */
	public String deploy(String modelId) throws Exception{
	
		Model modelData = repositoryService.getModel(modelId);
		byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
		if (bytes == null) {
//			theModel.addAttribute("message", "模型数据为空，请先设计流程并成功保存，再进行发布。");
//			return "modeler/failure";
		}
		byte[] bpmnBytes = null;
		JsonNode modelNode = new ObjectMapper().readTree(bytes);
		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		bpmnBytes = new BpmnXMLConverter().convertToXML(model);
//		bpmnBytes = new BaseBpmnXMLConverter().convertToXML(model);
		String processName = modelData.getName() + ".bpmn20.xml";
		
		Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
		modelData.setDeploymentId(deployment.getId());
		repositoryService.saveModel(modelData);
//		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
//		for (ProcessDefinition processDefinition : processDefinitions) {
//			this.processEngine.getManagementService().executeCommand(new SyncProcessCmd(processDefinition.getId()));
//		}
		
		return null;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("application/json;charset=utf-8");
		arg1.setHeader("pragma","no-cache");
		arg1.setHeader("cache-control","no-cache");
		String dispatcherUrl = "./../webDiagram/modeler.html?modelId=";
		//return "redirect:/widgets/modeler/modeler.html?modelId=" + id;
		String opt=null == arg0.getParameter("opt")?"":arg0.getParameter("opt").toString();
		String modelId=null == arg0.getParameter("modelId")?"":arg0.getParameter("modelId").toString();
//		String description=null == arg0.getParameter("description")?"":arg0.getParameter("description").toString();
		if(opt.equals("editorJson")){
			String jsonString = getEditorJson(modelId);
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(opt.equals("stencilset")){
			String jsonString = "";
			try {
				jsonString = stencilset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//传输JSON
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(opt.equals("save")){
			//保存
			String jsonString = "{}";
			String name=null == arg0.getParameter("name")?"":arg0.getParameter("name").toString();
			String jsonXml=null == arg0.getParameter("json_xml")?"":arg0.getParameter("json_xml").toString();
			String description=null == arg0.getParameter("description")?"":arg0.getParameter("description").toString();
			
			
			Model model = repositoryService.getModel(modelId);
			if(null == model){
				model = repositoryService.newModel();
			}
			model.setName(name);
			ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		    modelObjectNode.put("name", name);
		    modelObjectNode.put("description", description);
		    model.setMetaInfo(modelObjectNode.toString());
			repositoryService.saveModel(model);
			jsonString = jsonXml;
			repositoryService.addModelEditorSource(model.getId(), jsonXml.getBytes("utf-8"));
//			InputStream editorJsonStream = getClass().getClassLoader().getResourceAsStream(jsonXml);
//			repositoryService.addModelEditorSource(model.getId(), IOUtils.toByteArray(editorJsonStream));
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(opt.equals("modelerAdd")){
			String id = "";
			if(null == modelId || modelId.equals("")){
				id = open();
			}else{
				id = modelId;
			}
			
			dispatcherUrl = dispatcherUrl + id;
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			arg1.sendRedirect(dispatcherUrl);
		}else if(opt.equals("modelerList")){

			String jsonString = modelerList();
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json;charset=utf-8");
			arg1.setHeader("pragma","no-cache");
			arg1.setHeader("cache-control","no-cache");
			System.out.println(jsonString);
			PrintWriter out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}else if(opt.equals("deploy")){
			try {
				deploy(modelId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			arg1.sendRedirect("./../webDiagram/modelerList.jsp");
		}
		//deploy
	}
	
	public String modelerList() throws IOException {
		List<Model> models = repositoryService.createModelQuery().list();
		System.out.println(models.size());
		String jsonString = JSON.toJSONString(models);
		//jsonString = jsonString.replaceAll("\\", "");
		JSONArray jsonArr = JSON.parseArray(jsonString); 
	    JSONObject jsonObject=new JSONObject();
	    jsonObject.put("rows", jsonArr);//JSONArray
	    jsonObject.put("total",10);//总记录数
		return JSON.toJSONString(jsonObject);
	}
	
	/**得到字符串
	 * 
	* @Title: stencilset 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-21 下午3:14:25 
	* @throws
	 */
	public String stencilset() throws Exception { 
		InputStream stencilsetStream = getClass().getClassLoader().getResourceAsStream("stencilset.json");
		try{
			return IOUtils.toString(stencilsetStream, "utf-8"); 
		} catch (Exception e) {
			throw new RuntimeException("Error while loading stencil set", e);
		}
	}
	
	/**
	 * 编辑的数据
	* @Title: getEditorJson 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param modelId
	* @param @return
	* @param @throws IOException    设定文件 
	* @return String    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-21 下午4:38:19 
	* @throws
	 */
	public String getEditorJson(String modelId) throws IOException{
		if(null == modelId || modelId.equals("")){
			modelId = "";
		}
	    Model model = repositoryService.getModel(modelId);
	 
	    if (model == null) {
	       model = repositoryService.newModel();
	       repositoryService.saveModel(model);
	     }
	 
	     Map<String,Object> root = new HashMap<String,Object>();
	     root.put("modelId", model.getId());
	     root.put("name", "name");
	     root.put("revision", Integer.valueOf(1));
	     root.put("description", "description");
	 
	     byte[] bytes = repositoryService.getModelEditorSource(model.getId());
	 
	     if (bytes != null) {
	       String modelEditorSource = new String(bytes, "utf-8");
	 
	       @SuppressWarnings("unchecked")
	       Map<String,Object> modelNode = this.jsonMapper.fromJson(modelEditorSource, Map.class);
	       root.put("model", modelNode);
	     } else {
	       Map<String,Object> modelNode = new HashMap<String,Object>();
	       modelNode.put("id", "canvas");
	       modelNode.put("resourceId", "canvas");
	 
	       Map<String,Object> stencilSetNode = new HashMap<String,Object>();
	       stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
	 
	       modelNode.put("stencilset", stencilSetNode);
	 
	       model.setMetaInfo(this.jsonMapper.toJson(root));
	       model.setName("name");
	       model.setKey("key");
	 
	       root.put("model", modelNode);
	     }
	     return this.jsonMapper.toJson(root);

	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/model/diagram";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}

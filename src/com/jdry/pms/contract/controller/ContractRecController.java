package com.jdry.pms.contract.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.contract.pojo.ContractInfo;
import com.jdry.pms.contract.pojo.ContractRec;
import com.jdry.pms.contract.pojo.VContractRec;
import com.jdry.pms.contract.service.ContractInfoService;
import com.jdry.pms.contract.service.ContractRecService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class ContractRecController implements IController{
	
	@Resource
	ContractRecService service;
	
	@Resource
	ContractInfoService contractInfoService;

	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		String recId=null == arg0.getParameter("recId")?"":arg0.getParameter("recId").toString();//员工工号

		try {
			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("list")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			    Page<VContractRec> page =new Page<VContractRec>(showCount, currentPage);
			    service.queryVContractRecByParam(page, parameter, null);
			    List<VContractRec> result=(List<VContractRec>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editContractRec") || method.equals("viewContractRec")){
				
				arg1.setContentType("application/json;charset=utf-8");
				VContractRec contractRec = new VContractRec();
				contractRec = service.getVContractRecById(recId);
				String jsonString = JSON.toJSONString(contractRec);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd");
				String contractCode= null == arg0.getParameter("contractCode")?"":arg0.getParameter("contractCode").toString();
				String contractDealContent= null == arg0.getParameter("contractDealContent")?"":arg0.getParameter("contractDealContent").toString();
				String contractId= null == arg0.getParameter("contractId")?"":arg0.getParameter("contractId").toString();
				String recPerson= null == arg0.getParameter("recPerson")?"":arg0.getParameter("recPerson").toString();
				String recTime= null == arg0.getParameter("recTime")?"":arg0.getParameter("recTime").toString();
				String remark= null == arg0.getParameter("remark")?"":arg0.getParameter("remark").toString();
				
				String result = "保存失败";
				
				
				ContractRec  contractRec = new ContractRec();
				if(recId !=null && !recId.isEmpty()){
					contractRec.setRecId(recId);
				}
				contractRec.setContractCode(contractCode);
				contractRec.setContractDealContent(contractDealContent);
				contractRec.setContractId(contractId);
				contractRec.setRecPerson(recPerson);
				contractRec.setRecTime(sdf.parse(recTime));
				contractRec.setRemark(remark);
				
				try{
					service.addContractRec(contractRec);
					result = "保存成功";
				}catch(Exception e){
					result = "保存失败";
					e.printStackTrace();
				}finally{
					
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.print(JSON.toJSONString(result));
					out.flush();
				}
				
			}
			
			if(method.equalsIgnoreCase("deleteContractRec")){
				
				PrintWriter out = arg1.getWriter();
				String result = "删除失败";
				try{
					
					if(!StringUtil.isEmpty(recId)){
						service.deleteContractRec(recId);
						result = "删除成功";
					}

				}catch(Exception e){
					result = "删除失败";
					e.printStackTrace();
				}finally{
					String jsonString = JSON.toJSONString(result);
					out.println(jsonString);
					out.flush();
				}
			
			}else if(method.equalsIgnoreCase("searchContract")){
				
				String keyword = java.net.URLDecoder.decode(arg0.getParameter("keyword"),"UTF-8");
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("keyword", keyword);
				List<ContractInfo> contract = (List<ContractInfo>) contractInfoService.queryContractByParam(param);
				String jsonString = JSON.toJSONString(contract);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl() {
		return "/contract/contractRec";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}

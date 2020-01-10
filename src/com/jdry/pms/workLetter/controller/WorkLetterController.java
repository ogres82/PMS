package com.jdry.pms.workLetter.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.comm.util.DeleteFileUtil;
import com.jdry.pms.workLetter.pojo.WorkLetter;
import com.jdry.pms.workLetter.service.WorkLetterService;

@Controller
public class WorkLetterController implements IController {
	@Resource
	WorkLetterService service;

	// @Resource
	// DeleteFileUtil;

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method = arg0.getParameter("method");
		String deptId = arg0.getParameter("deptId");
		PrintWriter out = arg1.getWriter();
		String jsonString = "";

		try {
			if (method.equals("list")) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("deptId", deptId);
				List<WorkLetter> List = service.findEntityByHQL("from WorkLetter where deptId =:deptId", params);
				jsonString = JSON.toJSONString(List);
			}
			if (method.equals("save")) {
				String wlStr = arg0.getParameter("wlInfo");
				WorkLetter wl = JSON.parseObject(wlStr, WorkLetter.class);
				service.saveOrUpdateEntity(wl);
				jsonString = JSON.toJSONString("succese");
			}
			if (method.equals("del")) {
				String letId[] = arg0.getParameter("letIds").split(",");
				if (letId.length > 0) {
					for (int i = 0, len = letId.length; i < len; i++) {
						WorkLetter wl = service.findById(Integer.parseInt(letId[i]));
						if (wl != null) {
							service.deleteEntity(wl);
						}
					}
				}
				jsonString = JSON.toJSONString("succese");
			}

			if (method.equals("delFile")) {
				String fileName = arg0.getParameter("fileId");
				boolean delFalg = DeleteFileUtil.deleteFile(fileName);
				if(delFalg){
					jsonString = JSON.toJSONString("删除成功！");
				}else{
					jsonString = JSON.toJSONString("删除失败！");
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			jsonString = JSON.toJSONString("操作失败！");
			e.printStackTrace();
		} finally {
			out = arg1.getWriter();
			out.println(jsonString);
			out.flush();
		}

	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/workLetter";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}

package com.jdry.pms.bbs.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.bbs.pojo.VBbsReply;
import com.jdry.pms.bbs.service.TopicService;
import com.jdry.pms.comm.util.FileUploadController;

@Repository
@Component
public class ForumController implements IController {
	@Resource
	TopicService service;

	@Resource
	private FileUploadController upload;

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
		PrintWriter out = arg1.getWriter();
		String jsonString = "";
		try {
			if (method.equals("loadTopicList")) {
				jsonString = service.loadTopicList(arg0);
			}
			if (method.equals("loadTopic")) {
				jsonString = service.loadTopic(arg0);
			}
			if (method.equals("loadReply")) {
				jsonString = service.loadReply(arg0);
			}
			if (method.equals("addReply")) {
				VBbsReply result = service.addReply(arg0);
				jsonString = JSON.toJSONString(result);
			}
			if (method.equals("setElite")) {
				String result = service.setElite(arg0);
				jsonString = JSON.toJSONString(result);
			}
			if (method.equals("setTop")) {
				String result = service.setTop(arg0);
				jsonString = JSON.toJSONString(result);
			}
			if (method.equals("cancelElite")) {
				String result = service.cancelElite(arg0);
				jsonString = JSON.toJSONString(result);
			}
			if (method.equals("cancelTop")) {
				String result = service.cancelTop(arg0);
				jsonString = JSON.toJSONString(result);
			}
			if (method.equals("delete")) {
				String result = service.delete(arg0);
				jsonString = JSON.toJSONString(result);
			}
			if (method.equals("uploadImg")) {
				Map<String, Object> result = upload.execute(arg0);
				jsonString = JSON.toJSONString(result);
			}
			if (method.equals("save")) {
				String result = service.saveTopic(arg0);
				jsonString = JSON.toJSONString("操作成功");
			}
			if (method.equals("delReply")) {
				String result = service.delComment(arg0);
				jsonString = JSON.toJSONString("操作成功");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			out.print(JSON.toJSONString("操作失败"));
		} finally {
			out.print(jsonString);
			out.flush();
			out.close();
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/forum";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}

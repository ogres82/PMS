package com.jdry.pms.hotLine.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.hotLine.pojo.HotLine;
import com.jdry.pms.hotLine.service.HotLineService;

@Repository
@Component
public class HotLineController implements IController {

	@Resource
	HotLineService service;

	@Override
	public String getUrl() {
		return "/hotLine";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

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
		String method = arg0.getParameter("method");
		String HotLineId = null == arg0.getParameter("id") ? "" : arg0
				.getParameter("id").toString();

		try {
			if (method.equalsIgnoreCase("initImgs")) {
				String path = arg0.getSession().getServletContext()
						.getRealPath("/hotLine/icon");
				File file = new File(path);
				String url = arg0.getRequestURL().toString();

				File[] subFile = file.listFiles();
				List<Map<String, String>> result = new ArrayList<Map<String, String>>();

				url = url.replace(".app", "");
				url += "/icon/";

				for (int i = 0; i < subFile.length; i++) {
					// 判断是否为文件夹
					if (!subFile[i].isDirectory()) {
						Map<String, String> imgs = new HashMap<String, String>();
						imgs.put("imgUrl", url + subFile[i].getName());
						result.add(imgs);
					}
				}
				String jsonString = JSON.toJSONString(result);
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			if (method.equalsIgnoreCase("list")) {
				List<HotLine> list = service.getHotLineList();
				String jsonString = JSON.toJSONString(list);
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			if (method.equals("save")) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String imgUrl = null == arg0.getParameter("imgUrl") ? "" : arg0.getParameter("imgUrl").toString();
				String name = null == arg0.getParameter("name") ? "" : arg0.getParameter("name").toString();
				String telephone = null == arg0.getParameter("telephone") ? ""	: arg0.getParameter("telephone").toString();
				String lineType = null == arg0.getParameter("lineType") ? "" : arg0.getParameter("lineType").toString();
				String userId = null == arg0.getParameter("userId") ? "" : arg0.getParameter("userId").toString();
				String updateDate = null == arg0.getParameter("updateDate") ? "": arg0.getParameter("updateDate").toString();
				
				HotLine hotline = new HotLine();
				
				if(HotLineId !=null && !HotLineId.isEmpty()){
					hotline.setId(HotLineId);
				}
				
				hotline.setName(name);
				hotline.setTelephone(telephone);
				hotline.setImgUrl(imgUrl);
				hotline.setUserID(userId);
				hotline.setLineType(lineType);
				hotline.setUpdateDate(sdf.parse(updateDate));
				
				boolean flag = service.saveHotLineInfo(hotline);
				String result = "保存成功";
				if(flag){
					result = "保存失败";
				}
				
				String jsonString = JSON.toJSONString(result);
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			if (method.equalsIgnoreCase("delete")) {

				PrintWriter out = arg1.getWriter();
				String result = "删除成功";
				try{
					service.delHotLineInfo(HotLineId);
				}catch (Exception e) {
					e.printStackTrace();
					result = "删除失败";
				} finally {
					String jsonString = JSON.toJSONString(result);
					out.println(jsonString);
					out.flush();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

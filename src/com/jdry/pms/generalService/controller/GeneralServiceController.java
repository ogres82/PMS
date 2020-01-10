package com.jdry.pms.generalService.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;

import com.bstek.bdf2.core.controller.IController;



@ResponseBody
public class GeneralServiceController implements IController{

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String method = arg0.getParameter("method");
		String nodeId = arg0.getParameter("nodeId");
		String nodeLevel = arg0.getParameter("nodeLevel");
		
		try {
			if(method.equals("getTree")) {
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}finally {
			
		}
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/generalService";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}


//	@RequestMapping(value="/getHouseZtree",method=RequestMethod.GET)
//	public List<HouseZtree> getHouseZtree() {
//		String hql = "FROM HouseZtree";
//		Map<String,Object> objects = new HashMap<String,Object>();
//		List<HouseZtree> list =houseZtreeService.findEntityByHQL(hql, objects);
//		return list;
//	}
}

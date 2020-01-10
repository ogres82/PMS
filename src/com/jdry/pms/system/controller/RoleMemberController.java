package com.jdry.pms.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.RoleMember;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.bdf2.core.view.role.member.RoleMemberMaintain;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;
import com.jdry.pms.system.pojo.VBdf2RoleMember;
import com.jdry.pms.system.service.RoleMemberService;

@Component
public class RoleMemberController extends HibernateDao implements IController{

	@Resource
	private RoleMemberMaintain roleMemberMaintain;
	
	@Resource
	PropertyManagerService propertyManagerService;
	
	@Resource
	private RoleMemberService service;
	
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
		String roleId=null == arg0.getParameter("roleId")?"":arg0.getParameter("roleId").toString();//员工工号

		try {
			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("list")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    String type = arg0.getParameter("type") == null ? "" : arg0.getParameter("type");
			    search = CharacterUtil.getUTF_8String(search);
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			    parameter.put("type", type);
			    parameter.put("roleId", roleId);
			    Page<VBdf2RoleMember> page =new Page<VBdf2RoleMember>(showCount, currentPage);
			    List<VBdf2RoleMember> result = new ArrayList<VBdf2RoleMember>();
			    try{
			    	service.loadMembers(page,parameter);
			    	result = (List<VBdf2RoleMember>) page.getEntities();
			        
			    }catch(Exception e){
			    	e.printStackTrace();
			    }finally{
			    	String b = JSON.toJSONString(result);				
			    	long count = page.getEntityCount();				//获取总记录数
			    	PrintWriter out = arg1.getWriter();
			    	String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
			    	out.print(r);
			    	out.flush();
			    }
			}
			
			if(method.equalsIgnoreCase("positionList")){
				Collection<DefaultPosition> result= propertyManagerService.queryPosition(new HashMap<String,Object>());
				String b = JSON.toJSONString(result);				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("deptList")){
				Collection<DefaultDept> result= propertyManagerService.queryDept(new HashMap<String,Object>());
				String b = JSON.toJSONString(result);				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
			if(method.equals("save")){
				String id= null == arg0.getParameter("id")?"":arg0.getParameter("id").toString();
				String type= null == arg0.getParameter("type")?"":arg0.getParameter("type").toString();
				String[] strs= id.split(",");
				Collection<String> ids = new ArrayList<String>();
				for(int i=0;i<strs.length;i++){
					ids.add(strs[i]);
				}
				roleMemberMaintain.insertRoleMember(ids, type, roleId);
				roleMemberMaintain.refreshAllSecurityMetadata();
				roleMemberMaintain.refreshUrlSecurityMetadata();
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("deleteMember")){
				String memberId= null == arg0.getParameter("memberId")?"":arg0.getParameter("memberId").toString();
				String[] members = memberId.split(",");
				for(int i=0;i<members.length;i++){
					roleMemberMaintain.deleteRoleMember(members[i]);
				}
				roleMemberMaintain.refreshAllSecurityMetadata();
				roleMemberMaintain.refreshUrlSecurityMetadata();
				String b = JSON.toJSONString("删除成功");				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("checkUniqueMember")){
				String username= null == arg0.getParameter("username")?"":arg0.getParameter("username").toString();
				String result = "";
				List<RoleMember> lists = this
						.query("from " + RoleMember.class.getName()
								+ " where username='" + username + "'");
				if (null != lists && lists.size() > 0) {
					result = "false";
				} else {
					result = "true";
				}
				String b = JSON.toJSONString(result);				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

	@Override
	public String getUrl() {
		return "/role/roleMember";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}

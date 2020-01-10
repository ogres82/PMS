package com.jdry.pms.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.exception.NoneLoginException;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.view.user.UserMaintain;
import com.jdry.pms.system.pojo.VBdf2User;
import com.jdry.pms.system.service.UserMaintainService;

@SuppressWarnings("deprecation")
@Repository
@Component
public class UserMaintainController implements IController {

	@Resource
	UserMaintainService service;

	@Resource
	UserMaintain userMaintain;

	PasswordEncoder pEncoder = new ShaPasswordEncoder();

	@Override
	public boolean anonymousAccess() {
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method = arg0.getParameter("method");
		String username = null == arg0.getParameter("username") ? "" : arg0.getParameter("username").toString();// 员工工号
		String ids = null == arg0.getParameter("ids") ? "" : arg0.getParameter("ids").toString();
		PrintWriter out = arg1.getWriter();
		String jsonString = "";
		try {

			DefaultUser user = (DefaultUser) ContextHolder.getLoginUser();
			if (method.equalsIgnoreCase("list")) {
				List<VBdf2User> list = service.queryUserByParam(0);
				jsonString = JSON.toJSONString(list);
			}
			if (method.equals("save")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String username_ = arg0.getParameter("username_") == null ? "" : arg0.getParameter("username_");
				String administrator = arg0.getParameter("administrator") == null ? "" : arg0.getParameter("administrator");
				String enabled = arg0.getParameter("enabled") == null ? "" : arg0.getParameter("enabled");
				String birthday = arg0.getParameter("birthday") == null ? "" : arg0.getParameter("birthday");
				String password = arg0.getParameter("password") == null ? "" : arg0.getParameter("password");
				String cname = arg0.getParameter("cname") == null ? "" : arg0.getParameter("cname");
				String email = arg0.getParameter("email") == null ? "" : arg0.getParameter("email");
				String ename = arg0.getParameter("ename") == null ? "" : arg0.getParameter("ename");
				String mobile = arg0.getParameter("mobile") == null ? "" : arg0.getParameter("mobile");
				String male = arg0.getParameter("male") == null ? "" : arg0.getParameter("male");
				String positionId = arg0.getParameter("positionId") == null ? "" : arg0.getParameter("positionId");
				String deptId = arg0.getParameter("deptId") == null ? "" : arg0.getParameter("deptId");
				String salt = String.valueOf(RandomUtils.nextInt(100));
				String password_ = this.pEncoder.encodePassword(password, salt);
				if (user == null) {
					throw new NoneLoginException("Please login first!");
				}
				DefaultUser duser = new DefaultUser();
				if (!username_.isEmpty()) {
					duser = service.getUserById(username);
				} else {
					duser.setUsername(username);
					duser.setPassword(password_);
					duser.setSalt(salt);
					duser.setCompanyId("jdry");
					duser.setCreateDate(new Date());
				}
				duser.setAdministrator(true);
				duser.setEnabled(Boolean.valueOf(enabled).booleanValue());
				duser.setEname(ename);
				duser.setMobile(mobile);
			
				duser.setMale(Integer.parseInt(male) == 1 ? true : false);
				if (!birthday.isEmpty()) {
					duser.setBirthday(sdf.parse(birthday));
				}
				duser.setCname(cname);
				duser.setEmail(email);
				service.saveUser(duser);
				//插入部门和岗位
				userMaintain.insertUserDept(username_, deptId);
				userMaintain.insertUserPosition(username_, positionId);
				jsonString = JSON.toJSONString("success");
			}

			if (method.equalsIgnoreCase("deleteUser")) {
				if (!StringUtil.isEmpty(username)) {
					service.deleteUser(username);
					
					jsonString = JSON.toJSONString("删除成功！");
				} else {
					jsonString = JSON.toJSONString("用户名错误！");
				}
			}
			if (method.equalsIgnoreCase("checkUsername")) {
				String fieldId = null == arg0.getParameter("fieldId") ? "" : arg0.getParameter("fieldId").toString();
				String fieldValue = null == arg0.getParameter("fieldValue") ? "" : arg0.getParameter("fieldValue").toString();
				String info = "[\"" + fieldId + "\",false]";
				try {
					String check = userMaintain.userIsExists(fieldValue);
					if (check == null) {
						info = "[\"" + fieldId + "\",true]";
					}
				} catch (Exception e) {
					e.printStackTrace();
					info = "[\"" + fieldId + "\",false]";
				}
				out.print(info);
			}

			if (method.equalsIgnoreCase("insertUserPosition")) {

				userMaintain.insertUserPosition(username, ids);
				out.print(JSON.toJSONString("success"));
				jsonString = JSON.toJSONString("success");
			}
			if (method.equalsIgnoreCase("insertUserDept")) {
				userMaintain.insertUserDept(username, ids);
				jsonString = JSON.toJSONString("success");
				arg0.setAttribute("loginUser", user);
			}
			if(method.equalsIgnoreCase("resetPassword")){
				if("".equals(username)||username==null){
					jsonString = "用户名不能为空！";	
				}else{
					String pw =  userMaintain.resetPassword(username);
					jsonString = JSON.toJSONString(pw);	
				}
			}
			if(method.equalsIgnoreCase("initDropList")){
				List list =  service.getDeptAndPos();
				jsonString = JSON.toJSONString(list);	
			}
		} catch (Exception e) {
			out.print(JSON.toJSONString("操作失败"));
			e.printStackTrace();
		} finally {
			out.print(jsonString);
			out.flush();
		}
	}

	@Override
	public String getUrl() {
		return "/user/userMaintain";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}

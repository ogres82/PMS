package com.jdry.pms.basicInfo.view;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.RoleMember;
import com.jdry.pms.basicInfo.pojo.VUser;
import com.jdry.pms.basicInfo.service.ProManagerService;
import com.jdry.pms.basicInfo.util.Security;
import com.jdry.pms.bbs.pojo.BbsUser;
import com.jdry.pms.bbs.service.BbsService;
import com.jdry.pms.comm.service.BusinessService;
import com.jdry.pms.comm.util.CommEnum;
import com.jdry.pms.comm.util.RedisPool;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.system.pojo.VAppAuth;
import com.jdry.pms.system.service.AppAuthService;
import com.jdry.pms.system.service.impl.UserMaintainServiceImpl;

@SuppressWarnings("deprecation")
@Component
public class ProManagerView {
	@Resource
	ProManagerService service;
	@Resource
	BusinessService businessService;
	@Resource
	AppAuthService appAuthService;
	
	@Resource
	UserMaintainServiceImpl userMaintainServiceImpl;
	
	@Resource
	BbsService bbsService;
	private PasswordEncoder passwordEncoder = new ShaPasswordEncoder();
	
	static Logger log=Logger.getLogger(OwnerInfoView.class);
	public String managerLogin(String data){
		JSONObject obj = JSON.parseObject(data);
		String userName = obj.getString("username");
		String pwd = obj.getString("password");
		if(service == null){
			service = (ProManagerService) SpringUtil.getObjectFromApplication("proManagerServiceImpl");
		}
		String salt = service.getSalt(userName);
		String pass = this.passwordEncoder.encodePassword(pwd, salt);
		String loginStatus = service.managerLogin(userName, pass);
		String user = "";
		if(loginStatus.equals("0")){
			return "{\"status\":\"0\",\"message\":\"登录失败：用户不存在或密码错误\"}";
		}else{
			List<VUser> res = (List<VUser>) service.getPositionByName(userName);
			if(res.size()>1){
				//多个岗位暂时没处理
			}else{
				if(businessService == null){
					businessService = (BusinessService) SpringUtil.getObjectFromApplication("businessServiceImpl");
				}
				if(appAuthService == null){
					appAuthService = (AppAuthService) SpringUtil.getObjectFromApplication("appAuthServiceImpl");
				}
				VUser u = res.get(0);
				RoleMember rm = businessService.getRoleMemberByUsername(u.getUserName());
				List<VAppAuth> list = new ArrayList<VAppAuth>();
				if(rm == null){
					return "{\"status\":\"0\",\"message\":\"登录失败！用户未授权，请联系后台处理。\"}";
				}else{
					list = appAuthService.getAppAuthByRole(rm.getRoleId());
				}
				Map map = new HashMap();
				if(rm.getRoleId().equals("4a6b5a45-a1e5-4593-a249-5f6523f5f9e3")){
					map.put("bbsFlag", "1");
				}else{
					map.put("bbsFlag", "0");
				}
				map.put("userName", u.getUserName());
				map.put("cName", u.getcName());
				map.put("mobile", u.getMobile());
				map.put("deptId", u.getDeptId());
				map.put("deptName", u.getDeptName());
				map.put("positionName", u.getPositionName());
				map.put("positionId", u.getPositionId());
				map.put("auth", list);
				user = JSON.toJSONString(map);
			}
			String token = Security.getEncode(userName,pwd);
			Jedis jedis = null;
			try {
				jedis = RedisPool.getJedisObject();
				jedis.set(token, userName);
				jedis.expire(token, CommEnum.TOKEN_EXPIRE_TIME);
			} catch (Exception e) {
				// TODO: handle exception
//				log.error("redis处理异常！");
				e.printStackTrace();
			}finally{
				RedisPool.recycleJedisOjbect(jedis);
//				log.info("释放redis连接!");
			}
			
			
			return "{\"status\":\"1\",\"message\":\"登录成功！\",\"token\":\""+token+"\",\"data\":"+user+"}";
		}
		
	}
	
	/**
	 * 物管用户信息修改
	 * @param data
	 * @return
	 */
	public String promInfoUpdate(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String userName = obj.getString("userName");
		String male = obj.getString("male");
		String promHeadImg = obj.getString("promHeadImg");
		String promNickName = obj.getString("promNickName");
		if(userMaintainServiceImpl == null){
			userMaintainServiceImpl = (UserMaintainServiceImpl) SpringUtil.getObjectFromApplication("userMaintainServiceImpl");
		}
		DefaultUser duser = userMaintainServiceImpl.getUserById(userName);
		if(duser!=null){
			if(male!=null && !male.isEmpty()){
				duser.setMale(male.equals("1")?true:false);
			}
			if(promHeadImg!=null && !promHeadImg.isEmpty()){
				duser.setAddress(promHeadImg);
			}
			userMaintainServiceImpl.saveUser(duser);
			if(bbsService == null){
				bbsService = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
			}
			BbsUser bbsUser = bbsService.getBbsUserByPhone(userName);
			if(bbsUser!=null){
				if(promNickName!=null && !promNickName.isEmpty()){
					bbsUser.setUserNickname(promNickName);
					bbsService.addBbsUser(bbsUser);
				}
			}else{
				bbsUser = new BbsUser();
				bbsUser.setMappingUserId(userName);
				bbsUser.setUserType("0");
				bbsUser.setUserNickname(promNickName);
				bbsService.addBbsUser(bbsUser);
			}
			return "{\"status\":\"1\",\"message\":\"修改成功\"}";
		}else{
			return "{\"status\":\"0\",\"message\":\"修改失败\"}";
		}
	}
	
	/**
	 * 获取物业用户信息
	 * @param data
	 * @return
	 */
	public String promUserInfo(String data){
		JSONObject obj = JSON.parseObject(data);
		String userName = obj.getString("userName");
		if(userMaintainServiceImpl == null){
			userMaintainServiceImpl = (UserMaintainServiceImpl) SpringUtil.getObjectFromApplication("userMaintainServiceImpl");
		}
		Map<String,Object> result = userMaintainServiceImpl.getUserDetailById(userName);
		return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":"+JSON.toJSONString(result,SerializerFeature.WriteMapNullValue)+"}";
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
	    this.passwordEncoder = passwordEncoder;
	  }
	
	public static void main(String args[]){
		ProManagerService plservice = null;
		if(plservice == null){
			plservice = (ProManagerService) SpringUtil.getObjectFromApplication("proManagerServiceImpl");
		}
		String loginStatus = plservice.managerLogin("xulei", "111111");
		System.out.println("================="+loginStatus);
	}
}

package com.jdry.pms.basicInfo.view;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.dao.ValidatorsDao;
import com.jdry.pms.basicInfo.pojo.AllArea;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.HouseOwner;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.pojo.PropertyOwner;
import com.jdry.pms.basicInfo.pojo.VHouseOwner;
import com.jdry.pms.basicInfo.pojo.VHouseProperty;
import com.jdry.pms.basicInfo.service.AllAreaService;
import com.jdry.pms.basicInfo.service.AreaPropertyService;
import com.jdry.pms.basicInfo.service.BuildingPropertyService;
import com.jdry.pms.basicInfo.service.HousePropertyService;
import com.jdry.pms.basicInfo.service.OwnerInfoService;
import com.jdry.pms.basicInfo.util.Security;
import com.jdry.pms.bbs.pojo.BbsUser;
import com.jdry.pms.bbs.service.BbsService;
import com.jdry.pms.comm.service.impl.SmsSendInteface;
import com.jdry.pms.comm.util.CommEnum;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.RedisPool;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.lzmh.service.LzmhService;

@Component
public class OwnerInfoView {
	
	@Resource
	OwnerInfoService service;
	SmsSendInteface smsService;
	
	@Resource
	ValidatorsDao vDao;
	@Resource
	BbsService bbsService;
	
	@Resource
	private LzmhService lzmhService;
	
	@Resource
	private AllAreaService allAreaService;
	
	@Resource
	private AreaPropertyService areaPropertyService;
	
	@Resource
	private BuildingPropertyService buildingPropertyService;
	
	@Resource
	private HousePropertyService housePropertyService;
	
	static Logger log=Logger.getLogger(OwnerInfoView.class);
	
	@DataProvider
	public void queryOwnerInfo(Page<PropertyOwner> page,Map<String, Object> parameter,Criteria criteria){
		service.query(page,parameter,criteria);
	}
	
	@DataProvider
	public Collection<PropertyOwner> queryOwnerInfoByParam(Map<String, Object> parameter){
		return service.queryOwnerInfoByParam(parameter);
	}
	
	@DataResolver
	public void saveOwnerInfo(Collection<PropertyOwner> owners){
	    service.saveOwnerInfo(owners);
	}
	
	@DataProvider
	public Collection<PropertyOwner> queryPropertyOwnerByParent(String id){
		return service.queryPropertyOwnerByParent(id);
	}
	
	/**
	 * 验证业主姓名唯一性
	 * @param parameter
	 * @return
	 * @throws InterruptedException
	 */
	@Expose
	public String checkOwnerName(String parameter)
			throws InterruptedException {
		String isExist = vDao.uniqueCheck("from PropertyOwner where ownerName='"+parameter+"'");
		if(isExist == null){
			return null;
		}else{
			return "名称\"" + isExist;
		}
		
	}
	
	/**
	 * 验证房间是否已被入住
	 * @param parameter
	 * @return
	 * @throws InterruptedException
	 */
	@Expose
	public String checkRoomIdName(String parameter)
			throws InterruptedException {
		String isExist = vDao.uniqueCheck("from PropertyOwner where roomId='"+parameter+"'");
		if(isExist == null){
			return null;
		}else{
			return "该房间号\"" +parameter+ "\"已被入住" ;
		}
		
	}
	@Expose
	public String ownerLogin(String data){
		JSONObject obj = JSON.parseObject(data);
		String userName = null == obj.getString("username")?"" : obj.getString("username");
		String pwd = null == obj.getString("password")?"" : obj.getString("password");
		if(service == null){
			service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
		}
		String loginStatus = service.ownerLogin(userName, pwd);
		if(loginStatus.equals("0")){
			return "{\"status\":\"0\",\"message\":\"登录失败：用户不存在或密码错误\"}";
		}else{
			String token = Security.getEncode(userName,pwd);
			Jedis jedis = null;
			try {
				jedis = RedisPool.getJedisObject();
				jedis.set(token, userName);
				jedis.expire(token, CommEnum.TOKEN_EXPIRE_TIME);
			} catch (Exception e) {
				log.error("redis处理异常！"+e+"-----ownerLogin");
				e.printStackTrace();
			}finally{
				RedisPool.recycleJedisOjbect(jedis);
			}
			List list = service.getOwnerInfo(userName);
			List result = new ArrayList();
			String ownerId = null;
			String ownerName = null;
			String ownerHeadImg = null;
			String ownerNickName = null;
			String ownerIdentity = null;
			String rooms = null;
			for(int i=0;i<list.size();i++){
				Map map = new HashMap();
				Object[] ob = (Object[]) list.get(i);
				ownerId = (String) ob[7];
				ownerName = null == (String) ob[8]?"" : (String)ob[8];
				ownerHeadImg = null == (String) ob[10]?"" : (String) ob[10];
				ownerNickName = null == (String) ob[11]?"" : (String) ob[11];
				ownerIdentity = null == (String) ob[12]?"" : (String) ob[12];
				map.put("communityId", ob[0]);
				map.put("communityName", ob[1]);
				map.put("buildId", ob[2]);
				map.put("buildName", ob[3]);
				map.put("roomId", ob[4]);
				map.put("roomNo", ob[5]);
				map.put("defaultMark", ob[6]);
				result.add(map);
				
			}
			if(result.size() > 0){
				rooms = JSON.toJSONString(result);
			}
			return "{\"status\":\"1\",\"message\":\"登录成功！\",\"token\":\""+token+"\",\"data\":{\"ownerId\":\""+ownerId+"\"," +
			   "\"ownerName\":\""+ownerName+"\"," +
			   "\"ownerHeadImg\":\""+ownerHeadImg+"\"," +
			   "\"ownerNickName\":\""+ownerNickName+"\"," +
			   "\"ownerIdentity\":\""+ownerIdentity+"\"," +
			   "\"rooms\":"+rooms+"}}";
		}
	}
	
	/**
	 * 随机生成4位数验证码
	 * @paramphone
	 * @return
	 * @throws IOException 
	 */
	@Expose
	public String getVerifyCode(String data) {
		 JSONObject obj = JSON.parseObject(data);
		 String mobilePhone = obj.getString("mobilePhone");
		 String type = obj.getString("type");
		 Map<String,Object> parameter = new HashMap<String,Object>();
		 parameter.put("mobilePhone", mobilePhone);
		 String varify="";    
		 int  intCount=(new Random()).nextInt(9999);     
		 if(intCount<1000)intCount+=1000;    
		 varify=intCount+"";
		 if(service == null){
			 service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
		 }
		 if(smsService == null){
			 smsService = (SmsSendInteface) SpringUtil.getObjectFromApplication("smsSendInteface");
		 }
		 if(type.equals("01")){  //注册验证
			 Collection<PropertyOwner> owners2 = service.queryOwnerInfoByParam(parameter,"0");
			 if(owners2.size()>0){
				 for(PropertyOwner owner:owners2){
					 if(owner.getPassword()==null || (owner.getPassword().equals(""))){
						 try {
							String r = smsService.sendSmsByCode(varify, mobilePhone, CommEnum.CODE_TEMP_ID);
						} catch (IOException e) {
							System.out.println("验证码发送失败！");
							e.printStackTrace();
						}
					 }else{
						 return "{\"status\":\"0\",\"message\":\"获取失败：该手机已注册！\"}";
					 }
				 }
			 }else{
				 try {
					String r = smsService.sendSmsByCode(varify, mobilePhone, CommEnum.CODE_TEMP_ID);
				} catch (IOException e) {
					System.out.println("验证码发送失败！");
					e.printStackTrace();
				}
			 }
		 }else if(type.equals("02")){  //忘记密码验证
			 Collection<PropertyOwner> owners2 = service.queryOwnerInfoByParam(parameter,"0");
			 if(owners2.size()>0){
				 for(PropertyOwner owner:owners2){
					if(owner.getPassword().isEmpty() || owner.getPassword()==null){
						return "{\"status\":\"0\",\"message\":\"修改失败：请先注册！\"}";
					}
				 }
				 try {
					String r = smsService.sendSmsByCode(varify, mobilePhone, CommEnum.CODE_TEMP_ID);
				} catch (IOException e) {
					System.out.println("邀请码发送失败！");
					e.printStackTrace();
				}
			 }else{
				 return "{\"status\":\"0\",\"message\":\"获取失败：该手机未注册！\"}";
			 }
		 }else{  //业主邀请码
			 try {
				String r = smsService.sendSmsByCode(varify, mobilePhone, CommEnum.CODE_TEMP_ID);
			} catch (IOException e) {
				System.out.println("邀请码发送失败！");
				e.printStackTrace();
			}
		 }
		 Jedis jedis = null;
		 try {
			 jedis = RedisPool.getJedisObject();
			 jedis.set(mobilePhone, varify);		 
			 jedis.expire(mobilePhone, CommEnum.CODE_EXPIRE_TIME);
		 } catch (Exception e) {
			e.printStackTrace();
			log.error("redis处理异常！"+e+"-----getVerifyCode");
		 }finally{
			 RedisPool.recycleJedisOjbect(jedis);
		 }
		 //System.out.println("过期了：：：："+jedis.get(mobilePhone+"_varify"));
		 //DoradoContext.getCurrent().setAttribute("verifyCode",s);
		 return "{\"status\":\"1\",\"message\":\"\"}";
	}
	
	
	/**
	 * 忘记密码 获取验证码
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	@Expose
	public String pwdGetVerifyCode(String data) {
		JSONObject obj = JSON.parseObject(data);
		String mobilePhone = obj.getString("mobilePhone");
		
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("mobilePhone", mobilePhone);
		if(service == null){
			service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
		}
		Collection<PropertyOwner> owners = service.queryOwnerInfoByParam(parameter);
		if(owners.size()==1 && owners.size()>0){
			 String varify="";    
			 int  intCount=(new Random()).nextInt(9999);     
			 if(intCount<1000)intCount+=1000;    
			 varify=intCount+"";  
			 if(smsService == null){
				 smsService = (SmsSendInteface) SpringUtil.getObjectFromApplication("smsSendInteface");
			 }
			 try {
				String r = smsService.sendSmsByCode(varify, mobilePhone, CommEnum.CODE_TEMP_ID);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("验证码发送失败！");
				e1.printStackTrace();
			}
			 Jedis jedis = null;
			 try {
				 jedis = RedisPool.getJedisObject();
				 jedis.set(mobilePhone, varify);
				 jedis.expire(mobilePhone, CommEnum.CODE_EXPIRE_TIME);
			 } catch (Exception e) {
				e.printStackTrace();
				log.error("redis处理异常！"+e+"-----pwdGetVerifyCode");
			 }finally{
				 RedisPool.recycleJedisOjbect(jedis);
			 }
			 return "{\"status\":\"1\",\"message\":\"\",\"verifyCode\":\""+varify+"\"}";
		}else{
			 return "{\"status\":\"0\",\"message\":\"该用户未注册！\"}";
		}
		 
		 
	}
	
	/**
	 * 校验验证码
	 * @param data
	 * @return
	 */
	@Expose
	public String checkVerifyCode(String data){
		
		 JSONObject obj = JSON.parseObject(data);
		 String verifyCode = obj.getString("verifyCode");
		 String mobilePhone = obj.getString("mobilePhone");
		 String code_jedis = null;
		 Jedis jedis = null;
		 try{
			 jedis = RedisPool.getJedisObject();
			 code_jedis = jedis.get(mobilePhone);
			 code_jedis = code_jedis!=null?code_jedis:"";
		 }catch(Exception e) {
			 e.printStackTrace();
			 log.error("redis处理异常！"+e+"-----checkVerifyCode");
		 }finally{
			 RedisPool.recycleJedisOjbect(jedis);
		 }
		 if(code_jedis.equals(verifyCode)){
			 return "{\"status\":\"1\",\"message\":\"验证码验证成功\"}";
		 }else{
			 return "{\"status\":\"0\",\"message\":\"验证失败：请重新验证\"}";
		 }
	}
	
	/**
	 * 验证token
	 * @param token
	 * @return
	 */
	@Expose
	public boolean checkToken(String token){
		if(service == null){
			service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
		}
		return service.checkToken(token);
	}
	
	/**
	 * 注册、校验验证码、验证是否业主、登录
	 * @param data
	 * @return
	 */
	@Expose
	public String ownerRegister(String data){
		JSONObject obj = JSON.parseObject(data);
		String mobilePhone = obj.getString("mobilePhone");
		String password = obj.getString("password");
		String verifyCode = obj.getString("verifyCode");
		String token = Security.getEncode(mobilePhone,password);
		String str = null; 
		Jedis jedis = null;
		try {
			jedis = RedisPool.getJedisObject();
			str = jedis.get(mobilePhone);
			str = str!=null?str:"";
			jedis.set(token, mobilePhone);
			jedis.expire(token, CommEnum.TOKEN_EXPIRE_TIME);
		}catch(Exception e){
			 e.printStackTrace();
			 log.error("redis处理异常！"+e+"-----ownerRegister");
		}finally{
			RedisPool.recycleJedisOjbect(jedis);
		}
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("mobilePhone", mobilePhone);
		if(str.equals(verifyCode)){
			if(service == null){
				service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
			}
			Collection<PropertyOwner> owners = service.queryOwnerInfoByParam(parameter);
			String ownerName = "";
			String ownerId = "";
			String ownerIdentity = "";
			if(owners.size()==1 && owners.size()>0){
				for(PropertyOwner owner:owners){
					if(owner.getPassword()!=null && !(owner.getPassword().equals(""))){
						return "{\"status\":\"2\",\"message\":\"注册失败：该号码已注册\"}";
					}
					owner.setPassword(password);
					ownerName = owner.getOwnerName();
					ownerId=owner.getOwnerId();
					owner.setOwnerIdentity("0");
					ownerIdentity = owner.getOwnerIdentity();
					service.registerOwner(owner,"update");
					/**
					 * 社区论坛用户
					 */
					BbsUser bbsUser = new BbsUser();
					bbsUser.setMappingUserId(ownerId);
					bbsUser.setUserType("1");
					bbsUser.setUserNickname("业主"+CommUtil.getRandomCharAndNum(4));
					if(bbsService == null){
						bbsService = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
					}
					bbsService.addBbsUser(bbsUser);
				}
				if(housePropertyService == null){
					housePropertyService = (HousePropertyService) SpringUtil.getObjectFromApplication("housePropertyServiceImpl");
				}
				List<VHouseOwner> list = housePropertyService.getVHouseOwnerByOwnerId(ownerId);
				
				List<Map> result = new ArrayList<Map>();
//				if(list.size() == 0) return "{\"status\":\"4\",\"message\":\"注册成功，您尚未收房，名下尚无绑定房产！\",\"token\":\""+token+"\"}";
				if(list.size()>0){
					for(int i=0;i<list.size();i++){
						VHouseOwner vho = list.get(i);
						Map map = new HashMap();
						map.put("roomId", vho.getRoomId());
						map.put("roomNo", vho.getRoomNo());
						map.put("communityId", vho.getCommunity_id());
						map.put("communityName", vho.getCommunity_name());
						map.put("allAreaId", vho.getBuild_id());
						map.put("allAreaName", vho.getBuild_name());
						map.put("buildId", vho.getBelongSbId());
						map.put("buildName", vho.getStoried_build_name());
						result.add(map);
					}
				}
				String rooms = JSON.toJSONString(result);
				return "{\"status\":\"3\",\"message\":\"注册成功\",\"token\":\""+token+"\"," +
				"\"data\":{\"ownerIdentity\":\""+ownerIdentity+"\",\"ownerId\":\""+ownerId+"\",\"ownerName\":\""+ownerName+"\",\"rooms\":"+rooms+"}}";
			}else{
				//邀请码  
				Collection<PropertyOwner> owners2 = service.queryOwnerInfoByParam(parameter,"0");
				if(owners2.size()>0){
					return "{\"status\":\"2\",\"message\":\"注册失败：该号码已注册\"}";
				}
				PropertyOwner owner = new PropertyOwner();
				owner.setPhone(mobilePhone);
				owner.setPassword(password);
				owner.setOwnerIdentity("3");
				service.registerOwner(owner,"save");
				ownerId = owner.getOwnerId();
				ownerIdentity = owner.getOwnerIdentity();
				/**
				 * 社区论坛用户
				 */
				BbsUser bbsUser = new BbsUser();
				bbsUser.setMappingUserId(ownerId);
				bbsUser.setUserType("1");
				bbsUser.setUserNickname("用户"+CommUtil.getRandomCharAndNum(4));
				if(bbsService == null){
					bbsService = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
				}
				bbsService.addBbsUser(bbsUser);
				return "{\"status\":\"3\",\"message\":\"注册成功\",\"token\":\""+token+"\",\"data\":{\"ownerIdentity\":\""+ownerIdentity+"\",\"ownerId\":\""+ownerId+"\"}}";
			}
			
		}else{
			return "{\"status\":\"0\",\"message\":\"验证失败：请重新验证\"}";

		}
	}
	
	
	/**
	 * 通过房产信息获取业主手机号及邀请码（非业主）
	 * @param data
	 * @return
	 */
	@Expose
	public String getOwnerPhone(String data){
		JSONObject obj = JSON.parseObject(data);
		String ownerPropertyId = obj.getString("ownerPropertyId");
		Map<String,String> parameter = new HashMap<String,String>();
		if(service == null){
			service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
		}
		parameter.put("roomId", ownerPropertyId);
		List list = service.queryOwnerByRoomId(parameter);
		String mobilePhone = "";
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] ob = (Object[]) list.get(i);
				mobilePhone = (String) ob[10];
			}
			return "{\"status\":\"1\",\"message\":\"\",\"mobilePhone\":\""+mobilePhone+"\"}";
		}else{
			return "{\"status\":\"0\",\"message\":\"获取失败：该房产未有业主\"}";
		}
		
	}
	
	
	/**
	 * 重置密码
	 * @param data
	 * @return
	 */
	@Expose
	public String resetPassword(String data){
		JSONObject obj = JSON.parseObject(data);
		String mobilePhone = obj.getString("mobilePhone");
		String password = obj.getString("password");
		if(service == null){
			service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
		}
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("mobilePhone", mobilePhone);
		Collection<PropertyOwner> owners = service.queryOwnerInfoByParam(parameter,"0");
		
		if(owners.size()==1 && owners.size()>0){
			for(PropertyOwner owner:owners){
				if(owner.getPassword().isEmpty() || owner.getPassword()==null){
					return "{\"status\":\"0\",\"message\":\"修改失败：请先注册！\"}";
				}
				owner.setPassword(password);
				service.registerOwner(owner,"update");
			}
			String token = Security.getEncode(mobilePhone,password);
			Jedis jedis = null;
			try{
				jedis = RedisPool.getJedisObject();
				jedis.set(token, mobilePhone);
				jedis.expire(token, CommEnum.TOKEN_EXPIRE_TIME);
			}catch(Exception e){
				e.printStackTrace();
				log.error("redis处理异常！"+e+"-----resetPassword");
			}finally{
				RedisPool.recycleJedisOjbect(jedis);
			}
			return "{\"status\":\"1\",\"message\":\"登陆成功\",\"token\":\""+token+"\"}";
		}else if(owners.size()==0){
			return "{\"status\":\"0\",\"message\":\"获取失败：该用户未注册\"}";
		}
		return "";
	}
	
	/**
	 * 验证业主邀请码
	 * @param data
	 * @return
	 */
	@Expose
	public String verifyOwnerCode(String data){
		JSONObject obj = JSON.parseObject(data);
		Jedis jedis = null;
		String mobilePhone = obj.getString("mobilePhone"); //业主手机
		String verifyCode = obj.getString("verifyCode");
		String regPhone = obj.getString("regPhone");			   //注册手机
		String roomId = obj.getString("roomId");
		String j_verifyCode = null;
		if(service == null){
			service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
		}
		if(lzmhService == null){
			lzmhService = (LzmhService) SpringUtil.getObjectFromApplication("lzmhServiceImpl");
		}
		//验证用户是否绑定同一套房产
		List list = service.getOwnerInfo(regPhone);
		for(int i=0;i<list.size();i++){
			Map map = new HashMap();
			Object[] ob = (Object[]) list.get(i);
			if(ob[4] != null && ob[4].equals(roomId)){
				return "{\"status\":\"0\",\"message\":\"您已绑定该房产，不可重复绑定！\"}";
			}
		}
		try{
			jedis = RedisPool.getJedisObject();
			j_verifyCode = jedis.get(mobilePhone);
		}catch(Exception e){
			e.printStackTrace();
			log.error("redis处理异常！"+e+"---------verifyOwnerCode");
		}finally{
			RedisPool.recycleJedisOjbect(jedis);
		}
		j_verifyCode = j_verifyCode!=null?j_verifyCode:"";
		if(j_verifyCode.equals(verifyCode)){
			Map<String,Object> parameter = new HashMap<String,Object>();
			
			parameter.put("mobilePhone", mobilePhone);
			Collection<PropertyOwner> owners = service.queryOwnerInfoByParam(parameter);
			String ownerName="";
			String ownerId="";
			
			if(owners.size()==1 && owners.size()>0){
				for(PropertyOwner owner:owners){
					ownerName = owner.getOwnerName();
					ownerId=owner.getOwnerId();
				}
				PropertyOwner po = service.queryOwnerByPhone(regPhone);
				po.setOwnerIdentity("1");
				po.setParentId(ownerId);
				service.registerOwner(po,"update");
				HouseOwner ho = new HouseOwner();
				ho.setOwnerId(po.getOwnerId());
				ho.setRoomId(roomId);
				ho.setDefaultMark("0");
				ho.setBandingMark("1");
				if(housePropertyService == null){
					housePropertyService = (HousePropertyService) SpringUtil.getObjectFromApplication("housePropertyServiceImpl");
				}
				housePropertyService.addHouseOwner(ho);
				
				System.out.print("2018-05-05:test-1----------->"+JSON.toJSONString(ho));
				
				lzmhService.addOwnerInfo(ho);

				System.out.print("2018-05-05:test-2----------->"+JSON.toJSONString(ho));
				VHouseProperty vhp = housePropertyService.getVHousePropertyById(roomId);
				return "{\"status\":\"3\",\"message\":\"验证成功！\"," +
				"\"data\":{\"ownerName\":\""+ownerName+"\",\"roomId\":\""+roomId+"\",\"roomNo\":\""+vhp.getRoomNo()+"\"," +
				"\"communityId\":\""+vhp.getCommunity_id()+"\",\"communityName\":\""+vhp.getCommunity_name()+"\"," +
				"\"allAreaId\":\""+vhp.getBuild_id()+"\",\"allAreaName\":\""+vhp.getBuild_name()+"\"," +
				"\"buildId\":\""+vhp.getBelongSbId()+"\",\"buildName\":\""+vhp.getStoried_build_name()+"\"}}";
			}else{
				return "{\"status\":\"0\",\"message\":\"获取失败：未查到房间ID\"}";
			}
			
		}else{
			return "{\"status\":\"0\",\"message\":\"验证失败：请输入正确邀请码\"}";
		}
	}
	/**
	 * 确认绑定业主地址
	 * @param data
	 * @return
	 */
	public String confirmAddress(String data){
		JSONObject obj = JSON.parseObject(data);
		String mobilePhone = obj.getString("mobilePhone");
		String roomId = obj.getString("roomId");
		Map<String,Object> parameter = new HashMap<String,Object>();
		if(service == null){
			service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
		}
		parameter.put("mobilePhone", mobilePhone);
		List<PropertyOwner> owners = (List<PropertyOwner>) service.queryOwnerInfoByParam(parameter);
		if(owners.size()>0 && owners.size()==1){
			PropertyOwner owner = owners.get(0);
//			owner.setRoomId(roomId);
			service.addPropertyOwner(owner);
			return "{\"status\":\"1\",\"message\":\"绑定成功！\"}";
		}else{
			return "{\"status\":\"0\",\"message\":\"绑定失败：用户未注册！\"}";
		}
	}
	/**
	 * 确认业主默认地址
	 * @param data
	 * @return
	 */
	public String confirmDefaultAddress(String data){
		JSONObject obj = JSON.parseObject(data);
		String roomId = obj.getString("roomId");
		String ownerId = obj.getString("ownerId");
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("ownerId", ownerId);
		if(housePropertyService == null){
			housePropertyService = (HousePropertyService) SpringUtil.getObjectFromApplication("housePropertyServiceImpl");
		}
		List<HouseOwner> list = housePropertyService.queryHouseOwner(parameter);
		HouseOwner ho = new HouseOwner();
		if(list.size() == 0){
			return "{\"status\":\"0\",\"message\":\"绑定失败！业主或房间不存在\"}";
		}else{
			for(int i=0;i<list.size();i++){
				ho = list.get(i);
				if(ho.getDefaultMark().equals("1")){
					ho.setDefaultMark("0");
					housePropertyService.addHouseOwner(ho);
				}
				if(roomId.equals(ho.getRoomId())){
					ho.setDefaultMark("1");
					housePropertyService.addHouseOwner(ho);
				}
			}
		}
		return "{\"status\":\"1\",\"message\":\"绑定成功！\"}";
	}
	
	/**
	 * 选取业主地址
	 * @param data
	 * @return
	 */
	@Expose
	public String getAllArea(String data){
		Map<String,Object> param = new HashMap<String,Object>();
		if(allAreaService == null){
			allAreaService = (AllAreaService) SpringUtil.getObjectFromApplication("allAreaServiceImpl");
		}
		List<AllArea> allAreas = (List<AllArea>) allAreaService.queryAllAreaByParam(param);
		String jsonString = JSON.toJSONString(allAreas);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}
	
	@Expose
	public String getCommunity(String data){
		JSONObject obj = JSON.parseObject(data);
		String allAreaId = obj.getString("allAreaId");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("buildId", allAreaId);
		if(areaPropertyService == null){
			areaPropertyService = (AreaPropertyService) SpringUtil.getObjectFromApplication("areaPropertyServiceImpl");
		}
		List<AreaProperty> areaProperty = (List<AreaProperty>) areaPropertyService.queryAreaPropertyByParam(param);
		String jsonString = JSON.toJSONString(areaProperty);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}
	@Expose
	public String getBuilding(String data){
		JSONObject obj = JSON.parseObject(data);
		String communityId = obj.getString("communityId");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("communityId", communityId);
		if(buildingPropertyService == null){
			buildingPropertyService = (BuildingPropertyService) SpringUtil.getObjectFromApplication("buildingPropertyServiceImpl");
		}
		List<BuildingProperty> buildingProperty = (List<BuildingProperty>) buildingPropertyService.queryBuildingPropertyByParam(param);
		String jsonString = JSON.toJSONString(buildingProperty);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}
	@Expose
	public String getRoom(String data){
		JSONObject obj = JSON.parseObject(data);
		String storiedBuildId = obj.getString("storiedBuildId");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("storiedBuildId", storiedBuildId);
		if(housePropertyService == null){
			housePropertyService = (HousePropertyService) SpringUtil.getObjectFromApplication("housePropertyServiceImpl");
		}
		List<HouseProperty> houseProperty = (List<HouseProperty>) housePropertyService.queryHousePropertyByParam(param);
		List list = new ArrayList();
		String unitName  = "";
		String roomNo = "";

		for(int i=0;i<houseProperty.size();i++){
			Map map = new HashMap();
			StringBuffer buf=new StringBuffer();
			unitName  = houseProperty.get(i).getUnitName();
			roomNo =houseProperty.get(i).getRoomNo();
			if(unitName.contains("单元")){
				buf.append(unitName);
				buf.append("-");
				buf.append(roomNo);
			}else {
				buf.append(roomNo);
			}

			map.put("belongSbId", houseProperty.get(i).getBelongSbId());
			map.put("roomId", houseProperty.get(i).getRoomId());
			map.put("roomNo",buf.toString());
			list.add(map);
		}
		String jsonString = JSON.toJSONString(list);
		return "{\"status\":\"1\",\"message\":\"\",\"data\":"+jsonString+"}";
	}

	@Expose
	public String ownerInfoUpdate(String data){
		String str = "";
		try{
			str = URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
				
		}
		JSONObject obj = JSON.parseObject(str);
		String mobilePhone = obj.getString("mobilePhone");
		String ownerHeadImg = obj.getString("ownerHeadImg");
		String ownerName = obj.getString("ownerName");
		String ownerNickName = obj.getString("ownerNickName");
		if(service == null){
			service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
		}
		PropertyOwner owner = service.queryOwnerByPhone(mobilePhone);
		if(owner!=null){
			if(ownerName!=null && !ownerName.isEmpty()){
				owner.setOwnerName(ownerName);
			}
			if(ownerHeadImg!=null && !ownerHeadImg.isEmpty()){
				owner.setOwnerHeadImg(ownerHeadImg);
			}
			service.addPropertyOwner(owner);
			if(bbsService == null){
				bbsService = (BbsService) SpringUtil.getObjectFromApplication("bbsServiceImpl");
			}
			BbsUser bbsUser = bbsService.getBbsUserByPhone(owner.getOwnerId());
			if(bbsUser!=null && ownerNickName!=null && !ownerNickName.isEmpty()){
				bbsUser.setUserNickname(ownerNickName);
				bbsService.addBbsUser(bbsUser);
			}
			return "{\"status\":\"1\",\"message\":\"修改成功\"}";
		}else{
			return "{\"status\":\"0\",\"message\":\"修改失败\"}";
		}
	}
	
	public String getMySettingPage(String data){
		JSONObject obj = JSON.parseObject(data);
		String phone = obj.getString("phone");
		JSONArray roomIds = obj.getJSONArray("roomId");
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("phone", phone);
		para.put("roomIds", roomIds);
		if(service == null){
			service = (OwnerInfoService) SpringUtil.getObjectFromApplication("ownerInfoServiceImpl");
		}
		List<BigInteger> list = service.getMySettingPage(para);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("compNum", list.get(0).intValue());
		map.put("repairNum", list.get(1).intValue());
		map.put("houseWorkNum", list.get(2).intValue());
		map.put("chargeNum", list.get(3).intValue());
		String jsonString = JSON.toJSONString(map);
		return "{\"status\":\"1\",\"message\":\"成功\",\"data\":"+jsonString+"}";
	}
	
}

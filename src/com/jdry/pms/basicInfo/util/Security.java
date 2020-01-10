package com.jdry.pms.basicInfo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdry.pms.comm.util.RedisPool;
import com.jdry.pms.servlet.pojo.CustmerInfo;
/**
 * token操作类
 * @author hezuping
 *
 */

public class Security {
	
	/**
	 * 加密
	 * @param str
	 * @return
	 */
	
	static Logger log=Logger.getLogger(Security.class);
	public static String EncodePwd(String str) {
		try {
			return TokenTool.getEncryptedPwd(str);
		} catch (Exception e) {
			return MD5Encode(str);
		} 
	}
	
	
	/**
	 * 验证
	 * @param password
	 * @param passwordInDb
	 * @return
	 */
	public static boolean ValidPasswordPwd(String password,String passwordInDb) {
		try {
			
			return TokenTool.validPassword(password, passwordInDb);
		} catch (Exception e) {
			return false;
		}
	}

	
	/**
	 * MD5 加密
	 */
	public static String MD5Encode(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString().toUpperCase();
	}
	
	/**
	 * 验证首次登录包体和方法名是否合法
	 * @param clsName
	 * @param methodName
	 * @return
	 */
	public static boolean validInfo(String clsName,String methodName)
	{
		boolean isValid=false;
		String va=clsName+"."+methodName;
		if(va.equals("com.jdry.pms.basicInfo.view.OwnerInfoView.ownerLogin")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.basicInfo.view.OwnerInfoView.getVerifyCode")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.basicInfo.view.OwnerInfoView.ownerRegister")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.basicInfo.view.OwnerInfoView.checkVerifyCode")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.basicInfo.view.OwnerInfoView.pwdGetVerifyCode")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.basicInfo.view.OwnerInfoView.resetPassword")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.basicInfo.view.ProManagerView.managerLogin")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.gis.app.SendUserLocationInfo.insertUserLocation")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.appversion.UpdateAppVersion.getWyAppVersion")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.wechatToApp.service.impl.WechatToAppServiceImpl.getMatterInfoForApp")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.patrolPlan.service.Impl.PatrolNodeRecServiceImpl.getPatrolNodeRecForGis")){
			isValid=true;
		}else if(va.equals("com.jdry.pms.gis.app.SendUserLocationInfo.getRefreshTime")){
			isValid=true;
		}
		return isValid;
	}
	
	/**
	 * //首次登录返回token
	 * @param phone
	 * @param pwd
	 * @return
	 */
	
		public static String getEncode(String phone,String pwd)
		{
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date d=new Date();
			String key=sd.format(d)+"|"+phone+"|"+pwd+"|"+"pms";
			return EncodePwd(key);
		}
	   //获取session里面值
		public static String getReEncode(String phone,String pwd)
		{
			String key=phone+"|"+pwd+"|"+"pms";
			return key;
		}
	
		//获取当前时间
		public static String getCurrentTime()
		{
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH");
			Date d=new Date();
			return sd.format(d);
		}
	  //获取未来一个小时
		public static String getPreTime()
		{
			
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH");
			Date d1=new Date();
			d1.setHours(d1.getHours()+1);
			return sd.format(d1);
			
		}
		/**
		 * 进行校验
		 * @param token
		 * @param key
		 * @return
		 */
		public static boolean ValidPas(String token,String key)
		{
			String value=getCurrentTime()+"|"+key;
			String value2=getPreTime()+"|"+key;
			System.out.println(ValidPasswordPwd(value, token));
			System.out.println(ValidPasswordPwd(value2, token));
			System.out.println(ValidPasswordPwd(value, token)|| Security.ValidPasswordPwd(value2, token));
			return Security.ValidPasswordPwd(value, token)|| Security.ValidPasswordPwd(value2, token);
		}
	    
		/**
		 * 解析用户
		 * @param json
		 * @return
		 */
		public static CustmerInfo getCustInfo(String json)
		{
			JSONObject obj = JSON.parseObject(json);
			String username=null==obj.getString("username")?"":obj.getString("username");
			String password=null==obj.getString("password")?"":obj.getString("password");
			CustmerInfo cus=new CustmerInfo();
			cus.setUsername(username);
			cus.setPassword(password);
			return cus;
		}
		
		
		public static boolean checkToken(String token) throws Exception{
			Jedis jedis = null;
			boolean flag = false;
			try {
				jedis = RedisPool.getJedisObject();
				if(null!=jedis.get(token)){
					flag = true;
				}
			} catch (Exception e) {
				log.error("redis处理异常！"+e.getMessage()+"-----checkToken");
				e.printStackTrace();
			}finally{
				RedisPool.recycleJedisOjbect(jedis);
			}
			return flag;
		}
		
	
	   public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//安全的加密算法，每次生成结果不一致
				String str = "2016-03-16 01|15285630464|111111|pms";//服务端
				String encryptResult =EncodePwd(str);
				String encryptResult2 =EncodePwd(str);
				System.out.println(encryptResult);
				System.out.println(encryptResult2);
				System.out.println(ValidPasswordPwd(str, "F9FDFC968D495173CD612DC5BED36C09F37D519F07FB006831DDA590"));

				 String jsonText=JSON.toJSONString("1");
				System.out.println(jsonText);
	}

}

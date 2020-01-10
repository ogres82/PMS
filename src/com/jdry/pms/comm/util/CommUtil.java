package com.jdry.pms.comm.util;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jdry.pms.comm.service.BusinessService;

@Component
public class CommUtil {
	@Resource
	BusinessService businessService;

	/**生成业务单号
	 * 
	 * @ClassName: @param key 类型--sf，qq，fb，xxx
	 * @ClassName: @param type Y-年，M-年月，D-年月日
	 * @ClassName: @return 
	 * @Description: TODO(这里用一句话描述这个类的作用) 
	 * @author 追梦de蜗牛 yongqiang347@163.com 
	 * @date 2016-1-4 上午11:41:54
	 */
	public String getBusinessId(String key,String type){
//		businessService = new BusinessServiceImpl();
		return businessService.getBusinessId(key,type);
	}
	
	
	/**生成业务单号
	 * 
	 * @ClassName: @param key 类型--sf，qq，fb，xx
	 * @ClassName: @param type Y-年，M-年月，D-年月日
	 * @ClassName: @return 
	 * @Description: TODO(这里用一句话描述这个类的作用) 
	 * @author 追梦de蜗牛 yongqiang347@163.com 
	 * @date 2016-1-4 上午11:41:54
	 */
	public String getBusinessId(String key){
		
		return getBusinessId(key,null);
	}
	
	
	/**生成GUUID
	 * 
	 * @ClassName: @return 
	 * @Description: TODO(这里用一句话描述这个类的作用) 
	 * @author 追梦de蜗牛 yongqiang347@163.com 
	 * @date 2016-1-4 上午11:44:16
	 */
	public static String getGuuid(){
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(); 
	}
	
	/** 
	 * 获取随机字母数字组合 
	 *  
	 * @param length 
	 *            字符串长度 
	 * @return 
	 */  
	public static String getRandomCharAndNum(Integer length) {  
	    String str = "";  
	    Random random = new Random();  
	    for (int i = 0; i < length; i++) {  
	        boolean b = random.nextBoolean();  
	        if (b) { // 字符串  
	            //int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母  
	            str += (char) (65 + random.nextInt(26));// 取得大写字母  
	        } else { // 数字  
	            str += String.valueOf(random.nextInt(10));  
	        }  
	    }  
	    return str;  
	}  

}

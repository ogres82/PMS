package com.jdry.pms.comm.util;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;

@Component
public class CommUser {

	/**得到登录人
	 * 
	 * @return
	 */
	
	public static String getUserName(){
		return ContextHolder.getLoginUser().getUsername();
	}
	
	/**得到用户信息
	 * 
	 * @return
	 */
	public static IUser getUser(){
		return ContextHolder.getLoginUser();
	}
}

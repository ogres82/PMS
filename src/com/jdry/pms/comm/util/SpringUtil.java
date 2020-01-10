package com.jdry.pms.comm.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bstek.dorado.web.DoradoContext;

public class SpringUtil {
	
	public static Object getObjectFromApplication(String beanName) {
		
		DoradoContext application1 = DoradoContext.getCurrent();
		
		// 通过WebApplicationContextUtils 得到Spring容器的实例。
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(application1.getServletContext());
		// 返回Bean的实例。
		return applicationContext.getBean(beanName);
	}

}

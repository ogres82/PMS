package com.jdry.pms.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;

public class LogFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req=(HttpServletRequest)request; 
		HttpSession session= req.getSession(); 
//		System.out.println("req.getContextPath()=  "+req.getContextPath());  
//        System.out.println("req.getRequestURI()=  "+req.getRequestURI());  
//        System.out.println("req.getRequestURL()=  "+req.getRequestURL());  
//        System.out.println("req.getServletPath()   "+req.getServletPath());  
		if (session==null){ 
			
		}else{ 
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(user != null){
				MDC.put("user_id", user.getUsername()); 
				
			}else{
				MDC.put("user_id", "system/app");
			}
		}
		chain.doFilter(request, response); 
//		clearMDC();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	private void clearMDC() {  
        Map map = MDC.getContext();  
        if(map != null) {  
            map.clear();  
        }  
    }
}

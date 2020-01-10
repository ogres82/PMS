package com.jdry.pms.filter;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.MDC;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.aop.IMethodInterceptor;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.view.user.UserMaintain;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.jdry.pms.bbs.pojo.BbsUser;
import com.jdry.pms.bbs.service.BbsService;
import com.jdry.pms.comm.util.CommUtil;
@Component
public class ConfigInterceptor implements IMethodInterceptor{
	@Resource
	BbsService bbsService;
	@Override
	public void doAfter(Class<?> objectClass, Method method, Object[] obj, Object arg3)
			throws Exception {
		// TODO Auto-generated method stub
		if(objectClass.getName().equals(UserMaintain.class.getName())){
			if(method.getName().equals("saveUsers")){
				List li = (List) obj[0];
				DefaultUser user = (DefaultUser) li.get(0);
				EntityState state = EntityUtils.getState(user);
				if(state.equals(EntityState.NEW)){
					BbsUser bbsUser = new BbsUser();
					bbsUser.setMappingUserId(user.getUsername());
					bbsUser.setUserType("1");
					bbsUser.setUserNickname("乐湾物业"+CommUtil.getRandomCharAndNum(4));
					bbsService.addBbsUser(bbsUser);
				}else if(state.equals(EntityState.DELETED)){
					
				}
			}
		}
	}

	@Override
	public void doBefore(Class<?> arg0, Method method, Object[] arg2)
			throws Exception {
		// TODO Auto-generated method stub
		DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
		if(user != null){
			MDC.put("user_id", user.getUsername()); 
		}else{
			MDC.put("user_id", "system/app");
		}
	}

	@Override
	public boolean support(Class<?> objectClass, Method method) {
		// TODO Auto-generated method stub
		if(method.getName().equals("getUrl") || method.getName().equals("anonymousAccess")){
            return false;
        }
        return true;
	}

}

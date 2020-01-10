package com.jdry.pms.mainFrame.view;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.view.url.UrlMaintain;
import com.bstek.dorado.annotation.DataProvider;
import com.jdry.pms.comm.util.CommUser;

@Component
public class IndexView {

	@Resource
	CommUser user;
	
	@Resource
	UrlMaintain url;
	
	@DataProvider
	public List<Url> loadUrls(String parentId) throws Exception {
		
		return  (List<Url>) url.loadUrls(parentId);
	}
	
	@DataProvider
	public IUser getCurrentUserInfo() {
		@SuppressWarnings("static-access")
		IUser iuser = CommUser.getUser();
		return iuser;
	}
}

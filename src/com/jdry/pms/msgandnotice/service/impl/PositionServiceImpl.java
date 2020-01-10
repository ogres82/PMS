package com.jdry.pms.msgandnotice.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.UserDept;
import com.bstek.bdf2.core.model.UserPosition;
import com.bstek.dorado.annotation.DataProvider;
import com.jdry.pms.comm.util.FileUtil;
import com.jdry.pms.msgandnotice.dao.PositionDaoImpl;
import com.jdry.pms.msgandnotice.service.PositionService;
@Repository
@Component
public class PositionServiceImpl implements PositionService{
	@Resource
	PositionDaoImpl poitionDao;
	@Override
	@DataProvider
	public Collection<DefaultPosition> findPosition() 
			throws Exception {
		
		return poitionDao.findPosition();
	}
	@Override
	public DefaultPosition findPositionById(String posId)
			throws Exception {
		// TODO Auto-generated method stub
		DefaultPosition defaultPosition=poitionDao.findPositionById(posId);
		return defaultPosition;
	}
	@Override
	public UserPosition findUserByPoId(String posId) {
		UserPosition userPosition=poitionDao.findUserByPoId(posId);
		return userPosition;
	}
	@Override
	//获取userPositionList 通过posId
	public List<UserPosition> findUserPositionsByPoId(String posId) {
		List<UserPosition> userPositionList=new ArrayList<UserPosition>();
		Map paraMap=new HashMap();
		
		paraMap.put("positionId", posId);
		try{
	       String sql="from "+UserPosition.class.getName()+
	    		" up where 1=1 and up.positionId=:positionId";
	       userPositionList=poitionDao.findUserPositionsByPoId(sql,paraMap);
	     } catch (Exception e) {
		   e.printStackTrace();
	    }
		return userPositionList;
	}
	//获取资源文件的内容
	public static Properties getProperty() {
		Properties property = new Properties();
		try {
			property = FileUtil
					.getProperties("com/jdry/pms/msgandnotice/ApplicationResources.properties");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return property;
	}
	@Override
	public UserDept findUserDeptByUserId(String userId) {
		UserDept userDept=poitionDao.findUserDeptByUserId(userId);
		return userDept;
	}

}

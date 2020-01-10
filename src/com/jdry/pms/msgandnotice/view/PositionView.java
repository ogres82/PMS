package com.jdry.pms.msgandnotice.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.UserPosition;
import com.bstek.dorado.annotation.DataProvider;
import com.jdry.pms.msgandnotice.service.PositionService;
import com.jdry.pms.msgandnotice.service.UserPositionService;

@Component
public class PositionView {
	@Resource
	PositionService positionService;
	@Resource
	UserPositionService userPositionService;
	@DataProvider
	public Collection<DefaultPosition> findPosition(){
		List<DefaultPosition> result=new ArrayList<DefaultPosition>();
		try {
			result =(List<DefaultPosition>) positionService.findPosition();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@DataProvider
	public Map<String,String> findMapPosition(){
		Map<String,String> rtnResult=new LinkedHashMap<String,String>();
		try {
			List<UserPosition> result=new ArrayList<UserPosition>();
			result =(List<UserPosition>) userPositionService.findUserPosition();
		    for(UserPosition position:result){
		    	DefaultPosition defaultPosition=positionService.findPositionById(position.getPositionId());
		    	rtnResult.put(position.getUsername(),defaultPosition.getName());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnResult;
	}
}

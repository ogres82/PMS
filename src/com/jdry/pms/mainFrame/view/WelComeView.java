package com.jdry.pms.mainFrame.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.annotation.DataProvider;

@Component
public class WelComeView extends HibernateDao{

	@DataProvider
	public Collection<Map<String,String>> queryRemind(){
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		
		String[] projects = {"应收到期","费用到期","客户合同","车位合同","租赁合同","房间客户生日","租赁客户生日"};
		for(int i=0;i<7;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("remindProject", projects[i]+":");
			map.put("remindDay", "今天");
			map.put("remindDayNum", ""+(int)(Math.random()*100)+"");
			map.put("remindMonth", "本月");
			map.put("remindMonthNum", ""+(int)(Math.random()*100)+"");
			results.add(map);
		}
		return results;
	}
}

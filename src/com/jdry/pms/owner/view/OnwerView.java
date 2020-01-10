package com.jdry.pms.owner.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;



import com.bstek.dorado.annotation.DataProvider;
import com.jdry.pms.owner.pojo.OwnerEntity;
import com.jdry.pms.owner.service.OnwnerService;



@Component("onwerView")
public class OnwerView {
	
	@Resource
	private OnwnerService service;
	@SuppressWarnings("unchecked")
	@DataProvider
	public Collection<OwnerEntity> findOnwerInfo(Map<String,Object> parameter)
	{
		System.out.println("-------------");
        List ls =service.findOnwerInfo(parameter);
        List list=new ArrayList();
		for(int i=0;i<ls.size();i++)
		{
            Map map = new HashMap();
			Object[] obj = (Object[])ls.get(i);
			String ownerName = null == obj[0]?"":obj[0].toString();
			map.put("ownerName", ownerName);
			String sex = null == obj[1]?"":obj[1].toString();
			map.put("sex", sex);
			String address = null == obj[2]?"":obj[2].toString();
			map.put("address", address);
			String phone = null == obj[3]?"":obj[3].toString();
			map.put("phone", phone);
			String flower = null == obj[4]?"":obj[4].toString();
			map.put("flower", flower);
			String home = null == obj[5]?"":obj[5].toString();
			map.put("home", home);
			
			String other = null == obj[6]?"":obj[6].toString();
			map.put("other", other);
			list.add(map);
		}
        return list;
	}
	
	
}

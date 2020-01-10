package com.jdry.pms.chargeManager.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeArrearViewEntity;


@Repository
public class ChargeArrearViewDao extends HibernateDao {

	//参数type的值可取01未收，02已收，03欠费，用于前台不同的页面
	public void queryAll(Page<ChargeArrearViewEntity> page,Map<String, Object> parameter) {
		
		String hql = " from "+ChargeArrearViewEntity.class.getName();
		
			
		String whereCase = " a where 1=1 " ;
		String sqlCount="select count(*) from "+ChargeArrearViewEntity.class.getName()+" b where 1=1 ";
		Map<String,Object> map =new HashMap<String,Object>();
		 
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			String communityNameSearch = parameter.get("communityNameSearch")==null?"":parameter.get("communityNameSearch").toString();
			String chargeArrearNum = parameter.get("chargeArrearNum")==null?"":parameter.get("chargeArrearNum").toString();
			
			if(!search.equals("")){
				whereCase += " and (a.owner_name like:owner_name "+
							 " or a.room_no like:room_no) ";
				sqlCount += " and (b.owner_name like:owner_name "+
						    " or b.room_no like:room_no) ";
				map.put("owner_name", "%"+search+"%");
				map.put("room_no", "%"+search+"%");
			}
			if(!chargeArrearNum.equals("")){
				whereCase += " and TIMESTAMPDIFF(MONTH,begin_time,end_time)>=:chargeArrearNum ";
				sqlCount += " and TIMESTAMPDIFF(MONTH,begin_time,end_time)>=:chargeArrearNum ";
				map.put("chargeArrearNum",chargeArrearNum);
			}
			if(!communityNameSearch.equals("")){
				whereCase += " and community_name=:communityNameSearch ";
				sqlCount += " and community_name=:communityNameSearch ";
				map.put("communityNameSearch",communityNameSearch);
			}
		}
		
		hql += whereCase;
		hql += " ORDER BY begin_time DESC";
		//sqlCount += " ORDER BY begin_time DESC";
				
		System.out.println("hql==="+hql);
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//返回给其他模块用
	public List<ChargeArrearViewEntity> queryAll() {			
		String hql = " from "+ChargeArrearViewEntity.class.getName();			
		List<ChargeArrearViewEntity> list = this.query(hql);
		return list;
	}

		
}

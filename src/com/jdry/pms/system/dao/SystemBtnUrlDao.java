package com.jdry.pms.system.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.system.pojo.SystemBtnUrl;
import com.jdry.pms.system.pojo.VSystemBtnUrl;

@Repository
@Transactional
public class SystemBtnUrlDao extends HibernateDao{

	public void querySystemBtnUrlByPage(Page<VSystemBtnUrl> page,
			Map<String, Object> parameter) {
		String hql = " from "+VSystemBtnUrl.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VSystemBtnUrl.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.pageName like:pageName "+
							 " or a.btnName like:btnName";
				sqlCount += " and b.pageName like:pageName "+
							 " or b.btnName like:btnName";
						    
				map.put("pageName", "%"+search+"%");
				map.put("btnName", "%"+search+"%");
			}
		}		
		hql += whereCase;				
		System.out.println("hql==="+hql);
		
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void saveSystemBtnUrl(SystemBtnUrl btnUrl) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(btnUrl);
		session.flush();
		session.close();
	}

	public void deleteSystemBtnUrl(String id) {
		Session session = this.getSessionFactory().openSession();
		SystemBtnUrl sbu = getSystemBtnUrlById(id);
		if(null != sbu){
			session.delete(sbu);
		}
		session.flush();
		session.close();
	}
	
	public SystemBtnUrl getSystemBtnUrlById(String id) {
		List<SystemBtnUrl> lists = this
				.query("from " + SystemBtnUrl.class.getName()
						+ " where id='" + id + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		}else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> getPageList(Map<String, Object> param) {
		String keyword = param.get("keyword")!=null?param.get("keyword").toString():"";
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Session session = this.getSessionFactory().openSession();
		String sql = "select t.ID_,t.NAME_,t.URL_ from bdf2_url t where t.URL_ is not null and t.URL_ !='' and t.PARENT_ID_ is not null and t.PARENT_ID_ !=''";
		if(!"".equals(keyword)){
			sql+=" and t.NAME_ like '%"+keyword+"%'";
		}
		List list = session.createSQLQuery(sql).list();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = new HashMap<String,Object>();
			Object[] obj = (Object[]) list.get(i);
			map.put("urlId", obj[0]==null?"":obj[0].toString());
			map.put("pageName", obj[1]==null?"":obj[1].toString());
			map.put("urlName", obj[2]==null?"":obj[2].toString());
			result.add(map);
		}
		return result;
	}

}

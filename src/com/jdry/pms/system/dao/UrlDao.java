package com.jdry.pms.system.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.RoleMember;
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.comm.service.BusinessService;
import com.jdry.pms.comm.util.CommUser;
@Repository
@Transactional
public class UrlDao extends HibernateDao{
	
	@Resource
	BusinessService bService;
	
	public int countChildren(String parentId){
		String hql = "select count(*) from " + Url.class.getName() + " d where d.parentId = :parentId";
	    Map parameterMap = new HashMap();
	    parameterMap.put("parentId", parentId);
	    return queryForInt(hql, parameterMap);
	}
	public void saveUrl(Url url){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(url);
		session.flush();
		session.close();
	}
	
	public void deleteUrl(String id){
		Url url = getUrlById(id);
		Session session = this.getSessionFactory().openSession();
		session.delete(url);
		session.flush();
		session.close();
	}
	
	public Url getUrlById(String id){
		String hql = "from Url where id = '"+id+"'";
		return (Url) this.query(hql).get(0);
	}
	
	public List<Url> loadMeunUrls(String roleId) {
		if(roleId.isEmpty() || roleId==null){
			RoleMember rm = bService.getRoleMemberByUsername(CommUser.getUserName());
			roleId = rm.getRoleId();
		}
		String hql = " from "+Url.class.getName()+" where id in (select urlId from RoleResource where roleId = '"+roleId+"') order by order ";		
		System.out.println("hql==="+hql);
		
		try {
			List<Url> urls = this.query(hql);
			return buildMenuTree(urls);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Url> buildMenuTree(List<Url> urls) {
		Map<String, List<Url>> subUrl = new HashMap<String, List<Url>>();
		String rpId = "";
        for (Url r : urls) {
            rpId = r.getParentId()==null?"root":r.getParentId();
            List<Url> childResourceList = subUrl.get(rpId);
            if (childResourceList == null) {
                childResourceList = new LinkedList<Url>();
            }
            childResourceList.add(r);
            subUrl.put(rpId, childResourceList);

        }
        String rId = "";
        List<Url> urlTree = new ArrayList<Url>();
        for (Url r : urls) {
            rId = r.getId();
            r.setChildren(subUrl.get(rId));
            if (r.getParentId() == null) {
            	urlTree.add(r);
            }
        }
		
		return urlTree;
	}
	public List<Url> loadAllMeunUrls() {
		return this.query("from "+Url.class.getName());
	}
	public List<Url> loadRoleMeunUrls(String roleId) {
		String hql = " from "+Url.class.getName()+" where id in (select urlId from RoleResource where roleId = '"+roleId+"') order by order ";		
		System.out.println("hql==="+hql);
		return this.query(hql);
	}
	public List<Url> loadAllBuildMeunUrls() {
		String hql = "from "+Url.class.getName()+" order by order";
		List<Url> urls = this.query(hql);
		return buildMenuTree(urls);
	}
}

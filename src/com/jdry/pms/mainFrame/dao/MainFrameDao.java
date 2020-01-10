package com.jdry.pms.mainFrame.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.RoleMember;
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.comm.service.BusinessService;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.mainFrame.pojo.VMessage;

@Repository
@Transactional
public class MainFrameDao extends HibernateDao{

	@Resource
	BusinessService bService;
	
	public List<VMessage> queryReceiveMsgInfo() {

		String hql = " from "+VMessage.class.getName();		
		String where = " where 1=1 ";
		String whereCase = " a " + where;
		String username = CommUser.getUserName();
		Map<String,Object> map =new HashMap<String,Object>();	
		
		whereCase += " and a.receiver =:username ";
		map.put("username", username);
		hql += whereCase +" order by sendDate desc";				
		System.out.println("hql==="+hql);
		
		return this.query(hql, map);
	}
	
	public List<VMessage> queryHasSentMsgInfo() {

		String hql = " from "+VMessage.class.getName();		
		String where = " where 1=1 ";
		String whereCase = " a " + where;
		String username = CommUser.getUserName();
		Map<String,Object> map =new HashMap<String,Object>();	
		
		whereCase += " and a.sender =:username ";
		map.put("username", username);

		hql += whereCase+" order by sendDate desc";				
		System.out.println("hql==="+hql);
		
		return this.query(hql, map);
	}

	public List<Url> loadMeunUrls(String parentId) {
		
		RoleMember rm = bService.getRoleMemberByUsername(CommUser.getUserName());
		
		String hql = " from "+Url.class.getName()+" where id in (select urlId from RoleResource where roleId = '"+rm.getRoleId()+"') order by order ";		
		
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
	
}

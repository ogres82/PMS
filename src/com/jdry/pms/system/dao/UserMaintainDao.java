package com.jdry.pms.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.UserDept;
import com.bstek.bdf2.core.model.UserPosition;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.bdf2.core.service.IGroupService;
import com.bstek.bdf2.core.service.IRoleService;
import com.bstek.bdf2.core.service.MemberType;
import com.jdry.pms.system.pojo.VBdf2User;
import com.jdry.pms.system.pojo.DeptAndPos;

@Repository
@Transactional
public class UserMaintainDao extends HibernateDao {

	@Resource
	private IRoleService roleService;

	@Resource
	private IGroupService groupService;

	public List<VBdf2User> queryUserByParam(int rows) {
		Session session = this.getSessionFactory().openSession();
		String hql = " from " + VBdf2User.class.getName() + " where 1=1 ";

		Query query = session.createQuery(hql);
		if (rows > 0) {
			query.setMaxResults(rows);
		}

		List<VBdf2User> lists = query.list();
		if (null != lists && lists.size() > 0) {
			session.close();
			return lists;
		} else {
			session.close();
			return null;
		}
	}

	public DefaultUser getUserById(String username) {
		List<DefaultUser> lists = this.query("from " + DefaultUser.class.getName() + " where username='" + username + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void deleteUser(String username) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = username.split(",");

		for (int i = 0; i < ids.length; i++) {

			DefaultUser user = getUserById(ids[i]);
			if (null != user) {
				this.roleService.deleteRoleMemeber(user.getUsername(), MemberType.User);
				this.groupService.deleteGroupMemeber(user.getUsername(), MemberType.User);
				session.createQuery("delete " + UserPosition.class.getName() + " u where u.username = :username").setString("username", user.getUsername()).executeUpdate();
				session.createQuery("delete " + UserDept.class.getName() + " u where u.username = :username").setString("username", user.getUsername()).executeUpdate();
				session.delete(user);
			}
		}
		session.flush();
		session.close();
	}

	public void saveUser(DefaultUser duser) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(duser);
		session.flush();
		session.close();

	}

	public VBdf2User getVBdf2UserById(String username) {
		List<VBdf2User> lists = this.query("from " + VBdf2User.class.getName() + " where username='" + username + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public List<DeptAndPos> getDeptAndPos() {

		List<DeptAndPos> lists = this.query("from " + DeptAndPos.class.getName() + " where 1=1");
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getUserDetailById(String userName) {
		Session session = this.getSessionFactory().openSession();
		String sql = "select a.USERNAME_,a.CNAME_,a.MALE_,a.ADDRESS_,b.user_nickname from bdf2_user a " +
				"LEFT JOIN t_bbs_user b on a.USERNAME_ = b.mapping_user_id where a.USERNAME_ = '"+userName+"'";
		List list = session.createSQLQuery(sql).list();
		Map<String,Object> map = new HashMap<String,Object>();
		if(list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			map.put("userName", obj[0]);
			map.put("cname", obj[1]);
			map.put("male", obj[2].toString().equals("true")?"1":"0");
			map.put("headImg", obj[3]);
			map.put("nickName", obj[4]);
		}
		return map;
	}

}

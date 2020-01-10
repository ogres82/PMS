package com.jdry.pms.system.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.model.Role;
import com.bstek.bdf2.core.model.RoleResource;
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.system.pojo.RoleBtn;
import com.jdry.pms.system.pojo.UrlBtn;
import com.jdry.pms.system.pojo.VRoleBtn;
import com.jdry.pms.system.pojo.VRoleUrl;
@Repository
@Transactional
public class URLBtnDao extends HibernateDao{
	public List<Role> loadAllRoles(){
		String hql = "from Role";
		return this.query(hql);
	}

	public List<VRoleUrl> loadUrlByRole(Map<String,String> parameter){
		String roleId = parameter.get("roleId");
		String parentId = parameter.get("parentId");
		String hql = "from VRoleUrl where 1=1";
		if(!roleId.equals("") && roleId!=null){
			hql = hql+" and roleId='"+roleId+"'";
		}
		if(!parentId.equals("") && parentId!=null){
			hql = hql+" and parentId='"+parentId+"'";
		}
		hql = hql+" order by order asc";
		return this.query(hql);
	}
	
	public Url getUrl(String urlId){
		String hql = "from Url where id = '"+urlId+"'";
		return (Url) this.query(hql).get(0);
	}
	public List<Url> getUrlByParentId(String parentId){
		String hql = "";
		if(StringUtil.isEmpty(parentId)){
			hql = "from Url where parentId is null";
		}else{
			hql = "from Url where parentId = '"+parentId+"'";
		}
		return this.query(hql);
	}
	public List loadBtnByUrl(String urlId,String roleId){
//		String hql = "from UrlBtn where urlId='"+urlId+"'";
		String sql = "select * from (select b.id,a.url_id,a.btn_id,a.btn_name,a.order_,b.role_id from system_btn_url a left join ("+
		"select t.id,t.url_id,t.role_id,t.btn_id from system_role_btn t where t.role_id='"+roleId+"') b on a.url_id = b.url_id "+
		"and a.btn_id = b.btn_id) t where t.url_id = '"+urlId+"' order by t.order_ ASC";
		Session session = this.getSessionFactory().openSession();
		List list = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		return list;
	}
	
	public void saveRoleBtn(RoleBtn roleBtn){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(roleBtn);
		session.flush();
		session.close();
	}
	
	public void deleteRoleBtn(String id){
		Session session = this.getSessionFactory().openSession();
		RoleBtn roleBtn = getRoleBtnById(id);
		if(roleBtn != null){
			session.delete(roleBtn);
		}
		session.flush();
		session.close();
	}
	public RoleBtn getRoleBtnById(String id){
		String hql = "from RoleBtn where id = '"+id+"'";
		List li = this.query(hql);
		return (RoleBtn) this.query(hql).get(0);
	}
	public List<VRoleBtn> getAuthBtnByRole(String roleId,String url){
		String hql = "from VRoleBtn where roleId='"+roleId+"' and url='"+url+"' order by order";
		return this.query(hql);
	}
	/*
	 * 删除角色与url对应关系
	 */
	public void deleteRoleUrl(List<RoleResource> list){
		Session session = this.getSessionFactory().openSession();
		for(int i=0;i<list.size();i++){
			RoleResource rr = list.get(i);
			session.delete(rr);
		}
		session.flush();
		session.close();
	}
	public List<RoleResource> queryRoleUrlByUrlId(String urlId){
		String hql = "from RoleResource where urlId = '"+urlId+"'";
		return this.query(hql);
	}
	/*
	 * 删除按钮与url对应关系
	 */
	public void deleteUrlBtn(List<UrlBtn> list){
		Session session = this.getSessionFactory().openSession();
		for(int i=0;i<list.size();i++){
			UrlBtn ub = list.get(i);
			session.delete(ub);
		}
		session.flush();
		session.close();
	}
	public List<UrlBtn> queryUrlBtnByUrlId(String urlId){
		String hql = "from UrlBtn where urlId = '"+urlId+"'";
		return this.query(hql);
	}
	/*
	 * 删除角色、url、按钮对应关系
	 */
	public void deleteRoleBtn(List<RoleBtn> list){
		Session session = this.getSessionFactory().openSession();
		for(int i=0;i<list.size();i++){
			RoleBtn rb = list.get(i);
			session.delete(rb);
		}
		session.flush();
		session.close();
	}
	public List<RoleBtn> queryRoleBtnUrlId(String urlId){
		String hql = "from RoleBtn where urlId = '"+urlId+"'";
		return this.query(hql);
	}
}

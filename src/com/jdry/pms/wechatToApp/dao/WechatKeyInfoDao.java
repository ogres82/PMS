package com.jdry.pms.wechatToApp.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.wechatToApp.pojo.WechatKeyInfo;
@Repository
@Transactional
public class WechatKeyInfoDao extends HibernateDao {
	
	public List<WechatKeyInfo> getWechatKeyInfo(){
		String hql = "from WechatKeyInfo where 1=1";
		List<WechatKeyInfo> list = this.query(hql);
		return  list;
	}
	
	public void saveOrupWechatKeyInfo(WechatKeyInfo wki){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(wki);
		session.flush();
		session.close();
	}
}

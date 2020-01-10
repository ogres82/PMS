package com.jdry.pms.wechatToApp.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.wechatToApp.pojo.WechatMatterEntity;

@Repository
@Transactional
public class WechatToAppDao extends HibernateDao {

	public List<WechatMatterEntity> getMatterInfo(String publish, int rows) {
		// TODO Auto-generated method stub

		String hql = "from WechatMatterEntity where 1=1";
		Session session = this.getSessionFactory().openSession();

		if (publish.length() > 0) {
			hql += " and publish='" + publish + "'";
		}
		hql += " order by createTime desc";
		Query query = session.createQuery(hql);
		if (rows > 0) {
			query.setMaxResults(rows);
		}
		List<WechatMatterEntity> lists = query.list();
		if (null != lists && lists.size() > 0) {
			session.close();
			return lists;
		} else {
			session.close();
			return null;
		}
	}

	public void syncMatterInfo(WechatMatterEntity wme) {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(wme);
		session.flush();
		session.close();
	}

	public void setPublish(String thumbMediaId,String mediaId, String publish) {
		// TODO Auto-generated method stub
		WechatMatterEntity wme = getWechatMatterById(thumbMediaId,mediaId);
		if (null != wme) {
			wme.setPublish(publish);
			syncMatterInfo(wme);
		}
	}

	public void delMatterInfo(String data) {
		// TODO Auto-generated method stub
		JSONArray array=JSON.parseArray(data);
		Session session = this.getSessionFactory().openSession();	

		for (int i = 0,len=array.size(); i < len; i++) {
			JSONObject job = (JSONObject) array.get(i);
			String thumbMediaId = job.getString("thumbMediaId");
			String mediaId = job.getString("mediaId");
			WechatMatterEntity wme = getWechatMatterById(thumbMediaId,mediaId);
			if (null != wme) {
				session.delete(wme);
			}
		}
		session.flush();
		session.close();
	}

	public WechatMatterEntity getWechatMatterById(String thumbMediaId,String mediaId) {
		// TODO Auto-generated method stub
		if ("".equals(thumbMediaId) && thumbMediaId == null&&"".equals(mediaId) && mediaId == null) {
			return null;
		} else {
			List<WechatMatterEntity> list = this.query("from " + WechatMatterEntity.class.getName() + " where thumbMediaId='" + thumbMediaId + "' and mediaId='"+mediaId+"'");
			if (null != list && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}
	}

}

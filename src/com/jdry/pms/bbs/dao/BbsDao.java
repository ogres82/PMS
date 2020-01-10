package com.jdry.pms.bbs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.bbs.pojo.BbsImgUrl;
import com.jdry.pms.bbs.pojo.BbsReply;
import com.jdry.pms.bbs.pojo.BbsTopic;
import com.jdry.pms.bbs.pojo.BbsUser;
import com.jdry.pms.bbs.pojo.VBbsReply;
import com.jdry.pms.bbs.pojo.VBbsTopic;

@Repository
public class BbsDao extends HibernateDao{

	public void addTopic(BbsTopic bbsTopic){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(bbsTopic);
		session.flush();
		session.close();
	}
	
	public void addImg(BbsImgUrl imgUrl){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(imgUrl);
		session.flush();
		session.close();
	}
	
	public void addReply(BbsReply bbsReply){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(bbsReply);
		session.flush();
		session.close();
	}
	
	public int getCommentCount(String topicId){
		String hql = "from BbsReply t where t.topicId = '"+topicId+"'";
		Session session = this.getSessionFactory().openSession();
		int count = session.createQuery(hql).list().size();
		session.flush();
		session.close();
		return count;
	}
	
	public List<VBbsTopic> loadTopicList(Map<String,Object> parameter){
		String pageStr = (String) parameter.get("page");
		String ownerId = (String) parameter.get("ownerId");
		String hql = "from VBbsTopic t";
		String whereClause = " where t.topicDelMark = '0'";
		if(!ownerId.equals("")){
			whereClause = whereClause + " and t.mappingUserId='"+ownerId+"' ";
		}
		Session session = this.getSessionFactory().openSession();
		hql = hql + whereClause + " order by t.topicTop desc, t.lastUpdateTime desc";
		List<VBbsTopic> list = new ArrayList<VBbsTopic>();
		if(!pageStr.equals("")){
			int page = Integer.parseInt(pageStr);
			list = session.createQuery(hql).setFirstResult(page*10).setMaxResults(10).list();
		}else{
			list = session.createQuery(hql).list();
		}
		session.flush();
		session.close();
		return list;
	}
	
	public List<BbsImgUrl> queryImgById(String topicId){
		String hql = "from BbsImgUrl t where t.topicId = '"+topicId+"'";
		return this.query(hql);
	}
	
	public List<VBbsReply> loadReplyById(String topicId){
		String hql = "from VBbsReply t where t.topicId = '"+topicId+"' order by t.replyTime asc";
		return this.query(hql);
	}
	
	public BbsTopic getTopicById(String topicId){
		return (BbsTopic) this.query("from BbsTopic t where t.topicId = '"+topicId+"'").get(0);
	}
	
	public void addBbsUser(BbsUser bbsUser){
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(bbsUser);
		session.flush();
		session.close();
	}
	
	public VBbsTopic refreshTopic(String topicId){
		String hql = "from VBbsTopic where topicId = '"+topicId+"'";
		return (VBbsTopic) this.query(hql).get(0);
	}

	public BbsUser getBbsUserByPhone(String phone) {
		String hql = "from BbsUser where mappingUserId = '"+phone+"'";
		List<BbsUser> user = this.query(hql);
		if(user.size()>0){
			return user.get(0);
		}else{
			return null;
		}
	}
}

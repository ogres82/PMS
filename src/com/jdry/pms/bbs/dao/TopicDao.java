package com.jdry.pms.bbs.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.bbs.pojo.*;

@Repository
@Transactional
public class TopicDao extends HibernateDao {

	public void saveTopic(BbsTopic topic) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(topic);
		session.flush();
		session.close();
	}

	public List<VBbsTopic> loadTopicList() {
		String hql = "from VBbsTopic where topicDelMark = '0'";		
		hql += " order by lastUpdateTime desc";
		return this.query(hql);
	}

	public void loadTopicListByParam(Page<VBbsTopic> page, Map<String, Object> parameter) throws Exception {
		String hql = "from VBbsTopic where topicDelMark = '0'";
		String where = "";
		String order = " order by topicTop desc, lastUpdateTime desc";

		String sqlCount = "select count(*) from " + VBbsTopic.class.getName() + " where topicDelMark = '0' " + where;
		hql += where;
		hql += order;
		Map<String, Object> map = new HashMap<String, Object>();
		this.pagingQuery(page, hql, sqlCount, map);
	}

	public VBbsTopic loadTopic(String topicId) {
		String hql = "from VBbsTopic where topicId='" + topicId + "'";
		return (VBbsTopic) this.query(hql).get(0);
	}

	public BbsTopic queryTopic(String topicId) {
		String hql = "from BbsTopic where topicId='" + topicId + "'";
		return (BbsTopic) this.query(hql).get(0);
	}

	public List<VBbsReply> loadReply(String topicId) {
		String hql = "from VBbsReply where topicId='" + topicId + "' order by replyTime asc";
		return this.query(hql);
	}

	public VBbsReply loadReplyById(String replyId) {
		String hql = "from VBbsReply where replyId='" + replyId + "'";
		return (VBbsReply) this.query(hql).get(0);
	}

	public void addReply(BbsReply reply) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(reply);
		session.flush();
		session.close();

	}


	public int getCommentCount(String topicId) {
		String hql = "from TopicReply t where t.topicId = '" + topicId + "'";
		Session session = this.getSessionFactory().openSession();
		int count = session.createQuery(hql).list().size();
		session.flush();
		session.close();
		return count;
	}
	
	public void delComment(String replyId){
		String hql = "from BbsReply where replyId='"+replyId+"'";
		BbsReply reply = (BbsReply) this.query(hql).get(0);
		if(reply!=null){
			Session session = this.getSessionFactory().openSession();
			session.delete(reply);
			session.flush();
			session.close();
		}else{
			return;
		}
	}
}

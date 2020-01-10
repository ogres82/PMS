package com.jdry.pms.hotLine.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.hotLine.pojo.HotLine;

@Repository
@Transactional
public class HotLineDao extends HibernateDao {

	public HotLine getHotLineById(String Id) {

		List<HotLine> lists = this.query("from " + HotLine.class.getName() + " where id='" + Id + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public String addHotLine(HotLine hotline) {

		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(hotline);
		session.flush();
		session.close();
		return hotline.getId();
	}

	public List<HotLine> getHotLineList() {

		Session session = this.getSessionFactory().openSession();

		String hql = "from HotLine where 1=1";
		List<HotLine> list = this.query(hql);

		session.flush();
		session.close();
		return list;
	}

	public String getHotLineOfApp(String data) {

		JSONObject obj = JSON.parseObject(data);
		String line_type = null == obj.getString("type") ? "" : obj.getString("type");
		// line_type: 0 乐湾国际; 1生活服务
		if ("".equals(line_type) && line_type == null) {
			return "{\"status\":\"-1\",\"message\":\"接口参数错误！\",\"data\":\"\"}";
		}

		Session session = this.getSessionFactory().openSession();
		String hql = "from HotLine where line_type='" + line_type + "'";
		List<HotLine> list = this.query(hql);
		session.flush();
		session.close();

		if (list != null && list.size() > 0) {

			String jsonString = JSON.toJSONString(list);
			return "{\"status\":\"1\",\"message\":\"查询成功\",\"data\":" + jsonString + "}";

		} else {

			return "{\"status\":\"0\",\"message\":\"查无数据！\",\"data\":\"\"}";
		}
	}

	public boolean saveHotLineInfo(HotLine hotline) {

		Session session = this.getSessionFactory().openSession();
		try {
			session.saveOrUpdate(hotline);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			session.flush();
			session.close();
		}

	}

	public void delHotLineInfo(String Id) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = Id.split(",");
		try {
			for (int i = 0; i < ids.length; i++) {
				HotLine hotline = getHotLineById(ids[i]);
				if (null != hotline) {
					session.delete(hotline);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}
}

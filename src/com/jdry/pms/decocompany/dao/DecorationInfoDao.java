package com.jdry.pms.decocompany.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.decocompany.pojo.ShopsChecker;
import com.jdry.pms.decocompany.pojo.ShopsConsultor;
import com.jdry.pms.decocompany.pojo.ShopsGoods;
import com.jdry.pms.decocompany.pojo.ShopsGoodsImg;
import com.jdry.pms.decocompany.pojo.ShopsInfo;
import com.jdry.pms.decocompany.pojo.VShopsInfo;

@Repository
@Transactional
public class DecorationInfoDao extends HibernateDao{

	public void queryVDecorationInfoByParam(Page<VShopsInfo> page,
			Map<String, Object> parameter) {
		String hql = " from "+VShopsInfo.class.getName();		
		String where = " where editorId = '"+CommUser.getUserName()+"'";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+VShopsInfo.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and (a.name like:name "+
							 " or a.summary like:summary" +
							 " or a.telephone like:telephone)";
				sqlCount += " and (b.name like:name "+
						    " or b.summary like:summary"+
							" or b.telephone like:telephone)";
				map.put("name", "%"+search+"%");
				map.put("summary", "%"+search+"%");
				map.put("telephone", "%"+search+"%");
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

	public ShopsInfo getDecorationInfoById(String id) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);

		List<ShopsInfo> lists = this
				.query("from " + ShopsInfo.class.getName()
						+ " where id='" + id + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	
	}

	public String addDecorationInfo(ShopsInfo decInfo) {

		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(decInfo);
		session.flush();
		session.close();
		return decInfo.getId();
	
	}

	public void deleteDecorationInfo(String id) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = id.split(",");
		
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			ShopsInfo decInfo = getDecorationInfoById(ids[i]);
			if(null != decInfo){
				session.delete(decInfo);
			}
		}
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Object> getCheckerInfo(String id) {
		
		Session session = this.getSessionFactory().openSession();
		
		
		StringBuffer sqlStr = new StringBuffer();
		if(id!=null&&!"".equals(id)){
			sqlStr.append(" select c.owner_name,c.phone,count(c.owner_id) from t_decoration_info a RIGHT JOIN t_decoration_checker b on a.id = b.company_id LEFT JOIN t_property_owner c on c.phone = b.owner_id where b.company_id ='"+id+"' group by c.owner_id");
		}
		List<Object> objs =  session.createSQLQuery(sqlStr.toString()).list();
		session.close();
		return objs;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getConsultorInfo(String id) {
		
		Session session = this.getSessionFactory().openSession();
		StringBuffer sqlStr = new StringBuffer();
		if(id!=null&&!"".equals(id)){
			sqlStr.append(" select c.owner_name,c.phone,count(c.owner_id) from t_decoration_info a RIGHT JOIN t_decoration_consultor b on a.id = b.company_id LEFT JOIN t_property_owner c on c.phone = b.owner_id where b.company_id ='"+id+"' group by c.owner_id");
		}
		List<Object> objs = session.createSQLQuery(sqlStr.toString()).list();
		session.close();
		return objs;
	}

	public void saveDecorationQualify(ShopsGoodsImg qualify) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(qualify);
		session.flush();
		session.close();
	}

	public void saveDecorationImg(ShopsGoods img) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(img);
		session.flush();
		session.close();
	}

	public ShopsGoods getDecorationImgById(String imgId) {

		List<ShopsGoods> lists = this
				.query("from " + ShopsGoods.class.getName()
						+ " where id='" + imgId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public List<ShopsGoodsImg> getImgByCompanyId(String id) {
		List<ShopsGoodsImg> lists = this
				.query("from " + ShopsGoodsImg.class.getName()
						+ " where goodsId='" + id + "'");
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return new ArrayList<ShopsGoodsImg>();
		}
	}

	public void deleteImg(ShopsGoods img) {
		Session session = this.getSessionFactory().openSession();
		
		if(null != img){
			session.delete(img);
		}
		session.flush();
		session.close();
	}

	public List<ShopsGoodsImg> getQualifyByCompanyId(String id) {
		List<ShopsGoodsImg> lists = this
				.query("from " + ShopsGoodsImg.class.getName()
						+ " where goodsId='" + id + "'");
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	public ShopsGoodsImg getQualifyById(String imgId) {
		List<ShopsGoodsImg> lists = this
				.query("from " + ShopsGoodsImg.class.getName()
						+ " where id='" + imgId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public List<VShopsInfo> queryVDecorationInfoByParam(
			Map<String, Object> parameter) {
		List<VShopsInfo> lists = this
				.query("from " + VShopsInfo.class.getName());
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return null;
		}
	}

	public void addDecorationChecker(ShopsChecker checker) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(checker);
		session.flush();
		session.close();
	}

	public VShopsInfo getVDecorationInfoById(String id) {
		List<VShopsInfo> lists = this
				.query("from " + VShopsInfo.class.getName()+" where id='"+id+"'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void addDecorationConsultor(ShopsConsultor consultor) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(consultor);
		session.flush();
		session.close();
	}

	public List<ShopsGoods> queryShopsGoodsList(String companyId) {
		List<ShopsGoods> lists = this
				.query("from " + ShopsGoods.class.getName()+" where company_id='"+companyId+"'");
		if (null != lists && lists.size() > 0) {
			return lists;
		} else {
			return new ArrayList<ShopsGoods>();
		}
	}

}

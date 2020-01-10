package com.jdry.pms.advertise.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;
import com.jdry.pms.advertise.pojo.AdevertiseModel;
import com.jdry.pms.advertise.pojo.AdvertiseAttachModel;
@Repository
public class AdvertiseAttachDao extends HibernateDao {
	
	/**保存广告明细内容
	 * 
	 * @param attachModel
	 */
	public void addAttachModel(AdvertiseAttachModel attachModel) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(attachModel);
		session.flush();
		session.close();
		session = null;
	}
	
	/**根据编号查询数据
	 * 
	 * @param adv_code
	 * @return
	 */
	public AdvertiseAttachModel getAttachModelByCode(String adv_code) {

		List<AdvertiseAttachModel> lists = this
				.query("from " + AdvertiseAttachModel.class.getName()
						+ " where adv_pos_code='" + adv_code + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	/**根据编号查询,编号才唯一
	 * 
	 * @param adv_code
	 * @return
	 */
	public AdvertiseAttachModel getAttachModelById(String adv_code) {

		List<AdvertiseAttachModel> lists = this
				.query("from " + AdvertiseAttachModel.class.getName()
						+ " where adv_pos_id='" + adv_code + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	public void getAdvertiseAll(Page<AdevertiseModel> page,Map<String, Object> parameter, Criteria criteria) throws Exception {
		String sqlSt="";
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdevertiseModel.class);
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		Map<String,Object> map =new HashMap<String,Object>();
		String sql="from "+AdevertiseModel.class.getName()+" work where 1=1" ;
		String sqlCount="select count(*) from "+AdevertiseModel.class.getName()+" work where 1=1  " ;
		StringBuffer sqlStr=new StringBuffer();
		StringBuffer sqlCountStr=new StringBuffer();
		if(parameter != null){
			sqlSt=sqlParmeter(parameter);
		}
		
		sqlStr.append(sql);
		sqlStr.append(sqlSt);
		sqlStr.append("  order by a_code desc");
		
		sqlCountStr.append(sqlCount);
		sqlCountStr.append(sqlSt);

		if (criteria != null) {
            HibernateUtils.createFilter(detachedCriteria, criteria);
        }
		this.pagingQuery(page, sqlStr.toString(), sqlCountStr.toString(), map);
	}
	
	public String sqlParmeter(Map<String, Object> parameter)
	{
		
		String sql="";
		if(parameter != null){//模糊查询
			 String search = parameter.get("search")==null?"":parameter.get("search").toString();
			 if(!search.equals("")){
//			      sql+=" and work.rpt_id like"+"'%"+search+"%'"+
//					 " or work.rpt_name like"+"'%"+search+"%'"+
//					 " or work.event_source_name like"+"'%"+search+"%'"+
//					 " or work.event_type_name like"+"'%"+search+"%'"+
//					 " or work.addres like"+"'%"+search+"%'"+
//					 " or work.createby_name like"+"'%"+search+"%'";
				}
			 
		}
		return sql;
		
	}
	
	
	/**删除广告位置
	 * 
	 * @param id
	 */
	public void deleteAdvPositionByCode(String adv_code) {
		Session session = this.getSessionFactory().openSession();
		AdvertiseAttachModel attachModel = getAttachModelById(adv_code);
		if(null != attachModel){
			session.delete(attachModel);
		}
		session.flush();
        session.close();
	}
	public List<AdevertiseModel> queryAllAdvAttach(){
		String hql = "from AdevertiseModel";
		return this.query(hql);
	}
}

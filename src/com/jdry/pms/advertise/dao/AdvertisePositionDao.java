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
import com.jdry.pms.advertise.pojo.AdvertisePositionModel;
import com.jdry.pms.advertise.pojo.VAdvertisePositionModel;
@Repository
public class AdvertisePositionDao extends HibernateDao {
	
	/**保存广告主表
	 * 
	 * @param positionModel
	 */
	public void addAttachModel(AdvertisePositionModel positionModel) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(positionModel);
		session.flush();
		session.close();
		session = null;
	}
	
	/**初始化广告位下拉框
	 * 
	 * @return
	 */
	public List<AdvertisePositionModel> getAllPosition() {
		List<AdvertisePositionModel> lists = this.query("from " + AdvertisePositionModel.class.getName());
		
		return lists;
	}
	
	/**根据编号查询数据
	 * 
	 * @param adv_code
	 * @return
	 */
	public AdvertisePositionModel getPositionModelById(String adv_code) {

		List<AdvertisePositionModel> lists = this
				.query("from " + AdvertisePositionModel.class.getName()
						+ " where adv_pos_code='" + adv_code + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}

	}
	
	/**查询所有，用于列表显示
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void getAdvPositionAll(Page<VAdvertisePositionModel> page,Map<String, Object> parameter, Criteria criteria) throws Exception {
		String sqlSt="";
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VAdvertisePositionModel.class);
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		Map<String,Object> map =new HashMap<String,Object>();
		String sql="from "+VAdvertisePositionModel.class.getName()+" work where 1=1" ;
		String sqlCount="select count(*) from "+VAdvertisePositionModel.class.getName()+" work where 1=1  " ;
		StringBuffer sqlStr=new StringBuffer();
		StringBuffer sqlCountStr=new StringBuffer();
		if(parameter != null){
			sqlSt=sqlParmeter(parameter);
		}
		
		sqlStr.append(sql);
		sqlStr.append(sqlSt);
		sqlStr.append("  order by adv_pos_code desc");
		
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
		AdvertisePositionModel positionModel = getPositionModelById(adv_code);
		if(null != positionModel){
			session.delete(positionModel);
		}
		session.flush();
        session.close();
	}
	public List<VAdvertisePositionModel> queryAll(){
		String hql = "from VAdvertisePositionModel";
		return this.query(hql);
	}
}

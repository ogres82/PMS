package com.jdry.pms.assignWork.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;
import com.jdry.pms.assignWork.pojo.VWorkDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainDispatchEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;
@Repository
public class AssignWorkDispatchDao extends HibernateDao {

	/**
	 * 新增报障的时候同时新增明细,带派工的明细数据
	 * 
	 * @Title: addWorkMain
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param mainEntity 设定文件
	 * @return void 返回类型
	 * @author hyq 2906472@qq.com
	 * @date 2016-3-25 上午11:15:13
	 * @throws
	 */
	static Logger log=Logger.getLogger(AssignWorkDispatchDao.class);
	public void addWorkDispatch(WorkMainDispatchEntity dispatchEntity) {
		
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(dispatchEntity);
		log.info("业主报障："+dispatchEntity.getMtn_id());
		session.flush();
		session.close();
	}
	
	
	public WorkMainDispatchEntity getWorkDispatchById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rpt_id", id);

		List<WorkMainDispatchEntity> lists = this
				.query("from " + WorkMainDispatchEntity.class.getName()
						+ " where mtn_id='" + id + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}

	}
	
	/**删除派工单
	 * 
	* @Title: deleteWorkDispatch 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id    设定文件 
	* @return void    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-1 下午12:47:00 
	* @throws
	 */
	public void deleteWorkDispatch(String id) {
		Session session = this.getSessionFactory().openSession();
		WorkMainDispatchEntity dispatchEntity = getWorkDispatchById(id);
		if(null != dispatchEntity){
			session.delete(dispatchEntity);
		}
		session.flush();
        session.close();
	}
	
	/**得到所有的派工单信息
	 * 
	* @Title: getAllDispatchWork 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param page
	* @param @param parameter
	* @param @param criteria
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-1 下午2:13:47 
	* @throws
	 */
	@SuppressWarnings("unused")
	public void getAllDispatchWork(Page<VWorkDispatchEntity> page,Map<String, Object> parameter, Criteria criteria) throws Exception {
		String sqlSt="";
		String userId = parameter.get("userId")==null?"":parameter.get("userId").toString();
		String postionId=getUserOfRole(userId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VWorkDispatchEntity.class);
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		Map<String,Object> map =new HashMap<String,Object>();
		ProptyUtil p=new ProptyUtil();
		String rePostionId= p.getProperty("JDRY.postionId");
		String sql="";
		String sqlCount="";
	    if(postionId.equals(rePostionId)){
	    	sql="from "+VWorkDispatchEntity.class.getName()+" work where 1=1 and dispatch_status in(1,2)";
		    sqlCount="select count(*) from "+VWorkDispatchEntity.class.getName()+" work where 1=1 and dispatch_status in(1,2)";
	    }else if(!(rePostionId).equals(postionId)){
	    	sql="from "+VWorkDispatchEntity.class.getName()+" work where 1=1 and dispatch_handle_id='"+userId+"' and dispatch_status  in(1,2)";
			sqlCount="select count(*) from "+VWorkDispatchEntity.class.getName()+" work where 1=1  and dispatch_handle_id='"+userId+"' and dispatch_status  in(1,2)";	
	    }
		StringBuffer sqlStr=new StringBuffer();
		StringBuffer sqlCountStr=new StringBuffer();
		if(parameter != null){
			sqlSt=sqlParmeter(parameter);
		}
		sqlStr.append(sql);
		sqlStr.append(sqlSt);
		sqlStr.append(" order by mtn_id desc");
		sqlCountStr.append(sqlCount);
		sqlCountStr.append(sqlSt);
		if (criteria != null) {
            HibernateUtils.createFilter(detachedCriteria, criteria);
        }
		this.pagingQuery(page,  sqlStr.toString(), sqlCountStr.toString(), map);
	}
	
	/**
	 * 根据岗位查数据
	 * @param userId
	 * @return
	 */
	public String getUserOfRole(String userId){
		String sql="select u.POSITION_ID_ from bdf2_user_position u where u.USERNAME_='"+userId+"'";
		Session session = this.getSessionFactory().openSession();
		List ls=session.createSQLQuery(sql).list();
		session.close();
		if(ls==null||ls.size()<=0){   
			return "";
		}else{
			return ls.get(0).toString();
		}
	}
	/**
	 * 查询条件
	 * @param parameter
	 * @return
	 */
	public String sqlParmeter(Map<String, Object> parameter)
	{
		
		String sql="";
		if(parameter != null){//模糊查询
			 String search = parameter.get("search")==null?"":parameter.get("search").toString();
			 if(!search.equals("")){
			      sql+=" and work.mtn_id like"+"'%"+search+"%'"+
					 " or work.createby_name like"+"'%"+search+"%'"+
					 " or work.dispatch_id like"+"'%"+search+"%'"+
					 " or work.dispatch_expense like"+"'%"+search+"%'"+
					 " or work.addres like"+"'%"+search+"%'"+
					 " or work.dispatch_handle_name like"+"'%"+search+"%'";
				}
			 
		}
		return sql;
	}
	
	
	/**点击派工和查看明细,显示具体数据
	 * 
	* @Title: getDispatchWorkById 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param mtn_id
	* @param @return    设定文件 
	* @return List    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-1 下午2:55:59 
	* @throws
	 */
	public List getDispatchWorkById(String mtn_id){
		Session session = this.getSessionFactory().openSession();
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" ");
		bufferSql.append(" select {t.*}, {d.*} ");
		bufferSql.append(" from t_r_maintain t,t_r_maintain_dispatch d where t.rpt_id=d.mtn_id ");
		bufferSql.append(" and d.mtn_id='"+mtn_id+"'");
		List list = null;
		list = session.createSQLQuery(bufferSql.toString()).addEntity("t",WorkMainEntity.class).addEntity("d",WorkMainDispatchEntity.class).list();
		if(null == list){
			list = new ArrayList();
		}
		session.flush();
        session.close();
		return list;
	}
	
	
}

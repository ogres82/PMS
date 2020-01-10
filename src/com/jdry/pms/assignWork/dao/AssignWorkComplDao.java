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
import com.jdry.pms.assignWork.pojo.VWorkCompEntity;
import com.jdry.pms.assignWork.pojo.WorkComplaintEntity;
import com.jdry.pms.assignWork.pojo.WorkMainEntity;

@Repository
public class AssignWorkComplDao extends HibernateDao {

	static Logger log=Logger.getLogger(AssignWorkDispatchDao.class);
	public WorkComplaintEntity getWorkComplById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rpt_id", id);

		List<WorkComplaintEntity> lists = this
				.query("from " + WorkComplaintEntity.class.getName()
						+ " where mtn_id='" + id + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	/**新增,修改投诉
	 * 
	* @Title: addWorkMain 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param mainEntity    设定文件 
	* @return void    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-1 下午7:53:59 
	* @throws
	 */
	public void addWorkCompl(WorkComplaintEntity complaintEntity) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(complaintEntity);
		log.info("业主咨询建议："+complaintEntity.getMtn_id());
		session.flush();
		session.close();
	}
	
	/**删除投诉单
	 * 
	* @Title: deleteWorkComp 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id    设定文件 
	* @return void    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-1 下午8:21:41 
	* @throws
	 */
	public void deleteWorkComp(String id) {
		Session session = this.getSessionFactory().openSession();
		WorkComplaintEntity complaintEntity = getWorkComplById(id);
		if(null != complaintEntity){
			session.delete(complaintEntity);
		}
		session.flush();
        session.close();
	}
	
	/**查询所有
	 * 
	* @Title: getWorkComp 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param page
	* @param @param parameter
	* @param @param criteria
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-1 下午8:36:42 
	* @throws
	 */
	public void getWorkComp(Page<VWorkCompEntity> page,Map<String, Object> parameter, Criteria criteria) throws Exception {
		String sqlSt="";
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(VWorkCompEntity.class);
		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}
		Map<String,Object> map =new HashMap<String,Object>();//and comp.comp_emergency!='0'
		String userId = parameter.get("userId")==null?"":parameter.get("userId").toString();
		String postionId=getUserOfRole(userId);
		String sql="";
		String sqlCount="";
		ProptyUtil p=new ProptyUtil();
		String rePostionId= p.getProperty("JDRY.KFpostionId");
		if(postionId.equals(rePostionId))
		{
			 sql="from "+VWorkCompEntity.class.getName()+" comp where 1=1 and comp_status  in(1,2) " ;
			 sqlCount="select count(*) from "+VWorkCompEntity.class.getName()+" comp where 1=1 and comp_status  in(1,2)" ;
		}else{
			 sql="from "+VWorkCompEntity.class.getName()+" comp where 1=1 and comp_operator_id='"+userId+"' and comp_status  in(1,2)" ;
			 sqlCount="select count(*) from "+VWorkCompEntity.class.getName()+" comp where 1=1 and comp_operator_id='"+userId+"' and comp_status  in(1,2)" ;
		}
		
		StringBuffer sqlStr=new StringBuffer();
		StringBuffer sqlCountStr=new StringBuffer();
		
		if(parameter != null){
			sqlSt=sqlParmeter(parameter);
		}
		
		sqlStr.append(sql);
		sqlStr.append(sqlSt);
		sqlStr.append("  order by comp.mtn_id desc");
		
		sqlCountStr.append(sqlCount);
		sqlCountStr.append(sqlSt);
		if (criteria != null) {
            HibernateUtils.createFilter(detachedCriteria, criteria);
        }
		this.pagingQuery(page, sqlStr.toString(), sqlCountStr.toString(), map);
	}
	/**
	 * 查询条件模糊查询
	 * @param parameter
	 * @return
	 */
	public String sqlParmeter(Map<String, Object> parameter)
	{
		
		String sql="";
		if(parameter != null){//模糊查询
			 String search = parameter.get("search")==null?"":parameter.get("search").toString();
			 if(!search.equals("")){
			      sql+=" and comp.mtn_id like"+"'%"+search+"%'"+
					 " or comp.comp_kid like"+"'%"+search+"%'"+
					 " or comp.rpt_name like"+"'%"+search+"%'"+
					 " or comp.addres like"+"'%"+search+"%'"+
					 " or comp.rpt_name like"+"'%"+search+"%'"+
					 " or comp.comp_emergency_name like"+"'%"+search+"%'"+
					 " or comp.comp_status_name like"+"'%"+search+"%'";
				}
			 
		}
		return sql;
		
	}
	
	/**派工和查看结果
	 * 
	* @Title: getDispatchWorkById 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param mtn_id
	* @param @return    设定文件 
	* @return List    返回类型 
	* @author hyq 2906472@qq.com 
	* @date 2016-4-1 下午10:11:04 
	* @throws
	 */
	public List getCompWorkById(String mtn_id){
		Session session = this.getSessionFactory().openSession();
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append(" ");
		bufferSql.append(" select {t.*}, {d.*},{vd.*} ");
		bufferSql.append(" from t_r_maintain t,T_R_MAINTAIN_COMPLAINT d,v_r_t_assign_comp vd where t.rpt_id=d.mtn_id and t.rpt_id=vd.mtn_id ");
		bufferSql.append(" and d.mtn_id='"+mtn_id+"'");
		List list = null;
		list = session.createSQLQuery(bufferSql.toString()).addEntity("t",WorkMainEntity.class).addEntity("d",WorkComplaintEntity.class).addEntity("vd",VWorkCompEntity.class).list();
		if(null == list){
			list = new ArrayList();
		}
		session.flush();
        session.close();
		return list;
	}
	/**
	 * 通过用户查询岗位
	 * @param userId
	 * @return
	 */
	public String getUserOfRole(String userId)
	{
		String sql="select u.POSITION_ID_ from bdf2_user_position u where u.USERNAME_='"+userId+"'";
		Session session = this.getSessionFactory().openSession();
		List ls=session.createSQLQuery(sql).list();
		if(ls==null||ls.size()<=0)
		{   
			return "";
		}else
		session.flush();
		session.close();
		return ls.get(0).toString();
	}
}

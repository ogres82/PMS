package com.jdry.pms.comm.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultDept;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.RoleMember;
import com.bstek.bdf2.core.model.UserDept;
import com.bstek.bdf2.core.model.UserPosition;
import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.comm.pojo.BusinessKeyEntity;
import com.jdry.pms.comm.util.CommUtil;

@Repository
public class BusinessDao extends HibernateDao {

	/**得到规则编码
	 * 
	 * @ClassName: @param type
	 * @ClassName: @param type
	 * @ClassName: @return 
	 * @Description: TODO(这里用一句话描述这个类的作用) 
	 * @author 追梦de蜗牛 yongqiang347@163.com 
	 * @date 2016-1-4 上午11:28:12
	 */
	@SuppressWarnings("unchecked")
	public String getBusinessId(String key,String type){
		
		int bBusiness_num=0;
		BusinessKeyEntity businessId = null;
		
		if(key != null && !key.equals("")){
			key = key.toUpperCase();
		}
		
		Session session = this.getSessionFactory().openSession();
		if(type==null || type.equals("")){
			type = "D";
		}
		type = type.toUpperCase();
		String formatStr = getDateFormat(type);
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
		Date date = new Date();
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select * from  T_B_BUSINESS_KEY b where business_key='"+key+"'");
		
		if(type.equals("M")){
			//月编号
			sqlStr.append(" and month(business_date) = month(curdate()) and year(business_date) = year(curdate()) and business_type='M'");
		}else if(type.equals("Y")){
			//年编号
			sqlStr.append(" and year(business_date) = year(curdate()) and business_type='Y'");
		}else{
			//日编号
			sqlStr.append(" and date(business_date) = date(curdate()) and business_type='D'");
		}
		List<BusinessKeyEntity> list = session.createSQLQuery(sqlStr.toString()).addEntity("b",BusinessKeyEntity.class).list();
		if(list != null && list.size()>0){
			businessId = list.get(0);
			if(null != businessId){
				bBusiness_num = businessId.getBusiness_num();
			}
		}
		bBusiness_num = bBusiness_num+1;
		if(businessId==null){
			String guuid = CommUtil.getGuuid();
			businessId = new BusinessKeyEntity();
			businessId.setBuseness_id(guuid);
			try {
				businessId.setBusiness_date(dateFormat.parse(dateFormat.format(date)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			businessId.setBusiness_key(key);
			businessId.setBusiness_type(type);
			businessId.setBusiness_num(bBusiness_num);
		}else{
			businessId.setBusiness_num(bBusiness_num);
		}
		session.saveOrUpdate(businessId);
		session.flush();
		session.close();
		
		return key+dateFormat.format(date).replaceAll("-", "")+frontCompWithZore(bBusiness_num,6);
	}
	
	/**
	  * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	  * @param sourceDate
	  * @param formatLength
	  * @return 重组后的数据
	  */
	 public static String frontCompWithZore(int sourceDate,int formatLength)
	 {
	  /*
	   * 0 指前面补充零
	   * formatLength 字符总长度为 formatLength
	   * d 代表为正数。
	   */
	  String newString = String.format("%0"+formatLength+"d", sourceDate);
	  return  newString;
	 }
	 
	 /**dataFormat
	  * 
	  * @ClassName: @param date_type
	  * @ClassName: @return 
	  * @Description: TODO(这里用一句话描述这个类的作用) 
	  * @author 追梦de蜗牛 yongqiang347@163.com 
	  * @date 2016-1-4 下午1:50:46
	  */
	 public String getDateFormat(String date_type){
		 Map<String,String> map = new HashMap<String,String>();
		 map.put("Y", "yyyy");
		 map.put("M", "yyyy-MM");
		 map.put("D", "yyyy-MM-dd");
		 return map.get(date_type);
	 }
	 
	 
	 /**
	  * 
	  * @param parentId
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	public Collection<DefaultDept> getAllDetp(String parentId){
		 Session session = this.getSessionFactory().openSession();
		 StringBuilder str = new StringBuilder();
		 str.append("select * from bdf2_dept where 1=1 ");
		 if(null == parentId || parentId.length()==0){
			 str.append(" and PARENT_ID_ is null ");
		 }else{
			 str.append(" and PARENT_ID_ = '"+parentId+"' ");
		 }
		 
		 return session.createSQLQuery(str.toString()).addEntity(DefaultDept.class).list();
	 }
	
	 /**得到用户
	  * 
	 * @Title: findPosition 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param userName
	 * @param @return    设定文件 
	 * @return Collection<DefaultUser>    返回类型 
	 * @author hyq 2906472@qq.com 
	 * @date 2016-4-5 下午1:22:38 
	 * @throws
	  */
	 public Collection<DefaultUser> findUserByUserName(String userName) {
			List<DefaultUser> result =new ArrayList<DefaultUser>();
			try {
				Session session = this.getSessionFactory().openSession();
				StringBuilder hql = new StringBuilder();
				hql.append(" from ").append(DefaultUser.class.getName());
				hql.append(" u ").toString();
				hql.append(" where 1=1 and u.username='"+userName+"'");
				result=session.createQuery(hql.toString()).list();
				session.flush();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;

		}

	/**
	 * 通过username或position或dept查询角色，一次铜
	 * @param username
	 * @return
	 */
	public RoleMember getRoleMemberByUsername(String username) {
		String qStr = "from " + RoleMember.class.getName()+ " where username='" + username + "'";
		List<RoleMember> lists = this.query(qStr);
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			UserPosition pos = getUserPositionByUsername(username);
			if(pos!=null){
				qStr = "from " + RoleMember.class.getName()+ " where positionId='" + pos.getPositionId() + "'";
				lists = this.query(qStr);
				if (null != lists && lists.size() > 0) {
					return lists.get(0);
				}else{
					UserDept dept = getUserDeptByUsername(username);
					if(dept!=null){
						qStr = "from " + RoleMember.class.getName()+ " where deptId='" + dept.getDeptId() + "'";
						lists = this.query(qStr);
						if (null != lists && lists.size() > 0) {
							return lists.get(0);
						}else{
							return null;
						}
					}else{
						return null;
					}
				}
			}else{
				UserDept dept = getUserDeptByUsername(username);
				if(dept!=null){
					qStr = "from " + RoleMember.class.getName()+ " where deptId='" + dept.getDeptId() + "'";
					lists = this.query(qStr);
					if (null != lists && lists.size() > 0) {
						return lists.get(0);
					}else{
						return null;
					}
				}else{
					return null;
				}
			}
		}
	}
	
	public UserPosition getUserPositionByUsername(String username) {
		List<UserPosition> lists = this
				.query("from " + UserPosition.class.getName()
						+ " where username='" + username + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	public UserDept getUserDeptByUsername(String username) {
		List<UserDept> lists = this
				.query("from " + UserDept.class.getName()
						+ " where username='" + username + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	
}

package com.jdry.pms.chargeManager.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRelaViewEntity;
import com.jdry.pms.chargeManager.pojo.ChargeTypeRoomRelaEntity;
import com.jdry.pms.comm.util.CommUser;


@Repository
public class ChargeTypeRoomRelaDao extends HibernateDao {
	
	static Logger log = Logger.getLogger(ChargeTypeRoomRelaDao.class);
	
	//从数据库视图中查询数据
	public void queryAllFromView(Page<ChargeTypeRelaViewEntity> page, Map<String, Object> parameter) {
		
		String hql = " from "+ChargeTypeRelaViewEntity.class.getName();
		String whereCase = " a where 1=1 ";
		 String sqlCount="select count(*) from "+ChargeTypeRelaViewEntity.class.getName()+" b where 1=1";
		 Map<String,Object> map =new HashMap<String,Object>();
		 
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			
			if(!search.equals("")){
				whereCase += " and (a.charge_type_no like:charge_type_no_rela "
							+ " or a.room_no like:room_no "
							+ " or a.belong_unit like:belong_unit "
							+ " or a.charge_type_name like:charge_type_name) ";
				sqlCount += " and (b.charge_type_no like:charge_type_no_rela "
							+ " or b.room_no like:room_no "
							+ " or b.belong_unit like:belong_unit "
							+ " or b.charge_type_name like:charge_type_name) ";
				
				map.put("charge_type_no_rela", "%"+search+"%");
				map.put("room_no", "%"+search+"%");
				map.put("belong_unit", "%"+search+"%");
				map.put("charge_type_name", "%"+search+"%");
				
			}
		}
		
		hql += whereCase;
				
		System.out.println("hql==="+hql);
		//List<ChargeTypeRelaViewEntity> list = this.query(hql);
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return list;
	}
	
	//从数据库视图中查询数据
		public List queryAllFromView() {
			
			String hql = " select * from v_area_build_house_owner_rela ";
			
			Session session = this.getSessionFactory().openSession();
			List list = session.createSQLQuery(hql).list();		     
	        session.close();  		
			return list;
		}

	
	public void queryAll(Page<ChargeTypeRoomRelaEntity> page, Map<String, Object> parameter) {
		
		String hql = " from "+ChargeTypeRoomRelaEntity.class.getName();
		String whereCase = " a where 1=1 ";
		 String sqlCount="select count(*) from "+ChargeTypeRoomRelaEntity.class.getName()+" b where 1=1";
		 Map<String,Object> map =new HashMap<String,Object>();
		 
		if(parameter != null){
			String charge_type_no_rela = parameter.get("charge_type_no_rela")==null?"":parameter.get("charge_type_no_rela").toString();
			String room_no = parameter.get("room_no")==null?"":parameter.get("room_no").toString();
			String owner_id = parameter.get("owner_id")==null?"":parameter.get("owner_id").toString();

			if(!charge_type_no_rela.equals("")){
				whereCase += " and a.charge_type_no like:charge_type_no_rela ";
				sqlCount += " and b.charge_type_no like:charge_type_no_rela ";
				map.put("charge_type_no_rela", "%"+charge_type_no_rela+"%");
			}
			
			if(!room_no.equals("")){
				whereCase += " and a.room_no like:room_no ";
				sqlCount += " and b.room_no like:room_no ";
				map.put("room_no", "%"+room_no+"%");
			}
			
			if(!owner_id.equals("")){
				whereCase += " and a.owner_id like:owner_id ";
				sqlCount += " and b.owner_id like:owner_id ";
				map.put("owner_id", "%"+owner_id+"%");
			}
		}
		
		hql += whereCase;
				
		System.out.println("hql==="+hql);
		//List<ChargeTypeRoomRelaEntity> list = this.query(hql);
		try {
			this.pagingQuery(page, hql, sqlCount, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return list;
	}
	
	//新增和修改
	public void saveAll(ChargeTypeRoomRelaEntity chargeType){
		String charge_id = chargeType.getCharge_type_id();
		String room_id = chargeType.getRoom_id();
		
		if(charge_id!=null && !charge_id.isEmpty() && room_id!=null && !room_id.isEmpty()){
			Session session = this.getSessionFactory().openSession();
			
			chargeType.setUpdate_date(new Date());
			session.saveOrUpdate(chargeType);		
			
			session.flush();
			session.close();
			
			log.info("收费项关联--收费项："+charge_id +", 房间："+room_id);
		}
	}

	
	public void delete(List<ChargeTypeRoomRelaEntity> relas){
		Session session = null;
		Transaction tx = null;
		
		String chargeTypeIds="";
		String roomIds="";
		
		try{
			session = this.getSessionFactory().openSession();
			tx = session.beginTransaction();
			for(ChargeTypeRoomRelaEntity rela:relas){
				session.delete(rela);
				chargeTypeIds += rela.getCharge_type_id()+",";
				roomIds += rela.getRoom_id()+",";
			}
			tx.commit();
			
			log.info("收费项关联删除--收费项："+chargeTypeIds +", 房间："+roomIds);
		}catch(Exception e){
			e.printStackTrace();
			if(tx!=null){
				tx.rollback();
			}
			log.info("收费项关联删除失败--收费项："+chargeTypeIds +", 房间："+roomIds);
		}finally{
			if(session!=null){
				session.close();				
			}
		}
	}
	
	public void addBatch(Map<String, Object> parameter){
		if(parameter != null){
			String belong_unit_id = parameter.get("belong_unit_id")==null?null:parameter.get("belong_unit_id").toString();
			String storied_build_id = parameter.get("storied_build_id")==null?null:parameter.get("storied_build_id").toString();
			String charge_type_no = parameter.get("charge_type_no")==null?null:parameter.get("charge_type_no").toString();
			String charge_type_id = parameter.get("charge_type_id")==null?null:parameter.get("charge_type_id").toString();
			String type_flag = parameter.get("type_flag")==null?null:parameter.get("type_flag").toString();
			
			Session session = this.getSessionFactory().openSession();
			SQLQuery query = session.createSQLQuery("{Call pro_addBatchTypeRela(?,?,?,?,?,?)}");
			query.setString(0, belong_unit_id); 
			query.setString(1, storied_build_id); 
			query.setString(2, charge_type_id); 
			query.setString(3, charge_type_no); 
			query.setString(4, CommUser.getUserName());
			query.setString(5, type_flag);
			query.executeUpdate();  
			session.flush();
			session.close();
		}
	}
	
	//物业费和高层物业的互斥性验证
	public boolean validatePropertyRela(Map<String, Object> parameter){
		boolean flag = true;
		
		String room_id = parameter.get("room_id")==null?"":parameter.get("room_id").toString();
		String charge_type_no = parameter.get("charge_type_no")==null?"":parameter.get("charge_type_no").toString();
		String type_flag = parameter.get("type_flag")==null?"":parameter.get("type_flag").toString();
		
		String sql = "";
		//物业费验证,有其他需求再加
		if(type_flag.equals("01")){
			sql = " SELECT * FROM t_charge_type_room_rela r WHERE r.room_id='"+room_id+"' AND r.type_flag='"+type_flag+"' ";
		}
		
		Session session = this.getSessionFactory().openSession();
		List result = session.createSQLQuery(sql).list();
		session.flush();
		session.close();
		System.out.println(sql);
		
		if(result.size() > 0){
			flag = false;
		}
		
		return flag;
	}
	
	//通过房间号，查询房间关联费用项目信息
	public List<Map<String,Object>> getChargeTypeRoomRelaByRoomId(String roomId,String typeFlag){
		String sql ="SELECT * FROM v_charge_type_room_rela"+" Where room_id='"+roomId+"' AND type_flag='" +typeFlag+"'";
		Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = query.list();		
		return list;
		
	}
}

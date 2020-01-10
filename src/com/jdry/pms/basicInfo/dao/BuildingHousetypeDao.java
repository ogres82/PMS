package com.jdry.pms.basicInfo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.basicInfo.pojo.BuildingHousetype;
import com.jdry.pms.basicInfo.pojo.BuildingImg;

@Repository
@Transactional
public class BuildingHousetypeDao extends HibernateDao{

	static Logger log = Logger.getLogger(BuildingHousetypeDao.class);

	public void queryListByParam(Page<BuildingHousetype> page,Map<String, Object> parameter) {

		String hql = " from "+BuildingHousetype.class.getName();		
		String where =" where 1=1 ";
		String whereCase = " a " + where;
		String sqlCount="select count(*) from "+BuildingHousetype.class.getName()+" b " + where;
		Map<String,Object> map =new HashMap<String,Object>();		
		if(parameter != null){
			String search = parameter.get("search")==null?"":parameter.get("search").toString();
			if(!search.equals("")){
				whereCase += " and a.typeName like:typeName "+
							 " or a.remark like:remark";
				sqlCount += " and b.typeName like:typeName "+
						    " or b.remark like:remark";
				map.put("typeName", "%"+search+"%");
				map.put("remark", "%"+search+"%");
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

	public BuildingHousetype getBuildingHousetypeById(String typeId) {
		List<BuildingHousetype> lists = this
				.query("from " + BuildingHousetype.class.getName()
						+ " where typeId='" + typeId + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	
	}

	public String addBuildingHousetype(BuildingHousetype houseType) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(houseType);
		session.flush();
		session.close();
		log.info("新增或编辑户型 "+houseType.getTypeId());
		return houseType.getTypeId();
	}

	public void deleteBuildingHousetype(String typeId) {
		Session session = this.getSessionFactory().openSession();
		String[] ids = typeId.split(",");
		for(int i = 0;i<ids.length;i++){
			System.out.println(ids[i]);
			BuildingHousetype houseType = getBuildingHousetypeById(ids[i]);
			if(null != houseType){
				session.delete(houseType);
				log.info("删除户型 "+houseType.getTypeId());
				deleteBuildingImg(houseType.getTypeId());
			}
		}
		session.flush();
		session.close();
		
	}

	private void deleteBuildingImg(String typeId) {
		List<BuildingImg> imgs = getImgByTypeId(typeId);
		for(int i=0;i<imgs.size();i++){
			BuildingImg img = imgs.get(i);
			this.deleteImg(img);
		}
	}

	public BuildingHousetype getBuildingHousetypeByName(String typeName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeName", typeName);
		List<BuildingHousetype> lists = this
				.query("from " + BuildingHousetype.class.getName()
						+ " where typeName='" + typeName + "'");
		if (null != lists && lists.size() > 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	public void saveBuildingImg(BuildingImg img) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(img);
		session.flush();
		session.close();
	}

	public BuildingImg getBuildingImgById(String imgId) {
		List<BuildingImg> lists = this
				.query("from " + BuildingImg.class.getName()
						+ " where imgId='" + imgId + "'");
		if(null != lists && lists.size() > 0){
			return lists.get(0);
		}else{
			return null;
		}
	}

	public List<BuildingImg> getImgByTypeId(String typeId) {
		return this.query("from " + BuildingImg.class.getName()+ " where typeId='" + typeId + "'");
	}

	public void deleteImg(BuildingImg img) {
		Session session = this.getSessionFactory().openSession();
		session.delete(img);
		session.flush();
		session.close();
	}
	
}

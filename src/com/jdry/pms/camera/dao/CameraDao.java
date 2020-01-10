package com.jdry.pms.camera.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.jdry.pms.basicInfo.pojo.AreaProperty;
import com.jdry.pms.camera.pojo.CameraInfo;
import com.jdry.pms.comm.util.PageUtil;

@Repository
public class CameraDao extends HibernateDao {
	public void queryCameraList(PageUtil<?> pageUtil, Map<String, String> parameter) {
		String fields = "SELECT area.community_id,	area.community_name, camera.id,	camera.camera_name,	camera.camera_address, camera.camera_model, camera.camera_serial_number, camera.camera_verification_code, camera.create_date";
		String from = "  FROM	t_area_property area,t_camera_info camera ";
		String where = " WHERE	camera.region_id = area.community_id ";

		String search = parameter.get("searchName") == null ? "" : parameter.get("searchName");
		String regionId = parameter.get("regionId") == null ? "" : parameter.get("regionId");

		if (!"".equals(regionId)) {
			where += " AND camera.region_id='" + regionId + "'";
		}
		if (!"".equals(search)) {
			where += " AND (	camera.camera_name LIKE '%" + search + "%'	OR area.community_name LIKE '%" + search
					+ "%')";
		}
		try {
			String sql = fields + from + where;
			Session session = this.getSessionFactory().openSession();
			pageUtil.setEntityList(sql, parameter.get("offset"), parameter.get("limit"), session);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String saveorUpdateCamera(CameraInfo cameraInfo) {
		Session session = this.getSessionFactory().openSession();
		session.saveOrUpdate(cameraInfo);
		session.flush();
		session.close();
		return cameraInfo.getId();
	}
	
	public String deleteCamera(CameraInfo cameraInfo) {
		Session session = this.getSessionFactory().openSession();
		session.delete(cameraInfo);
		session.flush();
		session.close();
		return cameraInfo.getId();
	}

	public List<CameraInfo> getCameraList(Map<String, Object> param) {
		if(param == null){
			param = new HashMap<String,Object>();
		}
		
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+CameraInfo.class.getName()+" ca where 1=1";
	   
		return  this.query(sql, map);
	}

	public List<CameraInfo> getCameraListByCommunityId(Map<String, String> parameter) {
		String communityId = parameter.get("communityId")==null?"":parameter.get("communityId");
		String hql = "from CameraInfo where 1=1";
		if(!communityId.equals("")) {
			hql += " and regionId ='"+communityId+"'";
		}
		return this.query(hql);
	}
}

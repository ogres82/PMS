package com.jdry.pms.basicInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jdry.pms.basicInfo.pojo.BuildUnit;
import com.jdry.pms.basicInfo.pojo.VBuildingUnitAllInfo;
import com.jdry.pms.comm.dao.impl.BaseDaoImpl;
@Repository
@Transactional
public class BuildUnitDao extends BaseDaoImpl<BuildUnit> {
	public  BuildUnitDao(){
		super();
	}	
	
	//根据单元相关属性进行数据查询
	
	public List<BuildUnit> queryBuildUnitInfo(Map<String, Object> params){
		String hql = " FROM VBuildingUnitAllInfo ";
		String strWhere = " WHERE 1=1";
		String unitId = params.get("unitId").toString();
		String unitName = params.get("unitName").toString();
		String storiedBuildId = params.get("storiedBuildId") .toString();
		String communityId = params.get("communityId") .toString();
		if(unitId!=null&&!"".equals(unitId)){
			strWhere +=" AND unitId='"+unitId+"'";
		}
		if(unitName!=null&&!"".equals(unitName)){
			strWhere +=" AND unitName='"+unitName+"'";
		}
		if(storiedBuildId!=null&&!"".equals(storiedBuildId)){
			strWhere +=" AND storiedBuildId='"+ storiedBuildId+"'";
		}
		if(communityId!=null&&!"".equals(communityId)){
			strWhere +=" AND communityId='"+ communityId+"'";
		}
		hql+=strWhere;
		return this.findEntityByHQL(hql, null);
	}
	
}

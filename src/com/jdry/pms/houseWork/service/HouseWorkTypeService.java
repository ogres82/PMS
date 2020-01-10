package com.jdry.pms.houseWork.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jdry.pms.houseWork.pojo.HouseworkType;
/**
 * 家政服务管理控制器
 * @author hezuping
 *
 */
@Repository
public interface HouseWorkTypeService 
{
	/**
	 * 查询家政项目信息
	 * @return
	 */
	public List<HouseworkType> queryType();

}

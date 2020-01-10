package com.jdry.pms.basicInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;

/**
 * 数据验证方法类
 * @author Buxiaochao
 *
 */
@Repository
@Transactional
public class ValidatorsDao extends HibernateDao{

	/**
	 * 唯一性验证
	 * @param parameter
	 * @return
	 * @throws InterruptedException
	 */
	public String uniqueCheck(String parameter)
			throws InterruptedException {
		
		List<Object> object =  this.query(parameter);

		if (object.size()==0) {
			return null;
		} else {
			return  "已经被人注册了 。";
		}
	}
}

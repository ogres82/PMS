package com.jdry.pms.assignWork.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jdry.pms.assignWork.dao.DispatchPersonDao;
import com.jdry.pms.assignWork.pojo.DispatchPerson;
import com.jdry.pms.assignWork.service.DispatchPersonService;
/**
 * 
 * @author hezuping
 *  时间：2016年10月8日14:22:26
 */
@Component
public class DispatchPersonServiceImpl implements DispatchPersonService
{
    @Resource
    DispatchPersonDao dispatchPersonDao;
	
	@Override
	public void addDisptchPersonInfo(DispatchPerson person)
	{
		dispatchPersonDao.addDisptchPersonInfo(person);
	}

	@Override
	public List queryAllHandler()
	{
		return dispatchPersonDao.queryAllHandler();
	}

	@Override
	public void deleteDispatchPersonById(String rptId) {
		// TODO Auto-generated method stub
		dispatchPersonDao.deleteDispatchPersonById(rptId);
	}
	
	

}

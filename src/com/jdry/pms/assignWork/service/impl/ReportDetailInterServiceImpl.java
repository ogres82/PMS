package com.jdry.pms.assignWork.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.assignWork.dao.ReportDetailInterfaceDao;
import com.jdry.pms.assignWork.service.ReportDetailInterService;

/**
 * 报障报修接口实现
 * 
 * @author hezuping
 * 
 */
@Repository
@Component
public class ReportDetailInterServiceImpl implements ReportDetailInterService {
	@Resource
	ReportDetailInterfaceDao reportDetailInterfaceDao;

	@Override
	public List getReportDetailList(String mtr_id) {
		if (mtr_id.equals("")) 
		{
			return null;
		} else
		{
			return reportDetailInterfaceDao.getReportDetailList(mtr_id);
		}
	}

	@Override
	public List getReportDeailInfoList(String mtr_id)
	{
		return reportDetailInterfaceDao.getReportDeailInfoList(mtr_id);
	}

	@Override
	public List getRepairHistoryByPhone(String phone)
	{
		String moble=null==phone?"":phone;
		if(moble.equals(""))
		{
			return null;
		}
		else{
		return reportDetailInterfaceDao.getRepairHistoryByPhone(moble);
		}
	}

}

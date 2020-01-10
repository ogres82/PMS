package com.jdry.pms.assertStockManage.dao;

import java.util.Collection;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jdry.pms.assertStockManage.pojo.InstockModel;

/**
 * 描述：入库持久层
 * @author hezuping
 *
 */
@Repository
@Transactional
public class InstockDao
{

	public void OperationInstockInfo(Collection<InstockModel> instocks)
	{
		
		System.out.println("==入库============");
	}
	
}

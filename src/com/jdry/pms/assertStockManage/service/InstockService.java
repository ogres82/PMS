package com.jdry.pms.assertStockManage.service;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.jdry.pms.assertStockManage.pojo.InstockModel;

/**
 * 描述：入库信息
 * @author hezuping
 *
 */
@Repository
public interface InstockService 
{
	public void OperationInstockInfo(Collection<InstockModel> instock);
}

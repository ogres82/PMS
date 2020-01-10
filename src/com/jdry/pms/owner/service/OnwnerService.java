package com.jdry.pms.owner.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
/**
 * ҵ����Ϣά���ӿ�
 * @author hezuping
 *
 */

@Repository
public interface OnwnerService
{

	public List findOnwerInfo(Map<String,Object> parameter);
	
	
	
}

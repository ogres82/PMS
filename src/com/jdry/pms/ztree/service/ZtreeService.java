package com.jdry.pms.ztree.service;




import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jdry.pms.comm.dao.impl.BaseDaoImpl;
import com.jdry.pms.ztree.dao.ZtreeDao;
import com.jdry.pms.ztree.pojo.HouseZtree;

@Service
public class ZtreeService extends BaseDaoImpl<HouseZtree> {
	@Resource
	ZtreeDao dao;

}
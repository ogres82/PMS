package com.jdry.pms.owner.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface OnwnerDao {

	public List findOnwerInfo(Map<String,Object> parameter);
}

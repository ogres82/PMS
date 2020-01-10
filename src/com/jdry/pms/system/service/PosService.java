package com.jdry.pms.system.service;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.model.DefaultPosition;
@Repository
public interface PosService {
	public List<DefaultPosition> loadPosition();
	public void savePosition(DefaultPosition pos);
	public void deletePosition(String id);
}

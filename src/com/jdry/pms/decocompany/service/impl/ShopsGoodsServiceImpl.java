package com.jdry.pms.decocompany.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.PageUtil;
import com.jdry.pms.decocompany.dao.ShopsGoodsDao;
import com.jdry.pms.decocompany.pojo.ShopsChecker;
import com.jdry.pms.decocompany.pojo.ShopsConsultor;
import com.jdry.pms.decocompany.pojo.ShopsGoods;
import com.jdry.pms.decocompany.pojo.ShopsGoodsImg;
import com.jdry.pms.decocompany.pojo.ShopsInfo;
import com.jdry.pms.decocompany.pojo.VShopsInfo;
import com.jdry.pms.decocompany.service.ShopsGoodsService;

@Repository
@Component
public class ShopsGoodsServiceImpl implements ShopsGoodsService{
	
	@Resource
	ShopsGoodsDao dao;

	@Override
	public void queryVDecorationInfoByParam(Page<ShopsGoods> page,
			Map<String, Object> parameter) {
		dao.queryVDecorationInfoByParam(page,parameter);
	}
	@Override
	public void queryVDecorationInfoByParam(PageUtil<?> pageUtil,
			Map<String, Object> parameter) {
		dao.queryVDecorationInfoByParam(pageUtil,parameter);
	}

	@Override
	public ShopsGoods getDecorationInfoById(String id) {
		
		return dao.getDecorationInfoById(id);
	}

	@Override
	public String addDecorationInfo(ShopsInfo decInfo) {
		
		return dao.addDecorationInfo(decInfo);
	}

	@Override
	public void deleteDecorationInfo(String id) {
		dao.deleteDecorationInfo(id);
	}

	@Override
	public List<Object> getCheckerInfo(String id) {
		
		return dao.getCheckerInfo(id);
	}

	@Override
	public List<Object> getConsultorInfo(String id) {
		return dao.getConsultorInfo(id);
	}

	@Override
	public void saveDecorationQualify(ShopsGoodsImg qualify) {
		dao.saveDecorationQualify(qualify);
	}

	@Override
	public ShopsGoodsImg getDecorationImgById(String imgId) {
		
		return dao.getDecorationImgById(imgId);
	}

	@Override
	public List<ShopsGoodsImg> getImgByCompanyId(String id) {
		return dao.getImgByCompanyId(id);
	}

	@Override
	public void deleteImg(ShopsGoodsImg img) {
		dao.deleteImg(img);
	}

	@Override
	public List<ShopsGoodsImg> getQualifyByCompanyId(String id) {
		
		return dao.getQualifyByCompanyId(id);
	}

	@Override
	public ShopsGoodsImg getQualifyById(String imgId) {
		return dao.getQualifyById(imgId);
	}

	@Override
	public List<VShopsInfo> queryVDecorationInfoByParam(
			Map<String, Object> parameter) {
		
		return dao.queryVDecorationInfoByParam(parameter);
	}

	@Override
	public void addDecorationChecker(ShopsChecker checker) {
		dao.addDecorationChecker(checker);
	}

	@Override
	public VShopsInfo getVDecorationInfoById(String id) {
		
		return dao.getVDecorationInfoById(id);
	}

	@Override
	public void addDecorationConsultor(ShopsConsultor consultor) {
		 dao.addDecorationConsultor(consultor);
	}

	@Override
	public void saveShopsGoods(ShopsGoods sg) {
		dao.saveShopsGoods(sg);
	}
	@Override
	public void updateGoodsImg(String ids,String goodId) {
		dao.updateGoodsImg(ids,goodId);
	}

	@Override
	public void saveGoodsImg(ShopsGoodsImg img) {
		dao.saveGoodsImg(img);
	}

}

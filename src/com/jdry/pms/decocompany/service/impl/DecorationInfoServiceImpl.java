package com.jdry.pms.decocompany.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.decocompany.dao.DecorationInfoDao;
import com.jdry.pms.decocompany.pojo.ShopsChecker;
import com.jdry.pms.decocompany.pojo.ShopsConsultor;
import com.jdry.pms.decocompany.pojo.ShopsGoods;
import com.jdry.pms.decocompany.pojo.ShopsGoodsImg;
import com.jdry.pms.decocompany.pojo.ShopsInfo;
import com.jdry.pms.decocompany.pojo.VShopsInfo;
import com.jdry.pms.decocompany.service.DecorationInfoService;

@Repository
@Component
public class DecorationInfoServiceImpl implements DecorationInfoService{
	
	@Resource
	DecorationInfoDao dao;

	@Override
	public void queryVDecorationInfoByParam(Page<VShopsInfo> page,
			Map<String, Object> parameter) {
		dao.queryVDecorationInfoByParam(page,parameter);
	}

	@Override
	public ShopsInfo getDecorationInfoById(String id) {
		
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
	public void saveDecorationImg(ShopsGoods img) {
		dao.saveDecorationImg(img);
	}

	@Override
	public ShopsGoods getDecorationImgById(String imgId) {
		
		return dao.getDecorationImgById(imgId);
	}

	@Override
	public List<ShopsGoodsImg> getImgByCompanyId(String id) {
		return dao.getImgByCompanyId(id);
	}

	@Override
	public void deleteImg(ShopsGoods img) {
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
	public List<ShopsGoods> queryShopsGoodsList(String companyId) {
		return dao.queryShopsGoodsList(companyId);
	}

}

package com.jdry.pms.decocompany.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.decocompany.pojo.ShopsChecker;
import com.jdry.pms.decocompany.pojo.ShopsConsultor;
import com.jdry.pms.decocompany.pojo.ShopsGoods;
import com.jdry.pms.decocompany.pojo.ShopsGoodsImg;
import com.jdry.pms.decocompany.pojo.ShopsInfo;
import com.jdry.pms.decocompany.pojo.VShopsInfo;

@Repository
public interface DecorationInfoService {

	public void queryVDecorationInfoByParam(Page<VShopsInfo> page,
			Map<String, Object> parameter);
	
	public List<VShopsInfo> queryVDecorationInfoByParam(Map<String, Object> parameter);

	public ShopsInfo getDecorationInfoById(String id);
	
	public VShopsInfo getVDecorationInfoById(String id);

	public String addDecorationInfo(ShopsInfo decInfo);

	public void deleteDecorationInfo(String id);

	public List<Object> getCheckerInfo(String id);

	public List<Object> getConsultorInfo(String id);

	public void saveDecorationQualify(ShopsGoodsImg qualify);

	public void saveDecorationImg(ShopsGoods img);

	public ShopsGoods getDecorationImgById(String imgId);

	public List<ShopsGoodsImg> getImgByCompanyId(String id);

	public void deleteImg(ShopsGoods img);

	public List<ShopsGoodsImg> getQualifyByCompanyId(String id);

	public ShopsGoodsImg getQualifyById(String imgId);

	public void addDecorationChecker(ShopsChecker checker);

	public void addDecorationConsultor(ShopsConsultor consultor);

	public List<ShopsGoods> queryShopsGoodsList(String companyId);

}

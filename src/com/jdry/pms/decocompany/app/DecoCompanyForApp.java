package com.jdry.pms.decocompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jdry.pms.comm.util.AppResultData;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.decocompany.pojo.ShopsChecker;
import com.jdry.pms.decocompany.pojo.ShopsConsultor;
import com.jdry.pms.decocompany.pojo.ShopsGoods;
import com.jdry.pms.decocompany.pojo.ShopsGoodsImg;
import com.jdry.pms.decocompany.pojo.VShopsInfo;
import com.jdry.pms.decocompany.service.DecorationInfoService;

@Component
public class DecoCompanyForApp {
	
	@Resource
	private DecorationInfoService service;
	
	public String decoCompanyList(String data){
		Map<String,Object> parameter = new HashMap<String,Object>();
		JSONObject obj = JSON.parseObject(data);
		String url = obj.getString("url");
		String result = "{\"status\":\"0\",\"message\":\"服务异常,请稍后重试\"}";
		try{
			if(service == null){
				service = (DecorationInfoService) SpringUtil.getObjectFromApplication("decorationInfoServiceImpl");
			}
			List<VShopsInfo> info = service.queryVDecorationInfoByParam(parameter);
			if(info==null){
				result =  "{\"status\":\"0\",\"message\":\"无数据\"}";
			}else{
				List<VShopsInfo> info_ = new ArrayList<VShopsInfo>();
				for(int i =0;i<info.size();i++){
					VShopsInfo vdif = info.get(i);
					vdif.setLogoUrl(url+vdif.getLogoUrl());
					info_.add(vdif);
				}
				String jsonString = JSON.toJSONString(info_);
				result =  "{\"status\":\"1\",\"data\":"+jsonString+",\"message\":\"成功\"}";
			}
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return result;
		}
	}
	
	public String updateChecker(String data){
		JSONObject obj = JSON.parseObject(data);
		String id = obj.getString("id");
		String ownerId = obj.getString("ownerId");
		String type = obj.getString("type");
		if(type.equals("0")){
			ShopsChecker checker = new ShopsChecker();
			checker.setCompanyId(id);
			checker.setOwnerId(ownerId);
			if(service == null){
				service = (DecorationInfoService) SpringUtil.getObjectFromApplication("decorationInfoServiceImpl");
			}
			try{
				
				service.addDecorationChecker(checker);
				return  "{\"status\":\"1\",\"message\":\"更新成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return  "{\"status\":\"0\",\"message\":\"服务异常,请稍后重试\"}";
			}
		}else if(type.equals("1")){
			ShopsConsultor consultor = new ShopsConsultor();
			consultor.setCompanyId(id);
			consultor.setOwnerId(ownerId);
			if(service == null){
				service = (DecorationInfoService) SpringUtil.getObjectFromApplication("decorationInfoServiceImpl");
			}
			try{
				
				service.addDecorationConsultor(consultor);
				return  "{\"status\":\"1\",\"message\":\"更新成功\"}";
			}catch(Exception e){
				e.printStackTrace();
				return  "{\"status\":\"0\",\"message\":\"服务异常,请稍后重试\"}";
			}
		}else{
			return  "{\"status\":\"0\",\"message\":\"参数错误，类型不对\"}";
		}
	}
	
	public String getDecoCompanyById(String data){
		JSONObject obj = JSON.parseObject(data);
		String id = obj.getString("id");
		String url = obj.getString("url");
		AppResultData re = new AppResultData();
		Map<String,Object> map1 = new HashMap<String,Object>();
		try{
			if(service == null){
				service = (DecorationInfoService) SpringUtil.getObjectFromApplication("decorationInfoServiceImpl");
			}
			VShopsInfo decoInfo = service.getVDecorationInfoById(id);
			if(decoInfo!=null){
				List<ShopsGoodsImg> imgs = service.getImgByCompanyId(id);
				List<ShopsGoodsImg> qualify = service.getQualifyByCompanyId(id);
				
				List<ShopsGoodsImg> imgs_r = new ArrayList<ShopsGoodsImg>();
				List<ShopsGoodsImg> qualify_r = new ArrayList<ShopsGoodsImg>();
				
				for(int i=0;i<imgs.size();i++){
					ShopsGoodsImg img = imgs.get(i);
//					img.setImgUrl(url);
					imgs_r.add(img);
				}
				
				for(int i=0;i<qualify.size();i++){
					ShopsGoodsImg qy = qualify.get(i);
					qy.setImgUrl(url+qy.getImgUrl());
					qualify_r.add(qy);
				}
				
				re.setStatus("1");
				re.setMessage("查询成功");
				map1.put("id", decoInfo.getId());
				map1.put("name", decoInfo.getName());
				map1.put("summary", decoInfo.getSummary());
				map1.put("telephone", decoInfo.getTelephone());
				map1.put("logoUrl", decoInfo.getLogoUrl());
				map1.put("checker", decoInfo.getChecker());
				map1.put("consultor", decoInfo.getConsultor());
				map1.put("decoType", imgs_r);
				map1.put("qualify", qualify_r);
				re.setData(map1);
			}else{
				re.setStatus("0");
				re.setMessage("无数据");
			}
			return JSON.toJSONString(re,SerializerFeature.WriteMapNullValue);
		}catch(Exception e){
			e.printStackTrace();
			re.setStatus("0");
			re.setMessage("服务异常");
			return JSON.toJSONString(re,SerializerFeature.WriteMapNullValue);
		}
	}
	
	public String getGoodsList(String data){
		JSONObject obj = JSON.parseObject(data);
		String companyId = obj.getString("companyId");
		String url = obj.getString("url");
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			if(service == null){
				service = (DecorationInfoService) SpringUtil.getObjectFromApplication("decorationInfoServiceImpl");
			}
			List<ShopsGoods> info = service.queryShopsGoodsList(companyId);
			List<JSONObject> objs = new ArrayList<JSONObject>();
			for(int i=0;i<info.size();i++){
				JSONObject jobj = (JSONObject) JSON.toJSON(info.get(i));
				List<ShopsGoodsImg> sgi = service.getImgByCompanyId(info.get(i).getId());
				List<ShopsGoodsImg> imgss = new ArrayList<ShopsGoodsImg>();
				for(int j=0;j<sgi.size();j++){
					ShopsGoodsImg sgii = sgi.get(j);
					sgii.setImgUrl(url+sgii.getImgUrl());
					imgss.add(sgii);
				}
				jobj.put("imgs", imgss);
				objs.add(jobj);
			}
			result.put("status", "1");
			result.put("message", "查询成功");
			result.put("data", objs);
			return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
		}catch(Exception e){
			e.printStackTrace();
			result.put("status", "1");
			result.put("message", "查询成功");
			return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
		}
	}
}

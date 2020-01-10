package com.jdry.pms.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.basicInfo.dao.BuildUnitDao;
import com.jdry.pms.basicInfo.dao.BuildingPropertyDao;
import com.jdry.pms.basicInfo.dao.HousePropertyDao;
import com.jdry.pms.basicInfo.pojo.BuildUnit;
import com.jdry.pms.basicInfo.pojo.BuildingProperty;
import com.jdry.pms.basicInfo.pojo.HouseOwner;
import com.jdry.pms.basicInfo.pojo.HouseProperty;
import com.jdry.pms.basicInfo.service.HousePropertyService;
import com.jdry.pms.lzmh.service.LzmhService;

@Repository
@Component
public class TestController implements IController {

	@Resource
	private LzmhService lzmhService;

	@Resource
	private HousePropertyService housePropertyService;

	@Resource
	private BuildingPropertyDao bpDao;

	@Resource
	private BuildUnitDao buDao;

	@Resource
	private HousePropertyDao hpDao;

	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		// TODO Auto-generated method stub
		PrintWriter out = arg1.getWriter();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		arg1.setContentType("text/html");
		arg1.setCharacterEncoding("UTF-8");
		String method = arg0.getParameter("method");
		try {
			// 同步楼宇
			if (method.equalsIgnoreCase("sycnBuildProp")) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				String hql = "from BuildingProperty du where 1=1 and lzId is null";
				List<BuildingProperty> lists = bpDao.query(hql, parameter);
				int listLen = lists.size();
				if (listLen > 0) {
					for (int i = 0; i < listLen; i++) {
						String strResul = lzmhService.addTenement(lists.get(i));
						System.out.print(strResul);
						resultMap.put("" + i, strResul);
					}
				} else {
					resultMap.put("message", "没有数据");
				}
			}
			// 同步单元
			if (method.equalsIgnoreCase("sycnBuildUnit")) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				String hql = "from BuildUnit du where 1=1 and lzId is null";
				List<BuildUnit> lists = buDao.findEntityByHQL(hql, parameter);
				int listLen = lists.size();
				if (listLen > 0) {
					for (int i = 0; i < listLen; i++) {
						String strResul = lzmhService.addUnit(lists.get(i));
						System.out.print(strResul);
						resultMap.put("" + i, strResul);
					}
				} else {
					resultMap.put("message", "没有数据");
				}
			}
			// 同步房间
			if (method.equalsIgnoreCase("sycnRoom")) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				String hql = "from HouseProperty du where 1=1 and lzId is null";
				List<HouseProperty> lists = hpDao.query(hql, parameter);
				int listLen = lists.size();
				if (listLen > 0) {
					for (int i = 0; i < listLen; i++) {
						String strResul = lzmhService.addRoom(lists.get(i));
						System.out.print(strResul);
						resultMap.put("" + i, strResul);
					}
				} else {
					resultMap.put("message", "没有数据");
				}
			}
			// 同步住户
			if (method.equalsIgnoreCase("sycnOwner")) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				String hql = "from HouseOwner du where 1=1 and lzId is null";
				List<HouseOwner> lists = hpDao.query(hql);
				int listLen = lists.size();
				if (listLen > 0) {
					for (int i = 0; i < listLen; i++) {
						String strResul = lzmhService.addOwnerInfo(lists.get(i));
						System.out.print(strResul);
						resultMap.put("" + i, strResul);
					}
				} else {
					resultMap.put("message", "没有数据");
				}
			}
			// 删除用户
			if (method.equalsIgnoreCase("delOwner")) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				String hql = "from HouseOwner du where 1=1 and lzId is not null ";
				List<HouseOwner> lists = hpDao.query(hql);
				int listLen = lists.size();
				if (listLen > 0) {
					for (int i = 0; i < listLen; i++) {
						int resident_id = lists.get(i).getLzId();
						String strResul = lzmhService.delOwnerInfo(resident_id);
						System.out.print(strResul);
						resultMap.put("" + i, strResul);
					}
				} else {
					resultMap.put("message", "没有数据");
				}
			}
			// 删除房间
			if (method.equalsIgnoreCase("delRoom")) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				String hql = "from HouseProperty du where 1=1 and lzId is not null ";
				List<HouseProperty> lists = hpDao.query(hql, parameter);
				int listLen = lists.size();
				if (listLen > 0) {
					for (int i = 0; i < listLen; i++) {
						int lzId = lists.get(i).getLzId();
						String strResul = lzmhService.delRoom(lzId);
						System.out.print(strResul);
						resultMap.put("" + i, strResul);
					}
				} else {
					resultMap.put("message", "没有数据");
				}
			}
			if (method.equalsIgnoreCase("test")) {
				housePropertyService.changeOwnerInfo("f8cc50775dc505eb015dcf674e610a4e", "admin", "0");
			}

			// 修改房间
			if (method.equalsIgnoreCase("modRoom")) {
				String sql = "SELECT build_id,build_name,community_id,community_name,belong_sb_id,storied_build_name,unit_id,"
						+ "unit_name,room_id,CONCAT(REPLACE(room_no,'栋',''),'01') as room_no,"
						+ "house_type,build_area,within_area,room_type,room_state,charge_object,advance_amount,remark,make_room_date,"
						+ "decorate_start_date,decorate_end_date,receive_room_date,decorate_plan_date,room_decorate_status,decorate_instructions,lz_id"
						+ " FROM t_house_property where community_name = '湖语御景' and  LENGTH(room_no) = 5 ";

				@SuppressWarnings("unchecked")
				List<HouseProperty> lists = hpDao.getSession().createSQLQuery(sql).addEntity(HouseProperty.class)
						.list();
				int listLen = lists.size();
				System.out.print(listLen);
				if (listLen > 0) {
					for (int i = 0; i < listLen; i++) {
						String strResul = lzmhService.modRoom(lists.get(i));

						System.out.print(strResul);
						resultMap.put("" + i, strResul);
					}
				} else {
					resultMap.put("message", "没有数据");
				}
			}
			//处理用户变更号码后，没有同步到联掌的问题
			if (method.equalsIgnoreCase("changePhone")) {
				String sqlStr= "select owner_id from t_tmp_changer";
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.println(JSON.toJSONString(resultMap));
			out.close();
		}
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/sycn/lzmh";
	}

	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

}

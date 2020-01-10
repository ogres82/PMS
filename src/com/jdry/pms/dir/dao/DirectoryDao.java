package com.jdry.pms.dir.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.dir.pojo.DirDirectory;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;

@Repository
public class DirectoryDao extends HibernateDao {

	public Collection<DirDirectory> queryAll(Page<DirDirectory> page){
		String hql = " from "+DirDirectory.class.getName();
		System.out.println("hql==="+hql);
		List<DirDirectory> list = this.query(hql);
		
//		System.out.println(Arrays.asList(list));
		return list;
	}

	public List<DirDirectoryDetail> queryAll(){
		String hql = " from "+DirDirectoryDetail.class.getName();
		List<DirDirectoryDetail> list = this.query(hql);
		return list;
	}
	
	public void queryAllByPage(Page<DirDirectory> page) throws Exception{
		this.pagingQuery(page, "from "+DirDirectory.class.getName(), "select count(*) from "+DirDirectory.class.getName());
	}
	
	/**根据设定编号，得到设定信息
	 * 
	 * @param code
	 * @return
	 */
	public Collection<DirDirectoryDetail> getDirectory(String code){
		
		//return this.query("from "+DirDirectoryDetail.class.getName() +" where code_detail = ");
		
		Map<String,Object> map =new HashMap<String,Object>();
	    String sql="from "+DirDirectoryDetail.class.getName() +" di";
	    if(code!=null&&!"".equals(code)){
	        map.put("code", code);
	        sql+=" where di.code=:code";
	    }
	    return this.query(sql, map);

	}
	
	/**保存数据
	 * 
	 * @param dirs
	 */
	public void saveAll(Collection<DirDirectory> dirs){
		Session session = this.getSessionFactory().openSession();
		//BusinessTest businessTest = new BusinessTest();
		
	    try{
	        for (DirDirectory dir : dirs) {
	            EntityState state=EntityUtils.getState(dir);
	            if (state.equals(EntityState.NEW)) {
	                session.saveOrUpdate(dir);
	            } else if (state.equals(EntityState.MODIFIED)) {
	                session.update(dir);
	            } else if (state.equals(EntityState.DELETED)) {
	                session.delete(dir);
	            }
	        }
	    }finally{
	        session.flush();
	        session.close();
	    }
	}
	
	/**得到具体配置明细名
	 * 
	 * @ClassName: @param code
	 * @ClassName: @param code_detail
	 * @ClassName: @return 
	 * @Description: TODO(这里用一句话描述这个类的作用) 
	 * @author 追梦de蜗牛 yongqiang347@163.com 
	 * @date 2016-1-13 上午11:06:14
	 */
	public String getDetailName(String code, String code_detail){
		String detailName="";
		Map<String,Object> map =new HashMap<String,Object>();
	    StringBuffer sql= new StringBuffer("from "+DirDirectoryDetail.class.getName() +" di");
	    sql.append(" where 1=1 ");
	    if(code!=null&&!"".equals(code)){
	        map.put("code", code);
	        sql.append(" and di.code=:code");
	    }
	    if(code_detail!=null&&!"".equals(code_detail)){
	        map.put("code_detail", code_detail);
	        sql.append(" and di.code_detail=:code_detail");
	    }
	    List<DirDirectoryDetail> lists = this.query(sql.toString(), map);
	    if(null != lists && lists.size()>0){
	    	detailName = lists.get(0).getCode_detail_name();
	    }
		return detailName;
	}
	
	
	/**根据设定编号，得到设定信息
	 * 
	 * @param code
	 * @return
	 */
	public Collection<DirDirectoryDetail> getDirectoryLikeCode(String code){
		
		//return this.query("from "+DirDirectoryDetail.class.getName() +" where code_detail = ");
		
	    String sql="select code,code_detail,code_detail_name from "+DirDirectoryDetail.class.getName() +" di";
	    if(code!=null&&!"".equals(code)){
	        sql+=" where di.code like '"+code+"%' and delete_id='0' order by code";
	    }
	    System.out.println(sql);
	    return this.query(sql);

	}
}

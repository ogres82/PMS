package com.jdry.pms.msgandnotice.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.hibernate.HibernateUtils;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.msgandnotice.dao.MessageDaoImpl;
import com.jdry.pms.msgandnotice.pojo.MessageEntity;
import com.jdry.pms.msgandnotice.service.MessageService;
/**
*
* @author 钟涛
*
*/
@Repository
@Component
public class MessageServiceImpl implements MessageService{
	@Resource
    private MessageDaoImpl messageDaoImpl;
	@Override
	@DataProvider
    public void getMessage(Page<MessageEntity> page, Map<String, Object> parameter,Criteria criteria)
            throws Exception {
        DetachedCriteria detachedCriteria = DetachedCriteria
                .forClass(MessageEntity.class);
        if(parameter == null){
			parameter = new HashMap<String,Object>();
		}
        String msgCreater = parameter.get("msgCreater")!=null?parameter.get("msgCreater").toString():"";
		String msgStatus = parameter.get("msgStatus")!=null?parameter.get("msgStatus").toString():"";
		String msgCreateTimeStart = parameter.get("msgCreateTime")!=null?parameter.get("msgCreateTime").toString():"";
		String msgCreateTimeEnd = parameter.get("msgCreateTimeEnd")!=null?parameter.get("msgCreateTimeEnd").toString():"";
		String msgBusinessId = parameter.get("msgBusinessId")!=null?parameter.get("msgBusinessId").toString():"";
		String msgSubject = parameter.get("msgSubject")!=null?parameter.get("msgSubject").toString():"";
		Map map =new HashMap<String,Object>();
	    String sql="from "+MessageEntity.class.getName()+" du where 1=1";
	    String sqlCount="select count(*) from "+MessageEntity.class.getName()+" du where 1=1";
	    if(!StringUtil.isEmpty(msgCreater)){
	        map.put("msgCreater",msgCreater);
	        sql+=" and du.msgCreater =:msgCreater";
	        sqlCount+=" and du.msgCreater =:msgCreater";
	    }
	    if(!StringUtil.isEmpty(msgStatus)){
	        map.put("msgStatus",msgStatus);
	        sql+=" and du.msgStatus =:msgStatus";
	        sqlCount+=" and du.msgStatus =:msgStatus";
	    }
	    if(!StringUtil.isEmpty(msgCreateTimeStart)){
			Date msgCreateTimeStartDate = DateUtil.parseToDate(msgCreateTimeStart);
	        map.put("msgCreateTime", msgCreateTimeStartDate);
	        sql+=" and du.msgCreateTime >=:msgCreateTime";
	        sqlCount+=" and du.msgCreateTime >=:msgCreateTime";
	    }
	    if(!StringUtil.isEmpty(msgCreateTimeEnd)){
			Date msgCreateTimeEndDate = DateUtil.parseToDate(msgCreateTimeEnd);
	        map.put("msgCreateTimeEnd", msgCreateTimeEndDate);
	        sql+=" and du.msgCreateTime <=:msgCreateTimeEnd";
	        sqlCount+=" and du.msgCreateTime <=:msgCreateTimeEnd";
	    }
	    if(!StringUtil.isEmpty(msgBusinessId)){
	        map.put("msgBusinessId", msgBusinessId);
	        sql+=" and du.msgBusinessId =:msgBusinessId";
	        sqlCount+=" and du.msgBusinessId=:msgBusinessId";
	    }
	    if(!StringUtil.isEmpty(msgSubject)){
	        map.put("msgSubject", "%"+msgSubject+"%");
	        sql+=" and du.msgSubject like:msgSubject";
	        sqlCount+=" and du.msgSubject like:msgSubject";
	    }
	    
        if (criteria != null) {
            HibernateUtils.createFilter(detachedCriteria, criteria);
        }
        messageDaoImpl.find(page,sql,sqlCount,map);
 
    }
	@Override
	@DataResolver
	public void saveMessage(Collection<MessageEntity> mes){
		messageDaoImpl.saveMessage(mes);
	}
	@Override
	@DataResolver
    @Transactional
    public void saveAll(List<MessageEntity> customer) {
		messageDaoImpl.saveMessage(customer);
    }
	
}

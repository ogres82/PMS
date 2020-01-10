package com.jdry.pms.msgandnotice.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.msgandnotice.dao.MsgTempDaoImpl;
import com.jdry.pms.msgandnotice.pojo.MessageTempMain;
import com.jdry.pms.msgandnotice.service.MsgTempService;
@Repository
@Component
public class MsgTempServiceImpl implements MsgTempService{
	@Resource
    private MsgTempDaoImpl msgTempDaoImpl;
	@Override
	public void saveMsgTemp(Collection<MessageTempMain> mes) {
		msgTempDaoImpl.saveMsgTemp(mes);
	}

	@Override
	public void getMsgTemp(Page<MessageTempMain> page,
			Map<String, Object> parameter, Criteria criteria) {
		Map map =new HashMap<String,Object>();
	    String sql="from "+MessageTempMain.class.getName()+" du where 1=1 order by du.msgTempCtime desc";
	    String sqlCount="select count(*) from "+MessageTempMain.class.getName()+" du where 1=1";
	    msgTempDaoImpl.getMsgTemp(page, sql, sqlCount, map);
	}

	@Override
	public Collection<MessageTempMain> getAllMsgTemp() {
	    String sql="from "+MessageTempMain.class.getName()+" du where 1=1";
	    Collection<MessageTempMain> result=msgTempDaoImpl.getAllMsgTemp(sql);
		return result;
	}

	@Override
	public String findTempById(String tempId) {
		String content="";
		Map map =new HashMap<String,Object>();
		map.put("tempSubject", "%"+tempId+"%");
	    String sql="from "+MessageTempMain.class.getName()+" du where 1=1 and du.msgTempSubject like:tempSubject";
		List<MessageTempMain> result=(List) msgTempDaoImpl.getTempById(sql,map);
		if(result!=null){
			if(result.size()>0){
				MessageTempMain messageTempMain=result.get(0);
				content=messageTempMain.getMsgTempContent();
			}
		}
		// TODO Auto-generated method stub
		return content;
	}

	@Override
	public MessageTempMain getTempById(String tempId) {
		Map map =new HashMap<String,Object>();
		MessageTempMain messageTempMain=null;
		map.put("msgTempId", tempId);
	    String sql="from "+MessageTempMain.class.getName()+" du where 1=1 and du.msgTempId =:msgTempId";
	    List<MessageTempMain> result=(List) msgTempDaoImpl.getTempById(sql,map);
	    if(result!=null){
			if(result.size()>0){
				 messageTempMain=result.get(0);
			}
		}
	    return messageTempMain;
	}

	@Override
	public void updateTemp(Collection<MessageTempMain> mes) {
		msgTempDaoImpl.updateTemp(mes);
		
	}

	@Override
	public void deleteTempById(String tempId) {
		// TODO Auto-generated method stub
		msgTempDaoImpl.deleteTempById(tempId);

	}

}

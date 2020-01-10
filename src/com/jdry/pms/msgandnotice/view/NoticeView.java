package com.jdry.pms.msgandnotice.view;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.msgandnotice.pojo.MsgandnotNoticeMain;
import com.jdry.pms.msgandnotice.pojo.MsgandnoticeNoticeAuditinfo;
import com.jdry.pms.msgandnotice.service.NoticeService;

/**
*
* @author 钟涛
*
*/
@Component
public class NoticeView {
	@Resource
    public NoticeService noticeService;
    @DataProvider
    /*
     * 获取我待审核的公告
     */
    public void getNotice(Page<MsgandnotNoticeMain> page, Map<String, Object> parameter,Criteria criteria){
         try {
			noticeService.getNotice(page, parameter, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @DataProvider
    /*
     * 获取我所有的公告
     */
    public void getMyAllNotice(Page<MsgandnotNoticeMain> page, Map<String, Object> parameter,Criteria criteria){
         try {
			noticeService.getNotice(page, parameter, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /*
     * 获取我被驳回的公告
     */
    @DataProvider
    public void getMyNoticeReturn(Page<MsgandnotNoticeMain> page, Map<String, Object> parameter,Criteria criteria){
         try {
			noticeService.getMyNoticeReturn(page, parameter, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /*
     * 获取待我发布的公告
     */
    @DataProvider
    public void getMyWaitPubNotice(Page<MsgandnotNoticeMain> page, Map<String, Object> parameter,Criteria criteria){
         try {
			noticeService.getMyWaitPubNotice(page, parameter, criteria,null);

         } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /*
     * 获取所有已经发布的公告
     */
    @DataProvider
    public void getAllNotice(Page<MsgandnotNoticeMain> page, Map<String, Object> parameter,Criteria criteria){
         try {
			noticeService.getAllNotice(page, parameter, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /*
     * 获取我起草的公告
     */
    @DataProvider
    public void getMyCaogao(Page<MsgandnotNoticeMain> page, Map<String, Object> parameter,Criteria criteria){
         try {
			noticeService.getMyCaogao(page, parameter, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @DataProvider
    public void getAuditInfo(Page<MsgandnoticeNoticeAuditinfo> page, Map<String, Object> parameter,Criteria criteria){
         try {
			noticeService.getAuditInfo(page, parameter, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @DataProvider
    /*
     * 获取待我审核的公告
     */
    public void getMyAuditNotice(Page<MsgandnotNoticeMain> page, Map<String, Object> parameter,Criteria criteria){
         try {
			noticeService.getMyAuditNotice(page, parameter, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @DataResolver
	public void saveNotice(Collection<MsgandnotNoticeMain> mes){
    	
    	noticeService.saveNotice(mes,null);
	}
    @Expose
    public String getBusinessId() {
		CommUtil cu=new CommUtil();
		String businessId="test001";
        return businessId;
    }
}

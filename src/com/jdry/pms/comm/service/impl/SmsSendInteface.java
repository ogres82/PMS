package com.jdry.pms.comm.service.impl;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jdry.pms.comm.util.CommEnum;
import com.jdry.pms.comm.util.SpringUtil;
import com.jdry.pms.msgandnotice.pojo.MessageTempMain;
import com.jdry.pms.msgandnotice.service.MsgTempService;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.SendBatchSmsInfo;
import com.yunpian.sdk.service.SmsOperator;
import com.yunpian.sdk.service.YunpianRestClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 短信http接口的java代码调用示例
 * 基于Apache HttpClient 4.3
 *
 * @author songchao
 * @since 2015-04-03
 */
@Repository
@Component
public class SmsSendInteface {
	
	@Resource
    private MsgTempService msgTempService;
	@Resource
	private SmsSendInteface smsSendInteface;
    //查账户信息的http地址
    private static String URI_GET_USER_INFO = "https://sms.yunpian.com/v2/user/get.json";

    //智能匹配模版发送接口的http地址
    private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";

    //模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";

    //发送语音验证码接口的http地址
    private static String URI_SEND_VOICE = "https://voice.yunpian.com/v2/voice/send.json";

    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";
    
    //个性化批量发送http地址
    private static String URI_MULTI_SEND_SMS="https://sms.yunpian.com/v2/sms/multi_send.json";
    public  void main(String[] args) throws IOException, URISyntaxException {

        //修改为您的apikey.apikey可在官网（http://www.yuanpian.com)登录后获取
        String apikey = "f73a3ab1efe48b7300b014f36f9a3344";

        //修改为您要发送的手机号
        String mobile = "13985003206";

        /**************** 查账户信息调用示例 *****************/
        //System.out.println(SmsSendInteface.getUserInfo(apikey));

        /**************** 使用智能匹配模版接口发短信(推荐) *****************/
        //设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        String text = "【乐湾国际】尊敬的用户，您本次验证码为653242，验证码有效期为1分钟，请尽快验证。";
        //发短信调用示例
       // System.out.println(JavaSmsApi.sendSms(apikey, text, mobile));

        /**************** 使用指定模板接口发短信(不推荐，建议使用智能匹配模版接口) *****************/
        //设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
        //long tpl_id = 1;
        //设置对应的模板变量值

        String tpl_value = URLEncoder.encode("#code#",ENCODING) +"="
            + URLEncoder.encode("1234", ENCODING) + "&"
            + URLEncoder.encode("#company#",ENCODING) + "="
            + URLEncoder.encode("云片网",ENCODING);
        sendSms(apikey,text,mobile,"");
        //模板发送的调用示例
        //System.out.println(tpl_value);
        //System.out.println(SmsSendInteface.tplSendSms(apikey, tpl_id, tpl_value, mobile));

        /**************** 使用接口发语音验证码 *****************/
        String code = "1234";
        //System.out.println(JavaSmsApi.sendVoice(apikey, mobile ,code));
    }
    /**
     * 智能匹配模版接口发短信
     *
     * @param apikey apikey
     * @param text   　短信内容
     * @param mobile 　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */

    public  String sendSms(String apikey, String text, String mobile,String template) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        return post(URI_SEND_SMS, params);
    }
    /**
     * 智能匹配模版接口发短信
     *
     * @param apikey apikey
     * @param code   　验证码
     * @param mobile 　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */
    public String sendSmsByCode(String code, String mobile,String template) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        if(msgTempService == null){
        	msgTempService = (MsgTempService) SpringUtil.getObjectFromApplication("msgTempServiceImpl");
		}
        MessageTempMain messageTempMain=msgTempService.getTempById(template);
        String text=messageTempMain.getMsgTempContent();
        String text1=text.replaceAll("</?[^>]+>", "");
        String text2=text1.replace("#code#", code);
        params.put("apikey", CommEnum.API_KEY);
        params.put("text", text2);
        params.put("mobile", mobile);
        String rtnResult= post(URI_SEND_SMS, params);
        return rtnResult;
    }
    //批量方式短信
    public  String sendMultiSms(String text, String mobile,String template) throws IOException {
    	List<String> textList = new ArrayList<String>();
    	List<String> phoneList = new ArrayList<String>();
    	String[] textArray = text.split(",");
    	String[] phoneArray = mobile.split(",");
    	for(int i=0;i<textArray.length;i++){
    		textList.add(textArray[i]);
    		phoneList.add(phoneArray[i]);
    	}
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("apikey", CommEnum.API_KEY);
//        params.put("text", text);
//        URLEncoder.encode(text, "UTF-8");
//        params.put("mobile", mobile);
//        String rtnResult= post(URI_MULTI_SEND_SMS, params);
    	ResultDO<SendBatchSmsInfo> result = this.multiSendSms(CommEnum.API_KEY, phoneList, textList);
        return null;
    }
    //短信模块批量发送
    public  String sendSmsForMessage(String text, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", CommEnum.API_KEY);
        params.put("text", text);
        params.put("mobile", mobile);
        return post(URI_MULTI_SEND_SMS, params);
    }
    
    /**
     * 使用JDK发送批量短信,智能匹配短信模板
     *
     * @param apikey 成功注册后登录云片官网,进入后台可查看
     * @param text   短信内容集合,需要使用已审核通过的模板或者默认模板
     * @param mobile 手机号集合
     */
    public ResultDO<SendBatchSmsInfo> multiSendSms(String apikey, List<String> mobile, List<String> text) {
        YunpianRestClient client = new YunpianRestClient(apikey);//用apikey生成client,可作为全局静态变量
        SmsOperator smsOperator = client.getSmsOperator();//获取所需操作类
        ResultDO<SendBatchSmsInfo> result = smsOperator.multiSend(mobile, text);//此重载函数支持list,无需自己encode
        System.out.println(result);
        return result;
    }
    
    /**
     * 取账户信息
     *
     * @return json格式字符串
     * @throws java.io.IOException
     */

    public  String getUserInfo(String apikey) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        return post(URI_GET_USER_INFO, params);
    }

    /**
     * 通过模板发送短信(不推荐)
     *
     * @param apikey    apikey
     * @param tpl_id    　模板id
     * @param tpl_value 　模板变量值
     * @param mobile    　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */

    public  String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return post(URI_TPL_SEND_SMS, params);
    }

    /**
     * 通过接口发送语音验证码
     * @param apikey apikey
     * @param mobile 接收的手机号
     * @param code   验证码
     * @return
     */

    public static String sendVoice(String apikey, String mobile, String code) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("mobile", mobile);
        params.put("code", code);
        return post(URI_SEND_VOICE, params);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */

    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}

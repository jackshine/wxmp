package com.jeekhan.wx.msg;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.api.AccessToken;
import com.jeekhan.wx.utils.HttpUtils;

/**
 * 模板消息
 * 1、模板消息仅用于公众号向用户发送重要的服务通知，只能用于符合其要求的服务场景中，如信用卡刷卡通知，商品购买成功通知等。不支持广告等营销类消息以及其它所有可能对用户造成骚扰的消息。
 * 2、模板消息的定位是用户触发后的通知消息，不允许在用户没做任何操作或未经用户同意接收的前提下，主动下发消息给用户。目前在特殊情况下允许主动下发的消息只有故障类和灾害警示警告类通知，除此之外都要经过用户同意或用户有触发行为才能下发模板消息。
 * @author Jee Khan
 *
 */
@Component
public class TemplateMsgHandle {
	private static Logger log = LoggerFactory.getLogger(TemplateMsgHandle.class);

	/**
	 * 设置所属行业
	 * 设置行业可在MP中完成，每月可修改行业1次，账号仅可使用所属行业中相关的模板
	 * @param industry_id1	行业1代码，具体参照行业代码查询
	 * @param industry_id2	行业2代码，
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject setIndustry(String industry_id1,String industry_id2) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("industry_id1", industry_id1);
		jsonObj.put("industry_id2", "industry_id2");
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" + token;
		log.info("设置所属行业（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("设置所属行业（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取设置的行业信息 
	 * @return 
	 * {
	 * 	"primary_industry":{"first_class":"运输与仓储","second_class":"快递"},
	 * 	"secondary_industry":{"first_class":"IT科技","second_class":"互联网|电子商务"}
	 * }
	 * @throws JSONException 
	 */
	public JSONObject getIndustry() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=" + token;
		log.info("获取设置的行业信息（GET）：" + url);
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取设置的行业信息（GET）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取行业模板,添加至账号模板列表
	 * @param template_id_short 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式 
	 * @return	{"errcode":0,"errmsg":"ok","template_id":"Doclyl5uP7Aciu-qZ7mJNPtWkbkYnWBWVja26EGbNyk"}
	 * @throws JSONException 
	 */
	public JSONObject addPrivateTemplate(String template_id_short) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("template_id_short", template_id_short);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=" + token;
		log.info("获取行业模板（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取行业模板（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取模板列表 ,获取已添加至帐号下所有模板列表
	 * http请求方式：GET https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN
	 * @return
	 * {	
	 *  "template_list": [{
	 *       "template_id": "iPk5sOIt5X_flOVKn5GrTFpncEYTojx6ddbt8WYoV5s",
	 *       "title": "领取奖金提醒",
	 *       "primary_industry": "IT科技",
	 *       "deputy_industry": "互联网|电子商务",
	 *       "content": "{ {result.DATA} }\n\n领奖金额:{ {withdrawMoney.DATA} }\n领奖  时间:{ {withdrawTime.DATA} }\n银行信息:{ {cardInfo.DATA} }\n到账时间:  { {arrivedTime.DATA} }\n{ {remark.DATA} }",
	 *       "example": "您已提交领奖申请\n\n领奖金额：xxxx元\n领奖时间：2013-10-10 12:22:22\n银行信息：xx银行(尾号xxxx)\n到账时间：预计xxxxxxx\n\n预计将于xxxx到达您的银行卡"
	 *    }]
	 * }
	 * @throws JSONException 
	 */
	public JSONObject getAllPrivateTemplate() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=" + token;
		log.info("获取模板列表（GET）：" + url );
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取模板列表（GET）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 删除个人模板 
	 * @param template_id	公众帐号下模板消息ID
	 * @return
	 * {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	public JSONObject deletePrivateTemplate(String template_id) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("template_id", template_id);
		String token = AccessToken.getAccessToken();
		String url = " https://api,weixin.qq.com/cgi-bin/template/del_private_template?access_token=" + token;
		log.info("获取行业模板（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取行业模板（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	/**
	 * 发送模板消息 
	 * @param title		标题名称，颜色
	 * @param templateId	消息模板ID
	 * @param url	跳转url
	 * @param appId 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）
	 * @param pagepath 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar）
	 * @param keynotes	参数[内容，颜色]
	 * @param reamrk		备注信息，颜色
	 * @return   {"errcode":0,"errmsg":"ok","msgid":200228332}
	 * @throws JSONException 
	 */
	public JSONObject sendMsg(String toUser,String templateId,String url,String appId,String pagepath,String[] title,List<String[]> keynotes,String[] remark) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("template_id", templateId);
		jsonObj.put("url", url);
		JSONObject data = new JSONObject();
		JSONObject firstObj = new JSONObject();
		firstObj.put("value", title[0]);
		firstObj.put("color", title[1]);
		JSONObject remarkObj = new JSONObject();
		remarkObj.put("value", remark[0]);
		remarkObj.put("color", remark[1]);
		for(int i=0;i<keynotes.size();i++){
			JSONObject keynoteObj = new JSONObject();
			keynoteObj.put("value", keynotes.get(i)[0]);
			keynoteObj.put("color", keynotes.get(i)[1]);
			data.put("keynote" + (i+1), keynoteObj);
		}
		data.put("first", firstObj);
		data.put("remark", remarkObj);
		jsonObj.put("data", data);
		String token = AccessToken.getAccessToken();
		String sendurl = " https://api,weixin.qq.com/cgi-bin/template/del_private_template?access_token=" + token;
		log.info("发送模板消息 （POST）：" + sendurl + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(sendurl, jsonObj);
		log.info("发送模板消息 （POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	
}

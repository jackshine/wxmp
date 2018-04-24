package com.jeekhan.wx.api;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeekhan.wx.utils.HttpUtils;
import com.jeekhan.wx.utils.SunSHAUtils;


public class JSAPITicket {
	private static Logger log = LoggerFactory.getLogger(JSAPITicket.class);
	private static String JSAPI_TICKET = "";	//访问凭证
	private static long LASTUPDTIME = 0L;		//上次获取时间
	private static long EXPIRESIN = 0L;			//有效时间
	
	/**
	 * 获取调用微信JS接口的临时票据：JSAPI_TICKET
	 * @throws JSONException 
	 */
	public static String getJSAPITicket() throws JSONException{
		long cutTime = System.currentTimeMillis();
		long needTime = LASTUPDTIME + EXPIRESIN*1000 + 10000;//提前10更新
		if(JSAPI_TICKET == null || JSAPI_TICKET.length()<1 || cutTime >= needTime){
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi ";
			url.replaceAll("ACCESS_TOKEN", AccessToken.getAccessToken());
			String result = null;
			JSONObject json = null;
			//{"errcode":0,"errmsg":"ok","ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA","expires_in":7200}
			result = HttpUtils.doGet(url);
			json = new JSONObject(result);
			if(json != null){
				if(json.has("ticket")){
					JSAPI_TICKET = json.getString("ticket");
					EXPIRESIN = json.getLong("expires_in");
					LASTUPDTIME = System.currentTimeMillis();
					log.info("JSAPI_TICKET获取返回成功：" + result);
					return JSAPI_TICKET;
				}else if(json.has("errcode")){
					log.info("JSAPI_TICKET获取返回失败，失败信息：" + json.getString("errmsg"));
				}
			}
		}
		return null;
	}
	
	//生成JS-SDK权限验证的签名
	public static String signature(String url,long timestamp,String noncestr) throws JSONException {
		String jsapi_ticket = getJSAPITicket();
		String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr="+ noncestr +"&timestamp="+ timestamp + "&url=" + url;
		String ret = null;
		try {
			ret = SunSHAUtils.encodeSHAHex(string1);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
}

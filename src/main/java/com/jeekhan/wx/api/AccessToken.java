package com.jeekhan.wx.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.utils.HttpUtils;



/**
 * 调用微信通用、基础API
 * @author Jee Khan
 *
 */
@Component  // 配置文件中的前缀
public class AccessToken {
	private static Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String APPID = "wx8864bd7398aa5270	";		//公众号的唯一标识 
	private static String APPSECRET = "c2c15a91c950e8a6fc106192b044adfb";
	private static String ACCESS_TOKEN = "";	//访问凭证
	private static long LASTUPDTIME = 0L;	//上次获取时间
	private static long EXPIRESIN = 0L;		//有效时间
	
	@Value("${wx.APPID}")
    public void setAPPID(String APPID) {
		AccessToken.APPID = APPID;
    }

    @Value("${wx.APPSECRET}")
    public void setAPPSECRET(String APPSECRET) {
    		AccessToken.APPSECRET = APPSECRET;
    }
	
	/**
	 * 获取微信接口访问凭证：ACCESS_TOKEN
	 * @throws JSONException 
	 */
	public static synchronized String getAccessToken() throws JSONException{
		long cutTime = System.currentTimeMillis();
		long needTime = LASTUPDTIME + EXPIRESIN*1000 + 10000;//提前10s更新
		if(ACCESS_TOKEN == null || ACCESS_TOKEN.length()<1 || cutTime >= needTime){
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
			url = url.replaceAll("APPID", APPID.trim()).replaceAll("APPSECRET", APPSECRET.trim());
			String result = null;
			JSONObject json = null;
			//返回格式：{"access_token":"ACCESS_TOKEN","expires_in":7200}
			//		   {"errcode":40013,"errmsg":"invalid appid"}
			result = HttpUtils.doGet(url);
			json = new JSONObject(result);
			if(json != null){
				if(json.has("access_token")){
					ACCESS_TOKEN = json.getString("access_token");
					EXPIRESIN = json.getLong("expires_in");
					LASTUPDTIME = System.currentTimeMillis();
					log.info("ACCESS_TOKEN获取返回成功：" + result);
					return ACCESS_TOKEN;
				}else if(json.has("errcode")){
					log.info("ACCESS_TOKEN获取返回失败，失败信息：" + json.getString("errmsg"));
				}
			}
		} else if(ACCESS_TOKEN.length()>1 && cutTime < needTime) {
			return ACCESS_TOKEN;
		}
		return null;
	}
	
	public static void main(String[] args) {
		try {
			String tk = getAccessToken();
			System.out.println(tk);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}

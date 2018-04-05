package com.jeekhan.wx.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.jeekhan.wx.utils.HttpUtils;


/**
 * 网页授权获取用户基本信息
 * @author Jee Khan
 *
 */
public class WebAuth {
	private static Logger log = LoggerFactory.getLogger(WebAuth.class);
	@Value("${wx.APPID}")
	private static String APPID ;		//公众号的唯一标识 
	@Value("${wx.APPSECRET}")
	private static String APPSECRET;
	public String LANG = "zh_CN";		//国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语 
	public String web_access_token = null;	//网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同 
	private long expires_in = 0;			//access_token接口调用凭证超时时间，单位（秒）
	private long last_update = 0;		//上次获取时间
	private String refresh_token = null;	//用户刷新access_token 
	private String openId = null;		//用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID 	
	private String scope = null;			//用户授权的作用域，使用逗号（,）分隔 
	private Object userInfo = null;		//用户信息
	private String code = null;			//用户授权获取的code参数
	/**
	 * 用户同意授权，获取code；
	 *  生成请求授权页面
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	 * @param redirect_uri	授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	 * @param scope			应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息） 
	 * @param state			重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * @throws UnsupportedEncodingException 
	 */
	public static String getCode(String redirect_uri ,String scope ,String state) throws UnsupportedEncodingException{
		String redUrl = URLEncoder.encode(redirect_uri, "utf-8");
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=" + APPID
				+ "&redirect_uri=" + redUrl
				+ "&response_type=code"
				+ "&scope=" + scope
				+ "&state=" + state
				+ "#wechat_redirect";
		return url;
	}
	/**
	 * 设置用户授权获取的code
	 * @param code	用户授权获取的code
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * 通过code换取网页授权access_token
	 * @param code	用户授权code参数 
	 * @return [access_token,openid]
	 * @throws JSONException 
	 */
	public String[] getAccessToken() throws JSONException{
		if(code == null){
			throw new RuntimeException("用户授权code参数未设置");
		}
		long cutTime = System.currentTimeMillis();
		long needTime = last_update + expires_in*1000 + 10000;//提前10更新
		if(web_access_token == null || openId == null){

			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			url.replaceAll("APPID", APPID).replaceAll("SECRET", APPSECRET).replaceAll("CODE", code);
			String result = null;
			JSONObject json = null;
			result = HttpUtils.doGetSSL(url);
			json = new JSONObject(result);
			if(json != null){
				if(json.has("access_token")){
					web_access_token = json.getString("access_token");
					expires_in = json.getLong("expires_in");
					last_update = System.currentTimeMillis();
					refresh_token = json.getString("refresh_token");
					openId = json.getString("openid");
					log.info("WEB_ACCESS_TOKEN获取返回成功：" + result);
				}else if(json.has("errcode")){
					log.info("WEB_ACCESS_TOKEN获取返回失败，失败信息：" + result);
				}
			}
		}else if(cutTime >= needTime){	//刷新
			String result = null;
			JSONObject json = null;
			String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
			url.replaceAll("APPID", APPID).replaceAll("REFRESH_TOKEN", refresh_token);
			result = HttpUtils.doGetSSL(url);
			json = new JSONObject(result);
			if(json != null){
				if(json.has("access_token")){
					web_access_token = json.getString("access_token");
					expires_in = json.getLong("expires_in");
					last_update = System.currentTimeMillis();
					refresh_token = json.getString("refresh_token");
					openId = json.getString("openid");
					log.info("WEB_ACCESS_TOKEN获取返回成功：" + result);
				}else if(json.has("errcode")){
					log.info("WEB_ACCESS_TOKEN获取返回失败，失败信息：" + result);
				}
			}
		}
		return new String[]{web_access_token,openId};
	}
	
	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 * @return
	 * @throws JSONException 
	 */
	public Object getUserInfo() throws JSONException{
		getAccessToken();
		if(userInfo != null){
			return userInfo;
		}
		getAccessToken();
		if(!"snsapi_userinfo".equals(scope)){
			throw new RuntimeException("需scope为 snsapi_userinfo 才可获取用户信息");
		}
		String url= "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url.replaceAll("ACCESS_TOKEN", this.web_access_token).replaceAll("OPENID", this.openId).replaceAll("zh_CN", LANG);
		String result = HttpUtils.doGetSSL(url);
		JSONObject json = new JSONObject(result);
		userInfo = json;
		return userInfo;
	}
	
	/**
	 * 检验授权凭证（access_token）是否有效
	 * @return
	 * @throws JSONException 
	 */
	public boolean checkAuth() throws JSONException{
		String url= "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
		url.replaceAll("ACCESS_TOKEN", this.web_access_token).replaceAll("OPENID", this.openId);
		String result = HttpUtils.doGetSSL(url);
		JSONObject json = new JSONObject(result);
		if(json.has("errcode") && json.getInt("errcode") == 0){
			return true;
		}else{
			return false;
		}
	}
}

package com.jeekhan.wx.api;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeekhan.wx.utils.HttpUtils;

/**
 * 通用处理类
 * @author jeekhan
 *
 */
public class CommonHandle {
	private static Logger log = LoggerFactory.getLogger(CommonHandle.class);
	
	/**
	 * 获取微信服务器IP地址
	 * @param ticket
	 * @return 以逗号分隔的IP地址
	 * @throws JSONException 
	 */
	public static String getWXIP() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN" + token;
		log.info("获取微信服务器IP地址(GET):" + url);
		String result = HttpUtils.doGet(url);
		//返回格式：{    "ip_list": [        "127.0.0.1",         "127.0.0.2",         "101.226.103.0/25"    ]}
		//		  {"errcode":40013,"errmsg":"invalid appid"}
		JSONObject json = new JSONObject(result);
		if(json != null){
			if(json.has("ip_list")){
				String ips = json.getJSONArray("ip_list").join(",");
				log.info("获取微信服务器IP地址：" + ips);
				return ips;
			}else if(json.has("errcode")){
				log.info("获取微信服务器IP地址，失败信息：" + json.getString("errmsg"));
			}
		}
		return null;
	}
}

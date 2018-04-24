package com.jeekhan.wx.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeekhan.wx.api.JSAPITicket;

/**
 * 微信网页需要使用的JSAPI服务控制类
 * @author jeekhan
 *
 */
@RestController
@RequestMapping("/jsapi")
public class JSAPIAction {
	
	/**
	 * 对指定URL生成签名
	 * @param url	待签名的URL，需要调用jsapi的网页的完整URL
	 * @param timestamp	参与签名的时间戳
	 * @param nonceStr 参与签名的随机字符串
	 * @return {"errcode":0,"errmsg",:"ok","signature":""}
	 * @throws JSONException 
	 */
	@RequestMapping("/sign")
	public String signUrl(String url,Long timestamp,String nonceStr) throws Exception{
		JSONObject jsonRet = new JSONObject();
		try {
			String signature = JSAPITicket.signature(url, timestamp, nonceStr);
			if(signature != null) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
				jsonRet.put("signature", signature);
				return jsonRet.toString();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		jsonRet.put("errcode", -1);
		jsonRet.put("errmsg", "生成签名失败！");
		return jsonRet.toString();
	}

}

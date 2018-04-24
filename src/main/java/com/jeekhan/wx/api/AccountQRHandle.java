package com.jeekhan.wx.api;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.utils.HttpUtils;


/**
 * 公众账号管理
 * @author Jee Khan
 * 为了满足用户渠道推广分析和用户帐号绑定等场景的需要，公众平台提供了生成带参数二维码的接口。
 * 使用该接口可以获得多个带不同场景值的二维码，用户扫描后，公众号可以接收到事件推送。
 */
@Component
public class AccountQRHandle {
	private  Logger log = LoggerFactory.getLogger(AccountQRHandle.class);
	/**
	 * 创建临时二维码，获取ticket
	 * @param sence_id		场景值ID，临时二维码时为32位非0整型
	 * @param expire_seconds	该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒
	 * @return 
	 * {
	 * "ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==",//获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
	 * "expire_seconds":60, //该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）
	 * "url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI"//二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	 * }
	 * @throws JSONException 
	 */
	public JSONObject createTempTicket(int sence_id,int expire_seconds ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("expire_seconds", expire_seconds);
		jsonObj.put("action_name", "QR_SCENE");
		JSONObject action_info = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_id", sence_id);
		action_info.put("scene",scene);
		jsonObj.put("action_info", action_info);
		log.info("创建公众号临时二维码（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("创建公众号临时二维码（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 创建临时二维码，获取ticket
	 * @param scene_str		场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
	 * @param expire_seconds	该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒
	 * @return 
	 * {
	 * "ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==",//获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
	 * "expire_seconds":60, //该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）
	 * "url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI"//二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	 * }
	 * @throws JSONException 
	 */
	public JSONObject createTempTicket(String scene_str,int expire_seconds ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("expire_seconds", expire_seconds);
		jsonObj.put("action_name", "QR_STR_SCENE");
		JSONObject action_info = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_str", scene_str);
		action_info.put("scene",scene);
		jsonObj.put("action_info", action_info);
		log.info("创建公众号临时二维码（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("创建公众号临时二维码（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 创建永久二维码，获取ticket
	 * @param scene_str		场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
	 * @return 
	 * {"ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==", //获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
	 * "url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI" //二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	 * }
	 * @throws JSONException 
	 */
	public JSONObject createTicket(String scene_str) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action_name", "QR_LIMIT_STR_SCENE");
		JSONObject action_info = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_str", scene_str);
		action_info.put("scene",scene);
		jsonObj.put("action_info", action_info);
		log.info("创建公众号永久二维码（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("创建公众号永久二维码（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 创建永久二维码，获取ticket
	 * @param sence_id		场景值ID，永久二维码时最大值为100000（目前参数只支持1--100000） 
	 * @return 
	 * {"ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==", //获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
	 * "url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI" //二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	 * }
	 * @throws JSONException 
	 */
	public JSONObject createTicket(int scene_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action_name", "QR_LIMIT_SCENE");
		JSONObject action_info = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_id", scene_id);
		action_info.put("scene",scene);
		jsonObj.put("action_info", action_info);
		log.info("创建公众号永久二维码（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("创建公众号永久二维码（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 通过ticket换取二维码
	 * @param ticket
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public File getQRCodeImg(String ticket) throws UnsupportedEncodingException{
		String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket,"utf8");
		log.info("通过ticket换取二维码(GET):" + url);
		File file = HttpUtils.downloadFileSSL(url);
		if(file != null){
			log.info("通过ticket换取二维码(GET)成功");
		}else{
			log.info("通过ticket换取二维码(GET)失败");
		}
		return file;
	}
	
	/**
	 * 长链接转短链接接口
	 * 用于生成二维码的原链接（商品、支付二维码等）太长导致扫码速度和成功率下降，将原长链接通过此接口转成短链接再生成二维码将大大提升扫码速度和成功率。
	 * @param longUrl	需要转换的长链接，支持http://、https://、weixin://wxpay 格式的url
	 * @return {"errcode":0,"errmsg":"ok","short_url":"http:\/\/w.url.cn\/s\/AvCo6Ih"}
	 * @throws JSONException
	 */
	public JSONObject getShortUrl(String longUrl) throws JSONException {
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action", "long2short");
		jsonObj.put("long_url", longUrl);

		log.info("长链接转短链接接口（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("长链接转短链接接口（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
}



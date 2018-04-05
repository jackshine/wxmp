package com.jeekhan.wx.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.utils.HttpUtils;



/**
 * 客服帐号管理
 * 每个公众号最多添加10个客服账号
 * 
 * @author Jee Khan
 *
 */
@Component
public class KFAccountHandle {
	private  Logger log = LoggerFactory.getLogger(KFAccountHandle.class);
	/**
	 * 添加客服帐号
	 * http请求方式: POST https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN
	 * @param account 账号名称
	 * @param nickname	昵称
	 * @param pwd	密码明文的md5
	 * @return {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	public  JSONObject addKF(String account,String nickname,String pwd) throws JSONException{
		String jsonStr = "{\"kf_account\":\""+ account +"\",\"nickname\":\""+nickname+"\",\"password\":\""+pwd+"\"}";
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=" + token;
		log.info("添加客服帐号（POST）：" + url + ",参数：" + jsonStr);
		String ret = HttpUtils.doPostSSL(url, jsonStr);
		log.info("添加客服帐号（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}

	/**
	 * 修改客服帐号
	 * http请求方式: POST https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN
	 * @param account 账号名称
	 * @param nickname	昵称
	 * @param pwd	密码明文的md5	 
	 * @return {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	public  JSONObject updateKF(String account,String nickname,String pwd) throws JSONException{
		String jsonStr = "{\"kf_account\":\""+ account +"\",\"nickname\":\""+nickname+"\",\"password\":\""+pwd+"\"}";
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=" + token;
		log.info("修改客服帐号（POST）：" + url + ",参数：" + jsonStr);
		String ret = HttpUtils.doPostSSL(url, jsonStr);
		log.info("修改客服帐号（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 删除客服帐号
	 * http请求方式: POST https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN
	 * @param account 账号名称
	 * @param nickname	昵称
	 * @param pwd	密码明文的md5	 
	 * @return {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	public  JSONObject deleteKF(String account,String nickname,String pwd) throws JSONException{
		String jsonStr = "{\"kf_account\":\""+ account +"\",\"nickname\":\""+nickname+"\",\"password\":\""+pwd+"\"}";
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=" + token;
		log.info("修改客服帐号（POST）：" + url + ",参数：" + jsonStr);
		String ret = HttpUtils.doPostSSL(url, jsonStr);
		log.info("修改客服帐号（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 设置客服帐号的头像
	 * 开发者可调用本接口来上传图片作为客服人员的头像，头像图片文件必须是jpg格式，推荐使用640*640大小的图片以达到最佳效果
	 * 调用示例：使用curl命令，用FORM表单方式上传一个多媒体文件，curl命令的具体用法请自行了解
	 * http请求方式: POST/FORM
     * http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
     * @return
	 * @throws JSONException 
	 */
	public  JSONObject uploadHeadImg(String account,File headJpgImg) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=" + token;
		Map<String,String> paramPair = new HashMap<String,String>();
		paramPair.put("kf_account",account);
		log.info("设置客服帐号的头像（POST）：" + url + ",参数：{kf_account:" + account + "}");
		String ret = HttpUtils.uploadFileSSL(url, headJpgImg, headJpgImg.getName(), paramPair);
		log.info("设置客服帐号的头像（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取所有客服账号
	 * 开发者通过本接口，获取公众号中所设置的客服基本信息，包括客服工号、客服昵称、客服登录账号。 
	 * http请求方式: GET
     * https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN
     * {
     *     "kf_list": [
     *         {
     *             "kf_account": "test1@test", 
     *             "kf_nick": "ntest1", 
     *             "kf_id": "1001"
     *             "kf_headimgurl": " http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjfUS8Ym0GSaLic0FD3vN0V8PILcibEGb2fPfEOmw/0"
     *         }, 
     *         ....
     *     ]
     * }
	 * @throws JSONException 
     */
	public  JSONObject getKFList() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=" + token;
		log.info("获取所有客服账号（GET）：" + url );
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取所有客服账号（GET）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet; 
	}
}

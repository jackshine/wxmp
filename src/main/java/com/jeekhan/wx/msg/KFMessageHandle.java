package com.jeekhan.wx.msg;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.api.AccessToken;
import com.jeekhan.wx.utils.HttpUtils;

/**
 * 客服消息
 * 当用户和公众号产生特定动作的交互时（具体动作列表请见下方说明），微信将会把消息数据推送给开发者，开发者可以在一段时间内（目前修改为48小时）调用客服接口，通过POST一个JSON数据包来发送消息给普通用户。
 * 1、用户发送信息
 * 2、点击自定义菜单（仅有点击推事件、扫码推事件、扫码推事件且弹出“消息接收中”提示框这3种菜单类型是会触发客服接口的）
 * 3、关注公众号
 * 4、扫描二维码
 * 5、支付成功
 * 6、用户维权
 * @author Jee Khan
 *
 */
@Component
public class KFMessageHandle {
	private Logger log = LoggerFactory.getLogger(KFMessageHandle.class);
		
	/**
	 * 客服发送文本消息
	 * 发送文本消息时，支持插入跳小程序的文字链
	 * 文本内容<a href="http://www.qq.com" data-miniprogram-appid="appid" data-miniprogram-path="pages/index/index">点击跳小程序</a>
	 * 1.data-miniprogram-appid 项，填写小程序appid，则表示该链接跳小程序；
	 * 2.data-miniprogram-path项，填写小程序路径，路径与app.json中保持一致，可带参数；
	 * 3.对于不支持data-miniprogram-appid 项的客户端版本，如果有herf项，则仍然保持跳href中的网页链接；
	 * 4.data-miniprogram-appid对应的小程序必须与公众号有绑定关系。
	 * @param toUser	普通用户openid 
	 * @param content	文本消息内容 
	 * @param account	客服账号，可为空
	 * @return	{"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject sendTextMsg(String toUser,String content,String account) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("msgtype", "text");
		JSONObject text = new JSONObject();
		text.put("content", content);
		jsonObj.put("text", text);
		if(account != null && account.length()>0){
			JSONObject customservice = new JSONObject();
			customservice.put("kf_account", account);
			jsonObj.put("customservice", customservice);
		}
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		log.info("客服发送文本消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("客服发送文本消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
     * 客服发送图片消息 
     * @param toUser	普通用户openid 
     * @param mediaId	发送的图片的媒体ID 
     * @param account	客服账号，可为空
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject sendImageMsg(String toUser,String mediaId,String account) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("msgtype", "image");
		JSONObject image = new JSONObject();
		image.put("media_id", mediaId);
		jsonObj.put("image", image);
		if(account != null && account.length()>0){
			JSONObject customservice = new JSONObject();
			customservice.put("kf_account", account);
			jsonObj.put("customservice", customservice);
		}
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		log.info("客服发送图片消息 （POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("客服发送图片消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 客服发送语音消息
	 * @param toUser	普通用户openid 
	 * @param mediaId	语音的媒体ID 
	 * @param account	客服账号，可为空
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject sendVoiceMsg(String toUser,String mediaId,String account) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("msgtype", "voice");
		JSONObject voice = new JSONObject();
		voice.put("media_id", mediaId);
		jsonObj.put("voice", voice);
		if(account != null && account.length()>0){
			JSONObject customservice = new JSONObject();
			customservice.put("kf_account", account);
			jsonObj.put("customservice", customservice);
		}
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		log.info("客服发送语音消息 （POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("客服发送语音消息  （POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 客服发送视频消息
	 * @param toUser		普通用户openid 
	 * @param mediaId		视频的媒体ID 
	 * @param thumbMediaId	缩略图的媒体ID 
	 * @param title			视频消息的标题 
	 * @param description	视频消息的描述 
	 * @param account		客服账号，可为空
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject sendVideoMsg(String toUser,String mediaId,String thumbMediaId,String title,String description,String account) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("msgtype", "video");
		JSONObject video = new JSONObject();
		video.put("media_id", mediaId);
		video.put("thumb_media_id", thumbMediaId);
		video.put("title", title);
		video.put("description", description);
		jsonObj.put("video", video);
		if(account != null && account.length()>0){
			JSONObject customservice = new JSONObject();
			customservice.put("kf_account", account);
			jsonObj.put("customservice", customservice);
		}
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		log.info("客服发送视频消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("客服发送视频消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 客服发送音乐消息
	 * @param toUser		普通用户openid 
	 * @param title			音乐消息的标题 
	 * @param description	音乐消息的描述 
	 * @param musicurl		音乐链接 
	 * @param hqmusicurl	高品质音乐链接，wifi环境优先使用该链接播放音乐 
	 * @param thumbMediaId	缩略图的媒体ID 
	 * @param account		客服账号，可为空
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject sendMusicMsg(String toUser,String title,String description,String musicurl,String hqmusicurl,String thumbMediaId,String account) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("msgtype", "music");
		JSONObject music = new JSONObject();
		music.put("title", title);
		music.put("description", description);
		music.put("musicurl", musicurl);
		music.put("hqmusicurl", hqmusicurl);
		music.put("thumb_media_id", thumbMediaId);
		jsonObj.put("music", music);
		if(account != null && account.length()>0){
			JSONObject customservice = new JSONObject();
			customservice.put("kf_account", account);
			jsonObj.put("customservice", customservice);
		}
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		log.info("客服发送视频消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("客服发送视频消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 发送图文消息（点击跳转到图文消息页面） 图文消息条数限制在8条以内，注意，如果图文数超过8，则将会无响应
	 * @param toUser	普通用户openid 
	 * @param mediaId	图文消息（点击跳转到图文消息页）的媒体ID
	 * @param account	客服账号，可为空 
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject sendNewsMsg(String toUser,String mediaId,String account) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("msgtype", "mpnews");
		JSONObject mpnews = new JSONObject();
		mpnews.put("media_id", mediaId);
		jsonObj.put("news", mpnews);
		if(account != null && account.length()>0){
			JSONObject customservice = new JSONObject();
			customservice.put("kf_account", account);
			jsonObj.put("customservice", customservice);
		}
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		log.info("客服发送图文消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("客服发送图文消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 客服发送图文消息（点击跳转到外链） 
	 * 图文消息条数限制在8条以内，注意，如果图文数超过8，则将会无响应。 
	 * @param toUser	普通用户openid 
	 * @param list(news)
	 * 	title 		图文消息的标题 
	 * 	description 图文消息的描述 
	 * 	url 		图文消息被点击后跳转的链接 
	 * 	picurl 		图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80 
	 * @param account	客服账号，可为空
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject sendMPNewsMsg(String toUser,List<Map<String,String>> list,String account) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("msgtype", "news");
		JSONObject news = new JSONObject();
		JSONArray articles = new JSONArray();
		for(Map<String,String> item:list){
			JSONObject article = new JSONObject();
			article.put("title", item.get("title"));
			article.put("description", item.get("description"));
			article.put("url", item.get("url"));
			article.put("picurl", item.get("picurl"));
			articles.put(article);
		}
		news.put("articles", articles);
		if(account != null && account.length()>0){
			JSONObject customservice = new JSONObject();
			customservice.put("kf_account", account);
			jsonObj.put("customservice", customservice);
		}
		jsonObj.put("news", news);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		log.info("客服发送图文消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("客服发送图文消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 客服发送卡券 
     * 	{"touser":"OPENID", "msgtype":"wxcard",
     *   "wxcard":{"card_id":"123dsdajkasd231jhksad",}
     *   }
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject sendWXCardMsg(String toUser,String wxcardInfo,String account) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("msgtype", "wxcard");
		JSONObject wxcard = new JSONObject();
		wxcard.put("card_id", "");
		
		jsonObj.put("wxcard", wxcard);
		if(account != null && account.length()>0){
			JSONObject customservice = new JSONObject();
			customservice.put("kf_account", account);
			jsonObj.put("customservice", customservice);
		}
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		log.info("客服发送卡券（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("客服发送卡券（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 客服发送小程序卡片（要求小程序与公众号已关联） 
     * 	{"touser":"OPENID", "msgtype":"miniprogrampage",
     *   "miniprogrampage":{"title":"title","appid":"appid", "pagepath":"pagepath", "thumb_media_id":"thumb_media_id"}   
     * 	}
     * @param	toUser	普通用户openid
     * @param	title	小程序卡片的标题
     * @param	appid	小程序的appid，要求小程序的appid需要与公众号有关联关系
     * @param	pagepath	小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar
     * @param	thumb_media_id	缩略图/小程序卡片图片的媒体ID，小程序卡片图片建议大小为520*416
     * @param	account	客服账号
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject sendMiniProgramMsg(String toUser,String title,String appid,String pagepath,String thumb_media_id,String account) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("msgtype", "miniprogrampage");
		JSONObject miniprogrampage = new JSONObject();
		miniprogrampage.put("title", title);
		miniprogrampage.put("appid", appid);
		miniprogrampage.put("pagepath", pagepath);
		miniprogrampage.put("thumb_media_id", thumb_media_id);
		
		jsonObj.put("miniprogrampage", miniprogrampage);
		if(account != null && account.length()>0){
			JSONObject customservice = new JSONObject();
			customservice.put("kf_account", account);
			jsonObj.put("customservice", customservice);
		}
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		log.info("客服发送小程序卡片（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("客服发送小程序卡片（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	/**
	 * 发送客服输入状态给用户
	 * 如果不满足发送客服消息的触发条件，则无法下发输入状态。
	 *	下发输入状态，需要客服之前30秒内跟用户有过消息交互。
	 *	在输入状态中（持续15s），不可重复下发输入态。
	 *	在输入状态中，如果向用户下发消息，会同时取消输入状态。
	 * @param toUser		普通用户（openid）
	 * @param command	"Typing"：对用户下发“正在输入"状态 "CancelTyping"：取消对用户的”正在输入"状态
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject sendTypingStatusMsg(String toUser,String command) throws JSONException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("touser", toUser);
		jsonObj.put("command", "Typing");

		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=" + token;
		log.info("客服发送输入状态（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("客服发送输入状态（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
}

package com.jeekhan.wx.msg;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.api.AccessToken;
import com.jeekhan.wx.api.MediaMaterialHandle;
import com.jeekhan.wx.utils.HttpUtils;

/**
 * 高级消息群发
 * 订阅号提供了每天一条的群发权限，为服务号提供每月（自然月）4条的群发权限。而对于某些具备开发能力的公众号运营者，可以通过高级群发接口，实现更灵活的群发能力
 * 由于群发任务提交后，群发任务可能在一定时间后才完成，因此，群发接口调用时，仅会给出群发任务是否提交成功的提示，若群发任务提交成功，则在群发任务结束时，会向开发者在公众平台填写的开发者URL（callback URL）推送事件。
 * @author Jee Khan
 *
 */
@Component
public class MassMsgHandle {
	private static Logger log = LoggerFactory.getLogger(KFMessageHandle.class);
	
	@Autowired
	private MediaMaterialHandle mediaMaterialHandle;
	
	public void setMediaMaterialHandle(MediaMaterialHandle mediaMaterialHandle) {
		this.mediaMaterialHandle = mediaMaterialHandle;
	}

	/**
	 * 根据标签进行群发
	 * @param mediaId	图文消息的媒体ID
	 * @param tagId	标签ID 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * 	"msg_data_id": 206227730	//消息的数据ID，该字段只有在群发图文消息时，才会出现
	 * }
	 * @throws JSONException 
	 */
	public JSONObject sendNewsToTag(String tagId,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		
		JSONObject mpnews = new JSONObject();
		mpnews.put("media_id", mediaId);
		
		JSONObject filter = new JSONObject();
		filter.put("is_to_all", false);
		filter.put("tag_id", tagId);
		
		jsonObj.put("mpnews", mpnews);
		jsonObj.put("msgtype", "mpnews");
		jsonObj.put("filter", filter);
		jsonObj.put("send_ignore_reprint", 0);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据标签进行群发（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据标签进行群发（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}

	/**
	 * 根据标签进行群发文本消息
	 * @param content	文本消息内容
	 * @param tagId	标签ID 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject sendTextToTag(String tagId,String content) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "text");
		JSONObject text = new JSONObject();
		text.put("content", content);
		
		JSONObject filter = new JSONObject();
		filter.put("is_to_all", false);
		filter.put("tag_id", tagId);
		
		jsonObj.put("text", text);
		jsonObj.put("filter", filter);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据标签进行群发文本消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据标签进行群发文本消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 根据标签进行群发语音消息
	 * @param mediaId	需通过基础支持中的上传下载多媒体文件来得到
	 * @param tagId	标签ID 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * } 
	 * @throws JSONException 
	 */
	public JSONObject sendVoiceToTag(String tagId,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "voice");
		JSONObject voice = new JSONObject();
		voice.put("media_id", mediaId);
		
		JSONObject filter = new JSONObject();
		filter.put("is_to_all", false);
		filter.put("tag_id", tagId);
		
		jsonObj.put("voice", voice);
		jsonObj.put("filter", filter);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据标签进行群发语音消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据标签进行群发语音消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 根据标签进行群发图片消息
	 * @param mediaId	需通过基础支持中的上传下载多媒体文件来得到
	 * @param tagId	标签ID 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject sendImageToTag(String tagId,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "image");
		JSONObject image = new JSONObject();
		image.put("media_id", mediaId);
		
		JSONObject filter = new JSONObject();
		filter.put("is_to_all", false);
		filter.put("tag_id", tagId);
		
		jsonObj.put("image", image);
		jsonObj.put("filter", filter);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据标签进行群发图片消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据标签进行群发图片消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 根据标签进行群发视频消息
	 * @param mediaId	需通过基础支持中的上传下载多媒体文件来得到
	 * @param tagId	标签ID 
	 * @return
	 * @throws JSONException 
	 */
	public JSONObject sendVideoToTag(String tagId,String mediaId,String title,String description) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "mpvideo");
		
		JSONObject videoRet = mediaMaterialHandle.uploadVideo(mediaId, title, description);
		if(videoRet.has("media_id")) {
			mediaId = videoRet.getString("media_id");//转换mediaID
		}else {
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg", "转换MediaId失败！");
			return jsonRet;
		}
		JSONObject mpvideo = new JSONObject();
		mpvideo.put("media_id", mediaId);
		
		JSONObject filter = new JSONObject();
		filter.put("is_to_all", false);
		filter.put("tag_id", tagId);
		
		jsonObj.put("mpvideo", mpvideo);
		jsonObj.put("filter", filter);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据标签进行群发视频消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据标签进行群发视频消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 根据标签进行群发卡券消息
	 * @param mediaId	
	 * @param tagId	标签ID 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * } 
	 * @throws JSONException 
	 */
	public JSONObject sendWxcardToTag(String tagId,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "wxcard");
		JSONObject wxcard = new JSONObject();
		wxcard.put("media_id", mediaId);
		
		JSONObject filter = new JSONObject();
		filter.put("is_to_all", false);
		filter.put("tag_id", tagId);
		
		jsonObj.put("wxcard", wxcard);
		jsonObj.put("filter", filter);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据标签进行群发卡券消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据标签进行群发卡券消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 根据OpenID列表群发图文消息
	 * @param mediaId	图文消息的媒体ID
	 * @param openIds	OpenID列表 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * 	"msg_data_id": 206227730	//消息的数据ID，该字段只有在群发图文消息时，才会出现
	 * }
	 * @throws JSONException 
	 */
	public JSONObject sendNewsToOpenIds(List<String> openIds,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "mpnews");
		JSONObject mpnews = new JSONObject();
		mpnews.put("media_id", mediaId);
		
		JSONArray touser = new JSONArray();
		for(String openId:openIds){
			touser.put(openId);
		}
		
		jsonObj.put("mpnews", mpnews);
		jsonObj.put("touser", touser);
		jsonObj.put("send_ignore_reprint", 0);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据OpenID列表进行群发图文消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据OpenID列表进行群发图文消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}

	/**
	 * 根据OpenID列表群发文本消息
	 * @param content	文本消息内容
	 * @param openIds	OpenID列表  
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject sendTextToOpenIds(List<String> openIds,String content) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "text");
		JSONObject text = new JSONObject();
		text.put("content", content);
		
		JSONArray touser = new JSONArray();
		for(String openId:openIds){
			touser.put(openId);
		}
		
		jsonObj.put("text", text);
		jsonObj.put("touser", touser);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据OpenID列表进行群发文本消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据OpenID列表进行群发文本消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 根据OpenID列表群发语音消息
	 * @param mediaId	需通过基础支持中的上传下载多媒体文件来得到
	 * @param openIds	OpenID列表 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject sendVoiceToOpenIds(List<String> openIds,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "voice");
		JSONObject voice = new JSONObject();
		voice.put("media_id", mediaId);
		
		JSONArray touser = new JSONArray();
		for(String openId:openIds){
			touser.put(openId);
		}
		
		jsonObj.put("voice", voice);
		jsonObj.put("touser", touser);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据OpenID列表进行群发语音消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据OpenID列表进行群发语音消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 根据OpenID列表进行群发图片消息
	 * @param mediaId	需通过基础支持中的上传下载多媒体文件来得到
	 * @param openIds	OpenID列表 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject sendImageToOpenIds(List<String> openIds,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "image");
		JSONObject image = new JSONObject();
		image.put("media_id", mediaId);
		
		JSONArray touser = new JSONArray();
		for(String openId:openIds){
			touser.put(openId);
		}
		
		jsonObj.put("image", image);
		jsonObj.put("touser", touser);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据OpenID列表进行群发图片消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据OpenID列表进行群发图片消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	
	/**
	 * 根据OpenID列表进行群发视频消息
	 * @param mediaId	需通过基础支持中的上传下载多媒体文件来得到
	 * @param title			
	 * @param description	
	 * @param openIds	OpenID列表 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * } 
	 * @throws JSONException 
	 */
	public JSONObject sendVideoToOpenIds(List<String> openIds,String mediaId,String title,String description) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "mpvideo");
		JSONObject videoRet = mediaMaterialHandle.uploadVideo(mediaId, title, description);
		if(videoRet.has("media_id")) {
			mediaId = videoRet.getString("media_id");//转换mediaID
		}else {
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg", "转换MediaId失败！");
			return jsonRet;
		}
		JSONObject mpvideo = new JSONObject();
		mpvideo.put("media_id", mediaId);
		mpvideo.put("title", title);
		mpvideo.put("description", description);
		
		JSONArray touser = new JSONArray();
		for(String openId:openIds){
			touser.put(openId);
		}
		
		jsonObj.put("mpvideo", mpvideo);
		jsonObj.put("touser", touser);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据OpenID列表进行群发视频消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据OpenID列表进行群发视频消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 根据OpenID列表进行群发卡券消息
	 * @param mediaId	需通过基础支持中的上传下载多媒体文件来得到
	 * @param openIds	OpenID列表  
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject sendWxcardToOpenIds(List<String> openIds,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "wxcard");
		JSONObject wxcard = new JSONObject();
		wxcard.put("media_id", mediaId);
		
		JSONArray touser = new JSONArray();
		for(String openId:openIds){
			touser.put(openId);
		}
		
		jsonObj.put("wxcard", wxcard);
		jsonObj.put("touser", touser);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
		log.info("根据OpenID列表进行群发卡券消息（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据OpenID列表进行群发卡券消息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}

	
	/**
	 * 发送图文消息给指定用户进行预览
	 * @param mediaId	图文消息的媒体ID
	 * @param openId		预览用户 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject previewNews(String openId,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "mpnews");
		JSONObject mpnews = new JSONObject();
		mpnews.put("media_id", mediaId);
		
		jsonObj.put("mpnews", mpnews);
		jsonObj.put("touser", openId);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=" + token;
		log.info("发送图文消息给指定用户进行预览（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("发送图文消息给指定用户进行预览（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}

	/**
	 * 发送文本消息给指定用户进行预览
	 * @param content	文本消息的内容
	 * @param openId		预览用户 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject previewText(String openId,String content) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "text");
		JSONObject text = new JSONObject();
		text.put("content", content);
		
		jsonObj.put("text", text);
		jsonObj.put("touser", openId);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=" + token;
		log.info("发送文本消息给指定用户进行预览（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("发送文本消息给指定用户进行预览（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 发送语音消息给指定用户进行预览
	 * @param mediaId	语音消息的媒体ID
	 * @param openId		预览用户 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject previewVoice(String openId,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "voice");
		JSONObject voice = new JSONObject();
		voice.put("media_id", mediaId);
		
		jsonObj.put("voice", voice);
		jsonObj.put("touser", openId);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=" + token;
		log.info("发送语音消息给指定用户进行预览（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("发送语音消息给指定用户进行预览（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 发送图片消息给指定用户进行预览
	 * @param mediaId	图片消息的媒体ID
	 * @param openId		预览用户 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject previewImage(String openId,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "image");
		JSONObject image = new JSONObject();
		image.put("media_id", mediaId);
		
		jsonObj.put("image", image);
		jsonObj.put("touser", openId);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=" + token;
		log.info("发送图片消息给指定用户进行预览（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("发送图片消息给指定用户进行预览（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	
	/**
	 * 发送视频消息给指定用户进行预览
	 * @param mediaId	视频消息的媒体ID
	 * @param title 视频消息的标题
	 * @param description	视频消息的描述
	 * @param openId		预览用户 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	public JSONObject previewVideo(String openId,String mediaId,String title,String description) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "mpvideo");
		
		JSONObject videoRet = mediaMaterialHandle.uploadVideo(mediaId, title, description);
		if(videoRet.has("media_id")) {
			mediaId = videoRet.getString("media_id");//转换mediaID
		}else {
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg", "转换MediaId失败！");
			return jsonRet;
		}
		JSONObject mpvideo = new JSONObject();
		mpvideo.put("media_id", mediaId);
		
		jsonObj.put("mpvideo", mpvideo);
		jsonObj.put("touser", openId);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=" + token;
		log.info("发送视频消息给指定用户进行预览（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("发送视频消息给指定用户进行预览（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}

	
	/**
	 * 功能待完善
	 * 发送卡券消息给指定用户进行预览
	 * 
	 * @param mediaId	卡券消息的媒体ID
	 * @param openId		预览用户 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	private JSONObject previewCard(String openId,String mediaId) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msgtype", "wxcard");
		JSONObject wxcard = new JSONObject();
		wxcard.put("card_id", mediaId);
		
		jsonObj.put("wxcard", wxcard);
		jsonObj.put("touser", openId);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=" + token;
		log.info("发送卡券消息给指定用户进行预览（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("发送卡券消息给指定用户进行预览（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 删除群发
	 * 群发之后，随时可以通过该接口删除群发。
	 * 1、只有已经发送成功的消息才能删除
	 * 2、删除消息是将消息的图文详情页失效，已经收到的用户，还是能在其本地看到消息卡片。
	 * 3、删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。
	 * 4、如果多次群发发送的是一个图文消息，那么删除其中一次群发，就会删除掉这个图文消息也，导致所有群发都失效
	 * @param msgId	发送出去的消息ID
	 * @param articleIdx 要删除的文章在图文消息中的位置，第一篇编号为1，该字段不填或填0会删除全部文章.
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject deleteSendedMsg(String msgId,Integer articleIdx) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msg_id", "msgId");
		log.info("删除群发（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("删除群发（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 查询群发消息发送状态
	 * http请求方式: POST https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN
	 * @param msgId	消息ID
	 * @return
	 * {
	 * "msg_id":201053012,	//群发消息后返回的消息id
	 * "msg_status":"SEND_SUCCESS"	//消息发送后的状态，SEND_SUCCESS表示发送成功，SENDING表示发送中，SEND_FAIL表示发送失败，DELETE表示已删除
	 * }	
	 * @throws JSONException 
	 */
	public JSONObject getSendStatus(String msgId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=" + token;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msg_id", "msgId");
		log.info("查询群发消息发送状态（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("查询群发消息发送状态（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	
	
}

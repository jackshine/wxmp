package com.jeekhan.wx.msg;

import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.api.AccessToken;
import com.jeekhan.wx.utils.HttpUtils;

/**
 * 被动回复消息
 * 当用户发送消息给公众号时（或某些特定的用户操作引发的事件推送时），会产生一个POST请求，开发者可以在响应包中返回特定XML结构，来对该消息进行响应（现支持回复文本、图片、图文、语音、视频、音乐）。
 * 严格来说，发送被动响应消息其实并不是一种接口，而是对微信服务器发过来消息的一次回复
 * @author Jee Khan
 *
 */
@Component
public class RespMsgHandle {
	private static String USER_ID = "55555555";	//公众号
	private static Logger log = LoggerFactory.getLogger(RespMsgHandle.class);
	
	@Value("${wx.PUBLIC-USER-ID}")
	public void setUERID(String userID) {
		RespMsgHandle.USER_ID = userID;
	}

	/**
	 * 回复文本消息
	 * @param toUser	接收方帐号（收到的OpenID） 
	 * @param content	回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示） 
	 * @return
	 */
	public String respTextMsg(String toUser,String content){
		Element root = DocumentHelper.createElement("xml");
		root.addElement("ToUserName").addText(toUser);
		root.addElement("FromUserName").addText(USER_ID);
		root.addElement("CreateTime").addText("" + System.currentTimeMillis());
		root.addElement("MsgType").addText("text");
		root.addElement("Content").addText(content);
		return root.asXML();
	}
	
	/**
	 * 回复图片消息
	 * @param toUser	接收方帐号（收到的OpenID） 
	 * @param mediaId	通过素材管理接口上传多媒体文件，得到的id 
	 * @return
	 */
	public String respImageMsg(String toUser,String mediaId){
		Element root = DocumentHelper.createElement("xml");
		root.addElement("ToUserName").addText(toUser);
		root.addElement("FromUserName").addText(USER_ID);
		root.addElement("CreateTime").addText("" + System.currentTimeMillis());
		root.addElement("MsgType").addText("image");
		root.addElement("Image").addElement("MediaId").addText(mediaId);
		return root.asXML();
	}
	
	/**
	 * 回复语音消息
	 * @param toUser	接收方帐号（收到的OpenID） 
	 * @param mediaId	通过素材管理接口上传多媒体文件，得到的id 
	 * @return
	 */
	public String respVoiceMsg(String toUser,String mediaId){
		Element root = DocumentHelper.createElement("xml");
		root.addElement("ToUserName").addText(toUser);
		root.addElement("FromUserName").addText(USER_ID);
		root.addElement("CreateTime").addText("" + System.currentTimeMillis());
		root.addElement("MsgType").addText("voice");
		root.addElement("Voice").addElement("MediaId").addText(mediaId);
		return root.asXML();
	}
	
	/**
	 * 回复视频消息
	 * @param toUser	接收方帐号（收到的OpenID） 
	 * @param mediaId	通过素材管理接口上传多媒体文件，得到的id 
	 * @param Title 	视频消息的标题 
	 * @param Description 视频消息的描述 
	 * @return
	 */
	public String respVideoMsg(String toUser,String mediaId,String title,String description){
		Element root = DocumentHelper.createElement("xml");
		root.addElement("ToUserName").addText(toUser);
		root.addElement("FromUserName").addText(USER_ID);
		root.addElement("CreateTime").addText("" + System.currentTimeMillis());
		root.addElement("MsgType").addText("video");
		Element video = DocumentHelper.createElement("Video");
		video.addElement("MediaId").addText(mediaId);
		video.addElement("Title").addText(title);
		video.addElement("Description").addText(description);
		root.add(video);
		return root.asXML();
	}
	
	/**
	 * 回复音乐消息
	 * @param toUser	接收方帐号（收到的OpenID） 
	 * @param title		音乐标题 
	 * @param description	音乐描述 
	 * @param musicUrl		音乐链接 
	 * @param hQMusicUrl	高质量音乐链接，WIFI环境优先使用该链接播放音乐 
	 * @param thumbMediaId	缩略图的媒体id，通过素材管理接口上传多媒体文件，得到的id 
	 * @return
	 */
	public String respMusicMsg(String toUser,String musicUrl,String title,String description,String hQMusicUrl,String thumbMediaId){
		Element root = DocumentHelper.createElement("xml");
		root.addElement("ToUserName").addText(toUser);
		root.addElement("FromUserName").addText(USER_ID);
		root.addElement("CreateTime").addText("" + System.currentTimeMillis());
		root.addElement("MsgType").addText("music");
		Element music = DocumentHelper.createElement("Music");
		music.addElement("Title").addText(title);
		music.addElement("Description").addText(description);
		music.addElement("MusicUrl").addText(musicUrl);
		music.addElement("HQMusicUrl").addText(hQMusicUrl);
		music.addElement("ThumbMediaId").addText(thumbMediaId);
		root.add(music);
		return root.asXML();
	}
	
	/**
	 * 回复图文消息
	 * @param toUser			接收方帐号（收到的OpenID） 
	 * @param ArticleCount 		图文消息个数，限制为10条以内 
	 * @param list(Articles) 	多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 * 	Title 		图文消息标题 
	 * 	Description 图文消息描述
	 * 	PicUrl 		图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 * 	Url			点击图文消息跳转链接
	 * @return
	 */
	public String respNewsMsg(String toUser,List<Map<String,Object>> list){
		Element root = DocumentHelper.createElement("xml");
		root.addElement("ToUserName").addText(toUser);
		root.addElement("FromUserName").addText(USER_ID);
		root.addElement("CreateTime").addText("" + System.currentTimeMillis());
		root.addElement("MsgType").addText("news");
		root.addElement("ArticleCount").addText("" + list.size());
		Element articles = DocumentHelper.createElement("Articles");
		for(Map<String,Object> item:list){
			Element itemEle = DocumentHelper.createElement("item");
			itemEle.addElement("Title").addText((String)item.get("Title"));
			itemEle.addElement("Description").addText((String)item.get("Description"));
			itemEle.addElement("PicUrl").addText((String)item.get("PicUrl"));
			itemEle.addElement("Url").addText((String)item.get("Url"));
			articles.add(itemEle);
		}
		root.add(articles);
		return root.asXML();
	}
	
	/**
	 * 获取自动回复规则
	 * http请求方式: GET https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=ACCESS_TOKEN
	 * @return
	 * @throws JSONException 
	 */
	public Object getAutoReplyInfo() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=" + token;
		log.info("获取自动回复规则（POST）：" + url);
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取自动回复规则（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
}

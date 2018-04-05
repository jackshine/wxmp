package com.jeekhan.wx.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeekhan.wx.msg.KFMessageHandle;

/**
 * 客服消息控制服务
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/kfmessage")
public class KFMessageAction {
	@Autowired
	private KFMessageHandle customServiceMsgHandle;
	
	/**
	 * 获取客服消息主页
	 * @param map
	 * @return
	 */
	@RequestMapping("/index")
	public String getIndex(ModelMap map) {
		
		return "page-kfmessage";
	}
	
	/**
	 * 客服发送文本消息
	 * 发送文本消息时，支持插入跳小程序的文字链
	 * 文本内容<a href="http://www.qq.com" data-miniprogram-appid="appid" data-miniprogram-path="pages/index/index">点击跳小程序</a>
	 * 1.data-miniprogram-appid 项，填写小程序appid，则表示该链接跳小程序；
	 * 2.data-miniprogram-path项，填写小程序路径，路径与app.json中保持一致，可带参数；
	 * 3.对于不支持data-miniprogram-appid 项的客户端版本，如果有herf项，则仍然保持跳href中的网页链接；
	 * 4.data-miniprogram-appid对应的小程序必须与公众号有绑定关系。
	 * @param toUser		普通用户openid 
	 * @param content	文本消息内容 
	 * @param account	客服账号，可为空
	 * @return	{"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/sendText")
	public JSONObject sendTextMsg(String toUser,String content,@RequestParam(value="account",required=false)String account) throws JSONException{
		JSONObject jsonRet ;
		if(toUser == null || toUser.trim().length() == 0 ||
				content == null || content.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg","粉丝用户ID和消息内容不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = this.customServiceMsgHandle.sendTextMsg(toUser, content, account);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/sendImage")
	public JSONObject sendImageMsg(String toUser,String mediaId,@RequestParam(value="account",required=false)String account) throws JSONException{
		JSONObject jsonRet ;
		if(toUser == null || toUser.trim().length() == 0 ||
				mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg","粉丝用户ID和图片的媒体ID不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = this.customServiceMsgHandle.sendImageMsg(toUser, mediaId, account);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/sendVoice")
	public JSONObject sendVoiceMsg(String toUser,String mediaId,@RequestParam(value="account",required=false)String account) throws JSONException{
		JSONObject jsonRet;
		if(toUser == null || toUser.trim().length() == 0 ||
				mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg","粉丝用户ID和语音的媒体ID不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = this.customServiceMsgHandle.sendVoiceMsg(toUser, mediaId, account);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/sendVideo")
	public JSONObject sendVideoMsg(String toUser,String mediaId,String thumbMediaId,String title,String description,@RequestParam(value="account",required=false)String account) throws JSONException{
		JSONObject jsonRet ;
		if(toUser == null || toUser.trim().length() == 0 ||
				mediaId == null || mediaId.trim().length() == 0 ||
				thumbMediaId == null || thumbMediaId.trim().length() == 0 ||
				title == null || title.trim().length() == 0 ||
				description == null || description.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg","粉丝用户ID、视频的媒体ID、缩略图的媒体ID、视频消息的标题、视频消息的描述 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = this.customServiceMsgHandle.sendVideoMsg(toUser, mediaId, thumbMediaId, title, description, account);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/sendMusic")
	public JSONObject sendMusicMsg(String toUser,String title,String description,String musicurl,String hqmusicurl,String thumbMediaId,@RequestParam(value="account",required=false)String account) throws JSONException{
		JSONObject jsonRet ;
		if(toUser == null || toUser.trim().length() == 0 ||
				title == null || title.trim().length() == 0 ||
				description == null || description.trim().length() == 0 ||
				musicurl == null || musicurl.trim().length() == 0 ||
				hqmusicurl == null || hqmusicurl.trim().length() == 0 ||
				thumbMediaId == null || thumbMediaId.trim().length() == 0 ) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg","粉丝用户ID、音乐消息的标题、音乐消息的描述、音乐链接、高品质音乐链接、缩略图的媒体ID 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = this.customServiceMsgHandle.sendMusicMsg(toUser, title, description, musicurl, hqmusicurl, thumbMediaId, account);
					
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/sendNews")
	public Object sendNewsMsg(String toUser,String mediaId,@RequestParam(value="account",required=false)String account) throws JSONException{
		JSONObject jsonRet ;
		if(toUser == null || toUser.trim().length() == 0 ||
				mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg","粉丝用户ID和图文消息ID不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = this.customServiceMsgHandle.sendNewsMsg(toUser, mediaId, account);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/sendMPNews")
	public JSONObject sendMPNewsMsg(String toUser,List<Map<String,String>> list,@RequestParam(value="account",required=false)String account) throws JSONException{
		JSONObject jsonRet ;
		if(toUser == null || toUser.trim().length() == 0 ||
				list == null || list.size() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg","粉丝用户ID和图文消息不可为空！");
			return jsonRet;
		}
		if(list.size() > 8) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg","图文消息条数限制在8条！");
			return jsonRet;
		}
		try {
			jsonRet = this.customServiceMsgHandle.sendMPNewsMsg(toUser, list, account);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/sendWXCard")
	public JSONObject sendWXCardMsg(String toUser,String wxcardId,@RequestParam(value="account",required=false)String account) throws JSONException{
		JSONObject jsonRet ;
		if(toUser == null || toUser.trim().length() == 0 ||
				wxcardId == null || wxcardId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg","粉丝用户ID和卡券信息 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = this.customServiceMsgHandle.sendWXCardMsg(toUser, wxcardId, account);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/sendMiniProgram")
	public Object sendMiniProgramMsg(String toUser,String title,String appid,String pagepath,String thumb_media_id,@RequestParam(value="account",required=false)String account) throws JSONException{
		JSONObject jsonRet ;
		if(toUser == null || toUser.trim().length() == 0 ||
				title == null || title.trim().length() == 0 ||
				appid == null || appid.trim().length() == 0 ||
				pagepath == null || pagepath.trim().length() == 0 ||
				thumb_media_id == null || thumb_media_id.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg","粉丝用户ID、小程序卡片的标题、小程序的appid、小程序的页面路径、小程序卡片图片的媒体ID 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = this.customServiceMsgHandle.sendMiniProgramMsg(toUser, title, appid, pagepath, thumb_media_id, account);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/sendTypingStatus")
	public JSONObject sendTypingStatusMsg(String toUser,String command) throws JSONException {
		JSONObject jsonRet ;
		if(toUser == null || toUser.trim().length() == 0 ||
				command == null || command.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg","粉丝用户ID和输入状态不可为空！");
			return jsonRet;
		}
		toUser = toUser.trim();
		command = command.trim();
		try {
			jsonRet = this.customServiceMsgHandle.sendTypingStatusMsg(toUser, command);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	

}

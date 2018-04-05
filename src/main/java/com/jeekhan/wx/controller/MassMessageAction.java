package com.jeekhan.wx.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeekhan.wx.msg.MassMsgHandle;

/**
 * 群发消息控制器服务
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/mass")
public class MassMessageAction {
	
	@Autowired
	private MassMsgHandle massMsgHandle;
	
	/**
	 * 获取群发消息主页
	 * @param map
	 * @return
	 */
	@RequestMapping("/index")
	public String getIndex(ModelMap map) {
		
		return "page-mass";
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
	@RequestMapping("/news2Tag")
	public JSONObject sendNewsToTag(String tagId,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(tagId == null || tagId.length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户标签ID、图文消息的媒体ID 不可为空！");
			return jsonRet;
		}
		tagId = tagId.trim();
		mediaId = mediaId.trim();
		try {
			jsonRet = massMsgHandle.sendNewsToTag(tagId, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/text2Tag")
	public JSONObject sendTextToTag(String tagId,String content) throws JSONException{
		JSONObject jsonRet;
		if(tagId == null || tagId.toString().length() == 0 || content == null || content.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户标签ID、文本消息内容 不可为空！");
			return jsonRet;
		}
		tagId = tagId.trim();
		content = content.trim();
		try {
			jsonRet = massMsgHandle.sendTextToTag(tagId, content);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 根据标签进行群发语音消息
	 * @param mediaId	语音媒体ID，需通过基础支持中的上传下载多媒体文件来得到
	 * @param tagId	用户标签ID 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * } 
	 * @throws JSONException 
	 */
	@RequestMapping("/voice2Tag")
	public JSONObject sendVoiceToTag(String tagId,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(tagId == null || tagId.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户标签ID、语音媒体ID 不可为空！");
			return jsonRet;
		}
		tagId = tagId.trim();
		mediaId = mediaId.trim();
		try {
			jsonRet = massMsgHandle.sendVoiceToTag(tagId, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 根据标签进行群发图片消息
	 * @param mediaId	图片媒体ID，需通过基础支持中的上传下载多媒体文件来得到
	 * @param tagId	标签ID 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/image2Tag")
	public JSONObject sendImageToTag(String tagId,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(tagId == null || tagId.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户标签ID、图片媒体ID 不可为空！");
			return jsonRet;
		}
		tagId = tagId.trim();
		mediaId = mediaId.trim();
		try {
			jsonRet = massMsgHandle.sendImageToTag(tagId, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 根据标签进行群发视频消息
	 * @param mediaId	视频媒体ID，需通过基础支持中的上传下载多媒体文件来得到
	 * @param title	视频消息标题
	 * @param description	视频消息描述
	 * @param tagId	标签ID 
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping("/video2Tag")
	public JSONObject sendVideoToTag(String tagId,String mediaId,String title,String description) throws JSONException{
		JSONObject jsonRet;
		if(tagId == null || tagId.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0 ||
				title == null || title.toString().length() == 0 || description == null || description.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户标签ID、视频媒体ID、视频消息标题、视频消息描述 不可为空！");
			return jsonRet;
		}
		tagId = tagId.trim();
		mediaId = mediaId.trim();
		title = title.trim();
		description = description.trim();
		try {
			jsonRet = massMsgHandle.sendVideoToTag(tagId, mediaId, title, description);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 待完善
	 * 根据标签进行群发卡券消息
	 * @param mediaId	卡券ID
	 * @param tagId	标签ID 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * } 
	 * @throws JSONException 
	 */
	@RequestMapping("/card2Tag")
	public JSONObject sendWxcardToTag(String tagId,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(tagId == null || tagId.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户标签ID、卡券ID 不可为空！");
			return jsonRet;
		}
		tagId = tagId.trim();
		mediaId = mediaId.trim();
		try {
			jsonRet = massMsgHandle.sendWxcardToTag(tagId, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 根据OpenID列表群发图文消息
	 * @param mediaId	图文消息的媒体ID
	 * @param openIds	OpenID列表，以逗号分隔 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * 	"msg_data_id": 206227730	//消息的数据ID，该字段只有在群发图文消息时，才会出现
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/news2OpenIds")
	public JSONObject sendNewsToOpenIds(String openIds,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(openIds == null || openIds.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表、图文消息内容 不可为空！");
			return jsonRet;
		}
		openIds = openIds.trim();
		mediaId = mediaId.trim();
		String[] arr = openIds.split(",");
		List<String> list = new ArrayList<String>();
		for(String openId:arr) {
			if(openId.trim().length()>0) {
				list.add(openId.trim());
			}
		}
		if(list.size() < 1) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = massMsgHandle.sendNewsToOpenIds(list, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}

	/**
	 * 根据OpenID列表群发文本消息
	 * @param content	文本消息内容
	 * @param openIds	OpenID列表，以逗号分隔 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/text2OpenIds")
	public JSONObject sendTextToOpenIds(String openIds,String content) throws JSONException{
		JSONObject jsonRet;
		if(openIds == null || openIds.toString().length() == 0 || content == null || content.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表、文本消息内容 不可为空！");
			return jsonRet;
		}
		openIds = openIds.trim();
		content = content.trim();
		String[] arr = openIds.split(",");
		List<String> list = new ArrayList<String>();
		for(String openId:arr) {
			if(openId.trim().length()>0) {
				list.add(openId.trim());
			}
		}
		if(list.size() < 1) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = massMsgHandle.sendTextToOpenIds(list, content);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 根据OpenID列表群发语音消息
	 * @param mediaId	语音媒体ID，需通过基础支持中的上传下载多媒体文件来得到
	 * @param openIds	OpenID列表 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/voice2OpenIds")
	public JSONObject sendVoiceToOpenIds(String openIds,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(openIds == null || openIds.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表、语音媒体ID 不可为空！");
			return jsonRet;
		}
		openIds = openIds.trim();
		mediaId = mediaId.trim();
		String[] arr = openIds.split(",");
		List<String> list = new ArrayList<String>();
		for(String openId:arr) {
			if(openId.trim().length()>0) {
				list.add(openId.trim());
			}
		}
		if(list.size() < 1) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = massMsgHandle.sendVoiceToOpenIds(list, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 根据OpenID列表进行群发图片消息
	 * @param mediaId	图片ID，需通过基础支持中的上传下载多媒体文件来得到
	 * @param openIds	OpenID列表 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/image2OpenIds")
	public JSONObject sendImageToOpenIds(String openIds,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(openIds == null || openIds.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表、图片ID 不可为空！");
			return jsonRet;
		}
		openIds = openIds.trim();
		mediaId = mediaId.trim();
		String[] arr = openIds.split(",");
		List<String> list = new ArrayList<String>();
		for(String openId:arr) {
			if(openId.trim().length()>0) {
				list.add(openId.trim());
			}
		}
		if(list.size() < 1) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = massMsgHandle.sendImageToOpenIds(list, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	
	/**
	 * 根据OpenID列表进行群发视频消息
	 * @param mediaId	视频媒体ID，需通过基础支持中的上传下载多媒体文件来得到
	 * @param title		视频消息标题	
	 * @param description	视频消息描述
	 * @param openIds	OpenID列表 
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * } 
	 * @throws JSONException 
	 */
	@RequestMapping("/video2OpenIds")
	public JSONObject sendVideoToOpenIds(String openIds,String mediaId,String title,String description) throws JSONException{
		JSONObject jsonRet;
		if(openIds == null || openIds.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0 ||
				title == null || title.toString().length() == 0 || description == null || description.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表、视频媒体ID、视频消息标题、视频消息描述 不可为空！");
			return jsonRet;
		}
		openIds = openIds.trim();
		mediaId = mediaId.trim();
		title = title.trim();
		description = description.trim();
		String[] arr = openIds.split(",");
		List<String> list = new ArrayList<String>();
		for(String openId:arr) {
			if(openId.trim().length()>0) {
				list.add(openId.trim());
			}
		}
		if(list.size() < 1) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = massMsgHandle.sendVideoToOpenIds(list, mediaId, title, description);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 根据OpenID列表进行群发卡券消息
	 * @param mediaId	卡券ID，需通过基础支持中的上传下载多媒体文件来得到
	 * @param openIds	OpenID列表  
	 * @return
	 * {
	 * 	"errcode":0,
	 * 	"errmsg":"send job submission success",
	 * 	"msg_id":34182, //消息发送任务的ID
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/card2OpenIds")
	public JSONObject sendWxcardToOpenIds(String openIds,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(openIds == null || openIds.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表、卡券ID 不可为空！");
			return jsonRet;
		}
		openIds = openIds.trim();
		mediaId = mediaId.trim();
		String[] arr = openIds.split(",");
		List<String> list = new ArrayList<String>();
		for(String openId:arr) {
			if(openId.trim().length()>0) {
				list.add(openId.trim());
			}
		}
		if(list.size() < 1) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID列表 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = massMsgHandle.sendWxcardToOpenIds(list, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/previewNews")
	public JSONObject previewNews(String openId,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(openId == null || openId.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID、图文消息内容 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = massMsgHandle.previewNews(openId, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/previewText")
	public JSONObject previewText(String openId,String content) throws JSONException{
		JSONObject jsonRet;
		if(openId == null || openId.toString().length() == 0 || content == null || content.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID、图文消息内容 不可为空！");
			return jsonRet;
		}
		openId = openId.trim();
		content = content.trim();
		
		try {
			jsonRet = massMsgHandle.previewText(openId, content);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/previewVoice")
	public JSONObject previewVoice(String openId,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(openId == null || openId.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID、语音消息的媒体ID 不可为空！");
			return jsonRet;
		}
		openId = openId.trim();
		mediaId = mediaId.trim();
		try {
			jsonRet = massMsgHandle.previewVoice(openId, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/previewImage")
	public JSONObject previewImage(String openId,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(openId == null || openId.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID、图片消息的媒体ID 不可为空！");
			return jsonRet;
		}
		openId = openId.trim();
		mediaId = mediaId.trim();
		try {
			jsonRet = massMsgHandle.previewImage(openId, mediaId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/previewVideo")
	public JSONObject previewVideo(String openId,String mediaId,String title,String description) throws JSONException{
		JSONObject jsonRet;
		if(openId == null || openId.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0 ||
				title == null || title.toString().length() == 0 || description == null || description.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID、视频消息的媒体ID、视频消息的标题、视频消息的描述 不可为空！");
			return jsonRet;
		}
		openId = openId.trim();
		mediaId = mediaId.trim();
		title = title.trim();
		description = description.trim();

		try {
			jsonRet = massMsgHandle.previewVideo(openId, mediaId, title, description);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
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
	public JSONObject previewCard(String openId,String mediaId) throws JSONException{
		JSONObject jsonRet;
		if(openId == null || openId.toString().length() == 0 || mediaId == null || mediaId.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID、卡券信息 不可为空！");
			return jsonRet;
		}
		openId = openId.trim();
		mediaId = mediaId.trim();

		try {
			jsonRet = null;
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
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
	@RequestMapping("/deleteSendedMsg")
	public JSONObject deleteSendedMsg(String msgId,@RequestParam(value="articleIdx",required=false)Integer articleIdx) throws JSONException{
		JSONObject jsonRet;
		if(msgId == null || msgId.toString().length() == 0 ) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "发送出去的消息ID 不可为空！");
			return jsonRet;
		}
		msgId = msgId.trim();

		try {
			jsonRet = massMsgHandle.deleteSendedMsg(msgId, articleIdx);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 查询群发消息发送状态
	 * http请求方式: POST https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN
	 * @param msgId	消息ID
	 * @return
	 * {"errcode":0,"errmsg":"ok",
	 * "msg_id":201053012,	//群发消息后返回的消息id
	 * "msg_status":"SEND_SUCCESS"	//消息发送后的状态，SEND_SUCCESS表示发送成功，SENDING表示发送中，SEND_FAIL表示发送失败，DELETE表示已删除
	 * }	
	 * @throws JSONException 
	 */
	@RequestMapping("/getSendStatus")
	public JSONObject getSendStatus(String msgId) throws JSONException{
		JSONObject jsonRet;
		if(msgId == null || msgId.toString().length() == 0 ) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "消息ID 不可为空！");
			return jsonRet;
		}
		msgId = msgId.trim();

		try {
			jsonRet = massMsgHandle.getSendStatus(msgId);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	

}

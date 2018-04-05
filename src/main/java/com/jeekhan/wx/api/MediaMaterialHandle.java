package com.jeekhan.wx.api;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.utils.HttpUtils;

/**
 * 多媒体素材管理
 * @author Jee Khan
 *
 */
@Component
public class MediaMaterialHandle {
	private static Logger log = LoggerFactory.getLogger(MediaMaterialHandle.class);
	
	/**
	 * 新增临时素材
	 * 媒体文件在后台保存时间为3天，即3天后media_id失效
	 * 图片（image）: 2M，支持bmp/png/jpeg/jpg/gif格式 
	 * 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式 
	 * 视频（video）：10MB，支持MP4格式 
	 * 缩略图（thumb）：64KB，支持JPG格式
	 * @param file
	 * @param type	媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb） 
	 * @return	{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
	 * @throws JSONException 
	 */
	public JSONObject addTempMedia(File file,String type) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + token + "&type=" + type;
		log.info("新增临时素材（POST）：" + url + ",媒体类型：" + type);
		String ret = HttpUtils.uploadFileSSL(url, file,"media",new HashMap<String,String>());
		log.info("新增临时素材（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 上传图文消息内的图片
	 * 本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下
	 * http请求方式: POST https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN
	 * @param file
	 * @return {"url":"http://mmbiz.qpic.cn/mmbiz/gLO17UPS6FS2xsypf378iaNhWacZ1G1UplZYWEYfwvuU6Ont96b1roYs CNFwaRrSaKTPCUdBK9DgEHicsKwWCBRQ/0"}
	 * @throws JSONException 
	 */
	public JSONObject addNewsImg(File file) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + token ;
		log.info("上传图文消息内的图片获取URL（POST）：" + url);
		String ret = HttpUtils.uploadFileSSL(url, file,"media",new HashMap<String,String>());
		log.info("上传图文消息内的图片获取URL（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 上传图文消息素材
	 * @param jsonNews（articles） 图文消息，一个图文消息支持1到8条图文 
	 * 	thumb_media_id ：图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
	 * 	author ：图文消息的作者，可为空
	 * 	title ：图文消息的标题 
	 * 	content_source_url ：在图文消息页面点击“阅读原文”后的页面 ，可为空
	 * 	content：图文消息页面的内容，支持HTML标签。具备微信支付权限的公众号，可以使用a标签，其他公众号不能使用，如需插入小程序卡片 
	 * 	digest ：图文消息的描述 ，可为空
	 * 	show_cover_pic：是否显示封面，1为显示，0为不显示 ，可为空
	 * @return	接口正确返回：{"type":"news","media_id":"CsEf3ldqkAYJAU6EJeIkStVDSvffUJ54vqbThMgplD-VJXXof6ctX5fI6-aYyUiQ","created_at":1391857799}
	 * @throws JSONException 
	 */
	public JSONObject addNews(String jsonNews) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=" + token;
		log.info("上传图文消息素材（POST）：" + url + ",参数：" + jsonNews);
		String ret = HttpUtils.doPostSSL(url, jsonNews);
		log.info("上传图文消息素材（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 根据基础支持中的视频MediaId获取群发的MediaId
	 * 群发视频、
	 * @param oldMediaId	基础支持中的MediaId
	 * @param title	多媒体消息标题
	 * @param description	多媒体消息描述
	 * @return
	 * @throws JSONException 
	 */
	public JSONObject uploadVideo(String oldMediaId,String title,String description) throws JSONException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("media_id", oldMediaId);
		jsonObj.put("title", title);
		jsonObj.put("description", description);
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=" + token;
		log.info("根据基础支持中的MediaId获取群发的MediaId（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("根据基础支持中的MediaId获取群发的MediaId（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取临时素材
	 * @param mediaId
	 * @return
	 * @throws JSONException 
	 */
	public File getTempMedia(String mediaId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + token + "&media_id=" + mediaId;
		log.info("获取临时素材（GET）：" + url );
		File file = HttpUtils.downloadFileSSL(url);
		if(file != null){
			log.info("获取临时素材（GET）返回：" + file.getName() );
		}else{
			log.info("获取临时素材失败");
		}
		return file;
	}
	
	/**
	 * 获取临时视频素材
	 * @param mediaId
	 * @return
	 * @throws JSONException 
	 */
	public File getTempVideo(String mediaId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "http://api.weixin.qq.com/cgi-bin/media/get?access_token=" + token + "&media_id=" + mediaId;
		log.info("获取临时视频素材（GET）：" + url );
		File file = HttpUtils.downloadFileSSL(url);
		if(file != null){
			log.info("获取临时视频素材（GET）返回：" + file.getName() );
		}else{
			log.info("获取临时视频素材失败");
		}
		return file;
	}
	
	/**
	 * 新增永久图文素材
	 * 若新增的是多图文素材，则此处应有几段article结构
	 * @param jsonNews(articles)
	 * 	title 			标题 
	 * 	thumb_media_id	图文消息的封面图片素材id（必须是永久mediaID）
	 * 	author 			作者
	 * 	digest 			图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
	 * 	show_cover_pic	是否显示封面，0为false，即不显示，1为true，即显示
	 *  content 		图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS 
	 *  content_source_url 	图文消息的原文地址，即点击“阅读原文”后的URL 
	 * @return {"media_id":MEDIA_ID}
	 * @throws JSONException 
	 */
	public JSONObject addPermanentNews(String jsonNews) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=" + token ;
		log.info("新增永久图文素材（POST）：" + url + "，参数：" + jsonNews);
		String ret = HttpUtils.doPost(url, jsonNews);
		log.info("新增永久图文素材（POST）返回：" + ret);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	
	/**
	 * 新增其他类型永久素材
	 * @param file 媒体文件
	 * @param type 媒体文件类型，分别有图片（image）、语音（voice）和缩略图（thumb）
	 * @return {"media_id":MEDIA_ID,"url":URL}
	 * @throws JSONException 
	 */
	public JSONObject addPernamentMedia(File file ,String type) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + token + "&type=" + type;
		log.info("新增其他类型永久素材（POST）：" + url + ",媒体类型：" + type);
		String ret =  HttpUtils.uploadFileSSL(url, file,"media",new HashMap<String,String>());
		log.info("新增其他类型永久素材（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 新增视频永久素材
	 * @param file			视频文件
	 * @param title			视频素材的标题
	 * @param introduction	视频素材的描述 
	 * @return	{"media_id":MEDIA_ID,"url":URL}
	 * @throws JSONException 
	 */
	public JSONObject addPernamentVideo(File file,String title,String introduction) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + token + "&type=video";
		log.info("新增视频永久素材（POST）：" + url );
		Map<String,String> map = new HashMap<String,String>();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("title",title);
		jsonObj.put("introduction", introduction);
		map.put("description",jsonObj.toString());
		String ret = HttpUtils.uploadFileSSL(url, file,"media",map);
		log.info("新增视频永久素材（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取图文永久素材
	 * @param mediaId
	 * @return
	 * {
 	 * "news_item":
 	 * [
	 *      {
	 *      "title":TITLE,
 	 *     "thumb_media_id"::THUMB_MEDIA_ID,
	 *      "show_cover_pic":SHOW_COVER_PIC(0/1),
	 *      "author":AUTHOR,
	 *      "digest":DIGEST,
	 *     "content":CONTENT,
	 *      "url":URL,
	 *      "content_source_url":CONTENT_SOURCE_URL
	 *      },
	 *      //多图文消息有多篇文章
	 *   ]
	 * }
	 * @throws JSONException 
	 */
	public JSONObject getPermanentNews(String mediaId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=" + token ;
		JSONObject json = new JSONObject();
		json.put("media_id", mediaId);
		log.info("获取图文永久素材（POST）" + url + "，参数：" + json);
		String ret = HttpUtils.doPostSSL(url, json);
		log.info("获取图文永久素材（POST）返回：" + ret );
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 获取视频永久素材
	 * @param mediaId
	 * @return { "title":TITLE,"description":DESCRIPTION,"down_url":DOWN_URL,}
	 * @throws JSONException 
	 */
	public JSONObject getPermanentVideo(String mediaId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=" + token ;
		JSONObject json = new JSONObject();
		json.put("media_id", mediaId);
		log.info("获取视频永久素材（POST）" + url + "，参数：" + json);
		String ret = HttpUtils.doPostSSL(url, json);
		log.info("获取视频永久素材（POST）返回：" + ret );
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 获取其他类型的素材(image,thumb,vioce)
	 * @param mediaId
	 * @return
	 * @throws JSONException 
	 */
	public File getPermanentMedia(String mediaId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=" + token ;
		JSONObject json = new JSONObject();
		json.put("media_id", mediaId);
		log.info("获取视频永久素材（POST）" + url + "，参数：" + json);
		File file = HttpUtils.downloadFileSSL(url, json);
		if(file != null){
			log.info("获取视频永久素材（POST）返回：" + file.getName() );
		}else{
			log.info("获取视频永久素材（POST）失败" );
		}
		return file;
	}
	
	/**
	 * 删除永久素材
	 * @param mediaId
	 * @return {"errcode":ERRCODE,"errmsg":ERRMSG}
	 * @throws JSONException 
	 */
	public JSONObject deletePermanentMedia(String mediaId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=" + token ;
		JSONObject json = new JSONObject();
		json.put("media_id", mediaId);
		log.info("删除永久素材（POST）" + url + "，参数：" + json);
		String ret = HttpUtils.doPostSSL(url, json);
		log.info("删除永久素材（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 修改永久图文素材
	 * @param mediaId	媒体ID
	 * @param index		要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0 
	 * @param article	文章信息
	 * 	title 			标题 
	 * 	thumb_media_id	图文消息的封面图片素材id（必须是永久mediaID）
	 * 	author 			作者
	 * 	digest 			图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
	 * 	show_cover_pic	是否显示封面，0为false，即不显示，1为true，即显示
	 *  content 		图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS 
	 *  content_source_url 	图文消息的原文地址，即点击“阅读原文”后的URL 
	 * @return  {"errcode":ERRCODE,"errmsg":ERRMSG}
	 * @throws JSONException 
	 */
	public JSONObject updatePermanentNews(String mediaId,Integer index,JSONObject article) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("media_id", index);
		jsonObj.put("index", mediaId);
		jsonObj.put("articles", article);
		log.info("修改永久图文素材（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPost(url, jsonObj);
		log.info("修改永久图文素材（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取永久素材总数
	 * @return {"voice_count":COUNT,"video_count":COUNT,"image_count":COUNT, "news_count":COUNT}
	 * @throws JSONException 
	 */
	public JSONObject getPermanentCount() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=" + token ;
		log.info("获取永久素材总数（GET）" + url );
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取永久素材总数（GET）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取永久素材列表
	 * @param type		素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news） 
	 * @param offset	从全部素材的该偏移位置开始返回，0表示从第一个素材 返回 
	 * @param count		返回素材的数量，取值在1到20之间 
	 * @return
	 * @throws JSONException 
	 */
	public JSONObject getPermanentList(String type,int offset,int count) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", type);
		jsonObj.put("offset", offset);
		jsonObj.put("count", count);
		log.info("获取永久素材列表（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPost(url, jsonObj);
		log.info("获取永久素材列表（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	
}

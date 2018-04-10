package com.jeekhan.wx.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeekhan.wx.api.UserMgrHandle;
import com.jeekhan.wx.dto.Operator;
import com.jeekhan.wx.model.FansBasic;
import com.jeekhan.wx.model.FansTag;
import com.jeekhan.wx.service.FansBasicService;
import com.jeekhan.wx.service.FansTagService;
import com.jeekhan.wx.utils.PageCond;
/**
 * 微信用户管理服务控制类
 * 供业务服务器进行调用
 * 1、用户添加、注销、更新；
 * 2、用户标签管理:不能和其他标签重名,标签名长度不超过30个字节，最多可以创建100个标签；
 * 3、设置用户备注名
 * 4、获取用户基本信息
 * 5、获取用户列表
 * 6、黑名单管理
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/fans")
public class FansAction {
	
	@Autowired
	private FansTagService  fansTagService;
	@Autowired
	private FansBasicService fansBasicService;
	
	/**
	 * 获取粉丝用户管理主页
	 * @param map
	 * @return
	 */
	@RequestMapping("/index")
	public String getIndex(ModelMap map) {
		
		return "page-fans";
	}
	
	/**
	 * 创建用户标签
	 * @param tagName	用户标签名称
	 * @return { "errcode:0,errmsg:"ok","tag":{ "id":134,"name":"广东"} }
	 * @throws JSONException 
	 */
	@RequestMapping("/tag/create")
	@ResponseBody
	public String createTag(@Valid FansTag fansTag,BindingResult result,Operator operator) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		try {
			//信息验证结果处理
			if(result.hasErrors()){
				StringBuilder sb = new StringBuilder();
				List<ObjectError> list = result.getAllErrors();
				for(ObjectError e :list){
					//String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
					sb.append(e.getDefaultMessage());
				}
				jsonRet.put("errmsg", sb.toString());
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			jsonRet = UserMgrHandle.addTag(fansTag.getTagName());
			if(jsonRet.has("tag")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
				fansTag.setTagId(jsonRet.getJSONObject("tag").getInt("id"));
				this.fansTagService.add(fansTag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 获取公众号已创建的标签
	 * @return {"errcode:0,errmsg:"ok","tags":[{"id":1, "name":"每天一罐可乐星人","count":0},{"id":2,"name":"星标组","count":0 },] } 
	 * @throws JSONException
	 */
	@RequestMapping("/tag/getAll")
	@ResponseBody
	public String getAllTags() throws JSONException {
		JSONObject jsonRet = new JSONObject();
		try {
			List<FansTag> list = this.fansTagService.getAll();
			jsonRet.put("errcode", 0);
			jsonRet.put("errmsg", "ok");
			jsonRet.put("data", new JSONArray(list));
		} catch (Exception e) {
			e.printStackTrace();
			
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 编辑标签
	 * @param tagName 新标签名称
	 * @param tagId	标签ID
	 * @return {"errcode":0,"errmsg":"ok" }
	 * @throws JSONException 
	 */
	@RequestMapping("/tag/update")
	@ResponseBody
	public String updateTag(@Valid FansTag fansTag,BindingResult result) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		try {
			if(fansTag.getTagId() == null) {
				jsonRet.put("errmsg", "标签ID不可为空！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			//信息验证结果处理
			if(result.hasErrors()){
				StringBuilder sb = new StringBuilder();
				List<ObjectError> list = result.getAllErrors();
				for(ObjectError e :list){
					//String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
					sb.append(e.getDefaultMessage());
				}
				jsonRet.put("errmsg", sb.toString());
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			jsonRet = UserMgrHandle.updateTag(fansTag.getTagName(), fansTag.getTagId());
			if(jsonRet.has("errcode") && jsonRet.getInt("errcode") == 0) {
				this.fansTagService.update(fansTag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
			
		}
		return jsonRet.toString();
	}
	/**
	 * 删除标签
	 * 当某个标签下的粉丝超过10w时，后台不可直接删除标签。此时，可以对该标签下的openid列表，先进行取消标签的操作，直到粉丝数不超过10w后，才可直接删除该标签。
	 * @param tagId	标签ID
	 * @return {"errcode":0, "errmsg":"ok"}
	 * @throws JSONException
	 */
	@RequestMapping("/tag/delete")
	@ResponseBody
	public String deleteTag(Integer tagId) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(tagId == null) {
			jsonRet.put("errcode", "-777");
			jsonRet.put("errmsg", "标签ID不可为空！");
			return jsonRet.toString();
		}
		try {
			jsonRet = UserMgrHandle.deleteTag(tagId);
			if(jsonRet.has("errcode") && jsonRet.getInt("errcode") == 0) {
				this.fansTagService.delete(tagId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 获取标签下粉丝列表
	 * @param tagId	标签ID
	 * @param beginOpenId	第一个拉取的OPENID，不填默认从头开始拉取
	 * @return 
	 * {		"errcode":0,"errmsg":"ok",
	 * 		"count":2,//这次获取的粉丝数量   
	 * 		"data":{//粉丝列表
	 *			"openid":[  
	 *			"ocYxcuAEy30bX0NXmGn4ypqx3tI0",    
	 *			"ocYxcuBt0mRugKZ7tGAHPnUaOW7Y"  ]  
	 *		 },  
	 *		 "next_openid":"ocYxcuBt0mRugKZ7tGAHPnUaOW7Y"//拉取列表最后一个用户的openid 
	 *}
	 * @throws JSONException
	 */
	//@RequestMapping("/getUsersByTag")
	public JSONObject getUsersByTag(Integer tagId,@RequestParam(value="beginOpenId",required=false)String beginOpenId) throws JSONException {
		if(tagId == null) {
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", "-777");
			jsonRet.put("errmsg", "标签ID不可为空！");
			return jsonRet;
		}
		try {
			JSONObject jsonRet = UserMgrHandle.getUsersByTag(tagId, beginOpenId);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
			return jsonRet;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
			return jsonRet;
		}
	}
	
	/**
	 * 批量为用户打标签
	 * @param openIds	用户ID列表
	 * @param tagId	标签ID
	 * @return {  "errcode":0,   "errmsg":"ok"}
	 * @throws JSONException
	 */
	@RequestMapping("/fans/addTag")
	@ResponseBody
	public String addTagToFanses(String openIds,Integer tagId) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(openIds == null || openIds.trim().length() == 0 || tagId == null) {
			jsonRet.put("errcode", "-777");
			jsonRet.put("errmsg", "用户ID列表与标签ID不可为空！");
			return jsonRet.toString();
		}
		openIds = openIds.trim();
		String[] arr = openIds.split(",");
		List<String> openIdList = new ArrayList<String>();
		for(String openId:arr) {
			openIdList.add(openId);
		}
		try {
			jsonRet = UserMgrHandle.addTagToUsers(openIdList, tagId);
			if(jsonRet.has("errcode") && jsonRet.getInt("errcode") ==0) {
				this.fansBasicService.addTag2Fanses(openIdList, tagId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 批量为用户取消标签
	 * @param openIds	用户ID列表
	 * @param tagId	标签ID
	 * @return { "errcode":0,   "errmsg":"ok"}
	 * @throws JSONException
	 */
	@RequestMapping("/fans/moveTag")
	@ResponseBody
	public String moveTagFromFanses(String openIds,Integer tagId) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(openIds == null || openIds.trim().length() == 0 || tagId == null) {
			jsonRet.put("errcode", "-777");
			jsonRet.put("errmsg", "用户ID列表与标签ID不可为空！");
			return jsonRet.toString();
		}
		openIds = openIds.trim();
		String[] arr = openIds.split(",");
		List<String> openIdList = new ArrayList<String>();
		for(String openId:arr) {
			openIdList.add(openId);
		}
		try {
			jsonRet = UserMgrHandle.moveTagFromUsers(openIdList, tagId);
			if(jsonRet.has("errcode") && jsonRet.getInt("errcode") ==0) {
				this.fansBasicService.removeTagFromFanses(tagId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 获取用户身上的标签列表
	 * @param openId	用户ID
	 * @return {"errcode":0,"errmsg":"ok"，"tagid_list":[ 134, 2] } 
	 * @throws JSONException 
	 */
	//@RequestMapping("/getTagsOnUser")
	public JSONObject getTagsOnUser(String openId) throws JSONException {
		if(openId == null || openId.trim().length() == 0 ) {
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", "-777");
			jsonRet.put("errmsg", "用户ID不可为空！");
			return jsonRet;
		}
		openId = openId.trim();
		try {
			JSONObject jsonRet = UserMgrHandle.getTagsOnUser(openId);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
			return jsonRet;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
			return jsonRet;
		}
	}
	
	/**
	 * 设置用户备注名
	 * @param openId	用户标识
	 * @param remark	新的备注名，长度必须小于30字符
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/fans/updateRemark")
	@ResponseBody
	public String updateRemark4Fans(String openId,String remark) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(openId == null || openId.trim().length() == 0 || remark == null || remark.trim().length() == 0) {
			jsonRet.put("errcode", "-777");
			jsonRet.put("errmsg", "用户ID与备注不可为空！");
			return jsonRet.toString();
		}
		openId = openId.trim();
		remark = remark.trim();
		if(remark.length() > 30) {
			jsonRet.put("errcode", "-888");
			jsonRet.put("errmsg", "备注长度超过30个字符！");
			return jsonRet.toString();
		}
		try {
			jsonRet = UserMgrHandle.updateRemark4User(openId, remark);
			if(jsonRet.has("errcode") && jsonRet.getInt("errcode") ==0) {
				this.fansBasicService.updateRemark(openId, remark);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 获取用户基本信息（包括UnionID机制）
	 * 更新系统中的用户
	 * @param openId	用户ID
	 * @return
	 * {   "errcode":0,"errmsg":"ok",
 	 *     "subscribe": 1, 	//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 *     "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", //用户的标识，对当前公众号唯一
	 *     "nickname": "Band",	//用户的昵称
	 *     "sex": 1, 			//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 *     "language": "zh_CN", 	//用户的语言，简体中文为zh_CN
	 *     "city": "广州", 		//用户所在城市
	 *     "province": "广东", 	//用户所在省份
 	 *     "country": "中国", 		//用户所在国家
 	 *     "headimgurl":"http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。
 	 *     "subscribe_time": 1382694957,	//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
 	 *     "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL",	//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
 	 *     "remark": "",		//公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 *     "groupid": 0,		//用户所在的分组ID（兼容旧的用户分组接口）
	 *     "tagid_list":[128,2],	//用户被打上的标签ID列表
	 *     "subscribe_scene": "ADD_SCENE_QR_CODE",	//返回用户关注的渠道来源
	 *     "qr_scene": 98765,	//二维码扫码场景（开发者自定义）
	 *     "qr_scene_str": ""	//二维码扫码场景描述（开发者自定义）
	 * }
	 * @throws JSONException
	 */
	@RequestMapping("/fans/getUserInfo")
	@ResponseBody
	public String getUserInfo(String openId) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(openId == null || openId.trim().length() == 0 ) {
			jsonRet.put("errcode", "-777");
			jsonRet.put("errmsg", "用户ID不可为空！");
			return jsonRet.toString();
		}
		openId = openId.trim();
		try {
			jsonRet = UserMgrHandle.getUserInfo(openId);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
				//更新用户信息
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 获取粉丝用户列表
	 * 一次拉取调用最多拉取10000个关注者的OpenID
	 * @param beginOpenId	第一个拉取的OPENID，不填默认从头开始拉取
	 * @return
	 * {   "errcode":0,"errmsg":"ok",
 	 *     "total":2,
 	 *     "count":2,
 	 *     "data":{"openid":["OPENID1","OPENID2"]},
 	 *     "next_openid":"NEXT_OPENID"
	 * }
	 * @throws JSONException
	 */
	//@RequestMapping("/getUsers")
	public JSONObject getUsers(@RequestParam(value="beginOpenId",required=false)String beginOpenId) throws JSONException {
		if(beginOpenId != null) {
			beginOpenId = beginOpenId.trim();
		}else {
			beginOpenId = "";
		}
		try {
			JSONObject jsonRet = UserMgrHandle.getUsers(beginOpenId);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
			return jsonRet;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
			return jsonRet;
		}
	}
	
	/**
	 * 获取公众号的黑名单列表
	 * 每次调用最多可拉取 10000 个OpenID
	 * @param beginOpenId	第一个拉取的OPENID，不填默认从头开始拉取
	 * @return
	 * {   "errcode":0,"errmsg":"ok",
 	 *     "total":2,
 	 *     "count":2,
 	 *     "data":{"openid":["OPENID1","OPENID2"]},
 	 *     "next_openid":"NEXT_OPENID"
	 * }
	 * @throws JSONException
	 */
	//@RequestMapping("/getBlackFans")
	public JSONObject getBlackFans(@RequestParam(value="beginOpenId",required=false)String beginOpenId) throws JSONException {
		beginOpenId = beginOpenId.trim();
		try {
			JSONObject jsonRet = UserMgrHandle.getBlackUsers(beginOpenId);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
			return jsonRet;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
			return jsonRet;
		}
	}
	
	/**
	 * 拉黑用户
	 * 一次只能拉黑20个用户
	 * @param openIds	用户ID列表
	 * @return {"errcode":40013,"errmsg":"invalid appid"}
	 * @throws JSONException 
	 */
	@RequestMapping("/fans/black")
	@ResponseBody
	public String blackFanses(String openIds) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(openIds == null || openIds.trim().length() == 0 ) {
			jsonRet.put("errcode", "-777");
			jsonRet.put("errmsg", "用户ID列表不可为空！");
			return jsonRet.toString();
		}
		openIds = openIds.trim();
		String[] arr = openIds.split(",");
		if(arr.length > 0) {
			jsonRet.put("errcode", "-999");
			jsonRet.put("errmsg", "一次只能拉黑20个用户！");
			return jsonRet.toString();
		}
		List<String> openIdList = new ArrayList<String>();
		for(String openId:arr) {
			openIdList.add(openId);
		}
		try {
			jsonRet = UserMgrHandle.blackUsers(openIdList);
			if(jsonRet.has("errcode") && jsonRet.getInt("errcode") ==0) {
				this.fansBasicService.blackFans(openIdList);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 取消拉黑用户
	 * 一次只能拉黑20个用户
	 * @param openIds	用户ID列表
	 * @return {"errcode":40013,"errmsg":"invalid appid"}
	 * @throws JSONException 
	 */
	@RequestMapping("/fans/unBlack")
	@ResponseBody
	public String unBlackFanses(String openIds) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(openIds == null || openIds.trim().length() == 0 ) {
			jsonRet.put("errcode", "-777");
			jsonRet.put("errmsg", "用户ID列表不可为空！");
			return jsonRet.toString();
		}
		openIds = openIds.trim();
		String[] arr = openIds.split(",");
		if(arr.length > 0) {
			jsonRet.put("errcode", "-999");
			jsonRet.put("errmsg", "一次只能拉黑20个用户！");
			return jsonRet.toString();
		}
		List<String> openIdList = new ArrayList<String>();
		for(String openId:arr) {
			openIdList.add(openId);
		}
		try {
			jsonRet = UserMgrHandle.blackUsers(openIdList);
			if(jsonRet.has("errcode") && jsonRet.getInt("errcode") ==0) {
				this.fansBasicService.unBlackFans(openIdList);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			jsonRet.put("errcode", "-666");
			jsonRet.put("errmsg", "系统出现异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 
	 * @param jsonParams 查询条件JSON格式
	 * @param pageCond	分页信息
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping("/fans/search")
	@ResponseBody
	public String searchAllFans(String jsonParams,PageCond pageCond) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		try {
			Map<String,Object> condParams = new HashMap<String,Object>();
			if(jsonParams != null) {
				JSONObject params = new JSONObject(jsonParams);
				//条件整合
				if(params.has("sex")) {
					if(params.getString("sex") != null && (params.getString("sex").trim().length()>0)){
						condParams.put("sex", params.getString("sex"));
					}
				}
				if(params.has("province")) {
					if(params.getString("province") != null && (params.getString("province").trim().length()>0)){
						condParams.put("province", params.getString("province"));
					}
				}
				if(params.has("city")) {
					if(params.getString("city") != null && (params.getString("city").trim().length()>0)){
						condParams.put("city", params.getString("city"));
					}
				}
				if(params.has("channel")) {
					if(params.getString("channel") != null && (params.getString("channel").trim().length()>0)){
						condParams.put("subscribeScene", params.getString("channel"));
					}
				}
				if(params.has("tagid")) {
					if(params.get("tagid") != null && params.getInt("tagid")>0){
						condParams.put("tagid", params.getInt("tagid"));
					}
				}
				if(params.has("isBlack")) {
					if(params.getString("isBlack") != null && (params.getString("isBlack").trim().length()>0)){
						condParams.put("isBlack", params.getString("isBlack"));
					}
				}
				if(params.has("openId")) {
					if(params.getString("openId") != null && (params.getString("openId").trim().length()>0)){
						condParams.put("openId", params.getString("openId"));
					}
				}
				if(params.has("subscribeTimeBegin")) {
					if(params.getString("subscribeTimeBegin") != null && (params.getString("subscribeTimeBegin").trim().length()>0)){
						condParams.put("subscribeTimeBegin", params.getString("subscribeTimeBegin"));
					}
				}
				if(params.has("subscribeTimeEnd")) {
					if(params.getString("subscribeTimeEnd") != null && (params.getString("subscribeTimeEnd").trim().length()>0)){
						condParams.put("subscribeTimeEnd", params.getString("subscribeTimeEnd"));
					}
				}
			}
			if(pageCond == null) {
				pageCond = new PageCond(0,20);
			}
			if(pageCond.getBegin()<0) {
				pageCond.setBegin(0);
			}
			if(pageCond.getPageSize() < 2 || pageCond.getPageSize() > 100) {
				pageCond.setPageSize(20);
			}
			
			int cnt = this.fansBasicService.countAll(condParams);
			pageCond.setCount(cnt);
			if(cnt > 0) {
			List<FansBasic> datas = this.fansBasicService.getAll(condParams, pageCond);
				jsonRet.put("datas", new JSONArray(datas));
			}
			JSONObject jaonPage = new JSONObject();
			jaonPage.put("pageSize", pageCond.getPageSize());
			jaonPage.put("begin", pageCond.getBegin());
			jaonPage.put("count", pageCond.getCount());
			jsonRet.put("errcode", 0);
			jsonRet.put("errmsg", "ok");
			jsonRet.put("pageCond", jaonPage);
		}catch(Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
}



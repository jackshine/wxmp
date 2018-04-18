package com.jeekhan.wx.api;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.utils.HttpUtils;

/**
 * 用户管理
 * @author Jee Khan
 *
 */
@Component
public class UserMgrHandle {
	private static Logger log = LoggerFactory.getLogger(UserMgrHandle.class);
	
	/**
	 * 创建用户标签
	 * 一个公众号，最多可以创建100个标签
	 * @param tagName	标签名长度不超过30个字节
	 * @return { "tag":{ "id":134,"name":"广东"} } 或 { "errcode":-1 "errmsg":"系统繁忙" }
	 * @throws JSONException
	 */
	public JSONObject addTag(String tagName) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		JSONObject tag = new JSONObject();
		tag.put("name", tagName);
		jsonObj.put("tag", tag);
		log.info("创建用户标签（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("创建用户标签（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取公众号已创建的标签
	 * @return {"tags":[{"id":1, "name":"每天一罐可乐星人","count":0},{"id":2,"name":"星标组","count":0 },{"id":127,"name":"广东","count":5 }] }
	 *  id:	分组id，由微信分配 
	 *  name :分组名字，UTF8编码 
	 *  count :此标签下粉丝数
	 * @throws JSONException 
	 */
	public JSONObject getAllTags() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=" + token ;
		log.info("查询所有已创建的标签(GET):" + url);
		String ret = HttpUtils.doGetSSL(url);
		log.info("查询所有已创建的标签(GET)返回:" + url);
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 编辑标签
	 * @param tagId	标签id，由微信分配 
	 * @param tagName	标签名字（30个字符以内） 
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject updateTag(String tagName,int tagId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		JSONObject tag = new JSONObject();
		tag.put("id", tagId);
		tag.put("name", tagName);
		jsonObj.put("tag", tag);
		log.info("编辑标签（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("编辑标签（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 删除标签
	 * 当某个标签下的粉丝超过10w时，后台不可直接删除标签。
	 * 此时，开发者可以对该标签下的openid列表，先进行取消标签的操作，直到粉丝数不超过10w后，才可直接删除该标签。
	 * @param tagId 分组的id 
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject deleteTag(int tagId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		JSONObject tag = new JSONObject();
		tag.put("id", tagId);
		jsonObj.put("tag", tag);
		log.info("删除标签（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("删除标签（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取用户身上的标签列表
	 * @param openId	用户的OpenID 
	 * @return {"tagid_list":[ 134, 2] } 或 { "errcode":-1,"errmsg":""}
	 * @throws JSONException 
	 */
	public JSONObject getTagsOnUser(String openId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid", openId);
		log.info("获取用户身上的标签列表（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取用户身上的标签列表（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);		
		return jsonRet;
	}

	
	/**
	 * 批量为用户取消标签
	 * @param openIdList	用户列表 
	 * @param tagId	分组id 
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject moveTagFromUsers(List<String> openIdList,int tagId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		JSONArray array = new JSONArray();
		for(String openId:openIdList){
			array.put(openId);
		}
		jsonObj.put("openid_list", array);
		jsonObj.put("tagid", tagId);
		log.info("批量为用户取消标签（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("批量为用户取消标签（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	

	/**
	 * 批量为用户打标签
	 * @param openIdList	用户列表 
	 * @param tagId	标签ID 
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject addTagToUsers(List<String> openIdList,int tagId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("tagid", tagId);
		JSONArray list = new JSONArray();
		for(String openId:openIdList) {
			list.put(openId);
		}
		jsonObj.put("openid_list", list);
		log.info("批量为用户打标签（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("批量为用户打标签（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 设置用户备注名
	 * @param openId	用户ID
	 * @param remark	备注信息
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject updateRemark4User(String openId,String remark) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid", openId);
		jsonObj.put("remark", remark);
		log.info("设置用户备注名（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("设置用户备注名（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取用户基本信息（包括UnionID机制）
	 * @param openId
	 * @return
	 * @throws JSONException 
	 */
	public JSONObject getUserInfo(String openId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openId + "&lang=" + "zh_CN";
		log.info("获取用户基本信息（GET）" + url );
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取用户基本信息（GET）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 批量获取用户基本信息
	 * 最多支持一次拉取100条。
	 * @param openIds	用户的标识，对当前公众号唯一 
	 * @return
	 * @throws JSONException 
	 */
	public JSONObject getUsersInfo(List<String> openIds) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		JSONArray array = new JSONArray();
		for(String openId:openIds){
			JSONObject user = new JSONObject();
			user.put("openid",openId);
			user.put("lang","zh_CN");
			array.put(user);
		}
		jsonObj.put("user_list", array);
		log.info("批量获取用户基本信息（POST）" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("批量获取用户基本信息（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取标签下粉丝列表
	 * 公众号可通过本接口来获取帐号的关注者列表，关注者列表由一串OpenID组成。
	 * 	一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求
	 * @param tagId	标签ID
	 * @param nextOpenId	第一个拉取的OPENID，不填默认从头开始拉取 
	 * @return {count":2,"data":{"openid":["OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
	 * @throws JSONException 
	 */
	public JSONObject getUsersByTag(int tagId,String nextOpenId) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("tagid", tagId);
		jsonObj.put("next_openid", nextOpenId);
		log.info("获取标签下粉丝列表（POST）" + url );
		String ret = HttpUtils.doPostSSL(url,jsonObj);
		log.info("获取标签下粉丝列表（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}

	/**
	 * 获取用户列表
	 * 公众号可通过本接口来获取帐号的关注者列表，关注者列表由一串OpenID;
	 * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
	 * @param nextOpenid	第一个拉取的OPENID，不填默认从头开始拉取
	 * @return {"total":2,"count":2,"data":{"openid":["OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"} 或 {"errcode":40013,"errmsg":"invalid appid"}
	 * @throws JSONException 
	 */
	public JSONObject getUsers(String nextOpenid) throws JSONException {
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + token + "&next_openid=" + nextOpenid ;
		log.info("获取用户列表（GET）" + url );
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取用户列表（GET）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}

	/**
	 * 获取公众号的黑名单列表
	 * 公众号可通过该接口来获取帐号的黑名单列表，黑名单列表由一串 OpenID组成。
	 * 该接口每次调用最多可拉取 10000 个OpenID，当列表数较多时，可以通过多次拉取的方式来满足需求.
	 * @param beginOpenid	第一个拉取的OPENID，不填默认从头开始拉取 
	 * @return {"total":23000,count":2,"data":{"openid":["OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"} 或 {"errcode":40013,"errmsg":"invalid appid"}
	 * @throws JSONException 
	 */
	public JSONObject getBlackUsers(String beginOpenid) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("begin_openid", beginOpenid);
		log.info("获取公众号的黑名单列表（POST）" + url );
		String ret = HttpUtils.doPostSSL(url,jsonObj);
		log.info("获取公众号的黑名单列表（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 批量拉黑用户
	 * 公众号可通过该接口来拉黑一批用户，黑名单列表由一串 OpenID组成。
	 * @param openIdList	用户ID列表
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject blackUsers(List<String> openIdList) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		JSONArray array = new JSONArray();
		for(String openId:openIdList){
			array.put(openId);
		}
		jsonObj.put("openid_list", array);
		log.info("拉黑用户（POST）" + url );
		String ret = HttpUtils.doPostSSL(url,jsonObj);
		log.info("拉黑用户（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 取消拉黑用户
	 * 公众号可通过该接口来取消拉黑一批用户，黑名单列表由一串OpenID组成。
	 * @param openIdList	用户ID列表
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public JSONObject unBlackUsers(List<String> openIdList) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		JSONArray array = new JSONArray();
		for(String openId:openIdList){
			array.put(openId);
		}
		jsonObj.put("openid_list", array);
		log.info("取消拉黑用户（POST）" + url );
		String ret = HttpUtils.doPostSSL(url,jsonObj);
		log.info("取消拉黑用户（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
}

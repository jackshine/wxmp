package com.jeekhan.wx.scheduling;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.api.UserMgrHandle;
import com.jeekhan.wx.model.FansBasic;
import com.jeekhan.wx.model.WXMsgLog;
import com.jeekhan.wx.service.FansBasicService;
import com.jeekhan.wx.service.WXMsgLogService;
import com.jeekhan.wx.utils.HttpUtils;

/**
 * 将待注册用户发送至摩放优选服务中心
 * @author jeekhan
 *
 */
@Component
public class SendUser4RegistSchedule {
	
	@Autowired
	private FansBasicService fansBasicService;
	@Autowired
	private WXMsgLogService wXMsgLogService;
	
	@Autowired
	private UserMgrHandle userMgrHandle;
	
	@Value("${busi.user-create-url}")
	private String mfyxUserRegistUrl = "";//用户注册地址
	
	@Value("${busi.user-cancel-url}")
	private String mfyxUserCancelUrl = ""; //用户注销地址
	
	private static Logger log = LoggerFactory.getLogger(SendUser4RegistSchedule.class);
	
	//粉丝关注-注册用户
	//每间隔三分钟执行一次
	@Scheduled(initialDelay=6000,fixedDelay=180000)
	public void sendUser4Regist() {
		List<WXMsgLog> list = wXMsgLogService.get4RegistFans();
		if(list == null || list.size() <= 0) {
			return;
		}
		for(WXMsgLog msgLog :list) {
			try {
				/* {   "errcode":0,"errmsg":"ok",
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
				 * }*/
				JSONObject jsonUser = this.userMgrHandle.getUserInfo(msgLog.getFromUser());
				if(!jsonUser.has("openid")) {//失败
					log.info("用户注册定时服务，向微信服务器获取用户详细信息失败，OPENID：" + msgLog.getFromUser());
					return;
				}
				if(this.fansBasicService.get(jsonUser.getString("openid")) != null) {
					//补漏
					BigInteger respId = new BigInteger("0");
					this.wXMsgLogService.updateRespInfo(msgLog.getId(), respId);
					return;
				}
				FansBasic user = new FansBasic();
				user.setOpenId(jsonUser.getString("openid"));
				user.setNickname(jsonUser.getString("nickname"));
				user.setSex(jsonUser.has("sex")?jsonUser.getInt("sex")+"":"0");
				user.setLanguage(jsonUser.getString("language"));
				user.setCity(jsonUser.has("city")?jsonUser.getString("city"):"");
				user.setProvince(jsonUser.has("province")?jsonUser.getString("province"):"");
				user.setCountry(jsonUser.has("country")?jsonUser.getString("country"):"");
				user.setHeadimgurl(jsonUser.has("headimgurl") ?jsonUser.getString("headimgurl"):"");
				user.setSubscribeTime(new Date(jsonUser.getInt("subscribe_time")));
				user.setUnionid(jsonUser.has("unionid")?jsonUser.getString("unionid"):"");
				user.setRemark("");
				user.setTagidList("0,0");
				user.setSubscribeScene(jsonUser.has("subscribe_scene")?jsonUser.getString("subscribe_scene"):"");
				user.setQrScene(jsonUser.has("qr_scene")?jsonUser.getInt("qr_scene"):null);
				user.setQrSceneStr("");
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("nickname", user.getNickname());
				//params.put("email", "");
				//params.put("birthday", null);
				params.put("sex", user.getSex());
				//params.put("passwd", "");
				params.put("country", user.getCountry());
				params.put("province", user.getProvince());
				params.put("city", user.getCity());
				//params.put("favourite", "");
				//params.put("profession", "");
				//params.put("introduce", "");
				params.put("headimgurl", user.getHeadimgurl());
				//params.put("phone", "");
				
				params.put("openId", user.getOpenId());
				params.put("unionId", user.getUnionid());
				params.put("senceId", user.getQrScene());
				params.put("registType", "2");
				
				log.info("用户注册定时服务，发送用户注册请求，用户OPENID：" + user.getOpenId());
				String ret = HttpUtils.doPost(this.mfyxUserRegistUrl, params);
				JSONObject jsonRet = new JSONObject(ret);
				if(jsonRet.has("errcode") && jsonRet.getInt("errcode") == 0) {
					msgLog.setStatus("2");
					//使用客服消息发送注册成功提示
					BigInteger respId = new BigInteger("1111");
					
					this.wXMsgLogService.updateRespInfo(msgLog.getId(), respId);
					this.fansBasicService.add(user);
					log.info("用户注册定时服务，用户注册成功，用户OPENID：" + user.getOpenId());
				}else {
					log.info("用户注册定时服务，用户注册失败，失败信息："+ jsonRet.getString("errmsg") +"用户OPENID：" + user.getOpenId());
				}
			} catch (JSONException e) {
				log.error("用户注册定时服务，出现异常：" + e.getMessage());
			}
			
		}
	}

	//粉丝取消关注-注销用户
	//每月1号1点执行一次
	//@Scheduled(cron="0 0 1 1 * ?")
	public void sendUser4Cancel() {
		
	}
	
}

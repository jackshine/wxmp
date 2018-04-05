package com.jeekhan.wx.api;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeekhan.wx.utils.HttpUtils;

public class WXCardHandle {
	private static Logger log = LoggerFactory.getLogger(WXCardHandle.class);
	
	/**
	 * 上传图片
	 * 1.上传的图片限制文件大小限制1MB，像素为300*300，仅支持JPG、PNG格式。 
	 * 2.调用接口获取的logo_url仅支持在微信相关业务下使用
	 * @param file
	 * @return	{"url":"http://mmbiz.qpic.cn/mmbiz/iaL1LJM1mF9aRKPZJkmG8xXhiaHqkKSVMMWeN3hLut7X7hicFNjakmxibMLGWpXrEXB33367o7zHN0CwngnQY7zb7g/0"}
	 * @throws JSONException 
	 */
	public static Object uploadImg(File file) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + token ;
		log.info("上传图片（POST）：" + url);
		String ret = HttpUtils.uploadFileSSL(url, file,"buffer",new HashMap<String,String>());
		log.info("上传图片返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 创建卡券/朋友券
	 * @param cardInfo
	 * @return {"errcode":0,"errmsg":"ok","card_id":"p1Pj9jr90_SQRaVqYI239Ka1erkI"}
	 * @throws JSONException 
	 */
	public static Object createCard(JSONObject cardInfo) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/create?access_token=" + token ;
		log.info("创建卡券（POST）：" + url + "，参数：" + cardInfo);
		String ret = HttpUtils.doPostSSL(url, cardInfo);
		log.info("创建卡券返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 更改卡券信息
	 * @param cardInfo
	 * @return
	 * 	errcode 
	 * 	errmsg 
	 * 	send_check	是否提交审核，false为修改后不会重新提审，true为修改字段后重新提审，该卡券的状态变为审核中。 
	 * @throws JSONException 
	 */
	public static Object cupdateCard(JSONObject cardInfo) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/update?access_token=" + token ;
		log.info("更改卡券信息（POST）：" + url + "，参数：" + cardInfo);
		String ret = HttpUtils.doPostSSL(url, cardInfo);
		log.info("更改卡券信息返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	/**
	 * 设置卡券支持微信买单
	 * @param cardId	卡券ID
	 * @param isOpen	是否开启买单功能，填true/false 
	 * @return 00-成功，【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String openWXPay(String cardId,boolean isOpen) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/paycell/set?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id", cardId);
		jsonObj.put("is_open", isOpen);
		log.info("设置卡券支持微信买单（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("设置卡券支持微信买单返回：" + ret );
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "设置卡券支持微信买单失败！";
			}
		}
	}
	
	/**
	 * 设置开通自助核销
	 * @param cardId	卡券ID
	 * @param isOpen	是否开启自助核销功能，填true/false 
	 * @return
	 * @throws JSONException 
	 */
	public static String openCheckSell(String cardId,boolean isOpen) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/selfconsumecell/set?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id", cardId);
		jsonObj.put("is_open", isOpen);
		log.info("设置开通自助核销（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("设置开通自助核销返回：" + ret );
		JSONObject retObj = new JSONObject(ret);
		if(retObj.has("errcode") && retObj.getInt("errcode") == 0){
			return "00";
		}else{
			if(retObj.has("errmsg")){
				return retObj.getString("errmsg");
			}else{
				return "设置开通自助核销失败！";
			}
		}
	}
	
	/**
	 * 创建卡券二维码
	 * @param expire_seconds	指定二维码的有效时间，范围是60 ~ 1800秒。不填默认为永久有效
	 * @param card_id	卡券ID
	 * @param code		卡券Code码,use_custom_code字段为true的卡券必须填写，非自定义code不必填写
	 * @param openid	指定领取者的openid，只有该用户能领取。bind_openid字段为true的卡券必须填写，非指定openid不必填写
	 * @param is_unique_code	指定下发二维码，生成的二维码随机分配一个code，领取后不可再次扫描。填写true或false。默认false，注意填写该字段时，卡券须通过审核且库存不为0
	 * @param outer_id	领取场景值，用于领取渠道的数据统计，默认值为0，字段类型为整型，长度限制为60位数字。用户领取卡券后触发的事件推送中会带上此自定义场景值
	 * @return {"errcode": 0,"errmsg": "ok","ticket":"","expire_seconds": 1800,"url": "","show_qrcode_url": "" }
	 * 	ticket ：获取的二维码ticket，凭借此ticket调用通过ticket换取二维码接口可以在有效时间内换取二维码。
	 * 	url：二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片 
	 * 	show_qrcode_url：二维码显示地址，点击后跳转二维码页面
	 * @throws JSONException 
	 */
	public static Object createCardQRCode(int expire_seconds,String card_id,String code,String openid,boolean is_unique_code,int outer_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/qrcode/create?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action_name","QR_CARD");
		jsonObj.put("expire_seconds", expire_seconds);
		JSONObject card = new JSONObject();
		card.put("card_id", card_id);
		card.put("code", code);
		card.put("openid", openid);
		card.put("is_unique_code", is_unique_code);
		card.put("outer_id", outer_id);
		JSONObject  action_info = new JSONObject();
		action_info.put("card", card);
		jsonObj.put("action_info", action_info);
		log.info("创建卡券二维码（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("创建卡券二维码返回：" + ret );
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 *  创建卡券二维码（扫描二维码领取多张卡券）
	 * @param cardList	卡券列表[卡券ID，卡券Code码]
	 * @return {"errcode": 0,"errmsg": "ok","ticket":"","expire_seconds": 1800,"url": "","show_qrcode_url": "" }
	 * 	ticket ：获取的二维码ticket，凭借此ticket调用通过ticket换取二维码接口可以在有效时间内换取二维码。
	 *	url：二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片 
	 * 	show_qrcode_url：二维码显示地址，点击后跳转二维码页面
	 * @throws JSONException 
	 */
	public static Object createCardQRCode(List<String[]> cardList) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/qrcode/create?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("action_name","QR_MULTIPLE_CARD");
		JSONObject multiple_card = new JSONObject();
		JSONArray array = new JSONArray();
		for(String[] cardInfo:cardList){
			JSONObject card = new JSONObject();
			card.put("card_id", cardInfo[0]);
			card.put("code", cardInfo[1]);
			array.put(card);
		}
		multiple_card.put("card_list", array);
		JSONObject action_info = new JSONObject();
		action_info.put("multiple_card", multiple_card);
		jsonObj.put("action_info", action_info);
		log.info("创建卡券二维码（扫描二维码领取多张卡券）（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("创建卡券二维码（扫描二维码领取多张卡券）返回：" + ret );
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 创建卡券货架
	 * @param banner	页面的banner图片链接，须调用，建议尺寸为640*300
	 * @param page_title	页面的title
	 * @param can_share		页面是否可以分享,填入true/false 
	 * @param scene			投放页面的场景值； SCENE_NEAR_BY 附近 SCENE_MENU 自定义菜单 SCENE_QRCODE 二维码 SCENE_ARTICLE 公众号文章 SCENE_H5 h5页面 SCENE_IVR 自动回复 SCENE_CARD_CUSTOM_CELL 卡券自定义cell 
	 * @param cardList		卡券列表，每个item有两个字段 [所要在页面投放的cardid ,缩略图url ]
	 * @return
	 * @throws JSONException 
	 */
	public static Object createLandPage(String banner,String page_title,boolean can_share,String scene,List<String[]> cardList) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/landingpage/create?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("banner",banner);
		jsonObj.put("page_title",page_title);
		jsonObj.put("can_share",can_share);
		jsonObj.put("scene",scene);
		JSONArray array = new JSONArray();
		for(String[] cardInfo:cardList){
			JSONObject card = new JSONObject();
			card.put("card_id", cardInfo[0]);
			card.put("thumb_url", cardInfo[1]);
			array.put(card);
		}
		jsonObj.put("card_list", array);
		log.info("创建卡券货架（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("创建卡券货架返回：" + ret );
		JSONObject retObj = new JSONObject(ret);
		return retObj;
	}
	
	/**
	 * 导入卡券自定义code
	 * @param card_id	需要进行导入code的卡券ID
	 * @param codes		需导入微信卡券后台的自定义code，上限为100个
	 * @return 
	 * 	errcode:错误码，0为正常；40109：code数量超过100个
	 * 	errmsg :错误信息
	 * 	succ_code :成功个数
	 * 	duplicate_code :重复导入的code会自动被过滤。
	 * 	fail_code :失败个数
	 * @throws JSONException 
	 */
	public static JSONObject deposit(String card_id,List<String> codes) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "http://api.weixin.qq.com/card/code/deposit?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		JSONArray array = new JSONArray();
		for(String code:codes){
			array.put(code);
		}
		jsonObj.put("code", array);
		log.info("导入卡券自定义code（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("导入卡券自定义code返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	} 
	
	/**
	 * 查询卡券导入code数目
	 * @param card_id	进行导入code的卡券ID
	 * @return	-1:失败；>=0 ：code 数目
	 * @throws JSONException 
	 */
	public static int getDepositCount(String card_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "http://api.weixin.qq.com/card/code/getdepositcount?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		log.info("查询卡券导入code数目（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("查询卡券导入code数目返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		if(jsonRet.has("count")){
			return jsonRet.getInt("count");
		}else{
			return -1;
		}
	}
	
	/**
	 * 核查卡券code
	 * @param card_id	进行导入code的卡券ID
	 * @param codes		已经微信卡券后台的自定义code，上限为100个
	 * @return
	 * 	errcode 
	 * 	errmsg 
	 * 	exist_code 		已经成功存入的code
	 * 	not_exist_code 	没有存入的code
	 * @throws JSONException 
	 */
	public static JSONObject checkCode(String card_id,List<String> codes) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "http://api.weixin.qq.com/card/code/checkcode?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		JSONArray array = new JSONArray();
		for(String code:codes){
			array.put(code);
		}
		jsonObj.put("code", array);
		log.info("核查卡券code（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("核查卡券code返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	} 
	
	/**
	 * 获取用于图文消息群发卡券的内容
	 * @param card_id
	 * @return
	 * 	errcode 
	 * 	errmsg 
	 * 	content ：返回一段html代码，可以直接嵌入到图文消息的正文里。即可以把这段代码嵌入到上传图文消息素材接口中的content字段里
	 * @throws JSONException 
	 */
	public static JSONObject getNewsHtml(String card_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/mpnews/gethtml?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		log.info("获取用于图文消息群发卡券的内容（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取用于图文消息群发卡券的内容返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	} 
	
	/**
	 * 查询卡券Code
	 * @param card_id	卡券ID代表一类卡券。自定义code卡券必填
	 * @param code		单张卡券的唯一标准
	 * @param check_consume 是否校验code核销状态，填入true和false时的code异常状态返回数据不同
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getCard(String card_id,String code,boolean check_consume ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/code/get?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		jsonObj.put("code",code);
		jsonObj.put("check_consume",check_consume);
		log.info("查询卡券Code（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("查询卡券Code返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 核销Code
	 * @param card_id
	 * @param code
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject consumeCode(String card_id,String code) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/code/consume?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		jsonObj.put("code",code);
		log.info("核销卡券Code（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("核销卡券Code返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * Code解码
	 * @param encrypt_code	加密后的code
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject codeDecrypt(String encrypt_code) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/code/decrypt?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("encrypt_code",encrypt_code);
		log.info("Code解码（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("Code解码返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取用户已领取卡券
	 * @param openid	需要查询的用户openid 
	 * @param card_id	卡券ID。不填写时默认查询当前appid下的卡券
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject userGetCardList(String openid ,String card_id ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/user/getcardlist?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid",openid);
		jsonObj.put("card_id",card_id);
		log.info("获取用户已领取卡券（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取用户已领取卡券返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 查看卡券详情
	 * @param card_id
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getCard(String card_id ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/get?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		log.info("查看卡券详情（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("查看卡券详情返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 批量查询卡券列表
	 * @param offset	查询卡列表的起始偏移量，从0开始，即offset: 5是指从从列表里的第六个开始读取。
	 * @param count		需要查询的卡片的数量（数量最大50）。 
	 * @param status_list	支持开发者拉出指定状态的卡券列表，例：仅拉出通过审核的卡券。 
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getCards(int offset ,int count,List<String> status_list  ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/batchget?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("offset",offset);
		jsonObj.put("count",count);
		JSONArray array = new JSONArray();
		for(String str: status_list){
			array.put(str);
		}
		jsonObj.put("status_list",array);
		log.info("批量查询卡券列表（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("批量查询卡券列表返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 修改库存
	 * @param card_id	卡券ID
	 * @param increase_stock_value	增加多少库存，支持不填或填0
	 * @param reduce_stock_value	减少多少库存，可以不填或填0
	 * @return 00-成功；【其他】-失败信息
	 * @throws JSONException 
	 */
	public static String modifyStock(String card_id,int increase_stock_value ,int reduce_stock_value ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/modifystock?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		jsonObj.put("increase_stock_value",increase_stock_value);
		jsonObj.put("reduce_stock_value",reduce_stock_value);
		log.info("修改库存（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("修改库存返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		if(jsonRet.has("errcode") && jsonRet.getInt("errcode") == 0){
			return "00";
		}else{
			if(jsonRet.has("errmsg")){
				return jsonRet.getString("errmsg");
			}else{
				return "修改库存失败";
			}
		}
	}
	/**
	 * 更改Code
	 * @param card_id	卡券ID。自定义Code码卡券为必填
	 * @param code		需变更的Code码
	 * @param new_code	变更后的有效Code码
	 * @return
	 * @throws JSONException 
	 */
	public static String updateCode(String card_id,String code ,String new_code  ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/code/update?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		jsonObj.put("code",code);
		jsonObj.put("new_code",new_code);
		log.info("更改Code（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("更改Code返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		if(jsonRet.has("errcode") && jsonRet.getInt("errcode") == 0){
			return "00";
		}else{
			if(jsonRet.has("errmsg")){
				return jsonRet.getString("errmsg");
			}else{
				return "更改Code失败";
			}
		}
	}
	
	/**
	 * 删除卡券
	 * 删除卡券不能删除已被用户领取，保存在微信客户端中的卡券
	 * @param card_id
	 * @return
	 * @throws JSONException 
	 */
	public static String deleteCard(String card_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/delete?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		log.info("删除卡券（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("删除卡券返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		if(jsonRet.has("errcode") && jsonRet.getInt("errcode") == 0){
			return "00";
		}else{
			if(jsonRet.has("errmsg")){
				return jsonRet.getString("errmsg");
			}else{
				return "删除卡券失败";
			}
		}
	}
	
	/**
	 * 设置卡券失效
	 * 为满足改票、退款等异常情况，可调用卡券失效接口将用户的卡券设置为失效状态
	 * @param card_id
	 * @param code
	 * @return
	 * @throws JSONException 
	 */
	public static String setCodeUnavailable(String card_id,String code ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/code/unavailable?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		jsonObj.put("code",code);
		log.info("设置卡券失效（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("设置卡券失效返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		if(jsonRet.has("errcode") && jsonRet.getInt("errcode") == 0){
			return "00";
		}else{
			if(jsonRet.has("errmsg")){
				return jsonRet.getString("errmsg");
			}else{
				return "设置卡券失效失败";
			}
		}
	}
	
	/**
	 * 拉取卡券概况数据
	 * @param begin_date	查询数据的起始时间，示例：2015-06-15 
	 * @param end_date		查询数据的截至时间
	 * @param cond_source	卡券来源，0为公众平台创建的卡券数据、1是API创建的卡券数据 
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getCardBizuinInfo(String begin_date,String end_date ,int cond_source  ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/datacube/getcardbizuininfo?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("begin_date",begin_date);
		jsonObj.put("end_date",end_date);
		jsonObj.put("cond_source",cond_source);
		log.info("拉取卡券概况数据（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("拉取卡券概况数据返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取免费券数据
	 * @param begin_date	查询数据的起始时间，示例：2015-06-15 
	 * @param end_date		查询数据的截至时间
	 * @param cond_source	卡券来源，0为公众平台创建的卡券数据、1是API创建的卡券数据 
	 * @param card_id		卡券ID,填写后，指定拉出该卡券的相关数据。
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getCardInfo(String begin_date,String end_date ,int cond_source ,String card_id ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/datacube/getcardcardinfo?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("begin_date",begin_date);
		jsonObj.put("end_date",end_date);
		jsonObj.put("cond_source",cond_source);
		log.info("获取免费券数据（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("获取免费券数据返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 拉取会员卡数据
	 * @param begin_date	查询数据的起始时间，示例：2015-06-15 
	 * @param end_date		查询数据的截至时间
	 * @param cond_source	卡券来源，0为公众平台创建的卡券数据、1是API创建的卡券数据 
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getMemberCardInfo(String begin_date,String end_date ,int cond_source  ) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/datacube/getcardmembercardinfo?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("begin_date",begin_date);
		jsonObj.put("end_date",end_date);
		jsonObj.put("cond_source",cond_source);
		log.info("拉取会员卡数据（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("拉取会员卡数据返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 开通券点账户
	 * @return
	 * 	errcode
	 * 	errmsg
	 * 	reward	奖励券点数量，以元为单位，微信卡券对每一个新开通券点账户的商户奖励200个券点
	 * @throws JSONException 
	 */
	public static JSONObject activateCardPay() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/pay/activate?access_token=" + token ;
		log.info("开通券点账户（GET）：" + url );
		String ret = HttpUtils.doGetSSL(url);
		log.info("开通券点账户返回（GET）：" + url );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 对优惠券批价
	 * 本接口用于提前查询本次新增库存需要多少券点 
	 * @param card_id	需要来配置库存的card_id 
	 * @param quantity	本次需要兑换的库存数目 
	 * @return
	 * 	order_id:本次批价的订单号，用于下面的确认充值库存接口，仅对当前订单有效且仅可以使用一次，60s内可用于兑换库存
	 * 	price :本次需要支付的券点总额度
	 * 	free_coin:本次需要支付的免费券点额度
	 * 	pay_coin:本次需要支付的付费券点额度 
	 * @throws JSONException 
	 */
	public static JSONObject getPayPrice(String card_id,int quantity) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/pay/getpayprice?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		jsonObj.put("quantity",quantity);
		log.info("对优惠券批价（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("对优惠券批价返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 查询券点余额
	 * 本接口用于开发者查询当前券点账户中的免费券点/付费券点数目以及总额。 
	 * @return
	 * 	free_coin：免费券点数目 
	 * 	pay_coin :付费券点数目 
	 * 	total_coin ：全部券点数目 
	 * @throws JSONException 
	 */
	public static JSONObject getCoinsInfo() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/pay/getcoinsinfo?access_token=" + token ;
		log.info("查询券点余额（GET）：" + url );
		String ret = HttpUtils.doGetSSL(url);
		log.info("查询券点余额返回（GET）：" + url );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 确认兑换库存
	 * @param card_id	需要来兑换库存的card_id 
	 * @param quantity	本次需要兑换的库存数目 
	 * @param order_id	仅可以使用上面得到的订单号，保证批价有效性 ,获得的order_id须在60s内使用，否则确认兑换库存接口将会失效 
	 * @return
	 * @throws JSONException 
	 */
	public static String getPayConfirm(String card_id,int quantity,String order_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/pay/confirm?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		jsonObj.put("quantity",quantity);
		jsonObj.put("order_id",order_id);
		log.info("确认兑换库存（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("确认兑换库存返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		if(jsonRet.has("errcode") && jsonRet.getInt("errcode") == 0){
			return "00";
		}else{
			if(jsonRet.has("errmsg")){
				return jsonRet.getString("errmsg");
			}else{
				return "确认兑换库存失败";
			}
		}
	}
	
	/**
	 * 充值券点
	 * @param coin_count
	 * @return
	 * 	order_id:本次支付的订单号，用于查询订单状态
	 * 	qrcode_url:支付二维码的的链接，开发者可以调用二维码生成的公开库转化为二维码显示在网页上，微信扫码支付 
	 * 	qrcode_buffer：二维码的数据流，开发者可以使用写入一个文件的方法显示该二维码 
	 * @throws JSONException 
	 */
	public static JSONObject reCharge(String coin_count) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/pay/confirm?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("coin_count",coin_count);
		log.info("充值券点（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("充值券点返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 查询充值订单详情
	 * @param order_id
	 * @return
	 * 	order_info 
	 * 		order_id ：订单号
	 * 		status ：订单状态，ORDER_STATUS_WAITING 等待支付 ORDER_STATUS_SUCC 支付成功 ORDER_STATUS_FINANCE_SUCC 加代币成功 ORDER_STATUS_QUANTITY_SUCC 加库存成功 ORDER_STATUS_HAS_REFUND 已退币 ORDER_STATUS_REFUND_WAITING 等待退币确认 ORDER_STATUS_ROLLBACK 已回退,系统失败 ORDER_STATUS_HAS_RECEIPT 已开发票 
	 * 		create_time ：订单创建时间 
	 * 		pay_finish_time :支付完成时间 
	 * 		desc :支付描述，一般为微信支付充值 
	 * 		free_coin_count :本次充值的免费券点数量，以元为单位
	 * 		pay_coin_count :本次充值的付费券点数量，以元为单位 
	 * 		refund_free_coin_count :回退的免费券点 
	 * 		refund_pay_coin_count :回退的付费券点 
	 * 		openid :支付人的openid 
	 * 		order_tpye :订单类型，ORDER_TYPE_WXPAY为充值 
	 * @throws JSONException 
	 */
	public static JSONObject getReChargeOrder(String order_id) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/pay/confirm?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("order_id",order_id);
		log.info("查询充值订单详情（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("查询充值订单详情返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 查询券点流水详情
	 * @param offset	分批查询的起点，默认为0 
	 * @param count		分批查询的数量 
	 * @param order_type	所要拉取的订单类型 ORDER_TYPE_SYS_ADD 平台赠送 ORDER_TYPE_WXPAY 充值 ORDER_TYPE_REFUND 库存回退券点 ORDER_TYPE_REDUCE 券点兑换库存 ORDER_TYPE_SYS_REDUCE 平台扣减 
	 * @param begin_time	批量查询订单的起始事件，为时间戳，默认1周前 
	 * @param end_time		批量查询订单的结束事件，为时间戳，默认为当前时间
	 * @param notStatuses	不要拉取的订单 状态
	 * @param sort_key	排序依据，SORT_BY_TIME 以订单时间排序
	 * @param sort_type	排序规则，SORT_ASC 升序 SORT_DESC 降序 
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getOrderList(int offset, int count,String order_type,String begin_time,String end_time,String notStatuses ,String sort_key ,String sort_type) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/pay/confirm?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("offset",offset);
		jsonObj.put("count",count);
		jsonObj.put("order_type",order_type);
		jsonObj.put("begin_time",begin_time);
		jsonObj.put("end_time",end_time);
		JSONObject notFilter = new JSONObject();
		notFilter.put("status",notStatuses);
		JSONObject sort_info = new JSONObject();
		sort_info.put("sort_key",sort_key);
		sort_info.put("sort_type",sort_type);
		jsonObj.put("nor_filter",notFilter);
		jsonObj.put("sort_info",sort_info);
		log.info("查询充值订单详情（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("查询充值订单详情返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * Mark(占用)Code
	 * @param card_id	卡券的ID。
	 * @param openid	用券用户的openid。 
	 * @param is_mark	是否要mark（占用）这个code，填写true或者false，表示占用或解除占用。 
	 * @return
	 * @throws JSONException 
	 */
	public static String getReChargeOrder(String card_id,String openid ,boolean is_mark) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/card/code/mark?access_token=" + token ;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("card_id",card_id);
		jsonObj.put("openid",openid);
		jsonObj.put("is_mark",is_mark);
		log.info("Mark(占用)Code（POST）：" + url + "，参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("Mark(占用)Code返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		if(jsonRet.has("errcode") && jsonRet.getInt("errcode") == 0){
			return "00";
		}else{
			if(jsonRet.has("errmsg")){
				return jsonRet.getString("errmsg");
			}else{
				return "Mark(占用)Code失败";
			}
		}
	}
}

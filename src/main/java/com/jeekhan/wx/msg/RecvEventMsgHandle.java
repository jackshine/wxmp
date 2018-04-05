package com.jeekhan.wx.msg;

import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.api.UserMgrHandle;
import com.jeekhan.wx.api.WXProcess;
import com.jeekhan.wx.utils.HttpUtils;

/**
 * 接收微信事件消息，并作相应的处理
 * 在微信用户和公众号产生交互的过程中，用户的某些操作会使得微信服务器通过事件推送的形式通知到开发者；
 * 开发者可完成对该类的继承重写，以实现自己的处理逻辑；
 * @author Administrator
 *
 */
@Component
public class RecvEventMsgHandle {
	@Value("${busi.user-create-url}")
	private String userCreateURl;	//用户注册请求URL
	@Value("${busi.user-cancel-url}")
	private String userCancelURl;	//用户注销请求URL
	@Autowired
	private RespMsgHandle respMsgHandle;
	
	
	/**
	 * 1、关注/取消关注事件消息
	 * <xml>
	 *<ToUserName><![CDATA[toUser]]></ToUserName>		开发者微信号
	 *<FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID）
	 *<CreateTime>123456789</CreateTime>				消息创建时间 （整型） 
	 *<MsgType><![CDATA[event]]></MsgType>				消息类型，event 
	 *<Event><![CDATA[subscribe/unsubscribe]]></Event>	事件类型，subscribe(订阅)、unsubscribe(取消订阅) 
	 *</xml>
	 * 2、 扫描公众号的带参数二维码事件
	 * 如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。 
	 * 用户未关注时，进行关注后的事件推送  
	 * <xml><ToUserName><![CDATA[toUser]]></ToUserName>		开发者微信号
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID）
	 * <CreateTime>123456789</CreateTime>					消息创建时间 （整型） 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[subscribe]]></Event>					事件类型，subscribe(订阅)	
	 * <EventKey><![CDATA[qrscene_123123]]></EventKey>		事件KEY值，qrscene_为前缀，后面为二维码的参数值 
	 * <Ticket><![CDATA[TICKET]]></Ticket>					二维码的ticket，可用来换取二维码图片 
	 * </xml>
	 */
	protected Object recvEventSubscribe(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		//调用用户注册回调
		new WXProcess() {
			@Override 
			public void process() {
				try {
					JSONObject user = UserMgrHandle.getUserInfo(fromUser);
					if(user != null) {
						HttpUtils.doPost(userCreateURl, user);	//发送注册请求给业务服务器
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}.process();
		return respMsgHandle.respTextMsg(fromUser,"欢迎您加入我们！！！");
	}
	
	/**
	 * 取消关注
	 * @param msgMap
	 * @return
	 */
	protected Object recvEventUnsubscribe(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		//调用用户注销回调
		new WXProcess() {
			@Override 
			public void process() {
				try {
					JSONObject user = new JSONObject();
					user.put("openid", fromUser);
					if(user != null) {
						HttpUtils.doPost(userCancelURl, user);	//发送注销请求给业务服务器
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}.process();
		return respMsgHandle.respTextMsg(fromUser,"多谢您以往的支持，期盼您的再次回来！！！");
	}
	
	/**
	 * 扫描公众号的带参数二维码事件
	 * 用户已关注时的事件推送 
	 * <xml>
	 * <xml><ToUserName><![CDATA[toUser]]></ToUserName>		开发者微信号
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID）
	 * <CreateTime>123456789</CreateTime>					消息创建时间 （整型） 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[SCAN]]></Event>						事件类型，SCAN
	 * <EventKey><![CDATA[SCENE_VALUE]]></EventKey>			事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id 
	 * <Ticket><![CDATA[TICKET]]></Ticket>					二维码的ticket，可用来换取二维码图片
	 * </xml>
	 */
	protected Object recvEventScan(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 上报地理位置事件
	 * 用户同意上报地理位置后，每次进入公众号会话时，都会在进入时上报地理位置，或在进入会话后每5秒上报一次地理位置，公众号可以在公众平台网站中修改以上设置
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[fromUser]]></FromUserName>	发送方帐号（一个OpenID） 
	 * <CreateTime>123456789</CreateTime>					消息创建时间 （整型） 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[LOCATION]]></Event>					事件类型，LOCATION 
	 * <Latitude>23.137466</Latitude>						地理位置纬度 
	 * <Longitude>113.352425</Longitude>					地理位置经度 
	 * <Precision>119.385040</Precision>					地理位置精度
	 * </xml>
	 */
	protected Object recvEventLocation(Element xmlElement){
		
		return "success";
	}
	
	
	/**
	 * 推送群发结果事件
	 * 1、由于群发任务提交后，群发任务可能在一定时间后才完成，因此，群发接口调用时，仅会给出群发任务是否提交成功的提示，若群发任务提交成功，
	 * 则在群发任务结束时，会向开发者在公众平台填写的开发者URL（callback URL）推送事件。
	 * 2、由于群发任务彻底完成需要较长时间，将会在群发任务即将完成的时候，就推送群发结果，此时的推送人数数据将会与实际情形存在一定误差 。
	 * <xml>
	 * <ToUserName><![CDATA[gh_3e8adccde292]]></ToUserName>		公众号的微信号
	 * <FromUserName><![CDATA[oR5Gjjl_eiZoUpGozMo7dbBJ362A]]></FromUserName>	公众号群发助手的微信号，为mphelper 
	 * <CreateTime>1394524295</CreateTime>			创建时间的时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>			消息类型，此处为event 
	 * <Event><![CDATA[MASSSENDJOBFINISH]]></Event>	事件信息，此处为MASSSENDJOBFINISH 
	 * <MsgID>1988</MsgID>							群发的消息ID 
	 * <Status><![CDATA[send success]]></Status>		群发的结果，为“send success”或“send fail”或“err(num)”。但send success时，也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。err(num)是审核失败的具体原因
	 * <TotalCount>100</TotalCount>					group_id下粉丝数；或者openid_list中的粉丝数 
	 * <FilterCount>80</FilterCount>				过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount = SentCount + ErrorCount 
	 * <SentCount>75</SentCount>					发送成功的粉丝数 
	 * <ErrorCount>5</ErrorCount>					发送失败的粉丝数 
	 * <CopyrightCheckResult>
	 * <Count>1</Count>
	 * <ResultList>	各个单图文校验结果
	 * <item>
	 * <ArticleIdx>1</ArticleIdx>	群发文章的序号，从1开始
	 * <UserDeclareState>0</UserDeclareState>		用户声明文章的状态
	 * <AuditState>2</AuditState>	系统校验的状态
	 * <OriginalArticleUrl>< ![CDATA[Url_1] ]></OriginalArticleUrl>	相似原创文的url
	 * <OriginalArticleType>1</OriginalArticleType>	相似原创文的类型
	 * <CanReprint>1</CanReprint>	是否能转载
	 * <NeedReplaceContent>1</NeedReplaceContent>		是否需要替换成原创文内容
	 * <NeedShowReprintSource>1</NeedShowReprintSource>	是否需要注明转载来源
	 * </item>
	 * </ResultList>
	 * <CheckState>2</CheckState>	整体校验结果，1-未被判为转载，可以群发，2-被判为转载，可以群发，3-被判为转载，不能群发
	 * </CopyrightCheckResult>
	 * 
	 * </xml>
	 */
	protected Object recvEventMassSendJobFinish(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 推送模板消息发送结果事件
	 * 在模版消息发送任务完成后，微信服务器会将是否送达成功作为通知
	 *<xml>
	 * <ToUserName><![CDATA[gh_7f083739789a]]></ToUserName>		公众号微信号 
	 * <FromUserName><![CDATA[oia2TjuEGTNoeX76QEjQNrcURxG8]]></FromUserName>	 接收模板消息的用户的openid 
	 * <CreateTime>1395658920</CreateTime>				创建时间 
	 * <MsgType><![CDATA[event]]></MsgType>				消息类型是事件 
	 * <Event><![CDATA[TEMPLATESENDJOBFINISH]]></Event>	事件为模板消息发送结束 
	 * <MsgID>200163836</MsgID>							消息id 
	 * <Status><![CDATA[Status]]></Status>				发送状态：success-成功；failed:user block-用户拒收；failed: system failed-其他原因失败 
	 *</xml>
	 */
	protected Object recvEventTemplateSendJobFinish(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String status = xmlElement.selectSingleNode("Status").getText();
		
		return "success";
	}
	
	/**
	 * 资质认证成功（此时立即获得接口权限） 
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[qualification_verify_success]]></Event>	事件类型 qualification_verify_success 
	 * <ExpiredTime>1442401156</ExpiredTime>				有效期 (整形)，指的是时间戳，将于该时间戳认证过期 
	 * </xml> 
	 */
	protected Object recvQualificationVerifySuccess(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 资质认证失败
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[qualification_verify_fail]]></Event>	事件类型 qualification_verify_fail 
	 * <FailTime>1442401122</FailTime>						失败发生时间 (整形)，时间戳 
	 * <FailReason><![CDATA[by time]]></FailReason>			认证失败的原因 
	 * </xml> 
	 */
	protected Object recvQualificationVerifyFail(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 名称认证成功（即命名成功） 
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[naming_verify_success]]></Event>		事件类型 naming_verify_success 
	 * <ExpiredTime>1442401093</ExpiredTime>				有效期 (整形)，指的是时间戳，将于该时间戳认证过期 
	 * </xml> 
	 */
	protected Object recvNamingVerifySuccess(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 名称认证失败（这时虽然客户端不打勾，但仍有接口权限） 
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[naming_verify_fail]]></Event>		事件类型 naming_verify_fail 
	 * <FailTime>1442401061</FailTime>						失败发生时间 (整形)，时间戳 
	 * <FailReason><![CDATA[by time]]></FailReason>			认证失败的原因 
	 * </xml> 
	 */
	protected Object recvNamingVerifyFail(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 年审通知 
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[annual_renew]]></Event>				事件类型 annual_renew，提醒公众号需要去年审了 
	 * <ExpiredTime>1442401004</ExpiredTime>				有效期 (整形)，指的是时间戳，将于该时间戳认证过期，需尽快年审 
	 * </xml>
	 */
	protected Object recvAnnualRenew(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 认证过期失效通知 
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[verify_expired]]></Event>			事件类型 verify_expired 
	 * <ExpiredTime>1442400900</ExpiredTime>				有效期 (整形)，指的是时间戳，表示已于该时间戳认证过期，需要重新发起微信认证 
	 * </xml>
	 */
	protected Object recvVerifyExpired(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 订单付款通知
	 * 在用户在微信中付款成功后，微信服务器会将订单付款通知推送给开发者
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[merchant_order]]></Event>			事件类型 merchant_order 
	 * <OrderId><![CDATA[test_order_id]]></OrderId>			订单ID
	 * <OrderStatus>2</OrderStatus>							订单状态
	 * <ProductId><![CDATA[test_product_id]]></ProductId>	商品ID
	 * <SkuInfo><![CDATA[10001:1000012;10002:100021]]></SkuInfo>	商品SKU
	 * </xml>
	 */
	protected Object recvMerchantOrder(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 买单推送事件
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[user_pay_from_pay_cell]]></Event>	事件类型 user_pay_from_pay_cell
	 * <CardId><![CDATA[po2VNuCuRo-8sxxxxxxxxxxx]]></CardId>	卡券ID
	 * <UserCardCode><![CDATA[38050000000]]></UserCardCode>		卡券Code码
	 * <TransId><![CDATA[10022403432015000000000]]></TransId>	微信支付交易订单号（只有使用买单功能核销的卡券才会出现）
	 * <LocationId>291710000</LocationId>						门店名称，当前卡券核销的门店名称（只有通过卡券商户助手和买单核销时才会出现） 
	 * <Fee><![CDATA[10000]]></Fee>								实付金额，单位为分
	 * <OriginalFee><![CDATA[10000]]> </OriginalFee>			应付金额，单位为分
	 * </xml>
	 */
	protected Object recvUserPay(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 用户领取卡券事件
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[user_get_card]]></Event>				事件类型 user_get_card
	 *<CardId><![CDATA[cardid]]></CardId> 					卡券ID
	 *<IsGiveByFriend>1</IsGiveByFriend>					是否为转赠，1代表是，0代表否。 
	 *<UserCardCode><![CDATA[12312312]]></UserCardCode>		code序列号。自定义code及非自定义code的卡券被领取后都支持事件推送。 
	 *<OuterId>1</OuterId>									场景值
	 *</xml>
	 */
	protected Object recvUserGetCard(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 卡券审核事件推送
	 * <xml> 
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event  
	 * <Event><![CDATA[card_pass_check]]></Event>  			事件类型，card_pass_check(卡券通过审核)、card_not_pass_check（卡券未通过审核） 
	 * <CardId><![CDATA[cardid]]></CardId> 					卡券ID 
	 * </xml>
	 */
	protected Object recvCardCheck(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 用户删除卡券事件
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[user_del_card]]></Event> 			事件类型，user_del_card(用户删除卡券) 
	 * <CardId><![CDATA[cardid]]></CardId> 					卡券ID
	 * <UserCardCode><![CDATA[12312312]]></UserCardCode>	code序列号。自定义code及非自定义code的卡券被领取后都支持事件推送
	 * </xml>
	 */
	protected Object recvUserDelCard(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 核销事件
	 * <xml> 
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName>	发送方帐号（一个OpenID，此时发送方是系统帐号） 
	 * <CreateTime>1442401156</CreateTime>					消息创建时间 （整型），时间戳 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[user_consume_card]]></Event> 		事件类型，user_consume_card(核销事件) 
	 * <CardId><![CDATA[cardid]]></CardId> 					卡券ID
	 * <UserCardCode><![CDATA[12312312]]></UserCardCode>	卡券Code码
	 * <ConsumeSource><![CDATA[(FROM_API)]]></ConsumeSource>	核销来源。支持开发者统计API核销（FROM_API）、公众平台核销（FROM_MP）、卡券商户助手核销（FROM_MOBILE_HELPER）（核销员微信号） 
	 * <OutTradeNo><![CDATA[aaaaaaaaaaaa]]></OutTradeNo>		
	 * <TransId><![CDATA[bbbbbbbbbb]]></TransId>				
	 * <LocationId><![CDATA[222222]]></LocationId>				门店名称，当前卡券核销的门店名称（只有通过卡券商户助手和买单核销时才会出现）
	 * <StaffOpenId><![CDATA[obLatjjwDolFjRRd3doGIdwNqRXw]></StaffOpenId>	核销该卡券核销员的openid（只有通过卡券商户助手核销时才会出现）
	 * </xml>
	 */
	protected Object recvUserConsumeCard(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 进入会员卡事件
	 * <xml> 
	 * <ToUserName><![CDATA[toUser]]></ToUserName> 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName> 
	 * <CreateTime>123456789</CreateTime> 
	 * <MsgType><![CDATA[event]]></MsgType> 
	 * <Event><![CDATA[user_view_card]]></Event> 	事件类型，user_view_card(核销事件) 
	 * <CardId><![CDATA[cardid]]></CardId> 			卡券ID
	 * <UserCardCode><![CDATA[12312312]]></UserCardCode>	商户自定义code值。非自定code推送为空串
	 * </xml>
	 */
	protected Object recvUserViewCard(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 从卡券进入公众号会话事件
	 * 用户在卡券里点击查看公众号进入会话时（需要用户已经关注公众号），微信会把这个事件推送
	 * <xml> 
	 * <ToUserName><![CDATA[toUser]]></ToUserName> 
	 * <FromUserName><![CDATA[FromUser]]></FromUserName> 
	 * <CreateTime>123456789</CreateTime> 
	 * <MsgType><![CDATA[event]]></MsgType> 
	 * <Event><![CDATA[user_enter_session_from_card]]></Event> 事件类型，user_enter_session_from_card(用户从卡券进入公众号会话)
	 * <CardId><![CDATA[cardid]]></CardId> 					卡券ID
	 * <UserCardCode><![CDATA[12312312]]></UserCardCode>	Code码
	 * </xml>
	 */
	protected Object recvUserEnterSessionFromCard(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 会员卡内容更新事件
	 * 当用户的会员卡积分余额发生变动时，微信会推送事件告知开发者
	 * <xml><ToUserName><![CDATA[gh_9e1765b5568e]]></ToUserName>
	 * <FromUserName><![CDATA[ojZ8YtyVyr30HheH3CM73y7h4jJE]]></FromUserName>
	 * <CreateTime>1445507140</CreateTime>
	 * <MsgType><![CDATA[event]]></MsgType>
	 * <Event><![CDATA[update_member_card]]></Event>	事件类型，update_member_card(用户从卡券进入公众号会话)
	 * <CardId><![CDATA[pjZ8Ytx-nwvpCRyQneH3Ncmh6N94]]></CardId>	卡券ID
	 * <UserCardCode><![CDATA[485027611252]]></UserCardCode>	Code码
	 * <ModifyBonus>3</ModifyBonus>			变动的积分值
	 * <ModifyBalance>0</ModifyBalance>		变动的余额值
	 * </xml>
	 */
	protected Object recvUpdateMemberCard(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 库存报警事件
	 * 用户领券时，若此时库存数小于预警值（默认为100），会发送事件给商户，事件每隔12小时发送一次
	 * <xml>
	 * <ToUserName><![CDATA[gh_2d62d*****0]]></ToUserName>
	 * <FromUserName><![CDATA[oa3LFuBvWb7*********]]></FromUserName> 
	 * <CreateTime>1443838506</CreateTime>
	 * <MsgType><![CDATA[event]]></MsgType>
	 * <Event><![CDATA[card_sku_remind]]></Event>		事件类型，card_sku_remind库存报警 
	 * <CardId><![CDATA[pa3LFuAh2P65**********]]></CardId>	卡券ID 
	 * <Detail><![CDATA[the card's quantity is equal to 0]]></Detail>	报警详细信息 
	 * </xml>
	 */
	protected Object recvCardSkuRemind(Element xmlElement){
		
		return "success";
	}
	
	/**
	 * 券点流水详情事件
	 * 当商户朋友的券券点发生变动时，微信服务器会推送消息给商户服务器
	 * <xml>
	 *  <ToUserName><![CDATA[gh_7223c83d4be5]]></ToUserName>
	 *  <FromUserName><![CDATA[ob5E7s-HoN9tslQY3-0I4qmgluHk]]></FromUserName>
	 *  <CreateTime>1453295737</CreateTime>
	 *  <MsgType><![CDATA[event]]></MsgType>
	 *  <Event><![CDATA[card_pay_order]]></Event>		事件类型，card_pay_order券点流水详情事件 
	 *  <OrderId><![CDATA[404091456]]></OrderId>		本次推送对应的订单号 
	 *  <Status><![CDATA[ORDER_STATUS_FINANCE_SUCC]]></Status>	本次订单号的状态,ORDER_STATUS_WAITING 等待支付 ORDER_STATUS_SUCC 支付成功 ORDER_STATUS_FINANCE_SUCC 加代币成功 ORDER_STATUS_QUANTITY_SUCC 加库存成功 ORDER_STATUS_HAS_REFUND 已退币 ORDER_STATUS_REFUND_WAITING 等待退币确认 ORDER_STATUS_ROLLBACK 已回退,系统失败 ORDER_STATUS_HAS_RECEIPT 已开发票
	 *  <CreateTime>1453295737</CreateTime>		购买券点时，支付二维码的生成时间 
	 *  <PayFinishTime>0</PayFinishTime>		购买券点时，实际支付成功的时间 
	 *  <Desc><![CDATA[]]></Desc>				支付方式，一般为微信支付充值 
	 *  <FreeCoinCount><![CDATA[200]]></FreeCoinCount>	剩余免费券点数量 
	 *  <PayCoinCount><![CDATA[0]]></PayCoinCount>		剩余付费券点数量
	 *  <RefundFreeCoinCount><![CDATA[0]]></RefundFreeCoinCount>	本次变动的免费券点数量 
	 *  <RefundPayCoinCount><![CDATA[0]]></RefundPayCoinCount>		本次变动的付费券点数量 
	 *  <OrderType><![CDATA[ORDER_TYPE_SYS_ADD]]></OrderType>	所要拉取的订单类型 ORDER_TYPE_SYS_ADD 平台赠送券点 ORDER_TYPE_WXPAY 充值券点 ORDER_TYPE_REFUND 库存未使用回退券点 ORDER_TYPE_REDUCE 券点兑换库存 ORDER_TYPE_SYS_REDUCE 平台扣减 
	 *  <Memo><![CDATA[开通账户奖励]]></Memo>	系统备注，说明此次变动的缘由，如开通账户奖励、门店奖励、核销奖励以及充值、扣减。 
	 *  <ReceiptInfo><![CDATA[]]></ReceiptInfo>	所开发票的详情 
	 * </xml>	
	 */
	protected Object recvCardPayOrder(Element xmlElement){
		
		return "success";
	}
	
}

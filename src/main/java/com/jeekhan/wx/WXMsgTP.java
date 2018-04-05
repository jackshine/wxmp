package com.jeekhan.wx;
/**
 * 微信消息类型
 * @author Jee Khan
 *
 */
public enum WXMsgTP {
	//用户发送消息
	text("文本消息"),
	image("图片消息"),
	voice("语音消息"),
	video("视频消息"),
	shortvideo("小视频消息"),
	location("地理位置消息"),
	link("链接消息"),
	event("事件消息"),
	//以下为具体的事件消息
	//关注相关事件
	subscribe("关注事件消息"),
	unsubscribe("取消关注事件消息"),
	SCAN("扫描公众号的带参数二维码事件"),
	LOCATION("上报地理位置事件"),
	//自定义菜单事件
	CLICK("自定义菜单-点击事件"),
	VIEW("点击菜单跳转链接时的事件"),
	scancode_push("扫码推事件的事件推送"),
	scancode_waitmsg("扫码推事件且弹出“消息接收中”提示框的事件"),
	pic_sysphoto("弹出系统拍照发图的事件"),
	pic_photo_or_album("弹出拍照或者相册发图的事件"),
	pic_weixin("弹出微信相册发图器的事件"),
	location_select("弹出地理位置选择器的事件"),
	//其他事件
	MASSSENDJOBFINISH("推送群发结果事件"),
	TEMPLATESENDJOBFINISH("推送模板消息发送结果事件"),
	qualification_verify_success("资质认证成功"),
	qualification_verify_fail("资质认证失败"),
	naming_verify_success("名称认证成功"),
	naming_verify_fail("名称认证失败"),
	annual_renew("年审通知 "),
	verify_expired("认证过期失效通知 "),
	merchant_order("订单付款通知"),
	user_pay_from_pay_cell("买单推送事件"),
	user_get_card("用户领取卡券事件"),
	card_pass_check("卡券审核事件推送"),
	user_del_card("用户删除卡券事件"),
	user_consume_card("核销事件"),
	user_view_card("进入会员卡事件"),
	user_enter_session_from_card("从卡券进入公众号会话事件"),
	update_member_card("会员卡内容更新事件"),
	card_sku_remind("库存报警事件"),
	card_pay_order("券点流水详情事件");
	
	private String desc;
	
	private WXMsgTP(String desc){
		this.desc = desc;
	}
	
	public String getDesc(){
		return this.desc;
	}

}

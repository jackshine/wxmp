package com.jeekhan.wx.msg;

import java.util.List;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 接收微信自定义菜单事件消息，并作相应的处理
 * 在微信用户和公众号产生交互的过程中，用户的某些操作会使得微信服务器通过事件推送的形式通知到开发者；
 * 开发者可完成对该类的继承重写，以实现自己的处理逻辑；
 * @author Administrator
 *
 */
@Component
public class RecvMenuEventMsgHandle {
	
	@Autowired
	private RespMsgHandle respMsgHandle;
	
	
	/**
	 * 自定义菜单-点击事件
	 * 用户点击自定义菜单后，微信会把点击事件推送给开发者，点击菜单弹出子菜单，不会产生上报
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[fromUser]]></FromUserName>		发送方帐号（一个OpenID） 
	 * <CreateTime>123456789</CreateTime>					消息创建时间 （整型） 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[CLICK]]></Event>						事件类型，CLICK 
	 * <EventKey><![CDATA[EVENTKEY]]></EventKey>			事件KEY值，与自定义菜单接口中KEY值对应
	 * </xml>
	 */
	protected Object recvMenuClick(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String key = xmlElement.selectSingleNode("EventKey").getText();
		
		String content = "点击事件，key:" + key;
		return this.respMsgHandle.respTextMsg(fromUser, content);
	}
	
	/**
	 * 点击菜单跳转链接时的事件推送 
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[fromUser]]></FromUserName>		发送方帐号（一个OpenID） 
	 * <CreateTime>123456789</CreateTime>					消息创建时间 （整型） 
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[VIEW]]></Event>						事件类型，VIEW 
	 * <EventKey><![CDATA[www.qq.com]]></EventKey>			事件KEY值，设置的跳转URL 
	 * <MenuId>MENUID</MenuId>	指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了
	 * </xml>
	 * 	 
	 */
	protected Object recvMenuView(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String url = xmlElement.selectSingleNode("EventKey").getText();
		//int menuId = (int) msgMap.get("MENUID");
		String content = "跳转链接事件，key:" + url;
		return this.respMsgHandle.respTextMsg(fromUser, content);
	}
	
	/**
	 * scancode_push：扫码推事件的事件推送 
	 * <xml>
	 * <ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>发送方帐号（一个OpenID）
	 * <CreateTime>1408090502</CreateTime>					消息创建时间（整型）
	 * <MsgType><![CDATA[event]]></MsgType>					消息类型，event 
	 * <Event><![CDATA[scancode_push]]></Event>				事件类型，scancode_push 
	 * <EventKey><![CDATA[6]]></EventKey>					事件KEY值，由开发者在创建菜单时设定 
	 * <ScanCodeInfo>										扫描信息
	 * <ScanType><![CDATA[qrcode]]></ScanType>				扫描类型，一般是qrcode
	 * <ScanResult><![CDATA[1]]></ScanResult>				扫描结果，即二维码对应的字符串信息 
	 * </ScanCodeInfo>						
	 * </xml>
	 */
	protected Object recvMenuScancodePush(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String key = xmlElement.selectSingleNode("EventKey").getText();
		String scanType =  xmlElement.selectSingleNode("ScanCodeInfo/ScanType").getText();
		String scanResult =  xmlElement.selectSingleNode("ScanCodeInfo/ScanResult").getText();
		String content = "扫码推事件，key:" + key + ",scanType:" + scanType + ",scanResult:" + scanResult;
		return this.respMsgHandle.respTextMsg(fromUser, content);
	}
	
	/**
	 * scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框的事件推送
	 * 同上
	 */
	protected Object recvMenuScancodeWaitmsg(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String key = xmlElement.selectSingleNode("EventKey").getText();
		String scanType =  xmlElement.selectSingleNode("ScanCodeInfo/ScanType").getText();
		String scanResult =  xmlElement.selectSingleNode("ScanCodeInfo/ScanResult").getText();
		String content = "扫码推事件且弹出“消息接收中”事件，key:" + key + ",scanType:" + scanType + ",scanResult:" + scanResult;
		return this.respMsgHandle.respTextMsg(fromUser, content);
	}
	
	/**
	 * pic_sysphoto：弹出系统拍照发图的事件推送 
	 * 微信先发送该消息，随后发送普通的图片消息image
	 * 随后可能会收到开发者下发的消息
	 * <ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>发送方帐号（一个OpenID）
	 * <CreateTime>1408090606</CreateTime>                          消息创建时间（整型）         
	 * <MsgType><![CDATA[event]]></MsgType>                         消息类型，event  
	 * <Event><![CDATA[pic_sysphoto]]></Event>						事件类型，pic_sysphoto 
	 * <EventKey><![CDATA[6]]></EventKey>							事件KEY值，由开发者在创建菜单时设定 
	 * <SendPicsInfo>												发送的图片信息 
	 * <Count>1</Count>												发送的图片数量
	 * <PicList>													图片列表
	 * <item>
	 * <PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>图片的MD5值，开发者若需要，可用于验证接收到图片 
	 * </item>
	 * </PicList>
	 * </SendPicsInfo>
	 * </xml>
	 */
	protected Object recvMenuPicSysphoto(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String key = xmlElement.selectSingleNode("EventKey").getText();
		String count =  xmlElement.selectSingleNode("SendPicsInfo/Count").getText();
		List picList =  xmlElement.selectNodes("SendPicsInfo/PicList");
		String content = "弹出系统拍照发图事件，key:" + key + ",count:" + count ;
		return this.respMsgHandle.respTextMsg(fromUser, content);
	}
	
	/**
	 * pic_photo_or_album：弹出拍照或者相册发图的事件推送 
	 * 微信先发送该消息，随后发送普通的图片消息image
	 * 随后可能会收到开发者下发的消息
	 * 同上
	 */
	protected Object recvMenuPicPhotoOralbum(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String key = xmlElement.selectSingleNode("EventKey").getText();
		String count =  xmlElement.selectSingleNode("SendPicsInfo/Count").getText();
		List picList =  xmlElement.selectNodes("SendPicsInfo/PicList");
		String content = "弹出拍照或者相册发图的事件，key:" + key + ",count:" + count ;
		return this.respMsgHandle.respTextMsg(fromUser, content);
	}
	
	/**
	 * pic_weixin：弹出微信相册发图器的事件推送 
	 * 微信先发送该消息，随后发送普通的图片消息image
	 * 随后可能会收到开发者下发的消息
	 * <ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>发送方帐号（一个OpenID）
	 * <CreateTime>1408090606</CreateTime>                          消息创建时间（整型）         
	 * <MsgType><![CDATA[event]]></MsgType>                         消息类型，event  
	 * <Event><![CDATA[pic_weixin]]></Event>						事件类型，pic_weixin 
	 * <EventKey><![CDATA[6]]></EventKey>							事件KEY值，由开发者在创建菜单时设定 
	 * <SendPicsInfo>												发送的图片信息 
	 * <Count>1</Count>												发送的图片数量
	 * <PicList>													图片列表
	 * <item>
	 * <PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>图片的MD5值，开发者若需要，可用于验证接收到图片 
	 * </item>
	 * </PicList>
	 * </SendPicsInfo>
	 * </xml>
	 */
	protected Object recvMenuPicWeixin(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String key = xmlElement.selectSingleNode("EventKey").getText();
		String count =  xmlElement.selectSingleNode("SendPicsInfo/Count").getText();
		List picList =  xmlElement.selectNodes("SendPicsInfo/PicList");
		String content = "弹出微信相册发图器事件，key:" + key + ",count:" + count ;
		return this.respMsgHandle.respTextMsg(fromUser, content);
	}
	
	/**
	 * location_select：弹出地理位置选择器的事件推送 
	 * 随后可能会收到开发者下发的消息
	 * <ToUserName><![CDATA[gh_e136c6e50636]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[oMgHVjngRipVsoxg6TuX3vz6glDg]]></FromUserName>发送方帐号（一个OpenID）
	 * <CreateTime>1408090606</CreateTime>                          消息创建时间（整型）         
	 * <MsgType><![CDATA[event]]></MsgType>                         消息类型，event  
	 * <Event><![CDATA[location_select]]></Event>					事件类型，location_select 
	 * <EventKey><![CDATA[6]]></EventKey>							事件KEY值，由开发者在创建菜单时设定 
	 * <SendLocationInfo>											发送的位置信息
	 * <Location_X><![CDATA[23]]></Location_X>						X坐标信息
	 * <Location_Y><![CDATA[113]]></Location_Y>						Y坐标信息 
	 * <Scale><![CDATA[15]]></Scale>								精度，可理解为精度或者比例尺、越精细的话 scale越高 
	 * <Label><![CDATA[ 广州市海珠区客村艺苑路 106号]]></Label>		地理位置的字符串信息 
	 * <Poiname><![CDATA[]]></Poiname>								朋友圈POI的名字，可能为空 
	 * </SendLocationInfo>
	 * </xml>
	 */
	protected Object recvMenuLocationSelect(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String key = xmlElement.selectSingleNode("EventKey").getText();
		String locationX =  xmlElement.selectSingleNode("SendLocationInfo/Location_X").getText();
		String locationY =  xmlElement.selectSingleNode("SendLocationInfo/Location_Y").getText();
		String scale = xmlElement.selectSingleNode("SendLocationInfo/Scale").getText();
		String label = xmlElement.selectSingleNode("SendLocationInfo/Label").getText();
		//String poiname = xmlElement.selectSingleNode("SendLocationInfo/Poiname").getText();
		
		String content = "弹出地理位置选择器的事件，key:" + key 
				+ ",locationX:" + locationX + ",locationY:" + locationY + ",scale:" + scale 
				+ ",label:" + label ;
		return this.respMsgHandle.respTextMsg(fromUser, content);
	}

	
}

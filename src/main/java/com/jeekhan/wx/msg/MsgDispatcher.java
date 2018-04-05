package com.jeekhan.wx.msg;

import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.WXMsgTP;
/**
 * 消息/事件分发处理控制类
 * @author Jee Khan
 *
 */
@Component
public class MsgDispatcher {
	@Autowired
	private RecvMsgHandle recvMsgHandle;	//普通消息处理逻辑
	@Autowired
	private RecvEventMsgHandle recvEventMsgHandle;//事件消息处理逻辑
	@Autowired
	private RecvMenuEventMsgHandle recvMenuEventMsgHandle;	//自定义菜单事件消息处理逻辑

	//根据接收到的消息的类型做出相应的处理
	public Object handle(Element xmlElement){
		String msgType = xmlElement.selectSingleNode("MsgType").getText();
		String event = null;
		Node eventNode = xmlElement.selectSingleNode("Event");
		if(eventNode != null)	{
			event = eventNode.getText();//具体的事件消息
		}
		WXMsgTP wxMsgTp = WXMsgTP.valueOf(msgType);
		
		switch(wxMsgTp){
		case text:
			//文本消息
			return recvMsgHandle.recvdText(xmlElement);
		case image:
			//图片消息
			return recvMsgHandle.recvdImage(xmlElement);
		case voice:
			//语音消息
			return recvMsgHandle.recvdVoice(xmlElement);
		case video:
			//视频消息
			return recvMsgHandle.recvdVideo(xmlElement);
		case shortvideo:
			//小视频消息
			return recvMsgHandle.recvdShortVideo(xmlElement);
		case location:
			//位置消息
			return recvMsgHandle.recvdLocation(xmlElement);
		case link:
			//链接消息
			return recvMsgHandle.recvdLink(xmlElement);
		case event:
			WXMsgTP eventTp = WXMsgTP.valueOf(event);
			switch(eventTp) {
				//==============关注与用户相关事件==============
				case subscribe:
					//用户关注
					return recvEventMsgHandle.recvEventSubscribe(xmlElement);
				case unsubscribe:
					//用户取消关注
					return recvEventMsgHandle.recvEventUnsubscribe(xmlElement);
				case SCAN:
					//用户二次关注
					return recvEventMsgHandle.recvEventScan(xmlElement);
				case LOCATION:
					//用户上报地理位置
					return recvEventMsgHandle.recvEventLocation(xmlElement);
				//==============菜单事件==============
				case CLICK:
					return recvMenuEventMsgHandle.recvMenuClick(xmlElement);
				case VIEW:
					return recvMenuEventMsgHandle.recvMenuView(xmlElement);
				case scancode_push:
					return recvMenuEventMsgHandle.recvMenuScancodePush(xmlElement);
				case scancode_waitmsg:
					return recvMenuEventMsgHandle.recvMenuScancodeWaitmsg(xmlElement);
				case pic_sysphoto:
					return recvMenuEventMsgHandle.recvMenuPicSysphoto(xmlElement);
				case pic_photo_or_album:
					return recvMenuEventMsgHandle.recvMenuPicPhotoOralbum(xmlElement);
				case pic_weixin:
					return recvMenuEventMsgHandle.recvMenuPicWeixin(xmlElement);
				case location_select:
					return recvMenuEventMsgHandle.recvMenuLocationSelect(xmlElement);
				//==============
				default:
					return "";	
			}
		default:
			return "";
		}
	}	 
}

package com.jeekhan.wx.msg;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 接收普通微信消息，并作相应的处理
 * 当普通微信用户向公众账号发消息时，微信服务器将POST消息的XML数据包
 * @author jeekhan
 *
 */

@Component
public class RecvMsgHandle {
	@Autowired
	private RespMsgHandle respMsgHandle;

	/**
	 * 接收到文本消息
	 *  <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[fromUser]]></FromUserName> 	发送方帐号（一个OpenID）
	 * <CreateTime>1348831860</CreateTime>					消息创建时间 （整型）
	 * <MsgType><![CDATA[text]]></MsgType>					text
	 * <Content><![CDATA[this is a test]]></Content>		文本消息内容 
	 * <MsgId>1234567890123456</MsgId>						消息id，64位整型 
	 * </xml>
	 * @param msgMap
	 * @return
	 */
	protected Object recvdText(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String content = xmlElement.selectSingleNode("Content").getText();
		return respMsgHandle.respTextMsg(fromUser,"收到文本消息:" + content);
	}
	
	/**
	 * 接收到图片消息
	 *  <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[fromUser]]></FromUserName> 	发送方帐号（一个OpenID）
	 * <CreateTime>1348831860</CreateTime>					消息创建时间 （整型）
	 * <MsgType><![CDATA[image]]></MsgType>					image
	 * <PicUrl><![CDATA[this is a url]]></PicUrl>				图片链接 
	 * <MediaId><![CDATA[media_id]]></MediaId>				图片消息媒体id，可以调用多媒体文件下载接口拉取数据。 
	 * <MsgId>1234567890123456</MsgId>						消息id，64位整型 
	 *  </xml>
	 */
	protected Object recvdImage(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String mediaId = xmlElement.selectSingleNode("MediaId").getText();
		//String picUrl = xmlElement.selectSingleNode("PicUrl").getText();
		return respMsgHandle.respImageMsg(fromUser,mediaId);
	}
	
	/**
	 * 语音消息
	 * 开通语音识别后，用户每次发送语音给公众号时，微信会在推送的语音消息XML数据包中，增加一个Recongnition字段
	 * （注：由于客户端缓存，开发者开启或者关闭语音识别功能，对新关注者立刻生效，对已关注用户需要24小时生效。开发者可以重新关注此帐号进行测试）。
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[fromUser]]></FromUserName> 	发送方帐号（一个OpenID）
	 * <CreateTime>1348831860</CreateTime>					消息创建时间 （整型）
	 * <MsgType><![CDATA[voice]]></MsgType>					语音为voice 
	 * <MediaId><![CDATA[media_id]]></MediaId>				语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 * <Format><![CDATA[Format]]></Format>					语音格式，如amr，speex等 
	 * <Recognition><![CDATA[腾讯微信团队]]></Recognition>
	 * <MsgId>1234567890123456</MsgId>						消息id，64位整型 
	 * </xml>
	 */
	protected Object recvdVoice(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String mediaId = xmlElement.selectSingleNode("MediaId").getText();
		//String format = xmlElement.selectSingleNode("Format").getText();
		//String fecognition = xmlElement.selectSingleNode("Recognition").getText();
		return respMsgHandle.respVoiceMsg(fromUser,mediaId);
	}
	
	
	/**
	 * 视频消息
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[fromUser]]></FromUserName> 	发送方帐号（一个OpenID）
	 * <CreateTime>1348831860</CreateTime>					消息创建时间 （整型）
	 * <MsgType><![CDATA[video]]></MsgType>					视频为video 
	 * <MediaId><![CDATA[media_id]]></MediaId>				视频消息媒体id，可以调用多媒体文件下载接口拉取数据。 
	 * <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。 
	 * <MsgId>1234567890123456</MsgId>						消息id，64位整型 
	 * </xml>
	 * 	 */
	protected Object recvdVideo(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String mediaId = xmlElement.selectSingleNode("MediaId").getText();
		//String thumbMediaId = xmlElement.selectSingleNode("ThumbMediaId").getText();
		return respMsgHandle.respVideoMsg(fromUser, mediaId, "回复视频", "henhao");
	}
	
	/**
	 * 小视频消息
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[fromUser]]></FromUserName> 	发送方帐号（一个OpenID）
	 * <CreateTime>1348831860</CreateTime>					消息创建时间 （整型）
	 * <MsgType><![CDATA[shortvideo]]></MsgType>				小视频为shortvideo 
	 * <MediaId><![CDATA[media_id]]></MediaId>				视频消息媒体id，可以调用多媒体文件下载接口拉取数据。 
	 * <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。 
	 * <MsgId>1234567890123456</MsgId>						消息id，64位整型 
	 * </xml>
	 */
	protected Object recvdShortVideo(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String mediaId = xmlElement.selectSingleNode("MediaId").getText();
		//String thumbMediaId = xmlElement.selectSingleNode("ThumbMediaId").getText();
		return respMsgHandle.respVideoMsg(fromUser, mediaId, "回复小视频", "henhao");
	}
	
	/**
	 * 地理位置消息
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[fromUser]]></FromUserName> 	发送方帐号（一个OpenID）
	 * <CreateTime>1348831860</CreateTime>					消息创建时间 （整型）
	 * <MsgType><![CDATA[location]]></MsgType>				location 
	 * <Location_X>23.134521</Location_X>					地理位置维度
	 * <Location_Y>113.358803</Location_Y>					地理位置经度 
	 * <Scale>20</Scale>									地图缩放大小
	 * <Label><![CDATA[位置信息]]></Label>					地理位置信息
	 * <MsgId>1234567890123456</MsgId>						消息id，64位整型 
	 * </xml>
	 */
	protected Object recvdLocation(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String locationX = xmlElement.selectSingleNode("Location_X").getText();
		String locationY = xmlElement.selectSingleNode("Location_Y").getText();
		String scale = xmlElement.selectSingleNode("Scale").getText();
		String label = xmlElement.selectSingleNode("Label").getText();
		return respMsgHandle.respTextMsg(fromUser,"收到地理位置消息;" + "x:"+locationX + ",y:" +locationY 
				+ ",scale:" + scale + ",label:" + label
				);
	}
	
	/**
	 * 链接消息
	 * <xml>
	 * <ToUserName><![CDATA[toUser]]></ToUserName>			开发者微信号 
	 * <FromUserName><![CDATA[fromUser]]></FromUserName> 	发送方帐号（一个OpenID）
	 * <CreateTime>1348831860</CreateTime>					消息创建时间 （整型）
	 * <MsgType><![CDATA[link]]></MsgType>					消息类型，link 
	 * <Title><![CDATA[公众平台官网链接]]></Title>			消息标题 
	 * <Description><![CDATA[公众平台官网链接]]></Description>消息描述
	 * <Url><![CDATA[url]]></Url>							消息链接 
	 * <MsgId>1234567890123456</MsgId>						消息id，64位整型 
	 * </xml>
	 */
	protected Object recvdLink(Element xmlElement){
		String fromUser = xmlElement.selectSingleNode("FromUserName").getText();
		String url = xmlElement.selectSingleNode("Url").getText();
		String title = xmlElement.selectSingleNode("Title").getText();
		String description = xmlElement.selectSingleNode("Description").getText();
		return respMsgHandle.respTextMsg(fromUser,"收到链接消息;" + "url:" + url + ",title" 
				+ title + ",description:" + description);
	}

}

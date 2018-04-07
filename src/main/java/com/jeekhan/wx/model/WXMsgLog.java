package com.jeekhan.wx.model;

import java.math.BigInteger;
import java.util.Date;

public class WXMsgLog {
    private BigInteger id;

	private String fromUser;

	private String inout;

	private String msgType;

	private String eventType;

	private String isMass;

	private String isTpl;

	private Date createTime;

	private String status;

	private Integer respId;

	private String content;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser == null ? null : fromUser.trim();
	}

	public String getInout() {
		return inout;
	}

	public void setInout(String inout) {
		this.inout = inout == null ? null : inout.trim();
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType == null ? null : msgType.trim();
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType == null ? null : eventType.trim();
	}

	public String getIsMass() {
		return isMass;
	}

	public void setIsMass(String isMass) {
		this.isMass = isMass == null ? null : isMass.trim();
	}

	public String getIsTpl() {
		return isTpl;
	}

	public void setIsTpl(String isTpl) {
		this.isTpl = isTpl == null ? null : isTpl.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Integer getRespId() {
		return respId;
	}

	public void setRespId(Integer respId) {
		this.respId = respId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}
    
}
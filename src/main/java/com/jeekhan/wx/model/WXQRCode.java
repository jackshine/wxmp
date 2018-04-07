package com.jeekhan.wx.model;

import java.util.Date;

public class WXQRCode {
    private Integer id;

	private String isPerm;

	private Integer sceneId;

	private String ticket;

	private String url;

	private Integer expireSeconds;

	private String localImgUrl;

	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsPerm() {
		return isPerm;
	}

	public void setIsPerm(String isPerm) {
		this.isPerm = isPerm == null ? null : isPerm.trim();
	}

	public Integer getSceneId() {
		return sceneId;
	}

	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket == null ? null : ticket.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public Integer getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(Integer expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public String getLocalImgUrl() {
		return localImgUrl;
	}

	public void setLocalImgUrl(String localImgUrl) {
		this.localImgUrl = localImgUrl == null ? null : localImgUrl.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
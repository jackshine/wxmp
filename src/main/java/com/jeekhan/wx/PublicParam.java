package com.jeekhan.wx;

/**
 * 接入方公众号的相关参数
 * @author jeekhan
 *
 */

public class PublicParam {
	
	// 公众号
	private String USERID;
	
	//公众号的appId
	private String APPID;
	
	//公众号的appSecret
	private String APPSECRET;
	
	//token
	private String TOKEN;
	
	//自定义默认菜单
	private String DEFAULTMENU;
	
	//小程序APPID
	private String miniprogramAPPID;
	
	//小程序的具体页面路径
	private String miniprogramPagepath;
	
	//用户注册回调URL、注销URL
	private String userCreateUrl;
	private String userCancelUrl;
	
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getAPPID() {
		return APPID;
	}
	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}
	public String getAPPSECRET() {
		return APPSECRET;
	}
	public void setAPPSECRET(String aPPSECRET) {
		APPSECRET = aPPSECRET;
	}
	public String getTOKEN() {
		return TOKEN;
	}
	public void setTOKEN(String tOKEN) {
		TOKEN = tOKEN;
	}
	public String getDEFAULTMENU() {
		return DEFAULTMENU;
	}
	public void setDEFAULTMENU(String dEFAULTMENU) {
		DEFAULTMENU = dEFAULTMENU;
	}
	public String getMiniprogramAPPID() {
		return miniprogramAPPID;
	}
	public void setMiniprogramAPPID(String miniprogramAPPID) {
		this.miniprogramAPPID = miniprogramAPPID;
	}
	public String getMiniprogramPagepath() {
		return miniprogramPagepath;
	}
	public void setMiniprogramPagepath(String miniprogramPagepath) {
		this.miniprogramPagepath = miniprogramPagepath;
	}
	public String getUserCreateUrl() {
		return userCreateUrl;
	}
	public void setUserCreateUrl(String userCreateUrl) {
		this.userCreateUrl = userCreateUrl;
	}
	public String getUserCancelUrl() {
		return userCancelUrl;
	}
	public void setUserCancelUrl(String userCancelUrl) {
		this.userCancelUrl = userCancelUrl;
	}
	
}

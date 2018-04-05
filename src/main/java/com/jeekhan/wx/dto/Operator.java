package com.jeekhan.wx.dto;

/**
 * 系统内存中操作人员信息
 * @author jeekhan
 *
 */
public class Operator {	
	//已登录用户名
	private String loginUsername;
	
	//已登录用户ID
	private Integer loginUserId;
	
	//用户的角色级别
	private String roleLvl;
	
	public String getRoleLvl() {
		return roleLvl;
	}

	public void setRoleLvl(String roleLvl) {
		this.roleLvl = roleLvl;
	}

	public String getLoginUsername() {
		return loginUsername;
	}
	
	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}
	
	public Integer getLoginUserId() {
		return loginUserId;
	}
	
	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}

}

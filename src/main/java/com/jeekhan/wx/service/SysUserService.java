package com.jeekhan.wx.service;

import com.jeekhan.wx.model.SysUser;

/**
 * 本系统平台用户
 * @author jeekhan
 *
 */
public interface SysUserService {
	
	/**
	 * 获取系统用户信息
	 * @param useId	用户ID
	 */
	public SysUser getUser(Integer userId);
	
	/**
	 * 获取系统用户信息
	 * @param username	用户名
	 */
	public SysUser getUser(String username);
	

}

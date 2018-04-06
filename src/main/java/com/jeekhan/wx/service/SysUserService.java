package com.jeekhan.wx.service;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 添加系统用户
	 * @param sysUser
	 * @return 用户ID或小于0的错误码
	 */
	public Integer addUser(SysUser sysUser);
	

	/**
	 * 获取用户
	 * @param condParams
	 * @return
	 */
	public List<SysUser> getUsers(Map<String,Object> condParams);
	
	/**
	 * 注销用户
	 * @param usename
	 * @return 用户ID或小于0的错误码
	 */
	public Integer destroyUser(SysUser user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return 用户ID或小于0的错误码
	 */
	public Integer updateUser(SysUser user); 
	
}

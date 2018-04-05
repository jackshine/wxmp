package com.jeekhan.wx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeekhan.wx.mapper.SysUserMapper;
import com.jeekhan.wx.model.SysUser;
import com.jeekhan.wx.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService{
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	/**
	 * 获取系统用户信息
	 * @param useId	用户ID
	 */
	@Override
	public SysUser getUser(Integer userId) {
		return sysUserMapper.selectById(userId);
	}

	/**
	 * 获取系统用户信息
	 * @param username	用户名
	 */
	@Override
	public SysUser getUser(String username) {
		return sysUserMapper.selectByUsername(username);
	}

}

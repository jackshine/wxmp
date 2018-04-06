package com.jeekhan.wx.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

	/**
	 * 添加系统用户
	 * @param sysUser
	 * @return 用户ID或小于0的错误码
	 */
	public Integer addUser(SysUser sysUser) {
		sysUser.setUpdateTime(new Date());
		int cnt = sysUserMapper.insert(sysUser);
		if(cnt>0) {
			return sysUser.getId();
		}
		return -1;
	}
	
	/**
	 * 获取用户
	 * @param condParams
	 * @return
	 */
	@Override
	public List<SysUser> getUsers(Map<String,Object> condParams){
		return sysUserMapper.selectUsers(condParams);
	}
	
	/**
	 * 注销用户
	 * @param usename
	 * @return 用户ID小于0的错误码
	 */
	@Override
	public Integer destroyUser(SysUser user) {
		int cnt = sysUserMapper.destroyUser(user.getUsername());
		if(cnt>0) {
			return user.getId();
		}
		return -1;
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return 用户ID或小于0的错误码
	 */
	@Override
	public Integer updateUser(SysUser user) { 
		user.setUpdateTime(new Date());
		int cnt = sysUserMapper.updateById(user);
		if(cnt>0) {
			return user.getId();
		}
		return -1;
	}
}

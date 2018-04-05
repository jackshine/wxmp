package com.jeekhan.wx.mapper;

import com.jeekhan.wx.model.SysUser;

public interface SysUserMapper {
	
    int insert(SysUser record);

    SysUser selectById(Integer id);

    int updateById(SysUser record);
    
    SysUser selectByUsername(String username);

}
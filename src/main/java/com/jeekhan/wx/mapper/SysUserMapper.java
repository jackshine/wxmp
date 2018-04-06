package com.jeekhan.wx.mapper;

import java.util.List;
import java.util.Map;

import com.jeekhan.wx.model.SysUser;

public interface SysUserMapper {
	
    int destroyUser(String usename);

	int insert(SysUser record);
	
	int updateById(SysUser record);

    SysUser selectById(Integer id);
    
    SysUser selectByUsername(String username);
    
    List<SysUser> selectUsers(Map<String,Object> condParams);

}
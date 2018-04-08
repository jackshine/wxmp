package com.jeekhan.wx.mapper;

import java.util.List;

import com.jeekhan.wx.model.FansTag;

public interface FansTagMapper {
	
    int deleteById(Integer tagId);

    int insert(FansTag record);

    FansTag selectById(Integer tagId);

    int updateById(FansTag record);
    
    List<FansTag> selectAll();
    
}
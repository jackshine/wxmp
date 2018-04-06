package com.jeekhan.wx.mapper;

import com.jeekhan.wx.model.MediaMaterial;

public interface MediaMaterialMapper {
	
    int deleteById(Integer id);

    int insert(MediaMaterial record);

    MediaMaterial selectById(Integer id);

    int updateById(MediaMaterial record);

}
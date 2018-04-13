package com.jeekhan.wx.mapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeekhan.wx.model.MediaMaterial;
import com.jeekhan.wx.utils.PageCond;

public interface MediaMaterialMapper {
	
    int deleteById(BigInteger id);

    int insert(MediaMaterial record);

    MediaMaterial selectById(BigInteger id);

    int updateById(MediaMaterial record);
    
    int countAll(Map<String,Object> params);
    
    List<MediaMaterial> selectAll(@Param("params")Map<String,Object> params,@Param("pageCond")PageCond pageCond);

}
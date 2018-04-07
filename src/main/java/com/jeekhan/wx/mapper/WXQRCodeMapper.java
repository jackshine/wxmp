package com.jeekhan.wx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeekhan.wx.model.WXQRCode;
import com.jeekhan.wx.utils.PageCond;

public interface WXQRCodeMapper {

	int deleteById(Integer id);

    int insert(WXQRCode record);

    WXQRCode selectById(Integer id);

    int updateById(WXQRCode record);
    
    WXQRCode selectBySceneId(@Param("sceneId")Integer sceneId,@Param("isPerm")String isPerm);
    
    List<WXQRCode> selectByTicket(String ticket);
    
    int countAll(@Param("params")Map<String,Object> condParams);
    
    List<WXQRCode> selectAll(@Param("params")Map<String,Object> condParams,@Param("pageCond")PageCond pageCond);
    
}
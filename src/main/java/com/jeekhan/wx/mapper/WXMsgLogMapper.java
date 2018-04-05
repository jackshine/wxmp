package com.jeekhan.wx.mapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.jeekhan.wx.model.WXMsgLog;

public interface WXMsgLogMapper {
	int insert(WXMsgLog record);
    
    WXMsgLog selectById(BigInteger id);

    int updateById(WXMsgLog record);
    
    /**
     * 获取最近48小时内的消息的详细信息
     * @param condParams	过滤条件
     * @return
     */
    List<WXMsgLog> selectLatestMsg(Map<String,String> condParams);
    
    /**
     * 统计最近48小时内的消息的条数
     * @param condParams	过滤条件
     * @return
     */
    int countLatestMsg(Map<String,String> condParams);
    
}
package com.jeekhan.wx.mapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeekhan.wx.model.WXMsgLog;
import com.jeekhan.wx.utils.PageCond;

public interface WXMsgLogMapper {
	
	int deleteById(BigInteger id);

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
    
    /**
     *根据条件查询消息
     * @param condParams
     * @return
     */
    List<WXMsgLog> selectMsgs(@Param("params")Map<String,Object> condParams,@Param("pageCond")PageCond pageCond);
    
	/**
	 * 统计消息条数
	 * @param condParams
	 * @return
	 */
	int countMsgs(@Param("params")Map<String,Object> condParams);
	
	/**
	 * 更新应答消息ID
	 * @return
	 */
	int updateRespInfo(@Param("recvId")BigInteger recvId,@Param("respId")BigInteger respId );
    
    /**
     * 获取待注册的粉丝用户
     * @return
     */
    List<WXMsgLog> select4RegistFans();
}


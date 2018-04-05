package com.jeekhan.wx.service;

import java.util.List;
import java.util.Map;

import com.jeekhan.wx.model.WXMsgLog;


/**
 * 微信消息日志
 * @author jeekhan
 *
 */
public interface WXMsgLogService {
	
	/**
	 * 取最近48小时内的消息详细信息
	 * @param condParams	获取消息的条件
	 */
	public List<WXMsgLog> getLatestMsg(Map<String,String> condParams);
	
	/**
	 * 统计最近48小时内的消息条数
	 * @param condParams	获取消息的条件
	 */
	public int countLatestMsg(Map<String,String> condParams);
	

}

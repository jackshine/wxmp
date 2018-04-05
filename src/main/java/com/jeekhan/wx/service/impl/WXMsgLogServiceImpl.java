package com.jeekhan.wx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeekhan.wx.mapper.WXMsgLogMapper;
import com.jeekhan.wx.model.WXMsgLog;
import com.jeekhan.wx.service.WXMsgLogService;

/**
 * 微信消息管理
 * @author jeekhan
 *
 */
@Service
public class WXMsgLogServiceImpl implements WXMsgLogService{
	
	@Autowired
	private WXMsgLogMapper wXMsgLogMapper;
	
	/**
	 * 取最近48小时内的消息
	 * @param condParams	获取消息的条件
	 */
	@Override
	public List<WXMsgLog> getLatestMsg(Map<String, String> condParams) {
		return wXMsgLogMapper.selectLatestMsg(condParams);
	}
	
	/**
	 * 统计最近48小时内的消息条数
	 * @param condParams	获取消息的条件
	 */
	public int countLatestMsg(Map<String,String> condParams) {
		return wXMsgLogMapper.countLatestMsg(condParams);
	}
	
}

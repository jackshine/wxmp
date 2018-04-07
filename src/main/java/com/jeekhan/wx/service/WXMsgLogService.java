package com.jeekhan.wx.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.jeekhan.wx.model.WXMsgLog;
import com.jeekhan.wx.utils.PageCond;


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
	
	/**
	 * 根据条件查询消息
	 * @param condParams
	 * @return
	 */
	public List<WXMsgLog> getMsgs(Map<String,Object> condParams,PageCond pageCond);
	
	
	/**
	 * 统计消息条数
	 * @param condParams
	 * @return
	 */
	public int countMsgs(Map<String,Object> condParams);
	
	/**
	 * 微信消息保存
	 * @param wxMsgLog	消息
	 * @return 消息ID或错误码
	 */
	public BigInteger saveMsg(WXMsgLog wxMsgLog);
	
	/**
	 * 更新应答消息ID
	 * @return
	 */
	public int updateRespInfo(BigInteger recvId,BigInteger respId );

}

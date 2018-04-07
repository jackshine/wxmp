package com.jeekhan.wx.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeekhan.wx.mapper.WXMsgLogMapper;
import com.jeekhan.wx.model.WXMsgLog;
import com.jeekhan.wx.service.WXMsgLogService;
import com.jeekhan.wx.utils.PageCond;

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
	
	/**
	 * 根据条件查询消息
	 * @param condParams
	 * @return
	 */
	@Override
	public List<WXMsgLog> getMsgs(Map<String,Object> condParams,PageCond pageCond){
		return this.wXMsgLogMapper.selectMsgs(condParams,pageCond);
	}
	
	/**
	 * 统计消息条数
	 * @param condParams
	 * @return
	 */
	public int countMsgs(Map<String,Object> condParams) {
		return this.wXMsgLogMapper.countMsgs(condParams);
	}
	
	/**
	 * 微信消息保存
	 * @param wxMsgLog	消息
	 * @return	消息ID或错误码
	 */
	public BigInteger saveMsg(WXMsgLog wxMsgLog) {
		wxMsgLog.setCreateTime(new Date());
		int cnt = this.wXMsgLogMapper.insert(wxMsgLog);
		if(cnt>0) {
			return wxMsgLog.getId();
		}
		return new BigInteger("-1");
	}
	
	/**
	 * 更新应答消息ID
	 * @return
	 */
	public int updateRespInfo(BigInteger recvId,BigInteger respId ) {
		int cnt = this.wXMsgLogMapper.updateRespInfo(recvId, respId);
		if(cnt>0) {
			return cnt;
		}
		return -1;
	}
}

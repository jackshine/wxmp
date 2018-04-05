package com.jeekhan.wx.msg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用于消息服务的智能机器人
 * @author jeekhan
 *
 */
@Component
public class AIRobot {
	//@Value("${wx.ai-service-info}")
	private String AIServieInfo ;	//智能服务的信息项目描述
	
	public String getAIServiceInfo() {
		return this.AIServieInfo;
	}
	
	/**
	 * 智能服务处理
	 * @param question	用户的问题
	 * @return
	 */
	public String servie(String question) {
		
		return null;
	}
}

package com.jeekhan.wx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 微信消息查询
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/searchmsg")
public class SearchMsgAction {
	
	/**
	 * 获取消息查询主页
	 * @param map
	 * @return
	 */
	@RequestMapping("/index")
	public String getIndex(ModelMap map) {
		
		return "page-searchmsg";
	}

}

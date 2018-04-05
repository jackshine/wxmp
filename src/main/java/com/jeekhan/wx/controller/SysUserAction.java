package com.jeekhan.wx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 本系统平台用户管理
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserAction {
	
	/**
	 * 获取平台用户管理主页
	 * @param map
	 * @return
	 */
	@RequestMapping("/index")
	public String getIndex(ModelMap map) {
		
		return "page-sysuser";
	}

}

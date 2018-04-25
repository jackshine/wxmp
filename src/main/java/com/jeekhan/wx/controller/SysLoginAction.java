package com.jeekhan.wx.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.jeekhan.wx.dto.Operator;
import com.jeekhan.wx.model.SysUser;
import com.jeekhan.wx.service.SysUserService;
import com.jeekhan.wx.service.WXMsgLogService;
import com.jeekhan.wx.utils.SunSHAUtils;


@Controller
@SessionAttributes({"operator"})
public class SysLoginAction {
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WXMsgLogService wXMsgLogService;
	
	/**
	 * 获取登陆页面
	 * @param errmsg		从login页面转发过来的错误信息，返回页面显示
	 * @param username	从login页面转发过来的登陆用户名，返回页面显示
	 * @param map
	 * @return
	 */
	@RequestMapping("login-page")
	public String getLoginPage(String errmsg,String username,ModelMap map) {
		map.put("username", username);
		map.put("errmsg", errmsg);
		return "loginPage";
	}
	
	/**
	 * 访问系统主页
	 * @return
	 */
	@RequestMapping("/index")
	public String accessIndex(ModelMap map) {
		//显示最新待处理消息数量
		Map<String,String> condParams ;
		//取待处理消息数量
		condParams = new HashMap<String,String>();
		condParams.put("status", "0");	
		int forDealCnt = wXMsgLogService.countLatestMsg(condParams);
		map.put("forDealCnt", forDealCnt);
		return "indexPage";
	}
	
	/**
	 * 用户登陆验证
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public String login(String username,String password,ModelMap map,HttpServletRequest request) {
		if(username == null || username.trim().length() == 0 || 
				password == null || password.trim().length() == 0) {
			return "redirect:login-page";
		}
		
		try {
			SysUser user = sysUserService.getUser(username);
			if(user == null || !"1".equals(user.getStatus())) {
				//map.put("errmsg", "用户不存在，请检查输入是否正确！");
				return "forward:login-page" + "?errmsg=" + "用户不存在，请检查输入是否正确！";
			}
			if(SunSHAUtils.encodeSHA512Hex(password).equals(user.getPasswd())) {
				Operator operator = new Operator();
				operator.setLoginUserId(user.getId());
				operator.setLoginUsername(user.getUsername());
				operator.setRoleLvl(user.getRoleLvl());
				map.addAttribute("operator", operator);//保存至session
				request.getSession().setAttribute("contextPath", request.getContextPath());
				return "redirect:index";
			}else {
				//map.put("errmsg", "密码不正确，请检查输入是否正确！");
				return "forward:login-page" + "?errmsg=" + "密码不正确，请检查输入是否正确！";
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			//map.put("errmsg", "系统异常：" + e.getMessage());
			return "forward:login-page" + "?errmsg=" + "系统异常：" + e.getMessage();
		}
	}
	
	/**
	 * 用户注销登录
	 * 【权限】
	 * 		已登录用户
	 * 【功能说明】
	 * 		1、清除登录信息；
	 * 		2、重定向至登录主页；
	 * 【输入输出】
	 * @param map
	 * @return	目标页面
	 */
	@RequestMapping(value="/logout")
	public String logout(Map<String,Object>map,SessionStatus session){
		Operator operator = (Operator) map.get("operator");
		if(operator != null && operator.getLoginUserId() != null && operator.getLoginUserId() > 0){
			session.setComplete();	//清除用户session
		}
		return "redirect:/login-page";
	}
}

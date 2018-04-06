package com.jeekhan.wx.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jeekhan.wx.dto.Operator;
import com.jeekhan.wx.model.SysUser;
import com.jeekhan.wx.service.SysUserService;
import com.jeekhan.wx.utils.SunSHAUtils;

/**
 * 本系统平台用户管理
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/sys/user")
@SessionAttributes({"operator"})
public class SysUserAction {
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private KFACcountAction kFACcountAction;
	
	/**
	 * 获取平台用户管理主页
	 * @param map
	 * @return
	 */
	@RequestMapping("/index")
	public String getIndex(ModelMap map) {
		
		return "page-sysuser";
	}

	/**
	 * 添加系统用户
	 * @param sysUser
	 * @return
	 * @throws JSONException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String addUser(@Valid SysUser sysUser,BindingResult result,Operator operator) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException {
		JSONObject jsonRet = new JSONObject();
		if(!"L9".equals(operator.getRoleLvl())) {
			jsonRet.put("errmsg", "您无权限执行该操作！！！");
			jsonRet.put("errcode", -777);
			return jsonRet.toString();
		}
		try {
			//用户信息验证结果处理
			if(result.hasErrors()){
				StringBuilder sb = new StringBuilder();
				List<ObjectError> list = result.getAllErrors();
				for(ObjectError e :list){
					//String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
					sb.append(e.getDefaultMessage());
				}
				jsonRet.put("errmsg", sb.toString());
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			//数据处理
			sysUser.setHeadImg(null);
			sysUser.setId(null);
			sysUser.setIntroduce(null);
			if("1".equals(sysUser.getOpenKf())) {
				//客服账号申请
				JSONObject ret = new JSONObject(kFACcountAction.addKF(sysUser.getUsername(), sysUser.getUsername(), "123456", operator));
				if(ret.getInt("errcode") == 0) { //开通成功
					sysUser.setOpenKf("1");
					sysUser.setKfPasswd("123456");	//默认客服密码123456
					sysUser.setNickname(sysUser.getUsername());
				}else {
					sysUser.setOpenKf("0");
				}
			}else {
				sysUser.setKfPasswd(null);
				sysUser.setNickname(null);
			}
			sysUser.setPasswd(SunSHAUtils.encodeSHA512Hex("123456"));//默认密码123456
			sysUser.setRoleLvl("L1");
			sysUser.setSex("0");
			sysUser.setStatus("1");	//状态正常
			int id = this.sysUserService.addUser(sysUser);
			if(id>0) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg","ok");
			}else {
				jsonRet.put("errcode", -1);
				jsonRet.put("errmsg","数据保存失败！");
			}
		}catch(Exception e) {
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 修改系统用户基本信息
	 * @param sysUser
	 * @return
	 * @throws JSONException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateUser(@Valid SysUser sysUser,BindingResult result,Operator operator) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException {
		JSONObject jsonRet = new JSONObject();
		if(!sysUser.getUsername().equals(operator.getLoginUsername())) {
			jsonRet.put("errmsg", "您只可修改自己的基本信息！！！");
			jsonRet.put("errcode", -777);
			return jsonRet.toString();
		}
		try {
			//用户信息验证结果处理
			if(result.hasErrors()){
				StringBuilder sb = new StringBuilder();
				List<ObjectError> list = result.getAllErrors();
				for(ObjectError e :list){
					//String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
					sb.append(e.getDefaultMessage());
				}
				jsonRet.put("errmsg", sb.toString());
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			SysUser user = this.sysUserService.getUser(sysUser.getUsername());
			if(user == null) {
				jsonRet.put("errmsg", "用户不存在或已注销！！！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			//数据处理
			sysUser.setId(user.getId());
			sysUser.setPasswd(user.getPasswd());
			sysUser.setRoleLvl(user.getRoleLvl());
			sysUser.setHeadImg(user.getHeadImg());
			sysUser.setOpenKf(user.getOpenKf());
			sysUser.setKfPasswd(user.getKfPasswd());	
			sysUser.setNickname(user.getNickname());
			sysUser.setStatus("1");	//状态正常
			
			int id = this.sysUserService.updateUser(sysUser);
			if(id>0) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg","ok");
			}else {
				jsonRet.put("errcode", -1);
				jsonRet.put("errmsg","数据保存失败！");
			}
		}catch(Exception e) {
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 获取系统所有正常用户信息
	 * 1、超级管理员获取所有用户；
	 * 2、其他用户获取自己；
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/get")
	@ResponseBody
	public String getUsers(Operator operator) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		try {
			Map<String,Object> condParams = new HashMap<String,Object>();
			condParams.put("status", "1");
			if(!"L9".equals(operator.getRoleLvl())) {
				condParams.put("id", operator.getLoginUserId());
			}
			List<SysUser> datas = this.sysUserService.getUsers(condParams);
			jsonRet.put("errcode", 0);
			jsonRet.put("errmsg", "ok");
			jsonRet.put("datas", new JSONArray(datas));
		}catch(Exception e) {
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 注销用户
	 * @param username	要注销的用户名
	 * @param operator
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping("/destroy")
	@ResponseBody
	public String destroyUser(String username,Operator operator) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(!"L9".equals(operator.getRoleLvl())) {
			jsonRet.put("errmsg", "您无权限执行该操作！！！");
			jsonRet.put("errcode", -777);
			return jsonRet.toString();
		}
		if(username == null || username.trim().length()<3) {
			jsonRet.put("errmsg", "用户名不可为空！！！");
			jsonRet.put("errcode", -666);
			return jsonRet.toString();
		}
		try {
			SysUser user = this.sysUserService.getUser(username);
			if(user == null) {
				jsonRet.put("errmsg", "用户不存在或已注销！！！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			if("1".equals(user.getOpenKf())) {//已开通客服
				JSONObject ret = new JSONObject(this.kFACcountAction.deleteKF(user.getUsername(),operator));
				if(ret.getInt("errcode") == 0) {
					int code = this.sysUserService.destroyUser(user);
					if(code < 1) {
						jsonRet.put("errmsg", "数据保存失败！");
						jsonRet.put("errcode", -999);
					}else {
						jsonRet.put("errmsg", "ok");
						jsonRet.put("errcode", 0);
					}
				}else {
					jsonRet.put("errmsg", "远程删除微信客服账号失败！");
					jsonRet.put("errcode", -999);
				}
				return jsonRet.toString();
			}else {
				int code = this.sysUserService.destroyUser(user);
				if(code < 1) {
					jsonRet.put("errmsg", "数据保存失败！");
					jsonRet.put("errcode", -999);
				}else {
					jsonRet.put("errmsg", "ok");
					jsonRet.put("errcode", 0);
				}
			}
		}catch(Exception e) {
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 用户修改自己的密码
	 * @param username
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping("/updatePwd")
	@ResponseBody
	public String updatePwd(String username,String oldPwd,String newPwd,Operator operator) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(!operator.getLoginUsername().equals(username)) {
			jsonRet.put("errmsg", "您只可修改自己的密码信息！！！");
			jsonRet.put("errcode", -777);
			return jsonRet.toString();
		}
		if(oldPwd == null || oldPwd.trim().length()<1) {
			jsonRet.put("errmsg", "原密码不可为空！！！");
			jsonRet.put("errcode", -777);
			return jsonRet.toString();
		}
		if(newPwd == null || newPwd.trim().length()<6 || newPwd.trim().length()>20) {
			jsonRet.put("errmsg", "密码长度为（6-20个字符）！！！");
			jsonRet.put("errcode", -777);
			return jsonRet.toString();
		}
		try {
			SysUser user = this.sysUserService.getUser(username);
			if(user == null) {
				jsonRet.put("errmsg", "用户不存在或已注销！！！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			if(!user.getPasswd().equals(SunSHAUtils.encodeSHA512Hex(oldPwd))){
				jsonRet.put("errmsg", "原密码不正确！！！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			user.setPasswd(SunSHAUtils.encodeSHA512Hex(newPwd));
			int id =this.sysUserService.updateUser(user);
			if(id>0) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg","ok");
			}else {
				jsonRet.put("errcode", -1);
				jsonRet.put("errmsg","数据保存失败！");
			}
		}catch(Exception e) {
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
}

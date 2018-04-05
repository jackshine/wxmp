package com.jeekhan.wx.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeekhan.wx.api.CustomizeMenuHandle;

/**
 * 公众号自定义菜单控制服务类
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/wxmenu")
public class WXMenuAction {
	
	
	@RequestMapping("/index")
	public String getMenuMgrPage(ModelMap map) {
		//获取已有菜单信息返回
		return "page-wxmenu";
	}
	
	/**
	 * 创建默认自定义菜单
	 * @param jsonMenuStr	json格式默认菜单字符串,为空则使用系统默认
	 * @return {"errcode":0,"errmsg":"ok"}
	 * 
	 * @throws JSONException 
	 */
	@RequestMapping("/createDefaultMenu")
	public JSONObject createDefaultMenu(@RequestParam(value="jsonMenuStr",required=false)String jsonMenuStr) throws JSONException {
		try {
			JSONObject jsonRet ;
			JSONObject jsonObj = new JSONObject(jsonMenuStr);
			jsonRet = CustomizeMenuHandle.createMenu(jsonObj);
			return jsonRet;
		}catch(Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			return jsonRet;
		}
	}
	
	/**
	 * 查询自定义菜单,包括个性化菜单
	 * @return
	 * { "errcode":0,"errmsg":"ok",
	 * 	"menu": {...},//默认菜单
	 * 	"conditionalmenu": {"button": [...],"matchrule": {... }]} //个性化菜单列表
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/getMenu")
	public JSONObject getMenu() throws JSONException {
		try {
			JSONObject jsonRet = CustomizeMenuHandle.getMenu();
			if(!jsonRet.has("errcode")) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
			return jsonRet;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			return jsonRet;
		}
	}
	
	/**
	 * 删除自定义菜单，包括默认菜单和个性化菜单
	 * 
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/deleteMenu")
	public JSONObject deleteMenu() throws JSONException {
		try {
			JSONObject jsonRet = CustomizeMenuHandle.deleteMenu();
			if(!jsonRet.has("errcode")) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
			return jsonRet;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			return jsonRet;
		}
	}
	
	/**
	 * 添加个性化菜单
	 * @param jsonMenuStr	json格式个性化菜单字符串
	 * @return
	 * {"errcode":0,"errmsg":"ok",
	 * 	"menuid":"208379533"	//菜单ID
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/addConditionalMenu")
	public JSONObject addConditionalMenu(String jsonMenuStr) throws JSONException {
		try {
			JSONObject jsonRet ;
			if(jsonMenuStr == null || jsonMenuStr.trim().length() == 0) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "个性化菜单字符串不可为空！");
				return jsonRet;
			}
			JSONObject jsonObj = new JSONObject(jsonMenuStr);
			jsonRet = CustomizeMenuHandle.addConditionalMenu(jsonObj);
			if(!jsonRet.has("errcode")) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
			return jsonRet;
		}catch(Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			return jsonRet;
		}
	}
	
	/**
	 * 删除指定个性化菜单
	 * @param menuId		个性化菜单ID
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/deleteConditionalMenu")
	public JSONObject deleteConditionalMenu(String menuId) throws JSONException {
		try {
			JSONObject jsonRet ;
			if(menuId == null || menuId.trim().length() == 0) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "个性化菜单字符串不可为空！");
				return jsonRet;
			}
			menuId = menuId.trim();
			jsonRet = CustomizeMenuHandle.deleteConditonalMenu(menuId);
			return jsonRet;
		}catch(Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			return jsonRet;
		}
	}
	
	/**
	 * 测试个性化菜单匹配结果
	 * @param openId		粉丝的OpenID，也可以是粉丝的微信号
	 * @return
	 * {	   "errcode":0,"errmsg":"ok",
	 *     "button": [
	 *         {
 	 *            "type": "view", 
 	 *            "name": "tx", 
  	 *           "url": "http://www.qq.com/", 
  	 *           "sub_button": [ ]
 	 *        }, 
  	 *       {
  	 *           "type": "view", 
  	 *           "name": "tx", 
	 *             "url": "http://www.qq.com/", 
	 *             "sub_button": [ ]
	 *         }, 
	 *         {
	 *             "type": "view", 
	 *             "name": "tx", 
	 *             "url": "http://www.qq.com/", 
	 *             "sub_button": [ ]
	 *         }
	 *     ]
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/tryMatch")
	public JSONObject tryMatch(String openId) throws JSONException {
		try {
			JSONObject jsonRet ;
			if(openId == null || openId.trim().length() == 0) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "用户ID不可为空！");
				return jsonRet;
			}
			jsonRet = CustomizeMenuHandle.tryMatchReult(openId);
			if(!jsonRet.has("errcode")) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
			return jsonRet;
		}catch(Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			return jsonRet;
		}
	}
}

package com.jeekhan.wx.controller;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeekhan.wx.api.CustomizeMenuHandle;
import com.jeekhan.wx.utils.FileFilter;


/**
 * 公众号自定义菜单控制服务类
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/wxmenu")
public class WXMenuAction {
	
	//微信自定义菜单保存路径
	@Value("${sys.wxmenu-file-dir}")
	private String wxmenuFileDir;
	
	private String defaultMenu_filename = "defaultMenu.json";//默认菜单文件名称
	private String conditionalMenu_filename = "conditionalMenu_MENUID.json";//个性化菜单文件名称,MENUID为菜单ID
	
	@Autowired
	private CustomizeMenuHandle customizeMenuHandle;
	
	@RequestMapping("/index")
	public String getMenuMgrPage(ModelMap map) {
		//获取已有菜单信息返回
		return "page-wxmenu";
	}
	
	/**
	 * 创建默认自定义菜单
	 * 已有则删除，并重新创建；没有则直接创建
	 * @param jsonMenuStr	json格式默认菜单字符串,为空则使用系统默认
	 * @return {"errcode":0,"errmsg":"ok"}
	 * 
	 * @throws JSONException 
	 */
	@RequestMapping("/saveDefaultMenu")
	@ResponseBody
	public String saveDefaultMenu(@RequestParam(value="jsonMenuStr",required=false)String jsonMenuStr) throws JSONException {
		try {
			JSONObject jsonRet = new JSONObject();
			if(jsonMenuStr == null || jsonMenuStr.trim().length() == 0) {
				jsonRet = 
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "默认菜单字符串不可为空！");
				return jsonRet.toString();
			}
			JSONObject jsonObj = new JSONObject(jsonMenuStr);
			jsonRet = customizeMenuHandle.createMenu(jsonObj);
			if(jsonRet.has("errcode") && jsonRet.getInt("errcode") == 0) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
				//保存于本地
				File dir = new File(this.wxmenuFileDir);
				if(!dir.exists()) {
					dir.mkdirs();
				}
				File file = new File(this.wxmenuFileDir,this.defaultMenu_filename);
				if(file.exists()) {
					file.delete();
				}
				FileUtils.write(file, jsonMenuStr, "utf-8");
			}
			return jsonRet.toString();
		}catch(Exception e) {
			e.printStackTrace();
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			return jsonRet.toString();
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
	//@RequestMapping("/getMenuFromWX")
	public JSONObject getMenuFromWX() throws JSONException {
		try {
			JSONObject jsonRet = customizeMenuHandle.getMenu();
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
	//@RequestMapping("/deleteMenu")
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
	 * 添加、修改个性化菜单
	 * 已有则删除，并重新创建；没有则直接创建
	 * @param jsonMenuStr	json格式个性化菜单字符串
	 * @return
	 * {"errcode":0,"errmsg":"ok",
	 * 	"menuid":"208379533"	//菜单ID
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/saveConditionalMenu")
	@ResponseBody
	public String saveConditionalMenu(String menuId,@RequestParam(value="jsonMenuStr",required=true)String jsonMenuStr) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		try {
			
			if(jsonMenuStr == null || jsonMenuStr.trim().length() == 0) {
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "个性化菜单字符串不可为空！");
				return jsonRet.toString();
			}
			File file = null;
			if(menuId != null && menuId.trim().length()>0) {
				file = new File(this.wxmenuFileDir,this.conditionalMenu_filename.replaceAll("MENUID", menuId));
				if(file.exists()) {//已有该菜单
					file.delete();
					JSONObject ret = this.customizeMenuHandle.deleteConditonalMenu(menuId);
					if(ret.getInt("errcode") != 0) {
						return ret.toString();
					}
				}
			}
			JSONObject jsonObj = new JSONObject(jsonMenuStr);
			jsonRet = customizeMenuHandle.addConditionalMenu(jsonObj);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
				//保存于本地
				File dir = new File(this.wxmenuFileDir);
				if(!dir.exists()) {
					dir.mkdirs();
				}
				file = new File(this.wxmenuFileDir,this.conditionalMenu_filename.replaceAll("MENUID", jsonRet.get("menuid").toString()));
				FileUtils.write(file, jsonMenuStr, "utf-8");
			}
			return jsonRet.toString();
		}catch(Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			return jsonRet.toString();
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
			jsonRet = customizeMenuHandle.deleteConditonalMenu(menuId);
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
	//@RequestMapping("/tryMatch")
	public JSONObject tryMatch(String openId) throws JSONException {
		try {
			JSONObject jsonRet ;
			if(openId == null || openId.trim().length() == 0) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "用户ID不可为空！");
				return jsonRet;
			}
			jsonRet = customizeMenuHandle.tryMatchReult(openId);
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
	 * 获取默认菜单
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/getDefaultMenu")
	@ResponseBody
	public String getDefaultMenu() throws JSONException {
		JSONObject jsonRet = new JSONObject();
		try {
			File file = new File(this.wxmenuFileDir,this.defaultMenu_filename);
			if(!file.exists()) {
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "默认菜单数据还未创建！" );
				return jsonRet.toString();
			}
			String data = FileUtils.readFileToString(file);
			jsonRet.put("errcode", 0);
			jsonRet.put("errmsg", "ok" );
			jsonRet.put("data", data);
		}catch(Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 获取个性化菜单数据
	 * @param menuId 菜单ID
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/getConditionalMenu/{menuId}")
	@ResponseBody
	public String getConditionalMenu(@PathVariable("menuId")String menuId) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(menuId == null || menuId.trim().length()<1) {
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "菜单ID不可为空！" );
			return jsonRet.toString();
		}
		menuId = menuId.trim();
		try {
			File file = new File(this.wxmenuFileDir,this.conditionalMenu_filename.replaceAll("MENUID", menuId));
			if(!file.exists()) {
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "该个性化菜单数据还未创建！" );
				return jsonRet.toString();
			}
			String data = FileUtils.readFileToString(file);
			jsonRet.put("errcode", 0);
			jsonRet.put("errmsg", "ok" );
			jsonRet.put("data", data);
		}catch(Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 获取所有个性化菜单ID列表
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping("/getCondiMenuIdList")
	@ResponseBody
	public String getCondiMenuIdList() throws JSONException {
		JSONObject jsonRet = new JSONObject();
		JSONArray data = new JSONArray();
		try {
			File dir = new File(this.wxmenuFileDir);
			String filePattern = this.conditionalMenu_filename.replaceAll("_MENUID.json", "");
			File[] files = dir.listFiles(new FileFilter(filePattern));
			if(files != null && files.length>0){
				for(File file:files) {
					String filename = file.getName();
					String menuId = filename.replaceAll("conditionalMenu_", "").replaceAll(".json", "");
					data.put(menuId);
				}
			}
			jsonRet.put("errcode", 0);
			jsonRet.put("errmsg", "ok" );
			jsonRet.put("data", data);
		}catch(Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
}

package com.jeekhan.wx.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jeekhan.wx.utils.HttpUtils;



/**
 * 自定义菜单管理
 * @author Jee Khan
 * 1、菜单规则
 * 1）、自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。
 * 2）、一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。
 * 3）、创建自定义菜单后，菜单的刷新策略是，在用户进入公众号会话页或公众号profile页时，如果发现上一次拉取菜单的请求在5分钟以前，
 *	就会拉取一下菜单，如果菜单有更新，就会刷新客户端的菜单。测试时可以尝试取消关注公众账号后再次关注，则可以看到创建后的效果。
 * 2、个性化菜单
 * 1）可以通过以下条件来设置用户看到的菜单： 用户分组、性别、手机操作系统、地区（用户在微信客户端设置的地区）、语言（用户在微信客户端设置的语言）
 * 2）出于安全考虑，一个公众号的所有个性化菜单，最多只能设置为跳转到3个域名下的链接
 * 3）创建个性化菜单之前必须先创建默认菜单（默认菜单是指使用普通自定义菜单创建接口创建的菜单）。如果删除默认菜单，个性化菜单也会全部删除。
 * 4）当公众号创建多个个性化菜单时，将按照发布顺序，由新到旧逐一匹配，直到用户信息与matchrule相符合。如果全部个性化菜单都没有匹配成功，则返回默认菜单。
 * 例如公众号先后发布了默认菜单，个性化菜单1，个性化菜单2，个性化菜单3。那么当用户进入公众号页面时，将从个性化菜单3开始匹配，如果个性化菜单3匹配成功，则直接返回个性化菜单3，否则继续尝试匹配个性化菜单2，直到成功匹配到一个菜单。
 * 根据上述匹配规则，为了避免菜单生效时间的混淆，决定不予提供个性化菜单编辑API，开发者需要更新菜单时，需将完整配置重新发布一轮
 */
@Component
public class CustomizeMenuHandle {
	static Logger log = LoggerFactory.getLogger(CustomizeMenuHandle.class);

	private static String CUSTOMMENU;
    
    @Value("${wx.DEFAULT-MENU}")
    public void setDefaultMenu(String defaultMenu) {
    		CustomizeMenuHandle.CUSTOMMENU = defaultMenu;
    }
	
	/**
	 * 创建默认菜单
	 * http请求方式：POST（请使用https协议） 
	 * @param menu	默认菜单 
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public static JSONObject createMenu(JSONObject menu) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token;
		if(menu == null ) {
			menu = new JSONObject(CUSTOMMENU);
		}
		log.info("创建默认菜单（POST）：" + url + ",参数：" + menu);
		String ret = HttpUtils.doPostSSL(url, CUSTOMMENU);
		JSONObject jsonRet = new JSONObject(ret);
		log.info("创建默认菜单（POST）返回：" + ret );
		return jsonRet;
	}
	
	/**
	 * 查询菜单
	 * 在设置了个性化菜单后，使用本自定义菜单查询接口可以获取默认菜单和全部个性化菜单信息
	 * http请求方式：GET https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN
	 * 
	 * @return
	 * { 
	 * 	"menu": {...},//默认菜单
	 * 	"conditionalmenu": {"button": [...],"matchrule": {... }]} //个性化菜单列表
	 * } 
	 * @throws JSONException 
	 */
	public static JSONObject getMenu() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + token;
		log.info("查询菜单（GET）：" + url );
		String ret = HttpUtils.doGetSSL(url);
		log.info("查询菜单（GET）返回：" + ret );
		JSONObject obj = new JSONObject(ret);
		return obj;
	}
	
	/**
	 * 删除所有自定义菜单（包括默认菜单和全部个性化菜单）
	 * http请求方式：GET https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN
	 * 
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public static JSONObject deleteMenu() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + token;
		log.info("删除所有自定义菜单（GET）：" + url );
		String ret = HttpUtils.doGetSSL(url);
		log.info("删除所有自定义菜单（GET）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 添加个性化菜单
	 * http请求方式：POST（请使用https协议） https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN
	 * 
	 * @param menu	个性化菜单
	 * @return 
	 * {
	 * 	"menuid":"208379533"	//菜单ID
	 * }
	 * @throws JSONException 
	 */
	public static JSONObject addConditionalMenu(JSONObject menu) throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = " https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=" + token;
		log.info("添加个性化菜单（POST）：" + url + ",参数：" + menu);
		String ret = HttpUtils.doPostSSL(url, menu);
		log.info("添加个性化菜单（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}

	/**
	 * 删除个性化菜单
	 * 
	 * http请求方式：POST（请使用https协议） https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN
	 * @return
	 * @param menuid	菜单ID
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException 
	 */
	public static JSONObject deleteConditonalMenu(String menuid) throws JSONException{
		String menuStr = "{\"menuid\":\""+ menuid +"\"}";
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=" + token;
		JSONObject jsonObj = new JSONObject(menuStr);
		log.info("删除个性化菜单（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, jsonObj);
		log.info("删除个性化菜单（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 测试个性化菜单匹配结果
	 * 
	 * http请求方式：POST（请使用https协议） https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=ACCESS_TOKEN
	 * @param userId 可以是粉丝的OpenID，也可以是粉丝的微信号
	 * @return 
	 * {	   
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
	public static JSONObject tryMatchReult(String userId) throws JSONException{
		String menuStr = "{\"user_id\":\""+ userId +"\"}";
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=" + token;
		JSONObject jsonObj = new JSONObject(menuStr);
		log.info("测试个性化菜单匹配结果（POST）：" + url + ",参数：" + jsonObj);
		String ret = HttpUtils.doPostSSL(url, menuStr);
		log.info("测试个性化菜单匹配结果（POST）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet;
	}
	
	/**
	 * 获取自定义菜单配置
	 * 本接口将会提供公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置.
	 * 本接口与自定义菜单查询接口的不同之处在于，本接口无论公众号的接口是如何设置的，都能查询到，而自定义菜单查询接口则仅能查询到使用API设置的菜单配置.
	 * 
	 * http请求方式: GET（请使用https协议）https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getCurrentSelfmenuInfo() throws JSONException{
		String token = AccessToken.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=" + token;
		log.info("获取自定义菜单配置（GET）：" + url );
		String ret = HttpUtils.doGetSSL(url);
		log.info("获取自定义菜单配置（GET）返回：" + ret );
		JSONObject jsonRet = new JSONObject(ret);
		return jsonRet; 
	}
}

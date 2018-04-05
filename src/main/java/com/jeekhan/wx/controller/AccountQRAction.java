package com.jeekhan.wx.controller;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeekhan.wx.api.AccountQRHandle;

/**
 * 微信公众账号二维码管理
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/qrcode")
public class AccountQRAction {
	
	@Autowired
	private AccountQRHandle accountQRHandle;
	
	/**
	 * 获取公众账号管理主页
	 * @param map
	 * @return
	 */
	@RequestMapping("/index")
	public String getIndex(ModelMap map) {
		
		
		return "page-account";
	}
	
	/**
	 * 创建临时二维码，获取ticket
	 * @param senceId		场景值ID，临时二维码时为32位非0整型
	 * @return 
	 * {"errcode":0,"errmsg":"ok",
	 * "ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==",//获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
	 * "expire_seconds":60, //该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）
	 * "url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI"//二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/createTmp")
	public JSONObject createTmpTicket(Integer sceneId) throws JSONException {
		int expire_seconds = 28*24*60*60;
		JSONObject jsonRet = null;
		if(sceneId == null || sceneId < 1 || sceneId > 2147483647) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "场景值ID不可为空且不可大于2147483647！");
			return jsonRet;
		}
		try {
			jsonRet = accountQRHandle.createTempTicket(sceneId, expire_seconds);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			
		}
		return jsonRet;
	}
	
	/**
	 * 
	 * @param sceneStr	场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
	 * @return 
	 * {"errcode":0,"errmsg":"ok",
	 * "ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==",//获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
	 * "expire_seconds":60, //该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）
	 * "url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI"//二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	 * }
	 * @throws JSONException
	 */
	//@RequestMapping("/createTmp")
	public JSONObject createTmpTicket(String sceneStr) throws JSONException {
		int expire_seconds = 28*24*60*60;
		JSONObject jsonRet = null;
		if(sceneStr == null || sceneStr.trim().length() < 1 || sceneStr.trim().length()>64) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "场景值字符串ID不可为空且长度不可大于64！");
			return jsonRet;
		}
		sceneStr = sceneStr.trim();
		try {
			jsonRet = accountQRHandle.createTempTicket(sceneStr, expire_seconds);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			
		}
		return jsonRet;
		
	}
	
	/**
	 * 
	 * @param sceneId	场景值ID，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @return 
	 * {"errcode":0,"errmsg":"ok",
	 * "ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==",//获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
	 * "url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI"//二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	 * }
	 * @throws JSONException
	 */
	@RequestMapping("/createPerm")
	public JSONObject createTicket(Integer sceneId) throws JSONException {
		JSONObject jsonRet = null;
		if(sceneId == null || sceneId < 1 || sceneId > 100000) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "场景值ID不可为空且不可大于100000！");
			return jsonRet;
		}
		try {
			jsonRet = accountQRHandle.createTicket(sceneId);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			
		}
		return jsonRet;
	}
	
	/**
	 * 
	 * @param sceneStr	场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
	 * @return 
	 * {"errcode":0,"errmsg":"ok",
	 * "ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==",//获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
	 * "url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI"//二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	 * }
	 * @throws JSONException
	 */
	//@RequestMapping("/createPerm")
	public JSONObject createTicket(String sceneStr) throws JSONException {
		JSONObject jsonRet = null;
		if(sceneStr == null || sceneStr.trim().length() < 1 || sceneStr.trim().length()>64) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "场景值字符串ID不可为空且长度不可大于64！");
			return jsonRet;
		}
		sceneStr = sceneStr.trim();
		try {
			jsonRet = accountQRHandle.createTicket(sceneStr);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			
		}
		return jsonRet;
	}
	
	/**
	 * 根据ticket获取二维码
	 * @param ticket
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/getImg")
	public Object getQRCodeImg(String ticket,HttpServletResponse response) throws JSONException {
		JSONObject jsonRet = null;
		if(ticket == null || ticket.trim().length() < 1) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "场景值字符串 不可为空！");
			return jsonRet;
		}
		ticket = ticket.trim();
		try {
			File file = accountQRHandle.getQRCodeImg(ticket);
			if(file == null || file.length()<1) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -999);
				jsonRet.put("errmsg", "获取二维码图片失败！");
				return jsonRet;
			}
//			if(file != null && file.length()>0){
//				BufferedImage image = ImageIO.read(file);
//				response.setContentType("image/*");  
//				OutputStream os = response.getOutputStream();  
//				ImageIO.write(image, "jpg", os);
//			}
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	
}

package com.jeekhan.wx.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeekhan.wx.api.AccountQRHandle;
import com.jeekhan.wx.dto.Operator;
import com.jeekhan.wx.model.WXQRCode;
import com.jeekhan.wx.service.WXQRCodeService;
import com.jeekhan.wx.utils.PageCond;
import com.jeekhan.wx.utils.QRCodeUtil;

/**
 * 微信公众账号二维码管理
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/qrcode")
public class AccountQRAction {
	//二维码图片目录
	@Value("${sys.qrcode-file-dir}")
	private String qrCodeFileDir;
	
	@Autowired
	private AccountQRHandle accountQRHandle;
	
	@Autowired
	private WXQRCodeService wXQRCodeService;
	
	
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
	@ResponseBody
	public String createTmpTicket(Integer sceneId) throws JSONException {
		int expire_seconds = 28*24*60*60;
		JSONObject jsonRet = null;
		if(sceneId == null || sceneId < 1 || sceneId > 2147483647) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "场景值ID不可为空且不可大于2147483647！");
			return jsonRet.toString();
		}
		try {
			//系统检查
			WXQRCode rec = this.wXQRCodeService.get("0", sceneId);
			if(rec != null) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "管理平台中该场景值已被使用！");
				return jsonRet.toString();
			}
			jsonRet = accountQRHandle.createTempTicket(sceneId, expire_seconds);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
				rec = new WXQRCode();
				rec.setExpireSeconds(jsonRet.getInt("expire_seconds"));
				rec.setIsPerm("0");
				rec.setSceneId(sceneId);
				rec.setTicket(jsonRet.getString("ticket"));
				rec.setUrl(jsonRet.getString("url"));
				this.wXQRCodeService.add(rec);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
			
		}
		return jsonRet.toString();
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
	 * 创建永久二维码
	 * 1、如果平台系统没有该场景值对应的二位码，则向微信服务器发送请求申请获取ticket；
	 * 2、如果平台系统已有该场景值对应的二维码，则返回错误信息；
	 * @param sceneId	场景值ID，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @return 
	 * {"errcode":0,"errmsg":"ok",
	 * "ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==",//获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
	 * "url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI"//二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	 * }
	 * @throws JSONException
	 */
	@RequestMapping("/createPerm")
	@ResponseBody
	public String createPerm(Integer sceneId) throws JSONException {
		JSONObject jsonRet = null;
		if(sceneId == null || sceneId < 1 || sceneId > 100000) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "场景值ID不可为空且不可大于100000！");
			return jsonRet.toString();
		}
		try {
			//系统检查
			WXQRCode rec = this.wXQRCodeService.get("1", sceneId);
			if(rec != null) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "管理平台中该场景值已被使用！");
				return jsonRet.toString();
			}
			jsonRet = accountQRHandle.createTicket(sceneId);
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg", "ok");
				rec = new WXQRCode();
				rec.setExpireSeconds(-1);
				rec.setIsPerm("1");
				rec.setSceneId(sceneId);
				rec.setTicket(jsonRet.getString("ticket"));
				rec.setUrl(jsonRet.getString("url"));
				this.wXQRCodeService.add(rec);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
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
	 * 1、如果被系统中已有二维码图片，则返回二维码图片名称；
	 * 2、否则系统根据url生成并保存；然后返回二维码图片名称；
	 * @param ticket
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("/getqrcode")
	@ResponseBody
	public String getQRCode(String ticket,HttpServletRequest request) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		if(ticket == null || ticket.trim().length() < 1) {
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "二维码ticket 不可为空！");
			return jsonRet.toString();
		}
		ticket = ticket.trim();
		try {
			//本系统检查
			WXQRCode rec = this.wXQRCodeService.get(ticket);
			if(rec == null) {
				jsonRet.put("errcode", -666);
				jsonRet.put("errmsg", "管理平台中没有该ticket对应的二维码数据！");
				return jsonRet.toString();
			}
			if(rec.getLocalImgUrl() != null && rec.getLocalImgUrl().length()>1) {
				;
			}else {
				//新生成
				//String logoPath = request.getServletContext().getRealPath("images/mfyx_logo.jpeg");
				Resource resource = new ClassPathResource("static/images/mfyx_logo.jpeg");
				File file = resource.getFile();
				String filename = QRCodeUtil.encode(rec.getUrl(),file.getAbsolutePath(),this.qrCodeFileDir, 710,false);
				WXQRCode rec4upd = new WXQRCode();
				rec4upd.setId(rec.getId());
				rec4upd.setLocalImgUrl(filename);
				rec.setLocalImgUrl(filename);
				this.wXQRCodeService.update(rec4upd);
			}
			jsonRet.put("errcode", 0);
			jsonRet.put("errmsg", "ok");
			jsonRet.put("filename", rec.getLocalImgUrl());
			return jsonRet.toString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
	
	/**
	 * 显示二维码图片
	 * @param picName	图片名称
	 * @param out
	 * @throws IOException 
	 */
	@RequestMapping(value="/show/{picName}")
	public void showQRCode(@PathVariable("picName")String picName,OutputStream out,HttpServletRequest request,HttpServletResponse response) throws IOException{
		File file = new File(this.qrCodeFileDir,picName);
		BufferedImage image = ImageIO.read(file);
		response.setContentType("image/*");  
		OutputStream os = response.getOutputStream();  
		String type = file.getName().substring(file.getName().lastIndexOf('.')+1);
		ImageIO.write(image, type, os);  
	}
	
	/**
	 * 根据条件查询数据
	 * @param jsonParams	json格式查询条件
	 * @param pageCond json格式字符串：begin,pageSize
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping("/search")
	@ResponseBody
	public String search(String jsonParams,String pageCond,Operator operator) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		try {
			Map<String,Object> condParams = new HashMap<String,Object>();
			PageCond pCond = new PageCond();
			pCond.setBegin(0);
			pCond.setPageSize(20);
			JSONObject params = new JSONObject(jsonParams);
			JSONObject page = new JSONObject(pageCond);
			//条件整合
			if(params.has("isPerm")) {
				if(params.getString("isPerm") != null && (params.getString("isPerm").trim().length()>0)){
					condParams.put("isPerm", params.getString("isPerm"));
				}
			}
			if(params.has("sceneId")) {
				if(params.get("sceneId") != null && (params.get("sceneId").toString().trim().length()>0)){
					condParams.put("sceneId", params.get("sceneId"));
				}
			}
			if(params.has("ticket")) {
				if(params.getString("ticket") != null && (params.getString("ticket").trim().length()>0)){
					condParams.put("ticket", params.getString("ticket"));
				}
			}
			if(params.has("isAvail")) {
				if(params.getString("isAvail") != null && (params.getString("isAvail").trim().length()>0)){
					condParams.put("isAvail", params.getString("isAvail"));
				}
			}
			if(params.has("beginTime")) {
				if(params.getString("beginTime") != null && (params.getString("beginTime").trim().length()>0)){
					condParams.put("beginTime", params.getString("beginTime"));
				}
			}
			if(params.has("endTime")) {
				if(params.getString("endTime") != null && (params.getString("endTime").trim().length()>0)){
					condParams.put("endTime", params.getString("endTime"));
				}
			}
			if(page.has("begin")) {
				pCond.setBegin(page.getInt("begin")<0 ? 0 : page.getInt("begin"));
			}
			if(page.has("pageSize")) {
				int size = page.getInt("pageSize");
				if(size < 2 || size > 100) {
					size = 20;
				}
				pCond.setPageSize(size);
			}
			int cnt = this.wXQRCodeService.countAll(condParams);
			pCond.setCount(cnt);
			page.put("pageSize", pCond.getPageSize());
			page.put("begin", pCond.getBegin());
			page.put("count", pCond.getCount());
			if(cnt > 0) {
			List<WXQRCode> datas = this.wXQRCodeService.getAll(condParams, pCond);
				jsonRet.put("datas", new JSONArray(datas));
			}
			jsonRet.put("errcode", 0);
			jsonRet.put("errmsg", "ok");
			jsonRet.put("pageCond", page);
		}catch(Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
}

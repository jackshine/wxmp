package com.jeekhan.wx.controller;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jeekhan.wx.api.KFAccountHandle;

/**
 * 客服账号管理
 * @author jeekhan
 *
 */
@RestController
@RequestMapping("/kfaccount")
public class KFACcountAction {
	@Autowired
	private KFAccountHandle customServiceAccountHandle;
	@Value("${sys.tmp-file-dir}")
	private String tmpFileDir;	//临时文件保存目录
	/**
	 * 添加客服帐号
	 * @param account 账号名称
	 * @param nickname	昵称
	 * @param pwd	密码明文的md5
	 * @return {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/add")
	public JSONObject addKF(String account,String nickname,String pwd) throws JSONException{
		JSONObject jsonRet;
		if(account == null || account.trim().length() == 0 ||
				nickname == null || nickname.trim().length() ==0 ||
				pwd == null || pwd.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "账号名称、昵称、密码MD5 不可为空！");
			return jsonRet;
		}
		
		try {
			jsonRet = customServiceAccountHandle.addKF(account, nickname, pwd);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}

	/**
	 * 修改客服帐号
	 * @param account 账号名称
	 * @param nickname	昵称
	 * @param pwd	密码明文的md5	 
	 * @return {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/update")
	public JSONObject updateKF(String account,String nickname,String pwd) throws JSONException{
		JSONObject jsonRet;
		if(account == null || account.trim().length() == 0 ||
				nickname == null || nickname.trim().length() ==0 ||
				pwd == null || pwd.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "账号名称、昵称、密码MD5 不可为空！");
			return jsonRet;
		}
		
		try {
			jsonRet = customServiceAccountHandle.updateKF(account, nickname, pwd);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 删除客服帐号
	 * @param account 账号名称
	 * @param nickname	昵称
	 * @param pwd	密码明文的md5	 
	 * @return {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/delete")
	public JSONObject deleteKF(String account,String nickname,String pwd) throws JSONException{
		JSONObject jsonRet;
		if(account == null || account.trim().length() == 0 ||
				nickname == null || nickname.trim().length() ==0 ||
				pwd == null || pwd.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "账号名称、昵称、密码MD5 不可为空！");
			return jsonRet;
		}
		try {
			jsonRet = customServiceAccountHandle.deleteKF(account, nickname, pwd);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 设置客服帐号的头像
	 * 开发者可调用本接口来上传图片作为客服人员的头像，头像图片文件必须是jpg格式，推荐使用640*640大小的图片以达到最佳效果
     * @return
	 * @throws JSONException 
	 */
	@RequestMapping("/uploadImg")
	public Object uploadHeadImg(String account,@RequestParam(value="headImg")MultipartFile headImg) throws JSONException{
		JSONObject jsonRet;
		if(account == null || account.trim().length() == 0 ||
				headImg == null || headImg.isEmpty() ) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "账号名称、头像文件 不可为空！");
			return jsonRet;
		}
		//文件类型判断
		String imgType = headImg.getOriginalFilename().substring(headImg.getOriginalFilename().lastIndexOf('.')+1);
		if(!"jpg".equalsIgnoreCase(imgType)) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -888);
			jsonRet.put("errmsg", "头像图片文件必须是jpg格式！");
			return jsonRet;
		}
		File dir = new File(this.tmpFileDir);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File tmpImg = new File(dir,headImg.getOriginalFilename());
		
		try {
			FileUtils.copyInputStreamToFile(headImg.getInputStream(), tmpImg);
			jsonRet = customServiceAccountHandle.uploadHeadImg(account, tmpImg);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}finally {
			tmpImg.delete();
		}
		return jsonRet;
	}
	
	/**
	 * 获取所有客服账号
	 * 开发者通过本接口，获取公众号中所设置的客服基本信息，包括客服工号、客服昵称、客服登录账号。 
	 * http请求方式: GET
     * https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN
     * {	   "errcode":0,"errmsg":"ok",
     *     "kf_list": [
     *         {
     *             "kf_account": "test1@test", 
     *             "kf_nick": "ntest1", 
     *             "kf_id": "1001"
     *             "kf_headimgurl": " http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjfUS8Ym0GSaLic0FD3vN0V8PILcibEGb2fPfEOmw/0"
     *         }, 
     *         ....
     *     ]
     * }
	 * @throws JSONException 
     */
	@RequestMapping("/getKFList")
	public JSONObject getKFList() throws JSONException {
		JSONObject jsonRet;
		try {
			jsonRet = customServiceAccountHandle.getKFList();
			if(!jsonRet.has("errcode")) {
				jsonRet.put("errcode", 0);
				jsonRet.put("errmsg","ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet; 
	}
	
}

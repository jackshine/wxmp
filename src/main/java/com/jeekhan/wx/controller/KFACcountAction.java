package com.jeekhan.wx.controller;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.jeekhan.wx.api.KFAccountHandle;
import com.jeekhan.wx.dto.Operator;
import com.jeekhan.wx.model.SysUser;
import com.jeekhan.wx.service.SysUserService;

/**
 * 客服账号管理
 * @author jeekhan
 *
 */
@RestController
@RequestMapping("/kfaccount")
@SessionAttributes({"operator"})
public class KFACcountAction {
	@Autowired
	private KFAccountHandle customServiceAccountHandle;
	@Value("${sys.tmp-file-dir}")
	private String tmpFileDir;	//临时文件保存目录
	@Value("${sys.kf-headimg-dir}")
	private String kfHeadImgDir; //客服头像文件保存目录
	@Autowired
	private SysUserService sysUserService;
	/**
	 * 添加客服帐号
	 * @param account 账号名称
	 * @param nickname	昵称
	 * @param pwd	密码明文的md5
	 * @return {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String addKF(String account,String nickname,String pwd,Operator operator) throws JSONException{
		JSONObject jsonRet = new JSONObject();
		if(!"L9".equals(operator.getRoleLvl())) {
			jsonRet.put("errmsg", "您无权限执行该操作！！！");
			jsonRet.put("errcode", -777);
			return jsonRet.toString();
		}
		if(account == null || account.trim().length() == 0 ||
				nickname == null || nickname.trim().length() ==0 ||
				pwd == null || pwd.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "账号名称、昵称、密码 不可为空！");
			return jsonRet.toString();
		}
		SysUser user = this.sysUserService.getUser(account);
		if(user == null) {
			jsonRet.put("errmsg", "用户不存在或已注销！！！");
			jsonRet.put("errcode", -666);
			return jsonRet.toString();
		}
		if("1".equals(user.getOpenKf())) {
			jsonRet.put("errmsg", "用户已开通客服！！！");
			jsonRet.put("errcode", -666);
			return jsonRet.toString();
		}
		try {
			jsonRet = customServiceAccountHandle.addKF(account, nickname, pwd);
			if(jsonRet.getInt("errcode") == 0) {
				user.setNickname(nickname);
				user.setKfPasswd(pwd);
				user.setOpenKf("1");
				this.sysUserService.updateUser(user);
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
	 * 修改客服帐号
	 * @param account 账号名称
	 * @param nickname	昵称
	 * @param pwd	密码明文的md5	 
	 * @return {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateKF(String account,String nickname,String pwd,Operator operator) throws JSONException{
		JSONObject jsonRet = new JSONObject();;
		if(!account.equals(operator.getLoginUsername())) {
			jsonRet.put("errmsg", "您只可修改自己的微信客服信息！！！");
			jsonRet.put("errcode", -777);
			return jsonRet.toString();
		}
		if(account == null || account.trim().length() == 0 ||
				nickname == null || nickname.trim().length() ==0 ||
				pwd == null || pwd.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "账号名称、昵称、密码 不可为空！");
			return jsonRet.toString();
		}
		
		try {
			SysUser user = this.sysUserService.getUser(account);
			if(user == null) {
				jsonRet.put("errmsg", "用户不存在或已注销！！！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			if("0".equals(user.getOpenKf())) {
				jsonRet.put("errmsg", "用户还未开通客服！！！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			jsonRet = customServiceAccountHandle.updateKF(account, nickname, pwd);
			if(jsonRet.getInt("errcode") == 0) {
				user.setNickname(nickname);
				user.setKfPasswd(pwd);
				user.setOpenKf("1");
				this.sysUserService.updateUser(user);
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
	 * 删除客服帐号
	 * @param account 账号名称
	 * @param nickname	昵称
	 * @param pwd	密码明文的md5	 
	 * @return {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteKF(String account,Operator operator) throws JSONException{
		JSONObject jsonRet = new JSONObject();
		if(!"L9".equals(operator.getRoleLvl())) {
			jsonRet.put("errmsg", "您无权限执行该操作！！！");
			jsonRet.put("errcode", -777);
			return jsonRet.toString();
		}
		if(account == null || account.trim().length() == 0) {
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "账号名称不可为空！");
			return jsonRet.toString();
		}
		try {
			SysUser user = this.sysUserService.getUser(account);
			if(user == null) {
				jsonRet.put("errmsg", "用户不存在或已注销！！！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			if("0".equals(user.getOpenKf())) {
				jsonRet.put("errmsg", "用户还未开通客服！！！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			jsonRet = customServiceAccountHandle.deleteKF(account, user.getNickname(), user.getKfPasswd());
			if(jsonRet.getInt("errcode") == 0) {
				user.setNickname("");
				user.setKfPasswd("");
				user.setOpenKf("0");
				this.sysUserService.updateUser(user);
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
	 * 设置客服帐号的头像
	 * 开发者可调用本接口来上传图片作为客服人员的头像，头像图片文件必须是jpg格式，推荐使用640*640大小的图片以达到最佳效果
     * @return {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	@RequestMapping(value="/uploadImg",method=RequestMethod.POST)
	@ResponseBody
	public String uploadHeadImg(String account,@RequestParam(value="headImg")MultipartFile headImg,Operator operator) throws JSONException{
		JSONObject jsonRet = new JSONObject();
		if(!account.equals(operator.getLoginUsername())) {
			jsonRet.put("errmsg", "您只可修改自己的微信客服信息！！！");
			jsonRet.put("errcode", -777);
			return jsonRet.toString();
		}
		if(account == null || account.trim().length() == 0 ||
				headImg == null || headImg.isEmpty() ) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "账号名称、头像文件 不可为空！");
			return jsonRet.toString();
		}
		//文件类型判断
		String imgType = headImg.getOriginalFilename().substring(headImg.getOriginalFilename().lastIndexOf('.')+1);
		if(!"jpg".equalsIgnoreCase(imgType)) {
			jsonRet.put("errcode", -888);
			jsonRet.put("errmsg", "头像图片文件必须是jpg格式！");
			return jsonRet.toString();
		}
		File dir = new File(this.tmpFileDir);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File tmpImg = new File(dir,headImg.getOriginalFilename());
		
		try {
			SysUser user = this.sysUserService.getUser(account);
			if(user == null) {
				jsonRet.put("errmsg", "用户不存在或已注销！！！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			if("0".equals(user.getOpenKf())) {
				jsonRet.put("errmsg", "用户还未开通客服！！！");
				jsonRet.put("errcode", -666);
				return jsonRet.toString();
			}
			FileUtils.copyInputStreamToFile(headImg.getInputStream(), tmpImg);
			jsonRet = customServiceAccountHandle.uploadHeadImg(account, tmpImg);
			if(jsonRet.getInt("errcode") == 0) {
				File headimgDir = new File(this.kfHeadImgDir);
				if(!headimgDir.exists()) {
					headimgDir.mkdirs();
				}
				File img = new File(this.kfHeadImgDir,headImg.getOriginalFilename());
				FileUtils.copyFile(tmpImg, img);
				user.setHeadImg(img.getAbsolutePath());
				this.sysUserService.updateUser(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}finally {
			tmpImg.delete();
		}
		return jsonRet.toString();
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
	@ResponseBody
	public String getKFList() throws JSONException {
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
		return jsonRet.toString(); 
	}	
}

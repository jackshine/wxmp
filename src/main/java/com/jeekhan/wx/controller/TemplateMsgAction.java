package com.jeekhan.wx.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeekhan.wx.api.AccessToken;
import com.jeekhan.wx.msg.TemplateMsgHandle;
import com.jeekhan.wx.utils.HttpUtils;

/**
 * 模板消息控制其服务
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/tplmsg")
public class TemplateMsgAction {
	@Autowired
	private TemplateMsgHandle  templateMsgHandle;
	
	/**
	 * 获取模板消息首页
	 * @param map
	 * @return
	 */
	@RequestMapping("/index")
	public String getIndex(ModelMap map) {
		
		return "page-tplmsg";
	}
	
	/**
	 * 设置公众号的所属行业
	 * 设置行业可在MP中完成，每月可修改行业1次，账号仅可使用所属行业中相关的模板
	 * @param firstIndustry	行业1代码，具体参照行业代码查询
	 * @param secondIndustry	行业2代码，
	 * @return {"errcode":0,"errmsg":"ok"}
	 * @throws JSONException
	 */
	@RequestMapping("/setIndustry")
	public JSONObject setIndustry(String firstIndustry,String secondIndustry) throws JSONException{
		JSONObject jsonRet;
		if(firstIndustry == null || firstIndustry.trim().length() == 0 ||
				secondIndustry == null || secondIndustry.trim().length() == 0) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "第一行业ID、第二行业ID 不可为空！");
			return jsonRet;
		}
		
		try {
			jsonRet = this.templateMsgHandle.setIndustry(firstIndustry, secondIndustry);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 获取设置的行业信息 
	 * @return 
	 * { "errcode":0,"errmsg":"ok",
	 * 	"primary_industry":{"first_class":"运输与仓储","second_class":"快递"},
	 * 	"secondary_industry":{"first_class":"IT科技","second_class":"互联网|电子商务"}
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/getIndustry")
	public JSONObject getIndustry() throws JSONException{
		JSONObject jsonRet;
		try {
			jsonRet = this.templateMsgHandle.getIndustry();
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
	 * 添加行业模板至账号模板列表
	 * @param templateIdShort 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式 
	 * @return	
	 * {"errcode":0,"errmsg":"ok",
	 * "template_id":"Doclyl5uP7Aciu-qZ7mJNPtWkbkYnWBWVja26EGbNyk"
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/addTpl")
	public JSONObject addTemplate(String templateIdShort) throws JSONException{
		JSONObject jsonRet;
		if(templateIdShort == null || templateIdShort.trim().length() == 0 ) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "模板库中模板的编号 不可为空！");
			return jsonRet;
		}
		
		try {
			jsonRet = this.templateMsgHandle.addPrivateTemplate(templateIdShort);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 获取已添加至帐号下所有模板列表
	 * @return
	 * {	"errcode":0,"errmsg":"ok",
	 *  "template_list": [{
	 *       "template_id": "iPk5sOIt5X_flOVKn5GrTFpncEYTojx6ddbt8WYoV5s",
	 *       "title": "领取奖金提醒",
	 *       "primary_industry": "IT科技",
	 *       "deputy_industry": "互联网|电子商务",
	 *       "content": "{ {result.DATA} }\n\n领奖金额:{ {withdrawMoney.DATA} }\n领奖  时间:{ {withdrawTime.DATA} }\n银行信息:{ {cardInfo.DATA} }\n到账时间:  { {arrivedTime.DATA} }\n{ {remark.DATA} }",
	 *       "example": "您已提交领奖申请\n\n领奖金额：xxxx元\n领奖时间：2013-10-10 12:22:22\n银行信息：xx银行(尾号xxxx)\n到账时间：预计xxxxxxx\n\n预计将于xxxx到达您的银行卡"
	 *    }]
	 * }
	 * @throws JSONException 
	 */
	@RequestMapping("/getAllTpl")
	public JSONObject getTemplates() throws JSONException{
		JSONObject jsonRet;
		try {
			jsonRet = this.templateMsgHandle.getAllPrivateTemplate();
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
	 * 删除个人模板 
	 * @param templateId	公众帐号下消息模板ID
	 * @return
	 * {"errcode" : 0,"errmsg" : "ok"}
	 * @throws JSONException 
	 */
	@RequestMapping("/deleteTpl")
	public JSONObject deleteTemplate(String templateId) throws JSONException{
		JSONObject jsonRet;
		if(templateId == null || templateId.trim().length() == 0 ) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "公众帐号下消息模板ID 不可为空！");
			return jsonRet;
		}
		
		try {
			jsonRet = this.templateMsgHandle.deletePrivateTemplate(templateId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}
	
	/**
	 * 发送模板消息
	 * 
	 * @param toUser		用户ID
	 * @param templateId	消息模板ID 
	 * @param title		标题名称#####颜色
	 * @param keynote1	参数1：内容#####颜色
	 * @param keynote2	参数1：内容#####颜色
	 * @param keynote3	参数1：内容#####颜色
	 * @param reamrk		备注信息#####颜色
	 * @param url	可选，跳转url
	 * @param appId 可选，所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）
	 * @param pagepath 可选，所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar）
	 * 
	 * @return   {"errcode":0,"errmsg":"ok","msgid":200228332}
	 * @throws JSONException 
	 */
	@RequestMapping("/sendmsg")
	public JSONObject sendMsg(String toUser,String templateId,
			@RequestParam(value="url",required=false)String url,@RequestParam(value="appId",required=false)String appId,@RequestParam(value="pagepath",required=false)String pagepath,
			String title,String remark,
			String keynote1,String keynote2,String keynote3) throws JSONException{
		JSONObject jsonRet;
		if(toUser == null || toUser.trim().length() == 0 || templateId == null || templateId.trim().length() == 0 ||
				title == null || title.trim().length() == 0 || remark == null || remark.trim().length() == 0 ||
				keynote1 == null || keynote1.trim().length() == 0 ||
				keynote2 == null || keynote2.trim().length() == 0 || keynote3 == null || keynote3.trim().length() == 0 ) {
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -666);
			jsonRet.put("errmsg", "用户ID、消息模板ID、标题名称、参数1、参数2、参数3、备注信息 不可为空！");
			return jsonRet;
		}
		try {
			String[] titleAndColor = title.split("#####");
			if(titleAndColor.length != 2 || titleAndColor[0].trim().length()<1 || titleAndColor[1].trim().length() < 1) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -999);
				jsonRet.put("errmsg", "标题格式不正确，格式为：【标题内容#####颜色值】 ！");
				return jsonRet;
			}else {
				titleAndColor[1] = "#" + titleAndColor[1];
			}
			String[] remarkAndColor = remark.split("#####");
			if(remarkAndColor.length != 2 || remarkAndColor[0].trim().length()<1 || remarkAndColor[1].trim().length() < 1) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -999);
				jsonRet.put("errmsg", "备注格式不正确，格式为：【备注内容#####颜色值】 ！");
				return jsonRet;
			}else {
				remarkAndColor[1] = "#" + remarkAndColor[1];
			}
			String[] keynote1AndColor = keynote1.split("#####");
			if(keynote1AndColor.length != 2 || keynote1AndColor[0].trim().length()<1 || keynote1AndColor[1].trim().length() < 1) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -999);
				jsonRet.put("errmsg", "参数1格式不正确，格式为：【参数内容#####颜色值】 ！");
				return jsonRet;
			}else {
				keynote1AndColor[1] = "#" + keynote1AndColor[1];
			}
			String[] keynote2AndColor = keynote2.split("#####");
			if(keynote2AndColor.length != 2 || keynote2AndColor[0].trim().length()<1 || keynote2AndColor[1].trim().length() < 1) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -999);
				jsonRet.put("errmsg", "参数2格式不正确，格式为：【参数内容#####颜色值】 ！");
				return jsonRet;
			}else {
				keynote2AndColor[1] = "#" + keynote2AndColor[1];
			}
			String[] keynote3AndColor = keynote3.split("#####");
			if(keynote3AndColor.length != 2 || keynote3AndColor[0].trim().length()<1 || keynote3AndColor[1].trim().length() < 1) {
				jsonRet = new JSONObject();
				jsonRet.put("errcode", -999);
				jsonRet.put("errmsg", "参数3格式不正确，格式为：【参数内容#####颜色值】 ！");
				return jsonRet;
			}else {
				keynote3AndColor[1] = "#" + keynote3AndColor[1];
			}
			List<String[]> keynotes = new ArrayList<String[]>();
			keynotes.add(keynote1AndColor);
			keynotes.add(keynote2AndColor);
			keynotes.add(keynote3AndColor);
			jsonRet = this.templateMsgHandle.sendMsg(toUser, templateId, url, appId, pagepath, titleAndColor, keynotes, remarkAndColor);
		} catch (Exception e) {
			e.printStackTrace();
			jsonRet = new JSONObject();
			jsonRet.put("errcode", -777);
			jsonRet.put("errmsg", "系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet;
	}

}

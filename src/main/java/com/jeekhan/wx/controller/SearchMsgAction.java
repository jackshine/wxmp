package com.jeekhan.wx.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jeekhan.wx.model.WXMsgLog;
import com.jeekhan.wx.service.WXMsgLogService;
import com.jeekhan.wx.utils.PageCond;

/**
 * 微信消息查询
 * @author jeekhan
 *
 */
@Controller
@RequestMapping("/searchmsg")
@SessionAttributes("operator")
public class SearchMsgAction {
	
	@Autowired
	private WXMsgLogService wXMsgLogService;
	/**
	 * 获取消息查询主页
	 * @param map
	 * @return
	 */
	@RequestMapping("/index")
	public String getIndex(ModelMap map) {
		
		return "page-searchmsg";
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
	public String search(String jsonParams,PageCond pageCond) throws JSONException {
		JSONObject jsonRet = new JSONObject();
		try {
			Map<String,Object> condParams = new HashMap<String,Object>();
			if(jsonParams == null) {
				JSONObject params = new JSONObject(jsonParams);
				//条件整合
				if(params.has("msgType")) {
					if(params.getString("msgType") != null && (params.getString("msgType").trim().length()>0)){
						condParams.put("msgType", params.getString("msgType"));
					}
				}
				if(params.has("eventType")) {
					if(params.getString("eventType") != null && (params.getString("eventType").trim().length()>0)){
						condParams.put("eventType", params.getString("eventType"));
					}
				}
				if(params.has("inout")) {
					if(params.getString("inout") != null && (params.getString("inout").trim().length()>0)){
						condParams.put("inout", params.getString("inout"));
					}
				}
				if(params.has("status")) {
					if(params.getString("status") != null && (params.getString("status").trim().length()>0)){
						condParams.put("status", params.getString("status"));
					}
				}
				if(params.has("isMass")) {
					if(params.getString("isMass") != null && (params.getString("isMass").trim().length()>0)){
						condParams.put("isMass", params.getString("isMass"));
					}
				}
				if(params.has("isTpl")) {
					if(params.getString("isTpl") != null && (params.getString("isTpl").trim().length()>0)){
						condParams.put("isTpl", params.getString("isTpl"));
					}
				}
				if(params.has("fromUser")) {
					if(params.getString("fromUser") != null && (params.getString("fromUser").trim().length()>0)){
						condParams.put("fromUser", params.getString("fromUser"));
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
			}
			if(pageCond == null) {
				pageCond = new PageCond(0,20);
			}
			if(pageCond.getBegin()<0) {
				pageCond.setBegin(0);
			}
			if(pageCond.getPageSize() < 2 || pageCond.getPageSize() > 100) {
				pageCond.setPageSize(20);
			}
			
			int cnt = wXMsgLogService.countMsgs(condParams);
			pageCond.setCount(cnt);
			if(cnt > 0) {
			List<WXMsgLog> datas = wXMsgLogService.getMsgs(condParams, pageCond);
				jsonRet.put("datas", new JSONArray(datas));
			}
			JSONObject jaonPage = new JSONObject();
			jaonPage.put("pageSize", pageCond.getPageSize());
			jaonPage.put("begin", pageCond.getBegin());
			jaonPage.put("count", pageCond.getCount());
			jsonRet.put("errcode", 0);
			jsonRet.put("errmsg", "ok");
			jsonRet.put("pageCond", jaonPage);
		}catch(Exception e) {
			e.printStackTrace();
			jsonRet.put("errcode", -999);
			jsonRet.put("errmsg","系统异常，异常信息：" + e.getMessage());
		}
		return jsonRet.toString();
	}
}

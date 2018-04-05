package com.jeekhan.wx.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeekhan.wx.api.CustomizeMenuHandle;
import com.jeekhan.wx.msg.MsgDispatcher;
import com.jeekhan.wx.utils.SunSHAUtils;
/**
 * 微信控制类
 * @author Jee Khan
 *
 */
@RestController
public class WechatAction {
	private static Logger log = LoggerFactory.getLogger(WechatAction.class);
	@Value("${wx.TOKEN}")
	private String token;
	@Autowired
	private MsgDispatcher msgDispatcher;	//消息分发处理类
	 
	/**
	 * 微信服务器验证，用于接受微信公众平台服务器的调用
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序
	 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * @param mode
	 * @param articleId
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/wx",params={"signature","timestamp","nonce","echostr"})
	@ResponseBody
	public String virifyWXServer(@RequestParam("signature")String signature,@RequestParam("timestamp")String timestamp,
			@RequestParam("nonce")String nonce, @RequestParam("echostr")String echostr){
		String[] arr = {token,timestamp,nonce};
		Arrays.sort(arr);
		String strAll = arr[0] + arr[1] + arr[2];
		String ret = null;
		try {
			ret = SunSHAUtils.encodeSHAHex(strAll);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(signature.equals(ret)){
			//创建菜单
			try {
				CustomizeMenuHandle.createMenu(null);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return echostr;
		}
		return "fail";
	}
	
	/**
	 * 接受消息并进行响应，用于接受微信公众平台服务器的调用
	 * @param is
	 * @return
	 */
	@RequestMapping(value="/wx",method=RequestMethod.POST)
	@ResponseBody
	public String recvMsg(InputStream is){
		BufferedReader br;
		StringBuffer sb = new StringBuffer();
		try {
			String line = "";
			br = new BufferedReader(new InputStreamReader(is,"utf-8"));
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			String recvMsg = sb.toString();
			Document doc = DocumentHelper.parseText(recvMsg);
			Element xmlElement = doc.getRootElement();
            log.info("接收到消息/事件：" + recvMsg);
            //使用消息分发处理逻辑
	    		Object retObj = msgDispatcher.handle(xmlElement);
	    		if(retObj != null) {
	    			log.info("回复信息：" + retObj.toString());
	    			return retObj.toString();
	    		}else {
	    			return "";
	    		}
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
			return "";
		}
	}
}
